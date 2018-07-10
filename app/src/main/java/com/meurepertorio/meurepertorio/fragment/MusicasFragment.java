package com.meurepertorio.meurepertorio.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.activity.MusicaActivity;
import com.meurepertorio.meurepertorio.activity.MusicasActivity;
import com.meurepertorio.meurepertorio.adapter.MusicasAdapter;
import com.meurepertorio.meurepertorio.model.Musica;
import com.meurepertorio.meurepertorio.service.MusicaServiceBD;

/**
 * Created by matheus_soares on 16/05/18.
 */

public class MusicasFragment extends BaseFragment implements SearchView.OnQueryTextListener {

        protected RecyclerView recyclerView;
        private List<Musica> musicas;
        private LinearLayoutManager linearLayoutManager;
        private String tipo;
        private MusicaServiceBD musicaServiceBD;

        public MusicasFragment() {
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            musicaServiceBD = MusicaServiceBD.getInstance(getContext());

            if (getArguments() != null) {
                this.tipo = getArguments().getString("tipo");
            }

            setHasOptionsMenu(true);

            ((MusicasActivity) getActivity()).getSupportActionBar().setTitle(R.string.titulo_fragmentmusicas);

            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == android.content.pm.PackageManager.PERMISSION_GRANTED) {

            }else{

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_musicas, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_fragmentmusicas);
            linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);

            new Task().execute();

            return view;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.menu_fragment_musicas, menu); //infla o menu

            //SearchView
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_pesquisar).getActionView();
            searchView.setQueryHint(getString(R.string.hint_pesquisar));
            searchView.setOnQueryTextListener(this);
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            //toast("Ao pressionar enter.");
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            List<Musica> musicaList = new ArrayList<>();

            for(Musica musica : musicas){
                if(musica.nome.contains(newText)) {
                    musicaList.add(musica);
                }
            }

            //Context, fonte de dados, tratador do evento onClick
            recyclerView.setAdapter(new MusicasAdapter(getContext(), musicaList, onClickMusica()));

            return true;
        }


        protected MusicasAdapter.MusicaOnClickListener onClickMusica() {

            return new MusicasAdapter.MusicaOnClickListener() {

                @Override
                public void onClickMusica(View view, int idx) {

                    Musica musica = musicas.get(idx);

                    Intent intent = new Intent(getContext(), MusicaActivity.class);
                    intent.putExtra("musica", musica);
                    intent.putExtra("qualFragmentAbrir", "MusicaMostraFragment");
                    startActivity(intent);
                }
            };
        }

        private class Task extends AsyncTask<Void, Void, List<Musica>>{ //<Params, Progress, Result>

            List<Musica> musicas;

            @Override
            protected List<Musica> doInBackground(Void... voids) {
                //busca os musicas em background, em uma thread exclusiva para esta tarefa.
                if(MusicasFragment.this.tipo.equals(getString(R.string.musicas))){
                    return musicaServiceBD.getByTipo(getString(R.string.nome));
                }else if(MusicasFragment.this.tipo.equals(getString(R.string.banda))) {
                    return musicaServiceBD.getByTipo(getString(R.string.banda));
                }else if(MusicasFragment.this.tipo.equals(getString(R.string.todas))) {
                    return musicaServiceBD.getAll();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<Musica> musicas) {
                super.onPostExecute(musicas);
                //copia a lista de musicas para uso no onQueryTextChange()
                MusicasFragment.this.musicas = musicas;
                //atualiza a view na UIThread
                recyclerView.setAdapter(new MusicasAdapter(getContext(), musicas, onClickMusica())); //Context, fonte de dados, tratador do evento onClick
            }
        }//fim classe interna

    }
package com.meurepertorio.meurepertorio.fragment;

/**
 * Created by matheus_soares on 28/06/18.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.activity.MusicaActivity;
import com.meurepertorio.meurepertorio.model.Musica;
import com.meurepertorio.meurepertorio.service.MusicaServiceBD;

public class MusicaEditaFragment extends BaseFragment{
    
    private Musica musica;
    private final int SAVE = 0;
    private final int DELETE = 1;
    
    private EditText editTextNome;
    private EditText editTextBanda;
    private EditText editTextLetra;


    public void setMusica(Musica musica) {
        this.musica = musica;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editamusica, container, false);

        ((MusicaActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_editamusica);

        Log.d(TAG, "Dados do registro = " + musica); //um log para depurar


        Log.d(TAG, "Nome = " + musica.nome + "\nDescrição = " + musica.banda); //um log para depurar
        editTextNome = (EditText) view.findViewById(R.id.etMusica_cardview1_fragment_editamusica);
        editTextBanda = (EditText) view.findViewById(R.id.etBanda_cardview1_fragment_editamusica);
        editTextNome.setText(musica.nome);
        editTextBanda.setText(musica.banda);

        Log.d(TAG, "Letra = " + musica.letra); //um log para depurar
        editTextLetra = (EditText) view.findViewById(R.id.etLetra_cardview2_fragment_editamusica);
        editTextLetra.setText(musica.letra);

        return view;
    }

    /*
        Infla o menu.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_editamusica, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar:{

                musica.nome = editTextNome.getText().toString();
                musica.banda = editTextBanda.getText().toString();
                musica.letra = editTextLetra.getText().toString();

                new MusicasTask().execute(SAVE);
                break;
            }
            case R.id.menuitem_excluir:{
                new MusicasTask().execute(DELETE);
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }


    private class MusicasTask extends AsyncTask<Integer, Void, Long>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertWait(R.string.alert_title_wait, R.string.alert_message_processando);
        }

        @Override
        protected Long doInBackground(Integer... params) {

            switch(params[0]){
                case SAVE:
                    return MusicaServiceBD.getInstance(getContext()).save(musica);
                case DELETE:
                    return MusicaServiceBD.getInstance(getContext()).delete(musica);
                default: return 0L;
            }

        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            alertWaitDismiss();
            if(aLong > 0){
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);
            }else{
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);
            }
        }
    }

}
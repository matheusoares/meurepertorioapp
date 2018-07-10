package com.meurepertorio.meurepertorio.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.activity.MusicaActivity;
import com.meurepertorio.meurepertorio.model.Musica;
import com.meurepertorio.meurepertorio.service.MusicaServiceBD;

/**
 * Created by matheus_soares on 06/06/18.
 */

public class MusicaNovaFragment extends BaseFragment{

    private Musica musica; 

    private EditText editTextNome;
    private EditText editTextBanda;
    private EditText editTextLetra;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); 

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novamusica, container, false);

        ((MusicaActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_novamusica);

        musica = new Musica();


        editTextNome = (EditText) view.findViewById(R.id.etMusica_cardview1_fragment_novamusica);
        editTextBanda = (EditText) view.findViewById(R.id.etBanda_cardview1_fragment_novamusica);

        editTextLetra = (EditText) view.findViewById(R.id.etLetra_cardview2_fragment_novamusica);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_novamusica, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_salvar:{

                if(!editTextNome.getText().toString().isEmpty()) {
                    musica.nome = editTextNome.getText().toString();
                    musica.banda = editTextBanda.getText().toString();
                    musica.letra = editTextLetra.getText().toString();

                    new MusicasTask().execute();
                }else{
                    toast(getContext().getResources().getString(R.string.val_dadosinputs));
                }
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return false;
    }


    private class MusicasTask extends AsyncTask<Void, Void, Long> {

        @Override
        protected Long doInBackground(Void... voids) {
            return MusicaServiceBD.getInstance(getContext()).save(musica);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            alertWait(R.string.alert_title_wait, R.string.alert_message_processando);
        }


        @Override
        protected void onPostExecute(Long cont) {
            super.onPostExecute(cont);
            alertWaitDismiss();
            if(cont > 0){
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_realizadacomsucesso);
            }else{
                alertOk(R.string.alert_title_resultadodaoperacao, R.string.alert_message_erroaorealizaroperacao);
            }
        }
    }

}
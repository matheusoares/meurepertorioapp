package com.meurepertorio.meurepertorio.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.activity.MusicaActivity;
import com.meurepertorio.meurepertorio.model.Musica;

/**
 * Created by matheus_soares on 28/06/18.
 */

public class MusicaMostraFragment extends BaseFragment{
    
    private Musica musica; 
    private ProgressBar progressBarRest; 

    private TextView textViewNome; 
    private TextView textViewBanda; 
    private TextView textViewLetra;  

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
        View view = inflater.inflate(R.layout.fragment_mostramusica, container, false);

        ((MusicaActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_mostramusica);

        Log.d(TAG, "Dados do registro = " + musica); //um log para depurar
        

        Log.d(TAG, "Nome = " + musica.nome + "\nLetra = " + musica.banda); //um log para depurar
        textViewNome = (TextView) view.findViewById(R.id.tvNome_fragmentmostramusica);
        textViewBanda = (TextView) view.findViewById(R.id.tvBanda_fragmentmostramusica);
        textViewNome.setText(musica.nome);
        textViewBanda.setText(musica.banda);

        //carrega a letra e a longitude
        textViewLetra = (TextView) view.findViewById(R.id.tvMusica_fragmentmostramusica);
        textViewLetra.setText(musica.letra);
        Log.d(TAG, "Letra = " + musica.letra);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_mostramusica, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_editar: {
                MusicaEditaFragment musicaEditaFragment = new MusicaEditaFragment();
                musicaEditaFragment.setMusica(this.musica);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, musicaEditaFragment).commit();
                break;
            }
            case android.R.id.home:
                getActivity().finish();
                break;
        }

        return false;
    }
    
}
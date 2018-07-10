package com.meurepertorio.meurepertorio.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.fragment.MusicaMostraFragment;
import com.meurepertorio.meurepertorio.fragment.MusicaNovaFragment;
import com.meurepertorio.meurepertorio.model.Musica;

/**
 * Created by matheus_soares on 16/05/18.
 * Video 2 1:35:51
 */

public class MusicaActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_musica);


        String msg = (String) getIntent().getCharSequenceExtra("qualFragmentAbrir");
        if(msg.equals("MusicaNovaFragment")){
            replaceFragment(R.id.fragment_container, new MusicaNovaFragment());
        }else if(msg.equals("MusicaMostraFragment")){

            MusicaMostraFragment musicaDetalheFragment = new MusicaMostraFragment();

            replaceFragment(R.id.fragment_container, musicaDetalheFragment);

            Musica musica = (Musica) getIntent().getSerializableExtra("musica");
            Log.d(TAG, "Objeto musica recebido: " + musica.toString());

            musicaDetalheFragment.setMusica(musica);
        }
    }
}
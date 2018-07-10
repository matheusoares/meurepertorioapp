package com.meurepertorio.meurepertorio.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by matheus_soares on 16/05/18.
 */

public class BaseActivity extends AppCompatActivity {

    protected static String TAG = "meurepertorio";

    protected void replaceFragment(int container, Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(container, fragment).commit();
    }
}

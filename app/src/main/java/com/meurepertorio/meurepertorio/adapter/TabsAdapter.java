package com.meurepertorio.meurepertorio.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.fragment.MusicasFragment;

/**
 * Created by matheus_soares on 18/05/18.
 VIDEO 2 1:06:56 / 1:54:39

 */

public class TabsAdapter extends FragmentPagerAdapter{
    protected static final String TAG = "TabsAdapter";
    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        Fragment f = null;

        switch (position){
            case 0:
                f = new MusicasFragment();
                args.putString("tipo", context.getString(R.string.todas));
                break;
            case 1:
                f = new MusicasFragment();
                args.putString("tipo", context.getString(R.string.musicas));
                break;
            case 2:
                f = new MusicasFragment();
                args.putString("tipo", context.getString(R.string.banda));
                break;
        }

        f.setArguments(args);

        return f;
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return context.getString(R.string.todas);
            case 1:
                return context.getString(R.string.musicas);
            case 2:
                return context.getString(R.string.banda);
        }

        return null;
    }
}

package com.example.nenad.businessideacreator;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.transition.TransitionInflater;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.MenuItem;

public class MainActivity extends SingleFragment {


    SharedPreferences prefs = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        setupWindowsAnimations();
    }

    private void setupWindowsAnimations(){
        Slide slide = new Slide();
        slide.setDuration(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(slide);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prefs.getBoolean("firstrun",true)){


            prefs.edit().putBoolean("firstrun",false).commit();
        }
    }

    @Override
    protected Fragment createFragment() {
        return new IdeaListFragment();
    }
}

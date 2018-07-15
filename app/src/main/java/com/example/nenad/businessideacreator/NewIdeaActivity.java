package com.example.nenad.businessideacreator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.UUID;

import static com.example.nenad.businessideacreator.IdeaListAdapter.ARG_IDEA;

public class NewIdeaActivity extends SingleFragment{


    public static Intent newIntent(Context packageContext, Idea idea) {
        Intent intent = new Intent(packageContext, NewIdeaActivity.class);
        intent.putExtra(ARG_IDEA, idea);
        return intent;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.idea_fragment_container);
        if(!(fragment instanceof IOnBackPressed) || !((IOnBackPressed)fragment).onBackPressed()){
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_new_item,menu);
//        return true;
//    }

    @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(fade);
        }
    }

    @Override
    protected Fragment createFragment() {
        Idea idea =  getIntent().getParcelableExtra(ARG_IDEA);

        return new IdeaFragment().newInstance(idea);
    }
}

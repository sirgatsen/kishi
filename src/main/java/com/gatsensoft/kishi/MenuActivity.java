package com.gatsensoft.kishi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void readBook(View view) {

        startActivity(new Intent(MenuActivity.this, UnityPlayerActivity.class));
    }

    public void playGame(View view) {

        startActivity(new Intent(MenuActivity.this, PuzzleActivity.class));
    }
}

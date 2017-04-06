package com.example.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class StartScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void startGame(View view) {
        setContentView(R.layout.activity_choose_game_mode);
    }

    public void backToMenu(View view) {
        setContentView(R.layout.activity_start_screen);
    }

    public void showSettings(View view) {
        setContentView(R.layout.settings_activity);
    }
}

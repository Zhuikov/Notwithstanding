package com.example.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ChooseGameModeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_mode);
    }

    public void onePlayerGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("players", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void backToMenu(View view) {
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }

}

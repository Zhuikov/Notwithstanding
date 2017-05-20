package com.example.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.Quoridor;

public class GameActivity extends Activity {

   private Quoridor game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            game = new Quoridor(bundle.getInt("players") == 1);
        }
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }


}

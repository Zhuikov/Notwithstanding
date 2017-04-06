package com.example.androidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;


public class ChooseGameModeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game_mode);

        MyView myView = new MyView(this);
        myView.draw(new Canvas());


    }

    class MyView extends View {


        public MyView(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {
            setBackgroundColor(0x00FF00);
        }
    }

//    public void backToMenu(View view) {
//        setContentView(R.layout.activity_main);
//    }

}

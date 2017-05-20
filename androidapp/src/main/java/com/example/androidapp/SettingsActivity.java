package com.example.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }


}

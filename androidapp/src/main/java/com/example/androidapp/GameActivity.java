package com.example.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.spbstu.icc.kspt.zhuikov.quoridor.core.exceptions.FieldItemException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.exceptions.NoBarriersException;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.game.Quoridor;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.items.BarrierPosition;
import ru.spbstu.icc.kspt.zhuikov.quoridor.core.player.Owner;

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
        game.launchGame();
        try {
//            game.placeBarrier(1, 1, BarrierPosition.HORIZONTAL);
            game.placeBarrier(3, 5, BarrierPosition.VERTICAL);
        } catch (NoBarriersException | FieldItemException e) {
            e.printStackTrace();
        }
        MyView view = (MyView) findViewById(R.id.field);
        updateBarrierLabels();
        updateTurnLabel();
        view.setGame(game);

    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, StartScreenActivity.class);
        startActivity(intent);
    }

    public void updateBarrierLabels() {

        TextView topLabel = (TextView) findViewById(R.id.top_barriers_text_number);
        TextView botLabel = (TextView) findViewById(R.id.bottom_barriers_text_number);
        Integer topBarriers = game.getBarriersNumber(Owner.TOP);
        Integer botBarriers = game.getBarriersNumber(Owner.BOTTOM);
        topLabel.setText(topBarriers.toString());
        botLabel.setText(botBarriers.toString());
    }

    public void updateTurnLabel() {

        TextView turnLabel = (TextView) findViewById(R.id.turn_text);
        if (game.getCurrentOwner() == Owner.TOP) {
            turnLabel.setText("Turn Top");
        } else {
            turnLabel.setText("Turn Bottom");
        }
    }

}

package com.example.pikachu_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class Activity_Game extends AppCompatActivity {

    public static final String SENSOR_MODE = "SENSOR_MODE";
    final int DELAY = 1000; // delay per 1sec
    final Handler handler = new Handler();

    private MySensorsUtils mySensors;
    private Game_Manager gameManager;
    private int timerSeconds = 0;
    private boolean sensorMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyScreenUtils.hideSystemUI(this);
        sensorMode = getIntent().getExtras().getBoolean(SENSOR_MODE);
        gameManager = new Game_Manager(this);

        if (sensorMode) {
            mySensors = new MySensorsUtils(this);
            mySensors.setCallBackSensor(gameManager.callBackSensor);
            gameManager.getPanel_BTN_right().setVisibility(View.INVISIBLE);
            gameManager.getPanel_BTN_left().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorMode) {
            mySensors.resumed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorMode) {
            mySensors.stop();
        }
        stopTicker();
    }

    protected void updateBackImage(int id, ImageView imageView) {
        Glide.with(this).load(id).centerCrop().into(imageView);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            gameManager.runLogic();
            if (!gameManager.isAlive())
                gameOver();
            timerSeconds++;
            gameManager.getTimer_TXT_field().setText(String.format("%03d", timerSeconds));
            handler.postDelayed(runnable, DELAY);
        }
    };

    private void startTicker() {
        handler.postDelayed(runnable, DELAY);
    }

    private void stopTicker() {
        handler.removeCallbacks(runnable);
    }

    protected void gameOver() {
        handler.removeCallbacksAndMessages(runnable);
        Intent intent = new Intent(getApplicationContext(), Activity_GameOver.class);
        intent.putExtra("message_key", timerSeconds);
        startActivity(intent);
        finish();
    }
}
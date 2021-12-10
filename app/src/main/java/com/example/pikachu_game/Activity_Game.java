package com.example.pikachu_game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class Activity_Game extends AppCompatActivity {
    private Game_Manager game_manager;
    private int timerSeconds = 0;

    final int DELAY = 1000; // delay per 1sec
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyScreenUtils.hideSystemUI(this);
        game_manager = new Game_Manager(this);
    }

    protected void updateBackImage(int id, ImageView imageView) {
        Glide.with(this).load(id).centerCrop().into(imageView);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            game_manager.runLogic();
            if(!game_manager.isAlive())
                gameOver();
            timerSeconds++;
            game_manager.getTimer_TXT_field().setText(String.format("%03d", timerSeconds));
            handler.postDelayed(runnable, DELAY);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        startTicker();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTicker();
    }

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
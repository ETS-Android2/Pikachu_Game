package com.example.pikachu_game;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;


public class Activity_GameOver extends AppCompatActivity {

    private ImageView panel_IMG_gameOver;
    private Button game_over_BTN_back;
    private TextView timerSecondsMSG;
    private Button restart;
    private EditText winner_EDT_name;
    private Button winner_BTN_save;

    public static final String TOP_TEN = "TOP_TEN";
    public static final String NOT_EXIST = "NOT_EXIST";
    private TopTen topTen ;
    private Player winner;
    private Gson gson ;

    public Activity_GameOver() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        MyScreenUtils.hideSystemUI(this);
        findViews();
        initViews();
    }

    private void findViews() {
        gson = new Gson();
        panel_IMG_gameOver = findViewById(R.id.panel_IMG_gameOver);
        Glide
                .with(this)
                .load(R.drawable.background_img)
                .centerCrop()
                .into(panel_IMG_gameOver);

        timerSecondsMSG =(TextView)findViewById(R.id.game_over_TXT_time);
        restart = (Button)findViewById(R.id.game_over_BTN_restart);
        game_over_BTN_back = findViewById(R.id.game_over_BTN_back);
        winner_EDT_name=findViewById(R.id.winner_EDT_name);
        winner_BTN_save= findViewById(R.id.winner_BTN_save);

    }

    private void initViews() {
        initTopTen();
        // start the Intent
        Intent intent = getIntent();
        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        int time = intent.getIntExtra("message_key",0);
        // display the string into textView
        timerSecondsMSG.setText(time + "");

        initWinner(time);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });

        game_over_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_GameOver.this, Activity_Start.class);
                startActivity(myIntent);
                finish();
            }
        });

        winner_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winner_BTN_save.setBackgroundColor(Color.GRAY);
                winner_BTN_save.setEnabled(false);
                winner_BTN_save.setClickable(false);
                String name = winner_EDT_name.getText()+ "";
                winner.setName(name);
                addAndSaveToTopTen(); // top ten
            }
        });

    }

    private void playAgain() {
        Intent intent = new Intent(getApplicationContext(), Activity_Game.class);
        startActivity(intent);
        finish();
    }

    private void initWinner(int score) {
        winner = new Player("Player",score);
        Location location = LocationFunction.getLocationOfUser(this);
        if (location != null)
            winner.setLocation(location.getLatitude(), location.getLongitude());
    }

    private void initTopTen() {
        String gson_topTen = MySP.getInstance().getString(TOP_TEN, NOT_EXIST);
        if(gson_topTen.equals(NOT_EXIST))
            topTen = new TopTen();
        else
            topTen = gson.fromJson(gson_topTen, TopTen.class);
    }

    private  void addAndSaveToTopTen(){
        boolean isInTopTen = topTen.checkAndAdd(winner);
        String newTopTen = gson.toJson(topTen);
        MySP.getInstance().putString(TOP_TEN, newTopTen);
        if(isInTopTen)
            Toast.makeText(getApplicationContext(),"Congratulations! you are in our top 10!", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(),"Sorry.. maybe next time :)", Toast.LENGTH_LONG).show();
    }


}

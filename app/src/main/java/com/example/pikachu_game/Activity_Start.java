package com.example.pikachu_game;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class Activity_Start extends AppCompatActivity {

    private ImageView panel_IMG_background;
    private Button start_BTN_play;
    private Button getStart_BTN_top_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        MyScreenUtils.hideSystemUI(this);
        LocationFunction.askLocationPermission(this);
        findViews();
        initViews();
    }

    private void findViews() {
        panel_IMG_background = findViewById(R.id.panel_IMG_start);
        Glide
                .with(this)
                .load(R.drawable.background_img)
                .centerCrop()
                .into(panel_IMG_background);
        start_BTN_play= findViewById(R.id.start_BTN_play);
        getStart_BTN_top_10 =findViewById(R.id.start_BTN_top_10);
    }

    private void initViews() {

        start_BTN_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Activity_Game.class);
                startActivity(myIntent);
                finish();
            }
        });

        getStart_BTN_top_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Activity_TopTen.class);
                startActivity(myIntent);
                finish();
            }
        });

    }
}
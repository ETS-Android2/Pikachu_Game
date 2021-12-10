package com.example.pikachu_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Activity_TopTen extends AppCompatActivity {
    private ImageView panel_IMG_background;
    private Button top_ten_BTN_back;
    private Fragment_Map fragment_map;
    private Fragment_List fragment_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        MyScreenUtils.hideSystemUI(this);
        findViews();
        initViews();
    }

    private void findViews() {
        panel_IMG_background = findViewById(R.id.panel_IMG_topTen);
        Glide
                .with(this)
                .load(R.drawable.background_img)
                .centerCrop()
                .into(panel_IMG_background);
        top_ten_BTN_back = findViewById(R.id.top_ten_BTN_Back);
    }

    private void initViews() {

        fragment_list = new Fragment_List();
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_FRG_list, fragment_list).commit();

        fragment_map = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_FRG_map, fragment_map).commit();

        fragment_list.setCallBack(callBack);

        top_ten_BTN_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Activity_TopTen.this, Activity_Start.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
    private CallBack callBack= new CallBack() {

        @Override
        public void updateMapLocation(double lat, double lon) {
            fragment_map.setLatLong(lat,lon);

        }
    };
}

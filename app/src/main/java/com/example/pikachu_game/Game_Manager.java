package com.example.pikachu_game;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class Game_Manager {

    final private Activity_Game activityGame;
    private ImageView panel_IMG_background;
    private TextView timer_TXT_field;

    private ImageButton panel_BTN_right;
    private ImageButton panel_BTN_left;
    private ImageView[] panel_IMG_heartbeet;
    private ImageView[] panel_IMG_pikachu;

    private int[][] vals;
    private ImageView[][] path;
    private int current = 1;
    private final int MAX_LIVES = 3;
    private final int NUM_OF_COLUMNS = 3;
    private int columnChoose;
    private int lives = MAX_LIVES;

    private boolean isAlive = true;
    public static final String GSON_WINNER = "GSON_WINNER";


    public Game_Manager(Activity_Game activity) {
        this.activityGame = activity;
        findViews();
        initButtons();
        initLogic();
        updateUI();
    }


    public TextView getTimer_TXT_field() {
        return timer_TXT_field;
    }

    private void findViews() {
        panel_BTN_left = activityGame.findViewById(R.id.panel_BTN_left);
        panel_BTN_right = activityGame.findViewById(R.id.panel_BTN_right);

        panel_IMG_background = activityGame.findViewById(R.id.panel_IMG_background);
        activityGame.updateBackImage(R.drawable.background_img,panel_IMG_background);

        // set Timer TextView
        timer_TXT_field = activityGame.findViewById(R.id.timer);

        panel_IMG_heartbeet = new ImageView[]{
                activityGame.findViewById(R.id.panel_IMG_heartbeet1),
                activityGame.findViewById(R.id.panel_IMG_heartbeet2),
                activityGame.findViewById(R.id.panel_IMG_heartbeet3)
        };

        panel_IMG_pikachu = new ImageView[]{
                activityGame.findViewById(R.id.panel_IMG_main_left),
                activityGame.findViewById(R.id.panel_IMG_main_mid),
                activityGame.findViewById(R.id.panel_IMG_main_right),
        };

        path = new ImageView[][]{
                {activityGame.findViewById(R.id.panel_IMG_00), activityGame.findViewById(R.id.panel_IMG_01), activityGame.findViewById(R.id.panel_IMG_02)},
                {activityGame.findViewById(R.id.panel_IMG_10), activityGame.findViewById(R.id.panel_IMG_11), activityGame.findViewById(R.id.panel_IMG_12)},
                {activityGame.findViewById(R.id.panel_IMG_20), activityGame.findViewById(R.id.panel_IMG_21), activityGame.findViewById(R.id.panel_IMG_22)},
                {activityGame.findViewById(R.id.panel_IMG_30), activityGame.findViewById(R.id.panel_IMG_31), activityGame.findViewById(R.id.panel_IMG_32)}
        };

        vals = new int[path.length][path[0].length];

    }
    private void initButtons() {
        panel_BTN_left.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               vibrate(100);
               changeDirection(false);
            }
        }));

        panel_BTN_right.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate(100);
                changeDirection(true);
            }
        }));
    }

    private void initLogic(){
        for (int i = 0; i < vals.length ; i++) {
            for (int j = 0; j < vals[i].length; j++) {
                vals[i][j] = 0;
            }
        }
    }

    private void updateUI() {
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                ImageView pokeball = path[i][j];
                if (vals[i][j] == 0) {
                    pokeball.setVisibility(View.INVISIBLE);
                } else if (vals[i][j] == 1) {
                    pokeball.setVisibility(View.VISIBLE);
                }
            }
        }
    }
    private void vibrate(int millisecond) {
        Vibrator v = (Vibrator) activityGame.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(millisecond, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(millisecond);
        }
    }

    private void changeDirection(boolean direction) {
        if (direction && current <= 1) {
            panel_IMG_pikachu[current].setVisibility(View.INVISIBLE);
            current++;
            panel_IMG_pikachu[current].setVisibility(View.VISIBLE);
        } else if (!direction && current >= 1) {
            panel_IMG_pikachu[current].setVisibility(View.INVISIBLE);
            current--;
            panel_IMG_pikachu[current].setVisibility(View.VISIBLE);
        }
    }

    protected void runLogic() {
        checkCollision();
        columnChoose = (int) Math.floor(Math.random() * NUM_OF_COLUMNS);
        for (int i = vals.length-1; i > 0; i--) {
            for(int j = 0; j < vals[0].length; j++){
                vals[i][j] = vals[i-1][j];
            }
        }

        for (int i = 0; i < vals[0].length; i++) {
            vals[0][i] = 0;
        }
        vals[0][columnChoose] = 1;
        updateUI();
    }

    private void checkCollision() {
        if (vals[3][current] == 1) {
            vibrate(300);
           playSound(R.raw.videoplayback);
            if(lives > 0){
                panel_IMG_heartbeet[lives-1].setVisibility(View.INVISIBLE);
            }
            lives--;

            if (lives == 0) {
                isAlive = false;
            }
        }
    }

    private void playSound(int audio_mayday) {
        final MediaPlayer mp = MediaPlayer.create(activityGame, audio_mayday);
        mp.start();
    }

    public boolean isAlive() {
        return isAlive;
    }
}



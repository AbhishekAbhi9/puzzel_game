package com.example.akabhi.backlight_game;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //================Data stucture for keeping data of same type at same places
    private ArrayList<ArrayList_LinearLayout> arrayList_linearLayouts;
    private int ArrayId[] = {R.id.box_one, R.id.box_two, R.id.box_three, R.id.box_four};
    private int[] ColorInder;

    //==============in build view class of android
    private TextView score;
    private LinearLayout box, quit, reset;
    private TextView scoreBoard;

    //=============Variables that are used in this
    private int milisecondRepeat = 5000;
    private int idPush = 0, temp, colorId, Score_Card = 0;

    //============inbuild classes of android
    private static final Random RANDOM = new Random();
    private CountDownTimer countDownTimer = null;
    private ColorDrawable viewColor;
    private MediaPlayer mediaPlayer;
    private Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vibe = (Vibrator) getSystemService(MainActivity.VIBRATOR_SERVICE);
        score = findViewById(R.id.score);
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.gamemusic);
        ColorInder = MainActivity.this.getResources().getIntArray(R.array.colorchange);

        //=============Adding Layout to ArrayList===================================================
        arrayList_linearLayouts = new ArrayList<>();
        for (int i = 0; i < ArrayId.length; i++) {
            ArrayList_LinearLayout arrayList = new ArrayList_LinearLayout();
            arrayList.id = ArrayId[i];
            arrayList_linearLayouts.add(arrayList);
        }
        startTimer();
    }

    //===========this is the dialog popup for showing updated score ================================
    private void dialogCustom(int score) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custompopup);
        scoreBoard = dialog.findViewById(R.id.scoreUpdate);
        scoreBoard.setText(String.valueOf(score));

        //===============For closing the dialog box
        quit = dialog.findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        //============For reset the dialog box
        reset = dialog.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer();
                dialog.cancel();
                Score_Card = 0;
            }
        });

        mediaPlayer.pause();
        dialog.show();
    }

    //this function is starting point of the game ,from this point user start to intract with the user
    private void startTimer() {

        //this is CountDownTimer in built class of android help to count the second in the game
        countDownTimer = new CountDownTimer(milisecondRepeat, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    mediaPlayer.start();
                    //==================Changing back to original color=============================
                    box = findViewById(arrayList_linearLayouts.get(temp).getId());
                    box.setBackgroundColor(ColorInder[temp]);
                    viewColor = (ColorDrawable) box.getBackground();
                    colorId = viewColor.getColor();

                    //==============Calculating Score===============================================
                    if (colorId != MainActivity.this.getResources().getColor(R.color.box_ramdom)) {
                        box.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                /*if user press non-grey box then the counter will stop and display pop
                                with reset button and score */
                                finishTimer();
                                dialogCustom(Score_Card);
                            }
                        });
                    }


                    if (temp == idPush) {
                        box = findViewById(arrayList_linearLayouts.get(temp).getId());
                        box.setBackgroundColor(ColorInder[temp]);
                        viewColor = (ColorDrawable) box.getBackground();
                        colorId = viewColor.getColor();

                        //==============Calculating Score===============================================
                        if (colorId != MainActivity.this.getResources().getColor(R.color.box_ramdom)) {
                            box.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*if user press non-grey box then the counter will stop and display pop
                                    with reset button and score */
                                    finishTimer();
                                    dialogCustom(Score_Card);
                                }
                            });
                        }

                    }

                    //=====================Changing color to grey===================================
                    //this is the logic for which changing the fixed color into gray color
                    idPush = randomFunction();
                    box = findViewById(arrayList_linearLayouts.get(idPush).getId());
                    box.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.box_ramdom));
                    viewColor = (ColorDrawable) box.getBackground();
                    colorId = viewColor.getColor();

                    //==============Calculating Score===============================================
                    /* if [colorid equals to graycolor]
                        linearlaout[box] onclick
                        [incremnet score_card] score_card++ */

                    if (colorId == MainActivity.this.getResources().getColor(R.color.box_ramdom)) {
                        box.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vibe.vibrate(100);
                                Score_Card++;
                            }
                        });
                    }

                    score.setText("seconds remaining: " + millisUntilFinished / 1000 + " Score :" + Score_Card);
                    //==============================================================================
                    //temp = 0 => temp = idPush => temp != 0
                    temp = idPush;

                } catch (
                        IndexOutOfBoundsException i) {
                }
            }

            //this is where on timer finish its count
            @Override
            public void onFinish() {
                //this function is used to restart the counter again to keep it in loop
                StartTimer();
            }
        }.start();
    }

    //this is the function used to create random number from array of linear layout
    private int randomFunction() {
        idPush = RANDOM.nextInt(ArrayId.length);
        return idPush;
    }

    //when counter finish it count this function start the counter again
    private void StartTimer() {
        if (countDownTimer != null) {
            mediaPlayer.start();
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }

    //Finishing the timer when user click wrong color
    private void finishTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    //this is method is used to stop the background music when out app in pause mode
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }
}

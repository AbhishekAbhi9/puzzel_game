package com.example.akabhi.backlight_game.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.akabhi.backlight_game.Adapter.TopScoreAdapter;
import com.example.akabhi.backlight_game.DataBase.DataBase;
import com.example.akabhi.backlight_game.PojoClasses.TopScore;
import com.example.akabhi.backlight_game.R;

import java.util.ArrayList;

public class TopScoreActiity extends AppCompatActivity {

    private RecyclerView playerHistoy;
    private ArrayList<TopScore> topScores;
    private TopScoreAdapter topScoreAdapter;
    private DataBase dataBase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_score_actiity);
        playerHistoy = findViewById(R.id.playerHistoy);

        //==================================================
        topScores = new ArrayList<>();
        dataBase = new DataBase(TopScoreActiity.this);
        cursor = dataBase.Select_Function();
        while (cursor.moveToNext()) {
            TopScore topScore_data = new TopScore();
            topScore_data.RecentScore = cursor.getInt(2);
            topScore_data.playerName = cursor.getString(1);
            topScores.add(topScore_data);
        }

        //=====Adding data to adapter=======================
        topScoreAdapter = new TopScoreAdapter(this, topScores);
        playerHistoy.setAdapter(topScoreAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        playerHistoy.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(TopScoreActiity.this, MainActivity.class));
        return;
    }
}

package com.example.akabhi.backlight_game.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.akabhi.backlight_game.Activity.TopScoreActiity;
import com.example.akabhi.backlight_game.PojoClasses.TopScore;
import com.example.akabhi.backlight_game.R;

import java.util.ArrayList;

public class TopScoreAdapter extends RecyclerView.Adapter<TopScoreAdapter.View_Holder> {
    private Context context;
    private ArrayList<TopScore> topScores;

    public TopScoreAdapter(TopScoreActiity topScoreActiity, ArrayList<TopScore> topScores) {
        context = topScoreActiity;
        this.topScores = topScores;
    }

    @Override
    public int getItemCount() {
        return topScores.size();
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.topscoreadapter, null);
        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        holder.playerName.setText(topScores.get(position).getPlayerName());
        holder.playerScore.setText(String.valueOf(topScores.get(position).getRecentScore()));
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        TextView playerName, playerScore;

        public View_Holder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerScore = itemView.findViewById(R.id.playerScore);
        }
    }

}

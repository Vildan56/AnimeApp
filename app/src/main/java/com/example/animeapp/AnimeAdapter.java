package com.example.animeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    private List<Anime> animeList;

    public AnimeAdapter(List<Anime> animeList) {
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anime, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.tvTitle.setText(anime.getTitle());
        Picasso.get().load(anime.getPosterUrl()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public void updateList(List<Anime> newList) {
        animeList = newList;
        notifyDataSetChanged();
    }

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}

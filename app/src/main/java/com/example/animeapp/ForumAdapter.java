package com.example.animeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private List<Forum> forumList;

    public ForumAdapter(List<Forum> forumList) {
        this.forumList = forumList;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ForumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        Forum forum = forumList.get(position);
        holder.tvForumTitle.setText(forum.getTitle());
    }

    @Override
    public int getItemCount() {
        return forumList.size();
    }

    public void updateList(List<Forum> newList) {
        forumList = newList;
        notifyDataSetChanged();
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        TextView tvForumTitle;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            tvForumTitle = itemView.findViewById(R.id.tv_forum_title);
        }
    }
}
package com.example.animeapp;

import java.util.List;

public class Anime {
    private String id;
    private String title;
    private String title_eng;
    private String description;
    private int episodes;
    private List<String> genres;
    private String poster_url;
    private double rating;
    private String status;

    public Anime() {}

    public Anime(String id, String title, String title_eng, String description, int episodes, List<String> genres, String poster_url, double rating, String status) {
        this.id = id;
        this.title = title;
        this.title_eng = title_eng;
        this.description = description;
        this.episodes = episodes;
        this.genres = genres;
        this.poster_url = poster_url;
        this.rating = rating;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_eng() {
        return title_eng;
    }

    public void setTitle_eng(String title_eng) {
        this.title_eng = title_eng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

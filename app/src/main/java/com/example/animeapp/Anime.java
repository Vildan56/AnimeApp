package com.example.animeapp;

public class Anime {
    private String animeId;
    private String title;
    private String posterUrl;
    private String description;
    private String type;
    private String episodes;

    public Anime() {}

    public Anime(String animeId, String title, String posterUrl, String description, String type, String episodes) {
        this.animeId = animeId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.description = description;
        this.type = type;
        this.episodes = episodes;
    }

    public String getAnimeId() {
        return animeId;
    }

    public void setAnimeId(String animeId) {
        this.animeId = animeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }
}

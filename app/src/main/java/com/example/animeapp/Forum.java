package com.example.animeapp;

public class Forum {
    private String forumId;
    private String title;

    public Forum() {}

    public Forum(String forumId, String title) {
        this.forumId = forumId;
        this.title = title;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

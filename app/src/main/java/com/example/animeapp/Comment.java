package com.example.animeapp;

public class Comment {
    private String commentId;
    private String userId;
    private String text;

    public Comment() {}

    public Comment(String commentId, String userId, String text) {
        this.commentId = commentId;
        this.userId = userId;
        this.text = text;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

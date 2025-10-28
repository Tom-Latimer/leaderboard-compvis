package com.sweets.leaderboard_compvis.challenges.models;

public enum EMimeTypes {
    textCsv("text/csv");

    private final String contentType;

    EMimeTypes(String contentType) {
        this.contentType = contentType;
    }

    public String getValue() {
        return contentType;
    }
}

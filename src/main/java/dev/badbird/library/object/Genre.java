package dev.badbird.library.object;

public enum Genre {
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    FANTASY("Fantasy"),
    HISTORICAL("Historical"),
    SELF_HELP("Self-Help"),
    POETRY("Poetry"),
    MEMOIR("Memoir"),
    AUTOBIOGRAPHY("Autobiography"),
    THRILLER("Thriller"),
    ACTION("Action"),
    ADVENTURE("Adventure"),
    HORROR("Horror"),
    TRUE_CRIME("True Crime"),
    HOW_TO("How-to"),
    TRAGEDY("Tragedy");

    String name;

    Genre(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

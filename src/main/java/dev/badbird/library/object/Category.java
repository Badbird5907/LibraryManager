package dev.badbird.library.object;

public enum Category {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction");
    String name;
    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

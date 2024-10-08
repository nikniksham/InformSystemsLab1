package com.example.demo1.ENUMs;

public enum UserStatus {
    USER ("Обычный", 0),
    CANDIDATE ("Самовыдвиженец", 1),
    ADMIN ("Путин", 2),
    OTHER ("Другое", 3);

    private final String title;
    private final int id;

    UserStatus(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}

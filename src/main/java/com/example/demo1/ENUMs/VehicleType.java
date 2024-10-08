package com.example.demo1.ENUMs;

public enum VehicleType {
    SUBMARINE ("Подводка", 0),
    BOAT ("Лодка", 1),
    BICYCLE ("Велосипед", 2),
    MOTORCYCLE ("Мотоцикл", 3),
    HOVERBOARD ("Летающая доска", 4),
    OTHER ("Чёто ещё", 5);

    private final String title;
    private final int id;

    VehicleType(String title, int id) {
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

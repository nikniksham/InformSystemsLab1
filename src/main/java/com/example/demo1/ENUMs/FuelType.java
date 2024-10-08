package com.example.demo1.ENUMs;

public enum FuelType {
    GASOLINE ("Газовое", 0),
    NUCLEAR ("Ядерное", 1),
    PLASMA ("Плазмовое", 2),
    OTHER ("Другое", 3);

    private final String title;
    private final int id;
    FuelType(String title, int id) {
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

package com.example.demo1.ENUMs;

public enum TypeOfOperation {
    CREATE ("Создание", 0),
    CHANGE ("Изменение", 1),
    DELETE ("Удаление", 2),
    OTHER ("Чёт другое", 3);

    private final String title;
    private final int id;

    TypeOfOperation(String title, int id) {
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

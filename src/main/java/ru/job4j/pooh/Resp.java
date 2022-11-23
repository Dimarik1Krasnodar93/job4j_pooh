package ru.job4j.pooh;

public class Resp  {
    private String sourceName;
    private final String param; // нарушает принцип DRY - в двух местах хранятся данные. Непонятно зачем RESP нужен

    public Resp(String sourceName, String param) {
        this.sourceName = sourceName;
        this.param = param;
    }

    public String text() {
        return param;
    }
}

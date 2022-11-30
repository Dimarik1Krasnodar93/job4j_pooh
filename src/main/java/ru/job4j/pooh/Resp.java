package ru.job4j.pooh;

public class Resp  {
    private String sourceName;
    private final String param;

    public Resp(String sourceName, String param) {
        this.sourceName = sourceName;
        this.param = param;
    }

    public String text() {
        return param;
    }

    public String status() {
        return "";
    }

}

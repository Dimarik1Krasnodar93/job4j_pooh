package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] strings = content.split("\n");
        String temp = strings.length > 0 ? strings[0] : "";
        int index = temp.indexOf(' ');
        String httpRequestType = temp.substring(0, index);
        temp = temp.substring(index + 2);
        index = temp.indexOf('/');
        String poopMode = temp.substring(0, index);
        temp = temp.substring(index + 1);
        String sourceName = temp.substring(0, Math.min(temp.indexOf(' '), temp.indexOf('/')));
        temp = temp.substring(Math.min(temp.indexOf(' '), temp.indexOf('/')));
        String param;
        if (temp.indexOf('/') == 0) {
            param = temp.substring(1, temp.indexOf(' '));
        } else {
            param = strings.length > 6 ? strings[7].substring(0, strings[7].length() - 1) : "";
        }
        return new Req(httpRequestType, poopMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}

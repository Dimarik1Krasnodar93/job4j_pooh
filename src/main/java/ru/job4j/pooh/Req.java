package ru.job4j.pooh;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Req {

    private static final String LINE_SEPARATOR = System.lineSeparator();
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
        String[] strings = content.split(LINE_SEPARATOR);
        var currentLine = 0;
        String method = strings[currentLine++].split(" ")[0];

        var headers = new HashMap<String, String>();
        while (currentLine < strings.length && !strings[currentLine].isEmpty()) {
            var lineParts = strings[currentLine++].split(":");
            headers.put(lineParts[0].trim(), lineParts.length > 1 ? lineParts[1].trim() : "");
        }
        var body = new StringJoiner(LINE_SEPARATOR);
        while (++currentLine < strings.length) {
            if (!strings[currentLine].isEmpty()) {
                body.add(strings[currentLine]);
            }
        }
        headers.put("param", body.toString());
        String[] str0Split = strings[0].split("/");
        headers.put("poohMode", str0Split[1]);
        headers.put("sourceName", str0Split[2].split(" ")[0]);
        if (headers.get("param").isEmpty()) {
            String[] splitParam = str0Split[3].split(" ");
            if (splitParam.length > 1) {
                headers.put("param", str0Split[3].split(" ")[0]);
            }
        }
        return new Req(method, headers.get("poohMode"), headers.get("sourceName"),
                headers.get("param"));
    }

    public static Req of1(String content) {
        String[] strings = content.split(LINE_SEPARATOR);
        String method = strings[0].split(" ")[0];
        String[] str0Split = strings[0].split("/");
        String poohMode = str0Split[1];
        String sourceName = str0Split[2].split(" ")[0];
        String[] str0Split2 = str0Split[3].split(" ");
        String param = str0Split2.length > 1 ? str0Split[3].split(" ")[0] : "";
        if (param.isEmpty()) {
            param = strings[strings.length - 1].contains("=") ? strings[strings.length - 1] : "";
        }
        return new Req(method, poohMode, sourceName, param);
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

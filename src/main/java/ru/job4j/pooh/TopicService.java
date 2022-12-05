package ru.job4j.pooh;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final Map<String, Queue<Resp>> map = new ConcurrentHashMap<>();
    private static final String STR_GET = "GET";
    @Override
    public Resp process(Req req) {
        map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        if (STR_GET.equals(req.httpRequestType())) {
            Resp result =  map.get(req.getSourceName()).poll();
            if (result == null) {
                result = new Resp("", "");
            }
            return result;
        } else {
            map.get(req.getSourceName()).offer(new Resp(req.getSourceName(), req.getParam()));
            return new Resp("", "");
        }
    }
}

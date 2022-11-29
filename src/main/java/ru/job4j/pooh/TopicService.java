package ru.job4j.pooh;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    Map<String, Queue<Resp>> map = new ConcurrentHashMap<>();
    @Override
    public Resp process(Req req) {
        if ("GET".equals(req.httpRequestType())) {
            map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            Resp result =  map.get(req.getSourceName()).poll();
            if (result == null) {
                result = new Resp("", "");
            }
            return result;
        } else {
            map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            map.get(req.getSourceName()).offer(new Resp(req.getSourceName(), req.getParam()));
            return new Resp("", "");
        }
    }
}

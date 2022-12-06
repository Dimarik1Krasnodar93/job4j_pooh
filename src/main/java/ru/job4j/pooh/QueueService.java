package ru.job4j.pooh;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final Map<String, Queue<Resp>> map = new ConcurrentHashMap<>();
    private static final String STR_GET = "GET";
    private static final Resp EMPTY_RESPONSE = new Resp("", "");

    public QueueService() {
    }

    @Override
    public Resp process(Req req) {
        Resp result = EMPTY_RESPONSE;
        map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        if (STR_GET.equals(req.httpRequestType())) {
            Resp message = map.get(req.getSourceName()).poll();
            if (message != null) {
                result = message;
            }
        } else {
            map.get(req.getSourceName()).offer(new Resp(req.getSourceName(), req.getParam()));
        }
       return result;
    }

    private Resp post(Req req) {
        Queue<Resp> queue = map.get(req.getSourceName());
        Resp resp = new Resp(req.getSourceName(), req.getParam());
        queue.offer(resp);
        return resp;
    }

    private Resp get(Req req) {
        Queue<Resp> queue = map.get(req.getSourceName());
        return queue.poll();
    }
}
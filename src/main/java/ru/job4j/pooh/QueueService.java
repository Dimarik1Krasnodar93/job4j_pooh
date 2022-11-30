package ru.job4j.pooh;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class QueueService implements Service {
    private final Map<String, Queue<Resp>> map = new ConcurrentHashMap<>();
    private static final String STR_GET = "GET";

    public QueueService() {
    }

    @Override
    public Resp process(Req req) {
        if (!map.containsKey(req.getSourceName())) {
            map.put(req.getSourceName(), new ArrayDeque<Resp>());
        }
        if (STR_GET.equals(req.httpRequestType())) {
            return get(req);
        } else {
            return post(req);
        }

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
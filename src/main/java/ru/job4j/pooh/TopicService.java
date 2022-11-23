package ru.job4j.pooh;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    Queue<Resp> queue = new ConcurrentLinkedQueue();
    @Override
    public Resp process(Req req) {
        if ("GET".equals(req.httpRequestType())) {
            Resp result =  queue.poll();
            if (result == null) {
                result = new Resp("", "");
            }
            return result;
        } else {
            queue.offer(new Resp(req.getSourceName(), req.getParam()));
            return new Resp("", "");
        }
    }
}

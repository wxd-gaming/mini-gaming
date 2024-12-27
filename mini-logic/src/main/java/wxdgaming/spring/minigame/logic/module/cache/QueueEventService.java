package wxdgaming.spring.minigame.logic.module.cache;

import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.core.threading.EventQueue;
import wxdgaming.spring.boot.core.threading.LogicExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 队列服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-25 19:23
 **/
@Service
public class QueueEventService implements InitPrint {

    private final ConcurrentHashMap<Integer, EventQueue> userQueueEventMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, EventQueue> globalQueueEventMap = new ConcurrentHashMap<>();

    @LogicStart
    public void onStart(LogicExecutor logicExecutor) {
        System.out.println("QueueEventService init " + QueueEventService.class.hashCode());
        for (int i = 1; i <= 10; i++) {
            EventQueue eventQueue = new EventQueue("q" + i, logicExecutor);
            userQueueEventMap.put(i, eventQueue);
        }

        for (int i = 1; i <= 10; i++) {
            EventQueue eventQueue = new EventQueue("q" + i, logicExecutor);
            globalQueueEventMap.put(i, eventQueue);
        }
    }

    public EventQueue getUserEventQueue(long uid) {
        return getUserEventQueue((int) ((uid % 10) + 1));
    }

    public EventQueue getUserEventQueue(int id) {
        return userQueueEventMap.get(id);
    }

    public EventQueue getGlobalEventQueue(long uid) {
        return getGlobalEventQueue((int) ((uid % 10) + 1));
    }

    public EventQueue getGlobalEventQueue(int id) {
        return globalQueueEventMap.get(id);
    }

}

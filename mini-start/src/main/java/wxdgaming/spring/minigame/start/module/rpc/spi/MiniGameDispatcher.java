package wxdgaming.spring.minigame.start.module.rpc.spi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.core.threading.Event;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.message.PojoBase;
import wxdgaming.spring.boot.net.server.ServerMessageDispatcher;
import wxdgaming.spring.minigame.start.MiniGameSpringReflect;
import wxdgaming.spring.minigame.start.MiniGameStartScan;
import wxdgaming.spring.minigame.start.module.data.DataCenter;

import java.util.concurrent.Executor;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Component
@RequestMapping("/broker")
public class MiniGameDispatcher extends ServerMessageDispatcher {

    final DataCenter dataCenter;

    public MiniGameDispatcher(@Value("${socket.printLogger:false}") boolean printLogger,
                              DataCenter dataCenter) {
        super(printLogger, new String[]{MiniGameStartScan.class.getPackageName()});
        this.dataCenter = dataCenter;
    }

    @LogicStart
    @Order(5000)
    public void init(MiniGameSpringReflect context) {
        super.initMapping(context.content());
    }

    @Override public void dispatch(SocketSession socketSession, int msgId, byte[] messageBytes) throws Exception {
        super.dispatch(socketSession, msgId, messageBytes);
    }

    @Override public void dispatch(SocketSession socketSession, PojoBase message) throws Exception {
        super.dispatch(socketSession, message);
    }

    @Override public void dispatch(SocketSession socketSession, int msgId, PojoBase message) throws Exception {
        super.dispatch(socketSession, msgId, message);
    }

    @Override protected void executor(SocketSession socketSession, Executor executor, String queueName, Event event) {
        // if ("map".equals(queueName)) {
        //     long sessionId = ThreadContext.context("sessionId");
        //     Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
        //     if (playerId == null) {
        //         throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
        //     }
        //     Player player = dbCacheService.find(Player.class, playerId);
        //     queueName = queueName + "-" + player.getMapId();
        //     ThreadContext.putContent(player);
        // } else {
        //     long sessionId = ThreadContext.context("sessionId");
        //     Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
        //     if (playerId == null) {
        //         throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
        //     }
        //     Player player = dbCacheService.find(Player.class, playerId);
        //     queueName = queueName + "-" + (player.getUid() % 10);
        //     ThreadContext.putContent(player);
        // }
        super.executor(socketSession, executor, queueName, event);
    }

}

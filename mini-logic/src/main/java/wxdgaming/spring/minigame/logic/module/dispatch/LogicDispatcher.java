package wxdgaming.spring.minigame.logic.module.dispatch;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.core.threading.Event;
import wxdgaming.spring.boot.core.threading.ThreadContext;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.message.PojoBase;
import wxdgaming.spring.boot.net.server.ServerMessageDispatcher;
import wxdgaming.spring.boot.rpc.RPC;
import wxdgaming.spring.minigame.bean.entity.user.Player;
import wxdgaming.spring.minigame.logic.LogicScan;
import wxdgaming.spring.minigame.logic.LogicSpringReflect;
import wxdgaming.spring.minigame.logic.module.cache.DbCacheService;
import wxdgaming.spring.minigame.logic.module.data.DataCenter;

import java.util.concurrent.Executor;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Component
@RequestMapping("/broker")
public class LogicDispatcher extends ServerMessageDispatcher {

    final DataCenter dataCenter;
    final DbCacheService dbCacheService;

    public LogicDispatcher(@Value("${socket.printLogger:false}") boolean printLogger,
                           DataCenter dataCenter, DbCacheService dbCacheService) {
        super(printLogger, new String[]{LogicScan.class.getPackageName()});
        this.dataCenter = dataCenter;
        this.dbCacheService = dbCacheService;
    }

    @LogicStart
    @Order(5000)
    public void init(LogicSpringReflect context) {
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
        if ("map".equals(queueName)) {
            long sessionId = ThreadContext.context("sessionId");
            Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
            if (playerId == null) {
                throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
            }
            Player player = dbCacheService.find(Player.class, playerId);
            queueName = queueName + "-" + player.getMapId();
            ThreadContext.putContent(player);
        } else {
            long sessionId = ThreadContext.context("sessionId");
            Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
            if (playerId == null) {
                throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
            }
            Player player = dbCacheService.find(Player.class, playerId);
            queueName = queueName + "-" + (player.getUid() % 10);
            ThreadContext.putContent(player);
        }
        super.executor(socketSession, executor, queueName, event);
    }

    /**
     * 通过 broker 转发过来的消息
     *
     * @param session
     * @param jsonObject
     * @throws Exception
     * @author: wxd-gaming(無心道, 15388152619)
     * @version: 2025-01-04 19:48
     */
    @RPC("/proto")
    public void proto(SocketSession session, JSONObject jsonObject) throws Exception {
        long sessionId = jsonObject.getLongValue("sessionId");
        int msgId = jsonObject.getIntValue("msgId");
        byte[] bytes = jsonObject.getBytes("data");
        ThreadContext.putContent("sessionId", sessionId);
        dispatch(session, msgId, bytes);
    }

}

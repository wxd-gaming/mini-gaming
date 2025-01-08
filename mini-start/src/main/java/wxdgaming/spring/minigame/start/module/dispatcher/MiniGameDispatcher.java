package wxdgaming.spring.minigame.start.module.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.net.MessageDispatcherHandler;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.message.SerializerUtil;
import wxdgaming.spring.boot.net.server.SocketService;
import wxdgaming.spring.minigame.proto.LoginMessage;
import wxdgaming.spring.minigame.start.ILogicServerMain;
import wxdgaming.spring.minigame.start.MiniGameSpringReflect;
import wxdgaming.spring.minigame.start.module.data.DataCenter;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Slf4j
@Component
public class MiniGameDispatcher extends MessageDispatcherHandler {

    final DataCenter dataCenter;
    int loginMessageId;

    public MiniGameDispatcher(@Value("${socket.printLogger:false}") boolean printLogger,
                              DataCenter dataCenter,
                              SocketService socketService) {
        super(printLogger);
        socketService.getServerMessageDecode().getDispatcher().setDispatcherHandler(this);
        this.dataCenter = dataCenter;
    }

    @LogicStart
    @Order(5000)
    public void init(MiniGameSpringReflect context, SocketService socketService) {
        loginMessageId = socketService.getServerMessageDecode().getDispatcher().registerMessage(LoginMessage.ReqLogin.class);
    }

    public void msgBytesNotDispatcher(SocketSession socketSession, int msgId, byte[] messageBytes) throws Exception {
        int serverId;
        if (msgId == loginMessageId) {
            /*第一个消息应该是登录*/
            LoginMessage.ReqLogin reqLogin = SerializerUtil.decode(messageBytes, LoginMessage.ReqLogin.class);
            serverId = reqLogin.getServerId();
            socketSession.attribute("serverId", serverId);
        } else {
            /*之后的所有消息都需要这个 serverid 才知道转发到某一个子容器*/
            Integer att = socketSession.attribute("serverId");
            serverId = att == null ? 0 : att;
        }
        if (serverId == 0) {
            throw new RuntimeException("玩家还没有进入游戏无法执行游戏消息");
        }
        ILogicServerMain iLogicServerMain = dataCenter.getServerMap().get(serverId);
        if (iLogicServerMain == null) {
            log.error("区服({})不存在, msgId={}", serverId, msgId);
            return;
        }
        iLogicServerMain.onReceive(socketSession, msgId, messageBytes);
    }

    //
    // @Override public void dispatch(SocketSession socketSession, int msgId, byte[] messageBytes) throws Exception {
    //
    // }
    //
    // @Override public void dispatch(SocketSession socketSession, PojoBase message) throws Exception {
    //     super.dispatch(socketSession, message);
    // }
    //
    // @Override public void dispatch(SocketSession socketSession, int msgId, PojoBase message) throws Exception {
    //     super.dispatch(socketSession, msgId, message);
    // }
    //
    // @Override protected void executor(SocketSession socketSession, Executor executor, String queueName, Event event) {
    //     // if ("map".equals(queueName)) {
    //     //     long sessionId = ThreadContext.context("sessionId");
    //     //     Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
    //     //     if (playerId == null) {
    //     //         throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
    //     //     }
    //     //     Player player = dbCacheService.find(Player.class, playerId);
    //     //     queueName = queueName + "-" + player.getMapId();
    //     //     ThreadContext.putContent(player);
    //     // } else {
    //     //     long sessionId = ThreadContext.context("sessionId");
    //     //     Long playerId = dataCenter.getSessionId2PlayerIdMap().get(sessionId);
    //     //     if (playerId == null) {
    //     //         throw new RuntimeException("玩家还没有进入地图无法执行地图消息");
    //     //     }
    //     //     Player player = dbCacheService.find(Player.class, playerId);
    //     //     queueName = queueName + "-" + (player.getUid() % 10);
    //     //     ThreadContext.putContent(player);
    //     // }
    //     super.executor(socketSession, executor, queueName, event);
    // }


}

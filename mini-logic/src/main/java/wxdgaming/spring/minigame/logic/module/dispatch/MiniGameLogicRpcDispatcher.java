package wxdgaming.spring.minigame.logic.module.dispatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.rpc.RpcDispatcher;
import wxdgaming.spring.minigame.logic.LogicScan;
import wxdgaming.spring.minigame.logic.LogicSpringReflect;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Component
public class MiniGameLogicRpcDispatcher extends RpcDispatcher {

    public MiniGameLogicRpcDispatcher(
            @Value("${socket.printLogger:false}") boolean printLogger,
            @Value("${socket.rpc-token:getg6jhkopw435dvmkmcvx5y63-40}") String RPC_TOKEN) {
        super(printLogger, RPC_TOKEN, new String[]{LogicScan.class.getPackageName()});
    }

    @LogicStart
    @Order(5000)
    public void init(LogicSpringReflect context) {
        super.initMapping(context.content());
    }

    @Override public void rpcReqSocketAction(SocketSession session, long rpcId, long targetId, String path, String remoteParams) {
        super.rpcReqSocketAction(session, rpcId, targetId, path, remoteParams);
    }
}

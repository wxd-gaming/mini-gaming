package wxdgaming.spring.minigame.start.module.rpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.SpringReflectContent;
import wxdgaming.spring.boot.core.ann.AppStart;
import wxdgaming.spring.boot.core.ann.ReLoad;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.rpc.RpcDispatcher;
import wxdgaming.spring.boot.rpc.RpcService;
import wxdgaming.spring.minigame.start.ILogicServerMain;
import wxdgaming.spring.minigame.start.module.data.DataCenter;

/**
 * mini rpc处理
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-20 21:15
 **/
@Component
public class MiniGameRpcDispatcher extends RpcDispatcher {

    final DataCenter dataCenter;

    public MiniGameRpcDispatcher(
            @Value("${socket.printLogger:false}") boolean printLogger,
            @Value("${socket.rpc-token:getg6jhkopw435dvmkmcvx5y63-40}") String RPC_TOKEN, DataCenter dataCenter) {
        super(printLogger, RPC_TOKEN);
        this.dataCenter = dataCenter;
    }

    @AppStart
    @Order(200)
    public void init(RpcService rpcService) {
        rpcService.setRpcDispatcher(this);
    }

    @AppStart
    @ReLoad
    @Order(99999)
    @Override public void initMapping(SpringReflectContent springReflectContent) {
        super.initMapping(springReflectContent);
    }


    @Override public void rpcReqSocketAction(SocketSession session, long rpcId, long targetId, String path, String remoteParams) {
        if (targetId == 0) {
            for (ILogicServerMain logicServerMain : dataCenter.getServerMap().values()) {
                logicServerMain.onReceiveRpc(session, rpcId, targetId, path, remoteParams);
            }
        } else {
            ILogicServerMain iLogicServerMain = dataCenter.getServerMap().get((int) targetId);
            if (iLogicServerMain == null) {
                response(session, rpcId, targetId, 10, "未知区服：" + targetId);
            }
            iLogicServerMain.onReceiveRpc(session, rpcId, targetId, path, remoteParams);
        }
    }

}

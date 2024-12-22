package wxdgaming.spring.minigame.start.module.rpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
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
@Service
public class MiniGameRpcDispatcher extends RpcDispatcher {

    final DataCenter dataCenter;

    public MiniGameRpcDispatcher(@Value("${socket.rpc-token:getg6jhkopw435dvmkmcvx5y63-40}")
                                 String RPC_TOKEN, DataCenter dataCenter) {
        super(RPC_TOKEN);
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


    @Override public Object rpcReqSocketAction(SocketSession session, String rpcToken, long rpcId, long targetId, String path, String remoteParams) throws Exception {
        if (targetId == 0) {
            for (ILogicServerMain logicServerMain : dataCenter.getServerMap().values()) {
                logicServerMain.onReceiveRpc(session, rpcToken, rpcId, targetId, path, remoteParams);
            }
        } else {
            ILogicServerMain iLogicServerMain = dataCenter.getServerMap().get((int) targetId);
            if (iLogicServerMain == null) {
                throw new RuntimeException("未知区服：" + targetId);
            }
            return iLogicServerMain.onReceiveRpc(session, rpcToken, rpcId, targetId, path, remoteParams);
        }
        return null;
    }

}

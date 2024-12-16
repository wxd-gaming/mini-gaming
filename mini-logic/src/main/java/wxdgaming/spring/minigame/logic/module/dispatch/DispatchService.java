package wxdgaming.spring.minigame.logic.module.dispatch;

import wxdgaming.spring.boot.net.server.ServerMessageDispatcher;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
public class DispatchService extends ServerMessageDispatcher {

    public DispatchService() {
        super(new String[]{"wxdgaming.spring.minigame.logic"});
    }

}

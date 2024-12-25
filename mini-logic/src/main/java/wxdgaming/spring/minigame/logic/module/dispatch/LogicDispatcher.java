package wxdgaming.spring.minigame.logic.module.dispatch;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.net.server.ServerMessageDispatcher;
import wxdgaming.spring.minigame.logic.LogicScan;
import wxdgaming.spring.minigame.logic.LogicSpringReflect;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Component
public class LogicDispatcher extends ServerMessageDispatcher {

    public LogicDispatcher() {
        super(new String[]{LogicScan.class.getPackageName()});
    }

    @LogicStart
    @Order(5000)
    public void init(LogicSpringReflect context) {
        super.initMapping(context.content());
    }

}

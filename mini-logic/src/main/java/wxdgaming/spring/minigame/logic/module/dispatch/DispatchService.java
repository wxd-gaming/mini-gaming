package wxdgaming.spring.minigame.logic.module.dispatch;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.SpringReflectContext;
import wxdgaming.spring.boot.core.ann.Start;
import wxdgaming.spring.boot.net.server.ServerMessageDispatcher;
import wxdgaming.spring.minigame.logic.LogicServerMain;
import wxdgaming.spring.minigame.logic.LogicSpringReflect;

/**
 * 消息分发服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:33
 **/
@Service
public class DispatchService extends ServerMessageDispatcher {

    public DispatchService() {
        super(new String[]{"wxdgaming.spring.minigame.logic"});
    }

    @Start
    public void init(@Qualifier("LogicServerMain") LogicServerMain logicServerMain, LogicSpringReflect context) {
        ConfigurableApplicationContext childContext = logicServerMain.getChildContext();
        SpringReflectContext build = SpringReflectContext.build(childContext);
        super.initMapping(build);
    }

}

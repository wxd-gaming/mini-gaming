package wxdgaming.spring.minigame.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import wxdgaming.spring.boot.core.SpringChildContext;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.module.service.ILogicServerMain;

/**
 * 逻辑服务主入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:35
 **/
public class LogicServerMain implements ILogicServerMain {

    @Override public void init() {
        ConfigurableApplicationContext run = SpringApplication.run(LogicServerMain.class, new String[0]);
        ConfigurableApplicationContext configurableApplicationContext = new SpringChildContext().newChild4Jar(null, LogicScan.class, "./lib", "./script");
    }

    @Override
    public void onLogin(SocketSession session) {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onReceive(SocketSession session) {

    }

    @Override
    public void setCfg() {

    }

}

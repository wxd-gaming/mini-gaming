package wxdgaming.spring.minigame.module.service;

import org.springframework.context.ConfigurableApplicationContext;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.net.SocketSession;

/**
 * 逻辑服务入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:13
 **/
public interface ILogicServerMain {

    void init(ConfigurableApplicationContext parent, ClassLoader parentClassLoad, JdbcContext jdbcContext);

    void onLogin(SocketSession session);

    void onLogout();

    void onReceive(SocketSession session);

    void setCfg();

}

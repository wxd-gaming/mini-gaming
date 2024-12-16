package wxdgaming.spring.minigame.module.service;

import wxdgaming.spring.boot.net.SocketSession;

/**
 * 逻辑服务入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:13
 **/
public interface ILogicServerMain {

    void init();

    void onLogin(SocketSession session);

    void onLogout();

    void onReceive(SocketSession session);

    void setCfg();

}

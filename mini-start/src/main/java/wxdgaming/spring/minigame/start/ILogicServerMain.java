package wxdgaming.spring.minigame.start;

import org.springframework.context.ConfigurableApplicationContext;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.loader.BootClassLoader;
import wxdgaming.spring.boot.loader.ExtendLoader;
import wxdgaming.spring.boot.net.SocketSession;

/**
 * 逻辑服务入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:13
 **/
public interface ILogicServerMain {

    void init(ConfigurableApplicationContext parent, BootClassLoader bootClassLoader, ExtendLoader extendLoader, JdbcContext jdbcContext, int sid);

    void onLogin(SocketSession session);

    void onLogout();

    void onReceive(SocketSession session);

    void onReceiveRpc(SocketSession session, long rpcId, long targetId, String path, String remoteParams);

    void setCfg();

}

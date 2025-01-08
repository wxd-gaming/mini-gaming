package code.spi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.threading.ExecutorWith;
import wxdgaming.spring.boot.core.threading.ThreadContext;
import wxdgaming.spring.boot.net.ProtoMapper;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.proto.LoginMessage;

/**
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-05 20:03
 **/
@Slf4j
@Component
public class LoginSpi {

    @ProtoMapper
    @ExecutorWith(queueName = "login")
    public void resLogin(SocketSession session, LoginMessage.ResLogin resLogin) {
        log.info("登录成功 queue={}, {}", ThreadContext.queueName(), resLogin.toString());
    }

}

package code.spi;

import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.net.ProtoMapper;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.proto.LoginMessage;

/**
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-05 20:03
 **/
@Component
public class LoginSpi {

    @ProtoMapper
    public void resLogin(SocketSession session, LoginMessage.ResLogin resLogin) {
        System.out.println("登录成功");
    }

}

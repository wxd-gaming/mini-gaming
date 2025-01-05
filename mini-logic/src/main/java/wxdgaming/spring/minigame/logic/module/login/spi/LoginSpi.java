package wxdgaming.spring.minigame.logic.module.login.spi;

import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.net.ProtoMapper;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.logic.LogicServerMain;
import wxdgaming.spring.minigame.proto.LoginMessage;

/**
 * 登录
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-05 12:05
 **/
@Component
public class LoginSpi {

    @ProtoMapper
    public void login(SocketSession session, LoginMessage.ReqLogin reqLogin) {

        LoginMessage.ResLogin resLogin = new LoginMessage.ResLogin();
        resLogin.setServerId(reqLogin.getServerId());
        LoginMessage.RoleBean roleBean = new LoginMessage.RoleBean();
        roleBean.setUid(1);
        roleBean.setRid(1);
        roleBean.setName("无心道");
        roleBean.setLevel(1);
        roleBean.setExp(1);
        resLogin.getRoleList().add(roleBean);
        session.writeAndFlush(resLogin);
    }

}

package wxdgaming.spring.minigame.logic.module.login.spi;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.threading.ExecutorWith;
import wxdgaming.spring.boot.core.threading.ThreadContext;
import wxdgaming.spring.boot.core.util.JwtUtils;
import wxdgaming.spring.boot.net.ProtoMapping;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.bean.entity.user.Player;
import wxdgaming.spring.minigame.logic.module.login.LoginService;
import wxdgaming.spring.minigame.proto.LoginMessage;

import java.util.List;

/**
 * 登录
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-05 12:05
 **/
@Slf4j
@Component
public class LoginSpi {

    @Value("${sid}")
    int sid;
    final LoginService loginService;

    public LoginSpi(LoginService loginService) {this.loginService = loginService;}

    @ProtoMapping
    @ExecutorWith(queueName = "login")
    public void login(SocketSession session, LoginMessage.ReqLogin reqLogin) {
        Claims payload = JwtUtils.createJwtParser("123456100000000000000000000000000000000L")
                .parseSignedClaims(reqLogin.getToken())
                .getPayload();

        String openId = payload.get("openId", String.class);
        log.info("登录请求 queue={}, sid={}, msg={}", ThreadContext.queueName(), sid, reqLogin.toString());
        List<Player> sdfsdf = loginService.login(openId);
        if (sdfsdf.isEmpty()) {
            Player player = new Player();
            player.setUid(loginService.getPlayerHexId().newId());
            player.setOpenId(openId);
            player.setName("無心道");
            player.setLv(1);
            player.setHp(100);
            player.setMp(100);
            loginService.save(player);
            sdfsdf.add(player);
        }
        LoginMessage.ResLogin resLogin = new LoginMessage.ResLogin();
        resLogin.setServerId(reqLogin.getServerId());
        for (Player player : sdfsdf) {
            LoginMessage.RoleBean roleBean = new LoginMessage.RoleBean();
            roleBean.setUid(player.getUid());
            roleBean.setName(player.getName());
            roleBean.setLevel(player.getLv());
            roleBean.setExp(player.getExp());
            resLogin.getRoleList().add(roleBean);
        }
        session.writeAndFlush(resLogin);
    }

}

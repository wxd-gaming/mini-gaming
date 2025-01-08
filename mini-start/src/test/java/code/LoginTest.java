package code;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.PostConstruct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import wxdgaming.spring.boot.core.CoreScan;
import wxdgaming.spring.boot.core.util.JwtUtils;
import wxdgaming.spring.boot.core.util.StringsUtil;
import wxdgaming.spring.boot.net.NetScan;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.client.SocketClient;
import wxdgaming.spring.minigame.proto.LoginMessage;

import java.io.IOException;

/**
 * login test
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-05 11:06
 **/
@Component
@RunWith(SpringRunner.class)
@SpringBootApplication()
@SpringBootTest(classes = {CoreScan.class, NetScan.class, LoginTest.class})
public class LoginTest {

    @Autowired SocketClient socketClient;
    @Autowired TestSpringReflect springReflect;

    @PostConstruct
    public void init() {
        this.socketClient.scanHandlers(springReflect);
        this.socketClient.scanMessages();
        this.socketClient.connect();
    }

    @Test
    public void login() throws IOException {
        while (true) {
            System.in.read();
            SocketSession socketSession = this.socketClient.idleSession();
            if (socketSession != null) {
                for (int i = 1; i < 5; i++) {
                    String compact = JwtUtils.createJwtBuilder("123456100000000000000000000000000000000L")
                            .claims()
                            .add("openId", String.valueOf(System.currentTimeMillis()))
                            .and()
                            .compact();
                    LoginMessage.ReqLogin pojoBase = new LoginMessage.ReqLogin()
                            .setServerId(i)
                            .setToken(compact)
                            .setParams(new JSONObject().fluentPut("ad", String.valueOf(StringsUtil.getRandomString(32))).toString());
                    socketSession.writeAndFlush(pojoBase);
                    System.out.println("发送登录消息：" + pojoBase.toString());
                }
            }
        }
    }


}

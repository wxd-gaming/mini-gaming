package code;

import jakarta.annotation.PostConstruct;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import wxdgaming.spring.boot.core.CoreScan;
import wxdgaming.spring.boot.net.NetScan;
import wxdgaming.spring.boot.net.ProtoMapper;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.client.SocketClient;
import wxdgaming.spring.minigame.proto.LoginMessage;
import wxdgaming.spring.minigame.proto.PojoScan;

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
                LoginMessage.ReqLogin pojoBase = new LoginMessage.ReqLogin()
                        .setServerId(1)
                        .setToken("123456")
                        .setParams("{}");
                socketSession.writeAndFlush(pojoBase);
                System.out.println("发送登录消息");
            }
        }
    }


}

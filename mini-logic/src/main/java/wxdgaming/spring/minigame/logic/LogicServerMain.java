package wxdgaming.spring.minigame.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.minigame.bean.entity.user.Player;
import wxdgaming.spring.minigame.logic.module.data.DataCenter;
import wxdgaming.spring.minigame.module.service.ILogicServerMain;

/**
 * 逻辑服务主入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:35
 **/
@Slf4j
public class LogicServerMain implements ILogicServerMain {

    @Override public void init(ConfigurableApplicationContext parent, ClassLoader classLoader, JdbcContext jdbcContext) {
        AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
        // 创建子容器
        childContext.setParent(parent);
        childContext.setEnvironment(parent.getEnvironment());
        childContext.setApplicationStartup(parent.getApplicationStartup());
        childContext.setClassLoader(classLoader);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JdbcContext.class, () -> jdbcContext);
        childContext.registerBeanDefinition("jdbcContext", beanDefinitionBuilder.getRawBeanDefinition());
        // 设置扫描类
        childContext.register(LogicScan.class);
        // 刷新子容器以完成初始化
        childContext.refresh();


        DataCenter bean = childContext.getBean(DataCenter.class);
        bean.setJdbcContext(jdbcContext);
        Player player = new Player();
        player.setUid(System.nanoTime());
        player.setOpenId("test");
        player.setNickName("test");
        jdbcContext.save(player);

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

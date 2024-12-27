package wxdgaming.spring.minigame.logic;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import lombok.Getter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertySource;
import wxdgaming.spring.boot.core.SpringReflectContent;
import wxdgaming.spring.boot.core.ann.LogicStart;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.loader.BootClassLoader;
import wxdgaming.spring.boot.loader.ExtendLoader;
import wxdgaming.spring.boot.loader.LogbackExtendLoader;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.rpc.RpcService;
import wxdgaming.spring.minigame.bean.entity.user.Player;
import wxdgaming.spring.minigame.logic.module.cache.DbCacheService;
import wxdgaming.spring.minigame.logic.module.cache.QueueEventService;
import wxdgaming.spring.minigame.logic.module.dispatch.LogicRpcDispatcher;
import wxdgaming.spring.minigame.start.ILogicServerMain;

import java.io.InputStream;

/**
 * 逻辑服务主入口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:35
 **/
@Getter
public class LogicServerMain implements ILogicServerMain {

    int sid;
    AnnotationConfigApplicationContext childContext = null;

    public static void resetLogback(ClassLoader classLoader, String logbackXml, String key, String value) {
        // 加载logback.xml配置文件
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.putProperty(key, value);
        InputStream resourceAsStream = classLoader.getResourceAsStream(logbackXml);
        try {
            configurator.doConfigure(resourceAsStream);
        } catch (JoranException e) {
            throw new RuntimeException(e);
        }
        LoggerFactory.getLogger("root").info("--------------- init end ---------------");
    }

    @Override public void init(ConfigurableApplicationContext parent, BootClassLoader classLoader, ExtendLoader extendLoader, JdbcContext jdbcContext, int sid) {
        // System.setProperty("sid", String.valueOf(sid));

        LogbackExtendLoader.resetLogback(extendLoader, "logback-logic.xml", "sid", String.valueOf(sid));
        this.sid = sid;

        childContext = new AnnotationConfigApplicationContext();
        // 创建子容器
        childContext.setParent(parent);
        childContext.getEnvironment().merge(parent.getEnvironment());

        childContext.getEnvironment().getPropertySources().addLast(new PropertySource<Integer>("sid", sid) {
            @Override public Object getProperty(String name) {
                if (this.getName().equals(name)) return sid;
                return null;
            }

        });
        childContext.setApplicationStartup(parent.getApplicationStartup());
        childContext.setClassLoader(classLoader);
        {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(LogicServerMain.class, () -> this);
            childContext.registerBeanDefinition(LogicServerMain.class.getSimpleName(), beanDefinitionBuilder.getRawBeanDefinition());
        }
        {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(JdbcContext.class, () -> jdbcContext);
            childContext.registerBeanDefinition("jdbcContext", beanDefinitionBuilder.getRawBeanDefinition());
        }
        childContext.setId("s-" + String.valueOf(sid));
        childContext.setDisplayName("s-" + String.valueOf(sid));
        // 设置扫描类
        childContext.register(LogicScan.class);
        // 刷新子容器以完成初始化
        childContext.refresh();
        LogicSpringReflect logicSpringReflect = childContext.getBean(LogicSpringReflect.class);
        SpringReflectContent springReflectContent = logicSpringReflect.content();
        springReflectContent.executorMethod(LogicStart.class);

        DbCacheService dbCacheService = childContext.getBean(DbCacheService.class);

        Player player = new Player();
        player.setUid(System.nanoTime());
        player.setOpenId("test");
        player.setNickName("test");
        dbCacheService.put(player);

        Player tmp = dbCacheService.find(Player.class, player.getUid());
        System.out.println(tmp);


        jdbcContext.delete(Player.class, player.getUid());
        jdbcContext.delete(player);
        jdbcContext.delete(player);

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

    @Override public Object onReceiveRpc(SocketSession session, long rpcId, long targetId, String path, String remoteParams) throws Exception {
        QueueEventService contextBean = childContext.getBean(QueueEventService.class);
        LogicRpcDispatcher logicRpcDispatcher = childContext.getBean(LogicRpcDispatcher.class);
        contextBean.getGlobalEventQueue().submit(() -> {
            try {
                return logicRpcDispatcher.rpcReqSocketAction(session, rpcId, targetId, path, remoteParams);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).whenComplete((ret, throwable) -> {
            logicRpcDispatcher.response(
                    session,
                    rpcId,
                    targetId,
                    throwable == null ? 1 : 99,
                    throwable == null ? String.valueOf(ret) : throwable.getMessage()
            );
        });
        return RpcService.IGNORE;
    }

    @Override
    public void setCfg() {

    }

}

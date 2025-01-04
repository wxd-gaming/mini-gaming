package wxdgaming.spring.minigame.start;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import wxdgaming.spring.boot.core.CoreScan;
import wxdgaming.spring.boot.core.Throw;
import wxdgaming.spring.boot.data.DataScan;
import wxdgaming.spring.boot.data.batis.DataJdbcScan;
import wxdgaming.spring.boot.data.batis.DruidSourceConfig;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.data.batis.JdbcHelper;
import wxdgaming.spring.boot.data.redis.DataRedisScan;
import wxdgaming.spring.boot.loader.BootClassLoader;
import wxdgaming.spring.boot.loader.JavaCoderCompile;
import wxdgaming.spring.boot.loader.LogbackExtendLoader;
import wxdgaming.spring.boot.net.NetScan;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.net.client.TcpSocketClient;
import wxdgaming.spring.boot.rpc.RpcDispatcher;
import wxdgaming.spring.boot.rpc.RpcScan;
import wxdgaming.spring.boot.web.WebScan;
import wxdgaming.spring.boot.webclient.WebClientScan;
import wxdgaming.spring.minigame.bean.MiniGameBeanScan;
import wxdgaming.spring.minigame.start.module.data.DataCenter;
import wxdgaming.spring.minigame.start.module.rpc.MiniGameRpcDispatcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小游戏启动器
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-18 13:50
 **/
@Slf4j
@SpringBootApplication(
        scanBasePackageClasses = {
                CoreScan.class,
                DataScan.class,
                DataJdbcScan.class,
                DataRedisScan.class,
                NetScan.class,
                RpcScan.class,
                WebScan.class,
                WebClientScan.class,
                MiniGameStart.class,
        },
        exclude = {
                DataSourceAutoConfiguration.class,
                MongoAutoConfiguration.class
        }
)
public class MiniGameStart {


    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(MiniGameStart.class, args);
        run.getBean(MiniGameSpringReflect.class).content().executorAppStartMethod();
        DataCenter dataCenter = run.getBean(DataCenter.class);
        loadServer(run, dataCenter, 1);
        loadServer(run, dataCenter, 2);
        loadServer(run, dataCenter, 3);
        // loadServer(run);


        RpcDispatcher rpcDispatcher = run.getBean(MiniGameRpcDispatcher.class);
        try {
            SocketSession session = run.getBean(TcpSocketClient.class).idleSession();
            for (int i = 0; i <= 3; i++) {

                rpcDispatcher
                        .request(session, 1, "gm/logic-rpc", new JSONObject().fluentPut("type", 1 + i).toString())
                        .subscribe(str -> log.debug("1服 {}", str));

                rpcDispatcher
                        .request(session, 2, "gm/logic-rpc", new JSONObject().fluentPut("type", 1 + i).toString())
                        .subscribe(str -> log.debug("2服 {}", str));


                rpcDispatcher
                        .request(session, 3, "gm/logic-rpc", new JSONObject().fluentPut("type", 1 + i).toString())
                        .subscribe(str -> log.debug("3服 {}", str));
            }


            // rpcDispatcher
            //         .request(session, 30, "gm/logic-rpc", new JSONObject().fluentPut("type", 1).toString())
            //         .subscribe(str -> log.debug("30服 {}", str));

        } catch (Exception e) {
            log.error("{}", Throw.ofString(e, false));
        }
    }

    public static List<String> javaClassPath() {
        return new ArrayList<>(List.of(System.getProperty("java.class.path").split(File.pathSeparator)));
    }

    public static void loadServer(ConfigurableApplicationContext parent, DataCenter dataCenter, int sid) throws Exception {

        JavaCoderCompile javaCoderCompile = new JavaCoderCompile()
                .parentClassLoader(MiniGameStart.class.getClassLoader());
        //
        javaCoderCompile.compilerJava("mini-logic/src/main/java")
                .outPutFile("target/scripts", true);

        BootClassLoader bootClassLoader = new BootClassLoader(MiniGameStart.class.getClassLoader(),
                "target/scripts"
        );

        LogbackExtendLoader extendLoader = new LogbackExtendLoader(bootClassLoader);
        extendLoader.addURLs(
                "mini-logic/src/main/resources"
        );
        // extendLoader.addExtendPackages("wxdgaming.spring.minigame.bean");
        bootClassLoader.setExtendLoader(extendLoader);

        JdbcHelper jdbcHelper = parent.getBean(JdbcHelper.class);
        DruidSourceConfig copy = jdbcHelper.getConfig().copy("s" + sid);
        copy.setShowSql(true);
        copy.setPackageNames(new String[]{MiniGameBeanScan.class.getPackageName()});
        copy.createDatabase();
        DruidDataSource dataSource = copy.toDataSource();
        EntityManager entityManager = copy.entityManager(dataSource, Map.of());
        JdbcContext jdbcContext = new JdbcContext(dataSource, entityManager);


        Class<?> aClass = bootClassLoader.loadClass("wxdgaming.spring.minigame.logic.LogicServerMain");

        ILogicServerMain iLogicServerMain = (ILogicServerMain) aClass.getDeclaredConstructor().newInstance();
        iLogicServerMain.init(parent, bootClassLoader, extendLoader, jdbcContext, sid);

        dataCenter.getServerMap().put(sid, iLogicServerMain);

    }

}

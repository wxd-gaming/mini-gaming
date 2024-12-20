package wxdgaming.spring.minigame.start;

import com.alibaba.druid.pool.DruidDataSource;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import wxdgaming.spring.boot.core.CoreScan;
import wxdgaming.spring.boot.core.ReflectContext;
import wxdgaming.spring.boot.core.loader.ClassDirLoader;
import wxdgaming.spring.boot.core.loader.JavaCoderCompile;
import wxdgaming.spring.boot.data.DataScan;
import wxdgaming.spring.boot.data.batis.DataJdbcScan;
import wxdgaming.spring.boot.data.batis.DruidSourceConfig;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.boot.data.batis.JdbcHelper;
import wxdgaming.spring.boot.data.redis.DataRedisScan;
import wxdgaming.spring.boot.net.NetScan;
import wxdgaming.spring.boot.rpc.RpcScan;
import wxdgaming.spring.boot.web.WebScan;
import wxdgaming.spring.boot.webclient.WebClientScan;
import wxdgaming.spring.minigame.bean.entity.user.Player;
import wxdgaming.spring.minigame.start.module.data.DataCenter;

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
    }

    public static void loadServer(ConfigurableApplicationContext run, DataCenter dataCenter, int sid) throws Exception {
        ClassDirLoader classLoader = new JavaCoderCompile()
                .parentClassLoader(MiniGameStart.class.getClassLoader())
                .compilerJava("mini-logic/src/main/java")
                .classLoader("target/scripts");

        classLoader.addURL(
                "mini-logic/src/main/resources"
        );
        JdbcHelper jdbcHelper = run.getBean(JdbcHelper.class);
        DruidSourceConfig copy = jdbcHelper.getDb().copy("s" + sid);
        copy.setShowSql(true);
        // copy.setDialect(org.hibernate.dialect.H2Dialect.class.getName());
        copy.setPackageNames(new String[]{EntityScan.class.getPackageName(), Player.class.getPackageName()});
        copy.createDatabase();
        DruidDataSource dataSource = copy.toDataSource();
        EntityManager entityManager = copy.entityManagerFactory(dataSource, Map.of());
        JdbcContext jdbcContext = new JdbcContext(dataSource, entityManager);

        ReflectContext.Content<ILogicServerMain> iLogicServerMainContent = new ReflectContext(classLoader.getLoadClassMap().values())
                .withSuper(ILogicServerMain.class)
                .findFirst().get();
        ILogicServerMain iLogicServerMain = iLogicServerMainContent.getCls().getDeclaredConstructor().newInstance();
        iLogicServerMain.init(run, classLoader, jdbcContext);

        dataCenter.getServerMap().put(sid, iLogicServerMain);

    }

}

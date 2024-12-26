package code;

import com.alibaba.druid.pool.DruidDataSource;
import jakarta.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import wxdgaming.spring.boot.core.format.HexId;
import wxdgaming.spring.boot.data.MyTestEntity;
import wxdgaming.spring.boot.data.batis.DruidSourceConfig;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.minigame.bean.MiniGameBeanScan;
import wxdgaming.spring.minigame.bean.entity.user.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class H2Test {

    HexId hexId = new HexId(1);
    JdbcContext jdbcContext;

    void createFileDriver(String url) {
        DruidSourceConfig dataSourceConfig = new DruidSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("test");
        dataSourceConfig.setDriverClassName("org.h2.Driver");
        dataSourceConfig.setPackageNames(new String[]{MiniGameBeanScan.class.getPackageName()});
        dataSourceConfig.setBatchInsert(true);
        dataSourceConfig.setDialect(org.hibernate.dialect.H2Dialect.class.getName());

        DruidDataSource dataSource = dataSourceConfig.toDataSource();
        EntityManager entityManager = dataSourceConfig.entityManagerFactory(dataSource, Map.of());

        jdbcContext = new JdbcContext(dataSource, entityManager);
    }

    @Before
    public void insert2File() {
        createFileDriver("jdbc:h2:file:./target/test");
    }

    @Test
    public void insert2Mem() {
        createFileDriver("jdbc:h2:mem:test");
    }


    @Test
    public void clearTable() {
        long start = System.nanoTime();
        int deleteFromMyTestEntity = jdbcContext.executeUpdate("delete from MY_TEST_ENTITY");
        System.out.println(deleteFromMyTestEntity + ", " + (((System.nanoTime() - start) / 10000 / 100f) + " ms"));
    }

    @Test
    public void count() {
        for (int k = 0; k < 3; k++) {
            count0();
        }
    }

    void count0() {
        long start = System.nanoTime();
        long count = jdbcContext.count(Player.class);
        System.out.println("查询：" + count + ", " + (((System.nanoTime() - start) / 10000 / 100f) + " ms"));
    }

    @Test
    public void insert() {
        IntStream.range(1, 11)
                .forEach(tk -> {
                    ArrayList<Player> logs = new ArrayList<>(10000);
                    for (int i = 0; i < 10000; i++) {
                        Player slog = new Player();
                        slog.setUid(hexId.newId());
                        slog.setNickName(String.valueOf(slog.getUid()));
                        logs.add(slog);
                    }
                    long start = System.nanoTime();
                    jdbcContext.batchInsert(logs);
                    System.out.println("写入：" + logs.size() + " 条数据 耗时：" + ((System.nanoTime() - start) / 10000 / 100f) + " ms");
                });
        count0();
        count0();
    }

    @Test
    public void insertParallel() {
        IntStream.range(1, 101)
                .parallel()
                .forEach(tk -> {
                    ArrayList<Player> logs = new ArrayList<>(10000);
                    for (int i = 0; i < 10000; i++) {
                        Player slog = new Player();
                        slog.setUid(hexId.newId());
                        slog.setNickName(String.valueOf(slog.getUid()));
                        logs.add(slog);
                    }
                    long start = System.nanoTime();
                    jdbcContext.batchInsert(logs);
                    System.out.println("写入：" + logs.size() + " 条数据 耗时：" + ((System.nanoTime() - start) / 10000 / 100f) + " ms");
                });
        count0();
        count0();
    }

    @Test
    public void insertMerge() {
        for (int k = 0; k < 2; k++) {

            ArrayList<Player> logs = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                Player slog = new Player();
                slog.setUid(i + 1L);
                slog.setNickName(String.valueOf(slog.getUid()));
                logs.add(slog);
            }
            long start = System.nanoTime();
            jdbcContext.batchSave(logs);
            System.out.println("写入：" + logs.size() + " 条数据 耗时：" + ((System.nanoTime() - start) / 10000 / 100f) + " ms");
        }
        List<MyTestEntity> all = jdbcContext.findAll(MyTestEntity.class);
        for (MyTestEntity myTestEntity : all) {
            System.out.println(myTestEntity.toJson());
        }
    }

    @Test
    public void select() {
        long start = System.nanoTime();
        long count = jdbcContext.findAll2Stream("from Player as m where m.uid > ?1", Player.class, 1L)
                .peek(myTestEntity -> System.out.println(myTestEntity.toJson()))
                .count();
        System.out.println("查询：" + count + " 条数据 耗时：" + ((System.nanoTime() - start) / 10000 / 100f) + " ms");
    }


}

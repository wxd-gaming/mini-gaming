package wxdgaming.spring.minigame.bean.cache;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.data.EntityUID;
import wxdgaming.spring.boot.data.batis.JdbcCache;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.minigame.bean.entity.global.GlobalBase;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库缓存
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-25 14:00
 **/
@Component
public class DbCacheService implements InitPrint {

    @Getter static DbCacheService ins = null;

    private final ConcurrentHashMap<Class<? extends EntityUID<?>>, JdbcCache<?, ?>> cache = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<Long, GlobalBase> globalDataMap = new ConcurrentHashMap<>();

    JdbcContext jdbcContext;

    @Autowired
    public DbCacheService(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
        DbCacheService.ins = this;
    }

    public <K, V extends EntityUID<K>> JdbcCache<K, V> get(Class<V> clazz) {
        return (JdbcCache<K, V>) cache.computeIfAbsent(clazz, k -> new JdbcCache<>(jdbcContext, clazz, 30));
    }

    public <K, V extends EntityUID<K>> V find(Class<V> clazz, K uid) {
        return get(clazz).get(uid);
    }

    public <K, V extends EntityUID<K>> void put(V v) {
        get(v.getClass()).put(v.getUid(), v);
    }

}

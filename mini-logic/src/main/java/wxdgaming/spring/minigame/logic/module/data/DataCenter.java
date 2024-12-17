package wxdgaming.spring.minigame.logic.module.data;


import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.minigame.bean.entity.global.GlobalBase;
import wxdgaming.spring.minigame.bean.entity.user.Player;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 区服数据
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:44
 **/
@Service
public class DataCenter implements InitPrint {

    private final ConcurrentHashMap<Long, GlobalBase> globalDataMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Player> playerMap = new ConcurrentHashMap<>();

    @Setter JdbcContext jdbcContext;
    final RedisTemplate<String, String> redisTemplate;

    public DataCenter(RedisTemplate<String, String> redisTemplate, JdbcContext jdbcContext) {
        this.redisTemplate = redisTemplate;


    }
}

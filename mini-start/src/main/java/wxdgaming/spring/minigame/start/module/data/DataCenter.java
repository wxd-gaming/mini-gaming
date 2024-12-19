package wxdgaming.spring.minigame.start.module.data;

import lombok.Getter;
import org.springframework.stereotype.Service;
import wxdgaming.spring.minigame.start.ILogicServerMain;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据中心
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-18 17:34
 **/
@Getter
@Service
public class DataCenter {

    private final ConcurrentHashMap<Integer, ILogicServerMain> serverMap = new ConcurrentHashMap<>();

}

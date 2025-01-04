package wxdgaming.spring.minigame.logic.module.data;


import lombok.Getter;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.InitPrint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 区服数据
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:44
 **/
@Getter
@Service
public class DataCenter implements InitPrint {

    /** session id 和 player id 映射关系 */
    private final ConcurrentSkipListMap<Long, Long> sessionId2PlayerIdMap = new ConcurrentSkipListMap<>();

}

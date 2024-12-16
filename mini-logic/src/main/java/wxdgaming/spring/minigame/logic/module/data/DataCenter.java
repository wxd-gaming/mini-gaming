package wxdgaming.spring.minigame.logic.module.data;


import wxdgaming.spring.minigame.logic.bean.entity.global.GlobalBase;
import wxdgaming.spring.minigame.logic.bean.entity.user.Player;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 区服数据
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:44
 **/
public class DataCenter {

    private final ConcurrentHashMap<Long, GlobalBase> globalDataMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Player> playerMap = new ConcurrentHashMap<>();

}

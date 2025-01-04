package wxdgaming.spring.minigame.bean;

import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.data.EntityBase;

/**
 * 地图对象
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-04 19:42
 **/
@Getter
@Setter
public class MapObject extends EntityBase<Long> {

    private transient int mapId;
    private transient int mapCfgId;
    private transient int mapLineId;

}

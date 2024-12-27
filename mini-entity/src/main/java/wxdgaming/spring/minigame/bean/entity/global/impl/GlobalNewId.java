package wxdgaming.spring.minigame.bean.entity.global.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import wxdgaming.spring.minigame.bean.entity.global.GlobalBase;

/**
 * 全局id生成存储器
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:07
 **/
@Getter
@Setter
@Accessors(chain = true)
public class GlobalNewId extends GlobalBase {

    private long uid;
    private long rid;
    private long gid;

    @Override public int getType() {
        return 1;
    }

}

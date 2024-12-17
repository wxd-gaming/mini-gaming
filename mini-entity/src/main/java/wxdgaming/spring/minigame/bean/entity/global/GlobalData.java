package wxdgaming.spring.minigame.bean.entity.global;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.data.EntityBase;

/**
 * 全局数据
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 16:06
 **/
@Getter
@Setter
@Entity
public class GlobalData extends EntityBase<Long> {
    private int type;
    private int sid;
    private byte[] data;
}

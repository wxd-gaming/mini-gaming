package wxdgaming.spring.minigame.bean.entity.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.data.EntityBase;

/**
 * 玩家摘要信息
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:58
 **/
@Getter
@Setter
@Entity
public class PlayerSummary extends EntityBase<Long> {

    private String openId;

    // public EventQueue eventQueue() {
    //     return QueueEventService.getUserEventQueue(getUid());
    // }

}

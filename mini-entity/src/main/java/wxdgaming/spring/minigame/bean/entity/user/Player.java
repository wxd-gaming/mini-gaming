package wxdgaming.spring.minigame.bean.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.minigame.bean.MapObject;

/**
 * 角色
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:56
 **/
@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(columnList = "openId"),
})
public class Player extends MapObject {


    private String openId;

    // public EventQueue eventQueue() {
    //     return QueueEventService.getUserEventQueue(getUid());
    // }
    //
    // public PlayerSummary playerSummary() {
    //     return DbCacheService.getIns().find(PlayerSummary.class, getUid());
    // }
    //
    // public MailPack mailPack() {
    //     return DbCacheService.getIns().find(MailPack.class, getUid());
    // }

}

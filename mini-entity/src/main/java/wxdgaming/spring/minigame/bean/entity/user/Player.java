package wxdgaming.spring.minigame.bean.entity.user;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.data.EntityBase;

/**
 * 角色
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 15:56
 **/
@Getter
@Setter
@Entity
public class Player extends EntityBase<Long> {

    private String openId;
    private String nickName;

}
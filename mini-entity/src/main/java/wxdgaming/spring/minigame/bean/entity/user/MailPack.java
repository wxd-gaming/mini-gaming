package wxdgaming.spring.minigame.bean.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.data.EntityBase;
import wxdgaming.spring.boot.data.converter.ObjectToJsonStringConverter;
import wxdgaming.spring.minigame.bean.entity.mail.UserMail;

import java.util.HashMap;

/**
 * 邮件容器
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-25 16:42
 **/
@Getter
@Setter
@Entity
public class MailPack extends EntityBase<Long> {

    @Column(columnDefinition = "json")
    @Convert(converter = ObjectToJsonStringConverter.class)
    private HashMap<Long, UserMail> mailMap = new HashMap<>();

}

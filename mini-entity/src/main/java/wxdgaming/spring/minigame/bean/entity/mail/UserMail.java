package wxdgaming.spring.minigame.bean.entity.mail;

import lombok.Getter;
import lombok.Setter;
import wxdgaming.spring.boot.core.lang.ObjectBase;

import java.util.ArrayList;

/**
 * 用户邮件
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-25 16:43
 **/
@Getter
@Setter
public class UserMail extends ObjectBase {

    private long uid;
    private long createdTime;
    private long sender;
    private String title;
    private String content;
    private ArrayList<String> params = new ArrayList<>();

}

package wxdgaming.spring.minigame.logic.module.login;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.core.format.HexId;
import wxdgaming.spring.boot.data.batis.JdbcContext;
import wxdgaming.spring.minigame.bean.entity.user.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录服务
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2025-01-07 20:12
 **/
@Getter
@Service
public class LoginService implements InitPrint {

    final HexId playerHexId;
    final JdbcContext jdbcContext;

    public LoginService(@Value("${sid}") int sid, JdbcContext jdbcContext) {
        playerHexId = new HexId(sid);
        this.jdbcContext = jdbcContext;
    }

    public List<Player> login(String openId) {
        List<Player> list = jdbcContext.findAll2Stream("from Player as p where p.openId=?1", Player.class, openId)
                .collect(Collectors.toList());
        return list;
    }

    public void save(Player player) {
        jdbcContext.save(player);
    }

}

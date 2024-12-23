package wxdgaming.spring.minigame.logic.module.gm;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.net.SocketSession;
import wxdgaming.spring.boot.rpc.RPC;

/**
 * gm命令接口
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-21 10:42
 **/
@Slf4j
@Service
public class GMSpi implements InitPrint {

    @Value("${sid:}")
    int sid;

    @RPC("logic-rpc")
    public String logicRpc(SocketSession session, JSONObject jsonObject) {
        log.info("logic-rpc 区服：{}", sid);
        return "logic-rpc-ok";
    }

}

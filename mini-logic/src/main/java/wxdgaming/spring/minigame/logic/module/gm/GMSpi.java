package wxdgaming.spring.minigame.logic.module.gm;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import wxdgaming.spring.boot.core.InitPrint;
import wxdgaming.spring.boot.core.threading.ExecutorWith;
import wxdgaming.spring.boot.core.threading.ThreadContext;
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
@RequestMapping("gm")
public class GMSpi implements InitPrint {

    int sid;

    public GMSpi(@Value("${sid}") int sid) {
        this.sid = sid;
    }

    @RPC("/logic-rpc")
    @ExecutorWith(queueName = "gm")
    public String logicRpc(SocketSession session, JSONObject jsonObject) {
        log.info("logic-rpc queue={} 区服：{} {}", ThreadContext.queueName(), sid, jsonObject.toString());
        return "logic-rpc-ok";
    }

    @RPC()
    @ExecutorWith(queueName = "gm")
    public String kickRole(SocketSession session, JSONObject jsonObject) {
        log.info("logic-rpc queue={} 区服：{} {}", ThreadContext.queueName(), sid, jsonObject.toString());
        return "logic-rpc-ok";
    }

}

package code;

import com.alibaba.fastjson.TypeReference;
import org.junit.Test;
import wxdgaming.spring.boot.core.json.FastJsonUtil;

/**
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-27 20:18
 **/
public class Json {

    @Test
    public void t1(){
        String json="{\"@type\":\"wxdgaming.spring.minigame.bean.entity.global.impl.GlobalNewId\",\"gid\":0L,\"rid\":0L,\"uid\":0L}";
        Object parse = FastJsonUtil.parse(json, new TypeReference<>() {});
        System.out.println(parse);
    }

}

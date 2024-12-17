package wxdgaming.spring.minigame.logic;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 扫描类
 *
 * @author: wxd-gaming(無心道, 15388152619)
 * @version: 2024-12-16 17:11
 **/
@EntityScan
@EnableAsync
@EnableScheduling
@ComponentScan(basePackageClasses = {
        LogicScan.class,
})
public class LogicScan {
}

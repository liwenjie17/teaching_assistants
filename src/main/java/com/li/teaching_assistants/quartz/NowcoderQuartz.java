package com.li.teaching_assistants.quartz;

import com.li.teaching_assistants.runnable.NowcoderDailyRunnable;
import com.li.teaching_assistants.runnable.PTADailyRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
@Slf4j
public class NowcoderQuartz {
    @Scheduled(cron = "0 0 3 * * ?")
    public void NowcQuartz(){
        log.info("开始扫描更新牛客用户的刷题记录");
        new Thread(new NowcoderDailyRunnable()).start();
        log.info("更新完成");
    }
}

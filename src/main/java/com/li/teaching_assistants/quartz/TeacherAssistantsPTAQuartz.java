package com.li.teaching_assistants.quartz;


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
public class TeacherAssistantsPTAQuartz {

    @Scheduled(cron = "0 0 3 * * ?")
    public void PTAQuartz(){
        log.info("开始扫描更新PTA用户的刷题记录");
        new Thread(new PTADailyRunnable()).start();
        log.info("更新完成");
    }
}

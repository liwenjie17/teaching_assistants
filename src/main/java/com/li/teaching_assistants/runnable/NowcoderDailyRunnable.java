package com.li.teaching_assistants.runnable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@NoArgsConstructor
@Slf4j
public class NowcoderDailyRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String pythonName = "Teacher_Assistants_NK.py";


    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName;
        log.info("执行脚本:"+common);
        try {
            log.info("开始执行更新牛客用户的刷题记录脚本");
            Process process = Runtime.getRuntime().exec(common);
            log.info("执行更新牛客用户的刷题记录脚本完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

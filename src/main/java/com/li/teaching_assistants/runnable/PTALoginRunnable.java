package com.li.teaching_assistants.runnable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Data
@NoArgsConstructor
@Slf4j
public class PTALoginRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String user_email;
    private String pta_account;
    private String pta_password;
    private String pythonName = "Teacher_Assistants_PTA.py";

    public PTALoginRunnable(String user_email, String pta_account, String pta_password) {
        this.user_email = user_email;
        this.pta_account = pta_account;
        this.pta_password = pta_password;
    }

    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName + " " + user_email + " " + pta_account + " " + pta_password;
        log.info("执行脚本:"+common);
        try {
            Process process = Runtime.getRuntime().exec(common);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

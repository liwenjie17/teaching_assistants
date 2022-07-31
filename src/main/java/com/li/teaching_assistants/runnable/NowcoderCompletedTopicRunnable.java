package com.li.teaching_assistants.runnable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Data
@NoArgsConstructor
@Slf4j
public class NowcoderCompletedTopicRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String user_email;
    private String nowcoder_account;
    private String nowcoder_password;
    private String pythonName = "Teacher_Assistants_NK.py";

    public NowcoderCompletedTopicRunnable(String user_email, String nowcoder_account, String nowcoder_password) {
        this.user_email = user_email;
        this.nowcoder_account = nowcoder_account;
        this.nowcoder_password = nowcoder_password;
    }

    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName + " " + user_email + " " + nowcoder_account + " " + nowcoder_password;
        log.info("执行脚本:"+common);
        try {
            Process process = Runtime.getRuntime().exec(common);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

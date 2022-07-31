package com.li.teaching_assistants.runnable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
@Data
@NoArgsConstructor
@Slf4j
public class TeaPTAGroupsSetRunnable implements Runnable {
    private String pythonDir = "C:/Teaching_Assistants_Spider/";
    private String user_pta_id;
    private String group_id;
    private String pythonName = "Teacher_Assistants_Tea_PTA_Groups_Set.py";

    public TeaPTAGroupsSetRunnable(String user_pta_id, String group_id) {
        this.user_pta_id = user_pta_id;
        this.group_id = group_id;
    }

    @Override
    public void run() {
        String common = "python " + pythonDir + pythonName + " " + user_pta_id + " " + group_id ;
        log.info("执行脚本:"+common);
        try {
            Process process = Runtime.getRuntime().exec(common);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

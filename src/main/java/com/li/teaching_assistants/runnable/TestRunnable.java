package com.li.teaching_assistants.runnable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestRunnable implements Runnable {
    private String dir = "C:/Teaching_Assistants_Spider/";

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(dir);
        }
    }
}

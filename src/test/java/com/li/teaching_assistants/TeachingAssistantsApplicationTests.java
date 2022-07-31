package com.li.teaching_assistants;

import com.alibaba.fastjson.JSONArray;
import com.li.teaching_assistants.pojo.TeacherCollectedRecord;
import com.li.teaching_assistants.service.DataService;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class TeachingAssistantsApplicationTests {
    @Resource
    private DataService dataService;


    @Test
    void contextLoads() {

        String email = "1145515132@qq.com";
        JSONArray jsonArray = new JSONArray();

        Map<String,Object> id = new HashMap<>();
        id.put("id","1");
        jsonArray.add(id);

        Map<String,Object> label = new HashMap<>();
        label.put("label","欢迎记录");
        jsonArray.add(label);

        Map<String,Object> level = new HashMap<>();
        level.put("level",1);
        jsonArray.add(level);

        Map<String,Object> isEdit = new HashMap<>();
        isEdit.put("isEdit",false);
        jsonArray.add(isEdit);

        Map<String,Object> valueEdit = new HashMap<>();
        valueEdit.put("valueEdit",false);
        jsonArray.add(valueEdit);

        String unescapeJava = StringEscapeUtils.unescapeJava("'### 题目内容:\n在此输入题目内容……\n\n### 输入格式:\n```in\n12-34\n```\n### 输出格式:\n```in\n12-34\n```\n## 题目思路:\n在此写题目思路\n\n### 题目代码:\n```c++\n#include<stdio.h>\r\nint main()\r\n{\r\n\tprintf(\"Hello World!\\n\");\r\n \treturn 0;\r\n}\n```\n#### 如果还有题目思路二 可以复制前面的粘贴到下面'");

        Map<String,Object> value = new HashMap<>();
        value.put("value","'### 题目内容:\n在此输入题目内容……\n\n### 输入格式:\n```in\n12-34\n```\n### 输出格式:\n```in\n12-34\n```\n## 题目思路:\n在此写题目思路\n\n### 题目代码:\n```c++\n#include<stdio.h>\r\nint main()\r\n{\r\n\tprintf(\"Hello World!\\n\");\r\n \treturn 0;\r\n}\n```\n#### 如果还有题目思路二 可以复制前面的粘贴到下面'");
        jsonArray.add(value);

        String InitRecords = jsonArray.toJSONString();
        //String unescapeJava = StringEscapeUtils.unescapeJava("'### 题目内容:\n在此输入题目内容……\n\n### 输入格式:\n```in\n12-34\n```\n### 输出格式:\n```in\n12-34\n```\n## 题目思路:\n在此写题目思路\n\n### 题目代码:\n```c++\n#include<stdio.h>\r\nint main()\r\n{\r\n\tprintf(\"Hello World!\\n\");\r\n \treturn 0;\r\n}\n```\n#### 如果还有题目思路二 可以复制前面的粘贴到下面'");

        String NewInitRecords = InitRecords.replaceAll("\"", "\"");

        System.out.println("InitRecords=="+InitRecords);
        System.out.println("NewInitRecords=="+NewInitRecords);
        System.out.println("unescapeJava=="+unescapeJava);

        TeacherCollectedRecord record = new TeacherCollectedRecord();
        record.setId(email+"Coll.");
        record.setEmail(email);
        record.setRecords(InitRecords);
        dataService.updateRecords(record);
    }

}

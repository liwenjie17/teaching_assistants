package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PTA_Ques  implements Serializable {
    private String ques_id;
    private String title;
    private String setId;
    private String setName;
    private boolean isCollected;
}

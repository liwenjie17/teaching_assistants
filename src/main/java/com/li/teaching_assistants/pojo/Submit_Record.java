package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submit_Record implements Serializable {
    private String recordId;
    private String userPtaId;
    private String quesId;
    private String setId;
    private String submitTime;
    private int submitState;

}

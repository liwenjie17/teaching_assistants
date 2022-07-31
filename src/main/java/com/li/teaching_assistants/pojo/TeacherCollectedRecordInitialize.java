package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCollectedRecordInitialize {
    private String id;
    private String label;
    private int level;
    private boolean isEdit;
    private boolean valueEdit;
    private String value;
}

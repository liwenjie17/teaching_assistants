package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class User_QuesBank_CombineKey implements Serializable {
    private String QuestionId;
    private String UserEmail;
}

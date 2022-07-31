package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class User_Classes_CombineKey implements Serializable {
    private String ClassId;
    private String UserEmail;
}

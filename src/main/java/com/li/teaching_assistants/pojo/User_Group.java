package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_Group implements Serializable {
    private String userEmail;
    private String userPtaId;
    private String groupsId;
    private String groupName;
}

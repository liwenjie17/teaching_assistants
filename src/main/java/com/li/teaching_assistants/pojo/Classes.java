package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@Table(name = "classes")
public class Classes {
    @Id
    @Column(name = "class_id")
    private int classId;
    @Column(name = "class_name")
    private String className;
    @Column(name = "class_owner")
    private String classOwner;
    @Column(name = "groups_id")
    private String groupsId;
    private List<User> users;
    private List<User> unBindUsers;
}

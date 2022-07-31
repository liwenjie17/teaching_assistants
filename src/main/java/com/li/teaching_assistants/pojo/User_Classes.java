package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_classes")
@IdClass(User_Classes_CombineKey.class)
public class User_Classes {
    @Id
    @Column(name = "class_id")
    private String ClassId;
    @Id
    @Column(name = "user_email")
    private String UserEmail;
}

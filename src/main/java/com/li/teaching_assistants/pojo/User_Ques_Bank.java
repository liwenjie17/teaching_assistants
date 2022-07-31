package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_question_bank")
@IdClass(User_QuesBank_CombineKey.class)
public class User_Ques_Bank {
    @Id
    @Column(name = "question_id")
    private String QuestionId;
    @Id
    @Column(name = "user_email")
    private String UserEmail;
}

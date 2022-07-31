package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookmarked_submission_records")
@IdClass(Submission_Records_CombineKey.class)
public class Submission_Records implements Serializable {
    @Id
    @Column(name = "ques_id")
    private String quesId;
    @Id
    @Column(name = "user_id")
    private String userId;
    private String quesTitle;
    private String userEmail;
    @Column(name = "ques_content")
    private String quesContent;
    @Column(name = "ques_code")
    private String quesCode;
    @Column(name = "user_notes")
    private String userNotes;
}

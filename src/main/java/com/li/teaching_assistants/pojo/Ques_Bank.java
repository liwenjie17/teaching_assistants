package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "question_bank")
public class Ques_Bank implements Serializable {
    @Id
    @Column(name = "ques_id")
    private String quesId;
    @Column(name = "ques_title")
    private String quesTitle;
    @Column(name = "ques_integral")
    private int quesIntegral;
    @Column(name = "ques_label")
    private String quesLabel;
    @Column(name = "ques_source")
    private String quesSource;
}

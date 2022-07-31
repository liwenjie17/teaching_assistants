package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nowcoder_completed_submit")
public class NowCoder_Ques implements Serializable {
    @Id
    @Column(name = "nowcoder_submit_id")
    private String submitId;
    @Column(name = "nowcoder_ques_uuid")
    private String quesId;
    @Column(name = "nowcoder_ques_no")
    private String quesNo;
    @Column(name = "nowcoder_ques_title")
    private String quesTitle;
    @Column(name = "nowcoder_ques_label")
    private String quesLabel;
    @Column(name = "nowcoder_ques_datetime")
    private String quesSubTime;
    private boolean isCollected;
}

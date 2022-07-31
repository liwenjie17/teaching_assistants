package com.li.teaching_assistants.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "questions_set")
public class Question_Set implements Serializable {
    @Id
    @Column(name = "set_id")
    private String setId;
    @Column(name = "set_title")
    private String setTitle;
    @Column(name = "set_total_score")
    private float setTotalScore;
    @Column(name = "set_ques_number")
    private int setQuesNumber;
    @Column(name = "set_start_time")
    private String setStartTime;
    @Column(name = "set_deadline")
    private String setDeadline;
    @Column(name = "set_creator")
    private String setCreator;
    @Column(name = "set_label")
    private String setLabel;
    @Column(name = "set_pass_rate_max")
    private float setPassRateMax;
    @Column(name = "set_pass_rate_min")
    private float setPassRateMin;

    private String startTime;
    private String endTime;
}

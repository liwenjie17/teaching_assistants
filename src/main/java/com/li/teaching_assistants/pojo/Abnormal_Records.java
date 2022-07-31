package com.li.teaching_assistants.pojo;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "abnormal_records")
public class Abnormal_Records implements Serializable {
    @Id
    @Column(name = "abnormal_id")
    private String abnormalId;
    @Column(name = "abnormal_title")
    private String abnormalTitle;
    @Column(name = "abnormal_content")
    private String abnormalContent;
    @Column(name = "abnormal_time")
    private String abnormalTime;
    @Column(name = "abnormal_stu")
    private String abnormalStu;
    @Column(name = "abnormal_ques")
    private String abnormalQues;
    @Column(name = "abnormal_code")
    private String abnormalCode;
}

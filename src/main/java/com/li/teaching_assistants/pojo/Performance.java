package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "performance")
@IdClass(Performance_CombineKey.class)
public class Performance {
    @Id
    @Column(name = "set_id")
    private String setId;
    @Id
    @Column(name = "user_pta_id")
    private String userId;
    @Column(name = "completed_number")
    private String completedNumber;
    @Column(name = "score")
    private float score;
    private String  platform;
    private int rank;
    private String user_true_name;
    private float percentageComplete;
    private String passingRate;
}

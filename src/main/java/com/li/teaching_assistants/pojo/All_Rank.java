package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class All_Rank implements Serializable {
    private String email;
    private int rank;
    private String userName;
    private String score;
    private String icon;
    private String title;       //称号
    private String rankImage;
    private int  completeNumber;
    private int ptaNumber;
    private int nowcoderNumber;
    private String ptaScore;
    private String nowcoderScore;
    private int current;
    private int nextLevel;
}

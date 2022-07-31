package com.li.teaching_assistants.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable{
    @Id
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_status")
    private int userStatus;
    @Column(name = "user_image")
    private String userImage;
    @Column(name = "user_pta_id")
    private String userPtaId;
    @Column(name = "user_pta_account")
    private String userPtaAccount;
    @Column(name = "user_pta_password")
    private String userPtaPassword;
    @Column(name = "user_nowcoder_id")
    private String userNowcoderId;
    @Column(name = "user_nowcoder_account")
    private String userNowcoderAccount;
    @Column(name = "user_nowcoder_password")
    private String userNowcoderPassword;
    @Column(name = "user_chaoxing_account")
    private String userChaoxingAccount;
    @Column(name = "user_chaoxing_password")
    private String userChaoxingPassword;
    @Column(name = "user_unit")
    private String userUnit;
    @Column(name = "user_true_name")
    private String userTrueName;
    @Column(name = "user_student_id")
    private String userStudentId;
    @Column(name = "user_invite_code")
    private String userInviteCode;
    @Column(name = "user_gender")
    private int userGender;
    @Column(name = "user_birthday")
    private String userBirthday;
    @Column(name = "user_score")
    private int userScore;
    @Column(name = "user_pta_number")
    private int userPtaNumber;
    @Column(name = "user_nowcoder_number")
    private int userNowcoderNumber;
    @Column(name = "user_pta_change")
    private int userPtaChange;
    @Column(name = "user_nowcoder_change")
    private int userNowcoderChange;
}

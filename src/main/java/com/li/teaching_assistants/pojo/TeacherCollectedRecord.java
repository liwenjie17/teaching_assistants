package com.li.teaching_assistants.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "TeacherCollectedRecord")
public class TeacherCollectedRecord implements Serializable {
    //id为email+Coll.
    @Id
    private String id;
    private String email;
    private String records;
}

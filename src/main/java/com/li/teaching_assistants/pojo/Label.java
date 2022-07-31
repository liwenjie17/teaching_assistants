package com.li.teaching_assistants.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "label")
public class Label {
    @Id
    @Column(name = "label_id")
    private int LabelId;
    @Column(name = "label_name")
    private String LabelName;
}

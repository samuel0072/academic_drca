package br.ufal.ic.academic.model;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    private Department department;

    public Course() {

    }
    public Course(String name, Department dp) {
        this.department = dp;
        this.name = name;
    }

}

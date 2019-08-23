package br.ufal.ic.academic.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @Setter
    private String name;

    public Student() {

    }

    public Student(String name) {
        this.name = name;
    }
}

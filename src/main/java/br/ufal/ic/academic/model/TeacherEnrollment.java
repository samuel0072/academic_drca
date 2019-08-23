package br.ufal.ic.academic.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Getter
public class TeacherEnrollment{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    protected int number;
    @ManyToMany
    private List<Subject> subjects;

    @OneToOne
    private Teacher teacher;

    @ManyToOne
    private Department depart;

    public TeacherEnrollment() {

    }

    public TeacherEnrollment(Teacher teacher, int number) {
        this.number = (number);
        this.teacher = teacher;
    }

    public void addCurrentSubject(Subject sub) {
        if(sub!= null) {
            subjects.add(sub);
        }
    }
}

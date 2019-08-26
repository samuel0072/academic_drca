package br.ufal.ic.academic.model;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
@Getter
public class TeacherEnrollment implements Model{

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

    public TeacherEnrollment(Teacher teacher, int number, List<Subject> subs) {
        this.number = (number);
        this.teacher = teacher;
        this.subjects = subs;
    }

    public void addCurrentSubject(Subject sub) {
        if(sub.isOkay()) {
            subjects.add(sub);
        }
    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.number <= 0) {
            ok = false;
        }
        else if(teacher == null || !this.teacher.isOkay()) {
            ok = false;
        }
        else if(subjects == null) {
            ok = false;
        }
        return ok;
    }
}

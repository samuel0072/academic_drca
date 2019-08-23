package br.ufal.ic.academic.model;


import br.ufal.ic.academic.util.types;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@Table

public class StudentEnrollment{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int number;

    @OneToOne
    private Student student;

    @ManyToOne
    private Department studentDepart;

    @Column
    private int credits;

    @ManyToMany
    @Column(nullable = true)
    private List<Subject> currentSubs;

    @ManyToMany
    @Column(nullable = true)
     private List<Subject> takenSubs;

    @ManyToOne
    private Course course;

    @Column
    private types type;

    public StudentEnrollment() {
    }
    public  StudentEnrollment(Student student, int number, Department dp, int cred,
                              Course course, types type, List<Subject> currentSubs, List<Subject> takenSubs) {
        this.number = number;
        this.student = student;
        this.studentDepart = dp;
        this.credits = cred;
        this.course = course;
        this.type = type;
        this.currentSubs = currentSubs;
        this.takenSubs = takenSubs;

    }

    public void addSubCurrent(Subject sub) {
        if(sub!= null){
            if(!takenSubs.contains(sub) && !currentSubs.contains(sub)) {
                currentSubs.add(sub);
            }
        }
    }
    public void addSubTaken(Subject sub) {
        if(sub!= null)
            if(!takenSubs.contains(sub)) {
                this.takenSubs.add(sub);
            }
    }

    public void addCredits(int credits) {
        if(credits > 0)
            this.credits += credits;
    }
}


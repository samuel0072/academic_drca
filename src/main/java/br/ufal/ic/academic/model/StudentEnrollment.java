package br.ufal.ic.academic.model;


import br.ufal.ic.academic.util.types;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@Table

public class StudentEnrollment implements Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private int number;

    @OneToOne
    private Student student;


    @Column
    private int credits;

    @ManyToMany
    private List<Subject> currentSubs;

    @ManyToMany
    private List<Subject> takenSubs;

    @ManyToOne
    private Course course;

    @Column
    private types type;

    public StudentEnrollment() {
    }
    public  StudentEnrollment(Student student, int number, int cred,
                              Course course, types type, List<Subject> currentSubs, List<Subject> takenSubs) {
        this.number = number;
        this.student = student;
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
                this.addCredits(sub.getCredits());
            }
    }

    private void addCredits(int credits) {
        if(credits > 0)
            this.credits += credits;
    }

    public boolean isOkay() {
        boolean ok = true;

        if(this.student == null || this.course == null ) {
            ok = false;
        }
        else if((!student.isOkay()) || (!this.course.isOkay())) {
            ok = false;
        }
        else if(this.number < 0) {
            ok = false;
        }
        else if(this.credits < 0 ) {
            ok = false;
        }
        else if((this.currentSubs == null) || (this.takenSubs == null)) {
            ok = false;
        }
        else if(this.type == null) {
            ok = false;
        }

        return ok;
    }
}


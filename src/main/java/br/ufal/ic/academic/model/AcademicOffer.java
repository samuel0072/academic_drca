package br.ufal.ic.academic.model;

import lombok.Getter;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class AcademicOffer implements Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    private List<Subject> subjects;
    @Column
    private int year;
    @Column
    private int period;
    @ManyToOne
    private Course course;

    public AcademicOffer() {
    }
    public AcademicOffer(int year, int period, Course course, List<Subject> subs) {
        this.year = year;
        this.period = period;
        this.course = course;
        this.subjects = subs;

    }

    //so adiciona se estiver tudo okay com a disciplina e forem do mesmo curso
    public void addSub(Subject sub) {
        if(sub.isOkay() && sub.getCourse() == this.course) {
            this.subjects.add(sub);
        }
    }

    public boolean isOkay() {
        boolean ok = true;

        if(this.subjects == null) {
            ok = false;
        }
        else if(this.period <= 0) {
            ok = false;
        }
        else if(this.year <= 0 ){
            ok = false;
        }
        else if(this.course == null || !this.course.isOkay()) {
            ok = false;
        }
        return ok;
    }
}

package br.ufal.ic.academic.model;

import lombok.Getter;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(appliesTo = "ACADEMICOFFER")
public class AcademicOffer {
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
    public AcademicOffer(int year, int period, Course course) {
        this.year = year;
        this.period = period;
        this.course = course;

    }

    public void addSub(Subject sub) {
        this.subjects.add(sub);
    }

    public  void removeSub(Subject sub) {
        this.subjects.remove(sub);
    }

}

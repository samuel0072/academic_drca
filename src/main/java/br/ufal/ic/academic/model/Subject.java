package br.ufal.ic.academic.model;

import br.ufal.ic.academic.util.types;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;


@AllArgsConstructor
@Getter
@Entity
@Table
public class Subject implements Model{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private String name;
    @Column
    private String code;
    @Column
    private int credits;
    @Column
    private int minCredits;
    @ManyToMany
    private List<Subject> requierements;

    @Column
    private types type;

    @ManyToOne
    private Course course;
    public Subject() {

    }

    public Subject(String name, String code, int credits, int minCredits, types type,
                   Course course, List<Subject> requierements) {
        this.name = name;
        this.code = code;
        this.credits = credits;
        this.minCredits = minCredits;
        this.type = type;
        this.course = course;
        this.requierements = requierements;
    }

    public void addRequirements(Subject sub) {
        if(sub != null) {
            this.requierements.add(sub);
        }
    }


    public boolean isOkay() {
        boolean ok = true;
        if(this.name == null || this.name == "") {
            ok = false;
        }
        else if(this.code == null || this.code == "") {
            ok = false;
        }
        else if(this.credits < 0) {
            ok = false;
        }
        else if(this.minCredits < 0 ) {
            ok = false;
        }
        else if(this.type == null) {
            ok = false;
        }
        else if(this.course == null || !this.course.isOkay()) {
            ok = false;
        }
        else if(this.requierements == null) {
            ok = false;
        }
        return ok;
    }
}

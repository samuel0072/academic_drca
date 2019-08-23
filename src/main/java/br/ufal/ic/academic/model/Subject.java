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
public class Subject {


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


}

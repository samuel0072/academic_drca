package br.ufal.ic.academic.model;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Course  implements  Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    private Secretary sec;

    public Course() {

    }
    public Course(String name, Secretary sec) {
        this.sec = sec;
        this.name = name;
    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.name == null || this.name == "") {
            ok = false;
        }
        if(this.sec == null || !this.sec.isOkay()) {
            ok = false;
        }
        return ok;
    }
}

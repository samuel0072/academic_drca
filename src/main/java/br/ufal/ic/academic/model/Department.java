package br.ufal.ic.academic.model;

import lombok.Getter;

import javax.persistence.*;


@Getter
@Entity
@Table
public class Department implements Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @OneToOne
    private Secretary graduationSec;
    @OneToOne
    private Secretary postGradSec;

    public Department(){

    }
    public Department(String name, Secretary grad, Secretary post){
        this.name = name;
        this.graduationSec = grad;
        this.postGradSec = post;

    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.name == null || this.name == "") {
            ok = false;
        }
        else if(this.graduationSec == null || this.postGradSec == null) {
            ok = false;
        }
        else if(!this.graduationSec.isOkay() || !this.postGradSec.isOkay()) {
            ok = false;
        }
        return ok;
    }
}

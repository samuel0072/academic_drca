package br.ufal.ic.academic.model;

import br.ufal.ic.academic.util.types;
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

    public Department(){

    }
    public Department(String name){
        this.name = name;

    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.name == null || this.name == "") {
            ok = false;
        }
        return ok;
    }
}

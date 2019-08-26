package br.ufal.ic.academic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@Table
public class Teacher implements Model{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;

    public Teacher(){

    }
    public Teacher(String name){
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

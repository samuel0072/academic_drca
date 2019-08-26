package br.ufal.ic.academic.model;

import br.ufal.ic.academic.util.types;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Entity
@Table
public class Secretary implements Model{


    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private types type;
    @ManyToOne
    private Department dp;

    public Secretary() {

    }
    public Secretary(types type, Department dp) {
        this.dp = dp;
        this.type = type;
    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.dp == null || !this.dp.isOkay()) {
            ok = false;
        }
        else if(this.type == null) {
            ok = false;
        }
        return ok;
    }
}

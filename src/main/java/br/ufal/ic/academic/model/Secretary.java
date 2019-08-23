package br.ufal.ic.academic.model;

import br.ufal.ic.academic.util.types;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Entity
@Table
public class Secretary {


    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private types type;
    @OneToOne
    @Setter
    private AcademicOffer offer;

    public Secretary() {

    }
    public Secretary(types type) {
        this.type = type;
    }


}

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
    @OneToOne
    @Setter
    private List<AcademicOffer> offers;

    public Secretary() {

    }
    public Secretary(types type, List<AcademicOffer> offers) {
        this.offers = offers;
        this.type = type;
    }
    public void addOffer(AcademicOffer offer) {
        if(offer.isOkay()) {
            this.offers.add(offer);
        }
    }

    public boolean isOkay() {
        boolean ok = true;
        if(this.offers == null) {
            ok = false;
        }
        return ok;
    }
}

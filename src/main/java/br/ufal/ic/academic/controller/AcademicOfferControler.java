package br.ufal.ic.academic.controller;

import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.AcademicOffer;
import br.ufal.ic.academic.model.Course;
import br.ufal.ic.academic.model.Department;
import br.ufal.ic.academic.model.Secretary;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;

import java.util.ArrayList;

public class AcademicOfferControler {
    private Database db;

    public AcademicOfferControler(Database db) {
        this.db = db;

    }

    public SystemResponse getsubjects(AcademicOffer offer) {
        SystemResponse res = new SystemResponse();
        if(offer == null) {
            res.error("offerta invalida");
        }
        else {
            res.ok(offer.getSubjects());
        }
        return res;
    }
    public SystemResponse getOffersFromDepartment(Department dp, types type ) {
        SystemResponse res = new SystemResponse();
        if(dp == null || type == null) {
            res.error("argumento invalido!");
        }
        else
        {
            String query = "select A.* from SECRETARY A where A.type = "+type+" and A.DP_ID="+dp.getId();
            Secretary sec = db.excuteQuery(query, false, Secretary.class);
            String query2 = "select A.* from COURSE A where A.SEC_ID = "+sec.getId();
            ArrayList<Course> courses = (ArrayList<Course>) db.excuteQuery(query2, true, ArrayList.class);
            ArrayList<AcademicOffer> ac = new ArrayList<>();

            courses.stream().forEach( u -> {
                String q = "SELECT A.* FROM ACADEMICOFFER A WHERE A.COURSE_ID ="+u.getId();
                AcademicOffer b = db.excuteQuery(q, false, AcademicOffer.class);
                if(b!= null) {
                    ac.add(b);
                }
            });

            res.ok(ac);
        }

        return res;
    }
}

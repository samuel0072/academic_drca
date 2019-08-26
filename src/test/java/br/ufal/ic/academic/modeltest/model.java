package br.ufal.ic.academic.modeltest;

import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.types;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class model {

    @Test
    public void isOkayTest() {
        Department dp = new Department("");
        Secretary sec = new Secretary(null, dp);
        Course cc = new Course("CC", sec);
        AcademicOffer ac = new AcademicOffer(-1, -1, cc, null);
        Subject subject = new Subject("calulo", "comp210", 0, -1, null, cc, new ArrayList<>());
        Student a = new Student("a");
        StudentEnrollment se = new StudentEnrollment(a, 1, 1, cc, types.GRAD, new ArrayList<>(), new ArrayList<>());
        Teacher t = new Teacher("");
        TeacherEnrollment t1 = new TeacherEnrollment(t,0, new ArrayList<>());

        assertAll(
                () -> assertFalse(dp.isOkay()),
                () -> assertFalse(sec.isOkay()),
                () -> assertFalse(cc.isOkay()),
                () -> assertFalse(ac.isOkay()),
                () -> assertFalse(subject.isOkay()),
                () -> assertTrue(a.isOkay()),
                () -> assertFalse(se.isOkay()),
                () -> assertFalse(t.isOkay()),
                () -> assertFalse(t1.isOkay())

        );

    }
}

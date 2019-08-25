package br.ic.ufal.academic.modeltest;

import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    Student student;
    Course cc;
    Secretary grad;
    Secretary post;
    Department ic;

    @BeforeEach
    void setup() {
        student = new Student("Samuel");
        grad = new Secretary(types.GRAD, new ArrayList<>());
        post = new Secretary(types.POST, new ArrayList<>());
        ic = new Department("IC", grad, post);
        cc = new Course("cc", ic);

    }
    @Test
    void initStudentTest() {
        Student a = new Student("Samuel");
        Student b = new Student();
        assertAll(
                () -> assertNotNull(a),
                () ->assertEquals("Samuel", a.getName()),
                () -> assertNull(a.getId()),
                () -> assertNull(b.getName())
        );
    }

    @Test
    void getSetStudentTest() {
        Student a = new Student("Samuel");
        Student b = new Student();
        Student c = new Student("");
        assertAll(
                () -> assertNotNull(a.getName()),
                () -> assertEquals("Samuel", a.getName()),
                () -> assertNull(a.getId()),
                () -> assertNull(b.getName()),
                () -> assertEquals("", c.getName())
        );
    }

    @Test
    void initEnrollTest() {
        StudentEnrollment e = new StudentEnrollment();
        StudentEnrollment b = new StudentEnrollment(new Student(), 002, new Department(), 1, new Course(), types.GRAD, new ArrayList<>(), new ArrayList<>());

        assertAll(
                () -> assertNotNull(e),
                () -> assertNotNull(b),
                () -> assertNotNull(b.getCredits()),
                () -> assertNotNull(b.getType()),
                () -> assertNotNull(b.getCourse()),
                () -> assertNotNull(b.getStudent()),
                () -> assertNotNull(b.getNumber()),
                () -> assertNotNull(b.getStudentDepart()),
                () -> assertNotNull(b.getTakenSubs()),
                () -> assertNotNull(b.getCurrentSubs()),
                () -> assertNull(b.getId())
        );

    }

    @Test
    void getSetEnrollTest() {
        Department d = new Department();
        Course c = new Course();
        ArrayList<Subject> taken = new ArrayList<>();
        ArrayList<Subject> current = new ArrayList<>();

        StudentEnrollment enroll1 = new StudentEnrollment(student, 123, d, 0, c, types.GRAD, current, taken);

        assertAll(
                () -> assertEquals(student, enroll1.getStudent()),
                () -> assertEquals(d, enroll1.getStudentDepart() ),
                () -> assertEquals(c, enroll1.getCourse()),
                () -> assertEquals(taken, enroll1.getTakenSubs()),
                () -> assertEquals(current, enroll1.getCurrentSubs()),
                () -> assertEquals( types.GRAD, enroll1.getType()),
                () -> assertEquals(0, enroll1.getCredits()),
                () -> assertEquals(123, enroll1.getNumber())
        );
        enroll1.addCredits(-1);
        assertNotEquals(-1, enroll1.getCredits());
        enroll1.addCredits(1);
        assertEquals(1, enroll1.getCredits());
    }

    @Test
    void addCurrentSubTest() {
        Subject sub = new Subject("Teste de Software", "COMP259", 50, 25, types.GRAD, new Course(), new ArrayList<>());
        Subject sub2 = new Subject();
        ArrayList<Subject> taken = new ArrayList<>();
        ArrayList<Subject> current = new ArrayList<>();
        StudentEnrollment enroll = new StudentEnrollment(student, 123, new Department(), 0, new Course(), types.GRAD, current, taken);

        enroll.addSubCurrent(sub);
        enroll.addSubCurrent(null);
        enroll.addSubTaken(sub);
        enroll.addSubTaken(null);

        assertAll(
                () -> assertEquals(taken, enroll.getTakenSubs()),
                () -> assertEquals(current, enroll.getTakenSubs())
        );

        assertAll(
                () -> assertEquals(1, taken.size()),
                () -> assertEquals(1, current.size())
        );

        enroll.addSubTaken(sub);
        enroll.addSubCurrent(sub);

        assertAll(
                () -> assertEquals(1, taken.size()),
                () ->  assertEquals(1, current.size())
        );

        enroll.addSubTaken(sub2);
        enroll.addSubCurrent(sub2);

        assertAll(
                () -> assertEquals(2, taken.size()),
                () -> assertEquals(1, current.size())
        );
    }

    @Test
    void isOkayTest() {
        Student a = new Student("Samuel");
        Student b = new Student();
        Student c = new Student("");
        StudentEnrollment a1 = new StudentEnrollment(a, 1, ic, 0, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment b1 = new StudentEnrollment(new Student(), 1, ic, 0, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment c1 = new StudentEnrollment(a, -1, ic, 0, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment d1 = new StudentEnrollment(null,-1, ic, 0, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment e1 = new StudentEnrollment(a, 1, null, 0, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment f1 = new StudentEnrollment(a, 1, ic, 0, null, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment g1 = new StudentEnrollment(a, 1, ic, 0, cc, null,
                new ArrayList<>(), new ArrayList<>());
        StudentEnrollment h1 = new StudentEnrollment(a, 1, ic, 0, cc, types.GRAD,
                null, new ArrayList<>());
        StudentEnrollment i1 = new StudentEnrollment(a, 1, ic, 0, cc, types.GRAD,
                new ArrayList<>(), null);
        StudentEnrollment j1 = new StudentEnrollment(a, 1, ic, -1, cc, types.GRAD,
                new ArrayList<>(), new ArrayList<>());
        assertAll(
                () -> assertTrue(a.isOkay()),
                () -> assertFalse(b.isOkay()),
                () -> assertFalse(c.isOkay()),
                () -> assertTrue(a1.isOkay()),
                () -> assertFalse(j1.isOkay()),
                () -> assertFalse(b1.isOkay()),
                () -> assertFalse(c1.isOkay()),
                () -> assertFalse(d1.isOkay()),
                () -> assertFalse(e1.isOkay()),
                () -> assertFalse(f1.isOkay()),
                () -> assertFalse(g1.isOkay()),
                () -> assertFalse(h1.isOkay()),
                () -> assertFalse(i1.isOkay())
        );
    }
}

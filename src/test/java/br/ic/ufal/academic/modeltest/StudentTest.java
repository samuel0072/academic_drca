package br.ic.ufal.academic.modeltest;

import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    Student student;

    @BeforeEach
    void setup() {
        student = new Student("Eric");

    }
    @Test
    void initStudentTest() {
        Student a = new Student("Samuel");
        String name = a.getName();
        assertAll(
                () -> assertNotNull(a),
                () ->assertEquals(name, "Samuel"),
                () -> assertNull(a.getId())
        );
    }

    @Test
    void getSetStudentTest() {
        String name = "Samuel";
        student.setName(name);
        assertAll(
                () -> assertNotNull(student.getName()),
                () -> assertEquals(student.getName(), name),
                () -> assertNull(student.getId())
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
}

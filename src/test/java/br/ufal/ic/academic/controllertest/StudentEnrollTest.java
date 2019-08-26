package br.ufal.ic.academic.controllertest;

import br.ufal.ic.academic.controller.StudentEnrollControler;
import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StudentEnrollTest {
    StudentEnrollment a;
    Department ic;
    Course cc;
    StudentEnrollControler controller;
    Secretary sec;

    @BeforeEach
    void setup() {
        ic = new Department("IC");
         sec = new Secretary(types.GRAD, ic);
        cc = new Course("CC", sec);
        a = new StudentEnrollment(new Student("Samuel"), 311, 0, cc, types.GRAD, new ArrayList<>(), new ArrayList<>());
        controller = new StudentEnrollControler();
    }

    @Test
    void enrollSubTest() {
        Subject p3, c1, paa;
        ArrayList<Subject> req = new ArrayList<>();
        StudentEnrollment r, s, t;
        Secretary sec2 = new Secretary(types.POST, new Department("CC"));

        SystemResponse r1, r2, r3, r4, r5, r6, r7, r8;

        p3  = new Subject("P3", "COMP215", 0, 60, types.GRAD, cc, new ArrayList<>());
        req.add(p3);
        c1  = new Subject("Calculo 1", "COMP210", 100, 60, types.GRAD, cc, req);
        paa = new Subject("PAA", "MCM", 100, 60, types.POST, cc, new ArrayList<>());
        r  = new StudentEnrollment(new Student("Samuel"), 111, 200, cc, types.POST, new ArrayList<>(), new ArrayList<>());
        s = new StudentEnrollment(new Student("Eric"), 112,  200, new Course("JC", sec2), types.POST, new ArrayList<>(), new ArrayList<>());
        t = new StudentEnrollment(new Student("Samuel"), 311, 160, cc,
                types.GRAD, new ArrayList<>(), new ArrayList<>());

        r1 = controller.enrollSub(null, a);//disciplina invalida
        r2 = controller.enrollSub(c1, a);//creditos insulficientes
        r3 = controller.enrollSub(c1, t);//nao tem todos os pre-requisitos
        t.addSubTaken(p3);
        r4 = controller.enrollSub(p3, t);//nao pode cursar de novo
        r5 = controller.enrollSub(p3, r);//pos não pode cursar grad
        r6 = controller.enrollSub(paa, t);//aluno de graducao com creditos insulficiente para pos
        r7 = controller.enrollSub(paa, s);//departamentos diferentes
        r8 = controller.enrollSub(c1, t);//matriculou


        assertAll(
                () -> assertTrue(r1.isError()),
                () -> assertTrue(r2.isError()),
                () -> assertTrue(r3.isError()),
                () -> assertTrue(r4.isError()),
                () -> assertTrue(r5.isError()),
                () -> assertTrue(r6.isError()),
                () -> assertTrue(r7.isError()),
                () -> assertFalse(r8.isError())
        );

        assertAll(
                () -> assertEquals("Disciplina invalida!", r1.getObject()),
                () -> assertEquals("creditos insulficientes", r2.getObject()),
                () -> assertEquals("disciplinas insulficientes", r3.getObject()),
                () -> assertEquals("ja cursou esta disciplina", r4.getObject()),
                () -> assertEquals("aluno de pos-graduacao e disciplina de graduacao", r5.getObject()),
                () -> assertEquals("creditos insulficientes", r6.getObject()),
                () -> assertEquals("departamentos diferentes", r7.getObject()),
                () -> assertEquals("matriculado", r8.getObject())
        );

    }

    @Test
    void getEnrollDataTest() {
        Subject c1, p3, teste;
        SystemResponse res,res2;
        ArrayList<Object> s;

        c1  = new Subject("Calculo 1", "COMP210", 100, 60, types.GRAD, cc, new ArrayList<>());
        teste = new Subject("TESTE", "MCM", 100, 60, types.POST, cc, new ArrayList<>());
        p3  = new Subject("P3", "COMP215", 100, 60, types.GRAD, cc, new ArrayList<>());
        a.addSubCurrent(c1);
        a.addSubCurrent(teste);
        a.addSubCurrent(p3);



        res = controller.getEnrollData(a);
        res2 = controller.getEnrollData(null);
        s = (ArrayList<Object>) res.getObject();


        assertAll(
                () -> assertFalse(res.isError()),
                () -> assertTrue(res2.isError()),
                () -> assertEquals("estudante invalido", res2.getObject()),
                () -> assertTrue(s.contains(a.getCurrentSubs())),
                () -> assertTrue(s.contains(a.getStudent().getName())),
                () -> assertTrue(s.contains(a.getNumber()))
        );


    }

}

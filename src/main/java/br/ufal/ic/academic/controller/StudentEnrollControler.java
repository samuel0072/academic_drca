package br.ufal.ic.academic.controller;

import br.ufal.ic.academic.model.StudentEnrollment;
import br.ufal.ic.academic.model.Subject;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;

import java.util.ArrayList;
import java.util.List;

public class StudentEnrollControler {

    //requisito 1 e 2
    public SystemResponse enrollSub(Subject sub, StudentEnrollment student) {
        SystemResponse res = new SystemResponse();
        if(sub == null) {
            res.error("Disciplina invalida!");
            return res;
        }


        List<Subject> requirements = sub.getRequierements();
        List<Subject> coursed = student.getTakenSubs();



        if(student.getCredits() < sub.getMinCredits()) {
            res.error("creditos insulficientes");
        }
        else if(!coursed.containsAll(requirements)) {
            res.error("disciplinas insulficientes");
        }
        else if(coursed.contains(sub)) {
            res.error("ja cursou esta disciplina");
        }
        else if(sub.getType()!= student.getType()) {
            if(student.getType() == types.POST && sub.getType() == types.GRAD) {
                res.error("aluno de pos-graduacao e disciplina de graduacao");
            }
            else if(student.getType() == types.GRAD && student.getCredits() < 170) {
                res.error("creditos insulficientes");
            }
        }
        else if(student.getStudentDepart() != sub.getCourse().getDepartment()) {
            res.error("departamentos diferentes");
        }
        else{
            student.addSubCurrent(sub);
            res.ok("matriculado");
        }
        return res;
    }

    //requisito 3 letra c
    public SystemResponse getEnrollData(StudentEnrollment student) {

        SystemResponse res = new SystemResponse();
        if(student == null) {
            res.error("estudante invalido");
        }
        else {
            List<Subject> current = student.getCurrentSubs();
            String name = student.getStudent().getName();
            int enroll = student.getNumber();
            ArrayList<Object> r = new ArrayList<>();

            r.add(current);
            r.add(name);
            r.add(enroll);
            res.ok(r);
        }


        return res;
    }
}

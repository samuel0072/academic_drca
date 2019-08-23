package br.ufal.ic.academic.controller;

import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.StudentEnrollment;
import br.ufal.ic.academic.model.Subject;
import br.ufal.ic.academic.model.TeacherEnrollment;
import br.ufal.ic.academic.util.SystemResponse;

import java.util.ArrayList;
import java.util.List;

public class SubjectController {

    Database<StudentEnrollment> studentDb;
    Database<TeacherEnrollment> teacherDb;

    public SubjectController(Database<StudentEnrollment> studentDb,
                             Database<TeacherEnrollment> teacherDb) {
        this.studentDb = studentDb;
        this.teacherDb = teacherDb;

    }

    //requisito 3 letra b
    public SystemResponse getSubjectSummary(Subject subject) {
        SystemResponse res = new SystemResponse();

        if(subject == null) {
            res.error("disciplina invalida");
        }
        else {
            List<Subject> requirements;
            ArrayList<StudentEnrollment> students;
            ArrayList<Object> r = new ArrayList<>();

            TeacherEnrollment teacher;

            String query1, query2;


            query1 = "select B.* from TEACHERENROLLMENT_SUBJECT A, TEACHERENROLLMENT B" +
                    " where A.SUBJECTS_ID =" + subject.getId() + " and A.TEACHERENROLLMENT_ID = B.ID";
            query2 = "select B.* from STUDENTENROLLMENT_SUBJECT A,  STUDENTENROLLMENT B where A.CURRENTSUBS_ID = "
                    + subject.getId() + " and A.STUDENTENROLLMENT_ID = B.ID";

            teacher = teacherDb.excuteQuery(query1, false, TeacherEnrollment.class);

            students = studentDb.excuteQuery(query2, true, ArrayList.class);

            requirements = subject.getRequierements();
            requirements.forEach( u -> {
                r.add(u);
            });
            students.forEach( u ->  {
                r.add(u);
            });
            r.add(teacher.getTeacher().getName());

            res.ok(r);
        }
        return res;
    }
}

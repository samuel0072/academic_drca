package br.ufal.ic.academic.resources;

import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.*;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/admin")
public class SubjectResource {

    private Database db;
    private Database studentEnrollmentDb;
    private Database teacherDb;

    /*@POST
    @UnitOfWork
    @Path("/subject/{name}")
    public Response create(@PathParam("name") String name, @QueryParam("code") String code,
                           @QueryParam("credits")int credits, @QueryParam("mincredits")int mincred,
                           @QueryParam("graduation")boolean grad) {
        Subject d = new Subject(name, code, credits, mincred, grad);
        db.create(d);
        return Response.ok(d).build();
    }*/

    @GET
    @UnitOfWork
    @Path("/subject")
    public Response findAllStudent() {
        List<Subject> d =  db.findAll(Subject.class);
        return Response.ok(d).build();
    }

    @GET
    @UnitOfWork
    @Path("/subject/{id}")
    public Response findOne(@PathParam("id") Long id) {
        Subject target =  (Subject) db.findById(Subject.class, id);
        return Response.ok(target).build();
    }

    @DELETE
    @UnitOfWork
    @Path("/subject/{id}/delete")
    public Response deleteById(@PathParam("id") Long id) {
        db.deleteById(Subject.class, id);
        return Response.ok().build();
    }

    @PUT
    @UnitOfWork
    @Path("/subject/{id}/addrequirement/{recid}")
    public Response updateById(@PathParam("id") Long id, @PathParam("recid")Long recid) {
        Subject c = (Subject) db.findById(Subject.class, id);
        Subject d = (Subject) db.findById(Subject.class, recid);

        c.addRequirements(d);
        db.updateById(c);

        return Response.ok(c).build();
    }

    /*@GET
    @UnitOfWork
    @Path("/subject/{id}/report")

    public Response getReport(@PathParam("id")Long id){
        Subject c = (Subject) db.findById(Subject.class, id);
        List<StudentEnrollment> students = studentEnrollmentDb.findAll(StudentEnrollment.class);
        List<TeacherEnrollment> teachers = teacherDb.findAll(TeacherEnrollment.class);

        List<StudentEnrollment> studentsres = new ArrayList<>();
        TeacherEnrollment d = null;
        for(StudentEnrollment a : students) {
            if(a.getCurrentSubs().contains(c)) {
                studentsres.add(a);
            }
        }
        for(TeacherEnrollment a: teachers ) {
            if(a.getSubjects().contains(c)) {
                d = a;
                break;
            }
        }
        ArrayList<Object> res = new ArrayList<>();
        res.add(c);
        res.add(students);
        res.add(d);

        return Response.ok(res).build();
    }*/
}

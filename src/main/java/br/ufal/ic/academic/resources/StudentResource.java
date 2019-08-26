package br.ufal.ic.academic.resources;

import br.ufal.ic.academic.controller.StudentEnrollControler;
import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;
import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/academic")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {
    private Database db;

    public StudentResource() {
    }
    public StudentResource(Database db) {
        this.db = db;
    }

    @POST
    @UnitOfWork
    @Path("/student/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("name") String name) {

        Student d = new Student(name);
        d = (Student) db.create(d);
        return Response.ok(d).build();
    }

    @GET
    @UnitOfWork
    @Path("/student")
    public Response findAllStudent() {
        List<Student> d =  db.findAll(Student.class);
        return Response.ok(d).build();
    }

    @GET
    @UnitOfWork
    @Path("/student/{id}")
    public Response findOne(@PathParam("id") Long id) {
        Student target =  (Student) db.findById(id);
        return Response.ok(target).build();
    }

    @DELETE
    @UnitOfWork
    @Path("/student/{id}/delete")
    public Response deleteById(@PathParam("id") Long id) {
        db.deleteById(id);
        return Response.ok().build();
    }
    @POST
    @UnitOfWork
    @Path("/student/enroll")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createEnroll(@FormParam("studentid") Long id,@FormParam("enrollnumber")int enrollnumber,
                                 @FormParam("departmentid") Long dpid,@FormParam("credits") int cred,
                                 @FormParam("courseid") Long courseid,@FormParam("type") types type) {
        Student d = (Student) db.findById(id);
        Department dp = (Department) db.findById(dpid);
        Course cc = (Course) db.findById(courseid);
        StudentEnrollment c = new StudentEnrollment(d, enrollnumber, dp, cred, cc, type, new ArrayList<>(), new ArrayList<>());
        c = (StudentEnrollment) db.create(c);
        return Response.ok(c).build();
    }
    @GET
    @UnitOfWork
    @Path("/student/enroll/{id}")
    public Response getEnroll(@PathParam("id") Long id) {
        StudentEnrollment target =  (StudentEnrollment) db.findById(id);
        return Response.ok(target).build();
    }
    @GET
    @UnitOfWork
    @Path("/student/enroll")
    public Response getEnroll() {
        ArrayList<StudentEnrollment> target =  (ArrayList<StudentEnrollment>) db.findAll(StudentEnrollment.class);
        return Response.ok(target).build();
    }
    @PUT
    @UnitOfWork
    @Path("/student/enroll/{id}/addcurrentsub/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addsubcurrent(@PathParam("id") Long id, @FormParam("subjectid") Long subid) {
        Subject sub = (Subject) db.findById(subid);
        StudentEnrollment target =  (StudentEnrollment) db.findById(id);
        StudentEnrollControler c = new StudentEnrollControler();
        SystemResponse d = c.enrollSub(sub, target);
        return Response.ok(target).build();
    }
    @PUT
    @UnitOfWork
    @Path("/student/enroll/{id}/addtakensub/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addsubtaken(@PathParam("id") Long id, @FormParam("subjectid") Long subid) {
        Subject sub = (Subject) db.findById(subid);
        StudentEnrollment target =  (StudentEnrollment) db.findById(id);
        target.addSubTaken(sub);
        return Response.ok(target).build();
    }
}

package br.ufal.ic.academic.resources;
import br.ufal.ic.academic.controller.AcademicOfferControler;
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
public class DepartmentResource {
    private Database db;

    public DepartmentResource() {
    }
    public DepartmentResource(Database db) {
        this.db = db;
    }

    @POST
    @UnitOfWork
    @Path("/department/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("name") String name) {
        Department dp = new Department(name);
        dp = (Department) db.create(dp);
        return Response.ok(dp).build();
    }

    @GET
    @UnitOfWork
    @Path("/department/")
    public Response findAll() {
        ArrayList<Department> dps = (ArrayList<Department>) db.findAll(Department.class);
        return Response.ok(dps).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}")
    public Response findOne(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        return Response.ok(dp).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}/students")
    public Response findAllStudents(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        String query = "select A.* from STUDENTENROLLMENT A, COURSE B, SECRETARY C where A.COURSE_ID = B.ID " +
                "and B.SEC_ID = C.ID and C.DP_ID = "+id;
        ArrayList<Student> students = (ArrayList<Student>)db.excuteQuery(query, true, ArrayList.class);
        return Response.ok(students).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}/teacher")
    public Response findAllTeacher(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        String query = "select A.* from TEACHERENROLLMENT  A where A.DEPART_ID ="+id;
        ArrayList<Teacher> teachers = (ArrayList<Teacher>)db.excuteQuery(query, true, ArrayList.class);
        return Response.ok(teachers).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}/course")
    public Response findAllCourse(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        String query = "select A.* from COURSE  A, SECRETARY B where A.SEC_ID =B.ID and B.DP_ID ="+id;
        ArrayList<Course> courses = (ArrayList<Course>)db.excuteQuery(query, true, ArrayList.class);
        return Response.ok(courses).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}/offers/graduation")
    public Response getAcademicOffersGrad(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        SystemResponse res = new AcademicOfferControler(db).getOffersFromDepartment(dp, types.GRAD);
        return Response.ok(res).build();
    }
    @GET
    @UnitOfWork
    @Path("/department/{id}/offers/postgraduation")
    public Response getAcademicOffersPost(@PathParam("id") Long id) {
        Department dp = (Department) db.findById(id);
        SystemResponse res = new AcademicOfferControler(db).getOffersFromDepartment(dp, types.POST);
        return Response.ok(res).build();
    }
}

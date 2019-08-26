package br.ufal.ic.academic.resources;
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
public class CourseResource {
    private Database db;

    public CourseResource() {
    }
    public CourseResource(Database db) {
        this.db = db;
    }

    @POST
    @UnitOfWork
    @Path("/course/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("name") String name, @FormParam("secretaryid") Long secretaryid) {
        Secretary d = (Secretary) db.findById(secretaryid);
        Course cc = new Course(name, d);

        cc = (Course) db.create(cc);
        return Response.ok(cc).build();
    }

    @GET
    @UnitOfWork
    @Path("/course/{id}/student")
    public Response findAllCourse(@PathParam("id") Long id) {
        Course cc = (Course) db.findById(id);
        String query = "select A.* from STUDENTENROLMENT  A where A.COURSE_ID _ID="+id;
        ArrayList<Course> courses = (ArrayList<Course>)db.excuteQuery(query, true, ArrayList.class);
        return Response.ok(courses).build();
    }
}

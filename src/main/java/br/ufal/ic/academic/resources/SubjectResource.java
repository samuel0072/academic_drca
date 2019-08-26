package br.ufal.ic.academic.resources;

import br.ufal.ic.academic.controller.SubjectController;
import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.Builder;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/academic")
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {

    private Database db;

    public SubjectResource() {
    }
    public SubjectResource(Database db) {
        this.db = db;
    }

    @POST
    @UnitOfWork
    @Path("/subject/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("name") String name,@FormParam("code") String code,
                           @FormParam("credits")int credits,@FormParam("mincred") int mincred,
                           @FormParam("type")types type,@FormParam("courseid") Long courseId) {
        //String name = request.getParameter("name");
        Course course = (Course) db.findById(courseId);
        Subject d = new Subject(name, code, credits, mincred, type,course,new ArrayList<>());
        d = (Subject)db.create(d);
        return Response.ok(d).build();
    }

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
        Subject target =  (Subject) db.findById(id);
        return Response.ok(target).build();
    }

    @DELETE
    @UnitOfWork
    @Path("/subject/{id}/delete")
    public Response deleteById(@PathParam("id") Long id) {
        db.deleteById(id);
        return Response.ok().build();
    }

    @PUT
    @UnitOfWork
    @Path("/subject/{id}/addrequirement/{recid}")
    public Response updateById(@PathParam("id") Long id, @PathParam("recid")Long recid) {
        Subject c = (Subject) db.findById(id);
        Subject d = (Subject) db.findById(recid);

        c.addRequirements(d);
        db.updateById(c);

        return Response.ok(c).build();
    }

    @GET
    @UnitOfWork
    @Path("/subject/{id}/report")

    public Response getReport(@PathParam("id")Long id){
        Subject sub =(Subject) db.findById(id);
        SubjectController c = new SubjectController(db);
        SystemResponse res = c.getSubjectSummary(sub);
        return Response.ok(res).build();
    }
}

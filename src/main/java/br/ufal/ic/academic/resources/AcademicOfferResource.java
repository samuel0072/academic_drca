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
public class AcademicOfferResource {
    private Database db;

    public AcademicOfferResource() {
    }
    public AcademicOfferResource(Database db) {
        this.db = db;
    }

    @POST
    @UnitOfWork
    @Path("/academicoffer/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response create(@FormParam("year") int year, @FormParam("period") int period,
                           @FormParam("courseid")Long courseid) {
        Course cc = (Course) db.findById(courseid);
        AcademicOffer ac = new AcademicOffer(year, period, cc, new ArrayList<>());

        ac = (AcademicOffer) db.create(ac);
        return Response.ok(ac).build();
    }

    @PUT
    @UnitOfWork
    @Path("/academicoffer/{id}/addsub")
    public Response addsub(@FormParam("subid") Long subid, @PathParam("id") Long id) {
        AcademicOffer ac = (AcademicOffer) db.findById(id);
        Subject sub = (Subject) db.findById(subid);
        ac.addSub(sub);
        db.update(ac);

        return Response.ok(ac).build();
    }
}

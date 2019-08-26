package br.ufal.ic.academic;

import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.resources.SubjectResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Aplication  extends Application<AplicationConfig> {
    public static void main(String[] args) throws Exception{
        new Aplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AplicationConfig> bootstrap) {

        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(AplicationConfig config, Environment environment) {
        Database db = new Database(hibernate.getSessionFactory());

        environment.jersey().register(new SubjectResource(db));

    }

    private final HibernateBundle<AplicationConfig> hibernate = new HibernateBundle<AplicationConfig>(Student.class,
            Department.class, Secretary.class, Subject.class, Teacher.class,
            StudentEnrollment.class, TeacherEnrollment.class, AcademicOffer.class, Course.class) {
        public DataSourceFactory getDataSourceFactory(AplicationConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

}

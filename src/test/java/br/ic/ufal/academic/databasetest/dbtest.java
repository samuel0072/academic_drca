package br.ic.ufal.academic.databasetest;

import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.Model;
import br.ufal.ic.academic.model.Student;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.testing.junit.DAOTestRule;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class dbtest {

    @Rule
    public DAOTestRule database = DAOTestRule.newBuilder().addEntityClass(Model.class).build();

    private Database mockdb;

    @BeforeEach
    @SneakyThrows
    @UnitOfWork
     void setup() {
        mockdb = new Database(database.getSessionFactory());
        System.out.println(database!=null);
    }

    @Test
    @UnitOfWork
     void createTest() {
        mockdb = new Database(database.getSessionFactory());
        Student a = new Student("samuel");
//        //Student b = database.inTransaction(() -> mockdb.create(a));
////
////        assertAll(
////                () -> assertNotNull(b),
////                () -> assertEquals(a.getId(), b.getId()),
////                () -> assertEquals(a.getName(), b.getName()),
////                () -> assertNotSame(a, b)
////                );
    }

}

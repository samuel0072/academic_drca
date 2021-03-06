package br.ufal.ic.academic.controllertest;

import br.ufal.ic.academic.controller.SubjectController;
import br.ufal.ic.academic.database.Database;
import br.ufal.ic.academic.model.*;
import br.ufal.ic.academic.util.SystemResponse;
import br.ufal.ic.academic.util.types;
import org.hibernate.*;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectControllerTest {

    public mockDatabase db;
    public StudentEnrollment s1, s2;
    public TeacherEnrollment t1, t2;
    public Subject sub1, sub2;
    public Department ic;
    public Course cc;
    public  Secretary sec;

    @BeforeEach
     void setup() {
        ic = new Department("IC");
        sec = new Secretary(types.GRAD, ic);
        cc = new Course("CC", sec);
        s1 = new StudentEnrollment(new Student("Samuel"), 1,
                0, cc, types.GRAD, new ArrayList<>(), new ArrayList<>());
        s2 = new StudentEnrollment(new Student("Eric"), 3,
                0, cc, types.GRAD, new ArrayList<>(), new ArrayList<>());

        t1 = new TeacherEnrollment(new Teacher("Willy"), 2, new ArrayList<>());
        t2 = new TeacherEnrollment(new Teacher("Paes"), 3, new ArrayList<>());

        sub1 = new Subject("Teste", "Comp000", 100, 90,
                types.GRAD, cc, new ArrayList<>());
        sub2 =  new Subject("P3", "Comp001", 100, 90,
                types.GRAD, cc, new ArrayList<>());
    }


    @Test
     void testGetSubjectSummary() {
        SystemResponse r1, r2, r3;
        SubjectController sc;
        ArrayList<Object> s, a1;
        ArrayList<StudentEnrollment> students;

        sub1.addRequirements(sub2);

        t1.addCurrentSubject(sub1);

        s1.addSubCurrent(sub1);
        s2.addSubCurrent(sub1);

        db = new mockDatabase(sub1);

        db.create(s1);
        db.create(s2);
        db.create(t1);

        sc = new SubjectController(db);
        students = new ArrayList<>();
        students.add(s1);
        students.add(s2);

        r1 = sc.getSubjectSummary(null);
        r2 = sc.getSubjectSummary(sub1);

        s = (ArrayList<Object>)r2.getObject();

        assertAll(
                () -> assertTrue(r1.isError()),
                () -> assertFalse(r2.isError()),
                () -> assertEquals("disciplina invalida", r1.getObject()),
                () -> assertTrue(s.contains(t1.getTeacher().getName())),
                //todo: verificar por que o corno do database não adiciona o teacher
                () -> assertTrue(s.containsAll(sub1.getRequierements())),
                () -> assertTrue(s.containsAll(students))
        );
        //falta testar o caso de nao haver requirements e nao haver estudantes e nao haver professor
        db = new mockDatabase(sub2);
        db = new mockDatabase(sub2);
        sc = new SubjectController(db);

        r3 = sc.getSubjectSummary(sub2);
        a1 = (ArrayList<Object>) r3.getObject();


        assertAll(
                () -> assertFalse(r3.isError()),
                () -> assertEquals(0, a1.size())
        );


    }


    public class mockDatabase extends Database {
        private ArrayList<Model> fakedb;

        private Subject subject;

        public mockDatabase(Subject subject) {
            super(new SessionFactory() {
                @Override
                public SessionFactoryOptions getSessionFactoryOptions() {
                    return null;
                }

                @Override
                public SessionBuilder withOptions() {
                    return null;
                }

                @Override
                public Session openSession() throws HibernateException {
                    return null;
                }

                @Override
                public Session getCurrentSession() throws HibernateException {
                    return null;
                }

                @Override
                public StatelessSessionBuilder withStatelessOptions() {
                    return null;
                }

                @Override
                public StatelessSession openStatelessSession() {
                    return null;
                }

                @Override
                public StatelessSession openStatelessSession(Connection connection) {
                    return null;
                }

                @Override
                public Statistics getStatistics() {
                    return null;
                }

                @Override
                public void close() throws HibernateException {

                }

                @Override
                public boolean isClosed() {
                    return false;
                }

                @Override
                public Cache getCache() {
                    return null;
                }

                @Override
                public Set getDefinedFilterNames() {
                    return null;
                }

                @Override
                public FilterDefinition getFilterDefinition(String s) throws HibernateException {
                    return null;
                }

                @Override
                public boolean containsFetchProfileDefinition(String s) {
                    return false;
                }

                @Override
                public TypeHelper getTypeHelper() {
                    return null;
                }

                @Override
                public ClassMetadata getClassMetadata(Class aClass) {
                    return null;
                }

                @Override
                public ClassMetadata getClassMetadata(String s) {
                    return null;
                }

                @Override
                public CollectionMetadata getCollectionMetadata(String s) {
                    return null;
                }

                @Override
                public Map<String, ClassMetadata> getAllClassMetadata() {
                    return null;
                }

                @Override
                public Map getAllCollectionMetadata() {
                    return null;
                }

                @Override
                public Reference getReference() throws NamingException {
                    return null;
                }

                @Override
                public <T> List<EntityGraph<? super T>> findEntityGraphsByType(Class<T> aClass) {
                    return null;
                }

                @Override
                public Metamodel getMetamodel() {
                    return null;
                }

                @Override
                public EntityManager createEntityManager() {
                    return null;
                }

                @Override
                public EntityManager createEntityManager(Map map) {
                    return null;
                }

                @Override
                public EntityManager createEntityManager(SynchronizationType synchronizationType) {
                    return null;
                }

                @Override
                public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
                    return null;
                }

                @Override
                public CriteriaBuilder getCriteriaBuilder() {
                    return null;
                }

                @Override
                public boolean isOpen() {
                    return false;
                }

                @Override
                public Map<String, Object> getProperties() {
                    return null;
                }

                @Override
                public PersistenceUnitUtil getPersistenceUnitUtil() {
                    return null;
                }

                @Override
                public void addNamedQuery(String s, javax.persistence.Query query) {

                }

                @Override
                public <T> T unwrap(Class<T> aClass) {
                    return null;
                }

                @Override
                public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {

                }
            });
            this.fakedb = new ArrayList<>();
            this.subject = subject;
        }

        @Override
        //retorna null se não encontrar resultado
        public<G> G excuteQuery(String query, boolean multiple, Class<G> clazz) {
            G res = null;

            if(multiple) {
                List<Model> res1;
                res1 = fakedb.stream().filter(u -> {
                    if(u.getClass() == StudentEnrollment.class) {
                        boolean a = false;
                        StudentEnrollment b = (StudentEnrollment) u;
                        if(b.getCurrentSubs().contains(subject)) {
                            a = true;
                        }
                        return a;
                    }
                    else {
                        return false;
                    }

                }).collect(Collectors.toList());
                if(!res1.isEmpty()) {
                    res = (G) res1;
                }
            }
            else {
                List<Model> res1;

                res1 =  fakedb.stream().filter(u -> {
                    if(u.getClass() == TeacherEnrollment.class) {
                        boolean a = false;
                        TeacherEnrollment b = (TeacherEnrollment)u;
                        if(b.getSubjects().contains(subject)){
                            a = true;
                        }
                        return a;
                    }
                    else {
                        return false;
                    }

                }).collect(Collectors.toList());
                if(!res1.isEmpty()) {
                    res = (G)res1.get(0);
                }
            }
            return res;

        }

        public Model create(Model object) {
            if(object.isOkay()) {
                this.fakedb.add(object);
            }
            return object;
        }

    }
}

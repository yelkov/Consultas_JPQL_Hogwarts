package edu.badpals.hogwarts;

import edu.badpals.hogwarts.entities.Course;
import edu.badpals.hogwarts.entities.HousePoint;
import edu.badpals.hogwarts.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;

public class Consultas {
    List<Person> getPersonasByProfesor(EntityManager em, Person profesor) {
        List<Person> personas = new ArrayList<Person>();
        try{
            Query query = em.createQuery("SELECT c FROM Course c WHERE c.teacher = :profesor");
            query.setParameter("profesor", profesor);
            Course course = (Course) query.getSingleResult();
            Query query2 = em.createQuery("""
                                                
                                                    SELECT  p
                                                    FROM Person p
                                                    WHERE p in (
                                                                SELECT  e.personEnrollment
                                                                    FROM Enrollment e
                                                                    WHERE e.courseEnrollment = :course
                                                              )
                                                """);
            query2.setParameter("course", course);
            personas.addAll(query2.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return personas;
    }

    Person getMaxReceiver(EntityManager em) {
        Person maxReceiver = em.createNamedQuery("HousePoint.maxReceiver", Person.class).getSingleResult();
        return maxReceiver;
    }
    Person getMaxgiver(EntityManager em) {
        Person maxGiver = em.createNamedQuery("HousePoint.maxgiver", Person.class).getSingleResult();
        return maxGiver;
    }

    public List<String> getNombresProfesores(EntityManager em) {
        List<String> nombresProfesores = new ArrayList<>();
        try{
            Query query = em.createNativeQuery("""
                                                    select  p.first_name
                                                        from person as p inner join course as c
                                                                            on p.id = c.teacher_id
                                                    """);
            nombresProfesores.addAll(query.getResultList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nombresProfesores;
    }

    public List<String> getNombresCursoProfesor(EntityManager em){
        List<String> nombresCursoProfesores = new ArrayList<>();
        try{
            Query query = em.createNativeQuery("""
                                                    select c.name, p.first_name, p.last_name
                                                        from course as c left join person as p
                                                                        on c.teacher_id = p.id
                                                    """);
            List<Object[]> resultados = query.getResultList();
            for(Object[] row : resultados){
                StringBuilder sb = new StringBuilder();
                sb.append((String)row[0]);
                sb.append(": ");
                sb.append(row[1] == null ? " - " : (String) row[1]);
                sb.append(" ");
                sb.append(row[2] == null ? " - " : (String) row[2]);
                nombresCursoProfesores.add(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nombresCursoProfesores;
    }

    public List<String> getNomPersonasPtosSobreMedia(EntityManager em){
        List<String> nomPersonasPtosSobreMedia = new ArrayList<>();
        try{
            Query query = em.createNativeQuery("""
                                                select p.first_name, p.last_name
                                                        from house_points as hp inner join person as p
                                                                            on hp.receiver = p.id
                                                        group by hp.receiver
                                                        having sum(hp.points) > (
                                                                                    select avg(points)
                                                                                        from house_points
                                                                                )
                                                    """);
            List<Object[]> resultados = query.getResultList();
            for(Object[] row : resultados){
                StringBuilder sb = new StringBuilder();
                sb.append((String)row[0]);
                sb.append(" ");
                sb.append((String)row[1]);
                nomPersonasPtosSobreMedia.add(sb.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nomPersonasPtosSobreMedia;
    }

    public int deletePersona(EntityManager em, Person person) {
        int instanciasEliminadas = 0;
        try{
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Person p WHERE p = :persona");
            query.setParameter("persona", person);
            instanciasEliminadas = query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instanciasEliminadas;
    }


}

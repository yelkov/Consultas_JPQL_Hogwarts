package edu.badpals.hogwarts;

import edu.badpals.hogwarts.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Consultas consultas = new Consultas();

        Person profesor = em.find(Person.class, 99);
        List<Person> estudiantes = consultas.getPersonasByProfesor(em,profesor);
        estudiantes.forEach(System.out::println);

        Person harry = consultas.getMaxReceiver(em);
        System.out.println("\n"+harry+"\n");

        Person dumbledore = consultas.getMaxgiver(em);
        System.out.println("\n"+dumbledore+"\n");


        em.close();
        emf.close();
    }
}

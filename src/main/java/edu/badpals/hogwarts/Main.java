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
        System.out.println("\nLista de personas que tienen como profesor a "+profesor.getFirstName()+" "+profesor.getLastName()+":");
        estudiantes.forEach(System.out::println);

        System.out.println("\nPersona que más puntos recibió:");
        Person harry = consultas.getMaxReceiver(em);
        System.out.println(harry);

        System.out.println("\nPersona que más puntos concedió:");
        Person dumbledore = consultas.getMaxgiver(em);
        System.out.println(dumbledore);

        List<String> nombreProfesores = consultas.getNombresProfesores(em);
        System.out.println("\nPROFESORES:");
        nombreProfesores.forEach(System.out::println);

        List<String> nombresCursoProfesor = consultas.getNombresCursoProfesor(em);
        System.out.println("\nNOMBRE CURSO: NOMBRE PROFESOR");
        nombresCursoProfesor.forEach(System.out::println);

        List<String> nombresConPtosSobreMedia = consultas.getNomPersonasPtosSobreMedia(em);
        System.out.println("\nPersonas con puntos sobre la media:");
        nombresConPtosSobreMedia.forEach(System.out::println);

        Person peterPettigrew = em.find(Person.class, 90);
        int personasEliminadas = consultas.deletePersona(em,peterPettigrew);
        if(personasEliminadas == 0){
            System.out.println("\nNo se ha eliminado a nadie");
        }else{
            System.out.println(personasEliminadas > 1? "\nSe han eliminado a "+personasEliminadas + " personas":
                                                        "\nSe ha eliminado a 1 persona");
        }

        em.close();
        emf.close();
    }
}

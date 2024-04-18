package org.example;

import domain.*;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Service;
import domain.Student;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class IntegrationTestGrade { public static Service service;
    @BeforeAll
    public  static  void setup(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @Test
    public void addStudent_ValidData_CreatedSuccessfully() {
        String idStudent = "55";
        String numeStudent = "test";
        int grupa = 934;
        service.saveStudent(idStudent, numeStudent, grupa);
        final List<Student> studs = new ArrayList<Student>();
        Iterator<Student> itStud = service.findAllStudents().iterator();
        itStud.forEachRemaining(studs::add);
        assert(studs.stream().map(s->s.getID()).anyMatch(id->id.equals("55")));
    }

    @Test
    public void addAssignment_ValidData(){
        String idAssignment = "50";
        String descriere = "Test";
        int deadline = 3;
        int startline = 2;
        service.saveTema(idAssignment, descriere, deadline, startline);
        final List<Tema> temas = new ArrayList<Tema>();
        Iterator<Tema> itTema = service.findAllTeme().iterator();
        itTema.forEachRemaining(temas::add);
        assert(temas.stream().map(t->t.getID()).anyMatch(id->id.equals("50")));
    }

    @Test
    public void addGrade_ValidExistingData(){
        String idStudent = "1";
        String idAssignment = "3";
        double valNota = 9;
        int predata =8;
        String feedback = "done";

        service.saveNota(idStudent, idAssignment, valNota, predata, feedback);
        final List<Nota> grades = new ArrayList<Nota>();
        Iterator<Nota> itNota = service.findAllNote().iterator();
        itNota.forEachRemaining(grades::add);
        assert (grades.stream().map(g->g.getID()).anyMatch(id->id.equals(new Pair(idStudent,idAssignment))));
    }

    @Test
    public void integrationTest_ValidGrade(){
        String idStudent = "67";
        String idAssignment = "67";
        double valNota = 9;
        int predata =3;
        String feedback = "done";
        String numeStudent = "test";
        int grupa = 934;

        String descriere = "Test";
        int deadline = 3;
        int startline = 2;
        service.saveStudent(idStudent, numeStudent, grupa);
        service.saveTema(idAssignment, descriere, deadline, startline);

        service.saveNota(idStudent, idAssignment, valNota, predata, feedback);
        final List<Nota> grades = new ArrayList<Nota>();
        Iterator<Nota> itNota = service.findAllNote().iterator();
        itNota.forEachRemaining(grades::add);
        assert (grades.stream().map(g->g.getID()).anyMatch(id->id.equals(new Pair(idStudent,idAssignment))));

    }

}


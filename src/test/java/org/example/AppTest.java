package org.example;

import domain.Student;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Service;
import domain.Nota;
import domain.Student;
import domain.Tema;
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
public class AppTest
{
    public static Service service;
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
    public void addStudent_NoId_ThrowsError() {
        String idStudent = null;
        String numeStudent = "test";
        int grupa = 934;
        service.saveStudent(idStudent, numeStudent, grupa);
        final List<Student> studs = new ArrayList<Student>();
        Iterator<Student> itStud = service.findAllStudents().iterator();
        itStud.forEachRemaining(studs::add);
        assert(studs.stream().map(s->s.getID()).anyMatch(id->id.equals(null)));
    }
}


package pl.edu.agh.iisg.to.dao;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;

public class StudentDao extends GenericDao<Student> {

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        Student student = new Student(firstName, lastName, indexNumber);
        try {
            save(student);
            return Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        try (Session session = currentSession()) {
            Student student = session.createQuery("Select s FROM Student s WHERE s.indexNumber = :indexNumber", Student.class).setParameter("indexNumber", indexNumber).getSingleResult();
            return Optional.of(student);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Map<Course, Float> createReport(final Student student) {
        Map<Course, Float> report = new java.util.HashMap<>(Collections.emptyMap());

        for (Grade grade : student.gradeSet()) {
            if (report.containsKey(grade.course())) {
                float sum = report.get(grade.course());
                sum += grade.grade();
                report.put(grade.course(), sum);
            } else {
                report.put(grade.course(), grade.grade());
            }
        }

        for (Course course : report.keySet()) {
            float sum = report.get(course);
            int count = student.gradeSet().stream().filter(grade -> grade.course().equals(course)).mapToInt(grade -> 1).sum();
            report.put(course, sum/count);
        }

        return report;
    }

}

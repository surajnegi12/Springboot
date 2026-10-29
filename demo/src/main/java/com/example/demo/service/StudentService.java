package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE
    public Student createStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    // READ (ALL)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // READ (BY ID)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
    }

    // UPDATE
    public Student updateStudent(Long id, Student updatedDetails) {
        Student student = getStudentById(id);
        student.setName(updatedDetails.getName());
        student.setEmail(updatedDetails.getEmail());
        student.setDob(updatedDetails.getDob());
        return studentRepository.save(student);
    }

    // DELETE
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
package com.RamaIT.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RamaIT.Models.Executive;
import com.RamaIT.Models.Student;
import com.RamaIT.Repositories.ExecutiveRepository;
import com.RamaIT.Repositories.StudentRepository;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepo;
	@Autowired
	ExecutiveRepository executiveRepo;
	public void updateStudent(Student student) {
		Student save = studentRepo.save(student);
	}
	public void upsertStudentwithExecutive(Student student, Executive executive) {
		executive = executiveRepo.findByEmailAndPassword(executive.getEmail(), executive.getPassword());
		executive.getStudents();
		executive.addStudent(student);
		Executive save = executiveRepo.save(executive);
	}

	public List<Student> getAllStudentsForExecutive(Executive executive) {
		executive = executiveRepo.findByEmailAndPassword(executive.getEmail(), executive.getPassword());
		List<Student> students = executive.getStudents();
		return students;
	}
	public Student getStudentById(Integer index) {
		Student student = studentRepo.getReferenceById(index);
		return student;
	}
}

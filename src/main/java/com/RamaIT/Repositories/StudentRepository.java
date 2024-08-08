package com.RamaIT.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RamaIT.Models.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}

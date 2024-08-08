package com.RamaIT.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.RamaIT.Enums.Status;
import com.RamaIT.Models.Executive;

public interface ExecutiveRepository extends JpaRepository<Executive, Integer>{
	Executive findByEmailAndPassword(String email,String password);

    @Query("select count(u) from Executive u left join u.students i where i.status =:status and u.id =:id ")
    Integer findUserWithStatus(Status status,Integer id);
    
    @Query("select count(u) from Executive u left join u.students i where u.id =:id and i.id IS NOT NULL")
    Integer findTotalStudents(Integer id);

}

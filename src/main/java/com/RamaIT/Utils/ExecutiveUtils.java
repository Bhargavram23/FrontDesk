package com.RamaIT.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.RamaIT.Enums.Status;
import com.RamaIT.Repositories.ExecutiveRepository;

@Component
public class ExecutiveUtils {
	@Autowired
	ExecutiveRepository executiveRepo;
	public int totalStudents(Integer id) {
		return executiveRepo.findTotalStudents(id);

	}

	public int totalSuccesfulStudents(Integer id) {
		return executiveRepo.findUserWithStatus(Status.GOT,id);
	}

	public int totalFailStudents(Integer id) {
		return executiveRepo.findUserWithStatus(Status.LOST,id);
	}

}

package com.RamaIT.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.RamaIT.Models.Executive;
import com.RamaIT.Repositories.ExecutiveRepository;

@Service
public class LoginService {
	@Autowired
	ExecutiveRepository executiveRepo;

	// Tells if executive is in database
	public boolean isValidExecutive(Executive executive) {
		Optional<Executive> inDBExective = executiveRepo.findOne(Example.of(executive));
		if (!inDBExective.isEmpty())
			return true;
		else {
			return false;
		}
	}

	// Tells if executive is verified in database
	public boolean isVerfiedExecutive(Executive executive) {
		Optional<Executive> inDBExective = executiveRepo.findOne(Example.of(executive));
		if (!inDBExective.isEmpty())
			return inDBExective.get().isVerified();

		return false;
	}

	public Executive addUnVerifiedExecutive(Executive executive) {
		Executive savedEntity = executiveRepo.save(executive);
		return savedEntity;
	}

	public Executive verifyUser(Executive executive) {
		executive.setVerified(true);
		Executive savedEntity = executiveRepo.save(executive);
		return savedEntity;
	}
	
	public Optional<Executive> getExecutiveById(Integer id) {
		Optional<Executive> executiveRecieved = executiveRepo.findById(id);
		return executiveRecieved;
	}

	public Executive getExecutiveByExample(Example<Executive> executiveExample) {
		Executive executive =  (Executive) executiveRepo.findByEmailAndPassword(executiveExample.getProbe().getEmail(), executiveExample.getProbe().getPassword());
		return executive;
	}

}

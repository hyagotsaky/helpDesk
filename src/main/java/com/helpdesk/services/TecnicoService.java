package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.domain.Tecnico;
import com.helpdesk.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow( ()-> new ObjectNotFoundException("Objeto não encontrado! Id:" + id));
		
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

}

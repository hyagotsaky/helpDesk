package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.dtos.ChamadosDto;
import com.helpdesk.repositories.ChamadoRepository;
import com.helpdesk.resources.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
	
	@Autowired
	private ChamadoRepository repository;
	
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}

}

package com.helpdesk.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.dtos.ChamadosDto;
import com.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadosDto> findById(@PathVariable Integer id) {
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadosDto(obj));
		
	}
	
	
}

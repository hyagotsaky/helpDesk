package com.helpdesk.resources;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.dtos.ChamadosDto;
import com.helpdesk.services.ChamadoService;

import jakarta.validation.Valid;

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

	@GetMapping
	public ResponseEntity<List<ChamadosDto>> findAll() {
		List<Chamado> list = service.findAll();
		List<ChamadosDto> listDto = list.stream().map(obj -> new ChamadosDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);

	}

	@PostMapping
	public ResponseEntity<ChamadosDto> create(@Valid @RequestBody ChamadosDto objDTO) {
		Chamado obj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadosDto> update(@PathVariable Integer id, @Valid @RequestBody ChamadosDto objDTO) {
		Chamado newObj = service.update(id,objDTO );
		return ResponseEntity.ok().body(new ChamadosDto(newObj));
		
	}

}

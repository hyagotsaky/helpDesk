package com.helpdesk.resources;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.domain.Tecnico;
import com.helpdesk.domain.dtos.TecnicoDto;
import com.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDto> findById(@PathVariable Integer id) {
		Tecnico obj = service.findById(id);
		return ResponseEntity.ok(new TecnicoDto(obj));
		
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDto>> findAll(){
		 List<Tecnico> list = service.findAll();
		 List<TecnicoDto> listDTO = list.stream().map(obj -> new TecnicoDto(obj)).collect(Collectors.toList());
		 System.out.println(listDTO);
		 return ResponseEntity.ok().body(listDTO);
	}
	
}

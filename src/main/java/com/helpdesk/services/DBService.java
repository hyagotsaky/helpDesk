package com.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.Cliente;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.enums.Perfil;
import com.helpdesk.enums.Prioridade;
import com.helpdesk.enums.Status;
import com.helpdesk.repositories.ChamadoRepository;
import com.helpdesk.repositories.ClienteRepository;
import com.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	
	public void instanciaDB () {
		Tecnico tec1 = new Tecnico(null, "Hyago Souza","43845638095","hyagotsaky@gmail.com","123");
		tec1.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Larissa", "73873717093", "larissa@gmail.com", "202122");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 01", "primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}

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
		Tecnico tec2 = new Tecnico(null, "Tayron Souza","86625422045","tayron@gmail.com","2556");
		tec2.addPerfis(Perfil.ADMIN);
		Tecnico tec3 = new Tecnico(null, "Pedro Silva","99201672071","pedro66@gmail.com","123");
		tec3.addPerfis(Perfil.ADMIN);
		Tecnico tec4 = new Tecnico(null, "Maria Pereira","15184051007","maria12@gmail.com","123");
		tec4.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Larissa", "73873717093", "larissa@gmail.com", "202122");
		Cliente cli2 = new Cliente(null, "Manuela", "81287569072", "manuela@gmail.com", "326555");
		Cliente cli3 = new Cliente(null, "Fernando", "85016827052", "fernando@gmail.com", "989862");
		Cliente cli4 = new Cliente(null, "Roberto", "47250307088", "roberto@gmail.com", "565448");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 01", "primeiro chamado", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 02", "segundo chamado", tec2, cli1);
		Chamado c3 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 03", "terceiro chamado", tec3, cli1);
		Chamado c4 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 04", "quarto chamado", tec1, cli1); 
		
		tecnicoRepository.saveAll(Arrays.asList(tec1, tec2,tec3,tec4));
		clienteRepository.saveAll(Arrays.asList(cli1, cli2,cli3,cli4));
		chamadoRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
	}

}

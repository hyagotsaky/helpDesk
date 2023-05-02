package com.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.helpdesk.domain.Chamado;
import com.helpdesk.domain.Cliente;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.enums.Perfil;
import com.helpdesk.enums.Prioridade;
import com.helpdesk.enums.Status;
import com.helpdesk.repositories.ChamadoRepository;
import com.helpdesk.repositories.ClienteRepository;
import com.helpdesk.repositories.TecnicoRepository;


@EntityScan(basePackages = {"com.helpdesk.domain" , "repositories"})
@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner{
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Hyago Souza","43845638095","hyagotsaky@gmail.com","123");
		tec1.addPerfis(Perfil.ADMIN);
		
		Cliente cli1 = new Cliente(null, "Larissa", "73873717093", "larissa@gmail.com", "202122");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA , Status.ANDAMENTO, "chamado 01", "primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}

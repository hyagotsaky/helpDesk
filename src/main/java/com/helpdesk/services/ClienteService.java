package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.domain.Cliente;
import com.helpdesk.domain.Pessoa;
import com.helpdesk.domain.dtos.ClienteDto;
import com.helpdesk.repositories.ClienteRepository;
import com.helpdesk.repositories.PessoaRepository;
import com.helpdesk.resources.exceptions.DataIntegrityViolationException;
import com.helpdesk.resources.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id));

	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente create(ClienteDto objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validaPorCpfEmail(objDto);
		Cliente newObj = new Cliente(objDto);
		return repository.save(newObj);
	}

	private void validaPorCpfEmail(ClienteDto objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}

		obj = pessoaRepository.findByEmail(objDto.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema");
		}

	}

	public Cliente update(Integer id, @Valid ClienteDto objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);

	}

}

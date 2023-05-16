package com.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.domain.Pessoa;
import com.helpdesk.domain.Tecnico;
import com.helpdesk.domain.dtos.TecnicoDto;
import com.helpdesk.repositories.PessoaRepository;
import com.helpdesk.repositories.TecnicoRepository;
import com.helpdesk.resources.exceptions.DataIntegrityViolationException;
import com.helpdesk.resources.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id));

	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDto objDto) {
		objDto.setId(null);
		objDto.setSenha(encoder.encode(objDto.getSenha()));
		validaPorCpfEmail(objDto);
		Tecnico newObj = new Tecnico(objDto);
		return repository.save(newObj);
	}

	private void validaPorCpfEmail(TecnicoDto objDto) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDto.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
		}

		obj = pessoaRepository.findByEmail(objDto.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDto.getId()) {
			throw new DataIntegrityViolationException("Email já cadastrado no sistema");
		}

	}

	public Tecnico update(Integer id, @Valid TecnicoDto objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaPorCpfEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if (obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Tecnico possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);

	}

}

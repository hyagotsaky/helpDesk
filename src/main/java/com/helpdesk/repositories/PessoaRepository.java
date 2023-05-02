package com.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.domain.Pessoa;
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}

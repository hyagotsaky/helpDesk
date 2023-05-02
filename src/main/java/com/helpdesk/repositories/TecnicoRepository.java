package com.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.helpdesk.domain.Tecnico;
@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}

package com.algaworks.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.model.Estado;

@Repository
public interface Cidades extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstadoCodigo(Long codigoEstado);
	Optional<Cidade> findByNomeIgnoringCaseAndEstado(String nomeCidade, Estado estado); 
}

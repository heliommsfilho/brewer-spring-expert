package com.algaworks.brewer.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;

public interface VendasQueries {

	Page<Venda> filtrar(VendaFilter filter, Pageable pageable);
	Venda buscarComItens(Long codigo);
}

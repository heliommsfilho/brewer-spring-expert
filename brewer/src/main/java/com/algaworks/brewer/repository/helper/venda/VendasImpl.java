package com.algaworks.brewer.repository.helper.venda;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class VendasImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Venda> filtrar(VendaFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltros(filter, criteria);
		
		List<Venda> vendas = criteria.list();
		return new PageImpl<>(vendas, pageable, total(filter));
	}

	private void adicionarFiltros(VendaFilter filter, Criteria criteria) {
		criteria.createAlias("cliente", "c");
		
		if (filter != null) {
			
			if (!StringUtils.isEmpty(filter.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filter.getCodigo()));
			}
			
			if (!StringUtils.isEmpty(filter.getStatus())) {
				criteria.add(Restrictions.eq("status", filter.getStatus()));
			}
			
			if (filter.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(filter.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}
			
			if (filter.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filter.getAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}
			
			if (filter.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filter.getValorMinimo()));
			}
			
			if (filter.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filter.getValorMaximo()));
			}
			
			if (!StringUtils.isEmpty(filter.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.nome", filter.getNomeCliente(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filter.getCpfOuCnpjCliente())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", TipoPessoa.removerFormatacao(filter.getCpfOuCnpjCliente())));
			}
		}
	}

	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltros(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
}

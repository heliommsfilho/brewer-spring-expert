package com.algaworks.brewer.dto;

import java.math.BigDecimal;

public class ValorItensEstoque {

	private Long quantidadeTotalEstoque;
	private BigDecimal valorTotalEstoque;
	
	public ValorItensEstoque() {
		super();
	}

	public ValorItensEstoque(BigDecimal valorTotalEstoque, Long quantidadeTotalEstoque) {
		super();
		this.quantidadeTotalEstoque = quantidadeTotalEstoque;
		this.valorTotalEstoque = valorTotalEstoque;
	}

	public BigDecimal getValorTotalEstoque() {
		return valorTotalEstoque;
	}
	
	public void setValorTotalEstoque(BigDecimal valorTotalEstoque) {
		this.valorTotalEstoque = valorTotalEstoque;
	}
	
	public Long getQuantidadeTotalEstoque() {
		return quantidadeTotalEstoque;
	}
	
	public void setQuantidadeTotalEstoque(Long quantidadeTotalEstoque) {
		this.quantidadeTotalEstoque = quantidadeTotalEstoque;
	}
}

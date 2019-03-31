package com.algaworks.brewer.dto;

import java.math.BigDecimal;

public class DadosEstoqueDTO {

	private Long quantidadeTotalEstoque;
	private BigDecimal valorTotalEstoque;
	
	public DadosEstoqueDTO() {
		super();
	}

	public DadosEstoqueDTO(Long quantidadeTotalEstoque, BigDecimal valorTotalEstoque) {
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

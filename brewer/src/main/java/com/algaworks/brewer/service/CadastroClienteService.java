package com.algaworks.brewer.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastradoException;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		
		if (clienteExistente.isPresent() && !cliente.getCodigo().equals(clienteExistente.get().getCodigo())){
			throw new CpfCnpjClienteJaCadastradoException("CPF/CNPJ já cadastrado");
		}		
		
		clientes.save(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		
		try {
			clientes.delete(cliente);
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir o cliente. Já existem vendas associadas a ele");
		}
	}

}

package com.algaworks.brewer.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	Optional<Usuario> usuarioEmailAtivo(String email);
	List<String> permissoes(Usuario usuario);
	List<Usuario> filtrar(UsuarioFilter filtro);
}

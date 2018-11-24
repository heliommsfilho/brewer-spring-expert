package com.algaworks.brewer.repository.helper.usuario;

import java.util.Optional;

import com.algaworks.brewer.model.Usuario;

public interface UsuariosQueries {

	Optional<Usuario> usuarioEmailAtivo(String email);
}

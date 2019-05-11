package com.algaworks.brewer.exception;

public class SenhaObrigatoriaUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 3992356268945228209L;

	public SenhaObrigatoriaUsuarioException(String message) {
		super(message);
	}
}

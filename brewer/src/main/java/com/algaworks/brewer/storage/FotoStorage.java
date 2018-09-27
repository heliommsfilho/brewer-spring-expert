package com.algaworks.brewer.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

	public String salvarTemporariamente(MultipartFile[] files);

	public byte[] recuperarFotoTemporaria(String nomeFoto);

	public void salvar(String foto);

	public byte[] recuperar(String nomeFoto);
}

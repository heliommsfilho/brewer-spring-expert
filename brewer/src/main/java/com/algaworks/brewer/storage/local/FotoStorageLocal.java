package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	
	private Path local;
	private Path localTemporario;
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOME"), ".brewerFotos"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarTemporariamente(MultipartFile[] files) {
		
		String novoNome = null;
		
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				arquivo.transferTo(new File(this.localTemporario.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar a fot na past teporária");
			}
		}
		
		return novoNome;
	}
	
	@Override
	public byte[] recuperarFotoTemporaria(String nomeFoto) {
		try {
			return Files.readAllBytes(this.localTemporario.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto temporária");
		}
	}
	
	private void criarPastas() {
		
		try {
			Files.createDirectories(this.local);
			this.localTemporario = FileSystems.getDefault().getPath(this.local.toString(), "temp");
			Files.createDirectories(this.localTemporario);
			
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas das fotos criadas");
				LOGGER.debug("Pasta default: " + this.local.toString());
				LOGGER.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar a foto", e);
		}
	}
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(String.format("Nome original: %s, novo nome: %s", nomeOriginal, novoNome));
			}
			
			return novoNome;
	}
}

package com.algaworks.brewer.storage.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

@Profile("!prod")
@Component
public class FotoStorageLocal implements FotoStorage {

	private static final Logger LOGGER = LoggerFactory.getLogger(FotoStorageLocal.class);
	static final String THUMBNAIL_PREFIX = "thumbnail.";
	
	private Path local;
	
	public FotoStorageLocal() {
		this(FileSystems.getDefault().getPath(System.getenv("HOMEPATH"), ".brewerFotos"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}
	
	@Override
	public byte[] recuperar(String nomeFoto) {
		
		try {
			return Files.readAllBytes(this.local.resolve(nomeFoto));
		} catch (IOException e) {
			throw new RuntimeException("Erro ao ler a foto temporária");
		}
	}
	
	@Override
	public byte[] recuperarThumbnail(String fotoCerveja) {
		return recuperar(THUMBNAIL_PREFIX + fotoCerveja);
	}
	
	@Override
	public void excluir(String foto) {
		try {
			Files.deleteIfExists(this.local.resolve(foto));
			Files.deleteIfExists(this.local.resolve(THUMBNAIL_PREFIX + foto));
		} catch (IOException e) {
			LOGGER.warn(String.format("Erro ao apagar foto '%s'. Mensagem: %s", foto, e.getMessage()));
		}
	}
	
	@Override
	public String getUrl(String foto) {
		return "http://localhost:8080/brewer/fotos/" + foto;
	}

	@Override
	public String salvar(MultipartFile[] files) {
		
		String novoNome = null;
		
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			
			try {
				arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + FileSystems.getDefault().getSeparator() + novoNome));
			} catch (IOException e) {
				throw new RuntimeException("Erro ao salvar a foto", e);
			}
		}
		
		try {
			Thumbnails.of(this.local.resolve(novoNome).toString()).size(40, 68).toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao gerar thumbnail");
		}
		
		return novoNome;
	}
	
	private void criarPastas() {
		
		try {
			Files.createDirectories(this.local);
			
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Pastas das fotos criadas");
				LOGGER.debug("Pasta default: " + this.local.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar pasta para salvar a foto", e);
		}
	}
}

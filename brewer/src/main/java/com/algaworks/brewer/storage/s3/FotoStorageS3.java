package com.algaworks.brewer.storage.s3;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.brewer.storage.FotoStorage;

@Component
public class FotoStorageS3 implements FotoStorage {

	@Override
	public String salvar(MultipartFile[] files) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] recuperar(String nomeFoto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] recuperarThumbnail(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excluir(String foto) {
		// TODO Auto-generated method stub
		
	}

}

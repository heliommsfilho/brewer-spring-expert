package com.algaworks.brewer.mail;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	@Async
	public void enviar() {
		
		System.out.println("Eviando email");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Email enviado");
	}
}

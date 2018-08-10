package com.algaworks.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EstilosController {

	@RequestMapping(value="/estilos/novo", method=RequestMethod.GET)
	public String novo() {
		return "estilo/CadastroEstilo";
	}
}

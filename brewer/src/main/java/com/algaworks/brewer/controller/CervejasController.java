package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.Cerveja;

@Controller
public class CervejasController {

	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.GET)
	public String novo() {
		return "cerveja/CadastroCerveja";
	}
	
	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST)
	public String cadastrar(@Valid Cerveja cerveja, BindingResult result) {
		
		if (result.hasErrors()) {
			System.out.println("Erro de validação");
		}
		
		System.out.println("Chamou Cadastrar ---> " + cerveja.getSku());
		return "cerveja/CadastroCerveja";
	}
}
 
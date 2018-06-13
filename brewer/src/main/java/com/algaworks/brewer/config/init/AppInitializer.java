package com.algaworks.brewer.config.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.WebConfig;

/* Configurações do Front Controller do Spring (DispatcherServlet) */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	/* Ensina o Spring onde achar os Controllers */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/* Define o padrão de URL que o DispatchetServlet via gerenciar */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}

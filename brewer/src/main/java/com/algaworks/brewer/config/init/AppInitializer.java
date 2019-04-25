package com.algaworks.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.config.JPAConfig;
import com.algaworks.brewer.config.MailConfig;
import com.algaworks.brewer.config.S3Config;
import com.algaworks.brewer.config.SecurityConfig;
import com.algaworks.brewer.config.ServiceConfig;
import com.algaworks.brewer.config.WebConfig;

/* Configurações do Front Controller do Spring (DispatcherServlet) */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class, SecurityConfig.class, S3Config.class }; // Registra as configurações
	}

	/* Ensina o Spring onde achar os Controllers */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class, MailConfig.class };
	}

	/* Define o padrão de URL que o DispatchetServlet via gerenciar */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/* Força a codificação para UTF-8 */
	@Override
	protected Filter[] getServletFilters() {	
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		return new Filter[] { httpPutFormContentFilter };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("spring.profiles.default", "local");
	}
}

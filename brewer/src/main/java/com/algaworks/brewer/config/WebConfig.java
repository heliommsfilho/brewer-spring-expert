package com.algaworks.brewer.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.brewer.controller.CervejasController;
import com.algaworks.brewer.controller.converter.CidadeConveter;
import com.algaworks.brewer.controller.converter.EstadoConveter;
import com.algaworks.brewer.controller.converter.EstiloConverter;
import com.algaworks.brewer.controller.converter.GrupoConveter;
import com.algaworks.brewer.session.TabelaItensSession;
import com.algaworks.brewer.thymeleaf.BrewerDialect;
import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { CervejasController.class, TabelaItensSession.class })
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
@EnableAsync
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
	}
	
//	@Bean
//	public ViewResolver jasperReportsViewResolver(DataSource dataSource) {
//		JasperReportsViewResolver resolver = new JasperReportsViewResolver();
//		resolver.setPrefix("classpath:/relatorios/");
//		resolver.setSuffix(".jasper");
//		resolver.setViewNames("relatorio_*");
//		resolver.setViewClass(JasperReportsMultiFormatView.class);
//		resolver.setJdbcDataSource(dataSource);
//		resolver.setOrder(0);
//		
//		return resolver;
//	}
	
	/* Define o Thymeleaf como View Resolver */
	@Bean 
	public ViewResolver ViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setOrder(1);
		
		return resolver;
	}
	/* Configura o processador de templates do Spring utilizando o templateResolver() */
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		
		/* Adicionado dialeto do Thymelead Layout Dialect */
		engine.addDialect(new LayoutDialect());
		engine.addDialect(new BrewerDialect());
		engine.addDialect(new DataAttributeDialect());
		engine.addDialect(new SpringSecurityDialect());
		
		return engine;
	}
	
	// Define conversores por tipo
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		conversionService.addConverter(new CidadeConveter());
		conversionService.addConverter(new EstadoConveter());
		conversionService.addConverter(new GrupoConveter());
		
		// Define o formato esperado para os números da aplicação (sensível à linguagem do navegador)
//		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
//		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		
		BigDecimalFormatter bigDecimalFormatter = new BigDecimalFormatter("#,##0.00");
		BigDecimalFormatter integerFormatter = new BigDecimalFormatter("#,##0");
		
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		// API de datas Java8+
		DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
		dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatterRegistrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	// Força o Spring a interpretar o formato dos números como sendo pt-BR (independente da configuração do browser)
//	@Bean
//	public LocaleResolver localeResolver() {
//		return new FixedLocaleResolver(new Locale("pt", "BR"));
//	}
	
	/* Configura onde achar os templates */
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public CacheManager cacheManager() {
//		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
//		GuavaCacheManager cacheManager = new GuavaCacheManager();
//		cacheManager.setCacheBuilder(cacheBuilder);
//		return cacheManager;
		return new ConcurrentMapCacheManager();
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("classpath:/messages");
		bundle.setDefaultEncoding("UTF-8");
		
		return bundle;
	}
	
	/* Permite receber uma entidade direto  */
	@Bean
	public DomainClassConverter<?> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource());
		
		return validatorFactoryBean;
	}

	@Override
	public Validator getValidator() {
		return validator();
	}
}

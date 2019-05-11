package com.algaworks.brewer.config.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeFormatter extends TemporalFormatter<LocalDateTime> {

	@Autowired
	private Environment env;
	
	@Override
	public String pattern(Locale locale) {
		return env.getProperty("localdate.format-" + locale, "dd/MM/yyyy HH:mm");
	}

	@Override
	public LocalDateTime parse(String texto, DateTimeFormatter formatter) {
		return LocalDateTime.parse(texto, formatter);
	}

}

package br.com.collegesmaster.util;

import java.util.Locale;

import javax.validation.MessageInterpolator;

public class LocaleSpecificMessageInterpolator implements MessageInterpolator {
	
	private final MessageInterpolator defaultInterpolator;
	private final Locale defaultLocale;
		
	public LocaleSpecificMessageInterpolator(MessageInterpolator interpolator,
			Locale locale) {
		this.defaultInterpolator = interpolator;
		this.defaultLocale = locale;
	}
	
	@Override
	public String interpolate(String messageTemplate, Context context) {
		return defaultInterpolator
				.interpolate(messageTemplate, context, this.defaultLocale);
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {		
		return defaultInterpolator
				.interpolate(messageTemplate, context, locale);
	}
}

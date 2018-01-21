package br.com.collegesmaster.config;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

public class ApplicationConfigurationProvider extends HttpConfigurationProvider {

	@Override
	public Configuration getConfiguration(ServletContext context) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/").to("/pages/login.xhtml"))
		        .addRule(Join.path("/edit_user").to("/pages/edit_user.xhtml"))
		        .addRule(Join.path("/create_challenge").to("/pages/professor/create_challenge.xhtml"))
		        .addRule(Join.path("/reply_challenges").to("/pages/student/reply_challenges.xhtml"))
		        .addRule(Join.path("/completed_challenges").to("/pages/student/completed_challenges.xhtml"))
		        .addRule(Join.path("/created_challenges").to("/pages/professor/created_challenges.xhtml"));
	}

	@Override
	public int priority() {
		return 0;
	}

}

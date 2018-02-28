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
				.addRule(Join.path("/").to("/pages/login.xhtml").withInboundCorrection())
		        .addRule(Join.path("/edit_user").to("/pages/edit_user.xhtml").withInboundCorrection())
		        .addRule(Join.path("/ranking").to("/pages/ranking.xhtml").withInboundCorrection())
		        .addRule(Join.path("/create_challenge").
		        		to("/pages/professor/create_challenge.xhtml").withInboundCorrection())
		        .addRule(Join.path("/reply_challenges")
		        		.to("/pages/student/reply_challenges.xhtml").withInboundCorrection())
		        .addRule(Join.path("/completed_challenges")
		        		.to("/pages/student/completed_challenges.xhtml").withInboundCorrection())
		        .addRule(Join.path("/created_challenges")
		        		.to("/pages/professor/created_challenges.xhtml").withInboundCorrection());
	}

	@Override
	public int priority() {
		return 0;
	}

}

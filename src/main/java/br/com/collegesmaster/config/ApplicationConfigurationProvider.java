package br.com.collegesmaster.config;

import static br.com.collegesmaster.view.enums.Page.COMPLETED_CHALLENGES;
import static br.com.collegesmaster.view.enums.Page.CREATED_CHALLENGES;
import static br.com.collegesmaster.view.enums.Page.CREATE_CHALLENGE;
import static br.com.collegesmaster.view.enums.Page.EDIT_USER;
import static br.com.collegesmaster.view.enums.Page.LOGOUT;
import static br.com.collegesmaster.view.enums.Page.RANKING;
import static br.com.collegesmaster.view.enums.Page.REPLY_CHALLENGES;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

public class ApplicationConfigurationProvider extends HttpConfigurationProvider {

	@Override
	public Configuration getConfiguration(ServletContext context) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/").to(LOGOUT.getPage()).withInboundCorrection())
		        .addRule(Join.path("/user_edit").to(EDIT_USER.getPage()).withInboundCorrection())
		        .addRule(Join.path("/ranking").to(RANKING.getPage()).withInboundCorrection())
		        .addRule(Join.path("/create_challenge").to(CREATE_CHALLENGE.getPage()).withInboundCorrection())
		        .addRule(Join.path("/reply_challenges").to(REPLY_CHALLENGES.getPage()).withInboundCorrection())
		        .addRule(Join.path("/completed_challenges").to(COMPLETED_CHALLENGES.getPage()).withInboundCorrection())
		        .addRule(Join.path("/created_challenges").to(CREATED_CHALLENGES.getPage()).withInboundCorrection());
	}

	@Override
	public int priority() {
		return 0;
	}
}

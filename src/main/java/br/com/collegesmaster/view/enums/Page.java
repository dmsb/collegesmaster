package br.com.collegesmaster.view.enums;

import java.util.Arrays;

import br.com.collegesmaster.exceptions.BusinessException;

public enum Page {
	RANKING("ranking", "/pages/ranking.xhtml"),
	CREATE_CHALLENGE("create_challenge", "/pages/professor/create_challenge.xhtml"),
	REPLY_CHALLENGES("reply_challenges", "/pages/student/reply_challenges.xhtml"),
	COMPLETED_CHALLENGES("completed_challenges", "/pages/student/completed_challenges.xhtml"),
	CREATED_CHALLENGES("created_challenges", "/pages/professor/created_challenges.xhtml"),
	EDIT_USER("edit_user", "/pages/edit_user.xhtml"),
	LOGIN("login", "/pages/login.xhtml");
	
	private String code;
	
	private String page;
	
	private Page(String code, String page) {
		this.code = code;
		this.page = page; 
	}
	
	public String getCode() {
		return code;
	}
	
	public String getPage() {
		return page;
	}
	
	public static String getPageByCode(final String code) {
		return Arrays.stream(values())
			.filter(page -> page.getCode().equals(code))
			.findFirst()
			.map(page -> { return page.getPage();})
			.orElseThrow(() -> new BusinessException("page_not_found_message"));
	}
}

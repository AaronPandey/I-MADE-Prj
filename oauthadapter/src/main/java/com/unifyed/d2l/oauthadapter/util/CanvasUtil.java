package com.unifyed.d2l.oauthadapter.util;

import java.util.HashMap;
import java.util.Map;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class CanvasUtil {

	public static Map<String, Object> processCanvasAuth(WebClient webClient, Map<String, String> canvasParams) {
		Map<String, Object> result = new HashMap();
		String host = (String) canvasParams.get("host");
		String client_id = (String) canvasParams.get("client_id");
		String response_type = (String) canvasParams.get("response_type");
		String redirect_uri = (String) canvasParams.get("redirect_uri");
		String username = (String) canvasParams.get("username");
		String password = (String) canvasParams.get("password");
		String submit1XPath = (String) canvasParams.get("submit1XPath");
		String submit2XPath = (String) canvasParams.get("submit2XPath");
		if ((submit1XPath == null) || (submit1XPath.isEmpty())) {
			submit1XPath = "//input[contains(@name,'submit')]";
		}
		if ((submit2XPath == null) || (submit2XPath.isEmpty())) {
			submit2XPath = "//input[contains(@name,'commit')]";
		}
		System.out.println("client_id: " + client_id);
		System.out.println("response_type: " + response_type);
		System.out.println("redirect_uri: " + redirect_uri);
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("Starting execution.....");
		String url = host + "/login/oauth2/auth?client_id=" + client_id + "&response_type=" + response_type
				+ "&redirect_uri=" + redirect_uri;
		System.out.println("URL: " + url);
		try {
			HtmlPage currentPage = (HtmlPage) webClient.getPage(url);
			HtmlInput usernameField = (HtmlInput) currentPage.getFirstByXPath("//input[contains(@id,'username')]");
			HtmlInput passwordField = (HtmlInput) currentPage.getFirstByXPath("//input[contains(@id,'password')]");
			HtmlButton loginBtn = (HtmlButton) currentPage.getFirstByXPath(submit1XPath);
			usernameField.setValueAttribute(username);
			passwordField.setValueAttribute(password);
			HtmlPage redirectPage = (HtmlPage) loginBtn.click();
			System.out.println("Page :"+redirectPage.asText());
			System.out.println("redirect title1 : " + redirectPage.getTitleText().toString());
			System.out.println(redirectPage.getUrl().toString());
			HtmlSubmitInput submitBtn = (HtmlSubmitInput) redirectPage.getFirstByXPath(submit2XPath);
			webClient.getOptions().setRedirectEnabled(false);
			HtmlPage finalPage = (HtmlPage) submitBtn.click();
			String location = finalPage.getWebResponse().getResponseHeaderValue("Location").toString();
			System.out.println("Location : " + location);
			String keyCode = location.split("=")[1];
			System.out.println("Code=" + keyCode);

			result.put("code", keyCode);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("error", "Unknown error");
		}
		return result;
	}

}

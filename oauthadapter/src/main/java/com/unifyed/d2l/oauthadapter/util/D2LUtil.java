package com.unifyed.d2l.oauthadapter.util;

import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.generic.GOTO;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FormEncodingType;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import net.sf.cglib.core.Constants;

public class D2LUtil {

	public static Map<String, String> JsonToMap(JSONObject d2lDataParam) {
		Map<String, String> mp = new HashMap<String, String>();
		mp.put(D2LConstants.D2L_APP_HOST, (String) d2lDataParam.get(D2LConstants.D2L_APP_HOST));
		mp.put(D2LConstants.D2L_APP_ID, (String) d2lDataParam.get(D2LConstants.D2L_APP_ID));
		mp.put(D2LConstants.D2L_APP_KEY, (String) d2lDataParam.get(D2LConstants.D2L_APP_KEY));
		mp.put(D2LConstants.D2L_APP_USERNAME, (String) d2lDataParam.get(D2LConstants.D2L_APP_USERNAME));
		mp.put(D2LConstants.D2L_APP_PASSWORD, (String) d2lDataParam.get(D2LConstants.D2L_APP_PASSWORD));
		mp.put(D2LConstants.D2L_APP_TENANT, (String) d2lDataParam.get(D2LConstants.D2L_APP_TENANT));
		return mp;

	}

	public static String authenticate(WebClient webClient, Map<String, String> inputPram, String baseUrl) {
		try {
			Map<String, Object> result = new HashMap();
			try {
				HtmlPage currentPage = webClient.getPage("https://apitesttool.desire2learnvalence.com/");
				HtmlInput hostField = (HtmlInput) currentPage.getFirstByXPath("//input[contains(@id,'hostField')]");
				HtmlInput appIDField = (HtmlInput) currentPage.getFirstByXPath("//input[contains(@id,'appIDField')]");
				HtmlInput appKeyField = (HtmlInput) currentPage.getFirstByXPath("//input[contains(@id,'appKeyField')]");
				hostField.setValueAttribute(inputPram.get(D2LConstants.D2L_APP_HOST));
				appIDField.setValueAttribute(inputPram.get(D2LConstants.D2L_APP_ID));
				appKeyField.setValueAttribute(inputPram.get(D2LConstants.D2L_APP_KEY)); 
				HtmlSubmitInput authBtn = currentPage.getFirstByXPath("//input[contains(@id,'authenticateBtn')]");
				HtmlPage redirectPage =  authBtn.click();
				webClient.waitForBackgroundJavaScript(20000);
				System.out.println("Title: "  + redirectPage.getTitleText());
				if(redirectPage.getTitleText().equalsIgnoreCase("API Test Tool")) {
					HtmlInput userIDField;
					HtmlInput userKeyField;
					userIDField = redirectPage.getFirstByXPath("//input[contains(@id,'userID')]");
					userKeyField = redirectPage.getFirstByXPath("//input[contains(@id,'userKey')]");
					System.out.println("User ID: "+ userIDField.getValueAttribute());
					System.out.println("User Key: "+ userKeyField.getValueAttribute());
					result.put("userId", userIDField.getValueAttribute());   
					result.put("userKey", userKeyField.getValueAttribute()); 
					return result.toString();
				}				
				HtmlInput target = redirectPage.getFirstByXPath("//input[contains(@id,'target')]");
				HtmlInput username = redirectPage.getFirstByXPath("//input[contains(@id,'userName')]");
				HtmlPasswordInput password = redirectPage.getFirstByXPath("//input[contains(@id,'password')]");					
				username.setValueAttribute(inputPram.get(D2LConstants.D2L_APP_USERNAME));
				password.setValueAttribute(inputPram.get(D2LConstants.D2L_APP_PASSWORD));					
				System.out.println("Target: "+ target.getValueAttribute());
				System.out.println("Username: "+ username.getValueAttribute());
				
				String postUrl = "https" + "://" + inputPram.get(D2LConstants.D2L_APP_HOST) + "/d2l/lp/auth/login/login.d2l";
				System.out.println("PostUrl=" + postUrl);
				URL url = new URL(postUrl);
				WebRequest request = new WebRequest(url, HttpMethod.POST);
				request.setEncodingType(FormEncodingType.MULTIPART);
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new NameValuePair("loginPath", "/d2l/login"));
				params.add(new NameValuePair("userName",inputPram.get(D2LConstants.D2L_APP_USERNAME)));
				params.add(new NameValuePair("password",inputPram.get(D2LConstants.D2L_APP_PASSWORD)));
				params.add(new NameValuePair("target",target.getValueAttribute()));
				request.setRequestParameters(params);
				request.setUrl(url);            
				Page retPage = webClient.getPage(request);
				
				if (retPage instanceof HtmlPage) {
					HtmlPage returnPage = (HtmlPage) retPage;
					System.out.println(returnPage.getTitleText());
					System.out.println("-Host: " + returnPage.getUrl().getHost());
					System.out.println("-Path: "+ returnPage.getUrl().getPath());
					System.out.println("-URL String: "+returnPage.getUrl().toString());
					HtmlInput userIDFieldDummy = returnPage.getFirstByXPath("//input[contains(@id,'userID')]");
					HtmlAnchor confirmAnchor = returnPage.getFirstByXPath("//form[contains(@id,'confirmForm')]//a");
					HtmlButton confirmButton = null;                
					if (confirmAnchor == null) {
						System.out.println("Anchor link couldnt found in the confirm page !");
						confirmButton = returnPage.getFirstByXPath("//form[contains(@id,'confirmForm')]//button");
						if (confirmButton == null) {
							System.out.println("ERROR*****confirm button is NULL");
						} else {
							System.out.println("INFO: confirm button: " + confirmButton.asText());
						}
					} 
					
					HtmlInput userIDField;
					HtmlInput userKeyField;
					if (userIDFieldDummy != null ) {
						userIDField = returnPage.getFirstByXPath("//input[contains(@id,'userID')]");
						userKeyField = returnPage.getFirstByXPath("//input[contains(@id,'userKey')]");
						System.out.println("User ID: "+ userIDField.getValueAttribute());
						System.out.println("User Key: "+ userKeyField.getValueAttribute());
						result.put("userId", userIDField.getValueAttribute());
						result.put("userKey", userKeyField.getValueAttribute());                    
					} else if (confirmAnchor != null) {
						System.out.println("Continue is a Link in Confirm page.");
						Page finalPage = confirmAnchor.click();
						if (finalPage instanceof HtmlPage) {
							HtmlPage lastPage = (HtmlPage)  finalPage;
							System.out.println(lastPage.getTitleText());
							userIDField = lastPage.getFirstByXPath("//input[contains(@id,'userID')]");
							userKeyField = lastPage.getFirstByXPath("//input[contains(@id,'userKey')]");
							System.out.println("User ID: "+ userIDField.getValueAttribute());
							System.out.println("User Key: "+ userKeyField.getValueAttribute());
							result.put("userId", userIDField.getValueAttribute());
							result.put("userKey", userKeyField.getValueAttribute());
						} else {
							System.out.println("Last Page not retrieved....");      
							result.put("error", "Unknown error");                      
						}
					} else if (confirmButton != null) {
						System.out.println("Continue is a button in Confirm page.");
						//HtmlForm confirmForm = returnPage.getFirstByXPath("//form[contains(@id,'confirmForm')]");
						//System.out.println("Form action: " + confirmForm.getAttribute("data-location"));
						webClient.getOptions().setRedirectEnabled(true);                    
						Page finalPage1 = returnPage.executeJavaScript("D2L.LP.Web.UI.Common.Controls.Form.DoSubmit(document.getElementById('confirmForm'))").getNewPage();
						webClient.waitForBackgroundJavaScript(20000);
						//Thread.sleep(10000);
						
						if (finalPage1 instanceof HtmlPage) {
							HtmlPage lastPage = (HtmlPage)  finalPage1;
							System.out.println("*Page title: " + lastPage.getTitleText());
							System.out.println("---------------------------------------------------------------------");
							System.out.println("**Page url: " + lastPage.getUrl().toString());
							userIDField = lastPage.getFirstByXPath("//input[contains(@id,'userID')]");
							userKeyField = lastPage.getFirstByXPath("//input[contains(@id,'userKey')]");
							System.out.println("*User ID: "+ userIDField.getValueAttribute());
							System.out.println("*User Key: "+ userKeyField.getValueAttribute());
							result.put("userId", userIDField.getValueAttribute());
							result.put("userKey", userKeyField.getValueAttribute());
						} else {
							System.out.println("Last Page not retrieved....");      
							result.put("error", "Unknown error");                      
						}                    
					} else {
						System.out.println("Not in correct page...........");
						result.put("error", "Unknown error");              	
					}

				} else {
					System.out.println("Return page is not an instance of HtmlPage:");
					System.out.println(retPage.getWebResponse().getContentAsString());  
					result.put("error", "Unknown error");                
				}
		
			} catch (Exception ex) {
				System.out.println("Exception occured");
				ex.printStackTrace();
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "ERROR :"+e.getMessage();
		}

	}
	
	public static Map<String, String> getParamsAsMap(String inputParams)
	  {
	    String[] params = inputParams.split("&");
	    Map<String, String> map = new HashMap();
	    for (String param : params)
	    {
	      String name = param.split("=")[0];
	      String value = param.split("=")[1];
	      try
	      {
	        if (value != null) {
	          value = URLDecoder.decode(value, "UTF-8");
	        }
	      }
	      catch (Exception e)
	      {
	       System.out.println(e);
	      }
	      map.put(name, value);
	    }
	    System.out.println("Input params map:");
	    System.out.println(map);
	    return map;
	  }

}

package com.unifyed.d2l.oauthadapter.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gargoylesoftware.htmlunit.WebClient;
import com.unifyed.d2l.oauthadapter.util.*;

@Controller
public class HomeController {

	@Autowired
	private WebClient webclient;

	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException {
		return new ModelAndView("home");
	}

	@ResponseBody
	@RequestMapping(value = "/d2l/authenticate", method = RequestMethod.POST)
	public String d2lAuth(HttpServletResponse request, HttpServletResponse response, @RequestBody String d2lData)
			throws IOException {
		try {
			Map<String, String> inputPram = D2LUtil.getParamsAsMap(d2lData);
			return D2LUtil.authenticate(webclient, inputPram, D2LConstants.TRUSTED_BASE_URL);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = { "/canvas/authenticate" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> canvasAuthenticate(@RequestBody String canvasData) {
		Map<String, String> params = D2LUtil.getParamsAsMap(canvasData);
		System.out.println(params);
		Map<String, Object> ticketResponse = CanvasUtil.processCanvasAuth(webclient, params);
		return ticketResponse;
	}

}

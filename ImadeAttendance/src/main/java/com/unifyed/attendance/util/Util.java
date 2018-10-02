package com.unifyed.attendance.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Util {

	public static Map<String, String> getParamsAsMap(String inputParams)
	  {
	    String[] params = inputParams.split("&");
	    Map<String, String> map = new HashMap<String, String>();
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

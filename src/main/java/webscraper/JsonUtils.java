package webscraper;


import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Wiki; 


public class JsonUtils { 

	public static String objToJsonStr(Wiki obj) 
	{ 

		// Creating Object of ObjectMapper define in Jakson Api 
		ObjectMapper objMapper = new ObjectMapper();
		
		String jsonStr = null;

		try { 

			// get object as a json string 
			jsonStr = objMapper.writeValueAsString(obj); 

			// Displaying JSON String 
			System.out.println("JSON:" + jsonStr); 
		} 

		catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return jsonStr;
	} 
	
	public static Wiki jsonStrToObj(String jsonStr) 
	{ 
		// Creating Object of ObjectMapper define in Jakson Api 
		ObjectMapper objMapper = new ObjectMapper();
		
		Wiki obj = null;

    try {

        obj = objMapper.readValue(jsonStr, Wiki.class);

        System.out.println(obj);


    } catch (IOException e) {
        e.printStackTrace();
    }
	return obj;
	}

}
package com.amazonaws.lambda.yelpeventapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, String> {

	@Override
	public String handleRequest(Map<String, Object> input, Context context) {
		if(input!=null) context.getLogger().log("Input: " + input.get("location"));
		URL url;
		StringBuffer content = new StringBuffer();
		try {
			String apiKey = "Bearer 2GJm_X0Pf2SYqdV56fXDobrWL2wnsCgilAM6rzaEXp8j_lyAC9stl2Zz79LoqxA8ya7Fsm9s5dKW7VjQqqpmW1zo-BxD9_LdlgOdJK-vfpjlNYCV84aOi1xDobDVW3Yx";
			url = new URL("https://api.yelp.com/v3/businesses/search?location=santa monica&sort_by=review_count");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", apiKey);
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: implement your handler
		return content.toString();
	}

}

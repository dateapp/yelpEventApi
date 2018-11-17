package com.amazonaws.lambda.yelpeventapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.amazonaws.lambda.yelpeventapi.model.Business;
import com.amazonaws.lambda.yelpeventapi.model.Category;
import com.amazonaws.lambda.yelpeventapi.model.Location;
import com.amazonaws.lambda.yelpeventapi.model.YelpEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, String> {

	final String[] categories = { "landmarks", "cupcakes", "gluten_free", "comfortfood", "jazzandblues", "tapas",
			"grocery", "halal", "british", "irish_pubs", "irish", "donuts", "whiskeybars", "persian", "chickenshop",
			"cantonese", "peruvian", "museums", "arcades", "winetastingroom", "wraps", "tikibars", "gelato", "szechuan",
			"divebars", "pakistani", "himalayan", "ethiopian", "soulfood", "polish", "seafoodmarkets", "fishnchips",
			"candy", "german", "shavedice", "bowling", "turkish", "brewpubs", "falafel", "caribbean", "bagels",
			"customcakes", "creperies", "danceclubs", "argentine", "artmuseums", "panasian", "dinnertheater",
			"scandinavian", "theater", "portuguese", "filipino", "chocolate", "observatories", "tacos", "parks",
			"catering", "izakaya", "lebanese", "cakeshop", "meats", "champagne_bars", "zoos", "gardens", "waffles",
			"intlgrocery", "teppanyaki", "movietheaters", "cheese", "cabaret", "armenian", "southafrican", "hawaiian",
			"beer_and_wine", "russian", "fooddeliveryservices", "empanadas", "basque", "austrian", "butcher",
			"nonprofit", "tours", "cheesesteaks", "giftshops", "macarons", "supperclubs", "brasseries" };

	@Override
	public String handleRequest(Map<String, Object> input, Context context) {
		if (input != null)
			context.getLogger().log("Input: " + input.get("location"));
		URL url;
		String apiKey = (String) input.get("apiKey");
		String city = (String) input.get("city");
		for (String category : categories) {
			for (int price = 1; price < 5; price++) {
				int offset = 0;
				int total = 0;
				do {
					String yelpJson = null;
					try {
						yelpJson = getYelpJson(category, price, offset, city, apiKey);
					} catch (Exception ex) {
						System.out.println("The yelp api has thrown an issue");
					}
					Connection conn = null;
					try {
						conn = getConnection(input);
						ObjectMapper objectMapper = new ObjectMapper();
						YelpEvent yelpevent = objectMapper.readValue(yelpJson, YelpEvent.class);
						String query = insertYelpEventQuery(yelpevent);
						Statement statement = conn.createStatement();
						System.out.println(category + " OFFSET : " + offset + "////" + query);
						statement.execute(query);
						// insert the category
						String categoryEventQuery = insertEventCategoryQuery(yelpevent);
						statement.execute(categoryEventQuery);
						conn.close();
						if (total == 0)
							total = yelpevent.getTotal();
						offset = offset + 50;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} while (offset < total && offset <= 950);
			}
		}

		return "success";
	}

	private String getYelpJson(String category, int price, int offset, String location, String apiKey)
			throws Exception {
		StringBuffer content = new StringBuffer();
		URL url = new URL(
				"https://api.yelp.com/v3/businesses/search?location=" + location + "&radius=40000&limit=50&offset="
						+ offset + "&sort_by=best_match&categories=" + category + "&price=" + price);
		System.out.println(url.toString());
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
		return content.toString();
	}

	private String insertEventCategoryQuery(YelpEvent event) {

		if (event.getBusinesses().size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("Insert into yelp_category (yelp_id,category_name) values ");
		for (Business biz : event.getBusinesses()) {
			for (Category category : biz.getCategories()) {
				sb.append("(");
				sb.append("'" + biz.getId() + "',");
				sb.append("'" + category.getAlias() + "'");
				sb.append("),");
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ON CONFLICT DO NOTHING");
		return sb.toString();

	}

	private String insertYelpEventQuery(YelpEvent event) {
		if (event.getBusinesses().size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append("Insert into yelp_event (yelp_id,name,image_url,yelp_url,rating,latitude,longitude,price"
				+ ",address1,address2,address3,city,zip_code,country,state,display_address,phone,display_phone,review_count) values ");
		for (Business biz : event.getBusinesses()) {
			sb.append("(");
			sb.append("'" + biz.getId() + "',");
			sb.append("'" + biz.getName().replaceAll("'", "''") + "',");
			sb.append("'" + biz.getImageUrl() + "',");
			sb.append("'" + biz.getUrl() + "',");
			sb.append("'" + biz.getRating() + "',");
			if (biz.getCoordinates() != null && biz.getCoordinates().getLatitude() != null)
				sb.append("'" + biz.getCoordinates().getLatitude() + "',");
			else
				sb.append("'0',");
			if (biz.getCoordinates() != null && biz.getCoordinates().getLongitude() != null)
				sb.append("'" + biz.getCoordinates().getLongitude() + "',");
			else
				sb.append("'0',");
			if (biz.getPrice() != null)
				sb.append("'" + biz.getPrice().length() + "',");
			else
				sb.append("'0',");
			if (biz.getLocation() != null && biz.getLocation().getAddress1() != null)
				sb.append("'" + biz.getLocation().getAddress1().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && biz.getLocation().getAddress2() != null)
				sb.append("'" + biz.getLocation().getAddress2().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && biz.getLocation().getAddress3() != null)
				sb.append("'" + biz.getLocation().getAddress3().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && biz.getLocation().getCity() != null)
				sb.append("'" + biz.getLocation().getCity().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null)
				sb.append("'" + biz.getLocation().getZipCode() + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && biz.getLocation().getCountry() != null)
				sb.append("'" + biz.getLocation().getCountry().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && biz.getLocation().getState() != null)
				sb.append("'" + biz.getLocation().getState().replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			if (biz.getLocation() != null && CollectionUtils.isNullOrEmpty(biz.getLocation().getDisplayAddress()))
				sb.append("'" + biz.getLocation().getDisplayAddress().get(0).replaceAll("'", "''") + "',");
			else
				sb.append("'',");
			sb.append("'" + biz.getPhone() + "',");
			sb.append("'" + biz.getDisplayPhone() + "',");
			if (biz.getReviewCount() != null)
				sb.append("'" + biz.getReviewCount() + "'");
			else
				sb.append("'0'");
			sb.append("),");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" ON CONFLICT DO NOTHING");
		return sb.toString();
	}

	private Connection getConnection(Map<String, Object> input) throws SQLException {
		String host = (String) input.get("rdsHost");
		String userName = (String) input.get("userName");
		String password = (String) input.get("password");
		Connection conn = DriverManager.getConnection(host, userName, password);
		return conn;

	}

}

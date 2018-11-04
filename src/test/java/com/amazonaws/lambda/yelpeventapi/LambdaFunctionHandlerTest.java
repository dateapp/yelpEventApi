package com.amazonaws.lambda.yelpeventapi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.lambda.yelpeventapi.model.YelpEvent;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static Map<String,Object> input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);
        try {
        	String json = readTestFile("src/test/resources/TestYelpEvent");
            ObjectMapper objectMapper = new ObjectMapper();
            YelpEvent yelpevent = objectMapper.readValue(json, YelpEvent.class);
        	System.out.print(json);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        // TODO: validate output here if needed.
        Assert.assertNotNull(output);
    }
    
    
    private String readTestFile(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
        	sb.append(br.readLine());
        }
        fr.close();
        return sb.toString();

    }
}

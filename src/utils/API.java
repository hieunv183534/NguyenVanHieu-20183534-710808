package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Represent an API call
 * @author sonnh
 * @version 1.1
 * Date: 12/7/2021
 */
public class API{

    /**
     * A date formatter to format all date to yyyy/MM/dd HH:mm:ss SONNH
     */
    public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    /**
     * Common Logger of class
     */
    private static final Logger LOGGER = Utils.getLogger(Utils.class.getName());

    /**
     * Get API SONNH
     *
     * @param url   url of api
     * @param token secret token
     * @return not known yet
     * @throws Exception
     */
    public static String get(String url, String token) throws Exception{
        // set up

        HttpURLConnection conn = setUpConnection(url, "GET", token);

        // return result after processing
        return readResponse(conn);
    }

    int var;

    /**
     * post APi SONNH
     *
     * @param url   API url
     * @param data  data to send
     * @param token secret token
     * @return not known yet
     * @throws IOException
     */
    public static String post(String url, String data, String token) throws IOException{
        // set up
        allowMethods("PATCH");
        HttpURLConnection conn = setUpConnection(url, "PATCH", token);

        // send data
        Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        writer.write(data);
        writer.close();

        return readResponse(conn);
    }

    /** SONNH
     * @param methods allowed method (POST, PATCH ...)
     * @deprecated only worked with java <= 11
     */
    private static void allowMethods(String... methods){
        try{
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            methodsField.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers()&~Modifier.FINAL);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/* static field */, newMethods);
        }catch(NoSuchFieldException|IllegalAccessException e){
            throw new IllegalStateException(e);
        }
    }

    /**
     * Prepare connection to call
     * SONNH
     * @param url    api url
     * @param method POST, GET, PATCH ...
     * @param token  secret token
     * @return HttpUrlConnection
     * @throws IOException
     */
    private static HttpURLConnection setUpConnection(String url, String method, String token) throws IOException{
        LOGGER.info("Request URL: " + url + "\n");
        URL apiUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();

        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer ".concat(token));

        return conn;

    }

    /** SONNH
     * Read response from HttpConnection after request
     * @param conn the connection
     * @return response string
     * @throws IOException
     */
    private static String readResponse(HttpURLConnection conn) throws IOException{
        BufferedReader in;
        String inputLine;

        if(conn.getResponseCode() == 200){
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else{
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        // use string builder for performance
        StringBuilder response = new StringBuilder();
        while((inputLine = in.readLine()) != null){
            System.out.println(inputLine);
            response.append(inputLine).append("\n");
        }

        in.close();

        LOGGER.info("Response info: " + response);

        return response.toString();
    }

}

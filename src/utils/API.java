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
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cấp các phương thức giúp gửi http request lên server và nhận dữ liệu về
 * @author HieuNV183534
 * @version 1.0
 */
public class API {

	/**
	 * Thuộc tính giúp format ngày tháng theo định dạng
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/**
	 * Thuộc tính giúp log thông tin ra console
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());


	/**
	 * Phương thức gọi các api dạng get
	 * @param url: đường dẫn tới server
	 * @param token: mã xác thực người dùng
	 * @return phản hồi từ server
	 * @throws Exception
	 * @author HieuNV183534
	 */
	public static String get(String url, String token) throws Exception {

		// 1.setup
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = getHttpURLConnection(token, line_api_url, "GET");

		// 2. Đọc dữ liệu trả về từ server
		StringBuilder response = getStringBuilder(conn);
		LOGGER.info("Respone Info: " + response.substring(0, response.length() - 1).toString());
		return response.substring(0, response.length() - 1).toString();
	}


	/**
	 * Phương thức đọc dữ liệu từ server
	 * @param conn
	 * @return response
	 * @throws IOException
	 * @author HieuNV183534
	 */
	private static StringBuilder getStringBuilder(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		String inputLine;
		StringBuilder response = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		response.append(inputLine + "\n");
		in.close();
		return response;
	}


	/**
	 * Connect tới server
	 * @param token
	 * @param line_api_url
	 * @return return connection
	 * @throws IOException
	 * @author HieuNV183534
	 */
	private static HttpURLConnection getHttpURLConnection(String token, URL line_api_url, String method) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}

	int var;

	/**
	 * Phương thức gọi các api dạng post
	 * @param url: dường dẫn tới server
	 * @param data: dữ liêuj truyền lên server(JSON)
	 * @return phản hồi từ server(string)
	 * @throws IOException
	 * @author HieuNV183534
	 */
	public static String post(String url, String data
//			, String token
	) throws IOException {

		// 1. setup
		allowMethods("PATCH");
		URL line_api_url = new URL(url);
		String payload = data;
		LOGGER.info("Request Info:\nRequest URL: " + url + "\n" + "Payload Data: " + payload + "\n");
		HttpURLConnection conn = getHttpURLConnection("xxx", line_api_url, "POST");

		// 2.gửi dữ liệu lên server
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(payload);
		writer.close();

		//3. đọc dữ liệu nhận được từ server
		StringBuilder response = getStringBuilder(conn);
		LOGGER.info("Respone Info: " + response.toString());
		return response.toString();
	}


	/**
	 * Phương thức cho phép gọi các loại giao thức khác nhau
	 * @deprecated chỉ hoạt động vs java <=11
	 * @param methods giao thức(PATCH,PUT,...)
	 * @author HieuNV183534
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}

package de.metamob.badesee.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.StrictMode;

/**
 * @author Felix Matuschek MatNr. 798423, Hans-Christian Hanke MatNr. 798152
 * 
 *         Access to URL-Based JSON-File
 */
public class Json {

	/**
	 * Reads an online JSON-File and converts it to an JSON-Object
	 * 
	 * @param url
	 *            URL to the JSON-File
	 * @return converted JSON-Object
	 */
	public static JSONObject getJson(String url) {
		InputStream is = null;
		String result = "";
		JSONObject jsonObject = null;

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httppost = new HttpGet(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			return null;
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, HTTP.ISO_8859_1), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			return null;
		}

		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			return null;
		}

		return jsonObject;
	}

}

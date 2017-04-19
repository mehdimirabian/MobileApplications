package pack.location.chat.com.chatmodule.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	 InputStream is = null;
	 JSONObject jObj = null;
	 String json = "";


	// constructor
	public JSONParser()
	{
		 is = null;
		 jObj = null;
		 json = "";

	}
	
	public JSONObject getJSONFromUrlGetMethod(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
			HttpConnectionParams.setSoTimeout(httpParameters, 20000);
			
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			HttpGet httpGet = new HttpGet(url);
			

			
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e)
		{
			//e.printStackTrace();
			
		} catch (ClientProtocolException e) 
		{
			//e.printStackTrace();
			
		} catch (IOException e) {
			//e.printStackTrace();
			
		}

		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			//Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			//Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}
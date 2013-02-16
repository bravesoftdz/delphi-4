package edu.mq.comp.delphi.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import com.google.api.client.extensions.appengine.http.urlfetch.UrlFetchTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;

import edu.mq.comp.delphi.util.IOUtil;

public class HttpClient {
  private static final HttpClient INSTANCE = new HttpClient();
  private static final int HTTP_OK = 200;
  private static final String NEWLINE = "\n";
  private static final String CHARSET_NAME = "UTF-8";
  private UrlFetchTransport transport = new UrlFetchTransport();
  private static final int MAX_CONNECT_TIMEOUT_IN_MS = 10000;
  private static final int MAX_READ_TIMEOUT_IN_MS = 10000;
  private final Logger logger = Logger.getLogger(HttpClient.class.getName());

  public static HttpClient getInstance() {
	return INSTANCE;
  }

  public String doGet(String url) {
	StringBuilder result = new StringBuilder();

	if (StringUtils.isBlank(url)) {
	  return result.toString();
	}

	BufferedReader reader = null;

	try {
	  LowLevelHttpRequest request = transport.buildGetRequest(url);
	  request.setTimeout(MAX_CONNECT_TIMEOUT_IN_MS, MAX_READ_TIMEOUT_IN_MS);
	  LowLevelHttpResponse response = request.execute();

	  if (response.getStatusCode() == HTTP_OK) {
		reader = new BufferedReader(new InputStreamReader(response.getContent(), CHARSET_NAME));

		String line;

		while ((line = reader.readLine()) != null) {
		  result.append(line).append(NEWLINE);
		}
	  } else {
		logger.severe(String.format("Http status code was %d", response.getStatusCode()));
	  }
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	} finally {
	  IOUtil.closeQuietly(reader);
	}

	return result.toString();
  }
  
  public boolean doPost(String urlString, Map<String, String> paramMap) {
	boolean result = false;
	
	if (StringUtils.isBlank(urlString)) {
	  return result;
	}

	StringBuilder queryBuilder = new StringBuilder();
	
	for (Iterator<Map.Entry<String, String>> iter = paramMap.entrySet().iterator(); iter.hasNext();) {
	  Map.Entry<String, String> entry = iter.next();
	  
	  try {
		queryBuilder.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		
		if (iter.hasNext()) {
		  queryBuilder.append("&");
		}
	  } catch (UnsupportedEncodingException ex) {
		logger.severe(ex.toString());
	  }
	}

    Writer writer = null;

	try {
	  URL url = new URL(urlString);
	  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	  connection.setDoOutput(true);
	  connection.setConnectTimeout(MAX_CONNECT_TIMEOUT_IN_MS);
	  connection.setReadTimeout(MAX_READ_TIMEOUT_IN_MS);
	  connection.setRequestMethod("POST");
	  
	  writer = new OutputStreamWriter(connection.getOutputStream());
	  writer.write(queryBuilder.toString());
	  writer.flush();
	  
	  if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
		result = true;
	  }
	} catch (IOException ex) {
	  logger.severe(ex.toString());
	} finally {
	  IOUtil.closeQuietly(writer);
	}

	return result;
  }

  // private constructor to prevent instantiation
  private HttpClient() {
  }
}

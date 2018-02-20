package br.com.ggroup.util;

import br.com.ggroup.models.MRequest;
import br.com.ggroup.models.MResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Fabio Garcia
 */
public class APIRequest {

   private final static int TIMEOUT = 30000; //miliseconds 

   public static MResponse sendRequest(MRequest request) throws Exception {
      HashMap<String, String> headers = new HashMap<>();

      headers.put("Content-Type", "application/json");
      headers.put("Authorization", "Basic " + request.getConfig().getBasicCredentials());

      if (request.getCustomHeaders() != null && request.getCustomHeaders().size() > 0) {
         for (Map.Entry pair : request.getCustomHeaders().entrySet()) {
            headers.put((String) pair.getKey(), (String) pair.getValue());
         }
      }

      if (request.getConfig().getUrl().endsWith("/")) {
         request.getConfig().setUrl(request.getConfig().getUrl().substring(0, request.getConfig().getUrl().length() - 1));
      }

      HttpURLConnection connection = (HttpURLConnection) new URL(request.getConfig().getUrl() + request.getaPIMethodAbstract().getUrl()).openConnection();

      connection.setConnectTimeout(TIMEOUT);
      connection.setReadTimeout(TIMEOUT);

      connection.setRequestMethod(request.getaPIMethodAbstract().getHTTPVerb());

      for (Map.Entry pair : headers.entrySet()) {
         connection.setRequestProperty((String) pair.getKey(), (String) pair.getValue());
      }

      connection.setDoOutput(true);

      if (request.getBodyContent() != null && request.getBodyContent().length() > 0) {
         OutputStream os = connection.getOutputStream();
         try {
            os.write(request.getBodyContent().toString().getBytes("UTF-8"));
         } finally {
            os.close();
         }
      }

      InputStreamReader isl = null;
      switch (connection.getResponseCode()) {
         case 200:
         case 201:
            isl = new InputStreamReader(connection.getInputStream(), "UTF-8");
            break;
         case 404:
            throw new IOException("Resource not found.");
         case 400:
            String apiReturn = "\n";
            try {
               isl = new InputStreamReader(connection.getErrorStream(), "UTF-8");

               JSONObject returnObject = new JSONObject(new JSONTokener(isl));
               for (int i = 0; i < returnObject.getJSONArray("errorMessages").length(); i++) {
                  apiReturn += returnObject.getJSONArray("errorMessages").getString(i) + "\n";
               }
            } catch (Exception e) {
               apiReturn += e.getMessage();
            } finally {
               if (isl != null) {
                  isl.close();
               }
            }
            throw new Exception("The Jira API returns: " + connection.getResponseCode() + " - " + connection.getResponseMessage() + ". " + apiReturn);
         case 401:
            throw new IOException("The Jira API returns: " + connection.getResponseCode() + " - " + connection.getResponseMessage() + ". Please, check your credentials.");
         default:
            InputStream is = connection.getErrorStream();
            if (is == null) {
               is = connection.getInputStream();
            }
            if (is == null) {
               throw new IOException("HTTP error code:" + connection.getResponseCode() + "\nNull response");
            }
            break;
      }

      try {
         JSONTokener returnTokener = new JSONTokener(isl);
         JSONArray returnArray = null;
         JSONObject returnObject = null;

         if (request.getaPIMethodAbstract().returnType() == APIMethod.RETURN_TYPE_ARRAY) {
            returnArray = new JSONArray(returnTokener);
         } else if (request.getaPIMethodAbstract().returnType() == APIMethod.RETURN_TYPE_OBJECT) {
            returnObject = new JSONObject(returnTokener);
         }

         return new MResponse(returnArray, returnObject, request.getaPIMethodAbstract().returnType(), connection.getResponseCode(), connection.getResponseMessage());
      } finally {
         if (isl != null) {
            isl.close();
         }
         connection.disconnect();
      }
   }

}

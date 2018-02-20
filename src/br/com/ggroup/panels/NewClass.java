/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ggroup.panels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.in;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author 1513 IRON
 */
public class NewClass {

   public static void main(String[] args) throws Exception {
      HashMap<String, String> headers = new HashMap<>();
      headers.put("Content-Type", "application/json");
      headers.put("Authorization", "Basic  ZmFiaW8uZ2FyY2lhQHlwc2UuY29tLmJyOm5hb3NlaTAwXy1AQCsreXBzZQ==");

//      Proxy proxy = null;
//      switch (getProxyType()) {
//         case SYSTEM_PROXY:
//            System.setProperty("java.net.useSystemProxies", "true");
//            ProxySelector.setDefault(new DefaultProxySelector());
//            proxy = getProxy(url);
//            System.setProperty("java.net.useSystemProxies", "false");
//            ProxySelector.setDefault(null);
//            break;
//         case NO_PROXY:
//            System.setProperty("java.net.useSystemProxies", "false");
//            proxy = Proxy.NO_PROXY;
//            break;
//         case CUSTOM_PROXY:
//            throw new UnsupportedOperationException("Not Implemented yet.");
//         default:
//            break;
//      }
      HttpURLConnection connection = (HttpURLConnection) new URL("https://ypsecom.atlassian.net/rest/agile/1.0/issue/BO-93").openConnection();

      connection.setConnectTimeout(10000);
      connection.setReadTimeout(10000);

      connection.setRequestMethod("GET");
      for (String key : headers.keySet()) {
         connection.setRequestProperty(key, headers.get(key));
      }

      connection.setDoOutput(true);

//      OutputStream os = new OutputStream(connection.getOutputStream());
//      
//      
//      try {
//         os.write(data.toString().getBytes("UTF-8"));
//      } finally {
//         os.close();
//      }
//
//      if (connection.getResponseCode() == 401) {
//         throw new ExpiredTokenException();
//      }
      InputStreamReader isl = null;
      switch (connection.getResponseCode()) {
         case 200:
            isl = new InputStreamReader(connection.getInputStream());
            break;
         case 404:
            throw new IOException("Err");
         default:
            InputStream is = connection.getErrorStream();
            if (is == null) {
               // Verifica se o InputStream normal não está retornando
               is = connection.getInputStream();
            }
            if (is == null) {
               // Caso não seja possível extrair um retorno, dispara uma exception
               throw new IOException("HTTP error code:" + connection.getResponseCode() + "\nNull response");
            }

            break;
      }
      try {

         JSONObject result = new JSONObject(new JSONTokener(isl));
         System.out.println(result.getString("key"));
         System.out.println(result.getJSONObject("fields").getString("summary"));
      } finally {
         isl.close();
      }

//      StringBuilder sb = new StringBuilder();
//      try (BufferedReader br = new BufferedReader(isl)) {
//         String read;
//
//         while ((read = br.readLine()) != null) {
//            //System.out.println(read);
//            sb.append(read);
//         }
//      }
//      System.out.println(sb.toString());
   }

}

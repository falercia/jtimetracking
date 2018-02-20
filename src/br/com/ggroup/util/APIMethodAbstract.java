package br.com.ggroup.util;

import java.util.HashMap;

public abstract class APIMethodAbstract {

   public abstract String getHTTPVerb();

   public abstract String getUrl();
   
   public abstract int returnType();

   protected String replaceParameters(String url, HashMap<String, String> params) {
      String _url = url;
      if (params != null && params.size() > 0) {
         for (String key : params.keySet()) {
            _url = _url.replace(key, params.get(key));
         }
      }
      return _url;
   }

}

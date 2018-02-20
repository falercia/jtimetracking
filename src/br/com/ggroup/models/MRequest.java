package br.com.ggroup.models;

import br.com.ggroup.util.APIMethodAbstract;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MRequest {

   private final MConfig config;
   private final APIMethodAbstract aPIMethodAbstract;
   private HashMap<String, String> customHeaders;
   private final JSONObject bodyContent;

   public MRequest(MConfig config, APIMethodAbstract aPIMethodAbstract, JSONObject bodyContent) {
      this.config = config;
      this.aPIMethodAbstract = aPIMethodAbstract;
      this.bodyContent = bodyContent;
   }

   public MConfig getConfig() {
      return config;
   }

   public APIMethodAbstract getaPIMethodAbstract() {
      return aPIMethodAbstract;
   }

   public HashMap<String, String> getCustomHeaders() {
      return customHeaders;
   }

   public JSONObject getBodyContent() {
      return bodyContent;
   }

}

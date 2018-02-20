package br.com.ggroup.models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MResponse {

   private final JSONArray returnArray;
   private final JSONObject returnObject;
   private final int returnType;
   private final int responseCode;
   private final String responseMessage;

   public MResponse(JSONArray returnArray, JSONObject returnObject, int returnType, int responseCode, String responseMessage) {
      this.returnArray = returnArray;
      this.returnObject = returnObject;
      this.returnType = returnType;
      this.responseCode = responseCode;
      this.responseMessage = responseMessage;
   }

   public JSONArray getReturnArray() {
      return returnArray;
   }

   public JSONObject getReturnObject() {
      return returnObject;
   }

   public int getReturnType() {
      return returnType;
   }

   public int getResponseCode() {
      return responseCode;
   }

   public String getResponseMessage() {
      return responseMessage;
   }

}

package br.com.ggroup.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MBoard {

   private final JSONObject orginalContent;
   private int id;
   private String name;
   private String type;

   public MBoard(JSONObject orginalContent) throws JSONException {
      this.orginalContent = orginalContent;
      extract();
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public JSONObject getOrginalContent() {
      return orginalContent;
   }

   private void extract() throws JSONException {
      if (getOrginalContent() != null) {
         setId(getOrginalContent().getInt("id"));
         setType(getOrginalContent().getJSONObject("location").getString("projectTypeKey"));
         setName(getOrginalContent().getJSONObject("location").getString("name"));
      } else {
         setId(-1);
         setType("");
         setName("Select");
      }
   }

   @Override
   public String toString() {
      return getName();
   }

}

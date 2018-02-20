package br.com.ggroup.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MSprint {

   private int id;
   private String name;
   private final JSONObject orginalContent;

   public MSprint(JSONObject orginalContent) throws JSONException {
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

   public JSONObject getOrginalContent() {
      return orginalContent;
   }

   private void extract() throws JSONException {
      if (getOrginalContent() != null) {
         setId(getOrginalContent().getInt("id"));
         setName(getOrginalContent().getString("name"));
      } else {
         setId(-1);
         setName("Select");
      }
   }

   @Override
   public String toString() {
      return getName();
   }
}

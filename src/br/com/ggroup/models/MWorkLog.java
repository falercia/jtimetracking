package br.com.ggroup.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MWorkLog {

   private final JSONObject orginalContent;
   private int id;
   private String authorEmailAddress;
   private String authorDisplayName;
   private String comment;
   private int timeSpentSeconds;
   private String createdAtStr;
   private final SimpleDateFormat sdfJiraFormat;
   private final SimpleDateFormat sdfPtBRFormat;

   public MWorkLog(JSONObject orginalContent) throws Exception {
      this.orginalContent = orginalContent;

      this.sdfJiraFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");;
      this.sdfPtBRFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
      extract();
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getAuthorEmailAddress() {
      return authorEmailAddress;
   }

   public void setAuthorEmailAddress(String authorEmailAddress) {
      this.authorEmailAddress = authorEmailAddress;
   }

   public String getAuthorDisplayName() {
      return authorDisplayName;
   }

   public void setAuthorDisplayName(String authorDisplayName) {
      this.authorDisplayName = authorDisplayName;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public int getTimeSpentSeconds() {
      return timeSpentSeconds;
   }

   public void setTimeSpentSeconds(int timeSpentSeconds) {
      this.timeSpentSeconds = timeSpentSeconds;
   }

   public JSONObject getOrginalContent() {
      return orginalContent;
   }

   public String getCreatedAtStr() {
      return createdAtStr;
   }

   public void setCreatedAtStr(String createdAtStr) {
      this.createdAtStr = createdAtStr;
   }

   private void extract() throws Exception {
      if (getOrginalContent() != null) {
         setId(Integer.valueOf(getOrginalContent().getString("id")));
         setAuthorEmailAddress(getOrginalContent().getJSONObject("author").getString("emailAddress"));
         setAuthorDisplayName(getOrginalContent().getJSONObject("author").getString("displayName"));
         setCreatedAtStr(sdfPtBRFormat.format(sdfJiraFormat.parse(getOrginalContent().getString("created"))));
         if (getOrginalContent().has("comment")) {
            setComment(getOrginalContent().getString("comment"));
         }
         setTimeSpentSeconds(getOrginalContent().getInt("timeSpentSeconds"));
      } else {
         setId(-1);
      }

   }

}

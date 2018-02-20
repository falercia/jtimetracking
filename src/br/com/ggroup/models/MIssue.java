package br.com.ggroup.models;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MIssue {
   
   private final JSONObject orginalContent;
   private int id;
   private String key;
   private String assignedEmail;
   private String assignedAvatar16x16;
   private String assignedDisplayName;
   private String description;
   private String summary;
   private String statusName;
   private ArrayList<MWorkLog> workLog;
   
   public MIssue(JSONObject orginalContent) throws Exception {
      this.orginalContent = orginalContent;
      this.workLog = new ArrayList<>();
      
      extract();
   }
   
   public JSONObject getOrginalContent() {
      return orginalContent;
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public String getKey() {
      return key;
   }
   
   public void setKey(String key) {
      this.key = key;
   }
   
   public String getAssignedEmail() {
      return assignedEmail;
   }
   
   public void setAssignedEmail(String assignedEmail) {
      this.assignedEmail = assignedEmail;
   }
   
   public String getAssignedAvatar16x16() {
      return assignedAvatar16x16;
   }
   
   public void setAssignedAvatar16x16(String assignedAvatar16x16) {
      this.assignedAvatar16x16 = assignedAvatar16x16;
   }
   
   public String getAssignedDisplayName() {
      return assignedDisplayName;
   }
   
   public void setAssignedDisplayName(String assignedDisplayName) {
      this.assignedDisplayName = assignedDisplayName;
   }
   
   public String getDescription() {
      return description;
   }
   
   public void setDescription(String description) {
      this.description = description;
   }
   
   public String getSummary() {
      return summary;
   }
   
   public void setSummary(String summary) {
      this.summary = summary;
   }
   
   public String getStatusName() {
      return statusName;
   }
   
   public void setStatusName(String statusName) {
      this.statusName = statusName;
   }
   
   public ArrayList<MWorkLog> getWorkLog() {
      return workLog;
   }
   
   public void setWorkLog(ArrayList<MWorkLog> workLog) {
      this.workLog = workLog;
   }
   
   private void extract() throws Exception {
      if (getOrginalContent() != null) {
         setId(getOrginalContent().getInt("id"));
         setKey(getOrginalContent().getString("key"));
         setAssignedEmail(getOrginalContent().getJSONObject("fields").getJSONObject("assignee").getString("emailAddress"));
         setAssignedAvatar16x16(getOrginalContent().getJSONObject("fields").getJSONObject("assignee").getJSONObject("avatarUrls").getString("16x16"));
         setAssignedDisplayName(getOrginalContent().getJSONObject("fields").getJSONObject("assignee").getString("displayName"));
         setDescription(getOrginalContent().getJSONObject("fields").getString("description"));
         setSummary(getOrginalContent().getJSONObject("fields").getString("summary"));
         setStatusName(getOrginalContent().getJSONObject("fields").getJSONObject("status").getString("name"));
         
         for (int i = 0; i < getOrginalContent().getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs").length(); i++) {
            workLog.add(new MWorkLog(getOrginalContent().getJSONObject("fields").getJSONObject("worklog").getJSONArray("worklogs").getJSONObject(i)));
         }
         
      } else {
         setId(-1);
      }
   }
   
}

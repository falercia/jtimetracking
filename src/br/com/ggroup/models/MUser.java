package br.com.ggroup.models;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class MUser {

   private final JSONObject orginalContent;
   private String accountId;
   private String key;
   private String emailAddress;
   private String displayName;
   private String avatar16x16;
   private String password;
   private BufferedImage img16x16;

   public MUser(JSONObject orginalContent) throws Exception {
      this.orginalContent = orginalContent;

      extract();
   }

   public String getAccountId() {
      return accountId;
   }

   public void setAccountId(String accountId) {
      this.accountId = accountId;
   }

   public String getKey() {
      return key;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getEmailAddress() {
      return emailAddress;
   }

   public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
   }

   public String getDisplayName() {
      return displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public String getAvatar16x16() {
      return avatar16x16;
   }

   public void setAvatar16x16(String avatar16x16) {
      this.avatar16x16 = avatar16x16;
   }

   public JSONObject getOrginalContent() {
      return orginalContent;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public BufferedImage getImg16x16() {
      return img16x16;
   }

   public void setImg16x16(BufferedImage img16x16) {
      this.img16x16 = img16x16;
   }

   private void extract() throws Exception {
      if (getOrginalContent() != null) {
         setAccountId(getOrginalContent().getString("accountId"));
         setKey(getOrginalContent().getString("key"));
         setEmailAddress(getOrginalContent().getString("emailAddress"));
         setDisplayName(getOrginalContent().getString("displayName"));
         setAvatar16x16(getOrginalContent().getJSONObject("avatarUrls").getString("16x16"));
         try {
            setImg16x16(ImageIO.read(new URL(getAvatar16x16())));
         } catch (Exception e) {
            //not problem...dont hava avatar maybe
         }
      } else {
         setAccountId("");
      }
   }

}

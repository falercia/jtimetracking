package br.com.ggroup.models;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 *
 * @author Fabio Garcia
 */
public class MConfig {

   private String url;
   private String userName;
   private String password;

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getBasicCredentials() throws Exception {
      return Base64.encode((getUserName() + ":" + getPassword()).getBytes("UTF-8"));
   }

}

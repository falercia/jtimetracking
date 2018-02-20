package br.com.ggroup.models;

/**
 *
 * @author Fabio Garcia
 */
public class MMenuItem {

   private final String label;
   private final String path;

   public MMenuItem(String label, String path) {
      this.label = label;
      this.path = path;
   }

   public String getLabel() {
      return label;
   }

   public String getPath() {
      return path;
   }

}

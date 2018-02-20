package br.com.ggroup.components;

import br.com.ggroup.models.MMenuItem;
import javax.swing.JMenuItem;

/**
 *
 * @author Fabio Garcia
 */
public class JMenuItemCustom extends JMenuItem {

   private final String lafPath;

   public JMenuItemCustom(MMenuItem item) {
      super(item.getLabel());
      this.lafPath = item.getPath();
   }

   public String getLaf() {
      return lafPath;
   }
}

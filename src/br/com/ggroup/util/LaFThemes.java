package br.com.ggroup.util;

import br.com.ggroup.models.MMenuItem;
import java.util.ArrayList;

/**
 *
 * @author Fabio Garcia
 */
public class LaFThemes {

   private static ArrayList<MMenuItem> themes;

   private static void configThemes() {
      if (themes == null) {
         themes = new ArrayList<>();
      }
      
      themes.clear();
      
      themes.add(new MMenuItem("Business", "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"));
      themes.add(new MMenuItem("Business Blue", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel"));
      themes.add(new MMenuItem("Business Black", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel"));
      themes.add(new MMenuItem("Creme", "org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel"));
      themes.add(new MMenuItem("Creme coffee", "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel"));
      themes.add(new MMenuItem("Sahara", "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel"));
      themes.add(new MMenuItem("Moderate", "org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel"));
      themes.add(new MMenuItem("Nebula", "org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel"));
      themes.add(new MMenuItem("Cerulean", "org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel"));
      themes.add(new MMenuItem("Mist Aqua", "org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel"));
   }

   public static ArrayList<MMenuItem> getThemes() {
      configThemes();
      return themes;
   }

}

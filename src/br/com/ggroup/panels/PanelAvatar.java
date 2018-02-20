package br.com.ggroup.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class PanelAvatar extends JComponent {

   private final BufferedImage img;

   public PanelAvatar(BufferedImage img) {
      this.img = img;
      setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);

   }

}

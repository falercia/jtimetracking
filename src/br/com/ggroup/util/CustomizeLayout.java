package br.com.ggroup.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 *
 * @author Fabio Garcia
 */
public class CustomizeLayout implements LayoutManager {

   @Override
   public void addLayoutComponent(String name, Component comp) {
   }

   @Override
   public void removeLayoutComponent(Component comp) {
   }

   @Override
   public Dimension preferredLayoutSize(Container parent) {
      int height = 0;
      int width = 200;
      for (int i = 0; i < parent.getComponentCount(); i++) {
         height += parent.getComponent(i).getPreferredSize().height;
         width = Math.max(width, parent.getComponent(i).getPreferredSize().width);
      }
      Insets insets = parent.getInsets();
      width += insets.left + insets.right;
      height += insets.top + insets.bottom;
      return new Dimension(width, height);
   }

   @Override
   public Dimension minimumLayoutSize(Container parent) {
      return new Dimension(100, 100);
   }

   @Override
   public void layoutContainer(Container parent) {
      synchronized (parent.getTreeLock()) {
         Insets insets = parent.getInsets();
         int maxwidth = parent.getWidth() - (insets.left + insets.right);
         int nmembers = parent.getComponentCount();
         int x = insets.left, y = insets.top;

         for (int i = 0; i < nmembers; i++) {
            Component c = parent.getComponent(i);
            c.setLocation(x, y);
            c.setSize(maxwidth, (int) c.getPreferredSize().getHeight());
            y += c.getPreferredSize().getHeight();
         }
      }
   }
}

package br.com.ggroup.util;

/**
 *
 * @author Fabio Garcia
 */
public class Chronometer {

   private long begin;
   private long end;
   private long elapsedTime;
   private boolean isRuning;

   public void start() {
      if (isRuning) {
         return;
      }
      isRuning = true;
      begin = System.currentTimeMillis() - elapsedTime;
   }

   public void stop() {
      if (!isRuning) {
         return;
      }
      isRuning = false;
      end = System.currentTimeMillis();
      elapsedTime = end - begin;
   }

   public void clear() {
      begin = end = elapsedTime = 0;
      isRuning = false;
   }

   /**
    *
    * @return return elapsed time in seconds
    */
   public int getElapsedTime() {
      long currentTime;

      if (isRuning) {
         currentTime = System.currentTimeMillis();
      } else {
         currentTime = end;
      }

      return (int) (currentTime - begin) / 1000;
   }

   public String getStringTime() {
      return secondToTimeString(getElapsedTime());
   }

   public static String lpad(String str, int padding) {
      StringBuilder sb = new StringBuilder();

      for (int toPrepend = padding - str.length(); toPrepend > 0; toPrepend--) {
         sb.append('0');
      }

      sb.append(str);
      return sb.toString();
   }

   public static String secondToTimeString(int seconds) {
      int _hours = seconds / 3600;
      int _minutes = (seconds % 3600) / 60;
      int _seconds = ((seconds % 3600) % 60);

      String hourStr = String.valueOf(_hours);
      return lpad(hourStr, hourStr.length() > 1 ? hourStr.length() : 2) + ":" + lpad(String.valueOf(_minutes), 2) + ":" + lpad(String.valueOf(_seconds), 2);
   }
}

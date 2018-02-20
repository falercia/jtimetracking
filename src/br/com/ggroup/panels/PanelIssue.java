package br.com.ggroup.panels;

import br.com.ggroup.models.MIssue;
import br.com.ggroup.models.MUser;
import br.com.ggroup.util.Chronometer;
import br.com.ggroup.view.DialogSendToJira;
import br.com.ggroup.view.DialogWorklog;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class PanelIssue extends javax.swing.JPanel {

   private final Frame parent;
   private final MIssue issue;
   private final MUser user;
   private JPopupMenu jPopupMenuissueDetails;
   private final Chronometer chronometer;
   private Timer timer;

   public PanelIssue(Frame parent, MIssue issue, MUser user) {
      initComponents();

      this.parent = parent;
      this.issue = issue;
      this.user = user;
      this.chronometer = new Chronometer();
      initScreen();
      initIssue();
   }
   
   private void initScreen() {
      setPreferredSize(new Dimension(650, 45));
      setMinimumSize(getPreferredSize());
   }

   private void initIssue() {
      jPopupMenuissueDetails = new JPopupMenu();
      jPopupMenuissueDetails.add(new PanelIssueDetail(issue, jPopupMenuissueDetails));
      jLabelTask.setText(issue.getSummary());
   }

   private void showDetails() {
      jPopupMenuissueDetails.show(jButtonDetails, jButtonDetails.getLocation().x, jButtonDetails.getLocation().y);
   }

   public MIssue getIssue() {
      return issue;
   }

   public Frame getFormTimeTracking() {
      return parent;
   }

   public Chronometer getChronometer() {
      return chronometer;
   }

   private class RefreshTime extends TimerTask {

      @Override
      public void run() {
         jTextFieldTime.setText(chronometer.getStringTime());
      }
   }

   private void sendToJira() throws Exception {
      JSONObject author = new JSONObject();
      author.put("displayName", user.getDisplayName());
      author.put("emailAddress", user.getEmailAddress());

      JSONObject sendContent = new JSONObject();
      sendContent.put("author", author);
      sendContent.put("timeSpentSeconds", chronometer.getElapsedTime());

      new DialogSendToJira(parent, this, sendContent).setVisible(true);
   }

   public void finishSend() {
      getChronometer().clear();
      jButtonSendToJira.setEnabled(false);
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanelOptions = new javax.swing.JPanel();
      jButtonDetails = new javax.swing.JButton();
      jButtonStart = new javax.swing.JButton();
      jButtonStop = new javax.swing.JButton();
      jButtonSendToJira = new javax.swing.JButton();
      jButtonIssueWorkLog = new javax.swing.JButton();
      jTextFieldTime = new javax.swing.JTextField();
      jPanelTitle = new javax.swing.JPanel();
      jLabelTask = new javax.swing.JLabel();

      setBorder(javax.swing.BorderFactory.createTitledBorder(""));
      setLayout(new java.awt.BorderLayout());

      jPanelOptions.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

      jButtonDetails.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/details.png"))); // NOI18N
      jButtonDetails.setToolTipText("Issue Details");
      jButtonDetails.setPreferredSize(new java.awt.Dimension(30, 25));
      jButtonDetails.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonDetailsActionPerformed(evt);
         }
      });
      jPanelOptions.add(jButtonDetails);

      jButtonStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/play.png"))); // NOI18N
      jButtonStart.setToolTipText("Start work");
      jButtonStart.setPreferredSize(new java.awt.Dimension(30, 25));
      jButtonStart.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonStartActionPerformed(evt);
         }
      });
      jPanelOptions.add(jButtonStart);

      jButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/pause.png"))); // NOI18N
      jButtonStop.setToolTipText("Stop work");
      jButtonStop.setEnabled(false);
      jButtonStop.setPreferredSize(new java.awt.Dimension(30, 25));
      jButtonStop.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonStopActionPerformed(evt);
         }
      });
      jPanelOptions.add(jButtonStop);

      jButtonSendToJira.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/upload.png"))); // NOI18N
      jButtonSendToJira.setToolTipText("Send to Jira");
      jButtonSendToJira.setEnabled(false);
      jButtonSendToJira.setPreferredSize(new java.awt.Dimension(30, 25));
      jButtonSendToJira.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonSendToJiraActionPerformed(evt);
         }
      });
      jPanelOptions.add(jButtonSendToJira);

      jButtonIssueWorkLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/timetracking_report.png"))); // NOI18N
      jButtonIssueWorkLog.setToolTipText("Issue work log");
      jButtonIssueWorkLog.setPreferredSize(new java.awt.Dimension(30, 25));
      jButtonIssueWorkLog.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonIssueWorkLogActionPerformed(evt);
         }
      });
      jPanelOptions.add(jButtonIssueWorkLog);

      jTextFieldTime.setEditable(false);
      jTextFieldTime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      jTextFieldTime.setText("00:00:00");
      jTextFieldTime.setPreferredSize(new java.awt.Dimension(60, 20));
      jPanelOptions.add(jTextFieldTime);

      add(jPanelOptions, java.awt.BorderLayout.LINE_END);

      jPanelTitle.setLayout(new java.awt.BorderLayout());
      jPanelTitle.add(jLabelTask, java.awt.BorderLayout.CENTER);

      add(jPanelTitle, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents

   private void jButtonDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetailsActionPerformed
      showDetails();
   }//GEN-LAST:event_jButtonDetailsActionPerformed

   private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
      if (timer == null) {
         timer = new Timer();
         timer.schedule(new RefreshTime(), 0, 1000);
      }
      jButtonStart.setEnabled(false);
      jButtonStop.setEnabled(true);
      jButtonSendToJira.setEnabled(false);
      chronometer.start();
   }//GEN-LAST:event_jButtonStartActionPerformed

   private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
      chronometer.stop();
      jButtonStart.setEnabled(true);
      jButtonStop.setEnabled(false);
      jButtonSendToJira.setEnabled(true);
   }//GEN-LAST:event_jButtonStopActionPerformed

   private void jButtonSendToJiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendToJiraActionPerformed
      try {
         sendToJira();
      } catch (Exception e) {
         JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
      }
   }//GEN-LAST:event_jButtonSendToJiraActionPerformed

   private void jButtonIssueWorkLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIssueWorkLogActionPerformed
      new DialogWorklog(parent, issue).setVisible(true);
   }//GEN-LAST:event_jButtonIssueWorkLogActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonDetails;
   private javax.swing.JButton jButtonIssueWorkLog;
   private javax.swing.JButton jButtonSendToJira;
   private javax.swing.JButton jButtonStart;
   private javax.swing.JButton jButtonStop;
   private javax.swing.JLabel jLabelTask;
   private javax.swing.JPanel jPanelOptions;
   private javax.swing.JPanel jPanelTitle;
   private javax.swing.JTextField jTextFieldTime;
   // End of variables declaration//GEN-END:variables
}

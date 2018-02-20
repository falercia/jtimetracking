package br.com.ggroup.view;

import br.com.ggroup.models.MIssue;
import br.com.ggroup.models.MWorkLog;
import br.com.ggroup.util.Chronometer;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fabio Garcia
 */
public class DialogWorklog extends javax.swing.JDialog {

   private final MIssue issue;

   public DialogWorklog(java.awt.Frame parent, MIssue issue) {
      super(parent, true);
      initComponents();

      this.issue = issue;
      configScreen();
      showWorklog();
   }

   private void configScreen() {
      this.setLocationRelativeTo(null);
      this.setPreferredSize(new Dimension(700, 400));
      this.setMinimumSize(new Dimension(700, 400));
   }

   private void showWorklog() {
      DefaultTableModel model = new DefaultTableModel(new Object[]{"Created by", "Created at", "Time", "Comment"}, 0);
      int _total = 0;
      for (MWorkLog workLog : issue.getWorkLog()) {
         model.addRow(new Object[]{workLog.getAuthorDisplayName(), workLog.getCreatedAtStr(), Chronometer.secondToTimeString(workLog.getTimeSpentSeconds()), workLog.getComment()});
         _total += workLog.getTimeSpentSeconds();
      }

      jTableWorklog.setModel(model);
      jTextFieldTotal.setText(Chronometer.secondToTimeString(_total));
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanelBottom = new javax.swing.JPanel();
      jButton1 = new javax.swing.JButton();
      jPanelContent = new javax.swing.JPanel();
      jScrollPaneContent = new javax.swing.JScrollPane();
      jTableWorklog = new javax.swing.JTable();
      jPanelTotal = new javax.swing.JPanel();
      jLabelTotal = new javax.swing.JLabel();
      jTextFieldTotal = new javax.swing.JTextField();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

      jPanelBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      jPanelBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/close.png"))); // NOI18N
      jButton1.setText("Close");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });
      jPanelBottom.add(jButton1);

      getContentPane().add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

      jPanelContent.setLayout(new java.awt.BorderLayout());

      jTableWorklog.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {},
            {},
            {},
            {}
         },
         new String [] {

         }
      ));
      jScrollPaneContent.setViewportView(jTableWorklog);

      jPanelContent.add(jScrollPaneContent, java.awt.BorderLayout.CENTER);

      jPanelTotal.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

      jLabelTotal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/sum.png"))); // NOI18N
      jLabelTotal.setText("Total:");
      jPanelTotal.add(jLabelTotal);

      jTextFieldTotal.setEditable(false);
      jTextFieldTotal.setPreferredSize(new java.awt.Dimension(75, 20));
      jPanelTotal.add(jTextFieldTotal);

      jPanelContent.add(jPanelTotal, java.awt.BorderLayout.PAGE_END);

      getContentPane().add(jPanelContent, java.awt.BorderLayout.CENTER);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      this.dispose();
   }//GEN-LAST:event_jButton1ActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabelTotal;
   private javax.swing.JPanel jPanelBottom;
   private javax.swing.JPanel jPanelContent;
   private javax.swing.JPanel jPanelTotal;
   private javax.swing.JScrollPane jScrollPaneContent;
   private javax.swing.JTable jTableWorklog;
   private javax.swing.JTextField jTextFieldTotal;
   // End of variables declaration//GEN-END:variables
}

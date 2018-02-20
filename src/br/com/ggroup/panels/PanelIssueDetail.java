package br.com.ggroup.panels;

import br.com.ggroup.models.MIssue;
import java.awt.Dimension;
import javax.swing.JPopupMenu;

/**
 *
 * @author Fabio Garcia
 */
public class PanelIssueDetail extends javax.swing.JPanel {

   private final MIssue issue;
   private final JPopupMenu jPopupMenuDetail;

   public PanelIssueDetail(MIssue issue, JPopupMenu jPopupMenuDetail) {
      initComponents();

      this.issue = issue;
      this.jPopupMenuDetail = jPopupMenuDetail;
      config();
   }

   private void config() {
      setPreferredSize(new Dimension(500, 300));
      jTextAreaDescription.setEditable(false);
      jTextAreaDescription.setText(issue.getDescription());
      jLabelIssueTitle.setText("Details - " + issue.getSummary());
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanelTop = new javax.swing.JPanel();
      jPanelHeader = new javax.swing.JPanel();
      jButtonClose = new javax.swing.JButton();
      jLabelIssueTitle = new javax.swing.JLabel();
      jScrollPaneData = new javax.swing.JScrollPane();
      jTextAreaDescription = new javax.swing.JTextArea();

      setLayout(new java.awt.BorderLayout());

      jPanelTop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      jPanelTop.setLayout(new java.awt.BorderLayout());

      jPanelHeader.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));

      jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/close.png"))); // NOI18N
      jButtonClose.setBorder(null);
      jButtonClose.setBorderPainted(false);
      jButtonClose.setFocusPainted(false);
      jButtonClose.setFocusable(false);
      jButtonClose.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonCloseActionPerformed(evt);
         }
      });
      jPanelHeader.add(jButtonClose);

      jPanelTop.add(jPanelHeader, java.awt.BorderLayout.LINE_END);
      jPanelTop.add(jLabelIssueTitle, java.awt.BorderLayout.CENTER);

      add(jPanelTop, java.awt.BorderLayout.PAGE_START);

      jTextAreaDescription.setColumns(20);
      jTextAreaDescription.setRows(5);
      jScrollPaneData.setViewportView(jTextAreaDescription);

      add(jScrollPaneData, java.awt.BorderLayout.CENTER);
   }// </editor-fold>//GEN-END:initComponents

   private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
      jPopupMenuDetail.setVisible(false);
   }//GEN-LAST:event_jButtonCloseActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonClose;
   private javax.swing.JLabel jLabelIssueTitle;
   private javax.swing.JPanel jPanelHeader;
   private javax.swing.JPanel jPanelTop;
   private javax.swing.JScrollPane jScrollPaneData;
   private javax.swing.JTextArea jTextAreaDescription;
   // End of variables declaration//GEN-END:variables
}

package br.com.ggroup.view;

import br.com.ggroup.models.MRequest;
import br.com.ggroup.models.MResponse;
import br.com.ggroup.models.MWorkLog;
import br.com.ggroup.panels.PanelIssue;
import br.com.ggroup.util.APIMethod;
import br.com.ggroup.util.APIRequest;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.json.JSONObject;

/**
 *
 * @author Fabio Garcia
 */
public class DialogSendToJira extends javax.swing.JDialog {

   private final JSONObject sendContent;
   private final HashMap<String, String> params;
   private final PanelIssue panelIssue;

   public DialogSendToJira(Frame parent, PanelIssue panelIssue, JSONObject sendContent) {
      super(parent, true);
      initComponents();

      this.panelIssue = panelIssue;
      this.sendContent = sendContent;
      this.params = new HashMap<>();
      setLocationRelativeTo(parent);
      setPreferredSize(new Dimension(500, 300));

      initFields();
   }

   private void initFields() {
      jTextFieldIssue.setText(panelIssue.getIssue().getKey() + " - " + panelIssue.getIssue().getSummary());
      jTextFieldWorklog.setText(panelIssue.getChronometer().getStringTime());
   }

   private void sendToJira() throws Exception {
      params.clear();
      if (!jTextAreaComment.getText().trim().isEmpty()) {
         sendContent.put("comment", jTextAreaComment.getText().trim());
      }
      params.put("{issueIdOrKey}", panelIssue.getIssue().getKey());

      MRequest request = new MRequest(((FormTimeTracking) panelIssue.getFormTimeTracking()).getConfig(), APIMethod.addWorklog(params), sendContent);

      MResponse response = APIRequest.sendRequest(request);

      panelIssue.getIssue().getWorkLog().add(new MWorkLog(response.getReturnObject()));
      JOptionPane.showMessageDialog(this, "Worklog sended successful.");
      panelIssue.finishSend();
      this.dispose();

   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanelBottom = new javax.swing.JPanel();
      jButtonCancel = new javax.swing.JButton();
      jButtonSend = new javax.swing.JButton();
      jPanelContent = new javax.swing.JPanel();
      jLabelWorklog = new javax.swing.JLabel();
      jTextFieldWorklog = new javax.swing.JTextField();
      jLabelIssue = new javax.swing.JLabel();
      jTextFieldIssue = new javax.swing.JTextField();
      jLabelComment = new javax.swing.JLabel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jTextAreaComment = new javax.swing.JTextArea();

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

      jPanelBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      jPanelBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      jButtonCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/close.png"))); // NOI18N
      jButtonCancel.setText("Cancel");
      jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonCancelActionPerformed(evt);
         }
      });
      jPanelBottom.add(jButtonCancel);

      jButtonSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/success.png"))); // NOI18N
      jButtonSend.setText("Send");
      jButtonSend.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonSendActionPerformed(evt);
         }
      });
      jPanelBottom.add(jButtonSend);

      getContentPane().add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

      jLabelWorklog.setText("Worklog time:");

      jTextFieldWorklog.setEditable(false);

      jLabelIssue.setText("Issue:");

      jTextFieldIssue.setEditable(false);

      jLabelComment.setText("Comment:");

      jTextAreaComment.setColumns(20);
      jTextAreaComment.setRows(5);
      jScrollPane1.setViewportView(jTextAreaComment);

      javax.swing.GroupLayout jPanelContentLayout = new javax.swing.GroupLayout(jPanelContent);
      jPanelContent.setLayout(jPanelContentLayout);
      jPanelContentLayout.setHorizontalGroup(
         jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelContentLayout.createSequentialGroup()
            .addGap(6, 6, 6)
            .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabelIssue)
               .addComponent(jLabelWorklog)
               .addComponent(jLabelComment))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
               .addComponent(jTextFieldIssue)
               .addGroup(jPanelContentLayout.createSequentialGroup()
                  .addComponent(jTextFieldWorklog, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanelContentLayout.setVerticalGroup(
         jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelContentLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabelIssue)
               .addComponent(jTextFieldIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jTextFieldWorklog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabelWorklog))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabelComment)
               .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      getContentPane().add(jPanelContent, java.awt.BorderLayout.CENTER);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
      this.dispose();
   }//GEN-LAST:event_jButtonCancelActionPerformed

   private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
      try {
         sendToJira();
      } catch (Exception e) {
         JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
      }
   }//GEN-LAST:event_jButtonSendActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonCancel;
   private javax.swing.JButton jButtonSend;
   private javax.swing.JLabel jLabelComment;
   private javax.swing.JLabel jLabelIssue;
   private javax.swing.JLabel jLabelWorklog;
   private javax.swing.JPanel jPanelBottom;
   private javax.swing.JPanel jPanelContent;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTextArea jTextAreaComment;
   private javax.swing.JTextField jTextFieldIssue;
   private javax.swing.JTextField jTextFieldWorklog;
   // End of variables declaration//GEN-END:variables
}

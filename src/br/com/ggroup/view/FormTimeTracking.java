package br.com.ggroup.view;

import br.com.ggroup.components.JMenuItemCustom;
import br.com.ggroup.models.MBoard;
import br.com.ggroup.models.MConfig;
import br.com.ggroup.models.MIssue;
import br.com.ggroup.models.MMenuItem;
import br.com.ggroup.models.MRequest;
import br.com.ggroup.models.MResponse;
import br.com.ggroup.models.MSprint;
import br.com.ggroup.models.MUser;
import br.com.ggroup.panels.PanelAvatar;
import br.com.ggroup.panels.PanelIssue;
import br.com.ggroup.util.APIMethod;
import br.com.ggroup.util.APIRequest;
import br.com.ggroup.util.CustomizeLayout;
import br.com.ggroup.util.LaFThemes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Fabio Garcia
 */
public class FormTimeTracking extends javax.swing.JFrame {

   private final MConfig config;
   private MUser user;
   private final HashMap<String, String> params;
   private final ArrayList<MIssue> listIssues;
   private final String args[];

   public FormTimeTracking(String args[]) {
      initComponents();

      this.args = args;

      config = new MConfig();
      this.params = new HashMap<>();
      this.listIssues = new ArrayList<>();

      jScrollPane1.getVerticalScrollBar().setUnitIncrement(13);
      this.setLocationRelativeTo(null);
      setIconImage(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/stopwatch.png")).getImage());
      configThemes();
      verifyArguments();
   }

   public MConfig getConfig() {
      return config;
   }

   @Override
   public String getTitle() {
      return "JTimeTracking";
   }

   private void configThemes() {
      for (final MMenuItem obj : LaFThemes.getThemes()) {
         JMenuItemCustom it = new JMenuItemCustom(obj);

         it.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                  setLaF(obj.getPath());
               } catch (Exception ex) {
                  JOptionPane.showMessageDialog(getRootPane(), ex);
               }
            }
         });

         jMenuThemes.add(it);
      }
   }

   private void verifyArguments() {
      if (args.length == 3) {
         jTextFieldURL.setText(args[0]);
         jTextFieldUsername.setText(args[1]);
         jPasswordFieldPassword.setText(args[2]);
      }
   }

   private void connect() {
      if (jTextFieldURL.getText().trim().isEmpty()) {
         JOptionPane.showMessageDialog(getRootPane(), "The field URL is required.");
         return;
      }
      if (jTextFieldUsername.getText().trim().isEmpty()) {
         JOptionPane.showMessageDialog(getRootPane(), "The field Username is required.");
         return;
      }
      if (jPasswordFieldPassword.getPassword().length == 0) {
         JOptionPane.showMessageDialog(getRootPane(), "The field Password is required.");
         return;
      }

      config.setUrl(jTextFieldURL.getText().trim());
      config.setUserName(jTextFieldUsername.getText().trim());
      config.setPassword(new String(jPasswordFieldPassword.getPassword()));
      //getToken();
      getUser();
      if (user != null) {
         MRequest request = new MRequest(config, APIMethod.allBoards(null), null);

         try {
            MResponse response = APIRequest.sendRequest(request);
            JOptionPane.showMessageDialog(getRootPane(), "Connected!");
            getAllBoards(response);
         } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
         }
      }
   }

   private void getUser() {
      params.clear();
      if (jTextFieldUsername.getText().contains("@")) {
         params.put("{key}", jTextFieldUsername.getText().trim().substring(0, jTextFieldUsername.getText().trim().indexOf("@")));
      } else {
         params.put("{key}", jTextFieldUsername.getText().trim());
      }

      MRequest request = new MRequest(config, APIMethod.getUser(params), null);

      try {
         MResponse response = APIRequest.sendRequest(request);
         user = new MUser(response.getReturnObject());

         jPanelAvatar.add(new PanelAvatar(user.getImg16x16()));
         jLabelUserDisplayName.setText(" Welcome, " + user.getDisplayName() + ".");
      } catch (Exception e) {
         JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
      }
   }

   private void getAllBoards(MResponse response) {
      ArrayList<MBoard> listBoards = new ArrayList<>();

      try {
         listBoards.add(new MBoard(null));
         for (int i = 0; i < response.getReturnObject().getJSONArray("values").length(); i++) {
            listBoards.add(new MBoard(response.getReturnObject().getJSONArray("values").getJSONObject(i)));
         }
         jComboBoxBoard.setModel(new DefaultComboBoxModel(listBoards.toArray()));
         controlFields(true);
      } catch (Exception e) {
         JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
      }
   }

   private void getAllActiveSprints() {
      MBoard board = ((MBoard) jComboBoxBoard.getSelectedItem());
      if (board.getId() != -1) {

         try {
            params.clear();

            params.put("{boardId}", String.valueOf(board.getId()));

            MRequest request = new MRequest(config, APIMethod.allActiveSprintsFromBoard(params), null);
            MResponse response = APIRequest.sendRequest(request);

            ArrayList<MSprint> listSprints = new ArrayList<>();

            listSprints.add(new MSprint(null));
            for (int i = 0; i < response.getReturnObject().getJSONArray("values").length(); i++) {
               listSprints.add(new MSprint(response.getReturnObject().getJSONArray("values").getJSONObject(i)));
            }
            jComboBoxSprint.setModel(new DefaultComboBoxModel(listSprints.toArray()));
         } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
         }
      }
   }

   private void getAllIssuesFromSprint() {
      MSprint sprint = ((MSprint) jComboBoxSprint.getSelectedItem());
      listIssues.clear();

      if (sprint.getId() != -1) {
         try {
            params.clear();
            params.put("{sprintId}", String.valueOf(sprint.getId()));

            MRequest request = new MRequest(config, APIMethod.allIssuesFromSprint(params), null);
            MResponse response = APIRequest.sendRequest(request);

            for (int i = 0; i < response.getReturnObject().getJSONArray("issues").length(); i++) {
               listIssues.add(new MIssue(response.getReturnObject().getJSONArray("issues").getJSONObject(i)));
            }
         } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
         }
      }
      createIssuesPanel();
   }

   private void createIssuesPanel() {
      jPanelIssuesContent.removeAll();

      jPanelIssuesContent.setLayout(new CustomizeLayout());
      for (MIssue listIssue : listIssues) {
         if (jCheckBoxOnlyMyIssues.isSelected()) {
            if (listIssue.getAssignedEmail().equals(user.getEmailAddress())) {
               jPanelIssuesContent.add(new PanelIssue(this, listIssue, user));
            }
         } else {
            jPanelIssuesContent.add(new PanelIssue(this, listIssue, user));
         }
      }
      revalidate();
      repaint();
   }

   private void controlFields(boolean status) {
      jComboBoxBoard.setEnabled(status);
      jComboBoxSprint.setEnabled(status);
   }

   private void setLaF(String path) throws Exception {
      UIManager.setLookAndFeel(path);
      revalidate();
      repaint();
   }

   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanelTop = new javax.swing.JPanel();
      jLabelURL = new javax.swing.JLabel();
      jTextFieldURL = new javax.swing.JTextField();
      jLabelUsername = new javax.swing.JLabel();
      jTextFieldUsername = new javax.swing.JTextField();
      jLabelPassword = new javax.swing.JLabel();
      jButtonConnect = new javax.swing.JButton();
      jPasswordFieldPassword = new javax.swing.JPasswordField();
      jPanelBottom = new javax.swing.JPanel();
      jPanelBottomRight = new javax.swing.JPanel();
      jButtonClose = new javax.swing.JButton();
      jPanelBottomLeft = new javax.swing.JPanel();
      jPanelAvatar = new javax.swing.JPanel();
      jLabelUserDisplayName = new javax.swing.JLabel();
      jPanelCenter = new javax.swing.JPanel();
      jPanelOptions = new javax.swing.JPanel();
      jLabelBoard = new javax.swing.JLabel();
      jComboBoxBoard = new javax.swing.JComboBox<>();
      jLabelSprint = new javax.swing.JLabel();
      jComboBoxSprint = new javax.swing.JComboBox<>();
      jCheckBoxOnlyMyIssues = new javax.swing.JCheckBox();
      jPanelIssues = new javax.swing.JPanel();
      jTabbedPaneIssues = new javax.swing.JTabbedPane();
      jPanelMyTasks = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      jPanelIssuesContent = new javax.swing.JPanel();
      jPanelOthers = new javax.swing.JPanel();
      jLabelFindTask = new javax.swing.JLabel();
      jTextFieldFindTask = new javax.swing.JTextField();
      jLabel1 = new javax.swing.JLabel();
      jMenuBarThemes = new javax.swing.JMenuBar();
      jMenuThemes = new javax.swing.JMenu();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

      jPanelTop.setBorder(javax.swing.BorderFactory.createTitledBorder("Credentials"));

      jLabelURL.setText("URL:");

      jLabelUsername.setText("Username:");

      jLabelPassword.setText("Password:");

      jButtonConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/network.png"))); // NOI18N
      jButtonConnect.setText("Connect");
      jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonConnectActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanelTopLayout = new javax.swing.GroupLayout(jPanelTop);
      jPanelTop.setLayout(jPanelTopLayout);
      jPanelTopLayout.setHorizontalGroup(
         jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelTopLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jLabelURL)
               .addComponent(jLabelUsername))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addGroup(jPanelTopLayout.createSequentialGroup()
                  .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jLabelPassword)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jTextFieldURL))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButtonConnect)
            .addContainerGap())
      );
      jPanelTopLayout.setVerticalGroup(
         jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelTopLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabelURL)
               .addComponent(jTextFieldURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanelTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabelUsername)
               .addComponent(jLabelPassword)
               .addComponent(jButtonConnect)
               .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      getContentPane().add(jPanelTop, java.awt.BorderLayout.PAGE_START);

      jPanelBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      jPanelBottom.setLayout(new java.awt.BorderLayout());

      jPanelBottomRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      jButtonClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/ggroup/resources/close.png"))); // NOI18N
      jButtonClose.setText("Close");
      jButtonClose.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonCloseActionPerformed(evt);
         }
      });
      jPanelBottomRight.add(jButtonClose);

      jPanelBottom.add(jPanelBottomRight, java.awt.BorderLayout.LINE_END);

      jPanelBottomLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 10));

      jPanelAvatar.setMinimumSize(new java.awt.Dimension(24, 24));
      jPanelAvatar.setLayout(new java.awt.BorderLayout());
      jPanelBottomLeft.add(jPanelAvatar);

      jLabelUserDisplayName.setText(" ");
      jPanelBottomLeft.add(jLabelUserDisplayName);

      jPanelBottom.add(jPanelBottomLeft, java.awt.BorderLayout.CENTER);

      getContentPane().add(jPanelBottom, java.awt.BorderLayout.PAGE_END);

      jPanelCenter.setLayout(new java.awt.BorderLayout());

      jPanelOptions.setBorder(javax.swing.BorderFactory.createTitledBorder("Issues From"));

      jLabelBoard.setText("Board:");

      jComboBoxBoard.setEnabled(false);
      jComboBoxBoard.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxBoardActionPerformed(evt);
         }
      });

      jLabelSprint.setText("Sprint:");

      jComboBoxSprint.setEnabled(false);
      jComboBoxSprint.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBoxSprintActionPerformed(evt);
         }
      });

      jCheckBoxOnlyMyIssues.setText("Only my issues");
      jCheckBoxOnlyMyIssues.setMargin(new java.awt.Insets(2, 0, 2, 2));
      jCheckBoxOnlyMyIssues.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBoxOnlyMyIssuesActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
      jPanelOptions.setLayout(jPanelOptionsLayout);
      jPanelOptionsLayout.setHorizontalGroup(
         jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelOptionsLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanelOptionsLayout.createSequentialGroup()
                  .addComponent(jLabelBoard)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jComboBoxBoard, 0, 300, Short.MAX_VALUE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jLabelSprint)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jComboBoxSprint, 0, 337, Short.MAX_VALUE))
               .addGroup(jPanelOptionsLayout.createSequentialGroup()
                  .addComponent(jCheckBoxOnlyMyIssues)
                  .addGap(0, 0, Short.MAX_VALUE)))
            .addContainerGap())
      );
      jPanelOptionsLayout.setVerticalGroup(
         jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelOptionsLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabelBoard)
               .addComponent(jComboBoxBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabelSprint)
               .addComponent(jComboBoxSprint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(7, 7, 7)
            .addComponent(jCheckBoxOnlyMyIssues)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );

      jPanelCenter.add(jPanelOptions, java.awt.BorderLayout.PAGE_START);

      jPanelIssues.setLayout(new java.awt.BorderLayout());

      jPanelMyTasks.setLayout(new java.awt.BorderLayout());

      javax.swing.GroupLayout jPanelIssuesContentLayout = new javax.swing.GroupLayout(jPanelIssuesContent);
      jPanelIssuesContent.setLayout(jPanelIssuesContentLayout);
      jPanelIssuesContentLayout.setHorizontalGroup(
         jPanelIssuesContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 753, Short.MAX_VALUE)
      );
      jPanelIssuesContentLayout.setVerticalGroup(
         jPanelIssuesContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 400, Short.MAX_VALUE)
      );

      jScrollPane1.setViewportView(jPanelIssuesContent);

      jPanelMyTasks.add(jScrollPane1, java.awt.BorderLayout.CENTER);

      jTabbedPaneIssues.addTab("1. My tasks", jPanelMyTasks);

      jLabelFindTask.setText("Find task (by code):");

      jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
      jLabel1.setText("Not implemented yet!");

      javax.swing.GroupLayout jPanelOthersLayout = new javax.swing.GroupLayout(jPanelOthers);
      jPanelOthers.setLayout(jPanelOthersLayout);
      jPanelOthersLayout.setHorizontalGroup(
         jPanelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelOthersLayout.createSequentialGroup()
            .addGroup(jPanelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanelOthersLayout.createSequentialGroup()
                  .addContainerGap()
                  .addComponent(jLabelFindTask)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jTextFieldFindTask, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addGroup(jPanelOthersLayout.createSequentialGroup()
                  .addGap(169, 169, 169)
                  .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(214, Short.MAX_VALUE))
      );
      jPanelOthersLayout.setVerticalGroup(
         jPanelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanelOthersLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanelOthersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabelFindTask)
               .addComponent(jTextFieldFindTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(102, 102, 102)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(195, Short.MAX_VALUE))
      );

      jTabbedPaneIssues.addTab("2. Others", jPanelOthers);

      jPanelIssues.add(jTabbedPaneIssues, java.awt.BorderLayout.CENTER);

      jPanelCenter.add(jPanelIssues, java.awt.BorderLayout.CENTER);

      getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

      jMenuThemes.setText("Themes");
      jMenuBarThemes.add(jMenuThemes);

      setJMenuBar(jMenuBarThemes);

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
      connect();
   }//GEN-LAST:event_jButtonConnectActionPerformed

   private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
      System.exit(0);
   }//GEN-LAST:event_jButtonCloseActionPerformed

   private void jComboBoxBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBoardActionPerformed
      getAllActiveSprints();
   }//GEN-LAST:event_jComboBoxBoardActionPerformed

   private void jComboBoxSprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSprintActionPerformed
      getAllIssuesFromSprint();
   }//GEN-LAST:event_jComboBoxSprintActionPerformed

   private void jCheckBoxOnlyMyIssuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOnlyMyIssuesActionPerformed
      createIssuesPanel();
   }//GEN-LAST:event_jCheckBoxOnlyMyIssuesActionPerformed

   public static void main(final String args[]) {

      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
               UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
            } catch (Exception e) {
               e.printStackTrace();
            }
            new FormTimeTracking(args).setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButtonClose;
   private javax.swing.JButton jButtonConnect;
   private javax.swing.JCheckBox jCheckBoxOnlyMyIssues;
   private javax.swing.JComboBox<String> jComboBoxBoard;
   private javax.swing.JComboBox<String> jComboBoxSprint;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabelBoard;
   private javax.swing.JLabel jLabelFindTask;
   private javax.swing.JLabel jLabelPassword;
   private javax.swing.JLabel jLabelSprint;
   private javax.swing.JLabel jLabelURL;
   private javax.swing.JLabel jLabelUserDisplayName;
   private javax.swing.JLabel jLabelUsername;
   private javax.swing.JMenuBar jMenuBarThemes;
   private javax.swing.JMenu jMenuThemes;
   private javax.swing.JPanel jPanelAvatar;
   private javax.swing.JPanel jPanelBottom;
   private javax.swing.JPanel jPanelBottomLeft;
   private javax.swing.JPanel jPanelBottomRight;
   private javax.swing.JPanel jPanelCenter;
   private javax.swing.JPanel jPanelIssues;
   private javax.swing.JPanel jPanelIssuesContent;
   private javax.swing.JPanel jPanelMyTasks;
   private javax.swing.JPanel jPanelOptions;
   private javax.swing.JPanel jPanelOthers;
   private javax.swing.JPanel jPanelTop;
   private javax.swing.JPasswordField jPasswordFieldPassword;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTabbedPane jTabbedPaneIssues;
   private javax.swing.JTextField jTextFieldFindTask;
   private javax.swing.JTextField jTextFieldURL;
   private javax.swing.JTextField jTextFieldUsername;
   // End of variables declaration//GEN-END:variables
}

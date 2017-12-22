/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

/**
 *
 * @author thr6
 */
public class MainFrame extends javax.swing.JFrame implements Elevator.ElevatorListener {
    private final int delay = 2000;
    private final String dingSoundPath = "resources/ding.wav";
    private final String doorsSoundPath = "resources/doors.wav";
    private final Elevator elevator;
    private final Color buttonDefaultColor;
    
    private Clip dingSound;
    private Clip doorsSound;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        try{
            InputStream dingSoundStream = MainFrame.class.getResourceAsStream(dingSoundPath);
            dingSoundStream = new BufferedInputStream(dingSoundStream);
            AudioInputStream dingAudioStream = AudioSystem.getAudioInputStream(dingSoundStream);
            dingSound = AudioSystem.getClip();
            dingSound.open(dingAudioStream);
            InputStream doorsSoundStream = MainFrame.class.getResourceAsStream(doorsSoundPath);
            doorsSoundStream = new BufferedInputStream(doorsSoundStream);
            AudioInputStream doorsAudioStream = AudioSystem.getAudioInputStream(doorsSoundStream);
            doorsSound = AudioSystem.getClip();
            doorsSound.open(doorsAudioStream);
        }catch(UnsupportedAudioFileException | LineUnavailableException | IOException exc){
            JOptionPane.showMessageDialog(null, "Error loading resources: " + exc.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        
        this.elevator = new Elevator(4, delay);
        this.elevator.addListener(this);
        
        this.buttonDefaultColor = new Color(238, 238, 238);
        
        btnLobbyUp.putClientProperty("floor", 1);
        btnLobbyUp.putClientProperty("direction", Elevator.Direction.UP);
        
        btnFloor2Up.putClientProperty("floor", 2);
        btnFloor2Up.putClientProperty("direction", Elevator.Direction.UP);
        btnFloor2Down.putClientProperty("floor", 2);
        btnFloor2Down.putClientProperty("direction", Elevator.Direction.DOWN);
        
        btnFloor3Up.putClientProperty("floor", 3);
        btnFloor3Up.putClientProperty("direction", Elevator.Direction.UP);
        btnFloor3Down.putClientProperty("floor", 3);
        btnFloor3Down.putClientProperty("direction", Elevator.Direction.DOWN);
        
        btnFloor4Down.putClientProperty("floor", 4);
        btnFloor4Down.putClientProperty("direction", Elevator.Direction.DOWN);
        
        btnLobby.putClientProperty("floor", 1);
        btnFloor2.putClientProperty("floor", 2);
        btnFloor3.putClientProperty("floor", 3);
        btnFloor4.putClientProperty("floor", 4);
        
        updateState();
    }

    private void updateState(){
        lbFloor.setText("" + elevator.getFloor());
        
        lbDoors.setText(elevator.getDoorState() == Elevator.DoorState.OPEN ? "Doors Open" : "Doors Closed");
        
        if(elevator.getDirection() == Elevator.Direction.UP){
            lbUp.setForeground(Color.GREEN);
            lbDown.setForeground(Color.BLACK);
        }else{
            lbUp.setForeground(Color.BLACK);
            lbDown.setForeground(Color.GREEN);
        }
        
        Map<Integer, List<Elevator.Direction>> floors = elevator.getRequests();
        
        btnLobby.setBackground(floors.get(1).contains(Elevator.Direction.NONE) ? Color.YELLOW : buttonDefaultColor);
        btnLobbyUp.setBackground(floors.get(1).contains(Elevator.Direction.UP) ? Color.YELLOW : buttonDefaultColor);
        
        btnFloor2.setBackground(floors.get(2).contains(Elevator.Direction.NONE) ? Color.YELLOW : buttonDefaultColor);
        btnFloor2Up.setBackground(floors.get(2).contains(Elevator.Direction.UP) ? Color.YELLOW : buttonDefaultColor);
        btnFloor2Down.setBackground(floors.get(2).contains(Elevator.Direction.DOWN) ? Color.YELLOW : buttonDefaultColor);
        
        btnFloor3.setBackground(floors.get(3).contains(Elevator.Direction.NONE) ? Color.YELLOW : buttonDefaultColor);
        btnFloor3Up.setBackground(floors.get(3).contains(Elevator.Direction.UP) ? Color.YELLOW : buttonDefaultColor);
        btnFloor3Down.setBackground(floors.get(3).contains(Elevator.Direction.DOWN) ? Color.YELLOW : buttonDefaultColor);
        
        btnFloor4.setBackground(floors.get(4).contains(Elevator.Direction.NONE) ? Color.YELLOW : buttonDefaultColor);
        btnFloor4Down.setBackground(floors.get(4).contains(Elevator.Direction.DOWN) ? Color.YELLOW : buttonDefaultColor);
    }
    
    @Override
    public void onElevatorUpdate(Elevator.Command cmd) {
        if(cmd == Elevator.Command.OPEN_DOORS || cmd == Elevator.Command.CLOSE_DOORS){
            doorsSound.stop();
            doorsSound.flush();
            doorsSound.setFramePosition(0);
            doorsSound.start();
        }else if(cmd == Elevator.Command.MOVE_UP || cmd == Elevator.Command.MOVE_DOWN){
            dingSound.stop();
            dingSound.flush();
            dingSound.setFramePosition(0);
            dingSound.start();
        }
        updateState();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInside = new javax.swing.JPanel();
        btnLobby = new javax.swing.JButton();
        btnFloor3 = new javax.swing.JButton();
        btnFloor2 = new javax.swing.JButton();
        btnFloor4 = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        lbUp = new javax.swing.JLabel();
        lbDown = new javax.swing.JLabel();
        lbFloor = new javax.swing.JLabel();
        btnLobbyUp = new javax.swing.JButton();
        lbLobby = new javax.swing.JLabel();
        lbFloor2 = new javax.swing.JLabel();
        btnFloor2Up = new javax.swing.JButton();
        btnFloor2Down = new javax.swing.JButton();
        lbFloor3 = new javax.swing.JLabel();
        btnFloor3Up = new javax.swing.JButton();
        btnFloor3Down = new javax.swing.JButton();
        lbFloor4 = new javax.swing.JLabel();
        btnFloor4Down = new javax.swing.JButton();
        lbDoors = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(286, 556));
        setMinimumSize(new java.awt.Dimension(286, 556));

        pnlInside.setBackground(new java.awt.Color(102, 102, 102));

        btnLobby.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnLobby.setText("*L");
        btnLobby.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFloorActionHandler(evt);
            }
        });

        btnFloor3.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor3.setText("3");
        btnFloor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFloorActionHandler(evt);
            }
        });

        btnFloor2.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor2.setText("2");
        btnFloor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFloorActionHandler(evt);
            }
        });

        btnFloor4.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor4.setText("4");
        btnFloor4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFloorActionHandler(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnClose.setText("▶|◀");
        btnClose.setToolTipText("");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnOpen.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnOpen.setText("◀|▶");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInsideLayout = new javax.swing.GroupLayout(pnlInside);
        pnlInside.setLayout(pnlInsideLayout);
        pnlInsideLayout.setHorizontalGroup(
            pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsideLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInsideLayout.createSequentialGroup()
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInsideLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLobby, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlInsideLayout.createSequentialGroup()
                                .addComponent(btnFloor2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFloor3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFloor4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlInsideLayout.setVerticalGroup(
            pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInsideLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFloor3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFloor2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFloor4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnLobby, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlInsideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbUp.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbUp.setText("▲");

        lbDown.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbDown.setText("▼");

        lbFloor.setFont(new java.awt.Font("Lucida Grande", 1, 36)); // NOI18N
        lbFloor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFloor.setText("###");
        lbFloor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnLobbyUp.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnLobbyUp.setText("▲");
        btnLobbyUp.setName(""); // NOI18N
        btnLobbyUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        lbLobby.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbLobby.setText("Lobby");

        lbFloor2.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbFloor2.setText("Level 2");

        btnFloor2Up.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor2Up.setText("▲");
        btnFloor2Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        btnFloor2Down.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor2Down.setText("▼");
        btnFloor2Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        lbFloor3.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbFloor3.setText("Level 3");

        btnFloor3Up.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor3Up.setText("▲");
        btnFloor3Up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        btnFloor3Down.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor3Down.setText("▼");
        btnFloor3Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        lbFloor4.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbFloor4.setText("Level 4");

        btnFloor4Down.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        btnFloor4Down.setText("▼");
        btnFloor4Down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requestLiftActionHandler(evt);
            }
        });

        lbDoors.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        lbDoors.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDoors.setText("Doors Open");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInside, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbDoors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbDown)
                        .addGap(18, 18, 18)
                        .addComponent(lbFloor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lbUp))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLobbyUp, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addComponent(lbLobby))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnFloor2Up, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFloor2Down, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFloor2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnFloor3Up, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFloor3Down, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFloor3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(btnFloor4Down, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbFloor4)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDown)
                    .addComponent(lbFloor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbUp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbDoors)
                .addGap(18, 18, 18)
                .addComponent(pnlInside, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFloor4Down, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFloor4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFloor3Up, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFloor3Down, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFloor3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFloor2Up, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFloor2Down, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbFloor2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLobbyUp, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbLobby))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void requestLiftActionHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requestLiftActionHandler
        int floor = (int)((javax.swing.JButton)evt.getSource()).getClientProperty("floor");
        Elevator.Direction direction = (Elevator.Direction)((javax.swing.JButton)evt.getSource()).getClientProperty("direction");
        elevator.requestALift(floor, direction);
        updateState();
    }//GEN-LAST:event_requestLiftActionHandler

    private void selectFloorActionHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFloorActionHandler
        int floor = (int)((javax.swing.JButton)evt.getSource()).getClientProperty("floor");
        elevator.selectFloor(floor);
        updateState();
    }//GEN-LAST:event_selectFloorActionHandler

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        elevator.closeDoors();
        updateState();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        elevator.openDoors();
        updateState();
    }//GEN-LAST:event_btnOpenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnFloor2;
    private javax.swing.JButton btnFloor2Down;
    private javax.swing.JButton btnFloor2Up;
    private javax.swing.JButton btnFloor3;
    private javax.swing.JButton btnFloor3Down;
    private javax.swing.JButton btnFloor3Up;
    private javax.swing.JButton btnFloor4;
    private javax.swing.JButton btnFloor4Down;
    private javax.swing.JButton btnLobby;
    private javax.swing.JButton btnLobbyUp;
    private javax.swing.JButton btnOpen;
    private javax.swing.JLabel lbDoors;
    private javax.swing.JLabel lbDown;
    private javax.swing.JLabel lbFloor;
    private javax.swing.JLabel lbFloor2;
    private javax.swing.JLabel lbFloor3;
    private javax.swing.JLabel lbFloor4;
    private javax.swing.JLabel lbLobby;
    private javax.swing.JLabel lbUp;
    private javax.swing.JPanel pnlInside;
    // End of variables declaration//GEN-END:variables
}

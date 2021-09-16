/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.GraphicsEnvironment;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.Timer;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CuttingFileNotify
/*    */   implements ActionListener
/*    */ {
/*    */   Timer StatusTimer;
/*    */   JFrame J;
/*    */   
/*    */   public CuttingFileNotify() {
/* 26 */     Rectangle dim = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
/* 27 */     this.J = new JFrame("Status..");
/* 28 */     this.J.setDefaultCloseOperation(2);
/* 29 */     this.J.setSize(new Dimension(240, 140));
/* 30 */     this.J.setLocation(dim.width / 2 - this.J.getWidth() / 2, dim.height / 2 - this.J.getHeight() / 2);
/* 31 */     JPanel contentPane = new JPanel();
/* 32 */     contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 33 */     this.J.setContentPane(contentPane);
/* 34 */     contentPane.setLayout(new GridBagLayout());
/*    */     
/* 36 */     JLabel textFieldBP = new JLabel("Cutting File..");
/* 37 */     textFieldBP.setHorizontalAlignment(0);
/* 38 */     contentPane.add(textFieldBP);
/* 39 */     this.J.pack();
/*    */     
/* 41 */     this.StatusTimer = new Timer(1500, this);
/* 42 */     this.J.setVisible(true);
/*    */     
/* 44 */     this.StatusTimer.start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 50 */     this.J.dispose();
/* 51 */     this.StatusTimer.stop();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/CuttingFileNotify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
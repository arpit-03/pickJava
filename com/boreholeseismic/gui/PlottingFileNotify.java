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
/*    */ 
/*    */ public class PlottingFileNotify
/*    */   implements ActionListener
/*    */ {
/*    */   Timer StatusTimer;
/*    */   JFrame J;
/*    */   
/*    */   public PlottingFileNotify() {
/* 27 */     Rectangle dim = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
/*    */     
/* 29 */     this.J = new JFrame("Status..");
/* 30 */     this.J.setDefaultCloseOperation(2);
/* 31 */     this.J.setSize(new Dimension(240, 140));
/* 32 */     this.J.setLocation(dim.width / 2 - this.J.getWidth() / 2, dim.height / 2 - this.J.getHeight() / 2);
/* 33 */     JPanel contentPane = new JPanel();
/* 34 */     contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 35 */     this.J.setContentPane(contentPane);
/* 36 */     contentPane.setLayout(new GridBagLayout());
/*    */     
/* 38 */     JLabel textFieldBP = new JLabel("Plotting File..");
/* 39 */     textFieldBP.setHorizontalAlignment(0);
/* 40 */     contentPane.add(textFieldBP);
/*    */     
/* 42 */     this.StatusTimer = new Timer(1500, this);
/* 43 */     this.J.validate();
/* 44 */     this.J.setVisible(true);
/*    */     
/* 46 */     this.StatusTimer.start();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 52 */     this.J.dispose();
/* 53 */     this.StatusTimer.stop();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/PlottingFileNotify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
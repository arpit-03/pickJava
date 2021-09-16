/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.RenderingHints;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoundedPanel
/*    */   extends JPanel
/*    */ {
/*    */   protected void paintComponent(Graphics g) {
/* 21 */     super.paintComponent(g);
/* 22 */     Dimension arcs = new Dimension(15, 15);
/* 23 */     int width = getWidth();
/* 24 */     int height = getHeight();
/* 25 */     Graphics2D graphics = (Graphics2D)g;
/* 26 */     graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*    */ 
/*    */     
/* 29 */     graphics.setColor(new Color(220, 221, 220));
/* 30 */     graphics.fillRect(0, 0, width, height);
/*    */ 
/*    */     
/* 33 */     graphics.setColor(getBackground());
/* 34 */     graphics.fillRoundRect(0, 0, width, height, arcs.width, arcs.height);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/RoundedPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
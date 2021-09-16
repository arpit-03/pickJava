/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Insets;
/*    */ import java.awt.geom.Area;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.geom.RoundRectangle2D;
/*    */ import javax.swing.border.AbstractBorder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoundedBorder
/*    */   extends AbstractBorder
/*    */ {
/*    */   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
/* 22 */     Graphics2D g2 = (Graphics2D)g.create();
/*    */     
/* 24 */     int r = 15;
/* 25 */     RoundRectangle2D round = new RoundRectangle2D.Float(x, y, width, height, r, r);
/* 26 */     Container parent = c.getParent();
/* 27 */     if (parent != null) {
/* 28 */       g2.setColor(parent.getBackground());
/* 29 */       Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
/* 30 */       corner.subtract(new Area(round));
/*    */     } 
/*    */ 
/*    */     
/* 34 */     g2.draw(round);
/* 35 */     g2.dispose();
/*    */   }
/*    */ 
/*    */   
/*    */   public Insets getBorderInsets(Component c) {
/* 40 */     return new Insets(5, 5, 5, 5);
/*    */   }
/*    */   
/*    */   public Insets getBorderInsets(Component c, Insets insets) {
/* 44 */     insets.left = insets.right = 5;
/* 45 */     insets.top = insets.bottom = 5;
/* 46 */     return insets;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/RoundedBorder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
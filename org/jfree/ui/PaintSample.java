/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import javax.swing.JComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaintSample
/*     */   extends JComponent
/*     */ {
/*     */   private Paint paint;
/*     */   private Dimension preferredSize;
/*     */   
/*     */   public PaintSample(Paint paint) {
/*  74 */     this.paint = paint;
/*  75 */     this.preferredSize = new Dimension(80, 12);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/*  84 */     return this.paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPaint(Paint paint) {
/*  93 */     this.paint = paint;
/*  94 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 103 */     return this.preferredSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 113 */     Graphics2D g2 = (Graphics2D)g;
/* 114 */     Dimension size = getSize();
/* 115 */     Insets insets = getInsets();
/* 116 */     double xx = insets.left;
/* 117 */     double yy = insets.top;
/* 118 */     double ww = size.getWidth() - insets.left - insets.right - 1.0D;
/* 119 */     double hh = size.getHeight() - insets.top - insets.bottom - 1.0D;
/* 120 */     Rectangle2D area = new Rectangle2D.Double(xx, yy, ww, hh);
/* 121 */     g2.setPaint(this.paint);
/* 122 */     g2.fill(area);
/* 123 */     g2.setPaint(Color.black);
/* 124 */     g2.draw(area);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/PaintSample.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
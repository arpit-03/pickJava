/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Insets;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.ListCellRenderer;
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
/*     */ public class StrokeSample
/*     */   extends JComponent
/*     */   implements ListCellRenderer
/*     */ {
/*     */   private Stroke stroke;
/*     */   private Dimension preferredSize;
/*     */   
/*     */   public StrokeSample(Stroke stroke) {
/*  80 */     this.stroke = stroke;
/*  81 */     this.preferredSize = new Dimension(80, 18);
/*  82 */     setPreferredSize(this.preferredSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStroke() {
/*  91 */     return this.stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStroke(Stroke stroke) {
/* 100 */     this.stroke = stroke;
/* 101 */     repaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 110 */     return this.preferredSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintComponent(Graphics g) {
/* 120 */     Graphics2D g2 = (Graphics2D)g;
/* 121 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*     */     
/* 123 */     Dimension size = getSize();
/* 124 */     Insets insets = getInsets();
/* 125 */     double xx = insets.left;
/* 126 */     double yy = insets.top;
/* 127 */     double ww = size.getWidth() - insets.left - insets.right;
/* 128 */     double hh = size.getHeight() - insets.top - insets.bottom;
/*     */ 
/*     */     
/* 131 */     Point2D one = new Point2D.Double(xx + 6.0D, yy + hh / 2.0D);
/*     */     
/* 133 */     Point2D two = new Point2D.Double(xx + ww - 6.0D, yy + hh / 2.0D);
/*     */ 
/*     */     
/* 136 */     Ellipse2D circle1 = new Ellipse2D.Double(one.getX() - 5.0D, one.getY() - 5.0D, 10.0D, 10.0D);
/*     */     
/* 138 */     Ellipse2D circle2 = new Ellipse2D.Double(two.getX() - 6.0D, two.getY() - 5.0D, 10.0D, 10.0D);
/*     */ 
/*     */     
/* 141 */     g2.draw(circle1);
/* 142 */     g2.fill(circle1);
/* 143 */     g2.draw(circle2);
/* 144 */     g2.fill(circle2);
/*     */ 
/*     */     
/* 147 */     Line2D line = new Line2D.Double(one, two);
/* 148 */     if (this.stroke != null) {
/* 149 */       g2.setStroke(this.stroke);
/* 150 */       g2.draw(line);
/*     */     } 
/*     */   }
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
/*     */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 169 */     if (value instanceof Stroke) {
/* 170 */       setStroke((Stroke)value);
/*     */     } else {
/*     */       
/* 173 */       setStroke((Stroke)null);
/*     */     } 
/* 175 */     return this;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/StrokeSample.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
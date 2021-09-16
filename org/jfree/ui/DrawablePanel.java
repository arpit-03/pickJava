/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import javax.swing.JPanel;
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
/*     */ public class DrawablePanel
/*     */   extends JPanel
/*     */ {
/*     */   private Drawable drawable;
/*     */   
/*     */   public DrawablePanel() {
/*  63 */     setOpaque(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Drawable getDrawable() {
/*  73 */     return this.drawable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawable(Drawable drawable) {
/*  83 */     this.drawable = drawable;
/*  84 */     revalidate();
/*  85 */     repaint();
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
/*     */   public Dimension getPreferredSize() {
/* 100 */     if (this.drawable instanceof ExtendedDrawable) {
/*     */       
/* 102 */       ExtendedDrawable ed = (ExtendedDrawable)this.drawable;
/* 103 */       return ed.getPreferredSize();
/*     */     } 
/* 105 */     return super.getPreferredSize();
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
/*     */   public Dimension getMinimumSize() {
/* 120 */     if (this.drawable instanceof ExtendedDrawable) {
/*     */       
/* 122 */       ExtendedDrawable ed = (ExtendedDrawable)this.drawable;
/* 123 */       return ed.getPreferredSize();
/*     */     } 
/* 125 */     return super.getMinimumSize();
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
/*     */ 
/*     */   
/*     */   public boolean isOpaque() {
/* 145 */     if (this.drawable == null)
/*     */     {
/* 147 */       return false;
/*     */     }
/* 149 */     return super.isOpaque();
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
/*     */   protected void paintComponent(Graphics g) {
/* 178 */     super.paintComponent(g);
/* 179 */     if (this.drawable == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 185 */     Graphics2D g2 = (Graphics2D)g.create(0, 0, getWidth(), getHeight());
/*     */     
/* 187 */     this.drawable.draw(g2, new Rectangle2D.Double(0.0D, 0.0D, getWidth(), getHeight()));
/* 188 */     g2.dispose();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/DrawablePanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
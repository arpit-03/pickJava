/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.PaintUtilities;
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
/*     */ public class BlockBorder
/*     */   implements BlockFrame, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4961579220410228283L;
/*  72 */   public static final BlockBorder NONE = new BlockBorder(RectangleInsets.ZERO_INSETS, Color.white);
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleInsets insets;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint paint;
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBorder() {
/*  85 */     this(Color.black);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBorder(Paint paint) {
/*  94 */     this(new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D), paint);
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
/*     */   public BlockBorder(double top, double left, double bottom, double right) {
/* 106 */     this(new RectangleInsets(top, left, bottom, right), Color.black);
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
/*     */   public BlockBorder(double top, double left, double bottom, double right, Paint paint) {
/* 120 */     this(new RectangleInsets(top, left, bottom, right), paint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockBorder(RectangleInsets insets, Paint paint) {
/* 130 */     ParamChecks.nullNotPermitted(insets, "insets");
/* 131 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 132 */     this.insets = insets;
/* 133 */     this.paint = paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getInsets() {
/* 143 */     return this.insets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 152 */     return this.paint;
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 165 */     double t = this.insets.calculateTopInset(area.getHeight());
/* 166 */     double b = this.insets.calculateBottomInset(area.getHeight());
/* 167 */     double l = this.insets.calculateLeftInset(area.getWidth());
/* 168 */     double r = this.insets.calculateRightInset(area.getWidth());
/* 169 */     double x = area.getX();
/* 170 */     double y = area.getY();
/* 171 */     double w = area.getWidth();
/* 172 */     double h = area.getHeight();
/* 173 */     g2.setPaint(this.paint);
/* 174 */     Rectangle2D rect = new Rectangle2D.Double();
/* 175 */     if (t > 0.0D) {
/* 176 */       rect.setRect(x, y, w, t);
/* 177 */       g2.fill(rect);
/*     */     } 
/* 179 */     if (b > 0.0D) {
/* 180 */       rect.setRect(x, y + h - b, w, b);
/* 181 */       g2.fill(rect);
/*     */     } 
/* 183 */     if (l > 0.0D) {
/* 184 */       rect.setRect(x, y, l, h);
/* 185 */       g2.fill(rect);
/*     */     } 
/* 187 */     if (r > 0.0D) {
/* 188 */       rect.setRect(x + w - r, y, r, h);
/* 189 */       g2.fill(rect);
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
/*     */   public boolean equals(Object obj) {
/* 202 */     if (obj == this) {
/* 203 */       return true;
/*     */     }
/* 205 */     if (!(obj instanceof BlockBorder)) {
/* 206 */       return false;
/*     */     }
/* 208 */     BlockBorder that = (BlockBorder)obj;
/* 209 */     if (!this.insets.equals(that.insets)) {
/* 210 */       return false;
/*     */     }
/* 212 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 213 */       return false;
/*     */     }
/* 215 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 226 */     stream.defaultWriteObject();
/* 227 */     SerialUtilities.writePaint(this.paint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 240 */     stream.defaultReadObject();
/* 241 */     this.paint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/BlockBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
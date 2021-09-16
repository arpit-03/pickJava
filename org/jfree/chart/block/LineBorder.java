/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineBorder
/*     */   implements BlockFrame, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 4630356736707233924L;
/*     */   private transient Paint paint;
/*     */   private transient Stroke stroke;
/*     */   private RectangleInsets insets;
/*     */   
/*     */   public LineBorder() {
/*  89 */     this(Color.black, new BasicStroke(1.0F), new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D));
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
/*     */   public LineBorder(Paint paint, Stroke stroke, RectangleInsets insets) {
/* 101 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 102 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 103 */     ParamChecks.nullNotPermitted(insets, "insets");
/* 104 */     this.paint = paint;
/* 105 */     this.stroke = stroke;
/* 106 */     this.insets = insets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint() {
/* 115 */     return this.paint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getInsets() {
/* 125 */     return this.insets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getStroke() {
/* 134 */     return this.stroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 145 */     double w = area.getWidth();
/* 146 */     double h = area.getHeight();
/*     */     
/* 148 */     if (w <= 0.0D || h <= 0.0D) {
/*     */       return;
/*     */     }
/* 151 */     double t = this.insets.calculateTopInset(h);
/* 152 */     double b = this.insets.calculateBottomInset(h);
/* 153 */     double l = this.insets.calculateLeftInset(w);
/* 154 */     double r = this.insets.calculateRightInset(w);
/* 155 */     double x = area.getX();
/* 156 */     double y = area.getY();
/* 157 */     double x0 = x + l / 2.0D;
/* 158 */     double x1 = x + w - r / 2.0D;
/* 159 */     double y0 = y + h - b / 2.0D;
/* 160 */     double y1 = y + t / 2.0D;
/* 161 */     g2.setPaint(getPaint());
/* 162 */     g2.setStroke(getStroke());
/* 163 */     Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
/* 164 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
/*     */     
/* 166 */     Line2D line = new Line2D.Double();
/* 167 */     if (t > 0.0D) {
/* 168 */       line.setLine(x0, y1, x1, y1);
/* 169 */       g2.draw(line);
/*     */     } 
/* 171 */     if (b > 0.0D) {
/* 172 */       line.setLine(x0, y0, x1, y0);
/* 173 */       g2.draw(line);
/*     */     } 
/* 175 */     if (l > 0.0D) {
/* 176 */       line.setLine(x0, y0, x0, y1);
/* 177 */       g2.draw(line);
/*     */     } 
/* 179 */     if (r > 0.0D) {
/* 180 */       line.setLine(x1, y0, x1, y1);
/* 181 */       g2.draw(line);
/*     */     } 
/* 183 */     g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
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
/* 195 */     if (obj == this) {
/* 196 */       return true;
/*     */     }
/* 198 */     if (!(obj instanceof LineBorder)) {
/* 199 */       return false;
/*     */     }
/* 201 */     LineBorder that = (LineBorder)obj;
/* 202 */     if (!PaintUtilities.equal(this.paint, that.paint)) {
/* 203 */       return false;
/*     */     }
/* 205 */     if (!ObjectUtilities.equal(this.stroke, that.stroke)) {
/* 206 */       return false;
/*     */     }
/* 208 */     if (!this.insets.equals(that.insets)) {
/* 209 */       return false;
/*     */     }
/* 211 */     return true;
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
/* 222 */     stream.defaultWriteObject();
/* 223 */     SerialUtilities.writePaint(this.paint, stream);
/* 224 */     SerialUtilities.writeStroke(this.stroke, stream);
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
/* 237 */     stream.defaultReadObject();
/* 238 */     this.paint = SerialUtilities.readPaint(stream);
/* 239 */     this.stroke = SerialUtilities.readStroke(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/LineBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
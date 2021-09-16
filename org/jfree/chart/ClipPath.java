/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Composite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClipPath
/*     */   implements Cloneable
/*     */ {
/*  76 */   private double[] xValue = null;
/*     */ 
/*     */   
/*  79 */   private double[] yValue = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean clip = true;
/*     */ 
/*     */   
/*     */   private boolean drawPath = false;
/*     */ 
/*     */   
/*     */   private boolean fillPath = false;
/*     */ 
/*     */   
/*  92 */   private Paint fillPaint = null;
/*     */ 
/*     */   
/*  95 */   private Paint drawPaint = null;
/*     */ 
/*     */   
/*  98 */   private Stroke drawStroke = null;
/*     */ 
/*     */   
/* 101 */   private Composite composite = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClipPath() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClipPath(double[] xValue, double[] yValue) {
/* 121 */     this(xValue, yValue, true, false, true);
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
/*     */   public ClipPath(double[] xValue, double[] yValue, boolean clip, boolean fillPath, boolean drawPath) {
/* 138 */     this.xValue = xValue;
/* 139 */     this.yValue = yValue;
/*     */     
/* 141 */     this.clip = clip;
/* 142 */     this.fillPath = fillPath;
/* 143 */     this.drawPath = drawPath;
/*     */     
/* 145 */     this.fillPaint = Color.gray;
/* 146 */     this.drawPaint = Color.blue;
/* 147 */     this.drawStroke = new BasicStroke(1.0F);
/* 148 */     this.composite = AlphaComposite.Src;
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
/*     */   public ClipPath(double[] xValue, double[] yValue, boolean fillPath, boolean drawPath, Paint fillPaint, Paint drawPaint, Stroke drawStroke, Composite composite) {
/* 167 */     this.xValue = xValue;
/* 168 */     this.yValue = yValue;
/*     */     
/* 170 */     this.fillPath = fillPath;
/* 171 */     this.drawPath = drawPath;
/*     */     
/* 173 */     this.fillPaint = fillPaint;
/* 174 */     this.drawPaint = drawPaint;
/* 175 */     this.drawStroke = drawStroke;
/* 176 */     this.composite = composite;
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
/*     */   public GeneralPath draw(Graphics2D g2, Rectangle2D dataArea, ValueAxis horizontalAxis, ValueAxis verticalAxis) {
/* 194 */     GeneralPath generalPath = generateClipPath(dataArea, horizontalAxis, verticalAxis);
/*     */ 
/*     */     
/* 197 */     if (this.fillPath || this.drawPath) {
/* 198 */       Composite saveComposite = g2.getComposite();
/* 199 */       Paint savePaint = g2.getPaint();
/* 200 */       Stroke saveStroke = g2.getStroke();
/*     */       
/* 202 */       if (this.fillPaint != null) {
/* 203 */         g2.setPaint(this.fillPaint);
/*     */       }
/* 205 */       if (this.composite != null) {
/* 206 */         g2.setComposite(this.composite);
/*     */       }
/* 208 */       if (this.fillPath) {
/* 209 */         g2.fill(generalPath);
/*     */       }
/*     */       
/* 212 */       if (this.drawStroke != null) {
/* 213 */         g2.setStroke(this.drawStroke);
/*     */       }
/* 215 */       if (this.drawPath) {
/* 216 */         g2.draw(generalPath);
/*     */       }
/* 218 */       g2.setPaint(savePaint);
/* 219 */       g2.setComposite(saveComposite);
/* 220 */       g2.setStroke(saveStroke);
/*     */     } 
/* 222 */     return generalPath;
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
/*     */   public GeneralPath generateClipPath(Rectangle2D dataArea, ValueAxis horizontalAxis, ValueAxis verticalAxis) {
/* 239 */     GeneralPath generalPath = new GeneralPath();
/* 240 */     double transX = horizontalAxis.valueToJava2D(this.xValue[0], dataArea, RectangleEdge.BOTTOM);
/*     */ 
/*     */     
/* 243 */     double transY = verticalAxis.valueToJava2D(this.yValue[0], dataArea, RectangleEdge.LEFT);
/*     */ 
/*     */     
/* 246 */     generalPath.moveTo((float)transX, (float)transY);
/* 247 */     for (int k = 0; k < this.yValue.length; k++) {
/* 248 */       transX = horizontalAxis.valueToJava2D(this.xValue[k], dataArea, RectangleEdge.BOTTOM);
/*     */ 
/*     */       
/* 251 */       transY = verticalAxis.valueToJava2D(this.yValue[k], dataArea, RectangleEdge.LEFT);
/*     */ 
/*     */       
/* 254 */       generalPath.lineTo((float)transX, (float)transY);
/*     */     } 
/* 256 */     generalPath.closePath();
/*     */     
/* 258 */     return generalPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Composite getComposite() {
/* 268 */     return this.composite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getDrawPaint() {
/* 277 */     return this.drawPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawPath() {
/* 286 */     return this.drawPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getDrawStroke() {
/* 295 */     return this.drawStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getFillPaint() {
/* 304 */     return this.fillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFillPath() {
/* 313 */     return this.fillPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getXValue() {
/* 322 */     return this.xValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getYValue() {
/* 331 */     return this.yValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComposite(Composite composite) {
/* 340 */     this.composite = composite;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawPaint(Paint drawPaint) {
/* 349 */     this.drawPaint = drawPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawPath(boolean drawPath) {
/* 358 */     this.drawPath = drawPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawStroke(Stroke drawStroke) {
/* 367 */     this.drawStroke = drawStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFillPaint(Paint fillPaint) {
/* 376 */     this.fillPaint = fillPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFillPath(boolean fillPath) {
/* 385 */     this.fillPath = fillPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXValue(double[] xValue) {
/* 394 */     this.xValue = xValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYValue(double[] yValue) {
/* 403 */     this.yValue = yValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClip() {
/* 412 */     return this.clip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setClip(boolean clip) {
/* 421 */     this.clip = clip;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 434 */     ClipPath clone = (ClipPath)super.clone();
/* 435 */     clone.xValue = (double[])this.xValue.clone();
/* 436 */     clone.yValue = (double[])this.yValue.clone();
/* 437 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ClipPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
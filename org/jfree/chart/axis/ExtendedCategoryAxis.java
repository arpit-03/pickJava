/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextBlock;
/*     */ import org.jfree.text.TextFragment;
/*     */ import org.jfree.text.TextLine;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ 
/*     */ 
/*     */ public class ExtendedCategoryAxis
/*     */   extends CategoryAxis
/*     */ {
/*     */   static final long serialVersionUID = -3004429093959826567L;
/*     */   private Map sublabels;
/*     */   private Font sublabelFont;
/*     */   private transient Paint sublabelPaint;
/*     */   
/*     */   public ExtendedCategoryAxis(String label) {
/*  91 */     super(label);
/*  92 */     this.sublabels = new HashMap<Object, Object>();
/*  93 */     this.sublabelFont = new Font("SansSerif", 0, 10);
/*  94 */     this.sublabelPaint = Color.black;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getSubLabelFont() {
/* 105 */     return this.sublabelFont;
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
/*     */   public void setSubLabelFont(Font font) {
/* 117 */     ParamChecks.nullNotPermitted(font, "font");
/* 118 */     this.sublabelFont = font;
/* 119 */     notifyListeners(new AxisChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getSubLabelPaint() {
/* 130 */     return this.sublabelPaint;
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
/*     */   public void setSubLabelPaint(Paint paint) {
/* 142 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 143 */     this.sublabelPaint = paint;
/* 144 */     notifyListeners(new AxisChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubLabel(Comparable category, String label) {
/* 154 */     this.sublabels.put(category, label);
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
/*     */   protected TextBlock createLabel(Comparable category, float width, RectangleEdge edge, Graphics2D g2) {
/* 171 */     TextBlock label = super.createLabel(category, width, edge, g2);
/* 172 */     String s = (String)this.sublabels.get(category);
/* 173 */     if (s != null) {
/* 174 */       if (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM) {
/* 175 */         TextLine line = new TextLine(s, this.sublabelFont, this.sublabelPaint);
/*     */         
/* 177 */         label.addLine(line);
/*     */       }
/* 179 */       else if (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT) {
/*     */         
/* 181 */         TextLine line = label.getLastLine();
/* 182 */         if (line != null) {
/* 183 */           line.addFragment(new TextFragment("  " + s, this.sublabelFont, this.sublabelPaint));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 188 */     return label;
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
/* 200 */     if (obj == this) {
/* 201 */       return true;
/*     */     }
/* 203 */     if (!(obj instanceof ExtendedCategoryAxis)) {
/* 204 */       return false;
/*     */     }
/* 206 */     ExtendedCategoryAxis that = (ExtendedCategoryAxis)obj;
/* 207 */     if (!this.sublabelFont.equals(that.sublabelFont)) {
/* 208 */       return false;
/*     */     }
/* 210 */     if (!PaintUtilities.equal(this.sublabelPaint, that.sublabelPaint)) {
/* 211 */       return false;
/*     */     }
/* 213 */     if (!this.sublabels.equals(that.sublabels)) {
/* 214 */       return false;
/*     */     }
/* 216 */     return super.equals(obj);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 228 */     ExtendedCategoryAxis clone = (ExtendedCategoryAxis)super.clone();
/* 229 */     clone.sublabels = new HashMap<Object, Object>(this.sublabels);
/* 230 */     return clone;
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
/* 241 */     stream.defaultWriteObject();
/* 242 */     SerialUtilities.writePaint(this.sublabelPaint, stream);
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
/* 255 */     stream.defaultReadObject();
/* 256 */     this.sublabelPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/ExtendedCategoryAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
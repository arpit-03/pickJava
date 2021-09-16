/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.block.Arrangement;
/*     */ import org.jfree.chart.block.BlockContainer;
/*     */ import org.jfree.chart.block.BorderArrangement;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.chart.event.TitleChangeEvent;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.Size2D;
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
/*     */ public class CompositeTitle
/*     */   extends Title
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6770854036232562290L;
/*     */   private transient Paint backgroundPaint;
/*     */   private BlockContainer container;
/*     */   
/*     */   public CompositeTitle() {
/*  90 */     this(new BlockContainer((Arrangement)new BorderArrangement()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompositeTitle(BlockContainer container) {
/*  99 */     ParamChecks.nullNotPermitted(container, "container");
/* 100 */     this.container = container;
/* 101 */     this.backgroundPaint = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 112 */     return this.backgroundPaint;
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
/*     */   public void setBackgroundPaint(Paint paint) {
/* 125 */     this.backgroundPaint = paint;
/* 126 */     notifyListeners(new TitleChangeEvent(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockContainer getContainer() {
/* 135 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleContainer(BlockContainer container) {
/* 144 */     ParamChecks.nullNotPermitted(container, "container");
/* 145 */     this.container = container;
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/* 159 */     RectangleConstraint contentConstraint = toContentConstraint(constraint);
/* 160 */     Size2D contentSize = this.container.arrange(g2, contentConstraint);
/* 161 */     return new Size2D(calculateTotalWidth(contentSize.getWidth()), 
/* 162 */         calculateTotalHeight(contentSize.getHeight()));
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area) {
/* 174 */     draw(g2, area, (Object)null);
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 188 */     area = trimMargin(area);
/* 189 */     drawBorder(g2, area);
/* 190 */     area = trimBorder(area);
/* 191 */     if (this.backgroundPaint != null) {
/* 192 */       g2.setPaint(this.backgroundPaint);
/* 193 */       g2.fill(area);
/*     */     } 
/* 195 */     area = trimPadding(area);
/* 196 */     return this.container.draw(g2, area, params);
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
/* 208 */     if (obj == this) {
/* 209 */       return true;
/*     */     }
/* 211 */     if (!(obj instanceof CompositeTitle)) {
/* 212 */       return false;
/*     */     }
/* 214 */     CompositeTitle that = (CompositeTitle)obj;
/* 215 */     if (!this.container.equals(that.container)) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
/* 219 */       return false;
/*     */     }
/* 221 */     return super.equals(obj);
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
/* 232 */     stream.defaultWriteObject();
/* 233 */     SerialUtilities.writePaint(this.backgroundPaint, stream);
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
/* 246 */     stream.defaultReadObject();
/* 247 */     this.backgroundPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/CompositeTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
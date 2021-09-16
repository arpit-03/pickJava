/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.event.AxisChangeEvent;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotRenderingInfo;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SubCategoryAxis
/*     */   extends CategoryAxis
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1279463299793228344L;
/*     */   private List subCategories;
/*  92 */   private Font subLabelFont = new Font("SansSerif", 0, 10);
/*     */ 
/*     */   
/*  95 */   private transient Paint subLabelPaint = Color.black;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubCategoryAxis(String label) {
/* 103 */     super(label);
/* 104 */     this.subCategories = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSubCategory(Comparable subCategory) {
/* 114 */     ParamChecks.nullNotPermitted(subCategory, "subCategory");
/* 115 */     this.subCategories.add(subCategory);
/* 116 */     notifyListeners(new AxisChangeEvent(this));
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
/* 127 */     return this.subLabelFont;
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
/* 139 */     ParamChecks.nullNotPermitted(font, "font");
/* 140 */     this.subLabelFont = font;
/* 141 */     notifyListeners(new AxisChangeEvent(this));
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
/* 152 */     return this.subLabelPaint;
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
/* 164 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 165 */     this.subLabelPaint = paint;
/* 166 */     notifyListeners(new AxisChangeEvent(this));
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
/*     */   public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space) {
/* 185 */     if (space == null) {
/* 186 */       space = new AxisSpace();
/*     */     }
/*     */ 
/*     */     
/* 190 */     if (!isVisible()) {
/* 191 */       return space;
/*     */     }
/*     */     
/* 194 */     space = super.reserveSpace(g2, plot, plotArea, edge, space);
/* 195 */     double maxdim = getMaxDim(g2, edge);
/* 196 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 197 */       space.add(maxdim, edge);
/*     */     }
/* 199 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/* 200 */       space.add(maxdim, edge);
/*     */     } 
/* 202 */     return space;
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
/*     */   private double getMaxDim(Graphics2D g2, RectangleEdge edge) {
/* 215 */     double result = 0.0D;
/* 216 */     g2.setFont(this.subLabelFont);
/* 217 */     FontMetrics fm = g2.getFontMetrics();
/* 218 */     Iterator<Comparable> iterator = this.subCategories.iterator();
/* 219 */     while (iterator.hasNext()) {
/* 220 */       double dim; Comparable subcategory = iterator.next();
/* 221 */       String label = subcategory.toString();
/* 222 */       Rectangle2D bounds = TextUtilities.getTextBounds(label, g2, fm);
/*     */       
/* 224 */       if (RectangleEdge.isLeftOrRight(edge)) {
/* 225 */         dim = bounds.getWidth();
/*     */       } else {
/*     */         
/* 228 */         dim = bounds.getHeight();
/*     */       } 
/* 230 */       result = Math.max(result, dim);
/*     */     } 
/* 232 */     return result;
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
/*     */   public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState) {
/* 257 */     if (!isVisible()) {
/* 258 */       return new AxisState(cursor);
/*     */     }
/*     */     
/* 261 */     if (isAxisLineVisible()) {
/* 262 */       drawAxisLine(g2, cursor, dataArea, edge);
/*     */     }
/*     */ 
/*     */     
/* 266 */     AxisState state = new AxisState(cursor);
/* 267 */     state = drawSubCategoryLabels(g2, plotArea, dataArea, edge, state, plotState);
/*     */     
/* 269 */     state = drawCategoryLabels(g2, plotArea, dataArea, edge, state, plotState);
/*     */     
/* 271 */     if (getAttributedLabel() != null) {
/* 272 */       state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, dataArea, edge, state);
/*     */     } else {
/*     */       
/* 275 */       state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
/*     */     } 
/* 277 */     return state;
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
/*     */   protected AxisState drawSubCategoryLabels(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state, PlotRenderingInfo plotState) {
/* 299 */     ParamChecks.nullNotPermitted(state, "state");
/*     */     
/* 301 */     g2.setFont(this.subLabelFont);
/* 302 */     g2.setPaint(this.subLabelPaint);
/* 303 */     CategoryPlot plot = (CategoryPlot)getPlot();
/* 304 */     int categoryCount = 0;
/* 305 */     CategoryDataset dataset = plot.getDataset();
/* 306 */     if (dataset != null) {
/* 307 */       categoryCount = dataset.getColumnCount();
/*     */     }
/*     */     
/* 310 */     double maxdim = getMaxDim(g2, edge);
/* 311 */     for (int categoryIndex = 0; categoryIndex < categoryCount; 
/* 312 */       categoryIndex++) {
/*     */       
/* 314 */       double x0 = 0.0D;
/* 315 */       double x1 = 0.0D;
/* 316 */       double y0 = 0.0D;
/* 317 */       double y1 = 0.0D;
/* 318 */       if (edge == RectangleEdge.TOP) {
/* 319 */         x0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 321 */         x1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 323 */         y1 = state.getCursor();
/* 324 */         y0 = y1 - maxdim;
/*     */       }
/* 326 */       else if (edge == RectangleEdge.BOTTOM) {
/* 327 */         x0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 329 */         x1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 331 */         y0 = state.getCursor();
/* 332 */         y1 = y0 + maxdim;
/*     */       }
/* 334 */       else if (edge == RectangleEdge.LEFT) {
/* 335 */         y0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 337 */         y1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 339 */         x1 = state.getCursor();
/* 340 */         x0 = x1 - maxdim;
/*     */       }
/* 342 */       else if (edge == RectangleEdge.RIGHT) {
/* 343 */         y0 = getCategoryStart(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 345 */         y1 = getCategoryEnd(categoryIndex, categoryCount, dataArea, edge);
/*     */         
/* 347 */         x0 = state.getCursor();
/* 348 */         x1 = x0 + maxdim;
/*     */       } 
/* 350 */       Rectangle2D area = new Rectangle2D.Double(x0, y0, x1 - x0, y1 - y0);
/*     */       
/* 352 */       int subCategoryCount = this.subCategories.size();
/* 353 */       float width = (float)((x1 - x0) / subCategoryCount);
/* 354 */       float height = (float)((y1 - y0) / subCategoryCount);
/*     */       
/* 356 */       for (int i = 0; i < subCategoryCount; i++) {
/* 357 */         float xx, yy; if (RectangleEdge.isTopOrBottom(edge)) {
/* 358 */           xx = (float)(x0 + (i + 0.5D) * width);
/* 359 */           yy = (float)area.getCenterY();
/*     */         } else {
/*     */           
/* 362 */           xx = (float)area.getCenterX();
/* 363 */           yy = (float)(y0 + (i + 0.5D) * height);
/*     */         } 
/* 365 */         String label = this.subCategories.get(i).toString();
/* 366 */         TextUtilities.drawRotatedString(label, g2, xx, yy, TextAnchor.CENTER, 0.0D, TextAnchor.CENTER);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 371 */     if (edge.equals(RectangleEdge.TOP)) {
/* 372 */       double h = maxdim;
/* 373 */       state.cursorUp(h);
/*     */     }
/* 375 */     else if (edge.equals(RectangleEdge.BOTTOM)) {
/* 376 */       double h = maxdim;
/* 377 */       state.cursorDown(h);
/*     */     }
/* 379 */     else if (edge == RectangleEdge.LEFT) {
/* 380 */       double w = maxdim;
/* 381 */       state.cursorLeft(w);
/*     */     }
/* 383 */     else if (edge == RectangleEdge.RIGHT) {
/* 384 */       double w = maxdim;
/* 385 */       state.cursorRight(w);
/*     */     } 
/* 387 */     return state;
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
/* 399 */     if (obj == this) {
/* 400 */       return true;
/*     */     }
/* 402 */     if (obj instanceof SubCategoryAxis && super.equals(obj)) {
/* 403 */       SubCategoryAxis axis = (SubCategoryAxis)obj;
/* 404 */       if (!this.subCategories.equals(axis.subCategories)) {
/* 405 */         return false;
/*     */       }
/* 407 */       if (!this.subLabelFont.equals(axis.subLabelFont)) {
/* 408 */         return false;
/*     */       }
/* 410 */       if (!this.subLabelPaint.equals(axis.subLabelPaint)) {
/* 411 */         return false;
/*     */       }
/* 413 */       return true;
/*     */     } 
/* 415 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 425 */     return super.hashCode();
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
/* 436 */     stream.defaultWriteObject();
/* 437 */     SerialUtilities.writePaint(this.subLabelPaint, stream);
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
/* 450 */     stream.defaultReadObject();
/* 451 */     this.subLabelPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/SubCategoryAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
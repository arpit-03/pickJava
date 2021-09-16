/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import javax.swing.Icon;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.io.SerialUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MinMaxCategoryRenderer
/*     */   extends AbstractCategoryItemRenderer
/*     */ {
/*     */   private static final long serialVersionUID = 2935615937671064911L;
/*     */   private boolean plotLines = false;
/* 115 */   private transient Paint groupPaint = Color.black;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   private transient Stroke groupStroke = new BasicStroke(1.0F);
/*     */ 
/*     */   
/* 123 */   private transient Icon minIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), (Paint)null, Color.black);
/*     */ 
/*     */ 
/*     */   
/* 127 */   private transient Icon maxIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), (Paint)null, Color.black);
/*     */ 
/*     */ 
/*     */   
/* 131 */   private transient Icon objectIcon = getIcon(new Line2D.Double(-4.0D, 0.0D, 4.0D, 0.0D), false, true);
/*     */ 
/*     */ 
/*     */   
/* 135 */   private int lastCategory = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double min;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double max;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDrawLines() {
/* 159 */     return this.plotLines;
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
/*     */   public void setDrawLines(boolean draw) {
/* 172 */     if (this.plotLines != draw) {
/* 173 */       this.plotLines = draw;
/* 174 */       fireChangeEvent();
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
/*     */   public Paint getGroupPaint() {
/* 187 */     return this.groupPaint;
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
/*     */   public void setGroupPaint(Paint paint) {
/* 200 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 201 */     this.groupPaint = paint;
/* 202 */     fireChangeEvent();
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
/*     */   public Stroke getGroupStroke() {
/* 214 */     return this.groupStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupStroke(Stroke stroke) {
/* 225 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 226 */     this.groupStroke = stroke;
/* 227 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Icon getObjectIcon() {
/* 238 */     return this.objectIcon;
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
/*     */   public void setObjectIcon(Icon icon) {
/* 250 */     ParamChecks.nullNotPermitted(icon, "icon");
/* 251 */     this.objectIcon = icon;
/* 252 */     fireChangeEvent();
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
/*     */   public Icon getMaxIcon() {
/* 264 */     return this.maxIcon;
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
/*     */   public void setMaxIcon(Icon icon) {
/* 277 */     ParamChecks.nullNotPermitted(icon, "icon");
/* 278 */     this.maxIcon = icon;
/* 279 */     fireChangeEvent();
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
/*     */   public Icon getMinIcon() {
/* 291 */     return this.minIcon;
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
/*     */   public void setMinIcon(Icon icon) {
/* 304 */     ParamChecks.nullNotPermitted(icon, "icon");
/* 305 */     this.minIcon = icon;
/* 306 */     fireChangeEvent();
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/* 330 */     Number value = dataset.getValue(row, column);
/* 331 */     if (value != null) {
/*     */       
/* 333 */       double x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot
/* 334 */           .getDomainAxisEdge());
/* 335 */       double y1 = rangeAxis.valueToJava2D(value.doubleValue(), dataArea, plot
/* 336 */           .getRangeAxisEdge());
/* 337 */       Shape hotspot = new Rectangle2D.Double(x1 - 4.0D, y1 - 4.0D, 8.0D, 8.0D);
/*     */       
/* 339 */       g2.setPaint(getItemPaint(row, column));
/* 340 */       g2.setStroke(getItemStroke(row, column));
/*     */       
/* 342 */       PlotOrientation orient = plot.getOrientation();
/* 343 */       if (orient == PlotOrientation.VERTICAL) {
/* 344 */         this.objectIcon.paintIcon(null, g2, (int)x1, (int)y1);
/*     */       } else {
/*     */         
/* 347 */         this.objectIcon.paintIcon(null, g2, (int)y1, (int)x1);
/*     */       } 
/*     */       
/* 350 */       if (this.lastCategory == column) {
/* 351 */         if (this.min > value.doubleValue()) {
/* 352 */           this.min = value.doubleValue();
/*     */         }
/* 354 */         if (this.max < value.doubleValue()) {
/* 355 */           this.max = value.doubleValue();
/*     */         }
/*     */ 
/*     */         
/* 359 */         if (dataset.getRowCount() - 1 == row) {
/* 360 */           g2.setPaint(this.groupPaint);
/* 361 */           g2.setStroke(this.groupStroke);
/* 362 */           double minY = rangeAxis.valueToJava2D(this.min, dataArea, plot
/* 363 */               .getRangeAxisEdge());
/* 364 */           double maxY = rangeAxis.valueToJava2D(this.max, dataArea, plot
/* 365 */               .getRangeAxisEdge());
/*     */           
/* 367 */           if (orient == PlotOrientation.VERTICAL) {
/* 368 */             g2.draw(new Line2D.Double(x1, minY, x1, maxY));
/* 369 */             this.minIcon.paintIcon(null, g2, (int)x1, (int)minY);
/* 370 */             this.maxIcon.paintIcon(null, g2, (int)x1, (int)maxY);
/*     */           } else {
/*     */             
/* 373 */             g2.draw(new Line2D.Double(minY, x1, maxY, x1));
/* 374 */             this.minIcon.paintIcon(null, g2, (int)minY, (int)x1);
/* 375 */             this.maxIcon.paintIcon(null, g2, (int)maxY, (int)x1);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 380 */         this.lastCategory = column;
/* 381 */         this.min = value.doubleValue();
/* 382 */         this.max = value.doubleValue();
/*     */       } 
/*     */ 
/*     */       
/* 386 */       if (this.plotLines && 
/* 387 */         column != 0) {
/* 388 */         Number previousValue = dataset.getValue(row, column - 1);
/* 389 */         if (previousValue != null) {
/*     */           Line2D line;
/* 391 */           double previous = previousValue.doubleValue();
/* 392 */           double x0 = domainAxis.getCategoryMiddle(column - 1, 
/* 393 */               getColumnCount(), dataArea, plot
/* 394 */               .getDomainAxisEdge());
/* 395 */           double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot
/* 396 */               .getRangeAxisEdge());
/* 397 */           g2.setPaint(getItemPaint(row, column));
/* 398 */           g2.setStroke(getItemStroke(row, column));
/*     */           
/* 400 */           if (orient == PlotOrientation.VERTICAL) {
/* 401 */             line = new Line2D.Double(x0, y0, x1, y1);
/*     */           } else {
/*     */             
/* 404 */             line = new Line2D.Double(y0, x0, y1, x1);
/*     */           } 
/* 406 */           g2.draw(line);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 412 */       EntityCollection entities = state.getEntityCollection();
/* 413 */       if (entities != null) {
/* 414 */         addItemEntity(entities, dataset, row, column, hotspot);
/*     */       }
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
/*     */   public boolean equals(Object obj) {
/* 432 */     if (obj == this) {
/* 433 */       return true;
/*     */     }
/* 435 */     if (!(obj instanceof MinMaxCategoryRenderer)) {
/* 436 */       return false;
/*     */     }
/* 438 */     MinMaxCategoryRenderer that = (MinMaxCategoryRenderer)obj;
/* 439 */     if (this.plotLines != that.plotLines) {
/* 440 */       return false;
/*     */     }
/* 442 */     if (!PaintUtilities.equal(this.groupPaint, that.groupPaint)) {
/* 443 */       return false;
/*     */     }
/* 445 */     if (!this.groupStroke.equals(that.groupStroke)) {
/* 446 */       return false;
/*     */     }
/* 448 */     return super.equals(obj);
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
/*     */   private Icon getIcon(Shape shape, final Paint fillPaint, final Paint outlinePaint) {
/* 463 */     final int width = (shape.getBounds()).width;
/* 464 */     final int height = (shape.getBounds()).height;
/* 465 */     final GeneralPath path = new GeneralPath(shape);
/* 466 */     return new Icon()
/*     */       {
/*     */         public void paintIcon(Component c, Graphics g, int x, int y) {
/* 469 */           Graphics2D g2 = (Graphics2D)g;
/* 470 */           path.transform(AffineTransform.getTranslateInstance(x, y));
/* 471 */           if (fillPaint != null) {
/* 472 */             g2.setPaint(fillPaint);
/* 473 */             g2.fill(path);
/*     */           } 
/* 475 */           if (outlinePaint != null) {
/* 476 */             g2.setPaint(outlinePaint);
/* 477 */             g2.draw(path);
/*     */           } 
/* 479 */           path.transform(AffineTransform.getTranslateInstance(-x, -y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int getIconWidth() {
/* 484 */           return width;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getIconHeight() {
/* 489 */           return height;
/*     */         }
/*     */       };
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
/*     */   private Icon getIcon(Shape shape, final boolean fill, final boolean outline) {
/* 505 */     final int width = (shape.getBounds()).width;
/* 506 */     final int height = (shape.getBounds()).height;
/* 507 */     final GeneralPath path = new GeneralPath(shape);
/* 508 */     return new Icon()
/*     */       {
/*     */         public void paintIcon(Component c, Graphics g, int x, int y) {
/* 511 */           Graphics2D g2 = (Graphics2D)g;
/* 512 */           path.transform(AffineTransform.getTranslateInstance(x, y));
/* 513 */           if (fill) {
/* 514 */             g2.fill(path);
/*     */           }
/* 516 */           if (outline) {
/* 517 */             g2.draw(path);
/*     */           }
/* 519 */           path.transform(AffineTransform.getTranslateInstance(-x, -y));
/*     */         }
/*     */ 
/*     */         
/*     */         public int getIconWidth() {
/* 524 */           return width;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getIconHeight() {
/* 529 */           return height;
/*     */         }
/*     */       };
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
/* 542 */     stream.defaultWriteObject();
/* 543 */     SerialUtilities.writeStroke(this.groupStroke, stream);
/* 544 */     SerialUtilities.writePaint(this.groupPaint, stream);
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
/* 557 */     stream.defaultReadObject();
/* 558 */     this.groupStroke = SerialUtilities.readStroke(stream);
/* 559 */     this.groupPaint = SerialUtilities.readPaint(stream);
/*     */     
/* 561 */     this.minIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), (Paint)null, Color.black);
/*     */     
/* 563 */     this.maxIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), (Paint)null, Color.black);
/*     */     
/* 565 */     this.objectIcon = getIcon(new Line2D.Double(-4.0D, 0.0D, 4.0D, 0.0D), false, true);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/MinMaxCategoryRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
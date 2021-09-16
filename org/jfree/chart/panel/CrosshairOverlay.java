/*     */ package org.jfree.chart.panel;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Crosshair;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleAnchor;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrosshairOverlay
/*     */   extends AbstractOverlay
/*     */   implements Overlay, PropertyChangeListener, PublicCloneable, Cloneable, Serializable
/*     */ {
/*  93 */   private List xCrosshairs = new ArrayList();
/*  94 */   private List yCrosshairs = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addDomainCrosshair(Crosshair crosshair) {
/* 107 */     ParamChecks.nullNotPermitted(crosshair, "crosshair");
/* 108 */     this.xCrosshairs.add(crosshair);
/* 109 */     crosshair.addPropertyChangeListener(this);
/* 110 */     fireOverlayChanged();
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
/*     */   public void removeDomainCrosshair(Crosshair crosshair) {
/* 122 */     ParamChecks.nullNotPermitted(crosshair, "crosshair");
/* 123 */     if (this.xCrosshairs.remove(crosshair)) {
/* 124 */       crosshair.removePropertyChangeListener(this);
/* 125 */       fireOverlayChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearDomainCrosshairs() {
/* 134 */     if (this.xCrosshairs.isEmpty()) {
/*     */       return;
/*     */     }
/* 137 */     List<Crosshair> crosshairs = getDomainCrosshairs();
/* 138 */     for (int i = 0; i < crosshairs.size(); i++) {
/* 139 */       Crosshair c = crosshairs.get(i);
/* 140 */       this.xCrosshairs.remove(c);
/* 141 */       c.removePropertyChangeListener(this);
/*     */     } 
/* 143 */     fireOverlayChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getDomainCrosshairs() {
/* 152 */     return new ArrayList(this.xCrosshairs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRangeCrosshair(Crosshair crosshair) {
/* 162 */     ParamChecks.nullNotPermitted(crosshair, "crosshair");
/* 163 */     this.yCrosshairs.add(crosshair);
/* 164 */     crosshair.addPropertyChangeListener(this);
/* 165 */     fireOverlayChanged();
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
/*     */   public void removeRangeCrosshair(Crosshair crosshair) {
/* 177 */     ParamChecks.nullNotPermitted(crosshair, "crosshair");
/* 178 */     if (this.yCrosshairs.remove(crosshair)) {
/* 179 */       crosshair.removePropertyChangeListener(this);
/* 180 */       fireOverlayChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearRangeCrosshairs() {
/* 189 */     if (this.yCrosshairs.isEmpty()) {
/*     */       return;
/*     */     }
/* 192 */     List<Crosshair> crosshairs = getRangeCrosshairs();
/* 193 */     for (int i = 0; i < crosshairs.size(); i++) {
/* 194 */       Crosshair c = crosshairs.get(i);
/* 195 */       this.yCrosshairs.remove(c);
/* 196 */       c.removePropertyChangeListener(this);
/*     */     } 
/* 198 */     fireOverlayChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getRangeCrosshairs() {
/* 207 */     return new ArrayList(this.yCrosshairs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void propertyChange(PropertyChangeEvent e) {
/* 218 */     fireOverlayChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintOverlay(Graphics2D g2, ChartPanel chartPanel) {
/* 229 */     Shape savedClip = g2.getClip();
/* 230 */     Rectangle2D dataArea = chartPanel.getScreenDataArea();
/* 231 */     g2.clip(dataArea);
/* 232 */     JFreeChart chart = chartPanel.getChart();
/* 233 */     XYPlot plot = (XYPlot)chart.getPlot();
/* 234 */     ValueAxis xAxis = plot.getDomainAxis();
/* 235 */     RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
/* 236 */     Iterator<Crosshair> iterator = this.xCrosshairs.iterator();
/* 237 */     while (iterator.hasNext()) {
/* 238 */       Crosshair ch = iterator.next();
/* 239 */       if (ch.isVisible()) {
/* 240 */         double x = ch.getValue();
/* 241 */         double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
/* 242 */         if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 243 */           drawVerticalCrosshair(g2, dataArea, xx, ch);
/*     */           continue;
/*     */         } 
/* 246 */         drawHorizontalCrosshair(g2, dataArea, xx, ch);
/*     */       } 
/*     */     } 
/*     */     
/* 250 */     ValueAxis yAxis = plot.getRangeAxis();
/* 251 */     RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
/* 252 */     iterator = this.yCrosshairs.iterator();
/* 253 */     while (iterator.hasNext()) {
/* 254 */       Crosshair ch = iterator.next();
/* 255 */       if (ch.isVisible()) {
/* 256 */         double y = ch.getValue();
/* 257 */         double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
/* 258 */         if (plot.getOrientation() == PlotOrientation.VERTICAL) {
/* 259 */           drawHorizontalCrosshair(g2, dataArea, yy, ch);
/*     */           continue;
/*     */         } 
/* 262 */         drawVerticalCrosshair(g2, dataArea, yy, ch);
/*     */       } 
/*     */     } 
/*     */     
/* 266 */     g2.setClip(savedClip);
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
/*     */   protected void drawHorizontalCrosshair(Graphics2D g2, Rectangle2D dataArea, double y, Crosshair crosshair) {
/* 280 */     if (y >= dataArea.getMinY() && y <= dataArea.getMaxY()) {
/*     */       
/* 282 */       Line2D line = new Line2D.Double(dataArea.getMinX(), y, dataArea.getMaxX(), y);
/* 283 */       Paint savedPaint = g2.getPaint();
/* 284 */       Stroke savedStroke = g2.getStroke();
/* 285 */       g2.setPaint(crosshair.getPaint());
/* 286 */       g2.setStroke(crosshair.getStroke());
/* 287 */       g2.draw(line);
/* 288 */       if (crosshair.isLabelVisible()) {
/* 289 */         String label = crosshair.getLabelGenerator().generateLabel(crosshair);
/*     */         
/* 291 */         RectangleAnchor anchor = crosshair.getLabelAnchor();
/* 292 */         Point2D pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
/* 293 */         float xx = (float)pt.getX();
/* 294 */         float yy = (float)pt.getY();
/* 295 */         TextAnchor alignPt = textAlignPtForLabelAnchorH(anchor);
/* 296 */         Shape hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
/*     */         
/* 298 */         if (!dataArea.contains(hotspot.getBounds2D())) {
/* 299 */           anchor = flipAnchorV(anchor);
/* 300 */           pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
/* 301 */           xx = (float)pt.getX();
/* 302 */           yy = (float)pt.getY();
/* 303 */           alignPt = textAlignPtForLabelAnchorH(anchor);
/* 304 */           hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
/*     */         } 
/*     */ 
/*     */         
/* 308 */         g2.setPaint(crosshair.getLabelBackgroundPaint());
/* 309 */         g2.fill(hotspot);
/* 310 */         g2.setPaint(crosshair.getLabelOutlinePaint());
/* 311 */         g2.draw(hotspot);
/* 312 */         TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
/*     */       } 
/* 314 */       g2.setPaint(savedPaint);
/* 315 */       g2.setStroke(savedStroke);
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
/*     */   protected void drawVerticalCrosshair(Graphics2D g2, Rectangle2D dataArea, double x, Crosshair crosshair) {
/* 330 */     if (x >= dataArea.getMinX() && x <= dataArea.getMaxX()) {
/*     */       
/* 332 */       Line2D line = new Line2D.Double(x, dataArea.getMinY(), x, dataArea.getMaxY());
/* 333 */       Paint savedPaint = g2.getPaint();
/* 334 */       Stroke savedStroke = g2.getStroke();
/* 335 */       g2.setPaint(crosshair.getPaint());
/* 336 */       g2.setStroke(crosshair.getStroke());
/* 337 */       g2.draw(line);
/* 338 */       if (crosshair.isLabelVisible()) {
/* 339 */         String label = crosshair.getLabelGenerator().generateLabel(crosshair);
/*     */         
/* 341 */         RectangleAnchor anchor = crosshair.getLabelAnchor();
/* 342 */         Point2D pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
/* 343 */         float xx = (float)pt.getX();
/* 344 */         float yy = (float)pt.getY();
/* 345 */         TextAnchor alignPt = textAlignPtForLabelAnchorV(anchor);
/* 346 */         Shape hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
/*     */         
/* 348 */         if (!dataArea.contains(hotspot.getBounds2D())) {
/* 349 */           anchor = flipAnchorH(anchor);
/* 350 */           pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
/* 351 */           xx = (float)pt.getX();
/* 352 */           yy = (float)pt.getY();
/* 353 */           alignPt = textAlignPtForLabelAnchorV(anchor);
/* 354 */           hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
/*     */         } 
/*     */         
/* 357 */         g2.setPaint(crosshair.getLabelBackgroundPaint());
/* 358 */         g2.fill(hotspot);
/* 359 */         g2.setPaint(crosshair.getLabelOutlinePaint());
/* 360 */         g2.draw(hotspot);
/* 361 */         TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
/*     */       } 
/* 363 */       g2.setPaint(savedPaint);
/* 364 */       g2.setStroke(savedStroke);
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
/*     */   private Point2D calculateLabelPoint(Line2D line, RectangleAnchor anchor, double deltaX, double deltaY) {
/*     */     double x, y;
/* 381 */     boolean left = (anchor == RectangleAnchor.BOTTOM_LEFT || anchor == RectangleAnchor.LEFT || anchor == RectangleAnchor.TOP_LEFT);
/*     */ 
/*     */     
/* 384 */     boolean right = (anchor == RectangleAnchor.BOTTOM_RIGHT || anchor == RectangleAnchor.RIGHT || anchor == RectangleAnchor.TOP_RIGHT);
/*     */ 
/*     */     
/* 387 */     boolean top = (anchor == RectangleAnchor.TOP_LEFT || anchor == RectangleAnchor.TOP || anchor == RectangleAnchor.TOP_RIGHT);
/*     */ 
/*     */     
/* 390 */     boolean bottom = (anchor == RectangleAnchor.BOTTOM_LEFT || anchor == RectangleAnchor.BOTTOM || anchor == RectangleAnchor.BOTTOM_RIGHT);
/*     */ 
/*     */     
/* 393 */     Rectangle rect = line.getBounds();
/*     */ 
/*     */     
/* 396 */     if (line.getX1() == line.getX2()) {
/* 397 */       x = line.getX1();
/* 398 */       y = (line.getY1() + line.getY2()) / 2.0D;
/* 399 */       if (left) {
/* 400 */         x -= deltaX;
/*     */       }
/* 402 */       if (right) {
/* 403 */         x += deltaX;
/*     */       }
/* 405 */       if (top) {
/* 406 */         y = Math.min(line.getY1(), line.getY2()) + deltaY;
/*     */       }
/* 408 */       if (bottom) {
/* 409 */         y = Math.max(line.getY1(), line.getY2()) - deltaY;
/*     */       }
/*     */     } else {
/*     */       
/* 413 */       x = (line.getX1() + line.getX2()) / 2.0D;
/* 414 */       y = line.getY1();
/* 415 */       if (left) {
/* 416 */         x = Math.min(line.getX1(), line.getX2()) + deltaX;
/*     */       }
/* 418 */       if (right) {
/* 419 */         x = Math.max(line.getX1(), line.getX2()) - deltaX;
/*     */       }
/* 421 */       if (top) {
/* 422 */         y -= deltaY;
/*     */       }
/* 424 */       if (bottom) {
/* 425 */         y += deltaY;
/*     */       }
/*     */     } 
/* 428 */     return new Point2D.Double(x, y);
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
/*     */   private TextAnchor textAlignPtForLabelAnchorV(RectangleAnchor anchor) {
/* 440 */     TextAnchor result = TextAnchor.CENTER;
/* 441 */     if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
/* 442 */       result = TextAnchor.TOP_RIGHT;
/*     */     }
/* 444 */     else if (anchor.equals(RectangleAnchor.TOP)) {
/* 445 */       result = TextAnchor.TOP_CENTER;
/*     */     }
/* 447 */     else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
/* 448 */       result = TextAnchor.TOP_LEFT;
/*     */     }
/* 450 */     else if (anchor.equals(RectangleAnchor.LEFT)) {
/* 451 */       result = TextAnchor.HALF_ASCENT_RIGHT;
/*     */     }
/* 453 */     else if (anchor.equals(RectangleAnchor.RIGHT)) {
/* 454 */       result = TextAnchor.HALF_ASCENT_LEFT;
/*     */     }
/* 456 */     else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
/* 457 */       result = TextAnchor.BOTTOM_RIGHT;
/*     */     }
/* 459 */     else if (anchor.equals(RectangleAnchor.BOTTOM)) {
/* 460 */       result = TextAnchor.BOTTOM_CENTER;
/*     */     }
/* 462 */     else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
/* 463 */       result = TextAnchor.BOTTOM_LEFT;
/*     */     } 
/* 465 */     return result;
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
/*     */   private TextAnchor textAlignPtForLabelAnchorH(RectangleAnchor anchor) {
/* 477 */     TextAnchor result = TextAnchor.CENTER;
/* 478 */     if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
/* 479 */       result = TextAnchor.BOTTOM_LEFT;
/*     */     }
/* 481 */     else if (anchor.equals(RectangleAnchor.TOP)) {
/* 482 */       result = TextAnchor.BOTTOM_CENTER;
/*     */     }
/* 484 */     else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
/* 485 */       result = TextAnchor.BOTTOM_RIGHT;
/*     */     }
/* 487 */     else if (anchor.equals(RectangleAnchor.LEFT)) {
/* 488 */       result = TextAnchor.HALF_ASCENT_LEFT;
/*     */     }
/* 490 */     else if (anchor.equals(RectangleAnchor.RIGHT)) {
/* 491 */       result = TextAnchor.HALF_ASCENT_RIGHT;
/*     */     }
/* 493 */     else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
/* 494 */       result = TextAnchor.TOP_LEFT;
/*     */     }
/* 496 */     else if (anchor.equals(RectangleAnchor.BOTTOM)) {
/* 497 */       result = TextAnchor.TOP_CENTER;
/*     */     }
/* 499 */     else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
/* 500 */       result = TextAnchor.TOP_RIGHT;
/*     */     } 
/* 502 */     return result;
/*     */   }
/*     */   
/*     */   private RectangleAnchor flipAnchorH(RectangleAnchor anchor) {
/* 506 */     RectangleAnchor result = anchor;
/* 507 */     if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
/* 508 */       result = RectangleAnchor.TOP_RIGHT;
/*     */     }
/* 510 */     else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
/* 511 */       result = RectangleAnchor.TOP_LEFT;
/*     */     }
/* 513 */     else if (anchor.equals(RectangleAnchor.LEFT)) {
/* 514 */       result = RectangleAnchor.RIGHT;
/*     */     }
/* 516 */     else if (anchor.equals(RectangleAnchor.RIGHT)) {
/* 517 */       result = RectangleAnchor.LEFT;
/*     */     }
/* 519 */     else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
/* 520 */       result = RectangleAnchor.BOTTOM_RIGHT;
/*     */     }
/* 522 */     else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
/* 523 */       result = RectangleAnchor.BOTTOM_LEFT;
/*     */     } 
/* 525 */     return result;
/*     */   }
/*     */   
/*     */   private RectangleAnchor flipAnchorV(RectangleAnchor anchor) {
/* 529 */     RectangleAnchor result = anchor;
/* 530 */     if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
/* 531 */       result = RectangleAnchor.BOTTOM_LEFT;
/*     */     }
/* 533 */     else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
/* 534 */       result = RectangleAnchor.BOTTOM_RIGHT;
/*     */     }
/* 536 */     else if (anchor.equals(RectangleAnchor.TOP)) {
/* 537 */       result = RectangleAnchor.BOTTOM;
/*     */     }
/* 539 */     else if (anchor.equals(RectangleAnchor.BOTTOM)) {
/* 540 */       result = RectangleAnchor.TOP;
/*     */     }
/* 542 */     else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
/* 543 */       result = RectangleAnchor.TOP_LEFT;
/*     */     }
/* 545 */     else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
/* 546 */       result = RectangleAnchor.TOP_RIGHT;
/*     */     } 
/* 548 */     return result;
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
/* 560 */     if (obj == this) {
/* 561 */       return true;
/*     */     }
/* 563 */     if (!(obj instanceof CrosshairOverlay)) {
/* 564 */       return false;
/*     */     }
/* 566 */     CrosshairOverlay that = (CrosshairOverlay)obj;
/* 567 */     if (!this.xCrosshairs.equals(that.xCrosshairs)) {
/* 568 */       return false;
/*     */     }
/* 570 */     if (!this.yCrosshairs.equals(that.yCrosshairs)) {
/* 571 */       return false;
/*     */     }
/* 573 */     return true;
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
/* 586 */     CrosshairOverlay clone = (CrosshairOverlay)super.clone();
/* 587 */     clone.xCrosshairs = (List)ObjectUtilities.deepClone(this.xCrosshairs);
/* 588 */     clone.yCrosshairs = (List)ObjectUtilities.deepClone(this.yCrosshairs);
/* 589 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/panel/CrosshairOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
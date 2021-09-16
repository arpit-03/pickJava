/*      */ package org.jfree.chart.plot;
/*      */ 
/*      */ import java.awt.AlphaComposite;
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Area;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.entity.PieSectionEntity;
/*      */ import org.jfree.chart.labels.PieToolTipGenerator;
/*      */ import org.jfree.chart.util.PaintAlpha;
/*      */ import org.jfree.data.general.DatasetUtilities;
/*      */ import org.jfree.data.general.PieDataset;
/*      */ import org.jfree.ui.RectangleInsets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PiePlot3D
/*      */   extends PiePlot
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3408984188945161432L;
/*  136 */   private double depthFactor = 0.12D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean darkerSides = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PiePlot3D() {
/*  151 */     this((PieDataset)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PiePlot3D(PieDataset dataset) {
/*  161 */     super(dataset);
/*  162 */     setCircular(false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDepthFactor() {
/*  173 */     return this.depthFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDepthFactor(double factor) {
/*  185 */     this.depthFactor = factor;
/*  186 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDarkerSides() {
/*  200 */     return this.darkerSides;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDarkerSides(boolean darker) {
/*  216 */     this.darkerSides = darker;
/*  217 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D g2, Rectangle2D plotArea, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
/*  238 */     RectangleInsets insets = getInsets();
/*  239 */     insets.trim(plotArea);
/*      */     
/*  241 */     Rectangle2D originalPlotArea = (Rectangle2D)plotArea.clone();
/*  242 */     if (info != null) {
/*  243 */       info.setPlotArea(plotArea);
/*  244 */       info.setDataArea(plotArea);
/*      */     } 
/*      */     
/*  247 */     drawBackground(g2, plotArea);
/*      */     
/*  249 */     Shape savedClip = g2.getClip();
/*  250 */     g2.clip(plotArea);
/*      */     
/*  252 */     Graphics2D savedG2 = g2;
/*  253 */     BufferedImage dataImage = null;
/*  254 */     if (getShadowGenerator() != null) {
/*      */       
/*  256 */       dataImage = new BufferedImage((int)plotArea.getWidth(), (int)plotArea.getHeight(), 2);
/*  257 */       g2 = dataImage.createGraphics();
/*  258 */       g2.translate(-plotArea.getX(), -plotArea.getY());
/*  259 */       g2.setRenderingHints(savedG2.getRenderingHints());
/*  260 */       originalPlotArea = (Rectangle2D)plotArea.clone();
/*      */     } 
/*      */     
/*  263 */     double gapPercent = getInteriorGap();
/*  264 */     double labelPercent = 0.0D;
/*  265 */     if (getLabelGenerator() != null) {
/*  266 */       labelPercent = getLabelGap() + getMaximumLabelWidth();
/*      */     }
/*  268 */     double gapHorizontal = plotArea.getWidth() * (gapPercent + labelPercent) * 2.0D;
/*      */     
/*  270 */     double gapVertical = plotArea.getHeight() * gapPercent * 2.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  284 */     double linkX = plotArea.getX() + gapHorizontal / 2.0D;
/*  285 */     double linkY = plotArea.getY() + gapVertical / 2.0D;
/*  286 */     double linkW = plotArea.getWidth() - gapHorizontal;
/*  287 */     double linkH = plotArea.getHeight() - gapVertical;
/*      */ 
/*      */     
/*  290 */     if (isCircular()) {
/*  291 */       double min = Math.min(linkW, linkH) / 2.0D;
/*  292 */       linkX = (linkX + linkX + linkW) / 2.0D - min;
/*  293 */       linkY = (linkY + linkY + linkH) / 2.0D - min;
/*  294 */       linkW = 2.0D * min;
/*  295 */       linkH = 2.0D * min;
/*      */     } 
/*      */     
/*  298 */     PiePlotState state = initialise(g2, plotArea, this, (Integer)null, info);
/*      */ 
/*      */ 
/*      */     
/*  302 */     Rectangle2D linkAreaXX = new Rectangle2D.Double(linkX, linkY, linkW, linkH * (1.0D - this.depthFactor));
/*      */     
/*  304 */     state.setLinkArea(linkAreaXX);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  317 */     double hh = linkW * getLabelLinkMargin();
/*  318 */     double vv = linkH * getLabelLinkMargin();
/*  319 */     Rectangle2D explodeArea = new Rectangle2D.Double(linkX + hh / 2.0D, linkY + vv / 2.0D, linkW - hh, linkH - vv);
/*      */ 
/*      */     
/*  322 */     state.setExplodedPieArea(explodeArea);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     double maximumExplodePercent = getMaximumExplodePercent();
/*  328 */     double percent = maximumExplodePercent / (1.0D + maximumExplodePercent);
/*      */     
/*  330 */     double h1 = explodeArea.getWidth() * percent;
/*  331 */     double v1 = explodeArea.getHeight() * percent;
/*      */ 
/*      */     
/*  334 */     Rectangle2D pieArea = new Rectangle2D.Double(explodeArea.getX() + h1 / 2.0D, explodeArea.getY() + v1 / 2.0D, explodeArea.getWidth() - h1, explodeArea.getHeight() - v1);
/*      */ 
/*      */ 
/*      */     
/*  338 */     int depth = (int)(pieArea.getHeight() * this.depthFactor);
/*  339 */     Rectangle2D linkArea = new Rectangle2D.Double(linkX, linkY, linkW, linkH - depth);
/*      */     
/*  341 */     state.setLinkArea(linkArea);
/*      */     
/*  343 */     state.setPieArea(pieArea);
/*  344 */     state.setPieCenterX(pieArea.getCenterX());
/*  345 */     state.setPieCenterY(pieArea.getCenterY() - depth / 2.0D);
/*  346 */     state.setPieWRadius(pieArea.getWidth() / 2.0D);
/*  347 */     state.setPieHRadius((pieArea.getHeight() - depth) / 2.0D);
/*      */ 
/*      */     
/*  350 */     PieDataset dataset = getDataset();
/*  351 */     if (DatasetUtilities.isEmptyOrNull(getDataset())) {
/*  352 */       drawNoDataMessage(g2, plotArea);
/*  353 */       g2.setClip(savedClip);
/*  354 */       drawOutline(g2, plotArea);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  359 */     if (dataset.getKeys().size() > plotArea.getWidth()) {
/*  360 */       String text = localizationResources.getString("Too_many_elements");
/*  361 */       Font sfont = new Font("dialog", 1, 10);
/*  362 */       g2.setFont(sfont);
/*  363 */       FontMetrics fm = g2.getFontMetrics(sfont);
/*  364 */       int stringWidth = fm.stringWidth(text);
/*      */       
/*  366 */       g2.drawString(text, (int)(plotArea.getX() + (plotArea.getWidth() - stringWidth) / 2.0D), 
/*      */           
/*  368 */           (int)(plotArea.getY() + plotArea.getHeight() / 2.0D));
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  374 */     if (isCircular()) {
/*  375 */       double min = Math.min(plotArea.getWidth(), plotArea
/*  376 */           .getHeight()) / 2.0D;
/*      */       
/*  378 */       plotArea = new Rectangle2D.Double(plotArea.getCenterX() - min, plotArea.getCenterY() - min, 2.0D * min, 2.0D * min);
/*      */     } 
/*      */     
/*  381 */     List<Comparable> sectionKeys = dataset.getKeys();
/*      */     
/*  383 */     if (sectionKeys.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  388 */     double arcX = pieArea.getX();
/*  389 */     double arcY = pieArea.getY();
/*      */ 
/*      */     
/*  392 */     Composite originalComposite = g2.getComposite();
/*  393 */     g2.setComposite(AlphaComposite.getInstance(3, 
/*  394 */           getForegroundAlpha()));
/*      */     
/*  396 */     double totalValue = DatasetUtilities.calculatePieDatasetTotal(dataset);
/*  397 */     double runningTotal = 0.0D;
/*  398 */     if (depth < 0) {
/*      */       return;
/*      */     }
/*      */     
/*  402 */     ArrayList<Arc2D.Double> arcList = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     Iterator<Comparable> iterator = sectionKeys.iterator();
/*  409 */     while (iterator.hasNext()) {
/*      */       
/*  411 */       Comparable currentKey = iterator.next();
/*  412 */       Number dataValue = dataset.getValue(currentKey);
/*  413 */       if (dataValue == null) {
/*  414 */         arcList.add(null);
/*      */         continue;
/*      */       } 
/*  417 */       double value = dataValue.doubleValue();
/*  418 */       if (value <= 0.0D) {
/*  419 */         arcList.add(null);
/*      */         continue;
/*      */       } 
/*  422 */       double startAngle = getStartAngle();
/*  423 */       double direction = getDirection().getFactor();
/*  424 */       double angle1 = startAngle + direction * runningTotal * 360.0D / totalValue;
/*      */       
/*  426 */       double angle2 = startAngle + direction * (runningTotal + value) * 360.0D / totalValue;
/*      */       
/*  428 */       if (Math.abs(angle2 - angle1) > getMinimumArcAngleToDraw()) {
/*  429 */         arcList.add(new Arc2D.Double(arcX, arcY + depth, pieArea
/*  430 */               .getWidth(), pieArea.getHeight() - depth, angle1, angle2 - angle1, 2));
/*      */       }
/*      */       else {
/*      */         
/*  434 */         arcList.add(null);
/*      */       } 
/*  436 */       runningTotal += value;
/*      */     } 
/*      */     
/*  439 */     Shape oldClip = g2.getClip();
/*      */ 
/*      */     
/*  442 */     Ellipse2D top = new Ellipse2D.Double(pieArea.getX(), pieArea.getY(), pieArea.getWidth(), pieArea.getHeight() - depth);
/*      */ 
/*      */     
/*  445 */     Ellipse2D bottom = new Ellipse2D.Double(pieArea.getX(), pieArea.getY() + depth, pieArea.getWidth(), pieArea.getHeight() - depth);
/*      */ 
/*      */ 
/*      */     
/*  449 */     Rectangle2D lower = new Rectangle2D.Double(top.getX(), top.getCenterY(), pieArea.getWidth(), bottom.getMaxY() - top.getCenterY());
/*      */ 
/*      */     
/*  452 */     Rectangle2D upper = new Rectangle2D.Double(pieArea.getX(), top.getY(), pieArea.getWidth(), bottom.getCenterY() - top.getY());
/*      */     
/*  454 */     Area a = new Area(top);
/*  455 */     a.add(new Area(lower));
/*  456 */     Area b = new Area(bottom);
/*  457 */     b.add(new Area(upper));
/*  458 */     Area pie = new Area(a);
/*  459 */     pie.intersect(b);
/*      */     
/*  461 */     Area front = new Area(pie);
/*  462 */     front.subtract(new Area(top));
/*      */     
/*  464 */     Area back = new Area(pie);
/*  465 */     back.subtract(new Area(bottom));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  471 */     int categoryCount = arcList.size();
/*  472 */     for (int categoryIndex = 0; categoryIndex < categoryCount; 
/*  473 */       categoryIndex++) {
/*  474 */       Arc2D.Double arc = arcList.get(categoryIndex);
/*  475 */       if (arc != null) {
/*      */ 
/*      */         
/*  478 */         Comparable key = getSectionKey(categoryIndex);
/*  479 */         Paint paint = lookupSectionPaint(key);
/*  480 */         Paint outlinePaint = lookupSectionOutlinePaint(key);
/*  481 */         Stroke outlineStroke = lookupSectionOutlineStroke(key);
/*  482 */         g2.setPaint(paint);
/*  483 */         g2.fill(arc);
/*  484 */         g2.setPaint(outlinePaint);
/*  485 */         g2.setStroke(outlineStroke);
/*  486 */         g2.draw(arc);
/*  487 */         g2.setPaint(paint);
/*      */         
/*  489 */         Point2D p1 = arc.getStartPoint();
/*      */ 
/*      */ 
/*      */         
/*  493 */         int[] xs = { (int)arc.getCenterX(), (int)arc.getCenterX(), (int)p1.getX(), (int)p1.getX() };
/*      */         
/*  495 */         int[] ys = { (int)arc.getCenterY(), (int)arc.getCenterY() - depth, (int)p1.getY() - depth, (int)p1.getY() };
/*  496 */         Polygon polygon = new Polygon(xs, ys, 4);
/*  497 */         g2.setPaint(Color.lightGray);
/*  498 */         g2.fill(polygon);
/*  499 */         g2.setPaint(outlinePaint);
/*  500 */         g2.setStroke(outlineStroke);
/*  501 */         g2.draw(polygon);
/*  502 */         g2.setPaint(paint);
/*      */       } 
/*      */     } 
/*      */     
/*  506 */     g2.setPaint(Color.gray);
/*  507 */     g2.fill(back);
/*  508 */     g2.fill(front);
/*      */ 
/*      */     
/*  511 */     int cat = 0;
/*  512 */     iterator = (Iterator)arcList.iterator();
/*  513 */     while (iterator.hasNext()) {
/*  514 */       Arc2D segment = (Arc2D)iterator.next();
/*  515 */       if (segment != null) {
/*  516 */         Comparable key = getSectionKey(cat);
/*  517 */         Paint paint = lookupSectionPaint(key);
/*  518 */         Paint outlinePaint = lookupSectionOutlinePaint(key);
/*  519 */         Stroke outlineStroke = lookupSectionOutlineStroke(key);
/*  520 */         drawSide(g2, pieArea, segment, front, back, paint, outlinePaint, outlineStroke, false, true);
/*      */       } 
/*      */       
/*  523 */       cat++;
/*      */     } 
/*      */ 
/*      */     
/*  527 */     cat = 0;
/*  528 */     iterator = (Iterator)arcList.iterator();
/*  529 */     while (iterator.hasNext()) {
/*  530 */       Arc2D segment = (Arc2D)iterator.next();
/*  531 */       if (segment != null) {
/*  532 */         Comparable key = getSectionKey(cat);
/*  533 */         Paint paint = lookupSectionPaint(key);
/*  534 */         Paint outlinePaint = lookupSectionOutlinePaint(key);
/*  535 */         Stroke outlineStroke = lookupSectionOutlineStroke(key);
/*  536 */         drawSide(g2, pieArea, segment, front, back, paint, outlinePaint, outlineStroke, true, false);
/*      */       } 
/*      */       
/*  539 */       cat++;
/*      */     } 
/*      */     
/*  542 */     g2.setClip(oldClip);
/*      */ 
/*      */ 
/*      */     
/*  546 */     for (int sectionIndex = 0; sectionIndex < categoryCount; 
/*  547 */       sectionIndex++) {
/*  548 */       Arc2D.Double arc = arcList.get(sectionIndex);
/*  549 */       if (arc != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  554 */         Arc2D upperArc = new Arc2D.Double(arcX, arcY, pieArea.getWidth(), pieArea.getHeight() - depth, arc.getAngleStart(), arc.getAngleExtent(), 2);
/*      */         
/*  556 */         Comparable currentKey = sectionKeys.get(sectionIndex);
/*  557 */         Paint paint = lookupSectionPaint(currentKey, true);
/*  558 */         Paint outlinePaint = lookupSectionOutlinePaint(currentKey);
/*  559 */         Stroke outlineStroke = lookupSectionOutlineStroke(currentKey);
/*  560 */         g2.setPaint(paint);
/*  561 */         g2.fill(upperArc);
/*  562 */         g2.setStroke(outlineStroke);
/*  563 */         g2.setPaint(outlinePaint);
/*  564 */         g2.draw(upperArc);
/*      */ 
/*      */         
/*  567 */         if (info != null) {
/*      */           
/*  569 */           EntityCollection entities = info.getOwner().getEntityCollection();
/*  570 */           if (entities != null) {
/*  571 */             String tip = null;
/*  572 */             PieToolTipGenerator tipster = getToolTipGenerator();
/*  573 */             if (tipster != null)
/*      */             {
/*  575 */               tip = tipster.generateToolTip(dataset, currentKey);
/*      */             }
/*  577 */             String url = null;
/*  578 */             if (getURLGenerator() != null) {
/*  579 */               url = getURLGenerator().generateURL(dataset, currentKey, 
/*  580 */                   getPieIndex());
/*      */             }
/*      */             
/*  583 */             PieSectionEntity entity = new PieSectionEntity(upperArc, dataset, getPieIndex(), sectionIndex, currentKey, tip, url);
/*      */             
/*  585 */             entities.add((ChartEntity)entity);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  590 */     List keys = dataset.getKeys();
/*      */ 
/*      */     
/*  593 */     Rectangle2D adjustedPlotArea = new Rectangle2D.Double(originalPlotArea.getX(), originalPlotArea.getY(), originalPlotArea.getWidth(), originalPlotArea.getHeight() - depth);
/*      */     
/*  595 */     if (getSimpleLabels()) {
/*  596 */       drawSimpleLabels(g2, keys, totalValue, adjustedPlotArea, linkArea, state);
/*      */     }
/*      */     else {
/*      */       
/*  600 */       drawLabels(g2, keys, totalValue, adjustedPlotArea, linkArea, state);
/*      */     } 
/*      */ 
/*      */     
/*  604 */     if (getShadowGenerator() != null) {
/*      */       
/*  606 */       BufferedImage shadowImage = getShadowGenerator().createDropShadow(dataImage);
/*  607 */       g2 = savedG2;
/*  608 */       g2.drawImage(shadowImage, (int)plotArea.getX() + 
/*  609 */           getShadowGenerator().calculateOffsetX(), 
/*  610 */           (int)plotArea.getY() + 
/*  611 */           getShadowGenerator().calculateOffsetY(), (ImageObserver)null);
/*  612 */       g2.drawImage(dataImage, (int)plotArea.getX(), 
/*  613 */           (int)plotArea.getY(), (ImageObserver)null);
/*      */     } 
/*      */     
/*  616 */     g2.setClip(savedClip);
/*  617 */     g2.setComposite(originalComposite);
/*  618 */     drawOutline(g2, originalPlotArea);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void drawSide(Graphics2D g2, Rectangle2D plotArea, Arc2D arc, Area front, Area back, Paint paint, Paint outlinePaint, Stroke outlineStroke, boolean drawFront, boolean drawBack) {
/*  647 */     if (getDarkerSides()) {
/*  648 */       paint = PaintAlpha.darker(paint);
/*      */     }
/*      */     
/*  651 */     double start = arc.getAngleStart();
/*  652 */     double extent = arc.getAngleExtent();
/*  653 */     double end = start + extent;
/*      */     
/*  655 */     g2.setStroke(outlineStroke);
/*      */ 
/*      */     
/*  658 */     if (extent < 0.0D) {
/*      */       
/*  660 */       if (isAngleAtFront(start)) {
/*      */         
/*  662 */         if (!isAngleAtBack(end))
/*      */         {
/*  664 */           if (extent > -180.0D)
/*      */           {
/*  666 */             if (drawFront)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  671 */               Area side = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), arc.getStartPoint().getX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*  672 */               side.intersect(front);
/*  673 */               g2.setPaint(paint);
/*  674 */               g2.fill(side);
/*  675 */               g2.setPaint(outlinePaint);
/*  676 */               g2.draw(side);
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else
/*      */           {
/*      */             
/*  685 */             Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  686 */             side1.intersect(front);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  691 */             Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*      */             
/*  693 */             side2.intersect(front);
/*  694 */             g2.setPaint(paint);
/*  695 */             if (drawFront) {
/*  696 */               g2.fill(side1);
/*  697 */               g2.fill(side2);
/*      */             } 
/*      */             
/*  700 */             if (drawBack) {
/*  701 */               g2.fill(back);
/*      */             }
/*      */             
/*  704 */             g2.setPaint(outlinePaint);
/*  705 */             if (drawFront) {
/*  706 */               g2.draw(side1);
/*  707 */               g2.draw(side2);
/*      */             } 
/*      */             
/*  710 */             if (drawBack) {
/*  711 */               g2.draw(back);
/*      */             
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  719 */           if (drawBack) {
/*      */ 
/*      */ 
/*      */             
/*  723 */             Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  724 */             side2.intersect(back);
/*  725 */             g2.setPaint(paint);
/*  726 */             g2.fill(side2);
/*  727 */             g2.setPaint(outlinePaint);
/*  728 */             g2.draw(side2);
/*      */           } 
/*      */           
/*  731 */           if (drawFront)
/*      */           {
/*      */ 
/*      */             
/*  735 */             Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  736 */             side1.intersect(front);
/*  737 */             g2.setPaint(paint);
/*  738 */             g2.fill(side1);
/*  739 */             g2.setPaint(outlinePaint);
/*  740 */             g2.draw(side1);
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  747 */       else if (!isAngleAtFront(end)) {
/*  748 */         if (extent > -180.0D) {
/*  749 */           if (drawBack)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  754 */             Area side = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), arc.getEndPoint().getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  755 */             side.intersect(back);
/*  756 */             g2.setPaint(paint);
/*  757 */             g2.fill(side);
/*  758 */             g2.setPaint(outlinePaint);
/*  759 */             g2.draw(side);
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  767 */           Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  768 */           side1.intersect(back);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  773 */           Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*      */           
/*  775 */           side2.intersect(back);
/*      */           
/*  777 */           g2.setPaint(paint);
/*  778 */           if (drawBack) {
/*  779 */             g2.fill(side1);
/*  780 */             g2.fill(side2);
/*      */           } 
/*      */           
/*  783 */           if (drawFront) {
/*  784 */             g2.fill(front);
/*      */           }
/*      */           
/*  787 */           g2.setPaint(outlinePaint);
/*  788 */           if (drawBack) {
/*  789 */             g2.draw(side1);
/*  790 */             g2.draw(side2);
/*      */           } 
/*      */           
/*  793 */           if (drawFront) {
/*  794 */             g2.draw(front);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  801 */         if (drawBack) {
/*      */ 
/*      */ 
/*      */           
/*  805 */           Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  806 */           side1.intersect(back);
/*  807 */           g2.setPaint(paint);
/*  808 */           g2.fill(side1);
/*  809 */           g2.setPaint(outlinePaint);
/*  810 */           g2.draw(side1);
/*      */         } 
/*      */         
/*  813 */         if (drawFront)
/*      */         {
/*      */ 
/*      */           
/*  817 */           Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*  818 */           side2.intersect(front);
/*  819 */           g2.setPaint(paint);
/*  820 */           g2.fill(side2);
/*  821 */           g2.setPaint(outlinePaint);
/*  822 */           g2.draw(side2);
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  828 */     else if (extent > 0.0D) {
/*      */       
/*  830 */       if (isAngleAtFront(start)) {
/*      */         
/*  832 */         if (!isAngleAtBack(end)) {
/*      */           
/*  834 */           if (extent < 180.0D) {
/*  835 */             if (drawFront)
/*      */             {
/*      */ 
/*      */ 
/*      */               
/*  840 */               Area side = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), arc.getEndPoint().getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  841 */               side.intersect(front);
/*  842 */               g2.setPaint(paint);
/*  843 */               g2.fill(side);
/*  844 */               g2.setPaint(outlinePaint);
/*  845 */               g2.draw(side);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  852 */             Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  853 */             side1.intersect(front);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  858 */             Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  859 */             side2.intersect(front);
/*      */             
/*  861 */             g2.setPaint(paint);
/*  862 */             if (drawFront) {
/*  863 */               g2.fill(side1);
/*  864 */               g2.fill(side2);
/*      */             } 
/*      */             
/*  867 */             if (drawBack) {
/*  868 */               g2.fill(back);
/*      */             }
/*      */             
/*  871 */             g2.setPaint(outlinePaint);
/*  872 */             if (drawFront) {
/*  873 */               g2.draw(side1);
/*  874 */               g2.draw(side2);
/*      */             } 
/*      */             
/*  877 */             if (drawBack) {
/*  878 */               g2.draw(back);
/*      */             }
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  884 */           if (drawBack) {
/*      */ 
/*      */ 
/*      */             
/*  888 */             Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*  889 */             side2.intersect(back);
/*  890 */             g2.setPaint(paint);
/*  891 */             g2.fill(side2);
/*  892 */             g2.setPaint(outlinePaint);
/*  893 */             g2.draw(side2);
/*      */           } 
/*      */           
/*  896 */           if (drawFront)
/*      */           {
/*      */ 
/*      */             
/*  900 */             Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  901 */             side1.intersect(front);
/*  902 */             g2.setPaint(paint);
/*  903 */             g2.fill(side1);
/*  904 */             g2.setPaint(outlinePaint);
/*  905 */             g2.draw(side1);
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  911 */       else if (!isAngleAtFront(end)) {
/*  912 */         if (extent < 180.0D) {
/*  913 */           if (drawBack)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  918 */             Area side = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), arc.getStartPoint().getX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*  919 */             side.intersect(back);
/*  920 */             g2.setPaint(paint);
/*  921 */             g2.fill(side);
/*  922 */             g2.setPaint(outlinePaint);
/*  923 */             g2.draw(side);
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  931 */           Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
/*  932 */           side1.intersect(back);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  937 */           Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
/*  938 */           side2.intersect(back);
/*      */           
/*  940 */           g2.setPaint(paint);
/*  941 */           if (drawBack) {
/*  942 */             g2.fill(side1);
/*  943 */             g2.fill(side2);
/*      */           } 
/*      */           
/*  946 */           if (drawFront) {
/*  947 */             g2.fill(front);
/*      */           }
/*      */           
/*  950 */           g2.setPaint(outlinePaint);
/*  951 */           if (drawBack) {
/*  952 */             g2.draw(side1);
/*  953 */             g2.draw(side2);
/*      */           } 
/*      */           
/*  956 */           if (drawFront) {
/*  957 */             g2.draw(front);
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  964 */         if (drawBack) {
/*      */ 
/*      */ 
/*      */           
/*  968 */           Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  969 */           side1.intersect(back);
/*  970 */           g2.setPaint(paint);
/*  971 */           g2.fill(side1);
/*  972 */           g2.setPaint(outlinePaint);
/*  973 */           g2.draw(side1);
/*      */         } 
/*      */         
/*  976 */         if (drawFront) {
/*      */ 
/*      */ 
/*      */           
/*  980 */           Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
/*  981 */           side2.intersect(front);
/*  982 */           g2.setPaint(paint);
/*  983 */           g2.fill(side2);
/*  984 */           g2.setPaint(outlinePaint);
/*  985 */           g2.draw(side2);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlotType() {
/* 1001 */     return localizationResources.getString("Pie_3D_Plot");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isAngleAtFront(double angle) {
/* 1014 */     return (Math.sin(Math.toRadians(angle)) < 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isAngleAtBack(double angle) {
/* 1027 */     return (Math.sin(Math.toRadians(angle)) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1039 */     if (obj == this) {
/* 1040 */       return true;
/*      */     }
/* 1042 */     if (!(obj instanceof PiePlot3D)) {
/* 1043 */       return false;
/*      */     }
/* 1045 */     PiePlot3D that = (PiePlot3D)obj;
/* 1046 */     if (this.depthFactor != that.depthFactor) {
/* 1047 */       return false;
/*      */     }
/* 1049 */     if (this.darkerSides != that.darkerSides) {
/* 1050 */       return false;
/*      */     }
/* 1052 */     return super.equals(obj);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PiePlot3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
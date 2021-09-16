/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.Format;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.entity.PieSectionEntity;
/*     */ import org.jfree.chart.labels.PieToolTipGenerator;
/*     */ import org.jfree.chart.urls.PieURLGenerator;
/*     */ import org.jfree.chart.util.LineUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PaintUtilities;
/*     */ import org.jfree.util.Rotation;
/*     */ import org.jfree.util.ShapeUtilities;
/*     */ import org.jfree.util.UnitType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RingPlot
/*     */   extends PiePlot
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1556064784129676620L;
/* 100 */   private CenterTextMode centerTextMode = CenterTextMode.NONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String centerText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private Format centerTextFormatter = new DecimalFormat("0.00");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Font centerTextFont;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Color centerTextColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean separatorsVisible;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Stroke separatorStroke;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint separatorPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   private double innerSeparatorExtension;
/*     */ 
/*     */ 
/*     */   
/*     */   private double outerSeparatorExtension;
/*     */ 
/*     */ 
/*     */   
/*     */   private double sectionDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RingPlot() {
/* 153 */     this((PieDataset)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RingPlot(PieDataset dataset) {
/* 162 */     super(dataset);
/* 163 */     this.centerTextMode = CenterTextMode.NONE;
/* 164 */     this.centerText = null;
/* 165 */     this.centerTextFormatter = new DecimalFormat("0.00");
/* 166 */     this.centerTextFont = DEFAULT_LABEL_FONT;
/* 167 */     this.centerTextColor = Color.BLACK;
/* 168 */     this.separatorsVisible = true;
/* 169 */     this.separatorStroke = new BasicStroke(0.5F);
/* 170 */     this.separatorPaint = Color.gray;
/* 171 */     this.innerSeparatorExtension = 0.2D;
/* 172 */     this.outerSeparatorExtension = 0.2D;
/* 173 */     this.sectionDepth = 0.2D;
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
/*     */   public CenterTextMode getCenterTextMode() {
/* 186 */     return this.centerTextMode;
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
/*     */   public void setCenterTextMode(CenterTextMode mode) {
/* 202 */     ParamChecks.nullNotPermitted(mode, "mode");
/* 203 */     this.centerTextMode = mode;
/* 204 */     fireChangeEvent();
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
/*     */   public String getCenterText() {
/* 216 */     return this.centerText;
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
/*     */   public void setCenterText(String text) {
/* 229 */     this.centerText = text;
/* 230 */     fireChangeEvent();
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
/*     */   public Format getCenterTextFormatter() {
/* 243 */     return this.centerTextFormatter;
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
/*     */   public void setCenterTextFormatter(Format formatter) {
/* 255 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 256 */     this.centerTextFormatter = formatter;
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
/*     */   public Font getCenterTextFont() {
/* 268 */     return this.centerTextFont;
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
/*     */   public void setCenterTextFont(Font font) {
/* 280 */     ParamChecks.nullNotPermitted(font, "font");
/* 281 */     this.centerTextFont = font;
/* 282 */     fireChangeEvent();
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
/*     */   public Color getCenterTextColor() {
/* 294 */     return this.centerTextColor;
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
/*     */   public void setCenterTextColor(Color color) {
/* 306 */     ParamChecks.nullNotPermitted(color, "color");
/* 307 */     this.centerTextColor = color;
/* 308 */     fireChangeEvent();
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
/*     */   public boolean getSeparatorsVisible() {
/* 320 */     return this.separatorsVisible;
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
/*     */   public void setSeparatorsVisible(boolean visible) {
/* 333 */     this.separatorsVisible = visible;
/* 334 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getSeparatorStroke() {
/* 345 */     return this.separatorStroke;
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
/*     */   public void setSeparatorStroke(Stroke stroke) {
/* 357 */     ParamChecks.nullNotPermitted(stroke, "stroke");
/* 358 */     this.separatorStroke = stroke;
/* 359 */     fireChangeEvent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getSeparatorPaint() {
/* 370 */     return this.separatorPaint;
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
/*     */   public void setSeparatorPaint(Paint paint) {
/* 382 */     ParamChecks.nullNotPermitted(paint, "paint");
/* 383 */     this.separatorPaint = paint;
/* 384 */     fireChangeEvent();
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
/*     */   public double getInnerSeparatorExtension() {
/* 397 */     return this.innerSeparatorExtension;
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
/*     */   public void setInnerSeparatorExtension(double percent) {
/* 411 */     this.innerSeparatorExtension = percent;
/* 412 */     fireChangeEvent();
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
/*     */   public double getOuterSeparatorExtension() {
/* 425 */     return this.outerSeparatorExtension;
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
/*     */   public void setOuterSeparatorExtension(double percent) {
/* 438 */     this.outerSeparatorExtension = percent;
/* 439 */     fireChangeEvent();
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
/*     */   public double getSectionDepth() {
/* 452 */     return this.sectionDepth;
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
/*     */   public void setSectionDepth(double sectionDepth) {
/* 465 */     this.sectionDepth = sectionDepth;
/* 466 */     fireChangeEvent();
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
/*     */   public PiePlotState initialise(Graphics2D g2, Rectangle2D plotArea, PiePlot plot, Integer index, PlotRenderingInfo info) {
/* 487 */     PiePlotState state = super.initialise(g2, plotArea, plot, index, info);
/* 488 */     state.setPassesRequired(3);
/* 489 */     return state;
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
/*     */   protected void drawItem(Graphics2D g2, int section, Rectangle2D dataArea, PiePlotState state, int currentPass) {
/* 505 */     PieDataset dataset = getDataset();
/* 506 */     Number n = dataset.getValue(section);
/* 507 */     if (n == null) {
/*     */       return;
/*     */     }
/* 510 */     double value = n.doubleValue();
/* 511 */     double angle1 = 0.0D;
/* 512 */     double angle2 = 0.0D;
/*     */     
/* 514 */     Rotation direction = getDirection();
/* 515 */     if (direction == Rotation.CLOCKWISE) {
/* 516 */       angle1 = state.getLatestAngle();
/* 517 */       angle2 = angle1 - value / state.getTotal() * 360.0D;
/*     */     }
/* 519 */     else if (direction == Rotation.ANTICLOCKWISE) {
/* 520 */       angle1 = state.getLatestAngle();
/* 521 */       angle2 = angle1 + value / state.getTotal() * 360.0D;
/*     */     } else {
/*     */       
/* 524 */       throw new IllegalStateException("Rotation type not recognised.");
/*     */     } 
/*     */     
/* 527 */     double angle = angle2 - angle1;
/* 528 */     if (Math.abs(angle) > getMinimumArcAngleToDraw()) {
/* 529 */       Comparable key = getSectionKey(section);
/* 530 */       double ep = 0.0D;
/* 531 */       double mep = getMaximumExplodePercent();
/* 532 */       if (mep > 0.0D) {
/* 533 */         ep = getExplodePercent(key) / mep;
/*     */       }
/* 535 */       Rectangle2D arcBounds = getArcBounds(state.getPieArea(), state
/* 536 */           .getExplodedPieArea(), angle1, angle, ep);
/* 537 */       Arc2D.Double arc = new Arc2D.Double(arcBounds, angle1, angle, 0);
/*     */ 
/*     */ 
/*     */       
/* 541 */       double depth = this.sectionDepth / 2.0D;
/* 542 */       RectangleInsets s = new RectangleInsets(UnitType.RELATIVE, depth, depth, depth, depth);
/*     */       
/* 544 */       Rectangle2D innerArcBounds = new Rectangle2D.Double();
/* 545 */       innerArcBounds.setRect(arcBounds);
/* 546 */       s.trim(innerArcBounds);
/*     */ 
/*     */       
/* 549 */       Arc2D.Double arc2 = new Arc2D.Double(innerArcBounds, angle1 + angle, -angle, 0);
/*     */       
/* 551 */       GeneralPath path = new GeneralPath();
/* 552 */       path.moveTo((float)arc.getStartPoint().getX(), 
/* 553 */           (float)arc.getStartPoint().getY());
/* 554 */       path.append(arc.getPathIterator(null), false);
/* 555 */       path.append(arc2.getPathIterator(null), true);
/* 556 */       path.closePath();
/*     */ 
/*     */       
/* 559 */       Line2D separator = new Line2D.Double(arc2.getEndPoint(), arc.getStartPoint());
/*     */       
/* 561 */       if (currentPass == 0) {
/* 562 */         Paint shadowPaint = getShadowPaint();
/* 563 */         double shadowXOffset = getShadowXOffset();
/* 564 */         double shadowYOffset = getShadowYOffset();
/* 565 */         if (shadowPaint != null && getShadowGenerator() == null) {
/* 566 */           Shape shadowArc = ShapeUtilities.createTranslatedShape(path, (float)shadowXOffset, (float)shadowYOffset);
/*     */           
/* 568 */           g2.setPaint(shadowPaint);
/* 569 */           g2.fill(shadowArc);
/*     */         }
/*     */       
/* 572 */       } else if (currentPass == 1) {
/* 573 */         Paint paint = lookupSectionPaint(key);
/* 574 */         g2.setPaint(paint);
/* 575 */         g2.fill(path);
/* 576 */         Paint outlinePaint = lookupSectionOutlinePaint(key);
/* 577 */         Stroke outlineStroke = lookupSectionOutlineStroke(key);
/* 578 */         if (getSectionOutlinesVisible() && outlinePaint != null && outlineStroke != null) {
/*     */           
/* 580 */           g2.setPaint(outlinePaint);
/* 581 */           g2.setStroke(outlineStroke);
/* 582 */           g2.draw(path);
/*     */         } 
/*     */         
/* 585 */         if (section == 0) {
/* 586 */           String nstr = null;
/* 587 */           if (this.centerTextMode.equals(CenterTextMode.VALUE)) {
/* 588 */             nstr = this.centerTextFormatter.format(n);
/* 589 */           } else if (this.centerTextMode.equals(CenterTextMode.FIXED)) {
/* 590 */             nstr = this.centerText;
/*     */           } 
/* 592 */           if (nstr != null) {
/* 593 */             g2.setFont(this.centerTextFont);
/* 594 */             g2.setPaint(this.centerTextColor);
/* 595 */             TextUtilities.drawAlignedString(nstr, g2, 
/* 596 */                 (float)dataArea.getCenterX(), 
/* 597 */                 (float)dataArea.getCenterY(), TextAnchor.CENTER);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 603 */         if (state.getInfo() != null) {
/* 604 */           EntityCollection entities = state.getEntityCollection();
/* 605 */           if (entities != null) {
/* 606 */             String tip = null;
/*     */             
/* 608 */             PieToolTipGenerator toolTipGenerator = getToolTipGenerator();
/* 609 */             if (toolTipGenerator != null) {
/* 610 */               tip = toolTipGenerator.generateToolTip(dataset, key);
/*     */             }
/*     */             
/* 613 */             String url = null;
/* 614 */             PieURLGenerator urlGenerator = getURLGenerator();
/* 615 */             if (urlGenerator != null) {
/* 616 */               url = urlGenerator.generateURL(dataset, key, 
/* 617 */                   getPieIndex());
/*     */             }
/*     */             
/* 620 */             PieSectionEntity entity = new PieSectionEntity(path, dataset, getPieIndex(), section, key, tip, url);
/*     */             
/* 622 */             entities.add((ChartEntity)entity);
/*     */           }
/*     */         
/*     */         } 
/* 626 */       } else if (currentPass == 2 && 
/* 627 */         this.separatorsVisible) {
/* 628 */         Line2D extendedSeparator = LineUtilities.extendLine(separator, this.innerSeparatorExtension, this.outerSeparatorExtension);
/*     */ 
/*     */         
/* 631 */         g2.setStroke(this.separatorStroke);
/* 632 */         g2.setPaint(this.separatorPaint);
/* 633 */         g2.draw(extendedSeparator);
/*     */       } 
/*     */     } 
/*     */     
/* 637 */     state.setLatestAngle(angle2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getLabelLinkDepth() {
/* 648 */     return Math.min(super.getLabelLinkDepth(), getSectionDepth() / 2.0D);
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
/* 660 */     if (this == obj) {
/* 661 */       return true;
/*     */     }
/* 663 */     if (!(obj instanceof RingPlot)) {
/* 664 */       return false;
/*     */     }
/* 666 */     RingPlot that = (RingPlot)obj;
/* 667 */     if (!this.centerTextMode.equals(that.centerTextMode)) {
/* 668 */       return false;
/*     */     }
/* 670 */     if (!ObjectUtilities.equal(this.centerText, that.centerText)) {
/* 671 */       return false;
/*     */     }
/* 673 */     if (!this.centerTextFormatter.equals(that.centerTextFormatter)) {
/* 674 */       return false;
/*     */     }
/* 676 */     if (!this.centerTextFont.equals(that.centerTextFont)) {
/* 677 */       return false;
/*     */     }
/* 679 */     if (!this.centerTextColor.equals(that.centerTextColor)) {
/* 680 */       return false;
/*     */     }
/* 682 */     if (this.separatorsVisible != that.separatorsVisible) {
/* 683 */       return false;
/*     */     }
/* 685 */     if (!ObjectUtilities.equal(this.separatorStroke, that.separatorStroke))
/*     */     {
/* 687 */       return false;
/*     */     }
/* 689 */     if (!PaintUtilities.equal(this.separatorPaint, that.separatorPaint)) {
/* 690 */       return false;
/*     */     }
/* 692 */     if (this.innerSeparatorExtension != that.innerSeparatorExtension) {
/* 693 */       return false;
/*     */     }
/* 695 */     if (this.outerSeparatorExtension != that.outerSeparatorExtension) {
/* 696 */       return false;
/*     */     }
/* 698 */     if (this.sectionDepth != that.sectionDepth) {
/* 699 */       return false;
/*     */     }
/* 701 */     return super.equals(obj);
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
/* 712 */     stream.defaultWriteObject();
/* 713 */     SerialUtilities.writeStroke(this.separatorStroke, stream);
/* 714 */     SerialUtilities.writePaint(this.separatorPaint, stream);
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
/* 727 */     stream.defaultReadObject();
/* 728 */     this.separatorStroke = SerialUtilities.readStroke(stream);
/* 729 */     this.separatorPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/RingPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
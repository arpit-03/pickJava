/*      */ package org.jfree.chart.renderer.xy;
/*      */ 
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import org.jfree.chart.LegendItem;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.EntityCollection;
/*      */ import org.jfree.chart.plot.CrosshairState;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.PlotRenderingInfo;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.util.LineUtilities;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.general.Dataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.io.SerialUtilities;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.util.BooleanList;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ import org.jfree.util.PublicCloneable;
/*      */ import org.jfree.util.ShapeUtilities;
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
/*      */ public class XYLineAndShapeRenderer
/*      */   extends AbstractXYItemRenderer
/*      */   implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -7435246895986425885L;
/*      */   private Boolean linesVisible;
/*      */   private BooleanList seriesLinesVisible;
/*      */   private boolean baseLinesVisible;
/*      */   private transient Shape legendLine;
/*      */   private Boolean shapesVisible;
/*      */   private BooleanList seriesShapesVisible;
/*      */   private boolean baseShapesVisible;
/*      */   private Boolean shapesFilled;
/*      */   private BooleanList seriesShapesFilled;
/*      */   private boolean baseShapesFilled;
/*      */   private boolean drawOutlines;
/*      */   private boolean useFillPaint;
/*      */   private boolean useOutlinePaint;
/*      */   private boolean drawSeriesLineAsPath;
/*      */   
/*      */   public XYLineAndShapeRenderer() {
/*  198 */     this(true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYLineAndShapeRenderer(boolean lines, boolean shapes) {
/*  208 */     this.linesVisible = null;
/*  209 */     this.seriesLinesVisible = new BooleanList();
/*  210 */     this.baseLinesVisible = lines;
/*  211 */     this.legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
/*      */     
/*  213 */     this.shapesVisible = null;
/*  214 */     this.seriesShapesVisible = new BooleanList();
/*  215 */     this.baseShapesVisible = shapes;
/*      */     
/*  217 */     this.shapesFilled = null;
/*  218 */     this.useFillPaint = false;
/*  219 */     this.seriesShapesFilled = new BooleanList();
/*  220 */     this.baseShapesFilled = true;
/*      */     
/*  222 */     this.drawOutlines = true;
/*  223 */     this.useOutlinePaint = false;
/*      */ 
/*      */     
/*  226 */     this.drawSeriesLineAsPath = false;
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
/*      */   public boolean getDrawSeriesLineAsPath() {
/*  238 */     return this.drawSeriesLineAsPath;
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
/*      */   public void setDrawSeriesLineAsPath(boolean flag) {
/*  251 */     if (this.drawSeriesLineAsPath != flag) {
/*  252 */       this.drawSeriesLineAsPath = flag;
/*  253 */       fireChangeEvent();
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
/*      */   public int getPassCount() {
/*  266 */     return 2;
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
/*      */   public boolean getItemLineVisible(int series, int item) {
/*  281 */     Boolean flag = this.linesVisible;
/*  282 */     if (flag == null) {
/*  283 */       flag = getSeriesLinesVisible(series);
/*      */     }
/*  285 */     if (flag != null) {
/*  286 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  289 */     return this.baseLinesVisible;
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
/*      */   public Boolean getLinesVisible() {
/*  305 */     return this.linesVisible;
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
/*      */   public void setLinesVisible(Boolean visible) {
/*  321 */     this.linesVisible = visible;
/*  322 */     fireChangeEvent();
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
/*      */   public void setLinesVisible(boolean visible) {
/*  337 */     setLinesVisible(Boolean.valueOf(visible));
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
/*      */   public Boolean getSeriesLinesVisible(int series) {
/*  351 */     return this.seriesLinesVisible.getBoolean(series);
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
/*      */   public void setSeriesLinesVisible(int series, Boolean flag) {
/*  364 */     this.seriesLinesVisible.setBoolean(series, flag);
/*  365 */     fireChangeEvent();
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
/*      */   public void setSeriesLinesVisible(int series, boolean visible) {
/*  378 */     setSeriesLinesVisible(series, Boolean.valueOf(visible));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseLinesVisible() {
/*  389 */     return this.baseLinesVisible;
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
/*      */   public void setBaseLinesVisible(boolean flag) {
/*  401 */     this.baseLinesVisible = flag;
/*  402 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getLegendLine() {
/*  413 */     return this.legendLine;
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
/*      */   public void setLegendLine(Shape line) {
/*  425 */     ParamChecks.nullNotPermitted(line, "line");
/*  426 */     this.legendLine = line;
/*  427 */     fireChangeEvent();
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
/*      */   public boolean getItemShapeVisible(int series, int item) {
/*  446 */     Boolean flag = this.shapesVisible;
/*  447 */     if (flag == null) {
/*  448 */       flag = getSeriesShapesVisible(series);
/*      */     }
/*  450 */     if (flag != null) {
/*  451 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  454 */     return this.baseShapesVisible;
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
/*      */   public Boolean getShapesVisible() {
/*  469 */     return this.shapesVisible;
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
/*      */   public void setShapesVisible(Boolean visible) {
/*  483 */     this.shapesVisible = visible;
/*  484 */     fireChangeEvent();
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
/*      */   public void setShapesVisible(boolean visible) {
/*  498 */     setShapesVisible(Boolean.valueOf(visible));
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
/*      */   public Boolean getSeriesShapesVisible(int series) {
/*  512 */     return this.seriesShapesVisible.getBoolean(series);
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
/*      */   public void setSeriesShapesVisible(int series, boolean visible) {
/*  525 */     setSeriesShapesVisible(series, Boolean.valueOf(visible));
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
/*      */   public void setSeriesShapesVisible(int series, Boolean flag) {
/*  538 */     this.seriesShapesVisible.setBoolean(series, flag);
/*  539 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseShapesVisible() {
/*  550 */     return this.baseShapesVisible;
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
/*      */   public void setBaseShapesVisible(boolean flag) {
/*  562 */     this.baseShapesVisible = flag;
/*  563 */     fireChangeEvent();
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
/*      */   public boolean getItemShapeFilled(int series, int item) {
/*  582 */     Boolean flag = this.shapesFilled;
/*  583 */     if (flag == null) {
/*  584 */       flag = getSeriesShapesFilled(series);
/*      */     }
/*  586 */     if (flag != null) {
/*  587 */       return flag.booleanValue();
/*      */     }
/*      */     
/*  590 */     return this.baseShapesFilled;
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
/*      */   public void setShapesFilled(boolean filled) {
/*  603 */     setShapesFilled(Boolean.valueOf(filled));
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
/*      */   public void setShapesFilled(Boolean filled) {
/*  615 */     this.shapesFilled = filled;
/*  616 */     fireChangeEvent();
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
/*      */   public Boolean getSeriesShapesFilled(int series) {
/*  630 */     return this.seriesShapesFilled.getBoolean(series);
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
/*      */   public void setSeriesShapesFilled(int series, boolean flag) {
/*  643 */     setSeriesShapesFilled(series, Boolean.valueOf(flag));
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
/*      */   public void setSeriesShapesFilled(int series, Boolean flag) {
/*  656 */     this.seriesShapesFilled.setBoolean(series, flag);
/*  657 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBaseShapesFilled() {
/*  668 */     return this.baseShapesFilled;
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
/*      */   public void setBaseShapesFilled(boolean flag) {
/*  680 */     this.baseShapesFilled = flag;
/*  681 */     fireChangeEvent();
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
/*      */   public boolean getDrawOutlines() {
/*  693 */     return this.drawOutlines;
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
/*      */   public void setDrawOutlines(boolean flag) {
/*  709 */     this.drawOutlines = flag;
/*  710 */     fireChangeEvent();
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
/*      */   public boolean getUseFillPaint() {
/*  727 */     return this.useFillPaint;
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
/*      */   public void setUseFillPaint(boolean flag) {
/*  740 */     this.useFillPaint = flag;
/*  741 */     fireChangeEvent();
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
/*      */   public boolean getUseOutlinePaint() {
/*  755 */     return this.useOutlinePaint;
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
/*      */   public void setUseOutlinePaint(boolean flag) {
/*  771 */     this.useOutlinePaint = flag;
/*  772 */     fireChangeEvent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class State
/*      */     extends XYItemRendererState
/*      */   {
/*      */     public GeneralPath seriesPath;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean lastPointGood;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public State(PlotRenderingInfo info) {
/*  797 */       super(info);
/*  798 */       this.seriesPath = new GeneralPath();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isLastPointGood() {
/*  808 */       return this.lastPointGood;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setLastPointGood(boolean good) {
/*  818 */       this.lastPointGood = good;
/*      */     }
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
/*      */     public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount) {
/*  835 */       this.seriesPath.reset();
/*  836 */       this.lastPointGood = false;
/*  837 */       super.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
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
/*      */   public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info) {
/*  862 */     return new State(info);
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
/*      */   public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass) {
/*  890 */     if (!getItemVisible(series, item)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  895 */     if (isLinePass(pass)) {
/*  896 */       if (getItemLineVisible(series, item)) {
/*  897 */         if (this.drawSeriesLineAsPath) {
/*  898 */           drawPrimaryLineAsPath(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
/*      */         }
/*      */         else {
/*      */           
/*  902 */           drawPrimaryLine(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */     }
/*  908 */     else if (isItemPass(pass)) {
/*      */ 
/*      */       
/*  911 */       EntityCollection entities = null;
/*  912 */       if (info != null && info.getOwner() != null) {
/*  913 */         entities = info.getOwner().getEntityCollection();
/*      */       }
/*      */       
/*  916 */       drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
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
/*      */   protected boolean isLinePass(int pass) {
/*  930 */     return (pass == 0);
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
/*      */   protected boolean isItemPass(int pass) {
/*  942 */     return (pass == 1);
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
/*      */   protected void drawPrimaryLine(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea) {
/*  971 */     if (item == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  976 */     double x1 = dataset.getXValue(series, item);
/*  977 */     double y1 = dataset.getYValue(series, item);
/*  978 */     if (Double.isNaN(y1) || Double.isNaN(x1)) {
/*      */       return;
/*      */     }
/*      */     
/*  982 */     double x0 = dataset.getXValue(series, item - 1);
/*  983 */     double y0 = dataset.getYValue(series, item - 1);
/*  984 */     if (Double.isNaN(y0) || Double.isNaN(x0)) {
/*      */       return;
/*      */     }
/*      */     
/*  988 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/*  989 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*      */     
/*  991 */     double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
/*  992 */     double transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);
/*      */     
/*  994 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/*  995 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*      */ 
/*      */     
/*  998 */     if (Double.isNaN(transX0) || Double.isNaN(transY0) || 
/*  999 */       Double.isNaN(transX1) || Double.isNaN(transY1)) {
/*      */       return;
/*      */     }
/*      */     
/* 1003 */     PlotOrientation orientation = plot.getOrientation();
/*      */     
/* 1005 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1006 */       state.workingLine.setLine(transY0, transX0, transY1, transX1);
/*      */     }
/* 1008 */     else if (orientation == PlotOrientation.VERTICAL) {
/* 1009 */       state.workingLine.setLine(transX0, transY0, transX1, transY1);
/*      */     } 
/* 1011 */     boolean visible = LineUtilities.clipLine(state.workingLine, dataArea);
/* 1012 */     if (visible) {
/* 1013 */       drawFirstPassShape(g2, pass, series, item, state.workingLine);
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
/*      */   
/*      */   protected void drawFirstPassShape(Graphics2D g2, int pass, int series, int item, Shape shape) {
/* 1028 */     g2.setStroke(getItemStroke(series, item));
/* 1029 */     g2.setPaint(getItemPaint(series, item));
/* 1030 */     g2.draw(shape);
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
/*      */   protected void drawPrimaryLineAsPath(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea) {
/* 1057 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 1058 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/*      */ 
/*      */     
/* 1061 */     double x1 = dataset.getXValue(series, item);
/* 1062 */     double y1 = dataset.getYValue(series, item);
/* 1063 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/* 1064 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*      */     
/* 1066 */     State s = (State)state;
/*      */     
/* 1068 */     if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
/* 1069 */       float x = (float)transX1;
/* 1070 */       float y = (float)transY1;
/* 1071 */       PlotOrientation orientation = plot.getOrientation();
/* 1072 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1073 */         x = (float)transY1;
/* 1074 */         y = (float)transX1;
/*      */       } 
/* 1076 */       if (s.isLastPointGood()) {
/* 1077 */         s.seriesPath.lineTo(x, y);
/*      */       } else {
/*      */         
/* 1080 */         s.seriesPath.moveTo(x, y);
/*      */       } 
/* 1082 */       s.setLastPointGood(true);
/*      */     } else {
/*      */       
/* 1085 */       s.setLastPointGood(false);
/*      */     } 
/*      */     
/* 1088 */     if (item == s.getLastItemIndex())
/*      */     {
/* 1090 */       drawFirstPassShape(g2, pass, series, item, s.seriesPath);
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
/*      */   protected void drawSecondaryPass(Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, Rectangle2D dataArea, ValueAxis rangeAxis, CrosshairState crosshairState, EntityCollection entities) {
/* 1118 */     Shape entityArea = null;
/*      */ 
/*      */     
/* 1121 */     double x1 = dataset.getXValue(series, item);
/* 1122 */     double y1 = dataset.getYValue(series, item);
/* 1123 */     if (Double.isNaN(y1) || Double.isNaN(x1)) {
/*      */       return;
/*      */     }
/*      */     
/* 1127 */     PlotOrientation orientation = plot.getOrientation();
/* 1128 */     RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
/* 1129 */     RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
/* 1130 */     double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
/* 1131 */     double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
/*      */     
/* 1133 */     if (getItemShapeVisible(series, item)) {
/* 1134 */       Shape shape = getItemShape(series, item);
/* 1135 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 1136 */         shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
/*      */       
/*      */       }
/* 1139 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 1140 */         shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
/*      */       } 
/*      */       
/* 1143 */       entityArea = shape;
/* 1144 */       if (shape.intersects(dataArea)) {
/* 1145 */         if (getItemShapeFilled(series, item)) {
/* 1146 */           if (this.useFillPaint) {
/* 1147 */             g2.setPaint(getItemFillPaint(series, item));
/*      */           } else {
/*      */             
/* 1150 */             g2.setPaint(getItemPaint(series, item));
/*      */           } 
/* 1152 */           g2.fill(shape);
/*      */         } 
/* 1154 */         if (this.drawOutlines) {
/* 1155 */           if (getUseOutlinePaint()) {
/* 1156 */             g2.setPaint(getItemOutlinePaint(series, item));
/*      */           } else {
/*      */             
/* 1159 */             g2.setPaint(getItemPaint(series, item));
/*      */           } 
/* 1161 */           g2.setStroke(getItemOutlineStroke(series, item));
/* 1162 */           g2.draw(shape);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1167 */     double xx = transX1;
/* 1168 */     double yy = transY1;
/* 1169 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 1170 */       xx = transY1;
/* 1171 */       yy = transX1;
/*      */     } 
/*      */ 
/*      */     
/* 1175 */     if (isItemLabelVisible(series, item)) {
/* 1176 */       drawItemLabel(g2, orientation, dataset, series, item, xx, yy, (y1 < 0.0D));
/*      */     }
/*      */ 
/*      */     
/* 1180 */     int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
/* 1181 */     int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
/* 1182 */     updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1187 */     if (entities != null && isPointInRect(dataArea, xx, yy)) {
/* 1188 */       addEntity(entities, entityArea, dataset, series, item, xx, yy);
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
/*      */   
/*      */   public LegendItem getLegendItem(int datasetIndex, int series) {
/* 1203 */     XYPlot plot = getPlot();
/* 1204 */     if (plot == null) {
/* 1205 */       return null;
/*      */     }
/*      */     
/* 1208 */     XYDataset dataset = plot.getDataset(datasetIndex);
/* 1209 */     if (dataset == null) {
/* 1210 */       return null;
/*      */     }
/*      */     
/* 1213 */     if (!getItemVisible(series, 0)) {
/* 1214 */       return null;
/*      */     }
/* 1216 */     String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
/*      */     
/* 1218 */     String description = label;
/* 1219 */     String toolTipText = null;
/* 1220 */     if (getLegendItemToolTipGenerator() != null) {
/* 1221 */       toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/* 1224 */     String urlText = null;
/* 1225 */     if (getLegendItemURLGenerator() != null) {
/* 1226 */       urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
/*      */     }
/*      */     
/* 1229 */     boolean shapeIsVisible = getItemShapeVisible(series, 0);
/* 1230 */     Shape shape = lookupLegendShape(series);
/* 1231 */     boolean shapeIsFilled = getItemShapeFilled(series, 0);
/*      */     
/* 1233 */     Paint fillPaint = this.useFillPaint ? lookupSeriesFillPaint(series) : lookupSeriesPaint(series);
/* 1234 */     boolean shapeOutlineVisible = this.drawOutlines;
/*      */     
/* 1236 */     Paint outlinePaint = this.useOutlinePaint ? lookupSeriesOutlinePaint(series) : lookupSeriesPaint(series);
/* 1237 */     Stroke outlineStroke = lookupSeriesOutlineStroke(series);
/* 1238 */     boolean lineVisible = getItemLineVisible(series, 0);
/* 1239 */     Stroke lineStroke = lookupSeriesStroke(series);
/* 1240 */     Paint linePaint = lookupSeriesPaint(series);
/* 1241 */     LegendItem result = new LegendItem(label, description, toolTipText, urlText, shapeIsVisible, shape, shapeIsFilled, fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, lineVisible, this.legendLine, lineStroke, linePaint);
/*      */ 
/*      */ 
/*      */     
/* 1245 */     result.setLabelFont(lookupLegendTextFont(series));
/* 1246 */     Paint labelPaint = lookupLegendTextPaint(series);
/* 1247 */     if (labelPaint != null) {
/* 1248 */       result.setLabelPaint(labelPaint);
/*      */     }
/* 1250 */     result.setSeriesKey(dataset.getSeriesKey(series));
/* 1251 */     result.setSeriesIndex(series);
/* 1252 */     result.setDataset((Dataset)dataset);
/* 1253 */     result.setDatasetIndex(datasetIndex);
/*      */     
/* 1255 */     return result;
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
/*      */   public Object clone() throws CloneNotSupportedException {
/* 1267 */     XYLineAndShapeRenderer clone = (XYLineAndShapeRenderer)super.clone();
/* 1268 */     clone
/* 1269 */       .seriesLinesVisible = (BooleanList)this.seriesLinesVisible.clone();
/* 1270 */     if (this.legendLine != null) {
/* 1271 */       clone.legendLine = ShapeUtilities.clone(this.legendLine);
/*      */     }
/* 1273 */     clone
/* 1274 */       .seriesShapesVisible = (BooleanList)this.seriesShapesVisible.clone();
/* 1275 */     clone
/* 1276 */       .seriesShapesFilled = (BooleanList)this.seriesShapesFilled.clone();
/* 1277 */     return clone;
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
/* 1289 */     if (obj == this) {
/* 1290 */       return true;
/*      */     }
/* 1292 */     if (!(obj instanceof XYLineAndShapeRenderer)) {
/* 1293 */       return false;
/*      */     }
/* 1295 */     if (!super.equals(obj)) {
/* 1296 */       return false;
/*      */     }
/* 1298 */     XYLineAndShapeRenderer that = (XYLineAndShapeRenderer)obj;
/* 1299 */     if (!ObjectUtilities.equal(this.linesVisible, that.linesVisible)) {
/* 1300 */       return false;
/*      */     }
/* 1302 */     if (!ObjectUtilities.equal(this.seriesLinesVisible, that.seriesLinesVisible))
/*      */     {
/*      */       
/* 1305 */       return false;
/*      */     }
/* 1307 */     if (this.baseLinesVisible != that.baseLinesVisible) {
/* 1308 */       return false;
/*      */     }
/* 1310 */     if (!ShapeUtilities.equal(this.legendLine, that.legendLine)) {
/* 1311 */       return false;
/*      */     }
/* 1313 */     if (!ObjectUtilities.equal(this.shapesVisible, that.shapesVisible)) {
/* 1314 */       return false;
/*      */     }
/* 1316 */     if (!ObjectUtilities.equal(this.seriesShapesVisible, that.seriesShapesVisible))
/*      */     {
/*      */       
/* 1319 */       return false;
/*      */     }
/* 1321 */     if (this.baseShapesVisible != that.baseShapesVisible) {
/* 1322 */       return false;
/*      */     }
/* 1324 */     if (!ObjectUtilities.equal(this.shapesFilled, that.shapesFilled)) {
/* 1325 */       return false;
/*      */     }
/* 1327 */     if (!ObjectUtilities.equal(this.seriesShapesFilled, that.seriesShapesFilled))
/*      */     {
/*      */       
/* 1330 */       return false;
/*      */     }
/* 1332 */     if (this.baseShapesFilled != that.baseShapesFilled) {
/* 1333 */       return false;
/*      */     }
/* 1335 */     if (this.drawOutlines != that.drawOutlines) {
/* 1336 */       return false;
/*      */     }
/* 1338 */     if (this.useOutlinePaint != that.useOutlinePaint) {
/* 1339 */       return false;
/*      */     }
/* 1341 */     if (this.useFillPaint != that.useFillPaint) {
/* 1342 */       return false;
/*      */     }
/* 1344 */     if (this.drawSeriesLineAsPath != that.drawSeriesLineAsPath) {
/* 1345 */       return false;
/*      */     }
/* 1347 */     return true;
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
/*      */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 1360 */     stream.defaultReadObject();
/* 1361 */     this.legendLine = SerialUtilities.readShape(stream);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 1372 */     stream.defaultWriteObject();
/* 1373 */     SerialUtilities.writeShape(this.legendLine, stream);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/xy/XYLineAndShapeRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
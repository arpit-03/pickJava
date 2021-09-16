/*     */ package org.jfree.chart.renderer.category;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.KeyToGroupMap;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.ui.RectangleEdge;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GroupedStackedBarRenderer
/*     */   extends StackedBarRenderer
/*     */   implements Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2725921399005922939L;
/*  94 */   private KeyToGroupMap seriesToGroupMap = new KeyToGroupMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeriesToGroupMap(KeyToGroupMap map) {
/* 104 */     ParamChecks.nullNotPermitted(map, "map");
/* 105 */     this.seriesToGroupMap = map;
/* 106 */     fireChangeEvent();
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
/*     */   public Range findRangeBounds(CategoryDataset dataset) {
/* 120 */     if (dataset == null) {
/* 121 */       return null;
/*     */     }
/* 123 */     Range r = DatasetUtilities.findStackedRangeBounds(dataset, this.seriesToGroupMap);
/*     */     
/* 125 */     return r;
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
/*     */   protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state) {
/* 143 */     CategoryAxis xAxis = plot.getDomainAxisForDataset(rendererIndex);
/* 144 */     CategoryDataset data = plot.getDataset(rendererIndex);
/* 145 */     if (data != null) {
/* 146 */       PlotOrientation orientation = plot.getOrientation();
/* 147 */       double space = 0.0D;
/* 148 */       if (orientation == PlotOrientation.HORIZONTAL) {
/* 149 */         space = dataArea.getHeight();
/*     */       }
/* 151 */       else if (orientation == PlotOrientation.VERTICAL) {
/* 152 */         space = dataArea.getWidth();
/*     */       } 
/* 154 */       double maxWidth = space * getMaximumBarWidth();
/* 155 */       int groups = this.seriesToGroupMap.getGroupCount();
/* 156 */       int categories = data.getColumnCount();
/* 157 */       int columns = groups * categories;
/* 158 */       double categoryMargin = 0.0D;
/* 159 */       double itemMargin = 0.0D;
/* 160 */       if (categories > 1) {
/* 161 */         categoryMargin = xAxis.getCategoryMargin();
/*     */       }
/* 163 */       if (groups > 1) {
/* 164 */         itemMargin = getItemMargin();
/*     */       }
/*     */ 
/*     */       
/* 168 */       double used = space * (1.0D - xAxis.getLowerMargin() - xAxis.getUpperMargin() - categoryMargin - itemMargin);
/*     */       
/* 170 */       if (columns > 0) {
/* 171 */         state.setBarWidth(Math.min(used / columns, maxWidth));
/*     */       } else {
/*     */         
/* 174 */         state.setBarWidth(Math.min(used, maxWidth));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double calculateBarW0(CategoryPlot plot, PlotOrientation orientation, Rectangle2D dataArea, CategoryAxis domainAxis, CategoryItemRendererState state, int row, int column) {
/*     */     double space;
/* 202 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 203 */       space = dataArea.getHeight();
/*     */     } else {
/*     */       
/* 206 */       space = dataArea.getWidth();
/*     */     } 
/* 208 */     double barW0 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot
/* 209 */         .getDomainAxisEdge());
/* 210 */     int groupCount = this.seriesToGroupMap.getGroupCount();
/* 211 */     int groupIndex = this.seriesToGroupMap.getGroupIndex(this.seriesToGroupMap
/* 212 */         .getGroup(plot.getDataset(plot
/* 213 */             .getIndexOf(this)).getRowKey(row)));
/* 214 */     int categoryCount = getColumnCount();
/* 215 */     if (groupCount > 1) {
/* 216 */       double groupGap = space * getItemMargin() / (categoryCount * (groupCount - 1));
/*     */       
/* 218 */       double groupW = calculateSeriesWidth(space, domainAxis, categoryCount, groupCount);
/*     */ 
/*     */       
/* 221 */       barW0 = barW0 + groupIndex * (groupW + groupGap) + groupW / 2.0D - state.getBarWidth() / 2.0D;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 226 */       barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
/*     */     } 
/* 228 */     return barW0;
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
/*     */   public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass) {
/*     */     double translatedBase, translatedValue;
/*     */     RectangleEdge barBase;
/*     */     Rectangle2D bar;
/* 252 */     Number dataValue = dataset.getValue(row, column);
/* 253 */     if (dataValue == null) {
/*     */       return;
/*     */     }
/*     */     
/* 257 */     double value = dataValue.doubleValue();
/* 258 */     Comparable group = this.seriesToGroupMap.getGroup(dataset
/* 259 */         .getRowKey(row));
/* 260 */     PlotOrientation orientation = plot.getOrientation();
/* 261 */     double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, row, column);
/*     */ 
/*     */     
/* 264 */     double positiveBase = 0.0D;
/* 265 */     double negativeBase = 0.0D;
/*     */     
/* 267 */     for (int i = 0; i < row; i++) {
/* 268 */       if (group.equals(this.seriesToGroupMap.getGroup(dataset
/* 269 */             .getRowKey(i)))) {
/* 270 */         Number v = dataset.getValue(i, column);
/* 271 */         if (v != null) {
/* 272 */           double d = v.doubleValue();
/* 273 */           if (d > 0.0D) {
/* 274 */             positiveBase += d;
/*     */           } else {
/*     */             
/* 277 */             negativeBase += d;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 285 */     boolean positive = (value > 0.0D);
/* 286 */     boolean inverted = rangeAxis.isInverted();
/*     */     
/* 288 */     if (orientation == PlotOrientation.HORIZONTAL) {
/* 289 */       if ((positive && inverted) || (!positive && !inverted)) {
/* 290 */         barBase = RectangleEdge.RIGHT;
/*     */       } else {
/*     */         
/* 293 */         barBase = RectangleEdge.LEFT;
/*     */       }
/*     */     
/*     */     }
/* 297 */     else if ((positive && !inverted) || (!positive && inverted)) {
/* 298 */       barBase = RectangleEdge.BOTTOM;
/*     */     } else {
/*     */       
/* 301 */       barBase = RectangleEdge.TOP;
/*     */     } 
/*     */     
/* 304 */     RectangleEdge location = plot.getRangeAxisEdge();
/* 305 */     if (value > 0.0D) {
/* 306 */       translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea, location);
/*     */       
/* 308 */       translatedValue = rangeAxis.valueToJava2D(positiveBase + value, dataArea, location);
/*     */     }
/*     */     else {
/*     */       
/* 312 */       translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea, location);
/*     */       
/* 314 */       translatedValue = rangeAxis.valueToJava2D(negativeBase + value, dataArea, location);
/*     */     } 
/*     */     
/* 317 */     double barL0 = Math.min(translatedBase, translatedValue);
/* 318 */     double barLength = Math.max(Math.abs(translatedValue - translatedBase), 
/* 319 */         getMinimumBarLength());
/*     */ 
/*     */     
/* 322 */     if (orientation == PlotOrientation.HORIZONTAL) {
/*     */       
/* 324 */       bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
/*     */     } else {
/*     */       
/* 327 */       bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);
/*     */     } 
/*     */     
/* 330 */     getBarPainter().paintBar(g2, this, row, column, bar, barBase);
/*     */     
/* 332 */     CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
/*     */     
/* 334 */     if (generator != null && isItemLabelVisible(row, column)) {
/* 335 */       drawItemLabel(g2, dataset, row, column, plot, generator, bar, (value < 0.0D));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 340 */     if (state.getInfo() != null) {
/* 341 */       EntityCollection entities = state.getEntityCollection();
/* 342 */       if (entities != null) {
/* 343 */         addItemEntity(entities, dataset, row, column, bar);
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
/*     */   public boolean equals(Object obj) {
/* 358 */     if (obj == this) {
/* 359 */       return true;
/*     */     }
/* 361 */     if (!(obj instanceof GroupedStackedBarRenderer)) {
/* 362 */       return false;
/*     */     }
/* 364 */     GroupedStackedBarRenderer that = (GroupedStackedBarRenderer)obj;
/* 365 */     if (!this.seriesToGroupMap.equals(that.seriesToGroupMap)) {
/* 366 */       return false;
/*     */     }
/* 368 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/renderer/category/GroupedStackedBarRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.plot.ColorPalette;
/*     */ import org.jfree.chart.plot.ContourPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.RainbowPalette;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorBar
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2101776212647268103L;
/*     */   public static final int DEFAULT_COLORBAR_THICKNESS = 0;
/*     */   public static final double DEFAULT_COLORBAR_THICKNESS_PERCENT = 0.1D;
/*     */   public static final int DEFAULT_OUTERGAP = 2;
/*     */   private ValueAxis axis;
/*  97 */   private int colorBarThickness = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private double colorBarThicknessPercent = 0.1D;
/*     */ 
/*     */ 
/*     */   
/* 106 */   private ColorPalette colorPalette = null;
/*     */ 
/*     */   
/* 109 */   private int colorBarLength = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int outerGap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorBar(String label) {
/* 122 */     NumberAxis a = new NumberAxis(label);
/* 123 */     a.setAutoRangeIncludesZero(false);
/* 124 */     this.axis = a;
/* 125 */     this.axis.setLowerMargin(0.0D);
/* 126 */     this.axis.setUpperMargin(0.0D);
/*     */     
/* 128 */     this.colorPalette = (ColorPalette)new RainbowPalette();
/* 129 */     this.colorBarThickness = 0;
/* 130 */     this.colorBarThicknessPercent = 0.1D;
/* 131 */     this.outerGap = 2;
/* 132 */     this.colorPalette.setMinZ(this.axis.getRange().getLowerBound());
/* 133 */     this.colorPalette.setMaxZ(this.axis.getRange().getUpperBound());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configure(ContourPlot plot) {
/* 143 */     double minZ = plot.getDataset().getMinZValue();
/* 144 */     double maxZ = plot.getDataset().getMaxZValue();
/* 145 */     setMinimumValue(minZ);
/* 146 */     setMaximumValue(maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValueAxis getAxis() {
/* 155 */     return this.axis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAxis(ValueAxis axis) {
/* 164 */     this.axis = axis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoAdjustRange() {
/* 171 */     this.axis.autoAdjustRange();
/* 172 */     this.colorPalette.setMinZ(this.axis.getLowerBound());
/* 173 */     this.colorPalette.setMaxZ(this.axis.getUpperBound());
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
/*     */   public double draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, Rectangle2D reservedArea, RectangleEdge edge) {
/*     */     double d1;
/* 194 */     Rectangle2D colorBarArea = null;
/*     */     
/* 196 */     double thickness = calculateBarThickness(dataArea, edge);
/* 197 */     if (this.colorBarThickness > 0) {
/* 198 */       thickness = this.colorBarThickness;
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (RectangleEdge.isLeftOrRight(edge)) {
/* 203 */       d1 = dataArea.getHeight();
/*     */     } else {
/*     */       
/* 206 */       d1 = dataArea.getWidth();
/*     */     } 
/*     */     
/* 209 */     if (this.colorBarLength > 0) {
/* 210 */       d1 = this.colorBarLength;
/*     */     }
/*     */     
/* 213 */     if (edge == RectangleEdge.BOTTOM) {
/*     */       
/* 215 */       colorBarArea = new Rectangle2D.Double(dataArea.getX(), plotArea.getMaxY() + this.outerGap, d1, thickness);
/*     */     }
/* 217 */     else if (edge == RectangleEdge.TOP) {
/*     */       
/* 219 */       colorBarArea = new Rectangle2D.Double(dataArea.getX(), reservedArea.getMinY() + this.outerGap, d1, thickness);
/*     */     }
/* 221 */     else if (edge == RectangleEdge.LEFT) {
/*     */       
/* 223 */       colorBarArea = new Rectangle2D.Double(plotArea.getX() - thickness - this.outerGap, dataArea.getMinY(), thickness, d1);
/*     */     }
/* 225 */     else if (edge == RectangleEdge.RIGHT) {
/*     */       
/* 227 */       colorBarArea = new Rectangle2D.Double(plotArea.getMaxX() + this.outerGap, dataArea.getMinY(), thickness, d1);
/*     */     } 
/*     */ 
/*     */     
/* 231 */     this.axis.refreshTicks(g2, new AxisState(), colorBarArea, edge);
/*     */     
/* 233 */     drawColorBar(g2, colorBarArea, edge);
/*     */     
/* 235 */     AxisState state = null;
/* 236 */     assert colorBarArea != null;
/* 237 */     if (edge == RectangleEdge.TOP) {
/* 238 */       cursor = colorBarArea.getMinY();
/* 239 */       state = this.axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.TOP, null);
/*     */     
/*     */     }
/* 242 */     else if (edge == RectangleEdge.BOTTOM) {
/* 243 */       cursor = colorBarArea.getMaxY();
/* 244 */       state = this.axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.BOTTOM, null);
/*     */     
/*     */     }
/* 247 */     else if (edge == RectangleEdge.LEFT) {
/* 248 */       cursor = colorBarArea.getMinX();
/* 249 */       state = this.axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.LEFT, null);
/*     */     
/*     */     }
/* 252 */     else if (edge == RectangleEdge.RIGHT) {
/* 253 */       cursor = colorBarArea.getMaxX();
/* 254 */       state = this.axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.RIGHT, null);
/*     */     } 
/*     */     
/* 257 */     assert state != null;
/* 258 */     return state.getCursor();
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
/*     */   public void drawColorBar(Graphics2D g2, Rectangle2D colorBarArea, RectangleEdge edge) {
/* 273 */     Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
/* 274 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     Stroke strokeSaved = g2.getStroke();
/* 281 */     g2.setStroke(new BasicStroke(1.0F));
/*     */     
/* 283 */     if (RectangleEdge.isTopOrBottom(edge)) {
/* 284 */       double y1 = colorBarArea.getY();
/* 285 */       double y2 = colorBarArea.getMaxY();
/* 286 */       double xx = colorBarArea.getX();
/* 287 */       Line2D line = new Line2D.Double();
/* 288 */       while (xx <= colorBarArea.getMaxX()) {
/* 289 */         double value = this.axis.java2DToValue(xx, colorBarArea, edge);
/* 290 */         line.setLine(xx, y1, xx, y2);
/* 291 */         g2.setPaint(getPaint(value));
/* 292 */         g2.draw(line);
/* 293 */         xx++;
/*     */       } 
/*     */     } else {
/*     */       
/* 297 */       double y1 = colorBarArea.getX();
/* 298 */       double y2 = colorBarArea.getMaxX();
/* 299 */       double xx = colorBarArea.getY();
/* 300 */       Line2D line = new Line2D.Double();
/* 301 */       while (xx <= colorBarArea.getMaxY()) {
/* 302 */         double value = this.axis.java2DToValue(xx, colorBarArea, edge);
/* 303 */         line.setLine(y1, xx, y2, xx);
/* 304 */         g2.setPaint(getPaint(value));
/* 305 */         g2.draw(line);
/* 306 */         xx++;
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
/* 311 */     g2.setStroke(strokeSaved);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorPalette getColorPalette() {
/* 321 */     return this.colorPalette;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getPaint(double value) {
/* 332 */     return this.colorPalette.getPaint(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorPalette(ColorPalette palette) {
/* 341 */     this.colorPalette = palette;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaximumValue(double value) {
/* 350 */     this.colorPalette.setMaxZ(value);
/* 351 */     this.axis.setUpperBound(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinimumValue(double value) {
/* 360 */     this.colorPalette.setMinZ(value);
/* 361 */     this.axis.setLowerBound(value);
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
/*     */   public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisSpace space) {
/* 381 */     AxisSpace result = this.axis.reserveSpace(g2, plot, plotArea, edge, space);
/*     */     
/* 383 */     double thickness = calculateBarThickness(dataArea, edge);
/* 384 */     result.add(thickness + (2 * this.outerGap), edge);
/* 385 */     return result;
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
/*     */   private double calculateBarThickness(Rectangle2D plotArea, RectangleEdge edge) {
/*     */     double result;
/* 400 */     if (RectangleEdge.isLeftOrRight(edge)) {
/* 401 */       result = plotArea.getWidth() * this.colorBarThicknessPercent;
/*     */     } else {
/*     */       
/* 404 */       result = plotArea.getHeight() * this.colorBarThicknessPercent;
/*     */     } 
/* 406 */     return result;
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
/* 419 */     ColorBar clone = (ColorBar)super.clone();
/* 420 */     clone.axis = (ValueAxis)this.axis.clone();
/* 421 */     return clone;
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
/* 433 */     if (obj == this) {
/* 434 */       return true;
/*     */     }
/* 436 */     if (!(obj instanceof ColorBar)) {
/* 437 */       return false;
/*     */     }
/* 439 */     ColorBar that = (ColorBar)obj;
/* 440 */     if (!this.axis.equals(that.axis)) {
/* 441 */       return false;
/*     */     }
/* 443 */     if (this.colorBarThickness != that.colorBarThickness) {
/* 444 */       return false;
/*     */     }
/* 446 */     if (this.colorBarThicknessPercent != that.colorBarThicknessPercent) {
/* 447 */       return false;
/*     */     }
/* 449 */     if (!this.colorPalette.equals(that.colorPalette)) {
/* 450 */       return false;
/*     */     }
/* 452 */     if (this.colorBarLength != that.colorBarLength) {
/* 453 */       return false;
/*     */     }
/* 455 */     if (this.outerGap != that.outerGap) {
/* 456 */       return false;
/*     */     }
/* 458 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 468 */     return this.axis.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/ColorBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
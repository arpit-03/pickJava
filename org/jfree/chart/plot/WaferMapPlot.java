/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ResourceBundle;
/*     */ import org.jfree.chart.LegendItemCollection;
/*     */ import org.jfree.chart.event.RendererChangeEvent;
/*     */ import org.jfree.chart.event.RendererChangeListener;
/*     */ import org.jfree.chart.renderer.WaferMapRenderer;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.data.general.Dataset;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.WaferMapDataset;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaferMapPlot
/*     */   extends Plot
/*     */   implements RendererChangeListener, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4668320403707308155L;
/*  83 */   public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
/*     */ 
/*     */   
/*     */   public static final boolean DEFAULT_CROSSHAIR_VISIBLE = false;
/*     */ 
/*     */   
/*  97 */   public static final Stroke DEFAULT_CROSSHAIR_STROKE = DEFAULT_GRIDLINE_STROKE;
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static final Paint DEFAULT_CROSSHAIR_PAINT = Color.blue;
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlotOrientation orientation;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WaferMapDataset dataset;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WaferMapRenderer renderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaferMapPlot() {
/* 127 */     this((WaferMapDataset)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaferMapPlot(WaferMapDataset dataset) {
/* 136 */     this(dataset, (WaferMapRenderer)null);
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
/*     */   public WaferMapPlot(WaferMapDataset dataset, WaferMapRenderer renderer) {
/* 149 */     this.orientation = PlotOrientation.VERTICAL;
/*     */     
/* 151 */     this.dataset = dataset;
/* 152 */     if (dataset != null) {
/* 153 */       dataset.addChangeListener(this);
/*     */     }
/*     */     
/* 156 */     this.renderer = renderer;
/* 157 */     if (renderer != null) {
/* 158 */       renderer.setPlot(this);
/* 159 */       renderer.addChangeListener(this);
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
/*     */   public String getPlotType() {
/* 171 */     return "WMAP_Plot";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WaferMapDataset getDataset() {
/* 180 */     return this.dataset;
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
/*     */   public void setDataset(WaferMapDataset dataset) {
/* 192 */     if (this.dataset != null) {
/* 193 */       this.dataset.removeChangeListener(this);
/*     */     }
/*     */ 
/*     */     
/* 197 */     this.dataset = dataset;
/* 198 */     if (dataset != null) {
/* 199 */       setDatasetGroup(dataset.getGroup());
/* 200 */       dataset.addChangeListener(this);
/*     */     } 
/*     */ 
/*     */     
/* 204 */     datasetChanged(new DatasetChangeEvent(this, (Dataset)dataset));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderer(WaferMapRenderer renderer) {
/* 215 */     if (this.renderer != null) {
/* 216 */       this.renderer.removeChangeListener(this);
/*     */     }
/* 218 */     this.renderer = renderer;
/* 219 */     if (renderer != null) {
/* 220 */       renderer.setPlot(this);
/*     */     }
/* 222 */     fireChangeEvent();
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
/*     */   public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState state, PlotRenderingInfo info) {
/* 240 */     boolean b1 = (area.getWidth() <= 10.0D);
/* 241 */     boolean b2 = (area.getHeight() <= 10.0D);
/* 242 */     if (b1 || b2) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 247 */     if (info != null) {
/* 248 */       info.setPlotArea(area);
/*     */     }
/*     */ 
/*     */     
/* 252 */     RectangleInsets insets = getInsets();
/* 253 */     insets.trim(area);
/*     */     
/* 255 */     drawChipGrid(g2, area);
/* 256 */     drawWaferEdge(g2, area);
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
/*     */   protected void drawChipGrid(Graphics2D g2, Rectangle2D plotArea) {
/* 268 */     Shape savedClip = g2.getClip();
/* 269 */     g2.setClip(getWaferEdge(plotArea));
/* 270 */     Rectangle2D chip = new Rectangle2D.Double();
/* 271 */     int xchips = 35;
/* 272 */     int ychips = 20;
/* 273 */     double space = 1.0D;
/* 274 */     if (this.dataset != null) {
/* 275 */       xchips = this.dataset.getMaxChipX() + 2;
/* 276 */       ychips = this.dataset.getMaxChipY() + 2;
/* 277 */       space = this.dataset.getChipSpace();
/*     */     } 
/* 279 */     double startX = plotArea.getX();
/* 280 */     double startY = plotArea.getY();
/* 281 */     double chipWidth = 1.0D;
/* 282 */     double chipHeight = 1.0D;
/* 283 */     if (plotArea.getWidth() != plotArea.getHeight()) {
/*     */       double major; double minor;
/* 285 */       if (plotArea.getWidth() > plotArea.getHeight()) {
/* 286 */         major = plotArea.getWidth();
/* 287 */         minor = plotArea.getHeight();
/*     */       } else {
/*     */         
/* 290 */         major = plotArea.getHeight();
/* 291 */         minor = plotArea.getWidth();
/*     */       } 
/*     */       
/* 294 */       if (plotArea.getWidth() == minor) {
/* 295 */         startY += (major - minor) / 2.0D;
/* 296 */         chipWidth = (plotArea.getWidth() - space * xchips - 1.0D) / xchips;
/*     */         
/* 298 */         chipHeight = (plotArea.getWidth() - space * ychips - 1.0D) / ychips;
/*     */       }
/*     */       else {
/*     */         
/* 302 */         startX += (major - minor) / 2.0D;
/* 303 */         chipWidth = (plotArea.getHeight() - space * xchips - 1.0D) / xchips;
/*     */         
/* 305 */         chipHeight = (plotArea.getHeight() - space * ychips - 1.0D) / ychips;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 310 */     for (int x = 1; x <= xchips; x++) {
/* 311 */       double upperLeftX = startX - chipWidth + chipWidth * x + space * (x - 1);
/*     */       
/* 313 */       for (int y = 1; y <= ychips; y++) {
/* 314 */         double upperLeftY = startY - chipHeight + chipHeight * y + space * (y - 1);
/*     */         
/* 316 */         chip.setFrame(upperLeftX, upperLeftY, chipWidth, chipHeight);
/* 317 */         g2.setColor(Color.white);
/* 318 */         if (this.dataset.getChipValue(x - 1, ychips - y - 1) != null) {
/* 319 */           g2.setPaint(this.renderer
/* 320 */               .getChipColor(this.dataset
/* 321 */                 .getChipValue(x - 1, ychips - y - 1)));
/*     */         }
/*     */ 
/*     */         
/* 325 */         g2.fill(chip);
/* 326 */         g2.setColor(Color.lightGray);
/* 327 */         g2.draw(chip);
/*     */       } 
/*     */     } 
/* 330 */     g2.setClip(savedClip);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Ellipse2D getWaferEdge(Rectangle2D plotArea) {
/* 341 */     Ellipse2D edge = new Ellipse2D.Double();
/* 342 */     double diameter = plotArea.getWidth();
/* 343 */     double upperLeftX = plotArea.getX();
/* 344 */     double upperLeftY = plotArea.getY();
/*     */     
/* 346 */     if (plotArea.getWidth() != plotArea.getHeight()) {
/*     */       double major, minor;
/* 348 */       if (plotArea.getWidth() > plotArea.getHeight()) {
/* 349 */         major = plotArea.getWidth();
/* 350 */         minor = plotArea.getHeight();
/*     */       } else {
/*     */         
/* 353 */         major = plotArea.getHeight();
/* 354 */         minor = plotArea.getWidth();
/*     */       } 
/*     */       
/* 357 */       diameter = minor;
/*     */       
/* 359 */       if (plotArea.getWidth() == minor) {
/* 360 */         upperLeftY = plotArea.getY() + (major - minor) / 2.0D;
/*     */       } else {
/*     */         
/* 363 */         upperLeftX = plotArea.getX() + (major - minor) / 2.0D;
/*     */       } 
/*     */     } 
/* 366 */     edge.setFrame(upperLeftX, upperLeftY, diameter, diameter);
/* 367 */     return edge;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawWaferEdge(Graphics2D g2, Rectangle2D plotArea) {
/*     */     Arc2D notch;
/* 378 */     Ellipse2D waferEdge = getWaferEdge(plotArea);
/* 379 */     g2.setColor(Color.black);
/* 380 */     g2.draw(waferEdge);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     Rectangle2D waferFrame = waferEdge.getFrame();
/* 386 */     double notchDiameter = waferFrame.getWidth() * 0.04D;
/* 387 */     if (this.orientation == PlotOrientation.HORIZONTAL) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 392 */       Rectangle2D notchFrame = new Rectangle2D.Double(waferFrame.getX() + waferFrame.getWidth() - notchDiameter / 2.0D, waferFrame.getY() + waferFrame.getHeight() / 2.0D - notchDiameter / 2.0D, notchDiameter, notchDiameter);
/*     */ 
/*     */       
/* 395 */       notch = new Arc2D.Double(notchFrame, 90.0D, 180.0D, 0);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 402 */       Rectangle2D notchFrame = new Rectangle2D.Double(waferFrame.getX() + waferFrame.getWidth() / 2.0D - notchDiameter / 2.0D, waferFrame.getY() + waferFrame.getHeight() - notchDiameter / 2.0D, notchDiameter, notchDiameter);
/*     */ 
/*     */       
/* 405 */       notch = new Arc2D.Double(notchFrame, 0.0D, 180.0D, 0);
/*     */     } 
/* 407 */     g2.setColor(Color.white);
/* 408 */     g2.fill(notch);
/* 409 */     g2.setColor(Color.black);
/* 410 */     g2.draw(notch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LegendItemCollection getLegendItems() {
/* 421 */     return this.renderer.getLegendCollection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rendererChanged(RendererChangeEvent event) {
/* 431 */     fireChangeEvent();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/WaferMapPlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.CategoryAxis;
/*     */ import org.jfree.chart.axis.ColorBar;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.CategoryPlot;
/*     */ import org.jfree.chart.plot.ContourPlot;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.PolarPlot;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*     */ import org.jfree.chart.renderer.category.LineAndShapeRenderer;
/*     */ import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ import org.jfree.ui.PaintSample;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.StrokeChooserPanel;
/*     */ import org.jfree.ui.StrokeSample;
/*     */ import org.jfree.util.BooleanUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultPlotEditor
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*  94 */   private static final String[] orientationNames = new String[] { "Vertical", "Horizontal" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int ORIENTATION_VERTICAL = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int ORIENTATION_HORIZONTAL = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   private PaintSample backgroundPaintSample;
/*     */ 
/*     */ 
/*     */   
/*     */   private StrokeSample outlineStrokeSample;
/*     */ 
/*     */ 
/*     */   
/*     */   private PaintSample outlinePaintSample;
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultAxisEditor domainAxisPropertyPanel;
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultAxisEditor rangeAxisPropertyPanel;
/*     */ 
/*     */ 
/*     */   
/*     */   private DefaultColorBarEditor colorBarAxisPropertyPanel;
/*     */ 
/*     */ 
/*     */   
/*     */   private StrokeSample[] availableStrokeSamples;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleInsets plotInsets;
/*     */ 
/*     */ 
/*     */   
/*     */   private PlotOrientation plotOrientation;
/*     */ 
/*     */ 
/*     */   
/*     */   private JComboBox orientationCombo;
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean drawLines;
/*     */ 
/*     */ 
/*     */   
/*     */   private JCheckBox drawLinesCheckBox;
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean drawShapes;
/*     */ 
/*     */ 
/*     */   
/*     */   private JCheckBox drawShapesCheckBox;
/*     */ 
/*     */ 
/*     */   
/* 163 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultPlotEditor(Plot plot) {
/* 178 */     JPanel panel = createPlotPanel(plot);
/* 179 */     add(panel);
/*     */   }
/*     */   
/*     */   protected JPanel createPlotPanel(Plot plot) {
/* 183 */     this.plotInsets = plot.getInsets();
/* 184 */     this.backgroundPaintSample = new PaintSample(plot.getBackgroundPaint());
/* 185 */     this.outlineStrokeSample = new StrokeSample(plot.getOutlineStroke());
/* 186 */     this.outlinePaintSample = new PaintSample(plot.getOutlinePaint());
/* 187 */     if (plot instanceof CategoryPlot) {
/* 188 */       this.plotOrientation = ((CategoryPlot)plot).getOrientation();
/*     */     }
/* 190 */     else if (plot instanceof XYPlot) {
/* 191 */       this.plotOrientation = ((XYPlot)plot).getOrientation();
/*     */     } 
/* 193 */     if (plot instanceof CategoryPlot) {
/* 194 */       CategoryItemRenderer renderer = ((CategoryPlot)plot).getRenderer();
/* 195 */       if (renderer instanceof LineAndShapeRenderer) {
/* 196 */         LineAndShapeRenderer r = (LineAndShapeRenderer)renderer;
/* 197 */         this.drawLines = BooleanUtilities.valueOf(r
/* 198 */             .getBaseLinesVisible());
/* 199 */         this.drawShapes = BooleanUtilities.valueOf(r
/* 200 */             .getBaseShapesVisible());
/*     */       }
/*     */     
/* 203 */     } else if (plot instanceof XYPlot) {
/* 204 */       XYItemRenderer renderer = ((XYPlot)plot).getRenderer();
/* 205 */       if (renderer instanceof StandardXYItemRenderer) {
/* 206 */         StandardXYItemRenderer r = (StandardXYItemRenderer)renderer;
/* 207 */         this.drawLines = BooleanUtilities.valueOf(r.getPlotLines());
/* 208 */         this.drawShapes = BooleanUtilities.valueOf(r
/* 209 */             .getBaseShapesVisible());
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     setLayout(new BorderLayout());
/*     */     
/* 215 */     this.availableStrokeSamples = new StrokeSample[4];
/* 216 */     this.availableStrokeSamples[0] = new StrokeSample(null);
/* 217 */     this.availableStrokeSamples[1] = new StrokeSample(new BasicStroke(1.0F));
/*     */     
/* 219 */     this.availableStrokeSamples[2] = new StrokeSample(new BasicStroke(2.0F));
/*     */     
/* 221 */     this.availableStrokeSamples[3] = new StrokeSample(new BasicStroke(3.0F));
/*     */ 
/*     */ 
/*     */     
/* 225 */     JPanel panel = new JPanel(new BorderLayout());
/* 226 */     panel.setBorder(BorderFactory.createTitledBorder(
/* 227 */           BorderFactory.createEtchedBorder(), plot.getPlotType() + localizationResources
/* 228 */           .getString(":")));
/*     */     
/* 230 */     JPanel general = new JPanel(new BorderLayout());
/* 231 */     general.setBorder(BorderFactory.createTitledBorder(localizationResources
/* 232 */           .getString("General")));
/*     */     
/* 234 */     JPanel interior = new JPanel((LayoutManager)new LCBLayout(7));
/* 235 */     interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 249 */     interior.add(new JLabel(localizationResources.getString("Outline_stroke")));
/*     */     
/* 251 */     JButton button = new JButton(localizationResources.getString("Select..."));
/*     */     
/* 253 */     button.setActionCommand("OutlineStroke");
/* 254 */     button.addActionListener(this);
/* 255 */     interior.add((Component)this.outlineStrokeSample);
/* 256 */     interior.add(button);
/*     */     
/* 258 */     interior.add(new JLabel(localizationResources.getString("Outline_Paint")));
/*     */     
/* 260 */     button = new JButton(localizationResources.getString("Select..."));
/* 261 */     button.setActionCommand("OutlinePaint");
/* 262 */     button.addActionListener(this);
/* 263 */     interior.add((Component)this.outlinePaintSample);
/* 264 */     interior.add(button);
/*     */     
/* 266 */     interior.add(new JLabel(localizationResources.getString("Background_paint")));
/*     */     
/* 268 */     button = new JButton(localizationResources.getString("Select..."));
/* 269 */     button.setActionCommand("BackgroundPaint");
/* 270 */     button.addActionListener(this);
/* 271 */     interior.add((Component)this.backgroundPaintSample);
/* 272 */     interior.add(button);
/*     */     
/* 274 */     if (this.plotOrientation != null) {
/* 275 */       boolean isVertical = this.plotOrientation.equals(PlotOrientation.VERTICAL);
/*     */       
/* 277 */       int index = isVertical ? 0 : 1;
/*     */       
/* 279 */       interior.add(new JLabel(localizationResources.getString("Orientation")));
/*     */       
/* 281 */       this.orientationCombo = new JComboBox<String>(orientationNames);
/* 282 */       this.orientationCombo.setSelectedIndex(index);
/* 283 */       this.orientationCombo.setActionCommand("Orientation");
/* 284 */       this.orientationCombo.addActionListener(this);
/* 285 */       interior.add(new JPanel());
/* 286 */       interior.add(this.orientationCombo);
/*     */     } 
/*     */     
/* 289 */     if (this.drawLines != null) {
/* 290 */       interior.add(new JLabel(localizationResources.getString("Draw_lines")));
/*     */       
/* 292 */       this.drawLinesCheckBox = new JCheckBox();
/* 293 */       this.drawLinesCheckBox.setSelected(this.drawLines.booleanValue());
/* 294 */       this.drawLinesCheckBox.setActionCommand("DrawLines");
/* 295 */       this.drawLinesCheckBox.addActionListener(this);
/* 296 */       interior.add(new JPanel());
/* 297 */       interior.add(this.drawLinesCheckBox);
/*     */     } 
/*     */     
/* 300 */     if (this.drawShapes != null) {
/* 301 */       interior.add(new JLabel(localizationResources.getString("Draw_shapes")));
/*     */       
/* 303 */       this.drawShapesCheckBox = new JCheckBox();
/* 304 */       this.drawShapesCheckBox.setSelected(this.drawShapes.booleanValue());
/* 305 */       this.drawShapesCheckBox.setActionCommand("DrawShapes");
/* 306 */       this.drawShapesCheckBox.addActionListener(this);
/* 307 */       interior.add(new JPanel());
/* 308 */       interior.add(this.drawShapesCheckBox);
/*     */     } 
/*     */     
/* 311 */     general.add(interior, "North");
/*     */     
/* 313 */     JPanel appearance = new JPanel(new BorderLayout());
/* 314 */     appearance.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 315 */     appearance.add(general, "North");
/*     */     
/* 317 */     JTabbedPane tabs = createPlotTabs(plot);
/* 318 */     tabs.add(localizationResources.getString("Appearance"), appearance);
/* 319 */     panel.add(tabs);
/*     */     
/* 321 */     return panel;
/*     */   }
/*     */   
/*     */   protected JTabbedPane createPlotTabs(Plot plot) {
/*     */     ValueAxis valueAxis1, valueAxis2;
/* 326 */     JTabbedPane tabs = new JTabbedPane();
/* 327 */     tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/*     */     
/* 329 */     Axis domainAxis = null;
/* 330 */     if (plot instanceof CategoryPlot) {
/* 331 */       CategoryAxis categoryAxis = ((CategoryPlot)plot).getDomainAxis();
/*     */     }
/* 333 */     else if (plot instanceof XYPlot) {
/* 334 */       valueAxis1 = ((XYPlot)plot).getDomainAxis();
/*     */     } 
/* 336 */     this.domainAxisPropertyPanel = DefaultAxisEditor.getInstance((Axis)valueAxis1);
/*     */     
/* 338 */     if (this.domainAxisPropertyPanel != null) {
/* 339 */       this.domainAxisPropertyPanel.setBorder(
/* 340 */           BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 341 */       tabs.add(localizationResources.getString("Domain_Axis"), this.domainAxisPropertyPanel);
/*     */     } 
/*     */ 
/*     */     
/* 345 */     Axis rangeAxis = null;
/* 346 */     if (plot instanceof CategoryPlot) {
/* 347 */       valueAxis2 = ((CategoryPlot)plot).getRangeAxis();
/*     */     }
/* 349 */     else if (plot instanceof XYPlot) {
/* 350 */       valueAxis2 = ((XYPlot)plot).getRangeAxis();
/*     */     }
/* 352 */     else if (plot instanceof PolarPlot) {
/* 353 */       valueAxis2 = ((PolarPlot)plot).getAxis();
/*     */     } 
/*     */     
/* 356 */     this.rangeAxisPropertyPanel = DefaultAxisEditor.getInstance((Axis)valueAxis2);
/* 357 */     if (this.rangeAxisPropertyPanel != null) {
/* 358 */       this.rangeAxisPropertyPanel.setBorder(
/* 359 */           BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 360 */       tabs.add(localizationResources.getString("Range_Axis"), this.rangeAxisPropertyPanel);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 365 */     ColorBar colorBar = null;
/* 366 */     if (plot instanceof ContourPlot) {
/* 367 */       colorBar = ((ContourPlot)plot).getColorBar();
/*     */     }
/*     */     
/* 370 */     this.colorBarAxisPropertyPanel = DefaultColorBarEditor.getInstance(colorBar);
/*     */     
/* 372 */     if (this.colorBarAxisPropertyPanel != null) {
/* 373 */       this.colorBarAxisPropertyPanel.setBorder(
/* 374 */           BorderFactory.createEmptyBorder(2, 2, 2, 2));
/* 375 */       tabs.add(localizationResources.getString("Color_Bar"), this.colorBarAxisPropertyPanel);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 380 */     return tabs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getPlotInsets() {
/* 389 */     if (this.plotInsets == null) {
/* 390 */       this.plotInsets = new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 392 */     return this.plotInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getBackgroundPaint() {
/* 401 */     return this.backgroundPaintSample.getPaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getOutlineStroke() {
/* 410 */     return this.outlineStrokeSample.getStroke();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getOutlinePaint() {
/* 419 */     return this.outlinePaintSample.getPaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultAxisEditor getDomainAxisPropertyEditPanel() {
/* 429 */     return this.domainAxisPropertyPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultAxisEditor getRangeAxisPropertyEditPanel() {
/* 439 */     return this.rangeAxisPropertyPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 448 */     String command = event.getActionCommand();
/* 449 */     if (command.equals("BackgroundPaint")) {
/* 450 */       attemptBackgroundPaintSelection();
/*     */     }
/* 452 */     else if (command.equals("OutlineStroke")) {
/* 453 */       attemptOutlineStrokeSelection();
/*     */     }
/* 455 */     else if (command.equals("OutlinePaint")) {
/* 456 */       attemptOutlinePaintSelection();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 461 */     else if (command.equals("Orientation")) {
/* 462 */       attemptOrientationSelection();
/*     */     }
/* 464 */     else if (command.equals("DrawLines")) {
/* 465 */       attemptDrawLinesSelection();
/*     */     }
/* 467 */     else if (command.equals("DrawShapes")) {
/* 468 */       attemptDrawShapesSelection();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptBackgroundPaintSelection() {
/* 477 */     Color c = JColorChooser.showDialog(this, localizationResources.getString("Background_Color"), Color.blue);
/*     */     
/* 479 */     if (c != null) {
/* 480 */       this.backgroundPaintSample.setPaint(c);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptOutlineStrokeSelection() {
/* 488 */     StrokeChooserPanel panel = new StrokeChooserPanel(this.outlineStrokeSample, this.availableStrokeSamples);
/*     */     
/* 490 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 491 */         .getString("Stroke_Selection"), 2, -1);
/*     */ 
/*     */     
/* 494 */     if (result == 0) {
/* 495 */       this.outlineStrokeSample.setStroke(panel.getSelectedStroke());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptOutlinePaintSelection() {
/* 505 */     Color c = JColorChooser.showDialog(this, localizationResources.getString("Outline_Color"), Color.blue);
/*     */     
/* 507 */     if (c != null) {
/* 508 */       this.outlinePaintSample.setPaint(c);
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
/*     */   
/*     */   private void attemptOrientationSelection() {
/* 535 */     int index = this.orientationCombo.getSelectedIndex();
/*     */     
/* 537 */     if (index == 0) {
/* 538 */       this.plotOrientation = PlotOrientation.VERTICAL;
/*     */     } else {
/*     */       
/* 541 */       this.plotOrientation = PlotOrientation.HORIZONTAL;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptDrawLinesSelection() {
/* 551 */     this.drawLines = BooleanUtilities.valueOf(this.drawLinesCheckBox
/* 552 */         .isSelected());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptDrawShapesSelection() {
/* 560 */     this.drawShapes = BooleanUtilities.valueOf(this.drawShapesCheckBox
/* 561 */         .isSelected());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updatePlotProperties(Plot plot) {
/* 572 */     plot.setOutlinePaint(getOutlinePaint());
/* 573 */     plot.setOutlineStroke(getOutlineStroke());
/* 574 */     plot.setBackgroundPaint(getBackgroundPaint());
/* 575 */     plot.setInsets(getPlotInsets());
/*     */ 
/*     */     
/* 578 */     if (this.domainAxisPropertyPanel != null) {
/* 579 */       ValueAxis valueAxis; Axis domainAxis = null;
/* 580 */       if (plot instanceof CategoryPlot) {
/* 581 */         CategoryPlot p = (CategoryPlot)plot;
/* 582 */         CategoryAxis categoryAxis = p.getDomainAxis();
/*     */       }
/* 584 */       else if (plot instanceof XYPlot) {
/* 585 */         XYPlot p = (XYPlot)plot;
/* 586 */         valueAxis = p.getDomainAxis();
/*     */       } 
/* 588 */       if (valueAxis != null) {
/* 589 */         this.domainAxisPropertyPanel.setAxisProperties((Axis)valueAxis);
/*     */       }
/*     */     } 
/*     */     
/* 593 */     if (this.rangeAxisPropertyPanel != null) {
/* 594 */       ValueAxis valueAxis; Axis rangeAxis = null;
/* 595 */       if (plot instanceof CategoryPlot) {
/* 596 */         CategoryPlot p = (CategoryPlot)plot;
/* 597 */         valueAxis = p.getRangeAxis();
/*     */       }
/* 599 */       else if (plot instanceof XYPlot) {
/* 600 */         XYPlot p = (XYPlot)plot;
/* 601 */         valueAxis = p.getRangeAxis();
/*     */       }
/* 603 */       else if (plot instanceof PolarPlot) {
/* 604 */         PolarPlot p = (PolarPlot)plot;
/* 605 */         valueAxis = p.getAxis();
/*     */       } 
/* 607 */       if (valueAxis != null) {
/* 608 */         this.rangeAxisPropertyPanel.setAxisProperties((Axis)valueAxis);
/*     */       }
/*     */     } 
/*     */     
/* 612 */     if (this.plotOrientation != null) {
/* 613 */       if (plot instanceof CategoryPlot) {
/* 614 */         CategoryPlot p = (CategoryPlot)plot;
/* 615 */         p.setOrientation(this.plotOrientation);
/*     */       }
/* 617 */       else if (plot instanceof XYPlot) {
/* 618 */         XYPlot p = (XYPlot)plot;
/* 619 */         p.setOrientation(this.plotOrientation);
/*     */       } 
/*     */     }
/*     */     
/* 623 */     if (this.drawLines != null) {
/* 624 */       if (plot instanceof CategoryPlot) {
/* 625 */         CategoryPlot p = (CategoryPlot)plot;
/* 626 */         CategoryItemRenderer r = p.getRenderer();
/* 627 */         if (r instanceof LineAndShapeRenderer) {
/* 628 */           ((LineAndShapeRenderer)r).setLinesVisible(this.drawLines
/* 629 */               .booleanValue());
/*     */         }
/*     */       }
/* 632 */       else if (plot instanceof XYPlot) {
/* 633 */         XYPlot p = (XYPlot)plot;
/* 634 */         XYItemRenderer r = p.getRenderer();
/* 635 */         if (r instanceof StandardXYItemRenderer) {
/* 636 */           ((StandardXYItemRenderer)r).setPlotLines(this.drawLines
/* 637 */               .booleanValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 642 */     if (this.drawShapes != null) {
/* 643 */       if (plot instanceof CategoryPlot) {
/* 644 */         CategoryPlot p = (CategoryPlot)plot;
/* 645 */         CategoryItemRenderer r = p.getRenderer();
/* 646 */         if (r instanceof LineAndShapeRenderer) {
/* 647 */           ((LineAndShapeRenderer)r).setShapesVisible(this.drawShapes
/* 648 */               .booleanValue());
/*     */         }
/*     */       }
/* 651 */       else if (plot instanceof XYPlot) {
/* 652 */         XYPlot p = (XYPlot)plot;
/* 653 */         XYItemRenderer r = p.getRenderer();
/* 654 */         if (r instanceof StandardXYItemRenderer) {
/* 655 */           ((StandardXYItemRenderer)r).setBaseShapesVisible(this.drawShapes
/* 656 */               .booleanValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 662 */     if (this.colorBarAxisPropertyPanel != null) {
/* 663 */       ColorBar colorBar = null;
/* 664 */       if (plot instanceof ContourPlot) {
/* 665 */         ContourPlot p = (ContourPlot)plot;
/* 666 */         colorBar = p.getColorBar();
/*     */       } 
/* 668 */       if (colorBar != null)
/* 669 */         this.colorBarAxisPropertyPanel.setAxisProperties(colorBar); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultPlotEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
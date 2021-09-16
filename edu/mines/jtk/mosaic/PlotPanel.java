/*      */ package edu.mines.jtk.mosaic;
/*      */ 
/*      */ import edu.mines.jtk.awt.ColorMap;
/*      */ import edu.mines.jtk.awt.ColorMapListener;
/*      */ import edu.mines.jtk.awt.ColorMapped;
/*      */ import edu.mines.jtk.dsp.Sampling;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import java.awt.Color;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GridBagConstraints;
/*      */ import java.awt.GridBagLayout;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PlotPanel
/*      */   extends IPanel
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private Mosaic _mosaic;
/*      */   private ColorBar _colorBar;
/*      */   private String _colorBarFormat;
/*      */   private int _colorBarWidthMinimum;
/*      */   private Title _title;
/*      */   private Orientation _orientation;
/*      */   private AxesPlacement _axesPlacement;
/*      */   private ColorMapped _colorMapped;
/*      */   private boolean _autoColorMapped;
/*      */   private ColorMapHandler _colorMapHandler;
/*      */   
/*      */   public enum AxesPlacement
/*      */   {
/*   66 */     LEFT_TOP,
/*   67 */     LEFT_BOTTOM,
/*   68 */     NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Orientation
/*      */   {
/*   77 */     X1RIGHT_X2UP,
/*   78 */     X1DOWN_X2RIGHT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotPanel() {
/*   86 */     this(1, 1, Orientation.X1RIGHT_X2UP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotPanel(int nrow, int ncol) {
/*   96 */     this(nrow, ncol, Orientation.X1RIGHT_X2UP);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotPanel(Orientation orientation) {
/*  104 */     this(1, 1, orientation);
/*      */   }
/*      */   
/*      */   public PlotPanel(int nrow, int ncol, Orientation orientation) {
/*  108 */     this(nrow, ncol, orientation, axesPlacement(orientation));
/*      */   }
/*      */   
/*      */   private static AxesPlacement axesPlacement(Orientation orientation) {
/*      */     AxesPlacement axesPlacement;
/*  113 */     if (orientation == Orientation.X1DOWN_X2RIGHT) {
/*  114 */       axesPlacement = AxesPlacement.LEFT_TOP;
/*      */     } else {
/*  116 */       axesPlacement = AxesPlacement.LEFT_BOTTOM;
/*      */     } 
/*  118 */     return axesPlacement;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlotPanel(int nrow, int ncol, Orientation orientation, AxesPlacement axesPlacement) {
/*      */     Set<Mosaic.AxesPlacement> axesPlacementSet;
/* 1163 */     this._colorBarWidthMinimum = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1168 */     this._autoColorMapped = true; this._orientation = orientation; this._axesPlacement = axesPlacement; setLayout(new GridBagLayout()); if (axesPlacement == AxesPlacement.LEFT_TOP) { axesPlacementSet = EnumSet.of(Mosaic.AxesPlacement.LEFT, Mosaic.AxesPlacement.TOP); } else if (axesPlacement == AxesPlacement.LEFT_BOTTOM) { axesPlacementSet = EnumSet.of(Mosaic.AxesPlacement.LEFT, Mosaic.AxesPlacement.BOTTOM); } else { axesPlacementSet = EnumSet.noneOf(Mosaic.AxesPlacement.class); }  this._mosaic = new Mosaic(nrow, ncol, axesPlacementSet); this._colorMapHandler = new ColorMapHandler(); GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.gridheight = 1; gbc.weightx = 100.0D; gbc.weighty = 100.0D; gbc.fill = 1; add(this._mosaic, gbc); setPreferredSize(new Dimension(200 + 300 * ncol, 100 + 300 * nrow)); revalidate();
/*      */   }
/*      */   public Mosaic getMosaic() { return this._mosaic; }
/*      */   public Tile getTile(int irow, int icol) { return this._mosaic.getTile(irow, icol); }
/*      */   public ColorBar addColorBar() { return addColorBar((ColorMapped)null, (String)null); }
/*      */   public ColorBar addColorBar(String label) { return addColorBar((ColorMapped)null, label); }
/*      */   public ColorBar addColorBar(ColorMapped cm) { return addColorBar(cm, (String)null); }
/*      */   public ColorBar addColorBar(ColorMapped cm, String label) { if (cm != null) { this._autoColorMapped = false; this._colorMapped = cm; } else { this._colorMapped = findBestColorMapped(); }  if (this._colorBar == null) { this._colorBar = new ColorBar(label); this._colorBar.setFont(getFont()); this._colorBar.setForeground(getForeground()); this._colorBar.setBackground(getBackground()); if (this._colorBarFormat != null) this._colorBar.setFormat(this._colorBarFormat);  if (this._colorBarWidthMinimum != 0) this._colorBar.setWidthMinimum(this._colorBarWidthMinimum);  if (this._colorMapped != null) this._colorMapped.getColorMap().addListener(this._colorBar);  add(this._colorBar, makeColorBarConstraints()); } else { this._colorBar.setLabel(label); }  revalidate(); return this._colorBar; }
/*      */   public void setColorBarWidthMinimum(int widthMinimum) { this._colorBarWidthMinimum = widthMinimum; if (this._colorBar != null) { this._colorBar.setWidthMinimum(widthMinimum); revalidate(); }  }
/*      */   public void setColorBarFormat(String format) { this._colorBarFormat = format; if (this._colorBar != null) { this._colorBar.setFormat(format); revalidate(); }  } public void removeColorBar() { if (this._colorBar != null) { remove(this._colorBar); revalidate(); this._colorBar = null; }  } public void addTitle(String title) { setTitle(title); } public void setTitle(String title) { if (title != null) { if (this._title == null) { this._title = new Title(title); Font font = getFont(); font.deriveFont(1.5F * font.getSize2D()); this._title.setFont(getFont()); this._title.setForeground(getForeground()); this._title.setBackground(getBackground()); GridBagConstraints gbc = new GridBagConstraints(); gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.gridheight = 1; gbc.weightx = 0.0D; gbc.weighty = 0.0D; gbc.fill = 0; gbc.insets.top = 0; gbc.insets.bottom = 0; gbc.insets.left = 0; gbc.insets.right = 0; add(this._title, gbc); revalidate(); } else { this._title.set(title); }  } else if (this._title != null) { remove(this._title); revalidate(); this._title = null; }  } public void removeTitle() { setTitle((String)null); } public void setLimits(double hmin, double vmin, double hmax, double vmax) { setHLimits(hmin, hmax); setVLimits(vmin, vmax); } public void setHLimits(double hmin, double hmax) { setHLimits(0, hmin, hmax); } public void setVLimits(double vmin, double vmax) { setVLimits(0, vmin, vmax); } public void setHLimits(int icol, double hmin, double hmax) { Check.argument((hmin < hmax), "hmin<hmax"); int nrow = getMosaic().countRows(); for (int irow = 0; irow < nrow; irow++) getTile(irow, icol).setHLimits(hmin, hmax);  } public void setVLimits(int irow, double vmin, double vmax) { Check.argument((vmin < vmax), "vmin<vmax"); int ncol = getMosaic().countColumns(); for (int icol = 0; icol < ncol; icol++) getTile(irow, icol).setVLimits(vmin, vmax);  } public void setLimitsDefault() { setHLimitsDefault(); setVLimitsDefault(); } public void setHLimitsDefault() { setHLimitsDefault(0); } public void setVLimitsDefault() { setVLimitsDefault(0); } public void setHLimitsDefault(int icol) { int nrow = getMosaic().countRows(); for (int irow = 0; irow < nrow; irow++) getTile(irow, icol).setHLimitsDefault();  } public void setVLimitsDefault(int irow) { int ncol = getMosaic().countColumns(); for (int icol = 0; icol < ncol; icol++) getTile(irow, icol).setVLimitsDefault();  } public void setHLabel(String label) { setHLabel(0, label); } public void setVLabel(String label) { setVLabel(0, label); } public void setHLabel(int icol, String label) { if (this._axesPlacement == AxesPlacement.LEFT_TOP) { this._mosaic.getTileAxisTop(icol).setLabel(label); adjustColorBar(); } else if (this._axesPlacement == AxesPlacement.LEFT_BOTTOM) { this._mosaic.getTileAxisBottom(icol).setLabel(label); adjustColorBar(); }  } public void setVLabel(int irow, String label) { if (this._axesPlacement != AxesPlacement.NONE) this._mosaic.getTileAxisLeft(irow).setLabel(label);  } public void setHFormat(String format) { setHFormat(0, format); } public void setVFormat(String format) { setVFormat(0, format); } public void setHFormat(int icol, String format) { if (this._axesPlacement == AxesPlacement.LEFT_TOP) { this._mosaic.getTileAxisTop(icol).setFormat(format); } else if (this._axesPlacement == AxesPlacement.LEFT_BOTTOM) { this._mosaic.getTileAxisBottom(icol).setFormat(format); }  } public void setVFormat(int irow, String format) { if (this._axesPlacement != AxesPlacement.NONE) this._mosaic.getTileAxisLeft(irow).setFormat(format);  } public void setVRotated(int irow, boolean rotated) { if (this._axesPlacement != AxesPlacement.NONE) this._mosaic.getTileAxisLeft(irow).setVerticalAxisRotated(rotated);  } public void setHInterval(double interval) { setHInterval(0, interval); } public void setVInterval(double interval) { setVInterval(0, interval); } public void setHInterval(int icol, double interval) { if (this._axesPlacement == AxesPlacement.LEFT_TOP) { this._mosaic.getTileAxisTop(icol).setInterval(interval); } else if (this._axesPlacement == AxesPlacement.LEFT_BOTTOM) { this._mosaic.getTileAxisBottom(icol).setInterval(interval); }  } public void setVInterval(int irow, double interval) { if (this._axesPlacement != AxesPlacement.NONE) this._mosaic.getTileAxisLeft(irow).setInterval(interval);  } public GridView addGrid() { return addGrid(0, 0); } public GridView addGrid(String parameters) { return addGrid(0, 0, parameters); } public GridView addGrid(int irow, int icol) { GridView gv = new GridView(); return addGridView(irow, icol, gv); } public GridView addGrid(int irow, int icol, String parameters) { GridView gv = new GridView(parameters); return addGridView(irow, icol, gv); } public BarsView addBars(float[] x2) { return addBars(0, 0, x2); } public BarsView addBars(Sampling s1, float[] x2) { return addBars(0, 0, s1, x2); } public BarsView addBars(float[][] x2) { return addBars(0, 0, x2); } public BarsView addBars(Sampling s1, float[][] x2) { return addBars(0, 0, s1, x2); } public BarsView addBars(int irow, int icol, Sampling s1, float[] x2) { BarsView bv = new BarsView(s1, x2); return addBarsView(irow, icol, bv); } public BarsView addBars(int irow, int icol, float[] x2) { BarsView bv = new BarsView(x2); return addBarsView(irow, icol, bv); } public BarsView addBars(int irow, int icol, float[][] x2) { BarsView bv = new BarsView(x2); return addBarsView(irow, icol, bv); } public BarsView addBars(int irow, int icol, Sampling s1, float[][] x2) { BarsView bv = new BarsView(s1, x2); return addBarsView(irow, icol, bv); } public PixelsView addPixels(float[][] f) { return addPixels(0, 0, f); } public PixelsView addPixels(Sampling s1, Sampling s2, float[][] f) { return addPixels(0, 0, s1, s2, f); } public PixelsView addPixels(int irow, int icol, float[][] f) { PixelsView pv = new PixelsView(f); return addPixelsView(irow, icol, pv); } public PixelsView addPixels(int irow, int icol, Sampling s1, Sampling s2, float[][] f) { PixelsView pv = new PixelsView(s1, s2, f); return addPixelsView(irow, icol, pv); } public PixelsView addPixels(float[][][] f) { return addPixels(0, 0, f); } public PixelsView addPixels(Sampling s1, Sampling s2, float[][][] f) { return addPixels(0, 0, s1, s2, f); } public PixelsView addPixels(int irow, int icol, float[][][] f) { PixelsView pv = new PixelsView(f); return addPixelsView(irow, icol, pv); } public PixelsView addPixels(int irow, int icol, Sampling s1, Sampling s2, float[][][] f) { PixelsView pv = new PixelsView(s1, s2, f); return addPixelsView(irow, icol, pv); } public PointsView addPoints(float[] x1, float[] x2) { return addPoints(0, 0, x1, x2); } public PointsView addPoints(float[] x1, float[] x2, float[] x3) { return addPoints(0, 0, x1, x2, x3); } public PointsView addPoints(float[] x2) { return addPoints(0, 0, x2); } public PointsView addPoints(Sampling s1, float[] x2) { return addPoints(0, 0, s1, x2); } public PointsView addPoints(float[][] x1, float[][] x2) { return addPoints(0, 0, x1, x2); } public PointsView addPoints(int irow, int icol, float[] x1, float[] x2) { PointsView pv = new PointsView(x1, x2); return addPointsView(irow, icol, pv); } public PointsView addPoints(int irow, int icol, float[] x1, float[] x2, float[] x3) { PointsView pv = new PointsView(x1, x2, x3); return addPointsView(irow, icol, pv); } public PointsView addPoints(int irow, int icol, float[] x2) { PointsView pv = new PointsView(x2); return addPointsView(irow, icol, pv); } public PointsView addPoints(int irow, int icol, Sampling s1, float[] x2) { PointsView pv = new PointsView(s1, x2); return addPointsView(irow, icol, pv); } public PointsView addPoints(int irow, int icol, float[][] x1, float[][] x2) { PointsView pv = new PointsView(x1, x2); return addPointsView(irow, icol, pv); } public ContoursView addContours(float[][] f) { return addContours(0, 0, f); } public ContoursView addContours(Sampling s1, Sampling s2, float[][] f) { return addContours(0, 0, s1, s2, f); } public ContoursView addContours(int irow, int icol, float[][] f) { ContoursView cv = new ContoursView(f); return addContoursView(irow, icol, cv); } public ContoursView addContours(int irow, int icol, Sampling s1, Sampling s2, float[][] f) { ContoursView cv = new ContoursView(s1, s2, f); return addContoursView(irow, icol, cv); } public SequenceView addSequence(float[] f) { return addSequence(0, 0, f); } public SequenceView addSequence(Sampling sx, float[] f) { return addSequence(0, 0, sx, f); } public SequenceView addSequence(int irow, int icol, float[] f) { SequenceView sv = new SequenceView(f); return addSequenceView(irow, icol, sv); } public SequenceView addSequence(int irow, int icol, Sampling sx, float[] f) { SequenceView sv = new SequenceView(sx, f); return addSequenceView(irow, icol, sv); } public boolean addTiledView(TiledView tv) { return addTiledView(0, 0, tv); } public boolean addTiledView(int irow, int icol, TiledView tv) { if (tv instanceof ColorMapped) { ColorMapped cm = (ColorMapped)tv; cm.getColorMap().addListener(this._colorMapHandler); }  return getTile(irow, icol).addTiledView(tv); } public boolean remove(TiledView tv) { if (tv instanceof ColorMapped) { ColorMapped cm = (ColorMapped)tv; cm.getColorMap().removeListener(this._colorMapHandler); }  int nrow = this._mosaic.countRows(); int ncol = this._mosaic.countColumns(); for (int irow = 0; irow < nrow; irow++) { for (int icol = 0; icol < ncol; icol++) { if (getTile(irow, icol).removeTiledView(tv)) return true;  }  }  return false; } public void setFont(Font font) { super.setFont(font); if (this._mosaic != null) this._mosaic.setFont(font);  if (this._colorBar != null) this._colorBar.setFont(font);  if (this._title != null) this._title.setFont(font.deriveFont(1.5F * font.getSize2D()));  adjustColorBar(); revalidate(); } public void setForeground(Color color) { super.setForeground(color); if (this._mosaic != null) this._mosaic.setForeground(color);  if (this._colorBar != null) this._colorBar.setForeground(color);  if (this._title != null) this._title.setForeground(color);  } public void setBackground(Color color) { super.setBackground(color); if (this._mosaic != null) this._mosaic.setBackground(color);  if (this._colorBar != null) this._colorBar.setBackground(color);  if (this._title != null) this._title.setBackground(color);  } private class Title extends IPanel
/*      */   {
/* 1179 */     private static final long serialVersionUID = 1L; Title(String text) { this.text = text; }
/*      */     
/*      */     String text;
/*      */     void set(String text) {
/* 1183 */       this.text = text;
/* 1184 */       repaint();
/*      */     }
/*      */     
/*      */     public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/* 1188 */       g2d = createGraphics(g2d, x, y, w, h);
/* 1189 */       g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/*      */ 
/*      */       
/* 1192 */       Font font = g2d.getFont();
/* 1193 */       FontMetrics fm = g2d.getFontMetrics();
/* 1194 */       FontRenderContext frc = g2d.getFontRenderContext();
/* 1195 */       LineMetrics lm = font.getLineMetrics(this.text, frc);
/*      */ 
/*      */       
/* 1198 */       int fd = Math.round(lm.getDescent());
/* 1199 */       int wt = fm.stringWidth(this.text);
/* 1200 */       int xt = Math.max(0, (w - wt) / 2);
/* 1201 */       int yt = h - 1 - 2 * fd;
/* 1202 */       g2d.drawString(this.text, xt, yt);
/* 1203 */       g2d.dispose();
/*      */     }
/*      */     
/*      */     protected void paintComponent(Graphics g) {
/* 1207 */       super.paintComponent(g);
/* 1208 */       paintToRect((Graphics2D)g, 0, 0, getWidth(), getHeight());
/*      */     }
/*      */     
/*      */     public Dimension getMinimumSize() {
/* 1212 */       if (isMinimumSizeSet()) {
/* 1213 */         return super.getMinimumSize();
/*      */       }
/* 1215 */       Font font = getFont();
/* 1216 */       FontMetrics fm = getFontMetrics(font);
/* 1217 */       int fh = fm.getHeight();
/* 1218 */       int fd = fm.getDescent();
/* 1219 */       int wt = fm.stringWidth(this.text);
/* 1220 */       return new Dimension(wt + 4 * fh, fd + fh);
/*      */     }
/*      */     
/*      */     public Dimension getPreferredSize() {
/* 1224 */       if (isPreferredSizeSet()) {
/* 1225 */         return super.getPreferredSize();
/*      */       }
/* 1227 */       return getMinimumSize();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private GridView addGridView(int irow, int icol, GridView gv) {
/* 1233 */     addTiledView(irow, icol, gv);
/* 1234 */     return gv;
/*      */   }
/*      */   
/*      */   private BarsView addBarsView(int irow, int icol, BarsView bv) {
/* 1238 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 1239 */       bv.setOrientation(BarsView.Orientation.X1RIGHT_X2UP);
/* 1240 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 1241 */       bv.setOrientation(BarsView.Orientation.X1DOWN_X2RIGHT);
/*      */     } 
/* 1243 */     addTiledView(irow, icol, bv);
/* 1244 */     return bv;
/*      */   }
/*      */   
/*      */   private PixelsView addPixelsView(int irow, int icol, PixelsView pv) {
/* 1248 */     pv.getColorMap().addListener(this._colorMapHandler);
/* 1249 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 1250 */       pv.setOrientation(PixelsView.Orientation.X1RIGHT_X2UP);
/* 1251 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 1252 */       pv.setOrientation(PixelsView.Orientation.X1DOWN_X2RIGHT);
/*      */     } 
/* 1254 */     addTiledView(irow, icol, pv);
/* 1255 */     return pv;
/*      */   }
/*      */   
/*      */   private PointsView addPointsView(int irow, int icol, PointsView pv) {
/* 1259 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 1260 */       pv.setOrientation(PointsView.Orientation.X1RIGHT_X2UP);
/* 1261 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 1262 */       pv.setOrientation(PointsView.Orientation.X1DOWN_X2RIGHT);
/*      */     } 
/* 1264 */     addTiledView(irow, icol, pv);
/* 1265 */     return pv;
/*      */   }
/*      */   
/*      */   private ContoursView addContoursView(int irow, int icol, ContoursView cv) {
/* 1269 */     if (this._orientation == Orientation.X1RIGHT_X2UP) {
/* 1270 */       cv.setOrientation(ContoursView.Orientation.X1RIGHT_X2UP);
/* 1271 */     } else if (this._orientation == Orientation.X1DOWN_X2RIGHT) {
/* 1272 */       cv.setOrientation(ContoursView.Orientation.X1DOWN_X2RIGHT);
/*      */     } 
/* 1274 */     addTiledView(irow, icol, cv);
/* 1275 */     return cv;
/*      */   }
/*      */   
/*      */   private SequenceView addSequenceView(int irow, int icol, SequenceView sv) {
/* 1279 */     addTiledView(irow, icol, sv);
/* 1280 */     return sv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void adjustColorBar() {
/* 1290 */     if (this._colorBar != null) {
/* 1291 */       GridBagLayout gbl = (GridBagLayout)getLayout();
/* 1292 */       gbl.setConstraints(this._colorBar, makeColorBarConstraints());
/* 1293 */       revalidate();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private GridBagConstraints makeColorBarConstraints() {
/* 1300 */     GridBagConstraints gbc = new GridBagConstraints();
/* 1301 */     gbc.gridx = 1;
/* 1302 */     gbc.gridy = 1;
/* 1303 */     gbc.gridwidth = 1;
/* 1304 */     gbc.gridheight = 1;
/* 1305 */     gbc.weightx = 0.0D;
/* 1306 */     gbc.weighty = 100.0D;
/* 1307 */     gbc.fill = 3;
/* 1308 */     gbc.anchor = 17;
/* 1309 */     gbc.insets.top = this._mosaic.getHeightAxesTop();
/* 1310 */     gbc.insets.left = 10;
/* 1311 */     gbc.insets.bottom = this._mosaic.getHeightAxesBottom();
/* 1312 */     gbc.insets.right = 0;
/* 1313 */     return gbc;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ColorMapped findBestColorMapped() {
/* 1321 */     if (this._autoColorMapped) {
/* 1322 */       int rows = this._mosaic.countRows();
/* 1323 */       int cols = this._mosaic.countColumns();
/* 1324 */       ColorMapped cmBest = null;
/* 1325 */       ColorMapped cmSolid = null;
/* 1326 */       for (int ncol = cols - 1; ncol >= 0; ncol--) {
/* 1327 */         for (int nrow = 0; nrow < rows; nrow++) {
/* 1328 */           Tile t = getTile(nrow, ncol);
/* 1329 */           int ntv = t.countTiledViews();
/* 1330 */           for (int itv = ntv - 1; itv >= 0 && cmBest == null; itv--) {
/* 1331 */             TiledView tv = t.getTiledView(itv);
/* 1332 */             if (tv instanceof ColorMapped) {
/* 1333 */               ColorMapped cm = (ColorMapped)tv;
/* 1334 */               if (isMultiColor(cm)) {
/* 1335 */                 cmBest = cm;
/* 1336 */               } else if (cmSolid == null) {
/* 1337 */                 cmSolid = cm;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1343 */       if (cmBest == null)
/* 1344 */         cmBest = cmSolid; 
/* 1345 */       return cmBest;
/*      */     } 
/* 1347 */     return this._colorMapped;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isMultiColor(ColorMapped cm) {
/* 1356 */     ColorMap cmap = cm.getColorMap();
/* 1357 */     IndexColorModel icm = cmap.getColorModel();
/* 1358 */     int n = icm.getMapSize();
/* 1359 */     int rgb = icm.getRGB(0) & 0xFFFFFF;
/* 1360 */     for (int i = 1; i < n; i++) {
/* 1361 */       if (rgb != (icm.getRGB(i) & 0xFFFFFF))
/* 1362 */         return true; 
/* 1363 */     }  return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateColorMapped() {
/* 1370 */     ColorMapped cm = findBestColorMapped();
/* 1371 */     if (cm != this._colorMapped && this._colorBar != null) {
/* 1372 */       if (this._colorMapped != null)
/* 1373 */         this._colorMapped.getColorMap().removeListener(this._colorBar); 
/* 1374 */       this._colorMapped = cm;
/* 1375 */       this._colorMapped.getColorMap().addListener(this._colorBar);
/* 1376 */       revalidate();
/*      */     } 
/*      */   }
/*      */   
/*      */   private class ColorMapHandler
/*      */     implements ColorMapListener
/*      */   {
/*      */     private ColorMapHandler() {}
/*      */     
/*      */     public void colorMapChanged(ColorMap cm) {
/* 1386 */       PlotPanel.this.updateColorMapped();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/PlotPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
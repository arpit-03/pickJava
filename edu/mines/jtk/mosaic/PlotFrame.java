/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ComponentAdapter;
/*     */ import java.awt.event.ComponentEvent;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlotFrame
/*     */   extends JFrame
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int DEFAULT_WIDTH = 720;
/*     */   private static final int DEFAULT_HEIGHT = 550;
/*     */   
/*     */   public enum Split
/*     */   {
/*  69 */     HORIZONTAL,
/*  70 */     VERTICAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotFrame(PlotPanel panel) {
/*  78 */     this._panelTL = panel;
/*  79 */     this._panelBR = panel;
/*  80 */     this._panelMain = new MainPanel();
/*  81 */     this._panelMain.setLayout(new BorderLayout());
/*  82 */     this._panelMain.add(this._panelTL, "Center");
/*  83 */     setSize(this._panelMain.getPreferredSize());
/*  84 */     add(this._panelMain, "Center");
/*  85 */     setFont(DEFAULT_FONT);
/*  86 */     setBackground(DEFAULT_BACKGROUND);
/*  87 */     setSize(720, 550);
/*  88 */     addModeManager();
/*  89 */     addResizedShownListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotFrame(PlotPanel panelTL, PlotPanel panelBR, Split split) {
/*     */     double resizeWeight;
/*  99 */     this._panelTL = panelTL;
/* 100 */     this._panelBR = panelBR;
/* 101 */     this._split = split;
/* 102 */     this._panelMain = new MainPanel();
/* 103 */     this._panelMain.setLayout(new BorderLayout());
/*     */     
/* 105 */     if (this._split == Split.HORIZONTAL) {
/* 106 */       this._splitPane = new JSplitPane(1, this._panelTL, this._panelBR);
/*     */       
/* 108 */       double colTL = this._panelTL.getMosaic().countColumns();
/* 109 */       double colBR = this._panelBR.getMosaic().countColumns();
/* 110 */       resizeWeight = colTL / (colTL + colBR);
/*     */     } else {
/* 112 */       this._splitPane = new JSplitPane(0, this._panelTL, this._panelBR);
/*     */       
/* 114 */       double rowTL = this._panelTL.getMosaic().countRows();
/* 115 */       double rowBR = this._panelBR.getMosaic().countRows();
/* 116 */       resizeWeight = rowTL / (rowTL + rowBR);
/*     */     } 
/* 118 */     this._splitPane.setResizeWeight(resizeWeight);
/* 119 */     this._splitPane.setDividerLocation(1.0D - resizeWeight);
/* 120 */     this._splitPane.setOneTouchExpandable(true);
/* 121 */     this._panelMain.add(this._splitPane, "Center");
/* 122 */     setSize(this._panelMain.getPreferredSize());
/* 123 */     add(this._panelMain, "Center");
/* 124 */     setBackground(DEFAULT_BACKGROUND);
/* 125 */     setFont(DEFAULT_FONT);
/* 126 */     setSize(720, 550);
/* 127 */     addModeManager();
/* 128 */     addResizedShownListener();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotPanel getPlotPanel() {
/* 137 */     return this._panelTL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotPanel getPlotPanelTopLeft() {
/* 146 */     return this._panelTL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlotPanel getPlotPanelBottomRight() {
/* 155 */     return this._panelBR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSplitPane getSplitPane() {
/* 163 */     return this._splitPane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModeManager getModeManager() {
/* 171 */     return this._modeManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileZoomMode getTileZoomMode() {
/* 180 */     return this._tileZoomMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MouseTrackMode getMouseTrackMode() {
/* 189 */     return this._mouseTrackMode;
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
/*     */   public void paintToPng(double dpi, double win, String fileName) {
/* 206 */     if (canPaintToPngNow()) {
/*     */       try {
/* 208 */         this._panelMain.paintToPng(dpi, win, fileName);
/* 209 */       } catch (IOException ioe) {
/* 210 */         throw new RuntimeException(ioe);
/*     */       } 
/*     */     } else {
/* 213 */       final double fdpi = dpi;
/* 214 */       final double fwin = win;
/* 215 */       final String ffileName = fileName;
/* 216 */       SwingUtilities.invokeLater(new Runnable() {
/*     */             public void run() {
/* 218 */               PlotFrame.this.paintToPng(fdpi, fwin, ffileName);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   private boolean canPaintToPngNow() {
/* 224 */     if (!this._panelMain.isValid())
/*     */     {
/* 226 */       return false;
/*     */     }
/* 228 */     if (!SwingUtilities.isEventDispatchThread())
/*     */     {
/* 230 */       return false;
/*     */     }
/* 232 */     if (TileAxis.revalidatePending(this))
/*     */     {
/* 234 */       return false;
/*     */     }
/*     */     
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 245 */     super.setFont(font);
/* 246 */     if (this._panelMain == null)
/* 247 */       return;  this._panelMain.setFont(font);
/* 248 */     this._panelTL.setFont(font);
/* 249 */     if (this._panelBR != this._panelTL) {
/* 250 */       this._panelBR.setFont(font);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontSize(float size) {
/* 259 */     setFontSizeInternal(size);
/* 260 */     this._fontSizeForPrint = false;
/* 261 */     this._fontSizeForSlide = false;
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
/*     */   public void setFontSizeForSlide(double fracWidth, double fracHeight) {
/* 280 */     setFontSizeForSlide(fracWidth, fracHeight, 1.3333333333333333D);
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
/*     */   public void setFontSizeForSlide(double fracWidth, double fracHeight, double aspectRatio) {
/* 295 */     this._fracWidthSlide = fracWidth;
/* 296 */     this._fracHeightSlide = fracHeight;
/* 297 */     this._aspectRatioSlide = aspectRatio;
/* 298 */     this._fontSizeForSlide = true;
/* 299 */     this._fontSizeForPrint = false;
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
/*     */   public void setFontSizeForPrint(double fontSize, double plotWidth) {
/* 311 */     this._fontSizePrint = fontSize;
/* 312 */     this._plotWidthPrint = plotWidth;
/* 313 */     this._fontSizeForPrint = true;
/* 314 */     this._fontSizeForSlide = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForeground(Color color) {
/* 322 */     super.setForeground(color);
/* 323 */     if (this._panelMain == null)
/* 324 */       return;  this._panelMain.setForeground(color);
/* 325 */     this._panelTL.setForeground(color);
/* 326 */     if (this._panelBR != this._panelTL) {
/* 327 */       this._panelBR.setForeground(color);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackground(Color color) {
/* 335 */     super.setBackground(color);
/* 336 */     if (this._panelMain == null)
/* 337 */       return;  this._panelMain.setBackground(color);
/* 338 */     this._panelTL.setBackground(color);
/* 339 */     if (this._panelBR != this._panelTL) {
/* 340 */       this._panelBR.setBackground(color);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void trace(String s) {
/* 345 */     System.out.println(s);
/*     */   }
/*     */ 
/*     */   
/* 349 */   private static final Font DEFAULT_FONT = new Font("Arial", 0, 24);
/* 350 */   private static final Color DEFAULT_BACKGROUND = Color.WHITE;
/*     */   
/*     */   private PlotPanel _panelTL;
/*     */   
/*     */   private PlotPanel _panelBR;
/*     */   
/*     */   private Split _split;
/*     */   
/*     */   private JSplitPane _splitPane;
/*     */   private MainPanel _panelMain;
/*     */   private ModeManager _modeManager;
/*     */   private TileZoomMode _tileZoomMode;
/*     */   private MouseTrackMode _mouseTrackMode;
/*     */   private boolean _fontSizeForPrint;
/*     */   private boolean _fontSizeForSlide;
/*     */   private double _fracWidthSlide;
/*     */   private double _fracHeightSlide;
/*     */   private double _aspectRatioSlide;
/*     */   private double _fontSizePrint;
/*     */   private double _plotWidthPrint;
/*     */   
/*     */   private class MainPanel
/*     */     extends IPanel
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private MainPanel() {}
/*     */     
/*     */     public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/* 379 */       if (PlotFrame.this._split == null) {
/* 380 */         PlotFrame.this._panelTL.paintToRect(g2d, x, y, w, h);
/*     */       } else {
/* 382 */         double ws = w / PlotFrame.this._splitPane.getWidth();
/* 383 */         double hs = h / PlotFrame.this._splitPane.getHeight();
/* 384 */         int nc = PlotFrame.this._splitPane.getComponentCount();
/* 385 */         for (int ic = 0; ic < nc; ic++) {
/* 386 */           Component c = PlotFrame.this._splitPane.getComponent(ic);
/* 387 */           int xc = c.getX();
/* 388 */           int yc = c.getY();
/* 389 */           int wc = c.getWidth();
/* 390 */           int hc = c.getHeight();
/* 391 */           xc = (int)Math.round(xc * ws);
/* 392 */           yc = (int)Math.round(yc * hs);
/* 393 */           wc = (int)Math.round(wc * ws);
/* 394 */           hc = (int)Math.round(hc * hs);
/* 395 */           if (c instanceof IPanel) {
/* 396 */             IPanel ip = (IPanel)c;
/* 397 */             ip.paintToRect(g2d, xc, yc, wc, hc);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addModeManager() {
/* 408 */     this._modeManager = new ModeManager();
/* 409 */     this._panelTL.getMosaic().setModeManager(this._modeManager);
/* 410 */     if (this._panelBR != this._panelTL)
/* 411 */       this._panelBR.getMosaic().setModeManager(this._modeManager); 
/* 412 */     this._tileZoomMode = new TileZoomMode(this._modeManager);
/* 413 */     this._mouseTrackMode = new MouseTrackMode(this._modeManager);
/* 414 */     this._tileZoomMode.setActive(true);
/* 415 */     this._mouseTrackMode.setActive(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setFontSizeInternal(float size) {
/* 420 */     Font font = getFont();
/* 421 */     if (font == null)
/* 422 */       font = UIManager.getFont("Panel.font"); 
/* 423 */     if (font != null) {
/* 424 */       font = font.deriveFont(size);
/* 425 */       setFont(font);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addResizedShownListener() {
/* 431 */     addComponentListener(new ComponentAdapter() {
/*     */           public void componentResized(ComponentEvent e) {
/* 433 */             PlotFrame.this.adjustFontSize();
/*     */           }
/*     */           public void componentShown(ComponentEvent e) {
/* 436 */             PlotFrame.this.adjustFontSize();
/*     */           }
/*     */         });
/*     */   }
/*     */   private void adjustFontSize() {
/* 441 */     if (this._fontSizeForPrint) {
/* 442 */       adjustFontSizeForPrint();
/* 443 */     } else if (this._fontSizeForSlide) {
/* 444 */       adjustFontSizeForSlide();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void adjustFontSizeForSlide() {
/* 452 */     double panelWidth = this._panelMain.getWidth();
/* 453 */     double panelHeight = this._panelMain.getHeight();
/* 454 */     if (panelWidth == 0.0D || panelHeight == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 458 */     double slideWidth = this._fracWidthSlide;
/* 459 */     double slideHeight = this._fracHeightSlide;
/*     */ 
/*     */     
/* 462 */     slideWidth *= this._aspectRatioSlide;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     double scaleHeight = 1.0D;
/* 469 */     if (panelWidth * slideHeight > panelHeight * slideWidth) {
/* 470 */       scaleHeight = panelHeight * slideWidth / panelWidth * slideHeight;
/*     */     }
/*     */ 
/*     */     
/* 474 */     double fontSize = panelHeight / scaleHeight / this._fracHeightSlide / 20.0D;
/* 475 */     setFontSizeInternal((float)fontSize);
/*     */   }
/*     */ 
/*     */   
/*     */   private void adjustFontSizeForPrint() {
/* 480 */     double panelWidth = this._panelMain.getWidth();
/* 481 */     float fontSize = (float)(this._fontSizePrint * panelWidth / this._plotWidthPrint);
/* 482 */     setFontSizeInternal(fontSize);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/PlotFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
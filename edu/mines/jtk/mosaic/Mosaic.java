/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Set;
/*     */ import javax.swing.JScrollBar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mosaic
/*     */   extends IPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int _nrow;
/*     */   private int _ncol;
/*     */   private Tile[][] _tiles;
/*     */   private TileAxis[] _axesTop;
/*     */   private TileAxis[] _axesLeft;
/*     */   private TileAxis[] _axesBottom;
/*     */   private TileAxis[] _axesRight;
/*     */   private ArrayList<Tile> _tileList;
/*     */   private ArrayList<TileAxis> _axisList;
/*     */   private HScrollBar[] _hsb;
/*     */   private VScrollBar[] _vsb;
/*     */   private int _wts;
/*     */   private int[] _wm;
/*     */   private int[] _we;
/*     */   private int[] _hm;
/*     */   private int[] _he;
/*     */   private ModeManager _modeManager;
/*     */   private static final int SCROLL_MAX = 1000000000;
/*     */   private static final double SCROLL_SCL = 1.0E-9D;
/*     */   
/*     */   public enum AxesPlacement
/*     */   {
/*  58 */     TOP, LEFT, BOTTOM, RIGHT;
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
/*     */   public void setModeManager(ModeManager modeManager) {
/*     */     if (this._modeManager != null) {
/*     */       for (Tile tile : this._tileList) {
/*     */         this._modeManager.remove(tile);
/*     */       }
/*     */       for (TileAxis axis : this._axisList) {
/*     */         this._modeManager.remove(axis);
/*     */       }
/*     */     } 
/*     */     this._modeManager = modeManager;
/*     */     if (this._modeManager != null) {
/*     */       for (Tile tile : this._tileList) {
/*     */         this._modeManager.add(tile);
/*     */       }
/*     */       for (TileAxis axis : this._axisList) {
/*     */         this._modeManager.add(axis);
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
/*     */   public int countRows() {
/*     */     return this._nrow;
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
/*     */   public int countColumns() {
/*     */     return this._ncol;
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
/*     */   public Tile getTile(int irow, int icol) {
/*     */     return this._tiles[irow][icol];
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
/*     */   public TileAxis getTileAxisTop(int icol) {
/*     */     return (this._axesTop != null) ? this._axesTop[icol] : null;
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
/*     */   public TileAxis getTileAxisLeft(int irow) {
/*     */     return (this._axesLeft != null) ? this._axesLeft[irow] : null;
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
/*     */   public TileAxis getTileAxisBottom(int icol) {
/*     */     return (this._axesBottom != null) ? this._axesBottom[icol] : null;
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
/*     */   public TileAxis getTileAxisRight(int irow) {
/*     */     return (this._axesRight != null) ? this._axesRight[irow] : null;
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
/*     */   public void setWidthMinimum(int icol, int widthMinimum) {
/*     */     this._wm[icol] = widthMinimum;
/*     */     revalidate();
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
/*     */   public void setWidthElastic(int icol, int widthElastic) {
/*     */     this._we[icol] = widthElastic;
/*     */     revalidate();
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
/*     */   public void setHeightMinimum(int irow, int heightMinimum) {
/*     */     this._hm[irow] = heightMinimum;
/*     */     revalidate();
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
/*     */   public void setHeightElastic(int irow, int heightElastic) {
/*     */     this._he[irow] = heightElastic;
/*     */     revalidate();
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
/*     */   public void setWidthTileSpacing(int wts) {
/*     */     this._wts = wts;
/*     */     revalidate();
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
/*     */   public ModeManager getModeManager() {
/*     */     return this._modeManager;
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
/*     */   public void setFont(Font font) {
/*     */     super.setFont(font);
/*     */     if (this._tileList != null) {
/*     */       for (Tile tile : this._tileList) {
/*     */         tile.setFont(font);
/*     */       }
/*     */       for (TileAxis axis : this._axisList) {
/*     */         axis.setFont(font);
/*     */       }
/*     */     } 
/*     */     revalidate();
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
/*     */   public void setForeground(Color color) {
/*     */     super.setForeground(color);
/*     */     if (this._tileList != null) {
/*     */       for (Tile tile : this._tileList) {
/*     */         tile.setForeground(color);
/*     */       }
/*     */       for (TileAxis axis : this._axisList) {
/*     */         axis.setForeground(color);
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
/*     */   public void setBackground(Color color) {
/*     */     super.setBackground(color);
/*     */     if (this._tileList != null) {
/*     */       for (Tile tile : this._tileList) {
/*     */         tile.setBackground(color);
/*     */       }
/*     */       for (TileAxis axis : this._axisList) {
/*     */         axis.setBackground(color);
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
/*     */   public Dimension getMinimumSize() {
/*     */     if (isMinimumSizeSet()) {
/*     */       return super.getMinimumSize();
/*     */     }
/*     */     return new Dimension(widthMinimum(), heightMinimum());
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
/*     */   public Dimension getPreferredSize() {
/*     */     if (isPreferredSizeSet()) {
/*     */       return super.getPreferredSize();
/*     */     }
/*     */     return getMinimumSize();
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
/*     */   public void doLayout() {
/*     */     updateScrollBars();
/*     */     int w = getWidth();
/*     */     int h = getHeight();
/*     */     int wf = widthFixed();
/*     */     int hf = heightFixed();
/*     */     int wfill = Math.max(0, w - wf);
/*     */     int hfill = Math.max(0, h - hf);
/*     */     int wesum = 0;
/*     */     for (int icol = 0; icol < this._ncol; icol++) {
/*     */       wesum += this._we[icol];
/*     */     }
/*     */     int hesum = 0;
/*     */     for (int irow = 0; irow < this._nrow; irow++) {
/*     */       hesum += this._he[irow];
/*     */     }
/*     */     wesum = Math.max(1, wesum);
/*     */     hesum = Math.max(1, hesum);
/*     */     int[] wcol = new int[this._ncol];
/*     */     for (int i = 0, wleft = wfill; i < this._ncol; i++) {
/*     */       int we = (i < this._ncol - 1) ? (wfill * this._we[i] / wesum) : wleft;
/*     */       if (this._we[i] == 0) {
/*     */         we = 0;
/*     */       }
/*     */       wcol[i] = Math.max(this._wm[i], we);
/*     */       wleft -= wcol[i];
/*     */     } 
/*     */     int[] hrow = new int[this._nrow];
/*     */     for (int j = 0, hleft = hfill; j < this._nrow; j++) {
/*     */       int he = (j < this._nrow - 1) ? (hfill * this._he[j] / hesum) : hleft;
/*     */       if (this._he[j] == 0) {
/*     */         he = 0;
/*     */       }
/*     */       hrow[j] = Math.max(this._hm[j], he);
/*     */       hleft -= hrow[j];
/*     */     } 
/*     */     int wab = widthAxesBorder();
/*     */     int wtb = widthTileBorder();
/*     */     int wts = widthTileSpacing();
/*     */     if (this._axesTop != null) {
/*     */       int haxis = heightMinimumAxesTop() - wab - wab;
/*     */       int xaxis = widthMinimumAxesLeft() + wtb;
/*     */       int yaxis = wab;
/*     */       for (int m = 0; m < this._ncol; m++) {
/*     */         int waxis = wcol[m];
/*     */         this._axesTop[m].setBounds(xaxis, yaxis, waxis, haxis);
/*     */         xaxis += waxis + wtb + wts + wtb;
/*     */       } 
/*     */     } 
/*     */     if (this._axesLeft != null) {
/*     */       int waxis = widthMinimumAxesLeft() - wab - wab;
/*     */       int xaxis = wab;
/*     */       int yaxis = heightMinimumAxesTop() + wtb;
/*     */       for (int m = 0; m < this._nrow; m++) {
/*     */         int haxis = hrow[m];
/*     */         this._axesLeft[m].setBounds(xaxis, yaxis, waxis, haxis);
/*     */         yaxis += haxis + wtb + wts + wtb;
/*     */       } 
/*     */     } 
/*     */     int xtile0 = wtb;
/*     */     int ytile0 = wtb;
/*     */     if (this._axesLeft != null) {
/*     */       xtile0 += widthMinimumAxesLeft();
/*     */     }
/*     */     if (this._axesTop != null) {
/*     */       ytile0 += heightMinimumAxesTop();
/*     */     }
/*     */     int xtile = xtile0;
/*     */     int ytile = ytile0;
/*     */     for (int k = 0; k < this._nrow; k++) {
/*     */       int htile = hrow[k];
/*     */       xtile = xtile0;
/*     */       for (int m = 0; m < this._ncol; m++) {
/*     */         int wtile = wcol[m];
/*     */         this._tiles[k][m].setBounds(xtile, ytile, wtile, htile);
/*     */         xtile += wtile + wtb + wts + wtb;
/*     */       } 
/*     */       ytile += htile + wtb + wts + wtb;
/*     */     } 
/*     */     xtile -= wts + wtb;
/*     */     ytile -= wts + wtb;
/*     */     if (this._axesBottom != null) {
/*     */       int haxis = heightMinimumAxesBottom() - wab - wab;
/*     */       int xaxis = widthMinimumAxesLeft() + wtb;
/*     */       int yaxis = ytile + wab;
/*     */       for (int m = 0; m < this._ncol; m++) {
/*     */         int waxis = wcol[m];
/*     */         this._axesBottom[m].setBounds(xaxis, yaxis, waxis, haxis);
/*     */         xaxis += waxis + wtb + wts + wtb;
/*     */       } 
/*     */     } 
/*     */     if (this._axesRight != null) {
/*     */       int waxis = widthMinimumAxesRight() - wab - wab;
/*     */       int xaxis = xtile + wab;
/*     */       int yaxis = heightMinimumAxesTop() + wtb;
/*     */       for (int m = 0; m < this._nrow; m++) {
/*     */         int haxis = hrow[m];
/*     */         this._axesRight[m].setBounds(xaxis, yaxis, waxis, haxis);
/*     */         yaxis += haxis + wtb + wts + wtb;
/*     */       } 
/*     */     } 
/*     */     int hhsb = heightHScrollBars();
/*     */     if (hhsb > 0) {
/*     */       int xhsb = widthMinimumAxesLeft() + wtb;
/*     */       int yhsb = ytile;
/*     */       if (this._axesBottom != null) {
/*     */         yhsb += heightMinimumAxesBottom() - wab;
/*     */       }
/*     */       for (int m = 0; m < this._ncol; m++) {
/*     */         int whsb = wcol[m];
/*     */         this._hsb[m].setBounds(xhsb, yhsb, whsb, hhsb);
/*     */         xhsb += whsb + wtb + wts + wtb;
/*     */       } 
/*     */     } 
/*     */     int wvsb = widthVScrollBars();
/*     */     if (wvsb > 0) {
/*     */       int xvsb = xtile;
/*     */       if (this._axesRight != null) {
/*     */         xvsb += widthMinimumAxesRight() - wab;
/*     */       }
/*     */       int yvsb = heightMinimumAxesTop() + wtb;
/*     */       for (int m = 0; m < this._nrow; m++) {
/*     */         int hvsb = hrow[m];
/*     */         this._vsb[m].setBounds(xvsb, yvsb, wvsb, hvsb);
/*     */         yvsb += hvsb + wtb + wts + wtb;
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
/*     */   public void paintToRect(Graphics2D g2d, int x, int y, int w, int h) {
/*     */     g2d = createGraphics(g2d, x, y, w, h);
/*     */     double ws = w / getWidth();
/*     */     double hs = h / getHeight();
/*     */     float lineWidth = getLineWidth(g2d);
/*     */     float wtb = lineWidth * widthTileBorder();
/*     */     float wab = lineWidth * widthAxesBorder();
/*     */     int itb = 1 + (int)(wtb / 2.0F);
/*     */     int iab = 1 + (int)(wab / 2.0F);
/*     */     BasicStroke stb = new BasicStroke(wtb);
/*     */     BasicStroke sab = new BasicStroke(wab);
/*     */     int nc = getComponentCount();
/*     */     for (int ic = 0; ic < nc; ic++) {
/*     */       Component c = getComponent(ic);
/*     */       int xc = c.getX();
/*     */       int yc = c.getY();
/*     */       int wc = c.getWidth();
/*     */       int hc = c.getHeight();
/*     */       xc = (int)Math.round(xc * ws);
/*     */       yc = (int)Math.round(yc * hs);
/*     */       wc = (int)Math.round(wc * ws);
/*     */       hc = (int)Math.round(hc * hs);
/*     */       if (c instanceof IPanel) {
/*     */         IPanel ip = (IPanel)c;
/*     */         ip.paintToRect(g2d, xc, yc, wc, hc);
/*     */         if (wtb > 0.0F && ip instanceof Tile) {
/*     */           Tile tile = (Tile)ip;
/*     */           if (tile.countTiledViews() > 0) {
/*     */             g2d.setStroke(stb);
/*     */             g2d.drawRect(xc - itb, yc - itb, wc + itb + itb - 1, hc + itb + itb - 1);
/*     */           } 
/*     */         } else if (wab > 0.0F && ip instanceof TileAxis) {
/*     */           g2d.setStroke(sab);
/*     */           g2d.drawRect(xc - iab, yc - iab, wc + iab + iab - 1, hc + iab + iab - 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     g2d.dispose();
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
/*     */   protected void paintComponent(Graphics g) {
/*     */     super.paintComponent(g);
/*     */     paintToRect((Graphics2D)g, 0, 0, getWidth(), getHeight());
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
/*     */   void alignProjectors(Tile tile) {
/*     */     int jrow = tile.getRowIndex();
/*     */     int jcol = tile.getColumnIndex();
/*     */     Projector bhp = tile.getBestHorizontalProjector();
/*     */     if (bhp != null) {
/*     */       bhp = new Projector(bhp);
/*     */       for (int i = 0; i < this._nrow; i++) {
/*     */         if (i != jrow) {
/*     */           bhp.merge(this._tiles[i][jcol].getBestHorizontalProjector());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     Projector bvp = tile.getBestVerticalProjector();
/*     */     if (bvp != null) {
/*     */       bvp = new Projector(bvp);
/*     */       for (int i = 0; i < this._ncol; i++) {
/*     */         if (i != jcol) {
/*     */           bvp.merge(this._tiles[jrow][i].getBestVerticalProjector());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     boolean[] checkScales = checkTileScales(tile);
/*     */     if (!checkScales[0] && bhp != null) {
/*     */       bhp.setScale(AxisScale.LINEAR);
/*     */     }
/*     */     if (!checkScales[1] && bvp != null) {
/*     */       bvp.setScale(AxisScale.LINEAR);
/*     */     }
/*     */     if (bhp != null && bvp != null) {
/*     */       tile.setProjectors(bhp, bvp);
/*     */     } else if (bhp != null) {
/*     */       tile.setHorizontalProjector(bhp);
/*     */     } else if (bvp != null) {
/*     */       tile.setVerticalProjector(bvp);
/*     */     } 
/*     */     for (int irow = 0; irow < this._nrow; irow++) {
/*     */       if (irow != jrow && bhp != null) {
/*     */         this._tiles[irow][jcol].setHorizontalProjector(bhp);
/*     */       }
/*     */     } 
/*     */     for (int icol = 0; icol < this._ncol; icol++) {
/*     */       if (icol != jcol && bvp != null) {
/*     */         this._tiles[jrow][icol].setVerticalProjector(bvp);
/*     */       }
/*     */     } 
/*     */     repaintAxis(this._axesTop, jcol);
/*     */     repaintAxis(this._axesBottom, jcol);
/*     */     repaintAxis(this._axesLeft, jrow);
/*     */     repaintAxis(this._axesRight, jrow);
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
/*     */   private boolean[] checkTileScales(Tile tile) {
/*     */     int i, j, jrow = tile.getRowIndex();
/*     */     int jcol = tile.getColumnIndex();
/*     */     AxisScale hscale = tile.getHScale();
/*     */     AxisScale vscale = tile.getVScale();
/*     */     boolean hcompat = true;
/*     */     for (int irow = 0; irow < this._nrow; irow++) {
/*     */       Tile t = this._tiles[irow][jcol];
/*     */       i = hcompat & ((t.getHScale() == hscale) ? 1 : 0);
/*     */     } 
/*     */     boolean vcompat = true;
/*     */     for (int icol = 0; icol < this._ncol; icol++) {
/*     */       Tile t = this._tiles[jrow][icol];
/*     */       j = vcompat & ((t.getVScale() == vscale) ? 1 : 0);
/*     */     } 
/*     */     return new boolean[] { i, j };
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
/*     */   void setViewRect(Tile tile, DRectangle vr) {
/*     */     int wvsb = widthVScrollBars();
/*     */     int hhsb = heightHScrollBars();
/*     */     double x = Math.max(0.0D, Math.min(1.0D, vr.x));
/*     */     double y = Math.max(0.0D, Math.min(1.0D, vr.y));
/*     */     double w = Math.max(0.0D, Math.min(1.0D - vr.x, vr.width));
/*     */     double h = Math.max(0.0D, Math.min(1.0D - vr.y, vr.height));
/*     */     DRectangle tr = new DRectangle(x, y, w, h);
/*     */     tile.setViewRect(tr);
/*     */     int jrow = tile.getRowIndex();
/*     */     int jcol = tile.getColumnIndex();
/*     */     for (int irow = 0; irow < this._nrow; irow++) {
/*     */       if (irow != jrow) {
/*     */         Tile ti = this._tiles[irow][jcol];
/*     */         DRectangle dr = ti.getViewRectangle();
/*     */         dr.x = tr.x;
/*     */         dr.width = tr.width;
/*     */         ti.setViewRect(dr);
/*     */       } 
/*     */     } 
/*     */     for (int icol = 0; icol < this._ncol; icol++) {
/*     */       if (icol != jcol) {
/*     */         Tile ti = this._tiles[jrow][icol];
/*     */         DRectangle dr = ti.getViewRectangle();
/*     */         dr.y = tr.y;
/*     */         dr.height = tr.height;
/*     */         ti.setViewRect(dr);
/*     */       } 
/*     */     } 
/*     */     repaintAxis(this._axesTop, jcol);
/*     */     repaintAxis(this._axesBottom, jcol);
/*     */     repaintAxis(this._axesLeft, jrow);
/*     */     repaintAxis(this._axesRight, jrow);
/*     */     this._hsb[jcol].update();
/*     */     this._vsb[jrow].update();
/*     */     if (wvsb != widthVScrollBars() || hhsb != heightHScrollBars()) {
/*     */       revalidate();
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
/*     */   int getHeightAxesTop() {
/*     */     return heightMinimumAxesTop();
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
/*     */   int getHeightAxesBottom() {
/*     */     return heightMinimumAxesBottom();
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
/*     */   int getWidthAxesLeft() {
/*     */     return widthMinimumAxesLeft();
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
/*     */   int getWidthAxesRight() {
/*     */     return widthMinimumAxesRight();
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
/*     */   public Mosaic(int nrow, int ncol, Set<AxesPlacement> axesPlacement) {
/* 744 */     this._wts = 2; this._nrow = nrow; this._ncol = ncol; this._tiles = new Tile[nrow][ncol]; this._tileList = new ArrayList<>(); int k; for (k = 0; k < nrow; k++) { for (int m = 0; m < ncol; m++) { Tile tile = this._tiles[k][m] = new Tile(this, k, m); this._tileList.add(tile); add(tile); }  }  this._axisList = new ArrayList<>(); if (axesPlacement.contains(AxesPlacement.TOP)) { this._axesTop = new TileAxis[ncol]; for (int m = 0; m < ncol; m++) { TileAxis axis = this._axesTop[m] = new TileAxis(this, TileAxis.Placement.TOP, m); this._axisList.add(axis); add(axis); }  }  if (axesPlacement.contains(AxesPlacement.LEFT)) { this._axesLeft = new TileAxis[nrow]; for (k = 0; k < nrow; k++) { TileAxis axis = this._axesLeft[k] = new TileAxis(this, TileAxis.Placement.LEFT, k); this._axisList.add(axis); add(axis); }  }  if (axesPlacement.contains(AxesPlacement.BOTTOM)) { this._axesBottom = new TileAxis[ncol]; for (int m = 0; m < ncol; m++) { TileAxis axis = this._axesBottom[m] = new TileAxis(this, TileAxis.Placement.BOTTOM, m); this._axisList.add(axis); add(axis); }  }  if (axesPlacement.contains(AxesPlacement.RIGHT)) { this._axesRight = new TileAxis[nrow]; for (k = 0; k < nrow; k++) { TileAxis axis = this._axesRight[k] = new TileAxis(this, TileAxis.Placement.RIGHT, k); this._axisList.add(axis); add(axis); }  }  this._vsb = new VScrollBar[this._nrow]; this._hsb = new HScrollBar[this._ncol]; for (k = 0; k < this._nrow; k++) { VScrollBar vsb = this._vsb[k] = new VScrollBar(k); add(vsb); }  for (int j = 0; j < this._ncol; j++) { HScrollBar hsb = this._hsb[j] = new HScrollBar(j); add(hsb); }  for (int i = 0; i < nrow; i++) { for (int m = 0; m < ncol; m++) { Tile tile = this._tiles[i][m]; tile.addMouseWheelListener(new MouseWheelListener() { public void mouseWheelMoved(MouseWheelEvent event) { double u = event.getWheelRotation(); Tile tile = (Tile)event.getSource(); DRectangle vr = tile.getViewRectangle(); if (event.isShiftDown() && vr.width < 1.0D) { Mosaic.HScrollBar hsb = Mosaic.this._hsb[tile.getColumnIndex()]; vr.x += u * hsb.getUnitIncrement(1) * 1.0E-9D; vr.x = Math.max(0.0D, Math.min(1.0D - vr.width, vr.x)); tile.setViewRectangle(vr); } else if (!event.isShiftDown() && vr.height < 1.0D) { Mosaic.VScrollBar vsb = Mosaic.this._vsb[tile.getRowIndex()]; vr.y += u * vsb.getUnitIncrement(1) * 1.0E-9D; vr.y = Math.max(0.0D, Math.min(1.0D - vr.height, vr.y)); tile.setViewRectangle(vr); }
/*     */                  } }); }
/*     */        }
/*     */      this._we = new int[ncol]; this._wm = new int[ncol]; for (int icol = 0; icol < ncol; icol++) { this._we[icol] = 100; this._wm[icol] = 100; }
/*     */      this._he = new int[nrow]; this._hm = new int[nrow]; for (int irow = 0; irow < nrow; irow++) { this._he[irow] = 100; this._hm[irow] = 100; }
/*     */      this._modeManager = new ModeManager(); for (Tile tile : this._tileList)
/*     */       this._modeManager.add(tile);  for (TileAxis axis : this._axisList)
/*     */       this._modeManager.add(axis); 
/* 752 */   } private void repaintAxis(TileAxis[] axes, int index) { if (axes != null)
/* 753 */       repaintAxis(axes[index]);  }
/*     */   
/*     */   private void repaintAxis(TileAxis axis) {
/* 756 */     axis.repaint();
/* 757 */     axis.updateAxisTics();
/*     */   }
/*     */   
/*     */   private int widthAxesBorder() {
/* 761 */     return 0;
/*     */   }
/*     */   
/*     */   private int widthTileBorder() {
/* 765 */     return 1;
/*     */   }
/*     */   
/*     */   private int widthTileSpacing() {
/* 769 */     return this._wts;
/*     */   }
/*     */   
/*     */   private int widthFixed() {
/* 773 */     int width = widthMinimumAxesLeft();
/* 774 */     width += (this._ncol - 1) * widthTileSpacing();
/* 775 */     width += 2 * this._ncol * widthTileBorder();
/* 776 */     width += widthMinimumAxesRight();
/* 777 */     width += widthVScrollBars();
/* 778 */     return width;
/*     */   }
/*     */   
/*     */   private int widthMinimum() {
/* 782 */     int width = widthMinimumAxesLeft();
/* 783 */     for (int icol = 0; icol < this._ncol; icol++)
/* 784 */       width += widthMinimumColumn(icol); 
/* 785 */     width += widthMinimumAxesRight();
/* 786 */     width += (this._ncol - 1) * widthTileSpacing();
/* 787 */     width += widthMinimumVScrollBars();
/* 788 */     return width;
/*     */   }
/*     */   
/*     */   private int widthMinimumColumn(int icol) {
/* 792 */     int width = 0;
/* 793 */     if (this._axesTop != null)
/* 794 */       width = Math.max(width, this._axesTop[icol].getWidthMinimum()); 
/* 795 */     width = Math.max(width, widthMinimumTiles(icol));
/* 796 */     if (this._axesBottom != null)
/* 797 */       width = Math.max(width, this._axesBottom[icol].getWidthMinimum()); 
/* 798 */     return width;
/*     */   }
/*     */   
/*     */   private int widthMinimumTiles(int icol) {
/* 802 */     int width = widthTileBorder();
/* 803 */     width += this._wm[icol];
/* 804 */     width += widthTileBorder();
/* 805 */     return width;
/*     */   }
/*     */   
/*     */   private int widthMinimumAxesLeft() {
/* 809 */     int width = 0;
/* 810 */     if (this._axesLeft != null) {
/* 811 */       for (int irow = 0; irow < this._nrow; irow++)
/* 812 */         width = Math.max(width, this._axesLeft[irow].getWidthMinimum()); 
/* 813 */       width += 2 * widthAxesBorder();
/*     */     } 
/* 815 */     return width;
/*     */   }
/*     */   
/*     */   private int widthMinimumAxesRight() {
/* 819 */     int width = 0;
/* 820 */     if (this._axesRight != null) {
/* 821 */       for (int irow = 0; irow < this._nrow; irow++)
/* 822 */         width = Math.max(width, this._axesRight[irow].getWidthMinimum()); 
/* 823 */       width += 2 * widthAxesBorder();
/*     */     } 
/* 825 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   private int widthMinimumVScrollBars() {
/* 830 */     return 0;
/*     */   }
/*     */   
/*     */   private int widthVScrollBars() {
/* 834 */     for (int irow = 0; irow < this._nrow; irow++) {
/* 835 */       if (this._vsb[irow].isVisible())
/* 836 */         return (this._vsb[irow].getMinimumSize()).width; 
/*     */     } 
/* 838 */     return 0;
/*     */   }
/*     */   
/*     */   private int heightFixed() {
/* 842 */     int height = heightMinimumAxesTop();
/* 843 */     height += (this._nrow - 1) * widthTileSpacing();
/* 844 */     height += 2 * this._nrow * widthTileBorder();
/* 845 */     height += heightMinimumAxesBottom();
/* 846 */     height += heightHScrollBars();
/* 847 */     return height;
/*     */   }
/*     */   
/*     */   private int heightMinimum() {
/* 851 */     int height = heightMinimumAxesTop();
/* 852 */     for (int irow = 0; irow < this._nrow; irow++)
/* 853 */       height += heightMinimumRow(irow); 
/* 854 */     height += heightMinimumAxesBottom();
/* 855 */     height += (this._nrow - 1) * widthTileSpacing();
/* 856 */     height += heightMinimumHScrollBars();
/* 857 */     return height;
/*     */   }
/*     */   
/*     */   private int heightMinimumRow(int irow) {
/* 861 */     int height = 0;
/* 862 */     if (this._axesLeft != null)
/* 863 */       height = Math.max(height, this._axesLeft[irow].getHeightMinimum()); 
/* 864 */     height = Math.max(height, heightMinimumTiles(irow));
/* 865 */     if (this._axesRight != null)
/* 866 */       height = Math.max(height, this._axesRight[irow].getHeightMinimum()); 
/* 867 */     return height;
/*     */   }
/*     */   
/*     */   private int heightMinimumTiles(int irow) {
/* 871 */     int height = widthTileBorder();
/* 872 */     height += this._hm[irow];
/* 873 */     height += widthTileBorder();
/* 874 */     return height;
/*     */   }
/*     */   
/*     */   private int heightMinimumAxesTop() {
/* 878 */     int height = 0;
/* 879 */     if (this._axesTop != null) {
/* 880 */       for (int icol = 0; icol < this._ncol; icol++)
/* 881 */         height = Math.max(height, this._axesTop[icol].getHeightMinimum()); 
/* 882 */       height += 2 * widthAxesBorder();
/*     */     } 
/* 884 */     return height;
/*     */   }
/*     */   
/*     */   private int heightMinimumAxesBottom() {
/* 888 */     int height = 0;
/* 889 */     if (this._axesBottom != null) {
/* 890 */       for (int icol = 0; icol < this._ncol; icol++)
/* 891 */         height = Math.max(height, this._axesBottom[icol].getHeightMinimum()); 
/* 892 */       height += 2 * widthAxesBorder();
/*     */     } 
/* 894 */     return height;
/*     */   }
/*     */ 
/*     */   
/*     */   private int heightMinimumHScrollBars() {
/* 899 */     return 0;
/*     */   }
/*     */   
/*     */   private int heightHScrollBars() {
/* 903 */     for (int icol = 0; icol < this._ncol; icol++) {
/* 904 */       if (this._hsb[icol].isVisible())
/* 905 */         return (this._hsb[icol].getMinimumSize()).height; 
/*     */     } 
/* 907 */     return 0;
/*     */   }
/*     */   
/*     */   private class TileScrollBar
/*     */     extends JScrollBar
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     Tile tile;
/*     */     private boolean _settingInternal;
/*     */     
/*     */     TileScrollBar(int orientation, final Tile tile) {
/* 918 */       super(orientation, 0, 1000000000, 0, 1000000000);
/* 919 */       setVisible(false);
/* 920 */       this.tile = tile;
/* 921 */       addAdjustmentListener(new AdjustmentListener() {
/*     */             public void adjustmentValueChanged(AdjustmentEvent ae) {
/* 923 */               if (Mosaic.TileScrollBar.this._settingInternal)
/*     */                 return; 
/* 925 */               DRectangle vr = tile.getViewRectangle();
/* 926 */               if (Mosaic.TileScrollBar.this.getOrientation() == 0) {
/* 927 */                 vr.x = Mosaic.TileScrollBar.this.getV();
/* 928 */                 vr.width = Mosaic.TileScrollBar.this.getE();
/*     */               } else {
/* 930 */                 vr.y = Mosaic.TileScrollBar.this.getV();
/* 931 */                 vr.height = Mosaic.TileScrollBar.this.getE();
/*     */               } 
/* 933 */               tile.setViewRectangle(vr);
/*     */             }
/*     */           });
/*     */     }
/*     */     void setV(double v) {
/* 938 */       this._settingInternal = true;
/* 939 */       setValue((int)(v * 1.0E9D + 0.5D));
/* 940 */       this._settingInternal = false;
/*     */     }
/*     */     void setE(double e) {
/* 943 */       this._settingInternal = true;
/* 944 */       setVisibleAmount((int)(e * 1.0E9D + 0.5D));
/* 945 */       setVisible((getVisibleAmount() < 1000000000));
/* 946 */       setUnitIncrement((int)(0.05D * e * 1.0E9D + 0.5D));
/* 947 */       setBlockIncrement((int)(0.5D * e * 1.0E9D + 0.5D));
/* 948 */       this._settingInternal = false;
/*     */     }
/*     */     double getV() {
/* 951 */       return 1.0E-9D * getValue();
/*     */     }
/*     */     double getE() {
/* 954 */       return 1.0E-9D * getVisibleAmount();
/*     */     }
/*     */     void update() {
/* 957 */       DRectangle vr = this.tile.getViewRectangle();
/* 958 */       if (getOrientation() == 0) {
/* 959 */         setV(vr.x);
/* 960 */         setE(vr.width);
/*     */       } else {
/* 962 */         setV(vr.y);
/* 963 */         setE(vr.height);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class HScrollBar extends TileScrollBar { private static final long serialVersionUID = 1L;
/*     */     int icol;
/*     */     
/*     */     HScrollBar(int icol) {
/* 972 */       super(0, Mosaic.this._tiles[0][icol]);
/* 973 */       this.icol = icol;
/*     */     } }
/*     */   
/*     */   private class VScrollBar extends TileScrollBar { private static final long serialVersionUID = 1L;
/*     */     int irow;
/*     */     
/*     */     VScrollBar(int irow) {
/* 980 */       super(1, Mosaic.this._tiles[irow][0]);
/* 981 */       this.irow = irow;
/*     */     } }
/*     */ 
/*     */   
/*     */   private void updateScrollBars() {
/* 986 */     for (int icol = 0; icol < this._ncol; icol++)
/* 987 */       this._hsb[icol].update(); 
/* 988 */     for (int irow = 0; irow < this._nrow; irow++)
/* 989 */       this._vsb[irow].update(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/Mosaic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
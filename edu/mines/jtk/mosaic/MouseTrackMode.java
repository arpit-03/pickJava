/*     */ package edu.mines.jtk.mosaic;
/*     */ 
/*     */ import edu.mines.jtk.awt.Mode;
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MouseTrackMode
/*     */   extends Mode
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Tile _tile;
/*     */   private int _xmouse;
/*     */   private int _ymouse;
/*     */   private MouseListener _ml;
/*     */   private MouseMotionListener _mml;
/*     */   
/*     */   public MouseTrackMode(ModeManager modeManager) {
/*  39 */     super(modeManager);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     this._ml = new MouseAdapter() {
/*     */         public void mouseEntered(MouseEvent e) {
/*  77 */           MouseTrackMode.this.beginTracking(e);
/*     */         }
/*     */         public void mouseExited(MouseEvent e) {
/*  80 */           MouseTrackMode.this.endTracking();
/*     */         }
/*     */       };
/*     */     
/*  84 */     this._mml = new MouseMotionAdapter() {
/*     */         public void mouseDragged(MouseEvent e) {
/*  86 */           MouseTrackMode.this.duringTracking(e);
/*     */         }
/*     */         
/*  89 */         public void mouseMoved(MouseEvent e) { MouseTrackMode.this.duringTracking(e); }
/*     */       }; setName("Track");
/*     */     setIcon(loadIcon(MouseTrackMode.class, "Track24.gif"));
/*     */     setMnemonicKey(90);
/*     */     setAcceleratorKey(KeyStroke.getKeyStroke(84, 0));
/*  94 */     setShortDescription("Track mouse in tile"); } public boolean isExclusive() { return false; } private void beginTracking(MouseEvent e) { this._xmouse = e.getX();
/*  95 */     this._ymouse = e.getY();
/*  96 */     this._tile = (Tile)e.getSource();
/*  97 */     beginTracking(this._tile.getTileAxisTop(), this._xmouse, this._ymouse);
/*  98 */     beginTracking(this._tile.getTileAxisLeft(), this._xmouse, this._ymouse);
/*  99 */     beginTracking(this._tile.getTileAxisBottom(), this._xmouse, this._ymouse);
/* 100 */     beginTracking(this._tile.getTileAxisRight(), this._xmouse, this._ymouse);
/* 101 */     fireTrack();
/* 102 */     this._tile.addMouseMotionListener(this._mml); }
/*     */   protected void setActive(Component component, boolean active) { if (component instanceof Tile)
/*     */       if (active) { component.addMouseListener(this._ml); } else { component.removeMouseListener(this._ml); }
/* 105 */         } private void beginTracking(TileAxis ta, int x, int y) { if (ta != null)
/* 106 */       ta.beginTracking(x, y);  }
/*     */ 
/*     */   
/*     */   private void duringTracking(MouseEvent e) {
/* 110 */     this._xmouse = e.getX();
/* 111 */     this._ymouse = e.getY();
/* 112 */     this._tile = (Tile)e.getSource();
/* 113 */     duringTracking(this._tile.getTileAxisTop(), this._xmouse, this._ymouse);
/* 114 */     duringTracking(this._tile.getTileAxisLeft(), this._xmouse, this._ymouse);
/* 115 */     duringTracking(this._tile.getTileAxisBottom(), this._xmouse, this._ymouse);
/* 116 */     duringTracking(this._tile.getTileAxisRight(), this._xmouse, this._ymouse);
/* 117 */     fireTrack();
/*     */   }
/*     */   private void duringTracking(TileAxis ta, int x, int y) {
/* 120 */     if (ta != null)
/* 121 */       ta.duringTracking(x, y); 
/*     */   }
/*     */   
/*     */   private void endTracking() {
/* 125 */     this._tile.removeMouseMotionListener(this._mml);
/* 126 */     endTracking(this._tile.getTileAxisTop());
/* 127 */     endTracking(this._tile.getTileAxisLeft());
/* 128 */     endTracking(this._tile.getTileAxisBottom());
/* 129 */     endTracking(this._tile.getTileAxisRight());
/* 130 */     fireTrack();
/* 131 */     this._tile = null;
/*     */   }
/*     */   private void endTracking(TileAxis ta) {
/* 134 */     if (ta != null)
/* 135 */       ta.endTracking(); 
/*     */   }
/*     */   
/*     */   private void fireTrack() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mosaic/MouseTrackMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
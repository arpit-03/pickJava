/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.Mode;
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.KeyAdapter;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.KeyStroke;
/*     */ 
/*     */ public class OrbitViewMode extends Mode {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private ViewCanvas _canvas;
/*     */   private OrbitView _view;
/*     */   private int _xmouse;
/*     */   private int _ymouse;
/*     */   private double _zmouse;
/*     */   private double _scale;
/*     */   private double _azimuth;
/*     */   private double _elevation;
/*     */   private Vector3 _translate;
/*     */   private Point3 _translateP;
/*     */   private Matrix44 _translateM;
/*     */   private boolean _rotating;
/*     */   private boolean _scaling;
/*     */   private boolean _translating;
/*     */   private KeyListener _kl;
/*     */   private MouseListener _ml;
/*     */   private MouseMotionListener _mml;
/*     */   
/*     */   public OrbitViewMode(ModeManager modeManager) {
/*  37 */     super(modeManager);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     this._kl = new KeyAdapter() {
/*     */         public void keyPressed(KeyEvent e) {
/*  85 */           ViewCanvas canvas = (ViewCanvas)e.getSource();
/*  86 */           OrbitView view = (OrbitView)canvas.getView();
/*  87 */           int kc = e.getKeyCode();
/*     */ 
/*     */           
/*  90 */           if (kc == 36) {
/*  91 */             view.reset();
/*     */           
/*     */           }
/*  94 */           else if (kc == 35) {
/*  95 */             float azimuth = (float)view.getAzimuth();
/*  96 */             float elevation = (float)view.getElevation();
/*  97 */             float scale = (float)view.getScale();
/*  98 */             Vector3 t = view.getTranslate();
/*  99 */             System.out.println("OrbitView: azimuth=" + azimuth);
/* 100 */             System.out.println("           elevation=" + elevation);
/* 101 */             System.out.println("           scale=" + scale);
/* 102 */             System.out.println("           translate=(" + t.x + "," + t.y + "," + t.z + ")");
/*     */           
/*     */           }
/* 105 */           else if (kc == 34) {
/* 106 */             Tuple3 s = view.getAxesScale();
/* 107 */             view.setAxesScale(s.x, s.y, s.z / 1.1D);
/*     */           
/*     */           }
/* 110 */           else if (kc == 33) {
/* 111 */             Tuple3 s = view.getAxesScale();
/* 112 */             view.setAxesScale(s.x, s.y, s.z * 1.1D);
/*     */           
/*     */           }
/* 115 */           else if (kc == 155) {
/* 116 */             OrbitView.Projection projection = view.getProjection();
/* 117 */             if (projection == OrbitView.Projection.ORTHOGRAPHIC) {
/* 118 */               projection = OrbitView.Projection.PERSPECTIVE;
/* 119 */             } else if (projection == OrbitView.Projection.PERSPECTIVE) {
/* 120 */               projection = OrbitView.Projection.ORTHOGRAPHIC;
/*     */             } 
/* 122 */             view.setProjection(projection);
/*     */ 
/*     */ 
/*     */           
/*     */           }
/* 127 */           else if (e.isControlDown() || e.isAltDown()) {
/* 128 */             double scale = view.getScale();
/* 129 */             if (kc == 38) {
/* 130 */               scale *= 0.9D;
/* 131 */             } else if (kc == 40) {
/* 132 */               scale *= 1.1D;
/*     */             } 
/* 134 */             view.setScale(scale);
/*     */ 
/*     */           
/*     */           }
/* 138 */           else if (e.isShiftDown()) {
/* 139 */             Matrix44 viewToCube = OrbitViewMode.this._canvas.getViewToCube();
/* 140 */             Matrix44 unitSphereToView = OrbitViewMode.this._view.getUnitSphereToView();
/* 141 */             Matrix44 unitSphereToCube = viewToCube.times(unitSphereToView);
/* 142 */             Matrix44 cubeToUnitSphere = unitSphereToCube.inverse();
/* 143 */             Vector3 translate = view.getTranslate();
/* 144 */             Matrix44 m = Matrix44.translate(translate).times(cubeToUnitSphere);
/* 145 */             double xc = 0.0D;
/* 146 */             double yc = 0.0D;
/* 147 */             double zc = 0.0D;
/* 148 */             Point3 c1 = new Point3(xc, yc, zc);
/* 149 */             if (kc == 37) {
/* 150 */               xc -= 0.05D;
/* 151 */             } else if (kc == 39) {
/* 152 */               xc += 0.05D;
/* 153 */             } else if (kc == 38) {
/* 154 */               yc += 0.05D;
/* 155 */             } else if (kc == 40) {
/* 156 */               yc -= 0.05D;
/*     */             } 
/* 158 */             Point3 c2 = new Point3(xc, yc, zc);
/* 159 */             translate.plusEquals(m.times(c2).minus(m.times(c1)));
/* 160 */             view.setTranslate(translate);
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */             
/* 166 */             double azimuth = view.getAzimuth();
/* 167 */             double elevation = view.getElevation();
/* 168 */             if (kc == 37) {
/* 169 */               azimuth += 5.0D;
/* 170 */             } else if (kc == 39) {
/* 171 */               azimuth -= 5.0D;
/* 172 */             } else if (kc == 38) {
/* 173 */               elevation -= 5.0D;
/* 174 */             } else if (kc == 40) {
/* 175 */               elevation += 5.0D;
/*     */             } 
/* 177 */             view.setAzimuthAndElevation(azimuth, elevation);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 182 */     this._ml = new MouseAdapter() {
/*     */         public void mousePressed(MouseEvent e) {
/* 184 */           if (e.isControlDown() || e.isAltDown()) {
/* 185 */             OrbitViewMode.this.beginScale(e);
/* 186 */             OrbitViewMode.this._scaling = true;
/* 187 */           } else if (e.isShiftDown()) {
/* 188 */             OrbitViewMode.this.beginTranslate(e);
/* 189 */             OrbitViewMode.this._translating = true;
/*     */           } else {
/* 191 */             OrbitViewMode.this.beginRotate(e);
/* 192 */             OrbitViewMode.this._rotating = true;
/*     */           } 
/*     */         }
/*     */         public void mouseReleased(MouseEvent e) {
/* 196 */           if (OrbitViewMode.this._scaling) {
/* 197 */             OrbitViewMode.this.endScale();
/* 198 */             OrbitViewMode.this._scaling = false;
/* 199 */           } else if (OrbitViewMode.this._translating) {
/* 200 */             OrbitViewMode.this.endTranslate();
/* 201 */             OrbitViewMode.this._translating = false;
/* 202 */           } else if (OrbitViewMode.this._rotating) {
/* 203 */             OrbitViewMode.this.endRotate();
/* 204 */             OrbitViewMode.this._rotating = false;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 209 */     this._mml = new MouseMotionAdapter()
/*     */       {
/* 211 */         public void mouseDragged(MouseEvent e) { if (OrbitViewMode.this._scaling) {
/* 212 */             OrbitViewMode.this.duringScale(e);
/* 213 */           } else if (OrbitViewMode.this._translating) {
/* 214 */             OrbitViewMode.this.duringTranslate(e);
/* 215 */           } else if (OrbitViewMode.this._rotating) {
/* 216 */             OrbitViewMode.this.duringRotate(e);
/*     */           }  } }; setName("View"); Class<OrbitViewMode> cls = OrbitViewMode.class;
/*     */     setIcon(loadIcon(cls, "ViewHandIcon16.png"));
/*     */     setCursor(loadCursor(cls, "ViewHandCursor16.png", 3, 2));
/*     */     setMnemonicKey(32);
/*     */     setAcceleratorKey(KeyStroke.getKeyStroke(32, 0));
/* 222 */     setShortDescription("Manipulate view"); } private void beginScale(MouseEvent e) { this._ymouse = e.getY();
/* 223 */     this._canvas = (ViewCanvas)e.getSource();
/* 224 */     this._canvas.addMouseMotionListener(this._mml);
/* 225 */     this._view = (OrbitView)this._canvas.getView();
/* 226 */     this._scale = this._view.getScale(); }
/*     */   protected void setActive(Component component, boolean active) { if (component instanceof ViewCanvas)
/*     */       if (active) { component.addMouseListener(this._ml); component.addKeyListener(this._kl); }
/*     */       else { component.removeMouseListener(this._ml); component.removeKeyListener(this._kl); this._rotating = false; this._scaling = false; this._translating = false; }
/* 230 */         } private void duringScale(MouseEvent e) { int h = this._canvas.getHeight();
/* 231 */     int y = e.getY();
/* 232 */     int dy = y - this._ymouse;
/* 233 */     double ds = 2.0D * dy / h;
/* 234 */     this._view.setScale(this._scale * Math.pow(10.0D, ds)); }
/*     */ 
/*     */   
/*     */   private void endScale() {
/* 238 */     this._canvas.removeMouseMotionListener(this._mml);
/*     */   }
/*     */   
/*     */   private void beginTranslate(MouseEvent e) {
/* 242 */     this._xmouse = e.getX();
/* 243 */     this._ymouse = e.getY();
/* 244 */     this._canvas = (ViewCanvas)e.getSource();
/* 245 */     this._canvas.addMouseMotionListener(this._mml);
/* 246 */     this._view = (OrbitView)this._canvas.getView();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 252 */     this._zmouse = this._canvas.getPixelZ(this._xmouse, this._ymouse);
/* 253 */     if (this._zmouse == 1.0D) {
/* 254 */       this._zmouse = 0.5D;
/*     */     }
/*     */     
/* 257 */     Matrix44 cubeToPixel = this._canvas.getCubeToPixel();
/* 258 */     Matrix44 viewToCube = this._canvas.getViewToCube();
/* 259 */     Matrix44 viewToPixel = cubeToPixel.times(viewToCube);
/* 260 */     Matrix44 unitSphereToView = this._view.getUnitSphereToView();
/* 261 */     Matrix44 unitSphereToPixel = viewToPixel.times(unitSphereToView);
/* 262 */     Matrix44 pixelToUnitSphere = unitSphereToPixel.inverse();
/*     */ 
/*     */     
/* 265 */     this._translate = this._view.getTranslate();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     this._translateM = Matrix44.translate(this._translate).times(pixelToUnitSphere);
/*     */ 
/*     */     
/* 273 */     this._translateP = this._translateM.times(new Point3(this._xmouse, this._ymouse, this._zmouse));
/*     */   }
/*     */   
/*     */   private void duringTranslate(MouseEvent e) {
/* 277 */     int x = e.getX();
/* 278 */     int y = e.getY();
/* 279 */     Point3 p = new Point3(x, y, this._zmouse);
/* 280 */     Vector3 t = this._translate.plus(this._translateM.times(p).minus(this._translateP));
/* 281 */     this._view.setTranslate(t);
/*     */   }
/*     */   
/*     */   private void endTranslate() {
/* 285 */     this._canvas.removeMouseMotionListener(this._mml);
/*     */   }
/*     */   
/*     */   private void beginRotate(MouseEvent e) {
/* 289 */     this._xmouse = e.getX();
/* 290 */     this._ymouse = e.getY();
/* 291 */     this._canvas = (ViewCanvas)e.getSource();
/* 292 */     this._canvas.addMouseMotionListener(this._mml);
/* 293 */     this._view = (OrbitView)this._canvas.getView();
/* 294 */     this._azimuth = this._view.getAzimuth();
/* 295 */     this._elevation = this._view.getElevation();
/*     */   }
/*     */   
/*     */   private void duringRotate(MouseEvent e) {
/* 299 */     int w = this._canvas.getWidth();
/* 300 */     int h = this._canvas.getHeight();
/* 301 */     int x = e.getX();
/* 302 */     int y = e.getY();
/* 303 */     int dx = x - this._xmouse;
/* 304 */     int dy = y - this._ymouse;
/* 305 */     double da = -360.0D * dx / w;
/* 306 */     double de = 360.0D * dy / h;
/* 307 */     this._view.setAzimuthAndElevation(this._azimuth + da, this._elevation + de);
/*     */   }
/*     */   
/*     */   private void endRotate() {
/* 311 */     this._canvas.removeMouseMotionListener(this._mml);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/OrbitViewMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.ogl.Gl;
/*     */ import edu.mines.jtk.util.Stopwatch;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Toolkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrbitView
/*     */   extends View
/*     */ {
/*     */   private double _scale;
/*     */   private Vector3 _translate;
/*     */   private double _azimuth;
/*     */   private double _elevation;
/*     */   private double _esd;
/*     */   private OrbitViewLighting _orbitViewLighting;
/*     */   private Projection _projection;
/*     */   private BoundingSphere _worldSphere;
/*     */   private Matrix44 _worldToUnitSphere;
/*     */   private Matrix44 _unitSphereToView;
/*     */   private Stopwatch _stopwatch;
/*     */   private int _ndraw;
/*     */   
/*     */   public enum Projection
/*     */   {
/*  71 */     PERSPECTIVE, ORTHOGRAPHIC;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrbitView()
/*     */   {
/* 459 */     this._orbitViewLighting = new OrbitViewLighting();
/* 460 */     this._projection = Projection.PERSPECTIVE;
/* 461 */     this._worldSphere = null; init(); } public OrbitView(World world) { super(world); this._orbitViewLighting = new OrbitViewLighting(); this._projection = Projection.PERSPECTIVE; this._worldSphere = null; init(); updateTransforms(); }
/*     */   public void reset() { init(); updateView(); }
/*     */   public void setWorldSphere(BoundingSphere worldSphere) { this._worldSphere = worldSphere; updateView(); }
/*     */   public BoundingSphere getWorldSphere() { return new BoundingSphere(this._worldSphere); }
/*     */   public void setProjection(Projection projection) { if (this._projection == projection) return;  this._projection = projection; updateView(); }
/*     */   public Projection getProjection() { return this._projection; }
/*     */   public void setAzimuth(double azimuth) { if (this._azimuth == azimuth) return;  this._azimuth = azimuth; updateView(); }
/* 468 */   public double getAzimuth() { return this._azimuth; } public void setElevation(double elevation) { if (this._elevation == elevation) return;  this._elevation = elevation; updateView(); } public double getElevation() { return this._elevation; } public void setAzimuthAndElevation(double azimuth, double elevation) { if (this._azimuth == azimuth && this._elevation == elevation) return;  this._azimuth = azimuth; this._elevation = elevation; updateView(); } private void init() { this._scale = 1.0D;
/* 469 */     this._translate = new Vector3(0.0D, 0.0D, 0.0D);
/* 470 */     this._azimuth = 40.0D;
/* 471 */     this._elevation = 25.0D;
/* 472 */     this._projection = Projection.PERSPECTIVE;
/* 473 */     this._ndraw = 0;
/* 474 */     this._stopwatch = new Stopwatch();
/* 475 */     this._stopwatch.start(); }
/*     */   public void setScale(double scale) { if (this._scale == scale) return;  this._scale = scale; updateView(); }
/*     */   public double getScale() { return this._scale; }
/*     */   public void setTranslate(Vector3 translate) { if (this._translate.equals(translate)) return;  this._translate = new Vector3(translate); updateView(); }
/* 479 */   public Vector3 getTranslate() { return new Vector3(this._translate); } public Matrix44 getWorldToUnitSphere() { return new Matrix44(this._worldToUnitSphere); } public Matrix44 getUnitSphereToView() { return new Matrix44(this._unitSphereToView); } public void setEyeToScreenDistance(double esd) { this._esd = esd; } public void setOrbitViewLighting(OrbitViewLighting orbitViewLighting) { this._orbitViewLighting = orbitViewLighting; } public OrbitViewLighting getOrbitViewLighting() { return this._orbitViewLighting; } protected void updateTransforms(ViewCanvas canvas) { Matrix44 viewToCube; double distance; int w = canvas.getWidth(); int h = canvas.getHeight(); if (w == 0 || h == 0) return;  double ss = this._esd; if (ss <= 0.0D) { Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); double xs = screenSize.width; double ys = screenSize.height; ss = Math.sqrt(xs * xs + ys * ys); }  Matrix44 cubeToPixel = Matrix44.identity(); cubeToPixel.timesEquals(Matrix44.translate(0.5D * w, 0.5D * h, 0.5D)); cubeToPixel.timesEquals(Matrix44.scale(0.5D * w, -0.5D * h, 0.5D)); canvas.setCubeToPixel(cubeToPixel); double maxscale = 3.0D; if (this._projection == Projection.PERSPECTIVE) { double r = 1.0D; double e = ss; double m = Math.min(w, h); double a = 2.0D * Math.atan(m / 2.0D * e); double d = r / Math.sin(a / 2.0D); double fovy = 2.0D * Math.atan(h / 2.0D * e) * 180.0D / Math.PI; double aspect = w / h; double znear = Math.max(d - maxscale * r, 0.1D); double zfar = Math.max(d + maxscale * r, 100.0D * znear); viewToCube = Matrix44.perspective(fovy, aspect, znear, zfar); distance = d; } else { double r = 1.0D; double m = Math.min(w, h); double d = maxscale * r; double right = w / m; double left = -right; double top = h / m; double bottom = -top; double znear = 0.0D; double zfar = 2.0D * maxscale * r; viewToCube = Matrix44.ortho(left, right, bottom, top, znear, zfar); distance = d; }  canvas.setViewToCube(viewToCube); this._unitSphereToView = Matrix44.identity(); this._unitSphereToView.timesEquals(Matrix44.translate(0.0D, 0.0D, -distance)); this._unitSphereToView.timesEquals(Matrix44.rotateX(this._elevation)); this._unitSphereToView.timesEquals(Matrix44.rotateY(-this._azimuth)); this._unitSphereToView.timesEquals(Matrix44.scale(this._scale, this._scale, this._scale)); this._unitSphereToView.timesEquals(Matrix44.translate(this._translate)); Tuple3 as = getAxesScale(); AxesOrientation ao = getAxesOrientation(); this._worldToUnitSphere = Matrix44.identity(); World world = getWorld(); if (world != null) { if (this._worldSphere == null) this._worldSphere = world.getBoundingSphere(true);  BoundingSphere ws = this._worldSphere; Point3 c = !ws.isEmpty() ? ws.getCenter() : new Point3(); double tx = -c.x; double ty = -c.y; double tz = -c.z; double r = !ws.isEmpty() ? ws.getRadius() : 1.0D; double s = (r > 0.0D) ? (1.0D / r) : 1.0D; double sx = s * as.x; double sy = s * as.y; double sz = s * as.z; if (ao == AxesOrientation.XRIGHT_YUP_ZOUT) { this._worldToUnitSphere.timesEquals(Matrix44.identity()); } else if (ao == AxesOrientation.XRIGHT_YOUT_ZDOWN) { this._worldToUnitSphere.timesEquals(Matrix44.rotateX(90.0D)); } else if (ao == AxesOrientation.XRIGHT_YIN_ZDOWN) { this._worldToUnitSphere.timesEquals(Matrix44.rotateX(90.0D)); sy = -sy; } else if (ao == AxesOrientation.XOUT_YRIGHT_ZUP) { this._worldToUnitSphere.timesEquals(Matrix44.rotateY(-90.0D)); this._worldToUnitSphere.timesEquals(Matrix44.rotateX(-90.0D)); } else if (ao == AxesOrientation.XDOWN_YRIGHT_ZOUT) { this._worldToUnitSphere.timesEquals(Matrix44.rotateZ(-90.0D)); } else if (ao == AxesOrientation.XUP_YLEFT_ZOUT) { this._worldToUnitSphere.timesEquals(Matrix44.rotateZ(90.0D)); } else if (ao == AxesOrientation.XUP_YRIGHT_ZOUT) { this._worldToUnitSphere.timesEquals(Matrix44.rotateZ(90.0D)); sy = -sy; }  this._worldToUnitSphere.timesEquals(Matrix44.scale(sx, sy, sz)); this._worldToUnitSphere.timesEquals(Matrix44.translate(tx, ty, tz)); }  setWorldToView(this._unitSphereToView.times(this._worldToUnitSphere)); } protected void draw(ViewCanvas canvas) { Color c = canvas.getBackground(); float r = c.getRed() / 255.0F; float g = c.getGreen() / 255.0F; float b = c.getBlue() / 255.0F; Gl.glClearColor(r, g, b, 0.0F); Gl.glClear(16640); Gl.glEnable(3042); Gl.glBlendFunc(770, 771); Gl.glEnable(2848); Gl.glEnable(2832); Gl.glHint(3154, 4354); Gl.glHint(3153, 4354); Gl.glEnable(2977); Gl.glEnable(2929); World world = getWorld(); if (world == null) return;  int w = canvas.getSurfaceWidth(); int h = canvas.getSurfaceHeight(); Gl.glViewport(0, 0, w, h); Matrix44 viewToCube = canvas.getViewToCube(); Gl.glMatrixMode(5889); Gl.glLoadMatrixd(viewToCube.m, 0); Gl.glMatrixMode(5888); Gl.glLoadIdentity(); this._orbitViewLighting.draw(); Matrix44 worldToView = getWorldToView(); Gl.glLoadMatrixd(worldToView.m, 0); CullContext cc = new CullContext(canvas); world.cullApply(cc); DrawList dl = cc.getDrawList(); DrawContext dc = new DrawContext(canvas); dl.draw(dc); this._ndraw++; if (this._stopwatch.time() > 2.0D) { this._stopwatch.stop(); this._ndraw = 0; this._stopwatch.restart(); }  } private void updateView() { updateTransforms();
/* 480 */     repaint(); }
/*     */ 
/*     */   
/*     */   private static void trace(String s) {
/* 484 */     System.out.println(s);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/OrbitView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.awt.ModeManager;
/*     */ import edu.mines.jtk.awt.ModeToggleButton;
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JToolBar;
/*     */ import javax.swing.ToolTipManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleFrame
/*     */   extends JFrame
/*     */ {
/*     */   private ViewCanvas _canvas;
/*     */   private OrbitView _view;
/*     */   private World _world;
/*     */   private ModeManager _modeManager;
/*     */   private JToolBar _toolBar;
/*     */   private static final int SIZE = 600;
/*     */   
/*     */   public SimpleFrame() {
/*  36 */     this((World)null, (AxesOrientation)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFrame(AxesOrientation ao) {
/*  44 */     this((World)null, ao);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFrame(World world) {
/*  52 */     this(world, (AxesOrientation)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFrame(World world, AxesOrientation ao) {
/*  61 */     if (world == null) world = new World(); 
/*  62 */     if (ao == null) ao = AxesOrientation.XRIGHT_YOUT_ZDOWN; 
/*  63 */     this._world = world;
/*  64 */     this._view = new OrbitView(this._world);
/*  65 */     this._view.setAxesOrientation(ao);
/*  66 */     this._canvas = new ViewCanvas();
/*  67 */     this._canvas.setView(this._view);
/*     */     
/*  69 */     this._modeManager = new ModeManager();
/*  70 */     this._modeManager.add((Component)this._canvas);
/*  71 */     OrbitViewMode ovm = new OrbitViewMode(this._modeManager);
/*  72 */     SelectDragMode sdm = new SelectDragMode(this._modeManager);
/*     */     
/*  74 */     JPopupMenu.setDefaultLightWeightPopupEnabled(false);
/*  75 */     ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
/*     */     
/*  77 */     JMenu fileMenu = new JMenu("File");
/*  78 */     fileMenu.setMnemonic('F');
/*  79 */     Action exitAction = new AbstractAction("Exit") {
/*     */         public void actionPerformed(ActionEvent event) {
/*  81 */           System.exit(0);
/*     */         }
/*     */       };
/*  84 */     JMenuItem exitItem = fileMenu.add(exitAction);
/*  85 */     exitItem.setMnemonic('X');
/*     */     
/*  87 */     JMenu modeMenu = new JMenu("Mode");
/*  88 */     modeMenu.setMnemonic('M');
/*  89 */     JMenuItem ovmItem = new JMenuItem((Action)ovm);
/*  90 */     modeMenu.add(ovmItem);
/*  91 */     JMenuItem sdmItem = new JMenuItem((Action)sdm);
/*  92 */     modeMenu.add(sdmItem);
/*     */     
/*  94 */     JMenuBar menuBar = new JMenuBar();
/*  95 */     menuBar.add(fileMenu);
/*  96 */     menuBar.add(modeMenu);
/*     */     
/*  98 */     this._toolBar = new JToolBar(1);
/*  99 */     this._toolBar.setRollover(true);
/* 100 */     ModeToggleButton modeToggleButton1 = new ModeToggleButton(ovm);
/* 101 */     this._toolBar.add((Component)modeToggleButton1);
/* 102 */     ModeToggleButton modeToggleButton2 = new ModeToggleButton(sdm);
/* 103 */     this._toolBar.add((Component)modeToggleButton2);
/*     */     
/* 105 */     ovm.setActive(true);
/*     */     
/* 107 */     setDefaultCloseOperation(3);
/* 108 */     setSize(new Dimension(600, 600));
/* 109 */     add((Component)this._canvas, "Center");
/* 110 */     add(this._toolBar, "West");
/* 111 */     setJMenuBar(menuBar);
/* 112 */     setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModeManager getModeManager() {
/* 120 */     return this._modeManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JToolBar getJToolBar() {
/* 128 */     return this._toolBar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asTriangles(float[] xyz) {
/* 138 */     return asTriangles(true, xyz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asTriangles(boolean vn, float[] xyz) {
/* 148 */     return asTriangles(vn, xyz, (float[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asTriangles(float[] xyz, float[] rgb) {
/* 159 */     return asTriangles(true, xyz, rgb);
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
/*     */   public static SimpleFrame asTriangles(boolean vn, Sampling sx, Sampling sy, float[][] z) {
/* 171 */     return asTriangles(new TriangleGroup(vn, sx, sy, z));
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
/*     */   public static SimpleFrame asTriangles(boolean vn, Sampling sx, Sampling sy, float[][] z, float[][] r, float[][] g, float[][] b) {
/* 188 */     return asTriangles(new TriangleGroup(vn, sx, sy, z, r, g, b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asTriangles(boolean vn, float[] xyz, float[] rgb) {
/* 199 */     return asTriangles(new TriangleGroup(vn, xyz, rgb));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asTriangles(TriangleGroup tg) {
/* 208 */     SimpleFrame sf = new SimpleFrame();
/* 209 */     sf.addTriangles(tg);
/* 210 */     sf.getOrbitView().setWorldSphere(tg.getBoundingSphere(true));
/* 211 */     return sf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asImagePanels(float[][][] f) {
/* 220 */     return asImagePanels(new ImagePanelGroup(f));
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
/*     */   public static SimpleFrame asImagePanels(Sampling s1, Sampling s2, Sampling s3, float[][][] f) {
/* 233 */     return asImagePanels(new ImagePanelGroup(s1, s2, s3, f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SimpleFrame asImagePanels(ImagePanelGroup ipg) {
/* 242 */     SimpleFrame sf = new SimpleFrame();
/* 243 */     sf.addImagePanels(ipg);
/* 244 */     sf.getOrbitView().setWorldSphere(ipg.getBoundingSphere(true));
/* 245 */     return sf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriangleGroup addTriangles(float[] xyz) {
/* 254 */     return addTriangles(new TriangleGroup(true, xyz, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriangleGroup addTriangles(float[] xyz, float[] rgb) {
/* 264 */     return addTriangles(new TriangleGroup(true, xyz, rgb));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriangleGroup addTriangles(Sampling sx, Sampling sy, float[][] z) {
/* 274 */     return addTriangles(new TriangleGroup(true, sx, sy, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TriangleGroup addTriangles(TriangleGroup tg) {
/* 283 */     this._world.addChild(tg);
/* 284 */     return tg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanelGroup addImagePanels(float[][][] f) {
/* 293 */     return addImagePanels(new Sampling((f[0][0]).length), new Sampling((f[0]).length), new Sampling(f.length), f);
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
/*     */   public ImagePanelGroup addImagePanels(Sampling s1, Sampling s2, Sampling s3, float[][][] f) {
/* 310 */     return addImagePanels(new ImagePanelGroup(s1, s2, s3, f));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePanelGroup addImagePanels(ImagePanelGroup ipg) {
/* 320 */     this._world.addChild(ipg);
/* 321 */     return ipg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ViewCanvas getViewCanvas() {
/* 329 */     return this._canvas;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrbitView getOrbitView() {
/* 337 */     return this._view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 345 */     return this._world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldSphere(Point3 p, int r) {
/* 355 */     setWorldSphere(new BoundingSphere(p, r));
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
/*     */   public void setWorldSphere(double x, double y, double z, double r) {
/* 367 */     setWorldSphere(new BoundingSphere(x, y, z, r));
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
/*     */   public void setWorldSphere(double xmin, double ymin, double zmin, double xmax, double ymax, double zmax) {
/* 383 */     setWorldSphere(new BoundingSphere(new BoundingBox(xmin, ymin, zmin, xmax, ymax, zmax)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldSphere(BoundingSphere bs) {
/* 393 */     this._view.setWorldSphere(bs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintToFile(String fileName) {
/* 402 */     this._canvas.paintToFile(fileName);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/SimpleFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
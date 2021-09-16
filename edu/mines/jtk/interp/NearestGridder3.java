/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.mesh.TetMesh;
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NearestGridder3
/*     */   implements Gridder3
/*     */ {
/*     */   public NearestGridder3(float[] f, float[] x1, float[] x2, float[] x3) {
/*  42 */     setScattered(f, x1, x2, x3);
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
/*     */   public void computeDistancesAndValues(Sampling s1, Sampling s2, Sampling s3, float[][][] d, float[][][] g) {
/*  56 */     int n1 = s1.getCount();
/*  57 */     int n2 = s2.getCount();
/*  58 */     int n3 = s3.getCount();
/*  59 */     for (int i3 = 0; i3 < n3; i3++) {
/*  60 */       log.fine("computeDistancesAndValues: i3=" + i3);
/*  61 */       float x3 = (float)s3.getValue(i3);
/*  62 */       for (int i2 = 0; i2 < n2; i2++) {
/*  63 */         log.finer("computeDistancesAndValues: i2=" + i2);
/*  64 */         float x2 = (float)s2.getValue(i2);
/*  65 */         for (int i1 = 0; i1 < n1; i1++) {
/*  66 */           float x1 = (float)s1.getValue(i1);
/*  67 */           TetMesh.Node node = this._mesh.findNodeNearest(x1, x2, x3);
/*  68 */           if (g != null)
/*  69 */             g[i3][i2][i1] = this._f[node.index]; 
/*  70 */           if (d != null) {
/*  71 */             float d1 = x1 - node.x();
/*  72 */             float d2 = x2 - node.y();
/*  73 */             float d3 = x3 - node.z();
/*  74 */             d[i3][i2][i1] = ArrayMath.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScattered(float[] f, float[] x1, float[] x2, float[] x3) {
/*  85 */     this._f = ArrayMath.copy(f);
/*  86 */     this._mesh = new TetMesh();
/*  87 */     int n = f.length;
/*  88 */     for (int i = 0; i < n; i++) {
/*  89 */       TetMesh.Node node = new TetMesh.Node(x1[i], x2[i], x3[i]);
/*  90 */       node.index = i;
/*  91 */       boolean added = this._mesh.addNode(node);
/*  92 */       Check.argument(added, "samples have unique coordinates (x1,x2,x3)");
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[][][] grid(Sampling s1, Sampling s2, Sampling s3) {
/*  97 */     int n1 = s1.getCount();
/*  98 */     int n2 = s2.getCount();
/*  99 */     int n3 = s3.getCount();
/* 100 */     float[][][] g = new float[n3][n2][n1];
/* 101 */     computeDistancesAndValues(s1, s2, s3, (float[][][])null, g);
/* 102 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static Logger log = Logger.getLogger(NearestGridder3.class.getName());
/*     */   private float[] _f;
/*     */   private TetMesh _mesh;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/NearestGridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
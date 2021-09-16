/*    */ package edu.mines.jtk.interp;
/*    */ 
/*    */ import edu.mines.jtk.dsp.Sampling;
/*    */ import edu.mines.jtk.mesh.TriMesh;
/*    */ import edu.mines.jtk.util.ArrayMath;
/*    */ import edu.mines.jtk.util.Check;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NearestGridder2
/*    */   implements Gridder2
/*    */ {
/*    */   private float[] _f;
/*    */   private TriMesh _mesh;
/*    */   
/*    */   public NearestGridder2(float[] f, float[] x1, float[] x2) {
/* 39 */     setScattered(f, x1, x2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void computeDistancesAndValues(Sampling s1, Sampling s2, float[][] d, float[][] g) {
/* 52 */     int n1 = s1.getCount();
/* 53 */     int n2 = s2.getCount();
/* 54 */     for (int i2 = 0; i2 < n2; i2++) {
/* 55 */       float x2 = (float)s2.getValue(i2);
/* 56 */       for (int i1 = 0; i1 < n1; i1++) {
/* 57 */         float x1 = (float)s1.getValue(i1);
/* 58 */         TriMesh.Node node = this._mesh.findNodeNearest(x1, x2);
/* 59 */         float d1 = x1 - node.x();
/* 60 */         float d2 = x2 - node.y();
/* 61 */         if (g != null)
/* 62 */           g[i2][i1] = this._f[node.index]; 
/* 63 */         if (d != null) {
/* 64 */           d[i2][i1] = ArrayMath.sqrt(d1 * d1 + d2 * d2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setScattered(float[] f, float[] x1, float[] x2) {
/* 73 */     this._f = ArrayMath.copy(f);
/* 74 */     this._mesh = new TriMesh();
/* 75 */     int n = f.length;
/* 76 */     for (int i = 0; i < n; i++) {
/* 77 */       TriMesh.Node node = new TriMesh.Node(x1[i], x2[i]);
/* 78 */       node.index = i;
/* 79 */       boolean added = this._mesh.addNode(node);
/* 80 */       Check.argument(added, "samples have unique coordinates (x1,x2)");
/*    */     } 
/*    */   }
/*    */   
/*    */   public float[][] grid(Sampling s1, Sampling s2) {
/* 85 */     int n1 = s1.getCount();
/* 86 */     int n2 = s2.getCount();
/* 87 */     float[][] g = new float[n2][n1];
/* 88 */     computeDistancesAndValues(s1, s2, (float[][])null, g);
/* 89 */     return g;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/NearestGridder2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
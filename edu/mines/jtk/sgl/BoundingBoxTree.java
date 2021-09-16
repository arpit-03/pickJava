/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoundingBoxTree
/*     */ {
/*     */   private int _n;
/*     */   private int[] _i;
/*     */   private float[] _x;
/*     */   private float[] _y;
/*     */   private float[] _z;
/*     */   private Node _root;
/*     */   
/*     */   public class Node
/*     */   {
/*     */     private BoundingBox _bb;
/*     */     private int _kmin;
/*     */     private int _kmax;
/*     */     private Node _left;
/*     */     private Node _right;
/*     */     
/*     */     public BoundingBox getBoundingBox() {
/*  56 */       return new BoundingBox(this._bb);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getSize() {
/*  64 */       return 1 + this._kmax - this._kmin;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int[] getIndices() {
/*  72 */       return ArrayMath.copy(1 + this._kmax - this._kmin, this._kmin, BoundingBoxTree.this._i);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node getLeft() {
/*  80 */       return this._left;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node getRight() {
/*  88 */       return this._right;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isLeaf() {
/*  96 */       return (this._left == null);
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
/*     */   public BoundingBoxTree(int minSize, float[] xyz) {
/* 114 */     Check.argument((minSize > 0), "minSize>0");
/* 115 */     this._n = xyz.length / 3;
/* 116 */     this._i = ArrayMath.rampint(0, 1, this._n);
/* 117 */     this._x = ArrayMath.copy(this._n, 0, 3, xyz);
/* 118 */     this._y = ArrayMath.copy(this._n, 1, 3, xyz);
/* 119 */     this._z = ArrayMath.copy(this._n, 2, 3, xyz);
/* 120 */     this._root = new Node();
/* 121 */     this._root._bb = new BoundingBox(this._x, this._y, this._z);
/* 122 */     this._root._kmin = 0;
/* 123 */     this._root._kmax = this._n - 1;
/* 124 */     split(minSize, this._root);
/* 125 */     this._x = this._y = this._z = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBoxTree(int minSize, float[] x, float[] y, float[] z) {
/* 136 */     Check.argument((minSize > 0), "minSize>0");
/* 137 */     Check.argument((x.length == y.length), "x.length==y.length");
/* 138 */     Check.argument((x.length == z.length), "x.length==z.length");
/* 139 */     this._n = x.length;
/* 140 */     this._i = ArrayMath.rampint(0, 1, this._n);
/* 141 */     this._x = ArrayMath.copy(x);
/* 142 */     this._y = ArrayMath.copy(y);
/* 143 */     this._z = ArrayMath.copy(z);
/* 144 */     this._root = new Node();
/* 145 */     this._root._bb = new BoundingBox(this._x, this._y, this._z);
/* 146 */     this._root._kmin = 0;
/* 147 */     this._root._kmax = this._n - 1;
/* 148 */     split(minSize, this._root);
/* 149 */     this._x = this._y = this._z = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getRoot() {
/* 157 */     return this._root;
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
/*     */   private void split(int minSize, Node node) {
/*     */     float[] a;
/* 178 */     int kmin = node._kmin;
/* 179 */     int kmax = node._kmax;
/* 180 */     int n = 1 + kmax - kmin;
/* 181 */     if (n / 2 < minSize) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     BoundingBox bb = node._bb;
/* 186 */     Point3 min = bb.getMin();
/* 187 */     Point3 max = bb.getMax();
/* 188 */     double xdif = max.x - min.x;
/* 189 */     double ydif = max.y - min.y;
/* 190 */     double zdif = max.z - min.z;
/* 191 */     double adif = MathPlus.max(xdif, ydif, zdif);
/*     */     
/* 193 */     if (adif == xdif) {
/* 194 */       a = this._x;
/* 195 */     } else if (adif == ydif) {
/* 196 */       a = this._y;
/*     */     } else {
/* 198 */       a = this._z;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     int[] i = new int[n];
/* 206 */     for (int k = kmin; k <= kmax; k++)
/* 207 */       i[k - kmin] = this._i[k]; 
/* 208 */     int kmid = kmin + n / 2;
/* 209 */     ArrayMath.quickPartialIndexSort(kmid - kmin, a, i);
/* 210 */     for (int j = kmin; j <= kmax; j++) {
/* 211 */       this._i[j] = i[j - kmin];
/*     */     }
/*     */     
/* 214 */     Node left = new Node();
/* 215 */     Node right = new Node();
/* 216 */     if (adif == xdif) {
/* 217 */       float spltx = this._x[this._i[kmid]];
/* 218 */       left._bb = new BoundingBox(min.x, min.y, min.z, spltx, max.y, max.z);
/*     */       
/* 220 */       right._bb = new BoundingBox(spltx, min.y, min.z, max.x, max.y, max.z);
/*     */     }
/* 222 */     else if (adif == ydif) {
/* 223 */       float splty = this._y[this._i[kmid]];
/* 224 */       left._bb = new BoundingBox(min.x, min.y, min.z, max.x, splty, max.z);
/*     */       
/* 226 */       right._bb = new BoundingBox(min.x, splty, min.z, max.x, max.y, max.z);
/*     */     } else {
/*     */       
/* 229 */       float spltz = this._z[this._i[kmid]];
/* 230 */       left._bb = new BoundingBox(min.x, min.y, min.z, max.x, max.y, spltz);
/*     */       
/* 232 */       right._bb = new BoundingBox(min.x, min.y, spltz, max.x, max.y, max.z);
/*     */     } 
/*     */     
/* 235 */     left._kmin = kmin;
/* 236 */     left._kmax = kmid - 1;
/* 237 */     right._kmin = kmid;
/* 238 */     right._kmax = kmax;
/* 239 */     node._left = left;
/* 240 */     node._right = right;
/*     */ 
/*     */     
/* 243 */     split(minSize, left);
/* 244 */     split(minSize, right);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/BoundingBoxTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordinateTransform
/*     */ {
/*  59 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */   
/*  61 */   private int _nout = 0;
/*  62 */   private int _nin = 0;
/*     */   private final double[][] _hessian;
/*     */   private final double[][] _b;
/*     */   private double[][] _a;
/*  66 */   private double[] _in0 = null;
/*  67 */   private double[] _out0 = null;
/*  68 */   private double[] _inScr = null;
/*  69 */   private double[] _outScr = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinateTransform(int dimensionOut, int dimensionIn) {
/*  78 */     this._nout = dimensionOut;
/*  79 */     this._nin = dimensionIn;
/*  80 */     this._hessian = new double[this._nin][this._nin];
/*  81 */     this._b = new double[this._nout][this._nin];
/*  82 */     this._inScr = new double[this._nin];
/*  83 */     this._outScr = new double[this._nout];
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
/*     */   public void add(double[] out, double[] in) {
/* 100 */     this._a = (double[][])null;
/*     */     
/* 102 */     if (in.length != this._nin) {
/* 103 */       throw new IllegalArgumentException("in must have dimension " + this._nin);
/*     */     }
/*     */     
/* 106 */     if (out.length != this._nout) {
/* 107 */       throw new IllegalArgumentException("out must have dimension " + this._nout);
/*     */     }
/*     */     
/* 110 */     if (this._in0 == null) {
/* 111 */       this._in0 = (double[])in.clone();
/*     */     }
/* 113 */     if (this._out0 == null) {
/* 114 */       this._out0 = (double[])out.clone();
/*     */     }
/*     */     int i;
/* 117 */     for (i = 0; i < this._nin; i++) {
/* 118 */       this._inScr[i] = in[i] - this._in0[i];
/*     */     }
/* 120 */     for (i = 0; i < this._nout; i++) {
/* 121 */       this._outScr[i] = out[i] - this._out0[i];
/*     */     }
/*     */     
/* 124 */     for (int k = 0; k < this._nin; k++) {
/* 125 */       for (int j = 0; j < this._nin; j++) {
/* 126 */         this._hessian[k][j] = this._hessian[k][j] + this._inScr[k] * this._inScr[j];
/*     */       }
/* 128 */       for (int o = 0; o < this._nout; o++) {
/* 129 */         this._b[o][k] = this._b[o][k] - this._outScr[o] * this._inScr[k];
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
/*     */   public double[] get(double[] in) {
/* 142 */     for (int i = 0; i < in.length; i++) {
/* 143 */       this._inScr[i] = in[i] - this._in0[i];
/*     */     }
/* 145 */     in = null;
/*     */     
/* 147 */     if (this._a == null) {
/* 148 */       this._a = new double[this._nout][this._nin];
/* 149 */       for (int k = 0; k < this._nout; k++) {
/* 150 */         LinearQuadratic lq = new LinearQuadratic(k);
/* 151 */         QuadraticSolver qs = new QuadraticSolver(lq);
/* 152 */         ArrayVect1 solution = (ArrayVect1)qs.solve(this._nin + 4, null);
/* 153 */         double[] data = solution.getData();
/* 154 */         System.arraycopy(data, 0, this._a[k], 0, this._nin);
/* 155 */         solution.dispose();
/*     */       } 
/*     */     } 
/* 158 */     double[] result = new double[this._nout];
/* 159 */     for (int o = 0; o < this._nout; o++) {
/* 160 */       for (int k = 0; k < this._nin; k++) {
/* 161 */         result[o] = result[o] + this._a[o][k] * this._inScr[k];
/*     */       }
/*     */     } 
/* 164 */     for (int j = 0; j < result.length; j++) {
/* 165 */       result[j] = result[j] + this._out0[j];
/*     */     }
/* 167 */     return result;
/*     */   }
/*     */   
/*     */   private class LinearQuadratic
/*     */     implements Quadratic {
/* 172 */     private int _o = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private LinearQuadratic(int o) {
/* 180 */       this._o = o;
/*     */     }
/*     */ 
/*     */     
/*     */     public void multiplyHessian(Vect x) {
/* 185 */       ArrayVect1 m = (ArrayVect1)x;
/* 186 */       double[] data = m.getData();
/* 187 */       double[] oldData = (double[])data.clone();
/* 188 */       Arrays.fill(data, 0.0D);
/* 189 */       for (int i = 0; i < data.length; i++) {
/* 190 */         for (int j = 0; j < data.length; j++) {
/* 191 */           data[i] = data[i] + CoordinateTransform.this._hessian[i][j] * oldData[j];
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Vect getB() {
/* 198 */       return new ArrayVect1((double[])CoordinateTransform.this._b[this._o].clone(), 1.0D);
/*     */     }
/*     */     
/*     */     public void inverseHessian(Vect x) {}
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/CoordinateTransform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
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
/*     */ public class ArrayVect1
/*     */   implements Vect
/*     */ {
/*  38 */   private double[] _data = null;
/*     */ 
/*     */ 
/*     */   
/*  42 */   private double _variance = 1.0D;
/*  43 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1(double[] data, double variance) {
/*  55 */     init(data, variance);
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
/*     */   protected final void init(double[] data, double variance) {
/*  73 */     this._data = data;
/*  74 */     this._variance = variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  83 */     return this._data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getData() {
/*  92 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1 clone() {
/*     */     try {
/*  99 */       ArrayVect1 result = (ArrayVect1)super.clone();
/* 100 */       result._data = (double[])result._data.clone();
/* 101 */       return result;
/* 102 */     } catch (CloneNotSupportedException ex) {
/* 103 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/* 104 */       e.initCause(ex);
/* 105 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/* 112 */     double result = 0.0D;
/* 113 */     ArrayVect1 rhs = (ArrayVect1)other;
/* 114 */     for (int i = 0; i < this._data.length; i++) {
/* 115 */       result += this._data[i] * rhs._data[i];
/*     */     }
/* 117 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     StringBuilder sb = new StringBuilder();
/* 124 */     sb.append("(");
/* 125 */     for (int i = 0; i < this._data.length; i++) {
/* 126 */       sb.append(String.valueOf(this._data[i]));
/* 127 */       if (i < this._data.length - 1) {
/* 128 */         sb.append(", ");
/*     */       }
/*     */     } 
/* 131 */     sb.append(")");
/* 132 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 138 */     this._data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 144 */     double scale = Almost.FLOAT.divide(1.0D, getSize() * this._variance, 0.0D);
/* 145 */     VectUtil.scale(this, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 161 */     ArrayVect1 rhs = (ArrayVect1)other;
/* 162 */     for (int i = 0; i < this._data.length; i++) {
/* 163 */       this._data[i] = scaleThis * this._data[i] + scaleOther * rhs._data[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 170 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 176 */     return Almost.FLOAT.divide(dot(this), getSize() * this._variance, 0.0D);
/*     */   }
/*     */   
/*     */   protected ArrayVect1() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
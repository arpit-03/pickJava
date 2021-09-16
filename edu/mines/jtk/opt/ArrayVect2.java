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
/*     */ public class ArrayVect2
/*     */   implements Vect
/*     */ {
/*  36 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*  42 */   private double[][] _data = (double[][])null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private double _variance = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect2(double[][] data, double variance) {
/*  57 */     init(data, variance);
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
/*     */   protected void init(double[][] data, double variance) {
/*  74 */     this._data = data;
/*  75 */     this._variance = variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] getData() {
/*  84 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  93 */     return this._data.length * (this._data[0]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/*  99 */     ArrayVect2 o = (ArrayVect2)other;
/* 100 */     for (int i = 0; i < this._data.length && this._data.length > 0; i++) {
/* 101 */       for (int j = 0; j < (this._data[0]).length; j++) {
/* 102 */         this._data[i][j] = scaleThis * this._data[i][j] + scaleOther * o._data[i][j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 110 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 116 */     this._data = (double[][])null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 122 */     double scale = Almost.FLOAT.divide(1.0D, getSize() * this._variance, 0.0D);
/* 123 */     VectUtil.scale(this, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 129 */     return Almost.FLOAT.divide(dot(this), getSize() * this._variance, 0.0D);
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
/*     */   public ArrayVect2 clone() {
/*     */     try {
/* 146 */       double[][] newData = new double[this._data.length][];
/* 147 */       for (int i = 0; i < newData.length; i++) {
/* 148 */         newData[i] = (double[])this._data[i].clone();
/*     */       }
/* 150 */       ArrayVect2 result = (ArrayVect2)super.clone();
/* 151 */       result.init(newData, this._variance);
/* 152 */       return result;
/* 153 */     } catch (CloneNotSupportedException ex) {
/* 154 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/* 155 */       e.initCause(ex);
/* 156 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/* 163 */     ArrayVect2 rhs = (ArrayVect2)other;
/* 164 */     double result = 0.0D;
/* 165 */     for (int i = 0; i < this._data.length; i++) {
/* 166 */       for (int j = 0; j < (this._data[0]).length; j++) {
/* 167 */         result += this._data[i][j] * rhs._data[i][j];
/*     */       }
/*     */     } 
/* 170 */     return result;
/*     */   }
/*     */   
/*     */   protected ArrayVect2() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
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
/*     */ public class ArrayVect2f
/*     */   implements Vect
/*     */ {
/*  35 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */   
/*     */   private static final long serialVersionUID = 2L;
/*     */   
/*  39 */   protected float[][] _data = (float[][])null;
/*  40 */   private int[] _firstSample = null;
/*     */ 
/*     */   
/*  43 */   protected double _variance = 1.0D;
/*  44 */   private int _size = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect2f(float[][] data, double variance) {
/*  52 */     init(data, null, variance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect2f(float[][] data, int[] firstSample, double variance) {
/*  62 */     init(data, firstSample, variance);
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
/*     */   protected final void init(float[][] data, int[] firstSample, double variance) {
/*  75 */     this._data = data;
/*  76 */     this._variance = variance;
/*  77 */     this._firstSample = firstSample;
/*  78 */     if (this._firstSample != null && this._firstSample.length != data.length) {
/*  79 */       throw new IllegalArgumentException("Data and firstSample must have same length for slow dimension.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] getData() {
/*  88 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dataChanged() {
/*  96 */     this._size = -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 102 */     if (this._size < 0) {
/* 103 */       this._size = 0;
/* 104 */       for (int i = 0; i < this._data.length && this._data.length > 0; i++) {
/* 105 */         this._size += (this._data[i]).length;
/*     */       }
/*     */     } 
/* 108 */     return this._size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 114 */     float s1 = (float)scaleThis;
/* 115 */     float s2 = (float)scaleOther;
/* 116 */     ArrayVect2f o = (ArrayVect2f)other;
/* 117 */     for (int i = 0; i < this._data.length; i++) {
/* 118 */       for (int j = 0; j < (this._data[i]).length; j++) {
/* 119 */         this._data[i][j] = s1 * this._data[i][j] + s2 * o._data[i][j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 127 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 133 */     this._data = (float[][])null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 139 */     double scale = Almost.FLOAT.divide(1.0D, getSize() * this._variance, 0.0D);
/* 140 */     VectUtil.scale(this, scale);
/*     */   }
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 145 */     return Almost.FLOAT.divide(dot(this), getSize() * this._variance, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {
/* 151 */     if (this._firstSample == null) {
/*     */       return;
/*     */     }
/* 154 */     for (int i = 0; i < this._data.length; i++) {
/* 155 */       Arrays.fill(this._data[i], 0, this._firstSample[i], 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {}
/*     */ 
/*     */   
/*     */   public ArrayVect2f clone() {
/*     */     try {
/* 166 */       float[][] newData = new float[this._data.length][];
/* 167 */       for (int i = 0; i < newData.length; i++) {
/* 168 */         newData[i] = (float[])this._data[i].clone();
/*     */       }
/*     */       
/* 171 */       int[] newFirstSample = (this._firstSample != null) ? (int[])this._firstSample.clone() : null;
/*     */       
/* 173 */       ArrayVect2f result = (ArrayVect2f)super.clone();
/* 174 */       result.init(newData, newFirstSample, this._variance);
/* 175 */       return result;
/* 176 */     } catch (CloneNotSupportedException ex) {
/* 177 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/* 178 */       e.initCause(ex);
/* 179 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/* 186 */     ArrayVect2f rhs = (ArrayVect2f)other;
/* 187 */     double result = 0.0D;
/* 188 */     for (int i = 0; i < this._data.length; i++) {
/* 189 */       for (int j = 0; j < (this._data[i]).length; j++) {
/* 190 */         result += this._data[i][j] * rhs._data[i][j];
/*     */       }
/*     */     } 
/* 193 */     return result;
/*     */   }
/*     */   
/*     */   protected ArrayVect2f() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
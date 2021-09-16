/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class ArrayVect3f
/*     */   implements Vect
/*     */ {
/*  45 */   protected transient float[][][] _data = (float[][][])null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected transient double _variance = 1.0D;
/*     */   
/*  52 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   
/*     */   private static final int VERSION = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect3f(float[][][] data, double variance) {
/*  64 */     init(data, variance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVariance() {
/*  74 */     return this._variance;
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
/*     */   protected final void init(float[][][] data, double variance) {
/*  91 */     this._data = data;
/*  92 */     this._variance = variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] getData() {
/* 101 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 110 */     return this._data.length * (this._data[0]).length * (this._data[0][0]).length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 116 */     float s1 = (float)scaleThis;
/* 117 */     float s2 = (float)scaleOther;
/* 118 */     ArrayVect3f rhs = (ArrayVect3f)other;
/* 119 */     for (int i = 0; i < this._data.length; i++) {
/* 120 */       for (int j = 0; j < (this._data[0]).length; j++) {
/* 121 */         for (int k = 0; k < (this._data[0][0]).length; k++) {
/* 122 */           this._data[i][j][k] = s1 * this._data[i][j][k] + s2 * rhs._data[i][j][k];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 131 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 137 */     this._data = (float[][][])null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 143 */     double scale = Almost.FLOAT.divide(1.0D, getSize() * this._variance, 0.0D);
/* 144 */     VectUtil.scale(this, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 150 */     return Almost.FLOAT.divide(dot(this), getSize() * this._variance, 0.0D);
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
/*     */   public ArrayVect3f clone() {
/*     */     try {
/* 167 */       float[][][] newData = new float[this._data.length][(this._data[0]).length][];
/* 168 */       for (int i = 0; i < newData.length; i++) {
/* 169 */         for (int j = 0; j < (newData[0]).length; j++) {
/* 170 */           newData[i][j] = (float[])this._data[i][j].clone();
/*     */         }
/*     */       } 
/* 173 */       ArrayVect3f result = (ArrayVect3f)super.clone();
/* 174 */       result.init(newData, this._variance);
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
/* 186 */     double result = 0.0D;
/* 187 */     ArrayVect3f rhs = (ArrayVect3f)other;
/* 188 */     for (int i = 0; i < this._data.length; i++) {
/* 189 */       for (int j = 0; j < (this._data[0]).length; j++) {
/* 190 */         for (int k = 0; k < (this._data[0][0]).length; k++) {
/* 191 */           result += this._data[i][j][k] * rhs._data[i][j][k];
/*     */         }
/*     */       } 
/*     */     } 
/* 195 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 200 */     Map<String, Object> map = new HashMap<>();
/* 201 */     map.put("data", this._data);
/* 202 */     map.put("variance", Double.valueOf(this._variance));
/* 203 */     map.put("VERSION", Integer.valueOf(1));
/* 204 */     out.writeObject(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 211 */     Map<String, Object> map = (Map<String, Object>)in.readObject();
/*     */     
/* 213 */     this._data = (float[][][])map.get("data");
/* 214 */     this._variance = ((Double)map.get("variance")).doubleValue();
/*     */     
/* 216 */     int version = ((Integer)map.get("VERSION")).intValue();
/* 217 */     if (version != 1)
/* 218 */       Logger.getLogger(getClass().getName())
/* 219 */         .warning("Need to convert data from version " + version + " to " + '\001'); 
/*     */   }
/*     */   
/*     */   protected ArrayVect3f() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
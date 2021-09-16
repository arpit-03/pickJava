/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Arrays;
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
/*     */ public class ArrayVect1f
/*     */   implements Vect
/*     */ {
/*  41 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2L;
/*     */   
/*     */   private static final int VERSION = 1;
/*     */   
/*  48 */   protected transient float[] _data = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   protected transient double _variance = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   protected transient int _firstSample = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1f(float[] data, int firstSample, double variance) {
/*  72 */     init(data, firstSample, variance);
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
/*     */   protected final void init(float[] data, int firstSample, double variance) {
/*  92 */     this._data = data;
/*  93 */     this._firstSample = firstSample;
/*  94 */     this._variance = variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstSample() {
/* 103 */     return this._firstSample;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 112 */     return this._data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getData() {
/* 121 */     return this._data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(float[] data) {
/* 130 */     System.arraycopy(data, 0, this._data, 0, this._data.length);
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
/*     */   public static void fillContainer(VectContainer container, int[] firstSamples, float[][] data, double variance) {
/* 146 */     for (int i = 0; i < data.length; i++) {
/* 147 */       container.put(i, new ArrayVect1f(data[i], firstSamples[i], variance));
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
/*     */   public static void extractContainer(float[][] data, VectContainer container) {
/* 159 */     for (int i = 0; i < data.length; i++) {
/* 160 */       ArrayVect1f trace = (ArrayVect1f)container.get(i);
/* 161 */       float[] traceData = trace.getData();
/* 162 */       System.arraycopy(traceData, 0, data[i], 0, (data[i]).length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1f clone() {
/*     */     try {
/* 170 */       ArrayVect1f result = (ArrayVect1f)super.clone();
/* 171 */       if (this._data != null) {
/* 172 */         float[] newData = (float[])this._data.clone();
/* 173 */         result.init(newData, this._firstSample, this._variance);
/*     */       } 
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
/*     */   public String toString() {
/* 186 */     StringBuilder sb = new StringBuilder();
/* 187 */     sb.append("(");
/* 188 */     for (int i = 0; i < this._data.length; i++) {
/* 189 */       sb.append(String.valueOf(this._data[i]));
/* 190 */       if (i < this._data.length - 1) {
/* 191 */         sb.append(", ");
/*     */       }
/*     */     } 
/* 194 */     sb.append(")");
/* 195 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/* 201 */     double result = 0.0D;
/* 202 */     ArrayVect1f rhs = (ArrayVect1f)other;
/* 203 */     int i = 0;
/* 204 */     for (; i < this._data.length; 
/* 205 */       i++) {
/* 206 */       result += this._data[i] * rhs._data[i];
/*     */     }
/* 208 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 214 */     this._data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 220 */     double scale = Almost.FLOAT.divide(1.0D, getSize() * this._variance, 0.0D);
/* 221 */     VectUtil.scale(this, scale);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {
/* 227 */     Arrays.fill(this._data, 0, this._firstSample, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 233 */     float s1 = (float)scaleThis;
/* 234 */     float s2 = (float)scaleOther;
/* 235 */     ArrayVect1f rhs = (ArrayVect1f)other;
/* 236 */     for (int i = 0; i < this._data.length; i++) {
/* 237 */       this._data[i] = s1 * this._data[i] + s2 * rhs._data[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 244 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 250 */     return Almost.FLOAT.divide(dot(this), getSize() * this._variance, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 261 */     Map<String, Object> map = new HashMap<>();
/* 262 */     map.put("d", this._data);
/* 263 */     map.put("v", Double.valueOf(this._variance));
/* 264 */     map.put("f", Integer.valueOf(this._firstSample));
/* 265 */     map.put("V", Integer.valueOf(1));
/* 266 */     out.writeObject(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 273 */     Map<String, Object> map = (Map<String, Object>)in.readObject();
/*     */     
/* 275 */     this._data = (float[])map.get("d");
/* 276 */     this._variance = ((Double)map.get("v")).doubleValue();
/* 277 */     this._firstSample = ((Integer)map.get("f")).intValue();
/*     */     
/* 279 */     int version = ((Integer)map.get("V")).intValue();
/* 280 */     if (version != 1)
/* 281 */       Logger.getLogger(getClass().getName())
/* 282 */         .warning("Need to convert data from version " + version + " to " + '\001'); 
/*     */   }
/*     */   
/*     */   protected ArrayVect1f() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect1f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
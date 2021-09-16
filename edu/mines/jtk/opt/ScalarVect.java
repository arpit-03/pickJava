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
/*     */ public class ScalarVect
/*     */   implements Vect
/*     */ {
/*  33 */   protected transient double _value = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*  37 */   protected transient double _variance = 1.0D;
/*  38 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int VERSION = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalarVect(double value, double variance) {
/*  51 */     init(value, variance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ScalarVect() {
/*  58 */     init(0.0D, 1.0D);
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
/*     */   public final void init(double value, double variance) {
/*  70 */     this._value = value;
/*  71 */     this._variance = variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get() {
/*  80 */     return this._value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double value) {
/*  89 */     this._value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalarVect clone() {
/*     */     try {
/*  96 */       return (ScalarVect)super.clone();
/*  97 */     } catch (CloneNotSupportedException ex) {
/*  98 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/*  99 */       e.initCause(ex);
/* 100 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/* 107 */     ScalarVect rhs = (ScalarVect)other;
/* 108 */     return this._value * rhs._value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return "ScalarVect<" + this._value + ">";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 120 */     this._value = Double.NaN;
/* 121 */     this._variance = Double.NaN;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 127 */     this._value = Almost.FLOAT.divide(this._value, this._variance, 0.0D);
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
/* 143 */     ScalarVect rhs = (ScalarVect)other;
/* 144 */     this._value = scaleThis * this._value + scaleOther * rhs._value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 150 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 156 */     return Almost.FLOAT.divide(dot(this), this._variance, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 161 */     Map<String, Object> map = new HashMap<>();
/* 162 */     map.put("value", Double.valueOf(this._value));
/* 163 */     map.put("variance", Double.valueOf(this._variance));
/* 164 */     map.put("VERSION", Integer.valueOf(1));
/* 165 */     out.writeObject(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 172 */     Map<String, Object> map = (Map<String, Object>)in.readObject();
/*     */     
/* 174 */     this._value = ((Double)map.get("value")).doubleValue();
/* 175 */     this._variance = ((Double)map.get("variance")).doubleValue();
/*     */     
/* 177 */     int version = ((Integer)map.get("VERSION")).intValue();
/* 178 */     if (version != 1)
/* 179 */       Logger.getLogger(getClass().getName())
/* 180 */         .warning("Need to convert data from version " + version + " to " + '\001'); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ScalarVect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
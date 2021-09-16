/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ public class ArrayVect1fs
/*     */   implements Vect
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*  29 */   protected ArrayVect1f[] _data = null;
/*     */   
/*  31 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1fs(ArrayVect1f[] data) {
/*  36 */     init(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(ArrayVect1f[] data) {
/*  43 */     this._data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  48 */     return this._data.length;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayVect1f[] getData() {
/*  54 */     return this._data;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayVect1fs clone() {
/*     */     try {
/*  60 */       ArrayVect1fs result = (ArrayVect1fs)super.clone();
/*  61 */       if (this._data != null) {
/*  62 */         ArrayVect1f[] data = (ArrayVect1f[])this._data.clone();
/*  63 */         for (int i = 0; i < data.length; i++) {
/*  64 */           data[i] = data[i].clone();
/*     */         }
/*  66 */         result.init(data);
/*     */       } 
/*  68 */       return result;
/*  69 */     } catch (CloneNotSupportedException ex) {
/*  70 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/*  71 */       e.initCause(ex);
/*  72 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/*  79 */     double result = 0.0D;
/*  80 */     ArrayVect1fs rhs = (ArrayVect1fs)other;
/*  81 */     for (int i = 0; i < this._data.length; i++) {
/*  82 */       result += this._data[i].dot(rhs._data[i]);
/*     */     }
/*  84 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  89 */     StringBuilder sb = new StringBuilder();
/*  90 */     sb.append("(");
/*  91 */     for (int i = 0; i < this._data.length; i++) {
/*  92 */       sb.append(String.valueOf(this._data[i]));
/*  93 */       if (i < this._data.length - 1) sb.append(", "); 
/*     */     } 
/*  95 */     sb.append(")");
/*  96 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 102 */     for (ArrayVect1f a_data : this._data) {
/* 103 */       a_data.dispose();
/*     */     }
/* 105 */     this._data = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 111 */     double scale = Almost.FLOAT.divide(1.0D, getSize(), 0.0D);
/* 112 */     for (ArrayVect1f a_data : this._data) {
/* 113 */       a_data.multiplyInverseCovariance();
/* 114 */       VectUtil.scale(a_data, scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {
/* 121 */     for (ArrayVect1f a_data : this._data) {
/* 122 */       a_data.constrain();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 133 */     ArrayVect1fs rhs = (ArrayVect1fs)other;
/* 134 */     for (int i = 0; i < this._data.length; i++) {
/* 135 */       this._data[i].add(scaleThis, scaleOther, rhs._data[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 142 */     add(scaleThis, scaleOther, other);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 148 */     double result = 0.0D;
/* 149 */     for (ArrayVect1f a_data : this._data) {
/* 150 */       result += a_data.magnitude();
/*     */     }
/* 152 */     result = Almost.FLOAT.divide(result, getSize(), 0.0D);
/* 153 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 159 */     out.writeInt(this._data.length);
/* 160 */     for (ArrayVect1f a_data : this._data) {
/* 161 */       out.writeObject(a_data);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 168 */     int length = in.readInt();
/* 169 */     this._data = new ArrayVect1f[length];
/* 170 */     for (int i = 0; i < this._data.length; i++)
/* 171 */       this._data[i] = (ArrayVect1f)in.readObject(); 
/*     */   }
/*     */   
/*     */   public ArrayVect1fs() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/ArrayVect1fs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
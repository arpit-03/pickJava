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
/*     */ public class VectArray
/*     */   implements VectContainer
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  27 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*  28 */   private Vect[] _vect = null;
/*  29 */   private int[] _keys = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VectArray(int size) {
/*  35 */     this._vect = new Vect[size];
/*  36 */     this._keys = new int[size];
/*  37 */     for (int i = 0; i < size; ) { this._keys[i] = i; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(int index, Vect vect) {
/*  43 */     this._vect[index] = vect;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vect get(int index) {
/*  49 */     return this._vect[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  54 */     return this._vect.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(int index) {
/*  59 */     return (index >= 0 && index < this._vect.length && this._vect[index] != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getKeys() {
/*  65 */     return this._keys;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/*  71 */     VectArray otherMap = (VectArray)other;
/*  72 */     double result = 0.0D;
/*  73 */     for (int i = 0; i < this._vect.length; i++) {
/*  74 */       result += this._vect[i].dot(otherMap._vect[i]);
/*     */     }
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public VectArray clone() {
/*     */     VectArray result;
/*     */     try {
/*  83 */       result = (VectArray)super.clone();
/*  84 */       result._vect = new Vect[this._vect.length];
/*  85 */       for (int i = 0; i < this._vect.length; i++) {
/*  86 */         if (this._vect[i] != null) {
/*  87 */           result._vect[i] = this._vect[i].clone();
/*     */         }
/*     */       } 
/*  90 */     } catch (CloneNotSupportedException ex) {
/*  91 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/*  92 */       e.initCause(ex);
/*  93 */       throw e;
/*     */     } 
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 101 */     this._vect = null;
/* 102 */     this._keys = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 108 */     double scale = Almost.FLOAT.divide(1.0D, this._vect.length, 0.0D);
/* 109 */     for (Vect a_vect : this._vect) {
/* 110 */       a_vect.multiplyInverseCovariance();
/* 111 */       VectUtil.scale(a_vect, scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {
/* 118 */     for (Vect a_vect : this._vect) {
/* 119 */       a_vect.constrain();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {
/* 126 */     for (Vect a_vect : this._vect) {
/* 127 */       a_vect.postCondition();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 134 */     addOrProject(scaleThis, scaleOther, other, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 140 */     addOrProject(scaleThis, scaleOther, other, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOrProject(double scaleThis, double scaleOther, VectConst other, boolean project) {
/* 147 */     VectArray otherMap = (VectArray)other;
/* 148 */     for (int i = 0; i < this._vect.length; i++) {
/* 149 */       Vect vectTo = this._vect[i];
/* 150 */       Vect vectFrom = otherMap._vect[i];
/* 151 */       if (vectFrom == null) {
/* 152 */         throw new IllegalStateException("Cannot scale missing Vect " + i);
/*     */       }
/* 154 */       if (project) {
/* 155 */         vectTo.project(scaleThis, scaleOther, vectFrom);
/*     */       } else {
/* 157 */         vectTo.add(scaleThis, scaleOther, vectFrom);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 164 */     double result = 0.0D;
/* 165 */     for (Vect a_vect : this._vect) {
/* 166 */       result += a_vect.magnitude();
/*     */     }
/* 168 */     result = Almost.FLOAT.divide(result, this._vect.length, 0.0D);
/* 169 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/VectArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
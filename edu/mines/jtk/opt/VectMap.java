/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Set;
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
/*     */ public class VectMap
/*     */   implements VectContainer
/*     */ {
/*  31 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*  32 */   private LinkedHashMap<Integer, Vect> _map = new LinkedHashMap<>();
/*     */ 
/*     */   
/*     */   private boolean _cloneContents = false;
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */   
/*     */   public VectMap(boolean cloneContents) {
/*  43 */     if (cloneContents) {
/*  44 */       LOG.warning("Cloning hurts performance.  Use only for testing a VectContainer that requires puts.");
/*     */     }
/*     */     
/*  47 */     this._cloneContents = cloneContents;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(int index, Vect vect) {
/*  53 */     this._map.put(Integer.valueOf(index), this._cloneContents ? vect.clone() : vect);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vect get(int index) {
/*  59 */     Vect result = getPrivate(index);
/*  60 */     if (result != null && this._cloneContents) {
/*  61 */       result = result.clone();
/*     */     }
/*  63 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vect getPrivate(int index) {
/*  68 */     return this._map.get(Integer.valueOf(index));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  74 */     return this._map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(int index) {
/*  80 */     return this._map.containsKey(Integer.valueOf(index));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int[] getKeys() {
/*  86 */     Set<Integer> keys = this._map.keySet();
/*  87 */     int[] result = new int[keys.size()];
/*  88 */     int i = 0;
/*  89 */     for (Integer j : keys) {
/*  90 */       result[i++] = j.intValue();
/*     */     }
/*  92 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(VectConst other) {
/*  98 */     VectMap otherMap = (VectMap)other;
/*  99 */     int[] keys = getKeys();
/* 100 */     double result = 0.0D;
/* 101 */     for (int key : keys) {
/* 102 */       Vect lhs = getPrivate(key);
/* 103 */       Vect rhs = otherMap.getPrivate(key);
/* 104 */       result += lhs.dot(rhs);
/*     */     } 
/* 106 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VectMap clone() {
/*     */     VectMap result;
/*     */     try {
/* 114 */       result = (VectMap)super.clone();
/* 115 */       result._map = new LinkedHashMap<>();
/* 116 */       int[] keys = getKeys();
/* 117 */       for (int key : keys) {
/* 118 */         Vect vect = getPrivate(key);
/* 119 */         result.put(key, vect.clone());
/*     */       } 
/* 121 */     } catch (CloneNotSupportedException ex) {
/* 122 */       IllegalStateException e = new IllegalStateException(ex.getMessage());
/* 123 */       e.initCause(ex);
/* 124 */       throw e;
/*     */     } 
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 132 */     int[] keys = getKeys();
/* 133 */     for (int key : keys) {
/* 134 */       Vect vect = getPrivate(key);
/* 135 */       vect.dispose();
/*     */     } 
/* 137 */     this._map = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void multiplyInverseCovariance() {
/* 143 */     int[] keys = getKeys();
/* 144 */     double scale = Almost.FLOAT.divide(1.0D, keys.length, 0.0D);
/* 145 */     for (int key : keys) {
/* 146 */       Vect vect = getPrivate(key);
/* 147 */       vect.multiplyInverseCovariance();
/* 148 */       VectUtil.scale(vect, scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void constrain() {
/* 155 */     int[] keys = getKeys();
/* 156 */     for (int key : keys) {
/* 157 */       Vect vect = getPrivate(key);
/* 158 */       vect.constrain();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void postCondition() {
/* 165 */     int[] keys = getKeys();
/* 166 */     for (int key : keys) {
/* 167 */       Vect vect = getPrivate(key);
/* 168 */       vect.postCondition();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double scaleThis, double scaleOther, VectConst other) {
/* 175 */     addOrProject(scaleThis, scaleOther, other, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void project(double scaleThis, double scaleOther, VectConst other) {
/* 181 */     addOrProject(scaleThis, scaleOther, other, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOrProject(double scaleThis, double scaleOther, VectConst other, boolean project) {
/* 188 */     VectMap otherMap = (VectMap)other;
/* 189 */     int[] keys = getKeys();
/* 190 */     for (int key : keys) {
/* 191 */       Vect vectTo = getPrivate(key);
/* 192 */       Vect vectFrom = otherMap.getPrivate(key);
/* 193 */       if (vectFrom == null) {
/* 194 */         throw new IllegalStateException("Cannot scale a vector missing key " + key);
/*     */       }
/*     */       
/* 197 */       if (project) {
/* 198 */         vectTo.project(scaleThis, scaleOther, vectFrom);
/*     */       } else {
/* 200 */         vectTo.add(scaleThis, scaleOther, vectFrom);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double magnitude() {
/* 208 */     int[] keys = getKeys();
/* 209 */     double result = 0.0D;
/* 210 */     for (int key : keys) {
/* 211 */       Vect vect = getPrivate(key);
/* 212 */       result += vect.magnitude();
/*     */     } 
/* 214 */     result = Almost.FLOAT.divide(result, keys.length, 0.0D);
/* 215 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/VectMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.logging.Level;
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
/*     */ public class VectUtil
/*     */ {
/*  34 */   private static final Logger LOG = Logger.getLogger(VectUtil.class.getName());
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
/*     */   public static void scale(Vect v, double scalar) {
/*  46 */     v.add(scalar, 0.0D, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void zero(Vect v) {
/*  55 */     scale(v, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copy(Vect to, VectConst from) {
/*  66 */     to.add(0.0D, 1.0D, from);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vect cloneZero(VectConst v) {
/*  77 */     Vect result = v.clone();
/*  78 */     zero(result);
/*  79 */     return result;
/*     */   }
/*     */   
/*  82 */   static final Almost ALMOST_DOT = new Almost(1.5E-5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areSame(VectConst v1, VectConst v2) {
/*  93 */     double aa = v1.dot(v1);
/*  94 */     double ab = v1.dot(v2);
/*  95 */     double bb = v2.dot(v2);
/*     */     
/*  97 */     return (ALMOST_DOT
/*  98 */       .equal(aa, bb) && ALMOST_DOT
/*  99 */       .equal(aa, ab) && ALMOST_DOT
/* 100 */       .equal(ab, bb));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void test(VectConst vect) {
/* 111 */     double originalDot = vect.dot(vect);
/* 112 */     ass(!Almost.FLOAT.zero(originalDot), "cannot test a zero vector");
/*     */     
/* 114 */     Vect t = cloneZero(vect);
/* 115 */     ass(Almost.FLOAT.zero(t.dot(t)), "cloneZero() did not work");
/*     */     
/* 117 */     copy(t, vect);
/* 118 */     double check = t.dot(vect) / vect.dot(vect);
/* 119 */     ass(Almost.FLOAT.equal(check, 1.0D), "not 1. check=" + check);
/*     */     
/* 121 */     scale(t, 0.5D);
/* 122 */     check = t.dot(vect) / vect.dot(vect);
/* 123 */     ass(Almost.FLOAT.equal(check, 0.5D), "not 0.5 check=" + check);
/*     */     
/* 125 */     t.add(1.0D, 1.0D, vect);
/* 126 */     check = t.dot(vect) / vect.dot(vect);
/* 127 */     ass(Almost.FLOAT.equal(check, 1.5D), "not 1.5 check=" + check);
/*     */     
/* 129 */     t.add(2.0D, -5.0D, vect);
/* 130 */     check = t.dot(vect) / vect.dot(vect);
/* 131 */     ass(Almost.FLOAT.equal(check, -2.0D), "not -2, check=" + check);
/*     */     
/* 133 */     t.project(0.0D, 1.0D, vect);
/* 134 */     t.project(1.75D, -0.75D, vect);
/* 135 */     ass(areSame(t, vect), "project failed");
/*     */     
/* 137 */     t.dispose();
/* 138 */     ass(Almost.FLOAT.equal(originalDot, vect.dot(vect)), "exercise of clone damaged original");
/*     */ 
/*     */     
/* 141 */     t = vect.clone();
/* 142 */     t.multiplyInverseCovariance();
/* 143 */     double mag1 = vect.dot(t);
/* 144 */     t.dispose();
/* 145 */     double mag2 = vect.magnitude();
/* 146 */     ass(Almost.FLOAT.equal(mag1, mag2), "magnitude() inconsistent with multiplyInverseCovariance() and dot(): " + mag1 + "!=" + mag2);
/*     */ 
/*     */ 
/*     */     
/* 150 */     ass((mag1 > 0.0D), "inverse covariance gave zero magnitude");
/* 151 */     ass((mag2 > 0.0D), "magnitude was zero when dot product was not zero");
/*     */ 
/*     */     
/* 154 */     t = vect.clone();
/* 155 */     t.constrain();
/* 156 */     double mag3 = t.magnitude();
/* 157 */     ass((mag3 > 0.0D), "constrain() gave zero magnitude");
/* 158 */     t.dispose();
/*     */ 
/*     */     
/* 161 */     t = vect.clone();
/* 162 */     t.postCondition();
/* 163 */     t.dispose();
/*     */ 
/*     */     
/* 166 */     String vs = vect.toString();
/* 167 */     assert vs != null && vs.length() > 0;
/*     */ 
/*     */     
/* 170 */     byte[] data = null;
/*     */     try {
/* 172 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 173 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 174 */       t = vect.clone();
/* 175 */       oos.writeObject(t);
/* 176 */       oos.flush();
/* 177 */       oos.close();
/* 178 */       t.dispose();
/* 179 */       t = null;
/* 180 */       data = baos.toByteArray();
/* 181 */     } catch (IOException e) {
/* 182 */       LOG.log(Level.SEVERE, "", e);
/* 183 */       ass(false, "writing serialization failed " + e.getMessage());
/*     */     } 
/*     */     try {
/* 186 */       ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 187 */       ObjectInputStream ois = new ObjectInputStream(bais);
/* 188 */       t = (Vect)ois.readObject();
/* 189 */       ass(areSame(t, vect), "Serialization did not preserve Vect " + t
/*     */           
/* 191 */           .dot(t) + "==" + t.dot(vect) + "==" + vect.dot(vect));
/*     */       
/* 193 */       scale(t, 0.5D);
/* 194 */       double tt = t.dot(t);
/* 195 */       double tv = t.dot(vect);
/* 196 */       double vv = vect.dot(vect);
/* 197 */       ass((tt > 0.0D), "Scaling set serialized vect to zero magnitude");
/* 198 */       ass(Almost.FLOAT.equal(tt * 2.0D, tv), "Serialized vector does not have independent magnitude tt=" + tt + " tv=" + tv);
/*     */ 
/*     */       
/* 201 */       ass(Almost.FLOAT.equal(tv * 2.0D, vv), "serialized vector does not have independent magnitude tv=" + tv + " vv=" + vv);
/*     */ 
/*     */       
/* 204 */       t.dispose();
/* 205 */     } catch (IOException e) {
/* 206 */       LOG.log(Level.SEVERE, "", e);
/* 207 */       ass(false, "reading serialization failed " + e.getMessage());
/* 208 */     } catch (ClassNotFoundException e) {
/* 209 */       LOG.log(Level.SEVERE, "", e);
/* 210 */       ass(false, "Can't find class just written " + e.getMessage());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getTransposePrecision(VectConst data, VectConst model, LinearTransform transform) {
/* 225 */     return getTransposePrecision(data, model, new LinearTransformWrapper(transform));
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
/*     */   public static int getTransposePrecision(VectConst data, VectConst model, Transform transform) {
/* 240 */     test(data);
/* 241 */     test(model);
/* 242 */     boolean dampOnlyPerturbation = true;
/* 243 */     TransformQuadratic tq = new TransformQuadratic(data, model, null, transform, true);
/*     */     
/* 245 */     int precision = 200;
/* 246 */     precision = Math.min(precision, tq.getTransposePrecision());
/* 247 */     return precision;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void ass(boolean condition, String requirement) {
/* 252 */     if (!condition)
/* 253 */       throw new IllegalStateException(requirement); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/VectUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import org.jfree.util.BooleanList;
/*     */ import org.jfree.util.PaintList;
/*     */ import org.jfree.util.StrokeList;
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
/*     */ public class HashUtilities
/*     */ {
/*     */   public static int hashCodeForPaint(Paint p) {
/*     */     int result;
/*  72 */     if (p == null) {
/*  73 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (p instanceof GradientPaint) {
/*  78 */       GradientPaint gp = (GradientPaint)p;
/*  79 */       result = 193;
/*  80 */       result = 37 * result + gp.getColor1().hashCode();
/*  81 */       result = 37 * result + gp.getPoint1().hashCode();
/*  82 */       result = 37 * result + gp.getColor2().hashCode();
/*  83 */       result = 37 * result + gp.getPoint2().hashCode();
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  89 */       result = p.hashCode();
/*     */     } 
/*  91 */     return result;
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
/*     */   public static int hashCodeForDoubleArray(double[] a) {
/* 103 */     if (a == null) {
/* 104 */       return 0;
/*     */     }
/* 106 */     int result = 193;
/*     */     
/* 108 */     for (int i = 0; i < a.length; i++) {
/* 109 */       long temp = Double.doubleToLongBits(a[i]);
/* 110 */       result = 29 * result + (int)(temp ^ temp >>> 32L);
/*     */     } 
/* 112 */     return result;
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
/*     */   public static int hashCode(int pre, boolean b) {
/* 127 */     return 37 * pre + (b ? 0 : 1);
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
/*     */   public static int hashCode(int pre, int i) {
/* 142 */     return 37 * pre + i;
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
/*     */   public static int hashCode(int pre, double d) {
/* 157 */     long l = Double.doubleToLongBits(d);
/* 158 */     return 37 * pre + (int)(l ^ l >>> 32L);
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
/*     */   public static int hashCode(int pre, Paint p) {
/* 172 */     return 37 * pre + hashCodeForPaint(p);
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
/*     */   public static int hashCode(int pre, Stroke s) {
/* 186 */     int h = (s != null) ? s.hashCode() : 0;
/* 187 */     return 37 * pre + h;
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
/*     */   public static int hashCode(int pre, String s) {
/* 201 */     int h = (s != null) ? s.hashCode() : 0;
/* 202 */     return 37 * pre + h;
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
/*     */   public static int hashCode(int pre, Comparable c) {
/* 217 */     int h = (c != null) ? c.hashCode() : 0;
/* 218 */     return 37 * pre + h;
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
/*     */   public static int hashCode(int pre, Object obj) {
/* 233 */     int h = (obj != null) ? obj.hashCode() : 0;
/* 234 */     return 37 * pre + h;
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
/*     */   public static int hashCode(int pre, BooleanList list) {
/* 251 */     if (list == null) {
/* 252 */       return pre;
/*     */     }
/* 254 */     int result = 127;
/* 255 */     int size = list.size();
/* 256 */     result = hashCode(result, size);
/*     */ 
/*     */ 
/*     */     
/* 260 */     if (size > 0) {
/* 261 */       result = hashCode(result, list.getBoolean(0));
/* 262 */       if (size > 1) {
/* 263 */         result = hashCode(result, list
/* 264 */             .getBoolean(size - 1));
/* 265 */         if (size > 2) {
/* 266 */           result = hashCode(result, list
/* 267 */               .getBoolean(size / 2));
/*     */         }
/*     */       } 
/*     */     } 
/* 271 */     return 37 * pre + result;
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
/*     */   public static int hashCode(int pre, PaintList list) {
/* 288 */     if (list == null) {
/* 289 */       return pre;
/*     */     }
/* 291 */     int result = 127;
/* 292 */     int size = list.size();
/* 293 */     result = hashCode(result, size);
/*     */ 
/*     */ 
/*     */     
/* 297 */     if (size > 0) {
/* 298 */       result = hashCode(result, list.getPaint(0));
/* 299 */       if (size > 1) {
/* 300 */         result = hashCode(result, list
/* 301 */             .getPaint(size - 1));
/* 302 */         if (size > 2) {
/* 303 */           result = hashCode(result, list
/* 304 */               .getPaint(size / 2));
/*     */         }
/*     */       } 
/*     */     } 
/* 308 */     return 37 * pre + result;
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
/*     */   public static int hashCode(int pre, StrokeList list) {
/* 325 */     if (list == null) {
/* 326 */       return pre;
/*     */     }
/* 328 */     int result = 127;
/* 329 */     int size = list.size();
/* 330 */     result = hashCode(result, size);
/*     */ 
/*     */ 
/*     */     
/* 334 */     if (size > 0) {
/* 335 */       result = hashCode(result, list.getStroke(0));
/* 336 */       if (size > 1) {
/* 337 */         result = hashCode(result, list
/* 338 */             .getStroke(size - 1));
/* 339 */         if (size > 2) {
/* 340 */           result = hashCode(result, list
/* 341 */               .getStroke(size / 2));
/*     */         }
/*     */       } 
/*     */     } 
/* 345 */     return 37 * pre + result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/HashUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
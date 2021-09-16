/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.DoublePoint;
/*     */ import Catalano.Core.DoubleRange;
/*     */ import Catalano.Core.IntPoint;
/*     */ import Catalano.Math.Tools;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class KCurvature
/*     */ {
/*     */   public int k;
/*     */   public DoubleRange theta;
/*     */   private int suppression;
/*     */   
/*     */   public int getK() {
/*  49 */     return this.k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setK(int k) {
/*  57 */     this.k = k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleRange getTheta() {
/*  65 */     return this.theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTheta(DoubleRange theta) {
/*  73 */     this.theta = theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSuppression() {
/*  81 */     return this.suppression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSuppression(int suppression) {
/*  89 */     this.suppression = suppression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KCurvature(int k, DoubleRange theta) {
/*  98 */     this.k = k;
/*  99 */     this.theta = theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<IntPoint> FindPeaks(List<IntPoint> contour) {
/* 108 */     double[] map = new double[contour.size()];
/*     */     
/* 110 */     for (int i = 0; i < contour.size(); i++) {
/*     */       
/* 112 */       int ai = Tools.Mod(i + this.k, contour.size());
/* 113 */       int ci = Tools.Mod(i - this.k, contour.size());
/*     */       
/* 115 */       IntPoint a = contour.get(ai);
/* 116 */       IntPoint b = contour.get(i);
/* 117 */       IntPoint c = contour.get(ci);
/*     */       
/* 119 */       DoublePoint ab = new DoublePoint(b.x - a.x, b.y - a.y);
/* 120 */       DoublePoint cb = new DoublePoint(b.x - c.x, b.y - c.y);
/*     */       
/* 122 */       double angba = Math.atan2(ab.y, ab.x);
/* 123 */       double angbc = Math.atan2(cb.y, cb.x);
/* 124 */       double rslt = angba - angbc;
/*     */       
/* 126 */       if (rslt < 0.0D) rslt += 6.283185307179586D;
/*     */       
/* 128 */       double rs = rslt * 180.0D / Math.PI;
/*     */       
/* 130 */       if (this.theta.isInside(rs)) map[i] = rs;
/*     */     
/*     */     } 
/*     */     
/* 134 */     int r = this.suppression;
/* 135 */     List<IntPoint> peaks = new ArrayList<>();
/* 136 */     for (int j = 0; j < map.length; j++) {
/* 137 */       double current = map[j];
/* 138 */       if (current != 0.0D) {
/*     */         
/* 140 */         boolean isMinimum = true;
/*     */         
/* 142 */         for (int k = -r; k < r && isMinimum; k++) {
/* 143 */           int index = Tools.Mod(j + k, map.length);
/*     */           
/* 145 */           double candidate = map[index];
/*     */           
/* 147 */           if (candidate != 0.0D)
/*     */           {
/*     */             
/* 150 */             if (candidate < current)
/* 151 */             { isMinimum = false; }
/* 152 */             else { map[index] = 0.0D; }
/*     */              } 
/* 154 */         }  if (isMinimum) peaks.add(contour.get(j)); 
/*     */       } 
/* 156 */     }  return peaks;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/KCurvature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
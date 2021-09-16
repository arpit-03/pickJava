/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ public class LineUtilities
/*     */ {
/*     */   public static boolean clipLine(Line2D line, Rectangle2D rect) {
/*  65 */     double x1 = line.getX1();
/*  66 */     double y1 = line.getY1();
/*  67 */     double x2 = line.getX2();
/*  68 */     double y2 = line.getY2();
/*     */     
/*  70 */     double minX = rect.getMinX();
/*  71 */     double maxX = rect.getMaxX();
/*  72 */     double minY = rect.getMinY();
/*  73 */     double maxY = rect.getMaxY();
/*     */     
/*  75 */     int f1 = rect.outcode(x1, y1);
/*  76 */     int f2 = rect.outcode(x2, y2);
/*     */     
/*  78 */     while ((f1 | f2) != 0) {
/*  79 */       if ((f1 & f2) != 0) {
/*  80 */         return false;
/*     */       }
/*  82 */       double dx = x2 - x1;
/*  83 */       double dy = y2 - y1;
/*     */ 
/*     */       
/*  86 */       if (f1 != 0) {
/*     */ 
/*     */         
/*  89 */         if ((f1 & 0x1) == 1 && dx != 0.0D) {
/*     */           
/*  91 */           y1 += (minX - x1) * dy / dx;
/*  92 */           x1 = minX;
/*     */         }
/*  94 */         else if ((f1 & 0x4) == 4 && dx != 0.0D) {
/*     */           
/*  96 */           y1 += (maxX - x1) * dy / dx;
/*  97 */           x1 = maxX;
/*     */         }
/*  99 */         else if ((f1 & 0x8) == 8 && dy != 0.0D) {
/*     */           
/* 101 */           x1 += (maxY - y1) * dx / dy;
/* 102 */           y1 = maxY;
/*     */         }
/* 104 */         else if ((f1 & 0x2) == 2 && dy != 0.0D) {
/*     */           
/* 106 */           x1 += (minY - y1) * dx / dy;
/* 107 */           y1 = minY;
/*     */         } 
/* 109 */         f1 = rect.outcode(x1, y1); continue;
/*     */       } 
/* 111 */       if (f2 != 0) {
/*     */ 
/*     */         
/* 114 */         if ((f2 & 0x1) == 1 && dx != 0.0D) {
/*     */           
/* 116 */           y2 += (minX - x2) * dy / dx;
/* 117 */           x2 = minX;
/*     */         }
/* 119 */         else if ((f2 & 0x4) == 4 && dx != 0.0D) {
/*     */           
/* 121 */           y2 += (maxX - x2) * dy / dx;
/* 122 */           x2 = maxX;
/*     */         }
/* 124 */         else if ((f2 & 0x8) == 8 && dy != 0.0D) {
/*     */           
/* 126 */           x2 += (maxY - y2) * dx / dy;
/* 127 */           y2 = maxY;
/*     */         }
/* 129 */         else if ((f2 & 0x2) == 2 && dy != 0.0D) {
/*     */           
/* 131 */           x2 += (minY - y2) * dx / dy;
/* 132 */           y2 = minY;
/*     */         } 
/* 134 */         f2 = rect.outcode(x2, y2);
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     line.setLine(x1, y1, x2, y2);
/* 139 */     return true;
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
/*     */   public static Line2D extendLine(Line2D line, double startPercent, double endPercent) {
/* 158 */     ParamChecks.nullNotPermitted(line, "line");
/* 159 */     double x1 = line.getX1();
/* 160 */     double x2 = line.getX2();
/* 161 */     double deltaX = x2 - x1;
/* 162 */     double y1 = line.getY1();
/* 163 */     double y2 = line.getY2();
/* 164 */     double deltaY = y2 - y1;
/* 165 */     x1 -= startPercent * deltaX;
/* 166 */     y1 -= startPercent * deltaY;
/* 167 */     x2 += endPercent * deltaX;
/* 168 */     y2 += endPercent * deltaY;
/* 169 */     return new Line2D.Double(x1, y1, x2, y2);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/LineUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
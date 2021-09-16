/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
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
/*     */ 
/*     */ 
/*     */ public final class QuadrilateralTransformationCalc
/*     */ {
/*     */   private static final double TOLERANCE = 1.0E-13D;
/*     */   
/*     */   private static double Det2(double a, double b, double c, double d) {
/*  45 */     return a * d - b * c;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[][] MultiplyMatrix(double[][] a, double[][] b) {
/*  50 */     double[][] c = new double[3][3];
/*     */     
/*  52 */     c[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0] + a[0][2] * b[2][0];
/*  53 */     c[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1] + a[0][2] * b[2][1];
/*  54 */     c[0][2] = a[0][0] * b[0][2] + a[0][1] * b[1][2] + a[0][2] * b[2][2];
/*  55 */     c[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0] + a[1][2] * b[2][0];
/*  56 */     c[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1] + a[1][2] * b[2][1];
/*  57 */     c[1][2] = a[1][0] * b[0][2] + a[1][1] * b[1][2] + a[1][2] * b[2][2];
/*  58 */     c[2][0] = a[2][0] * b[0][0] + a[2][1] * b[1][0] + a[2][2] * b[2][0];
/*  59 */     c[2][1] = a[2][0] * b[0][1] + a[2][1] * b[1][1] + a[2][2] * b[2][1];
/*  60 */     c[2][2] = a[2][0] * b[0][2] + a[2][1] * b[1][2] + a[2][2] * b[2][2];
/*     */     
/*  62 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[][] AdjugateMatrix(double[][] a) {
/*  68 */     double[][] b = new double[3][3];
/*  69 */     b[0][0] = Det2(a[1][1], a[1][2], a[2][1], a[2][2]);
/*  70 */     b[1][0] = Det2(a[1][2], a[1][0], a[2][2], a[2][0]);
/*  71 */     b[2][0] = Det2(a[1][0], a[1][1], a[2][0], a[2][1]);
/*  72 */     b[0][1] = Det2(a[2][1], a[2][2], a[0][1], a[0][2]);
/*  73 */     b[1][1] = Det2(a[2][2], a[2][0], a[0][2], a[0][0]);
/*  74 */     b[2][1] = Det2(a[2][0], a[2][1], a[0][0], a[0][1]);
/*  75 */     b[0][2] = Det2(a[0][1], a[0][2], a[1][1], a[1][2]);
/*  76 */     b[1][2] = Det2(a[0][2], a[0][0], a[1][2], a[1][0]);
/*  77 */     b[2][2] = Det2(a[0][0], a[0][1], a[1][0], a[1][1]);
/*     */     
/*  79 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   private static double[][] MapSquareToQuad(List<IntPoint> quad) {
/*  84 */     double[][] sq = new double[3][3];
/*     */ 
/*     */     
/*  87 */     double px = (((IntPoint)quad.get(0)).x - ((IntPoint)quad.get(1)).x + ((IntPoint)quad.get(2)).x - ((IntPoint)quad.get(3)).x);
/*  88 */     double py = (((IntPoint)quad.get(0)).y - ((IntPoint)quad.get(1)).y + ((IntPoint)quad.get(2)).y - ((IntPoint)quad.get(3)).y);
/*     */     
/*  90 */     if (px < 1.0E-13D && px > -1.0E-13D && 
/*  91 */       py < 1.0E-13D && py > -1.0E-13D) {
/*     */       
/*  93 */       sq[0][0] = (((IntPoint)quad.get(1)).x - ((IntPoint)quad.get(0)).x);
/*  94 */       sq[0][1] = (((IntPoint)quad.get(2)).x - ((IntPoint)quad.get(1)).x);
/*  95 */       sq[0][2] = ((IntPoint)quad.get(0)).x;
/*     */       
/*  97 */       sq[1][0] = (((IntPoint)quad.get(1)).y - ((IntPoint)quad.get(0)).y);
/*  98 */       sq[1][1] = (((IntPoint)quad.get(2)).y - ((IntPoint)quad.get(1)).y);
/*  99 */       sq[1][2] = ((IntPoint)quad.get(0)).y;
/*     */       
/* 101 */       sq[2][0] = 0.0D;
/* 102 */       sq[2][1] = 0.0D;
/* 103 */       sq[2][2] = 1.0D;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 109 */       double dx1 = (((IntPoint)quad.get(1)).x - ((IntPoint)quad.get(2)).x);
/* 110 */       double dx2 = (((IntPoint)quad.get(3)).x - ((IntPoint)quad.get(2)).x);
/* 111 */       double dy1 = (((IntPoint)quad.get(1)).y - ((IntPoint)quad.get(2)).y);
/* 112 */       double dy2 = (((IntPoint)quad.get(3)).y - ((IntPoint)quad.get(2)).y);
/*     */       
/* 114 */       double del = Det2(dx1, dx2, dy1, dy2);
/*     */       
/* 116 */       if (del == 0.0D) {
/* 117 */         return null;
/*     */       }
/* 119 */       sq[2][0] = Det2(px, dx2, py, dy2) / del;
/* 120 */       sq[2][1] = Det2(dx1, px, dy1, py) / del;
/* 121 */       sq[2][2] = 1.0D;
/*     */       
/* 123 */       sq[0][0] = (((IntPoint)quad.get(1)).x - ((IntPoint)quad.get(0)).x) + sq[2][0] * ((IntPoint)quad.get(1)).x;
/* 124 */       sq[0][1] = (((IntPoint)quad.get(3)).x - ((IntPoint)quad.get(0)).x) + sq[2][1] * ((IntPoint)quad.get(3)).x;
/* 125 */       sq[0][2] = ((IntPoint)quad.get(0)).x;
/*     */       
/* 127 */       sq[1][0] = (((IntPoint)quad.get(1)).y - ((IntPoint)quad.get(0)).y) + sq[2][0] * ((IntPoint)quad.get(1)).y;
/* 128 */       sq[1][1] = (((IntPoint)quad.get(3)).y - ((IntPoint)quad.get(0)).y) + sq[2][1] * ((IntPoint)quad.get(3)).y;
/* 129 */       sq[1][2] = ((IntPoint)quad.get(0)).y;
/*     */     } 
/* 131 */     return sq;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double[][] MapQuadToQuad(List<IntPoint> input, List<IntPoint> output) {
/* 136 */     double[][] squareToInpit = MapSquareToQuad(input);
/* 137 */     double[][] squareToOutput = MapSquareToQuad(output);
/*     */     
/* 139 */     if (squareToOutput == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     return MultiplyMatrix(squareToOutput, AdjugateMatrix(squareToInpit));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/QuadrilateralTransformationCalc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
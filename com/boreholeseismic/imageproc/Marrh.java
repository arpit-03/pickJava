/*     */ package com.boreholeseismic.imageproc;
/*     */ 
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Marrh
/*     */   extends JFrame
/*     */ {
/*  90 */   int[] di = new int[] { 0, -1, -1, -1, 1, 1, 1 };
/*  91 */   int[] dj = new int[] { 1, 1, -1, -1, -1, 1 };
/*     */ 
/*     */   
/*     */   public Marrh(BufferedImage src) {
/*  95 */     double dp = 1.0D;
/*     */     
/*  97 */     BufferedImage dest1 = null, dest2 = null;
/*     */ 
/*     */ 
/*     */     
/* 101 */     int w = src.getWidth();
/* 102 */     int h = src.getHeight();
/* 103 */     Raster srcR = src.getRaster();
/* 104 */     int tipo = 10;
/* 105 */     dest1 = new BufferedImage(w, h, tipo);
/* 106 */     dest2 = new BufferedImage(w, h, tipo);
/* 107 */     WritableRaster dest1WR = dest1.getRaster();
/* 108 */     WritableRaster dest2WR = dest2.getRaster();
/*     */ 
/*     */     
/* 111 */     for (int i = 0; i < h; i++) {
/* 112 */       for (int m = 0; m < w; m++) {
/* 113 */         int pixel = srcR.getSample(m, i, 0);
/* 114 */         dest1WR.setSample(m, i, 0, pixel);
/* 115 */         dest2WR.setSample(m, i, 0, pixel);
/*     */       } 
/*     */     } 
/* 118 */     marrOp(dp - 0.8D, dest1);
/* 119 */     marrOp(dp + 0.8D, dest2);
/*     */     
/* 121 */     Raster dest1R = dest1.getRaster();
/* 122 */     Raster dest2R = dest2.getRaster();
/* 123 */     for (int k = 0; k < h; k++) {
/* 124 */       for (int m = 0; m < w; m++) {
/* 125 */         if (dest1R.getSample(m, k, 0) > 0 && 
/* 126 */           dest2R.getSample(m, k, 0) > 0) {
/* 127 */           dest1WR.setSample(m, k, 0, 0);
/*     */         } else {
/* 129 */           dest1WR.setSample(m, k, 0, 255);
/*     */         } 
/*     */       } 
/* 132 */     }  JFrame j = new JFrame("TEst");
/* 133 */     j.getContentPane().setLayout(new GridLayout(1, 2));
/* 134 */     JLabel img1 = new JLabel(new ImageIcon(src));
/* 135 */     JLabel img2 = new JLabel(new ImageIcon(dest1));
/* 136 */     j.setSize(2 * w, h);
/* 137 */     j.getContentPane().add(new JScrollPane(img2));
/* 138 */     j.getContentPane().add(new JScrollPane(img2));
/* 139 */     j.setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marrOp(double s, BufferedImage src) {
/* 149 */     int nCol = src.getWidth();
/* 150 */     int nLin = src.getHeight();
/*     */     
/* 152 */     WritableRaster srcWR = src.getRaster();
/*     */ 
/*     */     
/* 155 */     int width = (int)(3.35D * s + 0.33D);
/* 156 */     int n = 2 * width + 1;
/* 157 */     System.out.println("Suavizando com uma Gaussiana de tamanho " + n + 
/* 158 */         "x" + n + "\n");
/* 159 */     double[][] amostraLoG = new double[n][n]; int i;
/* 160 */     for (i = 0; i < n; i++) {
/* 161 */       for (int k = 0; k < n; k++) {
/* 162 */         amostraLoG[k][i] = funcLoG(distancia(i, k, 
/* 163 */               width, width), s);
/*     */       }
/*     */     } 
/* 166 */     System.out.println("Convolucao com Laplaciano do Gaussiano:\n");
/* 167 */     double[][] arrayLaplace = convolveImagem(src, amostraLoG);
/*     */ 
/*     */     
/* 170 */     System.out.println("Cruzamentos zero:\n");
/* 171 */     cruzaZero(arrayLaplace, src);
/*     */ 
/*     */     
/* 174 */     for (i = 0; i < nLin; i++) {
/* 175 */       int k; for (k = 0; k <= width; k++)
/* 176 */         srcWR.setSample(k, i, 0, 0); 
/* 177 */       for (k = nCol - width - 1; k < nCol; k++)
/* 178 */         srcWR.setSample(k, i, 0, 0); 
/*     */     } 
/* 180 */     for (int j = 0; j < nCol; j++) {
/* 181 */       int k; for (k = 0; k <= width; k++)
/* 182 */         srcWR.setSample(j, k, 0, 0); 
/* 183 */       for (k = nLin - width - 1; k < nLin; k++)
/* 184 */         srcWR.setSample(j, k, 0, 0); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double distancia(double a, double b, double c, double d) {
/* 189 */     return Math.sqrt((a - c) * (a - c) + (b - d) * (b - d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double funcLoG(double r, double sigma) {
/* 196 */     double x1 = gauss(r, sigma);
/* 197 */     return (r * r - 2.0D * sigma * sigma) / sigma * sigma * sigma * sigma * x1;
/*     */   }
/*     */ 
/*     */   
/*     */   private double gauss(double r, double sigma) {
/* 202 */     return Math.exp(-r * r / 2.0D * sigma * sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[][] convolveImagem(BufferedImage im, double[][] amostraLG) {
/* 210 */     int imCol = im.getWidth();
/* 211 */     int imLin = im.getHeight();
/* 212 */     int nLin = amostraLG.length, nCol = nLin;
/*     */     
/* 214 */     Raster imR = im.getRaster();
/*     */     
/* 216 */     double[][] res = new double[imCol][imLin];
/* 217 */     int nLin2 = nLin / 2;
/* 218 */     int nCol2 = nCol / 2;
/* 219 */     for (int i = 0; i < imLin; i++) {
/* 220 */       for (int j = 0; j < imCol; j++) {
/* 221 */         double x = 0.0D;
/* 222 */         for (int ii = 0; ii < nLin; ii++) {
/* 223 */           int n = i - nLin2 + ii;
/* 224 */           if (n >= 0 && n < imLin)
/*     */           {
/* 226 */             for (int jj = 0; jj < nCol; jj++) {
/* 227 */               int m = j - nCol2 + jj;
/* 228 */               if (m >= 0 && m < imCol)
/*     */               {
/* 230 */                 x += amostraLG[jj][ii] * imR.getSample(m, n, 0); } 
/*     */             }  } 
/*     */         } 
/* 233 */         res[j][i] = x;
/*     */       } 
/* 235 */     }  return res;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cruzaZero(double[][] imgLaplaciano, BufferedImage im) {
/* 242 */     int w = im.getWidth();
/* 243 */     int h = im.getHeight();
/* 244 */     WritableRaster imWR = im.getRaster();
/*     */     
/* 246 */     for (int i = 1; i < h - 1; i++) {
/* 247 */       for (int j = 1; j < w - 1; j++) {
/* 248 */         imWR.setSample(j, i, 0, 0);
/* 249 */         if (imgLaplaciano[j][i - 1] * imgLaplaciano[j][i + 1] < 0.0D) {
/* 250 */           imWR.setSample(j, i, 0, 255);
/*     */         
/*     */         }
/* 253 */         else if (imgLaplaciano[j - 1][i] * imgLaplaciano[j + 1][i] < 0.0D) {
/* 254 */           imWR.setSample(j, i, 0, 255);
/*     */         
/*     */         }
/* 257 */         else if (imgLaplaciano[j - 1][i + 1] * imgLaplaciano[j + 1][i - 1] < 0.0D) {
/* 258 */           imWR.setSample(j, i, 0, 255);
/*     */         
/*     */         }
/* 261 */         else if (imgLaplaciano[j - 1][i - 1] * imgLaplaciano[j + 1][i + 1] < 0.0D) {
/* 262 */           imWR.setSample(j, i, 0, 255);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/imageproc/Marrh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
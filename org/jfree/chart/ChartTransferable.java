/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartTransferable
/*     */   implements Transferable
/*     */ {
/*  62 */   final DataFlavor imageFlavor = new DataFlavor("image/x-java-image; class=java.awt.Image", "Image");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JFreeChart chart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int width;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int height;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int minDrawWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int minDrawHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxDrawWidth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int maxDrawHeight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartTransferable(JFreeChart chart, int width, int height) {
/* 114 */     this(chart, width, height, true);
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
/*     */   public ChartTransferable(JFreeChart chart, int width, int height, boolean cloneData) {
/* 127 */     this(chart, width, height, 0, 0, 2147483647, 2147483647, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartTransferable(JFreeChart chart, int width, int height, int minDrawW, int minDrawH, int maxDrawW, int maxDrawH, boolean cloneData) {
/*     */     try {
/* 155 */       this.chart = (JFreeChart)chart.clone();
/*     */     }
/* 157 */     catch (CloneNotSupportedException e) {
/* 158 */       this.chart = chart;
/*     */     } 
/*     */ 
/*     */     
/* 162 */     this.width = width;
/* 163 */     this.height = height;
/* 164 */     this.minDrawWidth = minDrawW;
/* 165 */     this.minDrawHeight = minDrawH;
/* 166 */     this.maxDrawWidth = maxDrawW;
/* 167 */     this.maxDrawHeight = maxDrawH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/* 177 */     return new DataFlavor[] { this.imageFlavor };
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
/*     */   public boolean isDataFlavorSupported(DataFlavor flavor) {
/* 189 */     return this.imageFlavor.equals(flavor);
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
/*     */   public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
/* 206 */     if (this.imageFlavor.equals(flavor)) {
/* 207 */       return createBufferedImage(this.chart, this.width, this.height, this.minDrawWidth, this.minDrawHeight, this.maxDrawWidth, this.maxDrawHeight);
/*     */     }
/*     */ 
/*     */     
/* 211 */     throw new UnsupportedFlavorException(flavor);
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
/*     */ 
/*     */ 
/*     */   
/*     */   private BufferedImage createBufferedImage(JFreeChart chart, int w, int h, int minDrawW, int minDrawH, int maxDrawW, int maxDrawH) {
/* 233 */     BufferedImage image = new BufferedImage(w, h, 2);
/*     */     
/* 235 */     Graphics2D g2 = image.createGraphics();
/*     */ 
/*     */     
/* 238 */     boolean scale = false;
/* 239 */     double drawWidth = w;
/* 240 */     double drawHeight = h;
/* 241 */     double scaleX = 1.0D;
/* 242 */     double scaleY = 1.0D;
/* 243 */     if (drawWidth < minDrawW) {
/* 244 */       scaleX = drawWidth / minDrawW;
/* 245 */       drawWidth = minDrawW;
/* 246 */       scale = true;
/*     */     }
/* 248 */     else if (drawWidth > maxDrawW) {
/* 249 */       scaleX = drawWidth / maxDrawW;
/* 250 */       drawWidth = maxDrawW;
/* 251 */       scale = true;
/*     */     } 
/* 253 */     if (drawHeight < minDrawH) {
/* 254 */       scaleY = drawHeight / minDrawH;
/* 255 */       drawHeight = minDrawH;
/* 256 */       scale = true;
/*     */     }
/* 258 */     else if (drawHeight > maxDrawH) {
/* 259 */       scaleY = drawHeight / maxDrawH;
/* 260 */       drawHeight = maxDrawH;
/* 261 */       scale = true;
/*     */     } 
/*     */     
/* 264 */     Rectangle2D chartArea = new Rectangle2D.Double(0.0D, 0.0D, drawWidth, drawHeight);
/*     */     
/* 266 */     if (scale) {
/* 267 */       AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
/*     */       
/* 269 */       g2.transform(st);
/*     */     } 
/* 271 */     chart.draw(g2, chartArea, null, null);
/* 272 */     g2.dispose();
/* 273 */     return image;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartTransferable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
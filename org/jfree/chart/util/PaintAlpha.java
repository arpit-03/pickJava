/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.LinearGradientPaint;
/*     */ import java.awt.Paint;
/*     */ import java.awt.RadialGradientPaint;
/*     */ import java.awt.TexturePaint;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaintAlpha
/*     */ {
/*     */   private static final double FACTOR = 0.7D;
/*     */   private static boolean legacyAlpha = false;
/*     */   
/*     */   public static boolean setLegacyAlpha(boolean legacyAlpha) {
/* 108 */     boolean old = PaintAlpha.legacyAlpha;
/* 109 */     PaintAlpha.legacyAlpha = legacyAlpha;
/* 110 */     return old;
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
/*     */   public static Paint darker(Paint paint) {
/* 124 */     if (paint instanceof Color) {
/* 125 */       return darker((Color)paint);
/*     */     }
/* 127 */     if (legacyAlpha == true)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 132 */       return paint;
/*     */     }
/* 134 */     if (paint instanceof GradientPaint) {
/* 135 */       return darker((GradientPaint)paint);
/*     */     }
/* 137 */     if (paint instanceof LinearGradientPaint) {
/* 138 */       return darkerLinearGradientPaint((LinearGradientPaint)paint);
/*     */     }
/* 140 */     if (paint instanceof RadialGradientPaint) {
/* 141 */       return darkerRadialGradientPaint((RadialGradientPaint)paint);
/*     */     }
/* 143 */     if (paint instanceof TexturePaint) {
/*     */       try {
/* 145 */         return darkerTexturePaint((TexturePaint)paint);
/*     */       }
/* 147 */       catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 154 */         return paint;
/*     */       } 
/*     */     }
/* 157 */     return paint;
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
/*     */   private static Color darker(Color paint) {
/* 171 */     return new Color(
/* 172 */         (int)(paint.getRed() * 0.7D), 
/* 173 */         (int)(paint.getGreen() * 0.7D), 
/* 174 */         (int)(paint.getBlue() * 0.7D), paint.getAlpha());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static GradientPaint darker(GradientPaint paint) {
/* 185 */     return new GradientPaint(paint
/* 186 */         .getPoint1(), darker(paint.getColor1()), paint
/* 187 */         .getPoint2(), darker(paint.getColor2()), paint
/* 188 */         .isCyclic());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Paint darkerLinearGradientPaint(LinearGradientPaint paint) {
/* 199 */     Color[] paintColors = paint.getColors();
/* 200 */     for (int i = 0; i < paintColors.length; i++) {
/* 201 */       paintColors[i] = darker(paintColors[i]);
/*     */     }
/* 203 */     return new LinearGradientPaint(paint.getStartPoint(), paint
/* 204 */         .getEndPoint(), paint.getFractions(), paintColors, paint
/* 205 */         .getCycleMethod(), paint.getColorSpace(), paint
/* 206 */         .getTransform());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Paint darkerRadialGradientPaint(RadialGradientPaint paint) {
/* 217 */     Color[] paintColors = paint.getColors();
/* 218 */     for (int i = 0; i < paintColors.length; i++) {
/* 219 */       paintColors[i] = darker(paintColors[i]);
/*     */     }
/* 221 */     return new RadialGradientPaint(paint.getCenterPoint(), paint
/* 222 */         .getRadius(), paint.getFocusPoint(), paint
/* 223 */         .getFractions(), paintColors, paint.getCycleMethod(), paint
/* 224 */         .getColorSpace(), paint.getTransform());
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
/*     */   private static TexturePaint darkerTexturePaint(TexturePaint paint) {
/* 245 */     if (paint.getImage().getColorModel().isAlphaPremultiplied());
/*     */ 
/*     */ 
/*     */     
/* 249 */     BufferedImage img = cloneImage(paint.getImage());
/*     */     
/* 251 */     WritableRaster ras = img.copyData(null);
/*     */     
/* 253 */     int miX = ras.getMinX();
/* 254 */     int miY = ras.getMinY();
/* 255 */     int maY = ras.getMinY() + ras.getHeight();
/*     */     
/* 257 */     int wid = ras.getWidth();
/*     */     
/* 259 */     int[] pix = new int[wid * img.getSampleModel().getNumBands()];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     if (img.getColorModel() instanceof java.awt.image.IndexColorModel) {
/*     */       
/* 282 */       int[] nco = new int[4];
/*     */ 
/*     */       
/* 285 */       for (int i = miY; i < maY; i++) {
/*     */         
/* 287 */         pix = ras.getPixels(miX, i, wid, 1, pix);
/*     */         
/* 289 */         for (int p = 0; p < pix.length; p++) {
/* 290 */           nco = img.getColorModel().getComponents(pix[p], nco, 0);
/* 291 */           nco[0] = (int)(nco[0] * 0.7D);
/* 292 */           nco[1] = (int)(nco[1] * 0.7D);
/* 293 */           nco[2] = (int)(nco[2] * 0.7D);
/*     */           
/* 295 */           pix[p] = img.getColorModel().getDataElement(nco, 0);
/*     */         } 
/* 297 */         ras.setPixels(miX, i, wid, 1, pix);
/*     */       } 
/* 299 */       img.setData(ras);
/*     */       
/* 301 */       return new TexturePaint(img, paint.getAnchorRect());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (img.getSampleModel().getNumBands() == 4) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       for (int i = miY; i < maY; i++) {
/*     */         
/* 319 */         pix = ras.getPixels(miX, i, wid, 1, pix);
/*     */         
/* 321 */         for (int p = 0; p < pix.length; ) {
/* 322 */           pix[p] = (int)(pix[p++] * 0.7D);
/* 323 */           pix[p] = (int)(pix[p++] * 0.7D);
/* 324 */           pix[p] = (int)(pix[p++] * 0.7D);
/* 325 */           p++;
/*     */         } 
/* 327 */         ras.setPixels(miX, i, wid, 1, pix);
/*     */       } 
/* 329 */       img.setData(ras);
/* 330 */       return new TexturePaint(img, paint.getAnchorRect());
/*     */     } 
/* 332 */     for (int y = miY; y < maY; y++) {
/*     */       
/* 334 */       pix = ras.getPixels(miX, y, wid, 1, pix);
/*     */       
/* 336 */       for (int p = 0; p < pix.length; p++) {
/* 337 */         pix[p] = (int)(pix[p] * 0.7D);
/*     */       }
/* 339 */       ras.setPixels(miX, y, wid, 1, pix);
/*     */     } 
/* 341 */     img.setData(ras);
/* 342 */     return new TexturePaint(img, paint.getAnchorRect());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage cloneImage(BufferedImage image) {
/* 380 */     WritableRaster rin = image.getRaster();
/* 381 */     WritableRaster ras = rin.createCompatibleWritableRaster();
/* 382 */     ras.setRect(rin);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     Hashtable<Object, Object> props = null;
/* 390 */     String[] propNames = image.getPropertyNames();
/* 391 */     if (propNames != null) {
/* 392 */       props = new Hashtable<Object, Object>();
/* 393 */       for (int i = 0; i < propNames.length; i++) {
/* 394 */         props.put(propNames[i], image.getProperty(propNames[i]));
/*     */       }
/*     */     } 
/* 397 */     return new BufferedImage(image.getColorModel(), ras, image
/* 398 */         .isAlphaPremultiplied(), props);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/PaintAlpha.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.encoders;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageEncoderFactory
/*     */ {
/*  58 */   private static Map encoders = null;
/*     */   
/*     */   static {
/*  61 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void init() {
/*  69 */     encoders = new HashMap<Object, Object>();
/*  70 */     encoders.put("jpeg", "org.jfree.chart.encoders.SunJPEGEncoderAdapter");
/*  71 */     encoders.put("png", "org.jfree.chart.encoders.SunPNGEncoderAdapter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setImageEncoder(String format, String imageEncoderClassName) {
/*  82 */     encoders.put(format, imageEncoderClassName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageEncoder newInstance(String format) {
/*  93 */     ImageEncoder imageEncoder = null;
/*  94 */     String className = (String)encoders.get(format);
/*  95 */     if (className == null) {
/*  96 */       throw new IllegalArgumentException("Unsupported image format - " + format);
/*     */     }
/*     */     
/*     */     try {
/* 100 */       Class<?> imageEncoderClass = Class.forName(className);
/* 101 */       imageEncoder = (ImageEncoder)imageEncoderClass.newInstance();
/*     */     }
/* 103 */     catch (Exception e) {
/* 104 */       throw new IllegalArgumentException(e.toString());
/*     */     } 
/* 106 */     return imageEncoder;
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
/*     */   public static ImageEncoder newInstance(String format, float quality) {
/* 118 */     ImageEncoder imageEncoder = newInstance(format);
/* 119 */     imageEncoder.setQuality(quality);
/* 120 */     return imageEncoder;
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
/*     */   public static ImageEncoder newInstance(String format, boolean encodingAlpha) {
/* 133 */     ImageEncoder imageEncoder = newInstance(format);
/* 134 */     imageEncoder.setEncodingAlpha(encodingAlpha);
/* 135 */     return imageEncoder;
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
/*     */   public static ImageEncoder newInstance(String format, float quality, boolean encodingAlpha) {
/* 149 */     ImageEncoder imageEncoder = newInstance(format);
/* 150 */     imageEncoder.setQuality(quality);
/* 151 */     imageEncoder.setEncodingAlpha(encodingAlpha);
/* 152 */     return imageEncoder;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/encoders/ImageEncoderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.encoders;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncoderUtil
/*     */ {
/*     */   public static byte[] encode(BufferedImage image, String format) throws IOException {
/*  65 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format);
/*  66 */     return imageEncoder.encode(image);
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
/*     */   public static byte[] encode(BufferedImage image, String format, boolean encodeAlpha) throws IOException {
/*  81 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, encodeAlpha);
/*     */     
/*  83 */     return imageEncoder.encode(image);
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
/*     */   public static byte[] encode(BufferedImage image, String format, float quality) throws IOException {
/*  98 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, quality);
/*     */     
/* 100 */     return imageEncoder.encode(image);
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
/*     */   public static byte[] encode(BufferedImage image, String format, float quality, boolean encodeAlpha) throws IOException {
/* 117 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, quality, encodeAlpha);
/*     */     
/* 119 */     return imageEncoder.encode(image);
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
/*     */   public static void writeBufferedImage(BufferedImage image, String format, OutputStream outputStream) throws IOException {
/* 132 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format);
/* 133 */     imageEncoder.encode(image, outputStream);
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
/*     */   public static void writeBufferedImage(BufferedImage image, String format, OutputStream outputStream, float quality) throws IOException {
/* 148 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, quality);
/*     */     
/* 150 */     imageEncoder.encode(image, outputStream);
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
/*     */   public static void writeBufferedImage(BufferedImage image, String format, OutputStream outputStream, boolean encodeAlpha) throws IOException {
/* 165 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, encodeAlpha);
/*     */     
/* 167 */     imageEncoder.encode(image, outputStream);
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
/*     */   public static void writeBufferedImage(BufferedImage image, String format, OutputStream outputStream, float quality, boolean encodeAlpha) throws IOException {
/* 185 */     ImageEncoder imageEncoder = ImageEncoderFactory.newInstance(format, quality, encodeAlpha);
/*     */     
/* 187 */     imageEncoder.encode(image, outputStream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/encoders/EncoderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
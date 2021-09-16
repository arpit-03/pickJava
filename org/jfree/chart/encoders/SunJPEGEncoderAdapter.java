/*     */ package org.jfree.chart.encoders;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.imageio.IIOImage;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SunJPEGEncoderAdapter
/*     */   implements ImageEncoder
/*     */ {
/*  71 */   private float quality = 0.95F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getQuality() {
/*  90 */     return this.quality;
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
/*     */   public void setQuality(float quality) {
/* 103 */     if (quality < 0.0F || quality > 1.0F) {
/* 104 */       throw new IllegalArgumentException("The 'quality' must be in the range 0.0f to 1.0f");
/*     */     }
/*     */     
/* 107 */     this.quality = quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEncodingAlpha() {
/* 118 */     return false;
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
/*     */   public void setEncodingAlpha(boolean encodingAlpha) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode(BufferedImage bufferedImage) throws IOException {
/* 146 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 147 */     encode(bufferedImage, outputStream);
/* 148 */     return outputStream.toByteArray();
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
/*     */   public void encode(BufferedImage bufferedImage, OutputStream outputStream) throws IOException {
/* 166 */     ParamChecks.nullNotPermitted(bufferedImage, "bufferedImage");
/* 167 */     ParamChecks.nullNotPermitted(outputStream, "outputStream");
/* 168 */     Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpeg");
/* 169 */     ImageWriter writer = iterator.next();
/* 170 */     ImageWriteParam p = writer.getDefaultWriteParam();
/* 171 */     p.setCompressionMode(2);
/* 172 */     p.setCompressionQuality(this.quality);
/* 173 */     ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
/* 174 */     writer.setOutput(ios);
/* 175 */     writer.write(null, new IIOImage(bufferedImage, null, null), p);
/* 176 */     ios.flush();
/* 177 */     writer.dispose();
/* 178 */     ios.close();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/encoders/SunJPEGEncoderAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
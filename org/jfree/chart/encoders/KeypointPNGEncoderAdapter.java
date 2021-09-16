/*     */ package org.jfree.chart.encoders;
/*     */ 
/*     */ import com.keypoint.PngEncoder;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class KeypointPNGEncoderAdapter
/*     */   implements ImageEncoder
/*     */ {
/*  60 */   private int quality = 9;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encodingAlpha = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getQuality() {
/*  74 */     return this.quality;
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
/*  87 */     this.quality = (int)quality;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEncodingAlpha() {
/*  97 */     return this.encodingAlpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingAlpha(boolean encodingAlpha) {
/* 108 */     this.encodingAlpha = encodingAlpha;
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
/*     */   public byte[] encode(BufferedImage bufferedImage) throws IOException {
/* 120 */     ParamChecks.nullNotPermitted(bufferedImage, "bufferedImage");
/* 121 */     PngEncoder encoder = new PngEncoder(bufferedImage, this.encodingAlpha, 0, this.quality);
/*     */     
/* 123 */     return encoder.pngEncode();
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
/*     */   public void encode(BufferedImage bufferedImage, OutputStream outputStream) throws IOException {
/* 137 */     ParamChecks.nullNotPermitted(bufferedImage, "bufferedImage");
/* 138 */     ParamChecks.nullNotPermitted(outputStream, "outputStream");
/* 139 */     PngEncoder encoder = new PngEncoder(bufferedImage, this.encodingAlpha, 0, this.quality);
/*     */     
/* 141 */     outputStream.write(encoder.pngEncode());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/encoders/KeypointPNGEncoderAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
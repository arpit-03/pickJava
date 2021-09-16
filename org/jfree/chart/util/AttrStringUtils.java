/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.text.AttributedString;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttrStringUtils
/*     */ {
/*     */   public static Rectangle2D getTextBounds(AttributedString text, Graphics2D g2) {
/*  75 */     TextLayout tl = new TextLayout(text.getIterator(), g2.getFontRenderContext());
/*  76 */     return tl.getBounds();
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
/*     */   public static void drawRotatedString(AttributedString text, Graphics2D g2, double angle, float x, float y) {
/*  93 */     drawRotatedString(text, g2, x, y, angle, x, y);
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
/*     */   public static void drawRotatedString(AttributedString text, Graphics2D g2, float textX, float textY, double angle, float rotateX, float rotateY) {
/* 113 */     ParamChecks.nullNotPermitted(text, "text");
/*     */     
/* 115 */     AffineTransform saved = g2.getTransform();
/* 116 */     AffineTransform rotate = AffineTransform.getRotateInstance(angle, rotateX, rotateY);
/*     */     
/* 118 */     g2.transform(rotate);
/*     */     
/* 120 */     TextLayout tl = new TextLayout(text.getIterator(), g2.getFontRenderContext());
/* 121 */     tl.draw(g2, textX, textY);
/*     */     
/* 123 */     g2.setTransform(saved);
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
/*     */   public static void drawRotatedString(AttributedString text, Graphics2D g2, float x, float y, TextAnchor textAnchor, double angle, float rotationX, float rotationY) {
/* 144 */     ParamChecks.nullNotPermitted(text, "text");
/* 145 */     float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor, null);
/*     */     
/* 147 */     drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], angle, rotationX, rotationY);
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
/*     */   public static void drawRotatedString(AttributedString text, Graphics2D g2, float x, float y, TextAnchor textAnchor, double angle, TextAnchor rotationAnchor) {
/* 167 */     ParamChecks.nullNotPermitted(text, "text");
/* 168 */     float[] textAdj = deriveTextBoundsAnchorOffsets(g2, text, textAnchor, null);
/*     */     
/* 170 */     float[] rotateAdj = deriveRotationAnchorOffsets(g2, text, rotationAnchor);
/*     */     
/* 172 */     drawRotatedString(text, g2, x + textAdj[0], y + textAdj[1], angle, x + textAdj[0] + rotateAdj[0], y + textAdj[1] + rotateAdj[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2, AttributedString text, TextAnchor anchor, Rectangle2D textBounds) {
/* 180 */     TextLayout layout = new TextLayout(text.getIterator(), g2.getFontRenderContext());
/* 181 */     Rectangle2D bounds = layout.getBounds();
/*     */     
/* 183 */     float[] result = new float[3];
/* 184 */     float ascent = layout.getAscent();
/* 185 */     result[2] = -ascent;
/* 186 */     float halfAscent = ascent / 2.0F;
/* 187 */     float descent = layout.getDescent();
/* 188 */     float leading = layout.getLeading();
/* 189 */     float xAdj = 0.0F;
/* 190 */     float yAdj = 0.0F;
/*     */     
/* 192 */     if (isHorizontalCenter(anchor)) {
/* 193 */       xAdj = (float)-bounds.getWidth() / 2.0F;
/*     */     }
/* 195 */     else if (isHorizontalRight(anchor)) {
/* 196 */       xAdj = (float)-bounds.getWidth();
/*     */     } 
/*     */     
/* 199 */     if (isTop(anchor)) {
/*     */       
/* 201 */       yAdj = (float)bounds.getHeight();
/*     */     }
/* 203 */     else if (isHalfAscent(anchor)) {
/* 204 */       yAdj = halfAscent;
/*     */     }
/* 206 */     else if (isHalfHeight(anchor)) {
/* 207 */       yAdj = -descent - leading + (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 209 */     else if (isBaseline(anchor)) {
/* 210 */       yAdj = 0.0F;
/*     */     }
/* 212 */     else if (isBottom(anchor)) {
/* 213 */       yAdj = -descent - leading;
/*     */     } 
/* 215 */     if (textBounds != null) {
/* 216 */       textBounds.setRect(bounds);
/*     */     }
/* 218 */     result[0] = xAdj;
/* 219 */     result[1] = yAdj;
/* 220 */     return result;
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
/*     */   private static float[] deriveRotationAnchorOffsets(Graphics2D g2, AttributedString text, TextAnchor anchor) {
/* 237 */     float[] result = new float[2];
/*     */ 
/*     */     
/* 240 */     TextLayout layout = new TextLayout(text.getIterator(), g2.getFontRenderContext());
/* 241 */     Rectangle2D bounds = layout.getBounds();
/* 242 */     float ascent = layout.getAscent();
/* 243 */     float halfAscent = ascent / 2.0F;
/* 244 */     float descent = layout.getDescent();
/* 245 */     float leading = layout.getLeading();
/* 246 */     float xAdj = 0.0F;
/* 247 */     float yAdj = 0.0F;
/*     */     
/* 249 */     if (isHorizontalLeft(anchor)) {
/* 250 */       xAdj = 0.0F;
/*     */     }
/* 252 */     else if (isHorizontalCenter(anchor)) {
/* 253 */       xAdj = (float)bounds.getWidth() / 2.0F;
/*     */     }
/* 255 */     else if (isHorizontalRight(anchor)) {
/* 256 */       xAdj = (float)bounds.getWidth();
/*     */     } 
/*     */     
/* 259 */     if (isTop(anchor)) {
/* 260 */       yAdj = descent + leading - (float)bounds.getHeight();
/*     */     }
/* 262 */     else if (isHalfHeight(anchor)) {
/* 263 */       yAdj = descent + leading - (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 265 */     else if (isHalfAscent(anchor)) {
/* 266 */       yAdj = -halfAscent;
/*     */     }
/* 268 */     else if (isBaseline(anchor)) {
/* 269 */       yAdj = 0.0F;
/*     */     }
/* 271 */     else if (isBottom(anchor)) {
/* 272 */       yAdj = descent + leading;
/*     */     } 
/* 274 */     result[0] = xAdj;
/* 275 */     result[1] = yAdj;
/* 276 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isTop(TextAnchor anchor) {
/* 281 */     return (anchor.equals(TextAnchor.TOP_LEFT) || anchor
/* 282 */       .equals(TextAnchor.TOP_CENTER) || anchor
/* 283 */       .equals(TextAnchor.TOP_RIGHT));
/*     */   }
/*     */   
/*     */   private static boolean isBaseline(TextAnchor anchor) {
/* 287 */     return (anchor.equals(TextAnchor.BASELINE_LEFT) || anchor
/* 288 */       .equals(TextAnchor.BASELINE_CENTER) || anchor
/* 289 */       .equals(TextAnchor.BASELINE_RIGHT));
/*     */   }
/*     */   
/*     */   private static boolean isHalfAscent(TextAnchor anchor) {
/* 293 */     return (anchor.equals(TextAnchor.HALF_ASCENT_LEFT) || anchor
/* 294 */       .equals(TextAnchor.HALF_ASCENT_CENTER) || anchor
/* 295 */       .equals(TextAnchor.HALF_ASCENT_RIGHT));
/*     */   }
/*     */   
/*     */   private static boolean isHalfHeight(TextAnchor anchor) {
/* 299 */     return (anchor.equals(TextAnchor.CENTER_LEFT) || anchor
/* 300 */       .equals(TextAnchor.CENTER) || anchor
/* 301 */       .equals(TextAnchor.CENTER_RIGHT));
/*     */   }
/*     */   
/*     */   private static boolean isBottom(TextAnchor anchor) {
/* 305 */     return (anchor.equals(TextAnchor.BOTTOM_LEFT) || anchor
/* 306 */       .equals(TextAnchor.BOTTOM_CENTER) || anchor
/* 307 */       .equals(TextAnchor.BOTTOM_RIGHT));
/*     */   }
/*     */   
/*     */   private static boolean isHorizontalLeft(TextAnchor anchor) {
/* 311 */     return (anchor.equals(TextAnchor.TOP_LEFT) || anchor
/* 312 */       .equals(TextAnchor.CENTER_LEFT) || anchor
/* 313 */       .equals(TextAnchor.HALF_ASCENT_LEFT) || anchor
/* 314 */       .equals(TextAnchor.BASELINE_LEFT) || anchor
/* 315 */       .equals(TextAnchor.BOTTOM_LEFT));
/*     */   }
/*     */   
/*     */   private static boolean isHorizontalCenter(TextAnchor anchor) {
/* 319 */     return (anchor.equals(TextAnchor.TOP_CENTER) || anchor
/* 320 */       .equals(TextAnchor.CENTER) || anchor
/* 321 */       .equals(TextAnchor.HALF_ASCENT_CENTER) || anchor
/* 322 */       .equals(TextAnchor.BASELINE_CENTER) || anchor
/* 323 */       .equals(TextAnchor.BOTTOM_CENTER));
/*     */   }
/*     */   
/*     */   private static boolean isHorizontalRight(TextAnchor anchor) {
/* 327 */     return (anchor.equals(TextAnchor.TOP_RIGHT) || anchor
/* 328 */       .equals(TextAnchor.CENTER_RIGHT) || anchor
/* 329 */       .equals(TextAnchor.HALF_ASCENT_RIGHT) || anchor
/* 330 */       .equals(TextAnchor.BASELINE_RIGHT) || anchor
/* 331 */       .equals(TextAnchor.BOTTOM_RIGHT));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/AttrStringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
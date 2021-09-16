/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineMetrics;
/*     */ import java.awt.geom.Rectangle2D;
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
/*     */ public class TextUtils
/*     */ {
/*     */   public static Rectangle2D drawAlignedString(String text, Graphics2D g2, float x, float y, TextAnchor anchor) {
/*  74 */     Rectangle2D textBounds = new Rectangle2D.Double();
/*  75 */     float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor, textBounds);
/*     */ 
/*     */     
/*  78 */     textBounds.setRect((x + adjust[0]), (y + adjust[1] + adjust[2]), textBounds
/*  79 */         .getWidth(), textBounds.getHeight());
/*  80 */     g2.drawString(text, x + adjust[0], y + adjust[1]);
/*  81 */     return textBounds;
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
/*     */   public static Rectangle2D calcAlignedStringBounds(String text, Graphics2D g2, float x, float y, TextAnchor anchor) {
/* 101 */     Rectangle2D textBounds = new Rectangle2D.Double();
/* 102 */     float[] adjust = deriveTextBoundsAnchorOffsets(g2, text, anchor, textBounds);
/*     */ 
/*     */     
/* 105 */     textBounds.setRect((x + adjust[0]), (y + adjust[1] + adjust[2]), textBounds
/* 106 */         .getWidth(), textBounds.getHeight());
/* 107 */     return textBounds;
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
/*     */   private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2, String text, TextAnchor anchor) {
/* 126 */     float[] result = new float[2];
/* 127 */     FontRenderContext frc = g2.getFontRenderContext();
/* 128 */     Font f = g2.getFont();
/* 129 */     FontMetrics fm = g2.getFontMetrics(f);
/* 130 */     Rectangle2D bounds = getTextBounds(text, fm);
/* 131 */     LineMetrics metrics = f.getLineMetrics(text, frc);
/* 132 */     float ascent = metrics.getAscent();
/* 133 */     float halfAscent = ascent / 2.0F;
/* 134 */     float descent = metrics.getDescent();
/* 135 */     float leading = metrics.getLeading();
/* 136 */     float xAdj = 0.0F;
/* 137 */     float yAdj = 0.0F;
/*     */     
/* 139 */     if (anchor.isHorizontalCenter()) {
/* 140 */       xAdj = (float)-bounds.getWidth() / 2.0F;
/*     */     }
/* 142 */     else if (anchor.isRight()) {
/* 143 */       xAdj = (float)-bounds.getWidth();
/*     */     } 
/*     */     
/* 146 */     if (anchor.isTop()) {
/* 147 */       yAdj = -descent - leading + (float)bounds.getHeight();
/*     */     }
/* 149 */     else if (anchor.isHalfAscent()) {
/* 150 */       yAdj = halfAscent;
/*     */     }
/* 152 */     else if (anchor.isVerticalCenter()) {
/* 153 */       yAdj = -descent - leading + (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 155 */     else if (anchor.isBaseline()) {
/* 156 */       yAdj = 0.0F;
/*     */     }
/* 158 */     else if (anchor.isBottom()) {
/* 159 */       yAdj = -metrics.getDescent() - metrics.getLeading();
/*     */     } 
/* 161 */     result[0] = xAdj;
/* 162 */     result[1] = yAdj;
/* 163 */     return result;
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
/*     */   private static float[] deriveTextBoundsAnchorOffsets(Graphics2D g2, String text, TextAnchor anchor, Rectangle2D textBounds) {
/* 186 */     float[] result = new float[3];
/* 187 */     FontRenderContext frc = g2.getFontRenderContext();
/* 188 */     Font f = g2.getFont();
/* 189 */     FontMetrics fm = g2.getFontMetrics(f);
/* 190 */     Rectangle2D bounds = getTextBounds(text, fm);
/* 191 */     LineMetrics metrics = f.getLineMetrics(text, frc);
/* 192 */     float ascent = metrics.getAscent();
/* 193 */     result[2] = -ascent;
/* 194 */     float halfAscent = ascent / 2.0F;
/* 195 */     float descent = metrics.getDescent();
/* 196 */     float leading = metrics.getLeading();
/* 197 */     float xAdj = 0.0F;
/* 198 */     float yAdj = 0.0F;
/*     */     
/* 200 */     if (anchor.isHorizontalCenter()) {
/* 201 */       xAdj = (float)-bounds.getWidth() / 2.0F;
/*     */     }
/* 203 */     else if (anchor.isRight()) {
/* 204 */       xAdj = (float)-bounds.getWidth();
/*     */     } 
/*     */     
/* 207 */     if (anchor.isTop()) {
/* 208 */       yAdj = -descent - leading + (float)bounds.getHeight();
/*     */     }
/* 210 */     else if (anchor.isHalfAscent()) {
/* 211 */       yAdj = halfAscent;
/*     */     }
/* 213 */     else if (anchor.isHorizontalCenter()) {
/* 214 */       yAdj = -descent - leading + (float)(bounds.getHeight() / 2.0D);
/*     */     }
/* 216 */     else if (anchor.isBaseline()) {
/* 217 */       yAdj = 0.0F;
/*     */     }
/* 219 */     else if (anchor.isBottom()) {
/* 220 */       yAdj = -metrics.getDescent() - metrics.getLeading();
/*     */     } 
/* 222 */     if (textBounds != null) {
/* 223 */       textBounds.setRect(bounds);
/*     */     }
/* 225 */     result[0] = xAdj;
/* 226 */     result[1] = yAdj;
/* 227 */     return result;
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
/*     */   public static Rectangle2D getTextBounds(String text, FontMetrics fm) {
/* 241 */     return getTextBounds(text, 0.0D, 0.0D, fm);
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
/*     */   public static Rectangle2D getTextBounds(String text, double x, double y, FontMetrics fm) {
/* 257 */     ParamChecks.nullNotPermitted(text, "text");
/* 258 */     ParamChecks.nullNotPermitted(fm, "fm");
/* 259 */     double width = fm.stringWidth(text);
/* 260 */     double height = fm.getHeight();
/* 261 */     return new Rectangle2D.Double(x, y - fm.getAscent(), width, height);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/TextUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.title;
/*     */ 
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import org.jfree.chart.block.LengthConstraintType;
/*     */ import org.jfree.chart.block.RectangleConstraint;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.text.TextUtilities;
/*     */ import org.jfree.ui.Size2D;
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
/*     */ public class ShortTextTitle
/*     */   extends TextTitle
/*     */ {
/*     */   public ShortTextTitle(String text) {
/*  71 */     setText(text);
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
/*     */   public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
/*  87 */     RectangleConstraint cc = toContentConstraint(constraint);
/*  88 */     LengthConstraintType w = cc.getWidthConstraintType();
/*  89 */     LengthConstraintType h = cc.getHeightConstraintType();
/*  90 */     Size2D contentSize = null;
/*  91 */     if (w == LengthConstraintType.NONE) {
/*  92 */       if (h == LengthConstraintType.NONE) {
/*  93 */         contentSize = arrangeNN(g2);
/*     */       } else {
/*  95 */         if (h == LengthConstraintType.RANGE) {
/*  96 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/*  98 */         if (h == LengthConstraintType.FIXED) {
/*  99 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/*     */       } 
/* 102 */     } else if (w == LengthConstraintType.RANGE) {
/* 103 */       if (h == LengthConstraintType.NONE) {
/* 104 */         contentSize = arrangeRN(g2, cc.getWidthRange());
/*     */       }
/* 106 */       else if (h == LengthConstraintType.RANGE) {
/* 107 */         contentSize = arrangeRR(g2, cc.getWidthRange(), cc
/* 108 */             .getHeightRange());
/*     */       }
/* 110 */       else if (h == LengthConstraintType.FIXED) {
/* 111 */         throw new RuntimeException("Not yet implemented.");
/*     */       }
/*     */     
/* 114 */     } else if (w == LengthConstraintType.FIXED) {
/* 115 */       if (h == LengthConstraintType.NONE) {
/* 116 */         contentSize = arrangeFN(g2, cc.getWidth());
/*     */       } else {
/* 118 */         if (h == LengthConstraintType.RANGE) {
/* 119 */           throw new RuntimeException("Not yet implemented.");
/*     */         }
/* 121 */         if (h == LengthConstraintType.FIXED)
/* 122 */           throw new RuntimeException("Not yet implemented."); 
/*     */       } 
/*     */     } 
/* 125 */     assert contentSize != null;
/* 126 */     if (contentSize.width <= 0.0D || contentSize.height <= 0.0D) {
/* 127 */       return new Size2D(0.0D, 0.0D);
/*     */     }
/*     */     
/* 130 */     return new Size2D(calculateTotalWidth(contentSize.getWidth()), 
/* 131 */         calculateTotalHeight(contentSize.getHeight()));
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
/*     */   protected Size2D arrangeNN(Graphics2D g2) {
/* 145 */     Range max = new Range(0.0D, 3.4028234663852886E38D);
/* 146 */     return arrangeRR(g2, max, max);
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
/*     */   protected Size2D arrangeRN(Graphics2D g2, Range widthRange) {
/* 160 */     Size2D s = arrangeNN(g2);
/* 161 */     if (widthRange.contains(s.getWidth())) {
/* 162 */       return s;
/*     */     }
/* 164 */     double ww = widthRange.constrain(s.getWidth());
/* 165 */     return arrangeFN(g2, ww);
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
/*     */   protected Size2D arrangeFN(Graphics2D g2, double w) {
/* 181 */     g2.setFont(getFont());
/* 182 */     FontMetrics fm = g2.getFontMetrics(getFont());
/* 183 */     Rectangle2D bounds = TextUtilities.getTextBounds(getText(), g2, fm);
/* 184 */     if (bounds.getWidth() <= w) {
/* 185 */       return new Size2D(w, bounds.getHeight());
/*     */     }
/*     */     
/* 188 */     return new Size2D(0.0D, 0.0D);
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
/*     */   protected Size2D arrangeRR(Graphics2D g2, Range widthRange, Range heightRange) {
/* 205 */     g2.setFont(getFont());
/* 206 */     FontMetrics fm = g2.getFontMetrics(getFont());
/* 207 */     Rectangle2D bounds = TextUtilities.getTextBounds(getText(), g2, fm);
/* 208 */     if (bounds.getWidth() <= widthRange.getUpperBound() && bounds
/* 209 */       .getHeight() <= heightRange.getUpperBound()) {
/* 210 */       return new Size2D(bounds.getWidth(), bounds.getHeight());
/*     */     }
/*     */     
/* 213 */     return new Size2D(0.0D, 0.0D);
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
/*     */   public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
/* 228 */     if (area.isEmpty()) {
/* 229 */       return null;
/*     */     }
/* 231 */     area = trimMargin(area);
/* 232 */     drawBorder(g2, area);
/* 233 */     area = trimBorder(area);
/* 234 */     area = trimPadding(area);
/* 235 */     g2.setFont(getFont());
/* 236 */     g2.setPaint(getPaint());
/* 237 */     TextUtilities.drawAlignedString(getText(), g2, (float)area.getMinX(), 
/* 238 */         (float)area.getMinY(), TextAnchor.TOP_LEFT);
/* 239 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/title/ShortTextTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
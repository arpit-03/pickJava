/*     */ package org.jfree.text;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextLine
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7100085690160465444L;
/*     */   private List fragments;
/*     */   
/*     */   public TextLine() {
/*  81 */     this.fragments = new ArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLine(String text) {
/*  90 */     this(text, TextFragment.DEFAULT_FONT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLine(String text, Font font) {
/* 100 */     this.fragments = new ArrayList();
/* 101 */     TextFragment fragment = new TextFragment(text, font);
/* 102 */     this.fragments.add(fragment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLine(String text, Font font, Paint paint) {
/* 113 */     if (text == null) {
/* 114 */       throw new IllegalArgumentException("Null 'text' argument.");
/*     */     }
/* 116 */     if (font == null) {
/* 117 */       throw new IllegalArgumentException("Null 'font' argument.");
/*     */     }
/* 119 */     if (paint == null) {
/* 120 */       throw new IllegalArgumentException("Null 'paint' argument.");
/*     */     }
/* 122 */     this.fragments = new ArrayList();
/* 123 */     TextFragment fragment = new TextFragment(text, font, paint);
/* 124 */     this.fragments.add(fragment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFragment(TextFragment fragment) {
/* 133 */     this.fragments.add(fragment);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeFragment(TextFragment fragment) {
/* 142 */     this.fragments.remove(fragment);
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
/*     */   public void draw(Graphics2D g2, float anchorX, float anchorY, TextAnchor anchor, float rotateX, float rotateY, double angle) {
/* 160 */     Size2D dim = calculateDimensions(g2);
/* 161 */     float xAdj = 0.0F;
/* 162 */     if (anchor.isHorizontalCenter()) {
/* 163 */       xAdj = (float)-dim.getWidth() / 2.0F;
/*     */     }
/* 165 */     else if (anchor.isRight()) {
/* 166 */       xAdj = (float)-dim.getWidth();
/*     */     } 
/* 168 */     float x = anchorX + xAdj;
/* 169 */     float yOffset = calculateBaselineOffset(g2, anchor);
/* 170 */     Iterator<TextFragment> iterator = this.fragments.iterator();
/* 171 */     while (iterator.hasNext()) {
/* 172 */       TextFragment fragment = iterator.next();
/* 173 */       Size2D d = fragment.calculateDimensions(g2);
/* 174 */       fragment.draw(g2, x, anchorY + yOffset, TextAnchor.BASELINE_LEFT, rotateX, rotateY, angle);
/*     */       
/* 176 */       x += (float)d.getWidth();
/*     */     } 
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
/*     */   public Size2D calculateDimensions(Graphics2D g2) {
/* 189 */     double width = 0.0D;
/* 190 */     double height = 0.0D;
/* 191 */     Iterator<TextFragment> iterator = this.fragments.iterator();
/* 192 */     while (iterator.hasNext()) {
/* 193 */       TextFragment fragment = iterator.next();
/* 194 */       Size2D dimension = fragment.calculateDimensions(g2);
/* 195 */       width += dimension.getWidth();
/* 196 */       height = Math.max(height, dimension.getHeight());
/*     */     } 
/* 198 */     return new Size2D(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFragment getFirstTextFragment() {
/* 207 */     TextFragment result = null;
/* 208 */     if (this.fragments.size() > 0) {
/* 209 */       result = this.fragments.get(0);
/*     */     }
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFragment getLastTextFragment() {
/* 220 */     TextFragment result = null;
/* 221 */     if (this.fragments.size() > 0) {
/* 222 */       result = this.fragments.get(this.fragments.size() - 1);
/*     */     }
/*     */     
/* 225 */     return result;
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
/*     */   private float calculateBaselineOffset(Graphics2D g2, TextAnchor anchor) {
/* 239 */     float result = 0.0F;
/* 240 */     Iterator<TextFragment> iterator = this.fragments.iterator();
/* 241 */     while (iterator.hasNext()) {
/* 242 */       TextFragment fragment = iterator.next();
/* 243 */       result = Math.max(result, fragment
/* 244 */           .calculateBaselineOffset(g2, anchor));
/*     */     } 
/* 246 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 257 */     if (obj == null) {
/* 258 */       return false;
/*     */     }
/* 260 */     if (obj == this) {
/* 261 */       return true;
/*     */     }
/* 263 */     if (obj instanceof TextLine) {
/* 264 */       TextLine line = (TextLine)obj;
/* 265 */       return this.fragments.equals(line.fragments);
/*     */     } 
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 276 */     return (this.fragments != null) ? this.fragments.hashCode() : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/text/TextLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
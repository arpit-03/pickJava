/*     */ package org.jfree.text;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ import org.jfree.util.ShapeUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextBlock
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4333175719424385526L;
/*  90 */   private List lines = new ArrayList();
/*  91 */   private HorizontalAlignment lineAlignment = HorizontalAlignment.CENTER;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HorizontalAlignment getLineAlignment() {
/* 100 */     return this.lineAlignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLineAlignment(HorizontalAlignment alignment) {
/* 109 */     if (alignment == null) {
/* 110 */       throw new IllegalArgumentException("Null 'alignment' argument.");
/*     */     }
/* 112 */     this.lineAlignment = alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLine(String text, Font font, Paint paint) {
/* 123 */     addLine(new TextLine(text, font, paint));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLine(TextLine line) {
/* 132 */     this.lines.add(line);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLine getLastLine() {
/* 141 */     TextLine last = null;
/* 142 */     int index = this.lines.size() - 1;
/* 143 */     if (index >= 0) {
/* 144 */       last = this.lines.get(index);
/*     */     }
/* 146 */     return last;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getLines() {
/* 155 */     return Collections.unmodifiableList(this.lines);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Size2D calculateDimensions(Graphics2D g2) {
/* 166 */     double width = 0.0D;
/* 167 */     double height = 0.0D;
/* 168 */     Iterator<TextLine> iterator = this.lines.iterator();
/* 169 */     while (iterator.hasNext()) {
/* 170 */       TextLine line = iterator.next();
/* 171 */       Size2D dimension = line.calculateDimensions(g2);
/* 172 */       width = Math.max(width, dimension.getWidth());
/* 173 */       height += dimension.getHeight();
/*     */     } 
/* 175 */     return new Size2D(width, height);
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
/*     */   public Shape calculateBounds(Graphics2D g2, float anchorX, float anchorY, TextBlockAnchor anchor, float rotateX, float rotateY, double angle) {
/* 197 */     Size2D d = calculateDimensions(g2);
/* 198 */     float[] offsets = calculateOffsets(anchor, d
/* 199 */         .getWidth(), d.getHeight());
/*     */ 
/*     */ 
/*     */     
/* 203 */     Rectangle2D bounds = new Rectangle2D.Double((anchorX + offsets[0]), (anchorY + offsets[1]), d.getWidth(), d.getHeight());
/*     */     
/* 205 */     Shape rotatedBounds = ShapeUtilities.rotateShape(bounds, angle, rotateX, rotateY);
/*     */ 
/*     */     
/* 208 */     return rotatedBounds;
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
/*     */   public void draw(Graphics2D g2, float x, float y, TextBlockAnchor anchor) {
/* 222 */     draw(g2, x, y, anchor, 0.0F, 0.0F, 0.0D);
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
/*     */   public void draw(Graphics2D g2, float anchorX, float anchorY, TextBlockAnchor anchor, float rotateX, float rotateY, double angle) {
/* 244 */     Size2D d = calculateDimensions(g2);
/* 245 */     float[] offsets = calculateOffsets(anchor, d.getWidth(), d
/* 246 */         .getHeight());
/* 247 */     Iterator<TextLine> iterator = this.lines.iterator();
/* 248 */     float yCursor = 0.0F;
/* 249 */     while (iterator.hasNext()) {
/* 250 */       TextLine line = iterator.next();
/* 251 */       Size2D dimension = line.calculateDimensions(g2);
/* 252 */       float lineOffset = 0.0F;
/* 253 */       if (this.lineAlignment == HorizontalAlignment.CENTER) {
/* 254 */         lineOffset = (float)(d.getWidth() - dimension.getWidth()) / 2.0F;
/*     */       
/*     */       }
/* 257 */       else if (this.lineAlignment == HorizontalAlignment.RIGHT) {
/* 258 */         lineOffset = (float)(d.getWidth() - dimension.getWidth());
/*     */       } 
/* 260 */       line.draw(g2, anchorX + offsets[0] + lineOffset, anchorY + offsets[1] + yCursor, TextAnchor.TOP_LEFT, rotateX, rotateY, angle);
/*     */ 
/*     */ 
/*     */       
/* 264 */       yCursor += (float)dimension.getHeight();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float[] calculateOffsets(TextBlockAnchor anchor, double width, double height) {
/* 282 */     float[] result = new float[2];
/* 283 */     float xAdj = 0.0F;
/* 284 */     float yAdj = 0.0F;
/*     */     
/* 286 */     if (anchor == TextBlockAnchor.TOP_CENTER || anchor == TextBlockAnchor.CENTER || anchor == TextBlockAnchor.BOTTOM_CENTER) {
/*     */ 
/*     */ 
/*     */       
/* 290 */       xAdj = (float)-width / 2.0F;
/*     */     
/*     */     }
/* 293 */     else if (anchor == TextBlockAnchor.TOP_RIGHT || anchor == TextBlockAnchor.CENTER_RIGHT || anchor == TextBlockAnchor.BOTTOM_RIGHT) {
/*     */ 
/*     */ 
/*     */       
/* 297 */       xAdj = (float)-width;
/*     */     } 
/*     */ 
/*     */     
/* 301 */     if (anchor == TextBlockAnchor.TOP_LEFT || anchor == TextBlockAnchor.TOP_CENTER || anchor == TextBlockAnchor.TOP_RIGHT) {
/*     */ 
/*     */ 
/*     */       
/* 305 */       yAdj = 0.0F;
/*     */     
/*     */     }
/* 308 */     else if (anchor == TextBlockAnchor.CENTER_LEFT || anchor == TextBlockAnchor.CENTER || anchor == TextBlockAnchor.CENTER_RIGHT) {
/*     */ 
/*     */ 
/*     */       
/* 312 */       yAdj = (float)-height / 2.0F;
/*     */     
/*     */     }
/* 315 */     else if (anchor == TextBlockAnchor.BOTTOM_LEFT || anchor == TextBlockAnchor.BOTTOM_CENTER || anchor == TextBlockAnchor.BOTTOM_RIGHT) {
/*     */ 
/*     */ 
/*     */       
/* 319 */       yAdj = (float)-height;
/*     */     } 
/*     */     
/* 322 */     result[0] = xAdj;
/* 323 */     result[1] = yAdj;
/* 324 */     return result;
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
/* 335 */     if (obj == this) {
/* 336 */       return true;
/*     */     }
/* 338 */     if (obj instanceof TextBlock) {
/* 339 */       TextBlock block = (TextBlock)obj;
/* 340 */       return this.lines.equals(block.lines);
/*     */     } 
/* 342 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 351 */     return (this.lines != null) ? this.lines.hashCode() : 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/text/TextBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
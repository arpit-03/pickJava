/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.ui.VerticalAlignment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColumnArrangement
/*     */   implements Arrangement, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -5315388482898581555L;
/*     */   private HorizontalAlignment horizontalAlignment;
/*     */   private VerticalAlignment verticalAlignment;
/*     */   private double horizontalGap;
/*     */   private double verticalGap;
/*     */   
/*     */   public ColumnArrangement() {}
/*     */   
/*     */   public ColumnArrangement(HorizontalAlignment hAlign, VerticalAlignment vAlign, double hGap, double vGap) {
/*  91 */     this.horizontalAlignment = hAlign;
/*  92 */     this.verticalAlignment = vAlign;
/*  93 */     this.horizontalGap = hGap;
/*  94 */     this.verticalGap = vGap;
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
/*     */   public void add(Block block, Object key) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 127 */     LengthConstraintType w = constraint.getWidthConstraintType();
/* 128 */     LengthConstraintType h = constraint.getHeightConstraintType();
/* 129 */     if (w == LengthConstraintType.NONE) {
/* 130 */       if (h == LengthConstraintType.NONE) {
/* 131 */         return arrangeNN(container, g2);
/*     */       }
/* 133 */       if (h == LengthConstraintType.FIXED) {
/* 134 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 136 */       if (h == LengthConstraintType.RANGE) {
/* 137 */         throw new RuntimeException("Not implemented.");
/*     */       }
/*     */     }
/* 140 */     else if (w == LengthConstraintType.FIXED) {
/* 141 */       if (h == LengthConstraintType.NONE) {
/* 142 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 144 */       if (h == LengthConstraintType.FIXED) {
/* 145 */         return arrangeFF(container, g2, constraint);
/*     */       }
/* 147 */       if (h == LengthConstraintType.RANGE) {
/* 148 */         throw new RuntimeException("Not implemented.");
/*     */       }
/*     */     }
/* 151 */     else if (w == LengthConstraintType.RANGE) {
/* 152 */       if (h == LengthConstraintType.NONE) {
/* 153 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 155 */       if (h == LengthConstraintType.FIXED) {
/* 156 */         return arrangeRF(container, g2, constraint);
/*     */       }
/* 158 */       if (h == LengthConstraintType.RANGE) {
/* 159 */         return arrangeRR(container, g2, constraint);
/*     */       }
/*     */     } 
/* 162 */     return new Size2D();
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
/*     */   protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 181 */     return arrangeNF(container, g2, constraint);
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
/*     */   protected Size2D arrangeNF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 199 */     List<Block> blocks = container.getBlocks();
/*     */     
/* 201 */     double height = constraint.getHeight();
/* 202 */     if (height <= 0.0D) {
/* 203 */       height = Double.POSITIVE_INFINITY;
/*     */     }
/*     */     
/* 206 */     double x = 0.0D;
/* 207 */     double y = 0.0D;
/* 208 */     double maxWidth = 0.0D;
/* 209 */     List<Block> itemsInColumn = new ArrayList();
/* 210 */     for (int i = 0; i < blocks.size(); i++) {
/* 211 */       Block block = blocks.get(i);
/* 212 */       Size2D size = block.arrange(g2, RectangleConstraint.NONE);
/* 213 */       if (y + size.height <= height) {
/* 214 */         itemsInColumn.add(block);
/* 215 */         block.setBounds(new Rectangle2D.Double(x, y, size.width, size.height));
/*     */ 
/*     */         
/* 218 */         y = y + size.height + this.verticalGap;
/* 219 */         maxWidth = Math.max(maxWidth, size.width);
/*     */       
/*     */       }
/* 222 */       else if (itemsInColumn.isEmpty()) {
/*     */         
/* 224 */         block.setBounds(new Rectangle2D.Double(x, y, size.width, 
/*     */               
/* 226 */               Math.min(size.height, height - y)));
/*     */ 
/*     */         
/* 229 */         y = 0.0D;
/* 230 */         x = x + size.width + this.horizontalGap;
/*     */       }
/*     */       else {
/*     */         
/* 234 */         itemsInColumn.clear();
/* 235 */         x = x + maxWidth + this.horizontalGap;
/* 236 */         y = 0.0D;
/* 237 */         maxWidth = size.width;
/* 238 */         block.setBounds(new Rectangle2D.Double(x, y, size.width, 
/*     */               
/* 240 */               Math.min(size.height, height)));
/*     */ 
/*     */         
/* 243 */         y = size.height + this.verticalGap;
/* 244 */         itemsInColumn.add(block);
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     return new Size2D(x + maxWidth, constraint.getHeight());
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
/*     */   protected Size2D arrangeRR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 266 */     Size2D s1 = arrangeNN(container, g2);
/* 267 */     if (constraint.getHeightRange().contains(s1.height)) {
/* 268 */       return s1;
/*     */     }
/*     */     
/* 271 */     RectangleConstraint c = constraint.toFixedHeight(constraint
/* 272 */         .getHeightRange().getUpperBound());
/*     */     
/* 274 */     return arrangeRF(container, g2, c);
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
/*     */   protected Size2D arrangeRF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 291 */     Size2D s = arrangeNF(container, g2, constraint);
/* 292 */     if (constraint.getWidthRange().contains(s.width)) {
/* 293 */       return s;
/*     */     }
/*     */     
/* 296 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 297 */         .getWidthRange().constrain(s.getWidth()));
/*     */     
/* 299 */     return arrangeFF(container, g2, c);
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
/*     */   protected Size2D arrangeNN(BlockContainer container, Graphics2D g2) {
/* 313 */     double y = 0.0D;
/* 314 */     double height = 0.0D;
/* 315 */     double maxWidth = 0.0D;
/* 316 */     List<Block> blocks = container.getBlocks();
/* 317 */     int blockCount = blocks.size();
/* 318 */     if (blockCount > 0) {
/* 319 */       Size2D[] sizes = new Size2D[blocks.size()]; int i;
/* 320 */       for (i = 0; i < blocks.size(); i++) {
/* 321 */         Block block = blocks.get(i);
/* 322 */         sizes[i] = block.arrange(g2, RectangleConstraint.NONE);
/* 323 */         height += sizes[i].getHeight();
/* 324 */         maxWidth = Math.max((sizes[i]).width, maxWidth);
/* 325 */         block.setBounds(new Rectangle2D.Double(0.0D, y, (sizes[i]).width, (sizes[i]).height));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 330 */         y = y + (sizes[i]).height + this.verticalGap;
/*     */       } 
/* 332 */       if (blockCount > 1) {
/* 333 */         height += this.verticalGap * (blockCount - 1);
/*     */       }
/* 335 */       if (this.horizontalAlignment != HorizontalAlignment.LEFT) {
/* 336 */         for (i = 0; i < blocks.size(); i++) {
/*     */           
/* 338 */           if (this.horizontalAlignment != HorizontalAlignment.CENTER)
/*     */           {
/*     */ 
/*     */             
/* 342 */             if (this.horizontalAlignment == HorizontalAlignment.RIGHT);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 349 */     return new Size2D(maxWidth, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 369 */     if (obj == this) {
/* 370 */       return true;
/*     */     }
/* 372 */     if (!(obj instanceof ColumnArrangement)) {
/* 373 */       return false;
/*     */     }
/* 375 */     ColumnArrangement that = (ColumnArrangement)obj;
/* 376 */     if (this.horizontalAlignment != that.horizontalAlignment) {
/* 377 */       return false;
/*     */     }
/* 379 */     if (this.verticalAlignment != that.verticalAlignment) {
/* 380 */       return false;
/*     */     }
/* 382 */     if (this.horizontalGap != that.horizontalGap) {
/* 383 */       return false;
/*     */     }
/* 385 */     if (this.verticalGap != that.verticalGap) {
/* 386 */       return false;
/*     */     }
/* 388 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/ColumnArrangement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
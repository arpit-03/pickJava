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
/*     */ public class FlowArrangement
/*     */   implements Arrangement, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 4543632485478613800L;
/*     */   private HorizontalAlignment horizontalAlignment;
/*     */   private VerticalAlignment verticalAlignment;
/*     */   private double horizontalGap;
/*     */   private double verticalGap;
/*     */   
/*     */   public FlowArrangement() {
/*  79 */     this(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 2.0D, 2.0D);
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
/*     */   public FlowArrangement(HorizontalAlignment hAlign, VerticalAlignment vAlign, double hGap, double vGap) {
/*  92 */     this.horizontalAlignment = hAlign;
/*  93 */     this.verticalAlignment = vAlign;
/*  94 */     this.horizontalGap = hGap;
/*  95 */     this.verticalGap = vGap;
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
/* 128 */     LengthConstraintType w = constraint.getWidthConstraintType();
/* 129 */     LengthConstraintType h = constraint.getHeightConstraintType();
/* 130 */     if (w == LengthConstraintType.NONE) {
/* 131 */       if (h == LengthConstraintType.NONE) {
/* 132 */         return arrangeNN(container, g2);
/*     */       }
/* 134 */       if (h == LengthConstraintType.FIXED) {
/* 135 */         return arrangeNF(container, g2, constraint);
/*     */       }
/* 137 */       if (h == LengthConstraintType.RANGE) {
/* 138 */         throw new RuntimeException("Not implemented.");
/*     */       }
/*     */     }
/* 141 */     else if (w == LengthConstraintType.FIXED) {
/* 142 */       if (h == LengthConstraintType.NONE) {
/* 143 */         return arrangeFN(container, g2, constraint);
/*     */       }
/* 145 */       if (h == LengthConstraintType.FIXED) {
/* 146 */         return arrangeFF(container, g2, constraint);
/*     */       }
/* 148 */       if (h == LengthConstraintType.RANGE) {
/* 149 */         return arrangeFR(container, g2, constraint);
/*     */       }
/*     */     }
/* 152 */     else if (w == LengthConstraintType.RANGE) {
/* 153 */       if (h == LengthConstraintType.NONE) {
/* 154 */         return arrangeRN(container, g2, constraint);
/*     */       }
/* 156 */       if (h == LengthConstraintType.FIXED) {
/* 157 */         return arrangeRF(container, g2, constraint);
/*     */       }
/* 159 */       if (h == LengthConstraintType.RANGE) {
/* 160 */         return arrangeRR(container, g2, constraint);
/*     */       }
/*     */     } 
/* 163 */     throw new RuntimeException("Unrecognised constraint type.");
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
/*     */   protected Size2D arrangeFN(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 180 */     List<Block> blocks = container.getBlocks();
/* 181 */     double width = constraint.getWidth();
/*     */     
/* 183 */     double x = 0.0D;
/* 184 */     double y = 0.0D;
/* 185 */     double maxHeight = 0.0D;
/* 186 */     List<Block> itemsInRow = new ArrayList();
/* 187 */     for (int i = 0; i < blocks.size(); i++) {
/* 188 */       Block block = blocks.get(i);
/* 189 */       Size2D size = block.arrange(g2, RectangleConstraint.NONE);
/* 190 */       if (x + size.width <= width) {
/* 191 */         itemsInRow.add(block);
/* 192 */         block.setBounds(new Rectangle2D.Double(x, y, size.width, size.height));
/*     */ 
/*     */         
/* 195 */         x = x + size.width + this.horizontalGap;
/* 196 */         maxHeight = Math.max(maxHeight, size.height);
/*     */       
/*     */       }
/* 199 */       else if (itemsInRow.isEmpty()) {
/*     */         
/* 201 */         block.setBounds(new Rectangle2D.Double(x, y, 
/*     */               
/* 203 */               Math.min(size.width, width - x), size.height));
/*     */ 
/*     */         
/* 206 */         x = 0.0D;
/* 207 */         y = y + size.height + this.verticalGap;
/*     */       }
/*     */       else {
/*     */         
/* 211 */         itemsInRow.clear();
/* 212 */         x = 0.0D;
/* 213 */         y = y + maxHeight + this.verticalGap;
/* 214 */         maxHeight = size.height;
/* 215 */         block.setBounds(new Rectangle2D.Double(x, y, 
/*     */               
/* 217 */               Math.min(size.width, width), size.height));
/*     */ 
/*     */         
/* 220 */         x = size.width + this.horizontalGap;
/* 221 */         itemsInRow.add(block);
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return new Size2D(constraint.getWidth(), y + maxHeight);
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
/*     */   protected Size2D arrangeFR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 241 */     Size2D s = arrangeFN(container, g2, constraint);
/* 242 */     if (constraint.getHeightRange().contains(s.height)) {
/* 243 */       return s;
/*     */     }
/*     */     
/* 246 */     RectangleConstraint c = constraint.toFixedHeight(constraint
/* 247 */         .getHeightRange().constrain(s.getHeight()));
/*     */     
/* 249 */     return arrangeFF(container, g2, c);
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
/*     */   protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 267 */     return arrangeFN(container, g2, constraint);
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
/* 285 */     Size2D s1 = arrangeNN(container, g2);
/* 286 */     if (constraint.getWidthRange().contains(s1.width)) {
/* 287 */       return s1;
/*     */     }
/*     */     
/* 290 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 291 */         .getWidthRange().getUpperBound());
/*     */     
/* 293 */     return arrangeFR(container, g2, c);
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
/* 310 */     Size2D s = arrangeNF(container, g2, constraint);
/* 311 */     if (constraint.getWidthRange().contains(s.width)) {
/* 312 */       return s;
/*     */     }
/*     */     
/* 315 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 316 */         .getWidthRange().constrain(s.getWidth()));
/*     */     
/* 318 */     return arrangeFF(container, g2, c);
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
/*     */   protected Size2D arrangeRN(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 336 */     Size2D s1 = arrangeNN(container, g2);
/* 337 */     if (constraint.getWidthRange().contains(s1.width)) {
/* 338 */       return s1;
/*     */     }
/*     */     
/* 341 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 342 */         .getWidthRange().getUpperBound());
/*     */     
/* 344 */     return arrangeFN(container, g2, c);
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
/* 358 */     double x = 0.0D;
/* 359 */     double width = 0.0D;
/* 360 */     double maxHeight = 0.0D;
/* 361 */     List<Block> blocks = container.getBlocks();
/* 362 */     int blockCount = blocks.size();
/* 363 */     if (blockCount > 0) {
/* 364 */       Size2D[] sizes = new Size2D[blocks.size()]; int i;
/* 365 */       for (i = 0; i < blocks.size(); i++) {
/* 366 */         Block block = blocks.get(i);
/* 367 */         sizes[i] = block.arrange(g2, RectangleConstraint.NONE);
/* 368 */         width += sizes[i].getWidth();
/* 369 */         maxHeight = Math.max((sizes[i]).height, maxHeight);
/* 370 */         block.setBounds(new Rectangle2D.Double(x, 0.0D, (sizes[i]).width, (sizes[i]).height));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 375 */         x = x + (sizes[i]).width + this.horizontalGap;
/*     */       } 
/* 377 */       if (blockCount > 1) {
/* 378 */         width += this.horizontalGap * (blockCount - 1);
/*     */       }
/* 380 */       if (this.verticalAlignment != VerticalAlignment.TOP) {
/* 381 */         for (i = 0; i < blocks.size(); i++) {
/*     */           
/* 383 */           if (this.verticalAlignment != VerticalAlignment.CENTER)
/*     */           {
/*     */             
/* 386 */             if (this.verticalAlignment == VerticalAlignment.BOTTOM);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 393 */     return new Size2D(width, maxHeight);
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
/*     */   protected Size2D arrangeNF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 409 */     return arrangeNN(container, g2);
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
/* 429 */     if (obj == this) {
/* 430 */       return true;
/*     */     }
/* 432 */     if (!(obj instanceof FlowArrangement)) {
/* 433 */       return false;
/*     */     }
/* 435 */     FlowArrangement that = (FlowArrangement)obj;
/* 436 */     if (this.horizontalAlignment != that.horizontalAlignment) {
/* 437 */       return false;
/*     */     }
/* 439 */     if (this.verticalAlignment != that.verticalAlignment) {
/* 440 */       return false;
/*     */     }
/* 442 */     if (this.horizontalGap != that.horizontalGap) {
/* 443 */       return false;
/*     */     }
/* 445 */     if (this.verticalGap != that.verticalGap) {
/* 446 */       return false;
/*     */     }
/* 448 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/FlowArrangement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
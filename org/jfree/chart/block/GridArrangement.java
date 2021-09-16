/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jfree.ui.Size2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GridArrangement
/*     */   implements Arrangement, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2563758090144655938L;
/*     */   private int rows;
/*     */   private int columns;
/*     */   
/*     */   public GridArrangement(int rows, int columns) {
/*  74 */     this.rows = rows;
/*  75 */     this.columns = columns;
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
/*     */   public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 105 */     LengthConstraintType w = constraint.getWidthConstraintType();
/* 106 */     LengthConstraintType h = constraint.getHeightConstraintType();
/* 107 */     if (w == LengthConstraintType.NONE) {
/* 108 */       if (h == LengthConstraintType.NONE) {
/* 109 */         return arrangeNN(container, g2);
/*     */       }
/* 111 */       if (h == LengthConstraintType.FIXED) {
/* 112 */         return arrangeNF(container, g2, constraint);
/*     */       }
/* 114 */       if (h == LengthConstraintType.RANGE)
/*     */       {
/* 116 */         return arrangeNR(container, g2, constraint);
/*     */       }
/*     */     }
/* 119 */     else if (w == LengthConstraintType.FIXED) {
/* 120 */       if (h == LengthConstraintType.NONE)
/*     */       {
/* 122 */         return arrangeFN(container, g2, constraint);
/*     */       }
/* 124 */       if (h == LengthConstraintType.FIXED) {
/* 125 */         return arrangeFF(container, g2, constraint);
/*     */       }
/* 127 */       if (h == LengthConstraintType.RANGE)
/*     */       {
/* 129 */         return arrangeFR(container, g2, constraint);
/*     */       }
/*     */     }
/* 132 */     else if (w == LengthConstraintType.RANGE) {
/*     */       
/* 134 */       if (h == LengthConstraintType.NONE)
/*     */       {
/* 136 */         return arrangeRN(container, g2, constraint);
/*     */       }
/* 138 */       if (h == LengthConstraintType.FIXED)
/*     */       {
/* 140 */         return arrangeRF(container, g2, constraint);
/*     */       }
/* 142 */       if (h == LengthConstraintType.RANGE) {
/* 143 */         return arrangeRR(container, g2, constraint);
/*     */       }
/*     */     } 
/* 146 */     throw new RuntimeException("Should never get to here!");
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
/*     */   protected Size2D arrangeNN(BlockContainer container, Graphics2D g2) {
/* 158 */     double maxW = 0.0D;
/* 159 */     double maxH = 0.0D;
/* 160 */     List blocks = container.getBlocks();
/* 161 */     Iterator<Block> iterator = blocks.iterator();
/* 162 */     while (iterator.hasNext()) {
/* 163 */       Block b = iterator.next();
/* 164 */       if (b != null) {
/* 165 */         Size2D s = b.arrange(g2, RectangleConstraint.NONE);
/* 166 */         maxW = Math.max(maxW, s.width);
/* 167 */         maxH = Math.max(maxH, s.height);
/*     */       } 
/*     */     } 
/* 170 */     double width = this.columns * maxW;
/* 171 */     double height = this.rows * maxH;
/* 172 */     RectangleConstraint c = new RectangleConstraint(width, height);
/* 173 */     return arrangeFF(container, g2, c);
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
/*     */   protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 187 */     double width = constraint.getWidth() / this.columns;
/* 188 */     double height = constraint.getHeight() / this.rows;
/* 189 */     List<Block> blocks = container.getBlocks();
/* 190 */     for (int c = 0; c < this.columns; c++) {
/* 191 */       for (int r = 0; r < this.rows; r++) {
/* 192 */         int index = r * this.columns + c;
/* 193 */         if (index >= blocks.size()) {
/*     */           break;
/*     */         }
/* 196 */         Block b = blocks.get(index);
/* 197 */         if (b != null) {
/* 198 */           b.setBounds(new Rectangle2D.Double(c * width, r * height, width, height));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return new Size2D(this.columns * width, this.rows * height);
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
/*     */   protected Size2D arrangeFR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 218 */     RectangleConstraint c1 = constraint.toUnconstrainedHeight();
/* 219 */     Size2D size1 = arrange(container, g2, c1);
/*     */     
/* 221 */     if (constraint.getHeightRange().contains(size1.getHeight())) {
/* 222 */       return size1;
/*     */     }
/*     */     
/* 225 */     double h = constraint.getHeightRange().constrain(size1.getHeight());
/* 226 */     RectangleConstraint c2 = constraint.toFixedHeight(h);
/* 227 */     return arrange(container, g2, c2);
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
/*     */   protected Size2D arrangeRF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 243 */     RectangleConstraint c1 = constraint.toUnconstrainedWidth();
/* 244 */     Size2D size1 = arrange(container, g2, c1);
/*     */     
/* 246 */     if (constraint.getWidthRange().contains(size1.getWidth())) {
/* 247 */       return size1;
/*     */     }
/*     */     
/* 250 */     double w = constraint.getWidthRange().constrain(size1.getWidth());
/* 251 */     RectangleConstraint c2 = constraint.toFixedWidth(w);
/* 252 */     return arrange(container, g2, c2);
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
/*     */   protected Size2D arrangeRN(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 268 */     RectangleConstraint c1 = constraint.toUnconstrainedWidth();
/* 269 */     Size2D size1 = arrange(container, g2, c1);
/*     */     
/* 271 */     if (constraint.getWidthRange().contains(size1.getWidth())) {
/* 272 */       return size1;
/*     */     }
/*     */     
/* 275 */     double w = constraint.getWidthRange().constrain(size1.getWidth());
/* 276 */     RectangleConstraint c2 = constraint.toFixedWidth(w);
/* 277 */     return arrange(container, g2, c2);
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
/*     */   protected Size2D arrangeNR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 293 */     RectangleConstraint c1 = constraint.toUnconstrainedHeight();
/* 294 */     Size2D size1 = arrange(container, g2, c1);
/*     */     
/* 296 */     if (constraint.getHeightRange().contains(size1.getHeight())) {
/* 297 */       return size1;
/*     */     }
/*     */     
/* 300 */     double h = constraint.getHeightRange().constrain(size1.getHeight());
/* 301 */     RectangleConstraint c2 = constraint.toFixedHeight(h);
/* 302 */     return arrange(container, g2, c2);
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
/*     */   protected Size2D arrangeRR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 318 */     Size2D size1 = arrange(container, g2, RectangleConstraint.NONE);
/*     */     
/* 320 */     if (constraint.getWidthRange().contains(size1.getWidth())) {
/* 321 */       if (constraint.getHeightRange().contains(size1.getHeight())) {
/* 322 */         return size1;
/*     */       }
/*     */ 
/*     */       
/* 326 */       double d = constraint.getHeightRange().constrain(size1
/* 327 */           .getHeight());
/*     */       
/* 329 */       RectangleConstraint rectangleConstraint = new RectangleConstraint(size1.getWidth(), d);
/* 330 */       return arrangeFF(container, g2, rectangleConstraint);
/*     */     } 
/*     */ 
/*     */     
/* 334 */     if (constraint.getHeightRange().contains(size1.getHeight())) {
/*     */       
/* 336 */       double d = constraint.getWidthRange().constrain(size1
/* 337 */           .getWidth());
/*     */       
/* 339 */       RectangleConstraint rectangleConstraint = new RectangleConstraint(d, size1.getHeight());
/* 340 */       return arrangeFF(container, g2, rectangleConstraint);
/*     */     } 
/*     */ 
/*     */     
/* 344 */     double w = constraint.getWidthRange().constrain(size1
/* 345 */         .getWidth());
/* 346 */     double h = constraint.getHeightRange().constrain(size1
/* 347 */         .getHeight());
/* 348 */     RectangleConstraint cc = new RectangleConstraint(w, h);
/* 349 */     return arrangeFF(container, g2, cc);
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
/* 366 */     double width = constraint.getWidth() / this.columns;
/* 367 */     RectangleConstraint bc = constraint.toFixedWidth(width);
/* 368 */     List<Block> blocks = container.getBlocks();
/* 369 */     double maxH = 0.0D;
/* 370 */     for (int r = 0; r < this.rows; r++) {
/* 371 */       for (int c = 0; c < this.columns; c++) {
/* 372 */         int index = r * this.columns + c;
/* 373 */         if (index >= blocks.size()) {
/*     */           break;
/*     */         }
/* 376 */         Block b = blocks.get(index);
/* 377 */         if (b != null) {
/* 378 */           Size2D s = b.arrange(g2, bc);
/* 379 */           maxH = Math.max(maxH, s.getHeight());
/*     */         } 
/*     */       } 
/*     */     } 
/* 383 */     RectangleConstraint cc = constraint.toFixedHeight(maxH * this.rows);
/* 384 */     return arrange(container, g2, cc);
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
/*     */   protected Size2D arrangeNF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 399 */     double height = constraint.getHeight() / this.rows;
/* 400 */     RectangleConstraint bc = constraint.toFixedHeight(height);
/* 401 */     List<Block> blocks = container.getBlocks();
/* 402 */     double maxW = 0.0D;
/* 403 */     for (int r = 0; r < this.rows; r++) {
/* 404 */       for (int c = 0; c < this.columns; c++) {
/* 405 */         int index = r * this.columns + c;
/* 406 */         if (index >= blocks.size()) {
/*     */           break;
/*     */         }
/* 409 */         Block b = blocks.get(index);
/* 410 */         if (b != null) {
/* 411 */           Size2D s = b.arrange(g2, bc);
/* 412 */           maxW = Math.max(maxW, s.getWidth());
/*     */         } 
/*     */       } 
/*     */     } 
/* 416 */     RectangleConstraint cc = constraint.toFixedWidth(maxW * this.columns);
/* 417 */     return arrange(container, g2, cc);
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
/* 437 */     if (obj == this) {
/* 438 */       return true;
/*     */     }
/* 440 */     if (!(obj instanceof GridArrangement)) {
/* 441 */       return false;
/*     */     }
/* 443 */     GridArrangement that = (GridArrangement)obj;
/* 444 */     if (this.columns != that.columns) {
/* 445 */       return false;
/*     */     }
/* 447 */     if (this.rows != that.rows) {
/* 448 */       return false;
/*     */     }
/* 450 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/GridArrangement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
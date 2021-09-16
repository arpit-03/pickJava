/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.Size2D;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BorderArrangement
/*     */   implements Arrangement, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 506071142274883745L;
/*     */   private Block centerBlock;
/*     */   private Block topBlock;
/*     */   private Block bottomBlock;
/*     */   private Block leftBlock;
/*     */   private Block rightBlock;
/*     */   
/*     */   public void add(Block block, Object key) {
/* 101 */     if (!(key instanceof RectangleEdge)) {
/* 102 */       this.centerBlock = block;
/*     */     } else {
/*     */       
/* 105 */       RectangleEdge edge = (RectangleEdge)key;
/* 106 */       if (edge == RectangleEdge.TOP) {
/* 107 */         this.topBlock = block;
/*     */       }
/* 109 */       else if (edge == RectangleEdge.BOTTOM) {
/* 110 */         this.bottomBlock = block;
/*     */       }
/* 112 */       else if (edge == RectangleEdge.LEFT) {
/* 113 */         this.leftBlock = block;
/*     */       }
/* 115 */       else if (edge == RectangleEdge.RIGHT) {
/* 116 */         this.rightBlock = block;
/*     */       } 
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
/*     */   public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 135 */     RectangleConstraint contentConstraint = container.toContentConstraint(constraint);
/* 136 */     Size2D contentSize = null;
/* 137 */     LengthConstraintType w = contentConstraint.getWidthConstraintType();
/* 138 */     LengthConstraintType h = contentConstraint.getHeightConstraintType();
/* 139 */     if (w == LengthConstraintType.NONE) {
/* 140 */       if (h == LengthConstraintType.NONE) {
/* 141 */         contentSize = arrangeNN(container, g2);
/*     */       } else {
/* 143 */         if (h == LengthConstraintType.FIXED) {
/* 144 */           throw new RuntimeException("Not implemented.");
/*     */         }
/* 146 */         if (h == LengthConstraintType.RANGE) {
/* 147 */           throw new RuntimeException("Not implemented.");
/*     */         }
/*     */       } 
/* 150 */     } else if (w == LengthConstraintType.FIXED) {
/* 151 */       if (h == LengthConstraintType.NONE) {
/* 152 */         contentSize = arrangeFN(container, g2, constraint.getWidth());
/*     */       }
/* 154 */       else if (h == LengthConstraintType.FIXED) {
/* 155 */         contentSize = arrangeFF(container, g2, constraint);
/*     */       }
/* 157 */       else if (h == LengthConstraintType.RANGE) {
/* 158 */         contentSize = arrangeFR(container, g2, constraint);
/*     */       }
/*     */     
/* 161 */     } else if (w == LengthConstraintType.RANGE) {
/* 162 */       if (h == LengthConstraintType.NONE) {
/* 163 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 165 */       if (h == LengthConstraintType.FIXED) {
/* 166 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 168 */       if (h == LengthConstraintType.RANGE) {
/* 169 */         contentSize = arrangeRR(container, constraint.getWidthRange(), constraint
/* 170 */             .getHeightRange(), g2);
/*     */       }
/*     */     } 
/* 173 */     assert contentSize != null;
/* 174 */     return new Size2D(container.calculateTotalWidth(contentSize.getWidth()), container
/* 175 */         .calculateTotalHeight(contentSize.getHeight()));
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
/* 187 */     double[] w = new double[5];
/* 188 */     double[] h = new double[5];
/* 189 */     if (this.topBlock != null) {
/* 190 */       Size2D size = this.topBlock.arrange(g2, RectangleConstraint.NONE);
/* 191 */       w[0] = size.width;
/* 192 */       h[0] = size.height;
/*     */     } 
/* 194 */     if (this.bottomBlock != null) {
/* 195 */       Size2D size = this.bottomBlock.arrange(g2, RectangleConstraint.NONE);
/*     */       
/* 197 */       w[1] = size.width;
/* 198 */       h[1] = size.height;
/*     */     } 
/* 200 */     if (this.leftBlock != null) {
/* 201 */       Size2D size = this.leftBlock.arrange(g2, RectangleConstraint.NONE);
/* 202 */       w[2] = size.width;
/* 203 */       h[2] = size.height;
/*     */     } 
/* 205 */     if (this.rightBlock != null) {
/* 206 */       Size2D size = this.rightBlock.arrange(g2, RectangleConstraint.NONE);
/* 207 */       w[3] = size.width;
/* 208 */       h[3] = size.height;
/*     */     } 
/*     */     
/* 211 */     h[2] = Math.max(h[2], h[3]);
/* 212 */     h[3] = h[2];
/*     */     
/* 214 */     if (this.centerBlock != null) {
/* 215 */       Size2D size = this.centerBlock.arrange(g2, RectangleConstraint.NONE);
/*     */       
/* 217 */       w[4] = size.width;
/* 218 */       h[4] = size.height;
/*     */     } 
/* 220 */     double width = Math.max(w[0], Math.max(w[1], w[2] + w[4] + w[3]));
/* 221 */     double centerHeight = Math.max(h[2], Math.max(h[3], h[4]));
/* 222 */     double height = h[0] + h[1] + centerHeight;
/* 223 */     if (this.topBlock != null) {
/* 224 */       this.topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, width, h[0]));
/*     */     }
/*     */     
/* 227 */     if (this.bottomBlock != null) {
/* 228 */       this.bottomBlock.setBounds(new Rectangle2D.Double(0.0D, height - h[1], width, h[1]));
/*     */     }
/*     */     
/* 231 */     if (this.leftBlock != null) {
/* 232 */       this.leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], centerHeight));
/*     */     }
/*     */     
/* 235 */     if (this.rightBlock != null) {
/* 236 */       this.rightBlock.setBounds(new Rectangle2D.Double(width - w[3], h[0], w[3], centerHeight));
/*     */     }
/*     */ 
/*     */     
/* 240 */     if (this.centerBlock != null) {
/* 241 */       this.centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], width - w[2] - w[3], centerHeight));
/*     */     }
/*     */     
/* 244 */     return new Size2D(width, height);
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
/*     */   protected Size2D arrangeFR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/* 258 */     Size2D size1 = arrangeFN(container, g2, constraint.getWidth());
/* 259 */     if (constraint.getHeightRange().contains(size1.getHeight())) {
/* 260 */       return size1;
/*     */     }
/*     */     
/* 263 */     double h = constraint.getHeightRange().constrain(size1.getHeight());
/* 264 */     RectangleConstraint c2 = constraint.toFixedHeight(h);
/* 265 */     return arrange(container, g2, c2);
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
/*     */   protected Size2D arrangeFN(BlockContainer container, Graphics2D g2, double width) {
/* 281 */     double[] w = new double[5];
/* 282 */     double[] h = new double[5];
/* 283 */     RectangleConstraint c1 = new RectangleConstraint(width, null, LengthConstraintType.FIXED, 0.0D, null, LengthConstraintType.NONE);
/*     */ 
/*     */     
/* 286 */     if (this.topBlock != null) {
/* 287 */       Size2D size = this.topBlock.arrange(g2, c1);
/* 288 */       w[0] = size.width;
/* 289 */       h[0] = size.height;
/*     */     } 
/* 291 */     if (this.bottomBlock != null) {
/* 292 */       Size2D size = this.bottomBlock.arrange(g2, c1);
/* 293 */       w[1] = size.width;
/* 294 */       h[1] = size.height;
/*     */     } 
/* 296 */     RectangleConstraint c2 = new RectangleConstraint(0.0D, new Range(0.0D, width), LengthConstraintType.RANGE, 0.0D, null, LengthConstraintType.NONE);
/*     */ 
/*     */     
/* 299 */     if (this.leftBlock != null) {
/* 300 */       Size2D size = this.leftBlock.arrange(g2, c2);
/* 301 */       w[2] = size.width;
/* 302 */       h[2] = size.height;
/*     */     } 
/* 304 */     if (this.rightBlock != null) {
/* 305 */       double maxW = Math.max(width - w[2], 0.0D);
/*     */       
/* 307 */       RectangleConstraint c3 = new RectangleConstraint(0.0D, new Range(Math.min(w[2], maxW), maxW), LengthConstraintType.RANGE, 0.0D, null, LengthConstraintType.NONE);
/*     */ 
/*     */       
/* 310 */       Size2D size = this.rightBlock.arrange(g2, c3);
/* 311 */       w[3] = size.width;
/* 312 */       h[3] = size.height;
/*     */     } 
/*     */     
/* 315 */     h[2] = Math.max(h[2], h[3]);
/* 316 */     h[3] = h[2];
/*     */     
/* 318 */     if (this.centerBlock != null) {
/* 319 */       RectangleConstraint c4 = new RectangleConstraint(width - w[2] - w[3], null, LengthConstraintType.FIXED, 0.0D, null, LengthConstraintType.NONE);
/*     */ 
/*     */       
/* 322 */       Size2D size = this.centerBlock.arrange(g2, c4);
/* 323 */       w[4] = size.width;
/* 324 */       h[4] = size.height;
/*     */     } 
/* 326 */     double height = h[0] + h[1] + Math.max(h[2], Math.max(h[3], h[4]));
/* 327 */     return arrange(container, g2, new RectangleConstraint(width, height));
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
/*     */   protected Size2D arrangeRR(BlockContainer container, Range widthRange, Range heightRange, Graphics2D g2) {
/* 344 */     double[] w = new double[5];
/* 345 */     double[] h = new double[5];
/* 346 */     if (this.topBlock != null) {
/* 347 */       RectangleConstraint c1 = new RectangleConstraint(widthRange, heightRange);
/*     */       
/* 349 */       Size2D size = this.topBlock.arrange(g2, c1);
/* 350 */       w[0] = size.width;
/* 351 */       h[0] = size.height;
/*     */     } 
/* 353 */     if (this.bottomBlock != null) {
/* 354 */       Range heightRange2 = Range.shift(heightRange, -h[0], false);
/* 355 */       RectangleConstraint c2 = new RectangleConstraint(widthRange, heightRange2);
/*     */       
/* 357 */       Size2D size = this.bottomBlock.arrange(g2, c2);
/* 358 */       w[1] = size.width;
/* 359 */       h[1] = size.height;
/*     */     } 
/* 361 */     Range heightRange3 = Range.shift(heightRange, -(h[0] + h[1]));
/* 362 */     if (this.leftBlock != null) {
/* 363 */       RectangleConstraint c3 = new RectangleConstraint(widthRange, heightRange3);
/*     */       
/* 365 */       Size2D size = this.leftBlock.arrange(g2, c3);
/* 366 */       w[2] = size.width;
/* 367 */       h[2] = size.height;
/*     */     } 
/* 369 */     Range widthRange2 = Range.shift(widthRange, -w[2], false);
/* 370 */     if (this.rightBlock != null) {
/* 371 */       RectangleConstraint c4 = new RectangleConstraint(widthRange2, heightRange3);
/*     */       
/* 373 */       Size2D size = this.rightBlock.arrange(g2, c4);
/* 374 */       w[3] = size.width;
/* 375 */       h[3] = size.height;
/*     */     } 
/*     */     
/* 378 */     h[2] = Math.max(h[2], h[3]);
/* 379 */     h[3] = h[2];
/* 380 */     Range widthRange3 = Range.shift(widthRange, -(w[2] + w[3]), false);
/* 381 */     if (this.centerBlock != null) {
/* 382 */       RectangleConstraint c5 = new RectangleConstraint(widthRange3, heightRange3);
/*     */       
/* 384 */       Size2D size = this.centerBlock.arrange(g2, c5);
/* 385 */       w[4] = size.width;
/* 386 */       h[4] = size.height;
/*     */     } 
/* 388 */     double width = Math.max(w[0], Math.max(w[1], w[2] + w[4] + w[3]));
/* 389 */     double height = h[0] + h[1] + Math.max(h[2], Math.max(h[3], h[4]));
/* 390 */     if (this.topBlock != null) {
/* 391 */       this.topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, width, h[0]));
/*     */     }
/*     */     
/* 394 */     if (this.bottomBlock != null) {
/* 395 */       this.bottomBlock.setBounds(new Rectangle2D.Double(0.0D, height - h[1], width, h[1]));
/*     */     }
/*     */     
/* 398 */     if (this.leftBlock != null) {
/* 399 */       this.leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], h[2]));
/*     */     }
/*     */     
/* 402 */     if (this.rightBlock != null) {
/* 403 */       this.rightBlock.setBounds(new Rectangle2D.Double(width - w[3], h[0], w[3], h[3]));
/*     */     }
/*     */ 
/*     */     
/* 407 */     if (this.centerBlock != null) {
/* 408 */       this.centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], width - w[2] - w[3], height - h[0] - h[1]));
/*     */     }
/*     */     
/* 411 */     return new Size2D(width, height);
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
/* 425 */     double[] w = new double[5];
/* 426 */     double[] h = new double[5];
/* 427 */     w[0] = constraint.getWidth();
/* 428 */     if (this.topBlock != null) {
/*     */ 
/*     */       
/* 431 */       RectangleConstraint c1 = new RectangleConstraint(w[0], null, LengthConstraintType.FIXED, 0.0D, new Range(0.0D, constraint.getHeight()), LengthConstraintType.RANGE);
/*     */       
/* 433 */       Size2D size = this.topBlock.arrange(g2, c1);
/* 434 */       h[0] = size.height;
/*     */     } 
/* 436 */     w[1] = w[0];
/* 437 */     if (this.bottomBlock != null) {
/*     */ 
/*     */       
/* 440 */       RectangleConstraint c2 = new RectangleConstraint(w[0], null, LengthConstraintType.FIXED, 0.0D, new Range(0.0D, constraint.getHeight() - h[0]), LengthConstraintType.RANGE);
/* 441 */       Size2D size = this.bottomBlock.arrange(g2, c2);
/* 442 */       h[1] = size.height;
/*     */     } 
/* 444 */     h[2] = constraint.getHeight() - h[1] - h[0];
/* 445 */     if (this.leftBlock != null) {
/*     */       
/* 447 */       RectangleConstraint c3 = new RectangleConstraint(0.0D, new Range(0.0D, constraint.getWidth()), LengthConstraintType.RANGE, h[2], null, LengthConstraintType.FIXED);
/*     */ 
/*     */       
/* 450 */       Size2D size = this.leftBlock.arrange(g2, c3);
/* 451 */       w[2] = size.width;
/*     */     } 
/* 453 */     h[3] = h[2];
/* 454 */     if (this.rightBlock != null) {
/*     */       
/* 456 */       RectangleConstraint c4 = new RectangleConstraint(0.0D, new Range(0.0D, Math.max(constraint.getWidth() - w[2], 0.0D)), LengthConstraintType.RANGE, h[2], null, LengthConstraintType.FIXED);
/*     */ 
/*     */       
/* 459 */       Size2D size = this.rightBlock.arrange(g2, c4);
/* 460 */       w[3] = size.width;
/*     */     } 
/* 462 */     h[4] = h[2];
/* 463 */     w[4] = constraint.getWidth() - w[3] - w[2];
/* 464 */     RectangleConstraint c5 = new RectangleConstraint(w[4], h[4]);
/* 465 */     if (this.centerBlock != null) {
/* 466 */       this.centerBlock.arrange(g2, c5);
/*     */     }
/*     */     
/* 469 */     if (this.topBlock != null) {
/* 470 */       this.topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, w[0], h[0]));
/*     */     }
/*     */     
/* 473 */     if (this.bottomBlock != null) {
/* 474 */       this.bottomBlock.setBounds(new Rectangle2D.Double(0.0D, h[0] + h[2], w[1], h[1]));
/*     */     }
/*     */     
/* 477 */     if (this.leftBlock != null) {
/* 478 */       this.leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], h[2]));
/*     */     }
/*     */     
/* 481 */     if (this.rightBlock != null) {
/* 482 */       this.rightBlock.setBounds(new Rectangle2D.Double(w[2] + w[4], h[0], w[3], h[3]));
/*     */     }
/*     */     
/* 485 */     if (this.centerBlock != null) {
/* 486 */       this.centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], w[4], h[4]));
/*     */     }
/*     */     
/* 489 */     return new Size2D(constraint.getWidth(), constraint.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 497 */     this.centerBlock = null;
/* 498 */     this.topBlock = null;
/* 499 */     this.bottomBlock = null;
/* 500 */     this.leftBlock = null;
/* 501 */     this.rightBlock = null;
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
/*     */   public boolean equals(Object obj) {
/* 513 */     if (obj == this) {
/* 514 */       return true;
/*     */     }
/* 516 */     if (!(obj instanceof BorderArrangement)) {
/* 517 */       return false;
/*     */     }
/* 519 */     BorderArrangement that = (BorderArrangement)obj;
/* 520 */     if (!ObjectUtilities.equal(this.topBlock, that.topBlock)) {
/* 521 */       return false;
/*     */     }
/* 523 */     if (!ObjectUtilities.equal(this.bottomBlock, that.bottomBlock)) {
/* 524 */       return false;
/*     */     }
/* 526 */     if (!ObjectUtilities.equal(this.leftBlock, that.leftBlock)) {
/* 527 */       return false;
/*     */     }
/* 529 */     if (!ObjectUtilities.equal(this.rightBlock, that.rightBlock)) {
/* 530 */       return false;
/*     */     }
/* 532 */     if (!ObjectUtilities.equal(this.centerBlock, that.centerBlock)) {
/* 533 */       return false;
/*     */     }
/* 535 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/BorderArrangement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.jfree.chart.block;
/*     */ 
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CenterArrangement
/*     */   implements Arrangement, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -353308149220382047L;
/*     */   
/*     */   public void add(Block block, Object key) {}
/*     */   
/*     */   public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint) {
/*  96 */     LengthConstraintType w = constraint.getWidthConstraintType();
/*  97 */     LengthConstraintType h = constraint.getHeightConstraintType();
/*  98 */     if (w == LengthConstraintType.NONE) {
/*  99 */       if (h == LengthConstraintType.NONE) {
/* 100 */         return arrangeNN(container, g2);
/*     */       }
/* 102 */       if (h == LengthConstraintType.FIXED) {
/* 103 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 105 */       if (h == LengthConstraintType.RANGE) {
/* 106 */         throw new RuntimeException("Not implemented.");
/*     */       }
/*     */     }
/* 109 */     else if (w == LengthConstraintType.FIXED) {
/* 110 */       if (h == LengthConstraintType.NONE) {
/* 111 */         return arrangeFN(container, g2, constraint);
/*     */       }
/* 113 */       if (h == LengthConstraintType.FIXED) {
/* 114 */         throw new RuntimeException("Not implemented.");
/*     */       }
/* 116 */       if (h == LengthConstraintType.RANGE) {
/* 117 */         throw new RuntimeException("Not implemented.");
/*     */       }
/*     */     }
/* 120 */     else if (w == LengthConstraintType.RANGE) {
/* 121 */       if (h == LengthConstraintType.NONE) {
/* 122 */         return arrangeRN(container, g2, constraint);
/*     */       }
/* 124 */       if (h == LengthConstraintType.FIXED) {
/* 125 */         return arrangeRF(container, g2, constraint);
/*     */       }
/* 127 */       if (h == LengthConstraintType.RANGE) {
/* 128 */         return arrangeRR(container, g2, constraint);
/*     */       }
/*     */     } 
/* 131 */     throw new IllegalArgumentException("Unknown LengthConstraintType.");
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
/* 148 */     List<Block> blocks = container.getBlocks();
/* 149 */     Block b = blocks.get(0);
/* 150 */     Size2D s = b.arrange(g2, RectangleConstraint.NONE);
/* 151 */     double width = constraint.getWidth();
/* 152 */     Rectangle2D bounds = new Rectangle2D.Double((width - s.width) / 2.0D, 0.0D, s.width, s.height);
/*     */     
/* 154 */     b.setBounds(bounds);
/* 155 */     return new Size2D((width - s.width) / 2.0D, s.height);
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
/* 171 */     Size2D s = arrangeFN(container, g2, constraint);
/* 172 */     if (constraint.getHeightRange().contains(s.height)) {
/* 173 */       return s;
/*     */     }
/*     */     
/* 176 */     RectangleConstraint c = constraint.toFixedHeight(constraint
/* 177 */         .getHeightRange().constrain(s.getHeight()));
/* 178 */     return arrangeFF(container, g2, c);
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
/* 196 */     return arrangeFN(container, g2, constraint);
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
/* 214 */     Size2D s1 = arrangeNN(container, g2);
/* 215 */     if (constraint.getWidthRange().contains(s1.width)) {
/* 216 */       return s1;
/*     */     }
/*     */     
/* 219 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 220 */         .getWidthRange().getUpperBound());
/* 221 */     return arrangeFR(container, g2, c);
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
/* 238 */     Size2D s = arrangeNF(container, g2, constraint);
/* 239 */     if (constraint.getWidthRange().contains(s.width)) {
/* 240 */       return s;
/*     */     }
/*     */     
/* 243 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 244 */         .getWidthRange().constrain(s.getWidth()));
/* 245 */     return arrangeFF(container, g2, c);
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
/* 263 */     Size2D s1 = arrangeNN(container, g2);
/* 264 */     if (constraint.getWidthRange().contains(s1.width)) {
/* 265 */       return s1;
/*     */     }
/*     */     
/* 268 */     RectangleConstraint c = constraint.toFixedWidth(constraint
/* 269 */         .getWidthRange().getUpperBound());
/* 270 */     return arrangeFN(container, g2, c);
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
/* 284 */     List<Block> blocks = container.getBlocks();
/* 285 */     Block b = blocks.get(0);
/* 286 */     Size2D s = b.arrange(g2, RectangleConstraint.NONE);
/* 287 */     b.setBounds(new Rectangle2D.Double(0.0D, 0.0D, s.width, s.height));
/* 288 */     return new Size2D(s.width, s.height);
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
/* 304 */     return arrangeNN(container, g2);
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
/* 324 */     if (obj == this) {
/* 325 */       return true;
/*     */     }
/* 327 */     if (!(obj instanceof CenterArrangement)) {
/* 328 */       return false;
/*     */     }
/* 330 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/block/CenterArrangement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
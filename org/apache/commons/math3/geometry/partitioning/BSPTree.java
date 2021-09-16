/*     */ package org.apache.commons.math3.geometry.partitioning;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BSPTree<S extends Space>
/*     */ {
/*     */   private SubHyperplane<S> cut;
/*     */   private BSPTree<S> plus;
/*     */   private BSPTree<S> minus;
/*     */   private BSPTree<S> parent;
/*     */   private Object attribute;
/*     */   
/*     */   public BSPTree() {
/*  88 */     this.cut = null;
/*  89 */     this.plus = null;
/*  90 */     this.minus = null;
/*  91 */     this.parent = null;
/*  92 */     this.attribute = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree(Object attribute) {
/*  99 */     this.cut = null;
/* 100 */     this.plus = null;
/* 101 */     this.minus = null;
/* 102 */     this.parent = null;
/* 103 */     this.attribute = attribute;
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
/*     */   public BSPTree(SubHyperplane<S> cut, BSPTree<S> plus, BSPTree<S> minus, Object attribute) {
/* 121 */     this.cut = cut;
/* 122 */     this.plus = plus;
/* 123 */     this.minus = minus;
/* 124 */     this.parent = null;
/* 125 */     this.attribute = attribute;
/* 126 */     plus.parent = this;
/* 127 */     minus.parent = this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean insertCut(Hyperplane<S> hyperplane) {
/* 155 */     if (this.cut != null) {
/* 156 */       this.plus.parent = null;
/* 157 */       this.minus.parent = null;
/*     */     } 
/*     */     
/* 160 */     SubHyperplane<S> chopped = fitToCell(hyperplane.wholeHyperplane());
/* 161 */     if (chopped == null || chopped.isEmpty()) {
/* 162 */       this.cut = null;
/* 163 */       this.plus = null;
/* 164 */       this.minus = null;
/* 165 */       return false;
/*     */     } 
/*     */     
/* 168 */     this.cut = chopped;
/* 169 */     this.plus = new BSPTree();
/* 170 */     this.plus.parent = this;
/* 171 */     this.minus = new BSPTree();
/* 172 */     this.minus.parent = this;
/* 173 */     return true;
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
/*     */   public BSPTree<S> copySelf() {
/* 186 */     if (this.cut == null) {
/* 187 */       return new BSPTree(this.attribute);
/*     */     }
/*     */     
/* 190 */     return new BSPTree(this.cut.copySelf(), this.plus.copySelf(), this.minus.copySelf(), this.attribute);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane<S> getCut() {
/* 199 */     return this.cut;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> getPlus() {
/* 207 */     return this.plus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> getMinus() {
/* 215 */     return this.minus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> getParent() {
/* 222 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(Object attribute) {
/* 230 */     this.attribute = attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute() {
/* 240 */     return this.attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(BSPTreeVisitor<S> visitor) {
/* 247 */     if (this.cut == null) {
/* 248 */       visitor.visitLeafNode(this);
/*     */     } else {
/* 250 */       switch (visitor.visitOrder(this)) {
/*     */         case PLUS:
/* 252 */           this.plus.visit(visitor);
/* 253 */           this.minus.visit(visitor);
/* 254 */           visitor.visitInternalNode(this);
/*     */           return;
/*     */         case MINUS:
/* 257 */           this.plus.visit(visitor);
/* 258 */           visitor.visitInternalNode(this);
/* 259 */           this.minus.visit(visitor);
/*     */           return;
/*     */         case BOTH:
/* 262 */           this.minus.visit(visitor);
/* 263 */           this.plus.visit(visitor);
/* 264 */           visitor.visitInternalNode(this);
/*     */           return;
/*     */         case null:
/* 267 */           this.minus.visit(visitor);
/* 268 */           visitor.visitInternalNode(this);
/* 269 */           this.plus.visit(visitor);
/*     */           return;
/*     */         case null:
/* 272 */           visitor.visitInternalNode(this);
/* 273 */           this.plus.visit(visitor);
/* 274 */           this.minus.visit(visitor);
/*     */           return;
/*     */         case null:
/* 277 */           visitor.visitInternalNode(this);
/* 278 */           this.minus.visit(visitor);
/* 279 */           this.plus.visit(visitor);
/*     */           return;
/*     */       } 
/* 282 */       throw new MathInternalError();
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
/*     */   private SubHyperplane<S> fitToCell(SubHyperplane<S> sub) {
/* 297 */     SubHyperplane<S> s = sub;
/* 298 */     for (BSPTree<S> tree = this; tree.parent != null && s != null; tree = tree.parent) {
/* 299 */       if (tree == tree.parent.plus) {
/* 300 */         s = s.split(tree.parent.cut.getHyperplane()).getPlus();
/*     */       } else {
/* 302 */         s = s.split(tree.parent.cut.getHyperplane()).getMinus();
/*     */       } 
/*     */     } 
/* 305 */     return s;
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
/*     */   @Deprecated
/*     */   public BSPTree<S> getCell(Vector<S> point) {
/* 318 */     return getCell((Point<S>)point, 1.0E-10D);
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
/*     */   public BSPTree<S> getCell(Point<S> point, double tolerance) {
/* 332 */     if (this.cut == null) {
/* 333 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 337 */     double offset = this.cut.getHyperplane().getOffset(point);
/*     */     
/* 339 */     if (FastMath.abs(offset) < tolerance)
/* 340 */       return this; 
/* 341 */     if (offset <= 0.0D)
/*     */     {
/* 343 */       return this.minus.getCell(point, tolerance);
/*     */     }
/*     */     
/* 346 */     return this.plus.getCell(point, tolerance);
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
/*     */   public List<BSPTree<S>> getCloseCuts(Point<S> point, double maxOffset) {
/* 359 */     List<BSPTree<S>> close = new ArrayList<BSPTree<S>>();
/* 360 */     recurseCloseCuts(point, maxOffset, close);
/* 361 */     return close;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recurseCloseCuts(Point<S> point, double maxOffset, List<BSPTree<S>> close) {
/* 372 */     if (this.cut != null) {
/*     */ 
/*     */       
/* 375 */       double offset = this.cut.getHyperplane().getOffset(point);
/*     */       
/* 377 */       if (offset < -maxOffset) {
/*     */         
/* 379 */         this.minus.recurseCloseCuts(point, maxOffset, close);
/* 380 */       } else if (offset > maxOffset) {
/*     */         
/* 382 */         this.plus.recurseCloseCuts(point, maxOffset, close);
/*     */       } else {
/*     */         
/* 385 */         close.add(this);
/* 386 */         this.minus.recurseCloseCuts(point, maxOffset, close);
/* 387 */         this.plus.recurseCloseCuts(point, maxOffset, close);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void condense() {
/* 398 */     if (this.cut != null && this.plus.cut == null && this.minus.cut == null && ((this.plus.attribute == null && this.minus.attribute == null) || (this.plus.attribute != null && this.plus.attribute.equals(this.minus.attribute)))) {
/*     */ 
/*     */       
/* 401 */       this.attribute = (this.plus.attribute == null) ? this.minus.attribute : this.plus.attribute;
/* 402 */       this.cut = null;
/* 403 */       this.plus = null;
/* 404 */       this.minus = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> merge(BSPTree<S> tree, LeafMerger<S> leafMerger) {
/* 427 */     return merge(tree, leafMerger, null, false);
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
/*     */   private BSPTree<S> merge(BSPTree<S> tree, LeafMerger<S> leafMerger, BSPTree<S> parentTree, boolean isPlusChild) {
/* 447 */     if (this.cut == null)
/*     */     {
/* 449 */       return leafMerger.merge(this, tree, parentTree, isPlusChild, true); } 
/* 450 */     if (tree.cut == null)
/*     */     {
/* 452 */       return leafMerger.merge(tree, this, parentTree, isPlusChild, false);
/*     */     }
/*     */     
/* 455 */     BSPTree<S> merged = tree.split(this.cut);
/* 456 */     if (parentTree != null) {
/* 457 */       merged.parent = parentTree;
/* 458 */       if (isPlusChild) {
/* 459 */         parentTree.plus = merged;
/*     */       } else {
/* 461 */         parentTree.minus = merged;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 466 */     this.plus.merge(merged.plus, leafMerger, merged, true);
/* 467 */     this.minus.merge(merged.minus, leafMerger, merged, false);
/* 468 */     merged.condense();
/* 469 */     if (merged.cut != null) {
/* 470 */       merged.cut = merged.fitToCell(merged.cut.getHyperplane().wholeHyperplane());
/*     */     }
/*     */     
/* 473 */     return merged;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> split(SubHyperplane<S> sub) {
/*     */     BSPTree<S> split;
/*     */     SubHyperplane.SplitSubHyperplane<S> cutParts;
/*     */     BSPTree<S> bSPTree1, tmp;
/* 572 */     if (this.cut == null) {
/* 573 */       return new BSPTree(sub, copySelf(), new BSPTree(this.attribute), null);
/*     */     }
/*     */     
/* 576 */     Hyperplane<S> cHyperplane = this.cut.getHyperplane();
/* 577 */     Hyperplane<S> sHyperplane = sub.getHyperplane();
/* 578 */     SubHyperplane.SplitSubHyperplane<S> subParts = sub.split(cHyperplane);
/* 579 */     switch (subParts.getSide()) {
/*     */       
/*     */       case PLUS:
/* 582 */         split = this.plus.split(sub);
/* 583 */         if (this.cut.split(sHyperplane).getSide() == Side.PLUS) {
/* 584 */           split.plus = new BSPTree(this.cut.copySelf(), split.plus, this.minus.copySelf(), this.attribute);
/*     */           
/* 586 */           split.plus.condense();
/* 587 */           split.plus.parent = split;
/*     */         } else {
/* 589 */           split.minus = new BSPTree(this.cut.copySelf(), split.minus, this.minus.copySelf(), this.attribute);
/*     */           
/* 591 */           split.minus.condense();
/* 592 */           split.minus.parent = split;
/*     */         } 
/* 594 */         return split;
/*     */ 
/*     */       
/*     */       case MINUS:
/* 598 */         split = this.minus.split(sub);
/* 599 */         if (this.cut.split(sHyperplane).getSide() == Side.PLUS) {
/* 600 */           split.plus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.plus, this.attribute);
/*     */           
/* 602 */           split.plus.condense();
/* 603 */           split.plus.parent = split;
/*     */         } else {
/* 605 */           split.minus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.minus, this.attribute);
/*     */           
/* 607 */           split.minus.condense();
/* 608 */           split.minus.parent = split;
/*     */         } 
/* 610 */         return split;
/*     */ 
/*     */       
/*     */       case BOTH:
/* 614 */         cutParts = this.cut.split(sHyperplane);
/* 615 */         bSPTree1 = new BSPTree(sub, this.plus.split(subParts.getPlus()), this.minus.split(subParts.getMinus()), null);
/*     */ 
/*     */         
/* 618 */         bSPTree1.plus.cut = cutParts.getPlus();
/* 619 */         bSPTree1.minus.cut = cutParts.getMinus();
/* 620 */         tmp = bSPTree1.plus.minus;
/* 621 */         bSPTree1.plus.minus = bSPTree1.minus.plus;
/* 622 */         bSPTree1.plus.minus.parent = bSPTree1.plus;
/* 623 */         bSPTree1.minus.plus = tmp;
/* 624 */         bSPTree1.minus.plus.parent = bSPTree1.minus;
/* 625 */         bSPTree1.plus.condense();
/* 626 */         bSPTree1.minus.condense();
/* 627 */         return bSPTree1;
/*     */     } 
/*     */     
/* 630 */     return cHyperplane.sameOrientationAs(sHyperplane) ? new BSPTree(sub, this.plus.copySelf(), this.minus.copySelf(), this.attribute) : new BSPTree(sub, this.minus.copySelf(), this.plus.copySelf(), this.attribute);
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
/*     */   @Deprecated
/*     */   public void insertInTree(BSPTree<S> parentTree, boolean isPlusChild) {
/* 649 */     insertInTree(parentTree, isPlusChild, new VanishingCutHandler<S>()
/*     */         {
/*     */           public BSPTree<S> fixNode(BSPTree<S> node)
/*     */           {
/* 653 */             throw new MathIllegalStateException(LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface VanishingCutHandler<S extends Space>
/*     */   {
/*     */     BSPTree<S> fixNode(BSPTree<S> param1BSPTree);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface LeafMerger<S extends Space>
/*     */   {
/*     */     BSPTree<S> merge(BSPTree<S> param1BSPTree1, BSPTree<S> param1BSPTree2, BSPTree<S> param1BSPTree3, boolean param1Boolean1, boolean param1Boolean2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertInTree(BSPTree<S> parentTree, boolean isPlusChild, VanishingCutHandler<S> vanishingHandler) {
/* 674 */     this.parent = parentTree;
/* 675 */     if (parentTree != null) {
/* 676 */       if (isPlusChild) {
/* 677 */         parentTree.plus = this;
/*     */       } else {
/* 679 */         parentTree.minus = this;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 684 */     if (this.cut != null) {
/*     */ 
/*     */       
/* 687 */       for (BSPTree<S> tree = this; tree.parent != null; tree = tree.parent) {
/*     */ 
/*     */         
/* 690 */         Hyperplane<S> hyperplane = tree.parent.cut.getHyperplane();
/*     */ 
/*     */ 
/*     */         
/* 694 */         if (tree == tree.parent.plus) {
/* 695 */           this.cut = this.cut.split(hyperplane).getPlus();
/* 696 */           this.plus.chopOffMinus(hyperplane, vanishingHandler);
/* 697 */           this.minus.chopOffMinus(hyperplane, vanishingHandler);
/*     */         } else {
/* 699 */           this.cut = this.cut.split(hyperplane).getMinus();
/* 700 */           this.plus.chopOffPlus(hyperplane, vanishingHandler);
/* 701 */           this.minus.chopOffPlus(hyperplane, vanishingHandler);
/*     */         } 
/*     */         
/* 704 */         if (this.cut == null) {
/*     */           
/* 706 */           BSPTree<S> fixed = vanishingHandler.fixNode(this);
/* 707 */           this.cut = fixed.cut;
/* 708 */           this.plus = fixed.plus;
/* 709 */           this.minus = fixed.minus;
/* 710 */           this.attribute = fixed.attribute;
/* 711 */           if (this.cut == null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 720 */       condense();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BSPTree<S> pruneAroundConvexCell(Object cellAttribute, Object otherLeafsAttributes, Object internalAttributes) {
/* 750 */     BSPTree<S> tree = new BSPTree(cellAttribute);
/*     */ 
/*     */     
/* 753 */     for (BSPTree<S> current = this; current.parent != null; current = current.parent) {
/* 754 */       SubHyperplane<S> parentCut = current.parent.cut.copySelf();
/* 755 */       BSPTree<S> sibling = new BSPTree(otherLeafsAttributes);
/* 756 */       if (current == current.parent.plus) {
/* 757 */         tree = new BSPTree(parentCut, tree, sibling, internalAttributes);
/*     */       } else {
/* 759 */         tree = new BSPTree(parentCut, sibling, tree, internalAttributes);
/*     */       } 
/*     */     } 
/*     */     
/* 763 */     return tree;
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
/*     */   private void chopOffMinus(Hyperplane<S> hyperplane, VanishingCutHandler<S> vanishingHandler) {
/* 776 */     if (this.cut != null) {
/*     */       
/* 778 */       this.cut = this.cut.split(hyperplane).getPlus();
/* 779 */       this.plus.chopOffMinus(hyperplane, vanishingHandler);
/* 780 */       this.minus.chopOffMinus(hyperplane, vanishingHandler);
/*     */       
/* 782 */       if (this.cut == null) {
/*     */         
/* 784 */         BSPTree<S> fixed = vanishingHandler.fixNode(this);
/* 785 */         this.cut = fixed.cut;
/* 786 */         this.plus = fixed.plus;
/* 787 */         this.minus = fixed.minus;
/* 788 */         this.attribute = fixed.attribute;
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
/*     */   private void chopOffPlus(Hyperplane<S> hyperplane, VanishingCutHandler<S> vanishingHandler) {
/* 803 */     if (this.cut != null) {
/*     */       
/* 805 */       this.cut = this.cut.split(hyperplane).getMinus();
/* 806 */       this.plus.chopOffPlus(hyperplane, vanishingHandler);
/* 807 */       this.minus.chopOffPlus(hyperplane, vanishingHandler);
/*     */       
/* 809 */       if (this.cut == null) {
/*     */         
/* 811 */         BSPTree<S> fixed = vanishingHandler.fixNode(this);
/* 812 */         this.cut = fixed.cut;
/* 813 */         this.plus = fixed.plus;
/* 814 */         this.minus = fixed.minus;
/* 815 */         this.attribute = fixed.attribute;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/BSPTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
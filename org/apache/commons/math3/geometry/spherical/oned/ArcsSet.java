/*     */ package org.apache.commons.math3.geometry.spherical.oned;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryProjection;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.Side;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArcsSet
/*     */   extends AbstractRegion<Sphere1D, Sphere1D>
/*     */   implements Iterable<double[]>
/*     */ {
/*     */   public ArcsSet(double tolerance) {
/*  55 */     super(tolerance);
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
/*     */   public ArcsSet(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
/*  73 */     super(buildTree(lower, upper, tolerance), tolerance);
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
/*     */   public ArcsSet(BSPTree<Sphere1D> tree, double tolerance) throws InconsistentStateAt2PiWrapping {
/*  90 */     super(tree, tolerance);
/*  91 */     check2PiConsistency();
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
/*     */   public ArcsSet(Collection<SubHyperplane<Sphere1D>> boundary, double tolerance) throws InconsistentStateAt2PiWrapping {
/* 118 */     super(boundary, tolerance);
/* 119 */     check2PiConsistency();
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
/*     */   private static BSPTree<Sphere1D> buildTree(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
/* 133 */     if (Precision.equals(lower, upper, 0) || upper - lower >= 6.283185307179586D)
/*     */     {
/* 135 */       return new BSPTree(Boolean.TRUE); } 
/* 136 */     if (lower > upper) {
/* 137 */       throw new NumberIsTooLargeException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(lower), Double.valueOf(upper), true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     double normalizedLower = MathUtils.normalizeAngle(lower, Math.PI);
/* 143 */     double normalizedUpper = normalizedLower + upper - lower;
/* 144 */     SubLimitAngle subLimitAngle1 = (new LimitAngle(new S1Point(normalizedLower), false, tolerance)).wholeHyperplane();
/*     */ 
/*     */     
/* 147 */     if (normalizedUpper <= 6.283185307179586D) {
/*     */       
/* 149 */       SubLimitAngle subLimitAngle = (new LimitAngle(new S1Point(normalizedUpper), true, tolerance)).wholeHyperplane();
/*     */       
/* 151 */       return new BSPTree((SubHyperplane)subLimitAngle1, new BSPTree(Boolean.FALSE), new BSPTree((SubHyperplane)subLimitAngle, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), null);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     SubLimitAngle subLimitAngle2 = (new LimitAngle(new S1Point(normalizedUpper - 6.283185307179586D), true, tolerance)).wholeHyperplane();
/*     */     
/* 162 */     return new BSPTree((SubHyperplane)subLimitAngle1, new BSPTree((SubHyperplane)subLimitAngle2, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), new BSPTree(Boolean.TRUE), null);
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
/*     */   private void check2PiConsistency() throws InconsistentStateAt2PiWrapping {
/* 180 */     BSPTree<Sphere1D> root = getTree(false);
/* 181 */     if (root.getCut() == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 186 */     Boolean stateBefore = (Boolean)getFirstLeaf(root).getAttribute();
/*     */ 
/*     */     
/* 189 */     Boolean stateAfter = (Boolean)getLastLeaf(root).getAttribute();
/*     */     
/* 191 */     if ((stateBefore.booleanValue() ^ stateAfter.booleanValue()) != 0) {
/* 192 */       throw new InconsistentStateAt2PiWrapping();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> getFirstLeaf(BSPTree<Sphere1D> root) {
/* 203 */     if (root.getCut() == null) {
/* 204 */       return root;
/*     */     }
/*     */ 
/*     */     
/* 208 */     BSPTree<Sphere1D> smallest = null;
/* 209 */     for (BSPTree<Sphere1D> n = root; n != null; n = previousInternalNode(n)) {
/* 210 */       smallest = n;
/*     */     }
/*     */     
/* 213 */     return leafBefore(smallest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> getLastLeaf(BSPTree<Sphere1D> root) {
/* 223 */     if (root.getCut() == null) {
/* 224 */       return root;
/*     */     }
/*     */ 
/*     */     
/* 228 */     BSPTree<Sphere1D> largest = null;
/* 229 */     for (BSPTree<Sphere1D> n = root; n != null; n = nextInternalNode(n)) {
/* 230 */       largest = n;
/*     */     }
/*     */     
/* 233 */     return leafAfter(largest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> getFirstArcStart() {
/* 244 */     BSPTree<Sphere1D> node = getTree(false);
/* 245 */     if (node.getCut() == null) {
/* 246 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 250 */     node = getFirstLeaf(node).getParent();
/*     */ 
/*     */     
/* 253 */     while (node != null && !isArcStart(node)) {
/* 254 */       node = nextInternalNode(node);
/*     */     }
/*     */     
/* 257 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isArcStart(BSPTree<Sphere1D> node) {
/* 267 */     if (((Boolean)leafBefore(node).getAttribute()).booleanValue())
/*     */     {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     if (!((Boolean)leafAfter(node).getAttribute()).booleanValue())
/*     */     {
/* 274 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 279 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isArcEnd(BSPTree<Sphere1D> node) {
/* 289 */     if (!((Boolean)leafBefore(node).getAttribute()).booleanValue())
/*     */     {
/* 291 */       return false;
/*     */     }
/*     */     
/* 294 */     if (((Boolean)leafAfter(node).getAttribute()).booleanValue())
/*     */     {
/* 296 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 301 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> nextInternalNode(BSPTree<Sphere1D> node) {
/* 312 */     if (childAfter(node).getCut() != null)
/*     */     {
/* 314 */       return leafAfter(node).getParent();
/*     */     }
/*     */ 
/*     */     
/* 318 */     while (isAfterParent(node)) {
/* 319 */       node = node.getParent();
/*     */     }
/* 321 */     return node.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> previousInternalNode(BSPTree<Sphere1D> node) {
/* 332 */     if (childBefore(node).getCut() != null)
/*     */     {
/* 334 */       return leafBefore(node).getParent();
/*     */     }
/*     */ 
/*     */     
/* 338 */     while (isBeforeParent(node)) {
/* 339 */       node = node.getParent();
/*     */     }
/* 341 */     return node.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> leafBefore(BSPTree<Sphere1D> node) {
/* 351 */     node = childBefore(node);
/* 352 */     while (node.getCut() != null) {
/* 353 */       node = childAfter(node);
/*     */     }
/*     */     
/* 356 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> leafAfter(BSPTree<Sphere1D> node) {
/* 366 */     node = childAfter(node);
/* 367 */     while (node.getCut() != null) {
/* 368 */       node = childBefore(node);
/*     */     }
/*     */     
/* 371 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBeforeParent(BSPTree<Sphere1D> node) {
/* 380 */     BSPTree<Sphere1D> parent = node.getParent();
/* 381 */     if (parent == null) {
/* 382 */       return false;
/*     */     }
/* 384 */     return (node == childBefore(parent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAfterParent(BSPTree<Sphere1D> node) {
/* 393 */     BSPTree<Sphere1D> parent = node.getParent();
/* 394 */     if (parent == null) {
/* 395 */       return false;
/*     */     }
/* 397 */     return (node == childAfter(parent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> childBefore(BSPTree<Sphere1D> node) {
/* 406 */     if (isDirect(node))
/*     */     {
/* 408 */       return node.getMinus();
/*     */     }
/*     */     
/* 411 */     return node.getPlus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Sphere1D> childAfter(BSPTree<Sphere1D> node) {
/* 420 */     if (isDirect(node))
/*     */     {
/* 422 */       return node.getPlus();
/*     */     }
/*     */     
/* 425 */     return node.getMinus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDirect(BSPTree<Sphere1D> node) {
/* 434 */     return ((LimitAngle)node.getCut().getHyperplane()).isDirect();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getAngle(BSPTree<Sphere1D> node) {
/* 442 */     return ((LimitAngle)node.getCut().getHyperplane()).getLocation().getAlpha();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArcsSet buildNew(BSPTree<Sphere1D> tree) {
/* 448 */     return new ArcsSet(tree, getTolerance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeGeometricalProperties() {
/* 454 */     if (getTree(false).getCut() == null) {
/* 455 */       setBarycenter(S1Point.NaN);
/* 456 */       setSize(((Boolean)getTree(false).getAttribute()).booleanValue() ? 6.283185307179586D : 0.0D);
/*     */     } else {
/* 458 */       double size = 0.0D;
/* 459 */       double sum = 0.0D;
/* 460 */       for (double[] a : this) {
/* 461 */         double length = a[1] - a[0];
/* 462 */         size += length;
/* 463 */         sum += length * (a[0] + a[1]);
/*     */       } 
/* 465 */       setSize(size);
/* 466 */       if (Precision.equals(size, 6.283185307179586D, 0)) {
/* 467 */         setBarycenter(S1Point.NaN);
/* 468 */       } else if (size >= Precision.SAFE_MIN) {
/* 469 */         setBarycenter(new S1Point(sum / 2.0D * size));
/*     */       } else {
/* 471 */         LimitAngle limit = (LimitAngle)getTree(false).getCut().getHyperplane();
/* 472 */         setBarycenter(limit.getLocation());
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
/*     */   public BoundaryProjection<Sphere1D> projectToBoundary(Point<Sphere1D> point) {
/* 484 */     double alpha = ((S1Point)point).getAlpha();
/*     */     
/* 486 */     boolean wrapFirst = false;
/* 487 */     double first = Double.NaN;
/* 488 */     double previous = Double.NaN;
/* 489 */     for (double[] a : this) {
/*     */       
/* 491 */       if (Double.isNaN(first))
/*     */       {
/* 493 */         first = a[0];
/*     */       }
/*     */       
/* 496 */       if (!wrapFirst) {
/* 497 */         if (alpha < a[0]) {
/*     */ 
/*     */           
/* 500 */           if (Double.isNaN(previous)) {
/*     */             
/* 502 */             wrapFirst = true;
/*     */           } else {
/* 504 */             double d1 = alpha - previous;
/* 505 */             double d2 = a[0] - alpha;
/* 506 */             if (d1 < d2) {
/* 507 */               return new BoundaryProjection(point, new S1Point(previous), d1);
/*     */             }
/* 509 */             return new BoundaryProjection(point, new S1Point(a[0]), d2);
/*     */           }
/*     */         
/* 512 */         } else if (alpha <= a[1]) {
/*     */ 
/*     */           
/* 515 */           double offset0 = a[0] - alpha;
/* 516 */           double offset1 = alpha - a[1];
/* 517 */           if (offset0 < offset1) {
/* 518 */             return new BoundaryProjection(point, new S1Point(a[1]), offset1);
/*     */           }
/* 520 */           return new BoundaryProjection(point, new S1Point(a[0]), offset0);
/*     */         } 
/*     */       }
/*     */       
/* 524 */       previous = a[1];
/*     */     } 
/*     */     
/* 527 */     if (Double.isNaN(previous))
/*     */     {
/*     */       
/* 530 */       return new BoundaryProjection(point, null, 6.283185307179586D);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 536 */     if (wrapFirst) {
/*     */       
/* 538 */       double d1 = alpha - previous - 6.283185307179586D;
/* 539 */       double d2 = first - alpha;
/* 540 */       if (d1 < d2) {
/* 541 */         return new BoundaryProjection(point, new S1Point(previous), d1);
/*     */       }
/* 543 */       return new BoundaryProjection(point, new S1Point(first), d2);
/*     */     } 
/*     */ 
/*     */     
/* 547 */     double previousOffset = alpha - previous;
/* 548 */     double currentOffset = first + 6.283185307179586D - alpha;
/* 549 */     if (previousOffset < currentOffset) {
/* 550 */       return new BoundaryProjection(point, new S1Point(previous), previousOffset);
/*     */     }
/* 552 */     return new BoundaryProjection(point, new S1Point(first), currentOffset);
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
/*     */   public List<Arc> asList() {
/* 568 */     List<Arc> list = new ArrayList<Arc>();
/* 569 */     for (double[] a : this) {
/* 570 */       list.add(new Arc(a[0], a[1], getTolerance()));
/*     */     }
/* 572 */     return list;
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
/*     */   public Iterator<double[]> iterator() {
/* 584 */     return new SubArcsIterator();
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
/*     */   private class SubArcsIterator
/*     */     implements Iterator<double[]>
/*     */   {
/* 603 */     private final BSPTree<Sphere1D> firstStart = ArcsSet.this.getFirstArcStart();
/* 604 */     private BSPTree<Sphere1D> current = this.firstStart;
/*     */     SubArcsIterator() {
/* 606 */       if (this.firstStart == null) {
/*     */         
/* 608 */         if (((Boolean)ArcsSet.this.getFirstLeaf(ArcsSet.this.getTree(false)).getAttribute()).booleanValue()) {
/*     */           
/* 610 */           this.pending = new double[] { 0.0D, 6.283185307179586D };
/*     */         }
/*     */         else {
/*     */           
/* 614 */           this.pending = null;
/*     */         } 
/*     */       } else {
/* 617 */         selectPending();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private double[] pending;
/*     */ 
/*     */     
/*     */     private void selectPending() {
/* 626 */       BSPTree<Sphere1D> start = this.current;
/* 627 */       while (start != null && !ArcsSet.this.isArcStart(start)) {
/* 628 */         start = ArcsSet.this.nextInternalNode(start);
/*     */       }
/*     */       
/* 631 */       if (start == null) {
/*     */         
/* 633 */         this.current = null;
/* 634 */         this.pending = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 639 */       BSPTree<Sphere1D> end = start;
/* 640 */       while (end != null && !ArcsSet.this.isArcEnd(end)) {
/* 641 */         end = ArcsSet.this.nextInternalNode(end);
/*     */       }
/*     */       
/* 644 */       if (end != null) {
/*     */ 
/*     */         
/* 647 */         this.pending = new double[] { ArcsSet.access$500(this.this$0, start), ArcsSet.access$500(this.this$0, end) };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 652 */         this.current = end;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 657 */         end = this.firstStart;
/* 658 */         while (end != null && !ArcsSet.this.isArcEnd(end)) {
/* 659 */           end = ArcsSet.this.previousInternalNode(end);
/*     */         }
/* 661 */         if (end == null)
/*     */         {
/* 663 */           throw new MathInternalError();
/*     */         }
/*     */ 
/*     */         
/* 667 */         this.pending = new double[] { ArcsSet.access$500(this.this$0, start), ArcsSet.access$500(this.this$0, end) + 6.283185307179586D };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 672 */         this.current = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 680 */       return (this.pending != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public double[] next() {
/* 685 */       if (this.pending == null) {
/* 686 */         throw new NoSuchElementException();
/*     */       }
/* 688 */       double[] next = this.pending;
/* 689 */       selectPending();
/* 690 */       return next;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 695 */       throw new UnsupportedOperationException();
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
/*     */   @Deprecated
/*     */   public Side side(Arc arc) {
/* 712 */     return split(arc).getSide();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Split split(Arc arc) {
/* 723 */     List<Double> minus = new ArrayList<Double>();
/* 724 */     List<Double> plus = new ArrayList<Double>();
/*     */     
/* 726 */     double reference = Math.PI + arc.getInf();
/* 727 */     double arcLength = arc.getSup() - arc.getInf();
/*     */     
/* 729 */     for (double[] a : this) {
/* 730 */       double syncedStart = MathUtils.normalizeAngle(a[0], reference) - arc.getInf();
/* 731 */       double arcOffset = a[0] - syncedStart;
/* 732 */       double syncedEnd = a[1] - arcOffset;
/* 733 */       if (syncedStart < arcLength) {
/*     */         
/* 735 */         minus.add(Double.valueOf(a[0]));
/* 736 */         if (syncedEnd > arcLength) {
/*     */ 
/*     */           
/* 739 */           double minusToPlus = arcLength + arcOffset;
/* 740 */           minus.add(Double.valueOf(minusToPlus));
/* 741 */           plus.add(Double.valueOf(minusToPlus));
/* 742 */           if (syncedEnd > 6.283185307179586D) {
/*     */ 
/*     */             
/* 745 */             double plusToMinus = 6.283185307179586D + arcOffset;
/* 746 */             plus.add(Double.valueOf(plusToMinus));
/* 747 */             minus.add(Double.valueOf(plusToMinus));
/* 748 */             minus.add(Double.valueOf(a[1]));
/*     */             continue;
/*     */           } 
/* 751 */           plus.add(Double.valueOf(a[1]));
/*     */           
/*     */           continue;
/*     */         } 
/* 755 */         minus.add(Double.valueOf(a[1]));
/*     */         
/*     */         continue;
/*     */       } 
/* 759 */       plus.add(Double.valueOf(a[0]));
/* 760 */       if (syncedEnd > 6.283185307179586D) {
/*     */ 
/*     */         
/* 763 */         double plusToMinus = 6.283185307179586D + arcOffset;
/* 764 */         plus.add(Double.valueOf(plusToMinus));
/* 765 */         minus.add(Double.valueOf(plusToMinus));
/* 766 */         if (syncedEnd > 6.283185307179586D + arcLength) {
/*     */ 
/*     */           
/* 769 */           double minusToPlus = 6.283185307179586D + arcLength + arcOffset;
/* 770 */           minus.add(Double.valueOf(minusToPlus));
/* 771 */           plus.add(Double.valueOf(minusToPlus));
/* 772 */           plus.add(Double.valueOf(a[1]));
/*     */           continue;
/*     */         } 
/* 775 */         minus.add(Double.valueOf(a[1]));
/*     */         
/*     */         continue;
/*     */       } 
/* 779 */       plus.add(Double.valueOf(a[1]));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 784 */     return new Split(createSplitPart(plus), createSplitPart(minus));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addArcLimit(BSPTree<Sphere1D> tree, double alpha, boolean isStart) {
/* 795 */     LimitAngle limit = new LimitAngle(new S1Point(alpha), !isStart, getTolerance());
/* 796 */     BSPTree<Sphere1D> node = tree.getCell(limit.getLocation(), getTolerance());
/* 797 */     if (node.getCut() != null)
/*     */     {
/* 799 */       throw new MathInternalError();
/*     */     }
/*     */     
/* 802 */     node.insertCut(limit);
/* 803 */     node.setAttribute(null);
/* 804 */     node.getPlus().setAttribute(Boolean.FALSE);
/* 805 */     node.getMinus().setAttribute(Boolean.TRUE);
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
/*     */   private ArcsSet createSplitPart(List<Double> limits) {
/* 819 */     if (limits.isEmpty()) {
/* 820 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 824 */     for (int i = 0; i < limits.size(); i++) {
/* 825 */       int k = (i + 1) % limits.size();
/* 826 */       double lA = ((Double)limits.get(i)).doubleValue();
/* 827 */       double lB = MathUtils.normalizeAngle(((Double)limits.get(k)).doubleValue(), lA);
/* 828 */       if (FastMath.abs(lB - lA) <= getTolerance())
/*     */       {
/* 830 */         if (k > 0) {
/*     */           
/* 832 */           limits.remove(k);
/* 833 */           limits.remove(i);
/* 834 */           i--;
/*     */         }
/*     */         else {
/*     */           
/* 838 */           double lEnd = ((Double)limits.remove(limits.size() - 1)).doubleValue();
/* 839 */           double lStart = ((Double)limits.remove(0)).doubleValue();
/* 840 */           if (limits.isEmpty()) {
/*     */             
/* 842 */             if (lEnd - lStart > Math.PI)
/*     */             {
/* 844 */               return new ArcsSet(new BSPTree(Boolean.TRUE), getTolerance());
/*     */             }
/*     */             
/* 847 */             return null;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 853 */           limits.add(Double.valueOf(((Double)limits.remove(0)).doubleValue() + 6.283185307179586D));
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 860 */     BSPTree<Sphere1D> tree = new BSPTree(Boolean.FALSE);
/* 861 */     for (int j = 0; j < limits.size() - 1; j += 2) {
/* 862 */       addArcLimit(tree, ((Double)limits.get(j)).doubleValue(), true);
/* 863 */       addArcLimit(tree, ((Double)limits.get(j + 1)).doubleValue(), false);
/*     */     } 
/*     */     
/* 866 */     if (tree.getCut() == null)
/*     */     {
/* 868 */       return null;
/*     */     }
/*     */     
/* 871 */     return new ArcsSet(tree, getTolerance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Split
/*     */   {
/*     */     private final ArcsSet plus;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ArcsSet minus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Split(ArcsSet plus, ArcsSet minus) {
/* 893 */       this.plus = plus;
/* 894 */       this.minus = minus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArcsSet getPlus() {
/* 901 */       return this.plus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArcsSet getMinus() {
/* 908 */       return this.minus;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Side getSide() {
/* 920 */       if (this.plus != null) {
/* 921 */         if (this.minus != null) {
/* 922 */           return Side.BOTH;
/*     */         }
/* 924 */         return Side.PLUS;
/*     */       } 
/* 926 */       if (this.minus != null) {
/* 927 */         return Side.MINUS;
/*     */       }
/* 929 */       return Side.HYPER;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class InconsistentStateAt2PiWrapping
/*     */     extends MathIllegalArgumentException
/*     */   {
/*     */     private static final long serialVersionUID = 20140107L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InconsistentStateAt2PiWrapping() {
/* 950 */       super((Localizable)LocalizedFormats.INCONSISTENT_STATE_AT_2_PI_WRAPPING, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/ArcsSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
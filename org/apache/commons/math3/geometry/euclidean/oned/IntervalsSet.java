/*     */ package org.apache.commons.math3.geometry.euclidean.oned;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryProjection;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
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
/*     */ public class IntervalsSet
/*     */   extends AbstractRegion<Euclidean1D, Euclidean1D>
/*     */   implements Iterable<double[]>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   
/*     */   public IntervalsSet(double tolerance) {
/*  45 */     super(tolerance);
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
/*     */   public IntervalsSet(double lower, double upper, double tolerance) {
/*  57 */     super(buildTree(lower, upper, tolerance), tolerance);
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
/*     */   public IntervalsSet(BSPTree<Euclidean1D> tree, double tolerance) {
/*  72 */     super(tree, tolerance);
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
/*     */   public IntervalsSet(Collection<SubHyperplane<Euclidean1D>> boundary, double tolerance) {
/*  98 */     super(boundary, tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IntervalsSet() {
/* 106 */     this(1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public IntervalsSet(double lower, double upper) {
/* 118 */     this(lower, upper, 1.0E-10D);
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
/*     */   @Deprecated
/*     */   public IntervalsSet(BSPTree<Euclidean1D> tree) {
/* 133 */     this(tree, 1.0E-10D);
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
/*     */   @Deprecated
/*     */   public IntervalsSet(Collection<SubHyperplane<Euclidean1D>> boundary) {
/* 158 */     this(boundary, 1.0E-10D);
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
/*     */   private static BSPTree<Euclidean1D> buildTree(double lower, double upper, double tolerance) {
/* 171 */     if (Double.isInfinite(lower) && lower < 0.0D) {
/* 172 */       if (Double.isInfinite(upper) && upper > 0.0D)
/*     */       {
/* 174 */         return new BSPTree(Boolean.TRUE);
/*     */       }
/*     */       
/* 177 */       SubOrientedPoint subOrientedPoint = (new OrientedPoint(new Vector1D(upper), true, tolerance)).wholeHyperplane();
/*     */       
/* 179 */       return new BSPTree((SubHyperplane)subOrientedPoint, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 184 */     SubOrientedPoint subOrientedPoint1 = (new OrientedPoint(new Vector1D(lower), false, tolerance)).wholeHyperplane();
/*     */     
/* 186 */     if (Double.isInfinite(upper) && upper > 0.0D)
/*     */     {
/* 188 */       return new BSPTree((SubHyperplane)subOrientedPoint1, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     SubOrientedPoint subOrientedPoint2 = (new OrientedPoint(new Vector1D(upper), true, tolerance)).wholeHyperplane();
/*     */     
/* 197 */     return new BSPTree((SubHyperplane)subOrientedPoint1, new BSPTree(Boolean.FALSE), new BSPTree((SubHyperplane)subOrientedPoint2, new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), null);
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
/*     */   public IntervalsSet buildNew(BSPTree<Euclidean1D> tree) {
/* 210 */     return new IntervalsSet(tree, getTolerance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeGeometricalProperties() {
/* 216 */     if (getTree(false).getCut() == null) {
/* 217 */       setBarycenter((Point)Vector1D.NaN);
/* 218 */       setSize(((Boolean)getTree(false).getAttribute()).booleanValue() ? Double.POSITIVE_INFINITY : 0.0D);
/*     */     } else {
/* 220 */       double size = 0.0D;
/* 221 */       double sum = 0.0D;
/* 222 */       for (Interval interval : asList()) {
/* 223 */         size += interval.getSize();
/* 224 */         sum += interval.getSize() * interval.getBarycenter();
/*     */       } 
/* 226 */       setSize(size);
/* 227 */       if (Double.isInfinite(size)) {
/* 228 */         setBarycenter((Point)Vector1D.NaN);
/* 229 */       } else if (size >= Precision.SAFE_MIN) {
/* 230 */         setBarycenter((Point)new Vector1D(sum / size));
/*     */       } else {
/* 232 */         setBarycenter((Point)((OrientedPoint)getTree(false).getCut().getHyperplane()).getLocation());
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
/*     */   public double getInf() {
/* 244 */     BSPTree<Euclidean1D> node = getTree(false);
/* 245 */     double inf = Double.POSITIVE_INFINITY;
/* 246 */     while (node.getCut() != null) {
/* 247 */       OrientedPoint op = (OrientedPoint)node.getCut().getHyperplane();
/* 248 */       inf = op.getLocation().getX();
/* 249 */       node = op.isDirect() ? node.getMinus() : node.getPlus();
/*     */     } 
/* 251 */     return ((Boolean)node.getAttribute()).booleanValue() ? Double.NEGATIVE_INFINITY : inf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSup() {
/* 261 */     BSPTree<Euclidean1D> node = getTree(false);
/* 262 */     double sup = Double.NEGATIVE_INFINITY;
/* 263 */     while (node.getCut() != null) {
/* 264 */       OrientedPoint op = (OrientedPoint)node.getCut().getHyperplane();
/* 265 */       sup = op.getLocation().getX();
/* 266 */       node = op.isDirect() ? node.getPlus() : node.getMinus();
/*     */     } 
/* 268 */     return ((Boolean)node.getAttribute()).booleanValue() ? Double.POSITIVE_INFINITY : sup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundaryProjection<Euclidean1D> projectToBoundary(Point<Euclidean1D> point) {
/* 278 */     double x = ((Vector1D)point).getX();
/*     */     
/* 280 */     double previous = Double.NEGATIVE_INFINITY;
/* 281 */     for (double[] a : this) {
/* 282 */       if (x < a[0]) {
/*     */ 
/*     */         
/* 285 */         double previousOffset = x - previous;
/* 286 */         double currentOffset = a[0] - x;
/* 287 */         if (previousOffset < currentOffset) {
/* 288 */           return new BoundaryProjection(point, (Point)finiteOrNullPoint(previous), previousOffset);
/*     */         }
/* 290 */         return new BoundaryProjection(point, (Point)finiteOrNullPoint(a[0]), currentOffset);
/*     */       } 
/* 292 */       if (x <= a[1]) {
/*     */ 
/*     */         
/* 295 */         double offset0 = a[0] - x;
/* 296 */         double offset1 = x - a[1];
/* 297 */         if (offset0 < offset1) {
/* 298 */           return new BoundaryProjection(point, (Point)finiteOrNullPoint(a[1]), offset1);
/*     */         }
/* 300 */         return new BoundaryProjection(point, (Point)finiteOrNullPoint(a[0]), offset0);
/*     */       } 
/*     */       
/* 303 */       previous = a[1];
/*     */     } 
/*     */ 
/*     */     
/* 307 */     return new BoundaryProjection(point, (Point)finiteOrNullPoint(previous), x - previous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector1D finiteOrNullPoint(double x) {
/* 316 */     return Double.isInfinite(x) ? null : new Vector1D(x);
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
/*     */   public List<Interval> asList() {
/* 333 */     List<Interval> list = new ArrayList<Interval>();
/* 334 */     for (double[] a : this) {
/* 335 */       list.add(new Interval(a[0], a[1]));
/*     */     }
/* 337 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> getFirstLeaf(BSPTree<Euclidean1D> root) {
/* 346 */     if (root.getCut() == null) {
/* 347 */       return root;
/*     */     }
/*     */ 
/*     */     
/* 351 */     BSPTree<Euclidean1D> smallest = null;
/* 352 */     for (BSPTree<Euclidean1D> n = root; n != null; n = previousInternalNode(n)) {
/* 353 */       smallest = n;
/*     */     }
/*     */     
/* 356 */     return leafBefore(smallest);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> getFirstIntervalBoundary() {
/* 367 */     BSPTree<Euclidean1D> node = getTree(false);
/* 368 */     if (node.getCut() == null) {
/* 369 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 373 */     node = getFirstLeaf(node).getParent();
/*     */ 
/*     */     
/* 376 */     while (node != null && !isIntervalStart(node) && !isIntervalEnd(node)) {
/* 377 */       node = nextInternalNode(node);
/*     */     }
/*     */     
/* 380 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isIntervalStart(BSPTree<Euclidean1D> node) {
/* 390 */     if (((Boolean)leafBefore(node).getAttribute()).booleanValue())
/*     */     {
/* 392 */       return false;
/*     */     }
/*     */     
/* 395 */     if (!((Boolean)leafAfter(node).getAttribute()).booleanValue())
/*     */     {
/* 397 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 402 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isIntervalEnd(BSPTree<Euclidean1D> node) {
/* 412 */     if (!((Boolean)leafBefore(node).getAttribute()).booleanValue())
/*     */     {
/* 414 */       return false;
/*     */     }
/*     */     
/* 417 */     if (((Boolean)leafAfter(node).getAttribute()).booleanValue())
/*     */     {
/* 419 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 424 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> nextInternalNode(BSPTree<Euclidean1D> node) {
/* 435 */     if (childAfter(node).getCut() != null)
/*     */     {
/* 437 */       return leafAfter(node).getParent();
/*     */     }
/*     */ 
/*     */     
/* 441 */     while (isAfterParent(node)) {
/* 442 */       node = node.getParent();
/*     */     }
/* 444 */     return node.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> previousInternalNode(BSPTree<Euclidean1D> node) {
/* 455 */     if (childBefore(node).getCut() != null)
/*     */     {
/* 457 */       return leafBefore(node).getParent();
/*     */     }
/*     */ 
/*     */     
/* 461 */     while (isBeforeParent(node)) {
/* 462 */       node = node.getParent();
/*     */     }
/* 464 */     return node.getParent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> leafBefore(BSPTree<Euclidean1D> node) {
/* 474 */     node = childBefore(node);
/* 475 */     while (node.getCut() != null) {
/* 476 */       node = childAfter(node);
/*     */     }
/*     */     
/* 479 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> leafAfter(BSPTree<Euclidean1D> node) {
/* 489 */     node = childAfter(node);
/* 490 */     while (node.getCut() != null) {
/* 491 */       node = childBefore(node);
/*     */     }
/*     */     
/* 494 */     return node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBeforeParent(BSPTree<Euclidean1D> node) {
/* 503 */     BSPTree<Euclidean1D> parent = node.getParent();
/* 504 */     if (parent == null) {
/* 505 */       return false;
/*     */     }
/* 507 */     return (node == childBefore(parent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAfterParent(BSPTree<Euclidean1D> node) {
/* 516 */     BSPTree<Euclidean1D> parent = node.getParent();
/* 517 */     if (parent == null) {
/* 518 */       return false;
/*     */     }
/* 520 */     return (node == childAfter(parent));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> childBefore(BSPTree<Euclidean1D> node) {
/* 529 */     if (isDirect(node))
/*     */     {
/* 531 */       return node.getMinus();
/*     */     }
/*     */     
/* 534 */     return node.getPlus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BSPTree<Euclidean1D> childAfter(BSPTree<Euclidean1D> node) {
/* 543 */     if (isDirect(node))
/*     */     {
/* 545 */       return node.getPlus();
/*     */     }
/*     */     
/* 548 */     return node.getMinus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDirect(BSPTree<Euclidean1D> node) {
/* 557 */     return ((OrientedPoint)node.getCut().getHyperplane()).isDirect();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double getAngle(BSPTree<Euclidean1D> node) {
/* 565 */     return ((OrientedPoint)node.getCut().getHyperplane()).getLocation().getX();
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
/*     */   public Iterator<double[]> iterator() {
/* 578 */     return new SubIntervalsIterator();
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
/*     */   private class SubIntervalsIterator
/*     */     implements Iterator<double[]>
/*     */   {
/* 594 */     private BSPTree<Euclidean1D> current = IntervalsSet.this.getFirstIntervalBoundary();
/*     */     SubIntervalsIterator() {
/* 596 */       if (this.current == null) {
/*     */         
/* 598 */         if (((Boolean)IntervalsSet.this.getFirstLeaf(IntervalsSet.this.getTree(false)).getAttribute()).booleanValue()) {
/*     */           
/* 600 */           this.pending = new double[] { Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY };
/*     */         }
/*     */         else {
/*     */           
/* 604 */           this.pending = null;
/*     */         } 
/* 606 */       } else if (IntervalsSet.this.isIntervalEnd(this.current)) {
/*     */ 
/*     */         
/* 609 */         this.pending = new double[] { Double.NEGATIVE_INFINITY, IntervalsSet.access$300(this$0, this.current) };
/*     */       }
/*     */       else {
/*     */         
/* 613 */         selectPending();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private double[] pending;
/*     */ 
/*     */     
/*     */     private void selectPending() {
/* 622 */       BSPTree<Euclidean1D> start = this.current;
/* 623 */       while (start != null && !IntervalsSet.this.isIntervalStart(start)) {
/* 624 */         start = IntervalsSet.this.nextInternalNode(start);
/*     */       }
/*     */       
/* 627 */       if (start == null) {
/*     */         
/* 629 */         this.current = null;
/* 630 */         this.pending = null;
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 635 */       BSPTree<Euclidean1D> end = start;
/* 636 */       while (end != null && !IntervalsSet.this.isIntervalEnd(end)) {
/* 637 */         end = IntervalsSet.this.nextInternalNode(end);
/*     */       }
/*     */       
/* 640 */       if (end != null) {
/*     */ 
/*     */         
/* 643 */         this.pending = new double[] { IntervalsSet.access$300(this.this$0, start), IntervalsSet.access$300(this.this$0, end) };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 648 */         this.current = end;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 653 */         this.pending = new double[] { IntervalsSet.access$300(this.this$0, start), Double.POSITIVE_INFINITY };
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 658 */         this.current = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 666 */       return (this.pending != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public double[] next() {
/* 671 */       if (this.pending == null) {
/* 672 */         throw new NoSuchElementException();
/*     */       }
/* 674 */       double[] next = this.pending;
/* 675 */       selectPending();
/* 676 */       return next;
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 681 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/IntervalsSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
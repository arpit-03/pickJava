/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Interval;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.OrientedPoint;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.SubOrientedPoint;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
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
/*     */ public class SubLine
/*     */   extends AbstractSubHyperplane<Euclidean2D, Euclidean1D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   
/*     */   public SubLine(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
/*  50 */     super(hyperplane, remainingRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubLine(Vector2D start, Vector2D end, double tolerance) {
/*  60 */     super(new Line(start, end, tolerance), (Region)buildIntervalSet(start, end, tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public SubLine(Vector2D start, Vector2D end) {
/*  70 */     this(start, end, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubLine(Segment segment) {
/*  77 */     super(segment.getLine(), (Region)buildIntervalSet(segment.getStart(), segment.getEnd(), segment.getLine().getTolerance()));
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
/*     */   public List<Segment> getSegments() {
/*  97 */     Line line = (Line)getHyperplane();
/*  98 */     List<Interval> list = ((IntervalsSet)getRemainingRegion()).asList();
/*  99 */     List<Segment> segments = new ArrayList<Segment>(list.size());
/*     */     
/* 101 */     for (Interval interval : list) {
/* 102 */       Vector2D start = line.toSpace((Point<Euclidean1D>)new Vector1D(interval.getInf()));
/* 103 */       Vector2D end = line.toSpace((Point<Euclidean1D>)new Vector1D(interval.getSup()));
/* 104 */       segments.add(new Segment(start, end, line));
/*     */     } 
/*     */     
/* 107 */     return segments;
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
/*     */   public Vector2D intersection(SubLine subLine, boolean includeEndPoints) {
/* 128 */     Line line1 = (Line)getHyperplane();
/* 129 */     Line line2 = (Line)subLine.getHyperplane();
/*     */ 
/*     */     
/* 132 */     Vector2D v2D = line1.intersection(line2);
/* 133 */     if (v2D == null) {
/* 134 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 138 */     Region.Location loc1 = getRemainingRegion().checkPoint((Point)line1.toSubSpace((Point<Euclidean2D>)v2D));
/*     */ 
/*     */     
/* 141 */     Region.Location loc2 = subLine.getRemainingRegion().checkPoint((Point)line2.toSubSpace((Point<Euclidean2D>)v2D));
/*     */     
/* 143 */     if (includeEndPoints) {
/* 144 */       return (loc1 != Region.Location.OUTSIDE && loc2 != Region.Location.OUTSIDE) ? v2D : null;
/*     */     }
/* 146 */     return (loc1 == Region.Location.INSIDE && loc2 == Region.Location.INSIDE) ? v2D : null;
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
/*     */   private static IntervalsSet buildIntervalSet(Vector2D start, Vector2D end, double tolerance) {
/* 158 */     Line line = new Line(start, end, tolerance);
/* 159 */     return new IntervalsSet(line.toSubSpace((Point<Euclidean2D>)start).getX(), line.toSubSpace((Point<Euclidean2D>)end).getX(), tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSubHyperplane<Euclidean2D, Euclidean1D> buildNew(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
/* 168 */     return new SubLine(hyperplane, remainingRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubHyperplane.SplitSubHyperplane<Euclidean2D> split(Hyperplane<Euclidean2D> hyperplane) {
/* 175 */     Line thisLine = (Line)getHyperplane();
/* 176 */     Line otherLine = (Line)hyperplane;
/* 177 */     Vector2D crossing = thisLine.intersection(otherLine);
/* 178 */     double tolerance = thisLine.getTolerance();
/*     */     
/* 180 */     if (crossing == null) {
/*     */       
/* 182 */       double global = otherLine.getOffset(thisLine);
/* 183 */       if (global < -tolerance)
/* 184 */         return new SubHyperplane.SplitSubHyperplane(null, (SubHyperplane)this); 
/* 185 */       if (global > tolerance) {
/* 186 */         return new SubHyperplane.SplitSubHyperplane((SubHyperplane)this, null);
/*     */       }
/* 188 */       return new SubHyperplane.SplitSubHyperplane(null, null);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 193 */     boolean direct = (FastMath.sin(thisLine.getAngle() - otherLine.getAngle()) < 0.0D);
/* 194 */     Vector1D x = thisLine.toSubSpace((Point<Euclidean2D>)crossing);
/* 195 */     SubOrientedPoint subOrientedPoint1 = (new OrientedPoint(x, !direct, tolerance)).wholeHyperplane();
/*     */     
/* 197 */     SubOrientedPoint subOrientedPoint2 = (new OrientedPoint(x, direct, tolerance)).wholeHyperplane();
/*     */ 
/*     */     
/* 200 */     BSPTree<Euclidean1D> splitTree = getRemainingRegion().getTree(false).split((SubHyperplane)subOrientedPoint2);
/* 201 */     BSPTree<Euclidean1D> plusTree = getRemainingRegion().isEmpty(splitTree.getPlus()) ? new BSPTree(Boolean.FALSE) : new BSPTree((SubHyperplane)subOrientedPoint1, new BSPTree(Boolean.FALSE), splitTree.getPlus(), null);
/*     */ 
/*     */ 
/*     */     
/* 205 */     BSPTree<Euclidean1D> minusTree = getRemainingRegion().isEmpty(splitTree.getMinus()) ? new BSPTree(Boolean.FALSE) : new BSPTree((SubHyperplane)subOrientedPoint2, new BSPTree(Boolean.FALSE), splitTree.getMinus(), null);
/*     */ 
/*     */ 
/*     */     
/* 209 */     return new SubHyperplane.SplitSubHyperplane((SubHyperplane)new SubLine(thisLine.copySelf(), (Region<Euclidean1D>)new IntervalsSet(plusTree, tolerance)), (SubHyperplane)new SubLine(thisLine.copySelf(), (Region<Euclidean1D>)new IntervalsSet(minusTree, tolerance)));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/SubLine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Interval;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private final Line line;
/*     */   private final IntervalsSet remainingRegion;
/*     */   
/*     */   public SubLine(Line line, IntervalsSet remainingRegion) {
/*  49 */     this.line = line;
/*  50 */     this.remainingRegion = remainingRegion;
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
/*     */   public SubLine(Vector3D start, Vector3D end, double tolerance) throws MathIllegalArgumentException {
/*  62 */     this(new Line(start, end, tolerance), buildIntervalSet(start, end, tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubLine(Vector3D start, Vector3D end) throws MathIllegalArgumentException {
/*  73 */     this(start, end, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubLine(Segment segment) throws MathIllegalArgumentException {
/*  81 */     this(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd(), segment.getLine().getTolerance()));
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
/* 101 */     List<Interval> list = this.remainingRegion.asList();
/* 102 */     List<Segment> segments = new ArrayList<Segment>(list.size());
/*     */     
/* 104 */     for (Interval interval : list) {
/* 105 */       Vector3D start = this.line.toSpace((Point<Euclidean1D>)new Vector1D(interval.getInf()));
/* 106 */       Vector3D end = this.line.toSpace((Point<Euclidean1D>)new Vector1D(interval.getSup()));
/* 107 */       segments.add(new Segment(start, end, this.line));
/*     */     } 
/*     */     
/* 110 */     return segments;
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
/*     */   public Vector3D intersection(SubLine subLine, boolean includeEndPoints) {
/* 131 */     Vector3D v1D = this.line.intersection(subLine.line);
/* 132 */     if (v1D == null) {
/* 133 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 137 */     Region.Location loc1 = this.remainingRegion.checkPoint((Point)this.line.toSubSpace((Point<Euclidean3D>)v1D));
/*     */ 
/*     */     
/* 140 */     Region.Location loc2 = subLine.remainingRegion.checkPoint((Point)subLine.line.toSubSpace((Point<Euclidean3D>)v1D));
/*     */     
/* 142 */     if (includeEndPoints) {
/* 143 */       return (loc1 != Region.Location.OUTSIDE && loc2 != Region.Location.OUTSIDE) ? v1D : null;
/*     */     }
/* 145 */     return (loc1 == Region.Location.INSIDE && loc2 == Region.Location.INSIDE) ? v1D : null;
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
/*     */   private static IntervalsSet buildIntervalSet(Vector3D start, Vector3D end, double tolerance) throws MathIllegalArgumentException {
/* 159 */     Line line = new Line(start, end, tolerance);
/* 160 */     return new IntervalsSet(line.toSubSpace((Point<Euclidean3D>)start).getX(), line.toSubSpace((Point<Euclidean3D>)end).getX(), tolerance);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/SubLine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
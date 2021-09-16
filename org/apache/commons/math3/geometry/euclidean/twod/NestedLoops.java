/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.RegionFactory;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class NestedLoops
/*     */ {
/*     */   private Vector2D[] loop;
/*     */   private List<NestedLoops> surrounded;
/*     */   private Region<Euclidean2D> polygon;
/*     */   private boolean originalIsClockwise;
/*     */   private final double tolerance;
/*     */   
/*     */   NestedLoops(double tolerance) {
/*  74 */     this.surrounded = new ArrayList<NestedLoops>();
/*  75 */     this.tolerance = tolerance;
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
/*     */   private NestedLoops(Vector2D[] loop, double tolerance) throws MathIllegalArgumentException {
/*  88 */     if (loop[0] == null) {
/*  89 */       throw new MathIllegalArgumentException(LocalizedFormats.OUTLINE_BOUNDARY_LOOP_OPEN, new Object[0]);
/*     */     }
/*     */     
/*  92 */     this.loop = loop;
/*  93 */     this.surrounded = new ArrayList<NestedLoops>();
/*  94 */     this.tolerance = tolerance;
/*     */ 
/*     */     
/*  97 */     ArrayList<SubHyperplane<Euclidean2D>> edges = new ArrayList<SubHyperplane<Euclidean2D>>();
/*  98 */     Vector2D current = loop[loop.length - 1];
/*  99 */     for (int i = 0; i < loop.length; i++) {
/* 100 */       Vector2D previous = current;
/* 101 */       current = loop[i];
/* 102 */       Line line = new Line(previous, current, tolerance);
/* 103 */       IntervalsSet region = new IntervalsSet(line.toSubSpace((Point<Euclidean2D>)previous).getX(), line.toSubSpace((Point<Euclidean2D>)current).getX(), tolerance);
/*     */ 
/*     */ 
/*     */       
/* 107 */       edges.add(new SubLine(line, (Region<Euclidean1D>)region));
/*     */     } 
/* 109 */     this.polygon = (Region<Euclidean2D>)new PolygonsSet(edges, tolerance);
/*     */ 
/*     */     
/* 112 */     if (Double.isInfinite(this.polygon.getSize())) {
/* 113 */       this.polygon = (new RegionFactory()).getComplement(this.polygon);
/* 114 */       this.originalIsClockwise = false;
/*     */     } else {
/* 116 */       this.originalIsClockwise = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(Vector2D[] bLoop) throws MathIllegalArgumentException {
/* 127 */     add(new NestedLoops(bLoop, this.tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void add(NestedLoops node) throws MathIllegalArgumentException {
/* 138 */     for (NestedLoops child : this.surrounded) {
/* 139 */       if (child.polygon.contains(node.polygon)) {
/* 140 */         child.add(node);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     for (Iterator<NestedLoops> iterator = this.surrounded.iterator(); iterator.hasNext(); ) {
/* 147 */       NestedLoops child = iterator.next();
/* 148 */       if (node.polygon.contains(child.polygon)) {
/* 149 */         node.surrounded.add(child);
/* 150 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 155 */     RegionFactory<Euclidean2D> factory = new RegionFactory();
/* 156 */     for (NestedLoops child : this.surrounded) {
/* 157 */       if (!factory.intersection(node.polygon, child.polygon).isEmpty()) {
/* 158 */         throw new MathIllegalArgumentException(LocalizedFormats.CROSSING_BOUNDARY_LOOPS, new Object[0]);
/*     */       }
/*     */     } 
/*     */     
/* 162 */     this.surrounded.add(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void correctOrientation() {
/* 172 */     for (NestedLoops child : this.surrounded) {
/* 173 */       child.setClockWise(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setClockWise(boolean clockwise) {
/* 183 */     if (this.originalIsClockwise ^ clockwise) {
/*     */       
/* 185 */       int min = -1;
/* 186 */       int max = this.loop.length;
/* 187 */       while (++min < --max) {
/* 188 */         Vector2D tmp = this.loop[min];
/* 189 */         this.loop[min] = this.loop[max];
/* 190 */         this.loop[max] = tmp;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 195 */     for (NestedLoops child : this.surrounded)
/* 196 */       child.setClockWise(!clockwise); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/NestedLoops.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
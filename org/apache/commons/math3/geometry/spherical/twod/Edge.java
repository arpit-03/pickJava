/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Arc;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Edge
/*     */ {
/*     */   private final Vertex start;
/*     */   private Vertex end;
/*     */   private final double length;
/*     */   private final Circle circle;
/*     */   
/*     */   Edge(Vertex start, Vertex end, double length, Circle circle) {
/*  53 */     this.start = start;
/*  54 */     this.end = end;
/*  55 */     this.length = length;
/*  56 */     this.circle = circle;
/*     */ 
/*     */     
/*  59 */     start.setOutgoing(this);
/*  60 */     end.setIncoming(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vertex getStart() {
/*  68 */     return this.start;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vertex getEnd() {
/*  75 */     return this.end;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLength() {
/*  82 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Circle getCircle() {
/*  89 */     return this.circle;
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
/*     */   public Vector3D getPointAt(double alpha) {
/* 103 */     return this.circle.getPointAt(alpha + this.circle.getPhase(this.start.getLocation().getVector()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNextEdge(Edge next) {
/* 110 */     this.end = next.getStart();
/* 111 */     this.end.setIncoming(this);
/* 112 */     this.end.bindWith(getCircle());
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
/*     */   void split(Circle splitCircle, List<Edge> outsideList, List<Edge> insideList) {
/* 129 */     double edgeStart = this.circle.getPhase(this.start.getLocation().getVector());
/* 130 */     Arc arc = this.circle.getInsideArc(splitCircle);
/* 131 */     double arcRelativeStart = MathUtils.normalizeAngle(arc.getInf(), edgeStart + Math.PI) - edgeStart;
/* 132 */     double arcRelativeEnd = arcRelativeStart + arc.getSize();
/* 133 */     double unwrappedEnd = arcRelativeEnd - 6.283185307179586D;
/*     */ 
/*     */     
/* 136 */     double tolerance = this.circle.getTolerance();
/* 137 */     Vertex previousVertex = this.start;
/* 138 */     if (unwrappedEnd >= this.length - tolerance) {
/*     */ 
/*     */ 
/*     */       
/* 142 */       insideList.add(this);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 148 */       double alreadyManagedLength = 0.0D;
/* 149 */       if (unwrappedEnd >= 0.0D) {
/*     */         
/* 151 */         previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + unwrappedEnd))), unwrappedEnd, insideList, splitCircle);
/*     */ 
/*     */         
/* 154 */         alreadyManagedLength = unwrappedEnd;
/*     */       } 
/*     */       
/* 157 */       if (arcRelativeStart >= this.length - tolerance) {
/*     */         
/* 159 */         if (unwrappedEnd >= 0.0D) {
/* 160 */           previousVertex = addSubEdge(previousVertex, this.end, this.length - alreadyManagedLength, outsideList, splitCircle);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 165 */           outsideList.add(this);
/*     */         } 
/*     */       } else {
/*     */         
/* 169 */         previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + arcRelativeStart))), arcRelativeStart - alreadyManagedLength, outsideList, splitCircle);
/*     */ 
/*     */         
/* 172 */         alreadyManagedLength = arcRelativeStart;
/*     */         
/* 174 */         if (arcRelativeEnd >= this.length - tolerance) {
/*     */           
/* 176 */           previousVertex = addSubEdge(previousVertex, this.end, this.length - alreadyManagedLength, insideList, splitCircle);
/*     */         }
/*     */         else {
/*     */           
/* 180 */           previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + arcRelativeStart))), arcRelativeStart - alreadyManagedLength, insideList, splitCircle);
/*     */ 
/*     */           
/* 183 */           alreadyManagedLength = arcRelativeStart;
/* 184 */           previousVertex = addSubEdge(previousVertex, this.end, this.length - alreadyManagedLength, outsideList, splitCircle);
/*     */         } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vertex addSubEdge(Vertex subStart, Vertex subEnd, double subLength, List<Edge> list, Circle splitCircle) {
/* 209 */     if (subLength <= this.circle.getTolerance())
/*     */     {
/* 211 */       return subStart;
/*     */     }
/*     */ 
/*     */     
/* 215 */     subEnd.bindWith(splitCircle);
/* 216 */     Edge edge = new Edge(subStart, subEnd, subLength, this.circle);
/* 217 */     list.add(edge);
/* 218 */     return subEnd;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/Edge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vertex
/*     */ {
/*     */   private final S2Point location;
/*     */   private Edge incoming;
/*     */   private Edge outgoing;
/*     */   private final List<Circle> circles;
/*     */   
/*     */   Vertex(S2Point location) {
/*  45 */     this.location = location;
/*  46 */     this.incoming = null;
/*  47 */     this.outgoing = null;
/*  48 */     this.circles = new ArrayList<Circle>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point getLocation() {
/*  55 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void bindWith(Circle circle) {
/*  62 */     this.circles.add(circle);
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
/*     */   Circle sharedCircleWith(Vertex vertex) {
/*  76 */     for (Circle circle1 : this.circles) {
/*  77 */       for (Circle circle2 : vertex.circles) {
/*  78 */         if (circle1 == circle2) {
/*  79 */           return circle1;
/*     */         }
/*     */       } 
/*     */     } 
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIncoming(Edge incoming) {
/*  94 */     this.incoming = incoming;
/*  95 */     bindWith(incoming.getCircle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Edge getIncoming() {
/* 102 */     return this.incoming;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setOutgoing(Edge outgoing) {
/* 113 */     this.outgoing = outgoing;
/* 114 */     bindWith(outgoing.getCircle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Edge getOutgoing() {
/* 121 */     return this.outgoing;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/Vertex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
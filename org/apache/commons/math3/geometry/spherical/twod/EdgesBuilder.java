/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Arc;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.ArcsSet;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.S1Point;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Sphere1D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EdgesBuilder
/*     */   implements BSPTreeVisitor<Sphere2D>
/*     */ {
/*     */   private final BSPTree<Sphere2D> root;
/*     */   private final double tolerance;
/*     */   private final Map<Edge, BSPTree<Sphere2D>> edgeToNode;
/*     */   private final Map<BSPTree<Sphere2D>, List<Edge>> nodeToEdgesList;
/*     */   
/*     */   EdgesBuilder(BSPTree<Sphere2D> root, double tolerance) {
/*  56 */     this.root = root;
/*  57 */     this.tolerance = tolerance;
/*  58 */     this.edgeToNode = new IdentityHashMap<Edge, BSPTree<Sphere2D>>();
/*  59 */     this.nodeToEdgesList = new IdentityHashMap<BSPTree<Sphere2D>, List<Edge>>();
/*     */   }
/*     */ 
/*     */   
/*     */   public BSPTreeVisitor.Order visitOrder(BSPTree<Sphere2D> node) {
/*  64 */     return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInternalNode(BSPTree<Sphere2D> node) {
/*  69 */     this.nodeToEdgesList.put(node, new ArrayList<Edge>());
/*     */     
/*  71 */     BoundaryAttribute<Sphere2D> attribute = (BoundaryAttribute<Sphere2D>)node.getAttribute();
/*  72 */     if (attribute.getPlusOutside() != null) {
/*  73 */       addContribution((SubCircle)attribute.getPlusOutside(), false, node);
/*     */     }
/*  75 */     if (attribute.getPlusInside() != null) {
/*  76 */       addContribution((SubCircle)attribute.getPlusInside(), true, node);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLeafNode(BSPTree<Sphere2D> node) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addContribution(SubCircle sub, boolean reversed, BSPTree<Sphere2D> node) {
/*  91 */     Circle circle = (Circle)sub.getHyperplane();
/*  92 */     List<Arc> arcs = ((ArcsSet)sub.getRemainingRegion()).asList();
/*  93 */     for (Arc a : arcs) {
/*  94 */       Edge edge; Vertex start = new Vertex(circle.toSpace((Point<Sphere1D>)new S1Point(a.getInf())));
/*  95 */       Vertex end = new Vertex(circle.toSpace((Point<Sphere1D>)new S1Point(a.getSup())));
/*  96 */       start.bindWith(circle);
/*  97 */       end.bindWith(circle);
/*     */       
/*  99 */       if (reversed) {
/* 100 */         edge = new Edge(end, start, a.getSize(), circle.getReverse());
/*     */       } else {
/* 102 */         edge = new Edge(start, end, a.getSize(), circle);
/*     */       } 
/* 104 */       this.edgeToNode.put(edge, node);
/* 105 */       ((List<Edge>)this.nodeToEdgesList.get(node)).add(edge);
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
/*     */   private Edge getFollowingEdge(Edge previous) throws MathIllegalStateException {
/* 119 */     S2Point point = previous.getEnd().getLocation();
/* 120 */     List<BSPTree<Sphere2D>> candidates = this.root.getCloseCuts(point, this.tolerance);
/*     */ 
/*     */     
/* 123 */     double closest = this.tolerance;
/* 124 */     Edge following = null;
/* 125 */     for (BSPTree<Sphere2D> node : candidates) {
/* 126 */       for (Edge edge : this.nodeToEdgesList.get(node)) {
/* 127 */         if (edge != previous && edge.getStart().getIncoming() == null) {
/* 128 */           Vector3D edgeStart = edge.getStart().getLocation().getVector();
/* 129 */           double gap = Vector3D.angle(point.getVector(), edgeStart);
/* 130 */           if (gap <= closest) {
/* 131 */             closest = gap;
/* 132 */             following = edge;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     if (following == null) {
/* 139 */       Vector3D previousStart = previous.getStart().getLocation().getVector();
/* 140 */       if (Vector3D.angle(point.getVector(), previousStart) <= this.tolerance)
/*     */       {
/* 142 */         return previous;
/*     */       }
/*     */ 
/*     */       
/* 146 */       throw new MathIllegalStateException(LocalizedFormats.OUTLINE_BOUNDARY_LOOP_OPEN, new Object[0]);
/*     */     } 
/*     */ 
/*     */     
/* 150 */     return following;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Edge> getEdges() throws MathIllegalStateException {
/* 161 */     for (Edge previous : this.edgeToNode.keySet()) {
/* 162 */       previous.setNextEdge(getFollowingEdge(previous));
/*     */     }
/*     */     
/* 165 */     return new ArrayList<Edge>(this.edgeToNode.keySet());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/EdgesBuilder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
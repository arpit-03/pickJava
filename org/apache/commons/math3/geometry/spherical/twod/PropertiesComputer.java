/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
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
/*     */ class PropertiesComputer
/*     */   implements BSPTreeVisitor<Sphere2D>
/*     */ {
/*     */   private final double tolerance;
/*     */   private double summedArea;
/*     */   private Vector3D summedBarycenter;
/*     */   private final List<Vector3D> convexCellsInsidePoints;
/*     */   
/*     */   PropertiesComputer(double tolerance) {
/*  50 */     this.tolerance = tolerance;
/*  51 */     this.summedArea = 0.0D;
/*  52 */     this.summedBarycenter = Vector3D.ZERO;
/*  53 */     this.convexCellsInsidePoints = new ArrayList<Vector3D>();
/*     */   }
/*     */ 
/*     */   
/*     */   public BSPTreeVisitor.Order visitOrder(BSPTree<Sphere2D> node) {
/*  58 */     return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInternalNode(BSPTree<Sphere2D> node) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLeafNode(BSPTree<Sphere2D> node) {
/*  68 */     if (((Boolean)node.getAttribute()).booleanValue()) {
/*     */ 
/*     */       
/*  71 */       SphericalPolygonsSet convex = new SphericalPolygonsSet(node.pruneAroundConvexCell(Boolean.TRUE, Boolean.FALSE, null), this.tolerance);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  78 */       List<Vertex> boundary = convex.getBoundaryLoops();
/*  79 */       if (boundary.size() != 1)
/*     */       {
/*  81 */         throw new MathInternalError();
/*     */       }
/*     */ 
/*     */       
/*  85 */       double area = convexCellArea(boundary.get(0));
/*  86 */       Vector3D barycenter = convexCellBarycenter(boundary.get(0));
/*  87 */       this.convexCellsInsidePoints.add(barycenter);
/*     */ 
/*     */       
/*  90 */       this.summedArea += area;
/*  91 */       this.summedBarycenter = new Vector3D(1.0D, this.summedBarycenter, area, barycenter);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double convexCellArea(Vertex start) {
/* 102 */     int n = 0;
/* 103 */     double sum = 0.0D;
/*     */ 
/*     */     
/* 106 */     for (Edge e = start.getOutgoing(); n == 0 || e.getStart() != start; e = e.getEnd().getOutgoing()) {
/*     */ 
/*     */       
/* 109 */       Vector3D previousPole = e.getCircle().getPole();
/* 110 */       Vector3D nextPole = e.getEnd().getOutgoing().getCircle().getPole();
/* 111 */       Vector3D point = e.getEnd().getLocation().getVector();
/* 112 */       double alpha = FastMath.atan2(Vector3D.dotProduct(nextPole, Vector3D.crossProduct(point, previousPole)), -Vector3D.dotProduct(nextPole, previousPole));
/*     */       
/* 114 */       if (alpha < 0.0D) {
/* 115 */         alpha += 6.283185307179586D;
/*     */       }
/* 117 */       sum += alpha;
/* 118 */       n++;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     return sum - (n - 2) * Math.PI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3D convexCellBarycenter(Vertex start) {
/* 135 */     int n = 0;
/* 136 */     Vector3D sumB = Vector3D.ZERO;
/*     */ 
/*     */     
/* 139 */     for (Edge e = start.getOutgoing(); n == 0 || e.getStart() != start; e = e.getEnd().getOutgoing()) {
/* 140 */       sumB = new Vector3D(1.0D, sumB, e.getLength(), e.getCircle().getPole());
/* 141 */       n++;
/*     */     } 
/*     */     
/* 144 */     return sumB.normalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getArea() {
/* 152 */     return this.summedArea;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point getBarycenter() {
/* 159 */     if (this.summedBarycenter.getNormSq() == 0.0D) {
/* 160 */       return S2Point.NaN;
/*     */     }
/* 162 */     return new S2Point(this.summedBarycenter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Vector3D> getConvexCellsInsidePoints() {
/* 170 */     return this.convexCellsInsidePoints;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/PropertiesComputer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
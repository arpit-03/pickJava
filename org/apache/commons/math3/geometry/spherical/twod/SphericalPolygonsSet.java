/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.enclosing.EnclosingBall;
/*     */ import org.apache.commons.math3.geometry.enclosing.SupportBallGenerator;
/*     */ import org.apache.commons.math3.geometry.enclosing.WelzlEncloser;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Euclidean3D;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.RotationConvention;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.SphereGenerator;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryProjection;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.RegionFactory;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Sphere1D;
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
/*     */ public class SphericalPolygonsSet
/*     */   extends AbstractRegion<Sphere2D, Sphere1D>
/*     */ {
/*     */   private List<Vertex> loops;
/*     */   
/*     */   public SphericalPolygonsSet(double tolerance) {
/*  54 */     super(tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SphericalPolygonsSet(Vector3D pole, double tolerance) {
/*  62 */     super(new BSPTree((SubHyperplane)(new Circle(pole, tolerance)).wholeHyperplane(), new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), tolerance);
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
/*     */   public SphericalPolygonsSet(Vector3D center, Vector3D meridian, double outsideRadius, int n, double tolerance) {
/*  79 */     this(tolerance, createRegularPolygonVertices(center, meridian, outsideRadius, n));
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
/*     */   public SphericalPolygonsSet(BSPTree<Sphere2D> tree, double tolerance) {
/*  93 */     super(tree, tolerance);
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
/*     */   public SphericalPolygonsSet(Collection<SubHyperplane<Sphere2D>> boundary, double tolerance) {
/* 118 */     super(boundary, tolerance);
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
/*     */   public SphericalPolygonsSet(double hyperplaneThickness, S2Point... vertices) {
/* 152 */     super(verticesToTree(hyperplaneThickness, vertices), hyperplaneThickness);
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
/*     */   private static S2Point[] createRegularPolygonVertices(Vector3D center, Vector3D meridian, double outsideRadius, int n) {
/* 164 */     S2Point[] array = new S2Point[n];
/* 165 */     Rotation r0 = new Rotation(Vector3D.crossProduct(center, meridian), outsideRadius, RotationConvention.VECTOR_OPERATOR);
/*     */     
/* 167 */     array[0] = new S2Point(r0.applyTo(center));
/*     */     
/* 169 */     Rotation r = new Rotation(center, 6.283185307179586D / n, RotationConvention.VECTOR_OPERATOR);
/* 170 */     for (int i = 1; i < n; i++) {
/* 171 */       array[i] = new S2Point(r.applyTo(array[i - 1].getVector()));
/*     */     }
/*     */     
/* 174 */     return array;
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
/*     */   private static BSPTree<Sphere2D> verticesToTree(double hyperplaneThickness, S2Point... vertices) {
/* 198 */     int n = vertices.length;
/* 199 */     if (n == 0)
/*     */     {
/* 201 */       return new BSPTree(Boolean.TRUE);
/*     */     }
/*     */ 
/*     */     
/* 205 */     Vertex[] vArray = new Vertex[n];
/* 206 */     for (int i = 0; i < n; i++) {
/* 207 */       vArray[i] = new Vertex(vertices[i]);
/*     */     }
/*     */ 
/*     */     
/* 211 */     List<Edge> edges = new ArrayList<Edge>(n);
/* 212 */     Vertex end = vArray[n - 1];
/* 213 */     for (int j = 0; j < n; j++) {
/*     */ 
/*     */       
/* 216 */       Vertex start = end;
/* 217 */       end = vArray[j];
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       Circle circle = start.sharedCircleWith(end);
/* 223 */       if (circle == null) {
/* 224 */         circle = new Circle(start.getLocation(), end.getLocation(), hyperplaneThickness);
/*     */       }
/*     */ 
/*     */       
/* 228 */       edges.add(new Edge(start, end, Vector3D.angle(start.getLocation().getVector(), end.getLocation().getVector()), circle));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 234 */       for (Vertex vertex : vArray) {
/* 235 */         if (vertex != start && vertex != end && FastMath.abs(circle.getOffset(vertex.getLocation())) <= hyperplaneThickness)
/*     */         {
/* 237 */           vertex.bindWith(circle);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     BSPTree<Sphere2D> tree = new BSPTree();
/* 245 */     insertEdges(hyperplaneThickness, tree, edges);
/*     */     
/* 247 */     return tree;
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
/*     */   private static void insertEdges(double hyperplaneThickness, BSPTree<Sphere2D> node, List<Edge> edges) {
/* 264 */     int index = 0;
/* 265 */     Edge inserted = null;
/* 266 */     while (inserted == null && index < edges.size()) {
/* 267 */       inserted = edges.get(index++);
/* 268 */       if (!node.insertCut(inserted.getCircle())) {
/* 269 */         inserted = null;
/*     */       }
/*     */     } 
/*     */     
/* 273 */     if (inserted == null) {
/*     */ 
/*     */       
/* 276 */       BSPTree<Sphere2D> parent = node.getParent();
/* 277 */       if (parent == null || node == parent.getMinus()) {
/* 278 */         node.setAttribute(Boolean.TRUE);
/*     */       } else {
/* 280 */         node.setAttribute(Boolean.FALSE);
/*     */       } 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 287 */     List<Edge> outsideList = new ArrayList<Edge>();
/* 288 */     List<Edge> insideList = new ArrayList<Edge>();
/* 289 */     for (Edge edge : edges) {
/* 290 */       if (edge != inserted) {
/* 291 */         edge.split(inserted.getCircle(), outsideList, insideList);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 296 */     if (!outsideList.isEmpty()) {
/* 297 */       insertEdges(hyperplaneThickness, node.getPlus(), outsideList);
/*     */     } else {
/* 299 */       node.getPlus().setAttribute(Boolean.FALSE);
/*     */     } 
/* 301 */     if (!insideList.isEmpty()) {
/* 302 */       insertEdges(hyperplaneThickness, node.getMinus(), insideList);
/*     */     } else {
/* 304 */       node.getMinus().setAttribute(Boolean.TRUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SphericalPolygonsSet buildNew(BSPTree<Sphere2D> tree) {
/* 312 */     return new SphericalPolygonsSet(tree, getTolerance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeGeometricalProperties() throws MathIllegalStateException {
/* 322 */     BSPTree<Sphere2D> tree = getTree(true);
/*     */     
/* 324 */     if (tree.getCut() == null) {
/*     */ 
/*     */ 
/*     */       
/* 328 */       if (tree.getCut() == null && ((Boolean)tree.getAttribute()).booleanValue()) {
/*     */         
/* 330 */         setSize(12.566370614359172D);
/* 331 */         setBarycenter(new S2Point(0.0D, 0.0D));
/*     */       } else {
/* 333 */         setSize(0.0D);
/* 334 */         setBarycenter(S2Point.NaN);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 340 */       PropertiesComputer pc = new PropertiesComputer(getTolerance());
/* 341 */       tree.visit(pc);
/* 342 */       setSize(pc.getArea());
/* 343 */       setBarycenter(pc.getBarycenter());
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
/*     */   
/*     */   public List<Vertex> getBoundaryLoops() throws MathIllegalStateException {
/* 374 */     if (this.loops == null) {
/* 375 */       if (getTree(false).getCut() == null) {
/* 376 */         this.loops = Collections.emptyList();
/*     */       }
/*     */       else {
/*     */         
/* 380 */         BSPTree<Sphere2D> root = getTree(true);
/* 381 */         EdgesBuilder visitor = new EdgesBuilder(root, getTolerance());
/* 382 */         root.visit(visitor);
/* 383 */         List<Edge> edges = visitor.getEdges();
/*     */ 
/*     */ 
/*     */         
/* 387 */         this.loops = new ArrayList<Vertex>();
/* 388 */         label20: while (!edges.isEmpty()) {
/*     */ 
/*     */           
/* 391 */           Edge edge = edges.get(0);
/* 392 */           Vertex startVertex = edge.getStart();
/* 393 */           this.loops.add(startVertex);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           while (true) {
/* 399 */             for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
/* 400 */               if (iterator.next() == edge) {
/* 401 */                 iterator.remove();
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */             
/* 407 */             edge = edge.getEnd().getOutgoing();
/*     */             
/* 409 */             if (edge.getStart() == startVertex) {
/*     */               continue label20;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 416 */     return Collections.unmodifiableList(this.loops);
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
/*     */   public EnclosingBall<Sphere2D, S2Point> getEnclosingCap() {
/* 470 */     if (isEmpty()) {
/* 471 */       return new EnclosingBall(S2Point.PLUS_K, Double.NEGATIVE_INFINITY, (Point[])new S2Point[0]);
/*     */     }
/* 473 */     if (isFull()) {
/* 474 */       return new EnclosingBall(S2Point.PLUS_K, Double.POSITIVE_INFINITY, (Point[])new S2Point[0]);
/*     */     }
/*     */ 
/*     */     
/* 478 */     BSPTree<Sphere2D> root = getTree(false);
/* 479 */     if (isEmpty(root.getMinus()) && isFull(root.getPlus())) {
/*     */       
/* 481 */       Circle circle = (Circle)root.getCut().getHyperplane();
/* 482 */       return new EnclosingBall((new S2Point(circle.getPole())).negate(), 1.5707963267948966D, (Point[])new S2Point[0]);
/*     */     } 
/*     */     
/* 485 */     if (isFull(root.getMinus()) && isEmpty(root.getPlus())) {
/*     */       
/* 487 */       Circle circle = (Circle)root.getCut().getHyperplane();
/* 488 */       return new EnclosingBall(new S2Point(circle.getPole()), 1.5707963267948966D, (Point[])new S2Point[0]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 493 */     List<Vector3D> points = getInsidePoints();
/*     */ 
/*     */     
/* 496 */     List<Vertex> boundary = getBoundaryLoops();
/* 497 */     label40: for (Vertex loopStart : boundary) {
/* 498 */       int count = 0;
/* 499 */       Vertex v = loopStart; while (true) { if (count == 0 || v != loopStart) {
/* 500 */           count++;
/* 501 */           points.add(v.getLocation().getVector()); v = v.getOutgoing().getEnd(); continue;
/*     */         } 
/*     */         continue label40; }
/*     */     
/*     */     } 
/* 506 */     SphereGenerator generator = new SphereGenerator();
/* 507 */     WelzlEncloser<Euclidean3D, Vector3D> encloser = new WelzlEncloser(getTolerance(), (SupportBallGenerator)generator);
/*     */     
/* 509 */     EnclosingBall<Euclidean3D, Vector3D> enclosing3D = encloser.enclose(points);
/* 510 */     Vector3D[] support3D = (Vector3D[])enclosing3D.getSupport();
/*     */ 
/*     */     
/* 513 */     double r = enclosing3D.getRadius();
/* 514 */     double h = ((Vector3D)enclosing3D.getCenter()).getNorm();
/* 515 */     if (h < getTolerance()) {
/*     */ 
/*     */       
/* 518 */       EnclosingBall<Sphere2D, S2Point> enclosingBall = new EnclosingBall(S2Point.PLUS_K, Double.POSITIVE_INFINITY, (Point[])new S2Point[0]);
/*     */       
/* 520 */       for (Vector3D outsidePoint : getOutsidePoints()) {
/* 521 */         S2Point outsideS2 = new S2Point(outsidePoint);
/* 522 */         BoundaryProjection<Sphere2D> projection = projectToBoundary(outsideS2);
/* 523 */         if (Math.PI - projection.getOffset() < enclosingBall.getRadius()) {
/* 524 */           enclosingBall = new EnclosingBall(outsideS2.negate(), Math.PI - projection.getOffset(), (Point[])new S2Point[] { (S2Point)projection.getProjected() });
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 529 */       return enclosingBall;
/*     */     } 
/* 531 */     S2Point[] support = new S2Point[support3D.length];
/* 532 */     for (int i = 0; i < support3D.length; i++) {
/* 533 */       support[i] = new S2Point(support3D[i]);
/*     */     }
/*     */     
/* 536 */     EnclosingBall<Sphere2D, S2Point> enclosingS2 = new EnclosingBall(new S2Point((Vector3D)enclosing3D.getCenter()), FastMath.acos((1.0D + h * h - r * r) / 2.0D * h), (Point[])support);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     return enclosingS2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Vector3D> getInsidePoints() {
/* 549 */     PropertiesComputer pc = new PropertiesComputer(getTolerance());
/* 550 */     getTree(true).visit(pc);
/* 551 */     return pc.getConvexCellsInsidePoints();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Vector3D> getOutsidePoints() {
/* 558 */     SphericalPolygonsSet complement = (SphericalPolygonsSet)(new RegionFactory()).getComplement((Region)this);
/*     */     
/* 560 */     PropertiesComputer pc = new PropertiesComputer(getTolerance());
/* 561 */     complement.getTree(true).visit(pc);
/* 562 */     return pc.getConvexCellsInsidePoints();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/SphericalPolygonsSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
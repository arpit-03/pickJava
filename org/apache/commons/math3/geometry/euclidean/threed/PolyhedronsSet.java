/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Line;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.SubLine;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.RegionFactory;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Transform;
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
/*     */ public class PolyhedronsSet
/*     */   extends AbstractRegion<Euclidean3D, Euclidean2D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   
/*     */   public PolyhedronsSet(double tolerance) {
/*  57 */     super(tolerance);
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
/*     */   public PolyhedronsSet(BSPTree<Euclidean3D> tree, double tolerance) {
/*  82 */     super(tree, tolerance);
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
/*     */   public PolyhedronsSet(Collection<SubHyperplane<Euclidean3D>> boundary, double tolerance) {
/* 108 */     super(boundary, tolerance);
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
/*     */   public PolyhedronsSet(List<Vector3D> vertices, List<int[]> facets, double tolerance) {
/* 131 */     super(buildBoundary(vertices, facets, tolerance), tolerance);
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
/*     */   public PolyhedronsSet(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double tolerance) {
/* 148 */     super(buildBoundary(xMin, xMax, yMin, yMax, zMin, zMax, tolerance), tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PolyhedronsSet() {
/* 156 */     this(1.0E-10D);
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
/*     */   public PolyhedronsSet(BSPTree<Euclidean3D> tree) {
/* 171 */     this(tree, 1.0E-10D);
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
/*     */   public PolyhedronsSet(Collection<SubHyperplane<Euclidean3D>> boundary) {
/* 196 */     this(boundary, 1.0E-10D);
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
/*     */   @Deprecated
/*     */   public PolyhedronsSet(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
/* 213 */     this(xMin, xMax, yMin, yMax, zMin, zMax, 1.0E-10D);
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
/*     */   private static BSPTree<Euclidean3D> buildBoundary(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double tolerance) {
/* 231 */     if (xMin >= xMax - tolerance || yMin >= yMax - tolerance || zMin >= zMax - tolerance)
/*     */     {
/* 233 */       return new BSPTree(Boolean.FALSE);
/*     */     }
/* 235 */     Plane pxMin = new Plane(new Vector3D(xMin, 0.0D, 0.0D), Vector3D.MINUS_I, tolerance);
/* 236 */     Plane pxMax = new Plane(new Vector3D(xMax, 0.0D, 0.0D), Vector3D.PLUS_I, tolerance);
/* 237 */     Plane pyMin = new Plane(new Vector3D(0.0D, yMin, 0.0D), Vector3D.MINUS_J, tolerance);
/* 238 */     Plane pyMax = new Plane(new Vector3D(0.0D, yMax, 0.0D), Vector3D.PLUS_J, tolerance);
/* 239 */     Plane pzMin = new Plane(new Vector3D(0.0D, 0.0D, zMin), Vector3D.MINUS_K, tolerance);
/* 240 */     Plane pzMax = new Plane(new Vector3D(0.0D, 0.0D, zMax), Vector3D.PLUS_K, tolerance);
/*     */     
/* 242 */     Region<Euclidean3D> boundary = (new RegionFactory()).buildConvex(new Hyperplane[] { pxMin, pxMax, pyMin, pyMax, pzMin, pzMax });
/*     */     
/* 244 */     return boundary.getTree(false);
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
/*     */   private static List<SubHyperplane<Euclidean3D>> buildBoundary(List<Vector3D> vertices, List<int[]> facets, double tolerance) {
/* 260 */     for (int i = 0; i < vertices.size() - 1; i++) {
/* 261 */       Vector3D vi = vertices.get(i);
/* 262 */       for (int j = i + 1; j < vertices.size(); j++) {
/* 263 */         if (Vector3D.distance(vi, vertices.get(j)) <= tolerance) {
/* 264 */           throw new MathIllegalArgumentException(LocalizedFormats.CLOSE_VERTICES, new Object[] { Double.valueOf(vi.getX()), Double.valueOf(vi.getY()), Double.valueOf(vi.getZ()) });
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 271 */     int[][] references = findReferences(vertices, facets);
/*     */ 
/*     */     
/* 274 */     int[][] successors = successors(vertices, facets, references);
/*     */ 
/*     */     
/* 277 */     for (int vA = 0; vA < vertices.size(); vA++) {
/* 278 */       for (int vB : successors[vA]) {
/*     */         
/* 280 */         if (vB >= 0) {
/*     */ 
/*     */           
/* 283 */           boolean found = false;
/* 284 */           for (int v : successors[vB]) {
/* 285 */             found = (found || v == vA);
/*     */           }
/* 287 */           if (!found) {
/* 288 */             Vector3D start = vertices.get(vA);
/* 289 */             Vector3D end = vertices.get(vB);
/* 290 */             throw new MathIllegalArgumentException(LocalizedFormats.EDGE_CONNECTED_TO_ONE_FACET, new Object[] { Double.valueOf(start.getX()), Double.valueOf(start.getY()), Double.valueOf(start.getZ()), Double.valueOf(end.getX()), Double.valueOf(end.getY()), Double.valueOf(end.getZ()) });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 298 */     List<SubHyperplane<Euclidean3D>> boundary = new ArrayList<SubHyperplane<Euclidean3D>>();
/*     */     
/* 300 */     for (int[] facet : facets) {
/*     */ 
/*     */       
/* 303 */       Plane plane = new Plane(vertices.get(facet[0]), vertices.get(facet[1]), vertices.get(facet[2]), tolerance);
/*     */ 
/*     */ 
/*     */       
/* 307 */       Vector2D[] two2Points = new Vector2D[facet.length];
/* 308 */       for (int j = 0; j < facet.length; j++) {
/* 309 */         Vector3D v = vertices.get(facet[j]);
/* 310 */         if (!plane.contains(v)) {
/* 311 */           throw new MathIllegalArgumentException(LocalizedFormats.OUT_OF_PLANE, new Object[] { Double.valueOf(v.getX()), Double.valueOf(v.getY()), Double.valueOf(v.getZ()) });
/*     */         }
/*     */         
/* 314 */         two2Points[j] = plane.toSubSpace(v);
/*     */       } 
/*     */ 
/*     */       
/* 318 */       boundary.add(new SubPlane(plane, (Region<Euclidean2D>)new PolygonsSet(tolerance, two2Points)));
/*     */     } 
/*     */ 
/*     */     
/* 322 */     return boundary;
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
/*     */   private static int[][] findReferences(List<Vector3D> vertices, List<int[]> facets) {
/* 336 */     int[] nbFacets = new int[vertices.size()];
/* 337 */     int maxFacets = 0;
/* 338 */     for (int[] facet : facets) {
/* 339 */       if (facet.length < 3) {
/* 340 */         throw new NumberIsTooSmallException(LocalizedFormats.WRONG_NUMBER_OF_POINTS, Integer.valueOf(3), Integer.valueOf(facet.length), true);
/*     */       }
/*     */       
/* 343 */       for (int index : facet) {
/* 344 */         maxFacets = FastMath.max(maxFacets, nbFacets[index] = nbFacets[index] + 1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 349 */     int[][] references = new int[vertices.size()][maxFacets];
/* 350 */     for (int[] r : references) {
/* 351 */       Arrays.fill(r, -1);
/*     */     }
/* 353 */     for (int f = 0; f < facets.size(); f++) {
/* 354 */       for (int v : (int[])facets.get(f)) {
/*     */         
/* 356 */         int k = 0;
/* 357 */         while (k < maxFacets && references[v][k] >= 0) {
/* 358 */           k++;
/*     */         }
/* 360 */         references[v][k] = f;
/*     */       } 
/*     */     } 
/*     */     
/* 364 */     return references;
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
/*     */   private static int[][] successors(List<Vector3D> vertices, List<int[]> facets, int[][] references) {
/* 382 */     int[][] successors = new int[vertices.size()][(references[0]).length];
/* 383 */     for (int[] s : successors) {
/* 384 */       Arrays.fill(s, -1);
/*     */     }
/*     */     
/* 387 */     for (int v = 0; v < vertices.size(); v++) {
/* 388 */       for (int k = 0; k < (successors[v]).length && references[v][k] >= 0; k++) {
/*     */ 
/*     */         
/* 391 */         int[] facet = facets.get(references[v][k]);
/* 392 */         int i = 0;
/* 393 */         while (i < facet.length && facet[i] != v) {
/* 394 */           i++;
/*     */         }
/*     */ 
/*     */         
/* 398 */         successors[v][k] = facet[(i + 1) % facet.length];
/* 399 */         for (int l = 0; l < k; l++) {
/* 400 */           if (successors[v][l] == successors[v][k]) {
/* 401 */             Vector3D start = vertices.get(v);
/* 402 */             Vector3D end = vertices.get(successors[v][k]);
/* 403 */             throw new MathIllegalArgumentException(LocalizedFormats.FACET_ORIENTATION_MISMATCH, new Object[] { Double.valueOf(start.getX()), Double.valueOf(start.getY()), Double.valueOf(start.getZ()), Double.valueOf(end.getX()), Double.valueOf(end.getY()), Double.valueOf(end.getZ()) });
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 412 */     return successors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolyhedronsSet buildNew(BSPTree<Euclidean3D> tree) {
/* 419 */     return new PolyhedronsSet(tree, getTolerance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeGeometricalProperties() {
/* 427 */     getTree(true).visit(new FacetsContributionVisitor());
/*     */     
/* 429 */     if (getSize() < 0.0D) {
/*     */ 
/*     */       
/* 432 */       setSize(Double.POSITIVE_INFINITY);
/* 433 */       setBarycenter((Point)Vector3D.NaN);
/*     */     } else {
/*     */       
/* 436 */       setSize(getSize() / 3.0D);
/* 437 */       setBarycenter((Point)new Vector3D(1.0D / 4.0D * getSize(), (Vector3D)getBarycenter()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class FacetsContributionVisitor
/*     */     implements BSPTreeVisitor<Euclidean3D>
/*     */   {
/*     */     FacetsContributionVisitor() {
/* 447 */       PolyhedronsSet.this.setSize(0.0D);
/* 448 */       PolyhedronsSet.this.setBarycenter((Point)new Vector3D(0.0D, 0.0D, 0.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public BSPTreeVisitor.Order visitOrder(BSPTree<Euclidean3D> node) {
/* 453 */       return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitInternalNode(BSPTree<Euclidean3D> node) {
/* 459 */       BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute<Euclidean3D>)node.getAttribute();
/*     */       
/* 461 */       if (attribute.getPlusOutside() != null) {
/* 462 */         addContribution(attribute.getPlusOutside(), false);
/*     */       }
/* 464 */       if (attribute.getPlusInside() != null) {
/* 465 */         addContribution(attribute.getPlusInside(), true);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitLeafNode(BSPTree<Euclidean3D> node) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void addContribution(SubHyperplane<Euclidean3D> facet, boolean reversed) {
/* 479 */       Region<Euclidean2D> polygon = ((SubPlane)facet).getRemainingRegion();
/* 480 */       double area = polygon.getSize();
/*     */       
/* 482 */       if (Double.isInfinite(area)) {
/* 483 */         PolyhedronsSet.this.setSize(Double.POSITIVE_INFINITY);
/* 484 */         PolyhedronsSet.this.setBarycenter((Point)Vector3D.NaN);
/*     */       } else {
/*     */         
/* 487 */         Plane plane = (Plane)facet.getHyperplane();
/* 488 */         Vector3D facetB = plane.toSpace(polygon.getBarycenter());
/* 489 */         double scaled = area * facetB.dotProduct(plane.getNormal());
/* 490 */         if (reversed) {
/* 491 */           scaled = -scaled;
/*     */         }
/*     */         
/* 494 */         PolyhedronsSet.this.setSize(PolyhedronsSet.this.getSize() + scaled);
/* 495 */         PolyhedronsSet.this.setBarycenter((Point)new Vector3D(1.0D, (Vector3D)PolyhedronsSet.this.getBarycenter(), scaled, facetB));
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
/*     */   public SubHyperplane<Euclidean3D> firstIntersection(Vector3D point, Line line) {
/* 511 */     return recurseFirstIntersection(getTree(true), point, line);
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
/*     */   private SubHyperplane<Euclidean3D> recurseFirstIntersection(BSPTree<Euclidean3D> node, Vector3D point, Line line) {
/*     */     BSPTree<Euclidean3D> near, far;
/* 526 */     SubHyperplane<Euclidean3D> cut = node.getCut();
/* 527 */     if (cut == null) {
/* 528 */       return null;
/*     */     }
/* 530 */     BSPTree<Euclidean3D> minus = node.getMinus();
/* 531 */     BSPTree<Euclidean3D> plus = node.getPlus();
/* 532 */     Plane plane = (Plane)cut.getHyperplane();
/*     */ 
/*     */     
/* 535 */     double offset = plane.getOffset((Point<Euclidean3D>)point);
/* 536 */     boolean in = (FastMath.abs(offset) < getTolerance());
/*     */ 
/*     */     
/* 539 */     if (offset < 0.0D) {
/* 540 */       near = minus;
/* 541 */       far = plus;
/*     */     } else {
/* 543 */       near = plus;
/* 544 */       far = minus;
/*     */     } 
/*     */     
/* 547 */     if (in) {
/*     */       
/* 549 */       SubHyperplane<Euclidean3D> facet = boundaryFacet(point, node);
/* 550 */       if (facet != null) {
/* 551 */         return facet;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 556 */     SubHyperplane<Euclidean3D> crossed = recurseFirstIntersection(near, point, line);
/* 557 */     if (crossed != null) {
/* 558 */       return crossed;
/*     */     }
/*     */     
/* 561 */     if (!in) {
/*     */       
/* 563 */       Vector3D hit3D = plane.intersection(line);
/* 564 */       if (hit3D != null && line.getAbscissa(hit3D) > line.getAbscissa(point)) {
/* 565 */         SubHyperplane<Euclidean3D> facet = boundaryFacet(hit3D, node);
/* 566 */         if (facet != null) {
/* 567 */           return facet;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 573 */     return recurseFirstIntersection(far, point, line);
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
/*     */   private SubHyperplane<Euclidean3D> boundaryFacet(Vector3D point, BSPTree<Euclidean3D> node) {
/* 585 */     Vector2D point2D = ((Plane)node.getCut().getHyperplane()).toSubSpace((Point<Euclidean3D>)point);
/*     */     
/* 587 */     BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute<Euclidean3D>)node.getAttribute();
/*     */     
/* 589 */     if (attribute.getPlusOutside() != null && ((SubPlane)attribute.getPlusOutside()).getRemainingRegion().checkPoint((Point)point2D) == Region.Location.INSIDE)
/*     */     {
/* 591 */       return attribute.getPlusOutside();
/*     */     }
/* 593 */     if (attribute.getPlusInside() != null && ((SubPlane)attribute.getPlusInside()).getRemainingRegion().checkPoint((Point)point2D) == Region.Location.INSIDE)
/*     */     {
/* 595 */       return attribute.getPlusInside();
/*     */     }
/* 597 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolyhedronsSet rotate(Vector3D center, Rotation rotation) {
/* 607 */     return (PolyhedronsSet)applyTransform(new RotationTransform(center, rotation));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RotationTransform
/*     */     implements Transform<Euclidean3D, Euclidean2D>
/*     */   {
/*     */     private Vector3D center;
/*     */ 
/*     */     
/*     */     private Rotation rotation;
/*     */ 
/*     */     
/*     */     private Plane cachedOriginal;
/*     */ 
/*     */     
/*     */     private Transform<Euclidean2D, Euclidean1D> cachedTransform;
/*     */ 
/*     */ 
/*     */     
/*     */     RotationTransform(Vector3D center, Rotation rotation) {
/* 630 */       this.center = center;
/* 631 */       this.rotation = rotation;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3D apply(Point<Euclidean3D> point) {
/* 636 */       Vector3D delta = ((Vector3D)point).subtract(this.center);
/* 637 */       return new Vector3D(1.0D, this.center, 1.0D, this.rotation.applyTo(delta));
/*     */     }
/*     */ 
/*     */     
/*     */     public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
/* 642 */       return ((Plane)hyperplane).rotate(this.center, this.rotation);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
/* 649 */       if (original != this.cachedOriginal) {
/*     */ 
/*     */         
/* 652 */         Plane oPlane = (Plane)original;
/* 653 */         Plane tPlane = (Plane)transformed;
/* 654 */         Vector3D p00 = oPlane.getOrigin();
/* 655 */         Vector3D p10 = oPlane.toSpace((Point<Euclidean2D>)new Vector2D(1.0D, 0.0D));
/* 656 */         Vector3D p01 = oPlane.toSpace((Point<Euclidean2D>)new Vector2D(0.0D, 1.0D));
/* 657 */         Vector2D tP00 = tPlane.toSubSpace((Point<Euclidean3D>)apply((Point<Euclidean3D>)p00));
/* 658 */         Vector2D tP10 = tPlane.toSubSpace((Point<Euclidean3D>)apply((Point<Euclidean3D>)p10));
/* 659 */         Vector2D tP01 = tPlane.toSubSpace((Point<Euclidean3D>)apply((Point<Euclidean3D>)p01));
/*     */         
/* 661 */         this.cachedOriginal = (Plane)original;
/* 662 */         this.cachedTransform = Line.getTransform(tP10.getX() - tP00.getX(), tP10.getY() - tP00.getY(), tP01.getX() - tP00.getX(), tP01.getY() - tP00.getY(), tP00.getX(), tP00.getY());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 671 */       return (SubHyperplane<Euclidean2D>)((SubLine)sub).applyTransform(this.cachedTransform);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolyhedronsSet translate(Vector3D translation) {
/* 682 */     return (PolyhedronsSet)applyTransform(new TranslationTransform(translation));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TranslationTransform
/*     */     implements Transform<Euclidean3D, Euclidean2D>
/*     */   {
/*     */     private Vector3D translation;
/*     */ 
/*     */     
/*     */     private Plane cachedOriginal;
/*     */ 
/*     */     
/*     */     private Transform<Euclidean2D, Euclidean1D> cachedTransform;
/*     */ 
/*     */ 
/*     */     
/*     */     TranslationTransform(Vector3D translation) {
/* 701 */       this.translation = translation;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector3D apply(Point<Euclidean3D> point) {
/* 706 */       return new Vector3D(1.0D, (Vector3D)point, 1.0D, this.translation);
/*     */     }
/*     */ 
/*     */     
/*     */     public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
/* 711 */       return ((Plane)hyperplane).translate(this.translation);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
/* 718 */       if (original != this.cachedOriginal) {
/*     */ 
/*     */         
/* 721 */         Plane oPlane = (Plane)original;
/* 722 */         Plane tPlane = (Plane)transformed;
/* 723 */         Vector2D shift = tPlane.toSubSpace((Point<Euclidean3D>)apply((Point<Euclidean3D>)oPlane.getOrigin()));
/*     */         
/* 725 */         this.cachedOriginal = (Plane)original;
/* 726 */         this.cachedTransform = Line.getTransform(1.0D, 0.0D, 0.0D, 1.0D, shift.getX(), shift.getY());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 733 */       return (SubHyperplane<Euclidean2D>)((SubLine)sub).applyTransform(this.cachedTransform);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/PolyhedronsSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
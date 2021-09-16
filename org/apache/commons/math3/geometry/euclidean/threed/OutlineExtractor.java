/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Line;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.SubLine;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*     */ import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
/*     */ import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.RegionFactory;
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
/*     */ 
/*     */ 
/*     */ public class OutlineExtractor
/*     */ {
/*     */   private Vector3D u;
/*     */   private Vector3D v;
/*     */   private Vector3D w;
/*     */   
/*     */   public OutlineExtractor(Vector3D u, Vector3D v) {
/*  54 */     this.u = u;
/*  55 */     this.v = v;
/*  56 */     this.w = Vector3D.crossProduct(u, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D[][] getOutline(PolyhedronsSet polyhedronsSet) {
/*  66 */     BoundaryProjector projector = new BoundaryProjector(polyhedronsSet.getTolerance());
/*  67 */     polyhedronsSet.getTree(true).visit(projector);
/*  68 */     PolygonsSet projected = projector.getProjected();
/*     */ 
/*     */     
/*  71 */     Vector2D[][] outline = projected.getVertices();
/*  72 */     for (int i = 0; i < outline.length; i++) {
/*  73 */       Vector2D[] rawLoop = outline[i];
/*  74 */       int end = rawLoop.length;
/*  75 */       int j = 0;
/*  76 */       while (j < end) {
/*  77 */         if (pointIsBetween(rawLoop, end, j)) {
/*     */           
/*  79 */           for (int k = j; k < end - 1; k++) {
/*  80 */             rawLoop[k] = rawLoop[k + 1];
/*     */           }
/*  82 */           end--;
/*     */           continue;
/*     */         } 
/*  85 */         j++;
/*     */       } 
/*     */       
/*  88 */       if (end != rawLoop.length) {
/*     */         
/*  90 */         outline[i] = new Vector2D[end];
/*  91 */         System.arraycopy(rawLoop, 0, outline[i], 0, end);
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     return outline;
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
/*     */   private boolean pointIsBetween(Vector2D[] loop, int n, int i) {
/* 108 */     Vector2D previous = loop[(i + n - 1) % n];
/* 109 */     Vector2D current = loop[i];
/* 110 */     Vector2D next = loop[(i + 1) % n];
/* 111 */     double dx1 = current.getX() - previous.getX();
/* 112 */     double dy1 = current.getY() - previous.getY();
/* 113 */     double dx2 = next.getX() - current.getX();
/* 114 */     double dy2 = next.getY() - current.getY();
/* 115 */     double cross = dx1 * dy2 - dx2 * dy1;
/* 116 */     double dot = dx1 * dx2 + dy1 * dy2;
/* 117 */     double d1d2 = FastMath.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2));
/* 118 */     return (FastMath.abs(cross) <= 1.0E-6D * d1d2 && dot >= 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class BoundaryProjector
/*     */     implements BSPTreeVisitor<Euclidean3D>
/*     */   {
/*     */     private PolygonsSet projected;
/*     */ 
/*     */     
/*     */     private final double tolerance;
/*     */ 
/*     */ 
/*     */     
/*     */     BoundaryProjector(double tolerance) {
/* 134 */       this.projected = new PolygonsSet(new BSPTree(Boolean.FALSE), tolerance);
/* 135 */       this.tolerance = tolerance;
/*     */     }
/*     */ 
/*     */     
/*     */     public BSPTreeVisitor.Order visitOrder(BSPTree<Euclidean3D> node) {
/* 140 */       return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void visitInternalNode(BSPTree<Euclidean3D> node) {
/* 146 */       BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute<Euclidean3D>)node.getAttribute();
/*     */       
/* 148 */       if (attribute.getPlusOutside() != null) {
/* 149 */         addContribution(attribute.getPlusOutside(), false);
/*     */       }
/* 151 */       if (attribute.getPlusInside() != null) {
/* 152 */         addContribution(attribute.getPlusInside(), true);
/*     */       }
/*     */     }
/*     */ 
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
/*     */     
/*     */     private void addContribution(SubHyperplane<Euclidean3D> facet, boolean reversed) {
/* 168 */       AbstractSubHyperplane<Euclidean3D, Euclidean2D> absFacet = (AbstractSubHyperplane)facet;
/*     */       
/* 170 */       Plane plane = (Plane)facet.getHyperplane();
/*     */       
/* 172 */       double scal = plane.getNormal().dotProduct(OutlineExtractor.this.w);
/* 173 */       if (FastMath.abs(scal) > 0.001D) {
/* 174 */         Vector2D[][] vertices = ((PolygonsSet)absFacet.getRemainingRegion()).getVertices();
/*     */ 
/*     */         
/* 177 */         if ((((scal < 0.0D) ? 1 : 0) ^ reversed) != 0) {
/*     */ 
/*     */           
/* 180 */           Vector2D[][] newVertices = new Vector2D[vertices.length][];
/* 181 */           for (int i = 0; i < vertices.length; i++) {
/* 182 */             Vector2D[] loop = vertices[i];
/* 183 */             Vector2D[] newLoop = new Vector2D[loop.length];
/* 184 */             if (loop[0] == null) {
/* 185 */               newLoop[0] = null;
/* 186 */               for (int j = 1; j < loop.length; j++) {
/* 187 */                 newLoop[j] = loop[loop.length - j];
/*     */               }
/*     */             } else {
/* 190 */               for (int j = 0; j < loop.length; j++) {
/* 191 */                 newLoop[j] = loop[loop.length - j + 1];
/*     */               }
/*     */             } 
/* 194 */             newVertices[i] = newLoop;
/*     */           } 
/*     */ 
/*     */           
/* 198 */           vertices = newVertices;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 203 */         ArrayList<SubHyperplane<Euclidean2D>> edges = new ArrayList<SubHyperplane<Euclidean2D>>();
/* 204 */         for (Vector2D[] loop : vertices) {
/* 205 */           boolean closed = (loop[0] != null);
/* 206 */           int previous = closed ? (loop.length - 1) : 1;
/* 207 */           Vector3D previous3D = plane.toSpace((Point<Euclidean2D>)loop[previous]);
/* 208 */           int current = (previous + 1) % loop.length;
/* 209 */           Vector2D pPoint = new Vector2D(previous3D.dotProduct(OutlineExtractor.this.u), previous3D.dotProduct(OutlineExtractor.this.v));
/*     */           
/* 211 */           while (current < loop.length) {
/*     */             SubHyperplane<Euclidean2D> subHyperplane;
/* 213 */             Vector3D current3D = plane.toSpace((Point<Euclidean2D>)loop[current]);
/* 214 */             Vector2D cPoint = new Vector2D(current3D.dotProduct(OutlineExtractor.this.u), current3D.dotProduct(OutlineExtractor.this.v));
/*     */             
/* 216 */             Line line = new Line(pPoint, cPoint, this.tolerance);
/*     */             
/* 218 */             SubLine subLine = line.wholeHyperplane();
/*     */             
/* 220 */             if (closed || previous != 1) {
/*     */ 
/*     */               
/* 223 */               double angle = line.getAngle() + 1.5707963267948966D;
/* 224 */               Line l = new Line(pPoint, angle, this.tolerance);
/*     */               
/* 226 */               subHyperplane = subLine.split((Hyperplane)l).getPlus();
/*     */             } 
/*     */             
/* 229 */             if (closed || current != loop.length - 1) {
/*     */ 
/*     */               
/* 232 */               double angle = line.getAngle() + 1.5707963267948966D;
/* 233 */               Line l = new Line(cPoint, angle, this.tolerance);
/*     */               
/* 235 */               subHyperplane = subHyperplane.split((Hyperplane)l).getMinus();
/*     */             } 
/*     */             
/* 238 */             edges.add(subHyperplane);
/*     */             
/* 240 */             previous = current++;
/* 241 */             previous3D = current3D;
/* 242 */             pPoint = cPoint;
/*     */           } 
/*     */         } 
/*     */         
/* 246 */         PolygonsSet projectedFacet = new PolygonsSet(edges, this.tolerance);
/*     */ 
/*     */         
/* 249 */         this.projected = (PolygonsSet)(new RegionFactory()).union((Region)this.projected, (Region)projectedFacet);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PolygonsSet getProjected() {
/* 258 */       return this.projected;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/OutlineExtractor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
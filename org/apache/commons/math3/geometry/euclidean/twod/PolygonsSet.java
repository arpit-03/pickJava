/*      */ package org.apache.commons.math3.geometry.euclidean.twod;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import org.apache.commons.math3.geometry.Point;
/*      */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*      */ import org.apache.commons.math3.geometry.euclidean.oned.Interval;
/*      */ import org.apache.commons.math3.geometry.euclidean.oned.IntervalsSet;
/*      */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*      */ import org.apache.commons.math3.geometry.partitioning.AbstractRegion;
/*      */ import org.apache.commons.math3.geometry.partitioning.AbstractSubHyperplane;
/*      */ import org.apache.commons.math3.geometry.partitioning.BSPTree;
/*      */ import org.apache.commons.math3.geometry.partitioning.BSPTreeVisitor;
/*      */ import org.apache.commons.math3.geometry.partitioning.BoundaryAttribute;
/*      */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*      */ import org.apache.commons.math3.geometry.partitioning.NodesSet;
/*      */ import org.apache.commons.math3.geometry.partitioning.Region;
/*      */ import org.apache.commons.math3.geometry.partitioning.Side;
/*      */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.Precision;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PolygonsSet
/*      */   extends AbstractRegion<Euclidean2D, Euclidean1D>
/*      */ {
/*      */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*      */   private Vector2D[][] vertices;
/*      */   
/*      */   public PolygonsSet(double tolerance) {
/*   55 */     super(tolerance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolygonsSet(BSPTree<Euclidean2D> tree, double tolerance) {
/*   80 */     super(tree, tolerance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolygonsSet(Collection<SubHyperplane<Euclidean2D>> boundary, double tolerance) {
/*  106 */     super(boundary, tolerance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolygonsSet(double xMin, double xMax, double yMin, double yMax, double tolerance) {
/*  120 */     super((Hyperplane[])boxBoundary(xMin, xMax, yMin, yMax, tolerance), tolerance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolygonsSet(double hyperplaneThickness, Vector2D... vertices) {
/*  154 */     super(verticesToTree(hyperplaneThickness, vertices), hyperplaneThickness);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PolygonsSet() {
/*  162 */     this(1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PolygonsSet(BSPTree<Euclidean2D> tree) {
/*  177 */     this(tree, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PolygonsSet(Collection<SubHyperplane<Euclidean2D>> boundary) {
/*  203 */     this(boundary, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public PolygonsSet(double xMin, double xMax, double yMin, double yMax) {
/*  216 */     this(xMin, xMax, yMin, yMax, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Line[] boxBoundary(double xMin, double xMax, double yMin, double yMax, double tolerance) {
/*  230 */     if (xMin >= xMax - tolerance || yMin >= yMax - tolerance)
/*      */     {
/*  232 */       return null;
/*      */     }
/*  234 */     Vector2D minMin = new Vector2D(xMin, yMin);
/*  235 */     Vector2D minMax = new Vector2D(xMin, yMax);
/*  236 */     Vector2D maxMin = new Vector2D(xMax, yMin);
/*  237 */     Vector2D maxMax = new Vector2D(xMax, yMax);
/*  238 */     return new Line[] { new Line(minMin, maxMin, tolerance), new Line(maxMin, maxMax, tolerance), new Line(maxMax, minMax, tolerance), new Line(minMax, minMin, tolerance) };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BSPTree<Euclidean2D> verticesToTree(double hyperplaneThickness, Vector2D... vertices) {
/*  264 */     int n = vertices.length;
/*  265 */     if (n == 0)
/*      */     {
/*  267 */       return new BSPTree(Boolean.TRUE);
/*      */     }
/*      */ 
/*      */     
/*  271 */     Vertex[] vArray = new Vertex[n];
/*  272 */     for (int i = 0; i < n; i++) {
/*  273 */       vArray[i] = new Vertex(vertices[i]);
/*      */     }
/*      */ 
/*      */     
/*  277 */     List<Edge> edges = new ArrayList<Edge>(n);
/*  278 */     for (int j = 0; j < n; j++) {
/*      */ 
/*      */       
/*  281 */       Vertex start = vArray[j];
/*  282 */       Vertex end = vArray[(j + 1) % n];
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  287 */       Line line = start.sharedLineWith(end);
/*  288 */       if (line == null) {
/*  289 */         line = new Line(start.getLocation(), end.getLocation(), hyperplaneThickness);
/*      */       }
/*      */ 
/*      */       
/*  293 */       edges.add(new Edge(start, end, line));
/*      */ 
/*      */       
/*  296 */       for (Vertex vertex : vArray) {
/*  297 */         if (vertex != start && vertex != end && FastMath.abs(line.getOffset((Point<Euclidean2D>)vertex.getLocation())) <= hyperplaneThickness)
/*      */         {
/*  299 */           vertex.bindWith(line);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  306 */     BSPTree<Euclidean2D> tree = new BSPTree();
/*  307 */     insertEdges(hyperplaneThickness, tree, edges);
/*      */     
/*  309 */     return tree;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void insertEdges(double hyperplaneThickness, BSPTree<Euclidean2D> node, List<Edge> edges) {
/*  326 */     int index = 0;
/*  327 */     Edge inserted = null;
/*  328 */     while (inserted == null && index < edges.size()) {
/*  329 */       inserted = edges.get(index++);
/*  330 */       if (inserted.getNode() == null) {
/*  331 */         if (node.insertCut(inserted.getLine())) {
/*  332 */           inserted.setNode(node); continue;
/*      */         } 
/*  334 */         inserted = null;
/*      */         continue;
/*      */       } 
/*  337 */       inserted = null;
/*      */     } 
/*      */ 
/*      */     
/*  341 */     if (inserted == null) {
/*      */ 
/*      */       
/*  344 */       BSPTree<Euclidean2D> parent = node.getParent();
/*  345 */       if (parent == null || node == parent.getMinus()) {
/*  346 */         node.setAttribute(Boolean.TRUE);
/*      */       } else {
/*  348 */         node.setAttribute(Boolean.FALSE);
/*      */       } 
/*      */ 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  355 */     List<Edge> plusList = new ArrayList<Edge>();
/*  356 */     List<Edge> minusList = new ArrayList<Edge>();
/*  357 */     for (Edge edge : edges) {
/*  358 */       if (edge != inserted) {
/*  359 */         double startOffset = inserted.getLine().getOffset((Point<Euclidean2D>)edge.getStart().getLocation());
/*  360 */         double endOffset = inserted.getLine().getOffset((Point<Euclidean2D>)edge.getEnd().getLocation());
/*  361 */         Side startSide = (FastMath.abs(startOffset) <= hyperplaneThickness) ? Side.HYPER : ((startOffset < 0.0D) ? Side.MINUS : Side.PLUS);
/*      */         
/*  363 */         Side endSide = (FastMath.abs(endOffset) <= hyperplaneThickness) ? Side.HYPER : ((endOffset < 0.0D) ? Side.MINUS : Side.PLUS);
/*      */         
/*  365 */         switch (startSide) {
/*      */           case PLUS:
/*  367 */             if (endSide == Side.MINUS) {
/*      */               
/*  369 */               Vertex splitPoint = edge.split(inserted.getLine());
/*  370 */               minusList.add(splitPoint.getOutgoing());
/*  371 */               plusList.add(splitPoint.getIncoming()); continue;
/*      */             } 
/*  373 */             plusList.add(edge);
/*      */             continue;
/*      */           
/*      */           case MINUS:
/*  377 */             if (endSide == Side.PLUS) {
/*      */               
/*  379 */               Vertex splitPoint = edge.split(inserted.getLine());
/*  380 */               minusList.add(splitPoint.getIncoming());
/*  381 */               plusList.add(splitPoint.getOutgoing()); continue;
/*      */             } 
/*  383 */             minusList.add(edge);
/*      */             continue;
/*      */         } 
/*      */         
/*  387 */         if (endSide == Side.PLUS) {
/*  388 */           plusList.add(edge); continue;
/*  389 */         }  if (endSide == Side.MINUS) {
/*  390 */           minusList.add(edge);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  398 */     if (!plusList.isEmpty()) {
/*  399 */       insertEdges(hyperplaneThickness, node.getPlus(), plusList);
/*      */     } else {
/*  401 */       node.getPlus().setAttribute(Boolean.FALSE);
/*      */     } 
/*  403 */     if (!minusList.isEmpty()) {
/*  404 */       insertEdges(hyperplaneThickness, node.getMinus(), minusList);
/*      */     } else {
/*  406 */       node.getMinus().setAttribute(Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Vertex
/*      */   {
/*      */     private final Vector2D location;
/*      */ 
/*      */ 
/*      */     
/*      */     private PolygonsSet.Edge incoming;
/*      */ 
/*      */     
/*      */     private PolygonsSet.Edge outgoing;
/*      */ 
/*      */     
/*      */     private final List<Line> lines;
/*      */ 
/*      */ 
/*      */     
/*      */     Vertex(Vector2D location) {
/*  430 */       this.location = location;
/*  431 */       this.incoming = null;
/*  432 */       this.outgoing = null;
/*  433 */       this.lines = new ArrayList<Line>();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Vector2D getLocation() {
/*  440 */       return this.location;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void bindWith(Line line) {
/*  447 */       this.lines.add(line);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Line sharedLineWith(Vertex vertex) {
/*  461 */       for (Line line1 : this.lines) {
/*  462 */         for (Line line2 : vertex.lines) {
/*  463 */           if (line1 == line2) {
/*  464 */             return line1;
/*      */           }
/*      */         } 
/*      */       } 
/*  468 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setIncoming(PolygonsSet.Edge incoming) {
/*  479 */       this.incoming = incoming;
/*  480 */       bindWith(incoming.getLine());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PolygonsSet.Edge getIncoming() {
/*  487 */       return this.incoming;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setOutgoing(PolygonsSet.Edge outgoing) {
/*  498 */       this.outgoing = outgoing;
/*  499 */       bindWith(outgoing.getLine());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PolygonsSet.Edge getOutgoing() {
/*  506 */       return this.outgoing;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class Edge
/*      */   {
/*      */     private final PolygonsSet.Vertex start;
/*      */ 
/*      */ 
/*      */     
/*      */     private final PolygonsSet.Vertex end;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Line line;
/*      */ 
/*      */ 
/*      */     
/*      */     private BSPTree<Euclidean2D> node;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Edge(PolygonsSet.Vertex start, PolygonsSet.Vertex end, Line line) {
/*  533 */       this.start = start;
/*  534 */       this.end = end;
/*  535 */       this.line = line;
/*  536 */       this.node = null;
/*      */ 
/*      */       
/*  539 */       start.setOutgoing(this);
/*  540 */       end.setIncoming(this);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PolygonsSet.Vertex getStart() {
/*  548 */       return this.start;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PolygonsSet.Vertex getEnd() {
/*  555 */       return this.end;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Line getLine() {
/*  562 */       return this.line;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNode(BSPTree<Euclidean2D> node) {
/*  569 */       this.node = node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BSPTree<Euclidean2D> getNode() {
/*  577 */       return this.node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public PolygonsSet.Vertex split(Line splitLine) {
/*  590 */       PolygonsSet.Vertex splitVertex = new PolygonsSet.Vertex(this.line.intersection(splitLine));
/*  591 */       splitVertex.bindWith(splitLine);
/*  592 */       Edge startHalf = new Edge(this.start, splitVertex, this.line);
/*  593 */       Edge endHalf = new Edge(splitVertex, this.end, this.line);
/*  594 */       startHalf.node = this.node;
/*  595 */       endHalf.node = this.node;
/*  596 */       return splitVertex;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PolygonsSet buildNew(BSPTree<Euclidean2D> tree) {
/*  604 */     return new PolygonsSet(tree, getTolerance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void computeGeometricalProperties() {
/*  611 */     Vector2D[][] v = getVertices();
/*      */     
/*  613 */     if (v.length == 0) {
/*  614 */       BSPTree<Euclidean2D> tree = getTree(false);
/*  615 */       if (tree.getCut() == null && ((Boolean)tree.getAttribute()).booleanValue()) {
/*      */         
/*  617 */         setSize(Double.POSITIVE_INFINITY);
/*  618 */         setBarycenter((Point)Vector2D.NaN);
/*      */       } else {
/*  620 */         setSize(0.0D);
/*  621 */         setBarycenter((Point)new Vector2D(0.0D, 0.0D));
/*      */       } 
/*  623 */     } else if (v[0][0] == null) {
/*      */       
/*  625 */       setSize(Double.POSITIVE_INFINITY);
/*  626 */       setBarycenter((Point)Vector2D.NaN);
/*      */     }
/*      */     else {
/*      */       
/*  630 */       double sum = 0.0D;
/*  631 */       double sumX = 0.0D;
/*  632 */       double sumY = 0.0D;
/*      */       
/*  634 */       for (Vector2D[] loop : v) {
/*  635 */         double x1 = loop[loop.length - 1].getX();
/*  636 */         double y1 = loop[loop.length - 1].getY();
/*  637 */         for (Vector2D point : loop) {
/*  638 */           double x0 = x1;
/*  639 */           double y0 = y1;
/*  640 */           x1 = point.getX();
/*  641 */           y1 = point.getY();
/*  642 */           double factor = x0 * y1 - y0 * x1;
/*  643 */           sum += factor;
/*  644 */           sumX += factor * (x0 + x1);
/*  645 */           sumY += factor * (y0 + y1);
/*      */         } 
/*      */       } 
/*      */       
/*  649 */       if (sum < 0.0D) {
/*      */         
/*  651 */         setSize(Double.POSITIVE_INFINITY);
/*  652 */         setBarycenter((Point)Vector2D.NaN);
/*      */       } else {
/*  654 */         setSize(sum / 2.0D);
/*  655 */         setBarycenter((Point)new Vector2D(sumX / 3.0D * sum, sumY / 3.0D * sum));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector2D[][] getVertices() {
/*  686 */     if (this.vertices == null) {
/*  687 */       if (getTree(false).getCut() == null) {
/*  688 */         this.vertices = new Vector2D[0][];
/*      */       }
/*      */       else {
/*      */         
/*  692 */         SegmentsBuilder visitor = new SegmentsBuilder(getTolerance());
/*  693 */         getTree(true).visit(visitor);
/*  694 */         List<ConnectableSegment> segments = visitor.getSegments();
/*      */ 
/*      */ 
/*      */         
/*  698 */         int pending = segments.size();
/*  699 */         pending -= naturalFollowerConnections(segments);
/*  700 */         if (pending > 0) {
/*  701 */           pending -= splitEdgeConnections(segments);
/*      */         }
/*  703 */         if (pending > 0) {
/*  704 */           pending -= closeVerticesConnections(segments);
/*      */         }
/*      */ 
/*      */         
/*  708 */         ArrayList<List<Segment>> loops = new ArrayList<List<Segment>>();
/*  709 */         for (ConnectableSegment s = getUnprocessed(segments); s != null; s = getUnprocessed(segments)) {
/*  710 */           List<Segment> loop = followLoop(s);
/*  711 */           if (loop != null) {
/*  712 */             if (((Segment)loop.get(0)).getStart() == null) {
/*      */               
/*  714 */               loops.add(0, loop);
/*      */             } else {
/*      */               
/*  717 */               loops.add(loop);
/*      */             } 
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  723 */         this.vertices = new Vector2D[loops.size()][];
/*  724 */         int i = 0;
/*      */         
/*  726 */         for (List<Segment> loop : loops) {
/*  727 */           if (loop.size() < 2 || (loop.size() == 2 && ((Segment)loop.get(0)).getStart() == null && ((Segment)loop.get(1)).getEnd() == null)) {
/*      */ 
/*      */             
/*  730 */             Line line = ((Segment)loop.get(0)).getLine();
/*  731 */             (new Vector2D[3])[0] = null; (new Vector2D[3])[1] = line.toSpace((Point<Euclidean1D>)new Vector1D(-3.4028234663852886E38D)); (new Vector2D[3])[2] = line.toSpace((Point<Euclidean1D>)new Vector1D(3.4028234663852886E38D)); this.vertices[i++] = new Vector2D[3];
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  736 */           if (((Segment)loop.get(0)).getStart() == null) {
/*      */             
/*  738 */             Vector2D[] arrayOfVector2D = new Vector2D[loop.size() + 2];
/*  739 */             int k = 0;
/*  740 */             for (Segment segment : loop) {
/*      */               
/*  742 */               if (k == 0) {
/*      */                 
/*  744 */                 double x = segment.getLine().toSubSpace((Point<Euclidean2D>)segment.getEnd()).getX();
/*  745 */                 x -= FastMath.max(1.0D, FastMath.abs(x / 2.0D));
/*  746 */                 arrayOfVector2D[k++] = null;
/*  747 */                 arrayOfVector2D[k++] = segment.getLine().toSpace((Point<Euclidean1D>)new Vector1D(x));
/*      */               } 
/*      */               
/*  750 */               if (k < arrayOfVector2D.length - 1)
/*      */               {
/*  752 */                 arrayOfVector2D[k++] = segment.getEnd();
/*      */               }
/*      */               
/*  755 */               if (k == arrayOfVector2D.length - 1) {
/*      */                 
/*  757 */                 double x = segment.getLine().toSubSpace((Point<Euclidean2D>)segment.getStart()).getX();
/*  758 */                 x += FastMath.max(1.0D, FastMath.abs(x / 2.0D));
/*  759 */                 arrayOfVector2D[k++] = segment.getLine().toSpace((Point<Euclidean1D>)new Vector1D(x));
/*      */               } 
/*      */             } 
/*      */             
/*  763 */             this.vertices[i++] = arrayOfVector2D; continue;
/*      */           } 
/*  765 */           Vector2D[] array = new Vector2D[loop.size()];
/*  766 */           int j = 0;
/*  767 */           for (Segment segment : loop) {
/*  768 */             array[j++] = segment.getStart();
/*      */           }
/*  770 */           this.vertices[i++] = array;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  777 */     return (Vector2D[][])this.vertices.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int naturalFollowerConnections(List<ConnectableSegment> segments) {
/*  786 */     int connected = 0;
/*  787 */     for (ConnectableSegment segment : segments) {
/*  788 */       if (segment.getNext() == null) {
/*  789 */         BSPTree<Euclidean2D> node = segment.getNode();
/*  790 */         BSPTree<Euclidean2D> end = segment.getEndNode();
/*  791 */         for (ConnectableSegment candidateNext : segments) {
/*  792 */           if (candidateNext.getPrevious() == null && candidateNext.getNode() == end && candidateNext.getStartNode() == node) {
/*      */ 
/*      */ 
/*      */             
/*  796 */             segment.setNext(candidateNext);
/*  797 */             candidateNext.setPrevious(segment);
/*  798 */             connected++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  804 */     return connected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int splitEdgeConnections(List<ConnectableSegment> segments) {
/*  812 */     int connected = 0;
/*  813 */     for (ConnectableSegment segment : segments) {
/*  814 */       if (segment.getNext() == null) {
/*  815 */         Hyperplane<Euclidean2D> hyperplane = segment.getNode().getCut().getHyperplane();
/*  816 */         BSPTree<Euclidean2D> end = segment.getEndNode();
/*  817 */         for (ConnectableSegment candidateNext : segments) {
/*  818 */           if (candidateNext.getPrevious() == null && candidateNext.getNode().getCut().getHyperplane() == hyperplane && candidateNext.getStartNode() == end) {
/*      */ 
/*      */ 
/*      */             
/*  822 */             segment.setNext(candidateNext);
/*  823 */             candidateNext.setPrevious(segment);
/*  824 */             connected++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  830 */     return connected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int closeVerticesConnections(List<ConnectableSegment> segments) {
/*  842 */     int connected = 0;
/*  843 */     for (ConnectableSegment segment : segments) {
/*  844 */       if (segment.getNext() == null && segment.getEnd() != null) {
/*  845 */         Vector2D end = segment.getEnd();
/*  846 */         ConnectableSegment selectedNext = null;
/*  847 */         double min = Double.POSITIVE_INFINITY;
/*  848 */         for (ConnectableSegment candidateNext : segments) {
/*  849 */           if (candidateNext.getPrevious() == null && candidateNext.getStart() != null) {
/*  850 */             double distance = Vector2D.distance(end, candidateNext.getStart());
/*  851 */             if (distance < min) {
/*  852 */               selectedNext = candidateNext;
/*  853 */               min = distance;
/*      */             } 
/*      */           } 
/*      */         } 
/*  857 */         if (min <= getTolerance()) {
/*      */           
/*  859 */           segment.setNext(selectedNext);
/*  860 */           selectedNext.setPrevious(segment);
/*  861 */           connected++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  865 */     return connected;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ConnectableSegment getUnprocessed(List<ConnectableSegment> segments) {
/*  874 */     for (ConnectableSegment segment : segments) {
/*  875 */       if (!segment.isProcessed()) {
/*  876 */         return segment;
/*      */       }
/*      */     } 
/*  879 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Segment> followLoop(ConnectableSegment defining) {
/*  892 */     List<Segment> loop = new ArrayList<Segment>();
/*  893 */     loop.add(defining);
/*  894 */     defining.setProcessed(true);
/*      */ 
/*      */     
/*  897 */     ConnectableSegment next = defining.getNext();
/*  898 */     while (next != defining && next != null) {
/*  899 */       loop.add(next);
/*  900 */       next.setProcessed(true);
/*  901 */       next = next.getNext();
/*      */     } 
/*      */     
/*  904 */     if (next == null) {
/*      */ 
/*      */       
/*  907 */       ConnectableSegment previous = defining.getPrevious();
/*  908 */       while (previous != null) {
/*  909 */         loop.add(0, previous);
/*  910 */         previous.setProcessed(true);
/*  911 */         previous = previous.getPrevious();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  916 */     filterSpuriousVertices(loop);
/*      */     
/*  918 */     if (loop.size() == 2 && ((Segment)loop.get(0)).getStart() != null)
/*      */     {
/*  920 */       return null;
/*      */     }
/*  922 */     return loop;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void filterSpuriousVertices(List<Segment> loop) {
/*  931 */     for (int i = 0; i < loop.size(); i++) {
/*  932 */       Segment previous = loop.get(i);
/*  933 */       int j = (i + 1) % loop.size();
/*  934 */       Segment next = loop.get(j);
/*  935 */       if (next != null && Precision.equals(previous.getLine().getAngle(), next.getLine().getAngle(), Precision.EPSILON)) {
/*      */ 
/*      */ 
/*      */         
/*  939 */         loop.set(j, new Segment(previous.getStart(), next.getEnd(), previous.getLine()));
/*  940 */         loop.remove(i--);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ConnectableSegment
/*      */     extends Segment
/*      */   {
/*      */     private final BSPTree<Euclidean2D> node;
/*      */ 
/*      */ 
/*      */     
/*      */     private final BSPTree<Euclidean2D> startNode;
/*      */ 
/*      */ 
/*      */     
/*      */     private final BSPTree<Euclidean2D> endNode;
/*      */ 
/*      */ 
/*      */     
/*      */     private ConnectableSegment previous;
/*      */ 
/*      */ 
/*      */     
/*      */     private ConnectableSegment next;
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean processed;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ConnectableSegment(Vector2D start, Vector2D end, Line line, BSPTree<Euclidean2D> node, BSPTree<Euclidean2D> startNode, BSPTree<Euclidean2D> endNode) {
/*  978 */       super(start, end, line);
/*  979 */       this.node = node;
/*  980 */       this.startNode = startNode;
/*  981 */       this.endNode = endNode;
/*  982 */       this.previous = null;
/*  983 */       this.next = null;
/*  984 */       this.processed = false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BSPTree<Euclidean2D> getNode() {
/*  991 */       return this.node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BSPTree<Euclidean2D> getStartNode() {
/*  998 */       return this.startNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public BSPTree<Euclidean2D> getEndNode() {
/* 1005 */       return this.endNode;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConnectableSegment getPrevious() {
/* 1012 */       return this.previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setPrevious(ConnectableSegment previous) {
/* 1019 */       this.previous = previous;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ConnectableSegment getNext() {
/* 1026 */       return this.next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setNext(ConnectableSegment next) {
/* 1033 */       this.next = next;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setProcessed(boolean processed) {
/* 1040 */       this.processed = processed;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isProcessed() {
/* 1047 */       return this.processed;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class SegmentsBuilder
/*      */     implements BSPTreeVisitor<Euclidean2D>
/*      */   {
/*      */     private final double tolerance;
/*      */ 
/*      */     
/*      */     private final List<PolygonsSet.ConnectableSegment> segments;
/*      */ 
/*      */ 
/*      */     
/*      */     SegmentsBuilder(double tolerance) {
/* 1065 */       this.tolerance = tolerance;
/* 1066 */       this.segments = new ArrayList<PolygonsSet.ConnectableSegment>();
/*      */     }
/*      */ 
/*      */     
/*      */     public BSPTreeVisitor.Order visitOrder(BSPTree<Euclidean2D> node) {
/* 1071 */       return BSPTreeVisitor.Order.MINUS_SUB_PLUS;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void visitInternalNode(BSPTree<Euclidean2D> node) {
/* 1077 */       BoundaryAttribute<Euclidean2D> attribute = (BoundaryAttribute<Euclidean2D>)node.getAttribute();
/* 1078 */       NodesSet nodesSet = attribute.getSplitters();
/* 1079 */       if (attribute.getPlusOutside() != null) {
/* 1080 */         addContribution(attribute.getPlusOutside(), node, (Iterable<BSPTree<Euclidean2D>>)nodesSet, false);
/*      */       }
/* 1082 */       if (attribute.getPlusInside() != null) {
/* 1083 */         addContribution(attribute.getPlusInside(), node, (Iterable<BSPTree<Euclidean2D>>)nodesSet, true);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void visitLeafNode(BSPTree<Euclidean2D> node) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void addContribution(SubHyperplane<Euclidean2D> sub, BSPTree<Euclidean2D> node, Iterable<BSPTree<Euclidean2D>> splitters, boolean reversed) {
/* 1102 */       AbstractSubHyperplane<Euclidean2D, Euclidean1D> absSub = (AbstractSubHyperplane)sub;
/*      */       
/* 1104 */       Line line = (Line)sub.getHyperplane();
/* 1105 */       List<Interval> intervals = ((IntervalsSet)absSub.getRemainingRegion()).asList();
/* 1106 */       for (Interval i : intervals) {
/*      */ 
/*      */         
/* 1109 */         Vector2D startV = Double.isInfinite(i.getInf()) ? null : line.toSpace((Point<Euclidean1D>)new Vector1D(i.getInf()));
/*      */         
/* 1111 */         Vector2D endV = Double.isInfinite(i.getSup()) ? null : line.toSpace((Point<Euclidean1D>)new Vector1D(i.getSup()));
/*      */ 
/*      */ 
/*      */         
/* 1115 */         BSPTree<Euclidean2D> startN = selectClosest(startV, splitters);
/* 1116 */         BSPTree<Euclidean2D> endN = selectClosest(endV, splitters);
/*      */         
/* 1118 */         if (reversed) {
/* 1119 */           this.segments.add(new PolygonsSet.ConnectableSegment(endV, startV, line.getReverse(), node, endN, startN));
/*      */           continue;
/*      */         } 
/* 1122 */         this.segments.add(new PolygonsSet.ConnectableSegment(startV, endV, line, node, startN, endN));
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private BSPTree<Euclidean2D> selectClosest(Vector2D point, Iterable<BSPTree<Euclidean2D>> candidates) {
/* 1136 */       BSPTree<Euclidean2D> selected = null;
/* 1137 */       double min = Double.POSITIVE_INFINITY;
/*      */       
/* 1139 */       for (BSPTree<Euclidean2D> node : candidates) {
/* 1140 */         double distance = FastMath.abs(node.getCut().getHyperplane().getOffset((Point)point));
/* 1141 */         if (distance < min) {
/* 1142 */           selected = node;
/* 1143 */           min = distance;
/*      */         } 
/*      */       } 
/*      */       
/* 1147 */       return (min <= this.tolerance) ? selected : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public List<PolygonsSet.ConnectableSegment> getSegments() {
/* 1155 */       return this.segments;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/PolygonsSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
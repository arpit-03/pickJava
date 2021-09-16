/*      */ package edu.mines.jtk.mesh;
/*      */ 
/*      */ import edu.mines.jtk.util.Check;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.TreeSet;
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
/*      */ 
/*      */ public class TriSurf
/*      */ {
/*      */   private static final int FACE_MARK_MAX = 2147483646;
/*      */   
/*      */   public static class Node
/*      */   {
/*      */     public int index;
/*      */     public Object data;
/*      */     private TetMesh.Node _meshNode;
/*      */     private TriSurf.Face _face;
/*      */     private TriSurf.Edge _edgeBefore;
/*      */     private TriSurf.Edge _edgeAfter;
/*      */     
/*      */     public Node(float x, float y, float z) {
/*   59 */       this._meshNode = new TetMesh.Node(x, y, z);
/*   60 */       this._meshNode.data = this;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float x() {
/*   68 */       return this._meshNode.x();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float y() {
/*   76 */       return this._meshNode.y();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float z() {
/*   84 */       return this._meshNode.z();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInSurface() {
/*   92 */       return (this._face != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOnBoundary() {
/*  100 */       return (this._edgeBefore != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriSurf.Edge edgeBefore() {
/*  110 */       return this._edgeBefore;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriSurf.Edge edgeAfter() {
/*  120 */       return this._edgeAfter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] normalVector() {
/*  128 */       float[] vn = new float[3];
/*  129 */       normalVector(vn);
/*  130 */       return vn;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void normalVector(float[] vn) {
/*  138 */       vn[2] = 0.0F; vn[1] = 0.0F; vn[0] = 0.0F;
/*  139 */       TriSurf.FaceIterator fi = getFaces();
/*  140 */       while (fi.hasNext()) {
/*  141 */         TriSurf.Face face = fi.next();
/*  142 */         accNormalVector(face, vn);
/*      */       } 
/*  144 */       float x = vn[0];
/*  145 */       float y = vn[1];
/*  146 */       float z = vn[2];
/*  147 */       float s = 1.0F / (float)Math.sqrt((x * x + y * y + z * z));
/*  148 */       vn[0] = vn[0] * s;
/*  149 */       vn[1] = vn[1] * s;
/*  150 */       vn[2] = vn[2] * s;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int countFaces() {
/*  158 */       int nface = 0;
/*  159 */       TriSurf.FaceIterator fi = getFaces();
/*  160 */       while (fi.hasNext()) {
/*  161 */         fi.next();
/*  162 */         nface++;
/*      */       } 
/*  164 */       return nface;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriSurf.FaceIterator getFaces() {
/*  172 */       return new TriSurf.FaceIterator() {
/*      */           public boolean hasNext() {
/*  174 */             return (this._next != null);
/*      */           }
/*      */           public TriSurf.Face next() {
/*  177 */             if (this._next == null)
/*  178 */               throw new NoSuchElementException(); 
/*  179 */             TriSurf.Face face = this._next;
/*  180 */             loadNext();
/*  181 */             return face;
/*      */           }
/*  183 */           private TriSurf.Face _next = TriSurf.Node.this._face; private boolean _ccw = true;
/*      */           
/*      */           private void loadNext() {
/*  186 */             if (this._ccw) {
/*  187 */               this._next = TriSurf.Node.this.faceNext(this._next);
/*  188 */               if (this._next == null) {
/*  189 */                 this._ccw = false;
/*  190 */                 this._next = TriSurf.Node.this._face;
/*  191 */               } else if (this._next == TriSurf.Node.this._face) {
/*  192 */                 this._next = null;
/*      */               } 
/*      */             } 
/*  195 */             if (!this._ccw) {
/*  196 */               this._next = TriSurf.Node.this.facePrev(this._next);
/*      */             }
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     public String toString() {
/*  203 */       return this._meshNode.toString();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void validate() {
/*  211 */       assert this._meshNode != null;
/*  212 */       assert this._face == null || this._face.references(this);
/*  213 */       if (this._edgeBefore == null) {
/*  214 */         assert this._edgeAfter == null;
/*      */       } else {
/*  216 */         assert this == this._edgeBefore.nodeB();
/*  217 */         assert this == this._edgeAfter.nodeA();
/*  218 */         assert this == this._edgeBefore.nodeA().edgeAfter().nodeB();
/*  219 */         assert this == this._edgeAfter.nodeB().edgeBefore().nodeA();
/*      */       } 
/*  221 */       assert this._edgeBefore != null && this == this._edgeBefore
/*  222 */         .nodeB() && this._edgeAfter != null && this == this._edgeAfter
/*  223 */         .nodeA();
/*      */     }
/*      */     private void init() {
/*  226 */       this._face = null;
/*  227 */       this._edgeBefore = null;
/*  228 */       this._edgeAfter = null;
/*      */     }
/*      */     private void setFace(TriSurf.Face face) {
/*  231 */       this._face = face;
/*      */     }
/*      */     private void setEdgeBefore(TriSurf.Edge edgeBefore) {
/*  234 */       this._edgeBefore = edgeBefore;
/*      */     }
/*      */     private void setEdgeAfter(TriSurf.Edge edgeAfter) {
/*  237 */       this._edgeAfter = edgeAfter;
/*      */     }
/*      */     private TriSurf.Face face() {
/*  240 */       return this._face;
/*      */     }
/*      */     private TriSurf.Face faceNext(TriSurf.Face face) {
/*  243 */       if (this == face.nodeA())
/*  244 */         return face.faceB(); 
/*  245 */       if (this == face.nodeB()) {
/*  246 */         return face.faceC();
/*      */       }
/*  248 */       return face.faceA();
/*      */     }
/*      */     
/*      */     private TriSurf.Face facePrev(TriSurf.Face face) {
/*  252 */       if (this == face.nodeA())
/*  253 */         return face.faceC(); 
/*  254 */       if (this == face.nodeB()) {
/*  255 */         return face.faceA();
/*      */       }
/*  257 */       return face.faceB();
/*      */     }
/*      */     
/*      */     private static void accNormalVector(TriSurf.Face face, float[] v) {
/*  261 */       Node na = face.nodeA();
/*  262 */       Node nb = face.nodeB();
/*  263 */       Node nc = face.nodeC();
/*  264 */       float xa = na.x();
/*  265 */       float ya = na.y();
/*  266 */       float za = na.z();
/*  267 */       float xb = nb.x();
/*  268 */       float yb = nb.y();
/*  269 */       float zb = nb.z();
/*  270 */       float xc = nc.x();
/*  271 */       float yc = nc.y();
/*  272 */       float zc = nc.z();
/*  273 */       float x0 = xc - xa;
/*  274 */       float y0 = yc - ya;
/*  275 */       float z0 = zc - za;
/*  276 */       float x1 = xa - xb;
/*  277 */       float y1 = ya - yb;
/*  278 */       float z1 = za - zb;
/*  279 */       v[0] = v[0] + y0 * z1 - y1 * z0;
/*  280 */       v[1] = v[1] + x1 * z0 - x0 * z1;
/*  281 */       v[2] = v[2] + x0 * y1 - x1 * y0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Edge
/*      */   {
/*      */     private TetMesh.Edge _meshEdge;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TriSurf.Face _faceLeft;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TriSurf.Face _faceRight;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriSurf.Node nodeA() {
/*  310 */       return (TriSurf.Node)(this._meshEdge.nodeA()).data;
/*      */     }
/*      */     public TriSurf.Node nodeB() {
/*  313 */       return (TriSurf.Node)(this._meshEdge.nodeB()).data;
/*      */     }
/*      */     public TriSurf.Face faceLeft() {
/*  316 */       return this._faceLeft;
/*      */     }
/*      */     public TriSurf.Face faceRight() {
/*  319 */       return this._faceRight;
/*      */     }
/*      */     public TriSurf.Node nodeLeft() {
/*  322 */       return (this._faceLeft != null) ? TriSurf.otherNode(this._faceLeft, nodeA(), nodeB()) : null;
/*      */     }
/*      */     public TriSurf.Node nodeRight() {
/*  325 */       return (this._faceRight != null) ? TriSurf.otherNode(this._faceRight, nodeA(), nodeB()) : null;
/*      */     }
/*      */     public Edge edgeBefore() {
/*  328 */       return (nodeA())._edgeBefore;
/*      */     }
/*      */     public Edge edgeAfter() {
/*  331 */       return (nodeB())._edgeAfter;
/*      */     }
/*      */     public Edge mate() {
/*  334 */       return new Edge(this._meshEdge.mate(), this._faceRight);
/*      */     }
/*      */     public boolean isInSurface() {
/*  337 */       return (this._faceRight != null);
/*      */     }
/*      */     public boolean isOnBoundary() {
/*  340 */       return (this._faceLeft == null);
/*      */     }
/*      */     public boolean equals(Object object) {
/*  343 */       if (object == this)
/*  344 */         return true; 
/*  345 */       if (object != null && object.getClass() == getClass()) {
/*  346 */         Edge other = (Edge)object;
/*  347 */         return (other.nodeA() == nodeA() && other.nodeB() == nodeB());
/*      */       } 
/*  349 */       return false;
/*      */     }
/*      */     public int hashCode() {
/*  352 */       return nodeA().hashCode() ^ nodeB().hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private void validate() {
/*  358 */       assert this._meshEdge != null;
/*  359 */       assert this._faceLeft == null || this._faceLeft.references(nodeA(), nodeB());
/*  360 */       assert this._faceRight == null || this._faceRight.references(nodeA(), nodeB());
/*      */     }
/*      */     private Edge(TetMesh.Edge meshEdge, TriSurf.Face face) {
/*  363 */       this._meshEdge = meshEdge;
/*  364 */       TriSurf.Node nodeA = (TriSurf.Node)(meshEdge.nodeA()).data;
/*  365 */       TriSurf.Node nodeB = (TriSurf.Node)(meshEdge.nodeB()).data;
/*  366 */       TriSurf.Node nodeC = (face != null) ? TriSurf.otherNode(face, nodeA, nodeB) : null;
/*  367 */       Check.argument((face == null || nodeC != null), "face references edge");
/*  368 */       if (nodeC != null) {
/*  369 */         if (TriSurf.nodesInOrder(face, nodeA, nodeB, nodeC)) {
/*  370 */           this._faceLeft = face;
/*  371 */           this._faceRight = face.faceNabor(nodeC);
/*      */         } else {
/*  373 */           this._faceLeft = face.faceNabor(nodeC);
/*  374 */           this._faceRight = face;
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Face
/*      */   {
/*      */     public int index;
/*      */ 
/*      */ 
/*      */     
/*      */     public Object data;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Face _meshFace;
/*      */ 
/*      */ 
/*      */     
/*      */     private Face _faceA;
/*      */ 
/*      */ 
/*      */     
/*      */     private Face _faceB;
/*      */ 
/*      */ 
/*      */     
/*      */     private Face _faceC;
/*      */ 
/*      */ 
/*      */     
/*      */     private int _mark;
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Node nodeA() {
/*  414 */       return (TriSurf.Node)(this._meshFace.nodeA()).data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Node nodeB() {
/*  422 */       return (TriSurf.Node)(this._meshFace.nodeB()).data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Node nodeC() {
/*  430 */       return (TriSurf.Node)(this._meshFace.nodeC()).data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Face faceA() {
/*  438 */       return this._faceA;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Face faceB() {
/*  446 */       return this._faceB;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Face faceC() {
/*  454 */       return this._faceC;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Face mate() {
/*  462 */       return new Face(this._meshFace.mate());
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
/*      */     public final TriSurf.Node nodeNearest(float x, float y, float z) {
/*  474 */       TriSurf.Node na = nodeA();
/*  475 */       TriSurf.Node nb = nodeB();
/*  476 */       TriSurf.Node nc = nodeC();
/*  477 */       double da = TriSurf.distanceSquared(na, x, y, z);
/*  478 */       double db = TriSurf.distanceSquared(nb, x, y, z);
/*  479 */       double dc = TriSurf.distanceSquared(nc, x, y, z);
/*  480 */       double dmin = da;
/*  481 */       TriSurf.Node nmin = na;
/*  482 */       if (db < dmin) {
/*  483 */         dmin = db;
/*  484 */         nmin = nb;
/*      */       } 
/*  486 */       if (dc < dmin) {
/*  487 */         dmin = dc;
/*  488 */         nmin = nc;
/*      */       } 
/*  490 */       return nmin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Face faceNabor(TriSurf.Node node) {
/*  499 */       if (node == nodeA()) return this._faceA; 
/*  500 */       if (node == nodeB()) return this._faceB; 
/*  501 */       if (node == nodeC()) return this._faceC; 
/*  502 */       Check.argument(false, "node is referenced by face");
/*  503 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Node nodeNabor(Face faceNabor) {
/*  512 */       if (faceNabor._faceA == this) return faceNabor.nodeA(); 
/*  513 */       if (faceNabor._faceB == this) return faceNabor.nodeB(); 
/*  514 */       if (faceNabor._faceC == this) return faceNabor.nodeC(); 
/*  515 */       Check.argument(false, "faceNabor is a nabor of face");
/*  516 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double centerCircle(double[] cc) {
/*  525 */       TriSurf.Node na = nodeA();
/*  526 */       TriSurf.Node nb = nodeB();
/*  527 */       TriSurf.Node nc = nodeC();
/*  528 */       double xa = na.x();
/*  529 */       double ya = na.y();
/*  530 */       double za = na.z();
/*  531 */       double xb = nb.x();
/*  532 */       double yb = nb.y();
/*  533 */       double zb = nb.z();
/*  534 */       double xc = nc.x();
/*  535 */       double yc = nc.y();
/*  536 */       double zc = nc.z();
/*  537 */       Geometry.centerCircle3D(xa, ya, za, xb, yb, zb, xc, yc, zc, cc);
/*  538 */       double xcc = cc[0];
/*  539 */       double ycc = cc[1];
/*  540 */       double zcc = cc[2];
/*  541 */       double dx = xcc - xc;
/*  542 */       double dy = ycc - yc;
/*  543 */       double dz = zcc - yc;
/*  544 */       return dx * dx + dy * dy + dz * dz;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] centerCircle() {
/*  552 */       double[] cc = new double[3];
/*  553 */       centerCircle(cc);
/*  554 */       return cc;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float area() {
/*  562 */       return TriSurf.normalVector(this._meshFace, (float[])null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float[] normalVector() {
/*  570 */       float[] vn = new float[3];
/*  571 */       TriSurf.normalVector(this._meshFace, vn);
/*  572 */       return vn;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float normalVector(float[] vn) {
/*  581 */       return TriSurf.normalVector(this._meshFace, vn);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TriSurf.Node node) {
/*  590 */       return (node == nodeA() || node == nodeB() || node == nodeC());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TriSurf.Node node1, TriSurf.Node node2) {
/*  600 */       TriSurf.Node na = nodeA();
/*  601 */       TriSurf.Node nb = nodeB();
/*  602 */       TriSurf.Node nc = nodeC();
/*  603 */       if (node1 == na)
/*  604 */         return (node2 == nb || node2 == nc); 
/*  605 */       if (node1 == nb)
/*  606 */         return (node2 == na || node2 == nc); 
/*  607 */       if (node1 == nc) {
/*  608 */         return (node2 == na || node2 == nb);
/*      */       }
/*  610 */       return false;
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
/*      */     public boolean references(TriSurf.Node node1, TriSurf.Node node2, TriSurf.Node node3) {
/*  622 */       TriSurf.Node na = nodeA();
/*  623 */       TriSurf.Node nb = nodeB();
/*  624 */       TriSurf.Node nc = nodeC();
/*  625 */       if (node1 == na) {
/*  626 */         if (node2 == nb)
/*  627 */           return (node3 == nc); 
/*  628 */         if (node2 == nc) {
/*  629 */           return (node3 == nb);
/*      */         }
/*  631 */         return false;
/*      */       } 
/*  633 */       if (node1 == nb) {
/*  634 */         if (node2 == na)
/*  635 */           return (node3 == nc); 
/*  636 */         if (node2 == nc) {
/*  637 */           return (node3 == na);
/*      */         }
/*  639 */         return false;
/*      */       } 
/*  641 */       if (node1 == nc) {
/*  642 */         if (node2 == na)
/*  643 */           return (node3 == nb); 
/*  644 */         if (node2 == nb) {
/*  645 */           return (node3 == na);
/*      */         }
/*  647 */         return false;
/*      */       } 
/*      */       
/*  650 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void validate() {
/*  658 */       assert this._meshFace != null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Face(TetMesh.Face meshFace) {
/*  665 */       this._meshFace = meshFace;
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
/*      */   public static class FaceList
/*      */   {
/*      */     public final void add(TriSurf.Face face) {
/*  687 */       if (this._n == this._a.length) {
/*  688 */         TriSurf.Face[] t = new TriSurf.Face[this._a.length * 2];
/*  689 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  690 */         this._a = t;
/*      */       } 
/*  692 */       this._a[this._n++] = face;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Face remove(int index) {
/*  701 */       TriSurf.Face face = this._a[index];
/*  702 */       this._n--;
/*  703 */       if (this._n > index)
/*  704 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/*  705 */       return face;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Face[] trim() {
/*  713 */       if (this._n < this._a.length) {
/*  714 */         TriSurf.Face[] t = new TriSurf.Face[this._n];
/*  715 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  716 */         this._a = t;
/*      */       } 
/*  718 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/*  725 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nface() {
/*  733 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriSurf.Face[] faces() {
/*  741 */       return this._a;
/*      */     }
/*  743 */     private int _n = 0;
/*  744 */     private TriSurf.Face[] _a = new TriSurf.Face[64];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean addNode(Node node) {
/*  753 */     boolean added = this._mesh.addNode(node._meshNode);
/*  754 */     if (added)
/*  755 */       rebuild(); 
/*  756 */     return added;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean addNodes(Node[] nodes) {
/*  765 */     int nnode = nodes.length;
/*  766 */     int nadded = 0;
/*  767 */     for (int inode = 0; inode < nnode; inode++) {
/*  768 */       if (this._mesh.addNode((nodes[inode])._meshNode))
/*  769 */         nadded++; 
/*      */     } 
/*  771 */     if (nadded > 0)
/*  772 */       rebuild(); 
/*  773 */     return (nadded == nnode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeNode(Node node) {
/*  782 */     boolean removed = this._mesh.removeNode(node._meshNode);
/*  783 */     if (removed)
/*  784 */       rebuild(); 
/*  785 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeNodes(Node[] nodes) {
/*  794 */     int nnode = nodes.length;
/*  795 */     int nremoved = 0;
/*  796 */     for (int inode = 0; inode < nnode; inode++) {
/*  797 */       if (this._mesh.removeNode((nodes[inode])._meshNode))
/*  798 */         nremoved++; 
/*      */     } 
/*  800 */     if (nremoved > 0)
/*  801 */       rebuild(); 
/*  802 */     return (nremoved == nnode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countNodes() {
/*  810 */     return this._mesh.countNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countFaces() {
/*  818 */     return this._faceMap.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized NodeIterator getNodes() {
/*  826 */     return new NodeIterator() {
/*      */         public final boolean hasNext() {
/*  828 */           return this._i.hasNext();
/*      */         }
/*      */         public final TriSurf.Node next() {
/*  831 */           return (TriSurf.Node)(this._i.next()).data;
/*      */         }
/*  833 */         private TetMesh.NodeIterator _i = TriSurf.this._mesh.getNodes();
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized FaceIterator getFaces() {
/*  842 */     return new FaceIterator() {
/*      */         public final boolean hasNext() {
/*  844 */           return this._i.hasNext();
/*      */         }
/*      */         public final TriSurf.Face next() {
/*  847 */           return this._i.next();
/*      */         }
/*  849 */         private Iterator<TriSurf.Face> _i = TriSurf.this._faceMap.values().iterator();
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Node findNodeNearest(float x, float y, float z) {
/*  861 */     TetMesh.Node meshNode = this._mesh.findNodeNearest(x, y, z);
/*  862 */     return (Node)meshNode.data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Face[] getFaceNabors(Node node) {
/*  871 */     FaceList nabors = new FaceList();
/*  872 */     getFaceNabors(node, nabors);
/*  873 */     return nabors.trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void getFaceNabors(Node node, FaceList nabors) {
/*  882 */     clearFaceMarks();
/*  883 */     getFaceNabors(node, node._face, nabors);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Face findFace(Node node) {
/*  893 */     return node._face;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Face findFace(Node node1, Node node2) {
/*  904 */     Face face = findFace(node1);
/*  905 */     if (face != null) {
/*      */ 
/*      */       
/*  908 */       if (face.references(node2))
/*  909 */         return face; 
/*  910 */       Face face1 = face;
/*  911 */       face = node1.faceNext(face1);
/*  912 */       while (face != face1 && face != null) {
/*  913 */         if (face.references(node2))
/*  914 */           return face; 
/*  915 */         face = node1.faceNext(face);
/*      */       } 
/*  917 */       if (face == null) {
/*  918 */         face = node1.facePrev(face1);
/*  919 */         while (face != face1 && face != null) {
/*  920 */           if (face.references(node2))
/*  921 */             return face; 
/*  922 */           face = node1.facePrev(face);
/*      */         } 
/*      */       } 
/*      */     } 
/*  926 */     return null;
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
/*      */   public synchronized Face findFace(Node node1, Node node2, Node node3) {
/*  938 */     Face face = findFace(node1, node2);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  943 */     if (face != null) {
/*  944 */       if (face.references(node3))
/*  945 */         return face; 
/*  946 */       face = face.faceNabor(node3);
/*  947 */       if (face != null && face.references(node3))
/*  948 */         return face; 
/*      */     } 
/*  950 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Edge findEdge(Node nodeA, Node nodeB) {
/*  961 */     TetMesh.Edge meshEdge = findMeshEdge(nodeA, nodeB);
/*  962 */     Edge edge = getEdge(meshEdge);
/*  963 */     if (meshEdge != null && edge == null) {
/*  964 */       Face face = findFace(nodeA, nodeB);
/*  965 */       if (face != null) {
/*  966 */         Node nodeC = otherNode(face, nodeA, nodeB);
/*  967 */         if (nodesInOrder(face, nodeA, nodeB, nodeC))
/*  968 */           edge = new Edge(meshEdge, face); 
/*      */       } 
/*      */     } 
/*  971 */     return edge;
/*      */   }
/*      */   
/*      */   private static class EdgeFace
/*      */     implements Comparable<EdgeFace>
/*      */   {
/*      */     TriSurf.Edge edge;
/*      */     TriSurf.Face face;
/*      */     double grade;
/*      */     
/*      */     EdgeFace(TriSurf.Edge edge, TriSurf.Face face, double grade) {
/*  982 */       this.edge = edge;
/*  983 */       this.face = face;
/*  984 */       this.grade = grade;
/*      */     }
/*      */     public int compareTo(EdgeFace other) {
/*  987 */       double gradeOther = other.grade;
/*  988 */       if (this.grade < gradeOther)
/*  989 */         return -1; 
/*  990 */       if (this.grade > gradeOther) {
/*  991 */         return 1;
/*      */       }
/*  993 */       TriSurf.Edge edgeOther = other.edge;
/*  994 */       int hash = this.edge.hashCode();
/*  995 */       int hashOther = edgeOther.hashCode();
/*  996 */       if (hash < hashOther)
/*  997 */         return -1; 
/*  998 */       if (hash > hashOther) {
/*  999 */         return 1;
/*      */       }
/* 1001 */       return 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1010 */   private TetMesh _mesh = new TetMesh();
/*      */ 
/*      */   
/* 1013 */   private Set<TetMesh.Face> _faceSet = new HashSet<>();
/*      */ 
/*      */   
/* 1016 */   private Map<TetMesh.Face, Face> _faceMap = new HashMap<>();
/*      */ 
/*      */   
/* 1019 */   private Map<TetMesh.Edge, EdgeFace> _edgeMap = new HashMap<>();
/*      */ 
/*      */ 
/*      */   
/* 1023 */   private SortedSet<EdgeFace> _edgeQueue = new TreeSet<>();
/*      */   
/*      */   private int _faceMarkRed;
/*      */   private int _faceMarkBlue;
/*      */   
/*      */   private void validate() {
/* 1029 */     NodeIterator ni = getNodes();
/* 1030 */     while (ni.hasNext()) {
/* 1031 */       Node node = ni.next();
/* 1032 */       node.validate();
/*      */     } 
/* 1034 */     FaceIterator fi = getFaces();
/* 1035 */     while (fi.hasNext()) {
/* 1036 */       Face face = fi.next();
/* 1037 */       face.validate();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double distanceSquared(Node node, double x, double y, double z) {
/* 1048 */     double dx = x - node.x();
/* 1049 */     double dy = y - node.y();
/* 1050 */     double dz = z - node.z();
/* 1051 */     return dx * dx + dy * dy + dz * dz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Face findFace(Face face, Node n1, Node n2) {
/* 1060 */     if (face != null) {
/* 1061 */       mark(face);
/* 1062 */       Node na = face.nodeA();
/* 1063 */       Node nb = face.nodeB();
/* 1064 */       Node nc = face.nodeC();
/* 1065 */       Face fa = face.faceA();
/* 1066 */       Face fb = face.faceB();
/* 1067 */       Face fc = face.faceC();
/* 1068 */       if (n1 == na) {
/* 1069 */         if (n2 == nb || n2 == nc || (fb != null && 
/* 1070 */           !isMarked(fb) && (face = findFace(fb, n1, n2)) != null) || (fc != null && 
/* 1071 */           !isMarked(fc) && (face = findFace(fc, n1, n2)) != null))
/* 1072 */           return face; 
/* 1073 */       } else if (n1 == nb) {
/* 1074 */         if (n2 == nc || n2 == na || (fc != null && 
/* 1075 */           !isMarked(fc) && (face = findFace(fc, n1, n2)) != null) || (fa != null && 
/* 1076 */           !isMarked(fa) && (face = findFace(fa, n1, n2)) != null))
/* 1077 */           return face; 
/* 1078 */       } else if (n1 == nc) {
/* 1079 */         if (n2 == na || n2 == nb || (fa != null && 
/* 1080 */           !isMarked(fa) && (face = findFace(fa, n1, n2)) != null) || (fb != null && 
/* 1081 */           !isMarked(fb) && (face = findFace(fb, n1, n2)) != null))
/* 1082 */           return face; 
/*      */       } else {
/* 1084 */         assert false : "n1 is referenced by face";
/*      */       } 
/*      */     } 
/* 1087 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Face findFace(Face face, Node n1, Node n2, Node n3) {
/* 1096 */     if (face != null) {
/* 1097 */       mark(face);
/* 1098 */       Node na = face.nodeA();
/* 1099 */       Node nb = face.nodeB();
/* 1100 */       Node nc = face.nodeC();
/* 1101 */       Face fa = face.faceA();
/* 1102 */       Face fb = face.faceB();
/* 1103 */       Face fc = face.faceC();
/* 1104 */       if (n1 == na) {
/* 1105 */         if (n2 == nb) {
/* 1106 */           if (n3 == nc || (fc != null && 
/* 1107 */             !isMarked(fc) && (face = findFace(fc, n1, n2, n3)) != null))
/* 1108 */             return face; 
/* 1109 */         } else if (n2 == nc) {
/* 1110 */           if (n3 == nb || (fb != null && 
/* 1111 */             !isMarked(fb) && (face = findFace(fb, n1, n2, n3)) != null))
/* 1112 */             return face; 
/*      */         } else {
/* 1114 */           assert false : "n2 is referenced by face";
/*      */         } 
/* 1116 */       } else if (n1 == nb) {
/* 1117 */         if (n2 == na) {
/* 1118 */           if (n3 == nc || (fc != null && 
/* 1119 */             !isMarked(fc) && (face = findFace(fc, n1, n2, n3)) != null))
/* 1120 */             return face; 
/* 1121 */         } else if (n2 == nc) {
/* 1122 */           if (n3 == na || (fa != null && 
/* 1123 */             !isMarked(fa) && (face = findFace(fa, n1, n2, n3)) != null))
/* 1124 */             return face; 
/*      */         } else {
/* 1126 */           assert false : "n2 is referenced by face";
/*      */         } 
/* 1128 */       } else if (n1 == nc) {
/* 1129 */         if (n2 == na) {
/* 1130 */           if (n3 == nb || (fb != null && 
/* 1131 */             !isMarked(fb) && (face = findFace(fb, n1, n2, n3)) != null))
/* 1132 */             return face; 
/* 1133 */         } else if (n2 == nb) {
/* 1134 */           if (n3 == na || (fa != null && 
/* 1135 */             !isMarked(fa) && (face = findFace(fa, n1, n2, n3)) != null))
/* 1136 */             return face; 
/*      */         } else {
/* 1138 */           assert false : "n2 is referenced by face";
/*      */         } 
/*      */       } else {
/* 1141 */         assert false : "n1 is referenced by face";
/*      */       } 
/*      */     } 
/* 1144 */     return null;
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
/*      */   private void mark(Face face) {
/* 1156 */     face._mark = this._faceMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markRed(Face face) {
/* 1165 */     face._mark = this._faceMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markBlue(Face face) {
/* 1173 */     face._mark = this._faceMarkBlue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMarked(Face face) {
/* 1182 */     return (face._mark == this._faceMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMarkedRed(Face face) {
/* 1191 */     return (face._mark == this._faceMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMarkedBlue(Face face) {
/* 1200 */     return (face._mark == this._faceMarkBlue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void clearFaceMarks() {
/* 1210 */     if (this._faceMarkRed == 2147483646) {
/* 1211 */       Iterator<Face> fi = this._faceMap.values().iterator();
/* 1212 */       while (fi.hasNext()) {
/* 1213 */         Face face = fi.next();
/* 1214 */         face._mark = 0;
/*      */       } 
/* 1216 */       this._faceMarkRed = 0;
/* 1217 */       this._faceMarkBlue = 0;
/*      */     } 
/*      */ 
/*      */     
/* 1221 */     this._faceMarkRed++;
/* 1222 */     this._faceMarkBlue--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getFaceNabors(Node node, Face face, FaceList nabors) {
/* 1232 */     if (face != null) {
/* 1233 */       mark(face);
/* 1234 */       nabors.add(face);
/* 1235 */       Node na = face.nodeA();
/* 1236 */       Node nb = face.nodeB();
/* 1237 */       Node nc = face.nodeC();
/* 1238 */       Face fa = face.faceA();
/* 1239 */       Face fb = face.faceB();
/* 1240 */       Face fc = face.faceC();
/* 1241 */       if (node == na) {
/* 1242 */         if (fb != null && !isMarked(fb))
/* 1243 */           getFaceNabors(node, fb, nabors); 
/* 1244 */         if (fc != null && !isMarked(fc))
/* 1245 */           getFaceNabors(node, fc, nabors); 
/* 1246 */       } else if (node == nb) {
/* 1247 */         if (fc != null && !isMarked(fc))
/* 1248 */           getFaceNabors(node, fc, nabors); 
/* 1249 */         if (fa != null && !isMarked(fa))
/* 1250 */           getFaceNabors(node, fa, nabors); 
/* 1251 */       } else if (node == nc) {
/* 1252 */         if (fa != null && !isMarked(fa))
/* 1253 */           getFaceNabors(node, fa, nabors); 
/* 1254 */         if (fb != null && !isMarked(fb))
/* 1255 */           getFaceNabors(node, fb, nabors); 
/*      */       } else {
/* 1257 */         assert false : "node is referenced by face";
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private Edge getEdge(TetMesh.Edge meshEdge) {
/* 1263 */     EdgeFace edgeFace = this._edgeMap.get(meshEdge);
/* 1264 */     return (edgeFace != null) ? edgeFace.edge : null;
/*      */   }
/*      */   
/*      */   private EdgeFace getEdgeFace(Edge edge) {
/* 1268 */     return this._edgeMap.get(edge._meshEdge);
/*      */   }
/*      */   
/*      */   private EdgeFace getBestEdgeFace() {
/* 1272 */     return !this._edgeQueue.isEmpty() ? this._edgeQueue.last() : null;
/*      */   }
/*      */   
/*      */   private EdgeFace getNextEdgeFace(EdgeFace edgeFace) {
/* 1276 */     SortedSet<EdgeFace> headSet = this._edgeQueue.headSet(edgeFace);
/* 1277 */     return !headSet.isEmpty() ? headSet.last() : null;
/*      */   }
/*      */ 
/*      */   
/*      */   private EdgeFace addEdge(Edge edge) {
/* 1282 */     EdgeFace edgeFace = makeEdgeFace(edge);
/* 1283 */     assert edgeFace != null : "edgeFace!=null";
/* 1284 */     Object edgeFaceOld = this._edgeMap.put(edge._meshEdge, edgeFace);
/* 1285 */     assert edgeFaceOld == null : "edge was not mapped";
/* 1286 */     boolean added = this._edgeQueue.add(edgeFace);
/* 1287 */     assert added : "edgeFace was not in queue";
/* 1288 */     return edgeFace;
/*      */   }
/*      */ 
/*      */   
/*      */   private void removeEdge(Edge edge) {
/* 1293 */     EdgeFace edgeFace = getEdgeFace(edge);
/* 1294 */     assert edgeFace != null : "edgeFace!=null";
/* 1295 */     Object edgeFaceOld = this._edgeMap.remove(edge._meshEdge);
/* 1296 */     assert edgeFaceOld != null : "edge was mapped";
/* 1297 */     boolean removed = this._edgeQueue.remove(edgeFace);
/* 1298 */     assert removed : "edgeFace was in queue";
/*      */   }
/*      */ 
/*      */   
/*      */   private void addFace(Face face) {
/* 1303 */     boolean removed = (this._faceSet.remove(face._meshFace) || this._faceSet.remove(face._meshFace.mate()));
/* 1304 */     assert removed : "face not already in surface";
/* 1305 */     Face faceOld = this._faceMap.put(face._meshFace, face);
/* 1306 */     assert faceOld == null : "face not already in surface";
/*      */   }
/*      */   
/*      */   private void removeFace(Face face) {
/* 1310 */     this._faceMap.remove(face._meshFace);
/*      */   }
/*      */   
/*      */   private void init(Face face) {
/* 1314 */     trace("init: face=" + face);
/* 1315 */     trace("  meshFace A=" + face._meshFace.nodeA());
/* 1316 */     trace("  meshFace B=" + face._meshFace.nodeB());
/* 1317 */     trace("  meshFace C=" + face._meshFace.nodeC());
/* 1318 */     face._faceA = null;
/* 1319 */     face._faceB = null;
/* 1320 */     face._faceC = null;
/* 1321 */     Node nodeA = face.nodeA();
/* 1322 */     Node nodeB = face.nodeB();
/* 1323 */     Node nodeC = face.nodeC();
/* 1324 */     nodeA.setFace(face);
/* 1325 */     nodeB.setFace(face);
/* 1326 */     nodeC.setFace(face);
/* 1327 */     Edge edgeCB = makeEdge(nodeC, nodeB, face);
/* 1328 */     Edge edgeBA = makeEdge(nodeB, nodeA, face);
/* 1329 */     Edge edgeAC = makeEdge(nodeA, nodeC, face);
/* 1330 */     nodeA.setEdgeBefore(edgeBA);
/* 1331 */     nodeB.setEdgeBefore(edgeCB);
/* 1332 */     nodeC.setEdgeBefore(edgeAC);
/* 1333 */     nodeA.setEdgeAfter(edgeAC);
/* 1334 */     nodeB.setEdgeAfter(edgeBA);
/* 1335 */     nodeC.setEdgeAfter(edgeCB);
/* 1336 */     addEdge(edgeCB);
/* 1337 */     addEdge(edgeBA);
/* 1338 */     addEdge(edgeAC);
/* 1339 */     addFace(face);
/*      */   }
/*      */   
/*      */   private void extend(Edge edge, Face face) {
/* 1343 */     trace("extend: edge=" + edge + " face=" + face);
/* 1344 */     trace("  meshEdge A=" + edge._meshEdge.nodeA());
/* 1345 */     trace("  meshEdge B=" + edge._meshEdge.nodeB());
/* 1346 */     trace("  meshFace A=" + face._meshFace.nodeA());
/* 1347 */     trace("  meshFace B=" + face._meshFace.nodeB());
/* 1348 */     trace("  meshFace C=" + face._meshFace.nodeC());
/* 1349 */     assert edge.isOnBoundary();
/* 1350 */     Node nodeA = edge.nodeA();
/* 1351 */     Node nodeB = edge.nodeB();
/* 1352 */     Node nodeC = otherNode(face, nodeA, nodeB);
/* 1353 */     nodeC.setFace(face);
/* 1354 */     linkFaces(face, nodeC, edge.faceRight(), edge.nodeRight());
/* 1355 */     Edge edgeAC = makeEdge(nodeA, nodeC, face);
/* 1356 */     Edge edgeCB = makeEdge(nodeC, nodeB, face);
/* 1357 */     nodeA.setEdgeAfter(edgeAC);
/* 1358 */     nodeB.setEdgeBefore(edgeCB);
/* 1359 */     nodeC.setEdgeAfter(edgeCB);
/* 1360 */     nodeC.setEdgeBefore(edgeAC);
/* 1361 */     removeEdge(edge);
/* 1362 */     addFace(face);
/* 1363 */     addEdge(edgeAC);
/* 1364 */     addEdge(edgeCB);
/*      */   }
/*      */   
/*      */   private void fillEar(Edge edge, Face face) {
/* 1368 */     trace("fillEar: edge=" + edge + " face=" + face);
/* 1369 */     trace("  meshEdge A=" + edge._meshEdge.nodeA());
/* 1370 */     trace("  meshEdge B=" + edge._meshEdge.nodeB());
/* 1371 */     trace("  meshFace A=" + face._meshFace.nodeA());
/* 1372 */     trace("  meshFace B=" + face._meshFace.nodeB());
/* 1373 */     trace("  meshFace C=" + face._meshFace.nodeC());
/* 1374 */     Node nodeA = edge.nodeA();
/* 1375 */     Node nodeB = edge.nodeB();
/* 1376 */     Node nodeC = otherNode(face, nodeA, nodeB);
/* 1377 */     Edge edge1 = nodeC.edgeBefore();
/* 1378 */     Edge edge2 = nodeC.edgeAfter();
/* 1379 */     Node node1 = edge1.nodeA();
/* 1380 */     Node node2 = edge2.nodeB();
/* 1381 */     if (node2 == nodeA) {
/* 1382 */       linkFaces(face, nodeC, edge.faceRight(), edge.nodeRight());
/* 1383 */       linkFaces(face, nodeB, edge2.faceRight(), edge2.nodeRight());
/* 1384 */       Edge edgeCB = makeEdge(nodeC, nodeB, face);
/* 1385 */       nodeC.setEdgeAfter(edgeCB);
/* 1386 */       nodeB.setEdgeBefore(edgeCB);
/* 1387 */       nodeA.setEdgeAfter(null);
/* 1388 */       nodeA.setEdgeBefore(null);
/* 1389 */       removeEdge(edge);
/* 1390 */       removeEdge(edge2);
/* 1391 */       addFace(face);
/* 1392 */       addEdge(edgeCB);
/* 1393 */     } else if (node1 == nodeB) {
/* 1394 */       linkFaces(face, nodeC, edge.faceRight(), edge.nodeRight());
/* 1395 */       linkFaces(face, nodeA, edge1.faceRight(), edge1.nodeRight());
/* 1396 */       Edge edgeAC = makeEdge(nodeA, nodeC, face);
/* 1397 */       nodeA.setEdgeAfter(edgeAC);
/* 1398 */       nodeC.setEdgeBefore(edgeAC);
/* 1399 */       nodeB.setEdgeAfter(null);
/* 1400 */       nodeB.setEdgeBefore(null);
/* 1401 */       removeEdge(edge);
/* 1402 */       removeEdge(edge1);
/* 1403 */       addFace(face);
/* 1404 */       addEdge(edgeAC);
/*      */     } else {
/* 1406 */       assert false : "ear is valid";
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fillHole(Edge edge, Face face) {
/* 1411 */     trace("fillHole: edge=" + edge + " face=" + face);
/* 1412 */     trace("  meshEdge A=" + edge._meshEdge.nodeA());
/* 1413 */     trace("  meshEdge B=" + edge._meshEdge.nodeB());
/* 1414 */     trace("  meshFace A=" + face._meshFace.nodeA());
/* 1415 */     trace("  meshFace B=" + face._meshFace.nodeB());
/* 1416 */     trace("  meshFace C=" + face._meshFace.nodeC());
/* 1417 */     Edge edgeAB = edge;
/* 1418 */     Edge edgeBC = edge.edgeAfter();
/* 1419 */     Edge edgeCA = edge.edgeBefore();
/* 1420 */     assert edgeAB.isOnBoundary();
/* 1421 */     assert edgeBC.isOnBoundary();
/* 1422 */     assert edgeCA.isOnBoundary();
/* 1423 */     Face faceAB = edgeAB.faceRight();
/* 1424 */     Face faceBC = edgeBC.faceRight();
/* 1425 */     Face faceCA = edgeCA.faceRight();
/* 1426 */     Node nodeA = edgeAB.nodeA();
/* 1427 */     Node nodeB = edgeBC.nodeA();
/* 1428 */     Node nodeC = edgeCA.nodeA();
/* 1429 */     linkFaces(face, nodeA, faceBC, otherNode(faceBC, nodeB, nodeC));
/* 1430 */     linkFaces(face, nodeB, faceCA, otherNode(faceCA, nodeA, nodeC));
/* 1431 */     linkFaces(face, nodeC, faceAB, otherNode(faceAB, nodeA, nodeB));
/* 1432 */     nodeA.setEdgeBefore(null);
/* 1433 */     nodeB.setEdgeBefore(null);
/* 1434 */     nodeC.setEdgeBefore(null);
/* 1435 */     nodeA.setEdgeAfter(null);
/* 1436 */     nodeB.setEdgeAfter(null);
/* 1437 */     nodeC.setEdgeAfter(null);
/* 1438 */     removeEdge(edgeAB);
/* 1439 */     removeEdge(edgeBC);
/* 1440 */     removeEdge(edgeCA);
/* 1441 */     addFace(face);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EdgeFace findTwin(EdgeFace edgeFace) {
/* 1450 */     Edge edge = edgeFace.edge;
/* 1451 */     Face face = edgeFace.face;
/* 1452 */     double grade = edgeFace.grade;
/* 1453 */     Node nodeA = edge.nodeA();
/* 1454 */     Node nodeB = edge.nodeB();
/* 1455 */     Node nodeC = otherNode(face, nodeA, nodeB);
/* 1456 */     assert nodeA.isOnBoundary();
/* 1457 */     assert nodeB.isOnBoundary();
/* 1458 */     assert nodeC.isOnBoundary();
/* 1459 */     Node node1 = nodeC.edgeBefore().nodeA();
/* 1460 */     assert node1 != nodeA;
/* 1461 */     assert node1 != nodeB;
/* 1462 */     if (node1.isOnBoundary()) {
/* 1463 */       Edge edgeTwin = node1.edgeAfter();
/* 1464 */       assert nodeC == edgeTwin.nodeB();
/* 1465 */       removeEdge(edgeTwin);
/* 1466 */       EdgeFace edgeFaceTwin = addEdge(edgeTwin);
/* 1467 */       Face faceTwin = edgeFaceTwin.face;
/* 1468 */       double gradeTwin = edgeFaceTwin.grade;
/* 1469 */       if (faceTwin != null && 
/* 1470 */         nodesInOrder(faceTwin, node1, nodeC, nodeB) && gradeTwin > grade)
/*      */       {
/* 1472 */         return edgeFaceTwin; } 
/*      */     } 
/* 1474 */     Node node2 = nodeC.edgeAfter().nodeB();
/* 1475 */     assert node2 != nodeA;
/* 1476 */     assert node2 != nodeB;
/* 1477 */     if (node2.isOnBoundary()) {
/* 1478 */       Edge edgeTwin = node2.edgeBefore();
/* 1479 */       assert nodeC == edgeTwin.nodeA();
/* 1480 */       removeEdge(edgeTwin);
/* 1481 */       EdgeFace edgeFaceTwin = addEdge(edgeTwin);
/* 1482 */       Face faceTwin = edgeFaceTwin.face;
/* 1483 */       double gradeTwin = edgeFaceTwin.grade;
/* 1484 */       if (faceTwin != null && 
/* 1485 */         nodesInOrder(faceTwin, node2, nodeA, nodeC) && gradeTwin > grade)
/*      */       {
/* 1487 */         return edgeFaceTwin; } 
/*      */     } 
/* 1489 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void glue(Edge edge, Face face, Edge edgeTwin, Face faceTwin) {
/* 1496 */     trace("glue: edge=" + edge + " face=" + face);
/* 1497 */     trace("  meshEdge A=" + edge._meshEdge.nodeA());
/* 1498 */     trace("  meshEdge B=" + edge._meshEdge.nodeB());
/* 1499 */     trace("  meshFace A=" + face._meshFace.nodeA());
/* 1500 */     trace("  meshFace B=" + face._meshFace.nodeB());
/* 1501 */     trace("  meshFace C=" + face._meshFace.nodeC());
/* 1502 */     trace("  meshEdgeTwin A=" + edgeTwin._meshEdge.nodeA());
/* 1503 */     trace("  meshEdgeTwin B=" + edgeTwin._meshEdge.nodeB());
/* 1504 */     trace("  meshFaceTwin A=" + faceTwin._meshFace.nodeA());
/* 1505 */     trace("  meshFaceTwin B=" + faceTwin._meshFace.nodeB());
/* 1506 */     trace("  meshFaceTwin C=" + faceTwin._meshFace.nodeC());
/* 1507 */     Node nodeA = edge.nodeA();
/* 1508 */     Node nodeB = edge.nodeB();
/* 1509 */     Node nodeC = otherNode(face, nodeA, nodeB);
/* 1510 */     assert nodeA.isOnBoundary();
/* 1511 */     assert nodeB.isOnBoundary();
/* 1512 */     assert nodeC.isOnBoundary();
/*      */ 
/*      */     
/* 1515 */     removeEdge(edge);
/* 1516 */     removeEdge(edgeTwin);
/* 1517 */     addFace(face);
/* 1518 */     addFace(faceTwin);
/*      */ 
/*      */     
/* 1521 */     if (faceTwin.references(nodeA)) {
/* 1522 */       Node nodeD = nodeC.edgeAfter().nodeB();
/* 1523 */       assert nodeD.isOnBoundary();
/*      */ 
/*      */       
/* 1526 */       if (nodeD.edgeAfter() == nodeA.edgeBefore()) {
/* 1527 */         Edge edgeDA = nodeD.edgeAfter();
/* 1528 */         nodeA.setEdgeBefore(null);
/* 1529 */         nodeD.setEdgeBefore(null);
/* 1530 */         nodeA.setEdgeAfter(null);
/* 1531 */         nodeD.setEdgeAfter(null);
/* 1532 */         removeEdge(edgeDA);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1537 */         Edge edgeAD = makeEdge(nodeA, nodeD, faceTwin);
/* 1538 */         nodeA.setEdgeAfter(edgeAD);
/* 1539 */         nodeD.setEdgeBefore(edgeAD);
/* 1540 */         addEdge(edgeAD);
/*      */       } 
/*      */ 
/*      */       
/* 1544 */       Edge edgeCB = makeEdge(nodeC, nodeB, face);
/* 1545 */       nodeC.setEdgeAfter(edgeCB);
/* 1546 */       nodeB.setEdgeBefore(edgeCB);
/* 1547 */       addEdge(edgeCB);
/* 1548 */       linkFaces(face, nodeB, faceTwin, nodeD);
/* 1549 */       linkFaces(face, nodeC, edge.faceRight(), edge.nodeRight());
/* 1550 */       linkFaces(faceTwin, nodeA, edgeTwin.faceRight(), edgeTwin.nodeRight());
/*      */ 
/*      */     
/*      */     }
/* 1554 */     else if (faceTwin.references(nodeB)) {
/* 1555 */       Node nodeD = nodeC.edgeBefore().nodeA();
/* 1556 */       assert nodeD.isOnBoundary();
/*      */ 
/*      */       
/* 1559 */       if (nodeD.edgeBefore() == nodeB.edgeAfter()) {
/* 1560 */         Edge edgeBD = nodeD.edgeBefore();
/* 1561 */         nodeB.setEdgeBefore(null);
/* 1562 */         nodeD.setEdgeBefore(null);
/* 1563 */         nodeB.setEdgeAfter(null);
/* 1564 */         nodeD.setEdgeAfter(null);
/* 1565 */         removeEdge(edgeBD);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1570 */         Edge edgeDB = makeEdge(nodeD, nodeB, faceTwin);
/* 1571 */         nodeD.setEdgeAfter(edgeDB);
/* 1572 */         nodeB.setEdgeBefore(edgeDB);
/* 1573 */         addEdge(edgeDB);
/*      */       } 
/*      */ 
/*      */       
/* 1577 */       Edge edgeAC = makeEdge(nodeA, nodeC, face);
/* 1578 */       nodeA.setEdgeAfter(edgeAC);
/* 1579 */       nodeC.setEdgeBefore(edgeAC);
/* 1580 */       addEdge(edgeAC);
/* 1581 */       linkFaces(face, nodeA, faceTwin, nodeD);
/* 1582 */       linkFaces(face, nodeC, edge.faceRight(), edge.nodeRight());
/* 1583 */       linkFaces(faceTwin, nodeB, edgeTwin.faceRight(), edgeTwin.nodeRight());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean stitch(EdgeFace edgeFace) {
/* 1589 */     Edge edge = edgeFace.edge;
/* 1590 */     Face face = edgeFace.face;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1596 */     Node nodeA = edge.nodeA();
/* 1597 */     Node nodeB = edge.nodeB();
/* 1598 */     Node nodeC = otherNode(face, nodeA, nodeB);
/*      */ 
/*      */     
/* 1601 */     if (!validForFace(nodeA, nodeB, nodeC)) {
/* 1602 */       removeEdge(edge);
/* 1603 */       addEdge(edge);
/* 1604 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1608 */     if (!nodeC.isInSurface()) {
/* 1609 */       extend(edge, face);
/* 1610 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1614 */     if (nodeC.isOnBoundary()) {
/*      */ 
/*      */       
/* 1617 */       Node node1 = nodeC.edgeBefore().nodeA();
/* 1618 */       Node node2 = nodeC.edgeAfter().nodeB();
/*      */ 
/*      */       
/* 1621 */       if (node1 == nodeB && node2 == nodeA) {
/* 1622 */         fillHole(edge, face);
/* 1623 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1627 */       if (node1 == nodeB || node2 == nodeA) {
/* 1628 */         fillEar(edge, face);
/* 1629 */         return true;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1636 */       EdgeFace edgeFaceTwin = findTwin(edgeFace);
/* 1637 */       if (edgeFaceTwin != null) {
/* 1638 */         Edge edgeTwin = edgeFaceTwin.edge;
/* 1639 */         Face faceTwin = edgeFaceTwin.face;
/* 1640 */         glue(edge, face, edgeTwin, faceTwin);
/* 1641 */         return true;
/*      */       } 
/* 1643 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1650 */     assert false : "valid face for extend, fill ear, fill hole, or glue";
/* 1651 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void rebuild() {
/* 1656 */     trace("rebuild");
/* 1657 */     init();
/* 1658 */     while (surf());
/*      */   }
/*      */ 
/*      */   
/*      */   private void init() {
/* 1663 */     trace("  init: ntets=" + this._mesh.countTets());
/* 1664 */     this._faceSet.clear();
/* 1665 */     this._faceMap.clear();
/* 1666 */     this._edgeMap.clear();
/* 1667 */     this._edgeQueue.clear();
/* 1668 */     TetMesh.TetIterator ti = this._mesh.getTets();
/* 1669 */     while (ti.hasNext()) {
/* 1670 */       TetMesh.Tet tet = ti.next();
/* 1671 */       TetMesh.Node a = tet.nodeA();
/* 1672 */       TetMesh.Node b = tet.nodeB();
/* 1673 */       TetMesh.Node c = tet.nodeC();
/* 1674 */       TetMesh.Node d = tet.nodeD();
/* 1675 */       TetMesh.Face[] meshFaces = { new TetMesh.Face(a, b, c, tet), new TetMesh.Face(b, d, c, tet), new TetMesh.Face(c, d, a, tet), new TetMesh.Face(d, b, a, tet) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1681 */       for (int i = 0; i < 4; i++) {
/* 1682 */         TetMesh.Face meshFacei = meshFaces[i];
/* 1683 */         if (!this._faceSet.contains(meshFacei.mate())) {
/* 1684 */           this._faceSet.add(meshFacei);
/* 1685 */           trace("  init: added face" + meshFacei);
/* 1686 */           trace("        node A=" + meshFacei.nodeA());
/* 1687 */           trace("        node B=" + meshFacei.nodeB());
/* 1688 */           trace("        node C=" + meshFacei.nodeC());
/*      */         } 
/*      */       } 
/* 1691 */       ((Node)a.data).init();
/* 1692 */       ((Node)b.data).init();
/* 1693 */       ((Node)c.data).init();
/* 1694 */       ((Node)d.data).init();
/*      */     } 
/* 1696 */     trace("  init: _faceSet size=" + this._faceSet.size());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean surf() {
/* 1705 */     int nface = countFaces();
/*      */ 
/*      */     
/* 1708 */     if (this._faceSet.isEmpty()) {
/* 1709 */       return false;
/*      */     }
/*      */     
/* 1712 */     TetMesh.Face meshFace = null;
/* 1713 */     double rrmin = Double.MAX_VALUE;
/* 1714 */     double[] cc = new double[3];
/* 1715 */     Iterator<TetMesh.Face> mfi = this._faceSet.iterator();
/* 1716 */     while (mfi.hasNext()) {
/* 1717 */       TetMesh.Face meshFacei = mfi.next();
/* 1718 */       double rr = meshFacei.centerCircle(cc);
/* 1719 */       if (rr < rrmin) {
/* 1720 */         meshFace = meshFacei;
/* 1721 */         rrmin = rr;
/*      */       } 
/*      */     } 
/* 1724 */     assert meshFace != null;
/*      */ 
/*      */     
/* 1727 */     Face face = new Face(meshFace);
/* 1728 */     init(face);
/*      */ 
/*      */     
/* 1731 */     trace("  surf: stitching");
/* 1732 */     EdgeFace edgeFace = getBestEdgeFace();
/* 1733 */     while (edgeFace != null && edgeFace.face != null) {
/* 1734 */       if (stitch(edgeFace)) {
/* 1735 */         edgeFace = getBestEdgeFace(); continue;
/*      */       } 
/* 1737 */       edgeFace = getNextEdgeFace(edgeFace);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1742 */     trace("  surf: removing faces");
/* 1743 */     ArrayList<TetMesh.Face> faceList = new ArrayList<>();
/* 1744 */     mfi = this._faceSet.iterator();
/* 1745 */     while (mfi.hasNext()) {
/* 1746 */       TetMesh.Face meshFacei = mfi.next();
/* 1747 */       Node nodeA = (Node)(meshFacei.nodeA()).data;
/* 1748 */       Node nodeB = (Node)(meshFacei.nodeB()).data;
/* 1749 */       Node nodeC = (Node)(meshFacei.nodeC()).data;
/* 1750 */       if (nodeA.isInSurface() || nodeB.isInSurface() || nodeC.isInSurface()) {
/* 1751 */         faceList.add(meshFacei);
/*      */       }
/*      */     } 
/* 1754 */     mfi = faceList.iterator();
/* 1755 */     while (mfi.hasNext()) {
/* 1756 */       TetMesh.Face meshFacei = mfi.next();
/* 1757 */       this._faceSet.remove(meshFacei);
/*      */     } 
/*      */ 
/*      */     
/* 1761 */     trace("  surf: more faces = " + ((countFaces() > nface) ? 1 : 0));
/* 1762 */     return (countFaces() > nface);
/*      */   }
/*      */   
/*      */   private static boolean nodesInOrder(Face face, Node na, Node nb, Node nc) {
/* 1766 */     Node fa = face.nodeA();
/* 1767 */     Node fb = face.nodeB();
/* 1768 */     Node fc = face.nodeC();
/* 1769 */     return ((na == fa && nb == fb && nc == fc) || (na == fb && nb == fc && nc == fa) || (na == fc && nb == fa && nc == fb));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Node otherNode(Face face, Node na, Node nb) {
/* 1775 */     Node fa = face.nodeA();
/* 1776 */     Node fb = face.nodeB();
/* 1777 */     Node fc = face.nodeC();
/* 1778 */     if (na == fa) {
/* 1779 */       if (nb == fb)
/* 1780 */         return fc; 
/* 1781 */       if (nb == fc) {
/* 1782 */         return fb;
/*      */       }
/* 1784 */       return null;
/*      */     } 
/* 1786 */     if (na == fb) {
/* 1787 */       if (nb == fa)
/* 1788 */         return fc; 
/* 1789 */       if (nb == fc) {
/* 1790 */         return fa;
/*      */       }
/* 1792 */       return null;
/*      */     } 
/* 1794 */     if (na == fc) {
/* 1795 */       if (nb == fa)
/* 1796 */         return fb; 
/* 1797 */       if (nb == fb) {
/* 1798 */         return fa;
/*      */       }
/* 1800 */       return null;
/*      */     } 
/*      */     
/* 1803 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TetMesh.Node otherNode(TetMesh.Face face, TetMesh.Node na, TetMesh.Node nb) {
/* 1810 */     TetMesh.Node fa = face.nodeA();
/* 1811 */     TetMesh.Node fb = face.nodeB();
/* 1812 */     TetMesh.Node fc = face.nodeC();
/* 1813 */     if (na == fa) {
/* 1814 */       if (nb == fb)
/* 1815 */         return fc; 
/* 1816 */       if (nb == fc) {
/* 1817 */         return fb;
/*      */       }
/* 1819 */       return null;
/*      */     } 
/* 1821 */     if (na == fb) {
/* 1822 */       if (nb == fa)
/* 1823 */         return fc; 
/* 1824 */       if (nb == fc) {
/* 1825 */         return fa;
/*      */       }
/* 1827 */       return null;
/*      */     } 
/* 1829 */     if (na == fc) {
/* 1830 */       if (nb == fa)
/* 1831 */         return fb; 
/* 1832 */       if (nb == fb) {
/* 1833 */         return fa;
/*      */       }
/* 1835 */       return null;
/*      */     } 
/*      */     
/* 1838 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void linkFaces(Face face, Node node, Face faceNabor, Node nodeNabor) {
/* 1845 */     if (face != null) {
/* 1846 */       if (node == face.nodeA()) {
/* 1847 */         face._faceA = faceNabor;
/* 1848 */       } else if (node == face.nodeB()) {
/* 1849 */         face._faceB = faceNabor;
/* 1850 */       } else if (node == face.nodeC()) {
/* 1851 */         face._faceC = faceNabor;
/*      */       } else {
/* 1853 */         assert false : "node referenced by face";
/*      */       } 
/*      */     }
/* 1856 */     if (faceNabor != null) {
/* 1857 */       if (nodeNabor == faceNabor.nodeA()) {
/* 1858 */         faceNabor._faceA = face;
/* 1859 */       } else if (nodeNabor == faceNabor.nodeB()) {
/* 1860 */         faceNabor._faceB = face;
/* 1861 */       } else if (nodeNabor == faceNabor.nodeC()) {
/* 1862 */         faceNabor._faceC = face;
/*      */       } else {
/* 1864 */         assert false : "nodeNabor referenced by faceNabor";
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   static float normalVector(TetMesh.Face meshFace, float[] v) {
/* 1870 */     TetMesh.Node na = meshFace.nodeA();
/* 1871 */     TetMesh.Node nb = meshFace.nodeB();
/* 1872 */     TetMesh.Node nc = meshFace.nodeC();
/* 1873 */     double xa = na.x();
/* 1874 */     double ya = na.y();
/* 1875 */     double za = na.z();
/* 1876 */     double xb = nb.x();
/* 1877 */     double yb = nb.y();
/* 1878 */     double zb = nb.z();
/* 1879 */     double xc = nc.x();
/* 1880 */     double yc = nc.y();
/* 1881 */     double zc = nc.z();
/* 1882 */     double x0 = xc - xa;
/* 1883 */     double y0 = yc - ya;
/* 1884 */     double z0 = zc - za;
/* 1885 */     double x1 = xa - xb;
/* 1886 */     double y1 = ya - yb;
/* 1887 */     double z1 = za - zb;
/* 1888 */     double x2 = y0 * z1 - y1 * z0;
/* 1889 */     double y2 = x1 * z0 - x0 * z1;
/* 1890 */     double z2 = x0 * y1 - x1 * y0;
/* 1891 */     double alpha = x2 * x2 + y2 * y2 + z2 * z2;
/* 1892 */     double delta = Math.sqrt(alpha);
/* 1893 */     double scale = (delta > 0.0D) ? (1.0D / delta) : 1.0D;
/* 1894 */     if (v != null) {
/* 1895 */       v[0] = (float)(x2 * scale);
/* 1896 */       v[1] = (float)(y2 * scale);
/* 1897 */       v[2] = (float)(z2 * scale);
/*      */     } 
/* 1899 */     return (float)(0.5D * scale * alpha);
/*      */   }
/*      */   
/*      */   static double normalVector(TetMesh.Face meshFace, double[] v) {
/* 1903 */     TetMesh.Node na = meshFace.nodeA();
/* 1904 */     TetMesh.Node nb = meshFace.nodeB();
/* 1905 */     TetMesh.Node nc = meshFace.nodeC();
/* 1906 */     double xa = na.x();
/* 1907 */     double ya = na.y();
/* 1908 */     double za = na.z();
/* 1909 */     double xb = nb.x();
/* 1910 */     double yb = nb.y();
/* 1911 */     double zb = nb.z();
/* 1912 */     double xc = nc.x();
/* 1913 */     double yc = nc.y();
/* 1914 */     double zc = nc.z();
/* 1915 */     double x0 = xc - xa;
/* 1916 */     double y0 = yc - ya;
/* 1917 */     double z0 = zc - za;
/* 1918 */     double x1 = xa - xb;
/* 1919 */     double y1 = ya - yb;
/* 1920 */     double z1 = za - zb;
/* 1921 */     double x2 = y0 * z1 - y1 * z0;
/* 1922 */     double y2 = x1 * z0 - x0 * z1;
/* 1923 */     double z2 = x0 * y1 - x1 * y0;
/* 1924 */     double alpha = x2 * x2 + y2 * y2 + z2 * z2;
/* 1925 */     double delta = Math.sqrt(alpha);
/* 1926 */     double scale = (delta > 0.0D) ? (1.0D / delta) : 1.0D;
/* 1927 */     if (v != null) {
/* 1928 */       v[0] = x2 * scale;
/* 1929 */       v[1] = y2 * scale;
/* 1930 */       v[2] = z2 * scale;
/*      */     } 
/* 1932 */     return 0.5D * scale * alpha;
/*      */   }
/*      */   
/*      */   private static double angle(TetMesh.Face face1, TetMesh.Face face2) {
/* 1936 */     double[] v1 = new double[3];
/* 1937 */     double[] v2 = new double[3];
/* 1938 */     normalVector(face1, v1);
/* 1939 */     normalVector(face2, v2);
/* 1940 */     double cos12 = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
/* 1941 */     return Math.acos(cos12);
/*      */   }
/*      */   
/*      */   private TetMesh.Edge findMeshEdge(Node nodeA, Node nodeB) {
/* 1945 */     TetMesh.Node meshNodeA = nodeA._meshNode;
/* 1946 */     TetMesh.Node meshNodeB = nodeB._meshNode;
/* 1947 */     TetMesh.Edge meshEdge = this._mesh.findEdge(meshNodeA, meshNodeB);
/* 1948 */     if (meshEdge != null && meshNodeA != meshEdge.nodeA())
/* 1949 */       meshEdge = meshEdge.mate(); 
/* 1950 */     return meshEdge;
/*      */   }
/*      */   
/*      */   private Edge makeEdge(Node nodeA, Node nodeB, Face face) {
/* 1954 */     TetMesh.Edge meshEdge = findMeshEdge(nodeA, nodeB);
/* 1955 */     return (meshEdge != null) ? new Edge(meshEdge, face) : null;
/*      */   }
/*      */   
/* 1958 */   private static final double VV_SLIVER = Math.cos(2.6179938779914944D);
/* 1959 */   private static final double VV_LARGE = Math.cos(1.7278759594743864D);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final boolean TRACE = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EdgeFace makeEdgeFace(Edge edge) {
/* 1971 */     assert edge.isOnBoundary();
/* 1972 */     Node nodeA = edge.nodeA();
/* 1973 */     Node nodeB = edge.nodeB();
/*      */ 
/*      */     
/* 1976 */     TetMesh.Face meshFace = (edge.faceRight())._meshFace;
/*      */ 
/*      */     
/* 1979 */     double[] v = new double[3];
/* 1980 */     normalVector(meshFace, v);
/*      */ 
/*      */     
/* 1983 */     TetMesh.Edge meshEdge = edge._meshEdge;
/* 1984 */     TetMesh.Node meshNodeA = meshEdge.nodeA();
/* 1985 */     TetMesh.Node meshNodeB = meshEdge.nodeB();
/*      */ 
/*      */     
/* 1988 */     double[] cc = new double[3];
/* 1989 */     double[] vi = new double[3];
/* 1990 */     double rrBest = Double.MAX_VALUE;
/* 1991 */     double vvBest = -1.0D;
/* 1992 */     TetMesh.Face mfBest = null;
/* 1993 */     TetMesh.Face mfMate = meshFace.mate();
/*      */ 
/*      */ 
/*      */     
/* 1997 */     TetMesh.Face[] meshFaces = this._mesh.getFaceNabors(meshEdge);
/* 1998 */     int n = meshFaces.length;
/*      */ 
/*      */     
/* 2001 */     for (int i = 0; i < n; i++) {
/* 2002 */       TetMesh.Face mf = meshFaces[i];
/*      */ 
/*      */       
/* 2005 */       if (!mf.equals(mfMate)) {
/*      */         Node nodeC;
/*      */ 
/*      */         
/* 2009 */         TetMesh.Node fa = mf.nodeA();
/* 2010 */         TetMesh.Node fb = mf.nodeB();
/* 2011 */         TetMesh.Node fc = mf.nodeC();
/*      */         
/* 2013 */         if (fc == meshNodeA) {
/* 2014 */           nodeC = (Node)fb.data;
/* 2015 */         } else if (fc == meshNodeB) {
/* 2016 */           nodeC = (Node)fa.data;
/*      */         } else {
/* 2018 */           nodeC = (Node)fc.data;
/*      */         } 
/*      */ 
/*      */         
/* 2022 */         if (validForFace(nodeA, nodeB, nodeC)) {
/*      */ 
/*      */           
/* 2025 */           normalVector(mf, vi);
/*      */ 
/*      */           
/* 2028 */           double vv = v[0] * vi[0] + v[1] * vi[1] + v[2] * vi[2];
/*      */ 
/*      */           
/* 2031 */           if (vv > VV_SLIVER) {
/*      */ 
/*      */             
/* 2034 */             double rr = mf.centerCircle(cc);
/*      */ 
/*      */             
/* 2037 */             if (rr < rrBest) {
/* 2038 */               rrBest = rr;
/* 2039 */               vvBest = vv;
/* 2040 */               mfBest = mf;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2049 */     assert !this._faceMap.containsKey(mfBest) && !this._faceMap.containsKey(mfBest);
/* 2050 */     Face face = (mfBest != null) ? new Face(mfBest) : null;
/* 2051 */     double grade = (vvBest > VV_LARGE) ? (1.0D / rrBest) : (vvBest - 1.0D);
/* 2052 */     if (grade <= 0.0D)
/* 2053 */       face = null; 
/* 2054 */     return new EdgeFace(edge, face, grade);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasInternalEdge(Node nodeA, Node nodeB) {
/* 2062 */     Face face = findFace(nodeA, nodeB);
/* 2063 */     if (face == null)
/* 2064 */       return false; 
/* 2065 */     face = face.faceNabor(otherNode(face, nodeA, nodeB));
/* 2066 */     if (face == null)
/* 2067 */       return false; 
/* 2068 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validForFace(Node nodeA, Node nodeB, Node nodeC) {
/* 2077 */     return (!nodeC.isInSurface() || (nodeC
/* 2078 */       .isOnBoundary() && 
/* 2079 */       !hasInternalEdge(nodeB, nodeC) && 
/* 2080 */       !hasInternalEdge(nodeC, nodeA)));
/*      */   }
/*      */   
/*      */   private static void trace(String s) {}
/*      */   
/*      */   public static interface FaceIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriSurf.Face next();
/*      */   }
/*      */   
/*      */   public static interface EdgeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriSurf.Edge next();
/*      */   }
/*      */   
/*      */   public static interface NodeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriSurf.Node next();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mesh/TriSurf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
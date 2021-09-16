/*      */ package edu.mines.jtk.mesh;
/*      */ 
/*      */ import edu.mines.jtk.util.Check;
/*      */ import edu.mines.jtk.util.MathPlus;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidClassException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.swing.event.EventListenerList;
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
/*      */ public class TriMesh
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private static final int NODE_MARK_MAX = 2147483646;
/*      */   private static final int TRI_MARK_MAX = 2147483646;
/*      */   private long _version;
/*      */   private int _nnode;
/*      */   private int _ntri;
/*      */   private Node _nroot;
/*      */   private Tri _troot;
/*      */   private HashSet<Node> _sampledNodes;
/*      */   private int _triMarkRed;
/*      */   private int _triMarkBlue;
/*      */   private int _nodeMarkRed;
/*      */   private int _nodeMarkBlue;
/*      */   private EdgeSet _edgeSet;
/*      */   private NodeSet _nodeSet;
/*      */   private NodeList _nodeList;
/*      */   private Node _nmin;
/*      */   private double _dmin;
/*      */   private TriList _deadTris;
/*      */   private int _nnodeListeners;
/*      */   private int _ntriListeners;
/*      */   private EventListenerList _listeners;
/*      */   private boolean _outerEnabled;
/*      */   private double _xminOuter;
/*      */   private double _yminOuter;
/*      */   private double _xmaxOuter;
/*      */   private double _ymaxOuter;
/*      */   private int _nnodeValues;
/*      */   private int _lnodeValues;
/*      */   private Map<String, NodePropertyMap> _nodePropertyMaps;
/*      */   static final boolean DEBUG = false;
/*      */   static final boolean TRACE = false;
/*      */   
/*      */   public static class Node
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     public int index;
/*      */     public Object data;
/*      */     private transient double _x;
/*      */     private transient double _y;
/*      */     private transient Node _prev;
/*      */     private transient Node _next;
/*      */     private transient int _mark;
/*      */     private transient TriMesh.Tri _tri;
/*      */     private transient int _hash;
/*      */     private transient Object[] _values;
/*      */     
/*      */     public Node(float x, float y) {
/*   83 */       this._prev = null;
/*   84 */       this._next = null;
/*   85 */       this._mark = 0;
/*   86 */       this._tri = null;
/*   87 */       this._hash = System.identityHashCode(this);
/*   88 */       setPosition(x, y);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float x() {
/*   96 */       return (float)this._x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float y() {
/*  104 */       return (float)this._y;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double xp() {
/*  112 */       return this._x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double yp() {
/*  120 */       return this._y;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Tri tri() {
/*  128 */       return this._tri;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  133 */       return "(" + x() + "," + y() + ")";
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
/*      */ 
/*      */     
/*      */     private static double perturb(float x, float p) {
/*  149 */       int m = Integer.MAX_VALUE;
/*  150 */       int i = Float.floatToIntBits(p);
/*  151 */       int j = 0;
/*  152 */       for (int k = 0; k < 32; k++, i >>= 1, j <<= 1) {
/*  153 */         j |= i & 0x1;
/*      */       }
/*  155 */       double xp = (x != 0.0F) ? x : 1.4012984643248171E-46D;
/*  156 */       xp *= 1.0D + j / 2.147483647E9D * 0.1D * 1.1920928955078125E-7D;
/*  157 */       assert (float)xp == x;
/*  158 */       return xp;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setPosition(float x, float y) {
/*  165 */       assert this._tri == null;
/*  166 */       this._x = perturb(x, 0.450599F * y);
/*  167 */       this._y = perturb(y, 0.298721F * x);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Tri
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int index;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object data;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int INNER_BIT = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int OUTER_BIT = 2;
/*      */ 
/*      */ 
/*      */     
/*      */     private static final int CENTER_BIT = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeA() {
/*  208 */       return this._n0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeB() {
/*  216 */       return this._n1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeC() {
/*  224 */       return this._n2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tri triA() {
/*  232 */       return this._t0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tri triB() {
/*  240 */       return this._t1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tri triC() {
/*  248 */       return this._t2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeNearest(float x, float y) {
/*  259 */       double d0 = TriMesh.distanceSquared(this._n0, x, y);
/*  260 */       double d1 = TriMesh.distanceSquared(this._n1, x, y);
/*  261 */       double d2 = TriMesh.distanceSquared(this._n2, x, y);
/*  262 */       double dmin = d0;
/*  263 */       TriMesh.Node nmin = this._n0;
/*  264 */       if (d1 < dmin) {
/*  265 */         dmin = d1;
/*  266 */         nmin = this._n1;
/*      */       } 
/*  268 */       if (d2 < dmin) {
/*  269 */         nmin = this._n2;
/*      */       }
/*  271 */       return nmin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tri triNabor(TriMesh.Node node) {
/*  280 */       if (node == this._n0) return this._t0; 
/*  281 */       if (node == this._n1) return this._t1; 
/*  282 */       if (node == this._n2) return this._t2; 
/*  283 */       Check.argument(false, "node is referenced by tri");
/*  284 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeNabor(Tri triNabor) {
/*  293 */       if (triNabor._t0 == this) return triNabor._n0; 
/*  294 */       if (triNabor._t1 == this) return triNabor._n1; 
/*  295 */       if (triNabor._t2 == this) return triNabor._n2; 
/*  296 */       Check.argument(false, "triNabor is a nabor of tri");
/*  297 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double centerCircle(double[] c) {
/*  306 */       if (hasCenter()) {
/*  307 */         c[0] = this._xc;
/*  308 */         c[1] = this._yc;
/*      */       } else {
/*  310 */         double x0 = this._n0._x;
/*  311 */         double y0 = this._n0._y;
/*  312 */         double x1 = this._n1._x;
/*  313 */         double y1 = this._n1._y;
/*  314 */         double x2 = this._n2._x;
/*  315 */         double y2 = this._n2._y;
/*  316 */         Geometry.centerCircle(x0, y0, x1, y1, x2, y2, c);
/*  317 */         setCenter(c[0], c[1]);
/*      */       } 
/*  319 */       double dx = this._xc - this._n2._x;
/*  320 */       double dy = this._yc - this._n2._y;
/*  321 */       return dx * dx + dy * dy;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] centerCircle() {
/*  329 */       double[] c = new double[2];
/*  330 */       centerCircle(c);
/*  331 */       return c;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double quality() {
/*  342 */       if (this._quality < 0.0D)
/*  343 */         this._quality = quality(this._n0, this._n1, this._n2); 
/*  344 */       return this._quality;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TriMesh.Node node) {
/*  353 */       return (node == this._n0 || node == this._n1 || node == this._n2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TriMesh.Node na, TriMesh.Node nb) {
/*  363 */       if (na == this._n0)
/*  364 */         return (nb == this._n1 || nb == this._n2); 
/*  365 */       if (na == this._n1)
/*  366 */         return (nb == this._n0 || nb == this._n2); 
/*  367 */       if (na == this._n2) {
/*  368 */         return (nb == this._n0 || nb == this._n1);
/*      */       }
/*  370 */       return false;
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
/*      */     public boolean references(TriMesh.Node na, TriMesh.Node nb, TriMesh.Node nc) {
/*  382 */       if (na == this._n0) {
/*  383 */         if (nb == this._n1)
/*  384 */           return (nc == this._n2); 
/*  385 */         if (nb == this._n2) {
/*  386 */           return (nc == this._n1);
/*      */         }
/*  388 */         return false;
/*      */       } 
/*  390 */       if (na == this._n1) {
/*  391 */         if (nb == this._n0)
/*  392 */           return (nc == this._n2); 
/*  393 */         if (nb == this._n2) {
/*  394 */           return (nc == this._n0);
/*      */         }
/*  396 */         return false;
/*      */       } 
/*  398 */       if (na == this._n2) {
/*  399 */         if (nb == this._n0)
/*  400 */           return (nc == this._n1); 
/*  401 */         if (nb == this._n1) {
/*  402 */           return (nc == this._n0);
/*      */         }
/*  404 */         return false;
/*      */       } 
/*      */       
/*  407 */       return false;
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
/*  431 */     private static double QUALITY_FACTOR = 2.0D / MathPlus.sqrt(3.0D);
/*      */ 
/*      */     
/*      */     private transient TriMesh.Node _n0;
/*      */ 
/*      */     
/*      */     private transient TriMesh.Node _n1;
/*      */ 
/*      */     
/*      */     private transient TriMesh.Node _n2;
/*      */ 
/*      */     
/*      */     private transient Tri _t0;
/*      */ 
/*      */     
/*      */     private transient Tri _t1;
/*      */ 
/*      */     
/*      */     private transient Tri _t2;
/*      */ 
/*      */     
/*      */     private transient int _mark;
/*      */     
/*      */     private transient int _bits;
/*      */     
/*  456 */     private transient double _quality = -1.0D;
/*      */ 
/*      */ 
/*      */     
/*      */     private transient double _xc;
/*      */ 
/*      */     
/*      */     private transient double _yc;
/*      */ 
/*      */ 
/*      */     
/*      */     private Tri(TriMesh.Node n0, TriMesh.Node n1, TriMesh.Node n2) {
/*  468 */       init(n0, n1, n2);
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
/*      */     private void init(TriMesh.Node n0, TriMesh.Node n1, TriMesh.Node n2) {
/*  482 */       this._n0 = n0;
/*  483 */       this._n1 = n1;
/*  484 */       this._n2 = n2;
/*  485 */       this._n0._tri = this;
/*  486 */       this._n1._tri = this;
/*  487 */       this._n2._tri = this;
/*  488 */       this._t0 = null;
/*  489 */       this._t1 = null;
/*  490 */       this._t2 = null;
/*  491 */       this._mark = 0;
/*  492 */       this._bits = 0;
/*  493 */       this._quality = -1.0D;
/*      */     }
/*      */     
/*      */     private void setInner() {
/*  497 */       this._bits |= 0x1;
/*      */     }
/*      */     
/*      */     private void clearInner() {
/*  501 */       this._bits &= 0xFFFFFFFE;
/*      */     }
/*      */     
/*      */     private boolean isInner() {
/*  505 */       return ((this._bits & 0x1) != 0);
/*      */     }
/*      */     
/*      */     private void setOuter() {
/*  509 */       this._bits |= 0x2;
/*      */     }
/*      */     
/*      */     private void clearOuter() {
/*  513 */       this._bits &= 0xFFFFFFFD;
/*      */     }
/*      */     
/*      */     private boolean isOuter() {
/*  517 */       return ((this._bits & 0x2) != 0);
/*      */     }
/*      */     
/*      */     private void setCenter(double xc, double yc) {
/*  521 */       this._xc = xc;
/*  522 */       this._yc = yc;
/*  523 */       this._bits |= 0x4;
/*      */     }
/*      */     
/*      */     private boolean hasCenter() {
/*  527 */       return ((this._bits & 0x4) != 0);
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
/*      */     private static double quality(TriMesh.Node na, TriMesh.Node nb, TriMesh.Node nc) {
/*  556 */       double xa = na._x;
/*  557 */       double ya = na._y;
/*  558 */       double xb = nb._x;
/*  559 */       double yb = nb._y;
/*  560 */       double xc = nc._x;
/*  561 */       double yc = nc._y;
/*  562 */       double xab = xa - xb;
/*  563 */       double yab = ya - yb;
/*  564 */       double xac = xa - xc;
/*  565 */       double yac = ya - yc;
/*  566 */       double xbc = xb - xc;
/*  567 */       double ybc = yb - yc;
/*  568 */       double det = xac * ybc - yac * xbc;
/*  569 */       double dab = xab * xab + yab * yab;
/*  570 */       double dac = xac * xac + yac * yac;
/*  571 */       double dbc = xbc * xbc + ybc * ybc;
/*  572 */       double dmx = dab;
/*  573 */       if (dac > dmx) dmx = dac; 
/*  574 */       if (dbc > dmx) dmx = dbc; 
/*  575 */       double quality = QUALITY_FACTOR * det / dmx;
/*      */       
/*  577 */       if (quality < 0.0D) quality = -quality; 
/*  578 */       if (quality > 1.0D) quality = 1.0D; 
/*  579 */       return (float)quality;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Edge
/*      */   {
/*      */     private TriMesh.Node _a;
/*      */ 
/*      */ 
/*      */     
/*      */     private TriMesh.Node _b;
/*      */ 
/*      */ 
/*      */     
/*      */     private TriMesh.Tri _triLeft;
/*      */ 
/*      */ 
/*      */     
/*      */     private TriMesh.Tri _triRight;
/*      */ 
/*      */ 
/*      */     
/*      */     private TriMesh.Node _nodeLeft;
/*      */ 
/*      */ 
/*      */     
/*      */     private TriMesh.Node _nodeRight;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Edge(TriMesh.Node a, TriMesh.Node b) {
/*  615 */       this(a, b, (TriMesh.Tri)null);
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
/*      */     public Edge(TriMesh.Node a, TriMesh.Node b, TriMesh.Tri abc) {
/*  627 */       TriMesh.Node c = (abc != null) ? TriMesh.otherNode(abc, a, b) : null;
/*  628 */       Check.argument((abc == null || c != null), "tri references nodes");
/*  629 */       this._a = a;
/*  630 */       this._b = b;
/*  631 */       if (c != null) {
/*  632 */         if (TriMesh.nodesInOrder(abc, a, b, c)) {
/*  633 */           this._triLeft = abc;
/*  634 */           this._nodeLeft = c;
/*  635 */           this._triRight = abc.triNabor(c);
/*  636 */           this._nodeRight = (this._triRight != null) ? abc.nodeNabor(this._triRight) : null;
/*      */         } else {
/*  638 */           this._triRight = abc;
/*  639 */           this._nodeRight = c;
/*  640 */           this._triLeft = abc.triNabor(c);
/*  641 */           this._nodeLeft = (this._triLeft != null) ? abc.nodeNabor(this._triLeft) : null;
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeA() {
/*  651 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node nodeB() {
/*  659 */       return this._b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Tri triLeft() {
/*  667 */       return this._triLeft;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Tri triRight() {
/*  675 */       return this._triRight;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Node nodeLeft() {
/*  683 */       return this._nodeLeft;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Node nodeRight() {
/*  691 */       return this._nodeRight;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Edge mate() {
/*  700 */       return new Edge(this._b, this._a, this._triRight, this._nodeRight, this._triLeft, this._nodeLeft);
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
/*      */     public boolean isVisibleFromPoint(double x, double y) {
/*  712 */       return (Geometry.leftOfLine(this._a._x, this._a._y, this._b._x, this._b._y, x, y) < 0.0D);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object object) {
/*  717 */       if (object == this)
/*  718 */         return true; 
/*  719 */       if (object != null && object.getClass() == getClass()) {
/*  720 */         Edge edge = (Edge)object;
/*  721 */         return (this._a == edge._a && this._b == edge._b);
/*      */       } 
/*  723 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/*  728 */       return this._a._hash ^ this._b._hash;
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
/*      */     private Edge(TriMesh.Node a, TriMesh.Node b, TriMesh.Tri triLeft, TriMesh.Node nodeLeft, TriMesh.Tri triRight, TriMesh.Node nodeRight) {
/*  740 */       this._a = a;
/*  741 */       this._b = b;
/*  742 */       this._triLeft = triLeft;
/*  743 */       this._nodeLeft = nodeLeft;
/*  744 */       this._triRight = triRight;
/*  745 */       this._nodeRight = nodeRight;
/*      */     }
/*      */     
/*      */     private Edge(TriMesh.Tri triLeft, TriMesh.Node nodeLeft) {
/*  749 */       initLeft(triLeft, nodeLeft);
/*  750 */       this._triLeft = triLeft;
/*  751 */       this._nodeLeft = nodeLeft;
/*  752 */       this._triRight = triLeft.triNabor(nodeLeft);
/*  753 */       this._nodeRight = (this._triRight != null) ? this._triLeft.nodeNabor(this._triRight) : null;
/*      */     }
/*      */     
/*      */     private Edge(TriMesh.Tri triLeft, TriMesh.Node nodeLeft, TriMesh.Tri triRight, TriMesh.Node nodeRight) {
/*  757 */       if (triLeft != null) {
/*  758 */         initLeft(triLeft, nodeLeft);
/*  759 */       } else if (triRight != null) {
/*  760 */         initRight(triRight, nodeRight);
/*      */       } else {
/*  762 */         assert false : "either triLeft or triRight is not null";
/*      */       } 
/*  764 */       this._triLeft = triLeft;
/*  765 */       this._triRight = triRight;
/*  766 */       this._nodeLeft = nodeLeft;
/*  767 */       this._nodeRight = nodeRight;
/*      */     }
/*      */     
/*      */     private void initLeft(TriMesh.Tri triLeft, TriMesh.Node nodeLeft) {
/*  771 */       if (nodeLeft == triLeft._n0) {
/*  772 */         this._a = triLeft._n1;
/*  773 */         this._b = triLeft._n2;
/*  774 */       } else if (nodeLeft == triLeft._n1) {
/*  775 */         this._a = triLeft._n2;
/*  776 */         this._b = triLeft._n0;
/*  777 */       } else if (nodeLeft == triLeft._n2) {
/*  778 */         this._a = triLeft._n0;
/*  779 */         this._b = triLeft._n1;
/*      */       } else {
/*  781 */         assert false : "nodeLeft referenced by triLeft";
/*      */       } 
/*      */     }
/*      */     
/*      */     private void initRight(TriMesh.Tri triRight, TriMesh.Node nodeRight) {
/*  786 */       if (nodeRight == triRight._n0) {
/*  787 */         this._a = triRight._n2;
/*  788 */         this._b = triRight._n1;
/*  789 */       } else if (nodeRight == triRight._n1) {
/*  790 */         this._a = triRight._n0;
/*  791 */         this._b = triRight._n2;
/*  792 */       } else if (nodeRight == triRight._n2) {
/*  793 */         this._a = triRight._n1;
/*  794 */         this._b = triRight._n0;
/*      */       } else {
/*  796 */         assert false : "nodeRight referenced by triRight";
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
/*      */   public static class NodeList
/*      */   {
/*      */     public final void add(TriMesh.Node node) {
/*  825 */       if (this._n == this._a.length) {
/*  826 */         TriMesh.Node[] t = new TriMesh.Node[this._a.length * 2];
/*  827 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  828 */         this._a = t;
/*      */       } 
/*  830 */       this._a[this._n++] = node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node remove(int index) {
/*  839 */       TriMesh.Node node = this._a[index];
/*  840 */       this._n--;
/*  841 */       if (this._n > index)
/*  842 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/*  843 */       return node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node[] trim() {
/*  851 */       if (this._n < this._a.length) {
/*  852 */         TriMesh.Node[] t = new TriMesh.Node[this._n];
/*  853 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  854 */         this._a = t;
/*      */       } 
/*  856 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/*  863 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nnode() {
/*  871 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node[] nodes() {
/*  879 */       return this._a;
/*      */     }
/*  881 */     private int _n = 0;
/*  882 */     private TriMesh.Node[] _a = new TriMesh.Node[64];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TriList
/*      */   {
/*      */     public final void add(TriMesh.Tri tri) {
/*  895 */       if (this._n == this._a.length) {
/*  896 */         TriMesh.Tri[] t = new TriMesh.Tri[this._a.length * 2];
/*  897 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  898 */         this._a = t;
/*      */       } 
/*  900 */       this._a[this._n++] = tri;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Tri remove(int index) {
/*  909 */       TriMesh.Tri tri = this._a[index];
/*  910 */       this._n--;
/*  911 */       if (this._n > index)
/*  912 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/*  913 */       return tri;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Tri[] trim() {
/*  921 */       if (this._n < this._a.length) {
/*  922 */         TriMesh.Tri[] t = new TriMesh.Tri[this._n];
/*  923 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  924 */         this._a = t;
/*      */       } 
/*  926 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/*  933 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int ntri() {
/*  941 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Tri[] tris() {
/*  949 */       return this._a;
/*      */     }
/*  951 */     private int _n = 0;
/*  952 */     private TriMesh.Tri[] _a = new TriMesh.Tri[64];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EdgeList
/*      */   {
/*      */     public final void add(TriMesh.Edge edge) {
/*  965 */       if (this._n == this._a.length) {
/*  966 */         TriMesh.Edge[] t = new TriMesh.Edge[this._a.length * 2];
/*  967 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  968 */         this._a = t;
/*      */       } 
/*  970 */       this._a[this._n++] = edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Edge remove(int index) {
/*  979 */       TriMesh.Edge edge = this._a[index];
/*  980 */       this._n--;
/*  981 */       if (this._n > index)
/*  982 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/*  983 */       return edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Edge[] trim() {
/*  991 */       if (this._n < this._a.length) {
/*  992 */         TriMesh.Edge[] t = new TriMesh.Edge[this._n];
/*  993 */         System.arraycopy(this._a, 0, t, 0, this._n);
/*  994 */         this._a = t;
/*      */       } 
/*  996 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1003 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nedge() {
/* 1011 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Edge[] edges() {
/* 1019 */       return this._a;
/*      */     }
/* 1021 */     private int _n = 0;
/* 1022 */     private TriMesh.Edge[] _a = new TriMesh.Edge[64];
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
/*      */   public static class NodeStepList
/*      */   {
/*      */     public final void add(TriMesh.Node node, int step) {
/* 1038 */       if (this._n == this._a.length) {
/* 1039 */         TriMesh.Node[] s = new TriMesh.Node[this._a.length * 2];
/* 1040 */         int[] t = new int[this._a.length * 2];
/* 1041 */         System.arraycopy(this._a, 0, s, 0, this._n);
/* 1042 */         System.arraycopy(this._b, 0, t, 0, this._n);
/* 1043 */         this._a = s;
/* 1044 */         this._b = t;
/*      */       } 
/* 1046 */       this._a[this._n] = node;
/* 1047 */       this._b[this._n] = step;
/* 1048 */       this._n++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void trim() {
/* 1055 */       if (this._n < this._a.length) {
/* 1056 */         TriMesh.Node[] s = new TriMesh.Node[this._n];
/* 1057 */         int[] t = new int[this._n];
/* 1058 */         System.arraycopy(this._a, 0, s, 0, this._n);
/* 1059 */         System.arraycopy(this._b, 0, t, 0, this._n);
/* 1060 */         this._a = s;
/* 1061 */         this._b = t;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1069 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nnode() {
/* 1077 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TriMesh.Node[] nodes() {
/* 1085 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int[] steps() {
/* 1093 */       return this._b;
/*      */     }
/* 1095 */     private int _n = 0;
/* 1096 */     private TriMesh.Node[] _a = new TriMesh.Node[64];
/* 1097 */     private int[] _b = new int[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PointLocation
/*      */   {
/*      */     private TriMesh.Node _node;
/*      */     
/*      */     private TriMesh.Edge _edge;
/*      */     
/*      */     private TriMesh.Tri _tri;
/*      */     private boolean _inside;
/*      */     
/*      */     public boolean isOnNode() {
/* 1111 */       return (this._node != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOnEdge() {
/* 1119 */       return (this._edge != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInside() {
/* 1127 */       return this._inside;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOutside() {
/* 1135 */       return !this._inside;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Node node() {
/* 1143 */       return this._node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Edge edge() {
/* 1151 */       return this._edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TriMesh.Tri tri() {
/* 1162 */       return this._tri;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private PointLocation(TriMesh.Tri tri) {
/* 1171 */       this._tri = tri;
/* 1172 */       this._inside = true;
/*      */     }
/*      */     private PointLocation(TriMesh.Tri tri, boolean inside) {
/* 1175 */       this._tri = tri;
/* 1176 */       this._inside = inside;
/*      */     }
/*      */     private PointLocation(TriMesh.Node node) {
/* 1179 */       this._tri = node._tri;
/* 1180 */       this._node = node;
/* 1181 */       this._inside = true;
/*      */     }
/*      */     private PointLocation(TriMesh.Edge edge) {
/* 1184 */       this._tri = edge._triLeft;
/* 1185 */       this._edge = edge;
/* 1186 */       this._inside = true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized NodePropertyMap getNodePropertyMap(String name) {
/* 1220 */     NodePropertyMap map = this._nodePropertyMaps.get(name);
/* 1221 */     if (map == null) {
/* 1222 */       if (this._nnodeValues == this._lnodeValues) {
/* 1223 */         if (this._lnodeValues == 0) {
/* 1224 */           this._lnodeValues = 4;
/*      */         } else {
/* 1226 */           this._lnodeValues *= 2;
/*      */         } 
/* 1228 */         NodeIterator ni = getNodes();
/* 1229 */         while (ni.hasNext()) {
/* 1230 */           Node node = ni.next();
/* 1231 */           Object[] valuesOld = node._values;
/* 1232 */           Object[] valuesNew = new Object[this._lnodeValues];
/* 1233 */           for (int i = 0; i < this._nnodeValues; i++)
/* 1234 */             valuesNew[i] = valuesOld[i]; 
/* 1235 */           node._values = valuesNew;
/*      */         } 
/*      */       } 
/* 1238 */       int index = this._nnodeValues++;
/* 1239 */       map = new NodePropertyMapInternal(index);
/* 1240 */       this._nodePropertyMaps.put(name, map);
/*      */     } 
/* 1242 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean hasNodePropertyMap(String name) {
/* 1251 */     return this._nodePropertyMaps.containsKey(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized String[] getNodePropertyMapNames() {
/* 1260 */     Set<String> nameSet = this._nodePropertyMaps.keySet();
/* 1261 */     String[] names = new String[nameSet.size()];
/* 1262 */     return nameSet.<String>toArray(names);
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
/*      */   public synchronized void addNodeListener(NodeListener nl) {
/* 1354 */     this._listeners.add(NodeListener.class, nl);
/* 1355 */     this._nnodeListeners++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeNodeListener(NodeListener nl) {
/* 1363 */     this._listeners.remove(NodeListener.class, nl);
/* 1364 */     this._nnodeListeners--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void addTriListener(TriListener tl) {
/* 1372 */     this._listeners.add(TriListener.class, tl);
/* 1373 */     this._ntriListeners++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void removeTriListener(TriListener tl) {
/* 1381 */     this._listeners.remove(TriListener.class, tl);
/* 1382 */     this._ntriListeners--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TriMesh() {
/* 1389 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countNodes() {
/* 1397 */     return this._nnode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countTris() {
/* 1405 */     return this._ntri;
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
/*      */   public long getVersion() {
/* 1417 */     return this._version;
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
/*      */   public synchronized boolean addNode(Node node) {
/* 1429 */     PointLocation pl = locatePoint(node._x, node._y);
/*      */ 
/*      */     
/* 1432 */     if (pl.isOnNode()) {
/* 1433 */       return false;
/*      */     }
/*      */     
/* 1436 */     fireNodeWillBeAdded(node);
/*      */ 
/*      */     
/* 1439 */     if (this._nroot == null) {
/* 1440 */       this._nroot = node;
/* 1441 */       this._nroot._prev = this._nroot._next = this._nroot;
/*      */     } else {
/* 1443 */       node._next = this._nroot;
/* 1444 */       node._prev = this._nroot._prev;
/* 1445 */       this._nroot._prev._next = node;
/* 1446 */       this._nroot._prev = node;
/* 1447 */       this._nroot = node;
/*      */     } 
/* 1449 */     this._nnode++;
/*      */ 
/*      */     
/* 1452 */     updatePropertyValues(node);
/*      */ 
/*      */ 
/*      */     
/* 1456 */     double factor = 0.45D * this._sampledNodes.size();
/* 1457 */     if (factor * factor * factor < this._nnode) {
/* 1458 */       this._sampledNodes.add(node);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1463 */     if (pl.isOutside() && this._nnode <= 3) {
/* 1464 */       if (this._nnode == 3) {
/* 1465 */         createFirstTri();
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1473 */       clearTriMarks();
/* 1474 */       this._edgeSet.clear();
/* 1475 */       if (pl.isInside()) {
/* 1476 */         getDelaunayEdgesInside(node, pl.tri());
/*      */       } else {
/* 1478 */         getDelaunayEdgesOutside(node, pl.tri());
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1484 */       this._nodeSet.clear(); boolean more;
/* 1485 */       for (more = this._edgeSet.first(); more; more = this._edgeSet.next()) {
/* 1486 */         Node a = this._edgeSet.a;
/* 1487 */         Node b = this._edgeSet.b;
/* 1488 */         Node c = this._edgeSet.c;
/* 1489 */         Tri abc = this._edgeSet.abc;
/* 1490 */         Tri nba = makeTri(node, b, a);
/* 1491 */         linkTris(nba, node, abc, c);
/* 1492 */         if (!this._nodeSet.add(a, b, nba))
/* 1493 */           linkTris(this._nodeSet.nba, this._nodeSet.b, nba, b); 
/* 1494 */         if (!this._nodeSet.add(b, a, nba)) {
/* 1495 */           linkTris(this._nodeSet.nba, this._nodeSet.b, nba, a);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1503 */     fireNodeAdded(node);
/*      */     
/* 1505 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized boolean removeNode(Node node) {
/* 1516 */     Tri tri = node._tri;
/*      */ 
/*      */     
/* 1519 */     if (tri == null) {
/* 1520 */       return false;
/*      */     }
/*      */     
/* 1523 */     fireNodeWillBeRemoved(node);
/*      */ 
/*      */     
/* 1526 */     this._nroot = node._next;
/* 1527 */     this._nmin = node._next;
/* 1528 */     if (this._nroot == node) {
/* 1529 */       this._nroot = null;
/* 1530 */       this._nmin = null;
/*      */     } 
/* 1532 */     node._prev._next = node._next;
/* 1533 */     node._next._prev = node._prev;
/* 1534 */     node._prev = null;
/* 1535 */     node._next = null;
/* 1536 */     node._tri = null;
/* 1537 */     this._sampledNodes.remove(node);
/* 1538 */     this._nnode--;
/*      */ 
/*      */     
/* 1541 */     if (this._nnode < 3) {
/* 1542 */       if (this._nnode == 2) {
/* 1543 */         Node n0 = this._nroot;
/* 1544 */         Node n1 = n0._next;
/* 1545 */         n0._tri = null;
/* 1546 */         n1._tri = null;
/* 1547 */         killTri(this._troot);
/* 1548 */         this._troot = null;
/*      */       } 
/* 1550 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1555 */     this._edgeSet.clear();
/* 1556 */     this._nodeList.clear();
/* 1557 */     clearTriMarks();
/* 1558 */     clearNodeMarks();
/* 1559 */     getDelaunayEdgesOpposite(node, tri);
/* 1560 */     int nnode = this._nodeList.nnode();
/* 1561 */     Node[] nodes = this._nodeList.nodes();
/*      */     
/*      */     boolean more;
/*      */     
/* 1565 */     for (more = this._edgeSet.remove(); more; more = this._edgeSet.remove()) {
/*      */ 
/*      */       
/* 1568 */       Node a = this._edgeSet.a;
/* 1569 */       Node b = this._edgeSet.b;
/* 1570 */       Node c = this._edgeSet.c;
/* 1571 */       Tri abc = this._edgeSet.abc;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1576 */       Node n = null;
/* 1577 */       for (int inode = 0; inode < nnode; inode++) {
/* 1578 */         Node m = nodes[inode];
/* 1579 */         if (m != a && m != b && !leftOfLine(a, b, m) && (
/* 1580 */           n == null || inCircle(n, b, a, m))) {
/* 1581 */           n = m;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1586 */       if (n != null) {
/*      */ 
/*      */         
/* 1589 */         Tri nba = makeTri(n, b, a);
/*      */ 
/*      */         
/* 1592 */         linkTris(nba, n, abc, c);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1597 */         if (!this._edgeSet.add(nba, a))
/* 1598 */           linkTris(this._edgeSet.abc, this._edgeSet.c, nba, a); 
/* 1599 */         if (!this._edgeSet.add(nba, b)) {
/* 1600 */           linkTris(this._edgeSet.abc, this._edgeSet.c, nba, b);
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1606 */         linkTris(abc, c, null, null);
/* 1607 */         a._tri = abc;
/* 1608 */         b._tri = abc;
/* 1609 */         this._troot = abc;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1617 */     fireNodeRemoved(node);
/*      */     
/* 1619 */     return true;
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
/*      */   public synchronized boolean moveNode(Node node, float x, float y) {
/* 1637 */     if (x != node.x() || y != node.y()) {
/* 1638 */       Node nodeNearest = findNodeNearest(x, y);
/* 1639 */       if (node == nodeNearest || x != nodeNearest.x() || y != nodeNearest.y()) {
/* 1640 */         boolean nodeInMesh = removeNode(node);
/* 1641 */         node.setPosition(x, y);
/* 1642 */         if (nodeInMesh) {
/* 1643 */           boolean addedNode = addNode(node);
/* 1644 */           assert addedNode;
/*      */         } 
/* 1646 */         return true;
/*      */       } 
/*      */     } 
/* 1649 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Node findNodeNearest(float x, float y) {
/* 1659 */     return findNodeNearest(x, y);
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
/*      */   public synchronized Edge findEdge(Node na, Node nb) {
/* 1674 */     Tri tri = findTri(na, nb);
/* 1675 */     if (tri != null) {
/* 1676 */       return edgeOfTri(tri, na, nb);
/*      */     }
/* 1678 */     return null;
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
/*      */   public Tri findTri(Node node) {
/* 1690 */     return node._tri;
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
/*      */   public synchronized Tri findTri(Node na, Node nb) {
/* 1702 */     Tri tri = findTri(na);
/* 1703 */     if (tri != null) {
/* 1704 */       clearTriMarks();
/* 1705 */       tri = findTri(tri, na, nb);
/*      */     } 
/* 1707 */     return tri;
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
/*      */   public synchronized Tri findTri(Node na, Node nb, Node nc) {
/* 1720 */     Tri tri = findTri(na, nb);
/* 1721 */     if (tri != null) {
/* 1722 */       clearTriMarks();
/* 1723 */       tri = findTri(tri, na, nb, nc);
/*      */     } 
/* 1725 */     return tri;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized PointLocation locatePoint(float x, float y) {
/* 1735 */     return locatePoint(x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized NodeIterator getNodes() {
/* 1743 */     return new NodeIterator() {
/*      */         public boolean hasNext() {
/* 1745 */           return (this._nnext != null);
/*      */         }
/*      */         public TriMesh.Node next() {
/* 1748 */           if (this._nnext == null)
/* 1749 */             throw new NoSuchElementException(); 
/* 1750 */           TriMesh.Node node = this._nnext;
/* 1751 */           this._nnext = node._next;
/* 1752 */           if (this._nnext == this._nroot) this._nnext = null; 
/* 1753 */           return node;
/*      */         }
/* 1755 */         private TriMesh.Node _nroot = TriMesh.this._nroot;
/* 1756 */         private TriMesh.Node _nnext = this._nroot;
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized TriIterator getTris() {
/* 1765 */     return new TriIterator() {
/*      */         public final boolean hasNext() {
/* 1767 */           return this._i.hasNext();
/*      */         } private Iterator<TriMesh.Tri> _i;
/*      */         public final TriMesh.Tri next() {
/* 1770 */           return this._i.next();
/*      */         }
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
/*      */   public synchronized EdgeIterator getEdgesOnHull() {
/* 1810 */     clearTriMarks();
/* 1811 */     Edge edge = getEdgeOnHull(this._troot);
/* 1812 */     final HashSet<Edge> edges = new HashSet<>(128);
/* 1813 */     getEdgesOnHull(edge, edges);
/* 1814 */     return new EdgeIterator() {
/* 1815 */         private Iterator<TriMesh.Edge> i = edges.iterator();
/*      */         public final boolean hasNext() {
/* 1817 */           return this.i.hasNext();
/*      */         }
/*      */         public final TriMesh.Edge next() {
/* 1820 */           return this.i.next();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Node[] getNodeNabors(Node node) {
/* 1831 */     NodeList nabors = new NodeList();
/* 1832 */     getNodeNabors(node, nabors);
/* 1833 */     return nabors.trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void getNodeNabors(Node node, NodeList nabors) {
/* 1842 */     clearNodeMarks();
/* 1843 */     clearTriMarks();
/* 1844 */     getNodeNabors(node, node._tri, nabors);
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
/*      */   public synchronized NodeStepList getNodeNabors(Node node, int stepMax) {
/* 1856 */     Check.argument((stepMax <= 256), "stepMax <= 256");
/*      */ 
/*      */     
/* 1859 */     clearNodeMarks();
/*      */ 
/*      */     
/* 1862 */     mark(node);
/*      */ 
/*      */     
/* 1865 */     NodeStepList list = new NodeStepList();
/*      */ 
/*      */     
/* 1868 */     for (int step = 1, nnabor1 = 0; step <= stepMax; step++) {
/*      */ 
/*      */       
/* 1871 */       if (step == 1) {
/*      */ 
/*      */         
/* 1874 */         getNodeNabors(node, step, list);
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1880 */         int nnabor2 = list.nnode();
/*      */ 
/*      */         
/* 1883 */         Node[] naborNodes = list.nodes();
/* 1884 */         for (int inabor = nnabor1; inabor < nnabor2; inabor++) {
/* 1885 */           node = naborNodes[inabor];
/* 1886 */           getNodeNabors(node, step, list);
/*      */         } 
/*      */ 
/*      */         
/* 1890 */         nnabor1 = nnabor2;
/*      */       } 
/*      */     } 
/* 1893 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Tri[] getTriNabors(Node node) {
/* 1902 */     TriList nabors = new TriList();
/* 1903 */     getTriNabors(node, nabors);
/* 1904 */     return nabors.trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void getTriNabors(Node node, TriList nabors) {
/* 1913 */     clearTriMarks();
/* 1914 */     getTriNabors(node, node._tri, nabors);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Tri[] getTriNabors(Edge edge) {
/* 1923 */     TriList nabors = new TriList();
/* 1924 */     getTriNabors(edge, nabors);
/* 1925 */     return nabors.trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void getTriNabors(Edge edge, TriList nabors) {
/* 1934 */     Tri triLeft = edge.triLeft();
/* 1935 */     Tri triRight = edge.triRight();
/* 1936 */     if (triLeft == null && triRight == null) {
/* 1937 */       Node na = edge.nodeA();
/* 1938 */       Node nb = edge.nodeB();
/* 1939 */       edge = findEdge(na, nb);
/* 1940 */       triLeft = edge.triLeft();
/* 1941 */       triRight = edge.triRight();
/*      */     } 
/* 1943 */     if (triLeft != null)
/* 1944 */       nabors.add(triLeft); 
/* 1945 */     if (triRight != null) {
/* 1946 */       nabors.add(triRight);
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
/*      */   public synchronized Edge[] getEdgeNabors(Node node) {
/* 1959 */     EdgeList nabors = new EdgeList();
/* 1960 */     getEdgeNabors(node, nabors);
/* 1961 */     return nabors.trim();
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
/*      */   public synchronized void getEdgeNabors(Node node, EdgeList nabors) {
/* 1979 */     Tri tri = node.tri();
/* 1980 */     Node ta = tri.nodeA();
/* 1981 */     Node tb = tri.nodeB();
/* 1982 */     Node tc = tri.nodeC();
/*      */ 
/*      */     
/* 1985 */     Edge edge = null;
/* 1986 */     if (node == ta) {
/* 1987 */       edge = new Edge(tc, ta, tri);
/* 1988 */     } else if (node == tb) {
/* 1989 */       edge = new Edge(ta, tb, tri);
/* 1990 */     } else if (node == tc) {
/* 1991 */       edge = new Edge(tb, tc, tri);
/*      */     } else {
/* 1993 */       assert false : "tri references node";
/*      */     } 
/*      */ 
/*      */     
/* 1997 */     Edge firstEdge = edge;
/*      */ 
/*      */     
/*      */     do {
/* 2001 */       nabors.add(edge);
/*      */ 
/*      */       
/* 2004 */       node = edge.nodeA();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2009 */       tri = edge.triRight();
/* 2010 */       if (tri == null) {
/* 2011 */         tri = edge.triLeft();
/* 2012 */         Node nodeBack = edge.nodeLeft();
/* 2013 */         Tri triBack = tri.triNabor(node);
/* 2014 */         while (triBack != null) {
/* 2015 */           node = nodeBack;
/* 2016 */           nodeBack = tri.nodeNabor(triBack);
/* 2017 */           tri = triBack;
/* 2018 */           triBack = tri.triNabor(node);
/*      */         } 
/* 2020 */         edge = new Edge(null, null, tri, node);
/*      */       } else {
/* 2022 */         edge = new Edge(tri, node);
/*      */       } 
/* 2024 */     } while (!edge.equals(firstEdge));
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
/*      */   public synchronized void setOuterBox(float xmin, float ymin, float xmax, float ymax) {
/* 2046 */     Check.argument((xmin < xmax), "outer box is valid");
/* 2047 */     Check.argument((ymin < ymax), "outer box is valid");
/*      */ 
/*      */     
/* 2050 */     if (xmin != this._xminOuter || xmax != this._xmaxOuter || ymin != this._yminOuter || ymax != this._ymaxOuter) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2056 */       this._xminOuter = xmin;
/* 2057 */       this._yminOuter = ymin;
/* 2058 */       this._xmaxOuter = xmax;
/* 2059 */       this._ymaxOuter = ymax;
/*      */ 
/*      */ 
/*      */       
/* 2063 */       TriIterator ti = getTris();
/* 2064 */       while (ti.hasNext()) {
/* 2065 */         Tri tri = ti.next();
/* 2066 */         tri.clearInner();
/* 2067 */         tri.clearOuter();
/*      */       } 
/*      */     } 
/*      */     
/* 2071 */     this._version++;
/* 2072 */     this._outerEnabled = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableOuterBox() {
/* 2081 */     this._version++;
/* 2082 */     this._outerEnabled = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableOuterBox() {
/* 2091 */     this._version++;
/* 2092 */     this._outerEnabled = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInner(Node node) {
/* 2101 */     Tri tri = node.tri();
/* 2102 */     if (tri == null || isInner(tri))
/* 2103 */       return true; 
/* 2104 */     Tri[] tris = getTriNabors(node);
/* 2105 */     int ntri = tris.length;
/* 2106 */     for (int itri = 0; itri < ntri; itri++) {
/* 2107 */       if (isInner(tris[itri]))
/* 2108 */         return true; 
/*      */     } 
/* 2110 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOuter(Node node) {
/* 2119 */     return !isInner(node);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInner(Tri tri) {
/* 2128 */     if (!this._outerEnabled)
/* 2129 */       return true; 
/* 2130 */     if (!tri.isInner() && !tri.isOuter())
/* 2131 */       markTriInnerOrOuter(tri); 
/* 2132 */     return tri.isInner();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOuter(Tri tri) {
/* 2141 */     return !isInner(tri);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInner(Edge edge) {
/* 2150 */     Tri triLeft = edge.triLeft();
/* 2151 */     if (triLeft != null && isInner(triLeft))
/* 2152 */       return true; 
/* 2153 */     Tri triRight = edge.triRight();
/* 2154 */     return (triRight != null && isInner(triRight));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOuter(Edge edge) {
/* 2163 */     return !isInner(edge);
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
/*      */   public final void mark(Node node) {
/* 2175 */     node._mark = this._nodeMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void markRed(Node node) {
/* 2184 */     node._mark = this._nodeMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void markBlue(Node node) {
/* 2192 */     node._mark = this._nodeMarkBlue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarked(Node node) {
/* 2201 */     return (node._mark == this._nodeMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarkedRed(Node node) {
/* 2210 */     return (node._mark == this._nodeMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarkedBlue(Node node) {
/* 2219 */     return (node._mark == this._nodeMarkBlue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void clearNodeMarks() {
/* 2229 */     if (this._nodeMarkRed == 2147483646) {
/* 2230 */       Node node = this._nroot;
/*      */       while (true) {
/* 2232 */         node._mark = 0;
/* 2233 */         node = node._next;
/* 2234 */         if (node == this._nroot) {
/* 2235 */           this._nodeMarkRed = 0;
/* 2236 */           this._nodeMarkBlue = 0; break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 2240 */     this._nodeMarkRed++;
/* 2241 */     this._nodeMarkBlue--;
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
/*      */   public final void mark(Tri tri) {
/* 2253 */     tri._mark = this._triMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void markRed(Tri tri) {
/* 2262 */     tri._mark = this._triMarkRed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void markBlue(Tri tri) {
/* 2270 */     tri._mark = this._triMarkBlue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarked(Tri tri) {
/* 2279 */     return (tri._mark == this._triMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarkedRed(Tri tri) {
/* 2288 */     return (tri._mark == this._triMarkRed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isMarkedBlue(Tri tri) {
/* 2297 */     return (tri._mark == this._triMarkBlue);
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
/*      */   public synchronized void clearTriMarks() {
/* 2310 */     if (this._triMarkRed == 2147483646) {
/* 2311 */       this._triMarkRed++;
/* 2312 */       this._triMarkBlue--;
/* 2313 */       markAllTris(this._troot);
/* 2314 */       zeroTriMarks(this._troot);
/* 2315 */       this._triMarkRed = 0;
/* 2316 */       this._triMarkBlue = 0;
/*      */     } 
/*      */ 
/*      */     
/* 2320 */     this._triMarkRed++;
/* 2321 */     this._triMarkBlue--;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void validate() {
/* 2331 */     int nnode = 0;
/* 2332 */     NodeIterator ni = getNodes();
/* 2333 */     while (ni.hasNext()) {
/* 2334 */       nnode++;
/* 2335 */       Node node = ni.next();
/* 2336 */       validate(node);
/*      */     } 
/* 2338 */     assert nnode == this._nnode;
/*      */ 
/*      */     
/* 2341 */     int ntri = 0;
/* 2342 */     TriIterator ti = getTris();
/* 2343 */     while (ti.hasNext()) {
/* 2344 */       ntri++;
/* 2345 */       Tri tri = ti.next();
/* 2346 */       validate(tri);
/*      */     } 
/* 2348 */     assert ntri == this._ntri;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void init() {
/* 2358 */     this._version = 0L;
/* 2359 */     this._nnode = 0;
/* 2360 */     this._ntri = 0;
/* 2361 */     this._nroot = null;
/* 2362 */     this._troot = null;
/* 2363 */     this._sampledNodes = new HashSet<>(256);
/* 2364 */     this._triMarkRed = 0;
/* 2365 */     this._triMarkBlue = 0;
/* 2366 */     this._nodeMarkRed = 0;
/* 2367 */     this._nodeMarkBlue = 0;
/* 2368 */     this._edgeSet = new EdgeSet(256, 0.25D);
/* 2369 */     this._nodeSet = new NodeSet(256, 0.25D);
/* 2370 */     this._nodeList = new NodeList();
/* 2371 */     this._nmin = null;
/* 2372 */     this._dmin = 0.0D;
/* 2373 */     this._deadTris = new TriList();
/* 2374 */     this._nnodeListeners = 0;
/* 2375 */     this._ntriListeners = 0;
/* 2376 */     this._listeners = new EventListenerList();
/* 2377 */     this._outerEnabled = false;
/* 2378 */     this._xminOuter = 0.0D;
/* 2379 */     this._yminOuter = 0.0D;
/* 2380 */     this._xmaxOuter = 0.0D;
/* 2381 */     this._ymaxOuter = 0.0D;
/* 2382 */     this._nnodeValues = 0;
/* 2383 */     this._lnodeValues = 0;
/* 2384 */     this._nodePropertyMaps = new HashMap<>();
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
/*      */   private static class NodePropertyMapInternal
/*      */     implements NodePropertyMap
/*      */   {
/*      */     private int _index;
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
/*      */     public Object get(TriMesh.Node node) {
/* 2426 */       return node._values[this._index];
/*      */     }
/*      */     public void put(TriMesh.Node node, Object value) {
/* 2429 */       node._values[this._index] = value;
/*      */     }
/*      */     NodePropertyMapInternal(int index) {
/* 2432 */       this._index = index;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void updatePropertyValues(Node node) {
/* 2441 */     if (this._lnodeValues == 0) {
/* 2442 */       node._values = null;
/* 2443 */     } else if (node._values == null) {
/* 2444 */       node._values = new Object[this._lnodeValues];
/* 2445 */     } else if (node._values.length != this._lnodeValues) {
/* 2446 */       Object[] valuesOld = node._values;
/* 2447 */       Object[] valuesNew = new Object[this._lnodeValues];
/* 2448 */       int n = MathPlus.min(valuesOld.length, valuesNew.length);
/* 2449 */       for (int i = 0; i < n; i++)
/* 2450 */         valuesNew[i] = valuesOld[i]; 
/* 2451 */       node._values = valuesNew;
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
/*      */   
/*      */   private Tri makeTri(Node n0, Node n1, Node n2) {
/* 2482 */     this._ntri++;
/* 2483 */     int ndead = this._deadTris.ntri();
/* 2484 */     if (ndead == 0) {
/* 2485 */       this._troot = new Tri(n0, n1, n2);
/*      */     } else {
/* 2487 */       this._troot = this._deadTris.remove(ndead - 1);
/* 2488 */       this._troot.init(n0, n1, n2);
/*      */     } 
/* 2490 */     if (this._ntriListeners > 0)
/* 2491 */       fireTriAdded(this._troot); 
/* 2492 */     return this._troot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void killTri(Tri tri) {
/* 2500 */     this._ntri--;
/* 2501 */     fireTriRemoved(tri);
/* 2502 */     int ndead = this._deadTris.ntri();
/* 2503 */     if (ndead < 256) {
/* 2504 */       this._deadTris.add(tri);
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
/*      */   private static double distanceSquared(Node node, double x, double y) {
/* 2518 */     double dx = x - node._x;
/* 2519 */     double dy = y - node._y;
/* 2520 */     return dx * dx + dy * dy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean leftOfLine(Node a, Node b, Node n) {
/* 2528 */     return (Geometry.leftOfLine(a._x, a._y, b._x, b._y, n._x, n._y) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean leftOfLine(Node a, Node b, double x, double y) {
/* 2538 */     return (Geometry.leftOfLine(a._x, a._y, b._x, b._y, x, y) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inCircle(Node a, Node b, Node c, Node n) {
/* 2548 */     return (Geometry.inCircle(a._x, a._y, b._x, b._y, c._x, c._y, n._x, n._y) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inCircle(Node a, Node b, Node c, double x, double y) {
/* 2558 */     return (Geometry.inCircle(a._x, a._y, b._x, b._y, c._x, c._y, x, y) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createFirstTri() {
/* 2565 */     Check.state((this._nnode == 3), "exactly three nodes available for first tri");
/* 2566 */     Node n0 = this._nroot;
/* 2567 */     Node n1 = n0._next;
/* 2568 */     Node n2 = n1._next;
/* 2569 */     double orient = Geometry.leftOfLine(n0
/* 2570 */         ._x, n0._y, n1
/* 2571 */         ._x, n1._y, n2
/* 2572 */         ._x, n2._y);
/* 2573 */     Check.state((orient != 0.0D), "three nodes for first tri are not co-linear");
/* 2574 */     if (orient > 0.0D) {
/* 2575 */       makeTri(n0, n1, n2);
/*      */     } else {
/* 2577 */       makeTri(n0, n2, n1);
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
/*      */   private void getNodeNabors(Node node, Tri tri, NodeList nabors) {
/* 2590 */     mark(tri);
/* 2591 */     Node n0 = tri._n0;
/* 2592 */     Node n1 = tri._n1;
/* 2593 */     Node n2 = tri._n2;
/* 2594 */     Tri t0 = tri._t0;
/* 2595 */     Tri t1 = tri._t1;
/* 2596 */     Tri t2 = tri._t2;
/* 2597 */     if (node == n0) {
/* 2598 */       if (!isMarked(n1)) {
/* 2599 */         mark(n1);
/* 2600 */         nabors.add(n1);
/*      */       } 
/* 2602 */       if (!isMarked(n2)) {
/* 2603 */         mark(n2);
/* 2604 */         nabors.add(n2);
/*      */       } 
/* 2606 */       if (t1 != null && !isMarked(t1))
/* 2607 */         getNodeNabors(node, t1, nabors); 
/* 2608 */       if (t2 != null && !isMarked(t2))
/* 2609 */         getNodeNabors(node, t2, nabors); 
/* 2610 */     } else if (node == n1) {
/* 2611 */       if (!isMarked(n2)) {
/* 2612 */         mark(n2);
/* 2613 */         nabors.add(n2);
/*      */       } 
/* 2615 */       if (!isMarked(n0)) {
/* 2616 */         mark(n0);
/* 2617 */         nabors.add(n0);
/*      */       } 
/* 2619 */       if (t2 != null && !isMarked(t2))
/* 2620 */         getNodeNabors(node, t2, nabors); 
/* 2621 */       if (t0 != null && !isMarked(t0))
/* 2622 */         getNodeNabors(node, t0, nabors); 
/* 2623 */     } else if (node == n2) {
/* 2624 */       if (!isMarked(n0)) {
/* 2625 */         mark(n0);
/* 2626 */         nabors.add(n0);
/*      */       } 
/* 2628 */       if (!isMarked(n1)) {
/* 2629 */         mark(n1);
/* 2630 */         nabors.add(n1);
/*      */       } 
/* 2632 */       if (t0 != null && !isMarked(t0))
/* 2633 */         getNodeNabors(node, t0, nabors); 
/* 2634 */       if (t1 != null && !isMarked(t1))
/* 2635 */         getNodeNabors(node, t1, nabors); 
/*      */     } else {
/* 2637 */       assert false : "node is referenced by tri";
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
/*      */   private void getNodeNabors(Node node, int step, NodeStepList nabors) {
/* 2649 */     Tri[] tris = getTriNabors(node);
/* 2650 */     int ntri = tris.length;
/* 2651 */     for (int itri = 0; itri < ntri; itri++) {
/* 2652 */       Tri tri = tris[itri];
/* 2653 */       Node n0 = tri._n0;
/* 2654 */       Node n1 = tri._n1;
/* 2655 */       Node n2 = tri._n2;
/* 2656 */       if (node == n0) {
/* 2657 */         if (!isMarked(n1)) {
/* 2658 */           mark(n1);
/* 2659 */           nabors.add(n1, step);
/*      */         } 
/* 2661 */         if (!isMarked(n2)) {
/* 2662 */           mark(n2);
/* 2663 */           nabors.add(n2, step);
/*      */         } 
/* 2665 */       } else if (node == n1) {
/* 2666 */         if (!isMarked(n0)) {
/* 2667 */           mark(n0);
/* 2668 */           nabors.add(n0, step);
/*      */         } 
/* 2670 */         if (!isMarked(n2)) {
/* 2671 */           mark(n2);
/* 2672 */           nabors.add(n2, step);
/*      */         } 
/* 2674 */       } else if (node == n2) {
/* 2675 */         if (!isMarked(n0)) {
/* 2676 */           mark(n0);
/* 2677 */           nabors.add(n0, step);
/*      */         } 
/* 2679 */         if (!isMarked(n1)) {
/* 2680 */           mark(n1);
/* 2681 */           nabors.add(n1, step);
/*      */         } 
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
/*      */   private void getTriNabors(Node node, Tri tri, TriList nabors) {
/* 2694 */     if (tri != null) {
/* 2695 */       mark(tri);
/* 2696 */       nabors.add(tri);
/* 2697 */       Node n0 = tri._n0;
/* 2698 */       Node n1 = tri._n1;
/* 2699 */       Node n2 = tri._n2;
/* 2700 */       Tri t0 = tri._t0;
/* 2701 */       Tri t1 = tri._t1;
/* 2702 */       Tri t2 = tri._t2;
/* 2703 */       if (node == n0) {
/* 2704 */         if (t1 != null && !isMarked(t1))
/* 2705 */           getTriNabors(node, t1, nabors); 
/* 2706 */         if (t2 != null && !isMarked(t2))
/* 2707 */           getTriNabors(node, t2, nabors); 
/* 2708 */       } else if (node == n1) {
/* 2709 */         if (t2 != null && !isMarked(t2))
/* 2710 */           getTriNabors(node, t2, nabors); 
/* 2711 */         if (t0 != null && !isMarked(t0))
/* 2712 */           getTriNabors(node, t0, nabors); 
/* 2713 */       } else if (node == n2) {
/* 2714 */         if (t0 != null && !isMarked(t0))
/* 2715 */           getTriNabors(node, t0, nabors); 
/* 2716 */         if (t1 != null && !isMarked(t1))
/* 2717 */           getTriNabors(node, t1, nabors); 
/*      */       } else {
/* 2719 */         assert false : "node is referenced by tri";
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node findNodeNearest(double x, double y) {
/* 2730 */     this._nmin = this._nroot;
/* 2731 */     this._dmin = distanceSquared(this._nmin, x, y);
/* 2732 */     for (Node n : this._sampledNodes) {
/* 2733 */       double d = distanceSquared(n, x, y);
/* 2734 */       if (d < this._dmin) {
/* 2735 */         this._dmin = d;
/* 2736 */         this._nmin = n;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2745 */     clearNodeMarks();
/*      */     
/*      */     while (true) {
/* 2748 */       clearTriMarks();
/* 2749 */       double dmin = this._dmin;
/* 2750 */       findNodeNaborNearest(x, y, this._nmin, this._nmin._tri);
/* 2751 */       if (this._dmin >= dmin) {
/* 2752 */         return this._nmin;
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
/*      */   private void findNodeNaborNearest(double x, double y, Node node, Tri tri) {
/* 2764 */     mark(tri);
/* 2765 */     Node n0 = tri._n0;
/* 2766 */     Node n1 = tri._n1;
/* 2767 */     Node n2 = tri._n2;
/* 2768 */     Tri t0 = tri._t0;
/* 2769 */     Tri t1 = tri._t1;
/* 2770 */     Tri t2 = tri._t2;
/* 2771 */     if (node == n0) {
/* 2772 */       findNodeNaborNearest(x, y, node, n1, n2, t1, t2);
/* 2773 */     } else if (node == n1) {
/* 2774 */       findNodeNaborNearest(x, y, node, n2, n0, t2, t0);
/* 2775 */     } else if (node == n2) {
/* 2776 */       findNodeNaborNearest(x, y, node, n0, n1, t0, t1);
/*      */     } else {
/* 2778 */       assert false : "node is referenced by tri";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void findNodeNaborNearest(double x, double y, Node node, Node na, Node nb, Tri ta, Tri tb) {
/* 2785 */     if (!isMarked(na)) {
/* 2786 */       mark(na);
/* 2787 */       double da = distanceSquared(na, x, y);
/* 2788 */       if (da < this._dmin) {
/* 2789 */         this._dmin = da;
/* 2790 */         this._nmin = na;
/*      */       } 
/*      */     } 
/* 2793 */     if (!isMarked(nb)) {
/* 2794 */       mark(nb);
/* 2795 */       double db = distanceSquared(nb, x, y);
/* 2796 */       if (db < this._dmin) {
/* 2797 */         this._dmin = db;
/* 2798 */         this._nmin = nb;
/*      */       } 
/*      */     } 
/* 2801 */     if (ta != null && !isMarked(ta))
/* 2802 */       findNodeNaborNearest(x, y, node, ta); 
/* 2803 */     if (tb != null && !isMarked(tb)) {
/* 2804 */       findNodeNaborNearest(x, y, node, tb);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tri findTri(Tri tri, Node na, Node nb) {
/* 2813 */     if (tri != null) {
/* 2814 */       mark(tri);
/* 2815 */       Node n0 = tri._n0;
/* 2816 */       Node n1 = tri._n1;
/* 2817 */       Node n2 = tri._n2;
/* 2818 */       Tri t0 = tri._t0;
/* 2819 */       Tri t1 = tri._t1;
/* 2820 */       Tri t2 = tri._t2;
/* 2821 */       if (na == n0) {
/* 2822 */         if (nb == n1 || nb == n2 || (t1 != null && 
/* 2823 */           !isMarked(t1) && (tri = findTri(t1, na, nb)) != null) || (t2 != null && 
/* 2824 */           !isMarked(t2) && (tri = findTri(t2, na, nb)) != null))
/* 2825 */           return tri; 
/* 2826 */       } else if (na == n1) {
/* 2827 */         if (nb == n2 || nb == n0 || (t2 != null && 
/* 2828 */           !isMarked(t2) && (tri = findTri(t2, na, nb)) != null) || (t0 != null && 
/* 2829 */           !isMarked(t0) && (tri = findTri(t0, na, nb)) != null))
/* 2830 */           return tri; 
/* 2831 */       } else if (na == n2) {
/* 2832 */         if (nb == n0 || nb == n1 || (t0 != null && 
/* 2833 */           !isMarked(t0) && (tri = findTri(t0, na, nb)) != null) || (t1 != null && 
/* 2834 */           !isMarked(t1) && (tri = findTri(t1, na, nb)) != null))
/* 2835 */           return tri; 
/*      */       } else {
/* 2837 */         assert false : "node na is referenced by tri";
/*      */       } 
/*      */     } 
/* 2840 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tri findTri(Tri tri, Node na, Node nb, Node nc) {
/* 2849 */     if (tri != null) {
/* 2850 */       mark(tri);
/* 2851 */       Node n0 = tri._n0;
/* 2852 */       Node n1 = tri._n1;
/* 2853 */       Node n2 = tri._n2;
/* 2854 */       Tri t0 = tri._t0;
/* 2855 */       Tri t1 = tri._t1;
/* 2856 */       Tri t2 = tri._t2;
/* 2857 */       if (na == n0) {
/* 2858 */         if (nb == n1) {
/* 2859 */           if (nc == n2 || (t2 != null && 
/* 2860 */             !isMarked(t2) && (tri = findTri(t2, na, nb, nc)) != null))
/* 2861 */             return tri; 
/* 2862 */         } else if (nb == n2) {
/* 2863 */           if (nc == n1 || (t1 != null && 
/* 2864 */             !isMarked(t1) && (tri = findTri(t1, na, nb, nc)) != null))
/* 2865 */             return tri; 
/*      */         } else {
/* 2867 */           assert false : "node nb is referenced by tri";
/*      */         } 
/* 2869 */       } else if (na == n1) {
/* 2870 */         if (nb == n0) {
/* 2871 */           if (nc == n2 || (t2 != null && 
/* 2872 */             !isMarked(t2) && (tri = findTri(t2, na, nb, nc)) != null))
/* 2873 */             return tri; 
/* 2874 */         } else if (nb == n2) {
/* 2875 */           if (nc == n0 || (t0 != null && 
/* 2876 */             !isMarked(t0) && (tri = findTri(t0, na, nb, nc)) != null))
/* 2877 */             return tri; 
/*      */         } else {
/* 2879 */           assert false : "node nb is referenced by tri";
/*      */         } 
/* 2881 */       } else if (na == n2) {
/* 2882 */         if (nb == n0) {
/* 2883 */           if (nc == n1 || (t1 != null && 
/* 2884 */             !isMarked(t1) && (tri = findTri(t1, na, nb, nc)) != null))
/* 2885 */             return tri; 
/* 2886 */         } else if (nb == n1) {
/* 2887 */           if (nc == n0 || (t0 != null && 
/* 2888 */             !isMarked(t0) && (tri = findTri(t0, na, nb, nc)) != null))
/* 2889 */             return tri; 
/*      */         } else {
/* 2891 */           assert false : "node nb is referenced by tri";
/*      */         } 
/*      */       } else {
/* 2894 */         assert false : "node na is referenced by tri";
/*      */       } 
/*      */     } 
/* 2897 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PointLocation locatePoint(double x, double y) {
/* 2907 */     if (this._troot == null) {
/* 2908 */       if (this._nroot != null) {
/* 2909 */         Node node = this._nroot;
/*      */         do {
/* 2911 */           if (x == node.x() && y == node.y())
/* 2912 */             return new PointLocation(node); 
/* 2913 */           node = node._next;
/* 2914 */         } while (node != this._nroot);
/*      */       } 
/* 2916 */       return new PointLocation(null, false);
/*      */     } 
/*      */ 
/*      */     
/* 2920 */     Node nmin = this._nroot;
/* 2921 */     double dmin = distanceSquared(nmin, x, y);
/* 2922 */     for (Node n : this._sampledNodes) {
/* 2923 */       double d = distanceSquared(n, x, y);
/* 2924 */       if (d < dmin) {
/* 2925 */         dmin = d;
/* 2926 */         nmin = n;
/*      */       } 
/*      */     } 
/* 2929 */     Tri tri = nmin._tri;
/* 2930 */     return locatePoint(tri, x, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PointLocation locatePoint(Tri tri, double x, double y) {
/* 2940 */     this._troot = tri;
/*      */ 
/*      */     
/* 2943 */     Node n0 = tri._n0;
/* 2944 */     Node n1 = tri._n1;
/* 2945 */     Node n2 = tri._n2;
/* 2946 */     double x0 = n0._x;
/* 2947 */     double y0 = n0._y;
/* 2948 */     double x1 = n1._x;
/* 2949 */     double y1 = n1._y;
/* 2950 */     double x2 = n2._x;
/* 2951 */     double y2 = n2._y;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2956 */     if (x == x0 && y == y0)
/* 2957 */       return new PointLocation(n0); 
/* 2958 */     if (x == x1 && y == y1)
/* 2959 */       return new PointLocation(n1); 
/* 2960 */     if (x == x2 && y == y2) {
/* 2961 */       return new PointLocation(n2);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2971 */     double d0 = Geometry.leftOfLine(x2, y2, x1, y1, x, y);
/* 2972 */     if (d0 > 0.0D) {
/* 2973 */       Tri triNabor = tri.triNabor(n0);
/* 2974 */       if (triNabor != null) {
/* 2975 */         return locatePoint(triNabor, x, y);
/*      */       }
/* 2977 */       return new PointLocation(tri, false);
/*      */     } 
/*      */     
/* 2980 */     double d1 = Geometry.leftOfLine(x0, y0, x2, y2, x, y);
/* 2981 */     if (d1 > 0.0D) {
/* 2982 */       Tri triNabor = tri.triNabor(n1);
/* 2983 */       if (triNabor != null) {
/* 2984 */         return locatePoint(triNabor, x, y);
/*      */       }
/* 2986 */       return new PointLocation(tri, false);
/*      */     } 
/*      */     
/* 2989 */     double d2 = Geometry.leftOfLine(x1, y1, x0, y0, x, y);
/* 2990 */     if (d2 > 0.0D) {
/* 2991 */       Tri triNabor = tri.triNabor(n2);
/* 2992 */       if (triNabor != null) {
/* 2993 */         return locatePoint(triNabor, x, y);
/*      */       }
/* 2995 */       return new PointLocation(tri, false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3000 */     if (d0 < 0.0D && d1 < 0.0D && d2 < 0.0D) {
/* 3001 */       return new PointLocation(tri);
/*      */     }
/*      */ 
/*      */     
/* 3005 */     if (d0 == 0.0D)
/* 3006 */       return new PointLocation(new Edge(tri, n0)); 
/* 3007 */     if (d1 == 0.0D)
/* 3008 */       return new PointLocation(new Edge(tri, n1)); 
/* 3009 */     if (d2 == 0.0D) {
/* 3010 */       return new PointLocation(new Edge(tri, n2));
/*      */     }
/*      */ 
/*      */     
/* 3014 */     assert false : "successfully located the point";
/* 3015 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDelaunayEdgesInside(Node node, Tri tri) {
/* 3024 */     if (tri != null && !isMarked(tri)) {
/* 3025 */       mark(tri);
/* 3026 */       Node n0 = tri._n0;
/* 3027 */       Node n1 = tri._n1;
/* 3028 */       Node n2 = tri._n2;
/* 3029 */       if (inCircle(n0, n1, n2, node)) {
/* 3030 */         killTri(tri);
/* 3031 */         Tri t0 = tri._t0;
/* 3032 */         Tri t1 = tri._t1;
/* 3033 */         Tri t2 = tri._t2;
/* 3034 */         this._edgeSet.addMate(tri, n0);
/* 3035 */         this._edgeSet.addMate(tri, n1);
/* 3036 */         this._edgeSet.addMate(tri, n2);
/* 3037 */         getDelaunayEdgesInside(node, t0);
/* 3038 */         getDelaunayEdgesInside(node, t1);
/* 3039 */         getDelaunayEdgesInside(node, t2);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDelaunayEdgesOutside(Node node, Tri tri) {
/* 3050 */     if (tri != null && !isMarked(tri)) {
/* 3051 */       mark(tri);
/* 3052 */       Node n0 = tri._n0;
/* 3053 */       Node n1 = tri._n1;
/* 3054 */       Node n2 = tri._n2;
/* 3055 */       Tri t0 = tri._t0;
/* 3056 */       Tri t1 = tri._t1;
/* 3057 */       Tri t2 = tri._t2;
/* 3058 */       if (t0 == null && leftOfLine(n2, n1, node)) {
/* 3059 */         this._edgeSet.add(tri, n0);
/* 3060 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n1, n0));
/* 3061 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n2, n0));
/*      */       } 
/* 3063 */       if (t1 == null && leftOfLine(n0, n2, node)) {
/* 3064 */         this._edgeSet.add(tri, n1);
/* 3065 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n2, n1));
/* 3066 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n0, n1));
/*      */       } 
/* 3068 */       if (t2 == null && leftOfLine(n1, n0, node)) {
/* 3069 */         this._edgeSet.add(tri, n2);
/* 3070 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n0, n2));
/* 3071 */         getDelaunayEdgesOutside(node, getNextTriOnHull(tri, n1, n2));
/*      */       } 
/* 3073 */       if (inCircle(n0, n1, n2, node)) {
/* 3074 */         killTri(tri);
/* 3075 */         this._edgeSet.addMate(tri, n0);
/* 3076 */         this._edgeSet.addMate(tri, n1);
/* 3077 */         this._edgeSet.addMate(tri, n2);
/* 3078 */         getDelaunayEdgesOutside(node, t0);
/* 3079 */         getDelaunayEdgesOutside(node, t1);
/* 3080 */         getDelaunayEdgesOutside(node, t2);
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
/*      */   private void getDelaunayEdgesOpposite(Node node, Tri tri) {
/* 3093 */     if (tri != null && !isMarked(tri)) {
/* 3094 */       mark(tri);
/* 3095 */       killTri(tri);
/* 3096 */       Node n0 = tri._n0;
/* 3097 */       Node n1 = tri._n1;
/* 3098 */       Node n2 = tri._n2;
/* 3099 */       Tri t0 = tri._t0;
/* 3100 */       Tri t1 = tri._t1;
/* 3101 */       Tri t2 = tri._t2;
/* 3102 */       if (node == n0) {
/* 3103 */         this._edgeSet.addMate(tri, n0);
/* 3104 */         getDelaunayEdgesOpposite(node, n1, n2, t1, t2);
/* 3105 */       } else if (node == n1) {
/* 3106 */         this._edgeSet.addMate(tri, n1);
/* 3107 */         getDelaunayEdgesOpposite(node, n2, n0, t2, t0);
/* 3108 */       } else if (node == n2) {
/* 3109 */         this._edgeSet.addMate(tri, n2);
/* 3110 */         getDelaunayEdgesOpposite(node, n0, n1, t0, t1);
/*      */       } else {
/* 3112 */         assert false : "node is referenced by tri";
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void getDelaunayEdgesOpposite(Node node, Node na, Node nb, Tri ta, Tri tb) {
/* 3119 */     if (!isMarked(na)) {
/* 3120 */       mark(na);
/* 3121 */       this._nodeList.add(na);
/*      */     } 
/* 3123 */     if (!isMarked(nb)) {
/* 3124 */       mark(nb);
/* 3125 */       this._nodeList.add(nb);
/*      */     } 
/* 3127 */     getDelaunayEdgesOpposite(node, ta);
/* 3128 */     getDelaunayEdgesOpposite(node, tb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tri getNextTriOnHull(Tri tri, Node node, Node nodeOther) {
/* 3138 */     for (Tri tnext = tri.triNabor(node); tnext != null; tnext = tri.triNabor(node)) {
/* 3139 */       node = nodeOther;
/* 3140 */       nodeOther = tri.nodeNabor(tnext);
/* 3141 */       tri = tnext;
/*      */     } 
/* 3143 */     return tri;
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
/*      */   public synchronized Node findNodeNearestSlow(float x, float y) {
/* 3158 */     clearTriMarks();
/* 3159 */     clearNodeMarks();
/*      */ 
/*      */     
/* 3162 */     this._dmin = Double.MAX_VALUE;
/* 3163 */     this._nmin = null;
/* 3164 */     if (this._troot == null) {
/* 3165 */       if (this._nroot != null) {
/* 3166 */         Node node = this._nroot;
/*      */         do {
/* 3168 */           updateNodeNearest(x, y, node);
/* 3169 */           node = node._next;
/* 3170 */         } while (node != this._nroot);
/*      */       } 
/* 3172 */       assert this._nmin != null;
/* 3173 */       return this._nmin;
/*      */     } 
/*      */ 
/*      */     
/* 3177 */     PointLocation pl = locatePoint(x, y);
/*      */ 
/*      */     
/* 3180 */     if (pl.isOnNode()) {
/* 3181 */       updateNodeNearest(x, y, pl.node());
/* 3182 */       return this._nmin;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3187 */     if (pl.isInside()) {
/* 3188 */       findNodeNearestInside(x, y, pl.tri());
/*      */     } else {
/* 3190 */       findNodeNearestOutside(x, y, pl.tri());
/*      */     } 
/* 3192 */     return this._nmin;
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
/*      */   private void findNodeNearestInside(double x, double y, Tri tri) {
/* 3204 */     if (tri != null && !isMarked(tri)) {
/* 3205 */       mark(tri);
/* 3206 */       Node n0 = tri._n0;
/* 3207 */       Node n1 = tri._n1;
/* 3208 */       Node n2 = tri._n2;
/* 3209 */       updateNodeNearest(x, y, n0);
/* 3210 */       updateNodeNearest(x, y, n1);
/* 3211 */       updateNodeNearest(x, y, n2);
/* 3212 */       if (inCircle(n0, n1, n2, x, y)) {
/* 3213 */         Tri t0 = tri._t0;
/* 3214 */         Tri t1 = tri._t1;
/* 3215 */         Tri t2 = tri._t2;
/* 3216 */         findNodeNearestInside(x, y, t0);
/* 3217 */         findNodeNearestInside(x, y, t1);
/* 3218 */         findNodeNearestInside(x, y, t2);
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
/*      */   private void findNodeNearestOutside(double x, double y, Tri tri) {
/* 3231 */     if (tri != null && !isMarked(tri)) {
/* 3232 */       mark(tri);
/* 3233 */       Node n0 = tri._n0;
/* 3234 */       Node n1 = tri._n1;
/* 3235 */       Node n2 = tri._n2;
/* 3236 */       updateNodeNearest(x, y, n0);
/* 3237 */       updateNodeNearest(x, y, n1);
/* 3238 */       updateNodeNearest(x, y, n2);
/* 3239 */       Tri t0 = tri._t0;
/* 3240 */       Tri t1 = tri._t1;
/* 3241 */       Tri t2 = tri._t2;
/* 3242 */       if (t0 == null && leftOfLine(n2, n1, x, y)) {
/* 3243 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n1, n0));
/* 3244 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n2, n0));
/*      */       } 
/* 3246 */       if (t1 == null && leftOfLine(n0, n2, x, y)) {
/* 3247 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n2, n1));
/* 3248 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n0, n1));
/*      */       } 
/* 3250 */       if (t2 == null && leftOfLine(n1, n0, x, y)) {
/* 3251 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n0, n2));
/* 3252 */         findNodeNearestOutside(x, y, getNextTriOnHull(tri, n1, n2));
/*      */       } 
/* 3254 */       if (inCircle(n0, n1, n2, x, y)) {
/* 3255 */         findNodeNearestOutside(x, y, t0);
/* 3256 */         findNodeNearestOutside(x, y, t1);
/* 3257 */         findNodeNearestOutside(x, y, t2);
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
/*      */   private void updateNodeNearest(double x, double y, Node n) {
/* 3270 */     if (!isMarked(n)) {
/* 3271 */       mark(n);
/* 3272 */       double d = distanceSquared(n, x, y);
/* 3273 */       if (d < this._dmin) {
/* 3274 */         this._dmin = d;
/* 3275 */         this._nmin = n;
/* 3276 */         this._nroot = n;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void linkTris(Tri tri, Node node, Tri triNabor, Node nodeNabor) {
/* 3285 */     if (tri != null) {
/* 3286 */       if (node == tri._n0) {
/* 3287 */         tri._t0 = triNabor;
/* 3288 */       } else if (node == tri._n1) {
/* 3289 */         tri._t1 = triNabor;
/* 3290 */       } else if (node == tri._n2) {
/* 3291 */         tri._t2 = triNabor;
/*      */       } else {
/* 3293 */         assert false : "node referenced by tri";
/*      */       } 
/*      */     }
/* 3296 */     if (triNabor != null) {
/* 3297 */       if (nodeNabor == triNabor._n0) {
/* 3298 */         triNabor._t0 = tri;
/* 3299 */       } else if (nodeNabor == triNabor._n1) {
/* 3300 */         triNabor._t1 = tri;
/* 3301 */       } else if (nodeNabor == triNabor._n2) {
/* 3302 */         triNabor._t2 = tri;
/*      */       } else {
/* 3304 */         assert false : "nodeNabor referenced by triNabor";
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markAllTris(Tri tri) {
/* 3313 */     tri._mark = this._triMarkRed;
/* 3314 */     Tri t0 = tri._t0;
/* 3315 */     if (t0 != null && t0._mark != this._triMarkRed)
/* 3316 */       markAllTris(t0); 
/* 3317 */     Tri t1 = tri._t1;
/* 3318 */     if (t1 != null && t1._mark != this._triMarkRed)
/* 3319 */       markAllTris(t1); 
/* 3320 */     Tri t2 = tri._t2;
/* 3321 */     if (t2 != null && t2._mark != this._triMarkRed) {
/* 3322 */       markAllTris(t2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void zeroTriMarks(Tri tri) {
/* 3330 */     tri._mark = 0;
/* 3331 */     Tri t0 = tri._t0;
/* 3332 */     if (t0 != null && t0._mark != 0)
/* 3333 */       zeroTriMarks(t0); 
/* 3334 */     Tri t1 = tri._t1;
/* 3335 */     if (t1 != null && t1._mark != 0)
/* 3336 */       zeroTriMarks(t1); 
/* 3337 */     Tri t2 = tri._t2;
/* 3338 */     if (t2 != null && t2._mark != 0) {
/* 3339 */       zeroTriMarks(t2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Edge getEdgeOnHull(Tri tri) {
/* 3348 */     mark(tri);
/* 3349 */     if (tri._t0 == null)
/* 3350 */       return new Edge(tri, tri._n0); 
/* 3351 */     if (tri._t1 == null)
/* 3352 */       return new Edge(tri, tri._n1); 
/* 3353 */     if (tri._t2 == null)
/* 3354 */       return new Edge(tri, tri._n2); 
/* 3355 */     if (!isMarked(tri._t0)) {
/* 3356 */       Edge edge = getEdgeOnHull(tri._t0);
/* 3357 */       if (edge != null)
/* 3358 */         return edge; 
/*      */     } 
/* 3360 */     if (!isMarked(tri._t1)) {
/* 3361 */       Edge edge = getEdgeOnHull(tri._t1);
/* 3362 */       if (edge != null)
/* 3363 */         return edge; 
/*      */     } 
/* 3365 */     if (!isMarked(tri._t2)) {
/* 3366 */       Edge edge = getEdgeOnHull(tri._t2);
/* 3367 */       if (edge != null)
/* 3368 */         return edge; 
/*      */     } 
/* 3370 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getEdgesOnHull(Edge edge, HashSet<Edge> edges) {
/* 3378 */     if (!edges.contains(edge)) {
/* 3379 */       edges.add(edge);
/* 3380 */       getEdgesOnHull(getNextEdgeOnHull(edge.nodeA(), edge), edges);
/* 3381 */       getEdgesOnHull(getNextEdgeOnHull(edge.nodeB(), edge), edges);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Edge getNextEdgeOnHull(Node node, Edge edge) {
/* 3390 */     Tri tri = edge.triLeft();
/* 3391 */     Node next = edge.nodeLeft();
/* 3392 */     for (Tri tnext = tri.triNabor(node); tnext != null; tnext = tri.triNabor(node)) {
/* 3393 */       node = next;
/* 3394 */       next = tri.nodeNabor(tnext);
/* 3395 */       tri = tnext;
/*      */     } 
/* 3397 */     return new Edge(tri, node);
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
/*      */   private static Edge edgeOfTri(Tri tri, Node na, Node nb) {
/* 3421 */     Node n0 = tri._n0;
/* 3422 */     Node n1 = tri._n1;
/* 3423 */     Node n2 = tri._n2;
/* 3424 */     if (na == n0) {
/* 3425 */       if (nb == n1)
/* 3426 */         return new Edge(tri, n2); 
/* 3427 */       if (nb == n2) {
/* 3428 */         return new Edge(tri, n1);
/*      */       }
/* 3430 */       return null;
/*      */     } 
/* 3432 */     if (na == n1) {
/* 3433 */       if (nb == n0)
/* 3434 */         return new Edge(tri, n2); 
/* 3435 */       if (nb == n2) {
/* 3436 */         return new Edge(tri, n0);
/*      */       }
/* 3438 */       return null;
/*      */     } 
/* 3440 */     if (na == n2) {
/* 3441 */       if (nb == n0)
/* 3442 */         return new Edge(tri, n1); 
/* 3443 */       if (nb == n1) {
/* 3444 */         return new Edge(tri, n0);
/*      */       }
/* 3446 */       return null;
/*      */     } 
/*      */     
/* 3449 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean nodesInOrder(Tri tri, Node na, Node nb, Node nc) {
/* 3459 */     Node n0 = tri._n0;
/* 3460 */     Node n1 = tri._n1;
/* 3461 */     Node n2 = tri._n2;
/* 3462 */     return ((na == n0 && nb == n1 && nc == n2) || (na == n1 && nb == n2 && nc == n0) || (na == n2 && nb == n0 && nc == n1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Node otherNode(Tri tri, Node na, Node nb) {
/* 3473 */     Node n0 = tri._n0;
/* 3474 */     Node n1 = tri._n1;
/* 3475 */     Node n2 = tri._n2;
/* 3476 */     if (na == n0) {
/* 3477 */       if (nb == n1)
/* 3478 */         return n2; 
/* 3479 */       if (nb == n2) {
/* 3480 */         return n1;
/*      */       }
/* 3482 */       return null;
/*      */     } 
/* 3484 */     if (na == n1) {
/* 3485 */       if (nb == n0)
/* 3486 */         return n2; 
/* 3487 */       if (nb == n2) {
/* 3488 */         return n0;
/*      */       }
/* 3490 */       return null;
/*      */     } 
/* 3492 */     if (na == n2) {
/* 3493 */       if (nb == n0)
/* 3494 */         return n1; 
/* 3495 */       if (nb == n1) {
/* 3496 */         return n0;
/*      */       }
/* 3498 */       return null;
/*      */     } 
/*      */     
/* 3501 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void markTriInnerOrOuter(Tri tri) {
/* 3510 */     assert this._xminOuter < this._xmaxOuter : "outer box is valid";
/* 3511 */     assert this._yminOuter < this._ymaxOuter : "outer box is valid";
/* 3512 */     double[] po = { 0.0D, 0.0D };
/* 3513 */     double s = tri.centerCircle(po);
/* 3514 */     double r = MathPlus.sqrt(s);
/* 3515 */     double xo = po[0];
/* 3516 */     double yo = po[1];
/* 3517 */     if (xo - r >= this._xminOuter && yo - r >= this._yminOuter && xo + r <= this._xmaxOuter && yo + r <= this._ymaxOuter) {
/*      */ 
/*      */ 
/*      */       
/* 3521 */       tri.setInner();
/* 3522 */       tri.clearOuter();
/*      */     } else {
/* 3524 */       tri.setOuter();
/* 3525 */       tri.clearInner();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fireNodeWillBeAdded(Node node) {
/* 3530 */     this._version++;
/* 3531 */     if (this._nnodeListeners > 0) {
/* 3532 */       Object[] list = this._listeners.getListenerList();
/* 3533 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3534 */         if (list[i] == NodeListener.class)
/* 3535 */           ((NodeListener)list[i + 1]).nodeWillBeAdded(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeAdded(Node node) {
/* 3540 */     this._version++;
/* 3541 */     if (this._nnodeListeners > 0) {
/* 3542 */       Object[] list = this._listeners.getListenerList();
/* 3543 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3544 */         if (list[i] == NodeListener.class)
/* 3545 */           ((NodeListener)list[i + 1]).nodeAdded(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeWillBeRemoved(Node node) {
/* 3550 */     this._version++;
/* 3551 */     if (this._nnodeListeners > 0) {
/* 3552 */       Object[] list = this._listeners.getListenerList();
/* 3553 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3554 */         if (list[i] == NodeListener.class)
/* 3555 */           ((NodeListener)list[i + 1]).nodeWillBeRemoved(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeRemoved(Node node) {
/* 3560 */     this._version++;
/* 3561 */     if (this._nnodeListeners > 0) {
/* 3562 */       Object[] list = this._listeners.getListenerList();
/* 3563 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3564 */         if (list[i] == NodeListener.class)
/* 3565 */           ((NodeListener)list[i + 1]).nodeRemoved(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireTriAdded(Tri tri) {
/* 3570 */     this._version++;
/* 3571 */     if (this._ntriListeners > 0) {
/* 3572 */       Object[] list = this._listeners.getListenerList();
/* 3573 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3574 */         if (list[i] == TriListener.class)
/* 3575 */           ((TriListener)list[i + 1]).triAdded(this, tri); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireTriRemoved(Tri tri) {
/* 3580 */     this._version++;
/* 3581 */     if (this._ntriListeners > 0) {
/* 3582 */       Object[] list = this._listeners.getListenerList();
/* 3583 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 3584 */         if (list[i] == TriListener.class) {
/* 3585 */           ((TriListener)list[i + 1]).triRemoved(this, tri);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void validate(Node node) {
/* 3593 */     Check.state((node == node._prev._next), "node==node._prev._next");
/* 3594 */     Check.state((node == node._next._prev), "node==node._next._prev");
/* 3595 */     Tri tri = node.tri();
/* 3596 */     if (this._troot != null) {
/* 3597 */       Check.state((tri != null), "tri!=null");
/* 3598 */       Check.state((node == tri.nodeA() || node == tri
/* 3599 */           .nodeB() || node == tri
/* 3600 */           .nodeC()), "node is one of tri nodes");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validate(Tri tri) {
/* 3609 */     Check.state((tri != null), "tri not null");
/* 3610 */     Node na = tri.nodeA();
/* 3611 */     Node nb = tri.nodeB();
/* 3612 */     Node nc = tri.nodeC();
/* 3613 */     validate(na);
/* 3614 */     validate(nb);
/* 3615 */     validate(nc);
/* 3616 */     Tri ta = tri.triA();
/* 3617 */     Tri tb = tri.triB();
/* 3618 */     Tri tc = tri.triC();
/* 3619 */     if (ta != null)
/* 3620 */       Check.state((ta.triNabor(tri.nodeNabor(ta)) == tri), "a nabors ok"); 
/* 3621 */     if (tb != null)
/* 3622 */       Check.state((tb.triNabor(tri.nodeNabor(tb)) == tri), "b nabors ok"); 
/* 3623 */     if (tc != null) {
/* 3624 */       Check.state((tc.triNabor(tri.nodeNabor(tc)) == tri), "c nabors ok");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class EdgeSet
/*      */   {
/*      */     TriMesh.Node a;
/*      */ 
/*      */     
/*      */     TriMesh.Node b;
/*      */ 
/*      */     
/*      */     TriMesh.Node c;
/*      */ 
/*      */     
/*      */     TriMesh.Tri abc;
/*      */ 
/*      */     
/*      */     private static final int MAX_SHIFT = 30;
/*      */ 
/*      */     
/*      */     private static final int MAX_CAPACITY = 1073741824;
/*      */     
/*      */     private TriMesh.Node[] _a;
/*      */     
/*      */     private TriMesh.Node[] _b;
/*      */     
/*      */     private TriMesh.Node[] _c;
/*      */     
/*      */     private TriMesh.Tri[] _abc;
/*      */     
/*      */     private boolean[] _filled;
/*      */     
/*      */     private int _nmax;
/*      */     
/*      */     private int _n;
/*      */     
/*      */     private double _factor;
/*      */     
/*      */     private int _shift;
/*      */     
/*      */     private int _mask;
/*      */     
/*      */     private int _index;
/*      */ 
/*      */     
/*      */     EdgeSet(int capacity, double factor) {
/* 3673 */       if (capacity > 1073741824) capacity = 1073741824; 
/* 3674 */       if (factor <= 0.0D) factor = 1.0E-4D; 
/* 3675 */       if (factor >= 1.0D) factor = 0.9999D; 
/* 3676 */       for (this._nmax = 2, this._shift = 30; this._nmax < capacity; this._nmax *= 2)
/* 3677 */         this._shift--; 
/* 3678 */       this._n = 0;
/* 3679 */       this._factor = factor;
/* 3680 */       this._mask = this._nmax - 1;
/* 3681 */       this._a = new TriMesh.Node[this._nmax];
/* 3682 */       this._b = new TriMesh.Node[this._nmax];
/* 3683 */       this._c = new TriMesh.Node[this._nmax];
/* 3684 */       this._abc = new TriMesh.Tri[this._nmax];
/* 3685 */       this._filled = new boolean[this._nmax];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 3692 */       this._n = 0;
/* 3693 */       for (int i = 0; i < this._nmax; i++) {
/* 3694 */         this._filled[i] = false;
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
/*      */     boolean contains(TriMesh.Tri tri, TriMesh.Node node) {
/* 3706 */       if (node == tri._n0) {
/* 3707 */         this._index = indexOfMate(tri._n2, tri._n1);
/* 3708 */       } else if (node == tri._n1) {
/* 3709 */         this._index = indexOfMate(tri._n0, tri._n2);
/* 3710 */       } else if (node == tri._n2) {
/* 3711 */         this._index = indexOfMate(tri._n1, tri._n0);
/*      */       } else {
/* 3713 */         assert false : "node is referenced by tri";
/* 3714 */         return false;
/*      */       } 
/* 3716 */       if (this._filled[this._index]) {
/* 3717 */         setCurrent();
/* 3718 */         return true;
/*      */       } 
/* 3720 */       return false;
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
/*      */     boolean add(TriMesh.Tri tri, TriMesh.Node node) {
/* 3732 */       if (node == tri._n0)
/* 3733 */         return add(tri._n1, tri._n2, node, tri); 
/* 3734 */       if (node == tri._n1)
/* 3735 */         return add(tri._n2, tri._n0, node, tri); 
/* 3736 */       if (node == tri._n2) {
/* 3737 */         return add(tri._n0, tri._n1, node, tri);
/*      */       }
/* 3739 */       assert false : "node is referenced by tri";
/* 3740 */       return false;
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
/*      */     boolean addMate(TriMesh.Tri tri, TriMesh.Node node) {
/* 3754 */       TriMesh.Tri triNabor = tri.triNabor(node);
/* 3755 */       TriMesh.Node nodeNabor = (triNabor != null) ? tri.nodeNabor(triNabor) : null;
/* 3756 */       if (node == tri._n0)
/* 3757 */         return add(tri._n2, tri._n1, nodeNabor, triNabor); 
/* 3758 */       if (node == tri._n1)
/* 3759 */         return add(tri._n0, tri._n2, nodeNabor, triNabor); 
/* 3760 */       if (node == tri._n2) {
/* 3761 */         return add(tri._n1, tri._n0, nodeNabor, triNabor);
/*      */       }
/* 3763 */       assert false : "node is referenced by tri";
/* 3764 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean remove() {
/* 3774 */       if (this._n > 0) {
/* 3775 */         int start = this._index;
/* 3776 */         for (; this._index < this._nmax; this._index++) {
/* 3777 */           if (this._filled[this._index]) {
/* 3778 */             setCurrent();
/* 3779 */             remove(this._index);
/* 3780 */             return true;
/*      */           } 
/*      */         } 
/* 3783 */         for (this._index = 0; this._index < start; this._index++) {
/* 3784 */           if (this._filled[this._index]) {
/* 3785 */             setCurrent();
/* 3786 */             remove(this._index);
/* 3787 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/* 3791 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isEmpty() {
/* 3799 */       return (this._n > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int count() {
/* 3807 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean first() {
/* 3816 */       this._index = -1;
/* 3817 */       return next();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean next() {
/* 3826 */       this._index++; for (; this._index < this._nmax; this._index++) {
/* 3827 */         if (this._filled[this._index]) {
/* 3828 */           setCurrent();
/* 3829 */           return true;
/*      */         } 
/*      */       } 
/* 3832 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int hash(TriMesh.Node a, TriMesh.Node b) {
/* 3853 */       int key = a._hash ^ b._hash;
/*      */ 
/*      */ 
/*      */       
/* 3857 */       return 1327217885 * key >> this._shift & this._mask;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int indexOfMate(TriMesh.Node a, TriMesh.Node b) {
/* 3865 */       int i = hash(a, b);
/* 3866 */       while (this._filled[i]) {
/* 3867 */         if (a == this._b[i] && b == this._a[i])
/* 3868 */           return i; 
/* 3869 */         i = i - 1 & this._mask;
/*      */       } 
/* 3871 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setCurrent() {
/* 3878 */       this.a = this._a[this._index];
/* 3879 */       this.b = this._b[this._index];
/* 3880 */       this.c = this._c[this._index];
/* 3881 */       this.abc = this._abc[this._index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean add(TriMesh.Node a, TriMesh.Node b, TriMesh.Node c, TriMesh.Tri abc) {
/* 3891 */       this._index = indexOfMate(a, b);
/* 3892 */       if (this._filled[this._index]) {
/* 3893 */         setCurrent();
/* 3894 */         remove(this._index);
/* 3895 */         return false;
/*      */       } 
/* 3897 */       this._a[this._index] = a;
/* 3898 */       this._b[this._index] = b;
/* 3899 */       this._c[this._index] = c;
/* 3900 */       this._abc[this._index] = abc;
/* 3901 */       this._filled[this._index] = true;
/* 3902 */       this._n++;
/* 3903 */       if (this._n > this._nmax * this._factor && this._nmax < 1073741824)
/* 3904 */         doubleCapacity(); 
/* 3905 */       setCurrent();
/* 3906 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void remove(int i) {
/* 3915 */       this._n--; while (true) {
/*      */         int r;
/* 3917 */         this._filled[i] = false;
/* 3918 */         int j = i;
/*      */         
/*      */         do {
/* 3921 */           i = i - 1 & this._mask;
/* 3922 */           if (!this._filled[i])
/*      */             return; 
/* 3924 */           r = hash(this._a[i], this._b[i]);
/* 3925 */         } while ((i <= r && r < j) || (r < j && j < i) || (j < i && i <= r));
/* 3926 */         this._a[j] = this._a[i];
/* 3927 */         this._b[j] = this._b[i];
/* 3928 */         this._c[j] = this._c[i];
/* 3929 */         this._abc[j] = this._abc[i];
/* 3930 */         this._filled[j] = this._filled[i];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doubleCapacity() {
/* 3939 */       EdgeSet set = new EdgeSet(2 * this._nmax, this._factor);
/* 3940 */       if (this._n > 0)
/* 3941 */         for (int i = 0; i < this._nmax; i++) {
/* 3942 */           if (this._filled[i]) {
/* 3943 */             set.add(this._a[i], this._b[i], this._c[i], this._abc[i]);
/*      */           }
/*      */         }  
/* 3946 */       this._a = set._a;
/* 3947 */       this._b = set._b;
/* 3948 */       this._c = set._c;
/* 3949 */       this._abc = set._abc;
/* 3950 */       this._filled = set._filled;
/* 3951 */       this._nmax = set._nmax;
/* 3952 */       this._n = set._n;
/* 3953 */       this._factor = set._factor;
/* 3954 */       this._shift = set._shift;
/* 3955 */       this._mask = set._mask;
/* 3956 */       this._index = set._index;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class NodeSet
/*      */   {
/*      */     TriMesh.Node a;
/*      */     
/*      */     TriMesh.Node b;
/*      */     
/*      */     TriMesh.Tri nba;
/*      */     
/*      */     private static final int MAX_SHIFT = 30;
/*      */     private static final int MAX_CAPACITY = 1073741824;
/*      */     private TriMesh.Node[] _a;
/*      */     private TriMesh.Node[] _b;
/*      */     private TriMesh.Tri[] _nba;
/*      */     private boolean[] _filled;
/*      */     private int _nmax;
/*      */     private int _n;
/*      */     private double _factor;
/*      */     private int _shift;
/*      */     private int _mask;
/*      */     private int _index;
/*      */     
/*      */     NodeSet(int capacity, double factor) {
/* 3983 */       if (capacity > 1073741824) capacity = 1073741824; 
/* 3984 */       if (factor <= 0.0D) factor = 1.0E-4D; 
/* 3985 */       if (factor >= 1.0D) factor = 0.9999D; 
/* 3986 */       for (this._nmax = 2, this._shift = 30; this._nmax < capacity; this._nmax *= 2)
/* 3987 */         this._shift--; 
/* 3988 */       this._n = 0;
/* 3989 */       this._factor = factor;
/* 3990 */       this._mask = this._nmax - 1;
/* 3991 */       this._a = new TriMesh.Node[this._nmax];
/* 3992 */       this._b = new TriMesh.Node[this._nmax];
/* 3993 */       this._nba = new TriMesh.Tri[this._nmax];
/* 3994 */       this._filled = new boolean[this._nmax];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 4001 */       this._n = 0;
/* 4002 */       for (int i = 0; i < this._nmax; i++) {
/* 4003 */         this._filled[i] = false;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean add(TriMesh.Node a, TriMesh.Node b, TriMesh.Tri nba) {
/* 4013 */       this._index = indexOfNode(a);
/* 4014 */       if (this._filled[this._index]) {
/* 4015 */         setCurrent();
/* 4016 */         remove(this._index);
/* 4017 */         return false;
/*      */       } 
/* 4019 */       this._a[this._index] = a;
/* 4020 */       this._b[this._index] = b;
/* 4021 */       this._nba[this._index] = nba;
/* 4022 */       this._filled[this._index] = true;
/* 4023 */       this._n++;
/* 4024 */       if (this._n > this._nmax * this._factor && this._nmax < 1073741824)
/* 4025 */         doubleCapacity(); 
/* 4026 */       setCurrent();
/* 4027 */       return true;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int hash(TriMesh.Node a) {
/* 4049 */       int key = a._hash;
/*      */ 
/*      */ 
/*      */       
/* 4053 */       return 1327217885 * key >> this._shift & this._mask;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int indexOfNode(TriMesh.Node a) {
/* 4061 */       int i = hash(a);
/* 4062 */       while (this._filled[i]) {
/* 4063 */         if (a == this._a[i])
/* 4064 */           return i; 
/* 4065 */         i = i - 1 & this._mask;
/*      */       } 
/* 4067 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setCurrent() {
/* 4074 */       this.a = this._a[this._index];
/* 4075 */       this.b = this._b[this._index];
/* 4076 */       this.nba = this._nba[this._index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void remove(int i) {
/* 4084 */       this._n--; while (true) {
/*      */         int r;
/* 4086 */         this._filled[i] = false;
/* 4087 */         int j = i;
/*      */         
/*      */         do {
/* 4090 */           i = i - 1 & this._mask;
/* 4091 */           if (!this._filled[i])
/*      */             return; 
/* 4093 */           r = hash(this._a[i]);
/* 4094 */         } while ((i <= r && r < j) || (r < j && j < i) || (j < i && i <= r));
/* 4095 */         this._a[j] = this._a[i];
/* 4096 */         this._b[j] = this._b[i];
/* 4097 */         this._nba[j] = this._nba[i];
/* 4098 */         this._filled[j] = this._filled[i];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doubleCapacity() {
/* 4107 */       NodeSet set = new NodeSet(2 * this._nmax, this._factor);
/* 4108 */       if (this._n > 0)
/* 4109 */         for (int i = 0; i < this._nmax; i++) {
/* 4110 */           if (this._filled[i]) {
/* 4111 */             set.add(this._a[i], this._b[i], this._nba[i]);
/*      */           }
/*      */         }  
/* 4114 */       this._a = set._a;
/* 4115 */       this._b = set._b;
/* 4116 */       this._nba = set._nba;
/* 4117 */       this._filled = set._filled;
/* 4118 */       this._nmax = set._nmax;
/* 4119 */       this._n = set._n;
/* 4120 */       this._factor = set._factor;
/* 4121 */       this._shift = set._shift;
/* 4122 */       this._mask = set._mask;
/* 4123 */       this._index = set._index;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 4132 */     init();
/*      */ 
/*      */     
/* 4135 */     int format = in.readInt();
/* 4136 */     if (format == 1) {
/*      */ 
/*      */       
/* 4139 */       this._version = in.readLong();
/*      */ 
/*      */       
/* 4142 */       int nnode = this._nnode = in.readInt();
/* 4143 */       Node[] nodes = new Node[nnode];
/* 4144 */       for (int inode = 0; inode < nnode; inode++) {
/* 4145 */         Node node = nodes[inode] = (Node)in.readObject();
/* 4146 */         node._x = in.readDouble();
/* 4147 */         node._y = in.readDouble();
/* 4148 */         int nvalue = in.readInt();
/* 4149 */         node._values = new Object[nvalue];
/* 4150 */         for (int ivalue = 0; ivalue < nvalue; ivalue++) {
/* 4151 */           Object value = in.readObject();
/* 4152 */           node._values[ivalue] = value;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 4157 */       int ntri = this._ntri = in.readInt();
/* 4158 */       Tri[] tris = new Tri[ntri];
/* 4159 */       for (int j = 0; j < ntri; j++) {
/* 4160 */         Tri tri = tris[j] = (Tri)in.readObject();
/* 4161 */         tri._quality = -1.0D;
/*      */       } 
/*      */ 
/*      */       
/* 4165 */       this._nroot = (Node)in.readObject();
/* 4166 */       for (int i = 0; i < nnode; i++) {
/* 4167 */         Node node = nodes[i];
/* 4168 */         node._prev = (Node)in.readObject();
/* 4169 */         node._next = (Node)in.readObject();
/* 4170 */         node._tri = (Tri)in.readObject();
/*      */       } 
/*      */ 
/*      */       
/* 4174 */       this._troot = (Tri)in.readObject();
/* 4175 */       for (int itri = 0; itri < ntri; itri++) {
/* 4176 */         Tri tri = tris[itri];
/* 4177 */         tri._n0 = (Node)in.readObject();
/* 4178 */         tri._n1 = (Node)in.readObject();
/* 4179 */         tri._n2 = (Node)in.readObject();
/* 4180 */         tri._t0 = (Tri)in.readObject();
/* 4181 */         tri._t1 = (Tri)in.readObject();
/* 4182 */         tri._t2 = (Tri)in.readObject();
/*      */       } 
/*      */ 
/*      */       
/* 4186 */       this._outerEnabled = in.readBoolean();
/* 4187 */       this._xminOuter = in.readDouble();
/* 4188 */       this._yminOuter = in.readDouble();
/* 4189 */       this._xmaxOuter = in.readDouble();
/* 4190 */       this._ymaxOuter = in.readDouble();
/*      */ 
/*      */       
/* 4193 */       this._nnodeValues = in.readInt();
/* 4194 */       this._lnodeValues = in.readInt();
/* 4195 */       this._nodePropertyMaps = (Map<String, NodePropertyMap>)in.readObject();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 4200 */       throw new InvalidClassException("invalid external format");
/*      */     } 
/*      */ 
/*      */     
/* 4204 */     sampleNodes();
/*      */ 
/*      */     
/*      */     try {
/* 4208 */       validate();
/* 4209 */     } catch (IllegalStateException ise) {
/* 4210 */       throw new IOException(ise.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 4219 */     out.writeInt(1);
/*      */ 
/*      */     
/* 4222 */     out.writeLong(this._version);
/*      */ 
/*      */     
/* 4225 */     int nnode = this._nnode;
/* 4226 */     out.writeInt(nnode);
/* 4227 */     Node[] nodes = new Node[nnode];
/* 4228 */     NodeIterator ni = getNodes();
/* 4229 */     for (int inode = 0; inode < nnode; inode++) {
/* 4230 */       Node node = nodes[inode] = ni.next();
/* 4231 */       out.writeObject(node);
/* 4232 */       out.writeDouble(node._x);
/* 4233 */       out.writeDouble(node._y);
/* 4234 */       int nvalue = node._values.length;
/* 4235 */       out.writeInt(nvalue);
/* 4236 */       for (int ivalue = 0; ivalue < nvalue; ivalue++) {
/* 4237 */         Object value = node._values[ivalue];
/* 4238 */         out.writeObject((value instanceof Serializable) ? value : null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4243 */     int ntri = this._ntri;
/* 4244 */     out.writeInt(ntri);
/* 4245 */     Tri[] tris = new Tri[ntri];
/* 4246 */     TriIterator ti = getTris();
/* 4247 */     for (int j = 0; j < ntri; j++) {
/* 4248 */       Tri tri = tris[j] = ti.next();
/* 4249 */       out.writeObject(tri);
/*      */     } 
/*      */ 
/*      */     
/* 4253 */     out.writeObject(this._nroot);
/* 4254 */     for (int i = 0; i < nnode; i++) {
/* 4255 */       Node node = nodes[i];
/* 4256 */       out.writeObject(node._prev);
/* 4257 */       out.writeObject(node._next);
/* 4258 */       out.writeObject(node._tri);
/*      */     } 
/*      */ 
/*      */     
/* 4262 */     out.writeObject(this._troot);
/* 4263 */     for (int itri = 0; itri < ntri; itri++) {
/* 4264 */       Tri tri = tris[itri];
/* 4265 */       out.writeObject(tri._n0);
/* 4266 */       out.writeObject(tri._n1);
/* 4267 */       out.writeObject(tri._n2);
/* 4268 */       out.writeObject(tri._t0);
/* 4269 */       out.writeObject(tri._t1);
/* 4270 */       out.writeObject(tri._t2);
/*      */     } 
/*      */ 
/*      */     
/* 4274 */     out.writeBoolean(this._outerEnabled);
/* 4275 */     out.writeDouble(this._xminOuter);
/* 4276 */     out.writeDouble(this._yminOuter);
/* 4277 */     out.writeDouble(this._xmaxOuter);
/* 4278 */     out.writeDouble(this._ymaxOuter);
/*      */ 
/*      */     
/* 4281 */     out.writeInt(this._nnodeValues);
/* 4282 */     out.writeInt(this._lnodeValues);
/* 4283 */     out.writeObject(this._nodePropertyMaps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sampleNodes() {
/* 4293 */     Random random = new Random();
/* 4294 */     this._sampledNodes.clear();
/* 4295 */     int nsamp = (int)(MathPlus.pow(this._nnode, 0.33D) / 0.45D);
/* 4296 */     Node node = this._nroot;
/* 4297 */     while (this._sampledNodes.size() < nsamp) {
/* 4298 */       int nskip = 1 + random.nextInt(this._nnode / 2);
/* 4299 */       while (--nskip > 0)
/* 4300 */         node = node._next; 
/* 4301 */       this._sampledNodes.add(node);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface TriListener extends EventListener {
/*      */     void triAdded(TriMesh param1TriMesh, TriMesh.Tri param1Tri);
/*      */     
/*      */     void triRemoved(TriMesh param1TriMesh, TriMesh.Tri param1Tri);
/*      */   }
/*      */   
/*      */   public static interface NodeListener extends EventListener {
/*      */     void nodeWillBeAdded(TriMesh param1TriMesh, TriMesh.Node param1Node);
/*      */     
/*      */     void nodeAdded(TriMesh param1TriMesh, TriMesh.Node param1Node);
/*      */     
/*      */     void nodeWillBeRemoved(TriMesh param1TriMesh, TriMesh.Node param1Node);
/*      */     
/*      */     void nodeRemoved(TriMesh param1TriMesh, TriMesh.Node param1Node);
/*      */   }
/*      */   
/*      */   public static interface NodePropertyMap extends Serializable {
/*      */     Object get(TriMesh.Node param1Node);
/*      */     
/*      */     void put(TriMesh.Node param1Node, Object param1Object);
/*      */   }
/*      */   
/*      */   public static interface EdgeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriMesh.Edge next();
/*      */   }
/*      */   
/*      */   public static interface TriIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriMesh.Tri next();
/*      */   }
/*      */   
/*      */   public static interface NodeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TriMesh.Node next();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mesh/TriMesh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
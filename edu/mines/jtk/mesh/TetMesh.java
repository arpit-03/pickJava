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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TetMesh
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   private static final int NODE_MARK_MAX = 2147483646;
/*      */   private static final int TET_MARK_MAX = 2147483646;
/*      */   private long _version;
/*      */   private int _nnode;
/*      */   private int _ntet;
/*      */   private Node _nroot;
/*      */   private Tet _troot;
/*      */   private HashSet<Node> _sampledNodes;
/*      */   private int _tetMarkRed;
/*      */   private int _tetMarkBlue;
/*      */   private int _nodeMarkRed;
/*      */   private int _nodeMarkBlue;
/*      */   private FaceSet _faceSet;
/*      */   private EdgeSet _edgeSet;
/*      */   private NodeList _nodeList;
/*      */   private Node _nmin;
/*      */   private double _dmin;
/*      */   private TetList _deadTets;
/*      */   private int _nnodeListeners;
/*      */   private int _ntetListeners;
/*      */   private EventListenerList _listeners;
/*      */   private boolean _outerEnabled;
/*      */   private double _xminOuter;
/*      */   private double _yminOuter;
/*      */   private double _zminOuter;
/*      */   private double _xmaxOuter;
/*      */   private double _ymaxOuter;
/*      */   private double _zmaxOuter;
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
/*      */     private transient double _z;
/*      */     private transient Node _prev;
/*      */     private transient Node _next;
/*      */     private transient TetMesh.Tet _tet;
/*      */     private transient int _mark;
/*      */     private transient int _hash;
/*      */     private transient Object[] _values;
/*      */     
/*      */     public Node(float x, float y, float z) {
/*  116 */       this._prev = null;
/*  117 */       this._next = null;
/*  118 */       this._tet = null;
/*  119 */       this._mark = 0;
/*  120 */       this._hash = System.identityHashCode(this);
/*  121 */       setPosition(x, y, z);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float x() {
/*  129 */       return (float)this._x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float y() {
/*  137 */       return (float)this._y;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final float z() {
/*  145 */       return (float)this._z;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double xp() {
/*  153 */       return this._x;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double yp() {
/*  161 */       return this._y;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double zp() {
/*  169 */       return this._z;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Tet tet() {
/*  177 */       return this._tet;
/*      */     }
/*      */     
/*      */     public String toString() {
/*  181 */       return "(" + x() + "," + y() + "," + z() + ")";
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
/*  197 */       int m = Integer.MAX_VALUE;
/*  198 */       int i = Float.floatToIntBits(p);
/*  199 */       int j = 0;
/*  200 */       for (int k = 0; k < 32; k++, i >>= 1, j <<= 1) {
/*  201 */         j |= i & 0x1;
/*      */       }
/*  203 */       double xp = (x != 0.0F) ? x : 1.4012984643248171E-46D;
/*  204 */       xp *= 1.0D + j / 2.147483647E9D * 0.1D * 1.1920928955078125E-7D;
/*  205 */       assert (float)xp == x;
/*  206 */       return xp;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setPosition(float x, float y, float z) {
/*  213 */       assert this._tet == null;
/*  214 */       this._x = perturb(x, 0.450599F * y + 0.374507F * z);
/*  215 */       this._y = perturb(y, 0.298721F * x + 0.983298F * z);
/*  216 */       this._z = perturb(z, 0.653901F * x + 0.598723F * y);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Tet
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */     
/*      */     public int index;
/*      */ 
/*      */     
/*      */     public Object data;
/*      */ 
/*      */     
/*      */     private static final int INNER_BIT = 1;
/*      */ 
/*      */     
/*      */     private static final int OUTER_BIT = 2;
/*      */     
/*      */     private static final int CENTER_BIT = 4;
/*      */     
/*      */     private transient TetMesh.Node _n0;
/*      */     
/*      */     private transient TetMesh.Node _n1;
/*      */     
/*      */     private transient TetMesh.Node _n2;
/*      */     
/*      */     private transient TetMesh.Node _n3;
/*      */     
/*      */     private transient Tet _t0;
/*      */     
/*      */     private transient Tet _t1;
/*      */     
/*      */     private transient Tet _t2;
/*      */     
/*      */     private transient Tet _t3;
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeA() {
/*  259 */       return this._n0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeB() {
/*  267 */       return this._n1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeC() {
/*  275 */       return this._n2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeD() {
/*  283 */       return this._n3;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tet tetA() {
/*  291 */       return this._t0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tet tetB() {
/*  299 */       return this._t1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tet tetC() {
/*  307 */       return this._t2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tet tetD() {
/*  315 */       return this._t3;
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
/*      */     public final TetMesh.Node nodeNearest(float x, float y, float z) {
/*  327 */       double d0 = TetMesh.distanceSquared(this._n0, x, y, z);
/*  328 */       double d1 = TetMesh.distanceSquared(this._n1, x, y, z);
/*  329 */       double d2 = TetMesh.distanceSquared(this._n2, x, y, z);
/*  330 */       double d3 = TetMesh.distanceSquared(this._n3, x, y, z);
/*  331 */       double dmin = d0;
/*  332 */       TetMesh.Node nmin = this._n0;
/*  333 */       if (d1 < dmin) {
/*  334 */         dmin = d1;
/*  335 */         nmin = this._n1;
/*      */       } 
/*  337 */       if (d2 < dmin) {
/*  338 */         dmin = d2;
/*  339 */         nmin = this._n2;
/*      */       } 
/*  341 */       if (d3 < dmin) {
/*  342 */         nmin = this._n3;
/*      */       }
/*  344 */       return nmin;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final Tet tetNabor(TetMesh.Node node) {
/*  354 */       if (node == this._n0) return this._t0; 
/*  355 */       if (node == this._n1) return this._t1; 
/*  356 */       if (node == this._n2) return this._t2; 
/*  357 */       if (node == this._n3) return this._t3; 
/*  358 */       Check.argument(false, "node is referenced by tet");
/*  359 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeNabor(Tet tetNabor) {
/*  370 */       if (tetNabor._t0 == this) return tetNabor._n0; 
/*  371 */       if (tetNabor._t1 == this) return tetNabor._n1; 
/*  372 */       if (tetNabor._t2 == this) return tetNabor._n2; 
/*  373 */       if (tetNabor._t3 == this) return tetNabor._n3; 
/*  374 */       Check.argument(false, "tetNabor is a nabor of tet");
/*  375 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeNabor(TetMesh.Node node) {
/*  386 */       Tet tetNabor = tetNabor(node);
/*  387 */       return (tetNabor != null) ? nodeNabor(tetNabor) : null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double centerSphere(double[] c) {
/*  396 */       if (hasCenter()) {
/*  397 */         c[0] = this._xc;
/*  398 */         c[1] = this._yc;
/*  399 */         c[2] = this._zc;
/*      */       } else {
/*  401 */         double x0 = this._n0._x;
/*  402 */         double y0 = this._n0._y;
/*  403 */         double z0 = this._n0._z;
/*  404 */         double x1 = this._n1._x;
/*  405 */         double y1 = this._n1._y;
/*  406 */         double z1 = this._n1._z;
/*  407 */         double x2 = this._n2._x;
/*  408 */         double y2 = this._n2._y;
/*  409 */         double z2 = this._n2._z;
/*  410 */         double x3 = this._n3._x;
/*  411 */         double y3 = this._n3._y;
/*  412 */         double z3 = this._n3._z;
/*  413 */         Geometry.centerSphere(x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, c);
/*  414 */         setCenter(c[0], c[1], c[2]);
/*      */       } 
/*  416 */       double dx = this._xc - this._n3._x;
/*  417 */       double dy = this._yc - this._n3._y;
/*  418 */       double dz = this._zc - this._n3._z;
/*  419 */       return dx * dx + dy * dy + dz * dz;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] centerSphere() {
/*  427 */       double[] c = new double[3];
/*  428 */       centerSphere(c);
/*  429 */       return c;
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
/*  440 */       if (this._quality < 0.0D)
/*  441 */         this._quality = quality(this._n0, this._n1, this._n2, this._n3); 
/*  442 */       return this._quality;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TetMesh.Node node) {
/*  451 */       return (node == this._n0 || node == this._n1 || node == this._n2 || node == this._n3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean references(TetMesh.Node na, TetMesh.Node nb) {
/*  461 */       if (na == this._n0)
/*  462 */         return (nb == this._n1 || nb == this._n2 || nb == this._n3); 
/*  463 */       if (na == this._n1)
/*  464 */         return (nb == this._n0 || nb == this._n2 || nb == this._n3); 
/*  465 */       if (na == this._n2)
/*  466 */         return (nb == this._n0 || nb == this._n1 || nb == this._n3); 
/*  467 */       if (na == this._n3) {
/*  468 */         return (nb == this._n0 || nb == this._n1 || nb == this._n2);
/*      */       }
/*  470 */       return false;
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
/*      */     public boolean references(TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc) {
/*  482 */       if (na == this._n0) {
/*  483 */         if (nb == this._n1)
/*  484 */           return (nc == this._n2 || nc == this._n3); 
/*  485 */         if (nb == this._n2)
/*  486 */           return (nc == this._n1 || nc == this._n3); 
/*  487 */         if (nb == this._n3) {
/*  488 */           return (nc == this._n1 || nc == this._n2);
/*      */         }
/*  490 */         return false;
/*      */       } 
/*  492 */       if (na == this._n1) {
/*  493 */         if (nb == this._n0)
/*  494 */           return (nc == this._n2 || nc == this._n3); 
/*  495 */         if (nb == this._n2)
/*  496 */           return (nc == this._n0 || nc == this._n3); 
/*  497 */         if (nb == this._n3) {
/*  498 */           return (nc == this._n0 || nc == this._n2);
/*      */         }
/*  500 */         return false;
/*      */       } 
/*  502 */       if (na == this._n2) {
/*  503 */         if (nb == this._n0)
/*  504 */           return (nc == this._n1 || nc == this._n3); 
/*  505 */         if (nb == this._n1)
/*  506 */           return (nc == this._n0 || nc == this._n3); 
/*  507 */         if (nb == this._n3) {
/*  508 */           return (nc == this._n0 || nc == this._n1);
/*      */         }
/*  510 */         return false;
/*      */       } 
/*  512 */       if (na == this._n3) {
/*  513 */         if (nb == this._n0)
/*  514 */           return (nc == this._n1 || nc == this._n2); 
/*  515 */         if (nb == this._n1)
/*  516 */           return (nc == this._n0 || nc == this._n2); 
/*  517 */         if (nb == this._n2) {
/*  518 */           return (nc == this._n0 || nc == this._n1);
/*      */         }
/*  520 */         return false;
/*      */       } 
/*      */       
/*  523 */       return false;
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
/*      */     public boolean references(TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc, TetMesh.Node nd) {
/*  536 */       if (na == this._n0) {
/*  537 */         if (nb == this._n1) {
/*  538 */           if (nc == this._n2)
/*  539 */             return (nd == this._n3); 
/*  540 */           if (nc == this._n3) {
/*  541 */             return (nd == this._n2);
/*      */           }
/*  543 */           return false;
/*      */         } 
/*  545 */         if (nb == this._n2) {
/*  546 */           if (nc == this._n1)
/*  547 */             return (nd == this._n3); 
/*  548 */           if (nc == this._n3) {
/*  549 */             return (nd == this._n1);
/*      */           }
/*  551 */           return false;
/*      */         } 
/*  553 */         if (nb == this._n3) {
/*  554 */           if (nc == this._n1)
/*  555 */             return (nd == this._n2); 
/*  556 */           if (nc == this._n2) {
/*  557 */             return (nd == this._n1);
/*      */           }
/*  559 */           return false;
/*      */         } 
/*      */         
/*  562 */         return false;
/*      */       } 
/*  564 */       if (na == this._n1) {
/*  565 */         if (nb == this._n0) {
/*  566 */           if (nc == this._n2)
/*  567 */             return (nd == this._n3); 
/*  568 */           if (nc == this._n3) {
/*  569 */             return (nd == this._n2);
/*      */           }
/*  571 */           return false;
/*      */         } 
/*  573 */         if (nb == this._n2) {
/*  574 */           if (nc == this._n0)
/*  575 */             return (nd == this._n3); 
/*  576 */           if (nc == this._n3) {
/*  577 */             return (nd == this._n0);
/*      */           }
/*  579 */           return false;
/*      */         } 
/*  581 */         if (nb == this._n3) {
/*  582 */           if (nc == this._n0)
/*  583 */             return (nd == this._n2); 
/*  584 */           if (nc == this._n2) {
/*  585 */             return (nd == this._n0);
/*      */           }
/*  587 */           return false;
/*      */         } 
/*      */         
/*  590 */         return false;
/*      */       } 
/*  592 */       if (na == this._n2) {
/*  593 */         if (nb == this._n0) {
/*  594 */           if (nc == this._n1)
/*  595 */             return (nd == this._n3); 
/*  596 */           if (nc == this._n3) {
/*  597 */             return (nd == this._n1);
/*      */           }
/*  599 */           return false;
/*      */         } 
/*  601 */         if (nb == this._n1) {
/*  602 */           if (nc == this._n0)
/*  603 */             return (nd == this._n3); 
/*  604 */           if (nc == this._n3) {
/*  605 */             return (nd == this._n0);
/*      */           }
/*  607 */           return false;
/*      */         } 
/*  609 */         if (nb == this._n3) {
/*  610 */           if (nc == this._n0)
/*  611 */             return (nd == this._n1); 
/*  612 */           if (nc == this._n1) {
/*  613 */             return (nd == this._n0);
/*      */           }
/*  615 */           return false;
/*      */         } 
/*      */         
/*  618 */         return false;
/*      */       } 
/*  620 */       if (na == this._n3) {
/*  621 */         if (nb == this._n0) {
/*  622 */           if (nc == this._n1)
/*  623 */             return (nd == this._n2); 
/*  624 */           if (nc == this._n2) {
/*  625 */             return (nd == this._n1);
/*      */           }
/*  627 */           return false;
/*      */         } 
/*  629 */         if (nb == this._n1) {
/*  630 */           if (nc == this._n0)
/*  631 */             return (nd == this._n2); 
/*  632 */           if (nc == this._n2) {
/*  633 */             return (nd == this._n0);
/*      */           }
/*  635 */           return false;
/*      */         } 
/*  637 */         if (nb == this._n2) {
/*  638 */           if (nc == this._n0)
/*  639 */             return (nd == this._n1); 
/*  640 */           if (nc == this._n1) {
/*  641 */             return (nd == this._n0);
/*      */           }
/*  643 */           return false;
/*      */         } 
/*      */         
/*  646 */         return false;
/*      */       } 
/*      */       
/*  649 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  683 */     private transient int _mark = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  688 */     private transient int _bits = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     private transient double _quality = -1.0D;
/*      */ 
/*      */     
/*      */     private transient double _xc;
/*      */ 
/*      */     
/*      */     private transient double _yc;
/*      */ 
/*      */     
/*      */     private transient double _zc;
/*      */ 
/*      */ 
/*      */     
/*      */     private Tet(TetMesh.Node n0, TetMesh.Node n1, TetMesh.Node n2, TetMesh.Node n3) {
/*  707 */       init(n0, n1, n2, n3);
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
/*      */     private void init(TetMesh.Node n0, TetMesh.Node n1, TetMesh.Node n2, TetMesh.Node n3) {
/*  730 */       this._n0 = n0;
/*  731 */       this._n1 = n1;
/*  732 */       this._n2 = n2;
/*  733 */       this._n3 = n3;
/*  734 */       this._n0._tet = this;
/*  735 */       this._n1._tet = this;
/*  736 */       this._n2._tet = this;
/*  737 */       this._n3._tet = this;
/*  738 */       this._t0 = null;
/*  739 */       this._t1 = null;
/*  740 */       this._t2 = null;
/*  741 */       this._t3 = null;
/*  742 */       this._mark = 0;
/*  743 */       this._bits = 0;
/*  744 */       this._quality = -1.0D;
/*      */     }
/*      */     
/*      */     private void setInner() {
/*  748 */       this._bits |= 0x1;
/*      */     }
/*      */     
/*      */     private void clearInner() {
/*  752 */       this._bits &= 0xFFFFFFFE;
/*      */     }
/*      */     
/*      */     private boolean isInner() {
/*  756 */       return ((this._bits & 0x1) != 0);
/*      */     }
/*      */     
/*      */     private void setOuter() {
/*  760 */       this._bits |= 0x2;
/*      */     }
/*      */     
/*      */     private void clearOuter() {
/*  764 */       this._bits &= 0xFFFFFFFD;
/*      */     }
/*      */     
/*      */     private boolean isOuter() {
/*  768 */       return ((this._bits & 0x2) != 0);
/*      */     }
/*      */     
/*      */     private void setCenter(double xc, double yc, double zc) {
/*  772 */       this._xc = xc;
/*  773 */       this._yc = yc;
/*  774 */       this._zc = zc;
/*  775 */       this._bits |= 0x4;
/*      */     }
/*      */     
/*      */     private boolean hasCenter() {
/*  779 */       return ((this._bits & 0x4) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean intersectsPlane(double a, double b, double c, double d) {
/*  787 */       int nn = 0;
/*  788 */       int np = 0;
/*  789 */       double s0 = a * this._n0._x + b * this._n0._y + c * this._n0._z + d;
/*  790 */       if (s0 < 0.0D) nn++; 
/*  791 */       if (s0 > 0.0D) np++; 
/*  792 */       double s1 = a * this._n1._x + b * this._n1._y + c * this._n1._z + d;
/*  793 */       if (s1 < 0.0D) nn++; 
/*  794 */       if (s1 > 0.0D) np++; 
/*  795 */       double s2 = a * this._n2._x + b * this._n2._y + c * this._n2._z + d;
/*  796 */       if (s2 < 0.0D) nn++; 
/*  797 */       if (s2 > 0.0D) np++; 
/*  798 */       double s3 = a * this._n3._x + b * this._n3._y + c * this._n3._z + d;
/*  799 */       if (s3 < 0.0D) nn++; 
/*  800 */       if (s3 > 0.0D) np++; 
/*  801 */       return (nn < 4 && np < 4);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static double quality(TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc, TetMesh.Node nd) {
/*  811 */       return qualityVolumeOverLongestEdge(na, nb, nc, nd);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static double qualityVolumeOverLongestEdge(TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc, TetMesh.Node nd) {
/*  822 */       double xa = na._x;
/*  823 */       double ya = na._y;
/*  824 */       double za = na._z;
/*  825 */       double xb = nb._x;
/*  826 */       double yb = nb._y;
/*  827 */       double zb = nb._z;
/*  828 */       double xc = nc._x;
/*  829 */       double yc = nc._y;
/*  830 */       double zc = nc._z;
/*  831 */       double xd = nd._x;
/*  832 */       double yd = nd._y;
/*  833 */       double zd = nd._z;
/*      */ 
/*      */       
/*  836 */       double xab = xa - xb;
/*  837 */       double yab = ya - yb;
/*  838 */       double zab = za - zb;
/*  839 */       double xac = xa - xc;
/*  840 */       double yac = ya - yc;
/*  841 */       double zac = za - zc;
/*  842 */       double xbc = xb - xc;
/*  843 */       double ybc = yb - yc;
/*  844 */       double zbc = zb - zc;
/*  845 */       double xad = xa - xd;
/*  846 */       double yad = ya - yd;
/*  847 */       double zad = za - zd;
/*  848 */       double xbd = xb - xd;
/*  849 */       double ybd = yb - yd;
/*  850 */       double zbd = zb - zd;
/*  851 */       double xcd = xc - xd;
/*  852 */       double ycd = yc - yd;
/*  853 */       double zcd = zc - zd;
/*      */ 
/*      */       
/*  856 */       double det = xad * (ybd * zcd - zbd * ycd) + xbd * (ycd * zad - zcd * yad) + xcd * (yad * zbd - zad * ybd);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  861 */       double dab = xab * xab + yab * yab + zab * zab;
/*  862 */       double dac = xac * xac + yac * yac + zac * zac;
/*  863 */       double dbc = xbc * xbc + ybc * ybc + zbc * zbc;
/*  864 */       double dad = xad * xad + yad * yad + zad * zad;
/*  865 */       double dbd = xbd * xbd + ybd * ybd + zbd * zbd;
/*  866 */       double dcd = xcd * xcd + ycd * ycd + zcd * zcd;
/*      */ 
/*      */       
/*  869 */       double dmx = dab;
/*  870 */       if (dac > dmx) dmx = dac; 
/*  871 */       if (dbc > dmx) dmx = dbc; 
/*  872 */       if (dad > dmx) dmx = dad; 
/*  873 */       if (dbd > dmx) dmx = dbd; 
/*  874 */       if (dcd > dmx) dmx = dcd; 
/*  875 */       dmx = MathPlus.sqrt(dmx);
/*      */ 
/*      */       
/*  878 */       double quality = QUALITY_VOL_LONGEST_EDGE_FACTOR * det / dmx * dmx * dmx;
/*  879 */       if (quality < 0.0D) quality = -quality; 
/*  880 */       if (quality > 1.0D) quality = 1.0D; 
/*  881 */       return quality;
/*      */     }
/*  883 */     private static double QUALITY_VOL_LONGEST_EDGE_FACTOR = 2.0D / MathPlus.sqrt(2.0D);
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
/*      */   public static class Edge
/*      */   {
/*      */     private TetMesh.Node _a;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Node _b;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Tet _tet;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Edge(TetMesh.Node a, TetMesh.Node b) {
/* 1001 */       this(a, b, (TetMesh.Tet)null);
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
/*      */     public Edge(TetMesh.Node a, TetMesh.Node b, TetMesh.Tet abcd) {
/* 1013 */       Check.argument((abcd == null || abcd
/* 1014 */           .references(a, b)), "tet references nodes");
/*      */       
/* 1016 */       this._a = a;
/* 1017 */       this._b = b;
/* 1018 */       this._tet = abcd;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Node nodeA() {
/* 1026 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Node nodeB() {
/* 1034 */       return this._b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Tet tet() {
/* 1042 */       return this._tet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Edge mate() {
/* 1050 */       return new Edge(this._b, this._a, this._tet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double midpoint(double[] c) {
/* 1059 */       double xa = this._a._x;
/* 1060 */       double ya = this._a._y;
/* 1061 */       double za = this._a._z;
/* 1062 */       double xb = this._b._x;
/* 1063 */       double yb = this._b._y;
/* 1064 */       double zb = this._b._z;
/* 1065 */       c[0] = 0.5D * (xa + xb);
/* 1066 */       c[1] = 0.5D * (ya + yb);
/* 1067 */       c[2] = 0.5D * (za + zb);
/* 1068 */       double dx = c[0] - xb;
/* 1069 */       double dy = c[1] - yb;
/* 1070 */       double dz = c[2] - zb;
/* 1071 */       return dx * dx + dy * dy + dz * dz;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] midpoint() {
/* 1079 */       double[] c = new double[3];
/* 1080 */       midpoint(c);
/* 1081 */       return c;
/*      */     }
/*      */     
/*      */     public boolean equals(Object object) {
/* 1085 */       if (object == this)
/* 1086 */         return true; 
/* 1087 */       if (object != null && object.getClass() == getClass()) {
/* 1088 */         Edge edge = (Edge)object;
/* 1089 */         return (this._a == edge._a && this._b == edge._b);
/*      */       } 
/* 1091 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1095 */       return this._a._hash ^ this._b._hash;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Edge(TetMesh.Tet abcd, TetMesh.Node a, TetMesh.Node b) {
/* 1102 */       this._a = a;
/* 1103 */       this._b = b;
/* 1104 */       this._tet = abcd;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Face
/*      */   {
/*      */     private TetMesh.Node _a;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Node _b;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Node _c;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Tet _tetLeft;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Tet _tetRight;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Node _nodeLeft;
/*      */ 
/*      */ 
/*      */     
/*      */     private TetMesh.Node _nodeRight;
/*      */ 
/*      */ 
/*      */     
/*      */     public Face(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c) {
/* 1142 */       this(a, b, c, (TetMesh.Tet)null);
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
/*      */     public Face(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c, TetMesh.Tet abcd) {
/* 1155 */       TetMesh.Node d = (abcd != null) ? TetMesh.otherNode(abcd, a, b, c) : null;
/* 1156 */       Check.argument((abcd == null || d != null), "tet references nodes");
/* 1157 */       this._a = a;
/* 1158 */       this._b = b;
/* 1159 */       this._c = c;
/* 1160 */       if (d != null) {
/* 1161 */         if (TetMesh.nodesInOrder(abcd, a, b, c, d)) {
/* 1162 */           this._tetLeft = abcd;
/* 1163 */           this._nodeLeft = d;
/* 1164 */           this._tetRight = abcd.tetNabor(d);
/* 1165 */           this._nodeRight = (this._tetRight != null) ? abcd.nodeNabor(this._tetRight) : null;
/*      */         } else {
/* 1167 */           this._tetRight = abcd;
/* 1168 */           this._nodeRight = d;
/* 1169 */           this._tetLeft = abcd.tetNabor(d);
/* 1170 */           this._nodeLeft = (this._tetLeft != null) ? abcd.nodeNabor(this._tetLeft) : null;
/*      */         } 
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeA() {
/* 1180 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeB() {
/* 1188 */       return this._b;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node nodeC() {
/* 1196 */       return this._c;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Tet tetLeft() {
/* 1204 */       return this._tetLeft;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Tet tetRight() {
/* 1212 */       return this._tetRight;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Node nodeLeft() {
/* 1220 */       return this._nodeLeft;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Node nodeRight() {
/* 1228 */       return this._nodeRight;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Face mate() {
/* 1236 */       return new Face(this._b, this._a, this._c, this._tetRight, this._nodeRight, this._tetLeft, this._nodeLeft);
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
/*      */     public boolean isVisibleFromPoint(double x, double y, double z) {
/* 1249 */       return (Geometry.leftOfPlane(this._a
/* 1250 */           ._x, this._a._y, this._a._z, this._b
/* 1251 */           ._x, this._b._y, this._b._z, this._c
/* 1252 */           ._x, this._c._y, this._c._z, x, y, z) < 0.0D);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double centerCircle(double[] c) {
/* 1262 */       double xa = this._a._x;
/* 1263 */       double ya = this._a._y;
/* 1264 */       double za = this._a._z;
/* 1265 */       double xb = this._b._x;
/* 1266 */       double yb = this._b._y;
/* 1267 */       double zb = this._b._z;
/* 1268 */       double xc = this._c._x;
/* 1269 */       double yc = this._c._y;
/* 1270 */       double zc = this._c._z;
/* 1271 */       Geometry.centerCircle3D(xa, ya, za, xb, yb, zb, xc, yc, zc, c);
/* 1272 */       double dx = c[0] - xc;
/* 1273 */       double dy = c[1] - yc;
/* 1274 */       double dz = c[2] - zc;
/* 1275 */       return dx * dx + dy * dy + dz * dz;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] centerCircle() {
/* 1283 */       double[] c = new double[3];
/* 1284 */       centerCircle(c);
/* 1285 */       return c;
/*      */     }
/*      */     
/*      */     public boolean equals(Object object) {
/* 1289 */       if (object == this)
/* 1290 */         return true; 
/* 1291 */       if (object != null && object.getClass() == getClass()) {
/* 1292 */         Face face = (Face)object;
/* 1293 */         return ((this._a == face._a && this._b == face._b && this._c == face._c) || (this._a == face._b && this._b == face._c && this._c == face._a) || (this._a == face._c && this._b == face._a && this._c == face._b));
/*      */       } 
/*      */ 
/*      */       
/* 1297 */       return false;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 1301 */       return this._a._hash ^ this._b._hash ^ this._c._hash;
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
/*      */     private Face(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c, TetMesh.Tet tetLeft, TetMesh.Node nodeLeft, TetMesh.Tet tetRight, TetMesh.Node nodeRight) {
/* 1313 */       this._a = a;
/* 1314 */       this._b = b;
/* 1315 */       this._c = c;
/* 1316 */       this._tetLeft = tetLeft;
/* 1317 */       this._tetRight = tetRight;
/* 1318 */       this._nodeLeft = nodeLeft;
/* 1319 */       this._nodeRight = nodeRight;
/*      */     }
/*      */     
/*      */     private Face(TetMesh.Tet tetLeft, TetMesh.Node nodeLeft) {
/* 1323 */       initLeft(tetLeft, nodeLeft);
/* 1324 */       this._tetLeft = tetLeft;
/* 1325 */       this._nodeLeft = nodeLeft;
/* 1326 */       this._tetRight = tetLeft.tetNabor(nodeLeft);
/* 1327 */       this._nodeRight = (this._tetRight != null) ? this._tetLeft.nodeNabor(this._tetRight) : null;
/*      */     }
/*      */     
/*      */     private Face(TetMesh.Tet tetLeft, TetMesh.Node nodeLeft, TetMesh.Tet tetRight, TetMesh.Node nodeRight) {
/* 1331 */       if (tetLeft != null) {
/* 1332 */         initLeft(tetLeft, nodeLeft);
/* 1333 */       } else if (tetRight != null) {
/* 1334 */         initRight(tetRight, nodeRight);
/*      */       } else {
/* 1336 */         assert false : "either tetLeft or tetRight is not null";
/*      */       } 
/* 1338 */       this._tetLeft = tetLeft;
/* 1339 */       this._tetRight = tetRight;
/* 1340 */       this._nodeLeft = nodeLeft;
/* 1341 */       this._nodeRight = nodeRight;
/*      */     }
/*      */     
/*      */     private void initLeft(TetMesh.Tet tetLeft, TetMesh.Node nodeLeft) {
/* 1345 */       if (nodeLeft == tetLeft._n0) {
/* 1346 */         this._a = tetLeft._n1;
/* 1347 */         this._b = tetLeft._n3;
/* 1348 */         this._c = tetLeft._n2;
/* 1349 */       } else if (nodeLeft == tetLeft._n1) {
/* 1350 */         this._a = tetLeft._n2;
/* 1351 */         this._b = tetLeft._n3;
/* 1352 */         this._c = tetLeft._n0;
/* 1353 */       } else if (nodeLeft == tetLeft._n2) {
/* 1354 */         this._a = tetLeft._n3;
/* 1355 */         this._b = tetLeft._n1;
/* 1356 */         this._c = tetLeft._n0;
/* 1357 */       } else if (nodeLeft == tetLeft._n3) {
/* 1358 */         this._a = tetLeft._n0;
/* 1359 */         this._b = tetLeft._n1;
/* 1360 */         this._c = tetLeft._n2;
/*      */       } else {
/* 1362 */         assert false : "nodeLeft referenced by tetLeft";
/*      */       } 
/*      */     }
/*      */     
/*      */     private void initRight(TetMesh.Tet tetRight, TetMesh.Node nodeRight) {
/* 1367 */       if (nodeRight == tetRight._n0) {
/* 1368 */         this._a = tetRight._n1;
/* 1369 */         this._b = tetRight._n2;
/* 1370 */         this._c = tetRight._n3;
/* 1371 */       } else if (nodeRight == tetRight._n1) {
/* 1372 */         this._a = tetRight._n2;
/* 1373 */         this._b = tetRight._n0;
/* 1374 */         this._c = tetRight._n3;
/* 1375 */       } else if (nodeRight == tetRight._n2) {
/* 1376 */         this._a = tetRight._n3;
/* 1377 */         this._b = tetRight._n0;
/* 1378 */         this._c = tetRight._n1;
/* 1379 */       } else if (nodeRight == tetRight._n3) {
/* 1380 */         this._a = tetRight._n0;
/* 1381 */         this._b = tetRight._n2;
/* 1382 */         this._c = tetRight._n1;
/*      */       } else {
/* 1384 */         assert false : "nodeRight referenced by tetRight";
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
/*      */     public final void add(TetMesh.Node node) {
/* 1413 */       if (this._n == this._a.length) {
/* 1414 */         TetMesh.Node[] t = new TetMesh.Node[this._a.length * 2];
/* 1415 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1416 */         this._a = t;
/*      */       } 
/* 1418 */       this._a[this._n++] = node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node remove(int index) {
/* 1427 */       TetMesh.Node node = this._a[index];
/* 1428 */       this._n--;
/* 1429 */       if (this._n > index)
/* 1430 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/* 1431 */       return node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node[] trim() {
/* 1439 */       if (this._n < this._a.length) {
/* 1440 */         TetMesh.Node[] t = new TetMesh.Node[this._n];
/* 1441 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1442 */         this._a = t;
/*      */       } 
/* 1444 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1451 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nnode() {
/* 1459 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node[] nodes() {
/* 1467 */       return this._a;
/*      */     }
/* 1469 */     private int _n = 0;
/* 1470 */     private TetMesh.Node[] _a = new TetMesh.Node[64];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TetList
/*      */   {
/*      */     public final void add(TetMesh.Tet tet) {
/* 1483 */       if (this._n == this._a.length) {
/* 1484 */         TetMesh.Tet[] t = new TetMesh.Tet[this._a.length * 2];
/* 1485 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1486 */         this._a = t;
/*      */       } 
/* 1488 */       this._a[this._n++] = tet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Tet remove(int index) {
/* 1497 */       TetMesh.Tet tet = this._a[index];
/* 1498 */       this._n--;
/* 1499 */       if (this._n > index)
/* 1500 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/* 1501 */       return tet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Tet[] trim() {
/* 1509 */       if (this._n < this._a.length) {
/* 1510 */         TetMesh.Tet[] t = new TetMesh.Tet[this._n];
/* 1511 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1512 */         this._a = t;
/*      */       } 
/* 1514 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1521 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int ntet() {
/* 1529 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Tet[] tets() {
/* 1537 */       return this._a;
/*      */     }
/* 1539 */     private int _n = 0;
/* 1540 */     private TetMesh.Tet[] _a = new TetMesh.Tet[64];
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
/*      */     public final void add(TetMesh.Edge edge) {
/* 1553 */       if (this._n == this._a.length) {
/* 1554 */         TetMesh.Edge[] t = new TetMesh.Edge[this._a.length * 2];
/* 1555 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1556 */         this._a = t;
/*      */       } 
/* 1558 */       this._a[this._n++] = edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Edge remove(int index) {
/* 1567 */       TetMesh.Edge edge = this._a[index];
/* 1568 */       this._n--;
/* 1569 */       if (this._n > index)
/* 1570 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/* 1571 */       return edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Edge[] trim() {
/* 1579 */       if (this._n < this._a.length) {
/* 1580 */         TetMesh.Edge[] t = new TetMesh.Edge[this._n];
/* 1581 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1582 */         this._a = t;
/*      */       } 
/* 1584 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1591 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nedge() {
/* 1599 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Edge[] edges() {
/* 1607 */       return this._a;
/*      */     }
/* 1609 */     private int _n = 0;
/* 1610 */     private TetMesh.Edge[] _a = new TetMesh.Edge[64];
/*      */   }
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
/*      */     public final void add(TetMesh.Face face) {
/* 1623 */       if (this._n == this._a.length) {
/* 1624 */         TetMesh.Face[] t = new TetMesh.Face[this._a.length * 2];
/* 1625 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1626 */         this._a = t;
/*      */       } 
/* 1628 */       this._a[this._n++] = face;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Face remove(int index) {
/* 1637 */       TetMesh.Face face = this._a[index];
/* 1638 */       this._n--;
/* 1639 */       if (this._n > index)
/* 1640 */         System.arraycopy(this._a, index + 1, this._a, index, this._n - index); 
/* 1641 */       return face;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Face[] trim() {
/* 1649 */       if (this._n < this._a.length) {
/* 1650 */         TetMesh.Face[] t = new TetMesh.Face[this._n];
/* 1651 */         System.arraycopy(this._a, 0, t, 0, this._n);
/* 1652 */         this._a = t;
/*      */       } 
/* 1654 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1661 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nface() {
/* 1669 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Face[] faces() {
/* 1677 */       return this._a;
/*      */     }
/* 1679 */     private int _n = 0;
/* 1680 */     private TetMesh.Face[] _a = new TetMesh.Face[64];
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
/*      */     public final void add(TetMesh.Node node, int step) {
/* 1696 */       if (this._n == this._a.length) {
/* 1697 */         TetMesh.Node[] s = new TetMesh.Node[this._a.length * 2];
/* 1698 */         int[] t = new int[this._a.length * 2];
/* 1699 */         System.arraycopy(this._a, 0, s, 0, this._n);
/* 1700 */         System.arraycopy(this._b, 0, t, 0, this._n);
/* 1701 */         this._a = s;
/* 1702 */         this._b = t;
/*      */       } 
/* 1704 */       this._a[this._n] = node;
/* 1705 */       this._b[this._n] = step;
/* 1706 */       this._n++;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void trim() {
/* 1713 */       if (this._n < this._a.length) {
/* 1714 */         TetMesh.Node[] s = new TetMesh.Node[this._n];
/* 1715 */         int[] t = new int[this._n];
/* 1716 */         System.arraycopy(this._a, 0, s, 0, this._n);
/* 1717 */         System.arraycopy(this._b, 0, t, 0, this._n);
/* 1718 */         this._a = s;
/* 1719 */         this._b = t;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void clear() {
/* 1727 */       this._n = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int nnode() {
/* 1735 */       return this._n;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final TetMesh.Node[] nodes() {
/* 1743 */       return this._a;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int[] steps() {
/* 1751 */       return this._b;
/*      */     }
/* 1753 */     private int _n = 0;
/* 1754 */     private TetMesh.Node[] _a = new TetMesh.Node[64];
/* 1755 */     private int[] _b = new int[64];
/*      */   }
/*      */ 
/*      */   
/*      */   public static class PointLocation
/*      */   {
/*      */     private TetMesh.Node _node;
/*      */     
/*      */     private TetMesh.Edge _edge;
/*      */     private TetMesh.Face _face;
/*      */     private TetMesh.Tet _tet;
/*      */     private boolean _inside;
/*      */     
/*      */     public boolean isOnNode() {
/* 1769 */       return (this._node != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOnEdge() {
/* 1777 */       return (this._edge != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOnFace() {
/* 1785 */       return (this._face != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInside() {
/* 1793 */       return this._inside;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isOutside() {
/* 1801 */       return !this._inside;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Node node() {
/* 1809 */       return this._node;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Edge edge() {
/* 1817 */       return this._edge;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Face face() {
/* 1825 */       return this._face;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public TetMesh.Tet tet() {
/* 1836 */       return this._tet;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private PointLocation(TetMesh.Tet tet) {
/* 1846 */       this._tet = tet;
/* 1847 */       this._inside = true;
/*      */     }
/*      */     private PointLocation(TetMesh.Tet tet, boolean inside) {
/* 1850 */       this._tet = tet;
/* 1851 */       this._inside = inside;
/*      */     }
/*      */     private PointLocation(TetMesh.Node node) {
/* 1854 */       this._tet = node._tet;
/* 1855 */       this._node = node;
/* 1856 */       this._inside = true;
/*      */     }
/*      */     private PointLocation(TetMesh.Face face) {
/* 1859 */       this._tet = face._tetLeft;
/* 1860 */       this._face = face;
/* 1861 */       this._inside = true;
/*      */     }
/*      */     private PointLocation(TetMesh.Edge edge) {
/* 1864 */       this._tet = edge._tet;
/* 1865 */       this._edge = edge;
/* 1866 */       this._inside = true;
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
/*      */   public int countNodes() {
/*      */     return this._nnode;
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
/*      */   public int countTets() {
/*      */     return this._ntet;
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
/*      */   public long getVersion() {
/*      */     return this._version;
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
/*      */   public synchronized boolean addNode(Node node) {
/*      */     PointLocation pl = locatePoint(node._x, node._y, node._z);
/*      */     if (pl.isOnNode()) {
/*      */       return false;
/*      */     }
/*      */     fireNodeWillBeAdded(node);
/*      */     if (this._nroot == null) {
/*      */       this._nroot = node;
/*      */       this._nroot._prev = this._nroot._next = this._nroot;
/*      */     } else {
/*      */       node._next = this._nroot;
/*      */       node._prev = this._nroot._prev;
/*      */       this._nroot._prev._next = node;
/*      */       this._nroot._prev = node;
/*      */       this._nroot = node;
/*      */     } 
/*      */     this._nnode++;
/*      */     updatePropertyValues(node);
/*      */     double factor = 0.5D * this._sampledNodes.size();
/*      */     if (factor * factor * factor * factor < this._nnode) {
/*      */       this._sampledNodes.add(node);
/*      */     }
/*      */     if (pl.isOutside() && this._nnode <= 4) {
/*      */       if (this._nnode == 4) {
/*      */         createFirstTet();
/*      */       }
/*      */     } else {
/*      */       clearTetMarks();
/*      */       this._faceSet.clear();
/*      */       if (pl.isInside()) {
/*      */         getDelaunayFacesInside(node, pl.tet());
/*      */       } else {
/*      */         getDelaunayFacesOutside(node, pl.tet());
/*      */       } 
/*      */       this._edgeSet.clear();
/*      */       boolean more;
/*      */       for (more = this._faceSet.first(); more; more = this._faceSet.next()) {
/*      */         Node a = this._faceSet.a;
/*      */         Node b = this._faceSet.b;
/*      */         Node c = this._faceSet.c;
/*      */         Node d = this._faceSet.d;
/*      */         Tet abcd = this._faceSet.abcd;
/*      */         Tet nabc = makeTet(node, a, b, c);
/*      */         linkTets(nabc, node, abcd, d);
/*      */         if (!this._edgeSet.add(a, b, c, nabc)) {
/*      */           linkTets(this._edgeSet.nabc, this._edgeSet.c, nabc, c);
/*      */         }
/*      */         if (!this._edgeSet.add(b, c, a, nabc)) {
/*      */           linkTets(this._edgeSet.nabc, this._edgeSet.c, nabc, a);
/*      */         }
/*      */         if (!this._edgeSet.add(c, a, b, nabc)) {
/*      */           linkTets(this._edgeSet.nabc, this._edgeSet.c, nabc, b);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     fireNodeAdded(node);
/*      */     return true;
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
/*      */   public synchronized boolean removeNode(Node node) {
/*      */     Tet tet = node._tet;
/*      */     if (tet == null) {
/*      */       return false;
/*      */     }
/*      */     fireNodeWillBeRemoved(node);
/*      */     unlinkNode(node);
/*      */     if (this._nnode < 4) {
/*      */       if (this._nnode == 3) {
/*      */         Node n0 = this._nroot;
/*      */         Node n1 = n0._next;
/*      */         Node n2 = n1._next;
/*      */         n0._tet = null;
/*      */         n1._tet = null;
/*      */         n2._tet = null;
/*      */         killTet(this._troot);
/*      */         this._troot = null;
/*      */       } 
/*      */     } else {
/*      */       this._faceSet.clear();
/*      */       this._nodeList.clear();
/*      */       clearTetMarks();
/*      */       clearNodeMarks();
/*      */       getDelaunayFacesOpposite(node, tet);
/*      */       int nnode = this._nodeList.nnode();
/*      */       Node[] nodes = this._nodeList.nodes();
/*      */       boolean more;
/*      */       for (more = this._faceSet.remove(); more; more = this._faceSet.remove()) {
/*      */         Node a = this._faceSet.a;
/*      */         Node b = this._faceSet.b;
/*      */         Node c = this._faceSet.c;
/*      */         Node d = this._faceSet.d;
/*      */         Tet abcd = this._faceSet.abcd;
/*      */         Node n = null;
/*      */         for (int inode = 0; inode < nnode; inode++) {
/*      */           Node m = nodes[inode];
/*      */           if (m != a && m != b && m != c && !leftOfPlane(a, b, c, m) && (n == null || inSphere(n, a, b, c, m))) {
/*      */             n = m;
/*      */           }
/*      */         } 
/*      */         if (n != null) {
/*      */           Tet nabc = makeTet(n, a, b, c);
/*      */           linkTets(nabc, n, abcd, d);
/*      */           if (!this._faceSet.add(nabc, a)) {
/*      */             linkTets(this._faceSet.abcd, this._faceSet.d, nabc, a);
/*      */           }
/*      */           if (!this._faceSet.add(nabc, b)) {
/*      */             linkTets(this._faceSet.abcd, this._faceSet.d, nabc, b);
/*      */           }
/*      */           if (!this._faceSet.add(nabc, c)) {
/*      */             linkTets(this._faceSet.abcd, this._faceSet.d, nabc, c);
/*      */           }
/*      */         } else {
/*      */           linkTets(abcd, d, null, null);
/*      */           a._tet = abcd;
/*      */           b._tet = abcd;
/*      */           c._tet = abcd;
/*      */           this._troot = abcd;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     fireNodeRemoved(node);
/*      */     return true;
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
/*      */   public synchronized boolean moveNode(Node node, float x, float y, float z) {
/*      */     if (x != node.x() || y != node.y() || z != node.z()) {
/*      */       Node nodeNearest = findNodeNearest(x, y, z);
/*      */       if (node == nodeNearest || x != nodeNearest.x() || y != nodeNearest.y() || z != nodeNearest.z()) {
/*      */         boolean nodeInMesh = removeNode(node);
/*      */         node.setPosition(x, y, z);
/*      */         if (nodeInMesh) {
/*      */           boolean addedNode = addNode(node);
/*      */           assert addedNode;
/*      */         } 
/*      */         return true;
/*      */       } 
/*      */     } 
/*      */     return false;
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
/*      */   public synchronized Node findNodeNearest(float x, float y, float z) {
/*      */     return findNodeNearest(x, y, z);
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
/*      */   public synchronized Edge findEdge(Node na, Node nb) {
/*      */     Tet tet = findTet(na, nb);
/*      */     if (tet != null) {
/*      */       return edgeOfTet(tet, na, nb);
/*      */     }
/*      */     return null;
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
/*      */   public synchronized Face findFace(Node na, Node nb, Node nc) {
/*      */     Tet tet = findTet(na, nb, nc);
/*      */     if (tet != null) {
/*      */       return faceOfTet(tet, na, nb, nc);
/*      */     }
/*      */     return null;
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
/*      */   public Tet findTet(Node node) {
/*      */     return node._tet;
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
/*      */   public synchronized Tet findTet(Node na, Node nb) {
/*      */     Tet tet = findTet(na);
/*      */     if (tet != null) {
/*      */       clearTetMarks();
/*      */       tet = findTet(tet, na, nb);
/*      */     } 
/*      */     return tet;
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
/*      */   public synchronized Tet findTet(Node na, Node nb, Node nc) {
/*      */     Tet tet = findTet(na, nb);
/*      */     if (tet != null) {
/*      */       clearTetMarks();
/*      */       tet = findTet(tet, na, nb, nc);
/*      */     } 
/*      */     return tet;
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
/*      */   public synchronized Tet findTet(Node na, Node nb, Node nc, Node nd) {
/*      */     Tet tet = findTet(na, nb, nc);
/*      */     if (tet != null) {
/*      */       clearTetMarks();
/*      */       tet = findTet(tet, na, nb, nc, nd);
/*      */     } 
/*      */     return tet;
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
/*      */   public synchronized PointLocation locatePoint(float x, float y, float z) {
/*      */     return locatePoint(x, y, z);
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
/*      */   public synchronized NodeIterator getNodes() {
/*      */     return new NodeIterator()
/*      */       {
/*      */         public boolean hasNext() {
/*      */           return (this._nnext != null);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public TetMesh.Node next() {
/*      */           if (this._nnext == null) {
/*      */             throw new NoSuchElementException();
/*      */           }
/*      */           TetMesh.Node node = this._nnext;
/*      */           this._nnext = node._next;
/*      */           if (this._nnext == this._nroot) {
/*      */             this._nnext = null;
/*      */           }
/*      */           return node;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private TetMesh.Node _nroot = TetMesh.this._nroot;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private TetMesh.Node _nnext = this._nroot;
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
/*      */   public synchronized TetIterator getTets() {
/*      */     return new TetIterator()
/*      */       {
/*      */         private Iterator<TetMesh.Tet> _i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final boolean hasNext() {
/*      */           return this._i.hasNext();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final TetMesh.Tet next() {
/*      */           return this._i.next();
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
/*      */   public synchronized EdgeIterator getEdges() {
/*      */     return new EdgeIterator()
/*      */       {
/*      */         private Iterator<TetMesh.Edge> _i;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final boolean hasNext() {
/*      */           return this._i.hasNext();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final TetMesh.Edge next() {
/*      */           return this._i.next();
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
/*      */   public synchronized TetIterator getTetsInPlane(final double a, final double b, final double c, final double d) {
/*      */     return new TetIterator()
/*      */       {
/*      */         private TetMesh.Tet _tnext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private ArrayList<TetMesh.Tet> _tets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final boolean hasNext() {
/*      */           return (this._tnext != null);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final TetMesh.Tet next() {
/*      */           if (this._tnext == null) {
/*      */             throw new NoSuchElementException();
/*      */           }
/*      */           TetMesh.Tet tet = this._tnext;
/*      */           addTet(tet._t0);
/*      */           addTet(tet._t1);
/*      */           addTet(tet._t2);
/*      */           addTet(tet._t3);
/*      */           int ntet = this._tets.size();
/*      */           if (ntet == 0) {
/*      */             this._tnext = null;
/*      */           } else {
/*      */             this._tnext = this._tets.remove(ntet - 1);
/*      */           } 
/*      */           return tet;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private void addTet(TetMesh.Tet tet) {
/*      */           if (tet != null && !TetMesh.this.isMarked(tet)) {
/*      */             TetMesh.this.mark(tet);
/*      */             if (tet.intersectsPlane(a, b, c, d)) {
/*      */               this._tets.add(tet);
/*      */             }
/*      */           } 
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
/*      */   public synchronized NodeIterator getNodesNearestPlane(final double a, final double b, final double c, final double d) {
/*      */     return new NodeIterator()
/*      */       {
/*      */         public final boolean hasNext() {
/*      */           return this._iterator.hasNext();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final TetMesh.Node next() {
/*      */           return this._iterator.next();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private Iterator<TetMesh.Node> _iterator = null;
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
/*      */   public Tet findTetInPlane(double a, double b, double c, double d) {
/*      */     Node node = findNodeNearestPlane(a, b, c, d);
/*      */     Tet[] tets = getTetNabors(node);
/*      */     for (Tet tet : tets) {
/*      */       if (tet.intersectsPlane(a, b, c, d)) {
/*      */         return tet;
/*      */       }
/*      */     } 
/*      */     return null;
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
/*      */   public synchronized FaceIterator getFacesOnHull() {
/*      */     clearTetMarks();
/*      */     Face face = getFaceOnHull(this._troot);
/*      */     final HashSet<Face> faces = new HashSet<>(128);
/*      */     getFacesOnHull(face, faces);
/*      */     return new FaceIterator()
/*      */       {
/*      */         private Iterator<TetMesh.Face> i = faces.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final boolean hasNext() {
/*      */           return this.i.hasNext();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public final TetMesh.Face next() {
/*      */           return this.i.next();
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
/*      */   public synchronized Node[] getNodeNabors(Node node) {
/*      */     NodeList nabors = new NodeList();
/*      */     getNodeNabors(node, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getNodeNabors(Node node, NodeList nabors) {
/*      */     Tet tet = node._tet;
/*      */     if (tet == null) {
/*      */       return;
/*      */     }
/*      */     clearNodeMarks();
/*      */     clearTetMarks();
/*      */     getNodeNabors(node, tet, nabors);
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
/*      */   public synchronized NodeStepList getNodeNabors(Node node, int stepMax) {
/*      */     Check.argument((stepMax <= 256), "stepMax <= 256");
/*      */     clearNodeMarks();
/*      */     mark(node);
/*      */     NodeStepList list = new NodeStepList();
/*      */     for (int step = 1, nnabor1 = 0; step <= stepMax; step++) {
/*      */       if (step == 1) {
/*      */         getNodeNabors(node, step, list);
/*      */       } else {
/*      */         int nnabor2 = list.nnode();
/*      */         Node[] naborNodes = list.nodes();
/*      */         for (int inabor = nnabor1; inabor < nnabor2; inabor++) {
/*      */           node = naborNodes[inabor];
/*      */           getNodeNabors(node, step, list);
/*      */         } 
/*      */         nnabor1 = nnabor2;
/*      */       } 
/*      */     } 
/*      */     return list;
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
/*      */   public synchronized Tet[] getTetNabors(Node node) {
/*      */     TetList nabors = new TetList();
/*      */     getTetNabors(node, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getTetNabors(Node node, TetList nabors) {
/*      */     clearTetMarks();
/*      */     getTetNabors(node, node._tet, nabors);
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
/*      */   public synchronized Tet[] getTetNabors(Edge edge) {
/*      */     TetList nabors = new TetList();
/*      */     getTetNabors(edge, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getTetNabors(Edge edge, TetList nabors) {
/*      */     Node na = edge.nodeA();
/*      */     Node nb = edge.nodeB();
/*      */     Tet tet = edge.tet();
/*      */     if (tet == null) {
/*      */       tet = findTet(na, nb);
/*      */     }
/*      */     if (tet == null) {
/*      */       return;
/*      */     }
/*      */     clearTetMarks();
/*      */     getTetNabors(na, nb, tet, nabors);
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
/*      */   public synchronized Tet[] getTetNabors(Face face) {
/*      */     TetList nabors = new TetList();
/*      */     getTetNabors(face, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getTetNabors(Face face, TetList nabors) {
/*      */     Tet tetLeft = face.tetLeft();
/*      */     Tet tetRight = face.tetRight();
/*      */     if (tetLeft == null && tetRight == null) {
/*      */       Node na = face.nodeA();
/*      */       Node nb = face.nodeB();
/*      */       Node nc = face.nodeC();
/*      */       face = findFace(na, nb, nc);
/*      */       tetLeft = face.tetLeft();
/*      */       tetRight = face.tetRight();
/*      */     } 
/*      */     if (tetLeft != null) {
/*      */       nabors.add(tetLeft);
/*      */     }
/*      */     if (tetRight != null) {
/*      */       nabors.add(tetRight);
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
/*      */   public synchronized Edge[] getEdgeNabors(Node node) {
/*      */     EdgeList nabors = new EdgeList();
/*      */     getEdgeNabors(node, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getEdgeNabors(Node node, EdgeList nabors) {
/*      */     Tet[] tets = getTetNabors(node);
/*      */     int ntet = tets.length;
/*      */     clearNodeMarks();
/*      */     for (int itet = 0; itet < ntet; itet++) {
/*      */       Tet tet = tets[itet];
/*      */       Node n0 = tet._n0;
/*      */       Node n1 = tet._n1;
/*      */       Node n2 = tet._n2;
/*      */       Node n3 = tet._n3;
/*      */       if (node == n0) {
/*      */         if (!isMarked(n1)) {
/*      */           mark(n1);
/*      */           nabors.add(new Edge(tet, n1, node));
/*      */         } 
/*      */         if (!isMarked(n2)) {
/*      */           mark(n2);
/*      */           nabors.add(new Edge(tet, n2, node));
/*      */         } 
/*      */         if (!isMarked(n3)) {
/*      */           mark(n3);
/*      */           nabors.add(new Edge(tet, n3, node));
/*      */         } 
/*      */       } else if (node == n1) {
/*      */         if (!isMarked(n0)) {
/*      */           mark(n0);
/*      */           nabors.add(new Edge(tet, n0, node));
/*      */         } 
/*      */         if (!isMarked(n2)) {
/*      */           mark(n2);
/*      */           nabors.add(new Edge(tet, n2, node));
/*      */         } 
/*      */         if (!isMarked(n3)) {
/*      */           mark(n3);
/*      */           nabors.add(new Edge(tet, n3, node));
/*      */         } 
/*      */       } else if (node == n2) {
/*      */         if (!isMarked(n0)) {
/*      */           mark(n0);
/*      */           nabors.add(new Edge(tet, n0, node));
/*      */         } 
/*      */         if (!isMarked(n1)) {
/*      */           mark(n1);
/*      */           nabors.add(new Edge(tet, n1, node));
/*      */         } 
/*      */         if (!isMarked(n3)) {
/*      */           mark(n3);
/*      */           nabors.add(new Edge(tet, n3, node));
/*      */         } 
/*      */       } else if (node == n3) {
/*      */         if (!isMarked(n0)) {
/*      */           mark(n0);
/*      */           nabors.add(new Edge(tet, n0, node));
/*      */         } 
/*      */         if (!isMarked(n1)) {
/*      */           mark(n1);
/*      */           nabors.add(new Edge(tet, n1, node));
/*      */         } 
/*      */         if (!isMarked(n2)) {
/*      */           mark(n2);
/*      */           nabors.add(new Edge(tet, n2, node));
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
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized Face[] getFaceNabors(Edge edge) {
/*      */     FaceList nabors = new FaceList();
/*      */     getFaceNabors(edge, nabors);
/*      */     return nabors.trim();
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
/*      */   public synchronized void getFaceNabors(Edge edge, FaceList nabors) {
/*      */     Node ea = edge.nodeA();
/*      */     Node eb = edge.nodeB();
/*      */     Tet tet = edge.tet();
/*      */     if (tet == null) {
/*      */       tet = findTet(ea, eb);
/*      */     }
/*      */     if (tet == null) {
/*      */       return;
/*      */     }
/*      */     Node ta = tet.nodeA();
/*      */     Node tb = tet.nodeB();
/*      */     Node tc = tet.nodeC();
/*      */     Node td = tet.nodeD();
/*      */     Face face = null;
/*      */     if ((ea == ta && eb == tb) || (ea == tb && eb == tc) || (ea == tc && eb == ta)) {
/*      */       face = new Face(ta, tb, tc, tet);
/*      */     } else if ((ea == tb && eb == td) || (ea == td && eb == tc) || (ea == tc && eb == tb)) {
/*      */       face = new Face(tb, td, tc, tet);
/*      */     } else if ((ea == tc && eb == td) || (ea == td && eb == ta) || (ea == ta && eb == tc)) {
/*      */       face = new Face(tc, td, ta, tet);
/*      */     } else if ((ea == td && eb == tb) || (ea == tb && eb == ta) || (ea == ta && eb == td)) {
/*      */       face = new Face(td, tb, ta, tet);
/*      */     } else {
/*      */       assert false : "tet references edge";
/*      */     } 
/*      */     Face firstFace = face;
/*      */     do {
/*      */       nabors.add(face);
/*      */       Node fa = face.nodeA();
/*      */       Node fb = face.nodeB();
/*      */       Node fc = face.nodeC();
/*      */       Node node = null;
/*      */       if (ea == fa && eb == fb) {
/*      */         node = fc;
/*      */       } else if (ea == fb && eb == fc) {
/*      */         node = fa;
/*      */       } else if (ea == fc && eb == fa) {
/*      */         node = fb;
/*      */       } else {
/*      */         assert false : "edge is aligned with face";
/*      */       } 
/*      */       tet = face.tetRight();
/*      */       if (tet == null) {
/*      */         tet = face.tetLeft();
/*      */         Node nodeBack = face.nodeLeft();
/*      */         Tet tetBack = tet.tetNabor(node);
/*      */         while (tetBack != null) {
/*      */           node = nodeBack;
/*      */           nodeBack = tet.nodeNabor(tetBack);
/*      */           tet = tetBack;
/*      */           tetBack = tet.tetNabor(node);
/*      */         } 
/*      */         face = new Face(null, null, tet, node);
/*      */       } else {
/*      */         face = new Face(tet, node);
/*      */       } 
/*      */     } while (!face.equals(firstFace));
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
/*      */   public synchronized void setOuterBox(float xmin, float ymin, float zmin, float xmax, float ymax, float zmax) {
/*      */     Check.argument((xmin < xmax), "outer box is valid");
/*      */     Check.argument((ymin < ymax), "outer box is valid");
/*      */     Check.argument((zmin < zmax), "outer box is valid");
/*      */     if (xmin != this._xminOuter || xmax != this._xmaxOuter || ymin != this._yminOuter || ymax != this._ymaxOuter || zmin != this._zminOuter || zmax != this._zmaxOuter) {
/*      */       this._xminOuter = xmin;
/*      */       this._yminOuter = ymin;
/*      */       this._zminOuter = zmin;
/*      */       this._xmaxOuter = xmax;
/*      */       this._ymaxOuter = ymax;
/*      */       this._zmaxOuter = zmax;
/*      */       TetIterator ti = getTets();
/*      */       while (ti.hasNext()) {
/*      */         Tet tet = ti.next();
/*      */         tet.clearInner();
/*      */         tet.clearOuter();
/*      */       } 
/*      */     } 
/*      */     this._version++;
/*      */     this._outerEnabled = true;
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
/*      */   public void enableOuterBox() {
/*      */     this._version++;
/*      */     this._outerEnabled = true;
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
/*      */   public void disableOuterBox() {
/*      */     this._version++;
/*      */     this._outerEnabled = false;
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
/*      */   public boolean isInner(Node node) {
/*      */     Tet tet = node.tet();
/*      */     if (tet == null || isInner(tet)) {
/*      */       return true;
/*      */     }
/*      */     Tet[] tets = getTetNabors(node);
/*      */     int ntet = tets.length;
/*      */     for (int itet = 0; itet < ntet; itet++) {
/*      */       if (isInner(tets[itet])) {
/*      */         return true;
/*      */       }
/*      */     } 
/*      */     return false;
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
/*      */   public boolean isOuter(Node node) {
/*      */     return !isInner(node);
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
/*      */   public boolean isInner(Tet tet) {
/*      */     if (!this._outerEnabled) {
/*      */       return true;
/*      */     }
/*      */     if (!tet.isInner() && !tet.isOuter()) {
/*      */       markTetInnerOrOuter(tet);
/*      */     }
/*      */     return tet.isInner();
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
/*      */   public boolean isOuter(Tet tet) {
/*      */     return !isInner(tet);
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
/*      */   public boolean isInner(Edge edge) {
/*      */     Tet tet = edge.tet();
/*      */     if (tet == null) {
/*      */       tet = findTet(edge.nodeA(), edge.nodeB());
/*      */     }
/*      */     if (tet == null) {
/*      */       return false;
/*      */     }
/*      */     if (tet.isInner()) {
/*      */       return true;
/*      */     }
/*      */     Tet[] tets = getTetNabors(edge);
/*      */     int ntet = tets.length;
/*      */     for (int itet = 0; itet < ntet; itet++) {
/*      */       if (isInner(tets[itet])) {
/*      */         return true;
/*      */       }
/*      */     } 
/*      */     return false;
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
/*      */   public boolean isOuter(Edge edge) {
/*      */     return !isInner(edge);
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
/*      */   public boolean isInner(Face face) {
/*      */     Tet tetLeft = face.tetLeft();
/*      */     if (tetLeft != null && isInner(tetLeft)) {
/*      */       return true;
/*      */     }
/*      */     Tet tetRight = face.tetRight();
/*      */     return (tetRight != null && isInner(tetRight));
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
/*      */   public boolean isOuter(Face face) {
/*      */     return !isInner(face);
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
/*      */   public final void mark(Node node) {
/*      */     node._mark = this._nodeMarkRed;
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
/*      */   public final void markRed(Node node) {
/*      */     node._mark = this._nodeMarkRed;
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
/*      */   public final void markBlue(Node node) {
/*      */     node._mark = this._nodeMarkBlue;
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
/*      */   public final void unmark(Node node) {
/*      */     node._mark = this._nodeMarkRed - 1;
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
/*      */   public final boolean isMarked(Node node) {
/*      */     return (node._mark == this._nodeMarkRed);
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
/*      */   public final boolean isMarkedRed(Node node) {
/*      */     return (node._mark == this._nodeMarkRed);
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
/*      */   public final boolean isMarkedBlue(Node node) {
/*      */     return (node._mark == this._nodeMarkBlue);
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
/*      */   public synchronized void clearNodeMarks() {
/*      */     if (this._nodeMarkRed == 2147483646) {
/*      */       Node node = this._nroot;
/*      */       while (true) {
/*      */         node._mark = 0;
/*      */         node = node._next;
/*      */         if (node == this._nroot) {
/*      */           this._nodeMarkRed = 0;
/*      */           this._nodeMarkBlue = 0;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     this._nodeMarkRed++;
/*      */     this._nodeMarkBlue--;
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
/*      */   public final void mark(Tet tet) {
/*      */     tet._mark = this._tetMarkRed;
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
/*      */   public final void markRed(Tet tet) {
/*      */     tet._mark = this._tetMarkRed;
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
/*      */   public final void markBlue(Tet tet) {
/*      */     tet._mark = this._tetMarkBlue;
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
/*      */   public final void unmark(Tet tet) {
/*      */     tet._mark = this._tetMarkRed - 1;
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
/*      */   public final boolean isMarked(Tet tet) {
/*      */     return (tet._mark == this._tetMarkRed);
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
/*      */   public final boolean isMarkedRed(Tet tet) {
/*      */     return (tet._mark == this._tetMarkRed);
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
/*      */   public final boolean isMarkedBlue(Tet tet) {
/*      */     return (tet._mark == this._tetMarkBlue);
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
/*      */   public synchronized void clearTetMarks() {
/*      */     if (this._tetMarkRed == 2147483646) {
/*      */       this._tetMarkRed++;
/*      */       this._tetMarkBlue--;
/*      */       markAllTets(this._troot);
/*      */       zeroTetMarks(this._troot);
/*      */       this._tetMarkRed = 0;
/*      */       this._tetMarkBlue = 0;
/*      */     } 
/*      */     this._tetMarkRed++;
/*      */     this._tetMarkBlue--;
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
/*      */   public synchronized NodePropertyMap getNodePropertyMap(String name) {
/*      */     NodePropertyMap map = this._nodePropertyMaps.get(name);
/*      */     if (map == null) {
/*      */       if (this._nnodeValues == this._lnodeValues) {
/*      */         if (this._lnodeValues == 0) {
/*      */           this._lnodeValues = 4;
/*      */         } else {
/*      */           this._lnodeValues *= 2;
/*      */         } 
/*      */         NodeIterator ni = getNodes();
/*      */         while (ni.hasNext()) {
/*      */           Node node = ni.next();
/*      */           Object[] valuesOld = node._values;
/*      */           Object[] valuesNew = new Object[this._lnodeValues];
/*      */           for (int i = 0; i < this._nnodeValues; i++) {
/*      */             valuesNew[i] = valuesOld[i];
/*      */           }
/*      */           node._values = valuesNew;
/*      */         } 
/*      */       } 
/*      */       int index = this._nnodeValues++;
/*      */       map = new NodePropertyMapInternal(index);
/*      */       this._nodePropertyMaps.put(name, map);
/*      */     } 
/*      */     return map;
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
/*      */   public synchronized boolean hasNodePropertyMap(String name) {
/*      */     return this._nodePropertyMaps.containsKey(name);
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
/*      */   public synchronized String[] getNodePropertyMapNames() {
/*      */     Set<String> nameSet = this._nodePropertyMaps.keySet();
/*      */     String[] names = new String[nameSet.size()];
/*      */     return nameSet.<String>toArray(names);
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
/*      */   public synchronized void addNodeListener(NodeListener nl) {
/*      */     this._listeners.add(NodeListener.class, nl);
/*      */     this._nnodeListeners++;
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
/*      */   public synchronized void removeNodeListener(NodeListener nl) {
/*      */     this._listeners.remove(NodeListener.class, nl);
/*      */     this._nnodeListeners--;
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
/*      */   public synchronized void addTetListener(TetListener tl) {
/*      */     this._listeners.add(TetListener.class, tl);
/*      */     this._ntetListeners++;
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
/*      */   public synchronized void removeTetListener(TetListener tl) {
/*      */     this._listeners.remove(TetListener.class, tl);
/*      */     this._ntetListeners--;
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
/*      */   public synchronized void validate() {
/*      */     int nnode = 0;
/*      */     NodeIterator ni = getNodes();
/*      */     while (ni.hasNext()) {
/*      */       nnode++;
/*      */       Node node = ni.next();
/*      */       validate(node);
/*      */     } 
/*      */     Check.state((nnode == this._nnode), "nnode==_nnode");
/*      */     int ntet = 0;
/*      */     TetIterator ti = getTets();
/*      */     while (ti.hasNext()) {
/*      */       ntet++;
/*      */       Tet tet = ti.next();
/*      */       validate(tet);
/*      */     } 
/*      */     Check.state((ntet == this._ntet), "ntet==_ntet");
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
/*      */   protected void init() {
/*      */     this._version = 0L;
/*      */     this._nnode = 0;
/*      */     this._ntet = 0;
/*      */     this._nroot = null;
/*      */     this._troot = null;
/*      */     this._sampledNodes = new HashSet<>(256);
/*      */     this._tetMarkRed = 0;
/*      */     this._tetMarkBlue = 0;
/*      */     this._nodeMarkRed = 0;
/*      */     this._nodeMarkBlue = 0;
/*      */     this._faceSet = new FaceSet(256, 0.25D);
/*      */     this._edgeSet = new EdgeSet(256, 0.25D);
/*      */     this._nodeList = new NodeList();
/*      */     this._nmin = null;
/*      */     this._dmin = 0.0D;
/*      */     this._deadTets = new TetList();
/*      */     this._nnodeListeners = 0;
/*      */     this._ntetListeners = 0;
/*      */     this._listeners = new EventListenerList();
/*      */     this._outerEnabled = false;
/*      */     this._xminOuter = 0.0D;
/*      */     this._yminOuter = 0.0D;
/*      */     this._zminOuter = 0.0D;
/*      */     this._xmaxOuter = 0.0D;
/*      */     this._ymaxOuter = 0.0D;
/*      */     this._zmaxOuter = 0.0D;
/*      */     this._nnodeValues = 0;
/*      */     this._lnodeValues = 0;
/*      */     this._nodePropertyMaps = new HashMap<>();
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
/*      */   public TetMesh() {
/* 3535 */     this._nroot = null;
/* 3536 */     this._troot = null;
/*      */     init();
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
/*      */     public Object get(TetMesh.Node node) {
/* 3567 */       Object[] values = node._values;
/* 3568 */       return values[this._index];
/*      */     }
/*      */     public void put(TetMesh.Node node, Object value) {
/* 3571 */       Object[] values = node._values;
/* 3572 */       values[this._index] = value;
/*      */     }
/*      */     NodePropertyMapInternal(int index) {
/* 3575 */       this._index = index;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void updatePropertyValues(Node node) {
/* 3584 */     if (this._lnodeValues == 0) {
/* 3585 */       node._values = null;
/* 3586 */     } else if (node._values == null) {
/* 3587 */       node._values = new Object[this._lnodeValues];
/* 3588 */     } else if (node._values.length != this._lnodeValues) {
/* 3589 */       Object[] valuesOld = node._values;
/* 3590 */       Object[] valuesNew = new Object[this._lnodeValues];
/* 3591 */       int n = MathPlus.min(valuesOld.length, valuesNew.length);
/* 3592 */       for (int i = 0; i < n; i++)
/* 3593 */         valuesNew[i] = valuesOld[i]; 
/* 3594 */       node._values = valuesNew;
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
/*      */   private TetIterator getTetsInternal() {
/* 3627 */     return new TetIterator() {
/*      */         public final boolean hasNext() {
/* 3629 */           return !this._stack.isEmpty();
/*      */         } private ArrayList<TetMesh.Tet> _stack;
/*      */         public final TetMesh.Tet next() {
/* 3632 */           int ntet = this._stack.size();
/* 3633 */           if (ntet == 0)
/* 3634 */             throw new NoSuchElementException(); 
/* 3635 */           TetMesh.Tet tet = this._stack.remove(ntet - 1);
/* 3636 */           stackTet(tet._t0);
/* 3637 */           stackTet(tet._t1);
/* 3638 */           stackTet(tet._t2);
/* 3639 */           stackTet(tet._t3);
/* 3640 */           return tet;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         private void stackTet(TetMesh.Tet tet) {
/* 3649 */           if (tet != null && !TetMesh.this.isMarked(tet)) {
/* 3650 */             TetMesh.this.mark(tet);
/* 3651 */             this._stack.add(tet);
/*      */           } 
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
/*      */   private Tet makeTet(Node n0, Node n1, Node n2, Node n3) {
/* 3664 */     this._ntet++;
/* 3665 */     int ndead = this._deadTets.ntet();
/* 3666 */     if (ndead == 0) {
/* 3667 */       this._troot = new Tet(n0, n1, n2, n3);
/*      */     } else {
/* 3669 */       this._troot = this._deadTets.remove(ndead - 1);
/* 3670 */       this._troot.init(n0, n1, n2, n3);
/*      */     } 
/* 3672 */     if (this._ntetListeners > 0)
/* 3673 */       fireTetAdded(this._troot); 
/* 3674 */     return this._troot;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void killTet(Tet tet) {
/* 3685 */     this._ntet--;
/* 3686 */     fireTetRemoved(tet);
/* 3687 */     int ndead = this._deadTets.ntet();
/* 3688 */     if (ndead < 256) {
/* 3689 */       this._deadTets.add(tet);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unlinkNode(Node node) {
/* 3699 */     this._nroot = node._next;
/* 3700 */     this._nmin = node._next;
/* 3701 */     if (this._nroot == node) {
/* 3702 */       this._nroot = null;
/* 3703 */       this._nmin = null;
/*      */     } 
/* 3705 */     node._prev._next = node._next;
/* 3706 */     node._next._prev = node._prev;
/* 3707 */     node._prev = null;
/* 3708 */     node._next = null;
/* 3709 */     node._tet = null;
/* 3710 */     this._sampledNodes.remove(node);
/* 3711 */     this._nnode--;
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
/*      */   private static double distanceSquared(Node node, double x, double y, double z) {
/* 3733 */     double dx = x - node._x;
/* 3734 */     double dy = y - node._y;
/* 3735 */     double dz = z - node._z;
/* 3736 */     return dx * dx + dy * dy + dz * dz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double distanceToPlaneSquared(Node node, double a, double b, double c, double d) {
/* 3746 */     double dp = a * node._x + b * node._y + c * node._z + d;
/* 3747 */     return dp * dp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean leftOfPlane(Node a, Node b, Node c, Node n) {
/* 3755 */     return (Geometry.leftOfPlane(a
/* 3756 */         ._x, a._y, a._z, b
/* 3757 */         ._x, b._y, b._z, c
/* 3758 */         ._x, c._y, c._z, n
/* 3759 */         ._x, n._y, n._z) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean leftOfPlane(Node a, Node b, Node c, double x, double y, double z) {
/* 3769 */     return (Geometry.leftOfPlane(a
/* 3770 */         ._x, a._y, a._z, b
/* 3771 */         ._x, b._y, b._z, c
/* 3772 */         ._x, c._y, c._z, x, y, z) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inSphere(Node a, Node b, Node c, Node d, Node n) {
/* 3783 */     return (Geometry.inSphere(a
/* 3784 */         ._x, a._y, a._z, b
/* 3785 */         ._x, b._y, b._z, c
/* 3786 */         ._x, c._y, c._z, d
/* 3787 */         ._x, d._y, d._z, n
/* 3788 */         ._x, n._y, n._z) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean inSphere(Node a, Node b, Node c, Node d, double x, double y, double z) {
/* 3799 */     return (Geometry.inSphere(a
/* 3800 */         ._x, a._y, a._z, b
/* 3801 */         ._x, b._y, b._z, c
/* 3802 */         ._x, c._y, c._z, d
/* 3803 */         ._x, d._y, d._z, x, y, z) > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createFirstTet() {
/* 3811 */     Check.state((this._nnode == 4), "exactly four nodes available for first tet");
/* 3812 */     Node n0 = this._nroot;
/* 3813 */     Node n1 = n0._next;
/* 3814 */     Node n2 = n1._next;
/* 3815 */     Node n3 = n2._next;
/* 3816 */     double orient = Geometry.leftOfPlane(n0
/* 3817 */         ._x, n0._y, n0._z, n1
/* 3818 */         ._x, n1._y, n1._z, n2
/* 3819 */         ._x, n2._y, n2._z, n3
/* 3820 */         ._x, n3._y, n3._z);
/* 3821 */     if (orient == 0.0D) {
/* 3822 */       trace("orient=" + orient);
/* 3823 */       trace("n0=(" + n0._x + "," + n0._y + "," + n0._z + ")");
/* 3824 */       trace("n1=(" + n1._x + "," + n1._y + "," + n1._z + ")");
/* 3825 */       trace("n2=(" + n2._x + "," + n2._y + "," + n2._z + ")");
/* 3826 */       trace("n3=(" + n3._x + "," + n3._y + "," + n3._z + ")");
/*      */     } 
/* 3828 */     Check.state((orient != 0.0D), "four nodes for first tet are not co-planar");
/* 3829 */     if (orient > 0.0D) {
/* 3830 */       makeTet(n0, n1, n2, n3);
/*      */     } else {
/* 3832 */       makeTet(n0, n2, n1, n3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNodeNabors(Node node, Tet tet, NodeList nabors) {
/* 3843 */     mark(tet);
/* 3844 */     Node n0 = tet._n0;
/* 3845 */     Node n1 = tet._n1;
/* 3846 */     Node n2 = tet._n2;
/* 3847 */     Node n3 = tet._n3;
/* 3848 */     Tet t0 = tet._t0;
/* 3849 */     Tet t1 = tet._t1;
/* 3850 */     Tet t2 = tet._t2;
/* 3851 */     Tet t3 = tet._t3;
/* 3852 */     if (node == n0) {
/* 3853 */       if (!isMarked(n1)) {
/* 3854 */         mark(n1);
/* 3855 */         nabors.add(n1);
/*      */       } 
/* 3857 */       if (!isMarked(n2)) {
/* 3858 */         mark(n2);
/* 3859 */         nabors.add(n2);
/*      */       } 
/* 3861 */       if (!isMarked(n3)) {
/* 3862 */         mark(n3);
/* 3863 */         nabors.add(n3);
/*      */       } 
/* 3865 */       if (t1 != null && !isMarked(t1))
/* 3866 */         getNodeNabors(node, t1, nabors); 
/* 3867 */       if (t2 != null && !isMarked(t2))
/* 3868 */         getNodeNabors(node, t2, nabors); 
/* 3869 */       if (t3 != null && !isMarked(t3))
/* 3870 */         getNodeNabors(node, t3, nabors); 
/* 3871 */     } else if (node == n1) {
/* 3872 */       if (!isMarked(n3)) {
/* 3873 */         mark(n3);
/* 3874 */         nabors.add(n3);
/*      */       } 
/* 3876 */       if (!isMarked(n2)) {
/* 3877 */         mark(n2);
/* 3878 */         nabors.add(n2);
/*      */       } 
/* 3880 */       if (!isMarked(n0)) {
/* 3881 */         mark(n0);
/* 3882 */         nabors.add(n0);
/*      */       } 
/* 3884 */       if (t3 != null && !isMarked(t3))
/* 3885 */         getNodeNabors(node, t3, nabors); 
/* 3886 */       if (t2 != null && !isMarked(t2))
/* 3887 */         getNodeNabors(node, t2, nabors); 
/* 3888 */       if (t0 != null && !isMarked(t0))
/* 3889 */         getNodeNabors(node, t0, nabors); 
/* 3890 */     } else if (node == n2) {
/* 3891 */       if (!isMarked(n3)) {
/* 3892 */         mark(n3);
/* 3893 */         nabors.add(n3);
/*      */       } 
/* 3895 */       if (!isMarked(n0)) {
/* 3896 */         mark(n0);
/* 3897 */         nabors.add(n0);
/*      */       } 
/* 3899 */       if (!isMarked(n1)) {
/* 3900 */         mark(n1);
/* 3901 */         nabors.add(n1);
/*      */       } 
/* 3903 */       if (t3 != null && !isMarked(t3))
/* 3904 */         getNodeNabors(node, t3, nabors); 
/* 3905 */       if (t0 != null && !isMarked(t0))
/* 3906 */         getNodeNabors(node, t0, nabors); 
/* 3907 */       if (t1 != null && !isMarked(t1))
/* 3908 */         getNodeNabors(node, t1, nabors); 
/* 3909 */     } else if (node == n3) {
/* 3910 */       if (!isMarked(n1)) {
/* 3911 */         mark(n1);
/* 3912 */         nabors.add(n1);
/*      */       } 
/* 3914 */       if (!isMarked(n0)) {
/* 3915 */         mark(n0);
/* 3916 */         nabors.add(n0);
/*      */       } 
/* 3918 */       if (!isMarked(n2)) {
/* 3919 */         mark(n2);
/* 3920 */         nabors.add(n2);
/*      */       } 
/* 3922 */       if (t1 != null && !isMarked(t1))
/* 3923 */         getNodeNabors(node, t1, nabors); 
/* 3924 */       if (t0 != null && !isMarked(t0))
/* 3925 */         getNodeNabors(node, t0, nabors); 
/* 3926 */       if (t2 != null && !isMarked(t2))
/* 3927 */         getNodeNabors(node, t2, nabors); 
/*      */     } else {
/* 3929 */       assert false : "node is referenced by tet";
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
/*      */   private void getNodeNabors(Node node, int step, NodeStepList list) {
/* 3941 */     Tet[] tets = getTetNabors(node);
/* 3942 */     int ntet = tets.length;
/* 3943 */     for (int itet = 0; itet < ntet; itet++) {
/* 3944 */       Tet tet = tets[itet];
/* 3945 */       Node n0 = tet._n0;
/* 3946 */       Node n1 = tet._n1;
/* 3947 */       Node n2 = tet._n2;
/* 3948 */       Node n3 = tet._n3;
/* 3949 */       if (node == n0) {
/* 3950 */         if (!isMarked(n1)) {
/* 3951 */           mark(n1);
/* 3952 */           list.add(n1, step);
/*      */         } 
/* 3954 */         if (!isMarked(n2)) {
/* 3955 */           mark(n2);
/* 3956 */           list.add(n2, step);
/*      */         } 
/* 3958 */         if (!isMarked(n3)) {
/* 3959 */           mark(n3);
/* 3960 */           list.add(n3, step);
/*      */         } 
/* 3962 */       } else if (node == n1) {
/* 3963 */         if (!isMarked(n0)) {
/* 3964 */           mark(n0);
/* 3965 */           list.add(n0, step);
/*      */         } 
/* 3967 */         if (!isMarked(n2)) {
/* 3968 */           mark(n2);
/* 3969 */           list.add(n2, step);
/*      */         } 
/* 3971 */         if (!isMarked(n3)) {
/* 3972 */           mark(n3);
/* 3973 */           list.add(n3, step);
/*      */         } 
/* 3975 */       } else if (node == n2) {
/* 3976 */         if (!isMarked(n0)) {
/* 3977 */           mark(n0);
/* 3978 */           list.add(n0, step);
/*      */         } 
/* 3980 */         if (!isMarked(n1)) {
/* 3981 */           mark(n1);
/* 3982 */           list.add(n1, step);
/*      */         } 
/* 3984 */         if (!isMarked(n3)) {
/* 3985 */           mark(n3);
/* 3986 */           list.add(n3, step);
/*      */         } 
/* 3988 */       } else if (node == n3) {
/* 3989 */         if (!isMarked(n0)) {
/* 3990 */           mark(n0);
/* 3991 */           list.add(n0, step);
/*      */         } 
/* 3993 */         if (!isMarked(n1)) {
/* 3994 */           mark(n1);
/* 3995 */           list.add(n1, step);
/*      */         } 
/* 3997 */         if (!isMarked(n2)) {
/* 3998 */           mark(n2);
/* 3999 */           list.add(n2, step);
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
/*      */   
/*      */   private void getTetNabors(Node node, Tet tet, TetList nabors) {
/* 4013 */     if (tet != null) {
/* 4014 */       mark(tet);
/* 4015 */       nabors.add(tet);
/* 4016 */       Node n0 = tet._n0;
/* 4017 */       Node n1 = tet._n1;
/* 4018 */       Node n2 = tet._n2;
/* 4019 */       Node n3 = tet._n3;
/* 4020 */       Tet t0 = tet._t0;
/* 4021 */       Tet t1 = tet._t1;
/* 4022 */       Tet t2 = tet._t2;
/* 4023 */       Tet t3 = tet._t3;
/* 4024 */       if (node == n0) {
/* 4025 */         if (t1 != null && !isMarked(t1))
/* 4026 */           getTetNabors(node, t1, nabors); 
/* 4027 */         if (t2 != null && !isMarked(t2))
/* 4028 */           getTetNabors(node, t2, nabors); 
/* 4029 */         if (t3 != null && !isMarked(t3))
/* 4030 */           getTetNabors(node, t3, nabors); 
/* 4031 */       } else if (node == n1) {
/* 4032 */         if (t3 != null && !isMarked(t3))
/* 4033 */           getTetNabors(node, t3, nabors); 
/* 4034 */         if (t2 != null && !isMarked(t2))
/* 4035 */           getTetNabors(node, t2, nabors); 
/* 4036 */         if (t0 != null && !isMarked(t0))
/* 4037 */           getTetNabors(node, t0, nabors); 
/* 4038 */       } else if (node == n2) {
/* 4039 */         if (t3 != null && !isMarked(t3))
/* 4040 */           getTetNabors(node, t3, nabors); 
/* 4041 */         if (t0 != null && !isMarked(t0))
/* 4042 */           getTetNabors(node, t0, nabors); 
/* 4043 */         if (t1 != null && !isMarked(t1))
/* 4044 */           getTetNabors(node, t1, nabors); 
/* 4045 */       } else if (node == n3) {
/* 4046 */         if (t1 != null && !isMarked(t1))
/* 4047 */           getTetNabors(node, t1, nabors); 
/* 4048 */         if (t0 != null && !isMarked(t0))
/* 4049 */           getTetNabors(node, t0, nabors); 
/* 4050 */         if (t2 != null && !isMarked(t2))
/* 4051 */           getTetNabors(node, t2, nabors); 
/*      */       } else {
/* 4053 */         assert false : "node is referenced by tet";
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
/*      */   private void getTetNabors(Node na, Node nb, Tet tet, TetList nabors) {
/* 4065 */     if (tet != null) {
/* 4066 */       mark(tet);
/* 4067 */       nabors.add(tet);
/* 4068 */       Node n0 = tet._n0;
/* 4069 */       Node n1 = tet._n1;
/* 4070 */       Node n2 = tet._n2;
/* 4071 */       Node n3 = tet._n3;
/* 4072 */       Tet t0 = tet._t0;
/* 4073 */       Tet t1 = tet._t1;
/* 4074 */       Tet t2 = tet._t2;
/* 4075 */       Tet t3 = tet._t3;
/* 4076 */       Tet tc = null;
/* 4077 */       Tet td = null;
/* 4078 */       if (na == n0) {
/* 4079 */         if (nb == n1) {
/* 4080 */           tc = t2;
/* 4081 */           td = t3;
/* 4082 */         } else if (nb == n2) {
/* 4083 */           tc = t1;
/* 4084 */           td = t3;
/* 4085 */         } else if (nb == n3) {
/* 4086 */           tc = t1;
/* 4087 */           td = t2;
/*      */         } else {
/* 4089 */           assert false : "nodes na and nb are referenced by tet";
/*      */         } 
/* 4091 */       } else if (na == n1) {
/* 4092 */         if (nb == n0) {
/* 4093 */           tc = t2;
/* 4094 */           td = t3;
/* 4095 */         } else if (nb == n2) {
/* 4096 */           tc = t0;
/* 4097 */           td = t3;
/* 4098 */         } else if (nb == n3) {
/* 4099 */           tc = t0;
/* 4100 */           td = t2;
/*      */         } else {
/* 4102 */           assert false : "nodes na and nb are referenced by tet";
/*      */         } 
/* 4104 */       } else if (na == n2) {
/* 4105 */         if (nb == n0) {
/* 4106 */           tc = t1;
/* 4107 */           td = t3;
/* 4108 */         } else if (nb == n1) {
/* 4109 */           tc = t0;
/* 4110 */           td = t3;
/* 4111 */         } else if (nb == n3) {
/* 4112 */           tc = t0;
/* 4113 */           td = t1;
/*      */         } else {
/* 4115 */           assert false : "nodes na and nb are referenced by tet";
/*      */         } 
/* 4117 */       } else if (na == n3) {
/* 4118 */         if (nb == n0) {
/* 4119 */           tc = t1;
/* 4120 */           td = t2;
/* 4121 */         } else if (nb == n1) {
/* 4122 */           tc = t0;
/* 4123 */           td = t2;
/* 4124 */         } else if (nb == n2) {
/* 4125 */           tc = t0;
/* 4126 */           td = t1;
/*      */         } else {
/* 4128 */           assert false : "nodes na and nb are referenced by tet";
/*      */         } 
/*      */       } else {
/* 4131 */         assert false : "node na is referenced by tet";
/*      */       } 
/* 4133 */       if (tc != null && !isMarked(tc)) {
/* 4134 */         getTetNabors(na, nb, tc, nabors);
/*      */       }
/* 4136 */       if (td != null && !isMarked(td)) {
/* 4137 */         getTetNabors(na, nb, td, nabors);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tet findTet(Tet tet, Node na, Node nb) {
/* 4148 */     if (tet != null) {
/* 4149 */       mark(tet);
/* 4150 */       Node n0 = tet._n0;
/* 4151 */       Node n1 = tet._n1;
/* 4152 */       Node n2 = tet._n2;
/* 4153 */       Node n3 = tet._n3;
/* 4154 */       Tet t0 = tet._t0;
/* 4155 */       Tet t1 = tet._t1;
/* 4156 */       Tet t2 = tet._t2;
/* 4157 */       Tet t3 = tet._t3;
/* 4158 */       if (na == n0) {
/* 4159 */         if (nb == n1 || nb == n2 || nb == n3 || (t1 != null && 
/* 4160 */           !isMarked(t1) && (tet = findTet(t1, na, nb)) != null) || (t2 != null && 
/* 4161 */           !isMarked(t2) && (tet = findTet(t2, na, nb)) != null) || (t3 != null && 
/* 4162 */           !isMarked(t3) && (tet = findTet(t3, na, nb)) != null))
/* 4163 */           return tet; 
/* 4164 */       } else if (na == n1) {
/* 4165 */         if (nb == n3 || nb == n2 || nb == n0 || (t3 != null && 
/* 4166 */           !isMarked(t3) && (tet = findTet(t3, na, nb)) != null) || (t2 != null && 
/* 4167 */           !isMarked(t2) && (tet = findTet(t2, na, nb)) != null) || (t0 != null && 
/* 4168 */           !isMarked(t0) && (tet = findTet(t0, na, nb)) != null))
/* 4169 */           return tet; 
/* 4170 */       } else if (na == n2) {
/* 4171 */         if (nb == n3 || nb == n0 || nb == n1 || (t3 != null && 
/* 4172 */           !isMarked(t3) && (tet = findTet(t3, na, nb)) != null) || (t0 != null && 
/* 4173 */           !isMarked(t0) && (tet = findTet(t0, na, nb)) != null) || (t1 != null && 
/* 4174 */           !isMarked(t1) && (tet = findTet(t1, na, nb)) != null))
/* 4175 */           return tet; 
/* 4176 */       } else if (na == n3) {
/* 4177 */         if (nb == n1 || nb == n0 || nb == n2 || (t1 != null && 
/* 4178 */           !isMarked(t1) && (tet = findTet(t1, na, nb)) != null) || (t0 != null && 
/* 4179 */           !isMarked(t0) && (tet = findTet(t0, na, nb)) != null) || (t2 != null && 
/* 4180 */           !isMarked(t2) && (tet = findTet(t2, na, nb)) != null))
/* 4181 */           return tet; 
/*      */       } else {
/* 4183 */         assert false : "node na is referenced by tet";
/*      */       } 
/*      */     } 
/* 4186 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tet findTet(Tet tet, Node na, Node nb, Node nc) {
/* 4195 */     if (tet != null) {
/* 4196 */       mark(tet);
/* 4197 */       Node n0 = tet._n0;
/* 4198 */       Node n1 = tet._n1;
/* 4199 */       Node n2 = tet._n2;
/* 4200 */       Node n3 = tet._n3;
/* 4201 */       Tet t0 = tet._t0;
/* 4202 */       Tet t1 = tet._t1;
/* 4203 */       Tet t2 = tet._t2;
/* 4204 */       Tet t3 = tet._t3;
/* 4205 */       if (na == n0) {
/* 4206 */         if (nb == n1) {
/* 4207 */           if (nc == n2 || nc == n3 || (t2 != null && 
/* 4208 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null) || (t3 != null && 
/* 4209 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4210 */             return tet; 
/* 4211 */         } else if (nb == n2) {
/* 4212 */           if (nc == n1 || nc == n3 || (t1 != null && 
/* 4213 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null) || (t3 != null && 
/* 4214 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4215 */             return tet; 
/* 4216 */         } else if (nb == n3) {
/* 4217 */           if (nc == n1 || nc == n2 || (t1 != null && 
/* 4218 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null) || (t2 != null && 
/* 4219 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null))
/* 4220 */             return tet; 
/*      */         } else {
/* 4222 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4224 */       } else if (na == n1) {
/* 4225 */         if (nb == n0) {
/* 4226 */           if (nc == n2 || nc == n3 || (t2 != null && 
/* 4227 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null) || (t3 != null && 
/* 4228 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4229 */             return tet; 
/* 4230 */         } else if (nb == n2) {
/* 4231 */           if (nc == n0 || nc == n3 || (t0 != null && 
/* 4232 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t3 != null && 
/* 4233 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4234 */             return tet; 
/* 4235 */         } else if (nb == n3) {
/* 4236 */           if (nc == n0 || nc == n2 || (t0 != null && 
/* 4237 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t2 != null && 
/* 4238 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null))
/* 4239 */             return tet; 
/*      */         } else {
/* 4241 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4243 */       } else if (na == n2) {
/* 4244 */         if (nb == n0) {
/* 4245 */           if (nc == n1 || nc == n3 || (t1 != null && 
/* 4246 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null) || (t3 != null && 
/* 4247 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4248 */             return tet; 
/* 4249 */         } else if (nb == n1) {
/* 4250 */           if (nc == n0 || nc == n3 || (t0 != null && 
/* 4251 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t3 != null && 
/* 4252 */             !isMarked(t3) && (tet = findTet(t3, na, nb, nc)) != null))
/* 4253 */             return tet; 
/* 4254 */         } else if (nb == n3) {
/* 4255 */           if (nc == n0 || nc == n1 || (t0 != null && 
/* 4256 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t1 != null && 
/* 4257 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null))
/* 4258 */             return tet; 
/*      */         } else {
/* 4260 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4262 */       } else if (na == n3) {
/* 4263 */         if (nb == n0) {
/* 4264 */           if (nc == n1 || nc == n2 || (t1 != null && 
/* 4265 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null) || (t2 != null && 
/* 4266 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null))
/* 4267 */             return tet; 
/* 4268 */         } else if (nb == n1) {
/* 4269 */           if (nc == n0 || nc == n2 || (t0 != null && 
/* 4270 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t2 != null && 
/* 4271 */             !isMarked(t2) && (tet = findTet(t2, na, nb, nc)) != null))
/* 4272 */             return tet; 
/* 4273 */         } else if (nb == n2) {
/* 4274 */           if (nc == n0 || nc == n1 || (t0 != null && 
/* 4275 */             !isMarked(t0) && (tet = findTet(t0, na, nb, nc)) != null) || (t1 != null && 
/* 4276 */             !isMarked(t1) && (tet = findTet(t1, na, nb, nc)) != null))
/* 4277 */             return tet; 
/*      */         } else {
/* 4279 */           assert false : "node nb is referenced by tet";
/*      */         } 
/*      */       } else {
/* 4282 */         assert false : "node na is referenced by tet";
/*      */       } 
/*      */     } 
/* 4285 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tet findTet(Tet tet, Node na, Node nb, Node nc, Node nd) {
/* 4295 */     if (tet != null) {
/* 4296 */       mark(tet);
/* 4297 */       Node n0 = tet._n0;
/* 4298 */       Node n1 = tet._n1;
/* 4299 */       Node n2 = tet._n2;
/* 4300 */       Node n3 = tet._n3;
/* 4301 */       Tet t0 = tet._t0;
/* 4302 */       Tet t1 = tet._t1;
/* 4303 */       Tet t2 = tet._t2;
/* 4304 */       Tet t3 = tet._t3;
/* 4305 */       if (na == n0) {
/* 4306 */         if (nb == n1) {
/* 4307 */           if (nc == n2) {
/* 4308 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4309 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4310 */               return tet; 
/* 4311 */           } else if (nc == n3) {
/* 4312 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4313 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4314 */               return tet; 
/*      */           } else {
/* 4316 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4318 */         } else if (nb == n2) {
/* 4319 */           if (nc == n1) {
/* 4320 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4321 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4322 */               return tet; 
/* 4323 */           } else if (nc == n3) {
/* 4324 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4325 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4326 */               return tet; 
/*      */           } else {
/* 4328 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4330 */         } else if (nb == n3) {
/* 4331 */           if (nc == n1) {
/* 4332 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4333 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4334 */               return tet; 
/* 4335 */           } else if (nc == n2) {
/* 4336 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4337 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4338 */               return tet; 
/*      */           } else {
/* 4340 */             assert false : "node nc is referenced by tet";
/*      */           } 
/*      */         } else {
/* 4343 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4345 */       } else if (na == n1) {
/* 4346 */         if (nb == n0) {
/* 4347 */           if (nc == n2) {
/* 4348 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4349 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4350 */               return tet; 
/* 4351 */           } else if (nc == n3) {
/* 4352 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4353 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4354 */               return tet; 
/*      */           } else {
/* 4356 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4358 */         } else if (nb == n2) {
/* 4359 */           if (nc == n0) {
/* 4360 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4361 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4362 */               return tet; 
/* 4363 */           } else if (nc == n3) {
/* 4364 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4365 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4366 */               return tet; 
/*      */           } else {
/* 4368 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4370 */         } else if (nb == n3) {
/* 4371 */           if (nc == n0) {
/* 4372 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4373 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4374 */               return tet; 
/* 4375 */           } else if (nc == n2) {
/* 4376 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4377 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4378 */               return tet; 
/*      */           } else {
/* 4380 */             assert false : "node nc is referenced by tet";
/*      */           } 
/*      */         } else {
/* 4383 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4385 */       } else if (na == n2) {
/* 4386 */         if (nb == n0) {
/* 4387 */           if (nc == n1) {
/* 4388 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4389 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4390 */               return tet; 
/* 4391 */           } else if (nc == n3) {
/* 4392 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4393 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4394 */               return tet; 
/*      */           } else {
/* 4396 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4398 */         } else if (nb == n1) {
/* 4399 */           if (nc == n0) {
/* 4400 */             if (nd == n3 || (t3 != null && !isMarked(t3) && (
/* 4401 */               tet = findTet(t3, na, nb, nc, nd)) != null))
/* 4402 */               return tet; 
/* 4403 */           } else if (nc == n3) {
/* 4404 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4405 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4406 */               return tet; 
/*      */           } else {
/* 4408 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4410 */         } else if (nb == n3) {
/* 4411 */           if (nc == n0) {
/* 4412 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4413 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4414 */               return tet; 
/* 4415 */           } else if (nc == n1) {
/* 4416 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4417 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4418 */               return tet; 
/*      */           } else {
/* 4420 */             assert false : "node nc is referenced by tet";
/*      */           } 
/*      */         } else {
/* 4423 */           assert false : "node nb is referenced by tet";
/*      */         } 
/* 4425 */       } else if (na == n3) {
/* 4426 */         if (nb == n0) {
/* 4427 */           if (nc == n1) {
/* 4428 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4429 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4430 */               return tet; 
/* 4431 */           } else if (nc == n2) {
/* 4432 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4433 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4434 */               return tet; 
/*      */           } else {
/* 4436 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4438 */         } else if (nb == n1) {
/* 4439 */           if (nc == n0) {
/* 4440 */             if (nd == n2 || (t2 != null && !isMarked(t2) && (
/* 4441 */               tet = findTet(t2, na, nb, nc, nd)) != null))
/* 4442 */               return tet; 
/* 4443 */           } else if (nc == n2) {
/* 4444 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4445 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4446 */               return tet; 
/*      */           } else {
/* 4448 */             assert false : "node nc is referenced by tet";
/*      */           } 
/* 4450 */         } else if (nb == n2) {
/* 4451 */           if (nc == n0) {
/* 4452 */             if (nd == n1 || (t1 != null && !isMarked(t1) && (
/* 4453 */               tet = findTet(t1, na, nb, nc, nd)) != null))
/* 4454 */               return tet; 
/* 4455 */           } else if (nc == n1) {
/* 4456 */             if (nd == n0 || (t0 != null && !isMarked(t0) && (
/* 4457 */               tet = findTet(t0, na, nb, nc, nd)) != null))
/* 4458 */               return tet; 
/*      */           } else {
/* 4460 */             assert false : "node nc is referenced by tet";
/*      */           } 
/*      */         } else {
/* 4463 */           assert false : "node nb is referenced by tet";
/*      */         } 
/*      */       } else {
/* 4466 */         assert false : "node na is referenced by tet";
/*      */       } 
/*      */     } 
/* 4469 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node findNodeNearest(double x, double y, double z) {
/* 4478 */     if (this._nnode == 0) {
/* 4479 */       return null;
/*      */     }
/*      */     
/* 4482 */     if (this._nnode < 20) {
/* 4483 */       this._nmin = this._nroot;
/* 4484 */       this._dmin = distanceSquared(this._nmin, x, y, z);
/* 4485 */       NodeIterator ni = getNodes();
/* 4486 */       while (ni.hasNext()) {
/* 4487 */         Node n = ni.next();
/* 4488 */         double d = distanceSquared(n, x, y, z);
/* 4489 */         if (d < this._dmin) {
/* 4490 */           this._dmin = d;
/* 4491 */           this._nmin = n;
/*      */         } 
/*      */       } 
/* 4494 */       return this._nmin;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4499 */     this._nmin = this._nroot;
/* 4500 */     this._dmin = distanceSquared(this._nmin, x, y, z);
/* 4501 */     for (Node n : this._sampledNodes) {
/* 4502 */       double d = distanceSquared(n, x, y, z);
/* 4503 */       if (d < this._dmin) {
/* 4504 */         this._dmin = d;
/* 4505 */         this._nmin = n;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4514 */     clearNodeMarks();
/*      */     
/*      */     while (true) {
/* 4517 */       clearTetMarks();
/* 4518 */       double dmin = this._dmin;
/* 4519 */       findNodeNaborNearest(x, y, z, this._nmin, this._nmin._tet);
/* 4520 */       if (this._dmin >= dmin) {
/* 4521 */         return this._nmin;
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
/*      */   private void findNodeNaborNearest(double x, double y, double z, Node node, Tet tet) {
/* 4533 */     mark(tet);
/* 4534 */     Node n0 = tet._n0;
/* 4535 */     Node n1 = tet._n1;
/* 4536 */     Node n2 = tet._n2;
/* 4537 */     Node n3 = tet._n3;
/* 4538 */     Tet t0 = tet._t0;
/* 4539 */     Tet t1 = tet._t1;
/* 4540 */     Tet t2 = tet._t2;
/* 4541 */     Tet t3 = tet._t3;
/* 4542 */     if (node == n0) {
/* 4543 */       findNodeNaborNearest(x, y, z, node, n1, n2, n3, t1, t2, t3);
/* 4544 */     } else if (node == n1) {
/* 4545 */       findNodeNaborNearest(x, y, z, node, n3, n2, n0, t3, t2, t0);
/* 4546 */     } else if (node == n2) {
/* 4547 */       findNodeNaborNearest(x, y, z, node, n3, n0, n1, t3, t0, t1);
/* 4548 */     } else if (node == n3) {
/* 4549 */       findNodeNaborNearest(x, y, z, node, n1, n0, n2, t1, t0, t2);
/*      */     } else {
/* 4551 */       assert false : "node is referenced by tet";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void findNodeNaborNearest(double x, double y, double z, Node node, Node na, Node nb, Node nc, Tet ta, Tet tb, Tet tc) {
/* 4558 */     if (!isMarked(na)) {
/* 4559 */       mark(na);
/* 4560 */       double da = distanceSquared(na, x, y, z);
/* 4561 */       if (da < this._dmin) {
/* 4562 */         this._dmin = da;
/* 4563 */         this._nmin = na;
/*      */       } 
/*      */     } 
/* 4566 */     if (!isMarked(nb)) {
/* 4567 */       mark(nb);
/* 4568 */       double db = distanceSquared(nb, x, y, z);
/* 4569 */       if (db < this._dmin) {
/* 4570 */         this._dmin = db;
/* 4571 */         this._nmin = nb;
/*      */       } 
/*      */     } 
/* 4574 */     if (!isMarked(nc)) {
/* 4575 */       mark(nc);
/* 4576 */       double dc = distanceSquared(nc, x, y, z);
/* 4577 */       if (dc < this._dmin) {
/* 4578 */         this._dmin = dc;
/* 4579 */         this._nmin = nc;
/*      */       } 
/*      */     } 
/* 4582 */     if (ta != null && !isMarked(ta))
/* 4583 */       findNodeNaborNearest(x, y, z, node, ta); 
/* 4584 */     if (tb != null && !isMarked(tb))
/* 4585 */       findNodeNaborNearest(x, y, z, node, tb); 
/* 4586 */     if (tc != null && !isMarked(tc)) {
/* 4587 */       findNodeNaborNearest(x, y, z, node, tc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Node findNodeNearestPlane(double a, double b, double c, double d) {
/* 4596 */     this._nmin = this._nroot;
/* 4597 */     this._dmin = distanceToPlaneSquared(this._nmin, a, b, c, d);
/* 4598 */     for (Node n : this._sampledNodes) {
/* 4599 */       double dp = distanceToPlaneSquared(n, a, b, c, d);
/* 4600 */       if (dp < this._dmin) {
/* 4601 */         this._dmin = dp;
/* 4602 */         this._nmin = n;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4611 */     clearNodeMarks();
/*      */     
/*      */     while (true) {
/* 4614 */       clearTetMarks();
/* 4615 */       double dmin = this._dmin;
/* 4616 */       findNodeNaborNearestPlane(a, b, c, d, this._nmin, this._nmin._tet);
/* 4617 */       if (this._dmin >= dmin) {
/* 4618 */         return this._nmin;
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
/*      */   private void findNodeNaborNearestPlane(double a, double b, double c, double d, Node node, Tet tet) {
/* 4630 */     mark(tet);
/* 4631 */     Node n0 = tet._n0;
/* 4632 */     Node n1 = tet._n1;
/* 4633 */     Node n2 = tet._n2;
/* 4634 */     Node n3 = tet._n3;
/* 4635 */     Tet t0 = tet._t0;
/* 4636 */     Tet t1 = tet._t1;
/* 4637 */     Tet t2 = tet._t2;
/* 4638 */     Tet t3 = tet._t3;
/* 4639 */     if (node == n0) {
/* 4640 */       findNodeNaborNearestPlane(a, b, c, d, node, n1, n2, n3, t1, t2, t3);
/* 4641 */     } else if (node == n1) {
/* 4642 */       findNodeNaborNearestPlane(a, b, c, d, node, n3, n2, n0, t3, t2, t0);
/* 4643 */     } else if (node == n2) {
/* 4644 */       findNodeNaborNearestPlane(a, b, c, d, node, n3, n0, n1, t3, t0, t1);
/* 4645 */     } else if (node == n3) {
/* 4646 */       findNodeNaborNearestPlane(a, b, c, d, node, n1, n0, n2, t1, t0, t2);
/*      */     } else {
/* 4648 */       assert false : "node is referenced by tet";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void findNodeNaborNearestPlane(double a, double b, double c, double d, Node node, Node na, Node nb, Node nc, Tet ta, Tet tb, Tet tc) {
/* 4655 */     if (!isMarked(na)) {
/* 4656 */       mark(na);
/* 4657 */       double da = distanceToPlaneSquared(na, a, b, c, d);
/* 4658 */       if (da < this._dmin) {
/* 4659 */         this._dmin = da;
/* 4660 */         this._nmin = na;
/*      */       } 
/*      */     } 
/* 4663 */     if (!isMarked(nb)) {
/* 4664 */       mark(nb);
/* 4665 */       double db = distanceToPlaneSquared(nb, a, b, c, d);
/* 4666 */       if (db < this._dmin) {
/* 4667 */         this._dmin = db;
/* 4668 */         this._nmin = nb;
/*      */       } 
/*      */     } 
/* 4671 */     if (!isMarked(nc)) {
/* 4672 */       mark(nc);
/* 4673 */       double dc = distanceToPlaneSquared(nc, a, b, c, d);
/* 4674 */       if (dc < this._dmin) {
/* 4675 */         this._dmin = dc;
/* 4676 */         this._nmin = nc;
/*      */       } 
/*      */     } 
/* 4679 */     if (ta != null && !isMarked(ta))
/* 4680 */       findNodeNaborNearestPlane(a, b, c, d, node, ta); 
/* 4681 */     if (tb != null && !isMarked(tb))
/* 4682 */       findNodeNaborNearestPlane(a, b, c, d, node, tb); 
/* 4683 */     if (tc != null && !isMarked(tc)) {
/* 4684 */       findNodeNaborNearestPlane(a, b, c, d, node, tc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PointLocation locatePoint(double x, double y, double z) {
/* 4694 */     if (this._troot == null) {
/* 4695 */       if (this._nroot != null) {
/* 4696 */         Node node = this._nroot;
/*      */         do {
/* 4698 */           if (x == node.x() && y == node.y() && z == node.z())
/* 4699 */             return new PointLocation(node); 
/* 4700 */           node = node._next;
/* 4701 */         } while (node != this._nroot);
/*      */       } 
/* 4703 */       return new PointLocation(null, false);
/*      */     } 
/*      */ 
/*      */     
/* 4707 */     Node nmin = this._nroot;
/* 4708 */     double dmin = distanceSquared(nmin, x, y, z);
/* 4709 */     for (Node n : this._sampledNodes) {
/* 4710 */       double d = distanceSquared(n, x, y, z);
/* 4711 */       if (d < dmin) {
/* 4712 */         dmin = d;
/* 4713 */         nmin = n;
/*      */       } 
/*      */     } 
/* 4716 */     Tet tet = nmin._tet;
/* 4717 */     return locatePoint(tet, x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private PointLocation locatePoint(Tet tet, double x, double y, double z) {
/* 4727 */     this._troot = tet;
/*      */ 
/*      */     
/* 4730 */     Node n0 = tet._n0;
/* 4731 */     Node n1 = tet._n1;
/* 4732 */     Node n2 = tet._n2;
/* 4733 */     Node n3 = tet._n3;
/* 4734 */     double x0 = n0._x;
/* 4735 */     double y0 = n0._y;
/* 4736 */     double z0 = n0._z;
/* 4737 */     double x1 = n1._x;
/* 4738 */     double y1 = n1._y;
/* 4739 */     double z1 = n1._z;
/* 4740 */     double x2 = n2._x;
/* 4741 */     double y2 = n2._y;
/* 4742 */     double z2 = n2._z;
/* 4743 */     double x3 = n3._x;
/* 4744 */     double y3 = n3._y;
/* 4745 */     double z3 = n3._z;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4750 */     if (x == x0 && y == y0 && z == z0)
/* 4751 */       return new PointLocation(n0); 
/* 4752 */     if (x == x1 && y == y1 && z == z1)
/* 4753 */       return new PointLocation(n1); 
/* 4754 */     if (x == x2 && y == y2 && z == z2)
/* 4755 */       return new PointLocation(n2); 
/* 4756 */     if (x == x3 && y == y3 && z == z3) {
/* 4757 */       return new PointLocation(n3);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4767 */     double d0 = Geometry.leftOfPlane(x1, y1, z1, x2, y2, z2, x3, y3, z3, x, y, z);
/* 4768 */     if (d0 > 0.0D) {
/* 4769 */       Tet tetNabor = tet.tetNabor(n0);
/* 4770 */       if (tetNabor != null) {
/* 4771 */         return locatePoint(tetNabor, x, y, z);
/*      */       }
/* 4773 */       return new PointLocation(tet, false);
/*      */     } 
/*      */     
/* 4776 */     double d1 = Geometry.leftOfPlane(x3, y3, z3, x2, y2, z2, x0, y0, z0, x, y, z);
/* 4777 */     if (d1 > 0.0D) {
/* 4778 */       Tet tetNabor = tet.tetNabor(n1);
/* 4779 */       if (tetNabor != null) {
/* 4780 */         return locatePoint(tetNabor, x, y, z);
/*      */       }
/* 4782 */       return new PointLocation(tet, false);
/*      */     } 
/*      */     
/* 4785 */     double d2 = Geometry.leftOfPlane(x3, y3, z3, x0, y0, z0, x1, y1, z1, x, y, z);
/* 4786 */     if (d2 > 0.0D) {
/* 4787 */       Tet tetNabor = tet.tetNabor(n2);
/* 4788 */       if (tetNabor != null) {
/* 4789 */         return locatePoint(tetNabor, x, y, z);
/*      */       }
/* 4791 */       return new PointLocation(tet, false);
/*      */     } 
/*      */     
/* 4794 */     double d3 = Geometry.leftOfPlane(x0, y0, z0, x2, y2, z2, x1, y1, z1, x, y, z);
/* 4795 */     if (d3 > 0.0D) {
/* 4796 */       Tet tetNabor = tet.tetNabor(n3);
/* 4797 */       if (tetNabor != null) {
/* 4798 */         return locatePoint(tetNabor, x, y, z);
/*      */       }
/* 4800 */       return new PointLocation(tet, false);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4805 */     if (d0 < 0.0D && d1 < 0.0D && d2 < 0.0D && d3 < 0.0D) {
/* 4806 */       return new PointLocation(tet);
/*      */     }
/*      */ 
/*      */     
/* 4810 */     if (d0 == 0.0D && d1 == 0.0D)
/* 4811 */       return new PointLocation(new Edge(tet, n2, n3)); 
/* 4812 */     if (d0 == 0.0D && d2 == 0.0D)
/* 4813 */       return new PointLocation(new Edge(tet, n3, n1)); 
/* 4814 */     if (d0 == 0.0D && d3 == 0.0D)
/* 4815 */       return new PointLocation(new Edge(tet, n1, n2)); 
/* 4816 */     if (d1 == 0.0D && d2 == 0.0D)
/* 4817 */       return new PointLocation(new Edge(tet, n0, n3)); 
/* 4818 */     if (d1 == 0.0D && d3 == 0.0D)
/* 4819 */       return new PointLocation(new Edge(tet, n2, n0)); 
/* 4820 */     if (d2 == 0.0D && d3 == 0.0D)
/* 4821 */       return new PointLocation(new Edge(tet, n0, n1)); 
/* 4822 */     if (d0 == 0.0D)
/* 4823 */       return new PointLocation(new Face(tet, n0)); 
/* 4824 */     if (d1 == 0.0D)
/* 4825 */       return new PointLocation(new Face(tet, n1)); 
/* 4826 */     if (d2 == 0.0D)
/* 4827 */       return new PointLocation(new Face(tet, n2)); 
/* 4828 */     if (d3 == 0.0D) {
/* 4829 */       return new PointLocation(new Face(tet, n3));
/*      */     }
/*      */ 
/*      */     
/* 4833 */     assert false : "successfully located the point";
/* 4834 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDelaunayFacesInside(Node node, Tet tet) {
/* 4843 */     if (tet != null && !isMarked(tet)) {
/* 4844 */       mark(tet);
/* 4845 */       Node n0 = tet._n0;
/* 4846 */       Node n1 = tet._n1;
/* 4847 */       Node n2 = tet._n2;
/* 4848 */       Node n3 = tet._n3;
/* 4849 */       if (inSphere(n0, n1, n2, n3, node)) {
/* 4850 */         killTet(tet);
/* 4851 */         Tet t0 = tet._t0;
/* 4852 */         Tet t1 = tet._t1;
/* 4853 */         Tet t2 = tet._t2;
/* 4854 */         Tet t3 = tet._t3;
/* 4855 */         this._faceSet.addMate(tet, n0);
/* 4856 */         this._faceSet.addMate(tet, n1);
/* 4857 */         this._faceSet.addMate(tet, n2);
/* 4858 */         this._faceSet.addMate(tet, n3);
/* 4859 */         getDelaunayFacesInside(node, t0);
/* 4860 */         getDelaunayFacesInside(node, t1);
/* 4861 */         getDelaunayFacesInside(node, t2);
/* 4862 */         getDelaunayFacesInside(node, t3);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getDelaunayFacesOutside(Node node, Tet tet) {
/* 4873 */     if (tet != null && !isMarked(tet)) {
/* 4874 */       mark(tet);
/* 4875 */       Node n0 = tet._n0;
/* 4876 */       Node n1 = tet._n1;
/* 4877 */       Node n2 = tet._n2;
/* 4878 */       Node n3 = tet._n3;
/* 4879 */       Tet t0 = tet._t0;
/* 4880 */       Tet t1 = tet._t1;
/* 4881 */       Tet t2 = tet._t2;
/* 4882 */       Tet t3 = tet._t3;
/* 4883 */       if (t0 == null && leftOfPlane(n1, n2, n3, node)) {
/* 4884 */         this._faceSet.add(tet, n0);
/* 4885 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n1, n0));
/* 4886 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n2, n0));
/* 4887 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n3, n0));
/*      */       } 
/* 4889 */       if (t1 == null && leftOfPlane(n3, n2, n0, node)) {
/* 4890 */         this._faceSet.add(tet, n1);
/* 4891 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n3, n1));
/* 4892 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n2, n1));
/* 4893 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n0, n1));
/*      */       } 
/* 4895 */       if (t2 == null && leftOfPlane(n3, n0, n1, node)) {
/* 4896 */         this._faceSet.add(tet, n2);
/* 4897 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n3, n2));
/* 4898 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n0, n2));
/* 4899 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n1, n2));
/*      */       } 
/* 4901 */       if (t3 == null && leftOfPlane(n1, n0, n2, node)) {
/* 4902 */         this._faceSet.add(tet, n3);
/* 4903 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n1, n3));
/* 4904 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n0, n3));
/* 4905 */         getDelaunayFacesOutside(node, getNextTetOnHull(tet, n2, n3));
/*      */       } 
/* 4907 */       if (inSphere(n0, n1, n2, n3, node)) {
/* 4908 */         killTet(tet);
/* 4909 */         this._faceSet.addMate(tet, n0);
/* 4910 */         this._faceSet.addMate(tet, n1);
/* 4911 */         this._faceSet.addMate(tet, n2);
/* 4912 */         this._faceSet.addMate(tet, n3);
/* 4913 */         getDelaunayFacesOutside(node, t0);
/* 4914 */         getDelaunayFacesOutside(node, t1);
/* 4915 */         getDelaunayFacesOutside(node, t2);
/* 4916 */         getDelaunayFacesOutside(node, t3);
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
/*      */   private void getDelaunayFacesOpposite(Node node, Tet tet) {
/* 4929 */     if (tet != null && !isMarked(tet)) {
/* 4930 */       mark(tet);
/* 4931 */       killTet(tet);
/* 4932 */       Node n0 = tet._n0;
/* 4933 */       Node n1 = tet._n1;
/* 4934 */       Node n2 = tet._n2;
/* 4935 */       Node n3 = tet._n3;
/* 4936 */       Tet t0 = tet._t0;
/* 4937 */       Tet t1 = tet._t1;
/* 4938 */       Tet t2 = tet._t2;
/* 4939 */       Tet t3 = tet._t3;
/* 4940 */       if (node == n0) {
/* 4941 */         this._faceSet.addMate(tet, n0);
/* 4942 */         getDelaunayFacesOpposite(node, n1, n2, n3, t1, t2, t3);
/* 4943 */       } else if (node == n1) {
/* 4944 */         this._faceSet.addMate(tet, n1);
/* 4945 */         getDelaunayFacesOpposite(node, n3, n2, n0, t3, t2, t0);
/* 4946 */       } else if (node == n2) {
/* 4947 */         this._faceSet.addMate(tet, n2);
/* 4948 */         getDelaunayFacesOpposite(node, n3, n0, n1, t3, t0, t1);
/* 4949 */       } else if (node == n3) {
/* 4950 */         this._faceSet.addMate(tet, n3);
/* 4951 */         getDelaunayFacesOpposite(node, n1, n0, n2, t1, t0, t2);
/*      */       } else {
/* 4953 */         assert false : "node is referenced by tet";
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void getDelaunayFacesOpposite(Node node, Node na, Node nb, Node nc, Tet ta, Tet tb, Tet tc) {
/* 4960 */     if (!isMarked(na)) {
/* 4961 */       mark(na);
/* 4962 */       this._nodeList.add(na);
/*      */     } 
/* 4964 */     if (!isMarked(nb)) {
/* 4965 */       mark(nb);
/* 4966 */       this._nodeList.add(nb);
/*      */     } 
/* 4968 */     if (!isMarked(nc)) {
/* 4969 */       mark(nc);
/* 4970 */       this._nodeList.add(nc);
/*      */     } 
/* 4972 */     getDelaunayFacesOpposite(node, ta);
/* 4973 */     getDelaunayFacesOpposite(node, tb);
/* 4974 */     getDelaunayFacesOpposite(node, tc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Tet getNextTetOnHull(Tet tet, Node node, Node nodeOther) {
/* 4984 */     for (Tet tnext = tet.tetNabor(node); tnext != null; tnext = tet.tetNabor(node)) {
/* 4985 */       node = nodeOther;
/* 4986 */       nodeOther = tet.nodeNabor(tnext);
/* 4987 */       tet = tnext;
/*      */     } 
/* 4989 */     return tet;
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
/*      */   public Node findNodeNearestSlow(float x, float y, float z) {
/* 5005 */     clearTetMarks();
/* 5006 */     clearNodeMarks();
/*      */ 
/*      */     
/* 5009 */     this._dmin = Double.MAX_VALUE;
/* 5010 */     this._nmin = null;
/* 5011 */     if (this._troot == null) {
/* 5012 */       if (this._nroot != null) {
/* 5013 */         Node node = this._nroot;
/*      */         do {
/* 5015 */           updateNodeNearest(x, y, z, node);
/* 5016 */           node = node._next;
/* 5017 */         } while (node != this._nroot);
/*      */       } 
/* 5019 */       assert this._nmin != null;
/* 5020 */       return this._nmin;
/*      */     } 
/*      */ 
/*      */     
/* 5024 */     PointLocation pl = locatePoint(x, y, z);
/*      */ 
/*      */     
/* 5027 */     if (pl.isOnNode()) {
/* 5028 */       updateNodeNearest(x, y, z, pl.node());
/* 5029 */       return this._nmin;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 5034 */     if (pl.isInside()) {
/* 5035 */       findNodeNearestInside(x, y, z, pl.tet());
/*      */     } else {
/* 5037 */       findNodeNearestOutside(x, y, z, pl.tet());
/*      */     } 
/* 5039 */     return this._nmin;
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
/*      */   private void findNodeNearestInside(double x, double y, double z, Tet tet) {
/* 5051 */     if (tet != null && !isMarked(tet)) {
/* 5052 */       mark(tet);
/* 5053 */       Node n0 = tet._n0;
/* 5054 */       Node n1 = tet._n1;
/* 5055 */       Node n2 = tet._n2;
/* 5056 */       Node n3 = tet._n3;
/* 5057 */       updateNodeNearest(x, y, z, n0);
/* 5058 */       updateNodeNearest(x, y, z, n1);
/* 5059 */       updateNodeNearest(x, y, z, n2);
/* 5060 */       updateNodeNearest(x, y, z, n3);
/* 5061 */       if (inSphere(n0, n1, n2, n3, x, y, z)) {
/* 5062 */         Tet t0 = tet._t0;
/* 5063 */         Tet t1 = tet._t1;
/* 5064 */         Tet t2 = tet._t2;
/* 5065 */         Tet t3 = tet._t3;
/* 5066 */         findNodeNearestInside(x, y, z, t0);
/* 5067 */         findNodeNearestInside(x, y, z, t1);
/* 5068 */         findNodeNearestInside(x, y, z, t2);
/* 5069 */         findNodeNearestInside(x, y, z, t3);
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
/*      */   private void findNodeNearestOutside(double x, double y, double z, Tet tet) {
/* 5082 */     if (tet != null && !isMarked(tet)) {
/* 5083 */       mark(tet);
/* 5084 */       Node n0 = tet._n0;
/* 5085 */       Node n1 = tet._n1;
/* 5086 */       Node n2 = tet._n2;
/* 5087 */       Node n3 = tet._n3;
/* 5088 */       updateNodeNearest(x, y, z, n0);
/* 5089 */       updateNodeNearest(x, y, z, n1);
/* 5090 */       updateNodeNearest(x, y, z, n2);
/* 5091 */       updateNodeNearest(x, y, z, n3);
/* 5092 */       Tet t0 = tet._t0;
/* 5093 */       Tet t1 = tet._t1;
/* 5094 */       Tet t2 = tet._t2;
/* 5095 */       Tet t3 = tet._t3;
/* 5096 */       if (t0 == null && leftOfPlane(n1, n2, n3, x, y, z)) {
/* 5097 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n1, n0));
/* 5098 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n2, n0));
/* 5099 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n3, n0));
/*      */       } 
/* 5101 */       if (t1 == null && leftOfPlane(n3, n2, n0, x, y, z)) {
/* 5102 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n3, n1));
/* 5103 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n2, n1));
/* 5104 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n0, n1));
/*      */       } 
/* 5106 */       if (t2 == null && leftOfPlane(n3, n0, n1, x, y, z)) {
/* 5107 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n3, n2));
/* 5108 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n0, n2));
/* 5109 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n1, n2));
/*      */       } 
/* 5111 */       if (t3 == null && leftOfPlane(n1, n0, n2, x, y, z)) {
/* 5112 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n1, n3));
/* 5113 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n0, n3));
/* 5114 */         findNodeNearestOutside(x, y, z, getNextTetOnHull(tet, n2, n3));
/*      */       } 
/* 5116 */       if (inSphere(n0, n1, n2, n3, x, y, z)) {
/* 5117 */         findNodeNearestOutside(x, y, z, t0);
/* 5118 */         findNodeNearestOutside(x, y, z, t1);
/* 5119 */         findNodeNearestOutside(x, y, z, t2);
/* 5120 */         findNodeNearestOutside(x, y, z, t3);
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
/*      */   private void updateNodeNearest(double x, double y, double z, Node n) {
/* 5133 */     if (!isMarked(n)) {
/* 5134 */       mark(n);
/* 5135 */       double d = distanceSquared(n, x, y, z);
/* 5136 */       if (d < this._dmin) {
/* 5137 */         this._dmin = d;
/* 5138 */         this._nmin = n;
/* 5139 */         this._nroot = n;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void linkTets(Tet tet, Node node, Tet tetNabor, Node nodeNabor) {
/* 5148 */     if (tet != null) {
/* 5149 */       if (node == tet._n0) {
/* 5150 */         tet._t0 = tetNabor;
/* 5151 */       } else if (node == tet._n1) {
/* 5152 */         tet._t1 = tetNabor;
/* 5153 */       } else if (node == tet._n2) {
/* 5154 */         tet._t2 = tetNabor;
/* 5155 */       } else if (node == tet._n3) {
/* 5156 */         tet._t3 = tetNabor;
/*      */       } else {
/* 5158 */         assert false : "node referenced by tet";
/*      */       } 
/*      */     }
/* 5161 */     if (tetNabor != null) {
/* 5162 */       if (nodeNabor == tetNabor._n0) {
/* 5163 */         tetNabor._t0 = tet;
/* 5164 */       } else if (nodeNabor == tetNabor._n1) {
/* 5165 */         tetNabor._t1 = tet;
/* 5166 */       } else if (nodeNabor == tetNabor._n2) {
/* 5167 */         tetNabor._t2 = tet;
/* 5168 */       } else if (nodeNabor == tetNabor._n3) {
/* 5169 */         tetNabor._t3 = tet;
/*      */       } else {
/* 5171 */         assert false : "nodeNabor referenced by tetNabor";
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void markAllTets(Tet tet) {
/* 5180 */     tet._mark = this._tetMarkRed;
/* 5181 */     Tet t0 = tet._t0;
/* 5182 */     if (t0 != null && t0._mark != this._tetMarkRed)
/* 5183 */       markAllTets(t0); 
/* 5184 */     Tet t1 = tet._t1;
/* 5185 */     if (t1 != null && t1._mark != this._tetMarkRed)
/* 5186 */       markAllTets(t1); 
/* 5187 */     Tet t2 = tet._t2;
/* 5188 */     if (t2 != null && t2._mark != this._tetMarkRed)
/* 5189 */       markAllTets(t2); 
/* 5190 */     Tet t3 = tet._t3;
/* 5191 */     if (t3 != null && t3._mark != this._tetMarkRed) {
/* 5192 */       markAllTets(t3);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void zeroTetMarks(Tet tet) {
/* 5200 */     tet._mark = 0;
/* 5201 */     Tet t0 = tet._t0;
/* 5202 */     if (t0 != null && t0._mark != 0)
/* 5203 */       zeroTetMarks(t0); 
/* 5204 */     Tet t1 = tet._t1;
/* 5205 */     if (t1 != null && t1._mark != 0)
/* 5206 */       zeroTetMarks(t1); 
/* 5207 */     Tet t2 = tet._t2;
/* 5208 */     if (t2 != null && t2._mark != 0)
/* 5209 */       zeroTetMarks(t2); 
/* 5210 */     Tet t3 = tet._t3;
/* 5211 */     if (t3 != null && t3._mark != 0)
/* 5212 */       zeroTetMarks(t3); 
/*      */   }
/*      */   
/*      */   private Face getFaceOnHull(Tet tet) {
/* 5216 */     ArrayList<Tet> stack = new ArrayList<>(128);
/* 5217 */     stack.add(tet);
/* 5218 */     while (!stack.isEmpty()) {
/* 5219 */       Tet t = stack.remove(stack.size() - 1);
/* 5220 */       mark(t);
/* 5221 */       if (t._t0 == null)
/* 5222 */         return new Face(t, t._n0); 
/* 5223 */       if (t._t1 == null)
/* 5224 */         return new Face(t, t._n1); 
/* 5225 */       if (t._t2 == null)
/* 5226 */         return new Face(t, t._n2); 
/* 5227 */       if (t._t3 == null)
/* 5228 */         return new Face(t, t._n3); 
/* 5229 */       if (!isMarked(t._t0))
/* 5230 */         stack.add(t._t0); 
/* 5231 */       if (!isMarked(t._t1))
/* 5232 */         stack.add(t._t1); 
/* 5233 */       if (!isMarked(t._t2))
/* 5234 */         stack.add(t._t2); 
/* 5235 */       if (!isMarked(t._t3))
/* 5236 */         stack.add(t._t3); 
/*      */     } 
/* 5238 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getFacesOnHull(Face face, HashSet<Face> faces) {
/* 5246 */     if (!faces.contains(face)) {
/* 5247 */       faces.add(face);
/* 5248 */       getFacesOnHull(getNextFaceOnHull(face.nodeA(), face), faces);
/* 5249 */       getFacesOnHull(getNextFaceOnHull(face.nodeB(), face), faces);
/* 5250 */       getFacesOnHull(getNextFaceOnHull(face.nodeC(), face), faces);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Face getNextFaceOnHull(Node node, Face face) {
/* 5259 */     Tet tet = face.tetLeft();
/* 5260 */     Node next = face.nodeLeft();
/* 5261 */     for (Tet tnext = tet.tetNabor(node); tnext != null; tnext = tet.tetNabor(node)) {
/* 5262 */       node = next;
/* 5263 */       next = tet.nodeNabor(tnext);
/* 5264 */       tet = tnext;
/*      */     } 
/* 5266 */     return new Face(tet, node);
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
/*      */   private static Edge edgeOfTet(Tet tet, Node na, Node nb) {
/* 5291 */     Node n0 = tet._n0;
/* 5292 */     Node n1 = tet._n1;
/* 5293 */     Node n2 = tet._n2;
/* 5294 */     Node n3 = tet._n3;
/* 5295 */     if (na == n0) {
/* 5296 */       if (nb == n1)
/* 5297 */         return new Edge(tet, n0, n1); 
/* 5298 */       if (nb == n2)
/* 5299 */         return new Edge(tet, n0, n2); 
/* 5300 */       if (nb == n3) {
/* 5301 */         return new Edge(tet, n0, n3);
/*      */       }
/* 5303 */       return null;
/*      */     } 
/* 5305 */     if (na == n1) {
/* 5306 */       if (nb == n0)
/* 5307 */         return new Edge(tet, n0, n1); 
/* 5308 */       if (nb == n2)
/* 5309 */         return new Edge(tet, n1, n2); 
/* 5310 */       if (nb == n3) {
/* 5311 */         return new Edge(tet, n1, n3);
/*      */       }
/* 5313 */       return null;
/*      */     } 
/* 5315 */     if (na == n2) {
/* 5316 */       if (nb == n0)
/* 5317 */         return new Edge(tet, n0, n2); 
/* 5318 */       if (nb == n1)
/* 5319 */         return new Edge(tet, n1, n2); 
/* 5320 */       if (nb == n3) {
/* 5321 */         return new Edge(tet, n2, n3);
/*      */       }
/* 5323 */       return null;
/*      */     } 
/* 5325 */     if (na == n3) {
/* 5326 */       if (nb == n0)
/* 5327 */         return new Edge(tet, n0, n3); 
/* 5328 */       if (nb == n1)
/* 5329 */         return new Edge(tet, n1, n3); 
/* 5330 */       if (nb == n2) {
/* 5331 */         return new Edge(tet, n2, n3);
/*      */       }
/* 5333 */       return null;
/*      */     } 
/*      */     
/* 5336 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Face faceOfTet(Tet tet, Node na, Node nb, Node nc) {
/* 5345 */     Node n0 = tet._n0;
/* 5346 */     Node n1 = tet._n1;
/* 5347 */     Node n2 = tet._n2;
/* 5348 */     Node n3 = tet._n3;
/* 5349 */     if (na == n0) {
/* 5350 */       if (nb == n1) {
/* 5351 */         if (nc == n2)
/* 5352 */           return new Face(tet, n3); 
/* 5353 */         if (nc == n3) {
/* 5354 */           return new Face(tet, n2);
/*      */         }
/* 5356 */         return null;
/*      */       } 
/* 5358 */       if (nb == n2) {
/* 5359 */         if (nc == n1)
/* 5360 */           return new Face(tet, n3); 
/* 5361 */         if (nc == n3) {
/* 5362 */           return new Face(tet, n1);
/*      */         }
/* 5364 */         return null;
/*      */       } 
/* 5366 */       if (nb == n3) {
/* 5367 */         if (nc == n1)
/* 5368 */           return new Face(tet, n2); 
/* 5369 */         if (nc == n2) {
/* 5370 */           return new Face(tet, n1);
/*      */         }
/* 5372 */         return null;
/*      */       } 
/*      */       
/* 5375 */       return null;
/*      */     } 
/* 5377 */     if (na == n1) {
/* 5378 */       if (nb == n0) {
/* 5379 */         if (nc == n2)
/* 5380 */           return new Face(tet, n3); 
/* 5381 */         if (nc == n3) {
/* 5382 */           return new Face(tet, n2);
/*      */         }
/* 5384 */         return null;
/*      */       } 
/* 5386 */       if (nb == n2) {
/* 5387 */         if (nc == n0)
/* 5388 */           return new Face(tet, n3); 
/* 5389 */         if (nc == n3) {
/* 5390 */           return new Face(tet, n0);
/*      */         }
/* 5392 */         return null;
/*      */       } 
/* 5394 */       if (nb == n3) {
/* 5395 */         if (nc == n0)
/* 5396 */           return new Face(tet, n2); 
/* 5397 */         if (nc == n2) {
/* 5398 */           return new Face(tet, n0);
/*      */         }
/* 5400 */         return null;
/*      */       } 
/*      */       
/* 5403 */       return null;
/*      */     } 
/* 5405 */     if (na == n2) {
/* 5406 */       if (nb == n0) {
/* 5407 */         if (nc == n1)
/* 5408 */           return new Face(tet, n3); 
/* 5409 */         if (nc == n3) {
/* 5410 */           return new Face(tet, n1);
/*      */         }
/* 5412 */         return null;
/*      */       } 
/* 5414 */       if (nb == n1) {
/* 5415 */         if (nc == n0)
/* 5416 */           return new Face(tet, n3); 
/* 5417 */         if (nc == n3) {
/* 5418 */           return new Face(tet, n0);
/*      */         }
/* 5420 */         return null;
/*      */       } 
/* 5422 */       if (nb == n3) {
/* 5423 */         if (nc == n0)
/* 5424 */           return new Face(tet, n1); 
/* 5425 */         if (nc == n1) {
/* 5426 */           return new Face(tet, n0);
/*      */         }
/* 5428 */         return null;
/*      */       } 
/*      */       
/* 5431 */       return null;
/*      */     } 
/* 5433 */     if (na == n3) {
/* 5434 */       if (nb == n0) {
/* 5435 */         if (nc == n1)
/* 5436 */           return new Face(tet, n2); 
/* 5437 */         if (nc == n2) {
/* 5438 */           return new Face(tet, n1);
/*      */         }
/* 5440 */         return null;
/*      */       } 
/* 5442 */       if (nb == n1) {
/* 5443 */         if (nc == n0)
/* 5444 */           return new Face(tet, n2); 
/* 5445 */         if (nc == n2) {
/* 5446 */           return new Face(tet, n0);
/*      */         }
/* 5448 */         return null;
/*      */       } 
/* 5450 */       if (nb == n2) {
/* 5451 */         if (nc == n0)
/* 5452 */           return new Face(tet, n1); 
/* 5453 */         if (nc == n1) {
/* 5454 */           return new Face(tet, n0);
/*      */         }
/* 5456 */         return null;
/*      */       } 
/*      */       
/* 5459 */       return null;
/*      */     } 
/*      */     
/* 5462 */     return null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean nodesInOrder(Tet tet, Node na, Node nb, Node nc, Node nd) {
/* 5602 */     Node n0 = tet._n0;
/* 5603 */     Node n1 = tet._n1;
/* 5604 */     Node n2 = tet._n2;
/* 5605 */     Node n3 = tet._n3;
/* 5606 */     if (na == n0) {
/* 5607 */       return ((nb == n1 && nc == n2 && nd == n3) || (nb == n2 && nc == n3 && nd == n1) || (nb == n3 && nc == n1 && nd == n2));
/*      */     }
/*      */     
/* 5610 */     if (na == n1) {
/* 5611 */       return ((nb == n2 && nc == n0 && nd == n3) || (nb == n3 && nc == n2 && nd == n0) || (nb == n0 && nc == n3 && nd == n2));
/*      */     }
/*      */     
/* 5614 */     if (na == n2) {
/* 5615 */       return ((nb == n3 && nc == n0 && nd == n1) || (nb == n0 && nc == n1 && nd == n3) || (nb == n1 && nc == n3 && nd == n0));
/*      */     }
/*      */     
/* 5618 */     if (na == n3) {
/* 5619 */       return ((nb == n0 && nc == n2 && nd == n1) || (nb == n1 && nc == n0 && nd == n2) || (nb == n2 && nc == n1 && nd == n0));
/*      */     }
/*      */ 
/*      */     
/* 5623 */     assert false : "tet references na";
/* 5624 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Node otherNode(Tet tet, Node na, Node nb, Node nc) {
/* 5634 */     Node n0 = tet._n0;
/* 5635 */     Node n1 = tet._n1;
/* 5636 */     Node n2 = tet._n2;
/* 5637 */     Node n3 = tet._n3;
/* 5638 */     if (na == n0) {
/* 5639 */       if (nb == n1) {
/* 5640 */         if (nc == n2)
/* 5641 */           return n3; 
/* 5642 */         if (nc == n3) {
/* 5643 */           return n2;
/*      */         }
/* 5645 */         return null;
/*      */       } 
/* 5647 */       if (nb == n2) {
/* 5648 */         if (nc == n1)
/* 5649 */           return n3; 
/* 5650 */         if (nc == n3) {
/* 5651 */           return n1;
/*      */         }
/* 5653 */         return null;
/*      */       } 
/* 5655 */       if (nb == n3) {
/* 5656 */         if (nc == n1)
/* 5657 */           return n2; 
/* 5658 */         if (nc == n2) {
/* 5659 */           return n1;
/*      */         }
/* 5661 */         return null;
/*      */       } 
/*      */       
/* 5664 */       return null;
/*      */     } 
/* 5666 */     if (na == n1) {
/* 5667 */       if (nb == n0) {
/* 5668 */         if (nc == n2)
/* 5669 */           return n3; 
/* 5670 */         if (nc == n3) {
/* 5671 */           return n2;
/*      */         }
/* 5673 */         return null;
/*      */       } 
/* 5675 */       if (nb == n2) {
/* 5676 */         if (nc == n0)
/* 5677 */           return n3; 
/* 5678 */         if (nc == n3) {
/* 5679 */           return n0;
/*      */         }
/* 5681 */         return null;
/*      */       } 
/* 5683 */       if (nb == n3) {
/* 5684 */         if (nc == n0)
/* 5685 */           return n2; 
/* 5686 */         if (nc == n2) {
/* 5687 */           return n0;
/*      */         }
/* 5689 */         return null;
/*      */       } 
/*      */       
/* 5692 */       return null;
/*      */     } 
/* 5694 */     if (na == n2) {
/* 5695 */       if (nb == n0) {
/* 5696 */         if (nc == n1)
/* 5697 */           return n3; 
/* 5698 */         if (nc == n3) {
/* 5699 */           return n1;
/*      */         }
/* 5701 */         return null;
/*      */       } 
/* 5703 */       if (nb == n1) {
/* 5704 */         if (nc == n0)
/* 5705 */           return n3; 
/* 5706 */         if (nc == n3) {
/* 5707 */           return n0;
/*      */         }
/* 5709 */         return null;
/*      */       } 
/* 5711 */       if (nb == n3) {
/* 5712 */         if (nc == n0)
/* 5713 */           return n1; 
/* 5714 */         if (nc == n1) {
/* 5715 */           return n0;
/*      */         }
/* 5717 */         return null;
/*      */       } 
/*      */       
/* 5720 */       return null;
/*      */     } 
/* 5722 */     if (na == n3) {
/* 5723 */       if (nb == n0) {
/* 5724 */         if (nc == n1)
/* 5725 */           return n2; 
/* 5726 */         if (nc == n2) {
/* 5727 */           return n1;
/*      */         }
/* 5729 */         return null;
/*      */       } 
/* 5731 */       if (nb == n1) {
/* 5732 */         if (nc == n0)
/* 5733 */           return n2; 
/* 5734 */         if (nc == n2) {
/* 5735 */           return n0;
/*      */         }
/* 5737 */         return null;
/*      */       } 
/* 5739 */       if (nb == n2) {
/* 5740 */         if (nc == n0)
/* 5741 */           return n1; 
/* 5742 */         if (nc == n1) {
/* 5743 */           return n0;
/*      */         }
/* 5745 */         return null;
/*      */       } 
/*      */       
/* 5748 */       return null;
/*      */     } 
/*      */     
/* 5751 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void markTetInnerOrOuter(Tet tet) {
/* 5760 */     assert this._xminOuter < this._xmaxOuter : "outer box is valid";
/* 5761 */     assert this._yminOuter < this._ymaxOuter : "outer box is valid";
/* 5762 */     assert this._zminOuter < this._zmaxOuter : "outer box is valid";
/* 5763 */     double[] po = { 0.0D, 0.0D, 0.0D };
/* 5764 */     double s = tet.centerSphere(po);
/* 5765 */     double r = MathPlus.sqrt(s);
/* 5766 */     double xo = po[0];
/* 5767 */     double yo = po[1];
/* 5768 */     double zo = po[2];
/* 5769 */     if (xo - r >= this._xminOuter && yo - r >= this._yminOuter && zo - r >= this._zminOuter && xo + r <= this._xmaxOuter && yo + r <= this._ymaxOuter && zo + r <= this._zmaxOuter) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5775 */       tet.setInner();
/* 5776 */       tet.clearOuter();
/*      */     } else {
/* 5778 */       tet.setOuter();
/* 5779 */       tet.clearInner();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void fireNodeWillBeAdded(Node node) {
/* 5784 */     this._version++;
/* 5785 */     if (this._nnodeListeners > 0) {
/* 5786 */       Object[] list = this._listeners.getListenerList();
/* 5787 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5788 */         if (list[i] == NodeListener.class)
/* 5789 */           ((NodeListener)list[i + 1]).nodeWillBeAdded(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeAdded(Node node) {
/* 5794 */     this._version++;
/* 5795 */     if (this._nnodeListeners > 0) {
/* 5796 */       Object[] list = this._listeners.getListenerList();
/* 5797 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5798 */         if (list[i] == NodeListener.class)
/* 5799 */           ((NodeListener)list[i + 1]).nodeAdded(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeWillBeRemoved(Node node) {
/* 5804 */     this._version++;
/* 5805 */     if (this._nnodeListeners > 0) {
/* 5806 */       Object[] list = this._listeners.getListenerList();
/* 5807 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5808 */         if (list[i] == NodeListener.class)
/* 5809 */           ((NodeListener)list[i + 1]).nodeWillBeRemoved(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireNodeRemoved(Node node) {
/* 5814 */     this._version++;
/* 5815 */     if (this._nnodeListeners > 0) {
/* 5816 */       Object[] list = this._listeners.getListenerList();
/* 5817 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5818 */         if (list[i] == NodeListener.class)
/* 5819 */           ((NodeListener)list[i + 1]).nodeRemoved(this, node); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireTetAdded(Tet tet) {
/* 5824 */     this._version++;
/* 5825 */     if (this._ntetListeners > 0) {
/* 5826 */       Object[] list = this._listeners.getListenerList();
/* 5827 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5828 */         if (list[i] == TetListener.class)
/* 5829 */           ((TetListener)list[i + 1]).tetAdded(this, tet); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void fireTetRemoved(Tet tet) {
/* 5834 */     this._version++;
/* 5835 */     if (this._ntetListeners > 0) {
/* 5836 */       Object[] list = this._listeners.getListenerList();
/* 5837 */       for (int i = list.length - 2; i >= 0; i -= 2) {
/* 5838 */         if (list[i] == TetListener.class) {
/* 5839 */           ((TetListener)list[i + 1]).tetRemoved(this, tet);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void validate(Node node) {
/* 5847 */     Check.state((node == node._prev._next), "node==node._prev._next");
/* 5848 */     Check.state((node == node._next._prev), "node==node._next._prev");
/* 5849 */     Tet tet = node.tet();
/* 5850 */     if (this._troot != null) {
/* 5851 */       Check.state((tet != null), "tet!=null");
/* 5852 */       Check.state((node == tet.nodeA() || node == tet
/* 5853 */           .nodeB() || node == tet
/* 5854 */           .nodeC() || node == tet
/* 5855 */           .nodeD()), "node is one of tet nodes");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validate(Tet tet) {
/* 5864 */     Node na = tet.nodeA();
/* 5865 */     Node nb = tet.nodeB();
/* 5866 */     Node nc = tet.nodeC();
/* 5867 */     Node nd = tet.nodeD();
/* 5868 */     if (!leftOfPlane(na, nb, nc, nd)) {
/* 5869 */       trace("xa=" + na._x + " ya=" + na._y + " za=" + na._z);
/* 5870 */       trace("xb=" + nb._x + " yb=" + nb._y + " zb=" + nb._z);
/* 5871 */       trace("xc=" + nc._x + " yc=" + nc._y + " zc=" + nc._z);
/* 5872 */       trace("xd=" + nd._x + " yd=" + nd._y + " zd=" + nd._z);
/*      */     } 
/* 5874 */     Check.state(leftOfPlane(na, nb, nc, nd), "leftOfPlane(na,nb,nc,nd)");
/* 5875 */     validate(na);
/* 5876 */     validate(nb);
/* 5877 */     validate(nc);
/* 5878 */     validate(nd);
/* 5879 */     Tet ta = tet.tetA();
/* 5880 */     Tet tb = tet.tetB();
/* 5881 */     Tet tc = tet.tetC();
/* 5882 */     Tet td = tet.tetD();
/* 5883 */     if (ta != null)
/* 5884 */       Check.state((ta.tetNabor(tet.nodeNabor(ta)) == tet), "a nabor ok"); 
/* 5885 */     if (tb != null)
/* 5886 */       Check.state((tb.tetNabor(tet.nodeNabor(tb)) == tet), "b nabor ok"); 
/* 5887 */     if (tc != null)
/* 5888 */       Check.state((tc.tetNabor(tet.nodeNabor(tc)) == tet), "c nabor ok"); 
/* 5889 */     if (td != null) {
/* 5890 */       Check.state((td.tetNabor(tet.nodeNabor(td)) == tet), "d nabor ok");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void trace(String s) {}
/*      */ 
/*      */   
/*      */   private static class FaceSet
/*      */   {
/*      */     TetMesh.Node a;
/*      */     
/*      */     TetMesh.Node b;
/*      */     
/*      */     TetMesh.Node c;
/*      */     
/*      */     TetMesh.Node d;
/*      */     
/*      */     TetMesh.Tet abcd;
/*      */     
/*      */     private static final int MAX_SHIFT = 30;
/*      */     
/*      */     private static final int MAX_CAPACITY = 1073741824;
/*      */     
/*      */     private TetMesh.Node[] _a;
/*      */     
/*      */     private TetMesh.Node[] _b;
/*      */     
/*      */     private TetMesh.Node[] _c;
/*      */     
/*      */     private TetMesh.Node[] _d;
/*      */     
/*      */     private TetMesh.Tet[] _abcd;
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
/*      */     private int _mask;
/*      */     private int _index;
/*      */     
/*      */     FaceSet(int capacity, double factor) {
/* 5937 */       if (capacity > 1073741824) capacity = 1073741824; 
/* 5938 */       if (factor <= 0.0D) factor = 1.0E-4D; 
/* 5939 */       if (factor >= 1.0D) factor = 0.9999D; 
/* 5940 */       for (this._nmax = 2, this._shift = 30; this._nmax < capacity; this._nmax *= 2)
/* 5941 */         this._shift--; 
/* 5942 */       this._n = 0;
/* 5943 */       this._factor = factor;
/* 5944 */       this._mask = this._nmax - 1;
/* 5945 */       this._a = new TetMesh.Node[this._nmax];
/* 5946 */       this._b = new TetMesh.Node[this._nmax];
/* 5947 */       this._c = new TetMesh.Node[this._nmax];
/* 5948 */       this._d = new TetMesh.Node[this._nmax];
/* 5949 */       this._abcd = new TetMesh.Tet[this._nmax];
/* 5950 */       this._filled = new boolean[this._nmax];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 5957 */       this._n = 0;
/* 5958 */       for (int i = 0; i < this._nmax; i++) {
/* 5959 */         this._filled[i] = false;
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
/*      */     boolean add(TetMesh.Tet tet, TetMesh.Node node) {
/* 5971 */       if (node == tet._n0)
/* 5972 */         return add(tet._n1, tet._n3, tet._n2, node, tet); 
/* 5973 */       if (node == tet._n1)
/* 5974 */         return add(tet._n2, tet._n3, tet._n0, node, tet); 
/* 5975 */       if (node == tet._n2)
/* 5976 */         return add(tet._n3, tet._n1, tet._n0, node, tet); 
/* 5977 */       if (node == tet._n3) {
/* 5978 */         return add(tet._n0, tet._n1, tet._n2, node, tet);
/*      */       }
/* 5980 */       assert false : "node is referenced by tet";
/* 5981 */       return false;
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
/*      */     boolean addMate(TetMesh.Tet tet, TetMesh.Node node) {
/* 5995 */       TetMesh.Tet tetNabor = tet.tetNabor(node);
/* 5996 */       TetMesh.Node nodeNabor = (tetNabor != null) ? tet.nodeNabor(tetNabor) : null;
/* 5997 */       if (node == tet._n0)
/* 5998 */         return add(tet._n1, tet._n2, tet._n3, nodeNabor, tetNabor); 
/* 5999 */       if (node == tet._n1)
/* 6000 */         return add(tet._n3, tet._n2, tet._n0, nodeNabor, tetNabor); 
/* 6001 */       if (node == tet._n2)
/* 6002 */         return add(tet._n3, tet._n0, tet._n1, nodeNabor, tetNabor); 
/* 6003 */       if (node == tet._n3) {
/* 6004 */         return add(tet._n1, tet._n0, tet._n2, nodeNabor, tetNabor);
/*      */       }
/* 6006 */       assert false : "node is referenced by tet";
/* 6007 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean remove() {
/* 6017 */       if (this._n > 0) {
/* 6018 */         int start = this._index;
/* 6019 */         for (; this._index < this._nmax; this._index++) {
/* 6020 */           if (this._filled[this._index]) {
/* 6021 */             setCurrent();
/* 6022 */             remove(this._index);
/* 6023 */             return true;
/*      */           } 
/*      */         } 
/* 6026 */         for (this._index = 0; this._index < start; this._index++) {
/* 6027 */           if (this._filled[this._index]) {
/* 6028 */             setCurrent();
/* 6029 */             remove(this._index);
/* 6030 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/* 6034 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isEmpty() {
/* 6042 */       return (this._n > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean first() {
/* 6051 */       this._index = -1;
/* 6052 */       return next();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean next() {
/* 6061 */       this._index++; for (; this._index < this._nmax; this._index++) {
/* 6062 */         if (this._filled[this._index]) {
/* 6063 */           setCurrent();
/* 6064 */           return true;
/*      */         } 
/*      */       } 
/* 6067 */       return false;
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
/*      */     private int hash(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c) {
/* 6088 */       int key = a._hash ^ b._hash ^ c._hash;
/*      */ 
/*      */ 
/*      */       
/* 6092 */       return 1327217885 * key >> this._shift & this._mask;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int indexOfMate(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c) {
/* 6100 */       int i = hash(a, b, c);
/* 6101 */       while (this._filled[i]) {
/* 6102 */         TetMesh.Node ai = this._a[i];
/* 6103 */         TetMesh.Node bi = this._b[i];
/* 6104 */         TetMesh.Node ci = this._c[i];
/* 6105 */         if ((a == ai && b == ci && c == bi) || (a == bi && b == ai && c == ci) || (a == ci && b == bi && c == ai))
/*      */         {
/*      */           
/* 6108 */           return i; } 
/* 6109 */         i = i - 1 & this._mask;
/*      */       } 
/* 6111 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setCurrent() {
/* 6118 */       this.a = this._a[this._index];
/* 6119 */       this.b = this._b[this._index];
/* 6120 */       this.c = this._c[this._index];
/* 6121 */       this.d = this._d[this._index];
/* 6122 */       this.abcd = this._abcd[this._index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean add(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c, TetMesh.Node d, TetMesh.Tet abcd) {
/* 6132 */       this._index = indexOfMate(a, b, c);
/* 6133 */       if (this._filled[this._index]) {
/* 6134 */         setCurrent();
/* 6135 */         remove(this._index);
/* 6136 */         return false;
/*      */       } 
/* 6138 */       this._a[this._index] = a;
/* 6139 */       this._b[this._index] = b;
/* 6140 */       this._c[this._index] = c;
/* 6141 */       this._d[this._index] = d;
/* 6142 */       this._abcd[this._index] = abcd;
/* 6143 */       this._filled[this._index] = true;
/* 6144 */       this._n++;
/* 6145 */       if (this._n > this._nmax * this._factor && this._nmax < 1073741824)
/* 6146 */         doubleCapacity(); 
/* 6147 */       setCurrent();
/* 6148 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void remove(int i) {
/* 6157 */       this._n--; while (true) {
/*      */         int r;
/* 6159 */         this._filled[i] = false;
/* 6160 */         int j = i;
/*      */         
/*      */         do {
/* 6163 */           i = i - 1 & this._mask;
/* 6164 */           if (!this._filled[i])
/*      */             return; 
/* 6166 */           r = hash(this._a[i], this._b[i], this._c[i]);
/* 6167 */         } while ((i <= r && r < j) || (r < j && j < i) || (j < i && i <= r));
/* 6168 */         this._a[j] = this._a[i];
/* 6169 */         this._b[j] = this._b[i];
/* 6170 */         this._c[j] = this._c[i];
/* 6171 */         this._d[j] = this._d[i];
/* 6172 */         this._abcd[j] = this._abcd[i];
/* 6173 */         this._filled[j] = this._filled[i];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doubleCapacity() {
/* 6182 */       FaceSet set = new FaceSet(2 * this._nmax, this._factor);
/* 6183 */       if (this._n > 0)
/* 6184 */         for (int i = 0; i < this._nmax; i++) {
/* 6185 */           if (this._filled[i]) {
/* 6186 */             set.add(this._a[i], this._b[i], this._c[i], this._d[i], this._abcd[i]);
/*      */           }
/*      */         }  
/* 6189 */       this._a = set._a;
/* 6190 */       this._b = set._b;
/* 6191 */       this._c = set._c;
/* 6192 */       this._d = set._d;
/* 6193 */       this._abcd = set._abcd;
/* 6194 */       this._filled = set._filled;
/* 6195 */       this._nmax = set._nmax;
/* 6196 */       this._n = set._n;
/* 6197 */       this._factor = set._factor;
/* 6198 */       this._shift = set._shift;
/* 6199 */       this._mask = set._mask;
/* 6200 */       this._index = set._index;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class EdgeSet
/*      */   {
/*      */     TetMesh.Node a;
/*      */     
/*      */     TetMesh.Node b;
/*      */     TetMesh.Node c;
/*      */     TetMesh.Tet nabc;
/*      */     private static final int MAX_SHIFT = 30;
/*      */     private static final int MAX_CAPACITY = 1073741824;
/*      */     private TetMesh.Node[] _a;
/*      */     private TetMesh.Node[] _b;
/*      */     private TetMesh.Node[] _c;
/*      */     private TetMesh.Tet[] _nabc;
/*      */     private boolean[] _filled;
/*      */     private int _nmax;
/*      */     private int _n;
/*      */     private double _factor;
/*      */     private int _shift;
/*      */     private int _mask;
/*      */     private int _index;
/*      */     
/*      */     EdgeSet(int capacity, double factor) {
/* 6227 */       if (capacity > 1073741824) capacity = 1073741824; 
/* 6228 */       if (factor <= 0.0D) factor = 1.0E-4D; 
/* 6229 */       if (factor >= 1.0D) factor = 0.9999D; 
/* 6230 */       for (this._nmax = 2, this._shift = 30; this._nmax < capacity; this._nmax *= 2)
/* 6231 */         this._shift--; 
/* 6232 */       this._n = 0;
/* 6233 */       this._factor = factor;
/* 6234 */       this._mask = this._nmax - 1;
/* 6235 */       this._a = new TetMesh.Node[this._nmax];
/* 6236 */       this._b = new TetMesh.Node[this._nmax];
/* 6237 */       this._c = new TetMesh.Node[this._nmax];
/* 6238 */       this._nabc = new TetMesh.Tet[this._nmax];
/* 6239 */       this._filled = new boolean[this._nmax];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void clear() {
/* 6246 */       this._n = 0;
/* 6247 */       for (int i = 0; i < this._nmax; i++) {
/* 6248 */         this._filled[i] = false;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean add(TetMesh.Node a, TetMesh.Node b, TetMesh.Node c, TetMesh.Tet nabc) {
/* 6258 */       this._index = indexOfMate(a, b);
/* 6259 */       if (this._filled[this._index]) {
/* 6260 */         setCurrent();
/* 6261 */         remove(this._index);
/* 6262 */         return false;
/*      */       } 
/* 6264 */       this._a[this._index] = a;
/* 6265 */       this._b[this._index] = b;
/* 6266 */       this._c[this._index] = c;
/* 6267 */       this._nabc[this._index] = nabc;
/* 6268 */       this._filled[this._index] = true;
/* 6269 */       this._n++;
/* 6270 */       if (this._n > this._nmax * this._factor && this._nmax < 1073741824)
/* 6271 */         doubleCapacity(); 
/* 6272 */       setCurrent();
/* 6273 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     int size() {
/* 6278 */       return this._n;
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
/*      */     private int hash(TetMesh.Node a, TetMesh.Node b) {
/* 6299 */       int key = a._hash ^ b._hash;
/*      */ 
/*      */ 
/*      */       
/* 6303 */       return 1327217885 * key >> this._shift & this._mask;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int indexOfMate(TetMesh.Node a, TetMesh.Node b) {
/* 6311 */       int i = hash(a, b);
/* 6312 */       while (this._filled[i]) {
/* 6313 */         if (a == this._b[i] && b == this._a[i])
/* 6314 */           return i; 
/* 6315 */         i = i - 1 & this._mask;
/*      */       } 
/* 6317 */       return i;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setCurrent() {
/* 6324 */       this.a = this._a[this._index];
/* 6325 */       this.b = this._b[this._index];
/* 6326 */       this.c = this._c[this._index];
/* 6327 */       this.nabc = this._nabc[this._index];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void remove(int i) {
/* 6335 */       this._n--; while (true) {
/*      */         int r;
/* 6337 */         this._filled[i] = false;
/* 6338 */         int j = i;
/*      */         
/*      */         do {
/* 6341 */           i = i - 1 & this._mask;
/* 6342 */           if (!this._filled[i])
/*      */             return; 
/* 6344 */           r = hash(this._a[i], this._b[i]);
/* 6345 */         } while ((i <= r && r < j) || (r < j && j < i) || (j < i && i <= r));
/* 6346 */         this._a[j] = this._a[i];
/* 6347 */         this._b[j] = this._b[i];
/* 6348 */         this._c[j] = this._c[i];
/* 6349 */         this._nabc[j] = this._nabc[i];
/* 6350 */         this._filled[j] = this._filled[i];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void doubleCapacity() {
/* 6359 */       EdgeSet set = new EdgeSet(2 * this._nmax, this._factor);
/* 6360 */       if (this._n > 0)
/* 6361 */         for (int i = 0; i < this._nmax; i++) {
/* 6362 */           if (this._filled[i]) {
/* 6363 */             set.add(this._a[i], this._b[i], this._c[i], this._nabc[i]);
/*      */           }
/*      */         }  
/* 6366 */       this._a = set._a;
/* 6367 */       this._b = set._b;
/* 6368 */       this._c = set._c;
/* 6369 */       this._nabc = set._nabc;
/* 6370 */       this._filled = set._filled;
/* 6371 */       this._nmax = set._nmax;
/* 6372 */       this._n = set._n;
/* 6373 */       this._factor = set._factor;
/* 6374 */       this._shift = set._shift;
/* 6375 */       this._mask = set._mask;
/* 6376 */       this._index = set._index;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 6385 */     init();
/*      */ 
/*      */     
/* 6388 */     int format = in.readInt();
/* 6389 */     if (format == 1) {
/*      */ 
/*      */       
/* 6392 */       this._version = in.readLong();
/*      */ 
/*      */       
/* 6395 */       int nnode = this._nnode = in.readInt();
/* 6396 */       Node[] nodes = new Node[nnode];
/* 6397 */       for (int inode = 0; inode < nnode; inode++) {
/* 6398 */         Node node = nodes[inode] = (Node)in.readObject();
/* 6399 */         node._x = in.readDouble();
/* 6400 */         node._y = in.readDouble();
/* 6401 */         node._z = in.readDouble();
/* 6402 */         int nvalue = in.readInt();
/* 6403 */         node._values = new Object[nvalue];
/* 6404 */         for (int ivalue = 0; ivalue < nvalue; ivalue++) {
/* 6405 */           Object value = in.readObject();
/* 6406 */           node._values[ivalue] = value;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 6411 */       int ntet = this._ntet = in.readInt();
/* 6412 */       Tet[] tets = new Tet[ntet];
/* 6413 */       for (int j = 0; j < ntet; j++) {
/* 6414 */         Tet tet = tets[j] = (Tet)in.readObject();
/* 6415 */         tet._quality = -1.0D;
/*      */       } 
/*      */ 
/*      */       
/* 6419 */       this._nroot = (Node)in.readObject();
/* 6420 */       for (int i = 0; i < nnode; i++) {
/* 6421 */         Node node = nodes[i];
/* 6422 */         node._prev = (Node)in.readObject();
/* 6423 */         node._next = (Node)in.readObject();
/* 6424 */         node._tet = (Tet)in.readObject();
/*      */       } 
/*      */ 
/*      */       
/* 6428 */       this._troot = (Tet)in.readObject();
/* 6429 */       for (int itet = 0; itet < ntet; itet++) {
/* 6430 */         Tet tet = tets[itet];
/* 6431 */         tet._n0 = (Node)in.readObject();
/* 6432 */         tet._n1 = (Node)in.readObject();
/* 6433 */         tet._n2 = (Node)in.readObject();
/* 6434 */         tet._n3 = (Node)in.readObject();
/* 6435 */         tet._t0 = (Tet)in.readObject();
/* 6436 */         tet._t1 = (Tet)in.readObject();
/* 6437 */         tet._t2 = (Tet)in.readObject();
/* 6438 */         tet._t3 = (Tet)in.readObject();
/*      */       } 
/*      */ 
/*      */       
/* 6442 */       this._outerEnabled = in.readBoolean();
/* 6443 */       this._xminOuter = in.readDouble();
/* 6444 */       this._yminOuter = in.readDouble();
/* 6445 */       this._zminOuter = in.readDouble();
/* 6446 */       this._xmaxOuter = in.readDouble();
/* 6447 */       this._ymaxOuter = in.readDouble();
/* 6448 */       this._zmaxOuter = in.readDouble();
/*      */ 
/*      */       
/* 6451 */       this._nnodeValues = in.readInt();
/* 6452 */       this._lnodeValues = in.readInt();
/* 6453 */       this._nodePropertyMaps = (Map<String, NodePropertyMap>)in.readObject();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 6458 */       throw new InvalidClassException("invalid external format");
/*      */     } 
/*      */ 
/*      */     
/* 6462 */     sampleNodes();
/*      */ 
/*      */     
/*      */     try {
/* 6466 */       validate();
/* 6467 */     } catch (IllegalStateException ise) {
/* 6468 */       throw new IOException(ise.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream out) throws IOException {
/* 6477 */     out.writeInt(1);
/*      */ 
/*      */     
/* 6480 */     out.writeLong(this._version);
/*      */ 
/*      */     
/* 6483 */     int nnode = this._nnode;
/* 6484 */     out.writeInt(nnode);
/* 6485 */     Node[] nodes = new Node[nnode];
/* 6486 */     NodeIterator ni = getNodes();
/* 6487 */     for (int inode = 0; inode < nnode; inode++) {
/* 6488 */       Node node = nodes[inode] = ni.next();
/* 6489 */       out.writeObject(node);
/* 6490 */       out.writeDouble(node._x);
/* 6491 */       out.writeDouble(node._y);
/* 6492 */       out.writeDouble(node._z);
/* 6493 */       int nvalue = node._values.length;
/* 6494 */       out.writeInt(nvalue);
/* 6495 */       for (int ivalue = 0; ivalue < nvalue; ivalue++) {
/* 6496 */         Object value = node._values[ivalue];
/* 6497 */         out.writeObject((value instanceof Serializable) ? value : null);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6502 */     int ntet = this._ntet;
/* 6503 */     out.writeInt(ntet);
/* 6504 */     Tet[] tets = new Tet[ntet];
/* 6505 */     TetIterator ti = getTets();
/* 6506 */     for (int j = 0; j < ntet; j++) {
/* 6507 */       Tet tet = tets[j] = ti.next();
/* 6508 */       out.writeObject(tet);
/*      */     } 
/*      */ 
/*      */     
/* 6512 */     out.writeObject(this._nroot);
/* 6513 */     for (int i = 0; i < nnode; i++) {
/* 6514 */       Node node = nodes[i];
/* 6515 */       out.writeObject(node._prev);
/* 6516 */       out.writeObject(node._next);
/* 6517 */       out.writeObject(node._tet);
/*      */     } 
/*      */ 
/*      */     
/* 6521 */     out.writeObject(this._troot);
/* 6522 */     for (int itet = 0; itet < ntet; itet++) {
/* 6523 */       Tet tet = tets[itet];
/* 6524 */       out.writeObject(tet._n0);
/* 6525 */       out.writeObject(tet._n1);
/* 6526 */       out.writeObject(tet._n2);
/* 6527 */       out.writeObject(tet._n3);
/* 6528 */       out.writeObject(tet._t0);
/* 6529 */       out.writeObject(tet._t1);
/* 6530 */       out.writeObject(tet._t2);
/* 6531 */       out.writeObject(tet._t3);
/*      */     } 
/*      */ 
/*      */     
/* 6535 */     out.writeBoolean(this._outerEnabled);
/* 6536 */     out.writeDouble(this._xminOuter);
/* 6537 */     out.writeDouble(this._yminOuter);
/* 6538 */     out.writeDouble(this._zminOuter);
/* 6539 */     out.writeDouble(this._xmaxOuter);
/* 6540 */     out.writeDouble(this._ymaxOuter);
/* 6541 */     out.writeDouble(this._zmaxOuter);
/*      */ 
/*      */     
/* 6544 */     out.writeInt(this._nnodeValues);
/* 6545 */     out.writeInt(this._lnodeValues);
/* 6546 */     out.writeObject(this._nodePropertyMaps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void sampleNodes() {
/* 6556 */     Random random = new Random();
/* 6557 */     this._sampledNodes.clear();
/* 6558 */     int nsamp = 2 * (int)MathPlus.pow(this._nnode, 0.25D);
/* 6559 */     Node node = this._nroot;
/* 6560 */     while (this._sampledNodes.size() < nsamp) {
/* 6561 */       int nskip = 1 + random.nextInt(this._nnode / 2);
/* 6562 */       while (--nskip > 0)
/* 6563 */         node = node._next; 
/* 6564 */       this._sampledNodes.add(node);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static interface TetListener extends EventListener {
/*      */     void tetAdded(TetMesh param1TetMesh, TetMesh.Tet param1Tet);
/*      */     
/*      */     void tetRemoved(TetMesh param1TetMesh, TetMesh.Tet param1Tet);
/*      */   }
/*      */   
/*      */   public static interface NodeListener extends EventListener {
/*      */     void nodeWillBeAdded(TetMesh param1TetMesh, TetMesh.Node param1Node);
/*      */     
/*      */     void nodeAdded(TetMesh param1TetMesh, TetMesh.Node param1Node);
/*      */     
/*      */     void nodeWillBeRemoved(TetMesh param1TetMesh, TetMesh.Node param1Node);
/*      */     
/*      */     void nodeRemoved(TetMesh param1TetMesh, TetMesh.Node param1Node);
/*      */   }
/*      */   
/*      */   public static interface NodePropertyMap extends Serializable {
/*      */     Object get(TetMesh.Node param1Node);
/*      */     
/*      */     void put(TetMesh.Node param1Node, Object param1Object);
/*      */   }
/*      */   
/*      */   public static interface FaceIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TetMesh.Face next();
/*      */   }
/*      */   
/*      */   public static interface EdgeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TetMesh.Edge next();
/*      */   }
/*      */   
/*      */   public static interface TetIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TetMesh.Tet next();
/*      */   }
/*      */   
/*      */   public static interface NodeIterator {
/*      */     boolean hasNext();
/*      */     
/*      */     TetMesh.Node next();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/mesh/TetMesh.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
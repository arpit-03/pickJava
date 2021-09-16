/*      */ package edu.mines.jtk.util;
/*      */ 
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RTree
/*      */   extends AbstractSet<Object>
/*      */ {
/*      */   private Node _root;
/*      */   private int _ndim;
/*      */   private int _nmin;
/*      */   private int _nmax;
/*      */   private int _size;
/*      */   private Boxer _boxer;
/*      */   private int _modIndex;
/*      */   private float[] _amin;
/*      */   private float[] _amax;
/*      */   private float[] _bmin;
/*      */   private float[] _bmax;
/*      */   private float[] _smin;
/*      */   private float[] _smax;
/*      */   private float[] _tmin;
/*      */   private float[] _tmax;
/*      */   
/*      */   public static interface Boxed
/*      */   {
/*      */     void getBounds(float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*      */     
/*      */     float getDistanceSquared(float[] param1ArrayOffloat);
/*      */   }
/*      */   
/*      */   public static interface Boxer
/*      */   {
/*      */     void getBounds(Object param1Object, float[] param1ArrayOffloat1, float[] param1ArrayOffloat2);
/*      */     
/*      */     float getDistanceSquared(Object param1Object, float[] param1ArrayOffloat);
/*      */   }
/*      */   
/*      */   public static class Box
/*      */     implements Boxed
/*      */   {
/*      */     private int _ndim;
/*      */     private float[] _min;
/*      */     private float[] _max;
/*      */     
/*      */     public Box(float xmin, float ymin, float xmax, float ymax) {
/*  131 */       this._ndim = 2;
/*  132 */       this._min = new float[] { xmin, ymin };
/*  133 */       this._max = new float[] { xmax, ymax };
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
/*      */     public Box(float xmin, float ymin, float zmin, float xmax, float ymax, float zmax) {
/*  149 */       this._ndim = 3;
/*  150 */       this._min = new float[] { xmin, ymin, zmin };
/*  151 */       this._max = new float[] { xmax, ymax, zmax };
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Box(float[] min, float[] max) {
/*  161 */       Check.argument((min.length == max.length), "min/max lengths are equal");
/*  162 */       this._ndim = min.length;
/*  163 */       this._min = new float[this._ndim];
/*  164 */       this._max = new float[this._ndim];
/*  165 */       for (int idim = 0; idim < this._ndim; idim++) {
/*  166 */         this._min[idim] = min[idim];
/*  167 */         this._max[idim] = max[idim];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Box(int ndim, RTree.Boxed boxed) {
/*  177 */       this._ndim = ndim;
/*  178 */       this._min = new float[this._ndim];
/*  179 */       this._max = new float[this._ndim];
/*  180 */       boxed.getBounds(this._min, this._max);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void getBounds(float[] min, float[] max) {
/*  189 */       for (int idim = 0; idim < this._ndim; idim++) {
/*  190 */         min[idim] = this._min[idim];
/*  191 */         max[idim] = this._max[idim];
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public float getDistanceSquared(float[] point) {
/*  201 */       float sum = 0.0F;
/*  202 */       for (int idim = 0; idim < this._ndim; idim++) {
/*  203 */         float p = point[idim];
/*  204 */         float s = this._min[idim];
/*  205 */         float t = this._max[idim];
/*  206 */         float d = (p < s) ? (p - s) : ((p > t) ? (p - t) : 0.0F);
/*  207 */         sum += d * d;
/*      */       } 
/*  209 */       return sum;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean overlaps(Box box) {
/*  218 */       float[] bmin = box._min;
/*  219 */       float[] bmax = box._max;
/*  220 */       for (int idim = 0; idim < this._ndim; idim++) {
/*  221 */         if (this._min[idim] > bmax[idim] || this._max[idim] < bmin[idim])
/*  222 */           return false; 
/*      */       } 
/*  224 */       return true;
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
/*      */   public RTree(int ndim, int nmin, int nmax) {
/*  242 */     this(ndim, nmin, nmax, new DefaultBoxer(null));
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
/*      */   public RTree(int ndim, int nmin, int nmax, Boxer boxer) {
/*  257 */     Check.argument((nmin > 0), "nmin>0");
/*  258 */     Check.argument((nmin <= nmax / 2), "nmin<=nmax/2");
/*  259 */     Check.argument((nmax >= 4), "nmax>=4");
/*  260 */     this._ndim = ndim;
/*  261 */     this._nmin = nmin;
/*  262 */     this._nmax = nmax;
/*  263 */     this._boxer = boxer;
/*  264 */     this._root = new Node(1);
/*  265 */     this._smin = new float[this._ndim];
/*  266 */     this._smax = new float[this._ndim];
/*  267 */     this._tmin = new float[this._ndim];
/*  268 */     this._tmax = new float[this._ndim];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  277 */     return this._size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  284 */     this._root = new Node(1);
/*  285 */     this._size = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  293 */     return (this._size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean add(Object object) {
/*  302 */     Check.argument((object != null), "object is not null");
/*  303 */     Node leaf = this._root.chooseNodeFor(object);
/*  304 */     if (leaf.indexOf(object) >= 0)
/*  305 */       return false; 
/*  306 */     leaf.add(object);
/*  307 */     this._size++;
/*  308 */     this._modIndex++;
/*      */     
/*  310 */     return true;
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
/*      */   public int addPacked(Object[] objects) {
/*  329 */     int size = this._size;
/*      */ 
/*      */     
/*  332 */     int n = objects.length;
/*  333 */     int[] index = new int[n];
/*  334 */     float[][] x = new float[this._ndim][n];
/*  335 */     float[] xmin = new float[3];
/*  336 */     float[] xmax = new float[3];
/*  337 */     for (int i = 0; i < n; i++) {
/*  338 */       index[i] = i;
/*  339 */       this._boxer.getBounds(objects[i], xmin, xmax);
/*  340 */       for (int idim = 0; idim < this._ndim; idim++) {
/*  341 */         x[idim][i] = 0.5F * (xmin[idim] + xmax[idim]);
/*      */       }
/*      */     } 
/*      */     
/*  345 */     addPacked(0, x, 0, n, index, objects);
/*      */ 
/*      */     
/*  348 */     return this._size - size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean remove(Object object) {
/*  357 */     Check.argument((object != null), "object is not null");
/*  358 */     Node leaf = this._root.findLeafWith(object);
/*  359 */     if (leaf == null)
/*  360 */       return false; 
/*  361 */     leaf.remove(object, null);
/*  362 */     this._size--;
/*  363 */     this._modIndex++;
/*      */     
/*  365 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(Object object) {
/*  374 */     Node leaf = this._root.findLeafWith(object);
/*  375 */     return (leaf != null);
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
/*      */   public Iterator<Object> iterator() {
/*  392 */     return new RTreeIterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLevels() {
/*  400 */     return this._root.level();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] findOverlapping(float[] min, float[] max) {
/*  410 */     Check.argument((min.length == this._ndim), "min.length equals tree ndim");
/*  411 */     Check.argument((max.length == this._ndim), "max.length equals tree ndim");
/*  412 */     ArrayList<Object> list = new ArrayList();
/*  413 */     this._root.findOverlapping(min, max, list);
/*  414 */     return list.toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] findOverlapping(Box box) {
/*  424 */     Check.argument((box != null), "box is not null");
/*  425 */     Check.argument((box._ndim == this._ndim), "box ndim = tree ndim");
/*  426 */     return findOverlapping(box._min, box._max);
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
/*      */   public Object[] findInSphere(float[] center, float radius) {
/*  439 */     Check.argument((center.length == this._ndim), "center.length equals tree ndim");
/*  440 */     ArrayList<Object> list = new ArrayList();
/*  441 */     this._root.findInSphere(center, radius, list);
/*  442 */     return list.toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object findNearest(float[] point) {
/*  451 */     if (isEmpty())
/*  452 */       return null; 
/*  453 */     return findNearest(1, point)[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object[] findNearest(int k, float[] point) {
/*  463 */     Check.argument((point.length == this._ndim), "point.length equals tree ndim");
/*  464 */     Nearest nearest = new Nearest(k, point);
/*  465 */     this._root.findNearest(nearest);
/*  466 */     return nearest.toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLeafArea() {
/*  474 */     return this._root.areaLeaf();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLeafVolume() {
/*  482 */     return this._root.volumeLeaf();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dump() {
/*  490 */     this._root.dump(this._root);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void validate() {
/*  498 */     this._root.validate();
/*      */   }
/*      */ 
/*      */   
/*      */   private static class DefaultBoxer
/*      */     implements Boxer
/*      */   {
/*      */     private DefaultBoxer() {}
/*      */ 
/*      */     
/*      */     public final void getBounds(Object object, float[] min, float[] max) {
/*  509 */       ((RTree.Boxed)object).getBounds(min, max);
/*      */     }
/*      */     public final float getDistanceSquared(Object object, float[] point) {
/*  512 */       return ((RTree.Boxed)object).getDistanceSquared(point);
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
/*      */   private class Node
/*      */   {
/*      */     private static final float INFINITY = 3.4028235E38F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private float[] _min;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private float[] _max;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Node _parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int _level;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int _nbox;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object[] _boxs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int level() {
/*      */       return this._level;
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
/*      */     boolean isLeaf() {
/*      */       return (this._level == 1);
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
/*      */     int size() {
/*      */       return this._nbox;
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
/*      */     void add(Object box) {
/*      */       if (this._nbox < RTree.this._nmax) {
/*      */         append(box);
/*      */         expandUp(box);
/*      */       } else {
/*      */         Node nodeNew = split(box);
/*      */         updateUp();
/*      */         if (this._parent == null) {
/*      */           RTree.this._root = this._parent = new Node(this._level + 1);
/*      */           this._parent.add(this);
/*      */         } 
/*      */         this._parent.add(nodeNew);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void remove(Object box, ArrayList<Object> orphans) {
/*      */       int ibox = indexOf(box);
/*      */       assert ibox >= 0 : "box is a child of this node";
/*      */       int jbox;
/*      */       for (jbox = ibox + 1; jbox < this._nbox; jbox++) {
/*      */         this._boxs[jbox - 1] = this._boxs[jbox];
/*      */       }
/*      */       this._nbox--;
/*      */       if (this._nbox >= RTree.this._nmin || this._parent == null) {
/*      */         updateUp();
/*      */       } else {
/*      */         if (orphans == null) {
/*      */           orphans = new ArrayList();
/*      */         }
/*      */         for (jbox = 0; jbox < this._nbox; jbox++) {
/*      */           orphans.add(this._boxs[jbox]);
/*      */         }
/*      */         this._nbox = 0;
/*      */         this._parent.remove(this, orphans);
/*      */         int norphan = orphans.size();
/*      */         for (int iorphan = 0; iorphan < norphan; iorphan++) {
/*      */           Object orphan = orphans.get(iorphan);
/*      */           Node parent = RTree.this._root.chooseNodeFor(orphan);
/*      */           parent.add(orphan);
/*      */         } 
/*      */         orphans.clear();
/*      */       } 
/*      */       if (RTree.this._root._nbox == 1 && RTree.this._root._level > 1) {
/*      */         RTree.this._root = (Node)RTree.this._root._boxs[0];
/*      */         RTree.this._root._parent = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void findOverlapping(float[] min, float[] max, ArrayList<Object> list) {
/*      */       for (int ibox = 0; ibox < this._nbox; ibox++) {
/*      */         Object boxi = this._boxs[ibox];
/*      */         if (overlaps(boxi, min, max)) {
/*      */           if (isLeaf()) {
/*      */             list.add(boxi);
/*      */           } else {
/*      */             ((Node)boxi).findOverlapping(min, max, list);
/*      */           } 
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void findInSphere(float[] center, float radius, ArrayList<Object> list) {
/*      */       float ss = radius * radius;
/*      */       for (int ibox = 0; ibox < this._nbox; ibox++) {
/*      */         Object boxi = this._boxs[ibox];
/*      */         float ds = distanceSquared(boxi, center);
/*      */         if (ds <= ss) {
/*      */           if (isLeaf()) {
/*      */             list.add(boxi);
/*      */           } else {
/*      */             ((Node)boxi).findInSphere(center, radius, list);
/*      */           } 
/*      */         }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Node(int level) {
/*  947 */       this._min = new float[RTree.this._ndim];
/*  948 */       this._max = new float[RTree.this._ndim];
/*      */ 
/*      */ 
/*      */       
/*  952 */       this._boxs = new Object[RTree.this._nmax]; this._level = level; for (int idim = 0; idim < RTree.this._ndim; idim++) { this._min[idim] = Float.MAX_VALUE; this._max[idim] = -3.4028235E38F; } 
/*      */     } Node chooseNodeFor(Object box) { int level = 1 + level(box); assert this._level >= level : "level of this node exceeds level of box"; if (this._level == level)
/*      */         return this;  Node node = null; float dmin = Float.MAX_VALUE; float vmin = Float.MAX_VALUE; for (int ibox = 0; ibox < this._nbox; ibox++) { Node nodei = (Node)this._boxs[ibox]; float d = nodei.volumeDelta(box); if (d <= dmin) { float v = nodei.volume(); if (d < dmin || v < vmin) { node = nodei; dmin = d; vmin = v; }  }  }  return node.chooseNodeFor(box); } Node findLeafWith(Object box) { if (isLeaf()) { for (int ibox = 0; ibox < this._nbox; ibox++) { if (this._boxs[ibox].equals(box))
/*      */             return this;  }  } else { for (int ibox = 0; ibox < this._nbox; ibox++) { Node nodei = (Node)this._boxs[ibox]; if (nodei.overlaps(box)) { Node node = nodei.findLeafWith(box); if (node != null)
/*      */               return node;  }  }  }
/*      */        return null; }
/*  958 */     private boolean overlaps(Object box) { RTree.this.loadB(box);
/*  959 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/*  960 */         if (this._min[idim] > RTree.this._bmax[idim] || this._max[idim] < RTree.this._bmin[idim])
/*  961 */           return false; 
/*      */       } 
/*  963 */       return true; }
/*      */     void findNearest(RTree.Nearest nearest) { if (isLeaf()) { for (int ibox = 0; ibox < this._nbox; ibox++)
/*      */           nearest.update(this._boxs[ibox]);  } else { ArrayList<RTree.BoxDistance> list = new ArrayList<>(this._nbox); int ibox; for (ibox = 0; ibox < this._nbox; ibox++) { Object box = this._boxs[ibox]; float distance = distanceSquared(box, nearest.point()); list.add(new RTree.BoxDistance(box, distance)); }  Collections.sort(list); for (ibox = 0; ibox < this._nbox; ibox++) { RTree.BoxDistance bd = list.get(ibox); if (bd.distance < nearest.cutoff())
/*      */             ((Node)bd.box).findNearest(nearest);  }
/*      */          }
/*      */        }
/*      */     float distanceSquared(float[] point) { float sum = 0.0F; for (int idim = 0; idim < RTree.this._ndim; idim++) { float p = point[idim]; float s = this._min[idim]; float t = this._max[idim]; float d = (p < s) ? (p - s) : ((p > t) ? (p - t) : 0.0F); sum += d * d; }
/*  970 */        return sum; } private boolean overlaps(Object box, float[] min, float[] max) { RTree.this.loadB(box);
/*  971 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/*  972 */         if (RTree.this._bmin[idim] > max[idim] || RTree.this._bmax[idim] < min[idim])
/*  973 */           return false; 
/*      */       } 
/*  975 */       return true; }
/*      */     float distanceSquared(Object box, float[] point) { if (box instanceof Node)
/*      */         return ((Node)box).distanceSquared(point);  return RTree.this._boxer.getDistanceSquared(box, point); }
/*      */     int indexOf(Object box) { if (box instanceof Node) { for (int ibox = 0; ibox < this._nbox; ibox++) { if (box == this._boxs[ibox])
/*      */             return ibox;  }  } else { for (int ibox = 0; ibox < this._nbox; ibox++) { if (box.equals(this._boxs[ibox]))
/*      */             return ibox;  }  }  return -1; }
/*      */     int level(Object box) { return (box instanceof Node) ? ((Node)box)._level : 0; }
/*  982 */     float volumeLeaf() { float volume = 0.0F; for (int ibox = 0; ibox < this._nbox; ibox++) { Object box = this._boxs[ibox]; if (box instanceof Node) { Node node = (Node)box; if (node.isLeaf()) { volume += volume(box); } else { volume += node.volumeLeaf(); }  }  }  return volume; } private float volume() { float v = 1.0F;
/*  983 */       for (int idim = 0; idim < RTree.this._ndim; idim++)
/*  984 */         v *= this._max[idim] - this._min[idim]; 
/*  985 */       return v; } float areaLeaf() { float area = 0.0F; for (int ibox = 0; ibox < this._nbox; ibox++) { Object box = this._boxs[ibox]; if (box instanceof Node) { Node node = (Node)box; if (node.isLeaf()) {
/*      */             area += area(box);
/*      */           } else {
/*      */             area += node.areaLeaf();
/*      */           }  }
/*      */          }
/*      */        return area; }
/*  992 */     private float volume(Object box) { RTree.this.loadB(box);
/*  993 */       float v = 1.0F;
/*  994 */       for (int idim = 0; idim < RTree.this._ndim; idim++)
/*  995 */         v *= RTree.this._bmax[idim] - RTree.this._bmin[idim]; 
/*  996 */       return v; } void dump(Object box) { int indent = 2 * (level(RTree.this._root) - level(box)); StringBuffer sb = new StringBuffer(indent); for (int i = 0; i < indent; i++)
/*      */         sb.append(' ');  System.out.println(sb.toString() + box); if (box instanceof Node) { Node node = (Node)box; int nbox = node._nbox; Object[] boxs = node._boxs; for (int ibox = 0; ibox < nbox; ibox++)
/*      */           dump(boxs[ibox]);  }  }
/*      */     void validate() { assert this._parent != null || RTree.this._root == this : "node without parent is root"; if (RTree.this._root != this) { assert this._nbox >= RTree.this._nmin : "_nbox>=_nmin"; assert this._nbox <= RTree.this._nmax : "_nbox<=_nmin"; } else if (isLeaf()) { assert this._nbox >= 0 : "_nbox>=0"; } else { assert this._nbox >= 2 : "_nbox>=2"; }  assert this._nbox <= RTree.this._nmax : "_nbox<=_nmin"; Node tmp = new Node(0); for (int ibox = 0; ibox < this._nbox; ibox++) { if (!isLeaf()) { Node node = (Node)this._boxs[ibox]; assert node._parent == this : "node._parent==this"; assert node._level == this._level - 1 : "node._level==_level-1"; node.validate(); }
/*      */          tmp.expand(this._boxs[ibox]); }
/*      */        for (int idim = 0; idim < RTree.this._ndim; idim++) { assert this._min[idim] == tmp._min[idim] : "minimum bounds are correct"; assert this._max[idim] == tmp._max[idim] : "maximum bounds are correct"; }
/*      */        }
/* 1003 */     private float area(Object box) { float v = volume(box);
/* 1004 */       RTree.this.loadB(box);
/* 1005 */       float area = 0.0F;
/* 1006 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/* 1007 */         float d = RTree.this._bmax[idim] - RTree.this._bmin[idim];
/* 1008 */         area += 2.0F * v / d;
/*      */       } 
/* 1010 */       return area; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private float volumeDelta(Object box) {
/* 1017 */       RTree.this.loadB(box);
/* 1018 */       float vnew = 1.0F;
/* 1019 */       float vold = 1.0F;
/* 1020 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/* 1021 */         float amin = this._min[idim];
/* 1022 */         float amax = this._max[idim];
/* 1023 */         vold *= amax - amin;
/* 1024 */         float bmin = RTree.this._bmin[idim];
/* 1025 */         float bmax = RTree.this._bmax[idim];
/* 1026 */         if (bmin < amin)
/* 1027 */           amin = bmin; 
/* 1028 */         if (bmax > amax)
/* 1029 */           amax = bmax; 
/* 1030 */         vnew *= amax - amin;
/*      */       } 
/* 1032 */       return vnew - vold;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private float volumeDelta(Object abox, Object bbox) {
/* 1039 */       RTree.this.loadA(abox);
/* 1040 */       RTree.this.loadB(bbox);
/* 1041 */       float vnew = 1.0F;
/* 1042 */       float vold = 1.0F;
/* 1043 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/* 1044 */         float amin = RTree.this._amin[idim];
/* 1045 */         float amax = RTree.this._amax[idim];
/* 1046 */         vold *= amax - amin;
/* 1047 */         float bmin = RTree.this._bmin[idim];
/* 1048 */         float bmax = RTree.this._bmax[idim];
/* 1049 */         if (bmin < amin)
/* 1050 */           amin = bmin; 
/* 1051 */         if (bmax > amax)
/* 1052 */           amax = bmax; 
/* 1053 */         vnew *= amax - amin;
/*      */       } 
/* 1055 */       return vnew - vold;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void append(Object box) {
/* 1064 */       this._boxs[this._nbox++] = box;
/* 1065 */       if (box instanceof Node) {
/* 1066 */         ((Node)box)._parent = this;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean expand(Object box) {
/* 1075 */       RTree.this.loadB(box);
/* 1076 */       boolean changed = false;
/* 1077 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/* 1078 */         if (RTree.this._bmin[idim] < this._min[idim]) {
/* 1079 */           this._min[idim] = RTree.this._bmin[idim];
/* 1080 */           changed = true;
/*      */         } 
/* 1082 */         if (RTree.this._bmax[idim] > this._max[idim]) {
/* 1083 */           this._max[idim] = RTree.this._bmax[idim];
/* 1084 */           changed = true;
/*      */         } 
/*      */       } 
/* 1087 */       return changed;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean update() {
/* 1095 */       for (int i = 0; i < RTree.this._ndim; i++) {
/* 1096 */         RTree.this._smin[i] = this._min[i];
/* 1097 */         RTree.this._smax[i] = this._max[i];
/* 1098 */         this._min[i] = Float.MAX_VALUE;
/* 1099 */         this._max[i] = -3.4028235E38F;
/*      */       } 
/* 1101 */       for (int ibox = 0; ibox < this._nbox; ibox++)
/* 1102 */         expand(this._boxs[ibox]); 
/* 1103 */       for (int idim = 0; idim < RTree.this._ndim; idim++) {
/* 1104 */         if (this._min[idim] != RTree.this._smin[idim] || this._max[idim] != RTree.this._smax[idim])
/* 1105 */           return true; 
/*      */       } 
/* 1107 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void expandUp(Object box) {
/* 1117 */       if (expand(box) && this._parent != null) {
/* 1118 */         this._parent.expandUp(this);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void updateUp() {
/* 1127 */       if (update() && this._parent != null) {
/* 1128 */         this._parent.updateUp();
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
/*      */     
/*      */     private Node split(Object box) {
/* 1143 */       int nbox = this._nbox + 1;
/* 1144 */       Object[] boxs = new Object[nbox];
/* 1145 */       for (int ibox = 0; ibox < this._nbox; ibox++)
/* 1146 */         boxs[ibox] = this._boxs[ibox]; 
/* 1147 */       boxs[this._nbox] = box;
/* 1148 */       this._nbox = 0;
/*      */ 
/*      */       
/* 1151 */       Node node1 = this;
/* 1152 */       Node node2 = new Node(this._level);
/*      */ 
/*      */       
/* 1155 */       float dmax = -3.4028235E38F;
/* 1156 */       int imax = -1;
/* 1157 */       int jmax = -1; int i;
/* 1158 */       for (i = 0; i < nbox; i++) {
/* 1159 */         Object bbox = boxs[i];
/* 1160 */         float bvol = volume(bbox);
/* 1161 */         for (int jbox = i + 1; jbox < nbox; jbox++) {
/* 1162 */           Object abox = boxs[jbox];
/* 1163 */           float dbox = volumeDelta(abox, bbox) - bvol;
/* 1164 */           if (dbox > dmax) {
/* 1165 */             dmax = dbox;
/* 1166 */             imax = i;
/* 1167 */             jmax = jbox;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1175 */       node1.append(boxs[imax]);
/* 1176 */       node2.append(boxs[jmax]);
/* 1177 */       boxs[imax] = null;
/* 1178 */       boxs[jmax] = null;
/*      */ 
/*      */       
/* 1181 */       for (i = 2; i < nbox; i++) {
/*      */ 
/*      */         
/* 1184 */         int kbox = -1;
/*      */ 
/*      */         
/* 1187 */         Node nodek = null;
/*      */ 
/*      */         
/* 1190 */         int nsmall = RTree.this._nmin - nbox - i;
/* 1191 */         if (nsmall == node1.size()) {
/* 1192 */           nodek = node1;
/* 1193 */         } else if (nsmall == node2.size()) {
/* 1194 */           nodek = node2;
/*      */         } 
/*      */ 
/*      */         
/* 1198 */         if (nodek != null) {
/* 1199 */           for (int jbox = 0; jbox < nbox && kbox < 0; jbox++) {
/* 1200 */             if (boxs[jbox] != null) {
/* 1201 */               kbox = jbox;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1208 */           dmax = -3.4028235E38F;
/* 1209 */           for (int jbox = 0; jbox < nbox; jbox++) {
/* 1210 */             Object boxj = boxs[jbox];
/* 1211 */             if (boxj != null) {
/* 1212 */               float d1 = node1.volumeDelta(boxj);
/* 1213 */               float d2 = node2.volumeDelta(boxj);
/* 1214 */               float dbox = (d1 >= d2) ? (d1 - d2) : (d2 - d1);
/* 1215 */               if (dbox > dmax) {
/* 1216 */                 dmax = dbox;
/* 1217 */                 kbox = jbox;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1224 */           float delta1 = node1.volumeDelta(boxs[kbox]);
/* 1225 */           float delta2 = node2.volumeDelta(boxs[kbox]);
/* 1226 */           if (delta1 == delta2) {
/* 1227 */             float volume1 = node1.volume();
/* 1228 */             float volume2 = node2.volume();
/* 1229 */             if (volume1 == volume2) {
/* 1230 */               int size1 = node1.size();
/* 1231 */               int size2 = node1.size();
/* 1232 */               nodek = (size1 < size2) ? node1 : node2;
/*      */             } else {
/* 1234 */               nodek = (volume1 < volume2) ? node1 : node2;
/*      */             } 
/*      */           } else {
/* 1237 */             nodek = (delta1 < delta2) ? node1 : node2;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1242 */         nodek.append(boxs[kbox]);
/* 1243 */         boxs[kbox] = null;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1248 */       node2.update();
/* 1249 */       return node2;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class Nearest
/*      */   {
/*      */     private int _k;
/*      */     private float[] _point;
/*      */     private TreeSet<RTree.BoxDistance> _set;
/*      */     private boolean _full;
/*      */     private float _cutoff;
/*      */     
/*      */     Nearest(int k, float[] point) {
/* 1263 */       this._k = k;
/* 1264 */       this._point = point;
/* 1265 */       this._set = new TreeSet<>();
/* 1266 */       this._full = false;
/* 1267 */       this._cutoff = Float.MAX_VALUE;
/*      */     }
/*      */     void update(Object box) {
/* 1270 */       float d = RTree.this._boxer.getDistanceSquared(box, this._point);
/* 1271 */       if (d < this._cutoff) {
/* 1272 */         RTree.BoxDistance bd = new RTree.BoxDistance(box, d);
/* 1273 */         if (this._full)
/* 1274 */           this._set.remove(this._set.last()); 
/* 1275 */         this._set.add(bd);
/* 1276 */         this._full = (this._full || this._k == this._set.size());
/* 1277 */         if (this._full)
/* 1278 */           this._cutoff = ((RTree.BoxDistance)this._set.last()).distance; 
/*      */       } 
/*      */     }
/*      */     float[] point() {
/* 1282 */       return this._point;
/*      */     }
/*      */     float cutoff() {
/* 1285 */       return this._cutoff;
/*      */     }
/*      */     Object[] toArray() {
/* 1288 */       Object[] boxs = new Object[this._set.size()];
/* 1289 */       Iterator<RTree.BoxDistance> i = this._set.iterator();
/* 1290 */       for (int ibox = 0; i.hasNext(); ibox++)
/* 1291 */         boxs[ibox] = ((RTree.BoxDistance)i.next()).box; 
/* 1292 */       return boxs;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class BoxDistance
/*      */     implements Comparable<BoxDistance>
/*      */   {
/*      */     Object box;
/*      */ 
/*      */     
/*      */     float distance;
/*      */ 
/*      */     
/*      */     BoxDistance(Object box, float distance) {
/* 1308 */       this.box = box;
/* 1309 */       this.distance = distance;
/*      */     }
/*      */     public int compareTo(BoxDistance bd) {
/* 1312 */       if (this.distance < bd.distance)
/* 1313 */         return -1; 
/* 1314 */       if (this.distance > bd.distance) {
/* 1315 */         return 1;
/*      */       }
/* 1317 */       int th = System.identityHashCode(this.box);
/* 1318 */       int oh = System.identityHashCode(bd.box);
/* 1319 */       if (th < oh)
/* 1320 */         return -1; 
/* 1321 */       if (th > oh) {
/* 1322 */         return 1;
/*      */       }
/* 1324 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object o) {
/* 1329 */       BoxDistance bd = (BoxDistance)o;
/* 1330 */       return (this.box == bd.box && this.distance == bd.distance);
/*      */     } }
/*      */   private class RTreeIterator implements Iterator<Object> { private RTree.Node _leaf; private int _ibox; private Object _next;
/*      */     private int _modIndexExpected;
/*      */     
/*      */     public boolean hasNext() {
/* 1336 */       return (this._next != null);
/*      */     }
/*      */     public Object next() {
/* 1339 */       if (this._next == null)
/* 1340 */         throw new NoSuchElementException(); 
/* 1341 */       if (this._modIndexExpected != RTree.this._modIndex)
/* 1342 */         throw new ConcurrentModificationException(); 
/* 1343 */       Object object = this._next;
/* 1344 */       loadNext();
/* 1345 */       return object;
/*      */     }
/*      */     public void remove() {
/* 1348 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     RTreeIterator() {
/* 1351 */       this._leaf = RTree.this._root;
/* 1352 */       while (!this._leaf.isLeaf())
/* 1353 */         this._leaf = (RTree.Node)this._leaf._boxs[0]; 
/* 1354 */       this._ibox = 0;
/* 1355 */       this._next = (this._ibox < this._leaf._nbox) ? this._leaf._boxs[0] : null;
/* 1356 */       this._modIndexExpected = RTree.this._modIndex;
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
/*      */     private void loadNext() {
/* 1371 */       this._ibox++;
/* 1372 */       if (this._ibox == this._leaf._nbox) {
/* 1373 */         RTree.Node node = this._leaf;
/* 1374 */         RTree.Node parent = node;
/* 1375 */         while (node == parent && parent != RTree.this._root) {
/* 1376 */           parent = node._parent;
/* 1377 */           int ibox = 1 + parent.indexOf(node);
/* 1378 */           if (ibox < parent._nbox) {
/* 1379 */             node = (RTree.Node)parent._boxs[ibox];
/* 1380 */             while (!node.isLeaf())
/* 1381 */               node = (RTree.Node)node._boxs[0]; 
/* 1382 */             this._leaf = node;
/* 1383 */             this._ibox = 0; continue;
/*      */           } 
/* 1385 */           node = parent;
/*      */         } 
/*      */       } 
/*      */       
/* 1389 */       this._next = (this._ibox < this._leaf._nbox) ? this._leaf._boxs[this._ibox] : null;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadA(Object box) {
/* 1429 */     if (box instanceof Node) {
/* 1430 */       Node node = (Node)box;
/* 1431 */       this._amin = node._min;
/* 1432 */       this._amax = node._max;
/*      */     } else {
/* 1434 */       this._boxer.getBounds(box, this._smin, this._smax);
/* 1435 */       this._amin = this._smin;
/* 1436 */       this._amax = this._smax;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadB(Object box) {
/* 1446 */     if (box instanceof Node) {
/* 1447 */       Node node = (Node)box;
/* 1448 */       this._bmin = node._min;
/* 1449 */       this._bmax = node._max;
/*      */     } else {
/* 1451 */       this._boxer.getBounds(box, this._tmin, this._tmax);
/* 1452 */       this._bmin = this._tmin;
/* 1453 */       this._bmax = this._tmax;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addPacked(int idim, float[][] x, int p, int q, int[] index, Object[] objects) {
/* 1460 */     int kdim = this._ndim - idim;
/*      */ 
/*      */     
/* 1463 */     if (p < q)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1468 */       if (kdim == 0) {
/*      */         
/* 1470 */         for (int i = p; i < q; i++)
/*      */         {
/* 1472 */           add(objects[index[i]]);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1480 */         int n = index.length;
/* 1481 */         int nsort = q - p;
/* 1482 */         int[] isort = index;
/* 1483 */         float[] xsort = x[idim];
/* 1484 */         if (nsort < n) {
/* 1485 */           isort = new int[nsort];
/* 1486 */           xsort = new float[nsort];
/* 1487 */           float[] xidim = x[idim];
/* 1488 */           for (int jsort = 0; jsort < nsort; jsort++) {
/* 1489 */             isort[jsort] = jsort;
/* 1490 */             xsort[jsort] = xidim[index[p + jsort]];
/*      */           } 
/*      */         } 
/* 1493 */         ArrayMath.quickIndexSort(xsort, isort);
/* 1494 */         if (nsort < n) {
/* 1495 */           int jsort; for (jsort = 0; jsort < nsort; jsort++)
/* 1496 */             isort[jsort] = index[p + isort[jsort]]; 
/* 1497 */           for (jsort = 0; jsort < nsort; jsort++) {
/* 1498 */             index[p + jsort] = isort[jsort];
/*      */           }
/*      */         } 
/*      */         
/* 1502 */         int nleaf = 1 + nsort / this._nmax;
/*      */ 
/*      */         
/* 1505 */         int nslab = (int)Math.ceil(Math.pow(nleaf, 1.0D / kdim));
/*      */ 
/*      */         
/* 1508 */         int mslab = (int)Math.ceil(nsort / nslab);
/*      */ 
/*      */         
/*      */         int pslab;
/*      */         
/* 1513 */         for (pslab = p; pslab < q; pslab += mslab) {
/* 1514 */           int qslab = pslab + mslab;
/* 1515 */           if (qslab > q)
/* 1516 */             qslab = q; 
/* 1517 */           if (qslab > pslab)
/* 1518 */             addPacked(idim + 1, x, pslab, qslab, index, objects); 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/RTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
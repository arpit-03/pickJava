/*      */ package edu.mines.jtk.interp;
/*      */ 
/*      */ import edu.mines.jtk.dsp.Sampling;
/*      */ import edu.mines.jtk.la.DMatrix;
/*      */ import edu.mines.jtk.la.DMatrixQrd;
/*      */ import edu.mines.jtk.mesh.Geometry;
/*      */ import edu.mines.jtk.mesh.TetMesh;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import java.util.ArrayList;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SibsonInterpolator3
/*      */ {
/*      */   public enum Method
/*      */   {
/*  105 */     HALE_LIANG,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  113 */     BRAUN_SAMBRIDGE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  121 */     WATSON_SAMBRIDGE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class IndexWeight
/*      */   {
/*      */     public int index;
/*      */ 
/*      */     
/*      */     public float weight;
/*      */ 
/*      */ 
/*      */     
/*      */     IndexWeight(int index, float weight) {
/*  136 */       this.index = index;
/*  137 */       this.weight = weight;
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
/*      */   public SibsonInterpolator3(float[] x1, float[] x2, float[] x3) {
/*  150 */     this(Method.HALE_LIANG, null, x1, x2, x3);
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
/*      */   public SibsonInterpolator3(float[] f, float[] x1, float[] x2, float[] x3) {
/*  162 */     this(Method.HALE_LIANG, f, x1, x2, x3);
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
/*      */   public SibsonInterpolator3(Method method, float[] x1, float[] x2, float[] x3) {
/*  178 */     this(method, null, x1, x2, x3);
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
/*      */   public SibsonInterpolator3(Method method, float[] f, float[] x1, float[] x2, float[] x3) {
/*  194 */     makeMesh(f, x1, x2, x3);
/*  195 */     this._nodeList = new TetMesh.NodeList();
/*  196 */     this._tetList = new TetMesh.TetList();
/*  197 */     if (method == Method.WATSON_SAMBRIDGE) {
/*  198 */       this._va = new WatsonSambridge();
/*  199 */     } else if (method == Method.BRAUN_SAMBRIDGE) {
/*  200 */       this._va = new BraunSambridge();
/*  201 */     } else if (method == Method.HALE_LIANG) {
/*  202 */       this._va = new HaleLiang();
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
/*      */   public void setSamples(float[] f, float[] x1, float[] x2, float[] x3) {
/*  215 */     makeMesh(f, x1, x2, x3);
/*  216 */     this._haveGradients = false;
/*  217 */     if (this._gradientPower > 0.0D) {
/*  218 */       estimateGradients();
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
/*      */   public void setGradients(float[] g1, float[] g2, float[] g3) {
/*  231 */     int n = g1.length;
/*  232 */     for (int i = 0; i < n; i++) {
/*  233 */       TetMesh.Node node = this._nodes[i];
/*  234 */       NodeData data = data(node);
/*  235 */       data.gx = g1[i];
/*  236 */       data.gy = g2[i];
/*  237 */       data.gz = g3[i];
/*      */     } 
/*  239 */     this._haveGradients = true;
/*  240 */     if (this._gradientPower == 0.0D) {
/*  241 */       this._gradientPower = 1.0D;
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
/*      */   public void setGradientPower(double gradientPower) {
/*  259 */     if (!this._haveGradients && gradientPower > 0.0D)
/*  260 */       estimateGradients(); 
/*  261 */     this._gradientPower = gradientPower;
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
/*      */   public void setNullValue(float fnull) {
/*  273 */     this._fnull = fnull;
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
/*      */   public void setBounds(float x1min, float x1max, float x2min, float x2max, float x3min, float x3max) {
/*  297 */     Check.argument((x1min < x1max), "x1min<x1max");
/*  298 */     Check.argument((x2min < x2max), "x2min<x2max");
/*  299 */     Check.argument((x3min < x3max), "x3min<x3max");
/*      */ 
/*      */     
/*  302 */     this._x1bmn = x1min; this._x1bmx = x1max;
/*  303 */     this._x2bmn = x2min; this._x2bmx = x2max;
/*  304 */     this._x3bmn = x3min; this._x3bmx = x3max;
/*  305 */     this._useBoundingBox = true;
/*      */ 
/*      */     
/*  308 */     float scale = 1.0F;
/*  309 */     float x1avg = 0.5F * (x1min + x1max);
/*  310 */     float x2avg = 0.5F * (x2min + x2max);
/*  311 */     float x3avg = 0.5F * (x3min + x3max);
/*  312 */     float x1dif = Math.max(x1max - this._x1min, this._x1max - x1min);
/*  313 */     float x2dif = Math.max(x2max - this._x2min, this._x2max - x2min);
/*  314 */     float x3dif = Math.max(x3max - this._x3min, this._x3max - x3min);
/*  315 */     float x1pad = scale * x1dif;
/*  316 */     float x2pad = scale * x2dif;
/*  317 */     float x3pad = scale * x3dif;
/*  318 */     x1min -= x1pad; x1max += x1pad;
/*  319 */     x2min -= x2pad; x2max += x2pad;
/*  320 */     x3min -= x3pad; x3max += x2pad;
/*  321 */     float[] x1g = { x1min, x1max, x1avg, x1avg, x1avg, x1avg };
/*  322 */     float[] x2g = { x2avg, x2avg, x2min, x2max, x2avg, x2avg };
/*  323 */     float[] x3g = { x3avg, x3avg, x3avg, x3avg, x3min, x3max };
/*  324 */     addGhostNodes(x1g, x2g, x3g);
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
/*      */   public void setBounds(Sampling s1, Sampling s2, Sampling s3) {
/*  341 */     setBounds((float)s1.getFirst(), (float)s1.getLast(), 
/*  342 */         (float)s2.getFirst(), (float)s2.getLast(), 
/*  343 */         (float)s3.getFirst(), (float)s3.getLast());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void useConvexHullBounds() {
/*  351 */     this._useBoundingBox = false;
/*  352 */     removeGhostNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float interpolate(float x1, float x2, float x3) {
/*  363 */     if (!inBounds(x1, x2, x3))
/*  364 */       return this._fnull; 
/*  365 */     double vsum = computeVolumes(x1, x2, x3);
/*  366 */     if (vsum <= 0.0D)
/*  367 */       return this._fnull; 
/*  368 */     if (usingGradients()) {
/*  369 */       return interpolate1(vsum, x1, x2, x3);
/*      */     }
/*  371 */     return interpolate0(vsum);
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
/*      */   public float[][][] interpolate(Sampling s1, Sampling s2, Sampling s3) {
/*  383 */     log.fine("interpolate: begin");
/*  384 */     int n1 = s1.getCount();
/*  385 */     int n2 = s2.getCount();
/*  386 */     int n3 = s2.getCount();
/*  387 */     float[][][] f = new float[n3][n2][n1];
/*  388 */     for (int i3 = 0; i3 < n3; i3++) {
/*  389 */       log.fine("interpolate: i3=" + i3);
/*  390 */       float x3 = (float)s3.getValue(i3);
/*  391 */       for (int i2 = 0; i2 < n2; i2++) {
/*  392 */         log.finer("interpolate: i2=" + i2);
/*  393 */         float x2 = (float)s2.getValue(i2);
/*  394 */         for (int i1 = 0; i1 < n1; i1++) {
/*  395 */           float x1 = (float)s1.getValue(i1);
/*  396 */           f[i3][i2][i1] = interpolate(x1, x2, x3);
/*      */         } 
/*      */       } 
/*      */     } 
/*  400 */     log.fine("interpolate: end");
/*  401 */     return f;
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
/*      */   public IndexWeight[] getIndexWeights(float x1, float x2, float x3) {
/*  420 */     if (!inBounds(x1, x2, x3))
/*  421 */       return null; 
/*  422 */     float wsum = (float)computeVolumes(x1, x2, x3);
/*  423 */     if (wsum == 0.0F)
/*  424 */       return null; 
/*  425 */     float wscl = 1.0F / wsum;
/*  426 */     int nnode = this._nodeList.nnode();
/*  427 */     TetMesh.Node[] nodes = this._nodeList.nodes();
/*  428 */     IndexWeight[] iw = new IndexWeight[nnode];
/*  429 */     for (int inode = 0; inode < nnode; inode++) {
/*  430 */       TetMesh.Node node = nodes[inode];
/*  431 */       int i = node.index;
/*  432 */       float w = (float)volume(node) * wscl;
/*  433 */       iw[inode] = new IndexWeight(i, w);
/*      */     } 
/*  435 */     return iw;
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
/*      */   public float validate(int i) {
/*  454 */     return validate(new int[] { i })[0];
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
/*      */   public float[] validate(int[] i) {
/*  473 */     int nv = i.length;
/*  474 */     for (int iv = 0; iv < nv; iv++)
/*  475 */       this._mesh.removeNode(this._nodes[i[iv]]); 
/*  476 */     float[] fv = new float[nv]; int j;
/*  477 */     for (j = 0; j < nv; j++) {
/*  478 */       TetMesh.Node node = this._nodes[i[j]];
/*  479 */       float xn = node.x();
/*  480 */       float yn = node.y();
/*  481 */       float zn = node.z();
/*  482 */       fv[j] = interpolate(xn, yn, zn);
/*      */     } 
/*  484 */     for (j = 0; j < nv; j++)
/*  485 */       this._mesh.addNode(this._nodes[i[j]]); 
/*  486 */     return fv;
/*      */   }
/*      */   private TetMesh _mesh; private TetMesh.Node[] _nodes; private TetMesh.NodeList _nodeList; private TetMesh.TetList _tetList; private VolumeAccumulator _va; private boolean _haveGradients; private double _gradientPower;
/*      */   private float _fnull;
/*      */   private float _x1min;
/*      */   private float _x1max;
/*      */   private float _x2min;
/*  493 */   private static Logger log = Logger.getLogger(SibsonInterpolator3.class.getName());
/*      */   private float _x2max;
/*      */   private float _x3min;
/*      */   private float _x3max;
/*      */   private float _x1bmn;
/*      */   private float _x1bmx;
/*      */   
/*      */   private static NodeData data(TetMesh.Node node) {
/*  501 */     return (NodeData)node.data;
/*      */   } private float _x2bmn; private float _x2bmx; private float _x3bmn; private float _x3bmx; private boolean _useBoundingBox; private static class NodeData {
/*      */     float f; float gx; float gy; float gz; double volume; private NodeData() {} } private static float f(TetMesh.Node node) {
/*  504 */     return (data(node)).f;
/*      */   }
/*      */   private static float gx(TetMesh.Node node) {
/*  507 */     return (data(node)).gx;
/*      */   }
/*      */   private static float gy(TetMesh.Node node) {
/*  510 */     return (data(node)).gy;
/*      */   }
/*      */   private static float gz(TetMesh.Node node) {
/*  513 */     return (data(node)).gz;
/*      */   }
/*      */   private static double volume(TetMesh.Node node) {
/*  516 */     return (data(node)).volume;
/*      */   }
/*      */   private static boolean ghost(TetMesh.Node node) {
/*  519 */     return (node.index < 0);
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
/*      */   private void makeMesh(float[] f, float[] x1, float[] x2, float[] x3) {
/*  538 */     int n = x1.length;
/*  539 */     this._x1min = x1[0]; this._x1max = x1[0];
/*  540 */     this._x2min = x2[0]; this._x2max = x2[0];
/*  541 */     this._x3min = x3[0]; this._x3max = x3[0]; int i;
/*  542 */     for (i = 1; i < n; i++) {
/*  543 */       if (x1[i] < this._x1min) this._x1min = x1[i]; 
/*  544 */       if (x1[i] > this._x1max) this._x1max = x1[i]; 
/*  545 */       if (x2[i] < this._x2min) this._x2min = x2[i]; 
/*  546 */       if (x2[i] > this._x2max) this._x2max = x2[i]; 
/*  547 */       if (x3[i] < this._x3min) this._x3min = x3[i]; 
/*  548 */       if (x3[i] > this._x3max) this._x3max = x3[i];
/*      */     
/*      */     } 
/*      */     
/*  552 */     this._nodes = new TetMesh.Node[n];
/*      */ 
/*      */     
/*  555 */     this._mesh = new TetMesh();
/*  556 */     for (i = 0; i < n; i++) {
/*  557 */       float x1i = x1[i];
/*  558 */       float x2i = x2[i];
/*  559 */       float x3i = x3[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  565 */       if (x1i == this._x1min) x1i -= Math.ulp(x1i); 
/*  566 */       if (x1i == this._x1max) x1i += Math.ulp(x1i); 
/*  567 */       if (x2i == this._x2min) x2i -= Math.ulp(x2i); 
/*  568 */       if (x2i == this._x2max) x2i += Math.ulp(x2i); 
/*  569 */       if (x3i == this._x3min) x3i -= Math.ulp(x3i); 
/*  570 */       if (x3i == this._x3max) x3i += Math.ulp(x3i);
/*      */ 
/*      */       
/*  573 */       TetMesh.Node node = new TetMesh.Node(x1i, x2i, x3i);
/*  574 */       boolean added = this._mesh.addNode(node);
/*  575 */       Check.argument(added, "each sample has unique coordinates");
/*  576 */       NodeData data = new NodeData();
/*  577 */       node.data = data;
/*  578 */       node.index = i;
/*  579 */       if (f != null)
/*  580 */         data.f = f[i]; 
/*  581 */       this._nodes[i] = node;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean usingGradients() {
/*  587 */     return (this._haveGradients && this._gradientPower > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGhostNodes(float[] x, float[] y, float[] z) {
/*  593 */     int ng = x.length;
/*  594 */     for (int ig = 0; ig < ng; ig++) {
/*  595 */       float xg = x[ig];
/*  596 */       float yg = y[ig];
/*  597 */       float zg = z[ig];
/*  598 */       TetMesh.PointLocation pl = this._mesh.locatePoint(xg, yg, zg);
/*  599 */       if (pl.isOutside()) {
/*  600 */         TetMesh.Node n = new TetMesh.Node(xg, yg, zg);
/*  601 */         n.data = new NodeData();
/*  602 */         n.index = -1 - ig;
/*  603 */         this._mesh.addNode(n);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGhostNodesWithValues(float[] x, float[] y, float[] z) {
/*  613 */     int ng = x.length;
/*  614 */     int nn = this._nodes.length;
/*      */ 
/*      */     
/*  617 */     DMatrix[] a = new DMatrix[ng];
/*  618 */     DMatrix[] b = new DMatrix[ng];
/*  619 */     for (int i = 0; i < ng; i++) {
/*  620 */       a[i] = new DMatrix(nn, 4);
/*  621 */       b[i] = new DMatrix(nn, 1);
/*      */     } 
/*  623 */     for (int in = 0; in < nn; in++) {
/*  624 */       TetMesh.Node n = this._nodes[in];
/*  625 */       double fn = f(n);
/*  626 */       double xn = n.xp();
/*  627 */       double yn = n.yp();
/*  628 */       double zn = n.zp();
/*  629 */       for (int j = 0; j < ng; j++) {
/*  630 */         double xg = x[j];
/*  631 */         double yg = y[j];
/*  632 */         double zg = z[j];
/*  633 */         double dx = xn - xg;
/*  634 */         double dy = yn - yg;
/*  635 */         double dz = zn - zg;
/*  636 */         double wn = 1.0D / Math.sqrt(dx * dx + dy * dy + dz * dz);
/*  637 */         a[j].set(in, 0, wn);
/*  638 */         a[j].set(in, 1, wn * dx);
/*  639 */         a[j].set(in, 2, wn * dy);
/*  640 */         a[j].set(in, 3, wn * dz);
/*  641 */         b[j].set(in, 0, wn * fn);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  648 */     for (int ig = 0; ig < ng; ig++) {
/*  649 */       float xg = x[ig];
/*  650 */       float yg = y[ig];
/*  651 */       float zg = z[ig];
/*  652 */       TetMesh.PointLocation pl = this._mesh.locatePoint(xg, yg, zg);
/*  653 */       if (pl.isOutside()) {
/*  654 */         TetMesh.Node n = new TetMesh.Node(xg, yg, zg);
/*  655 */         n.index = -1 - ig;
/*  656 */         this._mesh.addNode(n);
/*  657 */         NodeData data = new NodeData();
/*  658 */         n.data = data;
/*  659 */         DMatrixQrd qrd = new DMatrixQrd(a[ig]);
/*  660 */         if (qrd.isFullRank()) {
/*  661 */           DMatrix s = qrd.solve(b[ig]);
/*  662 */           data.f = (float)s.get(0, 0);
/*  663 */           data.gx = (float)s.get(1, 0);
/*  664 */           data.gy = (float)s.get(2, 0);
/*  665 */           data.gz = (float)s.get(3, 0);
/*      */         } else {
/*  667 */           TetMesh.Node m = this._mesh.findNodeNearest(xg, yg, zg);
/*  668 */           data.f = f(m);
/*  669 */           data.gx = 0.0F;
/*  670 */           data.gy = 0.0F;
/*  671 */           data.gz = 0.0F;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeGhostNodes() {
/*  681 */     ArrayList<TetMesh.Node> gnodes = new ArrayList<>(8);
/*  682 */     TetMesh.NodeIterator ni = this._mesh.getNodes();
/*  683 */     while (ni.hasNext()) {
/*  684 */       TetMesh.Node n = ni.next();
/*  685 */       if (ghost(n)) {
/*  686 */         gnodes.add(n);
/*      */       }
/*      */     } 
/*      */     
/*  690 */     for (TetMesh.Node gnode : gnodes) {
/*  691 */       this._mesh.removeNode(gnode);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private double computeVolumes(float x, float y, float z) {
/*  697 */     if (!getNaturalNabors(x, y, z))
/*  698 */       return 0.0D; 
/*  699 */     return this._va.accumulateVolumes(x, y, z, this._mesh, this._nodeList, this._tetList);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean inBounds(float x1, float x2, float x3) {
/*  704 */     return (!this._useBoundingBox || (this._x1bmn <= x1 && x1 <= this._x1bmx && this._x2bmn <= x2 && x2 <= this._x2bmx && this._x3bmn <= x3 && x3 <= this._x3bmx));
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
/*      */   private boolean getNaturalNabors(float x, float y, float z) {
/*  716 */     this._mesh.clearNodeMarks();
/*  717 */     this._mesh.clearTetMarks();
/*  718 */     this._nodeList.clear();
/*  719 */     this._tetList.clear();
/*  720 */     TetMesh.PointLocation pl = this._mesh.locatePoint(x, y, z);
/*  721 */     if (pl.isOutside())
/*  722 */       return false; 
/*  723 */     addTet(x, y, z, pl.tet());
/*  724 */     return true;
/*      */   }
/*      */   private void addTet(double xp, double yp, double zp, TetMesh.Tet tet) {
/*  727 */     this._mesh.mark(tet);
/*  728 */     this._tetList.add(tet);
/*  729 */     addNode(tet.nodeA());
/*  730 */     addNode(tet.nodeB());
/*  731 */     addNode(tet.nodeC());
/*  732 */     addNode(tet.nodeD());
/*  733 */     TetMesh.Tet ta = tet.tetA();
/*  734 */     TetMesh.Tet tb = tet.tetB();
/*  735 */     TetMesh.Tet tc = tet.tetC();
/*  736 */     TetMesh.Tet td = tet.tetD();
/*  737 */     if (needTet(xp, yp, zp, ta)) addTet(xp, yp, zp, ta); 
/*  738 */     if (needTet(xp, yp, zp, tb)) addTet(xp, yp, zp, tb); 
/*  739 */     if (needTet(xp, yp, zp, tc)) addTet(xp, yp, zp, tc); 
/*  740 */     if (needTet(xp, yp, zp, td)) addTet(xp, yp, zp, td); 
/*      */   }
/*      */   private void addNode(TetMesh.Node node) {
/*  743 */     if (this._mesh.isMarked(node))
/*      */       return; 
/*  745 */     this._mesh.mark(node);
/*  746 */     this._nodeList.add(node);
/*  747 */     NodeData data = data(node);
/*  748 */     data.volume = 0.0D;
/*      */   }
/*      */   private boolean needTet(double xp, double yp, double zp, TetMesh.Tet tet) {
/*  751 */     if (tet == null || this._mesh.isMarked(tet))
/*  752 */       return false; 
/*  753 */     TetMesh.Node na = tet.nodeA();
/*  754 */     TetMesh.Node nb = tet.nodeB();
/*  755 */     TetMesh.Node nc = tet.nodeC();
/*  756 */     TetMesh.Node nd = tet.nodeD();
/*  757 */     double xa = na.xp(), ya = na.yp(), za = na.zp();
/*  758 */     double xb = nb.xp(), yb = nb.yp(), zb = nb.zp();
/*  759 */     double xc = nc.xp(), yc = nc.yp(), zc = nc.zp();
/*  760 */     double xd = nd.xp(), yd = nd.yp(), zd = nd.zp();
/*  761 */     return (Geometry.inSphere(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd, xp, yp, zp) > 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   private float interpolate0(double vsum) {
/*  766 */     double vfsum = 0.0D;
/*  767 */     int nnode = this._nodeList.nnode();
/*  768 */     TetMesh.Node[] nodes = this._nodeList.nodes();
/*  769 */     for (int inode = 0; inode < nnode; inode++) {
/*  770 */       TetMesh.Node node = nodes[inode];
/*  771 */       float f = f(node);
/*  772 */       double v = volume(node);
/*  773 */       vfsum += v * f;
/*      */     } 
/*  775 */     return (float)(vfsum / vsum);
/*      */   }
/*      */ 
/*      */   
/*      */   private float interpolate1(double vsum, double x, double y, double z) {
/*  780 */     int nnode = this._nodeList.nnode();
/*  781 */     TetMesh.Node[] nodes = this._nodeList.nodes();
/*  782 */     double fs = 0.0D;
/*  783 */     double es = 0.0D;
/*  784 */     double wds = 0.0D;
/*  785 */     double wdds = 0.0D;
/*  786 */     double wods = 0.0D;
/*  787 */     for (int inode = 0; inode < nnode; inode++) {
/*  788 */       TetMesh.Node n = nodes[inode];
/*  789 */       double f = f(n);
/*  790 */       double gx = gx(n);
/*  791 */       double gy = gy(n);
/*  792 */       double gz = gz(n);
/*  793 */       double v = volume(n);
/*  794 */       double w = v / vsum;
/*  795 */       double xn = n.xp();
/*  796 */       double yn = n.yp();
/*  797 */       double zn = n.zp();
/*  798 */       double dx = x - xn;
/*  799 */       double dy = y - yn;
/*  800 */       double dz = z - zn;
/*  801 */       double dd = dx * dx + dy * dy + dz * dz;
/*  802 */       if (dd == 0.0D)
/*  803 */         return (float)f; 
/*  804 */       double d = Math.pow(dd, 0.5D * this._gradientPower);
/*  805 */       double wd = w * d;
/*  806 */       double wod = w / d;
/*  807 */       double wdd = w * dd;
/*  808 */       es += wod * (f + gx * dx + gy * dy + gz * dz);
/*  809 */       fs += w * f;
/*  810 */       wds += wd;
/*  811 */       wdds += wdd;
/*  812 */       wods += wod;
/*      */     } 
/*  814 */     es /= wods;
/*  815 */     double alpha = wds / wods;
/*  816 */     double beta = wdds;
/*  817 */     return (float)((alpha * fs + beta * es) / (alpha + beta));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void estimateGradients() {
/*  826 */     int nnode = this._nodes.length;
/*  827 */     for (int inode = 0; inode < nnode; inode++)
/*  828 */       estimateGradient(this._nodes[inode]); 
/*  829 */     this._haveGradients = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void estimateGradient(TetMesh.Node n) {
/*  837 */     NodeData data = data(n);
/*  838 */     double fn = data.f;
/*  839 */     double xn = n.xp();
/*  840 */     double yn = n.yp();
/*  841 */     double zn = n.zp();
/*  842 */     this._mesh.removeNode(n);
/*  843 */     double vsum = computeVolumes((float)xn, (float)yn, (float)zn);
/*  844 */     this._mesh.addNode(n);
/*  845 */     if (vsum > 0.0D) {
/*  846 */       int nm = this._nodeList.nnode();
/*  847 */       TetMesh.Node[] ms = this._nodeList.nodes();
/*  848 */       double hxx = 0.0D, hxy = 0.0D, hxz = 0.0D;
/*  849 */       double hyy = 0.0D, hyz = 0.0D;
/*  850 */       double hzz = 0.0D;
/*  851 */       double px = 0.0D, py = 0.0D, pz = 0.0D;
/*  852 */       double nr = 0.0D;
/*  853 */       for (int im = 0; im < nm; im++) {
/*  854 */         TetMesh.Node m = ms[im];
/*  855 */         if (!ghost(m)) {
/*  856 */           double fm = f(m);
/*  857 */           double wm = volume(m);
/*  858 */           double xm = m.xp();
/*  859 */           double ym = m.yp();
/*  860 */           double zm = m.zp();
/*  861 */           double df = fn - fm;
/*  862 */           double dx = xn - xm;
/*  863 */           double dy = yn - ym;
/*  864 */           double dz = zn - zm;
/*  865 */           double ds = wm / (dx * dx + dy * dy + dz * dz);
/*  866 */           hxx += ds * dx * dx;
/*  867 */           hxy += ds * dx * dy;
/*  868 */           hxz += ds * dx * dz;
/*  869 */           hyy += ds * dy * dy;
/*  870 */           hyz += ds * dy * dz;
/*  871 */           hzz += ds * dz * dz;
/*  872 */           px += ds * dx * df;
/*  873 */           py += ds * dy * df;
/*  874 */           pz += ds * dz * df;
/*  875 */           nr++;
/*      */         } 
/*      */       } 
/*  878 */       if (nr > 2.0D) {
/*  879 */         double lxx = Math.sqrt(hxx);
/*  880 */         double lxy = hxy / lxx;
/*  881 */         double lxz = hxz / lxx;
/*  882 */         double dyy = hyy - lxy * lxy;
/*  883 */         double lyy = Math.sqrt(dyy);
/*  884 */         double lyz = (hyz - lxz * lxy) / lyy;
/*  885 */         double dzz = hzz - lxz * lxz - lyz * lyz;
/*  886 */         double lzz = Math.sqrt(dzz);
/*  887 */         double qx = px / lxx;
/*  888 */         double qy = (py - lxy * qx) / lyy;
/*  889 */         double qz = (pz - lxz * qx - lyz * qy) / lzz;
/*  890 */         double gz = qz / lzz;
/*  891 */         double gy = (qy - lyz * gz) / lyy;
/*  892 */         double gx = (qx - lxy * gy - lxz * gz) / lxx;
/*  893 */         data.gx = (float)gx;
/*  894 */         data.gy = (float)gy;
/*  895 */         data.gz = (float)gz;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class VolumeAccumulator
/*      */   {
/*      */     private double _sum;
/*      */ 
/*      */     
/*      */     private VolumeAccumulator() {}
/*      */ 
/*      */     
/*      */     protected void clear() {
/*  911 */       this._sum = 0.0D;
/*      */     }
/*      */     protected double sum() {
/*  914 */       return this._sum;
/*      */     }
/*      */     protected void accumulate(TetMesh.Node node, double volume) {
/*  917 */       if (SibsonInterpolator3.ghost(node))
/*  918 */         return;  SibsonInterpolator3.NodeData data = SibsonInterpolator3.data(node);
/*  919 */       data.volume += volume;
/*  920 */       this._sum += volume;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract double accumulateVolumes(double param1Double1, double param1Double2, double param1Double3, TetMesh param1TetMesh, TetMesh.NodeList param1NodeList, TetMesh.TetList param1TetList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class WatsonSambridge
/*      */     extends VolumeAccumulator
/*      */   {
/*      */     private double[] _ca;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] _cb;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] _cc;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] _cd;
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] _ct;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private WatsonSambridge() {
/*  962 */       this._ca = new double[3];
/*  963 */       this._cb = new double[3];
/*  964 */       this._cc = new double[3];
/*  965 */       this._cd = new double[3];
/*  966 */       this._ct = new double[3];
/*      */     }
/*  968 */     private double volume(double[] ci, double[] cj, double[] ck, double[] ct) { double xt = ct[0], yt = ct[1], zt = ct[2];
/*  969 */       double xi = ci[0] - xt, yi = ci[1] - yt, zi = ci[2] - zt;
/*  970 */       double xj = cj[0] - xt, yj = cj[1] - yt, zj = cj[2] - zt;
/*  971 */       double xk = ck[0] - xt, yk = ck[1] - yt, zk = ck[2] - zt;
/*  972 */       return xi * (yj * zk - yk * zj) + yi * (zj * xk - zk * xj) + zi * (xj * yk - xk * yj); } public double accumulateVolumes(double xp, double yp, double zp, TetMesh mesh, TetMesh.NodeList nodeList, TetMesh.TetList tetList) { clear();
/*      */       int ntet = tetList.ntet();
/*      */       TetMesh.Tet[] tets = tetList.tets();
/*      */       for (int itet = 0; itet < ntet; itet++) {
/*      */         TetMesh.Tet tet = tets[itet];
/*      */         TetMesh.Node na = tet.nodeA();
/*      */         TetMesh.Node nb = tet.nodeB();
/*      */         TetMesh.Node nc = tet.nodeC();
/*      */         TetMesh.Node nd = tet.nodeD();
/*      */         double xa = na.xp(), ya = na.yp(), za = na.zp();
/*      */         double xb = nb.xp(), yb = nb.yp(), zb = nb.zp();
/*      */         double xc = nc.xp(), yc = nc.yp(), zc = nc.zp();
/*      */         double xd = nd.xp(), yd = nd.yp(), zd = nd.zp();
/*      */         Geometry.centerSphere(xp, yp, zp, xb, yb, zb, xc, yc, zc, xd, yd, zd, this._ca);
/*      */         Geometry.centerSphere(xp, yp, zp, xa, ya, za, xd, yd, zd, xc, yc, zc, this._cb);
/*      */         Geometry.centerSphere(xp, yp, zp, xa, ya, za, xb, yb, zb, xd, yd, zd, this._cc);
/*      */         Geometry.centerSphere(xp, yp, zp, xa, ya, za, xc, yc, zc, xb, yb, zb, this._cd);
/*      */         Geometry.centerSphere(xa, ya, za, xb, yb, zb, xc, yc, zc, xd, yd, zd, this._ct);
/*      */         double va = volume(this._cb, this._cc, this._cd, this._ct);
/*      */         double vb = volume(this._ca, this._cd, this._cc, this._ct);
/*      */         double vc = volume(this._ca, this._cb, this._cd, this._ct);
/*      */         double vd = volume(this._ca, this._cc, this._cb, this._ct);
/*      */         accumulate(na, va);
/*      */         accumulate(nb, vb);
/*      */         accumulate(nc, vc);
/*      */         accumulate(nd, vd);
/*      */       } 
/*      */       return sum(); } } private static class BraunSambridge extends VolumeAccumulator { private LasserreVolume _lv; public double accumulateVolumes(double x1i, double x2i, double x3i, TetMesh mesh, TetMesh.NodeList nodeList, TetMesh.TetList tetList) {
/*      */       clear();
/*      */       int nnode = nodeList.nnode();
/*      */       TetMesh.Node[] nodes = nodeList.nodes();
/*      */       for (int j = 0; j < nnode; j++) {
/*      */         TetMesh.Node jnode = nodes[j];
/*      */         double x1j = jnode.xp();
/*      */         double x2j = jnode.yp();
/*      */         double x3j = jnode.zp();
/*      */         this._lv.clear();
/*      */         double x1s = 0.5D * (x1j + x1i);
/*      */         double x2s = 0.5D * (x2j + x2i);
/*      */         double x3s = 0.5D * (x3j + x3i);
/*      */         double x1d = x1j - x1i;
/*      */         double x2d = x2j - x2i;
/*      */         double x3d = x3j - x3i;
/*      */         this._lv.addHalfSpace(x1d, x2d, x3d, 0.0D);
/*      */         for (int k = 0; k < nnode; k++) {
/*      */           if (j != k) {
/*      */             TetMesh.Node knode = nodes[k];
/*      */             if (mesh.findTet(jnode, knode) != null) {
/*      */               double x1k = knode.xp();
/*      */               double x2k = knode.yp();
/*      */               double x3k = knode.zp();
/*      */               double x1e = x1k - x1j;
/*      */               double x2e = x2k - x2j;
/*      */               double x3e = x3k - x3j;
/*      */               double x1t = 0.5D * (x1k + x1j) - x1s;
/*      */               double x2t = 0.5D * (x2k + x2j) - x2s;
/*      */               double x3t = 0.5D * (x3k + x3j) - x3s;
/*      */               this._lv.addHalfSpace(x1e, x2e, x3e, x1e * x1t + x2e * x2t + x3e * x3t);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         double vj = this._lv.getVolume();
/*      */         accumulate(jnode, vj);
/*      */       } 
/*      */       return sum();
/*      */     }
/*      */     private BraunSambridge() {
/* 1039 */       this._lv = new LasserreVolume(3);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class HaleLiang
/*      */     extends VolumeAccumulator
/*      */   {
/*      */     private FaceList _faceList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double[] _xyz;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private HaleLiang() {
/* 1064 */       this._faceList = new FaceList();
/* 1065 */       this._xyz = new double[3];
/*      */     } public double accumulateVolumes(double xp, double yp, double zp, TetMesh mesh, TetMesh.NodeList nodeList, TetMesh.TetList tetList) {
/*      */       clear();
/*      */       processTets(xp, yp, zp, mesh, tetList);
/*      */       boolean ok = processFaces(xp, yp, zp);
/*      */       return ok ? sum() : 0.0D;
/*      */     } private void processTets(double xp, double yp, double zp, TetMesh mesh, TetMesh.TetList tetList) {
/* 1072 */       this._faceList.clear();
/* 1073 */       int ntet = tetList.ntet();
/* 1074 */       TetMesh.Tet[] tets = tetList.tets();
/* 1075 */       for (int itet = 0; itet < ntet; itet++) {
/* 1076 */         TetMesh.Tet tet = tets[itet];
/* 1077 */         TetMesh.Tet ta = tet.tetA();
/* 1078 */         TetMesh.Tet tb = tet.tetB();
/* 1079 */         TetMesh.Tet tc = tet.tetC();
/* 1080 */         TetMesh.Tet td = tet.tetD();
/* 1081 */         TetMesh.Node na = tet.nodeA();
/* 1082 */         TetMesh.Node nb = tet.nodeB();
/* 1083 */         TetMesh.Node nc = tet.nodeC();
/* 1084 */         TetMesh.Node nd = tet.nodeD();
/* 1085 */         tet.centerSphere(this._xyz);
/* 1086 */         double xt = this._xyz[0] - xp, yt = this._xyz[1] - yp, zt = this._xyz[2] - zp;
/* 1087 */         processTetNabor(xp, yp, zp, xt, yt, zt, mesh, ta, nb, nc, nd);
/* 1088 */         processTetNabor(xp, yp, zp, xt, yt, zt, mesh, tb, nc, na, nd);
/* 1089 */         processTetNabor(xp, yp, zp, xt, yt, zt, mesh, tc, nd, na, nb);
/* 1090 */         processTetNabor(xp, yp, zp, xt, yt, zt, mesh, td, na, nc, nb);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processTetNabor(double xp, double yp, double zp, double xt, double yt, double zt, TetMesh mesh, TetMesh.Tet ta, TetMesh.Node nb, TetMesh.Node nc, TetMesh.Node nd) {
/* 1099 */       boolean saveFace = true;
/* 1100 */       if (ta != null && mesh.isMarked(ta)) {
/* 1101 */         ta.centerSphere(this._xyz);
/* 1102 */         double xa = this._xyz[0] - xp, ya = this._xyz[1] - yp, za = this._xyz[2] - zp;
/* 1103 */         double xb = nb.xp() - xp, yb = nb.yp() - yp, zb = nb.zp() - zp;
/* 1104 */         double xd = nd.xp() - xp, yd = nd.yp() - yp, zd = nd.zp() - zp;
/* 1105 */         double xc = nc.xp() - xp, yc = nc.yp() - yp, zc = nc.zp() - zp;
/* 1106 */         double xbd = xb + xd, ybd = yb + yd, zbd = zb + zd;
/* 1107 */         double xdc = xd + xc, ydc = yd + yc, zdc = zd + zc;
/* 1108 */         double xcb = xc + xb, ycb = yc + yb, zcb = zc + zb;
/* 1109 */         double xyz = yt * za - ya * zt, yzx = zt * xa - za * xt, zxy = xt * ya - xa * yt;
/* 1110 */         accumulate(nb, xbd * xyz + ybd * yzx + zbd * zxy);
/* 1111 */         accumulate(nd, xdc * xyz + ydc * yzx + zdc * zxy);
/* 1112 */         accumulate(nc, xcb * xyz + ycb * yzx + zcb * zxy);
/* 1113 */         saveFace = false;
/*      */       } 
/* 1115 */       if (saveFace)
/* 1116 */         addFace(xp, yp, zp, xt, yt, zt, nb, nc, nd); 
/*      */     }
/*      */     private static class Face {
/*      */       TetMesh.Node na;
/*      */       TetMesh.Node nb;
/*      */       TetMesh.Node nc;
/*      */       double xf;
/*      */       double yf;
/*      */       double zf;
/*      */       double xr;
/*      */       double yr;
/*      */       double zr;
/*      */       Face fa;
/*      */       Face fb;
/*      */       Face fc;
/*      */       
/*      */       private Face() {} }
/*      */     
/*      */     private void addFace(double xp, double yp, double zp, double xr, double yr, double zr, TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc) {
/* 1135 */       double xa = na.xp(), ya = na.yp(), za = na.zp();
/* 1136 */       double xb = nb.xp(), yb = nb.yp(), zb = nb.zp();
/* 1137 */       double xc = nc.xp(), yc = nc.yp(), zc = nc.zp();
/* 1138 */       Geometry.centerSphere(xp, yp, zp, xa, ya, za, xb, yb, zb, xc, yc, zc, this._xyz);
/* 1139 */       double xf = this._xyz[0] - xp, yf = this._xyz[1] - yp, zf = this._xyz[2] - zp;
/* 1140 */       this._faceList.add(na, nb, nc, xf, yf, zf, xr, yr, zr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean faceNaborsOk() {
/* 1147 */       int nface = this._faceList.nface();
/* 1148 */       ArrayList<Face> faces = this._faceList.faces();
/* 1149 */       for (int iface = 0; iface < nface; iface++) {
/* 1150 */         Face face = faces.get(iface);
/* 1151 */         if (face.fa == null || face.fb == null || face.fc == null)
/* 1152 */           return false; 
/*      */       } 
/* 1154 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean processFaces(double xp, double yp, double zp) {
/* 1159 */       if (!faceNaborsOk())
/* 1160 */         return false; 
/* 1161 */       int nface = this._faceList.nface();
/* 1162 */       ArrayList<Face> faces = this._faceList.faces();
/* 1163 */       for (int iface = 0; iface < nface; iface++)
/* 1164 */         processFace(xp, yp, zp, faces.get(iface)); 
/* 1165 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processFace(double xp, double yp, double zp, Face face) {
/* 1172 */       TetMesh.Node na = face.na, nb = face.nb, nc = face.nc;
/* 1173 */       double xa = na.xp() - xp, ya = na.yp() - yp, za = na.zp() - zp;
/* 1174 */       double xb = nb.xp() - xp, yb = nb.yp() - yp, zb = nb.zp() - zp;
/* 1175 */       double xc = nc.xp() - xp, yc = nc.yp() - yp, zc = nc.zp() - zp;
/*      */ 
/*      */       
/* 1178 */       double xab = xa + xb, yab = ya + yb, zab = za + zb;
/* 1179 */       double xbc = xb + xc, ybc = yb + yc, zbc = zb + zc;
/* 1180 */       double xca = xc + xa, yca = yc + ya, zca = zc + za;
/*      */ 
/*      */       
/* 1183 */       double x1 = face.xf, y1 = face.yf, z1 = face.zf;
/* 1184 */       double x2 = face.xr, y2 = face.yr, z2 = face.zr;
/* 1185 */       double xyz = y1 * z2 - y2 * z1, yzx = z1 * x2 - z2 * x1, zxy = x1 * y2 - x2 * y1;
/* 1186 */       double vab = xab * xyz + yab * yzx + zab * zxy;
/* 1187 */       double vbc = xbc * xyz + ybc * yzx + zbc * zxy;
/* 1188 */       double vca = xca * xyz + yca * yzx + zca * zxy;
/* 1189 */       accumulate(na, vab); accumulate(nb, -vab);
/* 1190 */       accumulate(nb, vbc); accumulate(nc, -vbc);
/* 1191 */       accumulate(nc, vca); accumulate(na, -vca);
/*      */ 
/*      */       
/* 1194 */       Face fa = face.fa; x2 = fa.xf; y2 = fa.yf; z2 = fa.zf;
/* 1195 */       xyz = y1 * z2 - y2 * z1; yzx = z1 * x2 - z2 * x1; zxy = x1 * y2 - x2 * y1;
/* 1196 */       accumulate(nb, xb * xyz + yb * yzx + zb * zxy);
/* 1197 */       accumulate(nc, xbc * xyz + ybc * yzx + zbc * zxy);
/* 1198 */       Face fb = face.fb; x2 = fb.xf; y2 = fb.yf; z2 = fb.zf;
/* 1199 */       xyz = y1 * z2 - y2 * z1; yzx = z1 * x2 - z2 * x1; zxy = x1 * y2 - x2 * y1;
/* 1200 */       accumulate(nc, xc * xyz + yc * yzx + zc * zxy);
/* 1201 */       accumulate(na, xca * xyz + yca * yzx + zca * zxy);
/* 1202 */       Face fc = face.fc; x2 = fc.xf; y2 = fc.yf; z2 = fc.zf;
/* 1203 */       xyz = y1 * z2 - y2 * z1; yzx = z1 * x2 - z2 * x1; zxy = x1 * y2 - x2 * y1;
/* 1204 */       accumulate(na, xa * xyz + ya * yzx + za * zxy);
/* 1205 */       accumulate(nb, xab * xyz + yab * yzx + zab * zxy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class FaceList
/*      */     {
/*      */       private int _nface;
/*      */ 
/*      */       
/* 1216 */       private ArrayList<SibsonInterpolator3.HaleLiang.Face> _faces = new ArrayList<>(48);
/*      */       int nface() {
/* 1218 */         return this._nface;
/*      */       }
/*      */       ArrayList<SibsonInterpolator3.HaleLiang.Face> faces() {
/* 1221 */         return this._faces;
/*      */       }
/*      */       void clear() {
/* 1224 */         this._nface = 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void add(TetMesh.Node na, TetMesh.Node nb, TetMesh.Node nc, double xf, double yf, double zf, double xr, double yr, double zr) {
/* 1231 */         if (this._nface == this._faces.size())
/* 1232 */           this._faces.add(new SibsonInterpolator3.HaleLiang.Face()); 
/* 1233 */         SibsonInterpolator3.HaleLiang.Face face = this._faces.get(this._nface);
/* 1234 */         int nfound = 0;
/* 1235 */         SibsonInterpolator3.HaleLiang.Face fa = null, fb = null, fc = null;
/* 1236 */         for (int iface = 0; iface < this._nface && nfound < 3; iface++) {
/* 1237 */           SibsonInterpolator3.HaleLiang.Face fi = this._faces.get(iface);
/* 1238 */           TetMesh.Node nai = fi.na, nbi = fi.nb, nci = fi.nc;
/* 1239 */           if (fa == null) {
/* 1240 */             if (nb == nci && nc == nbi) {
/* 1241 */               fa = fi; fi.fa = face; nfound++;
/* 1242 */             } else if (nb == nbi && nc == nai) {
/* 1243 */               fa = fi; fi.fc = face; nfound++;
/* 1244 */             } else if (nb == nai && nc == nci) {
/* 1245 */               fa = fi; fi.fb = face; nfound++;
/*      */             } 
/*      */           }
/* 1248 */           if (fb == null) {
/* 1249 */             if (nc == nai && na == nci) {
/* 1250 */               fb = fi; fi.fb = face; nfound++;
/* 1251 */             } else if (nc == nci && na == nbi) {
/* 1252 */               fb = fi; fi.fa = face; nfound++;
/* 1253 */             } else if (nc == nbi && na == nai) {
/* 1254 */               fb = fi; fi.fc = face; nfound++;
/*      */             } 
/*      */           }
/* 1257 */           if (fc == null) {
/* 1258 */             if (na == nbi && nb == nai) {
/* 1259 */               fc = fi; fi.fc = face; nfound++;
/* 1260 */             } else if (na == nai && nb == nci) {
/* 1261 */               fc = fi; fi.fb = face; nfound++;
/* 1262 */             } else if (na == nci && nb == nbi) {
/* 1263 */               fc = fi; fi.fa = face; nfound++;
/*      */             } 
/*      */           }
/*      */         } 
/* 1267 */         face.na = na; face.nb = nb; face.nc = nc;
/* 1268 */         face.xf = xf; face.yf = yf; face.zf = zf;
/* 1269 */         face.xr = xr; face.yr = yr; face.zr = zr;
/* 1270 */         face.fa = fa; face.fb = fb; face.fc = fc;
/* 1271 */         this._nface++;
/*      */       }
/*      */       
/*      */       private FaceList() {}
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SibsonInterpolator3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
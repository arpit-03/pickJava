/*      */ package edu.mines.jtk.interp;
/*      */ 
/*      */ import edu.mines.jtk.dsp.Sampling;
/*      */ import edu.mines.jtk.la.DMatrix;
/*      */ import edu.mines.jtk.la.DMatrixQrd;
/*      */ import edu.mines.jtk.mesh.Geometry;
/*      */ import edu.mines.jtk.mesh.TriMesh;
/*      */ import edu.mines.jtk.util.Check;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SibsonInterpolator2
/*      */ {
/*      */   private TriMesh _mesh;
/*      */   private TriMesh.Node[] _nodes;
/*      */   private TriMesh.NodeList _nodeList;
/*      */   private TriMesh.TriList _triList;
/*      */   private AreaAccumulator _va;
/*      */   private boolean _haveGradients;
/*      */   private double _gradientPower;
/*      */   private float _fnull;
/*      */   private float _x1min;
/*      */   private float _x1max;
/*      */   private float _x2min;
/*      */   private float _x2max;
/*      */   private float _x1bmn;
/*      */   private float _x1bmx;
/*      */   private float _x2bmn;
/*      */   private float _x2bmx;
/*      */   private boolean _useBoundingBox;
/*      */   
/*      */   public enum Method
/*      */   {
/*  103 */     HALE_LIANG,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  111 */     BRAUN_SAMBRIDGE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     WATSON_SAMBRIDGE;
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
/*  134 */       this.index = index;
/*  135 */       this.weight = weight;
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
/*      */   public SibsonInterpolator2(float[] x1, float[] x2) {
/*  148 */     this(Method.HALE_LIANG, null, x1, x2);
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
/*      */   public SibsonInterpolator2(float[] f, float[] x1, float[] x2) {
/*  160 */     this(Method.HALE_LIANG, f, x1, x2);
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
/*      */   public SibsonInterpolator2(Method method, float[] x1, float[] x2) {
/*  175 */     this(method, null, x1, x2);
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
/*      */   public SibsonInterpolator2(Method method, float[] f, float[] x1, float[] x2) {
/*  192 */     makeMesh(f, x1, x2);
/*  193 */     this._nodeList = new TriMesh.NodeList();
/*  194 */     this._triList = new TriMesh.TriList();
/*  195 */     if (method == Method.WATSON_SAMBRIDGE) {
/*  196 */       this._va = new WatsonSambridge();
/*  197 */     } else if (method == Method.BRAUN_SAMBRIDGE) {
/*  198 */       this._va = new BraunSambridge();
/*  199 */     } else if (method == Method.HALE_LIANG) {
/*  200 */       this._va = new HaleLiang();
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
/*      */   public void setSamples(float[] f, float[] x1, float[] x2) {
/*  212 */     makeMesh(f, x1, x2);
/*  213 */     this._haveGradients = false;
/*  214 */     if (this._gradientPower > 0.0D) {
/*  215 */       estimateGradients();
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
/*      */   public void setNullValue(float fnull) {
/*  227 */     this._fnull = fnull;
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
/*      */   public void setGradients(float[] g1, float[] g2) {
/*  239 */     int n = g1.length;
/*  240 */     for (int i = 0; i < n; i++) {
/*  241 */       TriMesh.Node node = this._nodes[i];
/*  242 */       NodeData data = data(node);
/*  243 */       data.gx = g1[i];
/*  244 */       data.gy = g2[i];
/*      */     } 
/*  246 */     this._haveGradients = true;
/*  247 */     if (this._gradientPower == 0.0D) {
/*  248 */       this._gradientPower = 1.0D;
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
/*  266 */     if (!this._haveGradients && gradientPower > 0.0D)
/*  267 */       estimateGradients(); 
/*  268 */     this._gradientPower = gradientPower;
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
/*      */   public void setBounds(float x1min, float x1max, float x2min, float x2max) {
/*  286 */     Check.argument((x1min < x1max), "x1min<x1max");
/*  287 */     Check.argument((x2min < x2max), "x2min<x2max");
/*      */ 
/*      */     
/*  290 */     this._x1bmn = x1min; this._x1bmx = x1max;
/*  291 */     this._x2bmn = x2min; this._x2bmx = x2max;
/*  292 */     this._useBoundingBox = true;
/*      */ 
/*      */     
/*  295 */     float scale = 1.0F;
/*  296 */     float x1avg = 0.5F * (x1min + x1max);
/*  297 */     float x2avg = 0.5F * (x2min + x2max);
/*  298 */     float x1dif = Math.max(x1max - this._x1min, this._x1max - x1min);
/*  299 */     float x2dif = Math.max(x2max - this._x2min, this._x2max - x2min);
/*  300 */     float x1pad = scale * x1dif;
/*  301 */     float x2pad = scale * x2dif;
/*  302 */     x1min -= x1pad; x1max += x1pad;
/*  303 */     x2min -= x2pad; x2max += x2pad;
/*  304 */     float[] x1g = { x1min, x1max, x1avg, x1avg };
/*  305 */     float[] x2g = { x2avg, x2avg, x2min, x2max };
/*  306 */     addGhostNodes(x1g, x2g);
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
/*      */   public void setBounds(Sampling s1, Sampling s2) {
/*  321 */     setBounds((float)s1.getFirst(), (float)s1.getLast(), 
/*  322 */         (float)s2.getFirst(), (float)s2.getLast());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void useConvexHullBounds() {
/*  330 */     this._useBoundingBox = false;
/*  331 */     removeGhostNodes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float interpolate(float x1, float x2) {
/*  341 */     if (!inBounds(x1, x2))
/*  342 */       return this._fnull; 
/*  343 */     double asum = computeAreas(x1, x2);
/*  344 */     if (asum <= 0.0D)
/*  345 */       return this._fnull; 
/*  346 */     if (usingGradients()) {
/*  347 */       return interpolate1(asum, x1, x2);
/*      */     }
/*  349 */     return interpolate0(asum);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[][] interpolate(Sampling s1, Sampling s2) {
/*  360 */     int n1 = s1.getCount();
/*  361 */     int n2 = s2.getCount();
/*  362 */     float[][] f = new float[n2][n1];
/*  363 */     for (int i2 = 0; i2 < n2; i2++) {
/*  364 */       float x2 = (float)s2.getValue(i2);
/*  365 */       for (int i1 = 0; i1 < n1; i1++) {
/*  366 */         float x1 = (float)s1.getValue(i1);
/*  367 */         f[i2][i1] = interpolate(x1, x2);
/*      */       } 
/*      */     } 
/*  370 */     return f;
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
/*      */   public IndexWeight[] getIndexWeights(float x1, float x2) {
/*  388 */     if (!inBounds(x1, x2))
/*  389 */       return null; 
/*  390 */     float wsum = (float)computeAreas(x1, x2);
/*  391 */     if (wsum == 0.0F)
/*  392 */       return null; 
/*  393 */     float wscl = 1.0F / wsum;
/*  394 */     int nnode = this._nodeList.nnode();
/*  395 */     TriMesh.Node[] nodes = this._nodeList.nodes();
/*  396 */     IndexWeight[] iw = new IndexWeight[nnode];
/*  397 */     for (int inode = 0; inode < nnode; inode++) {
/*  398 */       TriMesh.Node node = nodes[inode];
/*  399 */       int i = node.index;
/*  400 */       float w = (float)area(node) * wscl;
/*  401 */       iw[inode] = new IndexWeight(i, w);
/*      */     } 
/*  403 */     return iw;
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
/*  422 */     return validate(new int[] { i })[0];
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
/*  441 */     int nv = i.length;
/*  442 */     for (int iv = 0; iv < nv; iv++)
/*  443 */       this._mesh.removeNode(this._nodes[i[iv]]); 
/*  444 */     float[] fv = new float[nv]; int j;
/*  445 */     for (j = 0; j < nv; j++) {
/*  446 */       TriMesh.Node node = this._nodes[i[j]];
/*  447 */       float xn = node.x();
/*  448 */       float yn = node.y();
/*  449 */       fv[j] = interpolate(xn, yn);
/*      */     } 
/*  451 */     for (j = 0; j < nv; j++)
/*  452 */       this._mesh.addNode(this._nodes[i[j]]); 
/*  453 */     return fv;
/*      */   }
/*      */   
/*      */   private static class NodeData {
/*      */     float f;
/*      */     float gx;
/*      */     float gy;
/*      */     double area;
/*      */     
/*      */     private NodeData() {} }
/*      */   
/*      */   private static NodeData data(TriMesh.Node node) {
/*  465 */     return (NodeData)node.data;
/*      */   }
/*      */   private static float f(TriMesh.Node node) {
/*  468 */     return (data(node)).f;
/*      */   }
/*      */   private static float gx(TriMesh.Node node) {
/*  471 */     return (data(node)).gx;
/*      */   }
/*      */   private static float gy(TriMesh.Node node) {
/*  474 */     return (data(node)).gy;
/*      */   }
/*      */   private static double area(TriMesh.Node node) {
/*  477 */     return (data(node)).area;
/*      */   }
/*      */   private static boolean ghost(TriMesh.Node node) {
/*  480 */     return (node.index < 0);
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
/*      */   private void makeMesh(float[] f, float[] x1, float[] x2) {
/*  499 */     int n = x1.length;
/*  500 */     this._x1min = x1[0]; this._x1max = x1[0];
/*  501 */     this._x2min = x2[0]; this._x2max = x2[0]; int i;
/*  502 */     for (i = 1; i < n; i++) {
/*  503 */       if (x1[i] < this._x1min) this._x1min = x1[i]; 
/*  504 */       if (x1[i] > this._x1max) this._x1max = x1[i]; 
/*  505 */       if (x2[i] < this._x2min) this._x2min = x2[i]; 
/*  506 */       if (x2[i] > this._x2max) this._x2max = x2[i];
/*      */     
/*      */     } 
/*      */     
/*  510 */     this._nodes = new TriMesh.Node[n];
/*      */ 
/*      */     
/*  513 */     this._mesh = new TriMesh();
/*  514 */     for (i = 0; i < n; i++) {
/*  515 */       float x1i = x1[i];
/*  516 */       float x2i = x2[i];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  522 */       if (x1i == this._x1min) x1i -= Math.ulp(x1i); 
/*  523 */       if (x1i == this._x1max) x1i += Math.ulp(x1i); 
/*  524 */       if (x2i == this._x2min) x2i -= Math.ulp(x2i); 
/*  525 */       if (x2i == this._x2max) x2i += Math.ulp(x2i);
/*      */ 
/*      */       
/*  528 */       TriMesh.Node node = new TriMesh.Node(x1i, x2i);
/*  529 */       boolean added = this._mesh.addNode(node);
/*  530 */       Check.argument(added, "each sample has unique coordinates");
/*  531 */       NodeData data = new NodeData();
/*  532 */       node.data = data;
/*  533 */       node.index = i;
/*  534 */       if (f != null)
/*  535 */         data.f = f[i]; 
/*  536 */       this._nodes[i] = node;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean usingGradients() {
/*  542 */     return (this._haveGradients && this._gradientPower > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGhostNodes(float[] x, float[] y) {
/*  548 */     int ng = x.length;
/*  549 */     for (int ig = 0; ig < ng; ig++) {
/*  550 */       float xg = x[ig];
/*  551 */       float yg = y[ig];
/*  552 */       TriMesh.PointLocation pl = this._mesh.locatePoint(xg, yg);
/*  553 */       if (pl.isOutside()) {
/*  554 */         TriMesh.Node n = new TriMesh.Node(xg, yg);
/*  555 */         n.data = new NodeData();
/*  556 */         n.index = -1 - ig;
/*  557 */         this._mesh.addNode(n);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addGhostNodesWithValues(float[] x, float[] y) {
/*  567 */     int ng = x.length;
/*  568 */     int nn = this._nodes.length;
/*      */ 
/*      */     
/*  571 */     DMatrix[] a = new DMatrix[ng];
/*  572 */     DMatrix[] b = new DMatrix[ng];
/*  573 */     for (int i = 0; i < ng; i++) {
/*  574 */       a[i] = new DMatrix(nn, 3);
/*  575 */       b[i] = new DMatrix(nn, 1);
/*      */     } 
/*  577 */     for (int in = 0; in < nn; in++) {
/*  578 */       TriMesh.Node n = this._nodes[in];
/*  579 */       double fn = f(n);
/*  580 */       double xn = n.xp();
/*  581 */       double yn = n.yp();
/*  582 */       for (int j = 0; j < ng; j++) {
/*  583 */         double xg = x[j];
/*  584 */         double yg = y[j];
/*  585 */         double dx = xn - xg;
/*  586 */         double dy = yn - yg;
/*  587 */         double wn = 1.0D / Math.sqrt(dx * dx + dy * dy);
/*  588 */         a[j].set(in, 0, wn);
/*  589 */         a[j].set(in, 1, wn * dx);
/*  590 */         a[j].set(in, 2, wn * dy);
/*  591 */         b[j].set(in, 0, wn * fn);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     for (int ig = 0; ig < ng; ig++) {
/*  599 */       float xg = x[ig];
/*  600 */       float yg = y[ig];
/*  601 */       TriMesh.PointLocation pl = this._mesh.locatePoint(xg, yg);
/*  602 */       if (pl.isOutside()) {
/*  603 */         TriMesh.Node n = new TriMesh.Node(xg, yg);
/*  604 */         n.index = -1 - ig;
/*  605 */         this._mesh.addNode(n);
/*  606 */         NodeData data = new NodeData();
/*  607 */         n.data = data;
/*  608 */         DMatrixQrd qrd = new DMatrixQrd(a[ig]);
/*  609 */         if (qrd.isFullRank()) {
/*  610 */           DMatrix s = qrd.solve(b[ig]);
/*  611 */           data.f = (float)s.get(0, 0);
/*  612 */           data.gx = (float)s.get(1, 0);
/*  613 */           data.gy = (float)s.get(2, 0);
/*      */         } else {
/*  615 */           TriMesh.Node m = this._mesh.findNodeNearest(xg, yg);
/*  616 */           data.f = f(m);
/*  617 */           data.gx = 0.0F;
/*  618 */           data.gy = 0.0F;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeGhostNodes() {
/*  628 */     ArrayList<TriMesh.Node> gnodes = new ArrayList<>(4);
/*  629 */     TriMesh.NodeIterator ni = this._mesh.getNodes();
/*  630 */     while (ni.hasNext()) {
/*  631 */       TriMesh.Node n = ni.next();
/*  632 */       if (ghost(n)) {
/*  633 */         gnodes.add(n);
/*      */       }
/*      */     } 
/*      */     
/*  637 */     for (TriMesh.Node gnode : gnodes) {
/*  638 */       this._mesh.removeNode(gnode);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private double computeAreas(float x, float y) {
/*  644 */     if (!getNaturalNabors(x, y))
/*  645 */       return 0.0D; 
/*  646 */     return this._va.accumulateAreas(x, y, this._mesh, this._nodeList, this._triList);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean inBounds(float x1, float x2) {
/*  651 */     return (!this._useBoundingBox || (this._x1bmn <= x1 && x1 <= this._x1bmx && this._x2bmn <= x2 && x2 <= this._x2bmx));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean getNaturalNabors(float x, float y) {
/*  662 */     this._mesh.clearNodeMarks();
/*  663 */     this._mesh.clearTriMarks();
/*  664 */     this._nodeList.clear();
/*  665 */     this._triList.clear();
/*  666 */     TriMesh.PointLocation pl = this._mesh.locatePoint(x, y);
/*  667 */     if (pl.isOutside())
/*  668 */       return false; 
/*  669 */     addTri(x, y, pl.tri());
/*  670 */     return true;
/*      */   }
/*      */   private void addTri(double xp, double yp, TriMesh.Tri tri) {
/*  673 */     this._mesh.mark(tri);
/*  674 */     this._triList.add(tri);
/*  675 */     addNode(tri.nodeA());
/*  676 */     addNode(tri.nodeB());
/*  677 */     addNode(tri.nodeC());
/*  678 */     TriMesh.Tri ta = tri.triA();
/*  679 */     TriMesh.Tri tb = tri.triB();
/*  680 */     TriMesh.Tri tc = tri.triC();
/*  681 */     if (needTri(xp, yp, ta)) addTri(xp, yp, ta); 
/*  682 */     if (needTri(xp, yp, tb)) addTri(xp, yp, tb); 
/*  683 */     if (needTri(xp, yp, tc)) addTri(xp, yp, tc); 
/*      */   }
/*      */   private void addNode(TriMesh.Node node) {
/*  686 */     if (this._mesh.isMarked(node))
/*      */       return; 
/*  688 */     this._mesh.mark(node);
/*  689 */     this._nodeList.add(node);
/*  690 */     NodeData data = data(node);
/*  691 */     data.area = 0.0D;
/*      */   }
/*      */   private boolean needTri(double xp, double yp, TriMesh.Tri tri) {
/*  694 */     if (tri == null || this._mesh.isMarked(tri))
/*  695 */       return false; 
/*  696 */     TriMesh.Node na = tri.nodeA();
/*  697 */     TriMesh.Node nb = tri.nodeB();
/*  698 */     TriMesh.Node nc = tri.nodeC();
/*  699 */     double xa = na.xp(), ya = na.yp();
/*  700 */     double xb = nb.xp(), yb = nb.yp();
/*  701 */     double xc = nc.xp(), yc = nc.yp();
/*  702 */     return (Geometry.inCircle(xa, ya, xb, yb, xc, yc, xp, yp) > 0.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   private float interpolate0(double asum) {
/*  707 */     double afsum = 0.0D;
/*  708 */     int nnode = this._nodeList.nnode();
/*  709 */     TriMesh.Node[] nodes = this._nodeList.nodes();
/*  710 */     for (int inode = 0; inode < nnode; inode++) {
/*  711 */       TriMesh.Node node = nodes[inode];
/*  712 */       float f = f(node);
/*  713 */       double a = area(node);
/*  714 */       afsum += a * f;
/*      */     } 
/*  716 */     return (float)(afsum / asum);
/*      */   }
/*      */ 
/*      */   
/*      */   private float interpolate1(double asum, double x, double y) {
/*  721 */     int nnode = this._nodeList.nnode();
/*  722 */     TriMesh.Node[] nodes = this._nodeList.nodes();
/*  723 */     double fs = 0.0D;
/*  724 */     double es = 0.0D;
/*  725 */     double wds = 0.0D;
/*  726 */     double wdds = 0.0D;
/*  727 */     double wods = 0.0D;
/*  728 */     for (int inode = 0; inode < nnode; inode++) {
/*  729 */       TriMesh.Node n = nodes[inode];
/*  730 */       double f = f(n);
/*  731 */       double gx = gx(n);
/*  732 */       double gy = gy(n);
/*  733 */       double a = area(n);
/*  734 */       double w = a / asum;
/*  735 */       double xn = n.xp();
/*  736 */       double yn = n.yp();
/*  737 */       double dx = x - xn;
/*  738 */       double dy = y - yn;
/*  739 */       double dd = dx * dx + dy * dy;
/*  740 */       if (dd == 0.0D)
/*  741 */         return (float)f; 
/*  742 */       double d = Math.pow(dd, 0.5D * this._gradientPower);
/*  743 */       double wd = w * d;
/*  744 */       double wod = w / d;
/*  745 */       double wdd = w * dd;
/*  746 */       es += wod * (f + gx * dx + gy * dy);
/*  747 */       fs += w * f;
/*  748 */       wds += wd;
/*  749 */       wdds += wdd;
/*  750 */       wods += wod;
/*      */     } 
/*  752 */     es /= wods;
/*  753 */     double alpha = wds / wods;
/*  754 */     double beta = wdds;
/*  755 */     return (float)((alpha * fs + beta * es) / (alpha + beta));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void estimateGradients() {
/*  764 */     int nnode = this._nodes.length;
/*  765 */     for (int inode = 0; inode < nnode; inode++)
/*  766 */       estimateGradient(this._nodes[inode]); 
/*  767 */     this._haveGradients = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void estimateGradient(TriMesh.Node n) {
/*  775 */     NodeData data = data(n);
/*  776 */     double fn = data.f;
/*  777 */     double xn = n.xp();
/*  778 */     double yn = n.yp();
/*  779 */     this._mesh.removeNode(n);
/*  780 */     double asum = computeAreas((float)xn, (float)yn);
/*  781 */     this._mesh.addNode(n);
/*  782 */     if (asum > 0.0D) {
/*  783 */       int nm = this._nodeList.nnode();
/*  784 */       TriMesh.Node[] ms = this._nodeList.nodes();
/*  785 */       double hxx = 0.0D, hxy = 0.0D, hyy = 0.0D;
/*  786 */       double px = 0.0D, py = 0.0D;
/*  787 */       double nr = 0.0D;
/*  788 */       for (int im = 0; im < nm; im++) {
/*  789 */         TriMesh.Node m = ms[im];
/*  790 */         if (!ghost(m)) {
/*  791 */           double fm = f(m);
/*  792 */           double wm = area(m);
/*  793 */           double xm = m.xp();
/*  794 */           double ym = m.yp();
/*  795 */           double df = fn - fm;
/*  796 */           double dx = xn - xm;
/*  797 */           double dy = yn - ym;
/*  798 */           double ds = wm / (dx * dx + dy * dy);
/*  799 */           hxx += ds * dx * dx;
/*  800 */           hxy += ds * dx * dy;
/*  801 */           hyy += ds * dy * dy;
/*  802 */           px += ds * dx * df;
/*  803 */           py += ds * dy * df;
/*  804 */           nr++;
/*      */         } 
/*      */       } 
/*  807 */       if (nr > 1.0D) {
/*  808 */         double lxx = Math.sqrt(hxx);
/*  809 */         double lxy = hxy / lxx;
/*  810 */         double dyy = hyy - lxy * lxy;
/*  811 */         double lyy = Math.sqrt(dyy);
/*  812 */         double qx = px / lxx;
/*  813 */         double qy = (py - lxy * qx) / lyy;
/*  814 */         double gy = qy / lyy;
/*  815 */         double gx = (qx - lxy * gy) / lxx;
/*  816 */         data.gx = (float)gx;
/*  817 */         data.gy = (float)gy;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static abstract class AreaAccumulator
/*      */   {
/*      */     private double _sum;
/*      */ 
/*      */     
/*      */     private AreaAccumulator() {}
/*      */ 
/*      */     
/*      */     protected void clear() {
/*  833 */       this._sum = 0.0D;
/*      */     }
/*      */     protected double sum() {
/*  836 */       return this._sum;
/*      */     }
/*      */     protected void accumulate(TriMesh.Node node, double area) {
/*  839 */       if (SibsonInterpolator2.ghost(node))
/*  840 */         return;  SibsonInterpolator2.NodeData data = SibsonInterpolator2.data(node);
/*  841 */       data.area += area;
/*  842 */       this._sum += area;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract double accumulateAreas(double param1Double1, double param1Double2, TriMesh param1TriMesh, TriMesh.NodeList param1NodeList, TriMesh.TriList param1TriList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class WatsonSambridge
/*      */     extends AreaAccumulator
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
/*      */     private double[] _ct;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private WatsonSambridge() {
/*  879 */       this._ca = new double[2];
/*  880 */       this._cb = new double[2];
/*  881 */       this._cc = new double[2];
/*  882 */       this._ct = new double[2];
/*      */     } private double area(double[] ci, double[] cj, double[] ct) {
/*  884 */       double xt = ct[0], yt = ct[1];
/*  885 */       double xi = ci[0] - xt, yi = ci[1] - yt;
/*  886 */       double xj = cj[0] - xt, yj = cj[1] - yt;
/*  887 */       return xi * yj - xj * yi;
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
/*      */     public double accumulateAreas(double xp, double yp, TriMesh mesh, TriMesh.NodeList nodeList, TriMesh.TriList triList) {
/*      */       clear();
/*      */       int ntri = triList.ntri();
/*      */       TriMesh.Tri[] tris = triList.tris();
/*      */       for (int itri = 0; itri < ntri; itri++) {
/*      */         TriMesh.Tri tri = tris[itri];
/*      */         TriMesh.Node na = tri.nodeA();
/*      */         TriMesh.Node nb = tri.nodeB();
/*      */         TriMesh.Node nc = tri.nodeC();
/*      */         double xa = na.xp(), ya = na.yp();
/*      */         double xb = nb.xp(), yb = nb.yp();
/*      */         double xc = nc.xp(), yc = nc.yp();
/*      */         Geometry.centerCircle(xp, yp, xb, yb, xc, yc, this._ca);
/*      */         Geometry.centerCircle(xp, yp, xc, yc, xa, ya, this._cb);
/*      */         Geometry.centerCircle(xp, yp, xa, ya, xb, yb, this._cc);
/*      */         Geometry.centerCircle(xa, ya, xb, yb, xc, yc, this._ct);
/*      */         double aa = area(this._cb, this._cc, this._ct);
/*      */         double ab = area(this._cc, this._ca, this._ct);
/*      */         double ac = area(this._ca, this._cb, this._ct);
/*      */         accumulate(na, aa);
/*      */         accumulate(nb, ab);
/*      */         accumulate(nc, ac);
/*      */       } 
/*      */       return sum();
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
/*      */   private static class BraunSambridge
/*      */     extends AreaAccumulator
/*      */   {
/*      */     private LasserreVolume _lv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private BraunSambridge() {
/*  948 */       this._lv = new LasserreVolume(2);
/*      */     } public double accumulateAreas(double x1i, double x2i, TriMesh mesh, TriMesh.NodeList nodeList, TriMesh.TriList triList) { clear(); int nnode = nodeList.nnode(); TriMesh.Node[] nodes = nodeList.nodes(); for (int j = 0; j < nnode; j++) {
/*      */         TriMesh.Node jnode = nodes[j]; double x1j = jnode.xp(); double x2j = jnode.yp(); this._lv.clear();
/*      */         double x1s = 0.5D * (x1j + x1i);
/*      */         double x2s = 0.5D * (x2j + x2i);
/*      */         double x1d = x1j - x1i;
/*      */         double x2d = x2j - x2i;
/*      */         this._lv.addHalfSpace(x1d, x2d, 0.0D);
/*      */         for (int k = 0; k < nnode; k++) {
/*      */           if (j != k) {
/*      */             TriMesh.Node knode = nodes[k];
/*      */             if (mesh.findTri(jnode, knode) != null) {
/*      */               double x1k = knode.xp();
/*      */               double x2k = knode.yp();
/*      */               double x1e = x1k - x1j;
/*      */               double x2e = x2k - x2j;
/*      */               double x1t = 0.5D * (x1k + x1j) - x1s;
/*      */               double x2t = 0.5D * (x2k + x2j) - x2s;
/*      */               this._lv.addHalfSpace(x1e, x2e, x1e * x1t + x2e * x2t);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         double aj = this._lv.getVolume();
/*      */         accumulate(jnode, aj);
/*      */       } 
/*  973 */       return sum(); } } private static class HaleLiang extends AreaAccumulator { private EdgeList _edgeList; private double[] _xy; private HaleLiang() { this._edgeList = new EdgeList();
/*  974 */       this._xy = new double[2]; }
/*      */      public double accumulateAreas(double xp, double yp, TriMesh mesh, TriMesh.NodeList nodeList, TriMesh.TriList triList) {
/*      */       clear();
/*      */       processTris(xp, yp, mesh, triList);
/*      */       boolean ok = processEdges();
/*      */       return ok ? sum() : 0.0D;
/*      */     } private void processTris(double xp, double yp, TriMesh mesh, TriMesh.TriList triList) {
/*  981 */       this._edgeList.clear();
/*  982 */       int ntri = triList.ntri();
/*  983 */       TriMesh.Tri[] tris = triList.tris();
/*  984 */       for (int itri = 0; itri < ntri; itri++) {
/*  985 */         TriMesh.Tri tri = tris[itri];
/*  986 */         TriMesh.Tri ta = tri.triA();
/*  987 */         TriMesh.Tri tb = tri.triB();
/*  988 */         TriMesh.Tri tc = tri.triC();
/*  989 */         TriMesh.Node na = tri.nodeA();
/*  990 */         TriMesh.Node nb = tri.nodeB();
/*  991 */         TriMesh.Node nc = tri.nodeC();
/*  992 */         tri.centerCircle(this._xy);
/*  993 */         double xt = this._xy[0] - xp, yt = this._xy[1] - yp;
/*  994 */         processTriNabor(xp, yp, xt, yt, mesh, ta, nb, nc);
/*  995 */         processTriNabor(xp, yp, xt, yt, mesh, tb, nc, na);
/*  996 */         processTriNabor(xp, yp, xt, yt, mesh, tc, na, nb);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processTriNabor(double xp, double yp, double xt, double yt, TriMesh mesh, TriMesh.Tri ta, TriMesh.Node nb, TriMesh.Node nc) {
/* 1005 */       boolean saveEdge = true;
/* 1006 */       if (ta != null && mesh.isMarked(ta)) {
/* 1007 */         ta.centerCircle(this._xy);
/* 1008 */         double xa = this._xy[0] - xp;
/* 1009 */         double ya = this._xy[1] - yp;
/* 1010 */         double xy = xt * ya - xa * yt;
/* 1011 */         accumulate(nc, xy);
/* 1012 */         saveEdge = false;
/*      */       } 
/* 1014 */       if (saveEdge) {
/* 1015 */         addEdge(xp, yp, xt, yt, nb, nc);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     private static class Edge
/*      */     {
/*      */       TriMesh.Node na;
/*      */       
/*      */       TriMesh.Node nb;
/*      */       
/*      */       double xf;
/*      */       
/*      */       double yf;
/*      */       double xr;
/*      */       double yr;
/*      */       Edge eb;
/*      */       
/*      */       private Edge() {}
/*      */     }
/*      */     
/*      */     private void addEdge(double xp, double yp, double xr, double yr, TriMesh.Node na, TriMesh.Node nb) {
/* 1037 */       double xa = na.xp(), ya = na.yp();
/* 1038 */       double xb = nb.xp(), yb = nb.yp();
/* 1039 */       Geometry.centerCircle(xp, yp, xa, ya, xb, yb, this._xy);
/* 1040 */       double xf = this._xy[0] - xp, yf = this._xy[1] - yp;
/* 1041 */       this._edgeList.add(na, nb, xf, yf, xr, yr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private boolean edgeNaborsOk() {
/* 1048 */       int nedge = this._edgeList.nedge();
/* 1049 */       ArrayList<Edge> edges = this._edgeList.edges();
/* 1050 */       for (int iedge = 0; iedge < nedge; iedge++) {
/* 1051 */         Edge edge = edges.get(iedge);
/* 1052 */         if (edge.eb == null)
/* 1053 */           return false; 
/*      */       } 
/* 1055 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     private boolean processEdges() {
/* 1060 */       if (!edgeNaborsOk())
/* 1061 */         return false; 
/* 1062 */       int nedge = this._edgeList.nedge();
/* 1063 */       ArrayList<Edge> edges = this._edgeList.edges();
/* 1064 */       for (int iedge = 0; iedge < nedge; iedge++)
/* 1065 */         processEdge(edges.get(iedge)); 
/* 1066 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void processEdge(Edge edge) {
/* 1073 */       TriMesh.Node na = edge.na, nb = edge.nb;
/*      */ 
/*      */       
/* 1076 */       double x1 = edge.xf;
/* 1077 */       double y1 = edge.yf;
/* 1078 */       double x2 = edge.xr;
/* 1079 */       double y2 = edge.yr;
/* 1080 */       double xy = x1 * y2 - x2 * y1;
/* 1081 */       accumulate(na, xy);
/* 1082 */       accumulate(nb, -xy);
/*      */ 
/*      */       
/* 1085 */       Edge eb = edge.eb;
/* 1086 */       x2 = eb.xf;
/* 1087 */       y2 = eb.yf;
/* 1088 */       xy = x1 * y2 - x2 * y1;
/* 1089 */       accumulate(nb, xy);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static class EdgeList
/*      */     {
/*      */       private int _nedge;
/*      */ 
/*      */       
/* 1100 */       private ArrayList<SibsonInterpolator2.HaleLiang.Edge> _edges = new ArrayList<>(12);
/*      */       int nedge() {
/* 1102 */         return this._nedge;
/*      */       }
/*      */       ArrayList<SibsonInterpolator2.HaleLiang.Edge> edges() {
/* 1105 */         return this._edges;
/*      */       }
/*      */       void clear() {
/* 1108 */         this._nedge = 0;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       void add(TriMesh.Node na, TriMesh.Node nb, double xf, double yf, double xr, double yr) {
/* 1115 */         if (this._nedge == this._edges.size())
/* 1116 */           this._edges.add(new SibsonInterpolator2.HaleLiang.Edge()); 
/* 1117 */         SibsonInterpolator2.HaleLiang.Edge edge = this._edges.get(this._nedge);
/* 1118 */         SibsonInterpolator2.HaleLiang.Edge eb = null;
/* 1119 */         int nfound = 0;
/* 1120 */         for (int iedge = 0; iedge < this._nedge && nfound < 2; iedge++) {
/* 1121 */           SibsonInterpolator2.HaleLiang.Edge ei = this._edges.get(iedge);
/* 1122 */           if (nb == ei.na) {
/* 1123 */             eb = ei;
/* 1124 */             nfound++;
/*      */           } 
/* 1126 */           if (na == ei.nb) {
/* 1127 */             ei.eb = edge;
/* 1128 */             nfound++;
/*      */           } 
/*      */         } 
/* 1131 */         edge.na = na; edge.nb = nb;
/* 1132 */         edge.xf = xf; edge.yf = yf;
/* 1133 */         edge.xr = xr; edge.yr = yr;
/* 1134 */         edge.eb = eb;
/* 1135 */         this._nedge++;
/*      */       }
/*      */       
/*      */       private EdgeList() {}
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/SibsonInterpolator2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.FloatPoint;
/*     */ import Catalano.Core.IntPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public final class PointsCloud
/*     */ {
/*     */   public static void Shift(ArrayList<IntPoint> cloud, IntPoint shift) {
/*  26 */     for (int i = 0, n = cloud.size(); i < n; i++) {
/*  27 */       IntPoint p = cloud.get(i);
/*  28 */       p.Add(shift);
/*  29 */       cloud.set(i, p);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<IntPoint> GetBoundingRectangle(List<IntPoint> cloud) {
/*  40 */     List<IntPoint> bound = new ArrayList<>();
/*     */     
/*  42 */     int minX = Integer.MAX_VALUE;
/*  43 */     int maxX = Integer.MIN_VALUE;
/*  44 */     int minY = Integer.MAX_VALUE;
/*  45 */     int maxY = Integer.MIN_VALUE;
/*     */     
/*  47 */     for (IntPoint pt : cloud) {
/*     */       
/*  49 */       int x = pt.x;
/*  50 */       int y = pt.y;
/*     */ 
/*     */       
/*  53 */       if (x < minX)
/*  54 */         minX = x; 
/*  55 */       if (x > maxX) {
/*  56 */         maxX = x;
/*     */       }
/*     */       
/*  59 */       if (y < minY)
/*  60 */         minY = y; 
/*  61 */       if (y > maxY) {
/*  62 */         maxY = y;
/*     */       }
/*     */     } 
/*  65 */     if (minX > maxX) {
/*  66 */       throw new IllegalArgumentException("List of points can not be empty.");
/*     */     }
/*  68 */     IntPoint min = new IntPoint(minX, minY);
/*  69 */     IntPoint max = new IntPoint(maxX, maxY);
/*     */     
/*  71 */     bound.add(min);
/*  72 */     bound.add(max);
/*     */     
/*  74 */     return bound;
/*     */   }
/*     */   
/*     */   public static FloatPoint GetCenterOfGravity(ArrayList<IntPoint> cloud) {
/*  78 */     int numberOfPoints = 0;
/*  79 */     float xSum = 0.0F, ySum = 0.0F;
/*     */     
/*  81 */     for (IntPoint pt : cloud) {
/*     */       
/*  83 */       xSum += pt.x;
/*  84 */       ySum += pt.y;
/*  85 */       numberOfPoints++;
/*     */     } 
/*     */     
/*  88 */     xSum /= numberOfPoints;
/*  89 */     ySum /= numberOfPoints;
/*     */     
/*  91 */     return new FloatPoint(xSum, ySum);
/*     */   }
/*     */   
/*     */   public static FurthestPoint GetFurthestPoint(List<IntPoint> cloud, IntPoint referencePoint) {
/*  95 */     FurthestPoint furthestPoint = new FurthestPoint();
/*  96 */     float maxDistance = -1.0F;
/*     */     
/*  98 */     int rx = referencePoint.x;
/*  99 */     int ry = referencePoint.y;
/*     */     
/* 101 */     for (IntPoint point : cloud) {
/*     */       
/* 103 */       int dx = rx - point.x;
/* 104 */       int dy = ry - point.y;
/*     */ 
/*     */       
/* 107 */       float distance = (dx * dx + dy * dy);
/*     */       
/* 109 */       if (distance > maxDistance) {
/*     */         
/* 111 */         maxDistance = distance;
/* 112 */         furthestPoint.x = point.x;
/* 113 */         furthestPoint.y = point.y;
/* 114 */         furthestPoint.distance = maxDistance;
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return furthestPoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static FurthestPoint[] GetFurthestPointsFromLine(List<IntPoint> cloud, IntPoint linePoint1, IntPoint linePoint2) {
/* 123 */     FurthestPoint[] furthest = new FurthestPoint[2];
/* 124 */     furthest[0] = new FurthestPoint(linePoint1);
/* 125 */     furthest[1] = new FurthestPoint(linePoint2);
/*     */     
/* 127 */     double distance1 = 0.0D;
/* 128 */     double distance2 = 0.0D;
/*     */     
/* 130 */     if (linePoint2.x != linePoint1.x) {
/*     */       
/* 132 */       float k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
/* 133 */       float b = linePoint1.y - k * linePoint1.x;
/*     */       
/* 135 */       float div = (float)Math.sqrt((k * k + 1.0F));
/* 136 */       double distance = 0.0D;
/*     */       
/* 138 */       for (IntPoint point : cloud) {
/* 139 */         distance = (k * point.x + b - point.y) / div;
/*     */         
/* 141 */         if (distance > distance1) {
/* 142 */           distance1 = distance;
/* 143 */           furthest[0] = new FurthestPoint(point, (float)distance);
/*     */         } 
/* 145 */         if (distance < distance2) {
/* 146 */           distance2 = distance;
/* 147 */           furthest[1] = new FurthestPoint(point, (float)distance);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 152 */       int lineX = linePoint1.x;
/* 153 */       float distance = 0.0F;
/*     */       
/* 155 */       for (IntPoint point : cloud) {
/* 156 */         distance = (lineX - point.x);
/*     */         
/* 158 */         if (distance > distance1) {
/* 159 */           distance1 = distance;
/* 160 */           furthest[0] = new FurthestPoint(point, distance);
/*     */         } 
/* 162 */         if (distance < distance2) {
/* 163 */           distance2 = distance;
/* 164 */           furthest[1] = new FurthestPoint(point, distance);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 170 */     (furthest[1]).distance = (float)-distance2;
/*     */     
/* 172 */     return furthest;
/*     */   }
/*     */   
/*     */   public static IntPoint GetFurthestPointFromLine(ArrayList<IntPoint> cloud, IntPoint linePoint1, IntPoint linePoint2) {
/* 176 */     IntPoint furthestPoint = linePoint1;
/* 177 */     float distance = 0.0F;
/*     */     
/* 179 */     if (linePoint2.x != linePoint1.x) {
/*     */       
/* 181 */       float k = (linePoint2.y - linePoint1.y) / (linePoint2.x - linePoint1.x);
/* 182 */       float b = linePoint1.y - k * linePoint1.x;
/*     */       
/* 184 */       float div = (float)Math.sqrt((k * k + 1.0F));
/* 185 */       float pointDistance = 0.0F;
/*     */       
/* 187 */       for (IntPoint point : cloud) {
/* 188 */         pointDistance = Math.abs((k * point.x + b - point.y) / div);
/*     */         
/* 190 */         if (pointDistance > distance) {
/* 191 */           distance = pointDistance;
/* 192 */           furthestPoint = point;
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 197 */       int lineX = linePoint1.x;
/* 198 */       float pointDistance = 0.0F;
/*     */       
/* 200 */       for (IntPoint point : cloud) {
/* 201 */         distance = Math.abs(lineX - point.x);
/*     */         
/* 203 */         if (pointDistance > distance) {
/* 204 */           distance = pointDistance;
/* 205 */           furthestPoint = point;
/*     */         } 
/*     */       } 
/*     */     } 
/* 209 */     return furthestPoint;
/*     */   }
/*     */   
/* 212 */   private static float quadrilateralRelativeDistortionLimit = 0.1F;
/*     */   
/*     */   public static float getQuadrilateralRelativeDistortionLimit() {
/* 215 */     return quadrilateralRelativeDistortionLimit;
/*     */   }
/*     */   
/*     */   public static void setQuadrilateralRelativeDistortionLimit(float value) {
/* 219 */     quadrilateralRelativeDistortionLimit = Math.max(0.0F, Math.min(0.25F, value));
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
/*     */   public static List<IntPoint> FindQuadrilateralCorners(List<IntPoint> cloud) {
/*     */     // Byte code:
/*     */     //   0: new java/util/ArrayList
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore_1
/*     */     //   8: aload_0
/*     */     //   9: invokestatic GetBoundingRectangle : (Ljava/util/List;)Ljava/util/List;
/*     */     //   12: astore #4
/*     */     //   14: aload #4
/*     */     //   16: iconst_0
/*     */     //   17: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   22: checkcast Catalano/Core/IntPoint
/*     */     //   25: astore_2
/*     */     //   26: aload #4
/*     */     //   28: iconst_1
/*     */     //   29: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   34: checkcast Catalano/Core/IntPoint
/*     */     //   37: astore_3
/*     */     //   38: aload_3
/*     */     //   39: aload_2
/*     */     //   40: invokestatic Subtract : (LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)LCatalano/Core/IntPoint;
/*     */     //   43: astore #5
/*     */     //   45: aload #5
/*     */     //   47: iconst_2
/*     */     //   48: invokevirtual Divide : (I)V
/*     */     //   51: aload_2
/*     */     //   52: aload #5
/*     */     //   54: invokevirtual Add : (LCatalano/Core/IntPoint;)V
/*     */     //   57: aload_2
/*     */     //   58: astore #6
/*     */     //   60: getstatic Catalano/Math/Geometry/PointsCloud.quadrilateralRelativeDistortionLimit : F
/*     */     //   63: aload #5
/*     */     //   65: getfield x : I
/*     */     //   68: aload #5
/*     */     //   70: getfield y : I
/*     */     //   73: iadd
/*     */     //   74: i2f
/*     */     //   75: fmul
/*     */     //   76: fconst_2
/*     */     //   77: fdiv
/*     */     //   78: fstore #7
/*     */     //   80: aload_0
/*     */     //   81: aload #6
/*     */     //   83: invokestatic GetFurthestPoint : (Ljava/util/List;LCatalano/Core/IntPoint;)LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   86: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   89: astore #8
/*     */     //   91: aload_0
/*     */     //   92: aload #8
/*     */     //   94: invokestatic GetFurthestPoint : (Ljava/util/List;LCatalano/Core/IntPoint;)LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   97: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   100: astore #9
/*     */     //   102: aload_1
/*     */     //   103: aload #8
/*     */     //   105: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   110: pop
/*     */     //   111: aload_1
/*     */     //   112: aload #9
/*     */     //   114: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   119: pop
/*     */     //   120: aload_0
/*     */     //   121: aload #8
/*     */     //   123: aload #9
/*     */     //   125: invokestatic GetFurthestPointsFromLine : (Ljava/util/List;LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   128: astore #14
/*     */     //   130: aload #14
/*     */     //   132: iconst_0
/*     */     //   133: aaload
/*     */     //   134: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   137: astore #10
/*     */     //   139: aload #14
/*     */     //   141: iconst_0
/*     */     //   142: aaload
/*     */     //   143: getfield distance : F
/*     */     //   146: fstore #12
/*     */     //   148: aload #14
/*     */     //   150: iconst_1
/*     */     //   151: aaload
/*     */     //   152: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   155: astore #11
/*     */     //   157: aload #14
/*     */     //   159: iconst_1
/*     */     //   160: aaload
/*     */     //   161: getfield distance : F
/*     */     //   164: fstore #13
/*     */     //   166: fload #12
/*     */     //   168: fload #7
/*     */     //   170: fcmpl
/*     */     //   171: iflt -> 182
/*     */     //   174: fload #13
/*     */     //   176: fload #7
/*     */     //   178: fcmpl
/*     */     //   179: ifge -> 212
/*     */     //   182: fload #12
/*     */     //   184: fload #7
/*     */     //   186: fcmpg
/*     */     //   187: ifge -> 255
/*     */     //   190: fload #12
/*     */     //   192: fconst_0
/*     */     //   193: fcmpl
/*     */     //   194: ifeq -> 255
/*     */     //   197: fload #13
/*     */     //   199: fload #7
/*     */     //   201: fcmpg
/*     */     //   202: ifge -> 255
/*     */     //   205: fload #13
/*     */     //   207: fconst_0
/*     */     //   208: fcmpl
/*     */     //   209: ifeq -> 255
/*     */     //   212: aload_1
/*     */     //   213: aload #10
/*     */     //   215: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   220: ifne -> 232
/*     */     //   223: aload_1
/*     */     //   224: aload #10
/*     */     //   226: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   231: pop
/*     */     //   232: aload_1
/*     */     //   233: aload #11
/*     */     //   235: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */     //   240: ifne -> 677
/*     */     //   243: aload_1
/*     */     //   244: aload #11
/*     */     //   246: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   251: pop
/*     */     //   252: goto -> 677
/*     */     //   255: fload #12
/*     */     //   257: fload #13
/*     */     //   259: fcmpl
/*     */     //   260: ifle -> 268
/*     */     //   263: aload #10
/*     */     //   265: goto -> 270
/*     */     //   268: aload #11
/*     */     //   270: astore #15
/*     */     //   272: aload_0
/*     */     //   273: aload #8
/*     */     //   275: aload #15
/*     */     //   277: invokestatic GetFurthestPointsFromLine : (Ljava/util/List;LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   280: astore #14
/*     */     //   282: aload #14
/*     */     //   284: iconst_0
/*     */     //   285: aaload
/*     */     //   286: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   289: astore #10
/*     */     //   291: aload #14
/*     */     //   293: iconst_0
/*     */     //   294: aaload
/*     */     //   295: getfield distance : F
/*     */     //   298: fstore #12
/*     */     //   300: aload #14
/*     */     //   302: iconst_1
/*     */     //   303: aaload
/*     */     //   304: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   307: astore #11
/*     */     //   309: aload #14
/*     */     //   311: iconst_1
/*     */     //   312: aaload
/*     */     //   313: getfield distance : F
/*     */     //   316: fstore #13
/*     */     //   318: iconst_0
/*     */     //   319: istore #16
/*     */     //   321: fload #12
/*     */     //   323: fload #7
/*     */     //   325: fcmpl
/*     */     //   326: iflt -> 365
/*     */     //   329: fload #13
/*     */     //   331: fload #7
/*     */     //   333: fcmpl
/*     */     //   334: iflt -> 365
/*     */     //   337: aload #11
/*     */     //   339: aload #9
/*     */     //   341: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   344: aload #10
/*     */     //   346: aload #9
/*     */     //   348: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   351: fcmpl
/*     */     //   352: ifle -> 359
/*     */     //   355: aload #11
/*     */     //   357: astore #10
/*     */     //   359: iconst_1
/*     */     //   360: istore #16
/*     */     //   362: goto -> 452
/*     */     //   365: aload_0
/*     */     //   366: aload #9
/*     */     //   368: aload #15
/*     */     //   370: invokestatic GetFurthestPointsFromLine : (Ljava/util/List;LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   373: astore #14
/*     */     //   375: aload #14
/*     */     //   377: iconst_0
/*     */     //   378: aaload
/*     */     //   379: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   382: astore #10
/*     */     //   384: aload #14
/*     */     //   386: iconst_0
/*     */     //   387: aaload
/*     */     //   388: getfield distance : F
/*     */     //   391: fstore #12
/*     */     //   393: aload #14
/*     */     //   395: iconst_1
/*     */     //   396: aaload
/*     */     //   397: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   400: astore #11
/*     */     //   402: aload #14
/*     */     //   404: iconst_1
/*     */     //   405: aaload
/*     */     //   406: getfield distance : F
/*     */     //   409: fstore #13
/*     */     //   411: fload #12
/*     */     //   413: fload #7
/*     */     //   415: fcmpl
/*     */     //   416: iflt -> 452
/*     */     //   419: fload #13
/*     */     //   421: fload #7
/*     */     //   423: fcmpl
/*     */     //   424: iflt -> 452
/*     */     //   427: aload #11
/*     */     //   429: aload #8
/*     */     //   431: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   434: aload #10
/*     */     //   436: aload #8
/*     */     //   438: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   441: fcmpl
/*     */     //   442: ifle -> 449
/*     */     //   445: aload #11
/*     */     //   447: astore #10
/*     */     //   449: iconst_1
/*     */     //   450: istore #16
/*     */     //   452: iload #16
/*     */     //   454: ifne -> 469
/*     */     //   457: aload_1
/*     */     //   458: aload #15
/*     */     //   460: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   465: pop
/*     */     //   466: goto -> 677
/*     */     //   469: aload_1
/*     */     //   470: aload #10
/*     */     //   472: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   477: pop
/*     */     //   478: aload_0
/*     */     //   479: aload #8
/*     */     //   481: aload #10
/*     */     //   483: invokestatic GetFurthestPointsFromLine : (Ljava/util/List;LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   486: astore #14
/*     */     //   488: aload #14
/*     */     //   490: iconst_0
/*     */     //   491: aaload
/*     */     //   492: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   495: astore #15
/*     */     //   497: aload #14
/*     */     //   499: iconst_0
/*     */     //   500: aaload
/*     */     //   501: getfield distance : F
/*     */     //   504: fstore #17
/*     */     //   506: aload #14
/*     */     //   508: iconst_1
/*     */     //   509: aaload
/*     */     //   510: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   513: astore #11
/*     */     //   515: aload #14
/*     */     //   517: iconst_1
/*     */     //   518: aaload
/*     */     //   519: getfield distance : F
/*     */     //   522: fstore #13
/*     */     //   524: fload #13
/*     */     //   526: fload #7
/*     */     //   528: fcmpl
/*     */     //   529: iflt -> 565
/*     */     //   532: fload #17
/*     */     //   534: fload #7
/*     */     //   536: fcmpl
/*     */     //   537: iflt -> 565
/*     */     //   540: aload #15
/*     */     //   542: aload #9
/*     */     //   544: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   547: aload #11
/*     */     //   549: aload #9
/*     */     //   551: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   554: fcmpl
/*     */     //   555: ifle -> 647
/*     */     //   558: aload #15
/*     */     //   560: astore #11
/*     */     //   562: goto -> 647
/*     */     //   565: aload_0
/*     */     //   566: aload #9
/*     */     //   568: aload #10
/*     */     //   570: invokestatic GetFurthestPointsFromLine : (Ljava/util/List;LCatalano/Core/IntPoint;LCatalano/Core/IntPoint;)[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   573: astore #14
/*     */     //   575: aload #14
/*     */     //   577: iconst_0
/*     */     //   578: aaload
/*     */     //   579: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   582: astore #15
/*     */     //   584: aload #14
/*     */     //   586: iconst_0
/*     */     //   587: aaload
/*     */     //   588: getfield distance : F
/*     */     //   591: fstore #17
/*     */     //   593: aload #14
/*     */     //   595: iconst_1
/*     */     //   596: aaload
/*     */     //   597: invokevirtual toIntPoint : ()LCatalano/Core/IntPoint;
/*     */     //   600: astore #11
/*     */     //   602: aload #14
/*     */     //   604: iconst_1
/*     */     //   605: aaload
/*     */     //   606: getfield distance : F
/*     */     //   609: fstore #13
/*     */     //   611: aload #15
/*     */     //   613: aload #8
/*     */     //   615: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   618: aload #11
/*     */     //   620: aload #8
/*     */     //   622: invokevirtual DistanceTo : (LCatalano/Core/IntPoint;)F
/*     */     //   625: fcmpl
/*     */     //   626: ifle -> 647
/*     */     //   629: aload #15
/*     */     //   631: aload #9
/*     */     //   633: if_acmpeq -> 647
/*     */     //   636: aload #15
/*     */     //   638: aload #10
/*     */     //   640: if_acmpeq -> 647
/*     */     //   643: aload #15
/*     */     //   645: astore #11
/*     */     //   647: aload #11
/*     */     //   649: aload #8
/*     */     //   651: if_acmpeq -> 677
/*     */     //   654: aload #11
/*     */     //   656: aload #9
/*     */     //   658: if_acmpeq -> 677
/*     */     //   661: aload #11
/*     */     //   663: aload #10
/*     */     //   665: if_acmpeq -> 677
/*     */     //   668: aload_1
/*     */     //   669: aload #11
/*     */     //   671: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   676: pop
/*     */     //   677: iconst_1
/*     */     //   678: istore #15
/*     */     //   680: aload_1
/*     */     //   681: invokeinterface size : ()I
/*     */     //   686: istore #16
/*     */     //   688: goto -> 826
/*     */     //   691: aload_1
/*     */     //   692: iload #15
/*     */     //   694: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   699: checkcast Catalano/Core/IntPoint
/*     */     //   702: getfield x : I
/*     */     //   705: aload_1
/*     */     //   706: iconst_0
/*     */     //   707: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   712: checkcast Catalano/Core/IntPoint
/*     */     //   715: getfield x : I
/*     */     //   718: if_icmplt -> 781
/*     */     //   721: aload_1
/*     */     //   722: iload #15
/*     */     //   724: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   729: checkcast Catalano/Core/IntPoint
/*     */     //   732: getfield x : I
/*     */     //   735: aload_1
/*     */     //   736: iconst_0
/*     */     //   737: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   742: checkcast Catalano/Core/IntPoint
/*     */     //   745: getfield x : I
/*     */     //   748: if_icmpne -> 823
/*     */     //   751: aload_1
/*     */     //   752: iload #15
/*     */     //   754: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   759: checkcast Catalano/Core/IntPoint
/*     */     //   762: getfield y : I
/*     */     //   765: aload_1
/*     */     //   766: iconst_0
/*     */     //   767: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   772: checkcast Catalano/Core/IntPoint
/*     */     //   775: getfield y : I
/*     */     //   778: if_icmpge -> 823
/*     */     //   781: aload_1
/*     */     //   782: iload #15
/*     */     //   784: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   789: checkcast Catalano/Core/IntPoint
/*     */     //   792: astore #17
/*     */     //   794: aload_1
/*     */     //   795: iload #15
/*     */     //   797: aload_1
/*     */     //   798: iconst_0
/*     */     //   799: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   804: checkcast Catalano/Core/IntPoint
/*     */     //   807: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   812: pop
/*     */     //   813: aload_1
/*     */     //   814: iconst_0
/*     */     //   815: aload #17
/*     */     //   817: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   822: pop
/*     */     //   823: iinc #15, 1
/*     */     //   826: iload #15
/*     */     //   828: iload #16
/*     */     //   830: if_icmplt -> 691
/*     */     //   833: aload_1
/*     */     //   834: iconst_1
/*     */     //   835: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   840: checkcast Catalano/Core/IntPoint
/*     */     //   843: getfield x : I
/*     */     //   846: aload_1
/*     */     //   847: iconst_0
/*     */     //   848: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   853: checkcast Catalano/Core/IntPoint
/*     */     //   856: getfield x : I
/*     */     //   859: if_icmpeq -> 922
/*     */     //   862: aload_1
/*     */     //   863: iconst_1
/*     */     //   864: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   869: checkcast Catalano/Core/IntPoint
/*     */     //   872: getfield y : I
/*     */     //   875: aload_1
/*     */     //   876: iconst_0
/*     */     //   877: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   882: checkcast Catalano/Core/IntPoint
/*     */     //   885: getfield y : I
/*     */     //   888: isub
/*     */     //   889: i2f
/*     */     //   890: aload_1
/*     */     //   891: iconst_1
/*     */     //   892: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   897: checkcast Catalano/Core/IntPoint
/*     */     //   900: getfield x : I
/*     */     //   903: aload_1
/*     */     //   904: iconst_0
/*     */     //   905: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   910: checkcast Catalano/Core/IntPoint
/*     */     //   913: getfield x : I
/*     */     //   916: isub
/*     */     //   917: i2f
/*     */     //   918: fdiv
/*     */     //   919: goto -> 958
/*     */     //   922: aload_1
/*     */     //   923: iconst_1
/*     */     //   924: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   929: checkcast Catalano/Core/IntPoint
/*     */     //   932: getfield y : I
/*     */     //   935: aload_1
/*     */     //   936: iconst_0
/*     */     //   937: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   942: checkcast Catalano/Core/IntPoint
/*     */     //   945: getfield y : I
/*     */     //   948: if_icmple -> 956
/*     */     //   951: ldc Infinity
/*     */     //   953: goto -> 958
/*     */     //   956: ldc -Infinity
/*     */     //   958: fstore #15
/*     */     //   960: aload_1
/*     */     //   961: iconst_2
/*     */     //   962: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   967: checkcast Catalano/Core/IntPoint
/*     */     //   970: getfield x : I
/*     */     //   973: aload_1
/*     */     //   974: iconst_0
/*     */     //   975: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   980: checkcast Catalano/Core/IntPoint
/*     */     //   983: getfield x : I
/*     */     //   986: if_icmpeq -> 1049
/*     */     //   989: aload_1
/*     */     //   990: iconst_2
/*     */     //   991: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   996: checkcast Catalano/Core/IntPoint
/*     */     //   999: getfield y : I
/*     */     //   1002: aload_1
/*     */     //   1003: iconst_0
/*     */     //   1004: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1009: checkcast Catalano/Core/IntPoint
/*     */     //   1012: getfield y : I
/*     */     //   1015: isub
/*     */     //   1016: i2f
/*     */     //   1017: aload_1
/*     */     //   1018: iconst_2
/*     */     //   1019: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1024: checkcast Catalano/Core/IntPoint
/*     */     //   1027: getfield x : I
/*     */     //   1030: aload_1
/*     */     //   1031: iconst_0
/*     */     //   1032: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1037: checkcast Catalano/Core/IntPoint
/*     */     //   1040: getfield x : I
/*     */     //   1043: isub
/*     */     //   1044: i2f
/*     */     //   1045: fdiv
/*     */     //   1046: goto -> 1085
/*     */     //   1049: aload_1
/*     */     //   1050: iconst_2
/*     */     //   1051: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1056: checkcast Catalano/Core/IntPoint
/*     */     //   1059: getfield y : I
/*     */     //   1062: aload_1
/*     */     //   1063: iconst_0
/*     */     //   1064: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1069: checkcast Catalano/Core/IntPoint
/*     */     //   1072: getfield y : I
/*     */     //   1075: if_icmple -> 1083
/*     */     //   1078: ldc Infinity
/*     */     //   1080: goto -> 1085
/*     */     //   1083: ldc -Infinity
/*     */     //   1085: fstore #16
/*     */     //   1087: fload #16
/*     */     //   1089: fload #15
/*     */     //   1091: fcmpg
/*     */     //   1092: ifge -> 1147
/*     */     //   1095: aload_1
/*     */     //   1096: iconst_1
/*     */     //   1097: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1102: checkcast Catalano/Core/IntPoint
/*     */     //   1105: astore #17
/*     */     //   1107: aload_1
/*     */     //   1108: iconst_1
/*     */     //   1109: aload_1
/*     */     //   1110: iconst_2
/*     */     //   1111: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1116: checkcast Catalano/Core/IntPoint
/*     */     //   1119: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1124: pop
/*     */     //   1125: aload_1
/*     */     //   1126: iconst_2
/*     */     //   1127: aload #17
/*     */     //   1129: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1134: pop
/*     */     //   1135: fload #15
/*     */     //   1137: fstore #18
/*     */     //   1139: fload #16
/*     */     //   1141: fstore #15
/*     */     //   1143: fload #18
/*     */     //   1145: fstore #16
/*     */     //   1147: aload_1
/*     */     //   1148: invokeinterface size : ()I
/*     */     //   1153: iconst_4
/*     */     //   1154: if_icmpne -> 1404
/*     */     //   1157: aload_1
/*     */     //   1158: iconst_3
/*     */     //   1159: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1164: checkcast Catalano/Core/IntPoint
/*     */     //   1167: getfield x : I
/*     */     //   1170: aload_1
/*     */     //   1171: iconst_0
/*     */     //   1172: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1177: checkcast Catalano/Core/IntPoint
/*     */     //   1180: getfield x : I
/*     */     //   1183: if_icmpeq -> 1246
/*     */     //   1186: aload_1
/*     */     //   1187: iconst_3
/*     */     //   1188: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1193: checkcast Catalano/Core/IntPoint
/*     */     //   1196: getfield y : I
/*     */     //   1199: aload_1
/*     */     //   1200: iconst_0
/*     */     //   1201: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1206: checkcast Catalano/Core/IntPoint
/*     */     //   1209: getfield y : I
/*     */     //   1212: isub
/*     */     //   1213: i2f
/*     */     //   1214: aload_1
/*     */     //   1215: iconst_3
/*     */     //   1216: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1221: checkcast Catalano/Core/IntPoint
/*     */     //   1224: getfield x : I
/*     */     //   1227: aload_1
/*     */     //   1228: iconst_0
/*     */     //   1229: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1234: checkcast Catalano/Core/IntPoint
/*     */     //   1237: getfield x : I
/*     */     //   1240: isub
/*     */     //   1241: i2f
/*     */     //   1242: fdiv
/*     */     //   1243: goto -> 1282
/*     */     //   1246: aload_1
/*     */     //   1247: iconst_3
/*     */     //   1248: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1253: checkcast Catalano/Core/IntPoint
/*     */     //   1256: getfield y : I
/*     */     //   1259: aload_1
/*     */     //   1260: iconst_0
/*     */     //   1261: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1266: checkcast Catalano/Core/IntPoint
/*     */     //   1269: getfield y : I
/*     */     //   1272: if_icmple -> 1280
/*     */     //   1275: ldc Infinity
/*     */     //   1277: goto -> 1282
/*     */     //   1280: ldc -Infinity
/*     */     //   1282: fstore #17
/*     */     //   1284: fload #17
/*     */     //   1286: fload #15
/*     */     //   1288: fcmpg
/*     */     //   1289: ifge -> 1344
/*     */     //   1292: aload_1
/*     */     //   1293: iconst_1
/*     */     //   1294: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1299: checkcast Catalano/Core/IntPoint
/*     */     //   1302: astore #18
/*     */     //   1304: aload_1
/*     */     //   1305: iconst_1
/*     */     //   1306: aload_1
/*     */     //   1307: iconst_3
/*     */     //   1308: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1313: checkcast Catalano/Core/IntPoint
/*     */     //   1316: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1321: pop
/*     */     //   1322: aload_1
/*     */     //   1323: iconst_3
/*     */     //   1324: aload #18
/*     */     //   1326: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1331: pop
/*     */     //   1332: fload #15
/*     */     //   1334: fstore #19
/*     */     //   1336: fload #17
/*     */     //   1338: fstore #15
/*     */     //   1340: fload #19
/*     */     //   1342: fstore #17
/*     */     //   1344: fload #17
/*     */     //   1346: fload #16
/*     */     //   1348: fcmpg
/*     */     //   1349: ifge -> 1404
/*     */     //   1352: aload_1
/*     */     //   1353: iconst_2
/*     */     //   1354: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1359: checkcast Catalano/Core/IntPoint
/*     */     //   1362: astore #18
/*     */     //   1364: aload_1
/*     */     //   1365: iconst_2
/*     */     //   1366: aload_1
/*     */     //   1367: iconst_3
/*     */     //   1368: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   1373: checkcast Catalano/Core/IntPoint
/*     */     //   1376: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1381: pop
/*     */     //   1382: aload_1
/*     */     //   1383: iconst_3
/*     */     //   1384: aload #18
/*     */     //   1386: invokeinterface set : (ILjava/lang/Object;)Ljava/lang/Object;
/*     */     //   1391: pop
/*     */     //   1392: fload #16
/*     */     //   1394: fstore #19
/*     */     //   1396: fload #17
/*     */     //   1398: fstore #16
/*     */     //   1400: fload #19
/*     */     //   1402: fstore #17
/*     */     //   1404: aload_1
/*     */     //   1405: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #224	-> 0
/*     */     //   #228	-> 8
/*     */     //   #229	-> 14
/*     */     //   #230	-> 26
/*     */     //   #232	-> 38
/*     */     //   #234	-> 45
/*     */     //   #235	-> 51
/*     */     //   #236	-> 57
/*     */     //   #238	-> 60
/*     */     //   #241	-> 80
/*     */     //   #243	-> 91
/*     */     //   #245	-> 102
/*     */     //   #246	-> 111
/*     */     //   #252	-> 120
/*     */     //   #254	-> 130
/*     */     //   #255	-> 139
/*     */     //   #257	-> 148
/*     */     //   #258	-> 157
/*     */     //   #274	-> 166
/*     */     //   #276	-> 182
/*     */     //   #277	-> 197
/*     */     //   #282	-> 212
/*     */     //   #284	-> 223
/*     */     //   #286	-> 232
/*     */     //   #288	-> 243
/*     */     //   #290	-> 252
/*     */     //   #296	-> 255
/*     */     //   #299	-> 272
/*     */     //   #301	-> 282
/*     */     //   #302	-> 291
/*     */     //   #303	-> 300
/*     */     //   #304	-> 309
/*     */     //   #306	-> 318
/*     */     //   #308	-> 321
/*     */     //   #310	-> 337
/*     */     //   #311	-> 355
/*     */     //   #313	-> 359
/*     */     //   #314	-> 362
/*     */     //   #317	-> 365
/*     */     //   #319	-> 375
/*     */     //   #320	-> 384
/*     */     //   #321	-> 393
/*     */     //   #322	-> 402
/*     */     //   #324	-> 411
/*     */     //   #326	-> 427
/*     */     //   #327	-> 445
/*     */     //   #329	-> 449
/*     */     //   #333	-> 452
/*     */     //   #337	-> 457
/*     */     //   #338	-> 466
/*     */     //   #341	-> 469
/*     */     //   #346	-> 478
/*     */     //   #348	-> 488
/*     */     //   #349	-> 497
/*     */     //   #350	-> 506
/*     */     //   #351	-> 515
/*     */     //   #353	-> 524
/*     */     //   #355	-> 540
/*     */     //   #356	-> 558
/*     */     //   #357	-> 562
/*     */     //   #360	-> 565
/*     */     //   #362	-> 575
/*     */     //   #363	-> 584
/*     */     //   #364	-> 593
/*     */     //   #365	-> 602
/*     */     //   #367	-> 611
/*     */     //   #368	-> 629
/*     */     //   #370	-> 643
/*     */     //   #374	-> 647
/*     */     //   #375	-> 668
/*     */     //   #380	-> 677
/*     */     //   #382	-> 691
/*     */     //   #383	-> 721
/*     */     //   #385	-> 781
/*     */     //   #386	-> 794
/*     */     //   #387	-> 813
/*     */     //   #380	-> 823
/*     */     //   #393	-> 833
/*     */     //   #394	-> 862
/*     */     //   #395	-> 922
/*     */     //   #393	-> 958
/*     */     //   #397	-> 960
/*     */     //   #398	-> 989
/*     */     //   #399	-> 1049
/*     */     //   #397	-> 1085
/*     */     //   #401	-> 1087
/*     */     //   #403	-> 1095
/*     */     //   #404	-> 1107
/*     */     //   #405	-> 1125
/*     */     //   #407	-> 1135
/*     */     //   #408	-> 1139
/*     */     //   #409	-> 1143
/*     */     //   #412	-> 1147
/*     */     //   #414	-> 1157
/*     */     //   #415	-> 1186
/*     */     //   #416	-> 1246
/*     */     //   #414	-> 1282
/*     */     //   #418	-> 1284
/*     */     //   #420	-> 1292
/*     */     //   #421	-> 1304
/*     */     //   #422	-> 1322
/*     */     //   #424	-> 1332
/*     */     //   #425	-> 1336
/*     */     //   #426	-> 1340
/*     */     //   #428	-> 1344
/*     */     //   #430	-> 1352
/*     */     //   #431	-> 1364
/*     */     //   #432	-> 1382
/*     */     //   #434	-> 1392
/*     */     //   #435	-> 1396
/*     */     //   #436	-> 1400
/*     */     //   #440	-> 1404
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	1406	0	cloud	Ljava/util/List;
/*     */     //   8	1398	1	corners	Ljava/util/List;
/*     */     //   26	1380	2	minXY	LCatalano/Core/IntPoint;
/*     */     //   38	1368	3	maxXY	LCatalano/Core/IntPoint;
/*     */     //   14	1392	4	bounds	Ljava/util/List;
/*     */     //   45	1361	5	cloudSize	LCatalano/Core/IntPoint;
/*     */     //   60	1346	6	center	LCatalano/Core/IntPoint;
/*     */     //   80	1326	7	distortionLimit	F
/*     */     //   91	1315	8	point1	LCatalano/Core/IntPoint;
/*     */     //   102	1304	9	point2	LCatalano/Core/IntPoint;
/*     */     //   139	1267	10	point3	LCatalano/Core/IntPoint;
/*     */     //   157	1249	11	point4	LCatalano/Core/IntPoint;
/*     */     //   148	1258	12	distance3	F
/*     */     //   166	1240	13	distance4	F
/*     */     //   130	1276	14	fur	[LCatalano/Math/Geometry/PointsCloud$FurthestPoint;
/*     */     //   272	405	15	tempPoint	LCatalano/Core/IntPoint;
/*     */     //   321	356	16	thirdPointIsFound	Z
/*     */     //   506	171	17	tempDistance	F
/*     */     //   680	153	15	i	I
/*     */     //   688	145	16	n	I
/*     */     //   794	29	17	temp	LCatalano/Core/IntPoint;
/*     */     //   960	446	15	k1	F
/*     */     //   1087	319	16	k2	F
/*     */     //   1107	40	17	temp	LCatalano/Core/IntPoint;
/*     */     //   1139	8	18	tk	F
/*     */     //   1284	120	17	k3	F
/*     */     //   1304	40	18	temp	LCatalano/Core/IntPoint;
/*     */     //   1336	8	19	tk	F
/*     */     //   1364	40	18	temp	LCatalano/Core/IntPoint;
/*     */     //   1396	8	19	tk	F
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	1406	0	cloud	Ljava/util/List<LCatalano/Core/IntPoint;>;
/*     */     //   8	1398	1	corners	Ljava/util/List<LCatalano/Core/IntPoint;>;
/*     */     //   14	1392	4	bounds	Ljava/util/List<LCatalano/Core/IntPoint;>;
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
/*     */   public static class FurthestPoint
/*     */   {
/*     */     public int x;
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
/*     */     public int y;
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
/*     */     public float distance;
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
/*     */     public FurthestPoint() {}
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
/*     */     public FurthestPoint(int x, int y, float distance) {
/* 451 */       this.x = x;
/* 452 */       this.y = y;
/* 453 */       this.distance = distance;
/*     */     }
/*     */     
/*     */     public FurthestPoint(IntPoint p) {
/* 457 */       this.x = p.x;
/* 458 */       this.y = p.y;
/*     */     }
/*     */     
/*     */     public FurthestPoint(IntPoint p, float distance) {
/* 462 */       this.x = p.x;
/* 463 */       this.y = p.y;
/* 464 */       this.distance = distance;
/*     */     }
/*     */     
/*     */     public IntPoint toIntPoint() {
/* 468 */       return new IntPoint(this.x, this.y);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/PointsCloud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package Catalano.Math.Geometry;
/*    */ 
/*    */ import Catalano.Core.IntPoint;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeometryTools
/*    */ {
/*    */   public static float GetAngleBetweenVectors(IntPoint startPoint, IntPoint vector1end, IntPoint vector2end) {
/* 35 */     float x1 = (vector1end.x - startPoint.x);
/* 36 */     float y1 = (vector1end.y - startPoint.y);
/*    */     
/* 38 */     float x2 = (vector2end.x - startPoint.x);
/* 39 */     float y2 = (vector2end.y - startPoint.y);
/*    */     
/* 41 */     return (float)(Math.acos((x1 * x2 + y1 * y2) / Math.sqrt((x1 * x1 + y1 * y1)) * Math.sqrt((x2 * x2 + y2 * y2))) * 180.0D / Math.PI);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/GeometryTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
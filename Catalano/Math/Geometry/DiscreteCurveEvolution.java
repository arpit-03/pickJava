/*    */ package Catalano.Math.Geometry;
/*    */ 
/*    */ import Catalano.Core.IntPoint;
/*    */ import Catalano.Math.ComplexNumber;
/*    */ import Catalano.Math.Matrix;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class DiscreteCurveEvolution
/*    */   implements IShapeOptimizer
/*    */ {
/*    */   private int vertices;
/*    */   
/*    */   public DiscreteCurveEvolution() {
/* 24 */     this(20);
/*    */   }
/*    */   
/*    */   public DiscreteCurveEvolution(int vertices) {
/* 28 */     this.vertices = vertices;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<IntPoint> OptimizeShape(List<IntPoint> shape) {
/* 33 */     if (this.vertices > shape.size()) {
/* 34 */       throw new IllegalArgumentException("Number of points left must be higher than number of the shape.");
/*    */     }
/* 36 */     ArrayList<ComplexNumber> complex = new ArrayList<>(); int i;
/* 37 */     for (i = 0; i < shape.size(); i++) {
/* 38 */       complex.add(new ComplexNumber(((IntPoint)shape.get(i)).x, ((IntPoint)shape.get(i)).y));
/*    */     }
/* 40 */     for (i = 0; i < shape.size() - this.vertices; i++) {
/* 41 */       double[] winkelmaass = winkel(complex);
/*    */       
/* 43 */       int index = Matrix.MinIndex(winkelmaass);
/* 44 */       complex.remove(index);
/*    */     } 
/*    */     
/* 47 */     ArrayList<IntPoint> newShape = new ArrayList<>(complex.size());
/*    */     
/* 49 */     for (int j = 0; j < complex.size(); j++) {
/* 50 */       newShape.add(new IntPoint((int)((ComplexNumber)complex.get(j)).real, (int)((ComplexNumber)complex.get(j)).imaginary));
/*    */     }
/* 52 */     return newShape;
/*    */   }
/*    */   
/*    */   private double[] winkel(ArrayList<ComplexNumber> z) {
/* 56 */     int n = z.size();
/* 57 */     double max = -1.7976931348623157E308D;
/*    */     
/* 59 */     double[] his = new double[n];
/* 60 */     for (int j = 1; j < n - 1; j++) {
/* 61 */       ComplexNumber c = ComplexNumber.Subtract(z.get(j - 1), z.get(j + 1));
/*    */       
/* 63 */       double lm = c.getMagnitude();
/*    */       
/* 65 */       c = ComplexNumber.Subtract(z.get(j), z.get(j + 1));
/* 66 */       double lr = c.getMagnitude();
/*    */       
/* 68 */       c = ComplexNumber.Subtract(z.get(j - 1), z.get(j));
/* 69 */       double ll = c.getMagnitude();
/*    */       
/* 71 */       double alpha = Math.acos((lr * lr + ll * ll - lm * lm) / 2.0D * lr * ll);
/*    */ 
/*    */       
/* 74 */       double a = 180.0D - alpha * 180.0D / Math.PI;
/*    */ 
/*    */       
/* 77 */       his[j] = a * lr * ll / (lr + ll);
/*    */       
/* 79 */       if (his[j] > max) {
/* 80 */         max = his[j];
/*    */       }
/*    */     } 
/*    */     
/* 84 */     his[0] = max;
/* 85 */     his[n - 1] = max;
/*    */     
/* 87 */     return his;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/DiscreteCurveEvolution.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
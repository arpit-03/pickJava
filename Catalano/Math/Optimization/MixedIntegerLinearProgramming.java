/*     */ package Catalano.Math.Optimization;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixedIntegerLinearProgramming
/*     */ {
/*     */   public static final int INFEASIBLE = 0;
/*     */   public static final int OPTIMAL = 1;
/*     */   public static final int UNBOUNDED = 2;
/*     */   
/*     */   public enum Objective
/*     */   {
/*  59 */     Minimize,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     Maximize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private double tolL = 1.0E-5D;
/*  71 */   private double tolU = 0.99999D;
/*     */   
/*     */   private Objective objective;
/*     */   private LinearProgramming simplex;
/*  75 */   private int maxIteration = 100;
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] type;
/*     */ 
/*     */ 
/*     */   
/*     */   private Solution sol;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int[] type) {
/*  88 */     this.type = type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getCoefficients() {
/*  96 */     return this.sol.coef;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSolution() {
/* 104 */     return this.sol.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixedIntegerLinearProgramming(Objective objective) {
/* 112 */     this(objective, 1.0E-5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixedIntegerLinearProgramming(Objective objective, double tol) {
/* 121 */     this.objective = objective;
/* 122 */     this.tolL = tol;
/* 123 */     this.tolU = 1.0D - tol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int Solve(double[] function, List<Constraint> constraint) {
/* 134 */     int iter = 0;
/*     */     
/* 136 */     if (this.type == null) {
/* 137 */       throw new IllegalArgumentException("The data type must be definied.");
/*     */     }
/*     */ 
/*     */     
/* 141 */     double value = Double.NaN;
/* 142 */     if (this.objective == Objective.Maximize) {
/* 143 */       this.simplex = new LinearProgramming(LinearProgramming.Objective.Maximize);
/*     */     } else {
/*     */       
/* 146 */       this.simplex = new LinearProgramming(LinearProgramming.Objective.Minimize);
/*     */     } 
/*     */ 
/*     */     
/* 150 */     int status = this.simplex.Solve(function, constraint);
/* 151 */     if (status == 1) {
/* 152 */       boolean c = CheckSolution(this.simplex.getCoefficients(), function.length);
/* 153 */       if (c) {
/* 154 */         return 1;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 159 */       Stack<List<Constraint>> stack = new Stack<>();
/* 160 */       BranchAndBound(stack, constraint, this.simplex.getCoefficients(), function.length);
/*     */       
/* 162 */       List<Solution> solutions = new ArrayList<>();
/*     */       
/* 164 */       while (stack.size() > 0) {
/*     */         
/* 166 */         if (iter == this.maxIteration)
/* 167 */           break;  iter++;
/*     */         
/* 169 */         List<Constraint> p = stack.pop();
/* 170 */         int s = this.simplex.Solve(function, p);
/*     */         
/* 172 */         if (s == 1) {
/*     */           
/* 174 */           boolean b = CheckSolution(this.simplex.getCoefficients(), function.length);
/*     */           
/* 176 */           if (this.objective == Objective.Maximize) {
/* 177 */             if (b) {
/* 178 */               solutions.add(new Solution(this.simplex.getCoefficients(), this.simplex.getSolution()));
/*     */               
/* 180 */               if (Double.isNaN(value)) {
/* 181 */                 value = this.simplex.getSolution();
/*     */               }
/*     */               
/* 184 */               value = Math.max(value, this.simplex.getSolution());
/*     */               
/*     */               continue;
/*     */             } 
/* 188 */             if (Double.isNaN(value)) {
/* 189 */               BranchAndBound(stack, p, this.simplex.getCoefficients(), function.length);
/*     */             }
/* 191 */             if (!Double.isNaN(value) && this.simplex.getSolution() > value) {
/* 192 */               BranchAndBound(stack, p, this.simplex.getCoefficients(), function.length);
/*     */             }
/*     */             
/*     */             continue;
/*     */           } 
/* 197 */           if (b) {
/* 198 */             solutions.add(new Solution(this.simplex.getCoefficients(), this.simplex.getSolution()));
/*     */             
/* 200 */             if (Double.isNaN(value)) {
/* 201 */               value = this.simplex.getSolution();
/*     */             }
/*     */             
/* 204 */             value = Math.min(value, this.simplex.getSolution());
/*     */             
/*     */             continue;
/*     */           } 
/* 208 */           if (Double.isNaN(value)) {
/* 209 */             BranchAndBound(stack, p, this.simplex.getCoefficients(), function.length);
/*     */           }
/* 211 */           if (!Double.isNaN(value) && this.simplex.getSolution() < value) {
/* 212 */             BranchAndBound(stack, p, this.simplex.getCoefficients(), function.length);
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 219 */       if (solutions.isEmpty()) return 0;
/*     */       
/* 221 */       if (this.objective == Objective.Maximize) {
/* 222 */         Collections.sort(solutions, new Comparator<Solution>()
/*     */             {
/*     */               public int compare(MixedIntegerLinearProgramming.Solution o1, MixedIntegerLinearProgramming.Solution o2) {
/* 225 */                 return Double.compare(o2.z, o1.z);
/*     */               }
/*     */             });
/*     */       } else {
/*     */         
/* 230 */         Collections.sort(solutions, new Comparator<Solution>()
/*     */             {
/*     */               public int compare(MixedIntegerLinearProgramming.Solution o1, MixedIntegerLinearProgramming.Solution o2) {
/* 233 */                 return Double.compare(o1.z, o2.z);
/*     */               }
/*     */             });
/*     */       } 
/*     */       
/* 238 */       this.sol = solutions.get(0);
/*     */       
/* 240 */       return 1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 245 */     return status;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void BranchAndBound(Stack<List<Constraint>> stack, List<Constraint> constraint, double[] solution, int n) {
/* 251 */     int index = GetUpperValue(solution, n);
/*     */     
/* 253 */     double lower = Math.floor(solution[index]);
/* 254 */     double upper = Math.ceil(solution[index]);
/*     */     
/* 256 */     double[] v = new double[n];
/* 257 */     v[index] = 1.0D;
/*     */     
/* 259 */     List<Constraint> c1 = new ArrayList<>(constraint);
/* 260 */     c1.add(new Constraint(v, Constraint.Symbol.GREATER_THAN, upper));
/*     */     
/* 262 */     List<Constraint> c2 = new ArrayList<>(constraint);
/* 263 */     c2.add(new Constraint(v, Constraint.Symbol.LESS_THAN, lower));
/*     */     
/* 265 */     stack.add(c1);
/* 266 */     stack.add(c2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int GetUpperValue(double[] solution, int n) {
/* 272 */     double min = -1.7976931348623157E308D;
/* 273 */     int index = -1;
/* 274 */     for (int i = 0; i < n; i++) {
/* 275 */       if (solution[i] % 1.0D != 0.0D && 
/* 276 */         solution[i] > min) {
/* 277 */         min = Math.max(min, solution[i]);
/* 278 */         index = i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 284 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean CheckSolution(double[] solution, int n) {
/* 290 */     for (int i = 0; i < n; i++) {
/* 291 */       if (this.type[i] == 1 && 
/* 292 */         !isInteger(solution[i])) {
/* 293 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInteger(double value) {
/* 308 */     double d = Math.abs(Math.floor(value) - value);
/* 309 */     if (d < this.tolL) return true; 
/* 310 */     if (d - this.tolU >= 0.0D) return true; 
/* 311 */     return (d < this.tolL);
/*     */   }
/*     */   
/*     */   class Solution {
/*     */     private double[] coef;
/*     */     private double z;
/*     */     
/*     */     public Solution(double[] coef, double z) {
/* 319 */       this.coef = coef;
/* 320 */       this.z = z;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Optimization/MixedIntegerLinearProgramming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
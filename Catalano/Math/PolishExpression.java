/*     */ package Catalano.Math;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class PolishExpression
/*     */ {
/*     */   public enum Associativity
/*     */   {
/*  47 */     Left,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     Right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   private static int LEFT = 0;
/*  59 */   private static int RIGHT = 1;
/*     */   
/*  61 */   private static final Map<String, int[]> OPERATORS = (Map)new HashMap<>();
/*     */   
/*     */   static {
/*  64 */     OPERATORS.put("+", new int[] { 0, LEFT });
/*  65 */     OPERATORS.put("-", new int[] { 0, LEFT });
/*  66 */     OPERATORS.put("*", new int[] { 1, LEFT });
/*  67 */     OPERATORS.put("/", new int[] { 1, LEFT });
/*  68 */     OPERATORS.put("%", new int[] { 1, LEFT });
/*  69 */     OPERATORS.put("mod", new int[] { 1, LEFT });
/*  70 */     OPERATORS.put("abs", new int[] { 1, RIGHT });
/*  71 */     OPERATORS.put("ln", new int[] { 1, RIGHT });
/*  72 */     OPERATORS.put("sin", new int[] { 1, RIGHT });
/*  73 */     OPERATORS.put("cos", new int[] { 1, RIGHT });
/*  74 */     OPERATORS.put("sum", new int[] { 1, RIGHT });
/*  75 */     OPERATORS.put("^", new int[] { 2, RIGHT });
/*  76 */     OPERATORS.put("pow", new int[] { 2, RIGHT });
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
/*     */   public void AddOperator(String symbol, int precedence, Associativity associativity) {
/*  91 */     switch (associativity) {
/*     */       case null:
/*  93 */         OPERATORS.put(symbol, new int[] { precedence });
/*     */         break;
/*     */       case Right:
/*  96 */         OPERATORS.put(symbol, new int[] { precedence, 1 });
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Evaluate(String rpn) {
/* 107 */     return Evaluate(rpn.split(" "));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double Evaluate(String[] rpnTokens) {
/* 117 */     Stack<Double> values = new Stack<>();
/*     */     
/* 119 */     double result = 0.0D;
/* 120 */     for (int i = 0; i < rpnTokens.length; i++) {
/* 121 */       if (Tools.isNumeric(rpnTokens[i])) {
/* 122 */         values.push(Double.valueOf(Double.parseDouble(rpnTokens[i])));
/*     */       } else {
/* 124 */         result = Calc(values, rpnTokens[i]);
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double Calc(Stack<Double> values, String op) {
/* 217 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toReversePolishNotation(String infixNotation) {
/* 227 */     String[] tokens = toReversePolishNotation(infixNotation.split(" "));
/* 228 */     return Arrays.toString((Object[])tokens);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] toReversePolishNotation(String[] inputTokens) {
/* 237 */     ArrayList<String> out = new ArrayList<>();
/* 238 */     Stack<String> stack = new Stack<>(); byte b; int i;
/*     */     String[] arrayOfString1;
/* 240 */     for (i = (arrayOfString1 = inputTokens).length, b = 0; b < i; ) { String token = arrayOfString1[b];
/* 241 */       if (isOperator(token)) {
/*     */         
/* 243 */         while (!stack.empty() && isOperator(stack.peek())) {
/*     */           
/* 245 */           if ((isAssociative(token, LEFT) && cmpPrecedence(token, stack.peek()) <= 0) || (isAssociative(token, RIGHT) && cmpPrecedence(token, stack.peek()) < 0)) {
/* 246 */             out.add(stack.pop());
/*     */             
/*     */             continue;
/*     */           } 
/*     */           break;
/*     */         } 
/* 252 */         stack.push(token);
/* 253 */       } else if (token.equals("(")) {
/* 254 */         stack.push(token);
/* 255 */       } else if (token.equals(")")) {
/*     */         
/* 257 */         while (!stack.empty() && !((String)stack.peek()).equals("(")) {
/* 258 */           out.add(stack.pop());
/*     */         }
/* 260 */         stack.pop();
/*     */       } else {
/* 262 */         out.add(token);
/*     */       }  b++; }
/*     */     
/* 265 */     while (!stack.empty()) {
/* 266 */       out.add(stack.pop());
/*     */     }
/*     */     
/* 269 */     String[] output = new String[out.size()];
/* 270 */     return out.<String>toArray(output);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isOperator(String token) {
/* 279 */     return OPERATORS.containsKey(token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isAssociative(String token, int type) {
/* 289 */     if (!isOperator(token)) {
/* 290 */       throw new IllegalArgumentException("Invalid token: " + token);
/*     */     }
/* 292 */     if (((int[])OPERATORS.get(token))[1] == type) {
/* 293 */       return true;
/*     */     }
/* 295 */     return false;
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
/*     */   private static int cmpPrecedence(String token1, String token2) {
/* 307 */     if (!isOperator(token1) || !isOperator(token2)) {
/* 308 */       throw new IllegalArgumentException("Invalied tokens: " + token1 + " " + token2);
/*     */     }
/* 310 */     return ((int[])OPERATORS.get(token1))[0] - ((int[])OPERATORS.get(token2))[0];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/PolishExpression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
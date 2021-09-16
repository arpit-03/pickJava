/*    */ package edu.mines.jtk.bench;
/*    */ 
/*    */ import java.util.Scanner;
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
/*    */ public class ScannerBug
/*    */ {
/* 25 */   private static final String eol = System.getProperty("line.separator");
/* 26 */   private static final String input1 = "line 1" + eol + "" + eol + "line 3" + eol + "" + eol + "line 5" + eol + "" + eol + "line 7" + eol;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static final String input2 = "line 1" + eol + " " + eol + "line 3" + eol + " " + eol + "line 5" + eol + " " + eol + "line 7" + eol;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void scan(String input) {
/* 43 */     Scanner s = new Scanner(input);
/* 44 */     while (s.hasNextLine()) {
/* 45 */       s.findInLine("5");
/* 46 */       System.out.println(s.nextLine());
/*    */     } 
/* 48 */     s.close();
/*    */   }
/*    */   public static void main(String[] args) {
/* 51 */     System.out.println("Scanning input with empty lines (incorrectly):");
/* 52 */     scan(input1);
/* 53 */     System.out.println("Scanning input without empty lines (correctly):");
/* 54 */     scan(input2);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/bench/ScannerBug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
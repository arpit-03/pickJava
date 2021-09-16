/*    */ package us.hebi.matlab.mat.format;
/*    */ 
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
/*    */ class McosRegistry
/*    */ {
/*    */   synchronized McosReference register(McosReference reference) {
/* 32 */     this.references.add(reference);
/* 33 */     return reference;
/*    */   }
/*    */   
/*    */   List<McosReference> getReferences() {
/* 37 */     return this.references;
/*    */   }
/*    */   
/* 40 */   private final List<McosReference> references = new ArrayList<>();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/McosRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
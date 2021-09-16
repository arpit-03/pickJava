/*    */ package Catalano.Core;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stopwatch
/*    */ {
/*    */   private Long t1;
/*    */   private Long t2;
/*    */   private Long t3;
/*    */   
/*    */   public void Start() {
/* 41 */     this.t1 = Long.valueOf(System.currentTimeMillis());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void Stop() {
/* 48 */     this.t2 = Long.valueOf(System.currentTimeMillis());
/* 49 */     this.t3 = Long.valueOf(this.t2.longValue() - this.t1.longValue());
/* 50 */     Reset();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void Reset() {
/* 57 */     this.t1 = this.t2 = Long.valueOf(0L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void Restart() {
/* 64 */     Reset();
/* 65 */     this.t1 = Long.valueOf(System.currentTimeMillis());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Long ElapsedMilliseconds() {
/* 73 */     return this.t3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 81 */     return (this.t1.longValue() != 0L);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Core/Stopwatch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
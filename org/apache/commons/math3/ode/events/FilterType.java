/*     */ package org.apache.commons.math3.ode.events;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum FilterType
/*     */ {
/*  35 */   TRIGGER_ONLY_DECREASING_EVENTS
/*     */   {
/*     */     
/*     */     protected boolean getTriggeredIncreasing()
/*     */     {
/*  40 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Transformer selectTransformer(Transformer previous, double g, boolean forward) {
/*  92 */       if (forward) {
/*  93 */         switch (previous) {
/*     */           
/*     */           case UNINITIALIZED:
/*  96 */             if (g > 0.0D)
/*     */             {
/*  98 */               return Transformer.MAX; } 
/*  99 */             if (g < 0.0D)
/*     */             {
/* 101 */               return Transformer.PLUS;
/*     */             }
/*     */ 
/*     */             
/* 105 */             return Transformer.UNINITIALIZED;
/*     */           
/*     */           case PLUS:
/* 108 */             if (g >= 0.0D)
/*     */             {
/*     */               
/* 111 */               return Transformer.MIN;
/*     */             }
/*     */             
/* 114 */             return previous;
/*     */           
/*     */           case MINUS:
/* 117 */             if (g >= 0.0D)
/*     */             {
/*     */               
/* 120 */               return Transformer.MAX;
/*     */             }
/*     */             
/* 123 */             return previous;
/*     */           
/*     */           case MIN:
/* 126 */             if (g <= 0.0D)
/*     */             {
/*     */               
/* 129 */               return Transformer.MINUS;
/*     */             }
/*     */             
/* 132 */             return previous;
/*     */           
/*     */           case MAX:
/* 135 */             if (g <= 0.0D)
/*     */             {
/*     */               
/* 138 */               return Transformer.PLUS;
/*     */             }
/*     */             
/* 141 */             return previous;
/*     */         } 
/*     */ 
/*     */         
/* 145 */         throw new MathInternalError();
/*     */       } 
/*     */       
/* 148 */       switch (previous) {
/*     */         
/*     */         case UNINITIALIZED:
/* 151 */           if (g > 0.0D)
/*     */           {
/* 153 */             return Transformer.MINUS; } 
/* 154 */           if (g < 0.0D)
/*     */           {
/* 156 */             return Transformer.MIN;
/*     */           }
/*     */ 
/*     */           
/* 160 */           return Transformer.UNINITIALIZED;
/*     */         
/*     */         case PLUS:
/* 163 */           if (g <= 0.0D)
/*     */           {
/*     */             
/* 166 */             return Transformer.MAX;
/*     */           }
/*     */           
/* 169 */           return previous;
/*     */         
/*     */         case MINUS:
/* 172 */           if (g <= 0.0D)
/*     */           {
/*     */             
/* 175 */             return Transformer.MIN;
/*     */           }
/*     */           
/* 178 */           return previous;
/*     */         
/*     */         case MIN:
/* 181 */           if (g >= 0.0D)
/*     */           {
/*     */             
/* 184 */             return Transformer.PLUS;
/*     */           }
/*     */           
/* 187 */           return previous;
/*     */         
/*     */         case MAX:
/* 190 */           if (g >= 0.0D)
/*     */           {
/*     */             
/* 193 */             return Transformer.MINUS;
/*     */           }
/*     */           
/* 196 */           return previous;
/*     */       } 
/*     */ 
/*     */       
/* 200 */       throw new MathInternalError();
/*     */     }
/*     */   },
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 213 */   TRIGGER_ONLY_INCREASING_EVENTS
/*     */   {
/*     */     
/*     */     protected boolean getTriggeredIncreasing()
/*     */     {
/* 218 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected Transformer selectTransformer(Transformer previous, double g, boolean forward) {
/* 270 */       if (forward) {
/* 271 */         switch (previous) {
/*     */           
/*     */           case UNINITIALIZED:
/* 274 */             if (g > 0.0D)
/*     */             {
/* 276 */               return Transformer.PLUS; } 
/* 277 */             if (g < 0.0D)
/*     */             {
/* 279 */               return Transformer.MIN;
/*     */             }
/*     */ 
/*     */             
/* 283 */             return Transformer.UNINITIALIZED;
/*     */           
/*     */           case PLUS:
/* 286 */             if (g <= 0.0D)
/*     */             {
/*     */               
/* 289 */               return Transformer.MAX;
/*     */             }
/*     */             
/* 292 */             return previous;
/*     */           
/*     */           case MINUS:
/* 295 */             if (g <= 0.0D)
/*     */             {
/*     */               
/* 298 */               return Transformer.MIN;
/*     */             }
/*     */             
/* 301 */             return previous;
/*     */           
/*     */           case MIN:
/* 304 */             if (g >= 0.0D)
/*     */             {
/*     */               
/* 307 */               return Transformer.PLUS;
/*     */             }
/*     */             
/* 310 */             return previous;
/*     */           
/*     */           case MAX:
/* 313 */             if (g >= 0.0D)
/*     */             {
/*     */               
/* 316 */               return Transformer.MINUS;
/*     */             }
/*     */             
/* 319 */             return previous;
/*     */         } 
/*     */ 
/*     */         
/* 323 */         throw new MathInternalError();
/*     */       } 
/*     */       
/* 326 */       switch (previous) {
/*     */         
/*     */         case UNINITIALIZED:
/* 329 */           if (g > 0.0D)
/*     */           {
/* 331 */             return Transformer.MAX; } 
/* 332 */           if (g < 0.0D)
/*     */           {
/* 334 */             return Transformer.MINUS;
/*     */           }
/*     */ 
/*     */           
/* 338 */           return Transformer.UNINITIALIZED;
/*     */         
/*     */         case PLUS:
/* 341 */           if (g >= 0.0D)
/*     */           {
/*     */             
/* 344 */             return Transformer.MIN;
/*     */           }
/*     */           
/* 347 */           return previous;
/*     */         
/*     */         case MINUS:
/* 350 */           if (g >= 0.0D)
/*     */           {
/*     */             
/* 353 */             return Transformer.MAX;
/*     */           }
/*     */           
/* 356 */           return previous;
/*     */         
/*     */         case MIN:
/* 359 */           if (g <= 0.0D)
/*     */           {
/*     */             
/* 362 */             return Transformer.MINUS;
/*     */           }
/*     */           
/* 365 */           return previous;
/*     */         
/*     */         case MAX:
/* 368 */           if (g <= 0.0D)
/*     */           {
/*     */             
/* 371 */             return Transformer.PLUS;
/*     */           }
/*     */           
/* 374 */           return previous;
/*     */       } 
/*     */ 
/*     */       
/* 378 */       throw new MathInternalError();
/*     */     }
/*     */   };
/*     */   
/*     */   protected abstract boolean getTriggeredIncreasing();
/*     */   
/*     */   protected abstract Transformer selectTransformer(Transformer paramTransformer, double paramDouble, boolean paramBoolean);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/events/FilterType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
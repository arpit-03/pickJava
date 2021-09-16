/*     */ package org.apache.commons.math3.ml.neuralnet.twod.util;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.ml.neuralnet.Neuron;
/*     */ import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocationFinder
/*     */ {
/*  32 */   private final Map<Long, Location> locations = new HashMap<Long, Location>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Location
/*     */   {
/*     */     private final int row;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int column;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Location(int row, int column) {
/*  49 */       this.row = row;
/*  50 */       this.column = column;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getRow() {
/*  57 */       return this.row;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getColumn() {
/*  64 */       return this.column;
/*     */     }
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
/*     */   public LocationFinder(NeuronSquareMesh2D map) {
/*  80 */     int nR = map.getNumberOfRows();
/*  81 */     int nC = map.getNumberOfColumns();
/*     */     
/*  83 */     for (int r = 0; r < nR; r++) {
/*  84 */       for (int c = 0; c < nC; c++) {
/*  85 */         Long id = Long.valueOf(map.getNeuron(r, c).getIdentifier());
/*  86 */         if (this.locations.get(id) != null) {
/*  87 */           throw new MathIllegalStateException();
/*     */         }
/*  89 */         this.locations.put(id, new Location(r, c));
/*     */       } 
/*     */     } 
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
/*     */   public Location getLocation(Neuron n) {
/* 103 */     return this.locations.get(Long.valueOf(n.getIdentifier()));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/twod/util/LocationFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
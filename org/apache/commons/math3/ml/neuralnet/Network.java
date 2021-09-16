/*     */ package org.apache.commons.math3.ml.neuralnet;
/*     */ 
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Network
/*     */   implements Iterable<Neuron>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20130207L;
/*  52 */   private final ConcurrentHashMap<Long, Neuron> neuronMap = new ConcurrentHashMap<Long, Neuron>();
/*     */ 
/*     */   
/*     */   private final AtomicLong nextId;
/*     */   
/*     */   private final int featureSize;
/*     */   
/*  59 */   private final ConcurrentHashMap<Long, Set<Long>> linkMap = new ConcurrentHashMap<Long, Set<Long>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class NeuronIdentifierComparator
/*     */     implements Comparator<Neuron>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130207L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Neuron a, Neuron b) {
/*  75 */       long aId = a.getIdentifier();
/*  76 */       long bId = b.getIdentifier();
/*  77 */       return (aId < bId) ? -1 : ((aId > bId) ? 1 : 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Network(long nextId, int featureSize, Neuron[] neuronList, long[][] neighbourIdList) {
/*  97 */     int numNeurons = neuronList.length;
/*  98 */     if (numNeurons != neighbourIdList.length) {
/*  99 */       throw new MathIllegalStateException();
/*     */     }
/*     */     int i;
/* 102 */     for (i = 0; i < numNeurons; i++) {
/* 103 */       Neuron n = neuronList[i];
/* 104 */       long id = n.getIdentifier();
/* 105 */       if (id >= nextId) {
/* 106 */         throw new MathIllegalStateException();
/*     */       }
/* 108 */       this.neuronMap.put(Long.valueOf(id), n);
/* 109 */       this.linkMap.put(Long.valueOf(id), new HashSet<Long>());
/*     */     } 
/*     */     
/* 112 */     for (i = 0; i < numNeurons; i++) {
/* 113 */       long aId = neuronList[i].getIdentifier();
/* 114 */       Set<Long> aLinks = this.linkMap.get(Long.valueOf(aId)); long[] arr$; int len$, i$;
/* 115 */       for (arr$ = neighbourIdList[i], len$ = arr$.length, i$ = 0; i$ < len$; ) { Long bId = Long.valueOf(arr$[i$]);
/* 116 */         if (this.neuronMap.get(bId) == null) {
/* 117 */           throw new MathIllegalStateException();
/*     */         }
/* 119 */         addLinkToLinkSet(aLinks, bId.longValue());
/*     */         i$++; }
/*     */     
/*     */     } 
/* 123 */     this.nextId = new AtomicLong(nextId);
/* 124 */     this.featureSize = featureSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Network(long initialIdentifier, int featureSize) {
/* 134 */     this.nextId = new AtomicLong(initialIdentifier);
/* 135 */     this.featureSize = featureSize;
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
/*     */   public synchronized Network copy() {
/* 147 */     Network copy = new Network(this.nextId.get(), this.featureSize);
/*     */ 
/*     */ 
/*     */     
/* 151 */     for (Map.Entry<Long, Neuron> e : this.neuronMap.entrySet()) {
/* 152 */       copy.neuronMap.put(e.getKey(), ((Neuron)e.getValue()).copy());
/*     */     }
/*     */     
/* 155 */     for (Map.Entry<Long, Set<Long>> e : this.linkMap.entrySet()) {
/* 156 */       copy.linkMap.put(e.getKey(), new HashSet<Long>(e.getValue()));
/*     */     }
/*     */     
/* 159 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Neuron> iterator() {
/* 166 */     return this.neuronMap.values().iterator();
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
/*     */   public Collection<Neuron> getNeurons(Comparator<Neuron> comparator) {
/* 178 */     List<Neuron> neurons = new ArrayList<Neuron>();
/* 179 */     neurons.addAll(this.neuronMap.values());
/*     */     
/* 181 */     Collections.sort(neurons, comparator);
/*     */     
/* 183 */     return neurons;
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
/*     */   public long createNeuron(double[] features) {
/* 196 */     if (features.length != this.featureSize) {
/* 197 */       throw new DimensionMismatchException(features.length, this.featureSize);
/*     */     }
/*     */     
/* 200 */     long id = createNextId().longValue();
/* 201 */     this.neuronMap.put(Long.valueOf(id), new Neuron(id, features));
/* 202 */     this.linkMap.put(Long.valueOf(id), new HashSet<Long>());
/* 203 */     return id;
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
/*     */   public void deleteNeuron(Neuron neuron) {
/* 216 */     Collection<Neuron> neighbours = getNeighbours(neuron);
/*     */ 
/*     */     
/* 219 */     for (Neuron n : neighbours) {
/* 220 */       deleteLink(n, neuron);
/*     */     }
/*     */ 
/*     */     
/* 224 */     this.neuronMap.remove(Long.valueOf(neuron.getIdentifier()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFeaturesSize() {
/* 233 */     return this.featureSize;
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
/*     */   public void addLink(Neuron a, Neuron b) {
/* 249 */     long aId = a.getIdentifier();
/* 250 */     long bId = b.getIdentifier();
/*     */ 
/*     */     
/* 253 */     if (a != getNeuron(aId)) {
/* 254 */       throw new NoSuchElementException(Long.toString(aId));
/*     */     }
/* 256 */     if (b != getNeuron(bId)) {
/* 257 */       throw new NoSuchElementException(Long.toString(bId));
/*     */     }
/*     */ 
/*     */     
/* 261 */     addLinkToLinkSet(this.linkMap.get(Long.valueOf(aId)), bId);
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
/*     */   private void addLinkToLinkSet(Set<Long> linkSet, long id) {
/* 274 */     linkSet.add(Long.valueOf(id));
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
/*     */   public void deleteLink(Neuron a, Neuron b) {
/* 287 */     long aId = a.getIdentifier();
/* 288 */     long bId = b.getIdentifier();
/*     */ 
/*     */     
/* 291 */     if (a != getNeuron(aId)) {
/* 292 */       throw new NoSuchElementException(Long.toString(aId));
/*     */     }
/* 294 */     if (b != getNeuron(bId)) {
/* 295 */       throw new NoSuchElementException(Long.toString(bId));
/*     */     }
/*     */ 
/*     */     
/* 299 */     deleteLinkFromLinkSet(this.linkMap.get(Long.valueOf(aId)), bId);
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
/*     */   private void deleteLinkFromLinkSet(Set<Long> linkSet, long id) {
/* 312 */     linkSet.remove(Long.valueOf(id));
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
/*     */   public Neuron getNeuron(long id) {
/* 324 */     Neuron n = this.neuronMap.get(Long.valueOf(id));
/* 325 */     if (n == null) {
/* 326 */       throw new NoSuchElementException(Long.toString(id));
/*     */     }
/* 328 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Neuron> getNeighbours(Iterable<Neuron> neurons) {
/* 339 */     return getNeighbours(neurons, (Iterable<Neuron>)null);
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
/*     */   public Collection<Neuron> getNeighbours(Iterable<Neuron> neurons, Iterable<Neuron> exclude) {
/* 356 */     Set<Long> idList = new HashSet<Long>();
/*     */     
/* 358 */     for (Neuron n : neurons) {
/* 359 */       idList.addAll(this.linkMap.get(Long.valueOf(n.getIdentifier())));
/*     */     }
/* 361 */     if (exclude != null) {
/* 362 */       for (Neuron n : exclude) {
/* 363 */         idList.remove(Long.valueOf(n.getIdentifier()));
/*     */       }
/*     */     }
/*     */     
/* 367 */     List<Neuron> neuronList = new ArrayList<Neuron>();
/* 368 */     for (Long id : idList) {
/* 369 */       neuronList.add(getNeuron(id.longValue()));
/*     */     }
/*     */     
/* 372 */     return neuronList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Neuron> getNeighbours(Neuron neuron) {
/* 383 */     return getNeighbours(neuron, (Iterable<Neuron>)null);
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
/*     */   public Collection<Neuron> getNeighbours(Neuron neuron, Iterable<Neuron> exclude) {
/* 396 */     Set<Long> idList = this.linkMap.get(Long.valueOf(neuron.getIdentifier()));
/* 397 */     if (exclude != null) {
/* 398 */       for (Neuron n : exclude) {
/* 399 */         idList.remove(Long.valueOf(n.getIdentifier()));
/*     */       }
/*     */     }
/*     */     
/* 403 */     List<Neuron> neuronList = new ArrayList<Neuron>();
/* 404 */     for (Long id : idList) {
/* 405 */       neuronList.add(getNeuron(id.longValue()));
/*     */     }
/*     */     
/* 408 */     return neuronList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Long createNextId() {
/* 417 */     return Long.valueOf(this.nextId.getAndIncrement());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) {
/* 426 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 435 */     Neuron[] neuronList = (Neuron[])this.neuronMap.values().toArray((Object[])new Neuron[0]);
/* 436 */     long[][] neighbourIdList = new long[neuronList.length][];
/*     */     
/* 438 */     for (int i = 0; i < neuronList.length; i++) {
/* 439 */       Collection<Neuron> neighbours = getNeighbours(neuronList[i]);
/* 440 */       long[] neighboursId = new long[neighbours.size()];
/* 441 */       int count = 0;
/* 442 */       for (Neuron n : neighbours) {
/* 443 */         neighboursId[count] = n.getIdentifier();
/* 444 */         count++;
/*     */       } 
/* 446 */       neighbourIdList[i] = neighboursId;
/*     */     } 
/*     */     
/* 449 */     return new SerializationProxy(this.nextId.get(), this.featureSize, neuronList, neighbourIdList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SerializationProxy
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130207L;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long nextId;
/*     */ 
/*     */ 
/*     */     
/*     */     private final int featureSize;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Neuron[] neuronList;
/*     */ 
/*     */ 
/*     */     
/*     */     private final long[][] neighbourIdList;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SerializationProxy(long nextId, int featureSize, Neuron[] neuronList, long[][] neighbourIdList) {
/* 481 */       this.nextId = nextId;
/* 482 */       this.featureSize = featureSize;
/* 483 */       this.neuronList = neuronList;
/* 484 */       this.neighbourIdList = neighbourIdList;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 493 */       return new Network(this.nextId, this.featureSize, this.neuronList, this.neighbourIdList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/neuralnet/Network.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
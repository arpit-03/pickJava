/*     */ package org.apache.commons.math3.geometry.partitioning.utilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class AVLTree<T extends Comparable<T>>
/*     */ {
/*  57 */   private Node top = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(T element) {
/*  64 */     if (element != null) {
/*  65 */       if (this.top == null) {
/*  66 */         this.top = new Node(element, null);
/*     */       } else {
/*  68 */         this.top.insert(element);
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
/*     */ 
/*     */   
/*     */   public boolean delete(T element) {
/*  84 */     if (element != null) {
/*  85 */       for (Node node = getNotSmaller(element); node != null; node = node.getNext()) {
/*     */ 
/*     */         
/*  88 */         if (node.element == element) {
/*  89 */           node.delete();
/*  90 */           return true;
/*  91 */         }  if (node.element.compareTo(element) > 0)
/*     */         {
/*     */           
/*  94 */           return false;
/*     */         }
/*     */       } 
/*     */     }
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 105 */     return (this.top == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 113 */     return (this.top == null) ? 0 : this.top.size();
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
/*     */   public Node getSmallest() {
/* 126 */     return (this.top == null) ? null : this.top.getSmallest();
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
/*     */   public Node getLargest() {
/* 139 */     return (this.top == null) ? null : this.top.getLargest();
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
/*     */   public Node getNotSmaller(T reference) {
/* 154 */     Node candidate = null;
/* 155 */     for (Node node = this.top; node != null; ) {
/* 156 */       if (node.element.compareTo(reference) < 0) {
/* 157 */         if (node.right == null) {
/* 158 */           return candidate;
/*     */         }
/* 160 */         node = node.right; continue;
/*     */       } 
/* 162 */       candidate = node;
/* 163 */       if (node.left == null) {
/* 164 */         return candidate;
/*     */       }
/* 166 */       node = node.left;
/*     */     } 
/*     */     
/* 169 */     return null;
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
/*     */   public Node getNotLarger(T reference) {
/* 185 */     Node candidate = null;
/* 186 */     for (Node node = this.top; node != null; ) {
/* 187 */       if (node.element.compareTo(reference) > 0) {
/* 188 */         if (node.left == null) {
/* 189 */           return candidate;
/*     */         }
/* 191 */         node = node.left; continue;
/*     */       } 
/* 193 */       candidate = node;
/* 194 */       if (node.right == null) {
/* 195 */         return candidate;
/*     */       }
/* 197 */       node = node.right;
/*     */     } 
/*     */     
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private enum Skew
/*     */   {
/* 206 */     LEFT_HIGH,
/*     */ 
/*     */     
/* 209 */     RIGHT_HIGH,
/*     */ 
/*     */     
/* 212 */     BALANCED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Node
/*     */   {
/*     */     private T element;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Node left;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Node right;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Node parent;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AVLTree.Skew skew;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Node(T element, Node parent) {
/* 248 */       this.element = element;
/* 249 */       this.left = null;
/* 250 */       this.right = null;
/* 251 */       this.parent = parent;
/* 252 */       this.skew = AVLTree.Skew.BALANCED;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T getElement() {
/* 259 */       return this.element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int size() {
/* 266 */       return 1 + ((this.left == null) ? 0 : this.left.size()) + ((this.right == null) ? 0 : this.right.size());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Node getSmallest() {
/* 276 */       Node node = this;
/* 277 */       while (node.left != null) {
/* 278 */         node = node.left;
/*     */       }
/* 280 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Node getLargest() {
/* 290 */       Node node = this;
/* 291 */       while (node.right != null) {
/* 292 */         node = node.right;
/*     */       }
/* 294 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node getPrevious() {
/* 304 */       if (this.left != null) {
/* 305 */         Node node1 = this.left.getLargest();
/* 306 */         if (node1 != null) {
/* 307 */           return node1;
/*     */         }
/*     */       } 
/*     */       
/* 311 */       for (Node node = this; node.parent != null; node = node.parent) {
/* 312 */         if (node != node.parent.left) {
/* 313 */           return node.parent;
/*     */         }
/*     */       } 
/*     */       
/* 317 */       return null;
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
/*     */     public Node getNext() {
/* 329 */       if (this.right != null) {
/* 330 */         Node node1 = this.right.getSmallest();
/* 331 */         if (node1 != null) {
/* 332 */           return node1;
/*     */         }
/*     */       } 
/*     */       
/* 336 */       for (Node node = this; node.parent != null; node = node.parent) {
/* 337 */         if (node != node.parent.right) {
/* 338 */           return node.parent;
/*     */         }
/*     */       } 
/*     */       
/* 342 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean insert(T newElement) {
/* 351 */       if (newElement.compareTo(this.element) < 0) {
/*     */         
/* 353 */         if (this.left == null) {
/* 354 */           this.left = new Node(newElement, this);
/* 355 */           return rebalanceLeftGrown();
/*     */         } 
/* 357 */         return this.left.insert(newElement) ? rebalanceLeftGrown() : false;
/*     */       } 
/*     */ 
/*     */       
/* 361 */       if (this.right == null) {
/* 362 */         this.right = new Node(newElement, this);
/* 363 */         return rebalanceRightGrown();
/*     */       } 
/* 365 */       return this.right.insert(newElement) ? rebalanceRightGrown() : false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void delete() {
/* 372 */       if (this.parent == null && this.left == null && this.right == null) {
/*     */         
/* 374 */         this.element = null;
/* 375 */         AVLTree.this.top = null;
/*     */       } else {
/*     */         Node child;
/*     */         
/*     */         boolean leftShrunk;
/*     */         
/* 381 */         if (this.left == null && this.right == null) {
/* 382 */           node = this;
/* 383 */           this.element = null;
/* 384 */           leftShrunk = (node == node.parent.left);
/* 385 */           child = null;
/*     */         } else {
/* 387 */           node = (this.left != null) ? this.left.getLargest() : this.right.getSmallest();
/* 388 */           this.element = node.element;
/* 389 */           leftShrunk = (node == node.parent.left);
/* 390 */           child = (node.left != null) ? node.left : node.right;
/*     */         } 
/*     */         
/* 393 */         Node node = node.parent;
/* 394 */         if (leftShrunk) {
/* 395 */           node.left = child;
/*     */         } else {
/* 397 */           node.right = child;
/*     */         } 
/* 399 */         if (child != null) {
/* 400 */           child.parent = node;
/*     */         }
/*     */         
/* 403 */         while (leftShrunk ? node.rebalanceLeftShrunk() : node.rebalanceRightShrunk()) {
/* 404 */           if (node.parent == null) {
/*     */             return;
/*     */           }
/* 407 */           leftShrunk = (node == node.parent.left);
/* 408 */           node = node.parent;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean rebalanceLeftGrown() {
/* 418 */       switch (this.skew) {
/*     */         case LEFT_HIGH:
/* 420 */           if (this.left.skew == AVLTree.Skew.LEFT_HIGH) {
/* 421 */             rotateCW();
/* 422 */             this.skew = AVLTree.Skew.BALANCED;
/* 423 */             this.right.skew = AVLTree.Skew.BALANCED;
/*     */           } else {
/* 425 */             AVLTree.Skew s = this.left.right.skew;
/* 426 */             this.left.rotateCCW();
/* 427 */             rotateCW();
/* 428 */             switch (s) {
/*     */               case LEFT_HIGH:
/* 430 */                 this.left.skew = AVLTree.Skew.BALANCED;
/* 431 */                 this.right.skew = AVLTree.Skew.RIGHT_HIGH;
/*     */                 break;
/*     */               case RIGHT_HIGH:
/* 434 */                 this.left.skew = AVLTree.Skew.LEFT_HIGH;
/* 435 */                 this.right.skew = AVLTree.Skew.BALANCED;
/*     */                 break;
/*     */               default:
/* 438 */                 this.left.skew = AVLTree.Skew.BALANCED;
/* 439 */                 this.right.skew = AVLTree.Skew.BALANCED; break;
/*     */             } 
/* 441 */             this.skew = AVLTree.Skew.BALANCED;
/*     */           } 
/* 443 */           return false;
/*     */         case RIGHT_HIGH:
/* 445 */           this.skew = AVLTree.Skew.BALANCED;
/* 446 */           return false;
/*     */       } 
/* 448 */       this.skew = AVLTree.Skew.LEFT_HIGH;
/* 449 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean rebalanceRightGrown() {
/* 457 */       switch (this.skew) {
/*     */         case LEFT_HIGH:
/* 459 */           this.skew = AVLTree.Skew.BALANCED;
/* 460 */           return false;
/*     */         case RIGHT_HIGH:
/* 462 */           if (this.right.skew == AVLTree.Skew.RIGHT_HIGH) {
/* 463 */             rotateCCW();
/* 464 */             this.skew = AVLTree.Skew.BALANCED;
/* 465 */             this.left.skew = AVLTree.Skew.BALANCED;
/*     */           } else {
/* 467 */             AVLTree.Skew s = this.right.left.skew;
/* 468 */             this.right.rotateCW();
/* 469 */             rotateCCW();
/* 470 */             switch (s) {
/*     */               case LEFT_HIGH:
/* 472 */                 this.left.skew = AVLTree.Skew.BALANCED;
/* 473 */                 this.right.skew = AVLTree.Skew.RIGHT_HIGH;
/*     */                 break;
/*     */               case RIGHT_HIGH:
/* 476 */                 this.left.skew = AVLTree.Skew.LEFT_HIGH;
/* 477 */                 this.right.skew = AVLTree.Skew.BALANCED;
/*     */                 break;
/*     */               default:
/* 480 */                 this.left.skew = AVLTree.Skew.BALANCED;
/* 481 */                 this.right.skew = AVLTree.Skew.BALANCED; break;
/*     */             } 
/* 483 */             this.skew = AVLTree.Skew.BALANCED;
/*     */           } 
/* 485 */           return false;
/*     */       } 
/* 487 */       this.skew = AVLTree.Skew.RIGHT_HIGH;
/* 488 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean rebalanceLeftShrunk() {
/*     */       AVLTree.Skew s;
/* 496 */       switch (this.skew) {
/*     */         case LEFT_HIGH:
/* 498 */           this.skew = AVLTree.Skew.BALANCED;
/* 499 */           return true;
/*     */         case RIGHT_HIGH:
/* 501 */           if (this.right.skew == AVLTree.Skew.RIGHT_HIGH) {
/* 502 */             rotateCCW();
/* 503 */             this.skew = AVLTree.Skew.BALANCED;
/* 504 */             this.left.skew = AVLTree.Skew.BALANCED;
/* 505 */             return true;
/* 506 */           }  if (this.right.skew == AVLTree.Skew.BALANCED) {
/* 507 */             rotateCCW();
/* 508 */             this.skew = AVLTree.Skew.LEFT_HIGH;
/* 509 */             this.left.skew = AVLTree.Skew.RIGHT_HIGH;
/* 510 */             return false;
/*     */           } 
/* 512 */           s = this.right.left.skew;
/* 513 */           this.right.rotateCW();
/* 514 */           rotateCCW();
/* 515 */           switch (s)
/*     */           { case LEFT_HIGH:
/* 517 */               this.left.skew = AVLTree.Skew.BALANCED;
/* 518 */               this.right.skew = AVLTree.Skew.RIGHT_HIGH;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 528 */               this.skew = AVLTree.Skew.BALANCED;
/* 529 */               return true;case RIGHT_HIGH: this.left.skew = AVLTree.Skew.LEFT_HIGH; this.right.skew = AVLTree.Skew.BALANCED; this.skew = AVLTree.Skew.BALANCED; return true; }  this.left.skew = AVLTree.Skew.BALANCED; this.right.skew = AVLTree.Skew.BALANCED; this.skew = AVLTree.Skew.BALANCED; return true;
/*     */       } 
/*     */       
/* 532 */       this.skew = AVLTree.Skew.RIGHT_HIGH;
/* 533 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean rebalanceRightShrunk() {
/*     */       AVLTree.Skew s;
/* 541 */       switch (this.skew) {
/*     */         case RIGHT_HIGH:
/* 543 */           this.skew = AVLTree.Skew.BALANCED;
/* 544 */           return true;
/*     */         case LEFT_HIGH:
/* 546 */           if (this.left.skew == AVLTree.Skew.LEFT_HIGH) {
/* 547 */             rotateCW();
/* 548 */             this.skew = AVLTree.Skew.BALANCED;
/* 549 */             this.right.skew = AVLTree.Skew.BALANCED;
/* 550 */             return true;
/* 551 */           }  if (this.left.skew == AVLTree.Skew.BALANCED) {
/* 552 */             rotateCW();
/* 553 */             this.skew = AVLTree.Skew.RIGHT_HIGH;
/* 554 */             this.right.skew = AVLTree.Skew.LEFT_HIGH;
/* 555 */             return false;
/*     */           } 
/* 557 */           s = this.left.right.skew;
/* 558 */           this.left.rotateCCW();
/* 559 */           rotateCW();
/* 560 */           switch (s)
/*     */           { case LEFT_HIGH:
/* 562 */               this.left.skew = AVLTree.Skew.BALANCED;
/* 563 */               this.right.skew = AVLTree.Skew.RIGHT_HIGH;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 573 */               this.skew = AVLTree.Skew.BALANCED;
/* 574 */               return true;case RIGHT_HIGH: this.left.skew = AVLTree.Skew.LEFT_HIGH; this.right.skew = AVLTree.Skew.BALANCED; this.skew = AVLTree.Skew.BALANCED; return true; }  this.left.skew = AVLTree.Skew.BALANCED; this.right.skew = AVLTree.Skew.BALANCED; this.skew = AVLTree.Skew.BALANCED; return true;
/*     */       } 
/*     */       
/* 577 */       this.skew = AVLTree.Skew.LEFT_HIGH;
/* 578 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void rotateCW() {
/* 588 */       T tmpElt = this.element;
/* 589 */       this.element = this.left.element;
/* 590 */       this.left.element = tmpElt;
/*     */       
/* 592 */       Node tmpNode = this.left;
/* 593 */       this.left = tmpNode.left;
/* 594 */       tmpNode.left = tmpNode.right;
/* 595 */       tmpNode.right = this.right;
/* 596 */       this.right = tmpNode;
/*     */       
/* 598 */       if (this.left != null) {
/* 599 */         this.left.parent = this;
/*     */       }
/* 601 */       if (this.right.right != null) {
/* 602 */         this.right.right.parent = this.right;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void rotateCCW() {
/* 613 */       T tmpElt = this.element;
/* 614 */       this.element = this.right.element;
/* 615 */       this.right.element = tmpElt;
/*     */       
/* 617 */       Node tmpNode = this.right;
/* 618 */       this.right = tmpNode.right;
/* 619 */       tmpNode.right = tmpNode.left;
/* 620 */       tmpNode.left = this.left;
/* 621 */       this.left = tmpNode;
/*     */       
/* 623 */       if (this.right != null) {
/* 624 */         this.right.parent = this;
/*     */       }
/* 626 */       if (this.left.left != null)
/* 627 */         this.left.left.parent = this.left; 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/partitioning/utilities/AVLTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
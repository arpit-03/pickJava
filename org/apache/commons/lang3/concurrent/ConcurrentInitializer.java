package org.apache.commons.lang3.concurrent;

public interface ConcurrentInitializer<T> {
  T get() throws ConcurrentException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/lang3/concurrent/ConcurrentInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
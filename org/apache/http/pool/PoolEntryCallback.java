package org.apache.http.pool;

public interface PoolEntryCallback<T, C> {
  void process(PoolEntry<T, C> paramPoolEntry);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/pool/PoolEntryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
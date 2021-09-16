package Catalano.Statistics.Kernels;

import java.io.Serializable;

public interface IMercerKernel<T> extends Serializable {
  double Function(T paramT1, T paramT2);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Statistics/Kernels/IMercerKernel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
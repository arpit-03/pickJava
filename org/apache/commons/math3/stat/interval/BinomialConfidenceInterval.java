package org.apache.commons.math3.stat.interval;

import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.OutOfRangeException;

public interface BinomialConfidenceInterval {
  ConfidenceInterval createInterval(int paramInt1, int paramInt2, double paramDouble) throws NotStrictlyPositiveException, NotPositiveException, NumberIsTooLargeException, OutOfRangeException;
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/interval/BinomialConfidenceInterval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
package edu.mines.jtk.interp;

import edu.mines.jtk.dsp.Sampling;

public interface Gridder3 {
  void setScattered(float[] paramArrayOffloat1, float[] paramArrayOffloat2, float[] paramArrayOffloat3, float[] paramArrayOffloat4);
  
  float[][][] grid(Sampling paramSampling1, Sampling paramSampling2, Sampling paramSampling3);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/Gridder3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
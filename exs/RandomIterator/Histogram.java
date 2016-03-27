/* ****************************************************************************
 *  Compilation:  javac Histogram.java
 *  Execution:    java Histogram N T
 *
 *  This data type supports simple client code to create dynamic
 *  histograms of the frequency of occurrence of values in [0, N).
 *  The frequencies are kept in an instance-variable array, and
 *  an instance variable max tracks the maximum frequency (for scaling).
 *
 *  % java Histogram 50 1000000 
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.*; 

public class Histogram {
    private final double[] freq;   // freq[i] = # occurences of value i
    private double max;            // max frequency of any value

    // Create a new histogram. 
    public Histogram(int N) {
        freq = new double[N];
    }

    // Add one occurrence of the value i. 
    public void addDataPoint(int i) {
        freq[i]++; 
        if (freq[i] > max) max = freq[i]; 
    } 

    // draw (and scale) the histogram.
    public void draw() {
        StdDraw.setYscale(-1, max + 1);  // to leave a little border
        StdStats.plotBars(freq);
    }
}


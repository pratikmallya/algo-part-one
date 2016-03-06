import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Arrays;
import java.util.Collections;

public class PercolationStats {
  private double[] expResults;

  public PercolationStats(int N, int T){
    if (N <= 0 || T <= 0 ) {
      String msg = String.format("N and T must both be > 0, but got N=%d and T=%d", N, T);
      throw new java.lang.IllegalArgumentException(msg);
    }
    this.expResults = new double[T];
    // perform the experiment
    for (int i=0; i<T; i++) {
      int numOpen = 0;
      Percolation perc = new Percolation(N);
      // generate lookup array
      Integer[] lookup = new Integer[N*N];
      for (int j=0; j < N*N; j++) {
        lookup[j] = j;
      }
      Collections.shuffle(Arrays.asList(lookup));
      for (int j=0; j < N*N; j++) {
        // get the coordinates of the site
        int[] compIndex = this.expandDimension(lookup[j], N);
        // open closed site
        perc.open(compIndex[0], compIndex[1]);
        numOpen++;
        // if the system percolates, record stats and exit
        if (perc.percolates()) {
          this.expResults[i] = (double) numOpen / (N*N);
          break;
        }
      }
    }
  };

  public double mean() {
    return StdStats.mean(this.expResults);
  };

  public double stddev() {
    if (this.expResults.length < 2) {
      throw new java.lang.IllegalArgumentException("Need at least 2 experiments to compute std deviation");
    }
    return StdStats.stddev(this.expResults);
  }

  public double confidencelo(){
    double mu = this.mean();
    double sigma = this.stddev();
    return mu - ((1.96 * sigma)/Math.sqrt(this.expResults.length));
  };

  public double confidencehi(){
    double mu = this.mean();
    double sigma = this.stddev();
    return mu + ((1.96 * sigma)/Math.sqrt(this.expResults.length));
  };

  public static void main(String[] args) {
    if (args.length != 2) {
      throw new java.lang.IllegalArgumentException("Program accepts only 2 input arguments");
    };
    PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    System.out.printf("mean = %f\nstddev = %f\n95%% confidence interval = %f, %f\n", percStats.mean(), percStats.stddev(), percStats.confidencelo(), percStats.confidencehi());
  }

  private int[] expandDimension(int U, int N) {
    // result in range [(1,1), (N, N)]
    int i = U/(N + 1) + 1;
    int j = (U - N * (U/N)) + 1;
    int[] retarr = {i, j};
    return retarr;
  }
}

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

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
      while (!perc.percolates()) {
        // generate random site
        int x = StdRandom.uniform(1, N+1);
        int y = StdRandom.uniform(1, N+1);
        // if not closed site, continue
        if (perc.isOpen(x, y)) {
          continue;
        };
        // open closed site
        perc.open(x, y);
        // if the system does not percolate, continue
        if (!perc.percolates()) {
          continue;
        }
        // the system does percolate, record statistics and exit
        this.expResults[i] = (double) numOpen / (N*N - numOpen);
        break;
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
    System.out.printf("mean = %f\nstddev = %f\n95% confidence interval = %f, %f", percStats.mean(), percStats.stddev(), percStats.confidencelo(), percStats.confidencehi());
  }
}

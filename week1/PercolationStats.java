public class PercolationStats {
  private double[] expResults;

  public PercolationStats(int N, int T){
    if (N <= 0 || T <= 0 ) {
      String msg = String.format("N and T must both be > 0, but got N=%d and T=%d", N, T);
      throw new java.lang.IllegalArgumentException(msg);
    }
  };

  public double mean() {
    double sum = 0;
    for (int i = 0; i < this.expResults.length; i++) {
      sum += this.expResults[i];
    };
    return sum / this.expResults.length;
  };

  public double stddev() {
    double mu = this.mean();
    double sum = 0;
    if (this.expResults.length < 2) {
      throw new java.lang.IllegalArgumentException("Need at least 2 experiments to compute std deviation");
    }
    for (int i = 0; i < this.expResults.length; i++) {
      sum += Math.pow(this.expResults[i] - mu, 2);
    };
    return Math.sqrt(sum / (this.expResults.length - 1));
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
  public static void main(String[] args){}
}

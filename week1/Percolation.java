public class Percolation {
  private int N;
  private boolean[][] Grid;

  public Percolation(int N){
    if (N <= 0) {
      String msg = String.format("N must be > 0, but got N=%d", N);
      throw new java.lang.IllegalArgumentException(msg);
    };
    this.Grid = new boolean[N][N];
  };

  public void open(int i, int j){
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    this.Grid[i][j] = true;
  };

  public boolean isOpen(int i, int j){
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    return this.Grid[i][j];
  };

  public boolean isFull(int i, int j){
    return false;
  };

  public boolean percolates(){
    for (int i=1; i <=N; i++) {
      if (this.isFull(N, i)) {
        return true;
      };
    };
    return false;
  };

  public static void main(String [] args){};
}

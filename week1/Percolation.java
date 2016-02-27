import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ListIterator;

public class Percolation {
  private int N;
  private boolean[][] Grid;
  private WeightedQuickUnionUF QF;

  public Percolation(int N) {
    if (N <= 0) {
      String msg = String.format("N must be > 0, but got N=%d", N);
      throw new java.lang.IllegalArgumentException(msg);
    };
    this.Grid = new boolean[N][N];
    this.UF = new WeightedQuickUnionUF(N);
    this.N = N;
  };

  public void open(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    this.Grid[i][j] = true;
    // update UF structure with neighbor information
    int[] indexList = {-1, 1};
    for (int p : indexList){
      for(int q : indexList){
        if (1 <= p && p<= this.N && 1 <= q && q <= this.N && this.isOpen(p, q)){
          this.UF.union(this.flattenMatrix(i,j), this.flattenMatrix(p,q));
        }
      }
    }
  };

  public boolean isOpen(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    return this.Grid[i][j];
  };

  public boolean isFull(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    // check if given point is in the same component as an open site on top row
    for (int p=1; p<=this.N; p++) {
      if (this.isOpen(1, p) && this.UF.connected(this.flattenMatrix(i, j), this.flattenMatrix(1, p))){
        return true;
      }
    }
    return false;
  };

  public boolean percolates(){
    for (int i=1; i <=this.N; i++) {
      if (this.isFull(this.N, i)) {
        return true;
      };
    };
    return false;
  };

  public static void main(String [] args){};

  private int flattenMatrix(int i, int j) {
    return (i-1)*this.N + j;
  }
}

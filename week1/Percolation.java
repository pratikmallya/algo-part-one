import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ListIterator;

public class Percolation {
  private int N;
  private boolean[][] Grid;
  private WeightedQuickUnionUF QF;
  private boolean isPercolate;

  public Percolation(int N) {
    if (N <= 0) {
      String msg = String.format("N must be > 0, but got N=%d", N);
      throw new java.lang.IllegalArgumentException(msg);
    };
    this.Grid = new boolean[N][N];
    this.UF = new WeightedQuickUnionUF(N);
    this.N = N;
    this.isPercolate = false;
  };

  public void open(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in 1 <= %d, but got i=%d and j=%d", this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    // open up the site
    this.Grid[i][j] = true;
    // update UF structure with neighbor information
    int[] indexList = {-1, 1};
    for (int p : indexList){
      for(int q : indexList){
        if (1 <= p && p<= this.N && 1 <= q && q <= this.N && this.isOpen(p, q)){
          this.UF.union(this.collapseDimension(i,j), this.collapseDimension(p,q));
        }
      }
    }
    // if the newly opened site is full, the system percolates
    if (this.isFull(i, j)) {
      this.isPercolate = true;
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
    if (!this.isOpen(i, j)) {
      return false;
    }
    // check if the component that this site belongs to is open
    int[] compIndex = this.expandDimension(this.UF.find(this.collapseDimension(i, j)));
    return this.isOpen(compIndex[0], compIndex[1]);
  };

  public boolean percolates(){
    return this.isPercolate;
  };

  public static void main(String [] args){};

  private int collapseDimension(int i, int j) {
    return (i-1)*this.N + j;
  }

  private int[] expandDimension(int U) {
    int i = U/this.N + 1;
    int j = U - this.N * (i-1);
    int[] retarr = {i, j};
    return retarr;
  }
}

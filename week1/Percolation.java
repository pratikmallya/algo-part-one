import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
* A data structure to model Percolation.
* @param  N  the size of the percolation grid.
*
**/
public class Percolation {
  private int N;
  private boolean[][] grid;
  private WeightedQuickUnionUF uf;
  private boolean isPercolate;

  public Percolation(int N) {
    if (N <= 0) {
      String msg = String.format("N must be > 0, but got N=%d", N);
      throw new java.lang.IllegalArgumentException(msg);
    }
    this.grid = new boolean[N][N];
    this.uf = new WeightedQuickUnionUF(N*N);
    this.N = N;
    this.isPercolate = false;
  };

  public void open(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in [1, %d], but got i=%d and j=%d",
        this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    // open up the site
    this.grid[i-1][j-1] = true;
    // update UF structure with neighbor information
    int[] indexList = {-1, 0, 1};
    for (int p : indexList){
      for(int q : indexList){
        if ( p != q && p != -q && 0 < i+p && i+p <= this.N && 0 < j+q && j+q <= this.N &&
          this.isOpen(i+p, j+q)) {
          this.uf.union(this.collapseDimension(i,j)-1, this.collapseDimension(i+p,j+q)-1);
        }
      }
    }
    // if the newly opened site is on the bottom row and is full, the system percolates
    if (i == this.N && this.isFull(i, j)) {
      this.isPercolate = true;
    }
  };

  public boolean isOpen(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in [1, %d], but got i=%d and j=%d",
        this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    return this.grid[i-1][j-1];
  };

  public boolean isFull(int i, int j) {
    if (!(i > 0 && i <= this.N && j > 0 && j <= this.N)) {
      String msg = String.format("i and j must both lie in [1,%d], but got i=%d and j=%d",
        this.N, i, j);
      throw new java.lang.IndexOutOfBoundsException(msg);
    }
    // if this is a closed site, cannot possibly be full
    if (!this.isOpen(i, j)) {
      return false;
    }
    // check if the component that this site belongs to is open
    int[] compIndex = this.expandDimension(this.uf.find(this.collapseDimension(i, j)-1));
    return this.isOpen(compIndex[0], compIndex[1]);
  };

  public boolean percolates(){
    return this.isPercolate;
  };

  public static void main(String [] args){
    int N = 100;
    Percolation perc = new Percolation(N);
    // test if collapse and expand dimension methods work correctly
    System.out.println("Running tests.....");
    for(int i=1; i<N; i++) {
      for(int j=1; j<N; j++) {
          int [] result = perc.expandDimension(perc.collapseDimension(i, j));
          if (i != result[0] && j != result[1]) {
            System.out.printf("(%d, %d) Result = (%d, %d)\n", i, j, result[0], result[1]);
          }
      }
    }
    System.out.println("Testing complete");

  };

  private int collapseDimension(int i, int j) {
  // result in range [1, N*N]
    return (i-1)*this.N + j;
  }

  private int[] expandDimension(int U) {
    // result in range [(1,1), (N, N)]
    int i = U/(this.N) + 1;
    int j = (U - this.N * (U/this.N)) + 1;
    int[] retarr = {i, j};
    return retarr;
  }
}

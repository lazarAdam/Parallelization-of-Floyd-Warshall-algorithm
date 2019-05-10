import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class FloydW_parallel extends Thread {
	
	private static final int I = 999999999; // Infinity
	public static final int dim = 1000;
	private static double fill = 0.3;
	private static int maxDistance = 100;
	public static int adjacencyMatrix[][] = new int[dim][dim];
	public static int d[][] = new int[dim][dim];
    private static int ThRow;
    public  int row;
 
    private ReentrantLock cslock = new ReentrantLock();

    
    /*
     * Generate a randomized matrix to use for the algorithm.
     */
    
    public static void generateMatrix() {
        Random random = new Random();
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (i != j)
                    
                    adjacencyMatrix[i][j] = I; 
            }
        }
        for (int i = 0; i < dim * dim * fill; i++)
        {
            adjacencyMatrix[random.nextInt(dim)][random.nextInt(dim)] =
                    random.nextInt(maxDistance + 1);
        }
        


    
        //print(adjacencyMatrix);
        
        
        //This makes the main matrix d[][] ready
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
            {
                d[i][j] = adjacencyMatrix[i][j];
                if (i == j)
                {
                    d[i][j] = 0;
                }
            }
        }
    }
    
    public static void initMtarix(){
    	
    	
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++)
            {
                d[i][j] = adjacencyMatrix[i][j];
                if (i == j)
                {
                    d[i][j] = 0;
                }
            }
        }
    }
    
    /*
     * Execute Floyd Warshall on adjacencyMatrix.
     */
    public static void sequntailFloydW() {
    	
    	
    	
      
        //This makes the main matrix d[][] ready
   
        
        //System.out.println("Printing... intial matrix");
     
        for (int k = 0; k < dim; k++) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (d[i][k] == I || d[k][j] == I) {
                        continue;
                    } else if (d[i][j] > d[i][k] + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
            //System.out.println("sequntial pass " + (k + 1) + "/" + dim);
           
            //System.out.println();
        }
        
     adjacencyMatrix = d.clone();// reuse adjacencyMatrix as a matrix for comparison with parallel execution, and clone as d[][] 
     							
        
        
        //print(adjacencyMatrix);
    }
    /*
     * Print matrix[dim][dim]
     */
    public static void print(int[][] mat) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (mat[i][j] == I) {
                    System.out.print("I" + " ");
                } else {
                    System.out.print(mat[i][j] + "\t");
                }
            }
            System.out.println();
        }
    }
    
    
    
    /*
     * Compare two matrices, matrix1[dim][dim] and matrix2[dim][dim] and
     * print whether they are equivalent.
     */
    public static void compare () {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (d[i][j] != adjacencyMatrix[i][j])// here 
                {
                    System.out.println("Comparison failed");
                    
                    return;
                }
            }
        }
        System.out.println("Comparison succeeded");
    }
    
    

    public void run() {
   
    	int i,j;
		while (row< dim) {
			
		// System.out.println("parallel pass " + (row + 1) + "/" + dim);
			
			for(i=0;i<dim;i++) {
				for(j=0;j<dim;j++){
					
					if(d[i][row] == I || d[row][j] == I){continue;}
					
					else if(d[i][j]>d[i][row]+d[row][j]){
					
					d[i][j]= d[i][row]+d[row][j];}
					
				}
			}
			
			
			try{
			this.cslock.lock();
			
				ThRow++;// extra row variable to be used to increment row 
				  }
				
			
			finally{
		this.cslock.unlock();//release the lock
		row=ThRow;// Referencing row 
			}
		}
    	
		
			
			
		
    }
}

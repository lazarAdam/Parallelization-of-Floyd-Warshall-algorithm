import java.util.Scanner;

public class driverClass {
   
    public static int nThreads=10;
    
    
    
    public static void main(String[] args) throws InterruptedException {
        long start, end;
        
        Scanner reader = new Scanner(System.in);
        
        int UserInput;
    	
    	System.out.print("DO you want to run Floyd Warshall Sequntial first? enter 1 for Yes and 0 for No:  ");
    	UserInput = reader.nextInt();
    	System.out.println("\n"+"Note! If your answer was No there will be no comparision btween the the parallel and the sequntial matrices"+ "\n"+
    	"which is a way to confirm the parallel execution was Successful"+"\n");
        

   
        FloydW_parallel.generateMatrix();
        
        FloydW_parallel.initMtarix();
        
        
        if(UserInput == 1){
        start = System.nanoTime();
        
        System.out.println("Starting sequntail execution ......");
        
       FloydW_parallel.sequntailFloydW();
        
        end = System.nanoTime();
        
        System.out.println("time consumed seconds: " + (double)(end - start) / 1000000000+"\n");
        }
        
        //FloydW_parallel.print(FloydW_parallel.adjacencyMatrix);
        
        System.out.println("Starting parallel execution.......");
         final long start2 = System.nanoTime();

         FloydW_parallel mat[]=new FloydW_parallel[nThreads];
        
        for(int x=0;x<nThreads;x++){
            mat[x]=new FloydW_parallel();//create an instance 
            mat[x].row=x;//intil 
            mat[x].start();
        
        
        
        }
      
            for(int i=0;i<nThreads;i++){
                mat[i].join();}
      

       final long end2 = System.nanoTime();


     
       	//FloydW_parallel.print(FloydW_parallel.d);
        System.out.println("time consumed in seconds: " + (double)(end2 - start2) / 1000000000);
        
        if(UserInput==1 ){
        	
        	FloydW_parallel.compare();
        }
        
    }
}

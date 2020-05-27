import java.util.stream.IntStream;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    
    /*
    
    The N Queen is the problem of placing N chess queens on an N×N chessboard so that no two queens attack each other.
    
    Here I use Iterative Min Conflicts algorithm to solve the problem. 
    
    To write this program I used the solution in the following source(in python): 
    https://gist.github.com/vedantk/747203
    
    */
    
    
    public static void main(String[] args) throws Exception {
        System.out.println("HEY YOU! CAN YOU PLACE N QUEENS IN NxN CHESS BOARD WITH NONE ATTACKING EACH OTHER?? \nI CAN! "
        +"\nENTER AN INTEGER (starting from 4) TO SEE THE MAGIC HAPPEN! :) \n");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if(n>=4){
            nqueens(n);
        }
        else{
            System.out.println("Enter an integer more than or equal to 4, please.");
        }
        in.close();
    }
    
    
    public static char[][] nqueens(int n) throws Exception{
    	int [] list = new int [n];
    	for(int i=0; i<n; i++){
    		list[i]=i;
    	}
    	int [] sol = minConflicts(n, list, 1000);
    	if(sol!=null){
    		return show(n, sol);
    	}
    	else{
    		throw new Exception("More iterations are needed..."); 
    	}
    }
    
    public static char[][] show(int n, int [] sol){
    	char [][] board =  new char [n][n];
    	for(int i=0; i<n;i++){
    		char [] row= new char[n];
    		for(int j=0;j<n;j++){
    		    row[j] = '=';
    		}
    		for(int col=0; col<n; col++ ){
    			if(sol[col]==n-1-i){
    				row[col]='Q';
    			}
    		}
    		if(n<=6){
    		    print(row);
    		}
    		else{
    		    printModel(row);
    		}
    		board[i]=row;
    	}
    	return board;
    }
    
    
    public static int [] minConflicts(int n, int [] sol, int iterations){
        for(int i=0; i<iterations; i++){
            int [] conflicts = countConflicts(n, sol);
            int sum = IntStream.of(conflicts).sum();
    		if(sum==0){
    			return sol;
    		}
            //choose a random conflict
    		int row = randomConflictRow(conflicts);
	        //try for each column number of hits
            int [] trials = new int [n];
            for(int col=0; col<n; col++){
                trials[col]=hits(n, sol, row, col);
            }
            //choose a random min conflict column and the place queen
            sol[row]=randomMinCol(trials);
        }
        return null;
    }
    
    
    public static int randomConflictRow(int list []){
		int rnd =0;
	    while (true){
		rnd = new Random().nextInt(list.length);
	    if(list[rnd]>0){
	    	break;}
	    }
	    return rnd;
    }
    
    public static int randomMinCol(int list []){
    	int rnd =-1;
    	int min = list[0];
        for(int i=1; i<list.length; i++){
        	if (list[i]<min){
        		min = list[i];
        	}
        }
        while(true){
            rnd = new Random().nextInt(list.length);
            if(list[rnd]==min){
            	break;
            }
        }
        return rnd;
    }
    
    public static int[] countConflicts(int n, int [] sol){
        int [] conflicts= new int[n]; //indexes-rows,values-confs
        for (int i=0; i<n; i++){
                conflicts[i]=hits(n,sol,i,sol[i]);
            }
        return conflicts;
    }
   
    public static int hits(int n, int [] sol, int row, int col){
        int count = 0;
           for (int i=0; i<n; i++){
              if(i==row){continue;}
              if(sol[i]==col||Math.abs(col-sol[i])==Math.abs(i-row)){
                  count++;
              }
           }
           return count;
    }
     
    
    private static void print(char[] row) {

    	for (int i=0; i<row.length; i++){
    	    System.out.print("* * *  ");
        // 	System.out.print(row[i]);
    	}
    	System.out.print("\n");
    	for (int i=0; i<row.length; i++){
    	    if(row[i]=='Q')
    	        System.out.print("* Q *  ");
    	    else
    	        System.out.print("*   *  ");
    	}
    		System.out.print("\n");
    	for (int i=0; i<row.length; i++){
    	    System.out.print("* * *  ");
    	}
    	    	System.out.print("\n\n");

    }
    
    private static void printModel(char[] row) {

    	for (int i=0; i<row.length; i++){
    	    System.out.print(row[i]+ " ");
    	}
    	System.out.print("\n");
    }
    
        
}
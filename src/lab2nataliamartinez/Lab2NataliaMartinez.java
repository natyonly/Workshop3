/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2nataliamartinez;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
 * Algorithms and Complexity                                  August 24, 2022
 * IST 4310
 * Natalia Martínez
 * Student ID: 200156522
 * Number of duplicates in a plain text storing N random integers
 *
 * Synopsis:
 * Creates, writes and reads a file with the objetive of displaying the number of duplicates in the file storing N random integers.
 * 
 *
 * References:
 * [0] Files: www.w3schools.com/java/java_files_create.asp
 * [1] PrintWriter: (docs.oracle.com/en/java/javase/11/docs/api/java.base/
 *                   java/io/PrintWriter.html)
 * [2] IOException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                   IOException.html)
 * [3] FileNotFoundException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                             FileNotFoundException.html)
 * [4] Scanner: docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
 * [5] www.javatpoint.com/throw-keyword
 *
 */
/**
 *
 * @author natymartinez04
 */
public class Lab2NataliaMartinez{
        
        /** @Instructions
          * To run the Best Case for this algorithm uncomment each line that contains (1) next to it. These lines are found in main and in the function write.
          * Make sure the other lines that contain (2) or (3) next to them are commented.
          * Same procedure for the Average Case (2) and the Worst Case (3).
         */
    
	public static void main (String[] args){
                int iteraciones = 0;
                long numel;
                long num;
                Long tiempos[] = new Long[16];
                Long comparaciones[] = new Long[16];

                //Amount of numbers (start)
                numel = (int) Math.pow(2,iteraciones+5); 
                
                //Limit Number?
                //num = 1; //Best Case (1)
          
                //Average Case & Worst Case: No Limit 
                num = numel; //(2) (3)
                
                while (iteraciones < 16){ //
                    create("data.txt");
                    write("data.txt",iteraciones,numel, num);			
                    store(iteraciones,tiempos,numel,comparaciones);
                    numel = numel*2;
                    num = numel; //(2) (3)
                    iteraciones ++;
                }
                writeTime("tiempo.txt",tiempos,iteraciones,comparaciones);
			
		
	}

	private static void create (String nombre){
	// creates a file
		try{
			// defines the filename
			String fname = (nombre);
			// creates a new File object
			File f = new File (fname);

			String msg = "creating file `" + fname + "' ... ";
			System.out.println();
			System.out.printf("%s", msg);
			// creates the new file
			f.createNewFile();
			System.out.println("done");
		}
		catch (IOException err)
		{
			// complains if there is an Input/Output Error
			err.printStackTrace();
		}

		return;
	}

	public static void write (String nombre, Integer iteraciones,Long numel, Long num){
	// writes data to a file
		try{
			// defines the filename
			String filename = (nombre);
			// creates new PrintWriter object for writing file
			PrintWriter out = new PrintWriter (filename);
			String msg = "writing %d numbers ... ";
			System.out.printf(msg, numel);
                        Random random = new Random(); //used in the average case to generate random numbers depending on the limit
                        
			// writes random integers
			for (int i = 0; i < numel; ++i){ 
                            //writes integers to file depending on the case that is being tested
                            //out.printf("%d\n", 0); //Best Case (1)
                            out.printf("%d\n", random.nextInt(num.intValue())); //Average Case (2)
                            //out.printf("%d\n", i); //Worst Case (3)
                        }
			out.close();	// closes the output stream	
		}
		catch (FileNotFoundException err)
		{
			// complains if file does not exist
			err.printStackTrace();
		}
		return;
	}
        
        private static void writeTime(String nombre, Long[] tiempo, Integer iteraciones,Long[] comparaciones){
            //writes the amount of numbers in the file, the number of comparisons and time into a file.
            try
		{
                        create(nombre);
			// defines the filename
			String filename = (nombre);
			// creates new PrintWriter object for writing file
			PrintWriter out = new PrintWriter (filename);
                        for (int i = 0; i< iteraciones; i++){
                            out.printf((int) Math.pow(2, i+5)+"    "+comparaciones[i]+"    "+tiempo[i]+"\n"); 
                        }
			System.out.printf("closing file ... ");
			out.close();	// closes the output stream
			System.out.println("done");
		}
		catch (FileNotFoundException err){
			// complains if file does not exist
			err.printStackTrace();
		}
		return;
        }

	private static void store (Integer iteraciones,Long tiempos[],Long numel,Long comparaciones[]){
	// stores the file contents into an array
		String filename = "data.txt";
		File f = new File (filename);
                long listaNum[] = new long[numel.intValue()]; //Array containing each number found on the file
                int listaCounter[] = new int [numel.intValue()]; //Array containing the amount of times you can find the refered number on the file
		try{
			Scanner in = new Scanner (f);
                        int x;
                        int i = 0;
                        int j = 0;
                        long start = System.nanoTime();
                        comparaciones[iteraciones] = new Long(0);
                        boolean sw = false;
			while (in.hasNextInt()){ 
                                x = in.nextInt();
                                if (i == 0){ //when there was not being numbers added to the array
                                    listaNum[i] = x;
                                    listaCounter[i]++;
                                    i++;
                                }else{
                                    while (j<i && sw == false){
                                        //looks if number x already exists on the array
                                        if (listaNum[j]==x){ //finds number x and adds to the counter
                                            sw = true;
                                            listaCounter[j]++;
                                        }
                                        j++;
                                        comparaciones[iteraciones]++;
                                    }
                                    if (sw == false){ //number was not found on the list so it is added to the list
                                        listaNum[j]=x;
                                        listaCounter[j]++;
                                        i++;
                                    }
                                    j = 0;
                                    sw = false;
                                }
                        }
                        long end = System.nanoTime();
                        tiempos[iteraciones] = end-start;
			in.close();	// closes the input stream

		}catch (FileNotFoundException err){
			// complains if file does not exist
			err.printStackTrace();
		}

		return;
	}
}


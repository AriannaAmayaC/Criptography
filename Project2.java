/*
 * Department of Electrical and Information Technology.
 * Lund University, Sweden.
 * EDIN01 - Cryptography
 * Authors: Gabriel Voirol and Arianna Amaya
 *
 * Project 2: Shift Register Sequences
 */
package project2;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Project2 {

    public static int [] Z2() {
        boolean a = true;
        boolean b = false;
        boolean c = false;
        boolean d = true;
        boolean d_temp;
        int [] s = new int [16];
        
        s[0] = 5;
        
        for (int i = 1; i<16; i++)
        {  
            d_temp = (a != b) == (b | c | d);
            a = b;
            b = c;
            c = d;
            d = d_temp; 
            if (d) s[i] = 5;
            else s[i] = 0;
        }
        return s;
    }
    
    public static void main(String[] args) throws IOException{
        int [] s_2 = Z2();
        int [] s_5 = new int [625];
        
        FileWriter writer = new FileWriter("input.txt");
        FileWriter writer_final = new FileWriter("final.txt");
        
        int length = 624;
        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int D_temp = 0;

		writer.write(Integer.toString(D));
        s_5[0] = D;
     
        D = 1;
        for (int i = 0; i < length; i++){       
            writer.write(Integer.toString(D));
            s_5[i+1] = D;
            
            D_temp = -2*A-3*B-C;
            A = mod_pos(B,5);
            B = mod_pos(C,5);
            C = mod_pos(D,5);
            D = mod_pos(D_temp,5);
      
        }
        writer.close();

        int q;
        int [] init = new int[3];
        
        for (q=0; q<3; q++){
            init[q] = s_2[q]+s_5[q];
            writer_final.write(Integer.toString(init[q]));
            
        }
        for (q=3; q<16*625; q++){
            int i_2 = q%16;
            int i_5 = q%625;
            writer_final.write(Integer.toString(s_2[i_2]+s_5[i_5]));
            
        }
        for (q=0; q<3; q++){
            writer_final.write(Integer.toString(init[q]));
        }
        writer_final.close();
    }
    
    public static int mod_pos(int a, int b){
        int result = a % b;
        if (result < 0) result += b;
        return result;
    }   
}

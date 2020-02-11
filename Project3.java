/*
* Department of Electrical and Information Technology.
* Lund University, Sweden.
* EDIN01 - Cryptography
* Authors: Gabriel Voirol and Arianna Amaya
*
* Project 3: Correlation Attack
*/
package project3;
import
import
import
import
import
java.io.BufferedReader;
java.io.FileReader;
java.io.IOException;
java.util.Arrays;
java.util.stream.IntStream;
public class Project3 {
/*------------------------------ Global variables------------------------------*/
static final int N = 193;
/*--------------------------------- MAIN -------------------------------------*/
public static void main(String[] args) throws IOException {
int z[] = read_keystream();
int [] z_own = new int[N];
System.out.print("\nK1 is: ");
int [] u_1 = max_correlation (1, 13, z); //C1(D)
System.out.print(Arrays.toString(u_1));
int [] z_1 = c_1(u_1);
System.out.print("\nK2 is: ");
int [] u_2 = max_correlation (2, 15, z);//C2(D)
System.out.print(Arrays.toString(u_2));
int [] z_2 = c_2(u_2);
System.out.print("\nK3 is: ");
int [] u_3 = max_correlation (3, 17, z); //C3(D)
System.out.print(Arrays.toString(u_3));;
int [] z_3 = c_3(u_3);
2//comprobe(z_1, z_2, z_3, z);
}
public static int[] max_correlation (int c, int L, int z[]){
int [] u_N ;
int [] init;
int highest = (int)Math.pow(2, L);
float p[] = new float [highest];
for (int number = 0; number < highest; number++){
init = initialize (number,L);
if (c == 1)
u_N = c_1(init);
else if(c == 2)
u_N = c_2(init);
else
u_N = c_3(init);
p[number] = correlation(z, u_N);
}
int max_value = max(p);
int state [] = initialize (max_value, L);
//check_max(p);
return state;
}
/*--------------------------------- LFSRs -------------------------------------*/
public static int[] c_1(int [] init){
int input,output;
//change this for different c_i
int L = 13;
int N = 193;
int [] u_0 = init;
int [] u_N = new int [N];
Arrays.fill(u_N,-1);
if (init.length != L){
System.out.println("Wrong argument for starting LFSR1");
return u_N;
}
for (int i_N = 0; i_N < N ; i_N++){
output = u_0[0];
// -u_0[L1- "power of D"]
input =
3-u_0[L-1]-u_0[L-2]-u_0[L-4]-u_0[L-6]-u_0[L-7]-u_0[L-10]-u_0[L-11]-u_0[L-13];
input = input%2;
if (input<0) input = -input;
// shifts initial array positions
for (int i_L = 0; i_L < L-1 ; i_L++){
u_0[i_L]=u_0[i_L+1];
}
u_0[L-1]=input;
u_N[i_N] = output;
}
return u_N;
}
public static int[] c_2(int [] init){
int input,output;
//change this for different c_i
int L = 15;
int N = 193;
int [] u_0 = init;
int [] u_N = new int [N];
Arrays.fill(u_N,-1);
if (init.length != L){
System.out.println("Wrong argument for starting LFSR2");
return u_N;
}
for (int i_N = 0; i_N < N ; i_N++){
output = u_0[0];
// -u_0[L1- "power of D"]
input =
-u_0[L-2]-u_0[L-4]-u_0[L-6]-u_0[L-7]-u_0[L-10]-u_0[L-11]-u_0[L-13]-u_0[L-15];
input = input%2;
if (input<0) input = -input;
// shifts initial array positions
for (int i_L = 0; i_L < L-1 ; i_L++){
u_0[i_L]=u_0[i_L+1];
}
u_0[L-1]=input;
u_N[i_N] = output;
}
return u_N;
}
public static int[] c_3(int [] init){
int input,output;
//change this for different c_i
4int L = 17;
int N = 193;
int [] u_0 = init;
int [] u_N = new int [N];
Arrays.fill(u_N,-1);
if (init.length != L){
System.out.println("Wrong argument for starting LFSR3");
return u_N;
}
for (int i_N = 0; i_N < N ; i_N++){
output = u_0[0];
// -u_0[L1- "power of D"]
input =
-u_0[L-2]-u_0[L-4]-u_0[L-5]-u_0[L-8]-u_0[L-10]-u_0[L-13]-u_0[L-16]-u_0[L-17];
input = input%2;
if (input<0) input = -input;
// shifts initial array positions
for (int i_L = 0; i_L < L-1 ; i_L++){
u_0[i_L]=u_0[i_L+1];
}
u_0[L-1]=input;
u_N[i_N] = output;
}
return u_N;
}
public static int[] initialize(int value, int L){
int MSB,set_at;
int [] init = new int[L];
Arrays.fill(init,0);
for (int i_arr = L-1; i_arr > -1 ; i_arr--){
MSB = (int)Math.pow(2, i_arr);
if (value < MSB) continue;
init[L-1-i_arr] = 1;
value = value % MSB;
}
return init;
}
/*------------------- Function to check the values found ---------------------*/
public static void check_max(float p[]){
float [] max_values = new float[10];
Arrays.fill(max_values,0);
for (int i=0 ; i<p.length ; i++){
int ind_min = min(max_values);
if (p[i]>max_values[ind_min]) max_values[ind_min] = p[i];
}
System.out.println("max values are: "+Arrays.toString(max_values));
}
public static void comprobe(int []z_1, int []z_2, int []z_3, int z[]){
int z_own [] = new int [N];
for (int j=0; j<N ; j++){
int sum = z_1[j]+z_2[j]+z_3[j];
if (sum > 1) z_own[j] = 1;
else z_own[j] = 0;
}
float end = correlation(z,z_own);
System.out.println("Correlation between own z and z: "+end);
}
/*---------------------------- Others functions --------------------------------*/
public static int [] read_keystream() throws IOException{
int[] z = new int[N];
int aux;
String currentline;
BufferedReader reader = new BufferedReader(new FileReader("z.txt"));
char c;
String s;
currentline = reader.readLine();
for(int i =0; i<N;i++){
c= currentline.charAt(i);
s = String.valueOf(c);
z[i]= Integer.parseInt(s);
}
reader.close();
return z;
}
public static float correlation(int u[], int z[]){
float distance =0;
distance = IntStream.of(sust_arrays(u,z)).sum();
return (1-distance/N);
}
public static int [] sust_arrays(int a[], int b[]){
int[] c = new int [a.length];
for (int i =0; i< a.length; i++){
c[i]= Math.abs(a[i]-b[i]);
}
return c;
6}
public static int min(float a[]){
float max_value = a[0];
int index =0;
for (int i=0; i<a.length; i++){
if(a[i]<max_value){
max_value = a[i];
index = i;
}
}
return index;
}
public static int max(float a[]){
float max_value = a[0];
int index =0;
for (int i=0; i<a.length; i++){
if(a[i]>max_value){
max_value = a[i];
index = i;
}
}
return index;
}
}

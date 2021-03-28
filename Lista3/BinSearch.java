import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class BinSearch {
	static int n;
	static int comps;

	public static boolean compare(int a, int b) {
		//System.err.println("Porownuje: " + a + " - " + b);
		comps++;
		return a < b;
		
	}
	public static boolean equal(int a, int b) {
		//System.err.println("Porownuje: " + a + " - " + b);
		comps++;
		return a == b;
	}

	
	public static boolean search(int[] arr, int left, int right, int v) {
		if(left > right)
			return false;
		
		int mid = left + (right - left)/2;
		
		if(equal(v, arr[mid]))
			return true;
		if(compare(v, arr[mid]))
			return search(arr, left, mid - 1, v);
		else 
			return search(arr, mid +1, right, v);
		
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		if (args[0].equals("-t")){
			
			System.out.println("Podaj rozmiar tablicy");
			int n = Integer.parseInt(in.nextLine());
			int[] tab = new int[n];
			
			System.out.println("Podaj tablice");
			String line = in.nextLine();
			for (int j = 0; j < n; j++) {
				tab[j] = Integer.parseInt(line.split(",")[j]);
			}
			System.out.println("Podaj szukana wartosc");
			int v = Integer.parseInt(in.nextLine());
			System.out.println(search(tab, 0, n-1, v));
			
			
		}
		else if(args[0].equals("-s")) {
			System.out.println("Podaj nazwe pliku do zapisu");
			String filename = in.nextLine();
			File f = new File(filename);
			
			try {
				FileOutputStream fos = new FileOutputStream(f);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				n = 1000;
				int array[];
				Random rand = new Random();
				while(n < 101000) {
					comps = 0;
					array = new int[n];
					for(int i = 0; i < n; i++) 
						array[i] = i;
					
					int v = rand.nextInt(n);
					long start = System.currentTimeMillis();
					System.out.println("n="+n+" v="+ v +" - "+search(array, 0, n-1, v));
					long stop = System.currentTimeMillis();
					bw.write(String.format("n = %-10d   comps = %-10d  time = %-5d", n, comps, (int)(stop - start)));
					bw.newLine();
					bw.flush();
					n += 1000;
				}
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else {
			System.out.println("Zly param");
			System.exit(0);
		}

	}

}

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static Random rand = new Random();

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Podaj rozmiar tablicy");
		String s = in.nextLine();
		int n = Integer.parseInt(s);
		System.out.println("Podaj k");
		s = in.nextLine();
		int k = Integer.parseInt(s);
		if (k < 1 || k > n || n <= 0) {
			System.out.println("Bledny parametr");
			System.exit(0);
		}
		int array[] = new int[n];

		if (args[0].equals("-r")) {

			for (int i = 0; i < n; i++)
				array[i] = rand.nextInt(n) + 1;

		} else if (args[0].equals("-p")) {
			array = genPerm(n);
		} else {
			System.out.println("Zle parametry");
			System.exit(0);
		}

		if (args[1].equals("randselect")) {
			RandSelect sel = new RandSelect(array, n, k);
			sel.run();

		} else if (args[1].equals("select")) {
			Select sel = new Select(array, n, k);
			sel.run();

		} else if (args[1].equals("both")) {
			int[] copy = Arrays.copyOf(array, array.length);
			Select sel = new Select(array, n, k);
			RandSelect rsel = new RandSelect(copy, n, k);
			sel.run();
			rsel.run();

			System.err.println("\nSelect vs RandomizedSelect dla n=" + n + " i k=" + k);
			System.err.println("+------------------------+");
			System.err.println("Czas: " + sel.getTime() + "ms vs " + rsel.getTime() + "ms");
			System.err.println("Porownania: " + sel.getNumComps() + " vs " + rsel.getNumComps());
			System.err.println("Przestawienia: " + sel.getNumSwaps() + " vs " + rsel.getNumSwaps());
			System.err.println("+------------------------+");

		} else {
			System.out.println("Zle parametry");
			System.exit(0);
		}

	}

	public static int[] genPerm(int n) { // generowanie permutacji
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = i + 1;

		for (int i = n - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			int temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
		return arr;
	}

}

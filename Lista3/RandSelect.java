import java.util.Random;

public class RandSelect {

	private int[] array;
	private int n;
	private int k;
	private int numSwaps;
	private int numComps;
	private Random randGen;
	private int time;
	private int val;

	public RandSelect(int[] array, int n, int k) {
		this.array = array;
		this.n = n;
		this.k = k;
		numSwaps = 0;
		numComps = 0;
		randGen = new Random();
	}

	public void run() {
		System.err.println("+---------------------------------------------------------------------------------------------+");
		System.err.println("RandomSelect Startuje");
		System.err.println("+---------------------------------------------------------------------------------------------+");
		long start = System.currentTimeMillis();
		val = randSelect(array, 0, n - 1, k);
		long stop = System.currentTimeMillis();
		time = (int) (stop - start);
		System.err.println("\n\nPorownania: " + numComps + ", Przestawienia: " + numSwaps);
		System.err.println("W czasie: " + time + " ms");
		print(array);
		System.err.println(k + " statystyka pozycyjna: " + val);

	}

	private int partition(int[] arr, int left, int right) { // metoda na partycje z quicksorta
		int i = left;
		for (int j = left + 1; j <= right; j++) {
			if (compare(arr[j], arr[left])) {
				i++;
				swap(arr, i, j);
			}

		}
		swap(arr, i, left);
		return i;


	}

	public int randPartition(int[] arr, int left, int right) { // losowo zamienia pierwszy element z innym, a nastepnie wywoluje zwykla partycje
		int i = randGen.nextInt(right - left + 1) + left;
		System.err.println("Losowy indeks: " + i);
		swap(arr, left, i);
		System.err.print("Tablica: ");
		printToErr(arr);
		return partition(arr, left, right);

	}

	public int randSelect(int[] arr, int left, int right, int i) {
		if (left == right)
			return arr[left];
		System.err.print("Tablica: ");
		printToErr(arr);
		int p = randPartition(arr, left, right);
		System.err.println("Indeks partycji: " + p);
		int elems = p - left + 1; // liczba elementow w partycji
		if (i == elems) // element ktorego szuakmy
			return arr[p];
		if (i < elems) // element znajduje sie dolnej partycji
			return randSelect(arr, left, p - 1, i);
		else
			return randSelect(arr, p + 1, right, i - elems); // element znajduje sie w gornej partycji na pozycji wzglednej (i-elems)
	}

	public void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == val) {
				System.err.print("[" + arr[i] + "]" + ", ");
			} else
				System.err.print(arr[i] + ", ");
		}
		System.err.println();

	}

	private void printToErr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.err.print(arr[i] + ", ");
		}
		System.err.println();

	}

	public boolean compare(int a, int b) {
		numComps++;
		System.err.println("Porownuje: " + a + " - " + b);
		return a <= b;

	}

	public void swap(int[] arr, int a, int b) {
		if(arr[a] == arr[b])
			return;
		numSwaps++;
		System.err.println("Swapuje: " + arr[a] + " - " + arr[b] + " (" + a + "," + b + ")");
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;

	}
	
	public int getK() {
		return k;
	}

	public int getNumSwaps() {
		return numSwaps;
	}

	public int getNumComps() {
		return numComps;
	}

	public int getTime() {
		return time;
	}

}

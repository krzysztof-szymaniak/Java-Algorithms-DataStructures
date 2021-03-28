
public class Merge implements Algorythm {

	int[] tabToBeSorted;
	String comp;
	int n;
	int numComps;
	int numSwaps;
	int time;

	public Merge(int[] table, String comp, int n) {
		tabToBeSorted = table;
		this.comp = comp;
		this.n = n;
		numComps = 0;
		numSwaps = 0;
	}

	@Override
	public void sort() {
		long start = System.currentTimeMillis();
		mergeSort(tabToBeSorted, 0, n - 1);
		long stop = System.currentTimeMillis();
		long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

		System.err.println("Liczba Porownan: " + numComps);
		System.err.println("Liczba Swapow: " + numSwaps);
		time = (int) (stop - start);
		System.err.println("W czasie: " + time + " ms");

		try {
			checkIfSorted(tabToBeSorted);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}

		System.out.println("Liczba elementow: " + n);
		print(tabToBeSorted);

	}

	private void merge(int arr[], int left, int middle, int right) {
		int sizeLeft = middle - left + 1;
		int sizeRight = right - middle;
		int tmpLeft[] = new int[sizeLeft];
		int tmpRight[] = new int[sizeRight];

		for (int i = 0; i < sizeLeft; i++) { // Kopiowanie danych do tymczasowych tablic
			tmpLeft[i] = arr[left + i];
		}
		for (int i = 0; i < sizeRight; i++) {
			tmpRight[i] = arr[middle + 1 + i];
		}

		int i = 0, j = 0;
		int current = left;

		while (i < sizeLeft && j < sizeRight) {
			// Wkladanie do glownej tablicy elementu mniejszego w kontekscie zadanej relacji
			// porzadku, po kolei z dwoch tablic tymczasowych
			if (compare(tmpLeft[i], tmpRight[j])) {
				System.err.println("Swapuje: " + arr[current] + " - " + tmpLeft[i]);
				numSwaps++;
				arr[current] = tmpLeft[i];
				i++;
			} else {
				System.err.println("Swapuje: " + arr[current] + " - " + tmpRight[j]);
				numSwaps++;
				arr[current] = tmpRight[j];
				j++;
			}
			current++;

		}
		while (i < sizeLeft) { // Dopisanie pozozstalych elementow w przypadku, kiedy jedna z tablic
								// tymczasowych jest juz pusta
			System.err.println("Swapuje: " + arr[current] + " - " + tmpLeft[i]);
			numSwaps++;
			arr[current] = tmpLeft[i];
			i++;
			current++;
		}
		while (j < sizeRight) {
			System.err.println("Swapuje: " + arr[current] + " - " + tmpRight[j]);
			arr[current] = tmpRight[j];
			j++;
			current++;
		}

	}

	private void mergeSort(int arr[], int left, int right) {
		if (left < right) {

			int middle = (left + right) / 2;

			mergeSort(arr, left, middle);
			mergeSort(arr, middle + 1, right);

			merge(arr, left, middle, right);
		}
	}

	@Override
	public void checkIfSorted(int[] arr) throws Exception {
		for (int i = 1; i < arr.length; i++) {
			if (!compare(arr[i - 1], arr[i]))
				throw new Exception("Tablica nieposortowana");

		}

	}

	@Override
	public void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println("");
	}

	@Override
	public boolean compare(int a, int b) {
		numComps++;
		System.err.println("Porownuje: " + a + " - " + b);
		if (comp.equals("<=")) {
			return a <= b;
				
		} else if (comp.equals(">=")) {
			return a >= b;
			
		}
		return false;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getSwaps() {
		return numSwaps;
	}

	@Override
	public int getComps() {
		return numComps;
	}

}

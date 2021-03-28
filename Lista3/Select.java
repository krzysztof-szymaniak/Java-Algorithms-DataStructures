
public class Select {
	private int[] array;
	private int n;
	private int k;
	private int numSwaps;
	private int numComps;
	private int time;
	private int val;

	public Select(int[] array, int n, int k) {
		this.array = array;
		this.n = n;
		this.k = k;
		numSwaps = 0;
		numComps = 0;
	}

	private int findMed(int[] arr, int left, int numElems) {
		int right = left + numElems;

		for (int i = left; i < right; i++) { // posortowanie grupy 5 elementow za pomoca insertionsort
			int key = arr[i]; // Wybranie na klucz po kolei elementy z tablicy
			int j = i - 1;

			while (j >= left && compare(key, arr[j])) {// compare(key, arr[j])) {
				numSwaps++;
				System.err.println("Swapuje: " + arr[j + 1] + " - " + arr[j]);
				arr[j + 1] = arr[j]; // Przesuwanie elementow w bok
				j--;

			}
			arr[j + 1] = key; // Odlozenie klucza we wlasciwym miejscu
		}
		System.err.print("Tablica po sortowaniu: ");
		printToErr(arr);
		if (numElems % 2 == 0)
			return arr[(left + right + 1) / 2];
		else
			return arr[(left + right) / 2];

	}

	public void run() {
		System.err.println("+---------------------------------------------------------------------------------------------+");
		System.err.println("Select Startuje");
		System.err.println("+---------------------------------------------------------------------------------------------+");
		long start = System.currentTimeMillis();
		val = select(array, 0, n - 1, k);
		long stop = System.currentTimeMillis();
		time = (int) (stop - start);
		System.err.println("\n\nPorownania: " + numComps + ", Przestawienia: " + numSwaps);
		System.err.println("W czasie: " + time + " ms");
		print(array);
		System.err.println(k + " statystyka pozycyjna: " + val);

	}

	public int select(int[] arr, int left, int right, int i) {
		if(left == right)
			return arr[left];
		
		int num = right - left + 1; // liczba elementow 

		int[] median = new int[(num + 4) / 5]; // tablica przechowujaca mediany kazdej z grup, liczba grup to sufit z n/5
		int groupIndex = 0;

		while (groupIndex < num / 5) { // szukanie mediany dla kazdej z pelnych grup 5 elem.
			int m = findMed(arr, left + groupIndex * 5, 5);
			System.err.println("Mediana grupy " + groupIndex + ": " + m);
			median[groupIndex] = m;
			groupIndex++;
		}

		if (groupIndex * 5 < num) { // przypdaek jesli zostaly jeszcze jakies elementy
			int m = findMed(arr, left + groupIndex * 5, num % 5);
			System.err.println("Mediana grupy o indeksie" + groupIndex + ": " + m);
			median[groupIndex] = m;
			groupIndex++;

		}
		System.err.print("Tablica median: ");
		printToErr(median);
		System.err.print("Tablica: ");
		printToErr(arr);

		int totalMed;
		if (groupIndex == 1) // jesli byla tylko jedna grupa, nie ma potrzeby na wywolanie rekurencyjne
			totalMed = median[groupIndex - 1];
		else
			totalMed = select(median, 0, groupIndex - 1, (groupIndex) / 2); // liczenie mediany median
		System.err.println("Mediana median: " + totalMed);
		
		int p = partition(arr, left, right, totalMed);
		System.err.println("Indeks partycji: " + p);
		
		int elems = p - left + 1; // liczba elementow w partycji

		
		
		if (elems == i) // to jest element ktorego szukamy
			return arr[p];
		if (i < elems) // element znajduje sie w dolnej partycji
			return select(arr, left, p - 1, i);
		else
			return select(arr, p + 1, right, i - elems); // element znajduje sie w gornej partycji na pozycji wzglednej (i - elems)

	}

	private int partition(int[] arr, int left, int right, int pivot) {
		int i;
		for (i = left; i <= right; i++) { // znalezienie indeksu mediany median
			if (arr[i] == pivot)
				break;
		}
		swap(arr, i, left);

		i = left;
		for (int j = left + 1; j <= right; j++) {
			if (compare(arr[j], arr[left])) {
				i++;
				swap(arr, i, j);
			}
		}
		swap(arr, i, left);
		return i;
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
		if (arr[a] == arr[b])
			return;
		numSwaps++;
		System.err.println("Swapuje: " + arr[a] + " - " + arr[b] + " (" + a + "," + b + ")");
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;

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

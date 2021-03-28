
public class DualPivot implements Algorythm {
	int[] tabToBeSorted;
	String comp;
	int n;
	int numComps;
	int numSwaps;
	int time;

	public DualPivot(int[] table, String comp, int n) {
		tabToBeSorted = table;
		this.comp = comp;
		this.n = n;
		numComps = 0;
		numSwaps = 0;
	}

	@Override
	public void sort() {

		long start = System.currentTimeMillis();
		dualQuickSort(tabToBeSorted, 0, n - 1);
		long stop = System.currentTimeMillis();

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

	private int[] partition(int[] arr, int left, int right, int med1, int med2) {

		int i;
		if (!compare(med1, med2)) {
			int tmp = med1;
			med1 = med2;
			med2 = tmp;
		}
		for (i = left; i <= right; i++) { // znalezienie indeksu lewej mediany
			if (arr[i] == med1)
				break;
		}
		swap(arr, i, left);

		for (i = right; i >= left; i--) { // znalezienie indeksu prawej mediany
			if (arr[i] == med2)
				break;
		}
		swap(arr, i, right);

		int leftIndex = left + 1;
		int rightIndex = right - 1;
		int current = left + 1;
		int leftPivot = med1;
		int rightPivot = med2;

		while (current <= rightIndex) {

			// Jesli element jest mniejszy niz lewy pivot
			if (compare(arr[current], leftPivot)) {
				swap(arr, current, leftIndex);
				leftIndex++;
			}

			// Jesli element jest wiekszy niz prawy pivot
			else if (compare(rightPivot, arr[current])) {
				while (compare(rightPivot, arr[rightIndex]) && current < rightIndex)
					rightIndex--;
				swap(arr, current, rightIndex);
				rightIndex--;
				if (compare(arr[current], leftPivot)) { // Jesli element jest pomiedzy pivotami
					swap(arr, current, leftIndex);
					leftIndex++;
				}
			}
			current++;
		}
		leftIndex--;
		rightIndex++;
		swap(arr, left, leftIndex);
		swap(arr, right, rightIndex);

		int[] pivots = new int[2]; // Sposob na zwrocenie dwoch wartosci
		pivots[0] = leftIndex;
		pivots[1] = rightIndex;

		return pivots;
	}

	private void dualQuickSort(int[] arr, int left, int right) {
		if (left < right) {
			int midIndex = left + (right - left)/2;
			
			int mid = (midIndex - left + 1) / 2;
			//int midOfRight = (right - midIndex + 1) / 2;
			int medianL = select(arr, left, midIndex, mid); // znajdz mediane dla lewego i prawego fragmentu tablicy
			int medianR = select(arr, midIndex+1, right, mid);
			System.err.println("Left: "+left+", Right: "+right);
			System.err.println("midIndex: "+midIndex+ ", midOfRight: "+mid+", midOfLeft: "+mid+", medianL: "+medianL+", medianR: "+medianR);
			int[] pivots = partition(arr, left, right, medianL, medianR);
			print(arr);
			
			//System.exit(1);
			dualQuickSort(arr, left, pivots[0] - 1);
			dualQuickSort(arr, pivots[0] + 1, pivots[1] - 1);
			dualQuickSort(arr, pivots[1] + 1, right);

		}
	}

	public int select(int[] arr, int left, int right, int i) {
		int num = right - left + 1; // liczba elementow

		int[] median = new int[(num + 4) / 5]; // tablica przechowujaca mediany kazdej z grup, 
											// liczba grup to sufit zn/5
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

		int totalMed;
		if (groupIndex == 1) // jesli byla tylko jedna grupa, nie ma potrzeby na wywolanie rekurencyjne
			totalMed = median[groupIndex - 1];
		else
			totalMed = select(median, 0, groupIndex - 1, (groupIndex) / 2); // liczenie mediany median
		System.err.println("Mediana median: " + totalMed);

		int p = partition(arr, left, right, totalMed);
		System.err.println("Indeks partycji: " + p);

		if(left == right)
			return arr[p];
		
		int elems = p - left + 1; // liczba elementow w partycji
		if (elems == i) // to jest element ktorego szukamy
			return arr[p];
		if (i < elems) // element znajduje sie w dolnej partycji
			return select(arr, left, p - 1, i);
		else
			return select(arr, p + 1, right, i - elems); // element znajduje sie w gornej partycji na pozycji wzglednej
															// (i - elems)

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

		if (numElems % 2 == 0)
			return arr[(left + right + 1) / 2];
		else
			return arr[(left + right) / 2];

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
			System.err.print(arr[i] + ", ");
		}
		System.err.println("");

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

	public void swap(int[] arr, int a, int b) {
		if (arr[a] == arr[b])
			return;
		numSwaps++;
		System.err.println("Swapuje: " + arr[a] + " - " + arr[b] + " (" + a + "," + b + ")");
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;

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



public class Radix implements Algorythm {
	int[] tabToBeSorted;
	String comp;
	int n;
	int numComps;
	int numSwaps;
	int time;
	boolean reversed;

	public Radix(int[] tabToBeSorted, String comp, int n) {
		this.tabToBeSorted = tabToBeSorted;
		this.comp = comp;
		this.n = n;
		numComps = 0;
		numSwaps = 0;
		if (comp.equals(">="))
			reversed = true;
		else
			reversed = false;
	}

	public int maxElem() { // wyznaczenie maksymalnego elementu
		int max = tabToBeSorted[0];
		for (int i = 1; i < n; i++) {
			if (tabToBeSorted[i] > max) {
				max = tabToBeSorted[i];
				numComps++;
			}
		}
		return max;

	}

	void countSort(int pos) {
		int[] result = new int[n]; // tablica w ktorej przechowywany bedzie wynik
		int[] count = new int[10]; // tablica czestosci wystepowania cyfr

		for (int i = 0; i < n; i++) { // zliczanie czestosci kazdej cyfry stojacej na odpowiedniej pozycji
			int digit = (tabToBeSorted[i] / pos) % 10;
			count[digit]++;
		}

		for (int i = 1; i < 10; i++) // zsumowanie czestosci z poprzednia wartoscia pozwala na okreslenie porzadku w jakim powinnny byc cyfry
			count[i] += count[i - 1];

		if (reversed) { // jesli zadany porzadek jest malejacy, nastepuje generowanie wyniku w odwrotnym kierunku
			for (int i = 0; i < n; i++) {
				int digit = (tabToBeSorted[i] / pos) % 10;
				result[count[digit] - 1] = tabToBeSorted[i];
				count[digit]--;
			}
			for (int i = 0; i < n; i++)
				tabToBeSorted[i] = result[n - 1 - i];

		} else {
			for (int i = n - 1; i >= 0; i--) {
				int digit = (tabToBeSorted[i] / pos) % 10;
				result[count[digit] - 1] = tabToBeSorted[i];
				count[digit]--;
			}
			for (int i = 0; i < n; i++)
				tabToBeSorted[i] = result[i];

		}

	}

	@Override
	public void sort() {
		long start = System.currentTimeMillis();

		int max = maxElem();
		int position = 1;
		while (max > 0) {
			countSort(position);
			max /= 10;
			position *= 10;
		}
		long stop = System.currentTimeMillis();
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

	@Override
	public void checkIfSorted(int[] arr) throws Exception {
		for (int i = 1; i < arr.length; i++) {
			if (!compare(arr[i - 1], arr[i]))
				throw new Exception("Tablica nie posortowana");

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

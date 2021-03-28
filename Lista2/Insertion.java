
public class Insertion implements Algorythm {
	int[] tabToBeSorted;
	String comp;
	int n;
	int numComps;
	int numSwaps;
	int time;


	public Insertion(int[] tabToBeSorted, String comp, int n) {
		this.tabToBeSorted = tabToBeSorted;
		this.comp = comp;
		this.n = n;
		numComps = 0;
		numSwaps = 0;
	}

	@Override
	public void sort() {
	
		long start = System.currentTimeMillis();

		for (int i = 1; i < n; ++i) {
			int key = tabToBeSorted[i]; // Wybranie na klucz po kolei elementy z tablicy
			int j = i - 1;

			while (j >= 0 && compare(key, tabToBeSorted[j])) {
				numSwaps++;
				System.err.println("Swapuje: " + tabToBeSorted[j + 1] + " - " + tabToBeSorted[j]);
				tabToBeSorted[j + 1] = tabToBeSorted[j]; // Przesuwanie elementow w bok
				j = j - 1;

			}
			tabToBeSorted[j + 1] = key; // Odlozenie klucza we wlasciwym miejscu

		}
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

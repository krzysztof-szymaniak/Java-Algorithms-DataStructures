
public interface Algorythm {
	void sort();
	void checkIfSorted(int[] arr) throws Exception;
	void print(int[] arr);
	boolean compare(int a, int b);
	int getTime();
	int getSwaps();
	int getComps();
}

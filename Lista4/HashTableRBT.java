import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HashTableRBT implements Structure {
	// klasa zagniezdzona symbolizujaca element HashTablicy
	private class HashNode {
		// klasa zagniezdzona symbolizujaca element list jednokierunkowej
		private class LinkedListNode {
			String elem;
			LinkedListNode next;

			public LinkedListNode(String s) {
				elem = s;
				next = null;
			}
		}

		int nt;
		int size;
		RedBlackTree rbt;
		LinkedListNode head;

		public HashNode(int nt) {
			this.nt = nt;
			head = null;
			size = 0;
			rbt = null;
		}

		public void insertIntoHashNode(String s) {
			if (size < nt) {
				if (head == null)
					head = new LinkedListNode(s);
				else {
					LinkedListNode lln = new LinkedListNode(s);
					lln.next = head;
					head = lln;
				}
			} else if (size == nt) {
				if(rbt == null)
					rbt = new RedBlackTree();
				rbt.insert(s);
			} else {
				rbt.insert(s);
			}
			size ++;
		}

		public void removeFromHashNode(String s) {
			LinkedListNode lln = head;
			LinkedListNode prev = null;
			while (lln != null) {
				if (lln.elem.equals(s)) 
					break;			
				prev = lln;
				lln = lln.next;
			}
			if (lln == null) {
				if (rbt != null) {
					if(rbt.delete(s) == 1) {
						deletes++ ;
						size --;
					}					
				}				
			} else {
				if (prev != null) 
					prev.next = lln.next;
				else 
					head = lln.next;
				deletes ++;
				size --;
			}
		}

		public int find(String s) {
			LinkedListNode lln = head;
			while (lln != null) {
				numComps ++;
				totalComps ++;
				if (lln.elem.equals(s))
					return 1;
				lln = lln.next;
			}

			if (rbt != null) {
				int res = rbt.find(s);
				numComps += rbt.numComps;
				totalComps += rbt.numComps;
				return res;
			}
			return 0;
		}
	}
	/////// koniec klas zagniezdzonych /////

	HashNode[] bucketArray;
	int arrLength;
	int inserts;
	int deletes;
	int finds;
	int maxes;
	int mins;
	int succs;
	int inorders;
	int totalComps;
	int numComps;

	public HashTableRBT(int m, int nt) {
		inserts = deletes = finds = maxes = mins = succs = inorders = totalComps = numComps = 0;
		bucketArray = new HashNode[m];
		arrLength = m;
		for (int i = 0; i < m; i++) {
			bucketArray[i] = new HashNode(nt);
		}
	}

	private int getHashIndex(String s) {
		int hashCode = Math.abs(s.hashCode());
		int index = hashCode % arrLength;

		return index;
	}

	@Override
	public void insert(String s) {
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
			return;
		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}
		int index = getHashIndex(e);
		bucketArray[index].insertIntoHashNode(e);
		inserts++;
	}

	@Override
	public void load(String fileName) {
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = inserts;
		long begin = System.currentTimeMillis();
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {	
					insert(w);
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan insert: "+ (inserts - start));
			System.err.println("Sredni czas: "+ (double) totalTime/(inserts - start) +" ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int delete(String s) {
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
			return 0;
		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}
		int index = getHashIndex(e);
		bucketArray[index].removeFromHashNode(e);
		return 0;

	}

	@Override
	public int find(String s) {		
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
			return 0;
		finds++;
		numComps = 0;
		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}
		int index = getHashIndex(e);
		return bucketArray[index].find(e);
	}
	
	
	@Override
	public void deleteTest(String fileName) {// funkcja odwrotna do load(), usuwa z drzewa wszystkie wystapienia slowa w tekscie
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = deletes;
		long begin = System.currentTimeMillis();
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {
					delete(w);
					
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan delete: "+ (deletes- start));
			System.err.println("Sredni czas: "+ (double)totalTime/(deletes - start) +" ms");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void findTest(String fileName) {// funkcja wyszukujaca w drzewie wszystkich wystapien wyrazu w tekscie
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = finds;
		long begin = System.currentTimeMillis();
		int maxComps = Integer.MIN_VALUE;
		int minComps = Integer.MAX_VALUE;
		totalComps = 0;
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {			
					find(w);
					if(numComps == 0)
						continue;
					if(numComps < minComps)
						minComps = numComps;
					if(numComps > maxComps)
						maxComps = numComps;
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan find: "+ (finds - start));
			System.err.println("Sredni czas: "+ (double)totalTime/(finds - start) +" ms");
			System.err.println("Srednia liczba porowanan: "+(double)totalComps/(finds - start));
			System.err.println("Maksymalna liczba porowanan: "+maxComps);
			System.err.println("Minimalna liczba porowanan: "+minComps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String min() {
		return "";
	}

	@Override
	public String max() {
		return "";
	}

	@Override
	public String successor(String k) {
		return "";
	}

	@Override
	public void inorder() {
		System.out.println("");
	}

	@Override
	public int getInserts() {
		return inserts;
	}

	@Override
	public int getDeletes() {
		return deletes;
	}

	@Override
	public int getFinds() {
		return finds;
	}

	@Override
	public int getMins() {
		return mins;
	}

	@Override
	public int getMaxes() {
		return maxes;
	}

	@Override
	public int getSuccs() {
		return succs;
	}

	@Override
	public int getInorders() {
		return inorders;
	}

}

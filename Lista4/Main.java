import java.util.Scanner;

public class Main {
	static String type;
	static boolean runTests = true;

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			try {
				if (args[i].equals("--type")) {
					i++;
					switch (args[i]) {
					case "bst": {
						type = "bst";
						break;
					}
					case "rbt": {
						type = "rbt";
						break;
					}
					case "hmap": {
						type = "hmap";
						break;
					}
					default:
						throw new Exception("Bledny Parametr");
					}
				} 
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
			}
			Structure stc = null;
			switch (type) {
			case "bst":
				stc = new BinarySearchTree();
				break;
			case "rbt":
				stc = new RedBlackTree();
				break;
			case "hmap":
				int m = 100003;
				int nt = 10;
				stc = new HashTableRBT(m, nt);
			default:
				break;
			}
			Scanner in = new Scanner(System.in);
			int n = Integer.parseInt(in.nextLine());
			String[] lines = new String[n];
			for (int j = 0; j < n; j++) {
				lines[j] = in.nextLine();
			}
			long start = System.currentTimeMillis();
			for (int j = 0; j < n; j++) {
				String command = lines[j].split(" ")[0];
				switch (command) {
				case "insert":
					stc.insert(lines[j].split(" ")[1]);
					break;
				case "load":
					stc.load(lines[j].split(" ")[1]);
					break;
				case "delete":
					stc.delete(lines[j].split(" ")[1]);
					break;
				case "find":
					System.out.println(stc.find(lines[j].split(" ")[1]));
					break;
				case "min":
					long begin = System.currentTimeMillis();
					System.out.println(stc.min());
					System.err.println("Minimum finding time: " + (System.currentTimeMillis() - begin) + " ms");
					break;
				case "max":
					begin = System.currentTimeMillis();
					System.out.println(stc.max());
					System.err.println("Maximum finding time: " + (System.currentTimeMillis() - begin) + " ms");
					break;
				case "successor":
					begin = System.currentTimeMillis();
					System.out.println(stc.successor(lines[j].split(" ")[1]));
					System.err.println("Succesor finding time: " + (System.currentTimeMillis() - begin) + " ms");
					break;
				case "inorder":
					begin = System.currentTimeMillis();
					stc.inorder();
					System.err.println("Inorder time: " + (System.currentTimeMillis() - begin) + " ms");
					break;
				case "deleteTest":
					stc.deleteTest(lines[j].split(" ")[1]);
					break;
				case "findTest":
					stc.findTest(lines[j].split(" ")[1]);
					break;
				default:
					System.err.println("Nie znam takiej funkcji: " + command);
				}
			}
			long stop = System.currentTimeMillis();
			System.err.println("\nCzas dzialania: " + (stop - start) / 1000.0 + " s");
			System.err.println("Liczba wstawien: " + stc.getInserts());
			System.err.println("Liczba usuniec: " + stc.getDeletes());
			System.err.println("Liczba szukan: " + stc.getFinds());
			System.err.println("Liczba szukan minimum: " + stc.getMins());
			System.err.println("Liczba szukan maxiumum: " + stc.getMaxes());
			System.err.println("Liczba szukan nastepnika: " + stc.getSuccs());
			System.err.println("Liczba przejsc inorder: " + stc.getInorders());
			System.err.println("Maksymalne zapelnienie: " + stc.getInserts());
			System.err.println("Koncowe zapelnienie: " + (stc.getInserts() - stc.getDeletes()));

		}
	}

}

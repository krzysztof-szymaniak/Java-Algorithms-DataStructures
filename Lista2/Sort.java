import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class Sort {

	static String type = "";
	static String comp = "";
	static String filename = "";
	static int n = 0;
	static int k = 0;
	static boolean runTests = false;
	static boolean runSelect = false;

	public static void main(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("--type")) {
					i++;
					switch (args[i]) {
					case "insert": {
						type = "insert";
						break;
					}
					case "merge": {
						type = "merge";
						break;
					}
					case "quick": {
						type = "quick";
						break;
					}
					case "dual": {
						type = "dual";
						break;
					}
					case "radix": {
						type = "radix";
						break;
					}
					default:
						throw new Exception("Bledny Parametr");
					}
				} else if (args[i].equals("--comp")) {
					i++;
					switch (args[i]) {
					case ">=": {
						comp = ">=";
						break;
					}
					case "<=": {
						comp = "<=";
						break;
					}
					default: {
						throw new Exception("Bledny Parametr");
					}
					}
				} else if (args[i].equals("--stat")) {
					i++;
					filename = args[i];
					i++;
					k = Integer.parseInt(args[i]);
					runTests = true;

				}

			}

			if (runTests) {
				if (type == "" || comp == "")
					throw new Exception("Brak Parametru");
				n = 100;
				File f = new File(filename);
				FileOutputStream fos = new FileOutputStream(f);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
				while (n != 10100) {
					System.gc();
					int totalTime = 0;
					int totalSwaps = 0;
					int totalComps = 0;
					double totalMemUsage = 0;
					long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
					for (int j = 0; j < k; j++) {
						int tabToBeSorted[] = new int[n];
						Random gen = new Random();

						for (int i = 0; i < n; i++) {
							tabToBeSorted[i] = gen.nextInt(n);
						}

						Algorythm alg;
						switch (type) {
						case "insert": {
							alg = new Insertion(tabToBeSorted, comp, n);
							break;
						}
						case "merge": {
							alg = new Merge(tabToBeSorted, comp, n);
							break;
						}
						case "quick": {
							alg = new Quick(tabToBeSorted, comp, n);
							break;
						}
						case "radix": {
							alg = new Radix(tabToBeSorted, comp, n);
							break;
						}
						default: {
							alg = new DualPivot(tabToBeSorted, comp, n);

						}
						}

						alg.sort();
						totalTime += alg.getTime();
						totalSwaps += alg.getSwaps();
						totalComps += alg.getComps();
					
						long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
						double memUsage = 100.0 * (afterUsedMem - beforeUsedMem) / Runtime.getRuntime().totalMemory();
						totalMemUsage += memUsage;
						System.err.println("MemUsage: "+memUsage + " %");

					}
					bw.write(String.format(
							"n = %-7d k = %-5d AvgComps = %-20f AvgSwaps = %-20f AvgTime = %-20f AvgComps/n = %-20f AvgSwaps/n = %-20f AvgMemUsage = %f",
							n, k, (double) totalComps / k, (double) totalSwaps / k, (double) totalTime / k,
							(double) totalComps / (n * k), (double) totalSwaps / (n * k), totalMemUsage / k) + " %");

					bw.newLine();
					n += 100;
					bw.flush();
				}
				bw.close();
				fos.close();

			} else {
				Scanner in = new Scanner(System.in);
				System.out.println("Podaj rozmiar tablicy");
				String s = in.nextLine();
				n = Integer.parseInt(s);
				int tabToBeSorted[] = new int[n];
				if (n == 0 || type == "" || comp == "")
					throw new Exception("Brak Parametru");

				System.out.println("Wprowadz dane");
				String line = in.nextLine();
				for (int j = 0; j < n; j++) {
					tabToBeSorted[j] = Integer.parseInt(line.split(",")[j]);
				}

				Algorythm alg;
				switch (type) {
				case "insert": {
					alg = new Insertion(tabToBeSorted, comp, n);
					break;
				}
				case "merge": {
					alg = new Merge(tabToBeSorted, comp, n);
					break;
				}
				case "quick": {
					alg = new Quick(tabToBeSorted, comp, n);
					break;
				}
				case "radix": {
					alg = new Radix(tabToBeSorted, comp, n);
					break;
				}
				default: {
					alg = new DualPivot(tabToBeSorted, comp, n);

				}
				}
				alg.sort();
			}

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

/* 
 * 
 */

import java.util.Random;

public class Performance {

	static boolean	doLinked;
	static int	iterations;
	private static long startTime = System.currentTimeMillis();

	public static void doPut (int n, Random r, Map<Integer,Integer> m) {
		for (int i = 0; i < n; i++) {
			m.put(r.nextInt(), r.nextInt());
		}
	}

	public static void doGet (int n, Random r, Map<Integer, Integer> m ) {
		for (int i = 0; i < n; i++) {
			try {
				// Note that it is extremely likely that this value isn't in the Map
				// so this search is probably the worst case, and results in a
				// KeyNotFoundException being thrown.
				int search = r.nextInt();
				m.get(search);
			}
			catch (KeyNotFoundException e) {
			}
		}
	}

	public static void printStats (int iterations, long seed, Map<Integer, Integer> m, boolean linked) {
		System.out.println ("[" + seed + (linked ? " linked" : " bst   ") + "] put loop count: " + m.getPutLoopCount());
		System.out.println ("[" + seed + (linked ? " linked" : " bst   ") + "] get loop count: " + m.getGetLoopCount());
	}

	public static void doTests() {
		System.out.println("Doing initial tests:");
		Map<Integer, Integer>	bstMap = new BSTMap<Integer, Integer>();
		Map<Integer, Integer>	linkedMap = new LinkedMap<Integer, Integer>();

		Random	r = new Random(128);	// by hardcoding the seed, we get the same numbers every time
		doPut(1000, r, linkedMap);
		doGet(1000, r, linkedMap);

		doPut(1000, r, bstMap);
		doGet(1000, r, bstMap);

		System.out.println ("Your solution should match exactly for linked and be comparable for BST.");
		System.out.println ("-- Instructor's solution: ");
		System.out.println ("[128 linked] put loop count: 499500");
		System.out.println ("[128 linked] get loop count: 1000000");		
		System.out.println ("[128 bst   ] put loop count: 11079");
		System.out.println ("[128 bst   ] get loop count: 12952");

		System.out.println ("--\n");
		System.out.println ("--Your solution: ");
		printStats(1000, 128, linkedMap, true);
		printStats(1000, 128, bstMap, false);
		System.out.println ("--");
	}

	public static void main (String[] args) {
		if (args.length != 2) {
			System.out.println ("Usage: java Performance {linked | tree } iterations.");
			System.out.println ("  if iterations == 0, simple tests will be run.");
			return;
		}
		iterations = Integer.parseInt(args[1]);
		doLinked = args[0].equals("linked");

		if (iterations == 0) {
			doTests();
		}
		else {
			long seed = System.currentTimeMillis();
			Random r = new Random(seed);
			System.out.println ((doLinked ? " linked" : " bst   ") + "map over " + iterations + " iterations.");
			Map<Integer, Integer> m;

			if (doLinked)
				m = new LinkedMap<Integer, Integer>();
			else
				m = new BSTMap<Integer, Integer>(); 

			doPut(iterations, r, m);
			doGet(iterations, r, m);
			printStats(iterations, seed, m, doLinked);
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
}


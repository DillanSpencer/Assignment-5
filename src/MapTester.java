public class MapTester {

	static boolean testLinked;

	static int testCount;

	public static void displayResults (boolean passed) {
		/* There is some magic going on here getting the line number
		 * Borrowed from:
		 * http://blog.taragana.com/index.php/archive/core-java-how-to-get-java-source-code-line-number-file-name-in-code/
		 *
		 * Once we've discussed Exceptions in more detail this won't be required.
		*/
		if (passed) {
			System.out.println ("Passed test: " + testCount);
		}
		else {
			System.out.println ("Failed test: " + testCount + " at line " + Thread.currentThread().getStackTrace()[2].getLineNumber());
			System.exit(1);
		}
		testCount++;
	}

	public static void test_one() {
		System.out.println("**** begin size put get");
		Map<String, Integer> d = MapTester.<String,Integer>createMap();

		displayResults(d.size() == 0 );
		d.put("abc", 10);

		displayResults(d.size() == 1);
		try
		{
			int val = d.get("abc");

			displayResults(val == 10);
		}
		catch (KeyNotFoundException e)
		{
			displayResults(false);
		}

		d.put("abc", 15);

		displayResults(d.size() == 1);

		try
		{
			int val = d.get("abc");

			displayResults(val == 15);
		}
		catch (KeyNotFoundException e)
		{
			displayResults(false);
		}

		try
		{
			int val = d.get("def");
			displayResults(false);
			val = val + 1;

		}
		catch (KeyNotFoundException e)
		{
			displayResults(true);
		}
		System.out.println("****** end size put get\n");

	}

	public static void test_two() {
		System.out.println("**** begin stress test");
		Map<Integer, Integer> d = MapTester.<Integer,Integer>createMap();

		for (int i = 0; i < 10000; i++) {
			d.put(i,i);
		}
		displayResults(d.size() == 10000);
	
		boolean passed = true;
		for (int i = 0; i < 10000; i++) {
			try {
				d.get(i);
			}
			catch (KeyNotFoundException e) {
				passed = false;
				break;
			}		
		}
		displayResults(passed);
		System.out.println("****** end stress test\n");
	}

	public static <K extends Comparable<K>,V> Map<K,V> createMap() {
		if (testLinked)
		 	return new LinkedMap<K,V>();
		else
			return new BSTMap<K,V>();

	}
	public static void main (String[] args)
	{
		if (args.length != 0 && args[0].equals("linked")) {
			testLinked = true;
		}
		else {
			testLinked = false;
		}
		System.out.println("Testing " + (testLinked ? "LinkedList" : "BinarySearchTree")+ " implementation.");

		testCount = 0;
		try
		{
			test_one();
			test_two();
		}
		catch (Exception e)
		{
			System.out.println("Unhandled exception: "+ e);
		}
	}
}

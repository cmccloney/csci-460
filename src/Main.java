
public class Main {
	static int std_no = 8677;
	static int k = std_no % 3 + 2;
	static Processor[] processors = new Processor[k];
	
	public static void main(String[] args) {
		System.out.println("There are " + k + " processors");
		int i = 0; //current job
		int j = 0; //processor # job (i-1) ran on
		int time = 0; //time in milliseconds
					  //takes 1 ms to put each job on a processor
		
	}
}

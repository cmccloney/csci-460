
public class Main {
	static int std_no = 8677;
	static int k = std_no % 3 + 2;
	static Processor[] processors = new Processor[k];
	
	public static void main(String[] args) {
		System.out.println("There are " + k + " processors");
		k = k-1;
		for(int z = 0; z <= k; z++) {
			processors[z] = new Processor();
		}
		int i = 0; //current job
		int j = 0; //processor job (i-1) ran on
		int time = 0; //time in milliseconds
		
		//part (b.1)
		int maximum = 0;
		int minimum = 1000000;
		int average = -1;
		int std_dev = -1; //standard deviation
		int[] times = new int[100]; //store 100 elapsed times of processing 100 random jobs
		int[] processTime = {0,0,0}; //used for processing jobs
		int process = 0; //which processor to use for job i
		int round = 0; //repeat for 100 rounds
		Job[] jobs = new Job[100]; //100 randomly generated jobs
		while(round < 100) {
			time = 0;
			i = 0;
			generateJobs(jobs); //generate 100 jobs with random processing time, arriving every 1 ms
			while(i < jobs.length) {
				time++; //takes 1 ms to put each job on a processor
				for(int a = 0; a <= k; a++) {
					if(processTime[a] == time) { //processor has finished processing job
						processors[a].switchState(); //processor is now free to process more jobs
					}
				}
				process = (j+1)%(k+1); //processor to run job on
				//System.out.println(process + " " + i);
				if(processors[process].isFree()) { //if processor is free
					processors[process].processJob(jobs[i]); //process job
					processTime[process] = jobs[i].getProcessing() + time; //get processing time for this processor
					i++; //process next job, because they're arriving every 1 ms we don't need to worry about arrival time/queue
				}
				j = process; //j is now the processor job (i-1) ran on
			}
			times[round] = time;
			if(time < minimum) {
				minimum = times[round]; //set new minimum
			}
			if(time > maximum) {
				maximum = times[round]; //set new maximum
			}
			round++; //increase round
		}
		int sum = 0;
		for(int y = 0; y < 100; y++) {
			sum += times[y];
		}
		average = sum / 100; //compute average
		System.out.println("Average is " + average + " ms");
		System.out.println("Minimum is " + minimum + " ms");
		System.out.println("Maximum is " + maximum + " ms");
		System.out.println("Standard Deviation is " + std_dev + " ms");
	}
	
	public static void generateJobs(Job[] array) { //used to generate 100 jobs with random processing time between 1 ms and 500 ms
		int random, count = 0;
		
		while(count < 100) {
			random = (int )(Math.random() * 500 + 1);
			Job j = new Job(count,random);
			array[count] = j;
			count++;
		}
	}
}

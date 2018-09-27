import java.io.IOException;
import java.io.PrintWriter;

public class mccloney_1 { //not named Main due to the format requested
	static int std_no = 8677;
	static int k = std_no % 3 + 2;
	static Processor[] processors = new Processor[k];
	
	public static void main(String[] args) {
		k = k-1;
		for(int z = 0; z <= k; z++) {
			processors[z] = new Processor();
		}
		int i = 0; //current job
		int j = 0; //processor job (i-1) ran on
		int time = 0; //time in milliseconds
		
		try{
			PrintWriter writer = new PrintWriter("mccloney_1.output.txt","UTF-8");
			writer.println("There are " + (k + 1) + " processors");
			writer.println("\nPart B1:");
			b1(processors,k,i,j,time,writer); //part (b.1)
			writer.println("\nPart B2:");
			b2(processors,k,i,j,time,writer); //part (b.2)
			writer.println("\nPart C (12 jobs):");
			c1(processors,k,i,j,time,writer); //part c
			writer.println("\nPart C (100 random jobs):");
			c2(processors,k,i,j,time,writer); //part c
			writer.close();
		} catch(IOException e) {
			
		}
	}
	
	public static void c2(Processor[] processors, int k, int i, int j, int time, PrintWriter writer) {
		int maximum = 0;
		int minimum = 1000000;
		double average = -1;
		double std_dev = -1; //standard deviation
		i = 0; //re-initialize variables
		j = 0;
		time = 0;
		boolean inQueue = false; //used to see if there are any jobs waiting in the queue
		for(int a = 0; a <= k; a++) { //reset processors' status
			processors[a].free = true;
		}
		int[] processTime = {0,0,0}; //used for processing jobs
		int process = 0; //which processor to use for job i
		Job[] jobs = new Job[100]; //12 manually entered jobs
		generateJobs(jobs);
		int round = 0; //count of rounds
		int[] times = new int[100];
		while(round < 100) {
			while(i < jobs.length) {
				for(int a = 0; a <= k; a++) {
					if(processTime[a] <= time  && !processors[a].isFree()) { //processor has finished processing job
						processors[a].switchState(); //processor is now free to process more jobs
					}
				}
				if(time >= jobs[i].getArrival()) { //if the next job has arrived
					inQueue = true;
					for(int a  = 0; a <= k; a++) { //look for free processor
						process = a;
						if(processors[process].isFree()) { //if processor is free, use this one
							inQueue = false; //don't put this job in queue, move on to the next job ie. let i increase
							break;
						}
					}
					if(!inQueue) {
						processors[process].processJob(jobs[i]); //process job
						processTime[process] = jobs[i].getProcessing() + time; //get processing time for this processor
						//process = (j+1)%(k+1); //processor to run job on
						//j = process; //j is now the processor job (i-1) ran on
						i++;
					}
				}
				time++; //takes 1 ms to put each job on a processor, so start with increasing time
			}
			boolean finished = false;
			while(!finished) { //used for final job, because i == jobs.length
				time++; //increase time
				finished = true;
				for(int a = 0; a <= k; a++) { //for each processor
					if(processTime[a] <= time && !processors[a].isFree()) { //processor has finished processing job
						processors[a].switchState(); //processor is now free
					}
					if(!processors[a].isFree()) { //if there is still a processor processing a job, keep looping
						finished = false;
					}
				}
			}
			int turnAround = time - jobs[0].getArrival();
			times[round] = turnAround; //record turnaround time
			if(time < minimum) {
				minimum = times[round]; //set new minimum
			}
			if(time > maximum) {
				maximum = times[round]; //set new maximum
			}
			round++;
		}
		double sum = 0.0;
		for(int y = 0; y < 100; y++) {
			sum += times[y];
		}
		average = sum / 100; //compute average
		sum = 0;
		for(int y = 0; y < 100; y++) {
			sum += (times[y] - average)*(times[y] - average);
		}
		std_dev = Math.sqrt((sum / 100));
		writer.println("Average is " + average + " ms");
		writer.println("Minimum is " + minimum + " ms");
		writer.println("Maximum is " + maximum + " ms");
		writer.println("Standard Deviation is " + std_dev + " ms");
	}
	
	public static void c1(Processor[] processors, int k, int i, int j, int time, PrintWriter writer) {
		i = 0; //re-initialize variables
		j = 0;
		time = 0;
		boolean inQueue = false; //used to see if there are any jobs waiting in the queue
		for(int a = 0; a <= k; a++) { //reset processors' status
			processors[a].free = true;
		}
		int[] processTime = {0,0,0}; //used for processing jobs
		int process = 0; //which processor to use for job i
		Job[] jobs = new Job[12]; //12 manually entered jobs
		initJobs(jobs);
		while(i < jobs.length) {
			for(int a = 0; a <= k; a++) {
				if(processTime[a] <= time  && !processors[a].isFree()) { //processor has finished processing job
					processors[a].switchState(); //processor is now free to process more jobs
				}
			}
			if(time >= jobs[i].getArrival()) { //if the next job has arrived
				inQueue = true;
				for(int a  = 0; a <= k; a++) { //look for free processor
					process = a;
					if(processors[process].isFree()) { //if processor is free, use this one
						inQueue = false; //don't put this job in queue, move on to the next job ie. let i increase
						break;
					}
				}
				if(!inQueue) {
					processors[process].processJob(jobs[i]); //process job
					processTime[process] = jobs[i].getProcessing() + time; //get processing time for this processor
					//process = (j+1)%(k+1); //processor to run job on
					//j = process; //j is now the processor job (i-1) ran on
					i++;
				}
			}
			time++; //takes 1 ms to put each job on a processor, so start with increasing time
		}
		boolean finished = false;
		while(!finished) { //used for final job, because i == jobs.length
			time++; //increase time
			finished = true;
			for(int a = 0; a <= k; a++) { //for each processor
				if(processTime[a] <= time && !processors[a].isFree()) { //processor has finished processing job
					processors[a].switchState(); //processor is now free
				}
				if(!processors[a].isFree()) { //if there is still a processor processing a job, keep looping
					finished = false;
				}
			}
		}
		int turnAround = time - jobs[0].getArrival();
		writer.println("The total turnaround time was " + turnAround + " ms for " + (k+1) + " processors");
	}
	
	public static void b2(Processor[] processors, int k, int i, int j, int time, PrintWriter writer) {
		i = 0; //re-initialize variables
		j = 0;
		time = 0;
		boolean inQueue = false; //used to see if there are any jobs waiting in the queue
		for(int a = 0; a <= k; a++) { //reset processors' status
			processors[a].free = true;
		}
		int[] processTime = {0,0,0}; //used for processing jobs
		int process = 0; //which processor to use for job i
		Job[] jobs = new Job[12]; //12 manually entered jobs
		initJobs(jobs);
		while(i < jobs.length) {
			for(int a = 0; a <= k; a++) {
				if(processTime[a] <= time  && !processors[a].isFree()) { //processor has finished processing job
					processors[a].switchState(); //processor is now free to process more jobs
				}
			}
			if(time >= jobs[i].getArrival()) { //if the next job has arrived
				if(processors[process].isFree()) { //if processor is free
					processors[process].processJob(jobs[i]); //process job
					inQueue = false;  //move on to the next job ie. let i increase
					processTime[process] = jobs[i].getProcessing() + time; //get processing time for this processor
					process = (j+1)%(k+1); //processor to run job on
					j = process; //j is now the processor job (i-1) ran on
				}else {
					inQueue = true;
				}
				if(!inQueue) {
					i++;
				}
			}
			time++; //takes 1 ms to put each job on a processor, so start with increasing time
		}
		boolean finished = false;
		while(!finished) { //used for final job, because i == jobs.length
			time++; //increase time
			finished = true;
			for(int a = 0; a <= k; a++) { //for each processor
				if(processTime[a] <= time && !processors[a].isFree()) { //processor has finished processing job
					processors[a].switchState(); //processor is now free
				}
				if(!processors[a].isFree()) { //if there is still a processor processing a job, keep looping
					finished = false;
				}
			}
		}
		int turnAround = time - jobs[0].getArrival();
		writer.println("The total turnaround time was " + turnAround + " ms for " + (k+1) + " processors");
	}
	
	public static void b1(Processor[] processors, int k, int i, int j, int time, PrintWriter writer){
		int maximum = 0;
		int minimum = 1000000;
		double average = -1;
		double std_dev = -1; //standard deviation
		boolean inQueue = false; //used to see if there are any jobs waiting in the queue
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
				time++; //takes 1 ms to put each job on a processor, so start with increasing time
				for(int a = 0; a <= k; a++) {
					if(processTime[a] <= time  && !processors[a].isFree()) { //processor has finished processing job
						processors[a].switchState(); //processor is now free to process more jobs
					}
				}
				if(processors[process].isFree()) { //if processor is free
					processors[process].processJob(jobs[i]); //process job
					inQueue = false; //move on to the next job ie. let i increase
					processTime[process] = jobs[i].getProcessing() + time; //get processing time for this processor
					if(!inQueue) {
						i++; //process next job, because they're arriving every 1 ms we don't need to worry about arrival time/queue
					}
				}
				process = (j+1)%(k+1); //processor to run next job on
				j = process; //j is now the processor job (i-1) ran on
			}
			times[round] = time; //record total time
			if(time < minimum) {
				minimum = times[round]; //set new minimum
			}
			if(time > maximum) {
				maximum = times[round]; //set new maximum
			}
			round++; //increase round
		}
		double sum = 0.0;
		for(int y = 0; y < 100; y++) {
			sum += times[y];
		}
		average = sum / 100; //compute average
		sum = 0;
		for(int y = 0; y < 100; y++) {
			sum += (times[y] - average)*(times[y] - average);
		}
		std_dev = Math.sqrt((sum / 100));
		writer.println("Average is " + average + " ms");
		writer.println("Minimum is " + minimum + " ms");
		writer.println("Maximum is " + maximum + " ms");
		writer.println("Standard Deviation is " + std_dev + " ms");
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
	
	public static void initJobs(Job[] array) { //used to manually enter 12 jobs for part b.2 and c
		array[0] = new Job(4,9);
		array[1] = new Job(15,2);
		array[2] = new Job(18,16);
		array[3] = new Job(20,3);
		array[4] = new Job(26,29);
		array[5] = new Job(29,198);
		array[6] = new Job(35,7);
		array[7] = new Job(45,170);
		array[8] = new Job(57,180);
		array[9] = new Job(83,178);
		array[10] = new Job(88,73);
		array[11] = new Job(95,8);
	}
}

class Job { //class for creating jobs
	int arrival_time, processing_time;
	
	public Job(int arrival, int processing) {
		arrival_time = arrival;
		processing_time = processing + 1; //add 1 to account for time spent getting job onto processor
	}
	
	public int getArrival() { //get the arrival time in ms
		return arrival_time;
	}
	
	public int getProcessing() { //get the processing time in ms
		return processing_time;
	}
	
}

class Processor { //class for creating processors
	boolean free = true;
	
	public Processor() {
		
	}
	
	public boolean isFree() { //is the processor currently free
		return free;
	}
	
	public void processJob(Job j) { //process job j
		free = false;
	}
	public void switchState() { //switch the processor's state from busy to free
		free = true;
	}
}


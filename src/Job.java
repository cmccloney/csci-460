
public class Job {
	int arrival_time, processing_time;
	
	public Job(int arrival, int processing) {
		arrival_time = arrival;
		processing_time = processing + 1; //add 1 to account for time spent getting job onto processor
	}
	
	public int getArrival() {
		return arrival_time;
	}
	
	public int getProcessing() {
		return processing_time;
	}
	
}

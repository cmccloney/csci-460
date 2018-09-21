
public class Job {
	int arrival_time, processing_time;
	
	public Job(int arrival, int processing) {
		arrival_time = arrival;
		processing_time = processing;
	}
	
	public int getArrival() {
		return arrival_time;
	}
	
	public int getProccesing() {
		return processing_time;
	}
	
}

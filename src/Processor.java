
public class Processor {
	boolean free = true;
	int count = 0;
	
	public Processor() {
		
	}
	
	public boolean isFree() {
		return free;
	}
	
	public void processJob(Job j) {
		count += j.getProccesing();
		free = false;
	}
}
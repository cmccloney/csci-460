
public class Processor {
	boolean free = true;
	int count = 0;
	
	public void Processor() {
		
	}
	
	public boolean isFree() {
		return free;
	}
	
	public void processJob(Job j) {
		count += j.getProccesing();
		free = false;
	}
}


public class Processor {
	boolean free = true;
	int num = -1;
	
	public Processor() {
		
	}
	
	public boolean isFree() {
		return free;
	}
	
	public void processJob(Job j) {
		free = false;
	}
	
	public void jobNumber(int id) {
		num = id;
	}
	
	public int getJobNumber() {
		return num;
	}
	
	public void switchState() {
		free = true;
	}
}

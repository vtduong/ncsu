package processor_ReadAndWrite;

import java.io.FileNotFoundException;

public abstract class ProcessorCommon {
	
	/**
	 * keeps track of the process
	 */
	private static int numProcess = 0;
	
	/**
	 * returns the number of processes having made
	 * @return
	 */
	public int getNumProcess() {
		return numProcess;
	}
	
	public void resetNumProcess() {
		numProcess = 0;
	}
	/**
	 * sets number of processes
	 */
	public void incrementNumProcess(){
		numProcess++;
	}
	
	/**
	 * implements of the corresponding processor
	 */
	public abstract void process() throws FileNotFoundException;
}

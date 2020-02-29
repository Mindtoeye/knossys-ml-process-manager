package com.knossys.rnd.process.modules;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public class KModuleDataConsumer extends KModuleBase {

	private BlockingQueue<KDataset> incomingQueue = new LinkedBlockingQueue<KDataset>();
	
	/**
	 * 
	 */
	public KModuleDataConsumer () {
		setLogger(Logger.getLogger(KModuleDataConsumer.class));
	}		
	
	/**
	 * 
	 * @param newData
	 */
	public void consume (KDataset newData) {
		debug ("consume ()");
		
		// Quick sanity check
		if (newData==null) {
			debug ("Error: can't consume null data");
			return;
		}
		
		incomingQueue.add(newData);
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	protected void processNewData(KDataset data) throws InterruptedException {
		debug ("processNewData ()");
		
		debug ("This needs to be implemented in a child class!");
	}

	/**
	 * 
	 */
	public void run() {
		debug("run ()");
		
		while (true) {
			try {
				KDataset data = incomingQueue.take();
				debug ("Consuming dataset with command: " + data.getCommand() + " - " + data.getId());
				processNewData (data);
			} catch (InterruptedException err) {
				debug ("Caught interrupt: " + err.getMessage() +", exiting (main) thread ...");
				return;
			}
		}		
	}	
}

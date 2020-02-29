package com.knossys.rnd.process.modules;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public class KModuleGremlin extends KModuleBase {

	/**
	 * 
	 */
	public KModuleGremlin () {
		setLogger(Logger.getLogger(KModuleGremlin.class));
	}	
	
	/**
	 * 
	 */
	private void timerLoop() {
		debug("timerLoop ()");

		while (true) {
			try {
				Thread.sleep(30*1000); // Wait 30 seconds, then request the entire system to be shutdown
				
				debug("Calling stop() system call to force the task manager to exit");
				produce(new KDataset(), "stop()");
			} catch (InterruptedException err) {
				debug("Caught interrupt: " + err.getMessage() + ", exiting thread (" + threadNo + ") ...");
				return; // likely called by the manager, exit the thread
			}
		}
	}

	/**
	 * 
	 */
	public void run() {
		debug("run ()");

		timerLoop();
	}
}

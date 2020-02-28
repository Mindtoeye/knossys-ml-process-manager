package com.knossys.rnd.process;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public class KModuleTimer extends KModuleBase {

	/**
	 * 
	 */
	public KModuleTimer () {
		setLogger(Logger.getLogger(KModuleTimer.class));
	}	
	
	/**
	 * 
	 */
	private void timerLoop() {
		debug("timerLoop ()");

		int i = 0;

		while (true) {
			try {
				Integer number = i + (10 * threadNo);

				debug("\n\n\n");
				debug("Produced: " + number + ", by thread: " + threadNo);

				produce(new KDataset(), "copy (" + number.toString() + ")");
				Thread.sleep(getRandomInteger(1000, 10000));
			} catch (InterruptedException err) {
				debug("Caught interrupt: " + err.getMessage() + ", exiting thread (" + threadNo + ") ...");
				return; // likely called by the manager, exit the thread
			}

			i++;
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

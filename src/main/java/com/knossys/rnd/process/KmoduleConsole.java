package com.knossys.rnd.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public class KmoduleConsole extends KModuleBase {

	private BufferedReader reader = null;
	
	/**
	 * 
	 */
	public KmoduleConsole () {
		setLogger(Logger.getLogger(KmoduleConsole.class));
	}

	/**
	 * 
	 */
	public boolean isSingleton () {
		return (true);
	}
	
	/**
	 * 
	 */
	private void closeConsole() {
		debug("closeConsole ()");

		if (reader != null) {
			try {
				//System.in.close();
				reader.close();
			} catch (IOException e) {
				debug ("IO error while closing console input: " + e.getMessage());
				reader = null;
				return;
			}
		} else {
			debug ("reader is already null");
		}

		reader = null;
	}
	
	/**
	 * 
	 */
	public void run() {
		debug("run ()");
		
		reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String generated = generateString();
			
			try {
				if (reader.ready()==true) {
					generated = reader.readLine();
					
					if (generated.indexOf("quit") != -1) {
					  try {
							produce(new KDataset(),KDataset.COMMAND_STOP);
						} catch (InterruptedException e) {
							debug("Caught interrupt: " + e.getMessage() + ", exiting thread (" + threadNo + ") ...");
							closeConsole();
							return; // likely called by the manager, exit the thread
						}	
					} else {
						try {
							produce(new KDataset(),KDataset.COMMAND_IDLE);
						} catch (InterruptedException e) {
							debug("Caught interrupt: " + e.getMessage() + ", exiting thread (" + threadNo + ") ...");
							closeConsole();
							return; // likely called by the manager, exit the thread
						}	
					}					
				} else {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						debug("Caught interrupt: " + e.getMessage() + ", exiting thread (" + threadNo + ") ...");
						closeConsole();
						return; // likely called by the manager, exit the thread
					}
				}
			} catch (IOException e1) {
				debug("Caught interrupt: " + e1.getMessage() + ", exiting thread (" + threadNo + ") ...");
				closeConsole();
				return; // likely called by the manager, exit the thread
			}
		}
	}	
	
	/**
	 * 
	 */
	public void shutdown() {
		debug("shutdown ()");
		
		closeConsole();
	}		
}

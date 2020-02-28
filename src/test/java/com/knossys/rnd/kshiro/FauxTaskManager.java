/**
 * 
 */
package com.knossys.rnd.kshiro;

import org.apache.log4j.Logger;

import com.knossys.rnd.process.KModuleTemplate;
import com.knossys.rnd.process.KModuleThreadManager;
import com.knossys.rnd.process.KModuleTimer;
import com.knossys.rnd.process.KmoduleConsole;

/**
 * @author vvelsen
 */
public class FauxTaskManager extends KModuleThreadManager  {

	/**
	 * 
	 */
	public FauxTaskManager () {
		setLogger(Logger.getLogger(FauxTaskManager.class));
	}	
	
	/**
	 * 
	 */
	public void startDriver () {
		debug ("startDriver ()");
		
		boot ();
		
		addThread (new KModuleTimer());
		addThread (new KModuleTimer());
		addThread (new KmoduleConsole());
		// This one will be rejected because it's marked as a singleton
		addThread (new KmoduleConsole());
		addThread (new KModuleTemplate ());
		addThread (new KModuleTemplate ());
	}
		
	/**
	 * @param args
	 */
	/*
	public static void main(String args[]) {

		KThreadTestApp driver=new KThreadTestApp();
						
    driver.startDriver();
	}
	*/
}

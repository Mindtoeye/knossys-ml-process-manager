/**
 * 
 */
package com.knossys.rnd.process;

import org.apache.log4j.Logger;

import com.knossys.rnd.process.modules.KModuleGremlin;
import com.knossys.rnd.process.modules.KModuleTemplate;
import com.knossys.rnd.process.modules.KModuleTimer;
import com.knossys.rnd.process.modules.KmoduleConsole;

/**
 * @author vvelsen
 */
public class FauxTaskManager2 extends KModuleThreadManager  {

	/**
	 * 
	 */
	public FauxTaskManager2 () {
		setLogger(Logger.getLogger(FauxTaskManager2.class));
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
		addThread (new KModuleTemplate ());
		addThread (new KModuleTemplate ());
		addThread (new KModuleGremlin ());
	}
		
	/**
	 * @param args
	 */
	public static void main(String args[]) {

		FauxTaskManager2 driver=new FauxTaskManager2();
    driver.startDriver();
	}
}

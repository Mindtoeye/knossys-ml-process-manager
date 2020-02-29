package com.knossys.rnd.process.modules;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public class KModuleTemplate extends KModuleDataConsumer {
	
	/**
	 * 
	 */
	public KModuleTemplate () {
		setLogger(Logger.getLogger(KModuleTemplate.class));
	}	
	
	/**
	 * Called by the framework when new data has arrived. All you need to do is
	 * react to it and produce new data if warranted. There is no penalty for
	 * not using the data.
	 * 
	 * @throws InterruptedException 
	 */
	protected void processNewData(KDataset data) throws InterruptedException {
		debug ("processNewData ()");
		
		if (data.getCommand().indexOf("copy")!=-1) {
			debug ("Found copy command, generating new dataset ...");

			produce (new KDataset(),"dup ()");
		}
	}
}

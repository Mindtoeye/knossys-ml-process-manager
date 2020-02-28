package com.knossys.rnd.process;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
class KModuleBase extends KTestingTools implements Runnable, KThreadInterface {

	protected BlockingQueue<KDataset> eventQueue = null;
	protected int threadNo = -1;
	
	/**
	 * 
	 */
	public KModuleBase () {
		setLogger(Logger.getLogger(KModuleBase.class));
	}		
	
	/**
	 * 
	 */
	public boolean isSingleton () {
		return (false);
	}
	
	/**
	 * @param newData
	 */
	public void consume (KDataset newData) {
		//debug ("consume () (Dummy)");
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void produce (KDataset newData) throws InterruptedException {
		setDatasetCommand (newData,"data ()");
		eventQueue.put(newData);
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void produce (KDataset newData, String aCommand) throws InterruptedException {
		setDatasetCommand (newData,aCommand);
		eventQueue.put(newData);
	}		
		
	/**
	 * 
	 * @param aDataset
	 * @param aCommand
	 */
	protected void setDatasetCommand (KDataset aDataset, String aCommand) {
		aDataset.setCommand(this.threadNo + ":" + aCommand);
	}
	
	/**
	 * 
	 */	
	public BlockingQueue<KDataset> getEventQueue() {
		return eventQueue;
	}

	/**
	 * 
	 */	
	public void setEventQueue(BlockingQueue<KDataset> eventQueue) {
		this.eventQueue = eventQueue;
	}

	/**
	 * 
	 */	
	public int getThreadNo() {
		return threadNo;
	}

	/**
	 * 
	 */	
	public void setThreadNo(int threadNo) {
		this.threadNo = threadNo;
	}
	
	/**
	 * 
	 */
	public void shutdown() {
		debug("shutdown ()");
		
		debug ("Compilation error: the derived class needs to overwrite shutdown ()");
	}	

	/**
	 * 
	 */
	public void run() {
		debug("run ()");
	
		debug ("Compilation error: the derived class needs to overwrite run ()");
	}
}

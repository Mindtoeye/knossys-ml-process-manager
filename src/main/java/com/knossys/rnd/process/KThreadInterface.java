/**
 * 
 */
package com.knossys.rnd.process;

import java.util.concurrent.BlockingQueue;

import com.knossys.rnd.data.KDataset;

/**
 * @author vvelsen
 */
public interface KThreadInterface {

	/**
	 * Sometimes you may want to ensure that there is only one instance per pipeline of
	 * a module. In that case, implement in your class and return true. Returns false
	 * by default.
	 * @return
	 */
	public boolean isSingleton ();
	
	/**
	 * 
	 */
	public void shutdown ();

	/**
	 * 
	 * @param eventQueue
	 */
	public void setEventQueue(BlockingQueue<KDataset> eventQueue);
  	
	/**
	 * 
	 * @param newData
	 */
	public void consume (KDataset newData);
	
	/**
	 * @throws InterruptedException 
	 */
	public void produce (KDataset newData) throws InterruptedException;
	
	/**
	 * @throws InterruptedException 
	 */
	public void produce (KDataset newData, String aCommand) throws InterruptedException;

}

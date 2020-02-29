/**
 * Via: https://www.programcreek.com/2009/02/notify-and-wait-example/
 * Via: https://stackoverflow.com/questions/8562227/waiting-for-an-event-in-java-how-hard-is-it
 * Via: https://docs.oracle.com/javase/tutorial/essential/concurrency/join.html
 * Via: https://www.geeksforgeeks.org/producer-consumer-solution-using-threads-java/
 * Via: https://www.tutorialspoint.com/javaexamples/thread_procon.htm
 * Via: https://stackoverflow.com/questions/25393938/multiple-producer-multiple-consumer-multithreading-java
 */
package com.knossys.rnd.process;

import java.util.ArrayList;
import java.util.concurrent.*;

import org.apache.log4j.Logger;

import com.knossys.rnd.data.KDataset;
import com.knossys.rnd.process.modules.KModuleBase;
import com.knossys.rnd.process.modules.KThreadInterface;

/**
 * @author vvelsen
 */
public class KModuleThreadManager extends KTestingTools implements Runnable, KThreadInterface {

	/**
	 * @author vvelsen
	 */
  private class KThreadObject {
  	public KThreadInterface managedObject=null;
  	public Thread thread = null;
  };
	
	private BlockingQueue<KDataset> eventQueue = new LinkedBlockingQueue<KDataset>();
	private ArrayList<KThreadObject> threads = new ArrayList<KThreadObject> ();
	private Thread consumer=null;
	
	private int threadCount = 0;
	
	/**
	 * 
	 */
	public KModuleThreadManager () {
		setLogger(Logger.getLogger(KModuleThreadManager.class));
	}		
	
	/**
	 * 
	 */
	public boolean isSingleton() {
		return true;
	}	
	
	/**
	 * 
	 * @param aCommand
	 * @return
	 */
	protected Integer getThreadIndex (String aCommand) {
		int anIndex=aCommand.indexOf(":");
		
		if (anIndex!=-1) {
			return (Integer.parseInt(aCommand.substring(0, anIndex)));
		}
		
		return (-1);
	}
	
	/**
	 * WE NEED TO REMOVE THIS! Remove the KThreadInterface dependency
	 */
	public void consume(KDataset newData) {}

	/**
	 * WE NEED TO REMOVE THIS! Remove the KThreadInterface dependency
	 */	
	public void produce(KDataset newData) throws InterruptedException {}

	/**
	 * WE NEED TO REMOVE THIS! Remove the KThreadInterface dependency
	 */	
	public void produce(KDataset newData, String aCommand) throws InterruptedException {}
	
	/**
	 * 
	 */
	public void setEventQueue(BlockingQueue<KDataset> aQueue) {
		eventQueue=aQueue;
	}
	
	/**
	 * @return
	 */
	public BlockingQueue<KDataset> getQueue () {
		return (eventQueue);
	}
	
	/**
	 * 
	 */
	public void boot () {
		consumer = new Thread(this);
		consumer.start();
	}
	
	/**
	 * 
	 */
	public void shutdown() {
	  debug ("shutdown ()");	
	}	
	
	/**
	 * 
	 * @param aNewModule
	 */
	public void addThread (KModuleBase aNewModule) {
		debug ("addThread ()");
		
		if (aNewModule.isSingleton()==true) {
			for (int i=0;i<threads.size();i++) {
			  KThreadObject tracker=threads.get(i);
			
			  KThreadInterface managedObject=tracker.managedObject;
			  
			  if (aNewModule.getClass().getCanonicalName().equalsIgnoreCase(managedObject.getClass().getCanonicalName())==true) {
			  	debug ("Attempting to add a copy of a module marked as singleton we already have registered: " + aNewModule.getClass().getCanonicalName());
			  	return;
			  }
			}
		}
		
		aNewModule.setEventQueue(eventQueue);
		aNewModule.setThreadNo(threadCount);
		
		threadCount++;
		
		Thread newThread=new Thread (aNewModule);
		KThreadObject tracker=new KThreadObject ();
		tracker.thread=newThread;
		tracker.managedObject=aNewModule;
		threads.add(tracker);
		newThread.start();
	}
	
	/**
	 * 
	 */
	public void stopProducerThreads () {
		for (int i=0;i<threads.size();i++) {
			KThreadObject tracker=threads.get(i);
			
			KThreadInterface managedObject=tracker.managedObject;
			if (managedObject!=null) {
				managedObject.shutdown();
			}
			
			Thread closer=tracker.thread;
			
			if (closer!=null) {
  			closer.interrupt();
			}
		}
	}
	
	/**
	 * 
	 */
	public void shutdownManager () {
		debug ("shutdownManager ()");

		stopProducerThreads ();

		consumer.interrupt();
	}
	
	/**
	 * A full propagation method that uses the pipeline graph to
	 * determine where data should go next. Note that this version
	 * doesn't have any checks for cycles!
	 */
	private void propagate (KDataset dup, String originator) {
		debug ("Propagate ("+originator+")");
		
	}	
	
	/**
	 * A simple propagation method that simply sends the data to all
	 * registered modules except the one that produced the data. Of
	 * course this can get out of hand if every module creates a 'copy'
	 * statement when receiving a dataset.
	 */
	private void propagateAll (KDataset dup, String originator) {
		debug ("propagateAll ("+originator+")");
		
		for (int i=0;i<threads.size();i++) {
			KThreadObject tracker=threads.get(i);
			
			KThreadInterface managedObject=tracker.managedObject;
			
			// Safety check
			if (managedObject!=null) {
				KModuleBase dummy=(KModuleBase) managedObject;
				
			  // Make sure we don't propagate to ourselves!
			  if (dummy.getThreadNo()!=Integer.parseInt(originator)) {
			  	debug ("Propagating to: " + dummy.getThreadNo());
					dummy.consume(dup);
				}
			}
		}	
	}
	
	/**
	 * 
	 */
	public void run() {
		debug ("run ()");
		
		while (true) {
			try {
				KDataset data = eventQueue.take();
				
				Integer threadIndex = getThreadIndex (data.getCommand());
				
				debug ("Inspecting data with command: " + data.getCommand() + ", by thread:" + threadIndex + " and data id: " + data.getId());
								
				if (data.getCommand().indexOf(KDataset.COMMAND_STOP) != -1) {
					debug ("Found stop command, shutting down ...");
					shutdownManager ();
					return;
				}	else {
					debug ("Non core command: " + data.getCommand());
				}
				
				int subber=data.getCommand().indexOf(":");				
				
				if (subber!=-1) {
					propagateAll (data,data.getCommand().substring(0, subber));
				}
			} catch (InterruptedException err) {
				debug ("Caught interrupt: " + err.getMessage() +", exiting (main) thread ...");
				return;
			}
		}
	}
}

package com.knossys.rnd.process;

import java.util.Random;

import javax.json.JsonObject;

import org.apache.log4j.Logger;

import com.knossys.rnd.Base;

/**
 * @author vvelsen
 */
public class KTestingTools extends Base {

	/**
	 * 
	 */
	public KTestingTools () {
		setLogger(Logger.getLogger(KTestingTools.class));
	}	
	
	/**
	 * @param aMessage
	 */
	protected void taskIndicateError(String anId, String aData, String aMessage) {
		Base.debugStatic("taskIndicateError (" + anId + ", " + aData + ", " + aMessage + ")");
	}

	/**
	 * @param anId
	 * @param aData
	 */
	protected void taskIndicateStart(String anId, String aData) {
		Base.debugStatic("taskIndicateStart (" + anId + ", " + aData + ")");
	}

	/**
	 * @param anId
	 * @param aData
	 */
	protected void taskIndicateDone(String anId, String aData) {
		Base.debugStatic("taskIndicateDone (" + anId + ", " + aData + ")");
	}

	/**
	 * @param id
	 * @param name
	 * @param renderedDisplayObject
	 */
	protected void taskIndicateDisplayObject(String anId, String aData, JsonObject renderedDisplayObject) {
		Base.debugStatic("taskIndicateDisplayObject (" + anId + ", " + aData + ")");
	}

	protected void taskIndicateErrorException(String anId, String aData, String aMessage) {
		Base.debugStatic("taskIndicateErrorException (" + anId + ", " + aData + ", " + aMessage + ")");
	}

	protected int getRandomInteger (int min, int max) {
		Random random = new Random();
		int randomNumber = random.nextInt(max + 1 - min) + min;
		return (randomNumber);
	}
	
	/**
	 * @param id
	 * @param name
	 */
	protected void taskIndicatePipelineFinished(String anId, String aData) {
		Base.debugStatic(anId + ", " + aData);
	}

	/**
	 * 
	 */
	public String generateString() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 10;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		//System.out.println(generatedString);
		
		return (generatedString);
	}
}

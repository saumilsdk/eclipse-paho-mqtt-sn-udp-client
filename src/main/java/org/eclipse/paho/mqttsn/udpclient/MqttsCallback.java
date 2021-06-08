/*******************************************************************************
 * Copyright (c) 2010, 2013 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *    Ian Craggs - initial API and implementation and/or initial documentation
 *******************************************************************************/


package org.eclipse.paho.mqttsn.udpclient;

public interface MqttsCallback {
	
	public static final int MQTTS_OK = 0xF0;
	public static final int MQTTS_ERR_STACK_NOT_READY = 0xF1;
//	private static int MQTTS_ERR_DATA_TOO_LONG = 0xF2;
	public static final int MQTTS_LOST_GATEWAY = 0xF3;

	//public void connectionLost();

	public int publishArrived(boolean retain, int QoS, int topicId, byte[] thisPayload);
	
	public void connected();
	
	public void disconnected(int returnType);
	
	public void unsubackReceived();
	
	public void subackReceived(int grandesQos, int topicId, int returnCode);
	
	public void pubCompReceived(); 
	
	public void pubAckReceived(int topicId, int returnCode); 
	
	public void regAckReceived(int topicId, int returnCode); 
	
	public void registerReceived(int topicId, String topicName); 
	
	public void connectSent();
}
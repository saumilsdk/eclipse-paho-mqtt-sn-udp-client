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

package org.eclipse.paho.mqttsn.udpclient.messages.mqttsn;

/**
 * This object represents a Mqtts DISCONNECT message.
 * 
 *
 */
public class MqttsDisconnect extends MqttsMessage {

	private short sleepDuration;

	/**
	 * MqttsDisconnect constructor.Sets the appropriate message type. 
	 */
	public MqttsDisconnect() {
		msgType = MqttsMessage.DISCONNECT;
	}
	
	/**
	 * MqttsDisconnect constructor.Sets the appropriate message type and constructs 
	 * a Mqtts DISCONNECT message from a received byte array.
	 * @param data: The buffer that contains the DISCONNECT message.
	 */
	public MqttsDisconnect(byte[] data) {
		msgType = MqttsMessage.DISCONNECT;
	}

	public MqttsDisconnect(short sleepDuration) {
		msgType = MqttsMessage.DISCONNECT;
		this.sleepDuration = sleepDuration;
	}
	
	/**
	 * Method to convert this message to a byte array for transmission.
	 * @return A byte array containing the DISCONNECT message as it should appear on the wire.
	 */	
	public byte[] toBytes() {
		int length = 2;
		if (sleepDuration > 0) length = 4;
		byte[] data = new byte[length];
		data[0] = (byte)length;
		data[1] = (byte)msgType;
		if (sleepDuration > 0) {
			data[2] = (byte) ((sleepDuration >> 8) & 0xFF);
			data[3] = (byte) (sleepDuration & 0xFF);
		}
		return data;
	}
}
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

import java.io.UnsupportedEncodingException;

import org.eclipse.paho.mqttsn.udpclient.utils.Utils;


/**
 * This object represents a Mqtts WILLTOPICUPD message.
 * 
 *
 */
public class MqttsWillTopicUpd extends MqttsMessage {

	//Mqtts WILLTOPICUPD fields
	private int qos;
	private boolean retain;
	private String willTopic;
	
	/**
	 * MqttsWillTopicUpd constructor.Sets the appropriate message type. 
	 */
	public MqttsWillTopicUpd() {
		msgType = MqttsMessage.WILLTOPICUPD;
	}
	
	/** 
	 * MqttsWillTopicUpd constructor.Sets the appropriate message type and constructs 
	 * a Mqtts WILLTOPICUPD message from a received byte array.
	 * @param data: The buffer that contains the WILLTOPICUPD message.
	 */
	public MqttsWillTopicUpd(byte[] data) {
		int length = getLength(data);
		int headerLength = data[0] == 0x01 ? 5 : 3;
		msgType = MqttsMessage.WILLTOPICUPD;
		if (length > headerLength){ //non empty WILLTOPIC message
			qos = (data[headerLength - 1] & 0x60) >> 5;
			retain = ((data[headerLength - 1] & 0x10) >> 4 != 0);
			try {
				willTopic = new String(data, headerLength, length - headerLength, Utils.STRING_ENCODING);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Method to convert this message to a byte array for transmission.
	 * @return A byte array containing the WILLTOPICUPD message as it should appear on the wire.
	 */
	public byte[] toBytes(){
		int headerLength = willTopic.length() + 3 > 255 ? 5 : 3;
		int length = headerLength + willTopic.length();
		byte[] data = setLength(new byte[length], length);
		int flags = 0;
		if(qos == -1) {
			flags |= 0x60;
		} else if(qos == 0) {

		} else if(qos == 1) {
			flags |= 0x20;
		} else if(qos == 2) {
			flags |= 0x40;
		} else {
			throw new IllegalArgumentException("Unknown QoS value: " + qos);
		}
		if(retain) {
			flags |= 0x10;
		}

		data[headerLength - 2] = (byte)msgType;
		data[headerLength - 1] = (byte)flags;
		System.arraycopy(willTopic.getBytes(), 0, data, headerLength, willTopic.length());
		return data;
	}

	public int getQos() {
		return qos;
	}
	public void setQos(int qoS) {
		this.qos = qoS;
	}
	public boolean isRetain() {
		return retain;
	}
	public void setRetain(boolean retain) {
		this.retain = retain;
	}
	public String getWillTopic() {
		return willTopic;
	}
	public void setWillTopic(String willTopic) {
		this.willTopic = willTopic;
	}
}
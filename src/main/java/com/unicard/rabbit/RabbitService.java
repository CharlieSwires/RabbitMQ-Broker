package com.unicard.rabbit;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicard.rabbit.RequestBean.Inner;

import jakarta.annotation.PostConstruct;

@Service
public class RabbitService implements Observer, ObserverRequestBean{
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitService.class);

	@Autowired
	private RabbitMQConsumerBack consumer1;
	
	@Autowired
	private RabbitMQConsumerBack2 consumer2;
	
	@Autowired
	private RabbitMQProducer producer;

	@Autowired
	private RabbitMQProducer2 producer2;

	@Autowired
	private MessagesQueues mq;
	
	private Object dummy = (Object)"fred";
	
	@PostConstruct
	private void init() {
		consumer2.registerObserver((Observer) this);
		consumer1.registerObserver((Observer) this);
		consumer2.registerObserver((ObserverRequestBean) this);
		consumer1.registerObserver((ObserverRequestBean) this);
		LOGGER.info("Init completed");
	}
	
	public ResponseEntity<Long> post(String channel, List<Inner> input) {
		ObjectMapper mapper = new ObjectMapper();
		RequestBean bean = new RequestBean();
		bean.setList(input);

		try {
			SecureRandom rand = SecureRandom.getInstanceStrong();
			bean.setMessageId(rand.nextLong());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		switch(channel) {
		case "one":
			producer.sendMessage(jsonStr);
			break;
		case "two":
			producer2.sendMessage(jsonStr);
			break;
		default:
			throw new IllegalArgumentException("not found = "+channel);


		}
		waitForMessage2(bean.getMessageId());
		synchronized(dummy) {
			mq.responses.remove(bean.getMessageId());
			LOGGER.info("*remove responses count = "+mq.responses.size());
		}
		return ResponseEntity.ok(bean.getMessageId());

	}

	public ResponseEntity<List<Inner>> get(String dateStart, String dateEnd, Integer page, String channel) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		RequestBean bean = new RequestBean();
		bean.setDateStart(dateStart);
		bean.setDateEnd(dateEnd);
		bean.setPsge(page);
		ObjectMapper mapper = new ObjectMapper();

		try {
			SecureRandom rand = SecureRandom.getInstanceStrong();
			bean.setMessageId(rand.nextLong());
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		switch(channel) {
		case "one":
			producer.sendMessage(jsonStr);
			break;
		case "two":
			producer2.sendMessage(jsonStr);
			break;
		default:
			throw new IllegalArgumentException("not found = "+channel);


		}

		waitForMessage1(bean.getMessageId());
		List<Inner> results = mq.responses.get(bean.getMessageId()) != null ?
				mq.responses.get(bean.getMessageId()).getList() :
					new ArrayList<Inner>();
		List<Inner> resultsCopy = new ArrayList<Inner>();
		for (Inner item: results) {
			Inner temp = new Inner();
			temp.setCustomerId(item.getCustomerId());
			temp.setId(item.getId());
			temp.setUsedDate(item.getUsedDate());
			temp.setXmlData(item.getXmlData());
			resultsCopy.add(temp);
		}
		synchronized(dummy) {
			mq.responses.remove(bean.getMessageId());
			LOGGER.info("remove responses count = "+mq.responses.size());
		}
		return new ResponseEntity<List<Inner>>(resultsCopy, HttpStatus.OK);
	}

	private void waitForMessage1(Long messageId) {
		int i = 0;
		while (mq.messageIds.isEmpty() || !mq.messageIds.contains(messageId)) {
			if (i++ > 1000) break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		synchronized(dummy) {
			mq.messageIds.remove(messageId);
			LOGGER.info("*remove messageIds count = "+mq.messageIds.size());

		}
		return;
	}	
	private void waitForMessage2(Long messageId) {
		int i = 0;
		while (mq.messageIds.isEmpty() || !mq.messageIds.contains(messageId)) {
			if (i++ > 1000) break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		synchronized(dummy) {
			mq.messageIds.remove(messageId);
			LOGGER.info("remove messageIds count = "+mq.messageIds.size());
		}
		return;
	}

	@Override
	public synchronized void update(String str) {
		if (str == null) return;
		if (mq.messageIds != null && mq.messageIds.contains((Long)(Long.parseLong(str)))) return;
		mq.messageIds.add((Long)(Long.parseLong(str)));
		LOGGER.info("add messageIds count = "+mq.messageIds.size());
	}

	@Override
	public synchronized void update(RequestBean bean) {
		if (bean == null) return;
		if (mq.responses != null && mq.responses.containsKey(bean.getMessageId())) return;
		mq.responses.put(bean.getMessageId(), bean);
		LOGGER.info("put responses count = "+mq.responses.size());

	}


}

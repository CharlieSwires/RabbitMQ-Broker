package com.unicard.rabbit;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicard.rabbit.RequestBean.Inner;

@Service
public class RabbitService {
	@Autowired
	private RabbitMQProducer producer;

	@Autowired
	private RabbitMQProducer2 producer2;
	
	public AtomicLong messageId;
	public RequestBean response;
	
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
		int i = 0;
		while (!bean.getMessageId().equals((messageId != null ? messageId.get(): null))) {
			if (i++ > 1000) break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseEntity.ok(messageId != null ? messageId.get(): null);

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
		int i = 0;
		while (!bean.getMessageId().equals((messageId != null ? messageId.get(): null))) {
			if (i++ > 1000) break;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    	return new ResponseEntity<List<Inner>>(response.getList(), HttpStatus.OK);
	}

}

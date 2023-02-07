package com.unicard.rabbit;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unicard.rabbit.RequestBean.Inner;


@RestController
@RequestMapping(path = "/UniCard/api/v1")
public class RController  {

    Logger log = LoggerFactory.getLogger(RController.class);
    @Autowired
    private RabbitMQProducer producer;

    public String msg = "hello";
    
    public AtomicLong messageId = null;
    
    // http://localhost:9900/UniCard/api/v1/dataarray
    @PostMapping("/dataarray")
    public ResponseEntity<Long> post( @RequestBody List<Inner> input){
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
        producer.sendMessage(jsonStr);
        int i = 0;
//        try {
//			msg.wait();
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
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

}
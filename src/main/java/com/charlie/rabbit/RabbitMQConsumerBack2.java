package com.charlie.rabbit;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQConsumerBack2 implements Subject,SubjectRequestBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumerBack2.class);
   
    @RabbitListener(queues = {"${rabbitmq.queue4.name}"})
    public void consume(String message){

    	if (message != null) {
    		ObjectMapper mapper = new ObjectMapper();

    		RequestBean bean = null;
    		try {
    			bean = mapper.readValue(message, RequestBean.class);
    		} catch (JsonProcessingException e) {
    			e.printStackTrace();
    		}
    			setSubject(bean);
        		setSubject(bean.getMessageId().toString());
        		LOGGER.info(String.format("Received message -> %s", message));
        		LOGGER.info(String.format("observers count -> %s", observers.size()));
        		LOGGER.info(String.format("observersRB count -> %s", observersRB.size()));


    	}else {
            LOGGER.info(String.format("Received message -> null"));
    	}
    }

	private static ArrayList<Observer> observers = new ArrayList<Observer>();
	protected static String subject = null;
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
		
	}

	@Override
	public void notifyObservers() {
		for(Observer ob: observers) {
			ob.update(subject);
		}
		
	}
	public void setSubject(String sub) {
		if (sub == null) return;
		RabbitMQConsumerBack2.subject =sub;
		notifyObservers();
	}
	
	private static ArrayList<ObserverRequestBean> observersRB = new ArrayList<ObserverRequestBean>();
	protected static RequestBean subjectRB = null;

	@Override
	public void registerObserver(ObserverRequestBean o) {
		observersRB.add(o);
		
	}

	@Override
	public void removeObserver(ObserverRequestBean o) {
		observersRB.remove(o);
		
	}

	@Override
	public void notifyObserversRequestBean() {
		for(ObserverRequestBean ob: observersRB) {
			ob.update(subjectRB);
		}
		
	}
	public void setSubject(RequestBean sub) {
		if (sub == null) return;
		RabbitMQConsumerBack2.subjectRB =sub;
		notifyObserversRequestBean();
	}

}
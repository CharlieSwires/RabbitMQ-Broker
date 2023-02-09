package com.unicard.rabbit;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicard.rabbit.RequestBean.Inner;


@RestController
@RequestMapping(path = "/UniCard/api/v1")
public class RController  {

	Logger log = LoggerFactory.getLogger(RController.class);


	@Autowired
	private RabbitService service;

	public String msg = "hello";

	// http://localhost:9900/UniCard/api/v1/dataarray
	@PostMapping("/dataarray/{channel}")
	public ResponseEntity<Long> post(@PathVariable("channel") String channel, @RequestBody List<Inner> input){
		ResponseEntity<Long> result = service.post(channel,input);
		return result;
	}    
    @GetMapping(path="/dataarray/{dateStart}/{dateEnd}/{page}/{channel}", produces="application/json")
    public ResponseEntity<List<Inner>> getAllArray(@PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd,@PathVariable("page") Integer page, @PathVariable("channel") String channel) throws Exception {
    	ResponseEntity<List<Inner>> result = service.get(dateStart, dateEnd, page, channel);
		
		return result;
    }

}
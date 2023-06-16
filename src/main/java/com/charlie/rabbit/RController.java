package com.charlie.rabbit;

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

import com.charlie.rabbit.RequestBean.Inner;


@RestController
@RequestMapping(path = "/Charlie/api/v1")
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
    @GetMapping(path="/dataarray/{channel}/{dstart}/{dend}/{page}", produces="application/json")
    public ResponseEntity<List<Inner>> getAllArray(@PathVariable("channel") String channel, @PathVariable("dstart") String dstart,@PathVariable("dend") String dend,@PathVariable("page") String page) throws Exception {
    	ResponseEntity<List<Inner>> result = service.get(channel, "/"+dstart+"/"+dend+"/"+page);
		
		return result;
    }

}
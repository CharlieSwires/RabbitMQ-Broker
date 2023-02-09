package com.unicard.rabbit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class MessagesQueues {

	public Set<Long> messageIds = new HashSet<Long>();
	public Map<Long, RequestBean> responses = new HashMap<Long, RequestBean>();

}

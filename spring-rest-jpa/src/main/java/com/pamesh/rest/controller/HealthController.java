package com.pamesh.rest.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HealthController {

	
	@Autowired
	private ObjectMapper mapper;
	
	@GetMapping("/health")
	public ResponseEntity<String> health() throws UnknownHostException{
		log.info("Health check at {}", new Date());
		ObjectNode node = mapper.createObjectNode();
		node.put("systemName",InetAddress.getLocalHost().getHostName());
		node.put("time",String.valueOf(new Date()));
		return ResponseEntity.ok(node.toString());
	}
}

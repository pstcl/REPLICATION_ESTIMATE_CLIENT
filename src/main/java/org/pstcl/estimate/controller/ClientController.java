package org.pstcl.estimate.controller;

import org.pstcl.estimate.client.UtilService;
import org.pstcl.estimate.entity.Estimate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@Autowired
	private UtilService utilService;
	
	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/latestUpdate") 
	public Estimate getLatestUpdated()
	{
		return null;
		
		
	}
	
	 @Autowired 
	 private TaskScheduler taskScheduler;


	
	@CrossOrigin(allowCredentials="true")
	@GetMapping(value = "/refresh") 
	public void refresh()
	{
	
		utilService.clientHttpRequest();
	}
	
}

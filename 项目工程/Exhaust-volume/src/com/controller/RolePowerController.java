package com.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.service.RolePowerService;

@Controller
public class RolePowerController {

	@Resource
	private RolePowerService rolePowerService;
	
	
}

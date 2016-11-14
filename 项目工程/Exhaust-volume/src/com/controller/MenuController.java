package com.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.service.MenuService;

@Controller
@RequestMapping("menu")
public class MenuController {

	@Resource
	private MenuService menuService;
	
	
}

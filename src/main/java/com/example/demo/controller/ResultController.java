package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin    //跨域
@RestController
@RequestMapping("/Result")
public class ResultController {
	@Autowired
	ResultService service;

	@PostMapping(value = "/selectResult")
	public List<Result> selectResult(String stuNum){
		return service.selectResult(stuNum);
	}

}

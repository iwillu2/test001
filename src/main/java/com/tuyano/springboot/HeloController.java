package com.tuyano.springboot;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tuyano.springboot.repositories.MyDataRepository;

@Controller
public class HeloController {
	
	@Autowired
	MyDataRepository repository;
	
	@PostConstruct
	public void init() {
		//1つめ
		MyData d1 = new MyData();
		d1.setName("tuyano");
		d1.setAge(123);
		d1.setMail("syoda@tuyano.com");
		d1.setMemo("this is my data!");
		repository.saveAndFlush(d1);
		//2つめ
		MyData d2 = new MyData();
		d2.setName("hanako");
		d2.setAge(15);
		d2.setMail("hanako@flower");
		d2.setMemo("my girl friend.");
		repository.saveAndFlush(d2);
		//2つめ
		MyData d3 = new MyData();
		d3.setName("sachiko");
		d3.setAge(37);
		d3.setMail("sachiko@happy");
		d3.setMemo("my work friend.");
		repository.saveAndFlush(d3);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView index( @ModelAttribute("formModel") MyData mydata,@PathVariable int id, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","this is sample content.");
		mav.addObject("formModel",mydata);
		Optional<MyData>  data = repository.findById((long)id);
		mav.addObject("object", data.get());
		Iterable<MyData> list = repository.findAll();
		mav.addObject("datalist",list);
		mav.addObject("id",id);
		mav.addObject("check",id % 2==0);
		mav.addObject("trueVal", "Even number!");
		mav.addObject("falseVal", "Odd number!");
		return mav;
	}
	
	@RequestMapping(value = "/", method= RequestMethod.POST)
	@Transactional()
	public ModelAndView form(
		@ModelAttribute("formModel")
		@Validated MyData mydata,
		BindingResult result,
		ModelAndView mov) {
		ModelAndView res = null;
		if (!result.hasErrors()) {
			repository.saveAndFlush(mydata);
		    return new ModelAndView("redirect:/");
		} else {
			mov.setViewName("index");
			mov.addObject("msg","Sorry, error is occured...");
			Iterable<MyData> list = repository.findAll();
			mov.addObject("datalist",list);
			res = mov;
		}
		return res;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute("formModel") MyData mydata,
		@PathVariable int id, ModelAndView mav ) {
		mav.setViewName("edit");
		mav.addObject("title","edit mydata.");
		Optional<MyData>  data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(	@PathVariable int id, ModelAndView mav ) {
		mav.setViewName("delete");
		mav.addObject("title","delete mydata.");
		Optional<MyData> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@Transactional()
	public ModelAndView update(@ModelAttribute("formModel") MyData mydata, ModelAndView mav) {
		repository.saveAndFlush(mydata);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@Transactional()
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
	}

}

class DataObject {
	private int id;
	private String name;
	private String value;
	
	public DataObject(int id, String name, String value) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() {return name; }
	public void setName(String name) { this.name = name; }

	public String getValue() {return value; }
	public void setValue(String value) { this.value = value; }

	
}
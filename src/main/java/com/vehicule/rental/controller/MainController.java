package com.vehicule.rental.controller;

import com.vehicule.rental.model.ClientList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)

    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("index");
//        model.addAttribute("message", message);
        return modelAndView;
        //return "index";
    }

    @RequestMapping(value = { "/clientsList" }, method = RequestMethod.GET)
    public String clientsList(Model model) {

        ClientList[] clientLists = restTemplate.getForObject("http://172.22.119.129:8083/clientList/", ClientList[].class);
        model.addAttribute("clientlists", clientLists);

        return "clientsList";
    }
}

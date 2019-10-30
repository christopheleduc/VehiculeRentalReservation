package com.vehicule.rental.controller;

import com.vehicule.rental.form.ClientForm;
import com.vehicule.rental.model.ClientList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Api( description = "API pour les opérations CRUD sur la partie front.")
@Controller
public class MainController {

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    private RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "Page d'accueil !")
    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)

    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);
        modelAndView.setViewName("index");
//        model.addAttribute("message", message);
        return modelAndView;
        //return "index";
    }

    @ApiOperation(value = "Récupère la liste des clients !")
    @RequestMapping(value = { "/clientsList" }, method = RequestMethod.GET)
    public String clientsList(Model model) {

        ClientList[] clientLists = restTemplate.getForObject("http://172.22.119.129:8083/clientList/", ClientList[].class);
        model.addAttribute("clientlists", clientLists);

        return "clientsList";
    }

    @ApiOperation(value = "Page d'ajout clients !")
    @RequestMapping(value = { "/addClient" }, method = RequestMethod.GET)
    public String showAddClientPage(Model model) {

        ClientForm clientForm = new ClientForm();
        model.addAttribute("clientForm", clientForm);

        return "addClient";
    }

    @ApiOperation(value = "Ajoute un client à la liste des clients !")
    @RequestMapping(value = { "/addClient" }, method = RequestMethod.POST)
    public String saveClient(Model model, //
                          @ModelAttribute("clientForm") ClientForm clientForm) {

        String firstName = clientForm.getFirstName();
        String lastName = clientForm.getLastName();

        if (firstName != null && firstName.length() > 0 //
                && lastName != null && lastName.length() > 0) {
            ClientList newClientList = new ClientList(firstName, lastName);
            restTemplate.postForObject("http://172.22.119.129:8083/addNew/", newClientList, ClientList.class);

            return "redirect:/clientsList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addClient";
    }
}

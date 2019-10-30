package com.vehicule.rental.web.controller;

import com.vehicule.rental.model.ClientList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api( description = "API pour les opérations CRUD sur la liste des clients, au niveau de l'API distante.")
@RestController
public class ClientListController {

    private RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "Récupère la liste des clients à partir de l'API distante !")
    @RequestMapping( value= "/Reservations/ClientsList", method=RequestMethod.GET )
    public ClientList[] listClients(Model model){
            ClientList[] clientLists = restTemplate.getForObject("http://172.22.119.129:8083/clientList/", ClientList[].class);
//            model.addAttribute("clientLists", clientLists);

        return clientLists;
    }

}

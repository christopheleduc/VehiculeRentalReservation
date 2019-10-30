package com.vehicule.rental.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.vehicule.rental.dao.ReservationDao;
import com.vehicule.rental.model.Reservation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Api( description = "API pour les opérations CRUD de réservation.")
//@Controller
@RestController
public class ReservationController {

    @Autowired
    private ReservationDao reservationDao;
    private RestTemplate restTemplate = new RestTemplate();

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    // Liste des réservations
    @ApiOperation(value = "Récupère une liste d'ID !")
    @RequestMapping( value= "/Reservations", method=RequestMethod.GET )
    public MappingJacksonValue listingReservations() {

        Iterable<Reservation> reservations = reservationDao.findAll();

        SimpleBeanPropertyFilter myMask = SimpleBeanPropertyFilter.serializeAllExcept("buyPrice", "id");

        FilterProvider listMasks = new SimpleFilterProvider().addFilter("dynamicMask", myMask);
        MappingJacksonValue productsMask = new MappingJacksonValue(reservations);
        productsMask.setFilters(listMasks);

        return productsMask;
    }

    // Reservations par ID
    @ApiOperation(value = "Récupère une réservation grâce à son ID à condition que celui-ci existe!")
    @GetMapping(value = "/Reservations/{id}")
    public Reservation showReservation( @PathVariable int id ) {

        return reservationDao.findById(id);
    }

    //ajouter une reservation
    @ApiOperation(value = "Permet d'jouter une reservation à la liste !")
    @PostMapping(value = "/Reservations")
    public ResponseEntity<Void> addReservation(@RequestBody Reservation reservation) {

        Reservation reservationAdded = reservationDao.save(reservation);
        if (reservationAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
        //public void addProduct(@RequestBody Product product) {
        //productDao.save(product);
    }

    @ApiOperation(value = "Permet de supprimer une reservation de la liste !")
    @DeleteMapping (value = "/Reservations/{id}")
    public void delReservation(@PathVariable int id) {

        reservationDao.deleteById(id);
    }

    @ApiOperation(value = "Permet de modifier une reservation de la liste !")
    @PutMapping (value = "/Reservations")
    public void updateReservation(@RequestBody Reservation reservation) {

        reservationDao.save(reservation);
    }

    // Liste des réservations en passant par l'API distante
    @ApiOperation(value = "Récupère une liste d'ID en passant par une API distante !")
    @RequestMapping( value= "/Reservations/List", method=RequestMethod.GET )
    public Reservation[] clientsList(Model model) {
        Reservation[] reservations = restTemplate.getForObject("http://172.22.119.129:8083/clientList/", Reservation[].class);
        model.addAttribute("reservations", reservations);

        return reservations;
    }
}

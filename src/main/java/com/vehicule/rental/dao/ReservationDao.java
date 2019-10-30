package com.vehicule.rental.dao;

import com.vehicule.rental.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {
    Reservation findById(int id);
    //List<Reservation> findByPriceGreaterThan(int priceLimit);
    //List<Reservation> findByNameLike(String search);
}

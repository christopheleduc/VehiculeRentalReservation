package com.vehicule.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Reservation implements java.io.Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Basic(optional = false)
    @Column(nullable = false, name = "id_client")
    private int idClient;

    @Basic(optional = false)
    @Column(nullable = false, name = "id_vehicule")
    private int idVehicule;

    public  Reservation() {

    }

    public Reservation(int idClient, int idVehicule) {
        this.idClient = idClient;
        this.idVehicule = idVehicule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idVehicule=" + idVehicule +
                '}';
    }

}

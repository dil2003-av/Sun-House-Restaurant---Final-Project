package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Reservationsdto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBo {

    String getNextReservationId() throws SQLException, ClassNotFoundException;

    boolean saveReservation(Reservationsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Reservationsdto> getAllReservations() throws SQLException, ClassNotFoundException;

    boolean updateReservation(Reservationsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteReservation(String reservationId) throws SQLException, ClassNotFoundException;
}

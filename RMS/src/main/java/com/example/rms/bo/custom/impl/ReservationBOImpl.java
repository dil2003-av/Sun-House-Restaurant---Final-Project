package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.ReservationBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.ReservationDAO;
import com.example.rms.dto.Reservationsdto;
import com.example.rms.entity.Reservations;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {

    ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.RESERVATION);

    @Override
    public String getNextReservationId() throws SQLException, ClassNotFoundException {
        return reservationDAO.getNextId();
    }

    @Override
    public boolean saveReservation(Reservationsdto dto) throws SQLException, ClassNotFoundException {
        Reservations reservation = new Reservations(
                dto.getReservationId(),
                dto.getCustomerId(),
                dto.getReservationDate(),
                dto.getNumberOfGuests(),
                dto.getSpecialRequest(),
                dto.getStatus()
        );
        return reservationDAO.save(reservation);
    }

    @Override
    public ArrayList<Reservationsdto> getAllReservations() throws SQLException, ClassNotFoundException {
        ArrayList<Reservations> reservationsList = reservationDAO.getAll();
        ArrayList<Reservationsdto> reservationsDtos = new ArrayList<>();

        for (Reservations reservation : reservationsList) {
            reservationsDtos.add(new Reservationsdto(
                    reservation.getReservationId(),
                    reservation.getCustomerId(),
                    reservation.getReservationDate(),
                    reservation.getNumberOfGuests(),
                    reservation.getSpecialRequest(),
                    reservation.getStatus()
            ));
        }
        return reservationsDtos;
    }

    @Override
    public boolean updateReservation(Reservationsdto dto) throws SQLException, ClassNotFoundException {
        Reservations reservation = new Reservations(
                dto.getReservationId(),
                dto.getCustomerId(),
                dto.getReservationDate(),
                dto.getNumberOfGuests(),
                dto.getSpecialRequest(),
                dto.getStatus()
        );
        return reservationDAO.update(reservation);
    }

    @Override
    public boolean deleteReservation(String reservationId) throws SQLException, ClassNotFoundException {
        reservationDAO.delete(reservationId);
        return true;
    }
}

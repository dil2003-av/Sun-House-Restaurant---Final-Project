package com.example.rms.model;

import com.example.rms.dto.Reservationsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationsModel {

    // Get the next reservation ID
    public static String getNextReservationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT ReservationID FROM reservations ORDER BY ReservationID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("R%03d", nextId);
        }
        return "R001";
    }

    // Save a reservation
    public static boolean saveReservation(Reservationsdto reservation) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO reservations (ReservationID, CustomerID, ReservationDate, NumberOfGuests, SpecialRequests, Status) VALUES (?, ?, ?, ?, ?, ?)",
                reservation.getReservationId(),
                reservation.getCustomerId(),
                reservation.getReservationDate(),
                reservation.getNumberOfGuests(),
                reservation.getSpecialRequest(),
                reservation.getStatus()
        );
    }

    // Retrieve all reservations
    public static ArrayList<Reservationsdto> getAllReservations() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservations");
        ArrayList<Reservationsdto> reservationsList = new ArrayList<>();

        while (rst.next()) {
            reservationsList.add(new Reservationsdto(
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getDate("ReservationDate"),
                    rst.getString("NumberOfGuests"),
                    rst.getString("SpecialRequests"),
                    rst.getString("Status")
            ));
        }
        return reservationsList;
    }

    // Update a reservation
    public static boolean updateReservation(Reservationsdto reservation) throws SQLException {
        return CrudUtil.execute(
                "UPDATE reservations SET CustomerID=?, ReservationDate=?, NumberOfGuests=?, SpecialRequests=?, Status=? WHERE ReservationID=?",
                reservation.getCustomerId(),
                reservation.getReservationDate(),
                reservation.getNumberOfGuests(),
                reservation.getSpecialRequest(),
                reservation.getStatus(),
                reservation.getReservationId()
        );
    }

    // Delete a reservation
    public static boolean deleteReservation(String reservationId) throws SQLException {
        return CrudUtil.execute("DELETE FROM reservations WHERE ReservationID=?", reservationId);
    }

    // Search for a reservation by ID
    public static Reservationsdto searchReservation(String reservationId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservations WHERE ReservationID=?", reservationId);

        if (rst.next()) {
            return new Reservationsdto(
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getDate("ReservationDate"),
                    rst.getString("NumberOfGuests"),
                    rst.getString("SpecialRequests"),
                    rst.getString("Status")
            );
        }
        return null;
    }
}

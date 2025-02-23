package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.ReservationDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Reservations;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public ArrayList<Reservations> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservations");
        ArrayList<Reservations> reservationsList = new ArrayList<>();

        while (rst.next()) {
            Reservations reservation = new Reservations(
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getDate("ReservationDate"),
                    rst.getString("NumberOfGuests"),
                    rst.getString("SpecialRequests"),
                    rst.getString("Status")
            );
            reservationsList.add(reservation);
        }
        return reservationsList;
    }



    @Override
    public boolean save(Reservations reservationEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO reservations (ReservationID, CustomerID, ReservationDate, NumberOfGuests, SpecialRequests, Status) VALUES (?, ?, ?, ?, ?, ?)",
                reservationEntity.getReservationId(),
                reservationEntity.getCustomerId(),
                reservationEntity.getReservationDate(),
                reservationEntity.getNumberOfGuests(),
                reservationEntity.getSpecialRequest(),
                reservationEntity.getStatus()
        );
    }

    @Override
    public boolean update(Reservations reservationEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE reservations SET CustomerID=?, ReservationDate=?, NumberOfGuests=?, SpecialRequests=?, Status=? WHERE ReservationID=?",
                reservationEntity.getCustomerId(),
                reservationEntity.getReservationDate(),
                reservationEntity.getNumberOfGuests(),
                reservationEntity.getSpecialRequest(),
                reservationEntity.getStatus(),
                reservationEntity.getReservationId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT ReservationID FROM reservations WHERE ReservationID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String reservationId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM reservations WHERE ReservationID=?", reservationId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT ReservationID FROM reservations ORDER BY ReservationID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "R001"
            String numericPart = lastId.substring(1); // Remove the prefix letter 'R'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", nextId);
        }
        return "R001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<Reservations> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reservations WHERE ReservationID=?", newValue);
        ArrayList<Reservations> searchResults = new ArrayList<>();

        while (rst.next()) {
            Reservations reservation = new Reservations(
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getDate("ReservationDate"),
                    rst.getString("NumberOfGuests"),
                    rst.getString("SpecialRequests"),
                    rst.getString("Status")
            );
            searchResults.add(reservation);
        }
        return searchResults;
    }
}

package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.PaymentsDAO;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.entity.Payments;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentsDAOImpl implements PaymentsDAO {

    @Override
    public ArrayList<Payments> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM payments");
        ArrayList<Payments> paymentsList = new ArrayList<>();

        while (rst.next()) {
            paymentsList.add(new Payments(
                    rst.getString("PaymentID"),
                    rst.getString("PaymentMethod"),
                    rst.getDouble("Amount"),
                    rst.getDate("PaymentDate")
            ));
        }
        return paymentsList;
    }

    @Override
    public boolean save(Payments payment) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO payments (PaymentID, PaymentMethod, Amount, PaymentDate) VALUES (?, ?, ?, ?)",
                payment.getPaymentId(),
                payment.getPaymentMethod(),
                payment.getPaymentAmount(),
                payment.getPaymentDate()
        );
    }

    @Override
    public boolean update(Payments payment) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE payments SET PaymentMethod=?, Amount=?, PaymentDate=? WHERE PaymentID=?",
                payment.getPaymentMethod(),
                payment.getPaymentAmount(),
                payment.getPaymentDate(),
                payment.getPaymentId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String paymentId) throws SQLException, ClassNotFoundException {
         SQLUtill.execute("DELETE FROM payments WHERE PaymentID=?", paymentId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT PaymentID FROM payments ORDER BY PaymentID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("P%03d", nextId);
        }
        return "P001";
    }

    @Override
    public  ArrayList<Payments> search(String newValue) throws SQLException,ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM payments WHERE PaymentID=?", newValue);

//        if (rst.next()) {
//            return new Payments(
//                    rst.getString("PaymentID"),
//                    rst.getString("PaymentMethod"),
//                    rst.getDouble("Amount"),
//                    rst.getDate("PaymentDate")
//            );
//        }
//        return null;
//    }
        ArrayList<Payments> paymentsList = new ArrayList<>();
        while (rst.next()) {
            Payments payments = new Payments(
                    rst.getString("PaymentID"),
                   rst.getString("PaymentMethod"),
                  rst.getDouble("Amount"),
                   rst.getDate("PaymentDate")
            );
            paymentsList.add(payments);
        }
        return paymentsList;
    }
}

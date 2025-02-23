package com.example.rms.model;

import com.example.rms.dto.Paymentsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentsModel {

    // Get the next payment ID
    public static String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PaymentID FROM payments ORDER BY PaymentID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("P%03d", nextId);
        }
        return "P001";
    }

//    // Save a payment
//    public static boolean savePayment(Paymentsdto payment) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO payments (PaymentID, PaymentMethod, Amount, PaymentDate) VALUES (?, ?, ?, ?)",
//                payment.getPaymentId(),
//                payment.getPaymentMethod(),
//                payment.getPaymentAmount(),
//                payment.getPaymentDate()
//        );
//    }
//
//    // Retrieve all payments
//    public static ArrayList<Paymentsdto> getAllPayments() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM payments");
//        ArrayList<Paymentsdto> paymentsList = new ArrayList<>();
//
//        while (rst.next()) {
//            paymentsList.add(new Paymentsdto(
//                    rst.getString("PaymentID"),
//                    rst.getString("PaymentMethod"),
//                    rst.getDouble("Amount"),
//                    rst.getDate("PaymentDate")
//            ));
//        }
//        return paymentsList;
//    }
//
//    // Update a payment
//    public static boolean updatePayment(Paymentsdto payment) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE payments SET PaymentMethod=?, Amount=?, PaymentDate=? WHERE PaymentID=?",
//                payment.getPaymentMethod(),
//                payment.getPaymentAmount(),
//                payment.getPaymentDate(),
//                payment.getPaymentId()
//        );
//    }
//
//    // Delete a payment
//    public static boolean deletePayment(String paymentId) throws SQLException {
//        return CrudUtil.execute("DELETE FROM payments WHERE PaymentID=?", paymentId);
//    }

    // Search for a payment by ID
    public static Paymentsdto searchPayment(String paymentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM payments WHERE PaymentID=?", paymentId);

        if (rst.next()) {
            return new Paymentsdto(
                    rst.getString("PaymentID"),
                    rst.getString("PaymentMethod"),
                    rst.getDouble("Amount"),
                    rst.getDate("PaymentDate")
            );
        }
        return null;
    }
}

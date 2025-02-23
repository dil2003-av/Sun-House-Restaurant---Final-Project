package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Paymentsdto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBo {
    String getNextPaymentId() throws SQLException, ClassNotFoundException;

    boolean savePayment(Paymentsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Paymentsdto> getAllPayments() throws SQLException, ClassNotFoundException;

    boolean updatePayment(Paymentsdto dto) throws SQLException, ClassNotFoundException;

    boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException;

    ArrayList<Paymentsdto> searchPayment(String newValue) throws SQLException, ClassNotFoundException;
}

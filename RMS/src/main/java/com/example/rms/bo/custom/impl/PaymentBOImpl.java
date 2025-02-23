package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.PaymentBO;
//import com.example.rms.bo.custom.PaymentsBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.PaymentsDAO;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.entity.Payments;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentsDAO paymentsDAO = (PaymentsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENTS);

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentsDAO.getNextId();
    }

    @Override
    public boolean savePayment(Paymentsdto dto) throws SQLException, ClassNotFoundException {
        Payments payment = new Payments(dto.getPaymentId(), dto.getPaymentMethod(), dto.getPaymentAmount(), dto.getPaymentDate());
        return paymentsDAO.save(payment);
    }

    @Override
    public ArrayList<Paymentsdto> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<Payments> paymentsList = paymentsDAO.getAll();
        ArrayList<Paymentsdto> paymentsDtos = new ArrayList<>();

        for (Payments payment : paymentsList) {
            paymentsDtos.add(new Paymentsdto(
                    payment.getPaymentId(),
                    payment.getPaymentMethod(),
                    payment.getPaymentAmount(),
                    payment.getPaymentDate()
            ));
        }
        return paymentsDtos;
    }

    @Override
    public boolean updatePayment(Paymentsdto dto) throws SQLException, ClassNotFoundException {
        Payments payment = new Payments(dto.getPaymentId(), dto.getPaymentMethod(), dto.getPaymentAmount(), dto.getPaymentDate());
        return paymentsDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        paymentsDAO.delete(paymentId);
        return true;
    }

    @Override
    public ArrayList<Paymentsdto> searchPayment(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Payments> payment = paymentsDAO.search(newValue);
        ArrayList<Paymentsdto> paymentsdtos = new ArrayList<>();

        for (Payments payments : payment) {
            Paymentsdto paymentsdto = new Paymentsdto();
            paymentsdto.setPaymentId(payments.getPaymentId());
            paymentsdto.setPaymentMethod(payments.getPaymentMethod());
            paymentsdto.setPaymentAmount(payments.getPaymentAmount());
            paymentsdto.setPaymentDate(payments.getPaymentDate());
            paymentsdtos.add(paymentsdto);  // Fixed: Add single object, not list
        }

        return paymentsdtos;
    }
}

package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Reviewsdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReviewsBO extends SuperBo {
    String getNextReviewId() throws SQLException, ClassNotFoundException;

    boolean saveReview(Reviewsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Reviewsdto> getAllReviews() throws SQLException, ClassNotFoundException;

    boolean updateReview(Reviewsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteReview(String reviewId) throws SQLException, ClassNotFoundException;
}

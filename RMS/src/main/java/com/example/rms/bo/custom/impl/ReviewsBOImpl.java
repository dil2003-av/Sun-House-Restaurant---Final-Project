package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.ReviewsBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.ReviewsDAO;
import com.example.rms.dto.Reviewsdto;
import com.example.rms.entity.Reviews;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewsBOImpl implements ReviewsBO {

    ReviewsDAO reviewsDAO = (ReviewsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.REVIEWS);

    @Override
    public String getNextReviewId() throws SQLException, ClassNotFoundException {
        return reviewsDAO.getNextId();
    }

    @Override
    public boolean saveReview(Reviewsdto dto) throws SQLException, ClassNotFoundException {
        Reviews review = new Reviews(dto.getReviewId(), dto.getCustomerId(), dto.getMenuItemId(), dto.getRatings(), dto.getComments(), dto.getReviewDate());
        return reviewsDAO.save(review);
    }

    @Override
    public ArrayList<Reviewsdto> getAllReviews() throws SQLException, ClassNotFoundException {
        ArrayList<Reviews> reviewsList = reviewsDAO.getAll();
        ArrayList<Reviewsdto> reviewsDtos = new ArrayList<>();

        for (Reviews review : reviewsList) {
            reviewsDtos.add(new Reviewsdto(review.getReviewId(), review.getCustomerId(), review.getMenuItemId(), review.getRatings(), review.getComments(), review.getReviewDate()));
        }
        return reviewsDtos;
    }

    @Override
    public boolean updateReview(Reviewsdto dto) throws SQLException, ClassNotFoundException {
        Reviews review = new Reviews(dto.getReviewId(), dto.getCustomerId(), dto.getMenuItemId(), dto.getRatings(), dto.getComments(), dto.getReviewDate());
        return reviewsDAO.update(review);
    }

    @Override
    public boolean deleteReview(String reviewId) throws SQLException, ClassNotFoundException {
        reviewsDAO.delete(reviewId);
        return true;
    }

}

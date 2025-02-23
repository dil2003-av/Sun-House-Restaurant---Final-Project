package com.example.rms.model;

import com.example.rms.dto.Reviewsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewsModel {

//    // Get the next review ID
//    public static String getNextReviewId() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT ReviewID FROM reviews ORDER BY ReviewID DESC LIMIT 1");
//
//        if (rst.next()) {
//            String lastId = rst.getString(1).substring(1);
//            int nextId = Integer.parseInt(lastId) + 1;
//            return String.format("R%03d", nextId);
//        }
//        return "R001";
//    }
//
//    // Save a review
//    public static boolean saveReview(Reviewsdto review) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO reviews (ReviewID, CustomerID, MenuItemID, Rating, Comments, ReviewDate) VALUES (?, ?, ?, ?, ?, ?)",
//                review.getReviewId(),
//                review.getCustomerId(),
//                review.getMenuItemId(),
//                review.getRatings(),
//                review.getComments(),
//                review.getReviewDate()
//        );
//    }
//
//    // Retrieve all reviews
//    public static ArrayList<Reviewsdto> getAllReviews() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM reviews");
//        ArrayList<Reviewsdto> reviewsList = new ArrayList<>();
//
//        while (rst.next()) {
//            reviewsList.add(new Reviewsdto(
//                    rst.getString("ReviewID"),
//                    rst.getString("CustomerID"),
//                    rst.getString("MenuItemID"),
//                    rst.getString("Rating"),
//                    rst.getString("Comments"),
//                    rst.getDate("ReviewDate")
//            ));
//        }
//        return reviewsList;
//    }
//
//    // Update a review
//    public static boolean updateReview(Reviewsdto review) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE reviews SET CustomerID=?, MenuItemID=?, Rating=?, Comments=?, ReviewDate=? WHERE ReviewID=?",
//                review.getCustomerId(),
//                review.getMenuItemId(),
//                review.getRatings(),
//                review.getComments(),
//                review.getReviewDate(),
//                review.getReviewId()
//        );
//    }
//
//    // Delete a review
//    public static boolean deleteReview(String reviewId) throws SQLException {
//        return CrudUtil.execute("DELETE FROM reviews WHERE ReviewID=?", reviewId);
//    }

    // Search for a review by ID
    public static Reviewsdto searchReview(String reviewId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reviews WHERE ReviewID=?", reviewId);

        if (rst.next()) {
            return new Reviewsdto(
                    rst.getString("ReviewID"),
                    rst.getString("CustomerID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Rating"),
                    rst.getString("Comments"),
                    rst.getDate("ReviewDate")
            );
        }
        return null;
    }
}

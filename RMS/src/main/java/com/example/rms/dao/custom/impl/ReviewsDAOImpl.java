package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
//import com.example.rms.dao.custom.ReviewDAO;
import com.example.rms.dao.custom.ReviewsDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Reviews;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewsDAOImpl implements ReviewsDAO {

    @Override
    public ArrayList<Reviews> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reviews");
        ArrayList<Reviews> reviewsList = new ArrayList<>();

        while (rst.next()) {
            Reviews review = new Reviews(
                    rst.getString("ReviewID"),
                    rst.getString("CustomerID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Rating"),
                    rst.getString("Comments"),
                    rst.getDate("ReviewDate")
            );
            reviewsList.add(review);
        }
        return reviewsList;
    }



    @Override
    public boolean save(Reviews reviewEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO reviews (ReviewID, CustomerID, MenuItemID, Rating, Comments, ReviewDate) VALUES (?, ?, ?, ?, ?, ?)",
                reviewEntity.getReviewId(),
                reviewEntity.getCustomerId(),
                reviewEntity.getMenuItemId(),
                reviewEntity.getRatings(),
                reviewEntity.getComments(),
                reviewEntity.getReviewDate()
        );
    }

    @Override
    public boolean update(Reviews reviewEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE reviews SET CustomerID=?, MenuItemID=?, Rating=?, Comments=?, ReviewDate=? WHERE ReviewID=?",
                reviewEntity.getCustomerId(),
                reviewEntity.getMenuItemId(),
                reviewEntity.getRatings(),
                reviewEntity.getComments(),
                reviewEntity.getReviewDate(),
                reviewEntity.getReviewId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT ReviewID FROM reviews WHERE ReviewID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String reviewId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM reviews WHERE ReviewID=?", reviewId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT ReviewID FROM reviews ORDER BY ReviewID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "R001"
            String numericPart = lastId.substring(1); // Remove the prefix letter 'R'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("R%03d", nextId);
        }
        return "R001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<Reviews> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM reviews WHERE ReviewID=?", newValue);
        ArrayList<Reviews> searchResults = new ArrayList<>();

        while (rst.next()) {
            Reviews review = new Reviews(
                    rst.getString("ReviewID"),
                    rst.getString("CustomerID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Rating"),
                    rst.getString("Comments"),
                    rst.getDate("ReviewDate")
            );
            searchResults.add(review);
        }
        return searchResults;
    }
}

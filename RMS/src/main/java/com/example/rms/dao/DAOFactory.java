package com.example.rms.dao;

import com.example.rms.bo.custom.impl.OrderItemBOImpl;
import com.example.rms.dao.custom.impl.*;

public class DAOFactory implements SuperDAO {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public enum DAOType {
        CUSTOMER, EMPLOYEE, DELIVERY, INVENTORY_ITEM, MENU, MENU_ITEM_INGREDIANT, ORDER_ITEM,TABLES,USER,
       TABLE_ASSIGNMENT ,QUERY,SUPPLIERS,REVIEWS,RESERVATION,PURCHASE,PURCHASE_ITEMS,PAYMENTS,ORDERS
    }

    // Using Object as return type to accommodate different DAO implementations.
    public SuperDAO getDAO(DAOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case DELIVERY:
                return new DeliveriesDAOImpl();
            case INVENTORY_ITEM:
                return new InventoryItemsDAOImpl();
            case MENU:
                return new MenuDAOImpl();
            case MENU_ITEM_INGREDIANT:
                    return new MenuItemIngrediantsDAOImpl();
            case ORDER_ITEM:
                return new OrderItemDAOImpl();
                case TABLES:
                    return new TablesDAOImpl();
                    case USER:
                        return new UserDAOImpl();
                        case TABLE_ASSIGNMENT:
                            return new TableAssignmentsDAOImpl();
            case SUPPLIERS:
                return new SuppliersDAOImpl();
            case REVIEWS:
                return new ReviewsDAOImpl();
                case RESERVATION:
                    return new ReservationDAOImpl();
                    case PURCHASE:
                        return new PurchaseDAOImpl();
                        case PURCHASE_ITEMS:
                            return new PurchaseItemsDAOImpl();
                            case PAYMENTS:
                                return new PaymentsDAOImpl();
                                case ORDERS:
                                    return new OrderFormDAOImpl();


                            case QUERY:
                                return new QueryDaoImpl();

            default:
                return null;
        }
    }
}

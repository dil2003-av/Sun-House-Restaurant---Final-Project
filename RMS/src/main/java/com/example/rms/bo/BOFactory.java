package com.example.rms.bo;

import com.example.rms.bo.custom.impl.*;
import com.example.rms.dao.custom.impl.DeliveriesDAOImpl;
import com.example.rms.dao.custom.impl.MenuDAOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOType {
        CUSTOMER, EMPLOYEE, DELIVERY,INVENTORY_ITEM,MENU,MENU_ITEM_INGREDIANT,ORDER_ITEM,TABLES,USER,TABLE_ASSIGNMENT,
        SUPPLIERS,REVIEWS,RESERVATION,PURCHASE,PURCHASE_ITEMS,PAYMENTS,PLACE_ORDER
    }

    public SuperBo getBO(BOType type) {
        switch (type) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case DELIVERY:
                    return (SuperBo) new DeliveriesDAOImpl();
                    case INVENTORY_ITEM:
                        return new InventoryItemsBOImpl();
                        case MENU:
                            return (SuperBo) new MenuDAOImpl();
                            case MENU_ITEM_INGREDIANT:
                                return new MenuItemIngrediantsBOImpl();
                                case ORDER_ITEM:
                                    return new OrderItemBOImpl();
                                case USER:
                                    return new UserBOImpl();
                                    case TABLES:
                                  return new TablesBOImpl();
                                  case TABLE_ASSIGNMENT:
                                      return new TablesAssignmentBOImpl();
                                      case SUPPLIERS:
                                          return new SuppliersBOImpl();
                                          case REVIEWS:
                                              return new ReviewsBOImpl();
                                              case RESERVATION:
                                                  return new ReservationBOImpl();
                                                  case PURCHASE:
                                                      return new PurchaseBOImpl();
                                                      case PURCHASE_ITEMS:
                                                          return new PurchaseItemBOImpl();
                                                          case PAYMENTS:
                                                              return new PaymentBOImpl();
                                                              case PLACE_ORDER:
                                                                  return new PlaceOrderBOImpl();



            default:
                return null;
        }
    }
}


CREATE DATABASE RMS;

USE RMS;


-- Table: Employees
CREATE TABLE Employees (
    EmployeeID VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Position VARCHAR(50) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    HireDate DATE NOT NULL
);

-- Table: Users
CREATE TABLE Users (
    UserID VARCHAR(50) PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(50) NOT NULL,
    LoginTime DATETIME NOT NULL,
    EmployeeID VARCHAR(50) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: Customers
CREATE TABLE Customers (
    CustomerID VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    UserID VARCHAR(50) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: Suppliers
CREATE TABLE Suppliers (
    SupplierID VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    ContactPerson VARCHAR(100) NOT NULL,
    Phone VARCHAR(15) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    UserID VARCHAR(50) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: InventoryItems
CREATE TABLE InventoryItems (
    InventoryItemID VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Quantity INT NOT NULL,
    Unit VARCHAR(50) NOT NULL
);

-- Table: Purchases
CREATE TABLE Purchases (
    PurchaseID VARCHAR(50) PRIMARY KEY,
    SupplierID VARCHAR(50) NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    PurchaseDate DATE NOT NULL,
    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: PurchaseItems
CREATE TABLE PurchaseItems (
    PurchaseItemID VARCHAR(50) PRIMARY KEY,
    InventoryItemID VARCHAR(50) NOT NULL,
    PurchaseID VARCHAR(50) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Quantity INT NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (InventoryItemID) REFERENCES InventoryItems(InventoryItemID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (PurchaseID) REFERENCES Purchases(PurchaseID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: MenuItems
CREATE TABLE MenuItems (
    MenuItemID VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(255) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Category VARCHAR(50) NOT NULL,
    KitchenSection VARCHAR(50) NOT NULL
);

-- Table: Reservations
CREATE TABLE Reservations (
    ReservationID VARCHAR(50) PRIMARY KEY,
    CustomerID VARCHAR(50) NOT NULL,
    ReservationDate DATE NOT NULL,
    NumberOfGuests INT NOT NULL,
    SpecialRequests VARCHAR(255) NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: Tables
CREATE TABLE Tables (
    TableID VARCHAR(50) PRIMARY KEY,
    TableNumber INT NOT NULL,
    Capacity INT NOT NULL,
    Location VARCHAR(50) NOT NULL,
    Status VARCHAR(50) NOT NULL
);

-- Table: TableAssignments
CREATE TABLE TableAssignments (
	TableAssignmentID VARCHAR(50) PRIMARY KEY,
    TableID VARCHAR(50) NOT NULL,
    ReservationID VARCHAR(50) NOT NULL,
    AssignmentTime DATETIME NOT NULL,
    FOREIGN KEY (TableID) REFERENCES Tables(TableID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (ReservationID) REFERENCES Reservations(ReservationID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: Payments
CREATE TABLE Payments (
    PaymentID VARCHAR(50) PRIMARY KEY,
    PaymentMethod VARCHAR(50) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATE NOT NULL
);

-- Table: Orders
CREATE TABLE orders (
    OrderID VARCHAR(50) PRIMARY KEY,
    CustomerID VARCHAR(50) NOT NULL,
    OrderDate DATE NOT NULL,
    TotalAmount DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(50) NOT NULL,
    OrderType VARCHAR(50) NOT NULL,
    ReservationID VARCHAR(50) NULL,-- Optional field
    PaymentID VARCHAR(50) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (ReservationID) REFERENCES Reservations(ReservationID)
        ON DELETE SET NULL
        ON UPDATE CASCADE, -- If a reservation is deleted, set this to NULL
    FOREIGN KEY (PaymentID) REFERENCES Payments(PaymentID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: OrderItems
CREATE TABLE OrderItems (
    OrderItemID VARCHAR(50) PRIMARY KEY,
    OrderID VARCHAR(50) NOT NULL,
    MenuItemID VARCHAR(50) NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- Table: Deliveries
CREATE TABLE Deliveries (
    DeliveryID VARCHAR(50) PRIMARY KEY,
    OrderID VARCHAR(50) NOT NULL,
    DeliveryDate DATE NOT NULL,
    DeliveryStatus VARCHAR(50) NOT NULL,
    DeliveryAddress VARCHAR(255) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Table: Reviews
CREATE TABLE Reviews (
    ReviewID VARCHAR(50) PRIMARY KEY,
    CustomerID VARCHAR(50) NOT NULL,
    MenuItemID VARCHAR(50) NOT NULL,
    Rating INT NOT NULL,
    Comments VARCHAR(255) NULL,
    ReviewDate DATE NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (MenuItemID) REFERENCES MenuItems(MenuItemID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



show databases;

use RMS;

show tables;


INSERT INTO Employees (EmployeeID, Name, Position, Phone, Email, HireDate)
VALUES 
('E001', 'John Doe', 'Manager', '123-456-7890', 'jdoe@restaurant.com', '2022-01-15'),
('E002', 'Alice Smith', 'Chef', '123-456-7891', 'asmith@restaurant.com', '2021-06-10'),
('E003', 'Bob Johnson', 'Waiter', '123-456-7892', 'bjohnson@restaurant.com', '2023-03-22'),
('E004', 'Carol Williams', 'Cashier', '123-456-7893', 'cwilliams@restaurant.com', '2020-08-30'),
('E005', 'David Brown', 'Delivery Driver', '123-456-7894', 'drbrown@restaurant.com', '2023-11-05');

INSERT INTO Users (UserID, Username, Password, LoginTime, EmployeeID)
VALUES 
('U001', 'Manager', 'password123', '2024-09-01 08:00:00', 'E001'),
('U002', 'Cashier', 'password456', '2024-09-01 09:00:00', 'E004');


INSERT INTO Customers (CustomerID, Name, Phone, Email, Address, UserID)
VALUES 
('C001', 'Emma Thompson', '321-654-9870', 'ethompson@gmail.com', '123 Elm St', 'U002'),
('C002', 'Michael Green', '321-654-9871', 'mgreen@gmail.com', '456 Oak St', 'U002'),
('C003', 'Sophia White', '321-654-9872', 'swhite@gmail.com', '789 Pine St', 'U002'),
('C004', 'Liam Brown', '321-654-9873', 'lbrown@gmail.com', '101 Maple St', 'U002'),
('C005', 'Olivia Johnson', '321-654-9874', 'ojohnson@gmail.com', '202 Cedar St', 'U002');

INSERT INTO Suppliers (SupplierID, Name, ContactPerson, Phone, Email, Address, UserID)
VALUES 
('S001', 'Fresh Foods Inc.', 'William Hill', '987-654-3210', 'whill@freshfoods.com', '100 Market St', 'U001'),
('S002', 'Baker Supplies Ltd.', 'Sarah Carter', '987-654-3211', 'scarter@bakersupplies.com', '200 Market St', 'U001'),
('S003', 'Dairy Goods Co.', 'Emily Harris', '987-654-3212', 'eharris@dairygoods.com', '300 Market St', 'U001'),
('S004', 'Meat Distributors LLC', 'James King', '987-654-3213', 'jking@meatdistributors.com', '400 Market St', 'U001'),
('S005', 'Spice Traders', 'Olivia Baker', '987-654-3214', 'obaker@spicetraders.com', '500 Market St', 'U001');

INSERT INTO InventoryItems (InventoryItemID, Name, Description, Quantity, Unit)
VALUES 
('I001', 'Flour', 'All-purpose flour', 100, 'kg'),
('I002', 'Sugar', 'Granulated sugar', 50, 'kg'),
('I003', 'Eggs', 'Farm fresh eggs', 200, 'pcs'),
('I004', 'Butter', 'Unsalted butter', 30, 'kg'),
('I005', 'Milk', 'Whole milk', 40, 'L');

INSERT INTO Purchases (PurchaseID, SupplierID, TotalAmount, PurchaseDate)
VALUES 
('P001', 'S001', 500.00, '2024-09-10'),
('P002', 'S002', 300.00, '2024-09-11'),
('P003', 'S003', 200.00, '2024-09-12'),
('P004', 'S004', 700.00, '2024-09-13'),
('P005', 'S005', 150.00, '2024-09-14');

INSERT INTO PurchaseItems (PurchaseItemID, InventoryItemID, PurchaseID, Price, Quantity, Status)
VALUES 
('PI001', 'I001', 'P001', 2.50, 50, 'Received'),
('PI002', 'I002', 'P001', 3.00, 30, 'Received'),
('PI003', 'I003', 'P002', 0.10, 100, 'Pending'),
('PI004', 'I004', 'P003', 5.00, 20, 'Received'),
('PI005', 'I005', 'P004', 1.50, 40, 'Received');

INSERT INTO MenuItems (MenuItemID, Name, Description, Price, Category, KitchenSection)
VALUES 
('M001', 'Cheeseburger', 'Grilled beef patty with cheese', 8.99, 'Main Course', 'Grill'),
('M002', 'Caesar Salad', 'Fresh lettuce with Caesar dressing', 6.99, 'Appetizer', 'Salads'),
('M003', 'French Fries', 'Crispy potato fries', 3.99, 'Side Dish', 'Fryer'),
('M004', 'Chocolate Cake', 'Rich chocolate layer cake', 5.99, 'Dessert', 'Bakery'),
('M005', 'Iced Tea', 'Refreshing iced tea', 2.99, 'Beverage', 'Drinks');

INSERT INTO Reservations (ReservationID, CustomerID, ReservationDate, NumberOfGuests, SpecialRequests, Status)
VALUES 
('R001', 'C001', '2024-09-15', 4, 'Window seat', 'Confirmed'),
('R002', 'C002', '2024-09-16', 2, 'Birthday celebration', 'Pending'),
('R003', 'C003', '2024-09-17', 6, '', 'Confirmed'),
('R004', 'C004', '2024-09-18', 8, 'Anniversary', 'Cancelled'),
('R005', 'C005', '2024-09-19', 3, '', 'Confirmed');

INSERT INTO Tables (TableID, TableNumber, Capacity, Location, Status)
VALUES 
('T001', 1, 4, 'Main Dining Area', 'Available'),
('T002', 2, 2, 'Main Dining Area', 'Reserved'),
('T003', 3, 6, 'Outdoor', 'Available'),
('T004', 4, 8, 'Outdoor', 'Available'),
('T005', 5, 3, 'Private Room', 'Reserved');

INSERT INTO TableAssignments (TableAssignmentID, TableID, ReservationID, AssignmentTime)
VALUES 
('TA001', 'T001', 'R001', '2024-09-15 18:00:00'),
('TA002', 'T002', 'R002', '2024-09-16 19:00:00'),
('TA003', 'T003', 'R003', '2024-09-17 20:00:00'),
('TA004', 'T004', 'R004', '2024-09-18 21:00:00'),
('TA005', 'T005', 'R005', '2024-09-19 22:00:00');

INSERT INTO Payments (PaymentID, PaymentMethod, Amount, PaymentDate)
VALUES 
('P001', 'Credit Card', 50.00, '2024-09-15'),
('P002', 'Cash', 30.00, '2024-09-16'),
('P003', 'Debit Card', 75.00, '2024-09-17'),
('P004', 'Credit Card', 100.00, '2024-09-18'),
('P005', 'Online Payment', 45.00, '2024-09-19');

INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount, Status, OrderType, ReservationID, PaymentID)
VALUES 
('O001', 'C001', '2024-09-15', 50.00, 'Completed', 'Dine-in', 'R001', 'P001'),
('O002', 'C002', '2024-09-16', 30.00, 'Pending', 'Takeaway', NULL, 'P002'),
('O003', 'C003', '2024-09-17', 75.00, 'Completed', 'Dine-in', 'R003', 'P003'),
('O004', 'C004', '2024-09-18', 100.00, 'Cancelled', 'Dine-in', 'R004', 'P004'),
('O005', 'C005', '2024-09-19', 45.00, 'Pending', 'Delivery', NULL, 'P005');

INSERT INTO OrderItems (OrderItemID, OrderID, MenuItemID, Quantity, Price)
VALUES 
('OI001', 'O001', 'M001', 2, 8.99),
('OI002', 'O001', 'M003', 1, 3.99),
('OI003', 'O002', 'M002', 1, 6.99),
('OI004', 'O003', 'M004', 3, 5.99),
('OI005', 'O005', 'M005', 2, 2.99);

INSERT INTO Deliveries (DeliveryID, OrderID, DeliveryDate, DeliveryStatus, DeliveryAddress)
VALUES 
('D001', 'O005', '2024-09-20', 'Out for Delivery', '202 Cedar St'),
('D002', 'O002', '2024-09-17', 'Delivered', '456 Oak St'),
('D003', 'O001', '2024-09-15', 'Delivered', '123 Elm St'),
('D004', 'O003', '2024-09-17', 'Delivered', '789 Pine St'),
('D005', 'O004', '2024-09-18', 'Cancelled', '101 Maple St');


INSERT INTO Reviews (ReviewID, CustomerID, MenuItemID, Rating, Comments, ReviewDate)
VALUES 
('R001', 'C001', 'M001', 5, 'Excellent burger!', '2024-09-16'),
('R002', 'C002', 'M002', 4, 'Fresh and tasty.', '2024-09-17'),
('R003', 'C003', 'M003', 3, 'Crispy, but a bit salty.', '2024-09-18'),
('R004', 'C004', 'M004', 5, 'Best cake ever!', '2024-09-19'),
('R005', 'C005', 'M005', 4, 'Refreshing drink.', '2024-09-20');

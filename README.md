# Al-Silawi Online Shopping Store

## Overview
Al-Silawi Online Shopping Store is a desktop application designed for managing an online store that sells various home appliances. 
The project aims to facilitate inventory management, order processing, and customer management for Al-Silawi Company.

# Features
- **Customer Management**: Capture customer details including name, email, phone number, and shipping address.
- **Order Management**: Track order details such as order date, total price, payment status, and shipping status.
- **Product Management**: Manage product information including name, description, and price.
- **Manager Controls**: Allow managers to control store management, issue reports, and add new items to the store.
- **Payment Processing**: Facilitate payment processing for customer orders.
- **Cart Management**: Provide customers with shopping carts to add and manage their selected items.


# Data Requirements
## Entities
1. **Customer**: Contains customer details such as customer_id, name, email, phone number, etc.
2. **Order**: Represents order information including order_id, order_date, total_price, etc.
3. **Product**: Stores product details such as product_id, name, description, price, etc.
4. **Manager**: Holds manager information such as manager_id, name, email, phone number, etc.
5. **Login**: Manages login credentials for managers and customers.
6. **Categories**: Stores categories for organizing products.
7. **Payment**: Tracks payment details for orders.
8. **Cart**: Manages shopping carts for customers.
9. **Cart Item**: Contains details of items added to the shopping cart.


# Relationships
- **Customer-Order**: Each customer can place zero or many orders.
- **Order-Product**: Each order can contain zero or many products.
- **Manager-Login**: Each manager must have one login account.
- **Customer-Login**: Each customer must have one login account.
- **Product-Category**: Each product must belong to exactly one category.
- **Order-Payment**: Each order must be associated with one payment method.
- **Customer-Cart**: Each customer must have one shopping cart.
- **Cart-Cart Item**: Each shopping cart may have zero or many cart items.

# Technology Stack
- Hardware: Laptop Asus core i7, Laptop HP core i7
- Operating System: Windows 11
- Database: MySQL
- Programming Language: Java (JavaFX for GUI development)

# Getting Started
1. Clone the repository to your local machine.
2. Set up MySQL database with the provided schema.
3. Open the project in your preferred IDE.
4. Run the application.

# ScreenShot

Login Page
![login](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/7ca77c95-dcb7-43ea-8c0f-0f712a759ed2)

Forget Password Page
![ForgitPaswword](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/4b2b6c71-b1c0-4893-b7a0-fff2fcf83d88)

User SignUp Page
![UserSignUP](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/afa34abf-83af-4bd3-828a-0d1ae0911de6)

Admin SignUp Page
![AdminSignUP](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/762b98b6-b260-4e81-86b4-b427a6e6c952)

Store Page
![Store](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/95d64f02-5ab6-4cd8-a213-4aad0d44aaea)

SettingUser Page
![SettingUser](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/dbedc982-a54d-4fe9-96b4-b7e366e05dd2)

Admain Setting Page
![AdminSetting](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/677e5e36-220b-4855-a8bc-ef4d9edca680)

PaymentMethode Page
![paymentMethod](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/47e2eb0e-e323-4d43-af1d-7545a61ba0d6)

Products Page
![ProductScene](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/830728d5-a5e3-4244-b8d6-0009a7539202)

Report Page
![reportsScene](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/afc7dd51-239f-4c16-8dfe-2bde674f7893)

Orders Page
![Orders Scene](https://github.com/Anan-Elayan/Al-Silawi-Shopping-Store/assets/99610614/8debd30a-6017-4022-a373-33023a234239)








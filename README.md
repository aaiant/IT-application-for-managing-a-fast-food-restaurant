# 🍔 Fast-Food Ordering Application – Bachelor’s Thesis Project

## 📄 Overview

This repository contains the bachelor’s thesis developed at the **Faculty of Mathematics and Computer Science**, majoring in **Computer Science**, at **Ovidius University of Constanța**.

The thesis involved writing over **70 pages** and developing a **graphical user interface** for a fast-food restaurant. The application offers functionalities similar to commercial apps like **Spartan** or **Glovo**, allowing users to:

- create an account;
- place online orders;
- receive a **confirmation email** with the order details.

## 👤 User Features

Regular users benefit from an intuitive experience, with access to the following features:

- **Managing their personal account data**, including banking information and shopping cart;
- Access to a **Frequently Asked Questions (FAQ)** section to quickly find answers to common inquiries;
- **Customizing the interface** by choosing preferred colors from an extensive palette;
- Viewing detailed information about the **ingredients used** in the preparation of each product;
- Receiving a **confirmation email** after placing an order, listing all purchased items.

## 🔐 Role-Based Access and Administration

A key feature of the application is the implementation of a **role-based system** that provides extended control and functionalities for users with administrative privileges.

An **administrator** can:

- add, modify, or remove products available on the platform;
- manage the ingredients of products;
- adjust the color palette available for interface customization;
- edit the content of the FAQ section;
- **assign administrative roles** to other users, thereby expanding the application's management capabilities.

## 💻 Programming Languages and Technologies

This project was developed using the following languages and technologies:

- **Java** ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
- **Java Swing** (for building the graphical user interface)
- **Argon2** (password hashing algorithm)
- **JavaMail API** ![JavaMail](https://img.shields.io/badge/JavaMail-007396?style=flat&logo=java&logoColor=white)
- **Apache Maven** ![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=flat&logo=apache-maven&logoColor=white)
- **dotenv-java** (managing environment variables)
- **MVC Architecture** (Model-View-Controller design pattern)
- **PostgreSQL** ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white)
- **JDBC** (Java Database Connectivity)
- **DIA** (Diagram drawing application used for architecture design)

## 🗂️ Conceptual Database Schema

Below are the dedicated images illustrating the conceptual schema of the database used in this project:

![Database Schema 1](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/Database/Part%20I.png)  
*The first part of the conceptual schema presents the data associated with the user account, used for identification, login, and contact during registration and order confirmation.*

![Database Schema 2](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/Database/Part%20II.png)  
*The second part of the conceptual schema shows how products are organized into categories to provide users with complete information and advanced filtering options. It also highlights the connection between users and their shopping carts.*

![Database Schema 2](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/Database/Part%20III.png)  
*The third part focuses on storing user information, such as their home address, which is essential for direct product delivery. It also presents the Frequently Asked Questions (FAQ) menu, used to provide customers with 24/7 support.*

![Database Schema 2](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/Database/Part%20IV.png)  
*The final part of the conceptual schema illustrates how credit card data is managed, which users use to make simulated payments for the products in their shopping cart.*

## 🎯 Use Case Diagram

The following image illustrates the **use case diagram** for the Fast-Food Ordering Application, showing the interactions between users, administrators, and the system:

![Use Case Diagram](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/UML%20Diagram/Use_case_diagram.png)  
*This diagram highlights how different users interact with the system, including account management, order placement, and administrative actions.*

## 🖼️ Application Screenshots

Below are some screenshots of the Fast-Food Ordering Application, showcasing its graphical user interface (GUI) and key functionalities:

![Screenshot 1](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Login/Registration.png)  
*This menu is designed to register customers who wish to place online orders through a user-friendly graphical interface. The system validates all data entered in the available fields, ensuring compliance with the rules and detecting any existing accounts with similar information.*

![Screenshot 2](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Login/Login.png)  
*On this page of the application, users can log in to their accounts and subsequently access all available menus. The login process utilizes the Argon2 algorithm to secure the data entered by the user, adhering to the highest security standards.*

![Screenshot 3](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu/Coffee.png)  
*This image shows a section of the drinks menu after applying the filter Menu → Drinks → Coffees → Caffeinated Coffee. Each product displays key details (manufacturer, name, weight, label, and price). The appearance menu allows users to customize the interface, with hex codes provided for easy color selection.*

![Screenshot 4](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu/Product_details_2.png)  
*This image shows the full caffeinated coffee menu with a different color scheme, highlighting user interface customization. It also displays the selected product’s ingredients, demonstrating transparency in line with food industry regulations.*

![Screenshot 5](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu/Product_details.png)  
*This menu displays the types of smoothies available for preparation and delivery. Accessed via an advanced filter, it shows all ingredients used in each preparation.*

![Screenshot 8](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu%20Bar/Shawarma_menu.png)  
*Users can filter products via the menu bar, reducing search time and allowing faster addition of desired items to the shopping cart.*

![Screenshot 9](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu%20Bar/Account%20menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 10](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Menu%20Bar/Order_placement_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 11](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Appearance/Appearance_settings.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 12](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Profile/Profile_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 13](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Roles/Roles_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 14](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Q%26A/Questions_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 15](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Q%26A/Question_settings_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 16](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Q%26A/Sample_answer.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 17](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Q%26A/Answer_settings_menu.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 18](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Shopping%20Cart/Shopping_cart.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 19](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Shopping%20Cart/Place_order.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

![Screenshot 20](https://github.com/aaiant/IT-application-for-managing-a-fast-food-restaurant/blob/main/docs-images/App/Email/Email_confirmation.png)  
*Frequently Asked Questions (FAQ) section and support interface.*

---

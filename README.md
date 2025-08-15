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

---

# UNA PLANILLA WS

## Overview

**Una Planilla WS** is a web application designed for the administrative management of employees and payroll types. This project was developed as part of the Programming II course at the National University of Costa Rica and aims to introduce the basics of Java, JavaFX, and REST in an enterprise development environment.

## Description

This system allows the registration, search, editing, and deletion of employees and payroll types, as well as the assignment of employees to various payroll categories. It includes a REST service for data management, providing flexibility for integration with other systems and applications.

## Features

- **Employee Management**: Register, search, edit, and delete employees.
- **Payroll Type Management**: Create, search, edit, and delete payroll types.
- **Employee Assignment to Payrolls**: Add and organize employees across different payroll types.
- **REST API**: Offers RESTful services to interact with data remotely and in real-time.

## Technologies Used

This project leverages a modern tech stack to ensure reliability and optimal performance:

- **Java 21**: Core programming language.
- **JavaFX 21**: For a rich, customizable graphical user interface.
- **CSS**: For UI styling and customization.
- **Payara Server 6**: Application server for deploying RESTful services.
- **Oracle 21c XE (Docker)**: Database management system (can be installed on Docker or locally).
- **REST**: RESTful web services for data management.
- **Scene Builder**: For designing the JavaFX user interface.
- **IDE**: VSCode and NetBeans for development.

## Installation and Setup

To install and run **Una Planilla WS**, follow these steps:

1. **Install Java JDK 21**: Ensure that JDK 21 is installed on your machine.
2. **Install and Configure Payara Server**:
   - Download and set up Payara Server 6.
   - Configure a `Connection Pool` named `UnaPlanillaPool`.
   - Set up a `JDBC Resource` named `jdbc/UnaPlanilla`.
3. **Set Up Oracle Database**:
   - Install Oracle 21c XE, either on a Docker container or directly on your local machine.
   - Make sure it is accessible to the Payara Server for database operations.
4. **Run the Application**:
   - Build the project and package it as a JAR file.
   - Run the JAR files directly to start the application.

## Usage

1. **Initial Setup**: Configure an initial user in the database directly.
2. **Run the Application**:
   - Execute the following command in your terminal:
     ```bash
     mvn javafx:run
     ```
3. **Login**: Log in with the previously registered user.
4. **Explore Features**: Manage employees and payroll types through the user-friendly and customizable interface.

## Credits

- **Instructor**: MSc. Carlos Carranza Blanco, Head of IT at Coopeagri and Professor at the National University of Costa Rica.

## Contact

For questions or support, contact Alejandro León Marín at: aleleonmarin01@gmail.com

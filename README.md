# ERP System - Spring Boot Application

A comprehensive Enterprise Resource Planning (ERP) system built with Spring Boot 3.3.1, featuring modern web technologies and role-based access control.

## Features

### Core Modules
- **User Management** - User authentication, roles, and permissions
- **Customer Management** - Customer information, contacts, and addresses
- **Supplier Management** - Supplier details, contacts, and terms
- **Product Management** - Products, categories, and pricing
- **Inventory Management** - Stock control, movements, and adjustments
- **Purchase Management** - Purchase orders, receipts, and vendor management
- **Sales Management** - Sales orders, invoices, and customer management
- **Financial Management** - Basic accounting, payments, and reports
- **Reporting** - Dashboard and comprehensive reports

### Technical Features
- **Spring Security** - Role-based access control with multiple user roles
- **Thymeleaf Templates** - Modern Bootstrap 5 UI
- **JPA/Hibernate** - Database persistence with audit trails
- **MySQL Database** - Production-ready database support
- **RESTful APIs** - Well-structured API endpoints
- **Input Validation** - Comprehensive form validation
- **Error Handling** - Graceful error management

## Technology Stack

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Security 6**
- **Spring Data JPA**
- **Thymeleaf**
- **Bootstrap 5**
- **MySQL 8.0+**
- **Maven**

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd erp-system
```

### 2. Database Setup
Create a MySQL database and run the provided SQL scripts:

```bash
# Create database
mysql -u root -p < database/schema.sql

# Insert initial data (optional)
mysql -u root -p < database/initial_data.sql
```

### 3. Configure Database Connection
Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/erp_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run
```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will be available at: `http://localhost:8081`

## Default User Credentials

| Username  | Password    | Role      | Description |
|-----------|-------------|-----------|-------------|
| admin     | admin123    | ADMIN     | System Administrator |
| manager   | manager123  | MANAGER   | Manager |
| sales     | sales123    | SALES     | Sales Personnel |
| purchase  | purchase123 | PURCHASE  | Purchase Personnel |
| inventory | inventory123| INVENTORY | Inventory Manager |
| finance   | finance123  | FINANCE   | Finance Personnel |

## User Roles & Permissions

### ROLE_ADMIN
- Full system access
- User management
- All module access
- System configuration

### ROLE_MANAGER
- Administrative privileges
- Access to all modules except user management
- Reports and analytics

### ROLE_SALES
- Customer management
- Sales order management
- Product viewing
- Invoice creation

### ROLE_PURCHASE
- Supplier management
- Purchase order management
- Product viewing
- Inventory updates

### ROLE_INVENTORY
- Product management
- Inventory control
- Stock adjustments
- Movement tracking

### ROLE_FINANCE
- Invoice management
- Payment processing
- Financial reports
- Customer payment tracking

## Project Structure

```
src/main/java/com/erp/system/
├── config/          # Configuration classes
├── controller/      # Web controllers
├── entity/          # JPA entities
├── repository/      # Data repositories
├── service/         # Business logic
├── dto/            # Data transfer objects
├── util/           # Utility classes
└── exception/      # Custom exceptions

src/main/resources/
├── templates/      # Thymeleaf templates
├── static/         # Static resources (CSS, JS, images)
└── application.properties

database/
├── schema.sql      # Database schema
└── initial_data.sql # Sample data
```

## API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `POST /logout` - Logout

### Dashboard
- `GET /dashboard` - Main dashboard
- `GET /{role}/dashboard` - Role-specific dashboards

### Customers
- `GET /customers` - List customers
- `GET /customers/new` - New customer form
- `POST /customers` - Create/update customer
- `GET /customers/{id}` - View customer
- `GET /customers/{id}/edit` - Edit customer form
- `POST /customers/{id}/delete` - Delete customer

### Products
- `GET /products` - List products
- `GET /products/new` - New product form
- `POST /products` - Create/update product
- `GET /products/{id}` - View product
- `GET /products/{id}/edit` - Edit product form
- `POST /products/{id}/delete` - Delete product

## Database Schema

The system uses a comprehensive database schema with the following main tables:

- `users` - User accounts
- `roles` - User roles
- `permissions` - System permissions
- `customers` - Customer information
- `suppliers` - Supplier information
- `products` - Product catalog
- `product_categories` - Product categorization
- `inventory_movements` - Stock movements
- `sales_orders` - Sales transactions
- `purchase_orders` - Purchase transactions
- `invoices` - Customer invoices
- `payments` - Payment records

## Development

### Running in Development Mode
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package -Pprod
```

## Configuration

### Profiles
- `dev` - Development profile with debug logging
- `prod` - Production profile with optimized settings

### Key Configuration Properties
- `server.port=8081` - Application port
- `spring.jpa.hibernate.ddl-auto=update` - Database schema handling
- `spring.jpa.show-sql=true` - SQL logging (dev only)

## Security

The application implements comprehensive security measures:

- **Authentication** - Form-based login with secure password hashing
- **Authorization** - Role-based access control
- **Session Management** - Secure session handling
- **CSRF Protection** - Cross-site request forgery protection
- **Password Encoding** - BCrypt password encryption

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please contact the development team or create an issue in the repository.

---

**ERP System v1.0** - A complete business management solution built with Spring Boot.
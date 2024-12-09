# Binary Search Tree Visualizer

## Overview
A Spring Boot web application that allows users to create, visualize, and analyze binary search trees. This project includes AVL tree balancing, real-time visualization, and historical tree tracking.

## Features
- Interactive Binary Search Tree creation and visualization
- AVL tree balancing implementation
- Real-time tree metrics:
  - Tree height
  - Number of nodes
  - Leaf node count
  - Balance factor
- Search functionality with path visualization
- Historical tree storage and retrieval
- Responsive, modern UI design

## Technologies Used
- Backend:
  - Spring Boot
  - JPA/Hibernate
  - JUnit 5 for testing
- Frontend:
  - Thymeleaf templates
  - Tailwind CSS
  - Vanilla JavaScript

## Prerequisites
- Java 17 or higher
- Maven
- MySQL/PostgreSQL database

## Setup and Installation
1. Clone the repository:
```bash
git clone [[repository-url](https://github.com/brendaleearmstrong/S4-DAS-FinalSprint-BST)]
```

2. Configure database settings in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bst_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

5. Access the application at: `http://localhost:8080`

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/keyin/bst/
│   │       ├── controller/
│   │       │   └── BinaryTreeController.java
│   │       ├── model/
│   │       │   ├── BinarySearchTree.java
│   │       │   ├── SearchResult.java
│   │       │   └── TreeNode.java
│   │       ├── repository/
│   │       │   └── BinaryTreeRepository.java
│   │       └── service/
│   │           └── BinaryTreeService.java
│   └── resources/
│       └── templates/
│           ├── enter-numbers.html
│           ├── error.html
│           ├── home.html
│           ├── previous-trees.html
│           └── search-number.html
└── test/
    └── java/
        └── com/keyin/bst/
            ├── BinarySearchTreeTest.java
            ├── BinaryTreeServiceTest.java
            └── BinaryTreeControllerTest.java
```

## API Endpoints
- `GET /` - Home page
- `GET /enter-numbers` - Tree creation page
- `POST /process-numbers` - Process and create new tree
- `GET /previous-trees` - View historical trees
- `GET /search-number` - Search functionality
- `POST /search` - Perform tree search
- `GET /tree/{id}/metrics` - Get specific tree metrics

## Testing
The project includes comprehensive unit tests for all major components:
- Model testing (BinarySearchTreeTest)
- Service layer testing (BinaryTreeServiceTest)
- Controller testing (BinaryTreeControllerTest)

Run tests using:
```bash
mvn test
```

## Features in Detail

### 1. Tree Creation
- Input a series of numbers
- Automatic AVL balancing
- Real-time visualization
- Metric calculations

### 2. Tree Visualization
- Interactive tree display
- Node connections
- Balance factor indicators
- Tree metrics display

### 3. Search Functionality
- Number search across all trees
- Path visualization
- Search result highlighting

### 4. Historical Tracking
- Previous tree storage
- Tree comparison
- Metric history

## Author
Brenda Armstrong
SD10 - Keyin College
S4 Data Structures & Algorithms

## Contributing
This is a semester project for Keyin College. While it's not open for contributions, feel free to fork and modify for educational purposes.

## License
This project is created for educational purposes as part of the Keyin College curriculum.

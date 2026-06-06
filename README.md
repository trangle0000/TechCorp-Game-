# TechCorp Game - Java Decision-Making Game - Final Project Java

A turn-based business management simulation game built with **Java** and **Spring Boot**.

## 1. Project Overview

TechCorp Game is a console-based decision-making game where the player manages a tech company over multiple rounds, competing against an AI opponent. The application demonstrates key software engineering concepts including:

- Object-oriented design (inheritance, polymorphism, encapsulation)
- Layered architecture (Domain, Engine, Exceptions, Constants)
- Custom exception handling
- Input validation and error handling
- Turn-based game logic with AI opponent
- Maven-based build automation
- Unit testing with JUnit 5

## 2. Features

- **Turn-Based Gameplay** - Player vs AI over configurable rounds
- **Employee Management** - Hire Developer, Manager, or Tester with different skills
- **Project Management** - Start projects with different difficulties (Easy/Medium/Hard/Critical)
- **AI Opponent** - AI uses AGGRESSIVE, BALANCED, or DEFENSIVE strategy
- **Financial System** - Track cash, salaries, project budgets and revenue
- **Scoring System** - Final score based on cash + reputation
- **Exception Handling** - Custom exception hierarchy (GameException, InsufficientFundsException, InvalidProjectException)
- **Input Validation** - All constructor arguments and user inputs validated

## 3. Tech Stack

- **Language**: Java 11
- **Framework**: Spring Boot 2.7.15
- **Build Tool**: Maven 3.9.6
- **Testing**: JUnit 5
- **Container**: Docker
- **Version Control**: Git & GitHub

## 4. Project Structure

```
TechCorp-Game/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java                          # Entry point
│           ├── domain/
│           │   ├── Employee.java                  # Abstract base class
│           │   ├── Developer.java                 # 1.2x productivity
│           │   ├── Manager.java                   # 0.8x productivity
│           │   ├── Tester.java                    # 0.9x productivity
│           │   ├── Company.java                   # Company state management
│           │   └── Project.java                   # Project with status lifecycle
│           ├── engine/
│           │   ├── GameEngine.java                # Core game loop
│           │   └── AIPlayer.java                  # AI opponent logic
│           ├── exceptions/
│           │   ├── GameException.java             # Base exception
│           │   ├── InsufficientFundsException.java
│           │   └── InvalidProjectException.java
│           └── constants/
│               └── GameConstants.java             # Game configuration
├── pom.xml
├── Dockerfile
├── run.sh                                         # Quick run script
└── README.md
```

## 5. How to Run

### Option 1: Using run script (recommended)
```bash
./run.sh
```

### Option 2: Manual build and run
```bash
# Build
mvn clean package -DskipTests

# Run
java -jar target/techcorp-game-1.0.0.jar
```

### Option 3: Spring Boot
```bash
mvn spring-boot:run
```

## 6. Gameplay

Each round the player chooses:
1. **Hire Employee** - Choose type (Developer/Manager/Tester) and skill level (1-100)
2. **Start Strategic Project** - Choose difficulty (Easy/Medium/Hard/Critical)
3. **Skip** - Pass this round

The AI opponent takes its turn automatically after the player.

### Employee Types
| Type | Salary | Productivity |
|------|--------|--------------|
| Developer | skill × $5,000 | 1.2x |
| Manager | skill × $7,000 | 0.8x |
| Tester | skill × $3,000 | 0.9x |

### Project Difficulties
| Difficulty | Budget | Revenue | Success Rate | Deadline |
|------------|--------|---------|--------------|----------|
| Easy | $5,000 | $15,000 | 90% | 3 rounds |
| Medium | $10,000 | $30,000 | 75% | 4 rounds |
| Hard | $20,000 | $60,000 | 60% | 5 rounds |
| Critical | $40,000 | $120,000 | 40% | 6 rounds |

## 7. Docker

```bash
# Build image
docker build -t techcorp-game:1.0.0 .

# Run container
docker run -it techcorp-game:1.0.0
```

## 8. Game Classes

### Domain Layer
- **Company** - Manages cash, reputation, employees, projects. Score = cash + reputation × 100
- **Employee** - Abstract base with skill (1-100), salary, experience. Implements Comparable
- **Developer** - 1.2x productivity, salary = skill × 5000
- **Manager** - 0.8x productivity, salary = skill × 7000
- **Tester** - 0.9x productivity, salary = skill × 3000
- **Project** - Status lifecycle: NOT_STARTED → IN_PROGRESS → COMPLETED/FAILED

### Engine Layer
- **GameEngine** - Main game loop, player input handling, round management
- **AIPlayer** - Three strategies: AGGRESSIVE (Hard projects), BALANCED (Medium), DEFENSIVE (Easy)

### Exception Layer
- **GameException** - Base exception with errorCode and severity (LOW/MEDIUM/HIGH/CRITICAL)
- **InsufficientFundsException** - Thrown when cash < required amount
- **InvalidProjectException** - Thrown for invalid project operations

## 9. Starting Values

- Starting Cash: $100,000 (both Player and AI)
- Total Rounds: 10
- Starting Skill (default): 50

## 10. Learning Outcomes

This project demonstrates:
- Java OOP principles (inheritance, polymorphism, encapsulation, abstraction)
- Custom exception hierarchy with error codes and severity levels
- Input validation at all system boundaries
- Turn-based game logic with AI decision making
- Maven project structure and build automation
- Docker containerization
- Layered architecture pattern

## 11. Author

**Trang Van Le**
- GitHub: [@trangle0000](https://github.com/trangle0000)

---

**Last Updated**: June 2026  
**Java Version**: 11  
**Spring Boot Version**: 2.7.15

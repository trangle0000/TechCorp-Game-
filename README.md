# TechCorp Duel Game - Final Project Java Programming

A console-based turn-by-turn strategy game where you compete against an AI opponent to build the most successful tech company.

**Student**: Trang Van Le (140036)

---

## 1. Project Overview

TechCorp Duel Game is a console application built with Java and Spring Boot. Players manage a tech company by hiring employees, starting projects, and competing against an AI opponent across 12 rounds. The player with the highest score (cash + reputation) at the end wins.

Key concepts demonstrated:
- Object-Oriented Programming (inheritance, polymorphism, encapsulation, abstraction)
- Custom exception hierarchy with error codes and severity levels
- Layered architecture (Domain / Engine / Exceptions / Constants)
- Abstract classes and interfaces (`Comparable<Employee>`)
- Strategy pattern (AI opponent with AGGRESSIVE / BALANCED / DEFENSIVE strategies)
- Maven build automation and Spring Boot dependency management

---

## 2. Tech Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 11 | Core language |
| Spring Boot | 2.7.15 | Dependency management, logging (SLF4J) |
| Maven | 3.9.6 | Build automation |
| JUnit 5 | 5.9.3 | Unit testing |
| Docker | - | Containerization |
| Git & GitHub | - | Version control |

---

## 3. Project Structure

```
TechCorp-game/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java                          # Entry point
│           ├── constants/
│           │   └── GameConstants.java             # All game configuration constants
│           ├── domain/
│           │   ├── Company.java                   # Company model (cash, employees, projects)
│           │   ├── Employee.java                  # Abstract base class (Comparable<Employee>)
│           │   ├── Developer.java                 # 1.2x productivity, skill×5000 salary
│           │   ├── Manager.java                   # 0.8x productivity, skill×7000 salary
│           │   ├── Tester.java                    # 0.9x productivity, skill×3000 salary
│           │   └── Project.java                   # Project with Status & Difficulty enums
│           ├── engine/
│           │   ├── GameEngine.java                # Main game loop, player input handling
│           │   └── AIPlayer.java                  # AI opponent with strategy selection
│           └── exceptions/
│               ├── GameException.java             # Base exception (ErrorSeverity enum)
│               ├── InsufficientFundsException.java # Error code: FUNDS_001
│               └── InvalidProjectException.java   # Error code: PROJECT_001
├── pom.xml                                        # Maven dependencies
├── Dockerfile                                     # Docker build configuration
├── run.sh                                         # Quick run script
└── README.md
```

---

## 4. OOP Concepts

### 4.1 Employee Hierarchy (Inheritance & Polymorphism)

```
Employee (Abstract)  ← implements Comparable<Employee>
├── Developer        → getProductivity() = 1.2x, salary = skill × 5,000
├── Manager          → getProductivity() = 0.8x, salary = skill × 7,000
└── Tester           → getProductivity() = 0.9x, salary = skill × 3,000
```

- `Employee` is abstract with abstract methods: `getProductivity()`, `getRole()`
- Each subclass overrides `getProductivity()` and `getContribution()`
- `compareTo()` sorts employees by skill level (descending)

### 4.2 Custom Exception Hierarchy

```
Exception
└── GameException (base, with ErrorSeverity enum: LOW/MEDIUM/HIGH/CRITICAL)
    ├── InsufficientFundsException  → FUNDS_001, Severity: MEDIUM
    └── InvalidProjectException     → PROJECT_001, Severity: HIGH
```

### 4.3 Key Classes

| Class | Responsibility |
|-------|---------------|
| `Company` | Manages cash, reputation, employees, projects. Score = cash + reputation × 1000 |
| `Project` | Status enum (NOT_STARTED/IN_PROGRESS/COMPLETED/FAILED), Difficulty enum (EASY/MEDIUM/HARD/CRITICAL) |
| `GameEngine` | Main game loop, player input, advance projects each round |
| `AIPlayer` | Static class, selects strategy based on cash ratio |
| `GameConstants` | 40+ constants, validated on class load via static initializer |

---

## 5. Game Rules

- **Starting budget**: $100,000 each (player and AI)
- **Rounds**: 12 total
- **Each round**, player chooses:
  1. **Hire Employee** — choose type (Developer/Manager/Tester) and skill level (1–100)
  2. **Start Strategic Project** — choose difficulty (Easy/Medium/Hard/Critical)
  3. **Skip**
- **Projects** complete after a set number of rounds and reward cash + reputation
- **Score** = Final Cash + (Total Reputation × 1,000)
- Highest score wins!

### Project Options

| Difficulty | Cost | Revenue | Success Rate | Deadline |
|-----------|------|---------|-------------|---------|
| Easy | $5,000 | $15,000 | 90% | 3 rounds |
| Medium | $10,000 | $30,000 | 75% | 4 rounds |
| Hard | $20,000 | $60,000 | 60% | 5 rounds |
| Critical | $40,000 | $120,000 | 40% | 6 rounds |

---

## 6. How to Run

### Prerequisites
- Java 11+
- Maven 3.6+

### Run with script (recommended)
```bash
./run.sh
```

### Run manually
```bash
mvn clean package -DskipTests -q
java -jar target/techcorp-game-1.0.0.jar
```

### Run with Docker
```bash
docker build -t techcorp-game:1.0.0 .
docker run -it techcorp-game:1.0.0
```

---

## 7. Live Deployment

The game is deployed and accessible at:
**URL**: [https://techcorp-game-api.onrender.com](https://techcorp-game-api.onrender.com)
> Deployed on [Render.com](https://render.com) using Docker containerization.

## 8. Game Constants (GameConstants.java)

Key constants validated on startup via static initializer block:

| Constant | Value |
|----------|-------|
| PLAYER_STARTING_BUDGET | $100,000 |
| AI_STARTING_BUDGET | $100,000 |
| GAME_ROUNDS | 12 |
| DEVELOPER_SALARY_MULTIPLIER | 5,000 |
| MANAGER_SALARY_MULTIPLIER | 7,000 |
| TESTER_SALARY_MULTIPLIER | 3,000 |
| DEVELOPER_PRODUCTIVITY | 1.2 |
| MANAGER_PRODUCTIVITY | 0.8 |
| TESTER_PRODUCTIVITY | 0.9 |
| REPUTATION_MULTIPLIER | 1,000 |

---

## 9. AI Opponent

The AI uses a strategy based on its current cash ratio:

| Cash Ratio | Strategy | Behavior |
|-----------|----------|---------|
| ≥ 70% | AGGRESSIVE | Hire employees + start Hard projects |
| 30–70% | BALANCED | Maintain workforce + start Medium projects |
| ≤ 30% | DEFENSIVE | Start Easy projects, emergency hiring only |

---

## 10. Running Tests

```bash
mvn test
```

---

## 11. Learning Outcomes

- Java OOP: abstract classes, inheritance, polymorphism, interfaces
- Custom exception hierarchy with error codes and severity levels
- Static initializer blocks for configuration validation
- Strategy pattern for AI decision-making
- Maven project structure and build automation
- Spring Boot for logging (SLF4J with Logback)
- Docker containerization
- Git version control and GitHub collaboration

---

## 12. Author

**Trang Van Le** — Student ID: 140036
- GitHub: [@trangle0000](https://github.com/trangle0000)

---

*Java Programming Final Project — 2026*

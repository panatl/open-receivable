# Contributing to Open Receivable

Thank you for your interest in contributing to the Open Receivable System!

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Git

### Setting Up Development Environment

1. **Fork and Clone the Repository**

   ```bash
   git clone https://github.com/panatl/open-receivable.git
   cd open-receivable
   ```

2. **Build the Project**

   ```bash
   mvn clean compile
   ```

3. **Run Tests**

   ```bash
   mvn test
   ```

4. **Run the Application**

   ```bash
   mvn spring-boot:run
   ```

## Development Workflow

### Branch Strategy

This repository allows direct push to the `main` branch for rapid development. However, we recommend following these best practices:

#### For Individual Contributors

```bash
# Create a feature branch
git checkout -b feature/your-feature-name

# Make your changes
# ... edit files ...

# Commit your changes
git add .
git commit -m "Add feature: description of changes"

# Push to your branch
git push origin feature/your-feature-name

# Create a pull request (optional but recommended)
```

#### For Direct Push to Main (Enabled)

If you have permissions, you can push directly to main:

```bash
# Make sure you're on main
git checkout main

# Pull latest changes
git pull origin main

# Make your changes
# ... edit files ...

# Commit and push
git add .
git commit -m "Description of changes"
git push origin main
```

#### Force Push Guidelines

Force push is enabled on this repository. Use it responsibly:

```bash
# Safer force push (recommended)
git push --force-with-lease origin main

# Standard force push (use with caution)
git push --force origin main
```

**⚠️ Important:** Only use force push when:
- You're certain no one else is working on the same branch
- You need to rewrite history (rebase, amend, etc.)
- You've communicated with your team

## Code Style and Standards

### Java Code Style

- Follow standard Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public APIs
- Keep methods focused and concise

### Project Structure

```
open-receivable/
├── src/main/java/org/openreceivable/
│   ├── model/           # Domain entities
│   ├── repository/      # Data access layer
│   ├── service/         # Business logic
│   ├── enums/          # Enumerations
│   └── graphql/        # GraphQL resolvers and types
├── src/main/resources/
│   └── graphql/        # GraphQL schema definitions
└── schemas/            # JSON schema files
```

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=YourTestClass

# Run with coverage
mvn test jacoco:report
```

### Writing Tests

- Write unit tests for new functionality
- Maintain or improve code coverage
- Use meaningful test names that describe what is being tested
- Follow the existing test patterns in the codebase

## Making Changes

### Types of Changes

- **Bug Fix**: Fixes an issue in the existing code
- **Feature**: Adds new functionality
- **Enhancement**: Improves existing functionality
- **Refactoring**: Improves code quality without changing behavior
- **Documentation**: Updates or adds documentation

### Commit Messages

Write clear and descriptive commit messages:

```bash
# Good commit messages
git commit -m "Fix payment allocation calculation for partial payments"
git commit -m "Add support for bi-weekly payment frequency"
git commit -m "Update README with GraphQL federation examples"

# Less helpful commit messages (avoid these)
git commit -m "Fixed bug"
git commit -m "Update"
git commit -m "WIP"
```

### Code Review

While direct push is enabled, code review is still valuable:

1. For significant changes, consider creating a pull request
2. Request review from team members
3. Address feedback and comments
4. Ensure CI checks pass

## Building and Running

### Build Commands

```bash
# Clean build
mvn clean compile

# Package application
mvn clean package

# Skip tests (not recommended)
mvn clean package -DskipTests
```

### Running Locally

```bash
# Run with Maven
mvn spring-boot:run

# Run with Java
java -jar target/open-receivable-0.0.1-SNAPSHOT.jar

# Run with custom port
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Accessing the Application

- GraphQL Endpoint: http://localhost:8080/graphql
- GraphiQL Interface: http://localhost:8080/graphiql
- WebSocket: http://localhost:8080/graphql-ws

## Documentation

### Updating Documentation

When making changes, update relevant documentation:

- **README.md**: Main project documentation
- **DATA_MODEL.md**: Data model and entity relationships
- **EXAMPLES.md**: Usage examples and code samples
- **GRAPHQL_FEDERATION.md**: GraphQL schema and federation
- **ARCHITECTURE.md**: Architecture and design decisions

### Adding Examples

If you add new features, include examples in `EXAMPLES.md`:

```markdown
### New Feature Name

Description of the feature.

**Example:**
\`\`\`java
// Code example
\`\`\`
```

## Reporting Issues

### Bug Reports

When reporting a bug, include:

1. Description of the issue
2. Steps to reproduce
3. Expected behavior
4. Actual behavior
5. Environment details (Java version, OS, etc.)
6. Relevant logs or error messages

### Feature Requests

When requesting a feature:

1. Describe the feature and its benefits
2. Provide use cases
3. Suggest implementation approach (optional)

## Getting Help

- Review existing documentation in the repository
- Check closed issues for similar problems
- Open a new issue with your question
- Reach out to maintainers

## License

By contributing to this project, you agree that your contributions will be licensed under the same license as the project.

## Thank You!

Your contributions help make the Open Receivable System better for everyone. We appreciate your time and effort!

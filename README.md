Module 1
Reflection 1

Here, my code follows the clean code principals taught in class to improve readability, maintainability and efficiency.

1. Meaningful naming 
- class names (ProductController, ProductServiceImpl, etc.) follow standard naming conventions and describe roles clearly
- method names (findAll(), createProduct(), etc.) reflect functionality accurately.
2. Functions and modularity: code has clear separation 
- Controller handles user requests.
- Service processes business logic.
- Repository manages data storage.
3. Objects and data structures
- Product is structured properly using private fields with getter and setter methods, ensuring encapsulation.
- The List<Product> collection in ProductRepository allows dynamic storage.

What could be improved:
1. Code could have additional comments explaining complex logic and function purposes.
2. List<Product> should be replaced with a persistent database.
3. Risk of NullPointerException in the deleteProduct() method could be eliminated with illegal argument exception (adding a check before accessing productId).

My code also follows security practices.
1. To prevent injection attacks, I used Thymeleaf (th: attributes) to prevent Cross-Site Scripting.
2. Product class uses private fields with getters and setters from Lombok for encapsulation and data hiding.
3. I also used UUIDs.

Reflection 2
1. - Not gonna lie, I feel numb :) AHAHAHAHA pls I'm just kidding.
- I think that there isn't a specific number of unit tests that should be made, however we have to make sure that all possible scenarios are tested.
- Test them on various cases (edge cases, mock dependencies, measure code coverage, etc.)
- No, it just means that the entire code has been tested.

2. - The chances of both test suites having similar setup methods are high, hence the code might be redundant. To avoid this, we can gather the common setup logic into a base test class and both test suites can inherit from it.
- However if each test suite has its own implementation, code reusability would be reduced. To avoid this we can use helper methods.
- If a different validation rule is introduced, multiple test suites must be updated separately. We can use parameterized tests/reusable test utilites to keep the test logic modular.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Module 2
1. I noticed the following issues:
- SonarCloud Authentication Failure (401 Unauthorized)
Issue: CI/CD pipeline failed due to invalid/missing SonarCloud token.
Solution: Regenerated SonarCloud Token, updated it in GitHub Secrets, fix build.gradle.kts and sonarcloud.yml.
- Lack of Proper Dependency Caching
Issue: CI/CD runs were slow due to redundant dependency downloads.
Solution: Implemented Gradle caching in GitHub Actions using the actions/cache@v3 step.

2. Every push and pull request triggers automated builds and SonarCloud analysis, ensuring that code is always tested and analyzed before merging. However, additional unit tests, integration tests, and linting could further improve quality checks.
   The workflow does not yet automatically deploy successful builds. Adding a step to notify developers (e.g., via Slack or email) on failed builds can enhance team collaboration.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Module 3
1. I applied the entire SOLID principles.
- Single Responsibility Principle:  Instead of a single ProductServiceImpl handling product creation, updating, and deletion, we separated them into CreateProduct, UpdateProduct, and DeleteProduct interfaces.
- Open-Closed Principle: ProductServiceImpl supports different product types (like Car) without modifying the original class by using polymorphism.
- Liskov Substitution Principle: Car extends Product, and we can treat Car as a Product in our ProductService without breaking functionality.
- Interface Segregation Principle: We split ProductService into separate interfaces (CreateProduct, UpdateProduct, and DeleteProduct) to ensure that classes only implement the methods they need.
- Dependency Inversion Principle: ProductController depends on ProductService (interface), not on ProductServiceImpl. This allows for easier dependency injection and testing.

2. - Better Code Maintainability. Ex: If we want to change how products are updated, we only modify UpdateProduct without affecting other operations.
- New features can be added without modifying existing code: Ex: If we introduce a new product type (e.g., Electronics), we simply create a new class extending Product without modifying ProductService.
- Improved Testability: ProductController can be tested independently by mocking ProductService.
- Easier Collaboration: One developer can work on CreateProduct while another works on DeleteProduct without conflicts.

3. - Difficult to Maintain and Modify: If ProductService handled all operations in a single class, modifying product deletion could break product creation.
- Harder to Extend: Without OCP, adding a new product type (e.g., Electronics) would require modifying multiple files, increasing the risk of errors.
- Code Becomes Coupled and Rigid: Without DIP, ProductController would depend directly on ProductServiceImpl, making it harder to replace implementations.
- More Bugs and Testing Issues: A ReadOnlyProductService would still have to implement update() and delete(), even if they are not needed.
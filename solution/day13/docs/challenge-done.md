## Day 13: Find a way to eliminate the irrelevant, and amplify the essentials of those tests.

### Identifying the building technique

In all tests, we are manipulating Articles with Comments. We want to 
create them in a simpler and more maintainable way.

We can use [`Test Data Buidler`](https://xtrem-tdd.netlify.app/Flavours/test-data-builders) pattern to achieve this
purpose.

It will:

- `Facilitate writing` tests by allowing easy creation of inputs or expected data.
- `Improve test maintainability` by decoupling the creation of objects in tests, and isolating it in a single
  location (Single Responsibility Principle)
- Disseminate `functional understanding` in the team through the use of business terms
- Facilitate the `readability of the tests` by explaining only the data implied in the use case

- We need to identify what is irrelevant and what is essentials in those tests:

Irrelevant: author, comment-text, article's name and content
Essential: an article contains a comment

### How to create a test data builder

- Let's create a `Test Data Builder` for the `Article`
    - We would like to have a fluent data builder like this:

```java
@BeforeEach
void setup() {
        article = ArticleBuilder
        .anArticle()
        .build();
}
```

We then generate the code from usage. 
We end up with a builder class with the methods.

- We implement the builder with the build method (simplest as possible)

- We adapt the tests
- We iterate on the builder

### Optimizations

- Let's improve readability of our tests by
    - simplifying them
    - removing duplication

### Bonus: additional optimizations with other libs

We could remove call to the builder from the tests by creating a DSL and using `Higher Order Function`.

- We could also use a library to get random `String` data for our tests (they do not impact the results of the behavior and create noise)
    - We can use `Instancio` to do so

- By using it, it makes explicit that some required data do not influence the behavior

>**Tip of the day: Your test data and assertions can be abstracted to improve readability and maintainability.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

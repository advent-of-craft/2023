## Day 16: Make this code immutable.

### What is mutable and immutable?

- If we make the `Article` immutable that means its contract will look like this
  - `addComment`: `String` -> `String` -> `LocalDate` -> `Article`

- `Comment` is immutable by design because it is a `record`

🔴 Let's describe this in a test
- We do not use the `when`, `then` DSL to adapt it later on

- Of course, we can not compile anymore

🟢 We go to green as fast as possible
- By simply returning `this` like in a `Fluent API` (`ArticleBuilder` for example 😉)

🔵 Refactor to immutability
- We adapt the `private` method to instantiate a new `Article`
  - Based on the current one

- We need to create a new constructor from here

- Natively in java, it is not that easy to use an immutable `List`...
  - The proposal above does not work
  - We can adapt the code as below...

🔴 😱 This refactoring impacted the tests...

- We now have to adapt the tests to go back to a safe state
- We fix the test `dsl`

- The `CommentAlreadyException` is a real pain in the ass...
  - Making our test and code hardly readable...

- Why is it not a `RuntimeException`?
  - Let's remove the pain by doing it...

- We need to adapt our `Test Data Builder`

- We use a `reducer`

- We clean the tests
  - Remove duplicated test case
  - Adapt the `DSL` to end up with

### Use Immutable List
🔵 Let's refactor the internal of `Article` to use an immutable collection
- We use [`vavr`](https://docs.vavr.io/#_collections) collection to do so

- We adapt the `Article` to end up with

>**Tip of the day: Making your code immutable will greatly reduce concurrency-related problems.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.

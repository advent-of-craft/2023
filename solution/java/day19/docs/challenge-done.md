## Day 19: No more exception authorized - use a custom Error.

> Why would we like to avoid Exceptions?

### Exceptions vs Errors

> "Exception handling is the process of responding to the occurrence of exceptions – anomalous or exceptional conditions requiring special processing – during the execution of a program." - Wikipedia

Exceptions and errors are not the same thing:
- Exceptions: exceptional situations, which by definition can not be many
  - Should not leave the system boundaries in their original form
  - If the thrown exception is processed by our application, `should we really use an exception mechanism?`
- Error/Result explicitly declares the possibility of an error and allows `linear` processing

### Use an explicit Error instead of Exception
Once again we need to adapt our `Article` class.

The problem if we use an `Error` here is that we need to be able to express that the method `addComment` can return:
- an `Article` in case of `success`
- an `Error` in case of `failure`

The good news is that we can do it really easily by using existing mechanism: monadic container `Either<L, R>`. It is already defined in [`vavr`](https://docs.vavr.io/#_either).

More explanations on this concept [here](https://xtrem-tdd.netlify.app/Flavours/monads).

🔴 We start by describing our new expectation from a test
- We design a new method `addCommentWithoutException`
  - That will return `Either<blog.Error, Article>`
  - By convention the `left` case is the `failure` one

- We use this a test as a driver for our refactoring
- We generate the code from here
- We have a skeleton of a code and can compile

🟢 Make it green by simply returning what is expected from our assumption (hypothesis / test)

🔵 Anything to refactor?
- We can refactor the other `passing` test and the `DSL`

🔴 Let's use this new method from the `non passing` test
- We will triangulate the remaining of the implementation like this

- It is failing because we still throw the `CommentAlreadyExistException` exception

🔵 There is plenty of improvement opportunity

- Adapt the Builder to use the new method
- Delete the exception
- Our IDE detects some usages that we need to clean 🥳
- We can now delete the previous `addComment` (no more caller)
- We rename `addCommentWithoutException` to `addComment` and end up with
- Regarding the tests, we can simplify assertions by using [`assertj-vavr`](https://github.com/assertj/assertj-vavr)
- It simplifies assertions of monadic containers

The code should be fully integrated with Monads.

>**Tip of the day: Using monads instead of exceptions can greatly decrease the complexity of your program.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.

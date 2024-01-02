## Day 14: Do not use exceptions anymore.

How can we possibly avoid using exceptions?

### Extend the output

- Our convert method is looking like this at the moment
  - Meaning its contract looks like this: Integer -> String (or OutOfRangeException)

- We can use the [`Parse dont Validate`](https://xtrem-tdd.netlify.app/Flavours/parse-dont-validate) principle to redesign this method
  - We would like that the contract looks like this: `Integer` -> `Result<Integer>` (Meaning that the result could be a failure)

### How to refactor

🔴 Let's write a new failing test expressing this parsing (the method still doesn't exist)

🟢 We generate a new `convertWithResult` method from the test with the expected signature
- We generate the `Result` class as well to end up with

🔵 We look at what can be improved.

🔴 We write another test for the failure scenario.

🟢 We iterate on `convertWithResult` implementation to handle failure.
- We adapt the `Result` class to support failure

🔵 We delete the previous `convert` implementation and rename the `convertWithResult` method
- We can now delete the `OutOfRangeException` as well

#### Use an existing Result
Alternatively, we can use an existing data structure to represent the `Result` of the parsing method.
Let's use `vavr` to do so.

The equivalent of our `Result` class is the [`Option`](https://docs.vavr.io/#_option) monadic container.
- Immutable structure
- Functor

🔴 We adjust the tests to use the `Option`

🟢 We adapt the `convert` method

🔵 Anything to improve / refactor?

>**Tip of the day: Use exceptions for failures you cannot control.**

### Share your experience

What do you think about those concepts and this library? 
What could you do with it?

How does your code look like?

Please let everyone know in the discord.

## Day 9: Fix the code.

### Finding the issue with the code.

- The public method `toStatement` has a signature like this: `void` -> `String`
  - From the outside, it is a query method that simply returns data
  - But here is the problem if we look closer the implementation...
    - Each time we call the `toStatement` method it will mutate the internal state of the object...
    - It will update the `totalAmount` meaning that the second time we call the method the result will be wrong

- The responsibilities are mixed in the methods
- We will apply the [`Command Query Separation` principle](https://xtrem-tdd.netlify.app/Flavours/command-query-separation) to fix this problem.

|             | Returns  |           Side Effect           |
|-------------|:--------:|:-------------------------------:|
| **Query**   | a result |              None               |
| **Command** |   void   | changes the state of the system |

- `Queries` return a result without side effects on the system
  - We can call queries from anywhere, multiple times and in various orders
- `Commands` will affect the system but won't return any result. Therefore
  - The state will be different before and after a command
- A method cannot be both (returning a value and producing a side effect)

The `toStatement` method precisely belongs to both categories.

🔴 Let's write a failing test expressing the behavior we want to isolate (calculate total amount)
generating the code from the test.

🟢 Make it pass `as fast as possible`

🔵 We refactor the method to use the `stream` api

### Optimizations

- We can now use the `totalAmount` method from `toStatement`
- We fix the `formatLine` method
- We fix the test as well
- We can now remove the `totalAmount` field
- We give the code a finishing touch

The code should be more streamlined and more maintainable.

>**Tip of the day: A method name should represent what she does and returns.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

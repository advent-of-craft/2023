## Day 7: Simplify the run method by extracting the right behavior.

### Extracting the first level

Here we will use `Extract Method` refactorings to make our code 
more readable / maintainable.

Our run method should now only have methods calls.

- By just splitting the code, it is now much easier to understand the purpose of this run method

### Extracting the underlying methods

- Let's go further by refactoring the underlying methods
  - We start with the `runTests` method

- Then we change the return statements to simplify the flow
  - We remove redundant `else` as well

- We apply the same technique on the deployment method

### Finding the right abstraction

- Regarding the sending email logic let's adapt it
  - We extract the sending logic in a new method

- We then adapt the `run` logic to "move up" the conditions for failure.

- Maybe, we should invert the name of the methods to avoid the negation (`!`) 
and adapt them.

- Our final code in the run method should be very clear.

>**Tip of the day: Prefer indenting failure paths to increase cognitive readability.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

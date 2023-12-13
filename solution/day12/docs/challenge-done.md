## Day 12: Make your code open for extension.

### Analyzing the design issue

Here if we need to a new `formality` we have to change the logic to 
add a new branch in the `greet` method...

Not an ideal design... Maybe, there is a better way to design this code 🤔

Instead, of those `if, else` we may have a high-level `Greeter` class 
that is instantiated with some `Personality`... we don't know which yet, 
just that it will be some object that implements the `Personality`.

We could imagine having different personality class: FormatPersonality,
CasualPersonality and IntimatePersonality.

### Apply the Open-Closed Principle
Know more about [S.O.L.I.D](https://www.freecodecamp.org/news/solid-design-principles-in-software-development/) and [C.U.P.I.D](https://dannorth.net/cupid-for-joyful-coding/).

🔴 Let's express this design from an existing test

We create the NewGreeter interface from our test

Our test is now failing for a good reason

🟢 Let's adapt the implementation to fulfill expectations from our test

🔵 Anything to refactor?

🔴 Let's adapt other tests

🟢 We create a new class and its method

We end up with a full set of tests on the new class.
After a while, we are ready to safe delete the old class Greeter (no usages).

### Last optimizations

- You can simplify the tests
- You can extract the magic strings

Now your code should be open for extension. Go ahead, try to add another 
greeting, say, KingGreeting returning "Your Majesty!"

>**Tip of the day: Analyze foreseeable changes to know where to refactor first.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

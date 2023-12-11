## Day 10: Dot not use "if" statement.

### Ways to break a condition.

Conditions can be broken through the use of collections and/or objects.

- Here, we may want to define a `Map` that could contain the mapping logic
  - It would have a signature like this : `Predicate<Integer>, Function<Integer, String>` -> `String`
  - It contains a `Predicate` to check if the input ensures it.
  - The value of the map is the corresponding value `function` in case of a match.

- Now let's use it in our `convert` method

The code should reflect the business requirements and be more maintainable.

>**Tip of the day: Avoiding conditions in your code will make it more open to extensions.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

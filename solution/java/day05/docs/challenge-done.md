## Day 5: No "for" loop authorized.

### Deconstructing "for" loops

Instead of for loops we can use functions on `List`

- We will leverage the power of our IDE to do this

- After removing the `for` loop, we can go further:
  - One way to do it is to not use a `StringBuilder` anymore but aggregating the `String`
  - We prepare our refactoring by extracting variables

### Optimizations

- We can extract some methods

- Then we move pet logic inside the new method
  - We make the logic independent of the `StringBuilder` (`Pure Function`)

- We can now replace the `forEach` by the `map` method (`Person` -> `String`)
  - Regarding the `lineSeparator()` logic we can use a collector

- We can simplify the `formatPerson` method as well
- The code should have simple methods for each format

### Vavr alternative
By using, alternative `collections` the code could be a little bit less verbose...

>**Tip of the day: Removing loops makes your code more reusable.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

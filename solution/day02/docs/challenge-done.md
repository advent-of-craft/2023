## Day 2: One level of indentation.

Here once again the IDE can help you organize your code.

### Decrease your code complexity

1) Start by inverting condition
2) Apply the same `safe` refactoring for the other `if` on input
3) We can remove the redundant else
4) We group the guard in the same condition

✅ Challenge of the day checked

But it's the Advent Of Craft so we can do much better

### More optimisations

- Fight magic numbers by adding functional names.
- Extract the core complexity (modulo) into a single method.
- Extract your guard logic into an easy to read private method.

>**Tip of the day: break the complexity of your code down into small chunks.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

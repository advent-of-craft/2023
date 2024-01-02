## Day 3: One dot per line.

### Identify the cognition issue

Look at the code used in the `whoOwnsTheYoungestPet` test

As a human being, how could it be possible to understand this code?
We read vertically and here everything is positioned horizontally...

We want to enforce the `1 dot per line rule` because:
- High cognitive load to understand the code
- No IDE support
- Hard to understand what is changed from our git log

Let's start by improving the code by simply add some return lines
- It is already simpler to understand...

- Now our IDE is able to provide us some help by displaying return types on lines

- How could we go further?
    - We still have several dot per lines
    - Let's use `Extract Method`
  
- Our code should have a simple method to return the age of the person's pet.

### Vavr alternative
By using, alternative `collections` the code could be a little bit less verbose...

>**Tip of the day: your main effort in any refactoring should be on naming and vertical cognition.**

### Share your experience

How does your code look like? Anything you'd like to add?

Please let everyone know in the discord.

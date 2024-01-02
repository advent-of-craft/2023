# Day 23: Refactor the legacy code after putting it under test.
Refactor the `TripService` class to ensure Clean Code / SOLID principles

Legacy code golden rules :
`You cannot change any existing code if not covered by tests.`

The only exception is if we need to change the code to add unit tests, but in this case, just automated refactorings (via IDE) are allowed.

## Step by Step

We use different steps that are all described separately (step-by-step md files):

1. Cover the production code
2. Improve confidence in our tests
3. Refactor the production code
4. Other refactorings

## What did we use / learn ?
- `Seams`
- `Automated refactoring` via our IDE
  - Extract Method
  - Change Signature
  - Rename
- `Mutation Testing`
- Use `Higher Order Functions` to avoid duplication
- Use `code coverage` as a driver
- `Test Data Builder`
- `Feature Envy`
- `Sprout Class`
- `TDD`

>**Tip of the day1: Start testing from shortest to deepest branch.**

>**Tip of the day2: Start refactoring from deepest to shortest branch.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.

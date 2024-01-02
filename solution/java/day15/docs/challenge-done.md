## Day 15: Put a code under tests.

Imagine we need to adapt the code below to support a new document template
 - We want to be sure to not introduce regression / have a safety net

### Assessing the right type of tests

- Let's add some tests
  - We have plenty of possible combinations
  - We could use `ParameterizedTests` to make those assertions

### Use Approval Testing instead

We can use `Approval Testing` to quickly put legacy code under tests.

It is also called : `Characterization Tests` OR `Golden Master`
- Unit testing assertions can be difficult to use and long to write
- Approval tests simplify this by taking a snapshot of the results / confirming that they have not changed at each run

Let's set that up:
- We add [ApprovalTests](https://github.com/approvals/approvaltests.java) dependency in our pom
- Add `.gitignore` file to exclude `*.received.*` from git
- Instead, let's use the power of Approval !!!
- We can generate combinations and have only 1 test (golden master) using `CombinationApprovals.verifyAllCombinations`

How it works:
- On the first run, 2 files are created
  - `DocumentTests.combinationTests.received.txt`: the result of the call of the method under test
  - `DocumentTests.combinationTests.approved.txt`: the approved version of the result (approved manually)
- The library simply compare the 2 text files, so it fails the first time you run it
- It compares the actual result and an empty file

- We need to approve the `received` file to make the test passes
  - Meaning we create the `approved` one with the result of the current production code

### Refactoring time
- We can even improve the test by making it totally dynamic
  - If we add a new Enum entry the test will fail
  - Forcing us to approve the new version of the test output

- In just a few minutes, we have successfully covered a cryptic code with robust tests

>**Tip of the day: Choosing the right type of test can help you gather better feedback on your code.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.

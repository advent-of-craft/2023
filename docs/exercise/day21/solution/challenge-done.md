## Day 21: Refactor the tests and production code to Output-based tests.

### Refactor to Output-Based
- Instead of hiding side effects behind an interface and injecting that interface into `AuditManager`, we can move those side effects out of the class entirely :
  - `AuditManager`: responsible for making a decision about what to do with the files
  - A new class, `Persister` acts on that decision and applies updates to the filesystem

#### AuditManager
In terms of contract we would like something like this:
- `FileUpdate addRecord(FileContent[] files, String visitorName, LocalDateTime timeOfVisit)`
- We choose to write the code `from scratch` aside from existing code
  - When we do it on legacy code, it's called [`Sprout Class`](https://understandlegacycode.com/blog/key-points-of-working-effectively-with-legacy-code/#1-the-sprout-technique)

🔴 Let's express this expectation from a test

- Of course, we do not compile here...
  - We have just prototyped a first version of what we might want (it can evolve while writing the code)
- We generate the classes from our test
- Here is the generated skeleton

🔴 The test is still red but for a good reason: because of the assertion (no compilation error anymore)

🟢 Make it pass as fast as possible

- We hard code the value for now
  - We can check that our flow is correct

🔵 Let's refactor it

- Based on the existing logic we would like to design something that looks like this:
  - sortFiles -> formatDate -> createRecord -> createFileUpdate (based on maxEntriesPerFile)
- We will iterate on the code
- Our test is still green, so let's move on
- We improved the algorithm
- Other tests are missing to `triangulate` the rest of the implementation
  - Let's add them incrementally

> It is the same logic here for this test case

- We refactor the tests before adding a new test case

🔴 Let's add a test that writes in the `Current Audit File`

- Of course, we need to triangulate our algorithm with this test

🟢 Iterate on the `addRecord` method

🔴 By incrementing on the code, we have broken a test `addsNewVisitorToANewFileBecauseNoFileToday`

- We fix it as fast as possible to go back in a safe state

🔵 It's time to play 🥳

- We extract some methods and rename some variables
- We can definitely improve the `createNewFileOrUpdate` method
- We have lost some features in the battle
  - Let's implement the `Persister` class

#### Persister - Bonus
The Persister is responsible for
- accessing files from a given Directory
- applying update instruction (a.k.a update the file content)

#### AddRecordUseCase
We need to have some glue to control the flow of our application. Let's create a `UseCase` for that.

Our logic is now in a class of its own using the Persister as a dependency.

>**Tip of the day: Isolate your business logic from dependencies outside your system.**

### Share your experience

How does your code look like?

Please let everyone know in the discord.

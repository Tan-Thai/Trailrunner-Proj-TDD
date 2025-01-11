# TDD-Diary

## Summary

## Introduction

The general structure of the diary will follow a daily chapter including the goals and solutions to said goals.
Reflections and comments will be added as deemed necessary for clarity. Each day will in most cases start with a checklist
to assure that no side-quests are made. 

The entire project is set up as a repository on GitHub for version control and ease of access.
Maven and Jacoco is used within the project as of writing. If VG assignments are made then Mockito was used as well.

Preface: I did work with **FileStorage** right away and then reformated it to suit my needs. I'll add it again
if I do work on the VG part of the assignment.
---

---
## Day 1 - 08/01-25

- [x] Set up repository and skeleton to start project.
- [x] Write up initial mockup of diary.
- [x] Start feature-branch.
- [x] Create **Record** class and it's tests.
- [x] Implement tests for **FileStorage**. (now named **RecordHandler**)
- [x] Adjust **FileStorage** until functional according to tests. (now named **RecordHandler**)
- [x] Create a **RecordHandler** that contains a Map to use ID as keys to avoid duplications.
- [ ] Create a **Calculator** for all formulas and math needed for the project.
- [ ] Create a **Main** that will allow the user to input and use the program.
 

### Agenda
Create a **RecordTest** file to create tests which includes all variables included in Records (variables based on
parameters from **FileStorage**).

After baseline tests are made, create **Record** class and shape it to fit tests.

---
### Fleeting Notes
Record might not be the best word for the files, not sure what to call a "saved trip/run/walk". As much as I want to
name it "session", it might does not sound entirely correct either.

### RecordTests
Noticed that I did write a few scuffed initial drafts for the tests due to not having coded for about 2 weeks.
Note to self to make sure to stay on top of coding whenever on break, even if it's just a little bit of code.

Example of issues were:
- The initial structure.
- Asserts having the expected/actual in swapped positions.
- Somehow forgetting the () when calling to methods. (I'm honestly baffled that it just slipped my mind)
- I missed that id was a string initially. (I blame it on new year)

### FileStorage
Will rename this file to **RecordHandler** to simplify the naming-scheme. Also, easier to identify what it's connected to
in my opinion.

<img style="float: right; margin: 5px; position: relative; top: 30px" src="https://media1.tenor.com/m/oWSUU3x-wL0AAAAd/cat-cat-meme.gif" width=180></img>
### RecordHandler

Might be overdoing it with throws and checks during the test, but I'm giving it a go to do a thorough test-setup
to see how far I can push my own knowledge of working TDD. (which I'm not used to)

Lots of new things came up such as writing tests for exception-handling and throws. Stumbled upon a few issues that I
had to resort to Stack Overflow. Based on the projects tests now, it seems to be fully functional and work as intended.
I'm still not feeling 100% confident regarding this so it will have to be something I work with more down the line.

---

---
## Day 2 - 09/01-25 to 10/01-25

- [x] Rename **Record** to something more relatable and self-explanatory. (Now **Session**) 
- [x] Create a **CalculatorTest** for all formulas and math needed for the project.
- [x] Create a **Calculator** to code in said formulas.
- [ ] Create a **Main** that will allow the user to input and use the program.
- [x] Create a **User** that will handle user data.
- [ ] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [x] Create tests for a singleton **Scanner**. Accidentally made prior to tests.

### Agenda
Continue from previous days leftovers and work from there. Goal is to ideally get the G assignments done by end of day.
If possible also add the advanced features into it before working on VG assignments.

Combined 2 dates into "day 2" because of my narcolepsy hindering me from working on the project for the majority
of 09/01.

---
### Fleeting Notes
As discussed with Max during yesterday's lesson, **Record** was not a fitting name for what we are trying
to save. Two potential names were "**Session**" and "**Activity**". Noted within agenda to keep track as of why
the change was made.

Debating if "TotalFitnessScore" should be a value stored with SessionHandler or User. If kept with the handler then it's
stored with all the sessions it has within the collection, logical. But since it's a fitness app and revolves around
the user, then storing the score with the user also works. Especially if we want to enable user to "remove" sessions but
keep their score (or if they re-install the app from a new phone for example).

### Calculator
(✅)TODO: Set up all calculations needed for a session.

Calc consists of:
- Average speed
- Kilometres per hour/min
- "FitnessScore" (score based on session) \
  FitnessScore **needs** to show a positive integer and only whole numbers. Score starts at 0 if it's the **first**
  session.
- Total distance traveled across all sessions.
- Average distance traveled across all sessions.

#### Reflections
Creating tests for these as opposed to coding them up first is way trickier due to the need of "foresight"
for how the formulas will be used. All without coding it up. I'm fully aware that we only care about the output to assert,
but I'm the type that wants to understand how the process goes before making a tests. It helps me visualise it better.

The first few tests worked fine but FitnessScore was a bit more troublesome to make up. Both due to it
having 2 conditions over it being the first ever session or not, and the fact that it needs to make use of
"days since the previous session" for the formula. This means that you can't just send in a single session but now needs
either 2 latest sessions to calculate the days between, or send in the whole handler and extract the 2 latest sessions.

*Extended break between previous text and following (about 18h)*

The calculation between the "days since previous session" was the most troublesome to write tests for and code up.
The rest were on an acceptable level and got sorted quite fast. I do think I need more practice to *not* think about each
individual code and just work with the logic of input/output. I consider calculator to be complete for now though, there
are places I can improve or make better. But I would like to get a proper MVP before refactoring something that already 
works.

### User

User will be kept barebones for now and only contain whatever the assignment asks for + name.
Test are there to just ensure that setting and getting the names works as intended.

### Main

I've had somewhat of a hard time with creating tests for main. One reason being that I'm not sure if I should create
a second file called **MenuHandler** to essentially create/handle all types of menu system that will be needed for the 
different actions the user shall be able to make. The second reason is that it requires a lot of "human" input through
scanner. My initial thought would be to use "System.setIn", similar to ScannerSingletonTest, but I've been contemplating
**Mockito**. The idea is to essentially "mock" the inputs and test from there. If it does turn out working well then I'll 
most likely proceed to adjust the old tests to fit it. Will probably do a refactoring pass for all code eventually.

I'm proceeding with creating a **MenuHandler** file. It feels logical to push menu systems and switch cases to a separate file.
I'll also try to add **Mockito**. Mockup of Mockito tests are done, I'm afraid to say that I think a Singleton has come
to bite my ass once again. I need to figure out how to replace the singleton with my mock without causing issues.
I'll most likely find some solutions from StackOverflow and patchwork it somehow.

_rant_: I **hate** singletons, christ. ok I'm taking a break from the entire project due to frustrations.
<img style="float: right; margin: 5px; margin-bottom:" src="https://media1.tenor.com/m/X9pghVUlMvEAAAAd/sad-cat.gif" width="190" alt="A very sad and depressed cat."></img>

#### Reflection

After a longer than expected nap, I've decided to redo the entire scanner and all it's tests. I'll try to make as many
commits as possibles to show the changes, but I'm fully expecting myself to just forget to do it in the midsts of refactoring.

This and all leftover code/plans will be pushed to day 3.

---

---

## Day 3 - 11/01-25

- [ ] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [ ] Create a **Main** that will allow the user to input and use the program.
- [ ] Reformat/Re-do **Scanner** in its entirety. (Removing Singleton)
- [ ] Complete the writing of tests for **MenuHandler**.
- [ ] Develop **MenuHandler** based on tests.

### Agenda

Today's biggest goal is to get **MenuHandlerTest** fully functional with tests based on **Mockito**. To do that
I need to refactor **ScannerSingleton** and its tests since I believe that it's the biggest cause based on the error
messages. 

Snippet: "Mockito is currently self-attaching to enable the inline-mock-maker.
This will no longer work in future releases of the JDK..."

I've tried to solve it, but it turned out to be quite convoluted and required a JVM argument and IDE specific settings.
For me that's a no-go because of the sole principle that it would require setup from new users/workstations. My ideal
solution is that it should be self-sufficient and portable, able to be tested right away on download from GitHub.

A commit will be made before the large scale changes to create a backup in case I totally break it, or I find a solution
to the singleton issue down the line. Whichever comes first to be honest. (I'm quite frustrated with this to be honest)

---
###  Fleeting Notes

The amount of extra research and frustration I get with scanner is baffling. I get why it is the way it is but my god
does it feel _extra_ to handle. Both in how sensitive it is and how different the tests are made for it.

### ScannerWrapper

I'll be renaming **ScannerSingleton** to **ScannerWrapper** due to the removal of singleton, but also clarity. Since 
this class will encompass all the scanner related functions. This is a bit of an odd situation since I do work with
the mental image of how I've created the previous Scanner files in past projects. So the test might now be totally free
of "bias" as they normally would. 

I'll be splitting tests into invalid/valid or yes/no to create a more clear labeling of what it tests.

Methods will be similar to my previous projects, setup with validations and forced loops to ensure correct returns.

#### Reflections

the fact that I had to capture a whole string and convert it because it contained a single "\n" is crazy. I sat and tried
to find the issue for so long and the issue was that ".contains" method couldn't differentiate and read a \n the same way.
The entire process was quite a trial and error on top of searching for answers.

I did forget to commit between creating code and writing tests. It is mostly due to me jumping back and forth developing
1 singular method after each test. Especially due to how finicky scanners are.

I did solve the issue with the assertTrue to a printing of an error message not passing. It had to do with me not creating
a stream for **err** prints specifically. Which I did not expect to be 2 whole separate channels/streams. It has now 
been fixed by also generating a stream for it specifically. I have an ish understanding but not 100%. Will have to play
around with this further to grasp it completely.
<img style="float: right; margin: 5px" src="https://media1.tenor.com/m/rNCdBEqBKjoAAAAd/sad-cat.gif" width="200px"></img>

Tried being somewhat smart with being DRY, only to have that backfire. Had to add `scannerWrapper = new ScannerWrapper();`
in each method instead of once in `@BeforeEach`. Not totally sure why that is but will experiment with it.

Adding messages after each test, or at least trying to since it helps to see where the tests fail and the cause.

Final words regarding scanner; it's not fully covered yet but the important tools for inputs are sorted. I once again
want to say that I **do not** like scanner now after this roller-coaster.


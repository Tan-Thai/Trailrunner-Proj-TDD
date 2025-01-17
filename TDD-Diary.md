# TDD-Diary

## Summary

Important notes: automatic date will be set if it finds an empty whitespace, if *nothing* is entered then you will
hit the error handler for string-input.

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

<img style="float: right; margin: 5px;" src="https://media1.tenor.com/m/oWSUU3x-wL0AAAAd/cat-cat-meme.gif" width=180></img>
### FileStorage
Will rename this file to **RecordHandler** to simplify the naming-scheme. Also, easier to identify what it's connected to
in my opinion.

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
<img style="float: right; margin: 5px; " src="https://media1.tenor.com/m/X9pghVUlMvEAAAAd/sad-cat.gif" width="190" alt="A very sad and depressed cat."></img>

_rant_: I **hate** singletons, christ. ok I'm taking a break from the entire project due to frustrations.

#### Reflection

After a longer than expected nap, I've decided to redo the entire scanner and all it's tests. I'll try to make as many
commits as possibles to show the changes, but I'm fully expecting myself to just forget to do it in the midsts of refactoring.

This and all leftover code/plans will be pushed to day 3.

---

---

## Day 3 - 11/01-25 - 12/01-25

- [ ] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [x] Create a **Main** that will allow the user to input and use the program.
- [x] Reformat/Re-do **Scanner** in its entirety. (Removing Singleton)
- [ ] Complete the writing of tests for **MenuHandler**.
- [x] Develop **MenuHandler** based on tests.

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
<img style="float: right; margin: 5px" src="https://media1.tenor.com/m/rNCdBEqBKjoAAAAd/sad-cat.gif" width="200px"></img>
1 singular method after each test. Especially due to how finicky scanners are.

I did solve the issue with the assertTrue to a printing of an error message not passing. It had to do with me not creating
a stream for **err** prints specifically. Which I did not expect to be 2 whole separate channels/streams. It has now 
been fixed by also generating a stream for it specifically. I have an ish understanding but not 100%. Will have to play
around with this further to grasp it completely.

Tried being somewhat smart with being DRY, only to have that backfire. Had to add `scannerWrapper = new ScannerWrapper();`
in each method instead of once in `@BeforeEach`. Not totally sure why that is but will experiment with it.

Adding messages after each test, or at least trying to since it helps to see where the tests fail and the cause.

It also just hit me that I totally forgot the reason why I even wanted to do Mocks out of these.
This might be the 1 bad reason to goblin code at night. I also noticed that Jacoco is not picking up my tests done
for `ScannerWrapper`

Final words regarding scanner; it's not fully covered yet but the important tools for inputs are sorted. I once again
want to say that I **do not** like scanner now after this roller-coaster. 

---
***
## "Day 4" - 01/14-25

- [ ] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [ ] Complete `MenuHandler`'s functions (few adjustments left)
- [ ] Code cleanup, remove old redundant code or non-used code.
- [ ] Create new function in `ScannerWrapper` for `MenuHandler` methods. (tests too)

### Agenda

Ideally get done with all the G related assignments today if I have missed any. 
## MenuHandler

I'll be looking into mocking up the other classes to make the tests independent from `Session` and `SessionHandler`.
Though I will _start_ with writing test in a way I know works to get the project to move forward.

The idea is that the menu handler will be printing instructions and menus, and ask for the input.
One example being `searchSessionById`, this method should be called when the user selects "search for session" within
a menu and then be prompted to input text to search for. From here it will branch into 2 cases. Either a list of matching
sessions are printed and the user can then proceed to pick one to show details of, or no matches are found, and you will be notified
regarding it.

### Reflection

I ended up essentially making identical tests and methods as `sessionHandler`. Creating a new commit to start from a clean
slate with empty tests.

Trying to break down the tests into smaller bits, in this case _only_ checking for whatever prints are happening.
Reasoning behind that is due to us already having tests for the manipulation of sessions within `SessionHandlerTest`.
I'll add more tests/code to `SessionHandlerTest` now when I have more functions I want to implement.

## Misc - Ramble Text
Sorting sessions method done, `getSortedSessions`. I stumbled upon a few good explanations across google and will do a 
quick deep dive to *attempt* at making this sorting flexible based on what the user picked. Not going to implement it
fully to the actual project, but I want to see if it's possible with `.sorted()` call. Initial idea is to make an
Enum class that contains all types of comparators like comparing `(s1, s2) -> Double.compare(s1.getDistance(), s2.getDistance());`
for example. The general structure is the same, but you are just comparing different values!

**LETS GOOO, IT WORKED!**
I was not too sure if it would work, but it seems to work suuuper smoothly. This means that I could in theory let the 
user choose a sorting method with a switch-case and then pass it on to `getSortedSessions`. I won't focus on it now
though due to it not being relevant to the project. Tests to assure it works are there as well just as a FYI.

I'm diving back into singletons, I can't believe that I'm doing that... 
Update: jokes on me, it's still horrid.🙃 I'll temporarily pass the scanner for now, refactoring it later should not
be too difficult this time around due to the smaller project scale.

## Integration of classes

Whilst testing for singletons and scanner shenanigans I also started refactoring `User`. The user now includes a collection
of sessions (`SessionHandler`). This is both because of simplicity of parameters, and it being quite logical to have
the collection tied to a user. I've now started working on `MenuHandler` again for the 10000th time. I'll be honest and say
that I'm somewhat lost over how to write the tests. 

### Reflection
I do think my excessive googling has backfired due to the sheer amount
of different opinions, styles, versions both testing and mockito have. I went through so many versions of mockito to try
figure out the issue to no avail. I'm not getting any compilation errors anymore which I'm glad for, but at the cost of
a super messy project. This can be seen by my mixture of variable naming, code structure. 

---

---
## Day "4" - 13/01-25 (Will most likely just put the dates as titles due to days not fitting) 

- [ ] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [x] Refactor `MenuHandlerTest`, and add more tests
- [ ] Complete `MenuHandler`'s functions
- [x] Hate on Scanner a bit more
- [x] Look into mocking further and refactor accordingly.

### Agenda 

Goal for this part would be to finish up the `menuHanlder` so that the project will be an actually functional program.
The methods in question will be mostly menu systems to redirect to appropriate method calling, such as add session, that
will have its own followup of prompted inputs to fill in the details of a session.

## Fleeting-Notes

I feel like I've worked somewhat backwards with this, or at least I should've looked more into mockito properly since 
I had some gigantic troubles with **Scanner** and mocking. Which caused me to go down a rabbit hole of cursed stuff.
The idea of mocking for `menuHandlerTest` is to avoid essentially testing whatever scanner already have. We should not
care about the logic and if the return is correct, but rather the outcome of the entire method.

Totally forgot to check off the lil checklist. Most of the documentation are within the text and my rants!

---
## MenuHandler (again)

Refactored the entire `MenuHandlerTest` and it now incorporates mocking, specifically scannerWrapper. Tests works properly
now and I can proceed to make more tests for the branching menu. I won't cover all the branching paths if they call for a similar
method/ same method with another argument. 

Deep diving into the furthest branch to develop first due to me technically branch testing all the way
to reach that endpoint. So I will test "some" branches more due to me passing through menus. In this case deleting
a session whilst viewing the details

**Order of menus:**
Main Menu > session menu > view all sessions > detailed session view > delete method. 

By going this way I won't need to create tests for the menus themselves, but more if they accomplish the goal of 
passing me through to the right methods. If I went the other way around, then some of these tests would become redundant.
**TDLR**: Menus are a means to an end.

Test created and fully functional. Started with working on the method itself as a public call. Then worked my way
outwards towards `runMainMenu()`. That way the preceding menus will be created and tested along the path but not fully
developed due to missing method calls within the other cases.

With the deletion sorted, I'm now more confident in writing tests for this type of code. Going over to creation and 
searching for sessions tests. Ideally I would like to assert that the `sessionHandler` properly executed the task
and assert that the output was correct.

Currently hitting a bit of a snag, I want to create more `scannerWrapper` methods to fulfill unique needs
such as `dateInput()` or `minutesInput()` that would parse input and return proper data to create
a session. Will try to make a functioning base for now and then write tests for these methods and then develop them
<br/>✨(ITS SOON REFACTORING TIME)✨ <br/>

Baseline developed code are functioning, ironing out kinks I found and in the process of removing some redundant methods
such as `printAllSessions` and `printQueryResults`. Both essentially do the same thing, and I can refactor 
`resolveSessionView` to take in an argument of `List<String>` so it would work with both the search logic and standard
view.

### Reflection
**MEGA PRODUCTIVE DAY** I might've still hated on scanner *but*, it turned out to work quite smoothly when you don't
try to solve both `byteArrayInput` and `Mockito` whilst trying to further progress your tests/code. Especially if
2 of the issues turned out to be standard warnings that are quuuuite *recent*...

I might work more today, but odds are not. Got an early hospital visit the next day (01/14), so I'll not push myself to
work anymore unless a 💡 hits.
---
***
## "Day 4" - 01/14-25

- [x] Advanced G assignments. (Kept as a single point for now)
- [ ] Start on VG assignments.
- [x] Complete `MenuHandler`'s functions (few adjustments left)
- [ ] Code cleanup, remove old redundant code or non-used code. (Partial)
- [x] Create new function in `ScannerWrapper` for `MenuHandler` methods. (tests too)

### Agenda

Ideally get done with all the G related assignments today if I have missed any. Most of the heavy work is sorted
and passed their respective tests. When G is done, look into VG assignments and determine if it's easier to implement
*before* or *after* code clean-up. Before that is definitely adding tests and code for `ScannerWrapper` to preserve
the class logic of single responsibility.

### Fleeting-Notes

Once again, I'm struck with narcolepsy and now started working at 23:27 on the 14th... I feel like the "Day x" for me does not 
work at all due to this.🙃

Added a few `minutes > seconds` methods and the inverse to avoid faffing with writing 10023 seconds. It feels more
normal to input how many minutes you have been exercising.

Missed to write a try/catch block in `MenuHandler > resolveSessionCreation()`, also added `id.tolower()` for the hashmap
to ensure that the key's stay the same.

Not going to lie, `menuHandler` is handling **A LOT**. It should have been split into multiple classes. But it was 
created with *hate* and *despair*... I'll ~~maybe~~ fix it if I find the time. But it's a cursed child of mine that was 
made during the projects darkest times and shall forever be stained as such.👀 I'll at least sort it somewhat with regions.

Noticed some small spots I've missed due to only seeing green tests. It was more UX/UI related or logic that the tests
*shouldn't* cover. I would say the reason for that is because so much is tied to the menu logic that is not related
to the assignment.

### ScannerWrapper

Added tests for valid date input `checkIfValidDate`. Might change the rest of input validations to reflect a specific
assignment if the need is specific it enough, such as date, double/int instead of casting (int) etc.

All of that is lower prio due to not being related to assignment as a whole, back to `MenuHandler`!

I've made use of `scannerWrapper` to ensure that a date always is returned, even if someone enters a **blank**
space. `dateInput` will automatically return the current time if no other *proper* date is given.
**if** they somehow manage to enter nothing for date, then I created a constructor for session that does not
take in a `LocalDate` which sends `LocalDate.now()` to the date. Tests was also made ahead of time. 

Another test added is `dateInputTest_BlankInput()` in `ScannerWrapper`.

### MenuHandler

I'll have to restructure the menu slightly to accommodate `showUserInfo()` within the main menu. Multiple tests
will most likely fail due to mocking of menu choices changing.

Tried to create an adaptable mock that counted switch-cases(choices) and sent in an `int` to `numberInput` to add that
as a max limit. The issue is that I then would have to remake the entire structure of Mock/when inputs for each
singular menu (since they are all different in amounts). For the sake of the assignment, I'll not work on this function
despite knowing that people can enter a number that would result it in being out of bounds.

I can do a `while(true)` forced loop during wrong input. But that would also lead to multitudes of
mocked `0`inputs to step out one menu after the other.

Need to add an option pre search or post search to sort list based on time/distance. <br/>
I decided to change the logic instead, sending in restructured session based on search into `viewSessionList()`.
From there on out it loops within itself and re-arranges the list as needed depending on the choices of the user.
The one drawback would be that you have to exit manually instead of getting lobbed to the out-most menu. 
`changeSortMethod_SessionPrintTest()` tests this functionality and by proxy, ensuring that one of the 6 sort
methods are functional.

### InputLimit
Added a quick enum for the character limit for certain inputs. Only name and session are implemented due to ease of change.
Int would most likely rely on me changing a few things with how int works, and that would in turn cause quite an effect
on the whole system. Ofc tests help minimise the time spent, but we also have to consider the fact that it's out of 
scope of our assignment.


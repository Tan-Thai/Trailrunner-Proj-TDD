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
- [ ] 
 
### Fleeting Quick-Notes
Record might not be the best word for the files, not sure what to call a "saved trip/run/walk". As much as I want to
name it "session", it might does not sound entirely correct either.


### Initial plan
Create a **RecordTest** file to create tests which includes all variables included in Records (variables based on
parameters from **FileStorage**).

After baseline tests are made, create **Record** class and shape it to fit tests.

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

### RecordHandler

Might be overdoing it with throws and checks during the test, but I'm giving it a go to do a thorough test-setup
to see how far I can push my own knowledge of working TDD. (which I'm not used to)

Lots of new things came up such as writing tests for exception-handling and throws. Stumbled upon a few issues that I
had to resort to Stack Overflow. Based on the projects tests now, it seems to be fully functional and work as intended.
I'm still not feeling 100% confident regarding this so it will have to be something I work with more down the line.
# Learning Kotlin using Kotlin Koans

We are in this workshop going to use the Kotlin Koans learning framework to help you learn Kotlin. Kotlin Koans is a series of exercises made by JetBrains to teach developers Kotlin syntax. In Itera we believe in using open sourced learning materials and decided therefore to use Kotlin koans in this workshop, why invent the wheel if it already exists as we say in Norway.

You can either solve the Koans online: https://play.kotlinlang.org/koans, install the EduTools plugin in IntelliJ or clone this repository. We recommend you to use the EduTools plugin for the full IDE features or use the online editor. However, you can also clone this repo and run Gradle to solve the tasks. See below for further instructions on installations and use, no instructions are provided for the Online editor.

Select your prefered way of solving the Koans:

## Using plugin

1. Open IntelliJ IDEA and press `⌘,` on macOS or `Ctrl+Alt+S` on windows/linux to open the `Settings | Preferences` dialog and then go to Plugins.

2. Search for the EduTools plugin and install it.

3. Click OK in the dialog and restart IntelliJ IDEA.

4. Select Join on Kotlin Koans from the `Learn | Browse courses` menu

When running the plugin you will be presented with the task list, a code editor and a description for the selected task. In order to solve the tasks you are generelly required to replace the function body to produce desired results and make the associated unit tests run succesfully. You can run and check the task by clicking the `Check` button under the task description.

In the first example, to solve the tasks means replacing the `TODO()` part of the code

```kotlin
fun start(): String = TODO()
```

with the correct, meaningful code to solve the problem and allow the associated unit test to pass, such as:

```kotlin
fun start(): String = "OK"
```

## Cloning this repo

1. Clone this repository with `git clone https://github.com/Itera/kotlin-v19.git` from a terminal in your desired folder.

### Working with the project using Intellij IDEA:

1. Import this project as Gradle project.
2. To build the project and run the tests use `test` task on the Gradle tool window 
(`View | Tool Windows | Gradle`). 

Here https://www.jetbrains.com/help/idea/gradle.html#gradle_tasks you can read 
how to run Gradle task from the Gradle toolbar in IntelliJ IDEA.

### Working with the commandline

You can also build the project and run all tests in the terminal:
```
./gradlew test
```
But since running all the tests tend to take longer and the output can be
cluttered, it's more ideal to run selected tests only:
```
$ ./gradlew test --tests i_*          # run tests in part 1
$ ./gradlew test --tests ii_*         # run tests in part 2
$ ./gradlew test --tests ii_*22*      # run test number 22 in part 2
```

## How the tasks are organized
 
You have 42 tasks to do. 
Each task lives in its own function: from `task0` to `task41`.
For each task, there is an associated unit test that checks your solution.
 
You may navigate to the corresponding test automatically when you read the task.
Open the source file with the task and use the action `Navigate -> Test` to open the test file. 
You may also use `Navigate -> Test Subject` for reversed navigation. 
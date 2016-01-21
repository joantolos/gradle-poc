# Gradle POC

Just a simple POC to experiment with Gradle capabilities

## Create the IDE project

Adding this to the **build.gradle** file:

    apply plugin: 'idea'

and then from command line:

    gradle cleanIdea

    gradle idea
    
At this point, the typical .idea and .iml files are created so you can open the project with IntelliJ. You can use Eclipse as well, just change 'idea' to 'eclipse'.
    
## Building the code

Just type

    gradle build
    
## Executing the code

Just type

    java -cp gradle-poc-core/build/classes/main/ com.joantolos.gradle.poc.core.HelloGradle

## Executing task

You can define tasks on the Gradle build file, like this one:

    task greetings {
        def command = "java -cp gradle-poc-core/build/classes/main/ com.joantolos.gradle.poc.core.HelloGradle"
        def process = command.execute()
        process.waitFor()
    
        def output = process.in.text
        println(output)
    }

Just use **Groovy** language to define any script you need. Groovy is a JVM language, more info: http://www.groovy-lang.org/

To execute a task, you can type:
    
    gradle greetings
    
Gradle is creating an internal object model of your build before executing it. Every task you declare is actually a task object contained within the overall project. A task object has properties and methods just like any other object.

## Default tasks

If you type:

    gradle tasks
    
You will see all available tasks:
 
     ------------------------------------------------------------
     All tasks runnable from root project
     ------------------------------------------------------------
     
     Build tasks
     -----------
     assemble - Assembles the outputs of this project.
     build - Assembles and tests this project.
     buildDependents - Assembles and tests this project and all projects that depend on it.
     buildNeeded - Assembles and tests this project and all projects it depends on.
     classes - Assembles main classes.
     clean - Deletes the build directory.
     jar - Assembles a jar archive containing the main classes.
     testClasses - Assembles test classes.
     
     Build Setup tasks
     -----------------
     init - Initializes a new Gradle build. [incubating]
     wrapper - Generates Gradle wrapper files. [incubating]
     
     Documentation tasks
     -------------------
     javadoc - Generates Javadoc API documentation for the main source code.
     
     Help tasks
     ----------
     components - Displays the components produced by root project 'gradle-poc'. [incubating]
     dependencies - Displays all dependencies declared in root project 'gradle-poc'.
     dependencyInsight - Displays the insight into a specific dependency in root project 'gradle-poc'.
     help - Displays a help message.
     model - Displays the configuration model of root project 'gradle-poc'. [incubating]
     projects - Displays the sub-projects of root project 'gradle-poc'.
     properties - Displays the properties of root project 'gradle-poc'.
     tasks - Displays the tasks runnable from root project 'gradle-poc'.
     
     IDE tasks
     ---------
     cleanIdea - Cleans IDEA project files (IML, IPR)
     idea - Generates IDEA project files (IML, IPR, IWS)
     
     Verification tasks
     ------------------
     check - Runs all checks.
     test - Runs the unit tests.
     
     Other tasks
     -----------
     cleanIdeaWorkspace
     greetings
     
Take a look at "IDE tasks" where you can find some utilities for your IDE (works with Eclipse as well), and the "Other tasks" at the end, when you can find the task "greetings" manually defined on the **build.gradle** file.

You can use clean and build as you will do on maven: `` gradle clean build ``

You can also run `` gradle jar `` to build the jar artifact and the run `` java -jar build/libs/gradle-poc-1.0-SNAPSHOT.jar `` 

In order to make the jar ready to be execute, you will need to create the MANIFEST.INF file with the class containing the main method. You can do that directly on the build file:

    jar {
        manifest {
            attributes(
                    "Main-Class" : "com.joantolos.gradle.poc.HelloGradle"
            )
        }
    }

## Dependencies

Let's add some random dependency. As an example, we will use the GSON library from Google which offers several operations to work with Json:

    dependencies {
        testCompile group: 'junit', name: 'junit', version: '4.11'
        compile group: 'com.google.code.gson', name: 'gson', version: '2.5'
    }

With the dependency line on the build file, you can do the imports from the library correctly. You can see that on the class **GsonTest**

## Multi - Module project

There is two modules on the project, core and utils. On the "parent" build file, you can define behavior for "all projects" and also for the subprojects:

    allprojects {
        version '1.0-SNAPSHOT'
    }
    
    subprojects {
    
        apply plugin: 'java'
        apply plugin: 'idea'
    
        repositories {
            mavenCentral()
        }
    
        jar {
            manifest {
                attributes(
                        "Manifest-Version" : "1.0",
                        "Implementation-Version" : version,
                        "Main-Class" : "com.joantolos.gradle.poc.core.HelloGradle"
                )
            }
        }
    
        dependencies {
            testCompile group: 'junit', name: 'junit', version: '4.11'
        }
    }

The parent has to know about the children on the **settings.gradle** file:

    rootProject.name = 'gradle-poc'
    include 'gradle-poc-core', 'gradle-poc-utils'
    
Then the children DO NOT need to know about the parent and that's the most difference with a Maven multi-project. Then, you can define internal project dependencies like this:

    dependencies {
        compile project(':gradle-poc-utils')
    }

So, on the example, the utils component has the external Gson dependency and the core component has the internal dependency of utils. You can test that on the **GsonTest** class and the **JsonTest** class both on the core module.
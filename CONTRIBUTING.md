# Contributing to FaceRecognition

### Cloning this project
Make sure you have `git` installed. If you do not have `git`
installed, you can follow
[this tutorial] (https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).
Once `git` is installed, navigate (in your terminal) to the directory you
want to work in, and enter the following command:
```
git clone git@github.com:329Enjuneers/FaceRecognition.git
```
This command will clone the repository into your current directory. Type
`ls` to verify that you see a new directory, `FaceRecognition`.


### Adding this project to eclipse
Once you have cloned this project, open your eclipse application. Once opened,
go to `File > Import > Git > Projects from Git` then click next. Then, do:
 `Existing local repository > Next`. At this point, you will probably have to
 click `Add` (top right corner) to make eclipse aware of your project. Clicking
 add should open up a new popup. From this popup you can browse to find where
 your project was downloaded, and you can add it to eclipse. After adding it,
 this popup should close. The project should now be visible in the original
 popup, which you can then select. After selecting and clicking `Next`, you're
 all done!


### A basic understanding
A tutorial for creating a Google App Engine java application can be found
[here] (https://cloud.google.com/appengine/docs/java/tutorials). Going
through these tutorials will probably help you understand how to contribute
to this application.

### Running this application locally
You will need to [download and install the Java SE Development Kit (JDK)] (http://www.oracle.com/technetwork/java/javase/downloads/index.html).
You will also need to [download and install Apache Maven]
(https://maven.apache.org/download.cgi).
Once this is done, you can navigate to the project, and type:
```
mvn clean package
```
This will build the project.
Assuming you get a successful build, you can then run:
```
mvn appengine:devserver
```
Which will spin up an instance of our application locally. You can navigate
to this application by navigating to
[http://localhost:8080/] (http://localhost:8080/) in your browser.

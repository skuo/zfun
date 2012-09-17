# advanced options for multiple modules

-rf, –resume-from
        Resume reactor from specified project
-pl, –projects
        Build specified reactor projects instead of all projects
-am, –also-make
        If project list is specified, also build projects required by the list
-amd, –also-make-dependents

$ mvn --resume-from sample-client-connector install
$ mvn --projects sample-client-connector,sample-rest install
$ mvn --projects sample-services --also-make install
$ mvn --projects sample-services --also-make-dependents install

$ mvn -f tdb.pom.xml clean install
$ mvn -f adsvc-pom.xml clean install

$mvn -t 2.0C clean install

# install
mvn install:install-file -Dfile=lwes-java-0.2.4.jar -DgroupId=org.lwes -DartifactId=lwes-java -Dversion=0.2.4 -Dpackaging=jar

# jetty debug option
Here's my MAVEN_OPTS setting,  then you can debug local web app started by "mvn jetty:run"
Remote debugging to localhost:4000
MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=512m -Xdebug -Xrunjdwp:transport=dt_socket,address=4000,suspend=n,server=y"
[![Build Status](https://travis-ci.org/nikos/spring-boot-quartz-mongodb.svg?branch=master)](https://travis-ci.org/nikos/spring-boot-quartz-mongodb)

# spring-boot-quartz-mongodb

Sample Spring Boot application using Quartz scheduler with MongoDB for persisting job state.


Inspired by work on this topic from
[Arvind Rai](http://www.concretepage.com/spring-4/spring-4-quartz-2-scheduler-integration-annotation-example-using-javaconfig)
and
[David Kiss](https://github.com/davidkiss/spring-boot-quartz-demo).

Alternative approach
by [Mark Taylor](http://www.themoderngeek.co.uk/software-development/2015/02/01/quartz-scheduler-part-2.html)
using Spring Bean XML configuration files.


### Development & Debug

To start the application (with embedded Jetty) in debug mode, just call:

    ./gradlew bootRun --debug-jvm

Per default JDWP is listening on port 5005 per dt_socket transport.

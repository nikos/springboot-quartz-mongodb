[![Build Status](https://travis-ci.org/nikos/springboot-quartz-mongodb.svg?branch=master)](https://travis-ci.org/nikos/springboot-quartz-mongodb)

# spring-boot-quartz-mongodb

Sample Spring Boot application using [Quartz scheduler](http://quartz-scheduler.org/)
with [MongoDB for persisting job state](https://github.com/michaelklishin/quartz-mongodb).

Inspired by work on this topic from [Arvind Rai](http://www.concretepage.com/spring-4/spring-4-quartz-2-scheduler-integration-annotation-example-using-javaconfig)
and [David Kiss](https://github.com/davidkiss/spring-boot-quartz-demo).

Alternative approach by [Mark Taylor](http://www.themoderngeek.co.uk/software-development/2015/02/01/quartz-scheduler-part-2.html)
using Spring Bean XML configuration files.


### Development & Debugging

To start the application (with embedded Jetty) in debug mode, just call:

    ./gradlew bootRun --debug-jvm

Per default JDWP is listening on port 5005 per dt_socket transport.


### MongoDB maintenance

For cleaning up job and trigger definition, execute:

    mongo jobs-demo < cleanup-jobs.js


### TODO

* Allow integration testing by making use of [Fongo](https://github.com/fakemongo/fongo), see [Issue 77](https://github.com/michaelklishin/quartz-mongodb/issues/77) for inspiration
* Use [Jongo](http://jongo.org) to make Mongo database access more elegant (instead of direct access via MongoDB database driver)
* Migration in case job or trigger configuration has changed, see [mongeez](https://github.com/mongeez/mongeez)
* Discuss alternative approach to auto-wiring, but using job data map to transport payload
* Discuss more advanced Quartz features like failure recovery (misfire), job prioritization, see [spring-batch-quartz-example](https://github.com/ilya40umov/spring-batch-quartz-example) for inspirations
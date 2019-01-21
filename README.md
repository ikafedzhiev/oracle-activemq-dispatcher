# Oracle ActiveMQ Dispatcher
Oracle ActiveMQ Dispatcher is Java class for loading in Oracle EBS database to be used for dispatching Business Events messages to ESB (activeMQ brokers)

## Instructions
### Deployment Instructions
Copy jar file to application or database tier and load java classes + dependencies with loadjava tool

$ loadjava -v -r -u apps/apps -resolver "((* APPS) (*PUBLIC) (* -))" oracle-activemq-dispatcher.jar


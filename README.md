# Elasticsearch Demo
_Elasticsearch: v2.3.4, Grails: v3.2.4_, Java: 1.8.0_112

This is the sample application to demonstrate some example of using Elasticserach in a Grails project.

#### How to run the application?

In order to run, you need to make sure Elasticsearch is installed on your system and `ES_HOME` path variable is configured with Elasticserach home directory.

Also, you might need to update some configurations as per your need. For example: `cluster.name` should be equals to the name of elasticsearch cluster. 

Following are the minimum required configurations, to be added into `grails-app/conf/application.yml` as per my system: 

```yaml
elasticSearch:
    datastoreImpl: hibernateDatastore
    client:
        mode: transport
    cluster.name: elasticsearch_puneet
```

**Note:** Make sure that you indent the `yaml` configurations with spaces. 


#### List of Examples:


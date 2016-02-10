# Maintainers Guide

## How to publish libs

### Sonatype JIRA Account

You need to create Sonatype JIRA account to publish libs onto sonatype repos (and Maven central repos).

https://issues.sonatype.org/browse/OSSRH-15265

### $HOME/.m2/settings.xml for maven

```xml
<settings>
  <servers>
    <server>
      <id>sonatype-nexus-snapshots</id>
      <username>{SONATYPE_JIRA_ACCOUNT_USER_ID}</username>
      <password>{SONATYPE_JIRA_ACCOUNT_PASSWORD}</password>
    </server>
    <server>
      <id>sonatype-nexus-staging</id>
      <username>{SONATYPE_JIRA_ACCOUNT_USER_ID}</username>
      <password>{SONATYPE_JIRA_ACCOUNT_PASSWORD}</password>
    </server>
  </servers>
</settings>
```

### $HOME/.sbt/0.13/sonatype.sbt for sbt

```scala
credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  "{SONATYPE_JIRA_ACCOUNT_USER_ID}",
  "{SONATYPE_JIRA_ACCOUNT_PASSWORD}"
)
```

## Deployment

### Java library

```
mvn deploy
```

And then, access Sonatype's admin console:

https://oss.sonatype.org/

- click 'Staging Repositories'
- Select the artifact you deployed
- Click 'Close' button
- Wait a while...
- Click 'Release' button

### Scala library

Scala library depends on Java library. You must finish Java library release in front of Scala deployment.

```
brew install sbt
sbt +publishSigned sonatypeRelease
```

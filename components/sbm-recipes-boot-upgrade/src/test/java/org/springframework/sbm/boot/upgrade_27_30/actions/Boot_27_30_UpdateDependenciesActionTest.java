package org.springframework.sbm.boot.upgrade_27_30.actions;

import org.junit.jupiter.api.Test;
import org.springframework.sbm.build.api.Dependency;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.project.resource.TestProjectContext;

import static org.junit.jupiter.api.Assertions.*;

class Boot_27_30_UpdateDependenciesActionTest {

    @Test
    void test() {
        String pomXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                "  <modelVersion>4.0.0</modelVersion>\n" +
                "  <parent>\n" +
                "    <groupId>org.springframework.boot</groupId>\n" +
                "    <artifactId>spring-boot-starter-parent</artifactId>\n" +
                "    <version>2.7.0</version>\n" +
                "  </parent>\n" +
                "  <groupId>org.springsource.restbucks</groupId>\n" +
                "  <artifactId>restbucks</artifactId>\n" +
                "  <version>1.0.0.BUILD-SNAPSHOT</version>\n" +
                "  <name>Spring RESTBucks</name>\n" +
                "  <artifactId>test</artifactId>" +
                "  <pluginRepositories>\n" +
                "    <pluginRepository>\n" +
                "      <id>spring-milestone</id>\n" +
                "      <url>https://repo.spring.io/milestone</url>\n" +
                "      <snapshots>\n" +
                "        <enabled>false</enabled>\n" +
                "      </snapshots>\n" +
                "    </pluginRepository>\n" +
                "  </pluginRepositories>" +
                "</project>";

        ProjectContext context = TestProjectContext.buildProjectContext()
                .withMavenRootBuildFileSource(pomXml)
                .build();

        Boot_27_30_UpdateDependenciesAction sut = new Boot_27_30_UpdateDependenciesAction();
        sut.apply(context);

        System.out.println(context.getBuildFile().print());
    }
}
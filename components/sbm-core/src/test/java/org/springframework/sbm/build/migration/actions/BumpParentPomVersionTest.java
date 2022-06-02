package org.springframework.sbm.build.migration.actions;

import org.junit.jupiter.api.Test;
import org.springframework.sbm.build.migration.actions.BumpParentPomVersion;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.project.resource.TestProjectContext;

import static org.assertj.core.api.Assertions.assertThat;

class BumpParentPomVersionTest {

    @Test
    void bumpVersionWithNoParentShouldFailSilently() {
        String pomXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                        "  <modelVersion>4.0.0</modelVersion>\n" +
                        "  <groupId>com.example</groupId>\n" +
                        "  <artifactId>artifact</artifactId>\n" +
                        "  <version>1.0.0</version>\n" +
                        "</project>";

        ProjectContext context = TestProjectContext.buildProjectContext()
                .withMavenRootBuildFileSource(pomXml)
                .build();

        BumpParentPomVersion sut = new BumpParentPomVersion();
        sut.setGroupId("org.springframework.boot");
        sut.setArtifactId("spring-boot-starter-parent");
        sut.setToVersion("3.0.0-M3");
        sut.apply(context);

        assertThat(context.getBuildFile().hasParent()).isFalse();
    }

    @Test
    void bumpVersion() {
        String pomXml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">\n" +
                        "  <modelVersion>4.0.0</modelVersion>\n" +
                        "  <parent>\n" +
                        "    <groupId>org.springframework.boot</groupId>\n" +
                        "    <artifactId>spring-boot-starter-parent</artifactId>\n" +
                        "    <version>2.6.0</version>\n" +
                        "  </parent>\n" +
                        "  <groupId>com.example</groupId>\n" +
                        "  <artifactId>artifact</artifactId>\n" +
                        "  <version>1.0.0</version>\n" +
                        "</project>";

        ProjectContext context = TestProjectContext.buildProjectContext()
                .withMavenRootBuildFileSource(pomXml)
                .build();

        BumpParentPomVersion sut = new BumpParentPomVersion();
        sut.setGroupId("org.springframework.boot");
        sut.setArtifactId("spring-boot-starter-parent");
        sut.setToVersion("2.7.0");
        sut.apply(context);

        assertThat(context.getBuildFile().getParentPomDeclaration().getVersion()).isEqualTo("2.7.0");
    }

    @Test
    void bumpVersionToMilestoneVersion() {
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
                "  <groupId>com.example</groupId>\n" +
                "  <artifactId>artifact</artifactId>\n" +
                "  <version>1.0.0</version>\n" +
                "  <repositories>\n" +
                "    <repository>\n" +
                "      <id>spring-milestone</id>\n" +
                "      <url>https://repo.spring.io/milestone</url>\n" +
                "      <snapshots>\n" +
                "        <enabled>false</enabled>\n" +
                "      </snapshots>\n" +
                "    </repository>\n" +
                "  </repositories>\n" +
                "</project>";

        ProjectContext context = TestProjectContext.buildProjectContext()
                .withMavenRootBuildFileSource(pomXml)
                .build();

        BumpParentPomVersion sut = new BumpParentPomVersion();
        sut.setGroupId("org.springframework.boot");
        sut.setArtifactId("spring-boot-starter-parent");
        sut.setToVersion("3.0.0-M3");
        sut.apply(context);

        assertThat(context.getBuildFile().getParentPomDeclaration().getVersion()).isEqualTo("3.0.0-M3");
    }
}
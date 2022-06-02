package org.springframework.sbm.boot.common.conditions;

import lombok.Setter;
import org.springframework.sbm.build.api.ApplicationModule;
import org.springframework.sbm.build.api.BuildFile;
import org.springframework.sbm.build.api.ParentDeclaration;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.Condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HasSpringBootStarterParent implements Condition {
    private Pattern versionPattern = Pattern.compile(".*");

    @Override
    public String getDescription() {
        return String.format("Check if any Build file has a spring-boot-starter-parent as parent with a version matching pattern '%s'.", versionPattern);
    }

    public void setVersionPattern(String versionPattern) {
        this.versionPattern = Pattern.compile(versionPattern);
    }

    @Override
    public boolean evaluate(ProjectContext context) {
        return context.getApplicationModules().stream()
                .map(ApplicationModule::getBuildFile)
                .filter(BuildFile::hasParent)
                .map(BuildFile::getParentPomDeclaration)
                .map(ParentDeclaration::getVersion)
                .map(versionPattern::matcher)
                .anyMatch(Matcher::matches);
    }
}

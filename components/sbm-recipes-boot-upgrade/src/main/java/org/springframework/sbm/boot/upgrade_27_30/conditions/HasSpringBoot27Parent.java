package org.springframework.sbm.boot.upgrade_27_30.conditions;

import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.Condition;

public class HasSpringBoot27Parent implements Condition {
    @Override
    public String getDescription() {
        return "Check if updating Spring Boot version from 2.7.x to 3.0.x is applicable.";
    }

    @Override
    public boolean evaluate(ProjectContext context) {
        return context.getBuildFile().hasParent() &&
                context.getBuildFile().getParentPomDeclaration().getArtifactId().equals("spring-boot-starter-parent") &&
                context.getBuildFile().getParentPomDeclaration().getVersion().startsWith("2.7.");
    }
}

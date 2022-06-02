package org.springframework.sbm.boot.upgrade_27_30.actions;

import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.AbstractAction;

public class Boot_27_30_UpdateDependenciesAction extends AbstractAction {
    @Override
    public void apply(ProjectContext context) {
        if(hasSpringBootParent(context)) {
            context.getBuildFile().upgradeParentVersion("3.0.0-M3");
        } else {
            // FIXME: Add support to upgrade spring boot in dependencyManagement section
            // FIXME: Support spring boot application not having a parent neither dependencyManagement section
            throw new RuntimeException("Upgrading Spring Boot Dependency currently only supported for projects having a spring-boot-starter-parent.");
        }
    }

    private boolean hasSpringBootParent(ProjectContext context) {
        return context.getBuildFile().hasParent();
    }
}

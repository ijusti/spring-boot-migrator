package org.springframework.sbm.build.migration.actions;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.sbm.build.api.ApplicationModule;
import org.springframework.sbm.build.api.BuildFile;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.AbstractAction;

import javax.validation.constraints.NotNull;

@Slf4j
public class BumpParentPomVersion extends AbstractAction {

    @Setter
    @NotNull
    private String groupId;
    @Setter
    @NotNull
    private String artifactId;
    @Setter
    @NotNull
    private String toVersion;

    @Override
    public void apply(ProjectContext context) {
        context.getApplicationModules().stream()
                .map(ApplicationModule::getBuildFile)
                .filter(BuildFile::hasParent)
                .filter(b -> b.getParentPomDeclaration().getGroupId().equals(groupId))
                .filter(b -> b.getParentPomDeclaration().getArtifactId().equals(artifactId))
                .forEach(b -> b.upgradeParentVersion(toVersion));
    }

    private boolean hasParentPom(ProjectContext context) {
        return context.getBuildFile().hasParent();
    }
}

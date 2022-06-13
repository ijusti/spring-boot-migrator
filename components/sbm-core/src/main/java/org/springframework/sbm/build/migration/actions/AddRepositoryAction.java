package org.springframework.sbm.build.migration.actions;

import lombok.Setter;
import org.springframework.sbm.build.api.RepositoryDefinition;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.AbstractAction;

/**
 * Adds Repository to pom.xml
 */
@Setter
public class AddRepositoryAction extends AbstractAction {

    private String id;
    private String url;
    private String name;
    private String layout;
    private Boolean snapshotsEnabled;
    private String snapshotsChecksumPolicy;
    private String snapShotsUpdatePolicy;
    private Boolean releasesEnabled;
    private String releasesChecksumPolicy;
    private String releasesUpdatePolicy;


    @Override
    public void apply(ProjectContext context) {
        RepositoryDefinition.RepositoryDefinitionBuilder builder = RepositoryDefinition.builder();

        builder.id(id)
                .name(name)
                .url(url);

        if (snapshotsEnabled != null) {
            builder.snapshotsEnabled(snapshotsEnabled);
        }
        if (snapShotsUpdatePolicy != null) {
            builder.snapShotsUpdatePolicy(snapShotsUpdatePolicy);
        }
        if (snapshotsChecksumPolicy != null) {
            builder.snapshotsChecksumPolicy(snapshotsChecksumPolicy);
        }

        if (releasesEnabled != null) {
            builder.releasesEnabled(releasesEnabled);
        }
        if (releasesUpdatePolicy != null) {
            builder.releasesUpdatePolicy(releasesUpdatePolicy);
        }
        if (releasesChecksumPolicy != null) {
            builder.releasesChecksumPolicy(releasesChecksumPolicy);
        }
        if (layout != null) {
            builder.layout(layout);
        }

        RepositoryDefinition repository = builder.build();
        context.getApplicationModules().getRootModule().getBuildFile().addRepository(repository);
    }
}

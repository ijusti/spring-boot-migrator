package org.springframework.sbm.build.migration.conditions;

import lombok.Setter;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.Condition;

@Setter
public class NoRepositoryExistsCondition implements Condition {

    private String id;
    private String url;

    @Override
    public String getDescription() {
        return "Check that no Repository definition with same id or url exists";
    }

    @Override
    public boolean evaluate(ProjectContext context) {
        return context.getBuildFile().getRepositories().stream()
                .noneMatch(r -> r.getId().equals(id) || r.getUrl().equals(url));
    }
}

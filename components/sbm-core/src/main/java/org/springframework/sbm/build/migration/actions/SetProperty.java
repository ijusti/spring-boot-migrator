package org.springframework.sbm.build.migration.actions;

import lombok.Setter;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.AbstractAction;

@Setter
public class SetProperty extends AbstractAction {

    private String propertyName;
    private String propertyValue;

    @Override
    public void apply(ProjectContext context) {
        context.getBuildFile().setProperty(propertyName, propertyValue);
    }
}

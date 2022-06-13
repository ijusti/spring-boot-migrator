package org.springframework.sbm.boot.upgrade.common.actions;

import lombok.Setter;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.engine.recipe.AbstractAction;
import org.springframework.sbm.java.api.JavaSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplaceJavaxWithJakartaAction extends AbstractAction {

    @Setter
    private List<String> javaxPackagePatterns = new ArrayList<>();

    @Override
    public void apply(ProjectContext context) {
        context.getProjectJavaSources()
                .asStream()
                .forEach(js -> {
                    List<String> matchingPackages = javaxPackagePatterns.stream().filter(p -> js.hasImportStartingWith(p)).collect(Collectors.toList());
                    matchingPackages.forEach(p -> js.replaceImport(p, p.replace("javax.", "jakarta.")));
                });
    }
}

/*
 * Copyright 2021 - 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.sbm.boot.upgrade_24_25.filter;

import org.springframework.sbm.java.api.MethodCall;
import org.springframework.sbm.engine.context.ProjectContext;
import org.springframework.sbm.java.api.JavaSourceAndType;
import org.springframework.sbm.java.api.Method;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

public class SpringDataJpaAnalyzer {
    public List<MethodCall> findCallsToGetOneMethod(ProjectContext context) {
        return context.getProjectJavaSources().findMethodCalls("org.springframework.data.jpa.repository.JpaRepository getOne(java.lang.Long)");
    }

    public List<MatchingMethod> getJpaRepositoriesWithGetByIdMethod(ProjectContext context) {
        String jpaRepositoryInterface = "org.springframework.data.jpa.repository.JpaRepository";
        List<JavaSourceAndType> jpaRepositories = context.getProjectJavaSources().findTypesImplementing(jpaRepositoryInterface);
        // FIXME: type of PK must be retrieved, moves to rewrite when these migrations are provided as OpenRewrite recipes
        String methodPattern = "getById(java.lang.Long)";
        return jpaRepositories.stream()
                .filter(jat -> jat.getType().hasMethod(methodPattern))
                .map(jat -> {
                    return new MatchingMethod(jat, methodPattern, jat.getType().getMethod(methodPattern));
                })
                .collect(Collectors.toList());
        //return findRepositoriesDeclaring(jpaRepositories, methodPattern);
    }

    private List<MatchingMethod> findRepositoriesDeclaring(List<JavaSourceAndType> jpaRepositories, String methodPattern) {
        return jpaRepositories.stream()
                .filter(jat -> jat.getType().hasMethod(methodPattern))
                .map(jat -> {
                    return new MatchingMethod(jat, methodPattern, jat.getType().getMethod(methodPattern));
                })
                .collect(Collectors.toList());
    }

    @Value
    public class MatchingMethod {
        private final JavaSourceAndType jat;
        private final String methodPattern;
        private final Method method;

        public MatchingMethod(JavaSourceAndType jat, String methodPattern, Method method) {
            this.jat = jat;
            this.methodPattern = methodPattern;
            this.method = method;
        }
    }
}

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
package org.springframework.sbm.java.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.openrewrite.java.tree.J;
import org.slf4j.LoggerFactory;
import org.springframework.sbm.project.resource.SbmApplicationProperties;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RewriteJavaParserTest {

    @Test
    void shouldDelegateParsingErrorsToExceptionHandler() throws ClassNotFoundException {
        final Logger logger = (Logger) LoggerFactory.getLogger(Class.forName("org.openrewrite.java.Java11Parser"));
        logger.setLevel(Level.ALL);

        PrintStream realSysOut = System.out;
        ByteArrayOutputStream sysOutBuffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sysOutBuffer));

        SbmApplicationProperties sbmApplicationProperties = new SbmApplicationProperties();
        sbmApplicationProperties.setJavaParserLoggingCompilationWarningsAndErrors(true);
        RewriteJavaParser rewriteJavaParser = new RewriteJavaParser(sbmApplicationProperties);
        sysOutBuffer.reset();
        List<J.CompilationUnit> parsed = rewriteJavaParser.parse("compile error");

        String out = sysOutBuffer.toString();
        System.setOut(realSysOut);
        System.out.println(out);
        assertThat(out).containsPattern(
                ".*org.openrewrite.java.Java11Parser.*compile error.*");
    }

}

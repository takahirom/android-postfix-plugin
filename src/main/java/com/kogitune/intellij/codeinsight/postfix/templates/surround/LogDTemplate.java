/*
 * Copyright (C) 2014 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.LOG;

/**
 * Postfix template for android Log.
 *
 * @author takahirom
 */
public class LogDTemplate extends LogTemplate {

    public LogDTemplate() {
        this("logd");
    }

    public LogDTemplate(@NotNull String alias) {
        super(alias, "if(BuildConfig.DEBUG) Log.d(TAG, expr);", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        Project project = element.getProject();
        final GlobalSearchScope resolveScope = element.getResolveScope();
        PsiClass[] buildConfigClasses = PsiShortNamesCache.getInstance(project).getClassesByName("BuildConfig", resolveScope);

        String buildConfigDebug = "BuildConfig.DEBUG";
        if (buildConfigClasses.length != 0) {
            // Get BuildConfig QualifiedName
            PsiClass buildConfig = buildConfigClasses[0];
            String qualifiedName = buildConfig.getQualifiedName();
            buildConfigDebug = qualifiedName + ".DEBUG";
        }

        return "if (" + buildConfigDebug + ") " + getStaticPrefix(LOG, "d", element) + "($TAG$, $expr$)$END$";
    }

}

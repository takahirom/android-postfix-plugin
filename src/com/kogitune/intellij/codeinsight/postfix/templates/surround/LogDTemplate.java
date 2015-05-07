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

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.kogitune.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.macro.TagMacro;
import com.kogitune.intellij.codeinsight.postfix.macro.ToStringIfNeedMacro;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.*;
import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.LOG;

/**
 * Postfix template for android Log.
 *
 * @author takahirom
 */
public class LogDTemplate extends RichChooserStringBasedPostfixTemplate {

    public static final Condition<PsiElement> IS_NON_NULL = new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
            return (IS_NOT_PRIMITIVE.value(element) || IS_NUMBER.value(element) || IS_BOOLEAN.value(element)) && !AndroidPostfixTemplatesUtils.isAnnotatedNullable(element);
        }

    };

    public LogDTemplate() {
        this("logd");
    }

    public LogDTemplate(@NotNull String alias) {
        super(alias, "if(BuildConfig.DEBUG) Log.d(TAG, expr);", IS_NON_NULL);
    }


    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        Project project = element.getProject();
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        PsiClass[] buildConfigClasses = PsiShortNamesCache.getInstance(project).getClassesByName("BuildConfig", scope);

        String buildConfigDebug = "BuildConfig.DEBUG";
        if (buildConfigClasses.length != 0) {
            // got BuildConfig QualifiedName
            PsiClass buildConfig = buildConfigClasses[0];
            String qualifiedName = buildConfig.getQualifiedName();
            buildConfigDebug = qualifiedName + ".DEBUG";
        }

        return "if (" + buildConfigDebug + ") " + getStaticMethodPrefix(LOG, "d", element) + "($TAG$, $expr$);$END$";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        final ToStringIfNeedMacro toStringIfNeedMacro = new ToStringIfNeedMacro();
        MacroCallNode macroCallNode = new MacroCallNode(toStringIfNeedMacro);
        macroCallNode.addParameter(new ConstantNode(expr.getText()));
        template.addVariable("expr", macroCallNode, false);
    }

    @Override
    protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {
        MacroCallNode node = new MacroCallNode(new TagMacro());
        template.addVariable("TAG", node, new ConstantNode(""), false);
    }
}

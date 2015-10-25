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
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.internal.AbstractRichStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.macro.TagMacro;
import com.kogitune.intellij.codeinsight.postfix.macro.ToStringIfNeedMacro;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.LOG;

/**
 * Postfix template for android Log.
 *
 * @author takahirom
 */
public class LogTemplate extends AbstractRichStringBasedPostfixTemplate {

    public LogTemplate() {
        this("log", "Log.d(TAG, expr);", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    public LogTemplate(@NotNull String name, @NotNull String example, @NotNull Condition<PsiElement> typeChecker) {
        super(name, example, typeChecker);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return getStaticPrefix(LOG, "d", element) + "($TAG$, $expr$)$END$";
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

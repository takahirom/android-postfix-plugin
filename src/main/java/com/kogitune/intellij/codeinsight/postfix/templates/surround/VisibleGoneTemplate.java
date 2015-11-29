/*
 * Copyright (C) 2015 takahirom
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
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.internal.AbstractRichStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_BOOLEAN;

/**
 * Postfix template for android View visibility.
 *
 * @author kikuchy
 */
public class VisibleGoneTemplate extends AbstractRichStringBasedPostfixTemplate {

    public VisibleGoneTemplate() {
        this("vg");
    }

    public VisibleGoneTemplate(@NotNull String alias) {
        super(alias, "(expr) ? View.VISIBLE : View.GONE", IS_BOOLEAN);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "($expr$) ? "
                + getStaticPrefix(AndroidClassName.VIEW, "VISIBLE", element)
                + " : "
                + getStaticPrefix(AndroidClassName.VIEW, "GONE", element);
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("expr", new TextExpression(expr.getText()), false);
    }
}

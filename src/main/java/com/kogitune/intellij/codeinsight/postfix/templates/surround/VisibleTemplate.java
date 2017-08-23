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
import org.jetbrains.annotations.NotNull;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils.IS_VIEW;

/**
 * Postfix template for android View visibility.
 *
 * @author axlchen
 */
public class VisibleTemplate extends AbstractRichStringBasedPostfixTemplate {

    public VisibleTemplate() {
        this("vsb");
    }

    public VisibleTemplate(@NotNull String alias) {
        super(alias, "view.setVisibility(View.VISIBLE)", IS_VIEW);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "$view$.setVisibility(View.VISIBLE)";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("view", new TextExpression(expr.getText()), false);
    }
}

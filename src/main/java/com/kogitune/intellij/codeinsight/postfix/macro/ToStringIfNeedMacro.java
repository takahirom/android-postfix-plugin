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

package com.kogitune.intellij.codeinsight.postfix.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nullable;

public class ToStringIfNeedMacro extends Macro {


    public String getName() {
        return "to_string";
    }

    public String getPresentableName() {
        return "to_string";
    }

    @Nullable
    @Override
    public Result calculateResult(Expression[] expressions, ExpressionContext context) {
        if (expressions.length == 0) {
            return null;
        }
        Project project = context.getProject();
        final String exprText = expressions[0].calculateResult(context).toString();
        try {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
            final PsiExpression expression = elementFactory.createExpressionFromText(exprText, context.getPsiElementAtStartOffset());
            final PsiType type = expression.getType();

            if ("java.lang.String".equals(type.getCanonicalText())) {
                // example "test:" + test
                return new TextResult(exprText);
            }
            if (expression instanceof PsiPolyadicExpression) {
                // example 1 + 1
                return new TextResult("\"" + exprText + ":\" + (" + exprText + ")");
            }
            // example 1
            return new TextResult("\"" + exprText + ":\" + " + exprText);
        } catch (Exception e) {
            // ignored. because can use default result.
            e.printStackTrace();
        }
        return new TextResult(exprText);

    }
}

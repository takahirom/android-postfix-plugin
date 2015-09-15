package com.kogitune.intellij.codeinsight.postfix.macro;

import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.macro.ClassNameMacro;
import com.intellij.codeInsight.template.macro.MacroUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nullable;

/**
 * macro for android log TAG parameter.
 *
 * @author takahirom
 */
public class TagMacro extends Macro {


    public String getName() {
        return "tag";
    }

    public String getPresentableName() {
        return "tag";
    }

    @Nullable
    @Override
    public Result calculateResult(Expression[] expressions, ExpressionContext context) {
        if (isContainTagField(context)) {
            return new TextResult("TAG");
        } else {
            String className = new ClassNameMacro().calculateResult(new Expression[]{}, context).toString();
            if (className.length() > 23) {
                className = className.substring(0, 23);
            }
            return new TextResult("\"" + className + "\"");
        }
    }

    public boolean isAcceptableInContext(TemplateContextType context) {
        return context instanceof JavaCodeContextType;
    }


    public boolean isContainTagField(ExpressionContext context) {
        Project project = context.getProject();
        int offset = context.getStartOffset();
        PsiFile file = PsiDocumentManager.getInstance(project).getPsiFile(context.getEditor().getDocument());
        PsiElement place = file.findElementAt(offset);
        PsiVariable[] variables = MacroUtil.getVariablesVisibleAt(place, "");
        for (PsiVariable variable : variables) {
            if (variable instanceof PsiField && variable.hasModifierProperty("static")) {
                PsiField psiField = (PsiField) variable;
                if ("TAG".equals(psiField.getName())) {
                    return true;
                }
            }

        }

        return false;
    }
}

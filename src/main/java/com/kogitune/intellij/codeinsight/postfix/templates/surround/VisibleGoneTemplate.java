package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_BOOLEAN;

/**
 * Postfix template for android View visibility.
 *
 * @author kikuchy
 */
public class VisibleGoneTemplate extends RichChooserStringBasedPostfixTemplate {

    public VisibleGoneTemplate() {
        this("vg");
    }

    public VisibleGoneTemplate(@NotNull String alias) {
        super(alias, "(expr) ? View.VISIBLE : View.GONE", IS_BOOLEAN);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "($expr$) ? "
                + getStaticMethodPrefix(AndroidClassName.VIEW, "VISIBLE", element)
                + " : "
                + getStaticMethodPrefix(AndroidClassName.VIEW, "GONE", element);
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("expr", new TextExpression(expr.getText()), false);
    }
}

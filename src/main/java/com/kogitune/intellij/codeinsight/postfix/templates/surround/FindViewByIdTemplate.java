package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.internal.AbstractRichStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.macro.FindViewByIdMacro;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NUMBER;

/**
 * Postfix template for android findViewById.
 *
 * @author takahirom
 */
public class FindViewByIdTemplate extends AbstractRichStringBasedPostfixTemplate {

    public static final Condition<PsiElement> IS_NON_NULL_NUMBER = new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
            return IS_NUMBER.value(element) && !AndroidPostfixTemplatesUtils.isAnnotatedNullable(element);
        }

    };

    public FindViewByIdTemplate() {
        this("find", "findViewById(expr);");
    }

    public FindViewByIdTemplate(@NotNull String name, @NotNull String example) {
        super(name, example, IS_NON_NULL_NUMBER);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return "$expr$$END$";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        final FindViewByIdMacro toStringIfNeedMacro = new FindViewByIdMacro();
        MacroCallNode macroCallNode = new MacroCallNode(toStringIfNeedMacro);
        macroCallNode.addParameter(new ConstantNode(expr.getText()));
        template.addVariable("expr", macroCallNode, false);
    }

}

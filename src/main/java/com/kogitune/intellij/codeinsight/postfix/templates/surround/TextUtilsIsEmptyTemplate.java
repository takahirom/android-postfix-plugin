package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.util.InheritanceUtil;
import com.kogitune.intellij.codeinsight.postfix.internal.RichChooserStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.TEXT_UTILS;

/**
 * Postfix template for android TextUtils class.
 *
 * @author kikuchy
 */
public class TextUtilsIsEmptyTemplate extends RichChooserStringBasedPostfixTemplate {

    public static final Condition<PsiElement> IS_NON_NULL_STRING = new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
            return InheritanceUtil.isInheritor(((PsiExpression) element).getType(), "java.lang.CharSequence") && !AndroidPostfixTemplatesUtils.isAnnotatedNullable(element);
        }
    };

    @Override
    protected void onFinishCompleteStatement(TemplateManager manager, Editor editor, Template template) {
        // nothing
        // Prevent complete statement
    }

    public TextUtilsIsEmptyTemplate() {
        this("isemp");
    }

    public TextUtilsIsEmptyTemplate(@NotNull String alias) {
        super(alias, "TextUtils.isEmpty(expr)", IS_NON_NULL_STRING);
    }

    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return getStaticPrefix(TEXT_UTILS, "isEmpty", element) + "($expr$)$END$";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("expr", new TextExpression(expr.getText()), false);
    }
}

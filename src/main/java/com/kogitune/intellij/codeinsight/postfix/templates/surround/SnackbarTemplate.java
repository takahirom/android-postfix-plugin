package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.EmptyNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.macro.VariableOfTypeMacro;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.internal.AbstractRichStringBasedPostfixTemplate;
import com.kogitune.intellij.codeinsight.postfix.macro.ToStringIfNeedMacro;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.VIEW;


public class SnackbarTemplate extends AbstractRichStringBasedPostfixTemplate {

    private boolean mWithAction;

    public SnackbarTemplate() {
        this("snack", "Snackbar.make(view, expr, Snackbar.LENGTH_SHORT).show();", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }

    public SnackbarTemplate(boolean withAction) {
        this("snackaction", "Snackbar.make(view, expr, Snackbar.LENGTH_LONG)\n" +
                "                .setAction(actionText, new View.OnClickListener() {\n" +
                "                    @Override\n" +
                "                    public void onClick(View v) {\n" +
                "                        " +
                "                    }\n" +
                "                })\n" +
                "                .show();", AndroidPostfixTemplatesUtils.IS_NON_NULL);
        mWithAction = withAction;
    }

    protected SnackbarTemplate(@NotNull String name, @NotNull String example, @NotNull Condition<PsiElement> typeChecker) {
        super(name, example, typeChecker);
    }

    @Nullable
    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        if (mWithAction) {
            return "Snackbar.make($view$, $expr$, Snackbar.LENGTH_LONG)\n" +
                    "                .setAction($actionText$, new View.OnClickListener() {\n" +
                    "                    @Override\n" +
                    "                    public void onClick(View v) {\n" +
                    "                        $action$\n" +
                    "                    }\n" +
                    "                })\n" +
                    "                .show();$END$";
        } else {
            return "Snackbar.make($view$, $expr$, Snackbar.LENGTH_SHORT).show();$END$";
        }
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        ToStringIfNeedMacro toStringIfNeedMacro = new ToStringIfNeedMacro();
        MacroCallNode macroCallNode = new MacroCallNode(toStringIfNeedMacro);
        macroCallNode.addParameter(new ConstantNode(expr.getText()));

        MacroCallNode node = new MacroCallNode(new VariableOfTypeMacro());
        node.addParameter(new ConstantNode(VIEW.toString()));
        template.addVariable("view", node, new EmptyNode(), false);

        template.addVariable("expr", macroCallNode, false);

        if (mWithAction) {
            template.addVariable("actionText", new EmptyNode(), false);
            template.addVariable("action", new EmptyNode(), false);
        }
    }
}

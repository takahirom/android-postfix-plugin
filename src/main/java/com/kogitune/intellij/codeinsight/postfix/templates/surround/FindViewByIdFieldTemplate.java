package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;

import static com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils.IS_NUMBER;

/**
 * Postfix template for android Log.
 *
 * @author takahirom
 */
public class FindViewByIdFieldTemplate extends FindViewByIdTemplate {

    public static final Condition<PsiElement> IS_NON_NULL = new Condition<PsiElement>() {
        @Override
        public boolean value(PsiElement element) {
            return IS_NUMBER.value(element) && !AndroidPostfixTemplatesUtils.isAnnotatedNullable(element);
        }

    };

    public FindViewByIdFieldTemplate() {
        super("findf");
    }

    @Override
    protected void onFinishCompleteStatement(final TemplateManager manager, final Editor editor, Template template) {
        final ActionManager actionManager = ActionManagerImpl.getInstance();
        final String editorCompleteStatementText = "IntroduceField";
        final AnAction action = actionManager.getAction(editorCompleteStatementText);
        actionManager.tryToExecute(action, ActionCommand.getInputEvent(editorCompleteStatementText), null, ActionPlaces.UNKNOWN, true);
    }

}

package com.kogitune.intellij.codeinsight.postfix.templates.surround;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.playback.commands.ActionCommand;

/**
 * Postfix template for android findViewById.
 *
 * @author takahirom
 */
public class FindViewByIdVariableTemplate extends FindViewByIdTemplate {

    public FindViewByIdVariableTemplate() {
        super("findv");
    }

    @Override
    protected void onFinishCompleteStatement(final TemplateManager manager, final Editor editor, Template template) {
        final ActionManager actionManager = ActionManagerImpl.getInstance();
        final String editorCompleteStatementText = "IntroduceVariable";
        final AnAction action = actionManager.getAction(editorCompleteStatementText);
        actionManager.tryToExecute(action, ActionCommand.getInputEvent(editorCompleteStatementText), null, ActionPlaces.UNKNOWN, true);
    }

}

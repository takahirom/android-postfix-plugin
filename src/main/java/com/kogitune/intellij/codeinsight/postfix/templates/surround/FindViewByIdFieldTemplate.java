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
public class FindViewByIdFieldTemplate extends FindViewByIdTemplate {

    public FindViewByIdFieldTemplate() {
        super("findf", "mViewType = (ViewType)findViewById(expr);");
    }

    @Override
    protected void onTemplateFinished(final TemplateManager manager, final Editor editor, Template template) {
        final ActionManager actionManager = ActionManagerImpl.getInstance();
        final String editorCompleteStatementText = "IntroduceField";
        final AnAction action = actionManager.getAction(editorCompleteStatementText);
        actionManager.tryToExecute(action, ActionCommand.getInputEvent(editorCompleteStatementText), null, ActionPlaces.UNKNOWN, true);
    }

}

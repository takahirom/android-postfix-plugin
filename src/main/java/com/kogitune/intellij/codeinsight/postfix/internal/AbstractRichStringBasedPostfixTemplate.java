/*
 * Copyright (C) 2014 Bob Browning
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
package com.kogitune.intellij.codeinsight.postfix.internal;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateEditingAdapter;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TextExpression;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateExpressionSelector;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplateWithExpressionSelector;
import com.intellij.codeInsight.template.postfix.templates.PostfixTemplatesUtils;
import com.intellij.codeInsight.template.postfix.templates.StringBasedPostfixTemplate;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.impl.ActionManagerImpl;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.playback.commands.ActionCommand;
import com.intellij.psi.PsiElement;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName;
import com.kogitune.intellij.codeinsight.postfix.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base class that is a modified form of {@link StringBasedPostfixTemplate} that passes the project to {@link
 * AbstractRichStringBasedPostfixTemplate#createTemplate} to allow querying of project properties.
 *
 * @author Bob Browning
 */
public abstract class AbstractRichStringBasedPostfixTemplate extends PostfixTemplateWithExpressionSelector {

    protected AbstractRichStringBasedPostfixTemplate(@NotNull String name,
                                                     @NotNull String example,
                                                     @NotNull PostfixTemplateExpressionSelector selector) {
        super(name, example, selector);
    }

    @Override
    protected final void expandForChooseExpression(@NotNull PsiElement expr, @NotNull final Editor editor) {
        Project project = expr.getProject();
        Document document = editor.getDocument();
        PsiElement elementForRemoving = shouldRemoveParent() ? expr.getParent() : expr;
        document.deleteString(elementForRemoving.getTextRange().getStartOffset(),
                elementForRemoving.getTextRange().getEndOffset());
        TemplateManager manager = TemplateManager.getInstance(project);

        String templateString = getTemplateString(expr);
        if (templateString == null) {
            PostfixTemplatesUtils.showErrorHint(expr.getProject(), editor);
            return;
        }

        Template template = createTemplate(project, manager, templateString);

        if (shouldAddExpressionToContext()) {
            addExprVariable(expr, template);
        }

        setVariables(template, expr);
        manager.startTemplate(editor, template, new TemplateEditingAdapter() {
            @Override
            public void templateFinished(Template template, boolean brokenOff) {
                // format and add ;
                final ActionManager actionManager = ActionManagerImpl.getInstance();
                final String editorCompleteStatementText = "EditorCompleteStatement";
                final AnAction action = actionManager.getAction(editorCompleteStatementText);
                actionManager.tryToExecute(action, ActionCommand.getInputEvent(editorCompleteStatementText), null, ActionPlaces.UNKNOWN, true);
            }
        });
    }

    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        template.addVariable("expr", new TextExpression(expr.getText()), false);
    }

    /**
     * Add custom variables to the template.
     *
     * @param template The template
     * @param element  The expression being replaced
     */
    protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {
    }

    @Nullable
    public abstract String getTemplateString(@NotNull PsiElement element);

    /**
     * Returns true if the {@code expr} variable should be added to the template by default.
     */
    protected boolean shouldAddExpressionToContext() {
        return true;
    }

    /**
     * Returns true if the formatting manager should be applied to the generated code block.
     */
    protected boolean shouldReformat() {
        return true;
    }

    /**
     * Returns true if the parent element should be removed, for example for topmost expression.
     */
    protected abstract boolean shouldRemoveParent();

    /**
     * Create a new instance of a code template for the current postfix template.
     *
     * @param project        The current project
     * @param manager        The template manager
     * @param templateString The template string
     */
    protected Template createTemplate(Project project, TemplateManager manager, String templateString) {
        Template template = manager.createTemplate("", "", templateString);
        template.setToReformat(shouldReformat());
        template.setValue(Template.Property.USE_STATIC_IMPORT_IF_POSSIBLE, false);
        return template;
    }

    /**
     * Gets the static method prefix for the android static method.
     *
     * @param className  The android class name
     * @param methodName The method name
     * @param context    The context element
     */
    protected String getStaticPrefix(@NotNull AndroidClassName className,
                                     @NotNull String methodName,
                                     @NotNull PsiElement context) {
        return getStaticPrefix(className.getClassName(), methodName, context);
    }

    /**
     * Gets the static method prefix for the android static method.
     *
     * @param className  The android class name
     * @param methodName The method name
     * @param context    The context element
     */
    protected String getStaticPrefix(@NotNull String className,
                                     @NotNull String methodName,
                                     @NotNull PsiElement context) {
        return AndroidPostfixTemplatesUtils.getStaticPrefix(className, methodName, context);
    }
}

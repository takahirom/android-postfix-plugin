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

import com.intellij.codeInsight.template.postfix.util.JavaPostfixTemplatesUtils;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

/**
 * @author Bob Browning
 */
public abstract class RichChooserStringBasedPostfixTemplate extends AbstractRichStringBasedPostfixTemplate {

  protected RichChooserStringBasedPostfixTemplate(@NotNull String name,
                                                  @NotNull String example,
                                                  @NotNull Condition<PsiElement> typeChecker) {
    super(name, example, JavaPostfixTemplatesUtils.selectorAllExpressionsWithCurrentOffset(typeChecker));
  }

  @Override
  protected boolean shouldRemoveParent() {
    return false;
  }
}

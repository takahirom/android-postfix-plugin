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
package com.kogitune.intellij.codeinsight.postfix.utils;

import com.intellij.psi.*;
import com.siyeh.ig.psiutils.ClassUtils;

/**
 * Collection of method for supporting importing of members.
 *
 * @author Bob Browning
 */
public class ImportUtils {

    /**
     * Check whether the current context has a static member import, either on-demand or explicit.
     *
     * @param fqClassName The class to import from
     * @param memberName  The class member to import
     * @param context     The context to be imported into
     */
    public static boolean hasImportStatic(String fqClassName, String memberName, PsiElement context) {
        final PsiFile file = context.getContainingFile();
        if (!(file instanceof PsiJavaFile)) {
            return false;
        }
        final PsiJavaFile javaFile = (PsiJavaFile) file;
        final PsiImportList importList = javaFile.getImportList();
        if (importList == null) {
            return false;
        }
        final PsiImportStaticStatement[] importStaticStatements = importList.getImportStaticStatements();
        for (PsiImportStaticStatement importStaticStatement : importStaticStatements) {
            if (importStaticStatement.isOnDemand()) {
                PsiClass psiClass = ClassUtils.findClass(fqClassName, context);
                if (psiClass != null && psiClass.equals(importStaticStatement.resolveTargetClass())) {
                    return true;
                }
                continue;
            }
            final String name = importStaticStatement.getReferenceName();
            if (!memberName.equals(name)) {
                continue;
            }
            final PsiJavaCodeReferenceElement importReference = importStaticStatement.getImportReference();
            if (importReference == null) {
                continue;
            }
            final PsiElement qualifier = importReference.getQualifier();
            if (qualifier == null) {
                continue;
            }
            final String qualifierText = qualifier.getText();
            if (fqClassName.equals(qualifierText)) {
                return true;
            }
        }
        return false;
    }

}

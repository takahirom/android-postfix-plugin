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

package com.kogitune.intellij.codeinsight.postfix.macro;

import com.android.ide.common.res2.ResourceItem;
import com.android.resources.ResourceType;
import com.android.tools.idea.rendering.LocalResourceRepository;
import com.android.tools.idea.rendering.ProjectResourceRepository;
import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.macro.VariableOfTypeMacro;
import com.intellij.facet.Facet;
import com.intellij.facet.FacetManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.ACTIVITY;
import static com.kogitune.intellij.codeinsight.postfix.utils.AndroidClassName.VIEW;

/**
 * Created by takam on 2015/05/05.
 */
public class FindViewByIdMacro extends Macro {


    public String getName() {
        return "find_view";
    }

    public String getPresentableName() {
        return "find_view";
    }

    @Nullable
    @Override
    public Result calculateResult(Expression[] expressions, ExpressionContext context) {
        if (expressions.length == 0) {
            return null;
        }

        Project project = context.getProject();
        Expression expression = expressions[0];
        final String resource = expression.calculateResult(context).toString();
        final TextResult defaultResult = new TextResult("findViewById(" + resource + ")");
        if (!resource.startsWith("R.id.")) {
            return defaultResult;
        }

        final int index = resource.lastIndexOf(".");
        final String resourceId = resource.substring(index + 1);

        String viewTag = getViewTag(project, resourceId);
        if (viewTag == null) {
            return defaultResult;
        }
        final String contextVariable = getContextVariable(context);
        if (contextVariable == null) {
            return new TextResult("(" + viewTag + ")findViewById(" + resource + ")");
        } else {
            return new TextResult("(" + viewTag + ")" + contextVariable + ".findViewById(" + resource + ")");
        }


    }

    private String getContextVariable(ExpressionContext context) {
        Result calculateResult = getVariableByFQDN(context, ACTIVITY.toString());
        if (calculateResult == null) {
            // Retry by view
            calculateResult = getVariableByFQDN(context, VIEW.toString());
            if (calculateResult == null) {
                return null;
            }
        }
        final String result = calculateResult.toString();
        if (result == null || "".equals(result)) {
            return null;
        }
        if ("this".equals(result)) {
            return null;
        }
        return result;
    }

    private Result getVariableByFQDN(ExpressionContext context, String fqn) {
        MacroCallNode callNode = new MacroCallNode(new VariableOfTypeMacro());
        callNode.addParameter(new ConstantNode(fqn));
        return callNode.calculateResult(context);
    }


    @Nullable
    public String getViewTag(Project project, String resourceId) {
        final ModuleManager moduleManager = ModuleManager.getInstance(project);
        List<Module> modules = Arrays.asList(moduleManager.getModules());
        AndroidFacet androidFacet = null;
        for (Module module : modules) {
            for (Facet facet : FacetManager.getInstance(module).getAllFacets()) {
                if (facet instanceof AndroidFacet) {
                    androidFacet = (AndroidFacet) facet;
                }
            }
            if (androidFacet == null) {
                continue;
            }
            final LocalResourceRepository resources = ProjectResourceRepository.getProjectResources(androidFacet, true);
            List<ResourceItem> items = resources.getResourceItem(ResourceType.ID, resourceId);
            if (items == null || items.size() == 0) {
                continue;
            }
            final ResourceItem resourceItem = items.get(0);
            final String viewTag = resources.getViewTag(resourceItem);
            return viewTag;
        }
        return null;
    }
}

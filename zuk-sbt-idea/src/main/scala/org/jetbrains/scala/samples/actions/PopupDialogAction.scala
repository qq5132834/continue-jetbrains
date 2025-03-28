// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.scala.samples.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.pom.Navigatable
import org.jetbrains.annotations.{Nls, NotNull, Nullable}
import org.jetbrains.scala.samples.SamplePluginBundle
import org.jetbrains.scala.samples.services.{ApplicationHelloService, ProjectHelloService}

import javax.swing._

class PopupDialogAction extends AnAction() {

  /**
   * Gives the user feedback when the dynamic action menu is chosen.
   * 在选择动态操作菜单时向用户提供反馈。
   * Pops a simple message dialog.
   * 弹出一个简单的消息对话框。
   * @param event Event received when the associated menu item is chosen.
   * @param事件选择相关菜单项时收到的事件。
   */
  override def actionPerformed(event: AnActionEvent): Unit = { // Using the event, create and show a dialog 使用事件，创建并显示对话框
    val currentProject = event.getProject
    val dlgMsg = new StringBuilder(SamplePluginBundle.message("gettext.selected", event.getPresentation.getText) + '\n')
    val dlgTitle = event.getPresentation.getDescription

    // If an element is selected in the editor, add info about it. 如果在编辑器中选择了一个元素，请添加有关它的信息。
    val nav = event.getData(CommonDataKeys.NAVIGATABLE)
    if (nav != null)
      dlgMsg.append(SamplePluginBundle.message("selected.element.tostring", nav.toString) + '\n')

    val appHelloMessage = ApplicationHelloService.getInstance.getApplicationHelloInfo
    dlgMsg.append(appHelloMessage + '\n')

    val projectMessage = ProjectHelloService.getInstance(currentProject).getProjectHelloInfo
    dlgMsg.append(projectMessage + '\n')

    Messages.showMessageDialog(currentProject, dlgMsg.toString, dlgTitle, Messages.getInformationIcon)
  }

  /**
   * Determines whether this menu item is available for the current context.
   * 确定此菜单项是否可用于当前上下文。
   * Requires a project to be open.
   * 需要打开一个项目。
   *
   * @param e Event received when the associated group-id menu is chosen.
   * @parame选择相关组id菜单时收到的事件。
   */
  override def update(e: AnActionEvent): Unit = { // Set the availability based on whether a project is open.  根据项目是否打开设置可用性。
    val project = e.getProject
    e.getPresentation.setEnabledAndVisible(project != null)
  }
}

// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.jetbrains.scala.samples.ui

import com.github.continuedev.continueintellijextension.toolWindow.ContinueBrowser
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.{JBLabel, JBOptionButton, JBPanel, JBTextField}
import com.intellij.ui.content.{Content, ContentFactory}
import com.intellij.ui.jcef.JBCefBrowser
import org.jetbrains.scala.samples.SamplePluginBundle

import javax.swing.{JOptionPane, JPanel}
import java.awt.GridLayout
import java.awt.GridLayout
import java.nio.file.Paths

class MyToolWindowFactory extends ToolWindowFactory {

  /***
    idea 插件开发一个 ToolWindow ， 需求如下：
    1. 添加两个输入框，一个启动按钮
    2. 当点击启动按钮时将两个输入框的数据弹出
   */
  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {
    toolWindow.getComponent.add(new JBLabel(SamplePluginBundle.message("my.cool.tool.window")))

    //    val panel = new JBPanel(new GridLayout(3, 1))
//    val inputField1 = new JBTextField()
//    val inputField2 = new JBTextField()
//    panel.add(inputField1)
//    panel.add(inputField2)
//
//    toolWindow.getComponent.add(panel)


    // 获取本地 HTML 文件路径
    //val htmlFilePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "index.html").toUri.toString
    //val htmlFilePath = "D:\\AAAAAAAAAAAAAAAAAAAA\\github\\continue-jetbrains\\zuk-sbt-idea\\src\\main\\resources\\index.html"
    //val htmlFilePath = "D:/AAAAAAAAAAAAAAAAAAAA/github/continue-jetbrains/continue-1.0.2-jetbrains/extensions/intellij/src/main/resources/webview/index.html"
    val htmlFilePath = "https://www.baidu.com/"
//    val htmlFilePath = "http://localhost:5173/jetbrains_index.html"
    // 创建 JCEF 浏览器实例
    val browser = new JBCefBrowser(htmlFilePath)
    // 创建内容面板
    val content = ContentFactory.getInstance().createContent(browser.getComponent, "Web View", false)
    toolWindow.getContentManager.addContent(content)

  }

  def continueWeb(project: Project, url: String): Unit = {
    val browser = new ContinueBrowser(project, url)
    browser.init()


  }



}

//class ContinuePluginWindow(project: Project) {
//  private val defaultGUIUrl = "http://continue/index.html"
//
//  def apply(project: Project): ContinuePluginWindow = {
//    val JS_QUERY_POOL_SIZE = "200"
//    System.setProperty("ide.browser.jcef.jsQueryPoolSize", JS_QUERY_POOL_SIZE)
//    System.setProperty("ide.browser.jcef.contextMenu.devTools.enabled", "true")
//    new ContinuePluginWindow(project)
//  }
//
//  val browser
//
//}

/***
 * class ContinuePluginWindow(project: Project) {
 * private val defaultGUIUrl = "http://continue/index.html"
 *
 * init {
 * System.setProperty("ide.browser.jcef.jsQueryPoolSize", JS_QUERY_POOL_SIZE)
 * System.setProperty("ide.browser.jcef.contextMenu.devTools.enabled", "true")
 * }
 *
 * val browser: ContinueBrowser by lazy {
 * val url = System.getenv("GUI_URL")?.toString() ?: defaultGUIUrl
 *
 * val browser = ContinueBrowser(project, url)
 * val continuePluginService =
 * ServiceManager.getService(project, ContinuePluginService::class.java)
 * continuePluginService.continuePluginWindow = this
 * browser
 * }
 *
 * val content: JComponent
 * get() = browser.browser.component
 * }
 */

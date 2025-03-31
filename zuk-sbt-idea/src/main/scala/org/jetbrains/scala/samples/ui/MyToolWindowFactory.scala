// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package org.jetbrains.scala.samples.ui

import com.github.continuedev.continueintellijextension.toolWindow.ContinueBrowser
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.{ToolWindow, ToolWindowFactory}
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.{JBLabel, JBOptionButton, JBPanel, JBTabbedPane, JBTextArea, JBTextField}
import com.intellij.ui.content.{Content, ContentFactory}
import com.intellij.ui.jcef.JBCefBrowser
import org.jetbrains.scala.samples.SamplePluginBundle
import zuk.idea.plugin.ui.{StockMonitor, TabStock}

import javax.swing.{AbstractAction, Action, JOptionPane, JPanel}
import java.awt.{Frame, GridLayout}
import java.awt.event.ActionEvent
import java.nio.file.Paths
import java.time.LocalTime
import javax.swing._
import scala.collection.mutable.ListBuffer


class MyToolWindowFactory extends ToolWindowFactory {

  /***
    idea 插件开发一个 ToolWindow ， 需求如下：
    1. 添加两个输入框，一个启动按钮
    2. 当点击启动按钮时将两个输入框的数据弹出
   */
  override def createToolWindowContent(project: Project, toolWindow: ToolWindow): Unit = {
    val tabbedPane = new JBTabbedPane()
    addTab1(tabbedPane)
    addTab2(tabbedPane)

    val content = ContentFactory.getInstance().createContent(tabbedPane, "", false)
    toolWindow.getContentManager.addContent(content)
  }

  private def addTab1(tabbedPane: JBTabbedPane) = {
    val tab1 = new JBPanel()
    val input1 = new JBTextField()
    val input2 = new JBTextField()
    val textArea = new JBTextArea()

    tab1.add(new JBLabel("my.cool.tool.window"))
    val mainAction = new AbstractAction("主操作") {
      def actionPerformed(e: ActionEvent ): Unit = {
        StockMonitor.printData()
        val url = input1.getText
        val price = input2.getText
        //printData(tabbedPane, url, price)
        JOptionPane.showMessageDialog(tab1, s"${url},${price}")
      }
    }
    val list = ListBuffer[Action]()
    val action1 = new AbstractAction("选项 A") {
      def actionPerformed(e: ActionEvent ): Unit = {
        JOptionPane.showMessageDialog(tab1, "选项 A 被点击！")
      }
    }
    val action2 = new AbstractAction("选项 B") {
      def actionPerformed(e: ActionEvent ): Unit = {
        JOptionPane.showMessageDialog(tab1, "选项 B 被点击！")
      }
    }
    list += action1
    list += action2
    val button = new JBOptionButton(mainAction, list.toArray)

    tab1.add(button)

//    val tabStock1 = new TabStock()
//    tab1.add(tabStock1.getPanelMain)
    tabbedPane.addTab("tool", tab1)
  }

  private def addTab2(tabbedPane: JBTabbedPane) = {

    val htmlFilePath = "https://www.baidu.com/"
    // 创建 JCEF 浏览器实例
    val browser = new JBCefBrowser(htmlFilePath)
    val tab2 = new JBPanel()
    tab2.add(browser.getComponent)
    tabbedPane.addTab("browser", tab2)

  }

  def printData(tab: JBTabbedPane, url: String, price: String): Unit = {
    try {
      while (true) {
        val now = LocalTime.now
        val hour = now.getHour
        if (hour >= 15) System.exit(1)
        var responseDto = StockMonitor.getHttp(url)
        while (responseDto != null && responseDto.getData == null) {
          responseDto = StockMonitor.getHttp(url)
        }
        val dataDto = responseDto.getData.get(responseDto.getData.size - 1)
        System.out.println(dataDto.getTime + "  " + dataDto.getPrice)
        if (dataDto.getPrice.toFloat >= price.toFloat) {
//          val frame = new Frame
//          frame.setAlwaysOnTop(true)
          JOptionPane.showMessageDialog(tab, "OK", "提示", 2)
        }
        Thread.sleep(5000)
      }
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }



}

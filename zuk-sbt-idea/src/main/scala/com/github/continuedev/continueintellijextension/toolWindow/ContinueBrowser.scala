package com.github.continuedev.continueintellijextension.toolWindow

import com.github.continuedev.continueintellijextension.services.ContinueExtensionSettings
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.ui.jcef.JBCefBrowser
import org.cef.CefApp
import org.jetbrains.scala.samples.ui.CustomSchemeHandlerFactory

class ContinueBrowser(val project: Project, url: String) {

  private def registerAppSchemeHandler() {
    CefApp.getInstance().registerSchemeHandlerFactory(
      "http",
      "continue",
      new CustomSchemeHandlerFactory()
    )
  }

  val browser: JBCefBrowser = null

  def init() = {
    val isOSREnabled = ServiceManager.getService(classOf[ContinueExtensionSettings]).continueState.enableOSR

  }

}

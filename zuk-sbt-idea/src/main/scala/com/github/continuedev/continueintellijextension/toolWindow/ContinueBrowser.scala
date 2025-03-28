package com.github.continuedev.continueintellijextension.toolWindow

import com.github.continuedev.continueintellijextension.activities.ContinuePluginDisposable
import com.github.continuedev.continueintellijextension.constants.MessageTypes
import com.github.continuedev.continueintellijextension.services.{ContinueExtensionSettings, ContinuePluginService}
import com.google.gson.{Gson, JsonObject, JsonParser}
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.jcef.{JBCefBrowser, JBCefBrowserBase, JBCefJSQuery}
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.jetbrains.scala.samples.ui.CustomSchemeHandlerFactory

import java.util
import java.util.UUID

class ContinueBrowser(val project: Project, url: String) {

  private def registerAppSchemeHandler() {
    CefApp.getInstance().registerSchemeHandlerFactory(
      "http",
      "continue",
      new CustomSchemeHandlerFactory()
    )
  }

  var browser: JBCefBrowser = null

  def init() = {
//    val isOSREnabled = ServiceManager.getService(classOf[ContinueExtensionSettings]).continueState.enableOSR
//
//    this.browser = JBCefBrowser.createBuilder().setOffScreenRendering(isOSREnabled).build()
    this.browser = JBCefBrowser.createBuilder().setOffScreenRendering(true).build()

    registerAppSchemeHandler()
    browser.loadURL(url)
    Disposer.register(ContinuePluginDisposable.getInstance(this.project), this.browser)

    // Listen for events sent from browser
    val myJSQueryOpenInBrowser = JBCefJSQuery.create(browser.asInstanceOf[JBCefBrowserBase])

    myJSQueryOpenInBrowser.addHandler((msg: String) => {
      val parser = new JsonParser()
      val json: JsonObject = parser.parse(msg).getAsJsonObject
      val messageType = json.get("messageType").getAsString
      val data = json.get("data")
      val messageId = json.get("messageId").getAsString

      val continuePluginService = ServiceManager.getService(
        this.project,
        classOf[ContinuePluginService]
      )

      val respond = (data: AnyRef) => {
        sendToWebview(messageType, data, if(messageId!=null) messageId else uuid())
      }

      if (MessageTypes.PASS_THROUGH_TO_CORE.contains(messageType)) {
//        continuePluginService.coreMessenger?.request(messageType, data, messageId, respond)
//        return@addHandler null
      }

      // If not pass through, then put it in the status/content/done format for webview
      // Core already sends this format
      val respondToWebview = (data: AnyRef) => {
        val map = scala.collection.mutable.HashMap[String, AnyRef]()
        map.put("status" , "success")
        map.put("content" , data)
        map.put("done" , true)
        sendToWebview(messageType, map, if( messageId!=null) messageId else uuid())

      }

      if(msg!=null){
        //continuePluginService.ideProtocolClient?.handleMessage(msg, respondToWebview)
      }

      null
    })
  }

  def executeJavaScript(browser: CefBrowser, myJSQueryOpenInBrowser: JBCefJSQuery) = {
    // Execute JavaScript - you might want to handle potential exceptions here
    val script = """window.postIntellijMessage = function(messageType, data, messageId) {
                const msg = JSON.stringify({messageType, data, messageId});
                ${myJSQueryOpenInBrowser.inject("msg")}
            }""".trim
    if(browser!=null){
      browser.executeJavaScript(script, browser.getURL, 0)
    }
  }

  def sendToWebview(messageType: String,
                    data: AnyRef,
                    messageId: String = uuid()
                   ): Unit = {
    val map = scala.collection.mutable.HashMap[String, AnyRef]()
    map.put("messageId", messageId)
    map.put("messageType", messageType)
    map.put("data", data)

    val jsonData = new Gson().toJson(map)

    val jsCode = this.buildJavaScript(jsonData)

    try {
//      this.browser.executeJavaScriptAsync(jsCode).onError {
//        println("Failed to execute jsCode error: ${it.message}")
//      }
    }
    catch {
      case error: IllegalStateException => println(s"Webview not initialized yet ${error}")
    }
  }

  private def buildJavaScript(jsonData: String): String = {
    return """window.postMessage($jsonData, "*");"""
  }

  def uuid(): String = {
    UUID.randomUUID().toString
  }

}

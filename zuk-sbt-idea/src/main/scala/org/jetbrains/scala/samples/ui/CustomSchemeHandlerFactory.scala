package org.jetbrains.scala.samples.ui

import org.cef.browser.{CefBrowser, CefFrame}
import org.cef.callback.{CefCallback, CefSchemeHandlerFactory}
import org.cef.handler.CefResourceHandler
import org.cef.misc.{IntRef, StringRef}
import org.cef.network.{CefRequest, CefResponse}

import java.net.URLConnection

class CustomSchemeHandlerFactory extends CefSchemeHandlerFactory {

  override def create(cefBrowser: CefBrowser,
                      cefFrame: CefFrame,
                      s: String,
                      cefRequest: CefRequest): CefResourceHandler = {
    null
  }

}

class CustomResourceHandler extends CefResourceHandler {
  private var state: ResourceHandlerState = ClosedConnection
  override def processRequest(cefRequest: CefRequest, cefCallback: CefCallback): Boolean = {
    false
  }

  override def getResponseHeaders(cefResponse: CefResponse, intRef: IntRef, stringRef: StringRef): Unit = {

  }

  override def readResponse(bytes: Array[Byte], i: Int, intRef: IntRef, cefCallback: CefCallback): Boolean = {
    false
  }

  override def cancel(): Unit = {

  }

}

class CustomResourceHandler extends CefResourceHandler {
  private var state: ResourceHandlerState = ClosedConnection
  private var currentUrl: String = null
  override def processRequest(cefRequest: CefRequest,
                              cefCallback: CefCallback): Boolean = {
    val url = cefRequest.getURL
    if(url!=null){
      val pathToResource: String = url.replace("http://continue", "webview/").replace("http://localhost:5173", "webview/")
      val newUrl = this.getClass.getClassLoader.getResource(pathToResource)
      state = new OpenedConnection(newUrl.openConnection())
      currentUrl = url
      cefCallback.Continue()
      true
    }
    else {
      false
    }
  }

  override def getResponseHeaders(cefResponse: CefResponse,
                                  responseLength: IntRef,
                                  redirectUrl: StringRef): Unit = {
    if(currentUrl!=null){
      if(currentUrl.contains("css")){
        cefResponse.setMimeType("text/css")
      }
      if(currentUrl.contains("js")){
        cefResponse.setMimeType("text/javascript")
      }
      if(currentUrl.contains("html")){
        cefResponse.setMimeType("text/html")
      }
    }
    state.getResponseHeaders(cefResponse, responseLength, redirectUrl)
  }

  override def readResponse(dataOut: Array[Byte], bytesToRead: Int, bytesRead: IntRef, callback: CefCallback): Boolean = {
    state.readResponse(dataOut, bytesToRead, bytesRead, callback)
  }

  override def cancel(): Unit = {
    state.close()
    state = ClosedConnection
  }
}

class ResourceHandlerState {
  def getResponseHeaders(cefResponse: CefResponse,
                         responseLength: IntRef,
                         redirectUrl: StringRef): Unit = {

  }

  def readResponse(
    dataOut: Array[Byte],
    bytesToRead: Int,
    bytesRead: IntRef,
    callback: CefCallback
  ): Boolean = false

  def close() {}
}

class OpenedConnection(private val connection: URLConnection) extends ResourceHandlerState {

}

object ClosedConnection extends ResourceHandlerState {
  override def getResponseHeaders(
    cefResponse: CefResponse,
    responseLength: IntRef,
    redirectUrl: StringRef
  ) {
    cefResponse.setStatus(404)
  }
}

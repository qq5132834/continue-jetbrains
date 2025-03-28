package org.jetbrains.scala.samples.ui

import org.cef.browser.{CefBrowser, CefFrame}
import org.cef.callback.{CefCallback, CefSchemeHandlerFactory}
import org.cef.handler.{CefLoadHandler, CefResourceHandler}
import org.cef.misc.{IntRef, StringRef}
import org.cef.network.{CefRequest, CefResponse}

import java.io.InputStream
import java.net.URLConnection

class CustomSchemeHandlerFactory extends CefSchemeHandlerFactory {

  override def create(browser: CefBrowser,
                      frame: CefFrame,
                      schemeName: String,
                      request: CefRequest
                     ): CefResourceHandler = {
    new CustomResourceHandler()
  }
}


class CustomResourceHandler extends CefResourceHandler {
  private var state: ResourceHandlerState = ClosedConnection
  private var currentUrl: String = null
  override def processRequest(cefRequest: CefRequest,
                              cefCallback: CefCallback
                             ): Boolean = {
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

  override def readResponse(dataOut: Array[Byte],
                            bytesToRead: Int,
                            bytesRead: IntRef,
                            callback: CefCallback): Boolean = {
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
  private lazy val inputStream: InputStream = if(connection==null) null else connection.getInputStream

  override def getResponseHeaders(cefResponse: CefResponse,
                                  responseLength: IntRef,
                                  redirectUrl: StringRef
                                 ): Unit = {
    try {
      if(connection!=null){
        val url = connection.getURL.toString
        if(url.contains("css")){
          cefResponse.setMimeType("text/css")
        }
        else if(url.contains("js")){
          cefResponse.setMimeType("text/javascript")
        }
        else if(url.contains("html")){
          cefResponse.setMimeType("text/html")
        }
        else {
          cefResponse.setMimeType(connection.getContentType)
        }
        responseLength.set(0)
        if(inputStream!=null){
          if(inputStream.available()!=null){
            responseLength.set(inputStream.available())
          }
        }
        cefResponse.setStatus(200)
      }
      else {
        // Handle the case where connection is null
        cefResponse.setError(CefLoadHandler.ErrorCode.ERR_FAILED)
        cefResponse.setStatusText("Connection is null")
        cefResponse.setStatus(500)
      }
    }
    catch {
      case exception: Exception =>
        exception.printStackTrace()
        cefResponse.setError(CefLoadHandler.ErrorCode.ERR_FILE_NOT_FOUND)
        cefResponse.setStatusText(exception.getLocalizedMessage)
        cefResponse.setStatus(404)
    }
    finally {
      //
    }
  }

  override def readResponse(dataOut: Array[Byte],
                            bytesToRead: Int,
                            bytesRead: IntRef,
                            callback: CefCallback
                           ): Boolean = {
    if(inputStream!=null){
      val availableSize = inputStream.available()
      if(availableSize>0){
        val maxBytesToRead = if (availableSize > bytesToRead) bytesToRead else  availableSize
        val realBytesRead = inputStream.read(dataOut, 0, maxBytesToRead)
        bytesRead.set(realBytesRead)
        true
      }
      else {
        inputStream.close()
        false
      }
    }
    else {
      false
    }
  }

  override def close(): Unit = if(inputStream!=null) inputStream.close()
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

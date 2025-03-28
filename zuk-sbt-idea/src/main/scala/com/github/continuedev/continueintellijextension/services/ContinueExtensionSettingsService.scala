package com.github.continuedev.continueintellijextension.services

import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.components.{PersistentStateComponent, ServiceManager, State, Storage}

import java.util.concurrent.ScheduledFuture

class ContinueExtensionSettingsService {



}

object ContinueExtensionSettings {
  val instance: ContinueExtensionSettings = ServiceManager.getService(classOf[ContinueExtensionSettings])
}

@State(
  name = "com.github.continuedev.continueintellijextension.services.ContinueExtensionSettings"
//  ,
//  storages = {Storage("ContinueExtensionSettings.xml")}
)
class ContinueExtensionSettings extends PersistentStateComponent[ContinueState] {

  var continueState: ContinueState = new ContinueState
  private var remoteSyncFuture = null

  override def getState: ContinueState = {
    this.continueState
  }

  override def loadState(state: ContinueState): Unit = {
    continueState = state
  }


}

class ContinueState {
  var lastSelectedInlineEditModel: String = null
  var shownWelcomeDialog: Boolean = false
  var remoteConfigServerUrl: String = null
  var remoteConfigSyncPeriod: Int = 60
  var userToken: String = null
  var enableTabAutocomplete: Boolean = true
  var ghAuthToken: String = null
  var enableContinueTeamsBeta: Boolean = false
  var enableOSR: Boolean = shouldRenderOffScreen()
  var displayEditorTooltip: Boolean = true
  var showIDECompletionSideBySide: Boolean = false
  var continueTestEnvironment: String = "production"


  /**
   * This function checks if off-screen rendering (OSR) should be used.
   *
   * If ui.useOSR is set in config.json, that value is used.
   *
   * Otherwise, we check if the pluginSinceBuild is greater than or equal to 233, which corresponds
   * to IntelliJ platform version 2023.3 and later.
   *
   * Setting `setOffScreenRendering` to `false` causes a number of issues such as a white screen flash when loading
   * the GUI and the inability to set `cursor: pointer`. However, setting `setOffScreenRendering` to `true` on
   * platform versions prior to 2023.3.4 causes larger issues such as an inability to type input for certain languages,
   * e.g. Korean.
   *
   * References:
   * 1. https://youtrack.jetbrains.com/issue/IDEA-347828/JCEF-white-flash-when-tool-window-show#focus=Comments-27-9334070.0-0
   *    This issue mentions that white screen flash problems were resolved in platformVersion 2023.3.4.
   * 2. https://www.jetbrains.com/idea/download/other.html
   *    This documentation shows mappings from platformVersion to branchNumber.
   *
   * We use the branchNumber (e.g., 233) instead of the full version number (e.g., 2023.3.4) because
   * it's a simple integer without dot notation, making it easier to compare.
   */
  private def shouldRenderOffScreen(): Boolean = {
    val minBuildNumber = 233
    val applicationInfo = ApplicationInfo.getInstance()
    val currentBuildNumber = applicationInfo.getBuild.getBaselineVersion
    currentBuildNumber >= minBuildNumber
  }
}


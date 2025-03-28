package com.github.continuedev.continueintellijextension.activities

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

/**
 * The service is a parent disposable that represents the entire plugin lifecycle
 * and is intended to be used instead of the project/application as a parent disposable,
 * ensures that disposables registered using it as parents will be processed when the plugin is unloaded to avoid memory leaks.
 *
 * @author lk
 */
//@Service(Service.Level.APP, Service.Level.PROJECT)
class ContinuePluginDisposable extends Disposable {

  override def dispose(): Unit = {

  }
}

object ContinuePluginDisposable {

  def getInstance(): ContinuePluginDisposable = {
    ApplicationManager.getApplication().getService(classOf[ContinuePluginDisposable])
  }

  def getInstance(project: Project): ContinuePluginDisposable = {
    project.getService(classOf[ContinuePluginDisposable])
  }

}

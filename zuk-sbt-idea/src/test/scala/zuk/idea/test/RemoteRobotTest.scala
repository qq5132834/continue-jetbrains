package zuk.idea.test

import com.intellij.remoterobot.RemoteRobot
import com.intellij.remoterobot.fixtures.JButtonFixture
import com.intellij.remoterobot.search.locators


object RemoteRobotTest {

  def main(args: Array[String]): Unit = {
    val remoteRobot = new RemoteRobot("http://127.0.0.1:8082")
//    val os = remoteRobot.getOs
//    val newProjectButton = remoteRobot.find(JButtonFixture::class.java, byXpath("//div[@text='New Project']"))
//    newProjectButton.click()
    println("New Project button clicked!")
  }

}

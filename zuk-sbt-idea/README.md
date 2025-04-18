# 构建生成 idea 插件包
```
sbt -> sbt tasks -> packageArtifactZip （右键） -> run 

在target 下生成  xxx.zip 文件
```

# 安装wls
```
在管理员模式下打开 PowerShell 或 Windows 命令提示符，方法是右键单击并选择“以管理员身份运行”，输入 wsl --install 命令，然后重启计算机。
```

# Sample IntelliJ platform plugin written in Scala

[![JetBrains team project](http://jb.gg/badges/team.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)

This is an example IJ plugin written in Scala and managed by SBT.

Apart from the basic IJ plugin structure, 
this project also provides some example code to play with and demonstrates some IntelliJ platform API features.

Extended documentation on the build features such as packaging the artifact, uploading to a plugin marketplace, 
etc. is covered by the [sbt-idea-plugin](https://github.com/JetBrains/sbt-idea-plugin) readme

## Quick Start
- Open the `build.sbt` file in IntelliJ IDEA as a project
- Install JDK 17 and set it in `File | Project Settings | Project | SDK`
- To run your plugin, run or debug the `myAwesomeFramework` run configuration

## Example build features

- `intellijBuild` key in build.sbt contains the version of the dependency on IDEA, you can change it.

- `intellijPlugins` key in build.sbt contains a dependency on the "Properties" IJ plugin

- there's an external library dependency that will be packaged into your plugin distribution

- Don't forget to replace the plugin name in build.sbt. You also need to change the name in resources/META-INF/plugin.xml.

## Example plugin components

- `PopupDialogAction` creates an action under the "Tools" menu
- `SimpleCompletionContributor` adds the "HELLO" completion item to the values of the properties files
- `FileOpenedListener` shows a popup when a file is opened in the editor
- `ApplicationHelloService` and `ProjectHelloService` show examples of IJ platform service components
- `MyToolWindowFactory` creates a new ToolWindow at the bottom of the window

## 一、作品简介

Simple notebook是一个简单的笔记本，是笔者在面向对象程序设计课程中的大作业，结合面向对象编程的思想，基于javaFX平台开发，目前功能尚不完善，有待继续开发。

## 二、功能分析与建模

此笔记本最终拟实现的功能主要有三个：

* 一，支持从本地打开文件以及保存文件到本地；
* 二、支持使用markdown语法进行笔记，markdown笔记的显示效果呈现在编辑文本的旁边，随更改同时呈现；
* 三、支持云存储接入，在云端储存笔记

下面对提到的功能进行分析和需求建模

#### 	1.支持从本地打开文件以及保存文件到本地

由于开发基于javaFX平台，可以通过添加TextArea直接进行编辑，那么真正需要完成的其实就是：

- 从本地打开文件时读取文件内容到TextArea
- 在编辑TextArea后将其内容保存到位于本地的文件

对于第二个要求，有两种完成方式，可以通过添加一个按钮进行专门的保存，也可以对TextArea添加一个监听器，在编辑的同时自动保存，我采取的是第二种方式

考虑上述两个要求，分析得出需求模型如下

```
[用例名称]
    读取文件，编辑后保存文件
[场景]
    who   : 本地文件、TextArea、用户
    where : 笔记软件的主界面
    when  : 用户打开文件时
[用例描述]
    1.用户打开一个本地文件
    2.文件内容被TextArea呈现出来
    3.用户开始编辑TextArea
    4.在用户编辑的同时自动保存
```

提取出动词与名词如下：                     

- 动词：打开、呈现、编辑、保存
- 名词：用户、文件内容、TextArea

进行分析

* 对于用户打开文件的过程，通过在fxml文件中添加按钮，同时在其对应的Controller类中添加按钮点击时的函数完成；

* 对于文件内容被TextArea呈现的过程，以及在用户编辑的同时将TextArea保存到文件的过程，由于需要对文件内容进行读取和写入，封装实现一个FileTools类，在其中提供读取文件和写入文件的方法

* 用户编辑TextArea的过程由javafx库提供的TextArea类完成

上述所需完成的类如下图：

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%B8%80%E6%AC%A1/1-1.png" width="30%" />
</div>


注：NoteBookOperation即为fxml文件对应的Cotroller，initialize函数包括对TextArea的监听器]: 

#### 2.支持使用markdown语法进行笔记，markdown文本及图片呈现在编辑文本的旁边，随更改同时呈现

采取将markdown文本转换成html文本的方式实现这个功能，主要借助flexmark-java库进行实现，将转换函数封装在FileTools工具类中

考虑上述要求，分析得出需求模型如下

```
[用例名称]
    支持markdown进行笔记
[场景]
    who   : markdown文本、html文本
    where : 笔记软件的主界面的两个区域
    when  : 用户做笔记时
[用例描述]
    1.用户在TextArea区域编辑markdown文本
    2.markdown的源文本被转换成对应的html文本
    3.html文本在主界面中的右侧同时呈现出来 
```

提取出动词与名词如下： 

* 动词：编辑、同时呈现（html文本）、转换
* 名词：用户、markdown文本（由用户编辑）、TextArea区域、html文本

其中未实现的有：同时呈现（html文本）、转换、html文本

进行分析

* 对于markdown文本转换成为html文本，选择在FileTools类中再封装一个方法，依据flexmark-java库完成从markdown文本到html文本的转换
* 对于html文本的同时呈现，结合Controller中已经实现的监听器，选择javafx提供的WebView类，在fxml布局中实现一个WebView来作为html文本的载体，达到将html文本的效果展示出来的目的

增加方法后的FileTools类：

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%B8%80%E6%AC%A1/2-1.png" width="30%" />
</div>

增加WebView实现后的NoteBookOperation（Controller）类：

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%B8%80%E6%AC%A1/2-2.png" width="30%" />
</div>

实例图

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%B8%80%E6%AC%A1/2-3.png" width="50%" />
</div>


#### 3.支持云存储接入，在云端储存笔记

对于云存储接入功能的实现，目前的初步想法是借助百度网盘的接口，用户登录后获取对应的access_token，然后将当前NoteBook作为文件上传到用户的百度网盘。

考虑上述要求，分析需求模型

```
[用例名称]
    用户在云端储存笔记
[场景]
    who   : 用户、笔记本
    where : 笔记软件的主界面
    when  : 用户做笔记时
[用例描述]
    1.用户点击后要求上传
    2.用户登录
    2.笔记被上传至云端
```

提取出动词与名词如下： 

* 动词：点击、登录、上传
* 名词：用户、笔记本

其中未实现的有：点击、上传

进行分析

* 对于点击，可以通过增加一个按钮以及对应的点击触发方法的方式很简单地实现
* 复杂与困难之处在于登录和上传，初步设想是添加一个类用来获得用户的access_token以及将当前笔记本上传至指定云路径

初步设计的UploadController类

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%B8%80%E6%AC%A1/3-1.png" width="20%" />
</div>

## 三、核心流程设计分析

在上一节中，我们对笔记软件的功能进行了需求建模，明确了实现的流程，在这一节中，将对笔记本中涉及到的类与类间关系进行分析，进一步对笔记本的工作流程进行分析。

涉及到的类间关系如下图：
<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%BA%8C%E6%AC%A1/3-1.png" width="60%" />
</div>
可以看到最关键的类是NoteBookOperation，NoteBookOperation组合了TextArea、NoteBook、WebView这三类以完成将markdown笔记转为html呈现的目的。此外，这个类依赖FileTools和UploadController这两个类来进行读取文件、保存文件和上传文件，同时它实现了软件的初始化以及对输入内容的监听。

在流程的设计中，尽量符合了组合原则、单一职责原则和接口隔离原则，由于基于javafx平台设计，整体架构采用的是MVC模式，基本上是结合了23种设计模式中的种观察者模式、组合模式和策略模式。在每次修改、呈现当前正在编辑的笔记时，实际上用的是一个NoteBook的实例currentNoteBook，这里采用了单例模式

用户编辑笔记的过程的时序图如下

<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%BA%8C%E6%AC%A1/3-2.png" width="60%" />
</div>
用户编辑，随后软件进行处理，呈现内容即时被用户看到

设计实现的上传笔记的过程如下：
<div align=center>
<img src="https://gitee.com/flametornado/object-oriented-programming/raw/master/OOP%E5%A4%A7%E4%BD%9C%E4%B8%9A%E7%AC%AC%E4%BA%8C%E6%AC%A1/3-3.png" width="60%" />
</div>
用户点击上传，软件完成上传，远程存储端收到文件

在关键的流程中，尽可能地利用面向对象的思想处理问题，用户编辑笔记后，笔记内容变化被NoteBookOperation监听到，利用FileTools将新的内容写入笔记文件以及将html文本输出，利用WebView呈现html文本的效果。用户点击上传笔记后，内容被记录在笔记文件中，UploadController将文件上传至提前设置好的远程bucket中，从而用户可以在远程看到自己上传的文本。可以看到，在软件工作的过程中，任务由多个对象分工完成，每个对象分别完成自己的工作。
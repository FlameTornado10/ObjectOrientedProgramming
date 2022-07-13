## 一、作品简介

Simple Notebook 是一个简单的笔记本，是我在面向对象程序设计课程中的大作业，结合面向对象编程的思想，基于javaFX平台开发，目前功能尚不完善，有待继续开发。

## 二、功能分析与建模

此笔记本最终拟实现的功能主要有三个：

* 支持从本地打开文件以及保存文件到本地；
* 支持使用markdown语法进行笔记，markdown笔记的显示效果呈现在编辑文本的旁边，随更改同时呈现；
* 支持云存储接入，在云端储存笔记

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

在流程的设计中，尽量符合了组合原则、单一职责原则和接口隔离原则，由于基于javafx平台设计，整体架构采用的是MVC模式，基本上是结合了23种设计模式中的观察者模式、组合模式和策略模式。

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

在关键的流程中，尽可能地利用面向对象的思想处理问题，用户编辑笔记后，笔记内容变化被 NoteBookOperation 监听到，利用 FileTools 将新的内容写入笔记文件以及将 html 文本输出，利用 WebView 呈现 html 文本的效果。用户点击上传笔记后，内容被记录在笔记文件中，UploadController 将文件上传至提前设置好的远程 bucket 中，从而用户可以在远程看到自己上传的文本。可以看到，在软件工作的过程中，任务由多个对象分工完成，每个对象分别完成自己的工作。

## 四、高级设计意图分析

通过使用阿里云对象存储OSS的服务，我借助阿里云在 java 中的接口，在 SimpleNotebook 中实现了上传功能，并且使用对话框告知用户笔记本的下载地址。

我的设计最终完成了，在我的设计中，我尽量符合了组合原则、单一职责原则和接口隔离原则这三个原则，同时学习了中介者模式、观察者模式、组合模式以及策略模式这四种设计模式进行设计，下面对我的设计中体现出来的高级设计意图进行分析。

#### 1. 对设计原则的体现进行分析

#####			i. 组合原则

​	组合原则（又名合成复用原则）的特点是将已有的对象纳入新的对象，这点在 Simple Notebook 的设计中有所体现，从核心流程设计中可以知道 App 中最关键的类是 NoteBookOperation ，它组合了 TextArea、NoteBook、WebView 这三个类来进行工作，它使用的方法有：

* TextArea 的 getText 和 setText 方法、
* NoteBook 类的 setName、setFile、getFile 和 setTextArea 等方法
* WebView 类的 getEngine 方法以及得到的 engine 的 loadContent 方法

##### 			ii. 单一职责原则

​	单一职责原则提出对象不应该承担太多职责，核心在于控制类的粒度大小，降低类的复杂度，提高系统的可维护性。在 Simple Notebook 的设计中，这个设计原则有所体现：

* 应用场景的载入由 App 类实现
* 应用对文件的读写由 FileUtil 类实现
* 笔记本的功能由 NoteBook 类实现
* 用户对笔记本的基础操作由 NoteBookOperation 类实现
* 用户上传笔记本的操作由另一个类 UploadController 实现

​	可以看到设计中不同类别的功能由相应的类去实现，这样对于应用的可维护性和扩展性都有好处。

##### iii.  接口隔离原则

​	接口隔离原则要求设计者将较为庞大的接口拆分成更具体的接口，它和单一职责原则一样都是为了提高类的内聚性、降低它们之间的耦合性，不同之处在于：单一职责原则注重的是职责，主要是约束类的行为，针对程序中的实现和细节；而接口隔离原则注重对接口依赖的隔离，主要是约束类的接口，针对抽象和程序整体的构建。在 Simple Notebook 的设计中，这个设计原则有所体现：

* 应用对文件的读写过程由 FileUtil 提供 readFile 方法和 writeFile 方法
* 笔记本的文件名称、内容修改由 NoteBook 提供 setName、getFile、 setFile 等方法
* 用户修改 TextArea 后，对笔记本的操作过程由 NoteBookOperation 提供监听器
* 用户上传笔记本的操作过程由 UploadController 类提供uploadDocument 方法

​	可以看到，接口隔离原则通过定义合理的接口粒度大小，减少了代码冗余，提高了设计的灵活性和可拓展性。

#### 2.对设计模式的体现进行分析

##### i. 中介者模式

​	中介者模式定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，它要求类之间各司其职，符合单一职责原则，降低对象之间的耦合性，提高系统的灵活性。由于 Simple Notebook 的设计是基于 MVC 框架的， 在这个框架中，控制器（C）就是模型（M）和视图（C）的中介者，这正是中介者模式的应用。

​	中介者模式有一个主要缺点，即它将原本多个对象直接的相互依赖变成了中介者和多个同事类的依赖关系。当同事类越多时，中介者就会越臃肿，变得复杂且难以维护。这个缺点在我的设计中也有影响，主要表现在 NoteBookOperation 这个作为控制器的类关联起来了其他多个对象，相比较其他类来说较为复杂。

##### ii.观察者模式

​	观察者模式指多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖它的对象都将得到通知并被自动更新，它的特点在于，降低了目标与观察者之间的耦合，在目标与观察者之间建立了一套触发机制。在我的设计中，观察者模式有所体现：

* 用户对 TextArea 进行编辑，与此同时 TextArea 的观察者读取到新的内容，进行 markdown 文本的解析，将结果加载入 webView
* 用户点击上传按钮，点击行为被观察到，文件被上传

​	基于 MVC 框架的设计使得视图与模型分离，”发布——订阅“的机制使得应用的整个界面作为发布者，观察者可以是任意数量，一个按钮被点击了，视图并不会知道接下来会发生什么，但相应的过程会在观察者模式的机制下发生。

  ##### iii. 组合模式

​	组合模式使用户对单个对象和组合对象具有一致的访问性，它本身就是设计中对于组合原则的应用，正如之前对组合原则的应用进行介绍时所描述的，Simple Notebook 的设计中包含了对组合模式和组合原则的体现，除了之前提到的之外，应用的主界面中对于当前打开的所有 NoteBook 的管理也体现了组合模式，每打开一个笔记本，它都会被添加到应用程序中的一个表内。

​	组合模式的优点在于其使得应用更容易在组合体内加入新的对象，客户端不会因为加入了新的对象而需要修改代码。

##### iv. 策略模式

​	策略模式定义了算法族，分别封装起来，让他们之间可以相互替换，此模式让算法的变化独立于使用算法的客户。在 MVC 框架下，视图和控制器实现了经典的策略模式：视图是一个对象，可以被调整使用不同的策略（行为），而控制器提供了策略。视图和控制器实现了经典的策略模式：视图是一个对象，可以被调整使用不同的策略（行为），而控制器提供了策略（行为）。视图想换另一种行为，换控制器就可以了。视图只关心系统中可视的部分，对于任何界面行为，都委托给控制器处理。

​	比如用户编辑笔记，则界面在主界面，当用户上传时，界面会转到上传后弹出的对话框。

​	策略模式使得视图和模型之间的关系解耦，控制器负责和模型交互来传递用户的请求。而对于工作是怎么完成的，视图则毫不知情。

## 五、总结

​	这次课程设计让我对面向对象编程的思想理解更加深刻，同时也使我对 java 的功能更加了解，临近期末，三次大作业也全部完成，在这里祝愿老师和助教工作顺利，也希望这门课程越办越好！



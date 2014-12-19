android-app
===========
# **DynamicAchart简析** #
*注：本文假设你已经有Android开发环境*


该项目是我在工作中要绘制穿戴式设备传来的心率绘制实时心电图所做的Demo。<br>

## **一、项目的平台** ##

启动Eclipse，点击菜单并导入Android客户端项目，请确保你当前的Android SDK是最新版。<br>
*如果编译出错的处理方法：*
1.请修改项目根目录下的 project.properties 文件中的target。<br>

2.我的项目中为了兼容低版本采用了android.support.v7的库文件，如果style.xml有问题应该是需要引入该库。<br>
引入的方法参见Android开发文档https://developer.android.com/tools/support-library/setup.html<br>

如果你的SDK中没有\adt\adt-bundle\sdk\extras\android\support\v7该文件夹，你需要通过SDKManager下载。<br>
打开SDKManager，最下面的文件夹xtras展开Android Support Library点击下载。<br>
如果你被墙的话，可以通过该github网址https://github.com/koush/android-support-v7-appcompat下载，<br>
然后放到上述文件夹下。<br>
推荐使用Android 2.3 以上版本的SDK,默认编码UTF-8，请使用JDK1.7编译：

> target=android-21

## **二、项目的功能** ##

#### 1、AChartEngine ####
该项目主要运用AChartEngine库文件来绘制动态曲线图表。<br>
关于AChartEngine的介绍，请参见官网：http://www.achartengine.org/ <br>
googlecode链接：http://code.google.com/p/achartengine/ <br>
github链接：https://github.com/jondwillis/AChartEngine <br>

#### 2、动态绘制算法 ####

心电图的绘制需要不断填充新的数值同时移除旧的数据点，这与我的另一个动态图表项目不同。<br>
参照我的代码中updateChart()方法,如果你已经理解series和mDataset这两个变量的数据结构。<br>
你就可以了解我是如何添加新的数据点到series中，同时删除了series中的旧点。<br>
其实这也是我在学习Python时的一个编程小练习给我提供了思路，嘿嘿<br>

#### 3、参考链接 ####

http://stackoverflow.com/questions/20743109/dynamic-graph-of-sensor-data-with-achartengine-android
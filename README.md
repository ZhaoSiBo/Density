# Fast4Android屏幕适配文档
### 关于屏幕适配
##### 解决问题：给UI一套规则， 配合成熟的屏幕适配方案，告诉UI应该按照什么规则出设计图（主要指画布大小，dp和px的关系，文字大小也要合适，设计图不能出现小数点）
### 解释一下屏幕适配的目的：
屏幕适配的根本目的在于，不管实际物理尺寸，屏幕像素多大的手机，都尽量的贴合设计图，比如设计图是375dp*667dp（按照750pxx1334px尺寸320dpi计算），那么设计图上的187.5dp（375dp/2）就应该在手机上显示占用一半屏幕。
虽然谷歌设计dip可能是为了在更大的手机上展示更多的内容，但是因为Android屏幕碎片化的问题，只能开始做屏幕适配的工作。
### 屏幕适配概念：
1. px（像素）：设计图设计常用单元

2. 分辨率：长宽像素数量的乘积（例如1080x1920）

3. 屏幕尺寸：指手机对角线的物理尺寸（例如：某某手机，5.5寸手机屏幕），其中设计师给的设计图可能经常是按照IOS的屏幕尺寸给图的，这个有可能根据屏幕适配的方案不同，会产生不同影响，这里给出大部分IOS手机的屏幕尺寸

![IOS尺寸](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/v2-74195f3457be0b7ac2daf04ae467b2e0_hd.jpg?Expires=1587438597&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=wxZqoj%2BjYj%2FdGl0dToXhhmb%2B%2B2s%3D)

4. 屏幕像素密度（dpi）:每英寸的像素点数，例如每英寸内有160个像素点，则其像素密度为160dpi
    * 标准的项目密度就是 160dpi，既：1dpi = 1px
    * 计算公式： 像素密度=像素/屏幕物理尺寸
    * 标准屏幕像素密度（mdpi）：每英寸长度上还有160个像素点（160dpi），即称为标准屏幕像素密度（mdpi）。
    ![密度类型，分辨率，屏幕像素密度关系图](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/%E5%83%8F%E7%B4%A0%E5%AF%86%E5%BA%A6%E5%85%B3%E7%B3%BB%E5%9B%BE.jpg?Expires=1587438638&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=ONVdyb9j8hG3%2BPqrNCydrm1lPuU%3D)
    
5. 密度无关像素（dp或者dpi）：可以保证在不同屏幕像素密度的设备上显示相同的效果，是Android特有的长度单位。

6. 比例无关像素（sp）：字体大小专用单位 Android开发时用此单位设置文字大小，可根据字体大小首选项进行缩放； 推荐使用12sp、14sp、18sp、22sp作为字体大小，不推荐使用奇数和小数，容易造成精度丢失，12sp以下字体太小。

7. sp 与 dp 的区别：sp和dp很类似但唯一的区别是，Android系统允许用户自定义文字尺寸大小（小、正常、大、超大等等），当文字尺寸是“正常”时1sp=1dp=0.00625英寸，而当文字尺寸是“大”或“超大”时，1sp>1dp=0.00625英寸。类似我们在windows里调整字体尺寸以后的效果——窗口大小不变，只有文字大小改变


### 使用今日头条方案，适配手机屏幕

今日头条的实际适配方案本质上是动态修改手机的density（1dp 等于多少px）来进行设计图适配的

在将xml文件中的dp，转化成px，显示到屏幕的过程中，Android通过使用TypedValue#applyDimension(int unit, float value, DisplayMetrics metrics) 方法来进行转换
![TypedValue#applyDimension](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/TypedValue.webp?Expires=1587438650&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=MKLKfr3pnuWdLqwcwDDpNdRfH5Q%3D)
#### 所以，我们的适配方案就是修改手机中的density，来进行适配工作。我们只需要使，我们手机的宽度的dp值 = 设计图的宽度的dp值。这样，我们在xml文件中，所写的宽度，就会按照设计图中的宽度占比来显示。

#### 所以，我们修改后的 density = 设备的px / 设计图的dp值，我们只要将这个公式，应用在修改density的代码中即可。

### 如何配合UI

以本设计图为例

![样本设计图](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/v2-74195f3457be0b7ac2daf04ae467b2e0_hd.jpg?Expires=1587438663&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=jlqQ%2BP8g%2FoYGTFB2%2BstRCoVqjRg%3D)

#### 该设计图为750px * 1334px 大小，通过蓝湖，折算成 375dp * 750dp
#### 所以适配工作则为通过宽度适配，打到让不同dp宽度的手机适配为375dp宽度的效果
#### density=设备的物理像素/375dp


### 关于实现代码：

1. 采取自动初始化的策略，在ContenxtProvider中的onCreate（）方法中进行Context的初始化，并从AndroidManifest文件中获取MetaData的值，来作为屏幕适配的标准值
优点：可以进行自动初始化操作，不需要手动设置，完全自动化，缺点是没办法控制优化初始化时间

2. 目前只提供androidX自持效果

3. 通过使用Application中的registerActivityLifecycleCallbacks方法，适配所有Activity

4. 通过FragmentActivity的registerFragmentLifecycleCallbacks方法，适配所有Fragment

5. 通过DensityAdaptStrategy接口对适配策略进行抽取

6. 通过CancelAdapt接口来对取消适配的Activit进行标记

![Density的代码结构图](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/Dnesityd%E7%9A%84%E4%BB%A3%E7%A0%81%E7%BB%93%E6%9E%84.png?Expires=1587438677&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=C34FC4BTU0fFbKCriDgDpeo3CfY%3D
)

### 实现效果：

1. 通过约束布局中的约束，直接控制大小为50%等效于在xml文件中直接写入187dp，控制约束为100%等效于在xml文件中接入375dp，则证明，适配结果已经控制屏幕宽度始终未375dp


![Density的实现效果](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/Density%E7%9A%84%E5%AE%9E%E7%8E%B0%E6%95%88%E6%9E%9C.jpg?Expires=1587438686&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=90%2BV7wysgzno2F5qIzRji4rLbvg%3D
)


### 遗留的问题

1. 关于项目中文字大小的适配，一部分用户会调整手机的字体，来达到日常的观看效果，主要做法是根据目前计算的比例大小进行字体的同比例缩放
2. 关于bitmap，项目中经常出现使用bitmap，或者保存bitmap对象到文件中的情况，修改了屏幕适配之后，也要调用bitmap中的setDefaultDensity方法，来修改bitmap中的默认Density

### 一些小问题

1.开发过程中，关于APP的图标问题

这是推荐的图标尺寸

![推荐的图标尺寸](https://zhao-image.oss-cn-shenzhen.aliyuncs.com/Android%E6%8E%A8%E8%8D%90%E5%9B%BE%E6%A0%87%E5%B0%BA%E5%AF%B8.png?Expires=1587438700&OSSAccessKeyId=TMP.3KkcJhNj9oHF61yKAzQuyA14pzExMJKKK5wo4ALidVB1zSc6SGS1o6KQBiuCYewzndpekQNGeAcRHDkHdPcqXtVuCLZxQz&Signature=NyPKUHX0D9qvMgT9Vuopd%2BT153s%3D
)

2.关于图标问题，在8.0开始，图标开始分成前后景图标，需要UI配合制作特殊的，附上链接一篇讲解比较详细的文章https://blog.csdn.net/guolin_blog/article/details/79417483
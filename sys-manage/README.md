# sys-manage

后端代码，JDK21，SpringBoot3

前端代码：[sys-manage-vue](../sys-manage-vue/README.md)



* 工程结构说明

```
sys-manage-----------------总工程
   ├─sys-------------------主工程(启动工程)
   ├─file------------------文件工程
```
* 模块工程结构说明
```
module---------------------模块工程-聚合管理(如果想分多模块，则创建对应的模块工程，不想分就弄一个service就好了)
   ├─facade----------------对外工程-聚合管理
   │  ├─api-service--------service接口
   │  │  ├─service---------接口类
   │  │  │
   │  ├─common-------------对外工程公共包(所有对外工程会涉及到的公共包，比如：DTO,VO这类，以及一些提供的工具方法)
   │  │  ├─constants-------常量、枚举类等
   │  │  │  ├─enums--------枚举类
   │  │  ├─model-----------领域模型对象(DTO(req,res)、vo类等等(domain))
   │  │  │  ├─bo-----------业务对象
   │  │  │  ├─db-----------数据库对象
   │  │  │  ├─req----------接口请求对象
   │  │  │  ├─res----------接口放回对象
   │  │  │  ├─vo-----------前端某个组件返回对象，比如(k,v)、图表等
   │  │  ├─util------------对外提供的一些工具方法(按照工具名称分目录，不建议直接放当前目录)
   │  │  │
   ├─service---------------service工程，接口的实现，缓存和数据库等相关
   │  ├─aspect-------------切面
   │  ├─config-------------配置类相关(utils需要里需要spring管理的类推荐也在config中定义bean然后再注入使用)
   │  ├─constants----------常量、枚举类等
   │  │  ├─enums-----------枚举类
   │  ├─event--------------事件消费，自定义事件相关(作用类似当前应用内的mq,可同步也可异步使用.
   │  │                                     与当前业务不是强相关的操作,即事件中的操作成功与否不会影响到上面的业务,
   │  │                                     只是通知应该干什么了,例如登录完记录用户在线数,可以使用事件来处理,
   │  │                                     也可以作为广播去使用,一个事件有多个消费者)
   │  ├─jobs---------------任务
   │  ├─listen-------------监听
   │  ├─mapper-------------mapper类
   │  ├─model--------------领域模型对象
   │  │  ├─bo--------------业务对象
   │  │  ├─db--------------数据库对象
   │  ├─repository---------db存储层(可以理解为只是把基础的抽出来了进行缓存处理，可以基于业务设计判断是否需要)
   │  │  ├─redis-----------缓存类(基于db做的缓存层)
   │  │  ├─impl------------存储层实现类(调用mapper和redis进行缓存和查询)
   │  ├─service------------接口实现
   │  │  ├─impl------------实现类(根据业务设计调用mapper或db存储层(如果是宽表形式的通过多项缓存拼接返回))
   │  │  ├─redis-----------缓存类(基于业务做的缓存层)
   │  │  ├─sync------------异步类(调用实现类的接口，通过异步实现)
   │  ├─utils--------------工具(按照工具名称分目录，不建议直接放当前目录)
   │  │
   │  ├─resources----------资源目录(项目启动配置，logback配置等等)
   │  │  ├─mapper----------mapper类对应mybatis的xml类
   │  │  ├─META-INF--------可以配置spring相关属性以及新增配置类之后的提示文件
   │  │  │
----

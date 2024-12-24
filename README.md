# wxdgamig.spring.minigame

## 更新仓库地址

github https://github.com/orgs/wxd-gaming/repositories<br>
gitee &nbsp;&nbsp;&nbsp;https://gitee.com/wxd-gaming<br>
博客首页 https://www.cnblogs.com/wxd-gameing<br>

## 项目介绍

無心道 基于spring boot的 小游戏架构；
单进程多服部署模式，
模块化设计，采用spring data jpa 作为数据库访问层，
通过start容器实现隔离加载不同区服模块；
通过mini-cfg模块实现配置表读取，配置表支持动态更新；并且通过start模块的实现配置模块共享
mini-logic真正的业务逻辑，通过start容器以子容器形式隔离加载模块，

## 模块介绍

| 模块          | 说明                                | 
|-------------|-----------------------------------|
| mini-cfg    | 配置表，读取和解析excel文件，可以共享数据           |
| mini-entity | 实体类可共享内存                          |
| mini-logic  | 游戏模块，包含游戏逻辑，游戏数据，游戏配置，游戏规则等       |
| mini-start  | 启动模块容器模块，包含socket通信，http通信框架rpc框架 |


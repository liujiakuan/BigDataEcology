

![img](file:///C:/Users/MR.liu/AppData/Local/Temp/msohtmlclip1/01/clip_image001.png)

1.客户端提交一个Application，在客户端启动一个Driver进程。

2.Driver进程会向ResourceManager发送请求，启动ApplicationMaster的资源。

3.ResourceManager收到请求，随机选择一台NodeManager启动ApplicationMaster。这里的NodeManager相当于Standalone中的Worker节点。

4.ApplicationMaster启动后，会向ResourceManager请求一批container资源，用于启动Executor.

5.ResourceManager会找到一批NodeManager返回给ApplicationMaster,用于启动Executor。

6.ApplicationMaster会向NodeManager发送命令启动Executor。

7.Executor启动后，会反向注册给Driver，Driver发送task到Executor,执行情况和结果返回给Driver端
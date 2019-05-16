emqtt的概念、解释:


emq-topic:https://blog.csdn.net/qq_28877125/article/details/78360376
emq-docs:http://www.emqtt.com/docs/v3/getstarted.html


1.配置基础环境erlang
    erlang:https://blog.csdn.net/medeuz/article/details/79571395
    wget http://erlang.org/download/otp_src_20.3.tar.gz
    yum -y install make gcc gcc-c++ kernel-devel m4 ncurses-devel openssl-devel unixODBC-devel libtool libtool-ltdl-devel
    tar -zvxf otp_src_20.3.tar.gz
    cd otp
    ./otp_build autoconf
    ./configure
    make
    make install

2.emq,的文档及安装:
    下载地址：http://www.emqtt.com/downloads
    unzip emqx-macosx-v3.0.zip && cd emqx
    # debug模式启动emqx
    ./bin/emqx console
    # 启动emqx
    ./bin/emqx start
    # 检查运行状态
    ./bin/emqx_ctl status
    # 停止emqx
    ./bin/emqx stop

使用配置:
    控制台地址: http://127.0.0.1:18083，默认用户: admin，密码：public，修改成新密码newadmin123
    集群配置：http://www.emqtt.com/docs/v3/config.html#emq-x
















1.首先需要申请开通支付功能
	https://b.alipay.com/order/productDetail.htm?productId=2015110218012942
2.配置密钥,参考
	https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.VdnuoC&treeId=62&articleId=104746&docType=1
	
3.做程序,下载demo,我们采用的是java-utf-md5
	首先配置:
		AlipayConfig.java中的:
			partner:
			key:
		
		
		
		使用POST方式接收所有数据
		将数据使用AlipayNotify.verify() 验签方法验证。
		（1）验证签名是否正确。
		（2）验证是否是支付宝发来的通知。
		通过返回参数中的trade_status字段（交易状态）判断并可写入执行业务逻辑代码。
		使用out.print("success"); 打印success。不可有其他任何HTML代码。
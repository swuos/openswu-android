项目采用okHttp作为网络请求框架，为了迎合web-api服务端的接口设计，对网络请求进行了简单的封装。

## 1、执行环境HttpModel ##

app启动后将会全局初始化一个HttpModel对象作为单例模式存在，内部持有一个缓存的线程池和为UI线程服务的handler。

获取方式：

1. 在Activity/Fragment中：

	@Model
	private HttpModel httpModel;

2. 其他位置：

	private HttpModel httpModel = App.getInstance().getModel(HttpModel.class);

> 事实上，httpModel不只是为了网络请求提供执行环境，你可以使用该model的executor来执行任意的runnable代码

## 2、使用HttpRequester执行网络请求 ##

针对web-api的接口字段，我们对okhttp进行了修改，开发者使用时无需关心okhttp的其他特性。

使用时我们需要新建一个请求体，继承HttpRequester。

需要注意几个方法：

1. `setMethod(): HttpMethod`。返回HttpMethod枚举类，标明此次请求是post还是get（目前就设计了这两个）
2. `setApiUrl(): ApiUrl`。返回ApiUrl枚举类，该枚举类包含网络请求的url和日志打印的id，可在控制台看到请求的参数结果。
3. `appendUrl(String url)`。在get请求下，很有可能需要在url中拼接字段，而不是post时在body中添加，所以此方法可选择重写进行url的修改，具体案例在下面的代码中有说明。
4. `onPutParams(FormBody.Builder builder)`: FormBody.Builder builder。目前请求体参数只提供了FormBody的传入，考虑到app功能上的局限，也暂时没有提供其他类型的参数传入。post请求下该方法非常重要，如果是get方式，直接返回builder即可。
5. `onDumpData(JSONObject jsonObject): T`。该方法为结果解析，web-api返回成功的字段为json格式，所以在此处进行json转实体操作，可根据具体需求来决定此处逻辑。如果有转成实体类的需求可以使用JsonUtil.toList或者toObject方法进行转换，也可以直接提取特点字段手动组装成实体类返回。

使用案例：登录请求

登录请求api接口如下：`http://{server_url}/token/{swuId}/{password}`

即用户名密码拼接在url中，同时返回字段如下。

	{
	    success:true,
	    msg:"some information",
	    code:200,//204
	   	token:"token String"
	}

所以分析得出，我们需要手动拼接url，同时只需要获取结果中的token字段，然后手动组装成userInfo返回。请求体代码如下：


	/**
	 * 登录请求
	 * Created by wangyu on 2018/3/6.
	 */
	
	public class LoginRequester extends HttpRequester<UserInfo> {
	
	    private AccountInfo accountInfo;
	
	    public LoginRequester(AccountInfo accountInfo, @NonNull OnResultListener<UserInfo> listener) {
	        super(listener);
	        this.accountInfo = accountInfo;
	    }
	
	    @Override
	    protected UserInfo onDumpData(JSONObject jsonObject) throws JSONException {
	        //手动组装userInfo
	        UserInfo userInfo = new UserInfo();
	        userInfo.setStudentId(accountInfo.getUserName());
	        userInfo.setStudentName(accountInfo.getUserName());
	        userInfo.setToken(jsonObject.optString("token"));
	        return userInfo;
	    }
	
	    @NonNull
	    @Override
	    protected HttpMethod setMethod() {
	        return HttpMethod.GET;
	    }
	
	    @NonNull
	    @Override
	    protected ApiUrl setApiUrl() {
	        return ApiUrl.LOGIN_URL;
	    }
	
	    @Override
	    protected String appendUrl(String url) {//重写此方法拼接用户名密码
	        url = url + "/" + accountInfo.getUserName() + "/" + accountInfo.getUserPwd();
	        return url;
	    }
	
	    @NonNull
	    @Override
	    protected FormBody.Builder onPutParams(FormBody.Builder builder) {
	        return builder;//get请求直接返回builder
	    }
	}

## 3、业务代码 ##

以登录为例，业务代码如下

	 LoginRequester loginRequester = new LoginRequester(accountInfo, (code, userInfo, msg) -> {
				//此处处理请求结果
	            if (code == ErrorCode.RESULT_DATA_OK) {
	                saveAccountInfo(accountInfo);
	                saveUserInfo(userInfo);
	                if (listener != null) listener.onResult(ErrorCode.RESULT_DATA_OK, userInfo, msg);
	                notifyUserLogin();
	            } else {
	                if (listener != null) listener.onResult(code, null, msg);
	            }
	        });
	        loginRequester.execute();

3/6/2018 11:06:06 AM author by 王宇
package cn.geofound;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import cn.geofound.common.MyInit;


@Fail("json")
@IocBy(type=ComboIocProvider.class,args={
	"*org.nutz.ioc.loader.json.JsonLoader","/conf/datasource.js",
	"*org.nutz.ioc.loader.annotation.AnnotationIocLoader","cn.geofound"})
@Modules(scanPackage=true)
@SetupBy(MyInit.class)
@Encoding(input="UTF-8",output="UTF-8")
public class MainModule {
	
}

var ioc = {
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",                 
		fields : {                         
			paths : ["config.properties"]                 
		} 
	},
	custom : {
		type : "org.nutz.ioc.impl.PropertiesProxy",                 
		fields : {                         
			paths : ["custom.properties"]                 
		} 
	},
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"org.postgresql.Driver",
			url:"jdbc:postgresql://172.16.1.88:5432/school",
			username:"postgres",
			password:"post"
		}
	},
	//dao
	dao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	},
	mysqldao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'mysqlDataSource'}]
	},
	utils : {       
        type : 'cn.emay.common.util.MyUtils',       
        fields : {           
            sc : {app:'$servlet'}   // 将 ServletContext 对象注入 MyUtils  
        }  
	},
	tmpFilePool : {       
        type : 'org.nutz.filepool.NutFilePool',     // 临时文件最大个数为 1000 个  ，0表示不限制个数     
        args : [ {java:'$utils.getPath("/upload")'}, 0]      
	},
	uploadPicFileContext : {   
       type : 'org.nutz.mvc.upload.UploadingContext',       
       singleton : false,       
       args : [ { refer : 'tmpFilePool' } ],  
       fields : {  
           // 是否忽略空文件, 默认为 false           
           ignoreNull : true,   
           // 单个文件最大尺寸(大约的值，单位为字节，即 10485760 为 10M)           
           maxFileSize : 20485760,         
           // 正则表达式匹配可以支持的文件名           
           nameFilter : '^(.+[.])(gif|jpg|png|apk|ipa)$'  
       }  
    },  
    myUpload : { 
       type : 'org.nutz.mvc.upload.UploadAdaptor',       
       singleton : false,       
       args : [ { refer : 'uploadPicFileContext' } ]    
    }
};
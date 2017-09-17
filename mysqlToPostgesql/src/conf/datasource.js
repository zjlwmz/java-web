var ioc = {
	
	dataSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"org.postgresql.Driver",
			url:"jdbc:postgresql://localhost:5432/sfa",
			username:"postgres",
			password:"post"
		}
	},
	dataSource2:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"com.mysql.jdbc.Driver",
			url:"jdbc:mysql://localhost:3306/jeewx?useUnicode=true&characterEncoding=UTF-8",
			username:"root",
			password:"123456"
		}
	},
	dataSource3:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		events:{
			depose:"close"
		},
		fields:{
			driverClassName:"org.postgresql.Driver",
			url:"jdbc:postgresql://172.16.1.88:5432/lnglatoffset",
			//url:"jdbc:postgresql://localhost:5432/lnglatoffset",
			username:"postgres",
			password:"123456"
		}
	},
	sqlSource:{
		type:"org.apache.commons.dbcp.BasicDataSource",
		fields:{
			driverClassName:"com.microsoft.sqlserver.jdbc.SQLServerDriver",
			url:"jdbc:sqlserver://100.100.10.117:1433; DatabaseName=lonlatoffset",
			username:"sa",
			password:"emay123$%^"//emay123$%^
		}
	},
	//dao
	postgesqldao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource'}]
	},
	//dao
	postgesqldao2 : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource3'}]
	},
	mysqldao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'dataSource2'}]
	},
	
	sqlserverdao : {
		type : "org.nutz.dao.impl.NutDao",
		args : [{refer:'sqlSource'}]
	}
};
/**
 * 
 */
package cn.emay.common.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.nutz.lang.Files;
import org.nutz.mvc.View;

/**
 * @author zjlWm
 * @date
 *
 */
public class JPEGView implements View{
	@Override
	public void render(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Throwable {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		try {
			String imagesPath=request.getSession().getServletContext().getRealPath("resources")+"/images";
			File image=Files.getFile(new File(imagesPath), "default_avatar.png");
			InputStream input=new FileInputStream(image);
			byte[]b=new byte[1024];
			while(input.read(b)!=-1){
				out.write(b);
			}
			out.flush();
			input.close();
			out.close();
		} catch (Exception e) {
			
		} finally {
			out.close();
		}
	}
}
